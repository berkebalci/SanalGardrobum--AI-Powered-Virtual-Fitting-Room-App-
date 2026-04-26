package com.example.sanalgardrobum.presentation.screens.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsUiState(
    val notifications: Boolean = true,
    val aiSuggestions: Boolean = true
)

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun onNotificationsToggled(enabled: Boolean) {
        _uiState.update { it.copy(notifications = enabled) }
        // TODO: DataStore/SharedPreferences'a kaydet
    }

    fun onAiSuggestionsToggled(enabled: Boolean) {
        _uiState.update { it.copy(aiSuggestions = enabled) }
        // TODO: DataStore/SharedPreferences'a kaydet
    }

    fun onLogoutClicked() {
        // TODO: Firebase Auth signOut
    }
}
