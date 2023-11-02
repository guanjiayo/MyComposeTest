@file:OptIn(ExperimentalMaterial3Api::class)

package zs.xmx.compose.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import zs.xmx.compose.theme.MyTestTheme

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

    @Composable
    fun TextFieldSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //基本使用
            BasicUse()

            //TextField前面/后面显示布局
            TextFieldIcon()

            //颜色
            ColorTextField()

            //基于 BasicTextField , 自定义搜索框
            SearchBar()

            //TextField 样式
            StyledTextField()

            //更改输入文本的显示效果,如 * 号显示输入文本
            PasswordTextField()

        }

    }

    @Composable
    private fun BasicUse() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "基本使用", fontWeight = FontWeight.Bold)

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

    @Composable
    private fun ColorTextField() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())

        Text(text = "Colors 参数", fontWeight = FontWeight.Bold)

        var simpleText by remember { mutableStateOf(TextFieldValue()) }

        TextField(value = simpleText,
            onValueChange = { simpleText = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Enter text") },
            placeholder = { Text(text = "请输入文本!") },//hint
            //TextField 相关颜色属性都可以在这里设置
            //底部指示器和背景颜色设置为透明，实现看起来和EditText一样
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,//输入文本颜色
                unfocusedTextColor = Color.LightGray,//hint颜色
                focusedContainerColor = Color.Black,
                unfocusedContainerColor = Color.Black,//背景颜色
                focusedIndicatorColor = Color.Red,//底部指示器几种状态的颜色
                unfocusedIndicatorColor = Color.Green,
                disabledIndicatorColor = Color.LightGray,//禁用
                focusedLabelColor = Color.Red,//Label 几种状态的颜色
                unfocusedLabelColor = Color.LightGray,
                disabledLabelColor = Color.LightGray,
                focusedLeadingIconColor = Color.Red,//LeadingIcon 几种状态颜色
                unfocusedLeadingIconColor = Color.Green,
                focusedTrailingIconColor = Color.White,//TrailingIcon 几种状态颜色
                unfocusedTrailingIconColor = Color.Yellow
            ),
            leadingIcon = { //接收@Composable 函数,在 TextField 添加组件
                //组件位置是固定的,建议只放一个
                //Text(text = "文本")
                Icon(imageVector = Icons.Filled.Face, null)
            },
            trailingIcon = { //接收@Composable 函数,在 TextField 添加组件
                //组件位置是固定的,建议只放一个
                Icon(imageVector = Icons.Filled.Settings, null)
            })
    }

    @Composable
    private fun SearchBar() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())

        Text(text = "基于BasicTextField , 自定义搜索框", fontWeight = FontWeight.Bold)

        //自定义搜索框
        var searchText by remember { mutableStateOf("") }
        BasicTextField(value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(8.dp)
                .fillMaxWidth(),
            singleLine = true,
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

    @Composable
    private fun PasswordTextField() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())

        Text(text = "更改输入文本的显示效果,如 * 号显示输入文本", fontWeight = FontWeight.Bold)

        var password by rememberSaveable { mutableStateOf("") }
        var passwordHidden by remember { mutableStateOf(true) }

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter password") },
            //类似 inputType
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
                }
            },
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        )
    }

    /*
        - SingleLine
        - MaxLines
        - TextStyle
     */
    @Composable
    private fun StyledTextField() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "TextField 样式", fontWeight = FontWeight.Bold)

        var value by remember { mutableStateOf("Hello\nWorld\nInvisible") }

        TextField(
            value = value,
            onValueChange = { value = it },
            label = { Text(text = "Enter text") },
            //singleLine = true,//设置了 singleLine 再设置 maxLines 将无效
            maxLines = 2,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
        )

    }

    @Composable
    private fun TextFieldIcon() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "TextField前面/后面显示布局", fontWeight = FontWeight.Bold)

        var leadingIconText by remember { mutableStateOf("leadingIcon") }

        OutlinedTextField(value = leadingIconText,
            onValueChange = { leadingIconText = it },
            label = { Text(text = "LeadingIcon") },
            leadingIcon = { //接收@Composable 函数,在 TextField 添加组件
                //组件位置是固定的,建议只放一个
                //Text(text = "文本")
                Icon(
                    imageVector = Icons.Filled.Person, null
                )
            })

        Spacer(modifier = Modifier.height(10.dp))

        var trailingIconText by remember { mutableStateOf("trailingIcon") }

        TextField(value = trailingIconText,
            onValueChange = { trailingIconText = it },
            label = { Text(text = "TrailingIcon") },
            trailingIcon = { //接收@Composable 函数,在 TextField 添加组件
                //组件位置是固定的,建议只放一个
                Text(text = "@gmail.com", modifier = Modifier.padding(end = 10.dp))
                //Icon(imageVector = Icons.Filled.Settings, null)
            })

    }

}