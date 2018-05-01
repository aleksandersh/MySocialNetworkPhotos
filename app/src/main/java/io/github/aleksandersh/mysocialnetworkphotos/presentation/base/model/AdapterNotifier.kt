package io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model

import android.support.v7.widget.RecyclerView

sealed class AdapterNotifier {

    abstract fun notify(adapter: RecyclerView.Adapter<*>)

    class DataSetChanged : AdapterNotifier() {

        override fun notify(adapter: RecyclerView.Adapter<*>) {
            adapter.notifyDataSetChanged()
        }
    }

    class ItemInserted(private val position: Int) : AdapterNotifier() {

        override fun notify(adapter: RecyclerView.Adapter<*>) {
            adapter.notifyItemInserted(position)
        }
    }

    class ItemChanged(private val position: Int) : AdapterNotifier() {
        override fun notify(adapter: RecyclerView.Adapter<*>) {
            adapter.notifyItemChanged(position)
        }

    }

    class ItemRemoved(private val position: Int) : AdapterNotifier() {

        override fun notify(adapter: RecyclerView.Adapter<*>) {
            adapter.notifyItemRemoved(position)
        }
    }

    class ItemRangeInserted(
        private val positionStart: Int,
        private val count: Int
    ) : AdapterNotifier() {

        override fun notify(adapter: RecyclerView.Adapter<*>) {
            adapter.notifyItemRangeInserted(positionStart, count)
        }
    }
}