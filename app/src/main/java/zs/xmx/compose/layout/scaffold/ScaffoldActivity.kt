package zs.xmx.compose.layout.scaffold

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import zs.xmx.compose.theme.MyTestTheme

@OptIn(ExperimentalMaterial3Api::class)
class ScaffoldActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                AppScaffold()
            }
        }
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun AppScaffold() {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        var presses by remember { mutableIntStateOf(0) }

        val navController = rememberNavController()

        val selectionItem = remember { mutableStateOf("home") }

        ModalNavigationDrawer(drawerState = drawerState, drawerContent = { AppDrawerContent() }) {
            Scaffold(topBar = { AppTopBar(drawerState, scope) }, bottomBar = {
                //AppBottomBar(navController)
                AppBottomBar(selectionItem)
            }, floatingActionButton = {
                FloatingActionButton(onClick = { presses++ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }) { innerPadding ->
                //Navigation用法,但是效果卡顿,暂时不上
                //MyNavHost(innerPadding, navController)
                Column(
                    modifier = Modifier.padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    when (selectionItem.value) {
                        "home" -> HomePage()
                        "favorite" -> FavoritePage()
                        "setting" -> SettingPage()
                    }
                }
            }
        }
    }

    @Composable
    private fun MyNavHost(innerPadding: PaddingValues, navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = TabBottomInfo.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TabBottomInfo.Home.route) { HomePage() }
            composable(TabBottomInfo.Favorite.route) { FavoritePage() }
            composable(TabBottomInfo.Setting.route) { SettingPage() }
        }
    }

    @Composable
    private fun HomePage() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button presses times.
                """.trimIndent(),
            )
        }
    }

    @Composable
    private fun FavoritePage() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "FavoritePage")
        }
    }

    @Composable
    private fun SettingPage() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "SettingPage")
        }
    }

    @Composable
    private fun AppDrawerContent() {
        ModalDrawerSheet {
            Text("Drawer title", modifier = Modifier.padding(16.dp))
            Divider()
            NavigationDrawerItem(label = { Text(text = "Drawer Item") },
                selected = false,
                onClick = { /*TODO*/ })
        }
    }

    @Composable
    private fun AppTopBar(drawerState: DrawerState, scope: CoroutineScope) {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
            }
        }, actions = {
            IconButton(onClick = { } //do something
            ) {
                Icon(Icons.Filled.Search, null)
            }
            IconButton(onClick = { } //do something
            ) {
                Icon(Icons.Filled.MoreVert, null)
            }
        }, title = { Text(text = "Top app bar") })
    }

    @Composable
    private fun AppBottomBar(navController: NavHostController) {
        val items = listOf(TabBottomInfo.Home, TabBottomInfo.Favorite, TabBottomInfo.Setting)

        NavigationBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            items.forEach { screen ->
                NavigationBarItem(icon = {
                    Icon(
                        screen.drawableId,
                        contentDescription = stringResource(id = screen.resourceId)
                    )
                },
                    label = { Text(stringResource(id = screen.resourceId)) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        // 加这个可解决问题：按back键会返回2次，第一次先返回home, 第二次才会退出
                        navController.popBackStack()
                        navController.navigate(screen.route) {
                            // 点击item时，清空栈内 popUpTo ID到栈顶之间的所有节点，避免站内节点持续增加
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // 避免多次重复点击按钮时产生多个实例
                            launchSingleTop = true
                            // 再次点击之前选中的Item时，恢复之前的状态
                            restoreState = true
                            // 通过使用 saveState 和 restoreState 标志，当您在底部导航项之间切换时，
                            // 系统会正确保存并恢复该项的状态和返回堆栈。
                        }
                    })
            }
        }
    }

    @Composable
    private fun AppBottomBar(selectedItem: MutableState<String>) {
        val items = listOf(TabBottomInfo.Home, TabBottomInfo.Favorite, TabBottomInfo.Setting)

        NavigationBar {
            items.forEach { screen ->
                NavigationBarItem(icon = {
                    Icon(
                        screen.drawableId,
                        contentDescription = stringResource(id = screen.resourceId)
                    )
                },
                    label = { Text(stringResource(id = screen.resourceId)) },
                    selected = selectedItem.value == screen.route,
                    onClick = {
                        selectedItem.value = screen.route
                    })
            }
        }
    }

}