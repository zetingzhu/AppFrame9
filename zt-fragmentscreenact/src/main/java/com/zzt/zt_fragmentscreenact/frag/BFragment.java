package com.zzt.zt_fragmentscreenact.frag;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.zzt.zt_fragmentscreenact.R;

public class BFragment extends PreferenceFragmentCompat {
    public static BFragment newInstance() {
        Bundle args = new Bundle();
        BFragment fragment = new BFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}