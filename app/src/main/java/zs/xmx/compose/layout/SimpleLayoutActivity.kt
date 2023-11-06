package zs.xmx.compose.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import zs.xmx.compose.theme.MyTestTheme

class SimpleLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    SimpleLayoutSample()
                }
            }
        }
    }

    @Composable
    fun SimpleLayoutSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //竖向线性布局
            ColumnExample()

            //横向线性布局
            RowExample()

            //帧布局
            BoxExample()

        }

    }

    @Composable
    private fun ColumnExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Column 竖向线性布局", fontWeight = FontWeight.Bold)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(100.dp),
            verticalArrangement = Arrangement.Center,//布局子项的垂直排列。
            horizontalAlignment = Alignment.CenterHorizontally// 布局子级的水平对齐方式。
        ) {
            Text(text = "Hello World!!!")
            Text(text = "Android")
            Text(text = "Jetpack Compose")
        }
    }

    @Composable
    private fun RowExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "Row 横向线性布局", fontWeight = FontWeight.Bold)

        Text(modifier = Modifier.padding(bottom = 5.dp), text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("==> SpaceEvenly: ")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("控件之间的间隔均分")
            }
        })

        /**
         *  SpaceEvenly 控件之间的间隔均分
         */
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(modifier = Modifier.padding(bottom = 5.dp), text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("==> SpaceAround: ")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("主轴上均匀分布,视觉上: #1##2##3#")
            }
        })

        /**
         *  SpaceAround 主轴上均匀分布
         *  视觉上: #1##2##3#
         */
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(modifier = Modifier.padding(bottom = 5.dp), text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("==> SpaceBetween: ")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("第一个和最后一个控件,紧贴屏幕,其余的控件的均分")
            }
        })

        /**
         *  SpaceBetween 第一个和最后一个控件,紧贴屏幕,其余的控件的均分
         */
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(modifier = Modifier.padding(bottom = 5.dp), text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("==> spacedBy: ")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("控件紧贴在一起,可设置紧贴控件之间的间距")
            }
        })

        /**
         *  spacedBy 控件紧贴在一起,可设置紧贴控件之间的间距
         */
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(
                20.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red)
            )
        }

    }

    @Composable
    private fun BoxExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "Box 帧布局", fontWeight = FontWeight.Bold)

        Box {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.Green)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Red)
            )
            Text(text = "Compose", color = Color.White)
        }

    }


}