package com.example.submissioncompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.PlayerRepository
import com.example.submissioncompose.model.Player
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: PlayerRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Player>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Player>>>
        get() = _uiState

    fun getFavoritePlayer() = viewModelScope.launch {
        repository.getFavoritePlayer()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updatePlayer(id: Int, newState: Boolean) {
        repository.updatePlayer(id, newState)
        getFavoritePlayer()
    }
}