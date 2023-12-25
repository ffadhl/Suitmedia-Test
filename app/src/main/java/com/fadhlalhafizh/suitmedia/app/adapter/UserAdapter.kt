package com.fadhlalhafizh.suitmedia.app.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fadhlalhafizh.suitmedia.app.ui.thirdscreen.ThirdScreenActivity
import com.fadhlalhafizh.suitmedia.data.remote.respone.DataItem
import com.fadhlalhafizh.suitmedia.databinding.ItemUsersBinding

@SuppressLint("NotifyDataSetChanged")
class UserAdapter(
    private val list: ArrayList<DataItem>,
    private val onUserClickListener: ThirdScreenActivity
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    inner class UserViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userResponse: DataItem) {
            with(binding) {
                val firstName = userResponse.firstName
                val lastName = userResponse.lastName
                val email = userResponse.email
                val avatar = userResponse.avatar

                userName.text = "$firstName $lastName"
                userEmail.text = email
                Glide.with(itemView.context)
                    .load(avatar)
                    .override(140, 140)
                    .into(userAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUsersBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onUserClickListener.onUserItemClicked(position)
        }
    }

    override fun getItemCount(): Int = list.size

    fun addList(items: List<DataItem>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}