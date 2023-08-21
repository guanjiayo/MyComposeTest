package zs.xmx.compose.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zs.xmx.compose.R


/**
 * Author: 默小铭
 * Blog:   https://blog.csdn.net/u012792686
 * Desc:
 *
 */
@Composable
fun TextView() {
    val context = LocalContext.current

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(
            text = stringResource(id = R.string.tv_hello),
            fontSize = 25.sp,
            color = Color.Red,
            fontStyle = FontStyle.Italic,//只有斜体可设置
            fontWeight = FontWeight(1)//字体粗细
        )

        Text(
            text = stringResource(id = R.string.tv_hello),
            fontSize = 20.sp,
            color = Color.Black,
            fontStyle = FontStyle.Italic,//只有斜体可设置
            fontWeight = FontWeight.Bold,//字体粗细
            fontFamily = FontFamily.Cursive//字体
        )

        Text(
            text = stringResource(id = R.string.tv_hello),
            fontSize = 30.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,//字体粗细
        )

        //自定义字体库
        Text(
            text = stringResource(id = R.string.tv_hello),
            fontSize = 30.sp,
            color = Color.Blue,
            fontFamily = myFontFamily, fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(id = R.string.tv_hello),
            fontSize = 25.sp,
            color = Color.DarkGray,
            fontFamily = myFontFamily, fontWeight = FontWeight.Light,
            letterSpacing = 5.sp//字间距
        )

        Text(
            text = stringResource(id = R.string.tv_hello),
            fontSize = 25.sp,
            modifier = Modifier.fillMaxWidth(),//类似layout_width="match_parent"
            textDecoration = TextDecoration.Underline,//文字装饰 //Underline文本下方下划线,LineThrough文本中间水平线
            textAlign = TextAlign.Center//居中显示
        )

        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Red, fontSize = 20.sp)) {
                append("Hello ")
            }
            append("Android\n")
            withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                append("Hello ")
            }
            append("JetPack Compose")
        })

        Column {
            Text(
                text = stringResource(id = R.string.tv_music_word),
                fontSize = 16.sp,
                lineHeight = 24.sp
            )

            Spacer(Modifier.height(20.dp))

            //最多显示一行,超出部分...显示
            Text(
                text = stringResource(id = R.string.tv_music_word),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
            )

            Spacer(Modifier.height(20.dp))
        }

        //可选择的文字
        SelectionContainer(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text("天青色等煙雨")
                Text("而我在等妳")
                DisableSelection {//不允许点击
                    Text("炊煙裊裊昇起")
                    Text("隔江千萬里")
                }
                Text("在瓶底書漢隸仿前朝的飄逸")
                Text("就當我為遇見妳伏筆")
                ClickableText(text = AnnotatedString("点击"), onClick = {
                    Toast.makeText(context, "点击了这个文本", Toast.LENGTH_SHORT).show()
                })
            }
        }

        Spacer(Modifier.height(20.dp))

        ClickableText(text = annotatedText, onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    //如果不为空,记录该值
                    Toast.makeText(context, annotation.item, Toast.LENGTH_SHORT).show()
                }

        })
    }

}

val annotatedText = buildAnnotatedString {
    append("点击 ")
    // 此 *URL* 注释附加到以下内容
    // 直到 `pop()` 被调用
    pushStringAnnotation(tag = "URL", annotation = "https://www.baidu.com")//开始标记
    withStyle(
        style = SpanStyle(
            color = Color.Blue,
            fontWeight = FontWeight.Bold
        )
    ) {
        append("Url")
    }
    pop()//结束标记
    append(" 链接")
}

val myFontFamily = FontFamily(
    Font(R.font.oswald_bold, FontWeight.Bold),
    Font(R.font.oswald_light, FontWeight.Light),
    Font(R.font.oswald_regular, FontWeight.Normal)
)

@Preview(showBackground = true)
@Composable
fun TextPreview() {
    TextView()
}