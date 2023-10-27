@file:OptIn(ExperimentalMaterial3Api::class)

package zs.xmx.compose.ui.button

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zs.xmx.compose.ext.singClick
import zs.xmx.compose.ui.theme.MyTestTheme

class ButtonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ButtonSample()
                }
            }
        }
    }

    @Composable
    fun ButtonSample() {
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
            LeftIconButton()

            //不同点击状态下的按钮状态
            ButtonState()
        }

    }

    @Composable
    private fun BasicUse() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "基本使用", fontWeight = FontWeight.Bold)
        Button(onClick = { /*TODO*/ }) {
            Text(text = "简单按钮")
        }

    }

    @Composable
    private fun LeftIconButton() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())

        Text(text = "添加图标在文字左边", fontWeight = FontWeight.Bold)

        Button(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            //添加间隔
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "Favorite")
        }

    }

    @Composable
    private fun ButtonState() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())

        Text(text = "不同点击状态下的按钮状态", fontWeight = FontWeight.Bold)

        //获取按钮得状态
        val interactionState = remember { MutableInteractionSource() }
        val (text, textColor, buttonColor) = when {
            interactionState.collectIsPressedAsState().value -> ButtonState(
                "Just Pressed",
                Color.Red,
                Color.Black
            )

            else -> ButtonState(
                "Just Button",
                Color.White,
                Color.Red
            )
        }

        Button(
            onClick = { /*TODO*/ },
            interactionSource = interactionState,
            elevation = null,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
        ) {
            Text(text = text, color = textColor)
        }

        Text(text = "测试singleClick", modifier = Modifier
            .singClick {
                Log.d("TTTT", "点击事件: ")
            }
            .padding(10.dp))

    }


}



