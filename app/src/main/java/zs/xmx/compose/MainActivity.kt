package zs.xmx.compose

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import zs.xmx.compose.adapter.MainAdapter
import zs.xmx.compose.animation.CustomAnimationActivity
import zs.xmx.compose.animation.HighLevelAnimationActivity
import zs.xmx.compose.animation.LowLevelAnimationActivity
import zs.xmx.compose.gesture.ClickableActivity
import zs.xmx.compose.gesture.ScrollableActivity
import zs.xmx.compose.layout.ConstraintLayoutActivity
import zs.xmx.compose.layout.CustomLayoutActivity
import zs.xmx.compose.layout.FlowLayoutActivity
import zs.xmx.compose.layout.SimpleLayoutActivity
import zs.xmx.compose.layout.scaffold.ScaffoldActivity
import zs.xmx.compose.model.MainItem
import zs.xmx.compose.widget.CustomViewActivity
import zs.xmx.compose.widget.button.ButtonActivity
import zs.xmx.compose.widget.checkbox.CheckBoxActivity
import zs.xmx.compose.widget.chip.ChipActivity
import zs.xmx.compose.widget.edittext.TextFieldActivity
import zs.xmx.compose.widget.image.ImageActivity
import zs.xmx.compose.widget.list.LazyColumnActivity
import zs.xmx.compose.widget.list.LazyVerticalStaggerActivity
import zs.xmx.compose.widget.pager.PagerActivity
import zs.xmx.compose.widget.progress.ProgressActivity
import zs.xmx.compose.widget.radiobutton.RadioButtonActivity
import zs.xmx.compose.widget.seekbar.SliderActivity
import zs.xmx.compose.widget.switchview.SwitchActivity
import zs.xmx.compose.widget.text.TextViewActivity

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
        items.add(MainItem("xxProgressIndicator", "Android 原生 ProgressBar"))
        items.add(MainItem("Slider", "Android 原生 SeekBar"))
        items.add(MainItem("Switch", "Android 原生 Switch"))
        items.add(MainItem("RadioButton", "Android 原生 RadioButton"))
        items.add(MainItem("CheckBox", "Android 原生 CheckBox"))
        items.add(MainItem("Chip", "Android 原生 Chip"))
        items.add(MainItem("简单布局", "Android 原生 线性布局、帧布局等"))
        items.add(MainItem("FlowLayout", "Android 原生 FlowLayout流式布局"))
        items.add(
            MainItem(
                "Scaffold 脚手架", "Android 原生 AppCompatActivity,用于快速构建MD可视化布局结构"
            )
        )
        items.add(MainItem("ConstraintLayout", "Android 原生 ConstraintLayout"))
        items.add(MainItem("Pager", "Android 原生 ViewPager2"))
        items.add(MainItem("LazyColumn", "Android 原生 RecyclerView"))
        items.add(MainItem("LazyVerticalStaggeredGrid", "Android 原生 RecyclerView -- 瀑布流"))
        items.add(MainItem("自定义View", "Compose 自定义View"))
        items.add(MainItem("自定义布局", "Compose 自定义布局"))
        items.add(MainItem("高级别动画", "Compose 可见性动画、布局大小动画、布局切换动画"))
        items.add(MainItem("低级别动画", "Compose 属性动画、帧动画、多动画组合"))
        items.add(MainItem("自定义动画", "AnimationSpec 、AnimationVector 使用"))
        items.add(MainItem("点击手势", "Compose 手势监听---点击事件"))
        items.add(MainItem("滑动手势", "Compose 手势监听---滑动事件"))
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
                    4 -> startActivity(Intent(this@MainActivity, ProgressActivity::class.java))
                    5 -> startActivity(Intent(this@MainActivity, SliderActivity::class.java))
                    6 -> startActivity(Intent(this@MainActivity, SwitchActivity::class.java))
                    7 -> startActivity(Intent(this@MainActivity, RadioButtonActivity::class.java))
                    8 -> startActivity(Intent(this@MainActivity, CheckBoxActivity::class.java))
                    9 -> startActivity(Intent(this@MainActivity, ChipActivity::class.java))
                    10 -> startActivity(Intent(this@MainActivity, SimpleLayoutActivity::class.java))
                    11 -> startActivity(Intent(this@MainActivity, FlowLayoutActivity::class.java))
                    12 -> startActivity(Intent(this@MainActivity, ScaffoldActivity::class.java))
                    13 -> startActivity(
                        Intent(
                            this@MainActivity, ConstraintLayoutActivity::class.java
                        )
                    )

                    14 -> startActivity(Intent(this@MainActivity, PagerActivity::class.java))
                    15 -> startActivity(Intent(this@MainActivity, LazyColumnActivity::class.java))
                    16 -> startActivity(
                        Intent(
                            this@MainActivity, LazyVerticalStaggerActivity::class.java
                        )
                    )

                    17 -> startActivity(Intent(this@MainActivity, CustomViewActivity::class.java))
                    18 -> startActivity(Intent(this@MainActivity, CustomLayoutActivity::class.java))
                    19 -> startActivity(
                        Intent(
                            this@MainActivity, HighLevelAnimationActivity::class.java
                        )
                    )

                    20 -> startActivity(
                        Intent(
                            this@MainActivity, LowLevelAnimationActivity::class.java
                        )
                    )

                    21 -> startActivity(
                        Intent(
                            this@MainActivity, CustomAnimationActivity::class.java
                        )
                    )

                    22 -> startActivity(
                        Intent(
                            this@MainActivity, ClickableActivity::class.java
                        )
                    )

                    23 -> startActivity(
                        Intent(
                            this@MainActivity, ScrollableActivity::class.java
                        )
                    )

                }
            }
        })
    }
}

