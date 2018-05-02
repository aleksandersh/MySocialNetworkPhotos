package io.github.aleksandersh.mysocialnetworkphotos.presentation.photo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.PhotoInfo
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.PhotoInfoInteractor
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.PhotoInteractor
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.model.FriendVm
import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.simpleasync.AsyncTask
import io.github.aleksandersh.simpleasync.TaskSession
import io.github.aleksandersh.simplemvp.Presenter

class PhotoPresenter(
    private val resourceManager: ResourceManager,
    private val schedulersProvider: SchedulersProvider,
    private val photoInteractor: PhotoInteractor,
    private val photoInfoInteractor: PhotoInfoInteractor
) : Presenter {

    val viewState = PhotoViewState()

    private lateinit var friend: FriendVm

    private var initialized = false
    private var loadPreviewTask: TaskSession? = null
    private var loadPhotoTask: TaskSession? = null
    private var loadPhotoInfoTask: TaskSession? = null

    override fun onDestroy() {
        loadPreviewTask?.cancel()
        loadPhotoTask?.cancel()
        loadPhotoInfoTask?.cancel()
    }

    fun onViewCreated(friend: FriendVm) {
        if (!initialized) {
            initialized = true
            this.friend = friend
            loadPreview(friend.smallPhotoUrl)
            loadPhotoInfo(checkNotNull(friend.photoId))
        } else {
            viewState.startPostponedTransition.set(true)
            loadPhoto(friend.bigPhotoUrl)
        }
    }

    private fun loadPreview(url: String) {
        loadPreviewTask?.cancel()
        loadPreviewTask = AsyncTask
            .firstCall(schedulersProvider.backgroundThread) {
                val photo = photoInteractor.loadPhotoPreview(url)
                BitmapFactory.decodeByteArray(photo, 0, photo.size)
            }
            .switchScheduler(schedulersProvider.mainThread)
            .thenProcess(::handlePreview)
            .handleError(::handlePreviewError)
            .start()
    }

    private fun handlePreview(photo: Bitmap) {
        viewState.photo.set(photo)
        viewState.startPostponedTransition.set(true)
        loadPhoto(friend.bigPhotoUrl)
    }

    private fun handlePreviewError(error: Throwable) {
        // TODO
    }

    private fun loadPhoto(url: String) {
        loadPhotoTask?.cancel()
        loadPhotoTask = AsyncTask
            .firstCall(schedulersProvider.backgroundThread) {
                val photo = photoInteractor.loadPhoto(url)
                BitmapFactory.decodeByteArray(photo, 0, photo.size)
            }
            .switchScheduler(schedulersProvider.mainThread)
            .thenProcess(::handlePhoto)
            .handleError(::handlePhotoError)
            .start()
    }

    private fun handlePhoto(photo: Bitmap) {
        viewState.photo.set(photo)
    }

    private fun handlePhotoError(error: Throwable) {
        // TODO
    }

    private fun loadPhotoInfo(photoId: String) {
        loadPhotoInfoTask?.cancel()
        loadPhotoInfoTask = AsyncTask
            .firstCall(schedulersProvider.backgroundThread) {
                photoInfoInteractor.getPhotoInfo(photoId)
            }
            .switchScheduler(schedulersProvider.mainThread)
            .thenProcess(::handlePhotoInfo)
            .handleError(::handlePhotoInfoError)
            .start()
    }

    private fun handlePhotoInfo(info: PhotoInfo) {
        viewState.photoInfo.set(info)
    }

    private fun handlePhotoInfoError(error: Throwable) {
        // TODO
    }
}