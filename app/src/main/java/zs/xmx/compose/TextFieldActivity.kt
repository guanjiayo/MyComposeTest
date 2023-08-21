package zs.xmx.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import zs.xmx.compose.ui.theme.MyTestTheme

class TextFieldActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    TextFieldSample()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TextFieldSample() {

        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
            var simpleText by remember { mutableStateOf(TextFieldValue()) }

            TextField(
                value = simpleText,
                onValueChange = { simpleText = it },
                placeholder = { Text(text = "请输入文本!") },//hint
//                colors = TextFieldDefaults.textFieldColors(
//                    containerColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    disabledIndicatorColor = Color.Transparent
//                ),
                //modifier = Modifier.indicatorLine()
            )

            Spacer(modifier = Modifier.height(10.dp))

            //by get/setValue()
            var labelText by remember { mutableStateOf("Hello !") }

            TextField(value = labelText,
                onValueChange = { labelText = it },
                placeholder = { Text(text = "请输入文本!") },//hint
                label = { Text(text = "TextField") })

            Spacer(modifier = Modifier.height(10.dp))

            var outLineText by remember { mutableStateOf("Hello World!!!") }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "请输入文本") },
                label = { Text(text = "OutlinedTextField") },
                value = outLineText,
                onValueChange = { outLineText = it },
            )


        }

    }
}