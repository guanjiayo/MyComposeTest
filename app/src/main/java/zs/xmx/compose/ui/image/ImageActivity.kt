package zs.xmx.compose.ui.image

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import zs.xmx.compose.R
import zs.xmx.compose.ui.theme.MyTestTheme

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ImageSample()
                }
            }
        }
    }

    @Composable
    private fun ImageSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //矢量图
            ImageVector()
            //位图
            ImageBitmap()
            //画笔
            ImagePainter()
            //图片形状
            ImageShape()
            //图像边框
            ImageBorder()
            //Coil 加载网络图片
            CoilUrlImage()
        }
    }

    @Composable
    private fun ImageVector() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "矢量图", fontWeight = FontWeight.Bold)

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.music),
            contentDescription = "矢量图",
            modifier = Modifier
                .width(400.dp)
                .height(200.dp)
        )

    }

    @Composable
    private fun ImageBitmap() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "位图", fontWeight = FontWeight.Bold)

        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.owl),
            contentDescription = "位图",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

    }

    @Composable
    private fun ImagePainter() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "画笔", fontWeight = FontWeight.Bold)

        Image(
            painter = painterResource(id = R.drawable.coon),
            contentDescription = "画笔",
            modifier = Modifier
                .width(400.dp)
                .height(200.dp)
        )

    }

    @Composable
    private fun ImageShape() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "图片形状", fontWeight = FontWeight.Bold)

        Surface(shape = CircleShape) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.female),
                contentDescription = "图片形状",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
        }

    }

    @Composable
    private fun ImageBorder() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "图像边框", fontWeight = FontWeight.Bold)

        Surface(
            shape = CircleShape,
            border = BorderStroke(5.dp, Color.LightGray)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.male),
                contentDescription = "图像边框",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
        }

    }

    @Composable
    private fun CoilUrlImage() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "Coil加载网络图片", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "AsyncImage", fontWeight = FontWeight.Bold)

        AsyncImage(
            model = "https://pic-go-bed.oss-cn-beijing.aliyuncs.com/img/20220316151929.png",
            contentDescription = null,
            modifier = Modifier.wrapContentSize()
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "AsyncImage --- ImageRequest", fontWeight = FontWeight.Bold)

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://pic-go-bed.oss-cn-beijing.aliyuncs.com/img/20220316151929.png")
                .crossfade(true)//淡入效果
                .placeholder(R.drawable.dog)
                .error(R.drawable.dog)
                .build(),
            contentDescription = null,
            modifier = Modifier.wrapContentSize()
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "SubcomposeAsyncImage", fontWeight = FontWeight.Bold)

        SubcomposeAsyncImage(
            model = "https://pic-go-bed.oss-cn-beijing.aliyuncs.com/img/20220316151929.png",
            contentDescription = null,
            loading = {
                CircularProgressIndicator()
            }
        )

    }
}