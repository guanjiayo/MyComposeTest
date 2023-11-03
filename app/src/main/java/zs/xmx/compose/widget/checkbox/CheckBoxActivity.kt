package zs.xmx.compose.widget.checkbox

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zs.xmx.compose.theme.MyTestTheme

class CheckBoxActivity : AppCompatActivity() {
    private val TAG = CheckBoxActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    CheckBoxSample()
                }
            }
        }
    }

    @Composable
    fun CheckBoxSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //基本使用
            BasicUse()

            //模拟一组CheckBox,多选操作
            CheckBoxGroup()
        }

    }

    @Composable
    private fun BasicUse() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "基本使用", fontWeight = FontWeight.Bold)
        val checkedState = remember { mutableStateOf(true) }
        Checkbox(checked = checkedState.value, onCheckedChange = { checkedState.value = it })
    }

    @Composable
    private fun CheckBoxGroup() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())

        Text(text = "CheckBoxGroup", fontWeight = FontWeight.Bold)

        val checkItems = mutableListOf("Calls", "Missed", "Friends", "ZhangSan", "LiSi")
        MyCheckBoxGroup(checkItems, onOptionSelected = {
            Log.i(TAG, "CheckBoxGroup: $it")
            //Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }

    @Composable
    private fun MyCheckBoxGroup(
        options: List<String>, onOptionSelected: (String) -> Unit
    ) {

        val selectedOption = remember { mutableListOf<String>() }

        Column(modifier = Modifier.selectableGroup()) {

            options.forEach { option ->
                val isChecked = remember(option) { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(horizontal = 4.dp)
                        .selectable(
                            selected = isChecked.value, onClick = {
                                isChecked.value = !isChecked.value
                            }, role = Role.Checkbox
                        ), verticalAlignment = Alignment.CenterVertically
                ) {
                    LaunchedEffect(isChecked.value) {
                        if (isChecked.value) {
                            selectedOption.add(option)
                        } else {
                            selectedOption.remove(option)
                        }
                        if (selectedOption.isNotEmpty()) {
                            onOptionSelected(selectedOption.joinToString(","))
                        }
                    }
                    Checkbox(
                        checked = isChecked.value, onCheckedChange = null
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyMedium.merge(),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }

}