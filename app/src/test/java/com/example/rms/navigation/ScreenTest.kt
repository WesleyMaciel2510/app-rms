package com.example.rms.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import org.junit.Test
import org.junit.Assert.*

class ScreenTest {

    @Test
    fun screenHasFourDestinations() {
        val screens = Screen.values()
        assertEquals(4, screens.size)
    }

    @Test
    fun homeScreenHasCorrectProperties() {
        assertEquals("home", Screen.Home.route)
        assertEquals(Icons.Filled.Home, Screen.Home.icon)
        assertEquals("Home", Screen.Home.contentDescription)
    }

    @Test
    fun searchScreenHasCorrectProperties() {
        assertEquals("search", Screen.Search.route)
        assertEquals(Icons.Filled.Search, Screen.Search.icon)
        assertEquals("Search", Screen.Search.contentDescription)
    }

    @Test
    fun favoritesScreenHasCorrectProperties() {
        assertEquals("favorites", Screen.Favorites.route)
        assertEquals(Icons.Filled.Favorite, Screen.Favorites.icon)
        assertEquals("Favorites", Screen.Favorites.contentDescription)
    }

    @Test
    fun profileScreenHasCorrectProperties() {
        assertEquals("profile", Screen.Profile.route)
        assertEquals(Icons.Filled.Person, Screen.Profile.icon)
        assertEquals("Profile", Screen.Profile.contentDescription)
    }
}