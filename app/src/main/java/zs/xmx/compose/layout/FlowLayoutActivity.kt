package zs.xmx.compose.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.material.chip.Chip
import zs.xmx.compose.theme.MyTestTheme

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
class FlowLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    FlowLayoutSample()
                }
            }
        }
    }

    @Composable
    fun FlowLayoutSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //竖向线性流式布局
            FlowColumnExample()

            //横向线性流式布局
            FlowRowExample()

        }

    }

    @Composable
    private fun FlowColumnExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "FlowColumn 竖向线性流式布局", fontWeight = FontWeight.Bold)

        val filters = listOf(
            "Washer/Dryer", "Ramp access", "Garden", "Cats OK", "Dogs OK", "Smoke-free"
        )

        FlowColumn(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            maxItemsInEachColumn = 2//每列两个
        ) {
            filters.forEach { title ->
                var selected by remember { mutableStateOf(false) }
                val leadingIcon: @Composable () -> Unit = { Icon(Icons.Default.Check, null) }
                FilterChip(
                    selected,
                    onClick = { selected = !selected },
                    label = { Text(title) },
                    leadingIcon = if (selected) leadingIcon else null
                )
            }
        }

    }

    @Composable
    private fun FlowRowExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "FlowRow 横向线性流式布局", fontWeight = FontWeight.Bold)

        val filters = listOf(
            "LMB (Largmouth Bass)",
            "Trout",
            "Walleye",
            "SMB (Smallmouth Bass)",
            "Panfish",
            "Catfish",
            "Salmon",
            "Pike",
            "Saltwater",
            "Musky",
            "Steelhead"
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEach { title ->
                var selected by remember { mutableStateOf(false) }
                val leadingIcon: @Composable () -> Unit = { Icon(Icons.Default.Check, null) }
                FilterChip(
                    selected,
                    onClick = { selected = !selected },
                    label = { Text(title) },
                    leadingIcon = if (selected) leadingIcon else null
                )
            }

        }
    }

}