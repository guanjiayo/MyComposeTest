package zs.xmx.compose.exchange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.dylanc.viewbinding.binding
import zs.xmx.compose.R
import zs.xmx.compose.databinding.ActivityNativeBinding

class NativeActivity : AppCompatActivity() {

    private val mBinding by binding<ActivityNativeBinding>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.textview.text = "这是原生TextView"
        mBinding.composeView.setContent {
            Column {
                Text(text = "这是一个Compose  Text", fontSize = 16.sp)
            }
        }
        mBinding.llCompose.apply {
            addView(ComposeView(this@NativeActivity).apply {
                id = R.id.compose_one
                setContent {
                    Text(
                        text = "原生LinearLayout add ComposeView",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            })
            addView(Button(this@NativeActivity).apply {
                id = R.id.compose_btn
                text = "原生Button item"
            })
            addView(ComposeView(this@NativeActivity).apply {
                id = R.id.compose_two
                setContent {
                    Text(text = "Compose Text item2", fontSize = 16.sp)
                }
            })
        }

    }

}