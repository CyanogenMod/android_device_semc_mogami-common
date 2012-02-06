package com.cyanogenmod.ProParts;

/**
 * User: Pedro Veloso
 * Date: 1/14/12
 * Time: 7:19 PM
 */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.content.Context;
import android.util.Log;


public class AssetsManagment {

	final static String ZIP_FILTER = "assets";
    static final int BUFSIZE = 5192;
    private boolean unzippedAssets = false;
    public Context ctx;
    
    public AssetsManagment(Context ctx){
    	this.ctx = ctx;
    }
    
    public void unzipAssets() {
        Log.v(Constants.LOG_TAG,"Unzipping Assets now.");
        if (!unzippedAssets)
        {
            String apkPath = ctx.getPackageCodePath();
            String mAppRoot = ctx.getFilesDir().toString();
            try {
                File zipFile = new File(apkPath);
                long zipLastModified = zipFile.lastModified();
                ZipFile zip = new ZipFile(apkPath);
                Vector<ZipEntry> files = getAssets(zip);

                Enumeration<?> entries = files.elements();
                File outputFile;
                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    String path = entry.getName().substring(ZIP_FILTER.length());
                    outputFile = new File(mAppRoot, path);
                    outputFile.getParentFile().mkdirs();

                    // negation of all
                    if (!(outputFile.exists() && entry.getSize() == outputFile.length() && zipLastModified < outputFile.lastModified()))
                    {
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        copyStreams(zip.getInputStream(entry), fos);
                        Runtime.getRuntime().exec("chmod 774 " + outputFile.getAbsolutePath());
                    }
                }
                unzippedAssets=true;
            } catch (IOException e) {
                Log.e(Constants.LOG_TAG,"Error: "+e.getMessage());
                unzippedAssets=false;
                e.printStackTrace();
            }
        }
    }
    
    
    private Vector<ZipEntry> getAssets(ZipFile zip) {
        Vector<ZipEntry> list = new Vector<ZipEntry>();
        Enumeration<?> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            if (entry.getName().startsWith(ZIP_FILTER)) {
                list.add(entry);
            }
        }
        return list;
    }
    
    private void copyStreams(InputStream is, FileOutputStream fos) {
        BufferedOutputStream os = null;
        try {
            byte data[] = new byte[BUFSIZE];
            int count;
            os = new BufferedOutputStream(fos, BUFSIZE);
            while ((count = is.read(data, 0, BUFSIZE)) != -1) {
                os.write(data, 0, count);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
	
}
