package zs.xmx.compose.ext

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

const val VIEW_CLICK_INTERVAL_TIME = 800//View的click方法的两次点击间隔时间

@Composable
inline fun Modifier.singClick(
    time: Int = VIEW_CLICK_INTERVAL_TIME,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    crossinline onClick: () -> Unit
): Modifier {
    var lastClickTime by remember { mutableStateOf(value = 0L) }//使用remember函数记录上次点击的时间
    return clickable(enabled, onClickLabel, role) {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - time >= lastClickTime) {//判断点击间隔,如果在间隔内则不回调
            onClick()
            lastClickTime = currentTimeMillis
        }
    }
}

