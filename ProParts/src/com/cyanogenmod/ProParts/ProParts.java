package com.cyanogenmod.ProParts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class ProParts extends PreferenceActivity  implements Preference.OnPreferenceChangeListener
{

    private ListPreference keyboardLayout;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        keyboardLayout = (ListPreference) getPreferenceScreen().findPreference("keyboard_layout");
        keyboardLayout.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceChange(Preference preference, Object value) {
        if (preference.equals(keyboardLayout)){
            final String newLanguage = (String) value;
                    
            AlertDialog dialog = new AlertDialog.Builder(this)
            .setTitle(R.string.warning)
            .setMessage(R.string.caution_keyboard_layout)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setNewKeyboardLanguage(newLanguage);
                }
            })
            .setNegativeButton(android.R.string.no, null)
            .create();

            dialog.show();
        }
        return false;
    }
    
    
    private void setNewKeyboardLanguage(String language){
        // access app assets, and assign the correct file absolute paths
        AssetsManagment assetManager = new AssetsManagment(ProParts.this);
        assetManager.unzipAssets();
        String filesDir = getFilesDir().getAbsolutePath();
        String keychar1 = filesDir + "/qwerty."+language+".bin";
        String keylayout1 = filesDir + "/qwerty."+language+".kl";
        //ask for root permissions
        if (ShellInterface.isSuAvailable()){
            ShellInterface.runCommand("mount -o rw,remount /system");
            StringBuilder commandsList = new StringBuilder();
            // remove the US keyboard that usually comes with CM rom
            //commandsList.append("rm /system/usr/keychars/us104-keyboard.kcm.bin ; ");
            commandsList.append("cp " + keychar1 + " /system/usr/keychars/pm8058-keypad.kcm.bin ; ");
            commandsList.append("chown 0:0 /system/usr/keychars/pm8058-keypad.kcm.bin ; ");
            commandsList.append("chmod 644 /system/usr/keychars/pm8058-keypad.kcm.bin ; ");

            commandsList.append("cp "+keychar1+" /system/usr/keychars/qwerty.kcm.bin ; ");
            commandsList.append("chown 0:0 /system/usr/keychars/qwerty.kcm.bin ; ");
            commandsList.append("chmod 664 /system/usr/keychars/qwerty.kcm.bin ; ");

            commandsList.append("cp "+keylayout1+" /system/usr/keylayout/pm8058-keypad.kl ; ");
            commandsList.append("chown 0:0 /system/usr/keychars/pm8058-keypad.kl ; ");
            commandsList.append("chmod 664 /system/usr/keychars/pm8058-keypad.kl ; ");

            commandsList.append("cp "+keylayout1+" /system/usr/keylayout/qwerty.kl ; ");
            commandsList.append("chown 0:0 /system/usr/keychars/qwerty.kl ; ");
            commandsList.append("chmod 664 /system/usr/keychars/qwerty.kl ; ");

            commandsList.append("sync ; ");
            commandsList.append("reboot ; ");
            try{
                ShellInterface.runSuCommand(ProParts.this,commandsList.toString());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
