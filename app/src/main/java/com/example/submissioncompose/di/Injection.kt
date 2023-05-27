package com.example.submissioncompose.di

import com.example.submissioncompose.data.PlayerRepository

object Injection {
    fun provideRepository(): PlayerRepository {
        return PlayerRepository.getInstance()
    }
}