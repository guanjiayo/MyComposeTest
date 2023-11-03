package zs.xmx.compose.widget.progress

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zs.xmx.compose.theme.MyTestTheme


class ProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ProgressSample()
                }
            }
        }
    }

    @Composable
    fun ProgressSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //圆形进度
            CircularProgress()
            //条形进度
            LinearProgress()
            //手动设置圆形进度progress
            DynamicCircularProgress()
            //手动设置条形进度progress
            DynamicLinearProgress()
        }

    }

    @Composable
    private fun CircularProgress() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "圆形进度", fontWeight = FontWeight.Bold)
        CircularProgressIndicator()
    }

    @Composable
    private fun LinearProgress() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "条形进度", fontWeight = FontWeight.Bold)
        LinearProgressIndicator(
            color = Color.Red,
            trackColor = Color.Yellow
        )
    }

    @Composable
    private fun DynamicCircularProgress() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "手动设置圆形进度progress", fontWeight = FontWeight.Bold)

        var progress by remember { mutableFloatStateOf(0.1f) }
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec, label = ""
        )

        Spacer(modifier = Modifier.requiredHeight(10.dp))
        CircularProgressIndicator(progress = animatedProgress)

        Spacer(modifier = Modifier.requiredHeight(10.dp))

        Button(onClick = {
            if (progress < 1f) {
                progress += 0.1f
            }
        }) {
            Text(text = "增加进度")
        }

    }

    @Composable
    private fun DynamicLinearProgress() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "手动设置条形进度progress", fontWeight = FontWeight.Bold)

        var progress by remember { mutableFloatStateOf(0.1f) }
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec, label = ""
        )

        Spacer(modifier = Modifier.requiredHeight(10.dp))
        LinearProgressIndicator(progress = animatedProgress)

        Spacer(modifier = Modifier.requiredHeight(10.dp))

        Button(onClick = {
            if (progress < 1f) {
                progress += 0.1f
            }
        }) {
            Text(text = "增加进度")
        }

    }

}