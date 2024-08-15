package com.example.todoapp.presentation.fragments.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSettingsBinding
import com.example.todoapp.util.Constants
import com.example.todoapp.util.setLocale


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val mBinding get() = _binding!!
    val mLanguages by lazy {
        listOf(
            ContextCompat.getString(requireContext(), R.string.english),
            ContextCompat.getString(requireContext(), R.string.arabic)
        )
    }

    val mLangAdapter by lazy {
        ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line, mLanguages)
    }

    val mModes by lazy {
        listOf(
            ContextCompat.getString(requireContext(), R.string.light),
            ContextCompat.getString(requireContext(), R.string.night)
        )
    }

    val mModeAdapter by lazy {
        ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line, mModes)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI(){
        mBinding.autoCompleteTVLanguages.setAdapter(mLangAdapter)
        mBinding.autoCompleteTVModes.setAdapter(mModeAdapter)

        handleLanguageSelection()
        handleModeSelection()
    }

    private fun refreshDropdownMenus() {
        // Clear focus from the AutoCompleteTextView
        mBinding.autoCompleteTVLanguages.clearFocus()
        mBinding.autoCompleteTVModes.clearFocus()

        // Create new adapter instances
        val newLangAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mLanguages)
        val newModeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mModes)

        // Set the new adapters
        mBinding.autoCompleteTVLanguages.setAdapter(newLangAdapter)
        mBinding.autoCompleteTVModes.setAdapter(newModeAdapter)

        Log.d("SettingsFragment", "Languages: $mLanguages")
        Log.d("SettingsFragment", "Modes: $mModes")
    }

    private fun handleModeSelection() {
        mBinding.autoCompleteTVModes.setOnItemClickListener { parent, _, position, _ ->
            val selectedTheme = parent.getItemAtPosition(position).toString()
            when (selectedTheme) {
                mModes[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                mModes[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            refreshDropdownMenus()
        }
    }

    private fun handleLanguageSelection() {
        val sharedPref = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val savedLanguageCode = sharedPref.getString(Constants.LANG_SELECT_KEY, "en")

        val selectedLanguage = when (savedLanguageCode) {
            "en" -> mLanguages[0]
            "ar" -> mLanguages[1]
            else -> mLanguages[0]
        }

        mBinding.autoCompleteTVLanguages.setText(selectedLanguage, false)

        mBinding.autoCompleteTVLanguages.setOnItemClickListener { parent, view, position, id ->
            val selectedLanguage = parent.getItemAtPosition(position).toString()

            val languageCode = when (selectedLanguage) {
                mLanguages[0] -> "en"
                mLanguages[1] -> "ar"
                else -> "en"
            }

            sharedPref.edit().putString(Constants.LANG_SELECT_KEY, languageCode).apply()

            setLocale(requireContext(), languageCode)

            findNavController().navigate(R.id.settingsFragment)
        }
    }


}