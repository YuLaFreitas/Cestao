package com.mercados.cestao;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.annotation.NonNull;

public class SettingFragment extends PreferenceFragment {
    @NonNull
    public static SettingFragment newInstance(){
        return new SettingFragment();
    }
    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
        addPreferencesFromResource(R.xml.preferences);
        Fragment fragment =
                getFragmentManager().findFragmentById(android.R.id.content);

        if(fragment == null){
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, SettingFragment.newInstance());
        }else{
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, SettingFragment.newInstance())
                .commit();
        }
    }
}
