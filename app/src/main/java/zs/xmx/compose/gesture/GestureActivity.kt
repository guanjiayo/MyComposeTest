package zs.xmx.compose.gesture

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zs.xmx.compose.theme.MyTestTheme
import kotlin.math.roundToInt

class GestureActivity : AppCompatActivity() {

    private val TAG = GestureActivity::class.java.simpleName
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
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        ) {
            //基本使用
            BasicUse()
            //PointerInput 手势检测器
            PointerInputExample()
            //手指竖直方向上手势滑动偏移量的数值
            ScrollableStateExample()
            //水平方向拖动
            HorizontalDraggableExample()
            //多方向拖动
            MultiOrientationDraggableExample()
            //多点触控
            TransformableExample()
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

        Surface(shape = RoundedCornerShape(25.dp),
            color = Color.Blue, // 设置Surface的背景颜色为蓝色
            contentColor = Color.White, // 设置按钮文字颜色为白色
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = Modifier
                .padding(10.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onPress = { Log.d(TAG, "onPress") },
                        onDoubleTap = { Log.d(TAG, "onDoubleTap") },
                        onLongPress = { Log.d(TAG, "onLongPress") },
                        onTap = { Log.d(TAG, "onTap") })
                }) {
            Text(text = "点击按钮,查看日志", modifier = Modifier.padding(10.dp))
        }

    }

    @Composable
    private fun ScrollableStateExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "手指竖直方向上手势滑动偏移量的数值", fontWeight = FontWeight.Bold)

        // actual composable state
        var offset by remember { mutableFloatStateOf(0f) }
        Box(
            Modifier
                .size(120.dp)
                .scrollable(orientation = Orientation.Vertical,
                    // Scrollable state: describes how to consume
                    // scrolling delta and update offset
                    state = rememberScrollableState { delta ->
                        offset += delta
                        delta
                    })
                .background(Color.LightGray), contentAlignment = Alignment.Center
        ) {
            Text(offset.toString())
        }

    }

    @Composable
    private fun HorizontalDraggableExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "水平方向拖动", fontWeight = FontWeight.Bold)

        var offsetX by remember { mutableFloatStateOf(0f) }
        Text(modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                }), text = "Drag me!", fontSize = 30.sp, fontWeight = FontWeight.Bold
        )
    }

    @Composable
    private fun MultiOrientationDraggableExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Text(text = "多方位拖动", fontWeight = FontWeight.Bold)

        var offsetX by remember { mutableFloatStateOf(0f) }
        var offsetY by remember { mutableFloatStateOf(0f) }

        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                })

    }

    @Composable
    private fun TransformableExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "多点触控: 平移、缩放、旋转", fontWeight = FontWeight.Bold)

        // set up all transformation states
        var scale by remember { mutableFloatStateOf(1f) }
        var rotation by remember { mutableFloatStateOf(0f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
            scale *= zoomChange
            rotation += rotationChange
            offset += offsetChange
        }
        Box(
            Modifier
                // apply other transformations like rotation and zoom
                // on the pizza slice emoji
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
                // add transformable to listen to multitouch transformation events
                // after offset
                .transformable(state = state)
                .background(Color.Blue)
                .size(100.dp)
        )

        /*Box(Modifier
            .size(150.dp)
            .rotate(rotation) // 需要注意 offset 与 rotate 的调用先后顺序
            .offset {
                IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
            }
            .scale(scale)
            .background(Color.Blue)
            .transformable(
                state = state,
                lockRotationOnZoomPan = false
            )
        )*/
    }

}