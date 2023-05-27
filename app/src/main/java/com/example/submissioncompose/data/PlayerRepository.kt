package com.example.submissioncompose.data

import com.example.submissioncompose.model.PlayerData
import com.example.submissioncompose.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class PlayerRepository {
    private val dummyPlayer = mutableListOf<Player>()

    init {
        if (dummyPlayer.isEmpty()) {
            PlayerData.dummyPlayer.forEach {
                dummyPlayer.add(it)
            }
        }
    }

    fun getPlayerById(playerId: Int): Player {
        return dummyPlayer.first {
            it.id == playerId
        }
    }

    fun getFavoritePlayer(): Flow<List<Player>> {
        return flowOf(dummyPlayer.filter { it.isFavorite })
    }

    fun searchPlayer(query: String) = flow {
        val data = dummyPlayer.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updatePlayer(playerId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyPlayer.indexOfFirst { it.id == playerId }
        val result = if (index >= 0) {
            val player = dummyPlayer[index]
            dummyPlayer[index] = player.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: PlayerRepository? = null

        fun getInstance(): PlayerRepository =
            instance ?: synchronized(this) {
                PlayerRepository().apply {
                    instance = this
                }
            }
    }
}