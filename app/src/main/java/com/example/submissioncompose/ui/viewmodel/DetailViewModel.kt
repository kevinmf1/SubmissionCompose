package com.example.submissioncompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.PlayerRepository
import com.example.submissioncompose.model.Player
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: PlayerRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Player>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Player>>
        get() = _uiState

    fun getPlayerById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getPlayerById(id))
    }


    fun updatePlayer(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updatePlayer(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getPlayerById(id)
            }
    }

}