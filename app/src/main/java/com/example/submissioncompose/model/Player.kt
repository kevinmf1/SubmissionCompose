package com.example.submissioncompose.model

data class Player(
    val id: Int,
    val name: String,
    val club: String,
    val image: Int,
    val description: String,
    val positional: String,
    val rating: Double,
    val active: String,
    var isFavorite: Boolean = false
)