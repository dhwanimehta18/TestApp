package com.example.testapp.testapp.DatabaseExample.api;


import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testapp.testapp.DatabaseExample.api.response.notification.NotificationListResponse
import com.example.testapp.testapp.DatabaseExample.listDemo.Constants
import com.example.testapp.testapp.R
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.layout_no_data_found.*
import retrofit2.Response
import rx.Observer
import timber.log.Timber
import java.util.*

class PushFragment : Fragment() {

    private var notificationList = mutableListOf<NotificationListResponse.NotificationResponse.NotificationList>()
    private var pushAdapter: PushAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    var pageOffset: Int = 0
    var totalPage: Int = 0
    var isLoad: Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initComponents()
    }

    /**
     * Initialize components
     */
    private fun initComponents() {
        pageOffset = 0

        notificationList.clear()

        linearLayoutManager = LinearLayoutManager(activity)
        pushAdapter = context?.let { PushAdapter(it, notificationList) }
        rvNotificationList.layoutManager = linearLayoutManager
        rvNotificationList.adapter = pushAdapter

        getCurrentNotificationList()

        swipeNotificationList.setColorSchemeResources(R.color.colorAccent)
        swipeNotificationList.setOnRefreshListener {
            if (mNetworkUtils.isConnected) {
                pageOffset = 0
                isLoad = true
                getCurrentNotificationList()
            } else {
                swipeNotificationList.isRefreshing = false
            }
        }

        rvNotificationList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (notificationList.size < totalPage) {
                    if (linearLayoutManager!!.childCount + linearLayoutManager!!.findFirstVisibleItemPosition() >= linearLayoutManager!!.itemCount) {
                        if (!isLoad) {
                            if (dy > 0) {
                                if (mNetworkUtils.isConnected) {
                                    isLoad = true
                                    getCurrentNotificationList()
                                } else {
                                    showError(getString(R.string.internet_not_available))
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    private fun getCurrentNotificationList() {

        when {
            !swipeNotificationList.isRefreshing ->
                showLoader()
        }

        val mCurrentNotificationList =
                mApiManager.callNotificationList(pageOffset.toString(), Constants.OtherConsts.DEFAULT_PAGE_LIMIT.toString())


        val subscription = mCurrentNotificationList?.subscribe(object : Observer<Response<NotificationListResponse>> {
            override fun onCompleted() {
                isLoad = false
                hideLoader()
            }

            override fun onError(e: Throwable) {
                handleApiError(e, "")
                rvNotificationList.visibility = View.GONE
                llNoDataFound.visibility = View.VISIBLE
                swipeNotificationList.isRefreshing = false
            }

            override fun onNext(getFilterResponse: Response<NotificationListResponse>?) {
                val response = getFilterResponse?.body()!!.data

                if (response.list.isEmpty()) {
                    rvNotificationList.visibility = View.GONE
                    llNoDataFound.visibility = View.VISIBLE
                } else {
                    rvNotificationList.visibility = View.VISIBLE
                    llNoDataFound.visibility = View.GONE
                }

                if (swipeNotificationList.isRefreshing) {
                    notificationList.clear()
                    swipeNotificationList.isRefreshing = false
                }

                pageOffset += Constants.OtherConsts.DEFAULT_PAGE_LIMIT
                totalPage = response.total_records
                notificationList.addAll(response.list)
                Timber.e("NotificationList-->%s", notificationList.size)

                pushAdapter?.notifyDataSetChanged()

            }
        })

        mCompositeSubscription.add(subscription)
    }
}
