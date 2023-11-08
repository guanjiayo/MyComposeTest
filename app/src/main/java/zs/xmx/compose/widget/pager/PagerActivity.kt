package zs.xmx.compose.widget.pager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.animation.AnimationUtils.lerp
import kotlinx.coroutines.launch
import zs.xmx.compose.theme.MyTestTheme
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
class PagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    PagerSample()
                }
            }
        }
    }

    @Composable
    fun PagerSample() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            //水平方向的ViewPager
            HorizontalPagerExample()

            //水平方向的ViewPager结合TabRow使用
            HorizontalPagerWithTabRowExample()

            //水平方向的ViewPager 页面切换动画
            HorizontalPagerAnimationExample()

        }

    }

    @Composable
    private fun HorizontalPagerExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "HorizontalPager 基本使用", fontWeight = FontWeight.Bold)

        val pagerState = rememberPagerState {
            10
        }

        HorizontalPager(state = pagerState) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Page: $page",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

        }

    }

    @Composable
    private fun HorizontalPagerWithTabRowExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Text(text = "HorizontalPager 结合 TabRow 使用", fontWeight = FontWeight.Bold)

        val tabTitles = listOf("语文", "数学", "英语", "物理", "化学", "生物")

        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(initialPage = 0) { tabTitles.size }

        TabRow(
            selectedTabIndex = pagerState.currentPage, modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Black,//背景颜色
            contentColor = Color.White,//文本颜色
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 2.dp,
                    color = Color.Blue
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title) },
                    selected = pagerState.currentPage == index,
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Red,
                    modifier = Modifier
                        .background(if (pagerState.currentPage == index) Color.Red else Color.Black)
                        .fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    })
            }
        }

        HorizontalPager(state = pagerState) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Page: $page",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

        }

    }

    @SuppressLint("RestrictedApi")
    @Composable
    private fun HorizontalPagerAnimationExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Text(text = "HorizontalPager 页面切换动画", fontWeight = FontWeight.Bold)

        val pagerState = rememberPagerState(pageCount = {
            8
        })
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(start = 80.dp, end = 80.dp)
        ) { page ->
            Box(
                modifier = Modifier
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    Modifier
                        .size(200.dp)
                        .graphicsLayer {
                            // Calculate the absolute offset for the current page from the
                            // scroll position. We use the absolute value which allows us to mirror
                            // any effects for both directions
                            val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue

                            // We animate the alpha, between 50% and 100%
                            alpha = lerp(0.3f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                        }
                ) {
                    // Card content
                }
            }

        }

    }


}