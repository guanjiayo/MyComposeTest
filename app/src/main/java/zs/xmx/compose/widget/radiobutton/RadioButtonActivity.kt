package zs.xmx.compose.widget.radiobutton

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zs.xmx.compose.theme.MyTestTheme

class RadioButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    RadioButtonSample()
                }
            }
        }
    }

    @Composable
    fun RadioButtonSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //基本使用
            BasicUse()

            //Button左边带Icon
            RadioGroup()
        }

    }

    @Composable
    private fun BasicUse() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "基本使用", fontWeight = FontWeight.Bold)

        var state by remember { mutableStateOf(false) }

        RadioButton(selected = state, onClick = {
            state = !state
        })

    }

    @Composable
    private fun RadioGroup() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "RadioGroup", fontWeight = FontWeight.Bold)

        val radioItems = listOf("Calls", "Missed", "Friends")

        MyRadioGroup(
            options = radioItems,
            initialSelectedOption = radioItems[0],
            onOptionSelected = {
                Toast.makeText(this@RadioButtonActivity, it, Toast.LENGTH_SHORT).show()
            })

    }

    @Composable
    private fun MyRadioGroup(
        options: List<String>,
        initialSelectedOption: String,
        onOptionSelected: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        /*
            selectedOption 当前选中的值
            onOptionSelected  用于更新 mutableStateOf(radioItems[0])
         */
        val (selectedOption, setSelectedOption) = remember { mutableStateOf(initialSelectedOption) }

        Column(modifier = modifier.selectableGroup()) {
            options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (option == selectedOption),
                            onClick = {
                                setSelectedOption(option)
                                onOptionSelected(option)
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = null
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyMedium.merge(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }

}