package com.example.testapp.testapp.DatabaseExample.api;

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testapp.testapp.DatabaseExample.api.response.notification.NotificationListResponse
import com.example.testapp.testapp.R

class PushAdapter(
    val context: Context?,
    private val notificationList: List<NotificationListResponse.NotificationResponse.NotificationList>
) :
    RecyclerView.Adapter<PushAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PushAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            layoutInflater?.inflate(
                R.layout.item_notification_list,
                parent,
                false
            )!!
        )
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val notificationResponse: NotificationListResponse.NotificationResponse.NotificationList =
            notificationList[position]

        holder.tvNotificationText.text = notificationResponse.message
        holder.tvNotificationTime.text = notificationResponse.created_at

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNotificationText = view.tvNotificationText!!
        val tvNotificationTime = view.tvNotificationTime!!
    }
}