package zs.xmx.compose.exchange

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.dylanc.viewbinding.binding
import zs.xmx.compose.databinding.ActivityNativeUseComposeBinding

class NativeUseCustomComposeViewActivity : AppCompatActivity() {

    private val mBinding by binding<ActivityNativeUseComposeBinding>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.pageComposeWebview.setContent {
            WebViewPage()
        }

    }

    @Composable
    private fun WebViewPage() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "复杂控件--需要和页面生命周期联动的", fontWeight = FontWeight.Bold)

        val webView = rememberWebViewWithLifecycle()
        AndroidView(factory = { webView }, update = {
            //设置支持JavaScript
            val webSetting = it.settings
            webSetting.javaScriptEnabled = true
            webView.loadUrl("https://www.baidu.com")
        })
    }

    @Composable
    private fun rememberWebViewWithLifecycle(): WebView {
        val context = LocalContext.current
        val webView = remember { WebView(context) }
        val lifecycleObserver = rememberWebViewWithLifecycleObserver(webView)
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        DisposableEffect(key1 = lifecycle, effect = {
            lifecycle.addObserver(lifecycleObserver)
            onDispose {
                lifecycle.removeObserver(lifecycleObserver)
            }
        })
        return webView
    }

    @Composable
    private fun rememberWebViewWithLifecycleObserver(webView: WebView): LifecycleEventObserver =
        remember(webView) {
            LifecycleEventObserver { source, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> webView.onResume()
                    Lifecycle.Event.ON_PAUSE -> webView.onPause()
                    Lifecycle.Event.ON_DESTROY -> webView.destroy()
                    else -> Log.e("WebView", event.name)
                }
            }
        }

}