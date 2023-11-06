package zs.xmx.compose.widget.chip

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
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
import zs.xmx.compose.theme.MyTestTheme

class ChipActivity : AppCompatActivity() {
    private val TAG = ChipActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ChipSample()
                }
            }
        }
    }

    @Composable
    fun ChipSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //辅助条状标签
            AssistChipExample()

            //过滤条状标签
            FilterChipExample()

            //输入条状标签
            InputChipExample("Input Chip", onDismiss = {

            })

            //建议条状标签
            SuggestionChipExample()

            Note()
        }

    }

    @Composable
    private fun AssistChipExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "辅助条状标签", fontWeight = FontWeight.Bold)

        AssistChip(
            onClick = { Log.d(TAG, "Assist chip") },
            label = { Text("Assist chip") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null,
                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                )
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun FilterChipExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "过滤条状标签", fontWeight = FontWeight.Bold)

        var selected by remember { mutableStateOf(false) }

        FilterChip(
            selected = selected,
            onClick = { selected = !selected },
            label = { Text("Filter chip") },
            leadingIcon = {
                if (selected) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done icon",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                } else {
                    null
                }
            }
        )


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun InputChipExample(
        text: String,
        onDismiss: () -> Unit
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "输入条状标签", fontWeight = FontWeight.Bold)

        var enabled by remember { mutableStateOf(true) }
        if (!enabled) return

        InputChip(selected = enabled, onClick = {
            onDismiss()
            enabled = !enabled
        }, label = { Text(text) },
            avatar = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Localized description",
                    Modifier.size(InputChipDefaults.AvatarSize)
                )
            }, trailingIcon = {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Localized description",
                    Modifier.size(InputChipDefaults.AvatarSize)
                )
            })

    }

    @Composable
    private fun SuggestionChipExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "建议条状标签", fontWeight = FontWeight.Bold)

        SuggestionChip(
            onClick = { Log.d(TAG, "Suggestion  chip") },
            label = { Text("Suggestion  chip") },
        )
    }

    @Composable
    private fun Note() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "Note: ", color = Color.Red, fontWeight = FontWeight.Bold)

        Text(text = "Chip 自带上下间距,官方说法是增加可点击区域,可用 Modifier.height(32.dp)重写")
    }

}