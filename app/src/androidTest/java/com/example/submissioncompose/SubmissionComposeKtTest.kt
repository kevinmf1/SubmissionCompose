package com.example.submissioncompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.submissioncompose.model.PlayerData
import com.example.submissioncompose.navigation.Screen
import com.example.submissioncompose.ui.theme.SubmissionComposeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SubmissionComposeKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionComposeTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                SubmissionCompose(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    // klik item pada lazy list, lalu cek apakah item yang dituju sesuai dengan yang diharapkan
    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("lazy_list").performScrollToIndex(5)
        composeTestRule.onNodeWithText(PlayerData.dummyPlayer[5].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailPlayer.route)
        composeTestRule.onNodeWithText(PlayerData.dummyPlayer[5].name).assertIsDisplayed()
    }

    // melakukan navigasi antar screen, lalu cek apakah screen yang dituju sesuai dengan yang diharapkan
    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    // melakukan navigasi ke halaman about, lalu cek apakah data yang ditampilkan sesuai dengan yang diharapkan
    @Test
    fun navigateTo_AboutPage() {
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.name_author).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.email_author).assertIsDisplayed()
    }

    // melakukan pencarian dengan keyword yang salah, lalu cek apakah data yang dicari tidak ada di list
    @Test
    fun searchShowEmptyListPlayer() {
        val incorrectSearch = "aa31z"
        composeTestRule.onNodeWithStringId(R.string.search_text).performTextInput(incorrectSearch)
        composeTestRule.onNodeWithTag("emptyList").assertIsDisplayed()
    }

    // melakukan pencarian dengan keyword yang benar, lalu cek apakah data yang dicari ada di list
    @Test
    fun searchShowListPlayer() {
        val rightSearch = "kevin"
        composeTestRule.onNodeWithStringId(R.string.search_text).performTextInput(rightSearch)
        composeTestRule.onNodeWithText("kevin").assertIsDisplayed()
    }

    // Klik favorite di detail screen, lalu cek apakah data favorite tersedia di favorite screen
    @Test
    fun favoriteClickInDetailScreen_ShowInFavoriteScreen() {
        composeTestRule.onNodeWithText(PlayerData.dummyPlayer[0].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailPlayer.route)
        composeTestRule.onNodeWithTag("favorite_detail_button").performClick()
        composeTestRule.onNodeWithTag("back_home").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(PlayerData.dummyPlayer[0].name).assertIsDisplayed()
    }

    // Klik favorite dan delete favorite di detail screen, lalu cek apakah data tidak ada di favorite screen
    @Test
    fun favoriteClickAndDeleteFavoriteInDetailScreen_NotShowInFavoriteScreen() {
        composeTestRule.onNodeWithText(PlayerData.dummyPlayer[0].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailPlayer.route)
        composeTestRule.onNodeWithTag("favorite_detail_button").performClick()
        composeTestRule.onNodeWithTag("favorite_detail_button").performClick()
        composeTestRule.onNodeWithTag("back_home").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.empty_favorite).assertIsDisplayed()
    }

    // Klik favorite di home screen, lalu cek apakah data favorite tersedia di favorite screen
    @Test
    fun favoriteClickInHome_ShowInFavoriteScreen() {
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithTag("lazy_list").performScrollToIndex(0)
        composeTestRule.onNodeWithText(PlayerData.dummyPlayer[0].name).assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("item_favorite_button").onFirst().performClick()
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(PlayerData.dummyPlayer[0].name).assertIsDisplayed()
    }

    // Klik favorite dan delete favorite di home screen, lalu cek apakah data tidak ada di favorite screen
    @Test
    fun favoriteClickAndDeleteFavoriteInHome_NotShowInFavoriteScreen() {
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithTag("lazy_list").performScrollToIndex(0)
        composeTestRule.onNodeWithText(PlayerData.dummyPlayer[0].name).assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("item_favorite_button").onFirst().performClick()
        composeTestRule.onAllNodesWithTag("item_favorite_button").onFirst().performClick()
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.empty_favorite).assertIsDisplayed()
    }

}