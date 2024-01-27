package org.hyperskill.simplewallpaper

import org.hyperskill.simplewallpaper.internals.CustomShadowAsyncDifferConfig
import org.hyperskill.simplewallpaper.internals.CustomShadowPicasso
import org.hyperskill.simplewallpaper.internals.CustomShadowRequestCreator
import org.hyperskill.simplewallpaper.internals.SimpleWallpaperTest
import org.hyperskill.simplewallpaper.internals.screen.MainScreen
import org.hyperskill.simplewallpaper.internals.screen.WallpaperDetailsScreen
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
class Stage4UnitTest : SimpleWallpaperTest<MainActivity>(MainActivity::class.java) {


    @Before
    fun setUp() {
        CustomShadowPicasso.setContext(activity)
        CustomShadowPicasso.setSingletonNull()
    }

    @Test
    fun test00_CheckingTheExchangeOfFragments() {
        testActivity {
            MainScreen(this).apply {
                recyclerView.assertSingleListItem(0) { itemViewSupplier ->
                    val itemView = itemViewSupplier()
                    itemView.clickAndRun()
                }
            }
            WallpaperDetailsScreen(this)
        }
    }

    @Test
    fun test01_checkingTheFragmentBackgroundImage(){
        testActivity(arguments = defaultArgsUrl) {
            imageUrlList.forEachIndexed { imageIndex, imageUrl ->
                MainScreen(this).apply {
                    navigateToWallpaperDetails(imageIndex)
                }
                WallpaperDetailsScreen(this).apply {
                    assertImageLoaded(
                        caseDescription = "After clicking on item then image with same url " +
                                "should be loaded into wallpaperImageView",
                        imageUrl = imageUrl
                    )
                    assertPlaceHolderAndErrorImages(
                        caseDescription = "When loading image into wallpaperImageView",
                        imageUrl = imageUrl
                    )
                    activity.clickBackAndRun()
                }
            }
        }
    }
    @Test
    fun test02_checkSetLockWallpaperImage() {
        testActivity(arguments = defaultArgsUrl) {
            imageUrlList.forEachIndexed { imageIndex, imageUrl ->
                MainScreen(this).apply {
                    navigateToWallpaperDetails(imageIndex)
                }
                WallpaperDetailsScreen(this).apply {
                    loadFakeResponseImageWithUrlAsPath(imageUrl)

                    lockScreenSetBtn.clickAndRun()
                    assertLockWallpaperSetToImageWithUrlAsPath(
                        caseDescription = "When lockScreenSetBtn is clicked",
                        imageUrl = imageUrl
                    )
                    assertMainWallpaperNotSetToImageWithUrlAsPath(
                        caseDescription = "When lockScreenSetBtn is clicked",
                        imageUrl = imageUrl
                    )

                    activity.clickBackAndRun()
                }
            }
        }
    }

    @Test
    fun test03_checkSetMainWallpaperImage() {
        testActivity(arguments = defaultArgsUrl) {
            imageUrlList.forEachIndexed { imageIndex, imageUrl ->
                MainScreen(this).apply {
                    navigateToWallpaperDetails(imageIndex)
                }
                WallpaperDetailsScreen(this).apply {
                    loadFakeResponseImageWithUrlAsPath(imageUrl)

                    screenSetBtn.clickAndRun()
                    assertMainWallpaperSetToImageWithUrlAsPath(
                        caseDescription = "When screenSetBtn is clicked",
                        imageUrl = imageUrl
                    )
                    assertLockWallpaperNotSetToImageWithUrlAsPath(
                        caseDescription = "When screenSetBtn is clicked",
                        imageUrl = imageUrl
                    )
                    activity.clickBackAndRun()
                }
            }
        }
    }

    @Test
    fun test04_checkSetBothWallpaperImage() {
        testActivity(arguments = defaultArgsUrl) {
            imageUrlList.forEachIndexed { imageIndex, imageUrl ->
                MainScreen(this).apply {
                    navigateToWallpaperDetails(imageIndex)
                }
                WallpaperDetailsScreen(this).apply {
                    loadFakeResponseImageWithUrlAsPath(imageUrl)

                    lockAndMainScreenSetBtn.clickAndRun()

                    assertMainWallpaperSetToImageWithUrlAsPath(
                        caseDescription = "When lockAndMainScreenSetBtn is clicked",
                        imageUrl = imageUrl
                    )
                    assertLockWallpaperSetToImageWithUrlAsPath(
                        caseDescription = "When lockAndMainScreenSetBtn is clicked",
                        imageUrl = imageUrl
                    )

                    activity.clickBackAndRun()
                }
            }
        }
    }
}