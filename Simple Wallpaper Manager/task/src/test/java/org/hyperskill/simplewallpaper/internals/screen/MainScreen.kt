package org.hyperskill.simplewallpaper.internals.screen


import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.hyperskill.simplewallpaper.R
import org.hyperskill.simplewallpaper.internals.CustomShadowPicasso
import org.hyperskill.simplewallpaper.internals.CustomShadowRequestCreator
import org.hyperskill.simplewallpaper.internals.SimpleWallpaperTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.robolectric.shadow.api.Shadow
import java.util.concurrent.TimeUnit

// Version 1.1
@Suppress("UNUSED")
class MainScreen<T : Activity>(val test: SimpleWallpaperTest<T>) {

    val recyclerView: RecyclerView = with(test) {
        fragmentContainer.findViewByString("recyclerView")
    }

    val emptyListTv: TextView = with(test) {
        val idString = "emptyListTv"
        fragmentContainer.findViewByString<TextView>(idString).apply {
            assertText(idString, expectedText = "No items", ignoreCase = false)
        }
    }

    fun assertRecyclerViewManager(){
        val layoutManager = recyclerView.layoutManager
        val messageUnexpectedClass = "RecyclerView's layout manager is not GridLayoutManager."
        assertTrue(messageUnexpectedClass,layoutManager is GridLayoutManager)
    }

    fun assertRecyclerViewGridLayoutSpanCount(){
        val layoutManager = recyclerView.layoutManager as? GridLayoutManager
        val messageWrongSpanCount =
            "RecyclerView's SpanCount value does not match the task condition!"
        assertTrue(messageWrongSpanCount,layoutManager?.spanCount == 2)

    }

    fun assertRecyclerViewItems(
        fakeResultList:  ArrayList<Pair<Int, String>> = test.fakeImageListWithNames) = with(test) {

        shadowLooper.idleFor(500, TimeUnit.MILLISECONDS)
        recyclerView.assertListItems(fakeResultList) { itemViewSupplier, index, (image, imageName) ->
            val itemView = itemViewSupplier()
            val imageView = itemView.findViewByString<ImageView>("imageView")
            val messageIncorrectImage = "Incorrect image found on recyclerView index $index"
            imageView.drawable.assertEquals(messageIncorrectImage, image, imageName)
        }
    }
    fun assertEmptyList() {

        val messageErrorNotEmpty = "Expected recyclerView to be empty"

        recyclerView.adapter?.itemCount?.also { size ->
            // if adapter is null then recycler must be empty
            assertEquals(messageErrorNotEmpty, 0, size)
        }

        val actualRecyclerVisibility = recyclerView.visibility
        val expectedRecyclerVisibility = View.GONE
        val messageRecyclerVisibility = "Expected recyclerView visibility to be GONE"

        assertTrue(
            messageRecyclerVisibility,
            expectedRecyclerVisibility == actualRecyclerVisibility
        )

        val actualEmptyTvVisibility = emptyListTv.visibility
        val expectedEmptyTvVisibility = View.VISIBLE
        val messageEmptyTvVisibility = "Expected emptyListTv visibility to be VISIBLE"

        assertTrue(
            messageEmptyTvVisibility,
            expectedEmptyTvVisibility == actualEmptyTvVisibility
        )
    }

    fun assertEachImageLoadedInDifferentImageView(imageUrlList : List<String>) = with(test) {
        recyclerView.assertListItems(imageUrlList) { _, _, _ ->
            // making sure all recycler item views are instantiated
        }

        val imageViewSet = imageUrlList.mapNotNull { urlString ->
            val request = CustomShadowPicasso.requestMap[urlString]
            Assert.assertNotNull("Expected request for url $urlString", request)
            val requestShadow = Shadow.extract<CustomShadowRequestCreator>(request)
            requestShadow.imageViewLoaded
        }.toSet()

        assertEquals(
            "Expected number of imageViews to have image loaded to be the same number as urls",
            imageUrlList.size,
            imageViewSet.size
        )
    }

    fun assertPlaceHolderAndErrorImages(imageUrlList : List<String>) = with(test) {
        recyclerView.assertListItems(imageUrlList) { _, _, urlString ->
            val request = CustomShadowPicasso.requestMap[urlString]
            Assert.assertNotNull("Expected request for url $urlString", request)
            val requestShadow = Shadow.extract<CustomShadowRequestCreator>(request)

            val messagePlaceholderIdError =
                "Expected placeholder to be set with R.drawable.placeholder"
            val expectedPlaceholderId = R.drawable.placeholder
            val actualPlaceholderId = requestShadow.placeholderId
            assertEquals(messagePlaceholderIdError, expectedPlaceholderId, actualPlaceholderId)

            val messageErrorIdError =
                "Expected error to be set with R.drawable.error"
            val expectedErrorId = R.drawable.error
            val actualErrorId = requestShadow.errorId
            assertEquals(messageErrorIdError, expectedErrorId, actualErrorId)
        }
    }

    fun navigateToWallpaperDetails(imageIndex: Int) = with(test) {
        recyclerView.assertSingleListItem(imageIndex) { itemViewSupplier ->
            val itemView = itemViewSupplier()
            CustomShadowPicasso.clearRequests()
            itemView.clickAndRun()
        }
    }
}