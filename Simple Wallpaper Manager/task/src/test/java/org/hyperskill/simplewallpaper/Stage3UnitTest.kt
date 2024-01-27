package org.hyperskill.simplewallpaper

import org.hyperskill.simplewallpaper.internals.CustomShadowAsyncDifferConfig
import org.hyperskill.simplewallpaper.internals.CustomShadowPicasso
import org.hyperskill.simplewallpaper.internals.CustomShadowRequestCreator
import org.hyperskill.simplewallpaper.internals.SimpleWallpaperTest
import org.hyperskill.simplewallpaper.internals.screen.MainScreen
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// Version 1.1
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(RobolectricTestRunner::class)
@Config(shadows = [CustomShadowPicasso::class, CustomShadowRequestCreator::class, CustomShadowAsyncDifferConfig::class])
class Stage3UnitTest : SimpleWallpaperTest<MainActivity>(MainActivity::class.java) {


    @Before
    fun setUp() {
        CustomShadowPicasso.setContext(activity)
        CustomShadowPicasso.setSingletonNull()
    }

    @Test
    fun test00_checkPlaceHolderAndErrorImagesSet() {
        testActivity(arguments = defaultArgsUrl) {
            MainScreen(this).apply {
                assertPlaceHolderAndErrorImages(imageUrlList)
            }
        }
    }

    @Test
    fun test01_checkGetImagePicasso() {
        testActivity(arguments = defaultArgsUrl) {
            MainScreen(this).apply {
                assertEachImageLoadedInDifferentImageView(imageUrlList)
            }
        }
    }
}