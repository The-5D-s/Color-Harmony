package com.example.color_harmony;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import androidx.annotation.Nullable;
import android.preference.CheckBoxPreference;
import androidx.preference.PreferenceManager;

public class Preference extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        loadSettings();
    }

    private void loadSettings(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean checkNight = sp.getBoolean("NIGHT", false);
        if(checkNight){
            getListView().setBackgroundColor(Color.parseColor("#222222"));
        }else{
            getListView().setBackgroundColor(Color.parseColor("#ffffff"));
        }

        CheckBoxPreference night  = (CheckBoxPreference) findPreference("NIGHT");
        night.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference pref, Object obj) {
                boolean yes = (boolean) obj;

                if (yes){
                    getListView().setBackgroundColor(Color.parseColor("#222222"));
                }
                else{
                    getListView().setBackgroundColor(Color.parseColor("#ffffff"));
                }
                return true;
            }
        });

        ListPreference lP = (ListPreference) findPreference("ORIENTATION");
        String orien = sp.getString("ORIENTATION", "false");

        if("1".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
            lP.setSummary(lP.getEntry());
        }
        else if("2".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            lP.setSummary(lP.getEntry());
        }
        else if("3".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            lP.setSummary(lP.getEntry());
        }
        lP.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference prefs, Object obj) {

                String items = (String) obj;
                if(prefs.getKey().equals("ORIENTATION")){
                    switch (items){
                        case "1":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                            break;
                        case "2":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            break;
                        case "3":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            break;
                    }
                    ListPreference lPP = (ListPreference) prefs;
                    lPP.setSummary(lPP.getEntries()[lPP.findIndexOfValue(items)]);

                }
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}
