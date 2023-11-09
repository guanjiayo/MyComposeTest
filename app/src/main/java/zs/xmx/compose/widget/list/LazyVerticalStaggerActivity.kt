package zs.xmx.compose.widget.list

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import zs.xmx.compose.R
import zs.xmx.compose.theme.MyTestTheme

class LazyVerticalStaggerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    StaggerSample()
                }
            }
        }
    }

    @Composable
    fun StaggerSample() {
        val photos = mutableListOf<Int>()
        for (i in 0..20) {
            photos.add(R.drawable.owl)
        }

        /**
         * GridCells: 单元格是如何形成的
         *      FixedSize:  按精确的size,自适应最多的列数,不会均分间隔
         *      Fixed:  固定的列数
         *      Adaptive:  按最小宽度自适应最多的列数,且均分间隔
         *
         */
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(5.dp),
            verticalItemSpacing = 5.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(photos) { index, photo ->
                if (index == 1) {
                    Image(
                        painter = painterResource(id = photo),
                        contentDescription = null,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = photo),
                        contentDescription = null,
                        // modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }


}