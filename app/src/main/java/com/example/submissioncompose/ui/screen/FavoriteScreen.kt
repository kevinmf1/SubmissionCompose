package com.example.submissioncompose.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissioncompose.R
import com.example.submissioncompose.di.Injection
import com.example.submissioncompose.model.Player
import com.example.submissioncompose.ui.common.UiState
import com.example.submissioncompose.ui.item.EmptyList
import com.example.submissioncompose.ui.viewmodel.FavoriteViewModel
import com.example.submissioncompose.ui.viewmodel.ViewModelFactory

@Composable
fun FavoriteScreen(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoritePlayer()
            }
            is UiState.Success -> {
                FavoriteInformation(
                    listPlayer = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updatePlayer(id, newState)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteInformation(
    listPlayer: List<Player>,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        if (listPlayer.isNotEmpty()) {
            ListPlayer(
                listPlayer = listPlayer,
                onFavoriteIconClicked = onFavoriteIconClicked,
                contentPaddingTop = 16.dp,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyList(
                Warning = stringResource(R.string.empty_favorite)
            )
        }
    }
}