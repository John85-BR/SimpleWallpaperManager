package org.hyperskill.simplewallpaper.internals

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import org.hyperskill.simplewallpaper.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.robolectric.Shadows.shadowOf

// Version 1.1
open class SimpleWallpaperTest<T : Activity>(clazz: Class<T>) : AbstractUnitTest<T>(clazz) {


    val fakeImageListWithNames = arrayListOf(
        R.drawable.image1 to "image1",
        R.drawable.image2 to "image2",
        R.drawable.image3 to "image3",
        R.drawable.image4 to "image4",
        R.drawable.image5 to "image5",
    )

    val imageUrlList = arrayListOf(
        "https://ucarecdn.com/26d28a62-ced1-437c-82b2-faae9cb65920/",
        "https://ucarecdn.com/ce2e77eb-553b-4e4a-82b7-cbd2f6ce0ac4/",
        "https://ucarecdn.com/f4dce147-bf2a-4852-8064-a4bdb766ca4e/",
        "https://ucarecdn.com/8b1c0fbf-3c07-425a-943e-d81219d12440/",
        "https://ucarecdn.com/b8d0e783-afaa-46b5-973d-cd433edf59ef/",
    )

    private val fakeImageList = fakeImageListWithNames.map { it.first }

    val fragmentContainer: FragmentContainerView by lazy {
        activity.findViewByString("fragmentContainerView")
    }

    val defaultArgsRes by lazy {
        Intent().apply {
            putExtra("imageIdList", fakeImageList as java.io.Serializable)
        }
    }
    val defaultArgsUrl by lazy {
        Intent().apply {
            putExtra("imageUrlList", imageUrlList as java.io.Serializable)
        }
    }

    private fun String.normalizeCase(ignoreCase: Boolean): String {
        return if (ignoreCase) this.lowercase() else this
    }

    private fun CharSequence.normalizeCase(ignoreCase: Boolean): String {
        return this.toString().normalizeCase(ignoreCase)
    }

    private fun assertTextEquals(
        errorMessage: String,
        expectedText: CharSequence,
        actualText: CharSequence,
        ignoreCase: Boolean = true
    ) {
        val (expectedTextNorm, actualTextNorm) = listOf(expectedText, actualText)
            .map { it.normalizeCase(ignoreCase) }
        assertEquals(errorMessage, expectedTextNorm, actualTextNorm)
    }

    fun Drawable.assertEquals(
        message: String, expectedResourceId: Int, expectedResourceIdString: String
    ) {

        val shadowDrawable = shadowOf(this)
        val actualResourceId = shadowDrawable.createdFromResId
        val errorMessage = "$message " +
                "expected $expectedResourceIdString with id $expectedResourceId, " +
                "but was _ with id $actualResourceId"
        assertTrue(errorMessage, actualResourceId == expectedResourceId)
    }

    fun Button.assertButtonText(
        idString: String,
        expectedText: String,
        ignoreCase: Boolean = true
    ) {
        assertTextEquals("Wrong text on $idString", expectedText, text, ignoreCase)
    }
    fun TextView.assertText(idString: String, expectedText: String, ignoreCase: Boolean = true) {
        assertTextEquals("Wrong text on $idString", expectedText, this.text, ignoreCase)
    }

    fun TextView.assertTextWithCustomErrorMessage(
        errorMessage: String, expectedText: String, ignoreCase: Boolean = true
    ) {
        assertTextEquals(errorMessage, expectedText, this.text, ignoreCase)
    }
}