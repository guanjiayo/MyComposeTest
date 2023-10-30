package zs.xmx.compose.ui.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Compose 实现类似 Selector 选择器
 */
data class ButtonState(
    var interactionState: MutableInteractionSource,
    var text: String,
    var textColor: Color,
    var buttonColor: Color,
    var btnShape: Shape
)

@Composable
fun CommonButtonSelector(): ButtonState {
    val interactionState = remember { MutableInteractionSource() }
    return when {
        //按压状态
        interactionState.collectIsPressedAsState().value -> ButtonState(
            interactionState,
            "Just Pressed",
            Color.Red,
            Color.Black,
            RectangleShape
        )

        else -> ButtonState(
            interactionState,
            "Just Button",
            Color.White,
            Color.Red,
            RoundedCornerShape(50.dp)
        )
    }
}
