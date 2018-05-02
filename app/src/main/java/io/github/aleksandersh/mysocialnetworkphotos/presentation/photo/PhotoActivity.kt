package io.github.aleksandersh.mysocialnetworkphotos.presentation.photo

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.PhotoInfo
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.model.FriendVm
import kotlinx.android.synthetic.main.fragment_photo.*

class PhotoActivity : AppCompatActivity(), PhotoView {

    companion object {

        const val EXTRA_FRIEND = "key_friend"
        const val EXTRA_TRANSITION_NAME = "key_transition_name"
        const val UPDATE_PHOTO_INTERVAL_MS = 1000
    }

    private lateinit var friend: FriendVm
    private lateinit var presenter: PhotoPresenter
    private lateinit var viewState: PhotoViewState

    private val handler = Handler()

    private var lastPhotoUpdate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_photo)
        supportPostponeEnterTransition()

        intent.extras?.let { args ->
            friend = checkNotNull(args.getParcelable(EXTRA_FRIEND))
            args.getString(EXTRA_TRANSITION_NAME)
                ?.let { transitionName ->
                    ViewCompat.setTransitionName(fragment_photo_image_view, transitionName)
                }

        } ?: throw RuntimeException("Arguments of the fragment is not set")

        setSupportActionBar(fragment_photo_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            subtitle = friend.fullName
        }

        val appComponent = Tree.applicationComponent.provide(PhotoView.TAG)
        val photoComponent = Tree.applicationComponent
            .contentComponent
            .photoComponent
            .provide(PhotoView.TAG)

        val presenterProvider = appComponent.presenterProvider
        val presenterFactory = photoComponent.photoPresenterFactory

        presenter = presenterProvider.provide(this, PhotoView.TAG, presenterFactory)
        viewState = presenter.viewState

        viewState.startPostponedTransition.subscribe(this, ::startPostponedTransition)
        viewState.photo.subscribe(this, ::showPhoto)
        viewState.photoInfo.subscribe(this, ::showPhotoInfo)

        presenter.onViewCreated(friend)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                leaveView()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun leaveView() {
        supportFinishAfterTransition()
    }

    private fun startPostponedTransition(start: Boolean) {
        if (start) {
            supportStartPostponedEnterTransition()
        }
    }

    private fun showPhoto(photo: Bitmap) {
        val currentTime = System.currentTimeMillis()
        val nextUpdate = lastPhotoUpdate + UPDATE_PHOTO_INTERVAL_MS
        if (currentTime < nextUpdate) {
            val delay = nextUpdate - currentTime
            handler.postDelayed({ fragment_photo_image_view.setImageBitmap(photo) }, delay)
        } else {
            fragment_photo_image_view.setImageBitmap(photo)
        }
        lastPhotoUpdate = currentTime
    }

    private fun showPhotoInfo(photoInfo: PhotoInfo) {
        fragment_photo_text_view_like_count.text = photoInfo.likeCount.toString()
        fragment_photo_text_view_comment_count.text = photoInfo.commentCount.toString()
        fragment_photo_layout_info.visibility = View.VISIBLE
    }
}