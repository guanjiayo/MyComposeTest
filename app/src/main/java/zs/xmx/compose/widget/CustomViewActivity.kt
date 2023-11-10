package zs.xmx.compose.widget

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zs.xmx.compose.R
import zs.xmx.compose.theme.MyTestTheme

class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    CustomViewSample()
                }
            }
        }
    }

    @Composable
    fun CustomViewSample() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //绘制点
            DrawPoint()
            //绘制线段
            DrawLineSegment()
            //Image 作为 Brush
            ImageBrush()
            //绘制线
            DrawLine()
            //绘制渐变线
            DrawBrushLine()
            //绘制虚线
            DrawDashLine()
            //绘制椭圆
            DrawOval()
            //绘制圆
            DrawCircle()
            //绘制渐变圆环
            DrawColorRing()
            //绘制矩形
            DrawRect()
            //绘制圆角空心矩形
            DrawHollowRoundRect()
            //绘制圆弧
            DrawArc()
        }

    }

    @Composable
    private fun DrawPoint() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "drawPoints 绘制点", fontWeight = FontWeight.Bold)

        //坐标
        val points = arrayListOf(
            Offset(250f, 0f), Offset(340f, 0f), Offset(430f, 0f), Offset(520f, 0f), Offset(610f, 0f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp), contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), onDraw = {
                drawPoints(
                    points = points,
                    pointMode = PointMode.Points,
                    color = Color.Blue,
                    strokeWidth = 30f
                )
            })
        }

    }

    @Composable
    private fun DrawLineSegment() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Text(text = "drawPoints 绘制线段", fontWeight = FontWeight.Bold)

        //坐标  5个点,不承兑的话,最后的点不会连起来
        val points = arrayListOf(
            Offset(250f, 0f), Offset(340f, 0f), Offset(430f, 0f), Offset(520f, 0f), Offset(610f, 0f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp), contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), onDraw = {
                drawPoints(
                    points = points,
                    pointMode = PointMode.Lines,
                    color = Color.Blue,
                    strokeWidth = 30f
                )
            })
        }

    }

    @Composable
    private fun DrawLine() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Text(text = "drawPoints 绘制线", fontWeight = FontWeight.Bold)

        //坐标
        val points = arrayListOf(
            Offset(250f, 0f), Offset(340f, 0f), Offset(430f, 0f), Offset(520f, 0f), Offset(610f, 0f)
        )

        val points2 = arrayListOf(
            Offset(250f, 50f),
            Offset(340f, 50f),
            Offset(430f, 50f),
            Offset(520f, 50f),
            Offset(610f, 50f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp), contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), onDraw = {
                drawPoints(
                    points = points,
                    pointMode = PointMode.Polygon,
                    color = Color.Blue,
                    strokeWidth = 30f
                )

                /**
                 * StrokeCap 线段末端处理
                 * Butt 默认的
                 * Round 半圆
                 * Square 默认基础上,延笔触宽度的一半
                 */
                drawPoints(
                    points = points2,
                    pointMode = PointMode.Polygon,
                    color = Color.Blue,
                    strokeWidth = 30f,
                    cap = StrokeCap.Round
                )
            })
        }

    }

    /**
     * ps: 在Canvas中绘制图形,当设置渐变色时主义事项
     *      Canvas 画布必须固定大小,否则无效
     */
    @Composable
    private fun DrawBrushLine() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Text(text = "drawLine 绘制渐变线", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "--> linearGradient: ")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), contentAlignment = Alignment.TopCenter
        ) {

            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), onDraw = {


                /**
                 *  linearGradient
                 *  使用给定的开始坐标和结束坐标,使用提供的颜色创建线性渐变
                 */
                val linearBrush = Brush.linearGradient(
                    colors = listOf(
                        Color.Red, Color.Green, Color.Blue
                    )
                )

                drawLine(
                    brush = linearBrush,
                    start = Offset(0f, 50f),
                    end = Offset(size.width, 50f),
                    strokeWidth = 50f
                )

            })
        }

        Text(text = "--> horizontalGradient: ")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), contentAlignment = Alignment.TopCenter
        ) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), onDraw = {

                /**
                 *  horizontalGradient
                 *  水平渐变,给定的颜色均匀地分散在渐变中
                 */
                val horizontalBrush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Red, Color.Green, Color.Blue
                    ), startX = 0f, endX = size.width
                )

                drawLine(
                    brush = horizontalBrush,
                    start = Offset(0f, 50f),
                    end = Offset(size.width, 50f),
                    strokeWidth = 50f
                )
            })

        }

        Text(text = "--> verticalGradient: ")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp), contentAlignment = Alignment.TopCenter
        ) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp), onDraw = {

                /**
                 *  verticalGradient
                 *  竖直渐变,给定的颜色均匀地分散在渐变中
                 */
                drawLine(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Red, Color.Green, Color.Blue
                        )
                    ),
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height),
                    strokeWidth = size.width / 4
                )
            })
        }

        Text(text = "--> radialGradient: ")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp), contentAlignment = Alignment.TopCenter
        ) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp), onDraw = {

                /**
                 *  radialGradient
                 *  径向渐变,给定的颜色均匀地分散在渐变中
                 */
                drawLine(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Red, Color.Green, Color.Blue
                        )
                    ),
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height),
                    strokeWidth = size.width / 4
                )
            })
        }

        Text(text = "--> sweepGradient: ")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp), contentAlignment = Alignment.TopCenter
        ) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp), onDraw = {

                /**
                 *  sweepGradient
                 *  创建给定颜色围绕中心散步的扫描渐变,并在每个色标对中定义偏移量
                 *  扫描从三点钟方向开始,然后顺时针继续,直到回到起点
                 */
                drawLine(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color.Red, Color.Green, Color.Blue
                        )
                    ),
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height),
                    strokeWidth = size.width / 4
                )
            })
        }

    }

    @Composable
    private fun DrawDashLine() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Text(text = "drawLine 绘制虚线", fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.wrapContentSize(), onDraw = {
                drawDashedLine(color = Color.Blue, strokeWidth = 5f, dashWidth = 10f, dashGap = 5f)
            })
        }
    }

    @Composable
    private fun DrawOval() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Text(text = "绘制椭圆", fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                drawOval(
                    color = Color.Red, topLeft = Offset(size.width / 3, 0f), size = Size(180f, 300f)
                )
            }
        }

    }

    @Composable
    private fun DrawCircle() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Text(text = "绘制圆", fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            /**
             *  sweepGradient
             *  创建给定颜色围绕中心散步的扫描渐变,并在每个色标对中定义偏移量
             *  扫描从三点钟方向开始,然后顺时针继续,直到回到起点
             */
            val brush = Brush.sweepGradient(
                listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow)
            )
            Canvas(modifier = Modifier.size(100.dp), onDraw = {
                drawCircle(
                    brush = brush, radius = 100f, center = center
                )
            })
        }

    }

    @Composable
    private fun DrawColorRing() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Text(text = "绘制渐变圆环", fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            val radius = 80.dp
            val ringWidth = 15.dp
            Canvas(modifier = Modifier.size(radius)) {
                this.drawCircle( // 画圆
                    brush = Brush.sweepGradient(
                        listOf(Color.Red, Color.Green, Color.Red),
                        Offset(radius.toPx() / 2f, radius.toPx() / 2f)
                    ), radius = radius.toPx() / 2f, style = Stroke(
                        width = ringWidth.toPx()
                    )
                )
            }
        }

    }

    @Composable
    private fun DrawRect() {
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Text(text = "绘制矩形", fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                drawRect(
                    color = Color.Red, topLeft = Offset(size.width / 3, 0f), size = Size(250f, 250f)
                )
            }
        }

    }

    @Composable
    private fun DrawHollowRoundRect() {
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Text(text = "绘制圆角空心矩形", fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                drawRoundRect(
                    color = Color.Red,
                    topLeft = Offset(size.width / 3, 0f),
                    size = Size(250f, 250f),
                    cornerRadius = CornerRadius(30f),
                    style = Stroke(
                        width = 30f, join = StrokeJoin.Round
                    )
                )
            }
        }

    }

    @Composable
    private fun DrawArc() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Text(text = "绘制圆弧", fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(120.dp), onDraw = {
                drawArc(
                    color = Color.Red,
                    size= Size(200f,200f),
                    startAngle = 0f,
                    sweepAngle = 90f,
                    useCenter = true
                )
            })
        }

    }

    @Composable
    private fun ImageBrush() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Text(text = "ImageBitmap 作为 Brush", fontWeight = FontWeight.Bold)

        Row {
            val imageBrush =
                ShaderBrush(ImageShader(ImageBitmap.imageResource(id = R.drawable.lake)))

            // Use ImageShader Brush with background
            Box(
                modifier = Modifier
                    .requiredSize(120.dp)
                    .background(imageBrush)
            )

            // Use ImageShader Brush with TextStyle
            Text(
                text = "Hello \nAndroid!", style = TextStyle(
                    brush = imageBrush, fontWeight = FontWeight.ExtraBold, fontSize = 30.sp
                )
            )

            // Use ImageShader Brush with DrawScope#drawCircle()
            Canvas(onDraw = {
                drawCircle(imageBrush)
            }, modifier = Modifier.size(120.dp))
        }

    }

}

fun DrawScope.drawDashedLine(
    color: Color, strokeWidth: Float, dashWidth: Float, dashGap: Float
) {
    drawLine(
        color = color,
        start = Offset(size.width / 2 - 100.dp.toPx(), 0f),
        end = Offset(size.width / 2 + 100.dp.toPx(), 0f),
        strokeWidth = strokeWidth,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap))
    )
}