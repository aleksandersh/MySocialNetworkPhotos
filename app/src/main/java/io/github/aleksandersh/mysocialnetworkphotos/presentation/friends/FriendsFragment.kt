package io.github.aleksandersh.mysocialnetworkphotos.presentation.friends

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree
import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.AdapterNotifier
import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.ZeroScreenData
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.model.FriendVm
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.model.FriendsListItem
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.model.PhotoResult
import io.github.aleksandersh.mysocialnetworkphotos.presentation.photo.PhotoActivity
import io.github.aleksandersh.mysocialnetworkphotos.utils.extensions.isGone
import io.github.aleksandersh.mysocialnetworkphotos.utils.extensions.setTextOrHide
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.layout_zero_screen.*

class FriendsFragment : Fragment(), FriendsView {

    private lateinit var presenter: FriendsPresenter
    private lateinit var viewState: FriendsViewState
    private lateinit var adapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val appComponent = Tree.applicationComponent.provide(FriendsView.TAG)
        val friendsComponent = Tree.applicationComponent
            .contentComponent
            .friendsComponent
            .provide(FriendsView.TAG)

        val presenterProvider = appComponent.presenterProvider
        val presenterFactory = friendsComponent.friendsPresenterFactory

        presenter = presenterProvider.provide(this, FriendsView.TAG, presenterFactory)
        viewState = presenter.viewState

        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupList()

        viewState.items.subscribe(this, ::setItems)
        viewState.contentScreen.subscribe(this, ::showContent)
        viewState.zeroScreenData.subscribe(this, ::showZeroScreenData)
        viewState.adapterNotifiers.subscribe(this, ::notifyAdapter)

        presenter.onViewCreated()
    }

    private fun setupList() {
        if (!::adapter.isInitialized) {
            val onClickItem: (View, FriendVm) -> Unit =
                { view, friend -> onClickItem(view, friend) }
            adapter = FriendsAdapter(
                requireContext(),
                ::loadNextPage,
                ::loadPhoto,
                onClickItem,
                presenter::retryNextPageLoading
            )
        }
        fragment_friends_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        fragment_friends_recycler_view.adapter = adapter
    }

    private fun loadNextPage() {
        presenter.loadNextPage()
    }

    private fun loadPhoto(url: String, callback: (PhotoResult) -> Unit) {
        val observableField = ObservableField<PhotoResult>()
        observableField.subscribe(this, callback)
        presenter.loadPhoto(observableField, url)
    }

    private fun showContent(show: Boolean) {
        fragment_friends_recycler_view.isGone = !show
        fragment_friends_layout_zero_screen.isGone = show
    }

    private fun showZeroScreenData(data: ZeroScreenData) {
        layout_zero_screen_text_view_title.text = data.title
        layout_zero_screen_text_view_subtitle.setTextOrHide(data.subtitle)
        layout_zero_screen_button.isGone = !data.retry
        layout_zero_screen_progressbar.isGone = !data.progress
    }

    private fun notifyAdapter(notifier: AdapterNotifier) {
        notifier.notify(adapter)
    }

    private fun setItems(items: List<FriendsListItem>) {
        adapter.setItems(items)
        adapter.notifyDataSetChanged()
    }

    private fun onClickItem(view: View, friend: FriendVm) {
        val transitionName = ViewCompat.getTransitionName(view)
        val activity = requireActivity()

        val intent = Intent(activity, PhotoActivity::class.java)
        intent.putExtra(PhotoActivity.EXTRA_FRIEND, friend)
        intent.putExtra(PhotoActivity.EXTRA_TRANSITION_NAME, transitionName)

        val options = ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, view, transitionName)
            .toBundle()

        startActivity(intent, options)
    }
}