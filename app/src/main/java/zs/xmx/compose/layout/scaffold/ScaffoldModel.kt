package zs.xmx.compose.layout.scaffold

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import zs.xmx.compose.R

sealed class TabBottomInfo(
    val route: String, @StringRes val resourceId: Int, val drawableId: ImageVector
) {
    object Home : TabBottomInfo("home", R.string.tabBottom_home, Icons.Filled.Home)
    object Favorite : TabBottomInfo("favorite", R.string.tabBottom_favorite, Icons.Filled.Favorite)
    object Setting : TabBottomInfo("setting", R.string.tabBottom_setting, Icons.Filled.Settings)
}