package zs.xmx.compose.adapter

import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.RecyclerView
import zs.xmx.compose.model.MainItem

class MainAdapter(private val items: List<MainItem>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    class ViewHolder(val composeView: ComposeView) : RecyclerView.ViewHolder(composeView)

    private var itemClickListener: ItemClickListener? = null

    fun setItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val composeView = ComposeView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return ViewHolder(composeView)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.composeView.setContent {
            ItemLayout(item = item) {
                itemClickListener?.onItemClick(position)
            }
        }
    }


    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

}

@Composable
fun ItemLayout(item: MainItem, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(5.dp)
            .clickable(onClick = onItemClick)
    ) {
        Text(text = item.title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = item.desc, fontSize = 16.sp, color = Color.Gray)
    }

}