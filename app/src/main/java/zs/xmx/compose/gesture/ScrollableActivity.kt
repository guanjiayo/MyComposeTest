package zs.xmx.compose.gesture

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zs.xmx.compose.theme.MyTestTheme

class ScrollableActivity : AppCompatActivity() {

    private val TAG = ScrollableActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    GestureSample()
                }
            }
        }
    }

    @Composable
    private fun GestureSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //基本使用
            BasicUse()
            //PointerInput 手势检测器
            PointerInputExample()

        }

    }

    @Composable
    private fun BasicUse() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "基本使用", fontWeight = FontWeight.Bold)
        val count = remember { mutableIntStateOf(0) }
        Button(onClick = { count.intValue += 1 }) {
            Text(text = "${count.intValue}")
        }

    }

    @Composable
    private fun PointerInputExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())

        Text(text = "PointerInput 手势检测器", fontWeight = FontWeight.Bold)

        Surface(
            shape = RoundedCornerShape(25.dp),
            color = Color.Blue, // 设置Surface的背景颜色为蓝色
            contentColor = Color.White, // 设置按钮文字颜色为白色
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = Modifier
                .padding(10.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { Log.d(TAG, "onPress") },
                        onDoubleTap = { Log.d(TAG, "onDoubleTap") },
                        onLongPress = { Log.d(TAG, "onLongPress") },
                        onTap = { Log.d(TAG, "onTap") }
                    )
                }
        ) {
            Text(text = "点击按钮,查看日志", modifier = Modifier.padding(10.dp))
        }

    }


}