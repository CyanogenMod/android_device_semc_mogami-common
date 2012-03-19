package com.cyanogenmod.ProParts;

/**
 * User: Pedro Veloso pedronveloso@androidpt.com
 */
import android.content.Context;
import android.util.Log;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interface to the Superuser shell on Android devices with some helper
 * functions.
 * <p/>
 * <p/>
 * Common usage for su shell:
 * <p/>
 * <code>if(ShellInterface.isSuAvailable()) { ShellInterface.runCommand("reboot"); }</code>
 * <p/>
 * <p/>
 * To get process output as a String:
 * <p/>
 * <code>if(ShellInterface.isSuAvailable()) { String date = ShellInterface.getProcessOutput("date"); }</code>
 * <p/>
 * <p/>
 * To run command with standard shell (no root permissions):
 * <code>ShellInterface.setShell("sh");</code>
 * <p/>
 * <code>ShellInterface.runCommand("date");</code>
 * <p/>
 * <p/>
 */
public class ShellInterface {

    private static String shell;

    // uid=0(root) gid=0(root)
    private static final Pattern UID_PATTERN = Pattern
            .compile("^uid=(\\d+).*?");

    enum OUTPUT {
        STDOUT, STDERR, BOTH
    }

    private static final String EXIT = "exit\n";

    private static final String[] SU_COMMANDS = new String[]{"su",
            "/system/xbin/su", "/system/bin/su"};

    private static final String[] TEST_COMMANDS = new String[]{"id",
            "/system/xbin/id", "/system/bin/id"};

    public static synchronized boolean isSuAvailable() {
        if (shell == null) {
            checkSu();
        }
        return shell != null;
    }

    public static synchronized void setShell(String shell) {
        ShellInterface.shell = shell;
    }

    private static boolean checkSu() {
        for (String command : SU_COMMANDS) {
            shell = command;
            if (isRootUid())
                return true;
        }
        shell = null;
        return false;
    }

    private static boolean isRootUid() {
        String out = null;
        for (String command : TEST_COMMANDS) {
            out = getProcessOutput(command);
            if (out != null && out.length() > 0)
                break;
        }
        if (out == null || out.length() == 0)
            return false;
        Matcher matcher = UID_PATTERN.matcher(out);
        if (matcher.matches()) {
            if ("0".equals(matcher.group(1))) {
                return true;
            }
        }
        return false;
    }

    public static String getProcessOutput(String command) {
        try {
            return _runCommand(command, OUTPUT.STDERR);
        } catch (IOException ignored) {
            return null;
        }
    }

    public static boolean runCommand(String command) {
        try {
            _runCommand(command, OUTPUT.BOTH);
            return true;
        } catch (IOException ignored) { }
        return false;
    }

    public static void _runCommandNoOutput(String command){
        try {
            String[] args = new String[] { "su", "-c", command };
    		Runtime.getRuntime().exec(args);
        } catch (Exception ignored) {}
    }

    private static String _runCommand(String command, OUTPUT o)
            throws IOException {
        DataOutputStream os = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(shell);
            os = new DataOutputStream(process.getOutputStream());
            InputStreamHandler sh = sinkProcessOutput(process, o);
            os.writeBytes(command + '\n');
            os.flush();
            os.writeBytes(EXIT);
            os.flush();
            process.waitFor();
            if (sh != null) {
                String output = sh.getOutput();
                Log.d(Constants.LOG_TAG, command + " output: " + output);
                return output;
            } else {
                return null;
            }
        } catch (Exception e) {
            final String msg = e.getMessage();
            Log.e(Constants.LOG_TAG, "runCommand error: " + msg);
            throw new IOException(msg);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (Exception ignored) {
            }
        }
    }

    public static InputStreamHandler sinkProcessOutput(Process p, OUTPUT o) {
        InputStreamHandler output = null;
        switch (o) {
            case STDOUT:
                output = new InputStreamHandler(p.getErrorStream(), false);
                new InputStreamHandler(p.getInputStream(), true);
                break;
            case STDERR:
                output = new InputStreamHandler(p.getInputStream(), false);
                new InputStreamHandler(p.getErrorStream(), true);
                break;
            case BOTH:
                new InputStreamHandler(p.getInputStream(), true);
                new InputStreamHandler(p.getErrorStream(), true);
                break;
        }
        return output;
    }

    private static class InputStreamHandler extends Thread {
        private final InputStream stream;
        private final boolean sink;
        StringBuffer output;

        public String getOutput() {
            return output.toString();
        }

        InputStreamHandler(InputStream stream, boolean sink) {
            this.sink = sink;
            this.stream = stream;
            start();
        }

        @Override
        public void run() {
            try {
                if (sink) {
                    while (stream.read() != -1) {
                    }
                } else {
                    output = new StringBuffer();
                    BufferedReader b = new BufferedReader(
                            new InputStreamReader(stream));
                    String s;
                    while ((s = b.readLine()) != null) {
                        output.append(s);
                    }
                }
            } catch (IOException ignored) {
            }
        }
    }


    public final static String SCRIPT_NAME = "surunner.sh";

	public static Process runSuCommandAsync(Context context, String command) throws IOException
	{
		DataOutputStream fout = new DataOutputStream(context.openFileOutput(SCRIPT_NAME, 0));
		fout.writeBytes(command);
		fout.close();

		String[] args = new String[] { "su", "-c", ". " + context.getFilesDir().getAbsolutePath() + "/" + SCRIPT_NAME };
		return Runtime.getRuntime().exec(args);
	}

	public static int runSuCommand(Context context, String command) throws IOException, InterruptedException
	{
		return runSuCommandAsync(context, command).waitFor();
	}

	public static int runSuCommandNoScriptWrapper(Context context, String command) throws IOException, InterruptedException
	{
		String[] args = new String[] { "su", "-c", command };
		Process proc = Runtime.getRuntime().exec(args);
		return proc.waitFor();
	}


}
