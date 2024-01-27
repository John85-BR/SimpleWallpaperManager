package org.hyperskill.simplewallpaper.internals.screen


import android.app.Activity
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageButton
import android.widget.ImageView
import org.hyperskill.simplewallpaper.R
import org.hyperskill.simplewallpaper.internals.CustomShadowPicasso
import org.hyperskill.simplewallpaper.internals.CustomShadowRequestCreator
import org.hyperskill.simplewallpaper.internals.SimpleWallpaperTest
import org.junit.Assert.*
import org.robolectric.Shadows
import org.robolectric.shadow.api.Shadow
import org.robolectric.shadows.ShadowBitmap

// Version 1.1
@Suppress("UNUSED")
class WallpaperDetailsScreen<T : Activity>(private val test: SimpleWallpaperTest<T>) {

    val wallpaperImageView: ImageView = with(test){
        fragmentContainer.findViewByString("wallpaperImageView")
    }

    val screenSetBtn : ImageButton = with(test) {
        val idString = "screenSetBtn"
        val expectedImageId = R.drawable.set_main_screen
        val expectedImageIdString = "R.drawable.set_desktop"
        activity.findViewByString<ImageButton>(idString).apply {
            drawable.assertEquals(
                message = "Incorrect drawable set on $idString",
                expectedResourceId = expectedImageId,
                expectedResourceIdString = expectedImageIdString
            )
        }
    }

    val lockScreenSetBtn : ImageButton = with(test) {
        val idString = "lockScreenSetBtn"
        val expectedImageId = R.drawable.set_lock_screen
        val expectedImageIdString = "R.drawable.set_lock_screen"
        activity.findViewByString<ImageButton>(idString).apply {
            drawable.assertEquals(
                message = "Incorrect drawable set on $idString",
                expectedResourceId = expectedImageId,
                expectedResourceIdString = expectedImageIdString
            )
        }
    }

    val lockAndMainScreenSetBtn : ImageButton = with(test) {
        val idString = "lockAndMainScreenSetBtn"
        val expectedImageId = R.drawable.both
        val expectedImageIdString = "R.drawable.set_lock_screen"
        activity.findViewByString<ImageButton>(idString).apply {
            drawable.assertEquals(
                message = "Incorrect drawable set on $idString",
                expectedResourceId = expectedImageId,
                expectedResourceIdString = expectedImageIdString
            )
        }
    }

    fun assertImageLoaded(caseDescription: String, imageUrl: String) = with(test) {
        val request = CustomShadowPicasso.requestMap[imageUrl]
        assertNotNull("$caseDescription. Expected request for url $imageUrl", request)
        val requestShadow = Shadow.extract<CustomShadowRequestCreator>(request)
        assertEquals("$caseDescription. Expected image to be loaded into ",  wallpaperImageView, requestShadow.imageViewLoaded)
    }

    fun assertPlaceHolderAndErrorImages(caseDescription: String, imageUrl: String) = with(test) {
        val request = CustomShadowPicasso.requestMap[imageUrl]
        assertNotNull("$caseDescription. Expected request for url $imageUrl", request)
        val requestShadow = Shadow.extract<CustomShadowRequestCreator>(request)

        val messagePlaceholderIdError =
            "$caseDescription. Expected placeholder to be set with R.drawable.placeholder"

        val expectedPlaceholderId = R.drawable.placeholder
        val actualPlaceholderId = requestShadow.placeholderId
        assertEquals(messagePlaceholderIdError, expectedPlaceholderId, actualPlaceholderId)

        val messageErrorIdError =
            "$caseDescription. Expected error to be set with R.drawable.error"

        val expectedErrorId = R.drawable.error
        val actualErrorId = requestShadow.errorId
        assertEquals(messageErrorIdError, expectedErrorId, actualErrorId)
    }
    fun loadFakeResponseImageWithUrlAsPath(imageUrl: String) {
        val decodeFile = BitmapFactory.decodeFile(imageUrl)

        val fromPath = BitmapDrawable.createFromPath(imageUrl)!!.also {
            (it as? BitmapDrawable)?.bitmap = decodeFile
        }
        wallpaperImageView.setImageDrawable(fromPath)
    }

    fun assertLockWallpaperSetToImageWithUrlAsPath(caseDescription: String, imageUrl: String) = with(test) {
        val shadowWallpaperManager = Shadows.shadowOf(WallpaperManager.getInstance(activity))
        val lockBitmap: Bitmap? = shadowWallpaperManager.getBitmap(WallpaperManager.FLAG_LOCK)
        val shadowLockBitmap: ShadowBitmap? = lockBitmap?.let { Shadows.shadowOf(it) }
        val actualPath = shadowLockBitmap?.createdFromPath
        val message =
            "$caseDescription expected lock wallpaper image to be set with image loaded from url"
        assertEquals(message, imageUrl, actualPath)
    }

    fun assertMainWallpaperSetToImageWithUrlAsPath(caseDescription: String, imageUrl: String) = with(test) {
        val shadowWallpaperManager = Shadows.shadowOf(WallpaperManager.getInstance(activity))
        val lockBitmap: Bitmap? = shadowWallpaperManager.getBitmap(WallpaperManager.FLAG_SYSTEM)
        val shadowLockBitmap: ShadowBitmap? = lockBitmap?.let { Shadows.shadowOf(it) }
        val actualPath = shadowLockBitmap?.createdFromPath
        val message =
            "$caseDescription expected main wallpaper image to be set with image loaded from url"
        assertEquals(message, imageUrl, actualPath)
    }

    fun assertLockWallpaperNotSetToImageWithUrlAsPath(caseDescription: String, imageUrl: String) = with(test) {
        val shadowWallpaperManager = Shadows.shadowOf(WallpaperManager.getInstance(activity))
        val lockBitmap: Bitmap? = shadowWallpaperManager.getBitmap(WallpaperManager.FLAG_LOCK)
        val shadowLockBitmap: ShadowBitmap? = lockBitmap?.let { Shadows.shadowOf(it) }
        val actualPath = shadowLockBitmap?.createdFromPath
        val message =
            "$caseDescription expected main wallpaper image to not be set with image loaded from url"
        assertNotEquals(message, imageUrl, actualPath)
    }

    fun assertMainWallpaperNotSetToImageWithUrlAsPath(caseDescription: String, imageUrl: String) = with(test) {
        val shadowWallpaperManager = Shadows.shadowOf(WallpaperManager.getInstance(activity))
        val lockBitmap: Bitmap? = shadowWallpaperManager.getBitmap(WallpaperManager.FLAG_SYSTEM)
        val shadowLockBitmap: ShadowBitmap? = lockBitmap?.let { Shadows.shadowOf(it) }
        val actualPath = shadowLockBitmap?.createdFromPath
        val message =
            "$caseDescription expected main wallpaper image to not be set with image loaded from url"
        assertNotEquals(message, imageUrl, actualPath)
    }
}