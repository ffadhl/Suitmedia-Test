package com.fadhlalhafizh.suitmedia.app.ui.thirdscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fadhlalhafizh.suitmedia.app.adapter.UserAdapter
import com.fadhlalhafizh.suitmedia.app.ui.secondcreen.SecondScreenActivity
import com.fadhlalhafizh.suitmedia.data.remote.respone.DataItem
import com.fadhlalhafizh.suitmedia.data.remote.respone.UsersResponse
import com.fadhlalhafizh.suitmedia.data.remote.retrofit.ApiConfig
import com.fadhlalhafizh.suitmedia.data.remote.retrofit.ApiService
import com.fadhlalhafizh.suitmedia.databinding.ActivityThirdScreenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdScreenActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityThirdScreenBinding
    private val userList = ArrayList<DataItem>()
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: UserAdapter
    private var page = 1
    private var totalPage = 1
    private var isLoading = false
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getStringExtra("name")!!

        layoutManager = LinearLayoutManager(this)
        binding.swipeRefresh.setOnRefreshListener(this)
        setupRecyclerView()
        getUsers(false)
        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, x: Int, y: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem =
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val total = adapter.itemCount
                if (!isLoading && page < totalPage && visibleItemCount + pastVisibleItem >= total) {
                    page++
                    getUsers(false)
                }
                super.onScrolled(recyclerView, x, y)
            }
        })
        setBackButtonHandler()
    }

    private fun getUsers(isRefreshing: Boolean) {
        isLoading = true
        if (!isRefreshing) binding.progressBar.visibility = View.VISIBLE
        val currentPage = page
        val perPage = 20
        val retrofit = ApiConfig.retrofitData().create(ApiService::class.java)
        retrofit.getUsers(currentPage,perPage).enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                totalPage = response.body()?.totalPages!!
                response.body()?.data?.let { adapter.addList(it) }

                binding.progressBar.visibility = View.INVISIBLE
                isLoading = false
                binding.swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Toast.makeText(this@ThirdScreenActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = layoutManager
        adapter = UserAdapter(userList, this)
        binding.rvUsers.adapter = adapter
    }

    override fun onRefresh() {
        adapter.clear()
        page = 1
        getUsers(true)
    }

    private fun setBackButtonHandler() {
        val intent = Intent(this, SecondScreenActivity::class.java).apply {
            putExtra("name", name)
        }
        binding.ivArrowBack.setOnClickListener {
            startActivity(intent)
        }
    }

    fun onUserItemClicked(position: Int) {
        val intent = Intent(this, SecondScreenActivity::class.java).apply {
            putExtra("name", name)
            putExtra("username", "${userList[position].firstName} ${userList[position].lastName}")
        }
        startActivity(intent)
    }
}