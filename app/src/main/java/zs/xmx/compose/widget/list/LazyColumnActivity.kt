package zs.xmx.compose.widget.list

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import zs.xmx.compose.R
import zs.xmx.compose.theme.MyTestTheme
import kotlin.random.Random

class LazyColumnActivity : AppCompatActivity() {
    private val TAG = LazyColumnActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ConversationSample()
                }
            }
        }
    }

    @Composable
    fun ConversationSample() {
        val messages = SampleData.conversationSample
        LazyColumn {
            items(items = messages, key = { message ->
                //给每个item添加id,防止状态丢失,key的值必须能存储到bundle
                message.id!!
            }) { message ->
                if (message.isMale) {
                    MaleMessage(msg = message)
                } else {
                    FemaleMessage(msg = message, itemClick)
                }
            }
        }
    }

    //item 点击事件
    private val itemClick: (Message) -> Unit = { msg ->
        Log.v(TAG, "$msg")
    }


    @Composable
    fun FemaleMessage(msg: Message, itemClick: (msg: Message) -> Unit) {
        Card(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            Row(modifier = Modifier.padding(all = 8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.female),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(0.5.dp, Color.Black, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))

                var isExpanded by remember { mutableStateOf(false) }
                Column(modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        isExpanded = !isExpanded
                        itemClick(msg)
                    })
                }) {
                    Text(text = msg.author)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = msg.body, maxLines = if (isExpanded) Int.MAX_VALUE else 1
                    )
                }

            }
        }

    }

    @Composable
    fun MaleMessage(msg: Message) {
        Card(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Green)
        ) {
            Row(
                modifier = Modifier
                    .padding(all = 8.dp)
            ) {

                var isExpanded by remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .pointerInput(Unit) {
                            //不带动画的点击事件
                            detectTapGestures(onTap = { isExpanded = !isExpanded })
                        }
                ) {
                    Text(text = msg.author)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = msg.body, maxLines = if (isExpanded) Int.MAX_VALUE else 1
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = painterResource(id = R.drawable.male),
                    contentDescription = null,
                    modifier = Modifier
                        .requiredSize(50.dp)
                        .clip(CircleShape)
                        .border(0.5.dp, Color.Black, CircleShape)
                )
            }
        }

    }


}

object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Colleague", "Test...Test...Test..."
        ),
        Message(
            "Colleague",
            "List of Android versions:\n" + "Android KitKat (API 19)\n" + "Android Lollipop (API 21)\n" + "Android Marshmallow (API 23)\n" + "Android Nougat (API 24)\n" + "Android Oreo (API 26)\n" + "Android Pie (API 28)\n" + "Android 10 (API 29)\n" + "Android 11 (API 30)\n" + "Android 12 (API 31)\n"
        ),
        Message(
            "Colleague",
            "I think Kotlin is my favorite programming language.\n" + "It's so much fun!",
            true
        ),
        Message(
            "Colleague", "Searching for alternatives to XML layouts...", true
        ),
        Message(
            "Colleague",
            "Hey, take a look at Jetpack Compose, it's great!\n" + "It's the Android's modern toolkit for building native UI." + "It simplifies and accelerates UI development on Android." + "Less code, powerful tools, and intuitive Kotlin APIs :)",
            true
        ),
        Message(
            "Colleague", "It's available from API 21+ :)"
        ),
        Message(
            "Colleague",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        Message(
            "Colleague", "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Colleague", "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Colleague",
            "I didn't know you can now run the emulator directly from Android Studio",
            true
        ),
        Message(
            "Colleague",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Colleague",
            "Previews are also interactive after enabling the experimental setting",
            true
        ),
        Message(
            "Colleague", "Have you tried writing build.gradle with KTS?"
        ),
    )
}

data class Message(
    val author: String,
    val body: String,
    var isMale: Boolean = false,
    var id: String? = Random(System.currentTimeMillis()).toString()
)