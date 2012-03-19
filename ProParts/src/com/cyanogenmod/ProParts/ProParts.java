package com.cyanogenmod.ProParts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProParts extends PreferenceActivity  implements Preference.OnPreferenceChangeListener
{

    private ListPreference keyboardLayout;
    private Resources resources;  

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        keyboardLayout = (ListPreference) getPreferenceScreen().findPreference("keyboard_layout");
        keyboardLayout.setOnPreferenceChangeListener(this);
    }


	private void CopyAsset(String filein, String fileout) {
		try {
		InputStream myInput = this.getAssets().open(filein);
		String outFileName = "/data/data/com.cyanogenmod.ProParts/" + fileout;
		OutputStream myOutput;
		try {
			myOutput = new FileOutputStream(outFileName);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer))>0){
				myOutput.write(buffer, 0, length);
			}
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
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
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        String keychar1 =  "qwerty."+language+".kcm";
        String keylayout1 = "qwerty."+language+".kl";
		String trigger = "trigger";
		CopyAsset(keychar1, "pm8058-keypad.kcm");
		CopyAsset(keylayout1, "pm8058-keypad.kl");
		CopyAsset(trigger, "newKeyboard");
		pm.reboot("");
     }
}
