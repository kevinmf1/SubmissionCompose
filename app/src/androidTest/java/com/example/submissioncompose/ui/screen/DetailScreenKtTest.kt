package com.example.submissioncompose.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.submissioncompose.R
import com.example.submissioncompose.model.Player
import com.example.submissioncompose.ui.theme.SubmissionComposeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeDataPlayer = Player(
        id = 0,
        name = "Erling Haaland",
        club = "Manchester City",
        image = R.drawable.erling_haaland,
        description = "Erling Haaland is a Norwegian professional footballer who currently plays as a striker for German club Borussia Dortmund and the Norway national team. He is known for his incredible speed, strength, and finishing ability, and has quickly established himself as one of the best young players in the world.",
        positional = "Forward",
        rating = 5.0,
        active = "Active",
        isFavorite = false
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionComposeTheme {
                DetailInformation(
                    id = fakeDataPlayer.id,
                    name = fakeDataPlayer.name,
                    club = fakeDataPlayer.club,
                    image = fakeDataPlayer.image,
                    description = fakeDataPlayer.description,
                    positional = fakeDataPlayer.positional,
                    active = fakeDataPlayer.active,
                    rating = fakeDataPlayer.rating,
                    isFavorite = fakeDataPlayer.isFavorite,
                    navigateBack = {},
                    onFavoriteButtonClicked = {_, _ ->}
                )
            }
        }
    }

    @Test
    fun detailInformation_isDisplayed() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
        composeTestRule.onNodeWithText(fakeDataPlayer.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataPlayer.club).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataPlayer.positional).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataPlayer.description).assertIsDisplayed()
    }

    @Test
    fun addToFavoriteButton_hasClickAction() {
        composeTestRule.onNodeWithTag("favorite_detail_button").assertHasClickAction()
    }

    @Test
    fun detailInformation_isScrollable() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
    }

    @Test
    fun favoriteButton_hasCorrectStatus() {
        // Assert that the favorite button is displayed
        composeTestRule.onNodeWithTag("favorite_detail_button").assertIsDisplayed()

        // Assert that the content description of the favorite button is correct based on the isFavorite state
        val isFavorite = fakeDataPlayer.isFavorite // Set the isFavorite state here
        val expectedContentDescription = if (isFavorite) {
            "Remove from Favorite"
        } else {
            "Add to Favorite"
        }

        composeTestRule.onNodeWithTag("favorite_detail_button")
            .assertContentDescriptionEquals(expectedContentDescription)
    }
}