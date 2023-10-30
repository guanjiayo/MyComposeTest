package zs.xmx.compose.ext

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import kotlinx.coroutines.delay

@Composable
inline fun Modifier.singClick(
    time: Int = 500,//View的click方法的两次点击间隔时间
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    crossinline onClick: () -> Unit
): Modifier {
    var lastClickTime by remember { mutableLongStateOf(value = 0L) }//使用remember函数记录上次点击的时间
    return clickable(enabled, onClickLabel, role) {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - time >= lastClickTime) {//判断点击间隔,如果在间隔内则不回调
            onClick()
            lastClickTime = currentTimeMillis
        }
    }
}

/**
 * 防止重复点击,比如用在Button时直接传入onClick函数
 */
@Composable
inline fun composeClick(
    time: Int = 500,//View的click方法的两次点击间隔时间
    crossinline onClick: () -> Unit
): () -> Unit {
    var lastClickTime by remember { mutableLongStateOf(value = 0L) }//使用remember函数记录上次点击的时间
    return {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - time >= lastClickTime) {//判断点击间隔,如果在间隔内则不回调
            onClick()
            lastClickTime = currentTimeMillis
        }
    }
}

//Modifier 的扩展方法处理如下
inline fun Modifier.debouncedClickable(
    enabled: Boolean,
    crossinline onClick: () -> Unit,
    delay: Long = 300
) = composed {
    var clicked by remember {
        mutableStateOf(!enabled)
    }
    LaunchedEffect(key1 = clicked, block = {
        if (clicked) {
            delay(delay)
            clicked = !clicked
        }
    })

    Modifier.clickable(if (enabled) !clicked else false) {
        clicked = !clicked
        onClick()
    }
}