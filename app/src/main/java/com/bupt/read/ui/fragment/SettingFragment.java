package com.bupt.read.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.bupt.read.R;
import com.bupt.read.view.mvpview.SettingView;

/**
 * Created by xs on 2016/6/27.
 */
public class SettingFragment extends PreferenceFragment implements SettingView ,Preference.OnPreferenceClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()){
            case "sdfsdf":
                break;
        }
        return false;
    }
}
