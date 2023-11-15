package zs.xmx.compose.animation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zs.xmx.compose.theme.MyTestTheme

class HighLevelAnimationActivity : AppCompatActivity() {
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
            //可见性动画
            AnimVisibilityExample()
            //可见性动画状态监听
            AnimVisibilityStateListenerExample()
            //布局大小动画
            ContentSizeAnimExample()
            //布局切换动画
            CrossFadeAnimExample()

        }

    }

    /**
     * 进入/退场动画是成对的,具体效果看:
     * https://developer.android.google.cn/jetpack/compose/animation?hl=zh-cn#animatedvisibility
     */
    @Composable
    private fun AnimVisibilityExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "AnimatedVisibility 可见性动画", fontWeight = FontWeight.Bold)

        var visible by remember { mutableStateOf(true) }

        AnimatedVisibility(visible = visible,
            enter = slideIn { IntOffset(400, 400) } + expandIn(),
            exit = slideOut { IntOffset(400, 400) } + shrinkOut()) {
            Text(
                "天青色等烟雨 而我在等你,炊烟袅袅升起 隔江千万里, 在瓶底书汉隶仿前朝的飘逸," + "就当我为遇见你伏笔,天青色等烟雨 而我在等你,月色被打捞起," + "晕开了结局,如传世的青花瓷自顾自美丽,你眼带笑意",
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { visible = !visible }) {
            Text(text = "可见性动画")
        }
    }

    @Composable
    private fun AnimVisibilityStateListenerExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "AnimatedVisibility 可见性动画状态监听", fontWeight = FontWeight.Bold)

        val state = remember {
            MutableTransitionState(false).apply {
                // Start the animation immediately.
                targetState = true
            }
        }

        AnimatedVisibility(visibleState = state,//targetState
            enter = slideIn { IntOffset(400, 400) } + expandIn(),
            exit = slideOut { IntOffset(400, 400) } + shrinkOut()) {
            Text(
                "天青色等烟雨 而我在等你,炊烟袅袅升起 隔江千万里, 在瓶底书汉隶仿前朝的飘逸," + "就当我为遇见你伏笔,天青色等烟雨 而我在等你,月色被打捞起," + "晕开了结局,如传世的青花瓷自顾自美丽,你眼带笑意",
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = when {
                state.isIdle && state.currentState -> "State:   Visible"
                !state.isIdle && state.currentState -> "State:   Disappearing"
                state.isIdle && !state.currentState -> "State:   Invisible"
                else -> "State:   Appearing"
            }
        )
        Button(onClick = { state.targetState = !state.targetState }) {
            Text(text = "可见性动画状态监听")
        }
    }

    /**
     * animateContentSize 在修饰符链中的位置顺序很重要。为了确保流畅的动画，请务必将其放置在任何大小修饰符
     * （如 size 或 defaultMinSize）前面，以确保 animateContentSize 会将带动画效果的值的变化报告给布局。
     */
    @Composable
    private fun ContentSizeAnimExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "Modifier.animateContentSize() 布局大小动画", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))

        val expand = remember { mutableStateOf(false) }
        Text(
            text = "关关雎鸠，在河之洲。窈窕淑女，君子好逑。\n" +
                    "参差荇菜，左右流之。窈窕淑女，寤寐求之。\n" +
                    "求之不得，寤寐思服。悠哉悠哉，辗转反侧。\n" +
                    "参差荇菜，左右采之。窈窕淑女，琴瑟友之。\n" +
                    "参差荇菜，左右芼之。窈窕淑女，钟鼓乐之。",
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.animateContentSize(),
            maxLines = if (expand.value) Int.MAX_VALUE else 2
        )
        Text(
            text = if (expand.value) "收起" else "全文",
            color = Color.Blue,
            modifier = Modifier.clickable {
                expand.value = !expand.value
            })
    }

    @Composable
    private fun CrossFadeAnimExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "Crossfade 布局切换动画", fontWeight = FontWeight.Bold)

        var flag by remember { mutableStateOf(false) }
        Crossfade(
            targetState = flag, animationSpec = tween(1000), label = "CrossFadeAnim"
        ) {
            when (it) {
                false -> Screen1()
                true -> Screen2()
            }
        }
        Button(
            onClick = { flag = !flag }
        ) {
            Text("切换")
        }
    }

    @Composable
    fun Screen1() {
        Box(
            modifier = Modifier
                .background(Color.Red)
                .size(200.dp),
            contentAlignment = Alignment.Center
        ) { }
    }

    @Composable
    fun Screen2() {
        Box(
            modifier = Modifier
                .background(Color.Blue)
                .size(200.dp),
            contentAlignment = Alignment.Center
        ) { }
    }
}