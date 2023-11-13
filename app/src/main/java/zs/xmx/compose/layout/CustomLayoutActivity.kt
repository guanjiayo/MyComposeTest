package zs.xmx.compose.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zs.xmx.compose.theme.MyTestTheme
import kotlin.math.max

class CustomLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    CustomLayoutSample()
                }
            }
        }
    }

    @Composable
    fun CustomLayoutSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //自定义布局修饰符
            CustomLayoutModifier()
            //自定义Layout
            CustomLayoutExample()
            //网上随便抄的
            StaggeredGridExample()
        }

    }

    /**
     * 扩展Modifier方式,自定义布局
     * ps: 因为控件的一些布局相关参数,其实就是Modifier控制的
     */
    @Composable
    private fun CustomLayoutModifier() {
        Text(text = "自定义 Modifier 布局修饰符", fontWeight = FontWeight.Bold)
        Divider()
        Text(text = "文本顶部到分割线间隔32dp", modifier = Modifier.padding(top = 32.dp))
        Divider()
        Text(text = "文本底部基线到分割线间隔32dp", modifier = Modifier.firstBaselineToTop(32.dp))
    }

    @Composable
    private fun CustomLayoutExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "自定义Layout布局", fontWeight = FontWeight.Bold)

        Box(modifier = Modifier.height(100.dp)) {
            MyBasicColumn(modifier = Modifier.padding(8.dp)) {
                Text("MyBasicColumn")
                Text("places items")
                Text("vertically.")
                Text("We've done it by hand!")
            }
        }

    }

    @Composable
    private fun StaggeredGridExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "StaggeredGrid", fontWeight = FontWeight.Bold)

        val topics = listOf(
            "Arts & Crafts",
            "Beauty",
            "Books",
            "Business",
            "Comics",
            "Culinary",
            "Design",
            "Fashion",
            "Film",
            "History",
            "Maths",
            "Music",
            "People",
            "Philosophy",
            "Religion",
            "Social sciences",
            "Technology",
            "TV",
            "Writing"
        )
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            StaggeredGrid {
                for (topic in topics) {
                    AssistChip(onClick = { /*TODO*/ }, label = { Text(text = topic) })
                }
            }
        }
    }

    @Composable
    private fun MyBasicColumn(
        modifier: Modifier = Modifier, content: @Composable () -> Unit
    ) {
        Layout(
            modifier = modifier, content = content
        ) { measurables, constraints ->
            // Don't constrain child views further, measure them with given constraints
            // List of measured children
            //对 child 进行测量和放置
            val placeables = measurables.map { measurable ->
                // Measure each children
                // 测量每个 child 的尺寸
                measurable.measure(constraints)
            }

            // Set the size of the layout as big as it can
            layout(constraints.maxWidth, constraints.maxHeight) {
                // Track the y co-ord we have placed children up to
                var yPosition = 0

                // Place children in the parent layout
                placeables.forEach { placeable ->
                    // Position item on the screen
                    placeable.placeRelative(x = 0, y = yPosition)

                    // Record the y co-ord placed up to
                    yPosition += placeable.height
                }
            }
        }
    }

    /**
     * layout lambda , 这是创建自定义LayoutModifier修饰符的便捷 API，(无需创建实现LayoutModifier接口的类或对象。)
     * 允许更改包装元素的测量和布局方式。内在测量遵循LayoutModifier提供的默认逻辑。
     * ps: 仅作用于调用了该扩展方法的Compose控件
     */
    @Composable
    fun Modifier.firstBaselineToTop(
        firstBaselineToTop: Dp
    ) = layout { measurable, constraints ->
        // 测量可测量的Compose控件,如Text
        val placeable = measurable.measure(constraints)

        // Check the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Height of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            // Where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }
    }


    // 横向瀑布流自定义 layout
    @Composable
    fun StaggeredGrid(
        modifier: Modifier = Modifier, rows: Int = 3,  // 自定义的参数，控制展示的行数，默认为 3行
        content: @Composable () -> Unit
    ) {
        Layout(
            modifier = modifier, content = content
        ) { measurables, constraints ->
            // 用于记录每一横行的宽度信息
            val rowWidths = IntArray(rows) { 0 }
            // 用于记录每一横行的高度信息
            val rowHeights = IntArray(rows) { 0 }
            // 测量每个 child 尺寸，获得每个 child 的 Placeable 对象
            val placeables = measurables.mapIndexed { index, measurable ->
                // 标准流程：测量每个 child 尺寸，获得 placeable
                val placeable = measurable.measure(constraints)
                // 根据序号给每个 child 分组，记录每一个横行的宽高信息
                val row = index % rows
                rowWidths[row] += placeable.width
                rowHeights[row] = max(rowHeights[row], placeable.height)
                placeable    // 这句别忘了，返回每个 child 的placeable
            }

            // 自定义 Layout 的宽度取所有行中宽度最大值
            val width =
                rowWidths.maxOrNull()?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
                    ?: constraints.minWidth
            // 自定义 Layout 的高度当然为所有行高度之和
            val height = rowHeights.sumOf { it }
                .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))
            // 计算出每一行的元素在 Y轴 上的摆放位置
            val rowY = IntArray(rows) { 0 }
            for (i in 1 until rows) {
                rowY[i] = rowY[i - 1] + rowHeights[i - 1]
            }

            // 设置 自定义 Layout 的宽高
            layout(width, height) {
                // 摆放每个 child
                val rowX = IntArray(rows) { 0 }  // child 在 X 轴的位置
                placeables.forEachIndexed { index, placeable ->
                    val row = index % rows
                    placeable.placeRelative(
                        rowX[row], rowY[row]
                    )
                    rowX[row] += placeable.width
                }
            }
        }
    }

}