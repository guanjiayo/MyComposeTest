package zs.xmx.compose.exchange

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.sp
import com.dylanc.viewbinding.binding
import zs.xmx.compose.R
import zs.xmx.compose.databinding.ActivityNativeUseComposeBinding

class NativeUseCustomComposeViewActivity : AppCompatActivity() {

    private val mBinding by binding<ActivityNativeUseComposeBinding>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.textview.text = "这是原生TextView"
        mBinding.composeView.setContent {
            Column {
                Text(text = "这是一个Compose  Text", fontSize = 16.sp)
            }
        }
        mBinding.llCompose.apply {
            addView(ComposeView(this@NativeUseCustomComposeViewActivity).apply {
                id = R.id.compose_one
                setContent {
                    Text(
                        text = "原生LinearLayout add ComposeView",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            })
            addView(Button(this@NativeUseCustomComposeViewActivity).apply {
                id = R.id.compose_btn
                text = "原生Button item"
            })
            addView(ComposeView(this@NativeUseCustomComposeViewActivity).apply {
                id = R.id.compose_two
                setContent {
                    Text(text = "Compose Text item2", fontSize = 16.sp)
                }
            })
        }

    }

}