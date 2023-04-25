package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.kinetx.moneymanager.R

class SettingsFragment2 : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}