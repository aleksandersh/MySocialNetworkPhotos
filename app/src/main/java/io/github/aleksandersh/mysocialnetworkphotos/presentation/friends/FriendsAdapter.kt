package io.github.aleksandersh.mysocialnetworkphotos.presentation.friends

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.Friend
import kotlinx.android.synthetic.main.item_friends.view.*

class FriendsAdapter(context: Context) : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {

    var items: List<Friend> = emptyList()

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val view = inflater.inflate(R.layout.item_friends, parent, false)
        return FriendsViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(friend: Friend) {
            itemView.item_friends_text_view_name.text = friend.name
        }
    }
}