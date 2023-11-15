package zs.xmx.compose.animation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zs.xmx.compose.theme.MyTestTheme

class LowLevelAnimationActivity : AppCompatActivity() {

    private val TAG = LowLevelAnimationActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AnimationSample()
                }
            }
        }
    }

    @Composable
    private fun AnimationSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //属性动画
            AnimateAsStateExample()
            //帧动画
            AnimatableExample()
            //组合动画
            UpdateTransitionExample()
            //组合动画重复执行
            InfiniteTransitionExample()
        }

    }

    /**
     * Compose 为 Float、Color、Dp、Size、Offset、Rect、Int、IntOffset 和 IntSize 提供开箱即用的 animate*AsState 函数
     * 但任何数据类型都可以通过 animateValueAsState 提供 TwoWayConverter 来使用
     */
    @Composable
    private fun AnimateAsStateExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "animate*AsState 属性动画", fontWeight = FontWeight.Bold)

        var isSmall by remember { mutableStateOf(true) }
        val size: Dp by animateDpAsState(
            targetValue = if (isSmall) 40.dp else 100.dp, label = "animateDpAsState"
        ) {
            Log.e(TAG, "AnimateAsStateExample:  $it")
        }
        Box(
            modifier = Modifier
                .size(size)
                .background(Color.Blue)
        )
        Button(onClick = { isSmall = !isSmall }) {
            Text(text = "Change Size Dp")
        }
    }

    /**
     * Animatable 与 animate*AsState 相比,有更精细的控制
     * 比如:
     *      1. 可以设置与实际操作不同的默认颜色值
     *      2. 对内容值有更多操作:
     *                  snapTo 将当前值立即设置为目标值
     *                  animateDecay 启动一个从给定速度开始放缓的动画
     * Animatable 仅支持 Float 和 Color，但任何数据类型都可以通过 animateValueAsState 提供 TwoWayConverter 来使用
     */
    @Composable
    private fun AnimatableExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "Animatable 帧动画", fontWeight = FontWeight.Bold)

        var flag by remember { mutableStateOf(false) }
        val color = remember {
            Animatable(initialValue = Color.Red)
        }
        LaunchedEffect(key1 = flag, block = {
            color.animateTo(
                targetValue = if (flag) Color.Yellow else Color.Green, animationSpec = tween(1000)
            )
        })
        Box(
            modifier = Modifier
                .size(180.dp)
                .background(color.value)
        )
        Button(onClick = { flag = !flag }) {
            Text("切换")
        }
    }


    @SuppressLint("UnusedTransitionTargetStateParameter")
    @Composable
    private fun UpdateTransitionExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "updateTransition 组合动画", fontWeight = FontWeight.Bold)

        var boxState: BoxState by remember { mutableStateOf(BoxState.Small) }
        val transition = updateTransition(targetState = boxState, label = "updateTransition")
        val color by transition.animateColor(transitionSpec = {
            //添加过渡动画
            when {
                BoxState.Small isTransitioningTo BoxState.Large -> spring(stiffness = 50f)

                else -> tween(durationMillis = 500)
            }
        }, label = "color") {
            boxState.color
        }
        val size by transition.animateDp(label = "size") {
            boxState.size
        }
        val offset by transition.animateDp(label = "offset") {
            boxState.offset
        }
        val angle by transition.animateFloat(label = "angle") {
            boxState.angle
        }

        Row(modifier = Modifier.height(150.dp)) {
            Button(onClick = { boxState = !boxState }) {
                Text("Transition Test")
            }

            Box(
                modifier = Modifier
                    .rotate(angle)
                    .size(size)
                    .offset(x = offset)
                    .background(color)
            )
        }

    }

    @Composable
    private fun InfiniteTransitionExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "组合动画重复执行", fontWeight = FontWeight.Bold)

        val infiniteTransition = rememberInfiniteTransition(label = "infinite")
        val color by infiniteTransition.animateColor(
            initialValue = Color.Red, targetValue = Color.Green, animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing), repeatMode = RepeatMode.Reverse
            ), label = "infinite"
        )

        Box(
            Modifier
                .size(100.dp)
                .background(color)
        )
    }

    /**
     * 密封类确保类型安全
     */
    private sealed class BoxState(
        val color: Color, val size: Dp, val offset: Dp, val angle: Float
    ) {
        //对"!"操作符重载
        operator fun not() = if (this is Small) Large else Small

        object Small : BoxState(Color.Blue, 60.dp, 20.dp, 0f)
        object Large : BoxState(Color.Red, 90.dp, 50.dp, 45f)
    }

    private enum class BoxState2 {
        Collapsed, Expanded
    }
}