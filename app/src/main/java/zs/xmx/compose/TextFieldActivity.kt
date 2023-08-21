package zs.xmx.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.google.android.material.search.SearchBar
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
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            var simpleText by remember { mutableStateOf(TextFieldValue()) }

            TextField(
                value = simpleText,
                onValueChange = { simpleText = it },
                placeholder = { Text(text = "请输入文本!") },//hint
                colors = TextFieldDefaults.textFieldColors( //底部指示器和背景颜色设置为透明，实现看起来和EditText一样
                    containerColor = Color.Cyan,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
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

            Spacer(modifier = Modifier.height(10.dp))



            Spacer(modifier = Modifier.height(10.dp))

            //自定义搜索框
            var searchText by remember { mutableStateOf("") }
            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .padding(8.dp)
                    .fillMaxWidth(),
                singleLine = true,//设置了 singleLine 再设置 maxLines 将无效
                decorationBox = { innerTextField ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null,
                            tint = Color(0x88000000)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp, end = 4.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (searchText.isEmpty()) Text(text = "搜索", color = Color(0x88000000))
                            innerTextField()
                        }
                        if (searchText.isNotEmpty()) Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null,
                            tint = Color(0x88000000)
                        )
                        else Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            tint = Color(0xFF000000)
                        )
                    }
                })


        }

    }
}