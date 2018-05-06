package io.github.aleksandersh.mysocialnetworkphotos.presentation.friends

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.model.*
import kotlinx.android.synthetic.main.item_friends.view.*
import kotlinx.android.synthetic.main.item_friends_error.view.*

class FriendsAdapter(
    context: Context,
    private val loadNextPage: () -> Unit,
    private val loadPhoto: (url: String, callback: (PhotoResult) -> Unit) -> Unit,
    private val onClickItem: (View, FriendVm) -> Unit,
    private val onClickRetry: () -> Unit
) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    companion object {
        private const val LOAD_OFFSET = 3
    }

    private var items: List<FriendsListItem> = emptyList()
    private var lastLoadPosition: Int = 0

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            R.layout.item_friends -> {
                val view = inflater.inflate(viewType, parent, false)
                FriendViewHolder(view)
            }
            R.layout.item_friends_loading -> {
                val view = inflater.inflate(viewType, parent, false)
                LoadingViewHolder(view)
            }
            R.layout.item_friends_error -> {
                val view = inflater.inflate(viewType, parent, false)
                ErrorViewHolder(view)
            }
            else -> throw UnsupportedOperationException("Unsupported item type: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ItemFriend -> R.layout.item_friends
            is ItemLoading -> R.layout.item_friends_loading
            is ItemError -> R.layout.item_friends_error
            else -> throw UnsupportedOperationException("Unable to determine type of item with position: $position")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position > lastLoadPosition && position >= itemCount - LOAD_OFFSET) {
            lastLoadPosition = position
            loadNextPage()
        }
        holder.bind(items[position])
    }

    fun setItems(items: List<FriendsListItem>) {
        this.items = items
        lastLoadPosition = -1
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bind(item: FriendsListItem) {}
    }

    inner class FriendViewHolder(itemView: View) : ViewHolder(itemView) {

        private var photoUrl: String = ""

        override fun bind(item: FriendsListItem) {
            val friend = (item as ItemFriend).friend
            itemView.item_friends_text_view_name.text = friend.fullName

            val photoView = itemView.item_friends_image_view_photo
            photoView.setImageResource(android.R.color.transparent)
            val transitionName = friend.photoId
            ViewCompat.setTransitionName(photoView, transitionName.orEmpty())
            if (transitionName != null) {
                photoView.setOnClickListener { onClickItem(photoView, friend) }
            }

            photoUrl = friend.smallPhotoUrl
            loadPhoto(friend.smallPhotoUrl) { result ->
                if (result.url == photoUrl) {
                    itemView.item_friends_image_view_photo.setImageBitmap(result.bitmap)
                }
            }
        }
    }

    inner class ErrorViewHolder(itemView: View) : ViewHolder(itemView) {

        override fun bind(item: FriendsListItem) {
            val error = (item as ItemError).error
            itemView.item_friends_error_text_view_title.text = error
            itemView.item_friends_error_button_retry.setOnClickListener { onClickRetry() }
        }
    }

    class LoadingViewHolder(itemView: View) : ViewHolder(itemView)
}