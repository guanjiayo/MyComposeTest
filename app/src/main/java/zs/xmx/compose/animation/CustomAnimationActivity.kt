package zs.xmx.compose.animation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zs.xmx.compose.theme.MyTestTheme

class CustomAnimationActivity : AppCompatActivity() {

    private val TAG = CustomAnimationActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AnimationSpecSample()
                }
            }
        }
    }

    /**
     * 通过查看源码,大多数动画相关的Api都是通过可选的AnimationSpec参数来自定义动画规范的
     */
    @Composable
    private fun AnimationSpecSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //Spring 在起始值和结束值之间创建基于物理特性的动画
            SpringAnimExample()
            //tween 渐变动画
            TweenAnimExample()
            //Keyframes 帧动画
            KeyframesExample()
            //repeatable 重复有限次数动画
            RepeatableAnimExample()
            //InfiniteRepeatable 重复无限次数动画
            InfiniteRepeatableAnimExample()

        }

    }

    /**
     * Spring.XX 效果自行看文档
     * https://developer.android.google.cn/jetpack/compose/animation?hl=zh-cn#customize-animations
     */
    @Composable
    private fun SpringAnimExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Spring 基于物理特性的动画", fontWeight = FontWeight.Bold
        )
        var animEnabled by remember { mutableStateOf(false) }

        val scale by animateFloatAsState(
            targetValue = if (animEnabled) 2f else 1f, animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessMedium
            ), label = "SpringAnim"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.height(100.dp)) {
            Button(onClick = { animEnabled = !animEnabled }) {
                Text(text = "Change Scale")
            }
            Box(
                modifier = Modifier
                    .padding(start = 50.dp)
                    .size(60.dp)
                    .scale(scale)
                    .background(Color.Blue)
            )
        }

    }

    /**
     * easing 详情效果,看文档
     * https://developer.android.google.cn/reference/kotlin/androidx/compose/animation/core/package-summary#Ease()
     */
    @Composable
    private fun TweenAnimExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "Tween 渐变动画", fontWeight = FontWeight.Bold)

        var animEnabled by remember { mutableStateOf(false) }

        val alpha by animateFloatAsState(
            targetValue = if (animEnabled) 0.1f else 1f, animationSpec = tween(
                durationMillis = 1000, delayMillis = 50, easing = LinearOutSlowInEasing
            ), label = "TweenAnim"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.height(80.dp)) {
            Button(onClick = { animEnabled = !animEnabled }) {
                Text(text = "Change Alpha")
            }
            Box(
                modifier = Modifier
                    .padding(start = 50.dp)
                    .size(60.dp)
                    .alpha(alpha)
                    .background(Color.Blue)
            )
        }
    }


    @Composable
    private fun KeyframesExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "Keyframes 帧动画", fontWeight = FontWeight.Bold)

        var animEnabled by remember { mutableStateOf(false) }

        val angle by animateFloatAsState(
            targetValue = if (animEnabled) 360f else -360f, animationSpec = keyframes {
                durationMillis = 1500
                0.0f at 375 with LinearOutSlowInEasing // for 375-750 ms
                0.2f at 750 with FastOutLinearInEasing // for 750-1125 ms
                0.4f at 1125 // ms
                0.4f at 1500 // ms
            }, label = "Keyframes"
        )

        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.height(80.dp)) {
            Button(onClick = { animEnabled = !animEnabled }) {
                Text(text = "Change Angle")
            }
            Box(
                modifier = Modifier
                    .padding(start = 50.dp)
                    .size(60.dp)
                    .rotate(angle)
                    .background(Color.Blue)
            )
        }

    }

    @Composable
    private fun RepeatableAnimExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "repeatable 重复有限次数动画", fontWeight = FontWeight.Bold)

        var animEnabled by remember { mutableStateOf(false) }

        val alpha by animateFloatAsState(
            targetValue = if (animEnabled) 0.1f else 1f, animationSpec = repeatable(
                iterations = 5,
                animation = tween(durationMillis = 800),
                repeatMode = RepeatMode.Restart//RepeatMode.Restart 从头重复播放,RepeatMode.Reverse从结尾重复播放
            ), label = "RepeatTweenAnim"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.height(80.dp)) {
            Button(onClick = { animEnabled = !animEnabled }) {
                Text(text = "Repeat Change Alpha")
            }
            Box(
                modifier = Modifier
                    .padding(start = 50.dp)
                    .size(60.dp)
                    .alpha(alpha)
                    .background(Color.Red)
            )
        }
    }

    @Composable
    private fun InfiniteRepeatableAnimExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "InfiniteRepeatable 重复无限次数动画", fontWeight = FontWeight.Bold)

        var animEnabled by remember { mutableStateOf(false) }

        val alpha by animateFloatAsState(
            targetValue = if (animEnabled) 0f else 1f, animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 800),
                repeatMode = RepeatMode.Restart//RepeatMode.Restart 从头重复播放,RepeatMode.Reverse从结尾重复播放
            ), label = "InfiniteRepeatTweenAnim"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.height(80.dp)) {
            Button(onClick = { animEnabled = !animEnabled }) {
                Text(text = "InfiniteRepeat Change Alpha")
            }
            Box(
                modifier = Modifier
                    .padding(start = 50.dp)
                    .size(60.dp)
                    .alpha(alpha)
                    .background(Color.Green)
            )
        }
    }

}