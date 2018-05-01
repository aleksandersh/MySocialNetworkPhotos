package io.github.aleksandersh.mysocialnetworkphotos.presentation.friends

import android.graphics.BitmapFactory
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.FriendsInteractor
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.PhotoInteractor
import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.AdapterNotifier
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.model.*
import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField
import io.github.aleksandersh.simpleasync.AsyncTask
import io.github.aleksandersh.simpleasync.TaskSession
import io.github.aleksandersh.simpleasync.TasksBuffer
import io.github.aleksandersh.simpleasync.putInBuffer
import io.github.aleksandersh.simplemvp.Presenter
import java.util.*

class FriendsPresenter(
    private val resourceManager: ResourceManager,
    private val schedulersProvider: SchedulersProvider,
    private val friendsInteractor: FriendsInteractor,
    private val photoInteractor: PhotoInteractor
) : Presenter {

    companion object {

        private const val PHOTO_LOADING_TASKS_MAX_COUNT = 20
    }

    val viewState: FriendsViewState = FriendsViewState()

    private var initialized = false

    private val items: LinkedList<FriendsListItem> = LinkedList()
    private var currentPage = 0
    private var loading = false
    private var contentFinished = false
    private var loadFriendsTask: TaskSession? = null
    private val loadPhotoTasks: TasksBuffer = TasksBuffer(PHOTO_LOADING_TASKS_MAX_COUNT)

    override fun onDestroy() {
        Tree.applicationComponent
            .contentComponent
            .friendsComponent
            .release(FriendsView.TAG)
        loadFriendsTask?.cancel()
        loadPhotoTasks.cancel()
    }

    fun onViewCreated() {
        if (!initialized) {
            initialized = true
            initializeList()
        }
    }

    fun loadNextPage() {
        if (loading || contentFinished) return
        loading = true
        loadFriendsTask?.cancel()
        loadFriendsTask = AsyncTask
            .firstCall(schedulersProvider.backgroundThread) {
                val result = friendsInteractor.getFriends(currentPage)
                val friends = result.friends.map(::ItemFriend)
                val contentFinished = result.contentFinished
                Pair(friends, contentFinished)
            }
            .switchScheduler(schedulersProvider.mainThread)
            .thenProcess {
                val friends = it.first
                contentFinished = it.second
                val loadingItem = items.removeLast()
                val insertPosition = items.size
                var insertCount = friends.size
                items.addAll(friends)
                if (!contentFinished) {
                    items.add(loadingItem)
                    insertCount++
                }
                notifyAdapter(AdapterNotifier.ItemRemoved(insertPosition - 1))
                notifyAdapter(AdapterNotifier.ItemRangeInserted(insertPosition, insertCount))
                currentPage++
                loading = false
            }
            .handleError {
                items.removeLast()
                items.addLast(ItemError("Error!"))
                notifyAdapter(AdapterNotifier.ItemChanged(items.lastIndex))
                loading = false
            }
            .start()
    }

    fun loadPhoto(observableField: ObservableField<PhotoResult>, url: String) {
        AsyncTask
            .firstCall(schedulersProvider.backgroundThread) {
                photoInteractor.loadPhotoPreview(url)
            }
            .thenProcess {
                BitmapFactory.decodeByteArray(it, 0, it.size, BitmapFactory.Options())
            }
            .switchScheduler(schedulersProvider.mainThread)
            .thenProcess { observableField.set(PhotoResult(it, url)) }
            .handleError { } // TODO: тут можно выставлять заглушку при неудаче
            .start()
            .putInBuffer(loadPhotoTasks) // TODO: хотя это не решит проблему с отменой загрузки
        // картинки, она все равно скачается до конца, но результат передан не будет.
        // Может быть запускать каждую загрузку на отдельно потоке и при отмене грохать его?
    }

    private fun initializeList() {
        items.clear()
        items.add(ItemLoading())
        viewState.items.set(items)
        notifyAdapter(AdapterNotifier.ItemInserted(0))
    }

    private fun notifyAdapter(notifier: AdapterNotifier) {
        viewState.adapterNotifiers.set(notifier)
    }
}