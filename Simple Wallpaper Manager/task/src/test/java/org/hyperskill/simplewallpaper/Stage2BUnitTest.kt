package org.hyperskill.simplewallpaper

import org.hyperskill.simplewallpaper.internals.CustomShadowAsyncDifferConfig
import org.hyperskill.simplewallpaper.internals.CustomShadowPicasso
import org.hyperskill.simplewallpaper.internals.SimpleWallpaperTest
import org.hyperskill.simplewallpaper.internals.screen.MainScreen
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// Version 1.1
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(RobolectricTestRunner::class)
@Config(shadows = [CustomShadowAsyncDifferConfig::class])
class Stage2BUnitTest : SimpleWallpaperTest<MainActivity>(MainActivity::class.java) {

    @Before
    fun setUp() {
        CustomShadowPicasso.setContext(activity)
        CustomShadowPicasso.setSingletonNull()
    }
    @Test
    fun test00_checkRecyclerViewManager() {
        testActivity(arguments = defaultArgsUrl) {
            MainScreen(this).apply {
                assertRecyclerViewManager()
            }
        }
    }
    @Test
    fun test01_checkGridLayoutManagerSpanCount() {
        testActivity(arguments = defaultArgsUrl) {
            MainScreen(this).apply {
                assertRecyclerViewGridLayoutSpanCount()
            }
        }
    }
    @Test @Ignore("Incompatible with stage3 requirements")
    fun test02_checkMainScreenViews() {
        testActivity (arguments = defaultArgsRes){
            MainScreen(this).apply {
                assertRecyclerViewItems()
            }
        }
    }
}