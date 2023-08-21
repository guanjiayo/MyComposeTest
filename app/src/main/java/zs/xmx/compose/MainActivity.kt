package zs.xmx.compose

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import zs.xmx.compose.adapter.MainAdapter
import zs.xmx.compose.model.MainItem

class MainActivity : AppCompatActivity() {

    private val items = mutableListOf<MainItem>()
    private lateinit var mAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initAdapter()
        initEvent()
    }

    private fun initData() {
        items.add(MainItem("Text", "JetPack Compose 实现 TextView"))
        items.add(MainItem("TextField", "JetPack Compose 实现 EdiText"))

    }

    private fun initAdapter() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = MainAdapter(items)
        recyclerView.adapter = mAdapter
    }

    private fun initEvent() {
        mAdapter.setItemClickListener(object : MainAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> startActivity(Intent(this@MainActivity, TextViewActivity::class.java))
                    1 -> startActivity(Intent(this@MainActivity, TextFieldActivity::class.java))
                }
            }
        })
    }
}

