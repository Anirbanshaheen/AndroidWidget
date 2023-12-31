package com.example.androidwidgetapp.recyclerViewViewType.stickyHeaderType

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.androidwidgetapp.R

const val TYPE_HEADER = 0
const val TYPE_ITEM = 1

class UserAdapter(val users: List<User>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    StickyHeaderItemDecoration.StickyHeaderInterface {

    override fun isHeader(itemPosition: Int): Boolean {
        return users[itemPosition].header
    }

    override fun bindHeaderData(header: View, headerPosition: Int) {
        ((header as ConstraintLayout).getChildAt(0) as TextView).text =
            users[headerPosition].name
    }

    override fun getHeaderLayout(headerPosition: Int): Int {
        return R.layout.row_header
    }

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        var headerPosition = 0
        var position = itemPosition
        do {
            if (this.isHeader(position)) {
                headerPosition = position
                break
            }
            position -= 1
        } while (position >= 0)
        return headerPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            ViewHolderHeader(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_header, parent, false)
            )
        } else {
            ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_user_item, parent, false)
            )
        }
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.nameView.text = users[position].name
        } else if (holder is ViewHolderHeader) {
            holder.headerView.text = users[position].name
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (users[position].header) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView = view.findViewById(R.id.nameView) as TextView
    }

    class ViewHolderHeader(view: View) : RecyclerView.ViewHolder(view) {
        val headerView = view.findViewById(R.id.headerView) as TextView
    }

}
