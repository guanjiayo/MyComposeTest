package zs.xmx.compose

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import zs.xmx.compose.ui.button.ButtonActivity
import zs.xmx.compose.adapter.MainAdapter
import zs.xmx.compose.model.MainItem
import zs.xmx.compose.ui.TextFieldActivity
import zs.xmx.compose.ui.TextViewActivity
import zs.xmx.compose.ui.image.ImageActivity

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
        items.add(MainItem("Text", "Android 原生 TextView"))
        items.add(MainItem("TextField", "Android 原生 EdiText"))
        items.add(MainItem("Button", "Android 原生 Button"))
        items.add(MainItem("Image", "Android 原生 ImageView"))
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
                    2 -> startActivity(Intent(this@MainActivity, ButtonActivity::class.java))
                    3 -> startActivity(Intent(this@MainActivity, ImageActivity::class.java))
                }
            }
        })
    }
}

