package zs.xmx.compose.widget.seekbar

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zs.xmx.compose.theme.MyTestTheme

class SliderActivity : AppCompatActivity() {
    private val TAG = SliderActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    SliderSample()
                }
            }
        }
    }

    @Composable
    fun SliderSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //基本使用
            BasicUse()
            //颜色配置
            ColorConfig()
            //带刻度的Slider
            TickSlider()
            //区间Slider
            RangeSlider()
        }

    }


    @Composable
    private fun BasicUse() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "基本使用", fontWeight = FontWeight.Bold)

        var progress by remember { mutableFloatStateOf(0f) }
        Text(text = "progress: $progress")
        Slider(modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            value = progress,
            onValueChange = {
                progress = it
            })

    }

    @Composable
    private fun ColorConfig() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "颜色配置", fontWeight = FontWeight.Bold)

        var progress by remember { mutableFloatStateOf(0f) }
        Text(text = "progress: $progress")
        Slider(modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            value = progress,
            colors = SliderDefaults.colors(
                thumbColor = Color.Blue,//滑动圆圈颜色
                activeTrackColor = Color.Green,//滑动轨道颜色
                inactiveTrackColor = Color.Yellow//轨道默认颜色
            ),
            onValueChange = {
                progress = it
            })


    }

    @Composable
    private fun TickSlider() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "带刻度的Slider", fontWeight = FontWeight.Bold)

        var progress by remember { mutableFloatStateOf(0f) }
        Text(text = "progress: $progress")
        Slider(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            value = progress,
            colors = SliderDefaults.colors(
                thumbColor = Color.Gray,//滑动圆圈颜色
                activeTickColor = Color.Red,//滑动条经过时,刻度线颜色
                inactiveTickColor = Color.White,//滑动条未经过时,刻度线颜色
                activeTrackColor = Color.Yellow,//滑动轨道颜色
                inactiveTrackColor = Color.Black//轨道默认颜色
            ),
            onValueChange = {
                progress = it
            },
            steps = 8,
            valueRange = 0f..50f
        )

    }

    @Composable
    private fun RangeSlider() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "RangeSlider", fontWeight = FontWeight.Bold)

        var range by remember { mutableStateOf(0f..100f) }
        Text(text = "range : $range ")
        RangeSlider(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            value = range,
            steps = 5,
            valueRange = 0f..100f,
            onValueChange = {
                range = it
            }, onValueChangeFinished = {
                //用户结束操作后回调
                Log.i(TAG, "结束操作了: $range")
            })
    }

}