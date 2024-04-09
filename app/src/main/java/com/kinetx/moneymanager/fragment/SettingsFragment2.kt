package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.kinetx.moneymanager.R

class SettingsFragment2 : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val switchPreference = findPreference<SwitchPreference>("weekendSwitch")
        val listPreference = findPreference<ListPreference>("weekendPref")
        switchPreference?.let {
            listPreference?.isEnabled   = it.isChecked
            it.setOnPreferenceChangeListener{_, newValue ->
            listPreference?.isEnabled = newValue as Boolean
            true}
        }

    }
}