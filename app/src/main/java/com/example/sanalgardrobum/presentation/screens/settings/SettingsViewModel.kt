package com.example.sanalgardrobum.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sanalgardrobum.domain.usecase.auth.GetCurrentUserUseCase
import com.example.sanalgardrobum.domain.usecase.auth.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val notifications: Boolean = true,
    val aiSuggestions: Boolean = true,
    val userName: String = "",
    val userEmail: String = "",
    val userPhotoUrl: String? = null
)

sealed class SettingsNavigationEvent {
    data object NavigateToLogin : SettingsNavigationEvent()
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<SettingsNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        val user = getCurrentUserUseCase()
        if (user != null) {
            _uiState.update {
                it.copy(
                    userName = user.displayName ?: "Kullanıcı",
                    userEmail = user.email ?: "",
                    userPhotoUrl = user.photoUrl
                )
            }
        }
    }

    fun onNotificationsToggled(enabled: Boolean) {
        _uiState.update { it.copy(notifications = enabled) }
        // TODO: DataStore/SharedPreferences'a kaydet
    }

    fun onAiSuggestionsToggled(enabled: Boolean) {
        _uiState.update { it.copy(aiSuggestions = enabled) }
        // TODO: DataStore/SharedPreferences'a kaydet
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            signOutUseCase()
            _navigationEvent.send(SettingsNavigationEvent.NavigateToLogin)
        }
    }
}
