package zs.xmx.compose.widget

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //绘制点
            DrawPoint()
        }

    }

    @Composable
    private fun DrawPoint() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "绘制点", fontWeight = FontWeight.Bold)

        val points = arrayListOf(
            Offset(100f, 100f),
            Offset(300f, 300f),
            Offset(500f, 500f),
            Offset(700f, 700f),
            Offset(900f, 900f)
        )

        Canvas(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), onDraw = {
            drawPoints(
                points = points, pointMode = PointMode.Points,
                color = Color.Blue, strokeWidth = 30f
            )
        })

    }

}