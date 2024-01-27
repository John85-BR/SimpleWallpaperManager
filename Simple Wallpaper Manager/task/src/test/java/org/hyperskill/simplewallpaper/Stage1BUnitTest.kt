package org.hyperskill.simplewallpaper

import android.content.Intent
import org.hyperskill.simplewallpaper.internals.CustomShadowPicasso
import org.hyperskill.simplewallpaper.internals.SimpleWallpaperTest
import org.hyperskill.simplewallpaper.internals.screen.MainScreen
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner

// Version 1.1
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(RobolectricTestRunner::class)
class Stage1BUnitTest : SimpleWallpaperTest<MainActivity>(MainActivity::class.java) {
    @Before
    fun setUp() {
        CustomShadowPicasso.setContext(activity)
        CustomShadowPicasso.setSingletonNull()
    }
    @Test
    fun test00_checkMainScreenViews() {
        val fakeList: List<String> = emptyList()

        val args = Intent().apply {
            putExtra("imageUrlList", fakeList as java.io.Serializable)
        }
        testActivity(arguments = args) {
            MainScreen(this).apply {
                assertEmptyList()
            }
        }
    }
}