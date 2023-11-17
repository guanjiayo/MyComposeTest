@file:OptIn(ExperimentalMaterial3Api::class)

package zs.xmx.compose.exchange

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebView
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

class AndroidWebViewPage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AbstractComposeView(context, attrs) {
    @Composable
    override fun Content() {
        WebViewPage()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun WebViewPage() {
        val webView = rememberWebViewWithLifecycle()
        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "WebView 测试") }, navigationIcon = {
                IconButton(onClick = { Log.e("WebViewPage", "WebViewPage: 点击返回按钮") }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            })
        }) {
            AndroidView(factory = { webView }, update = {
                //设置支持JavaScript
                val webSetting = it.settings
                webSetting.javaScriptEnabled = true
                webView.loadUrl("https://www.baidu.com")
            })
        }
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