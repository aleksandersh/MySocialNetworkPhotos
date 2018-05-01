package io.github.aleksandersh.mysocialnetworkphotos.presentation.friends

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree
import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.AdapterNotifier
import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.ZeroScreenData
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.model.FriendsListItem
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.model.PhotoResult
import io.github.aleksandersh.mysocialnetworkphotos.utils.extensions.isGone
import io.github.aleksandersh.mysocialnetworkphotos.utils.extensions.setTextOrHide
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.layout_zero_screen.*

class FriendsFragment : Fragment(), FriendsView {

    private lateinit var presenter: FriendsPresenter
    private lateinit var viewState: FriendsViewState
    private lateinit var adapter: FriendsAdapter

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
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
//        fragment_friends_recycler_view.addItemDecoration(divider)
        fragment_friends_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        adapter = FriendsAdapter(requireContext(), ::loadNextPage, ::loadPhoto)
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
}