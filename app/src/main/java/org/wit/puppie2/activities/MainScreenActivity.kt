package org.wit.puppie2.activities


import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Feed
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.wit.puppie2.main.MainActivity
import org.wit.puppie2.models.Person
import org.wit.puppie2.models.Place
import timber.log.Timber.Forest.i
import java.time.LocalDate
import java.util.Date

class MainScreenActivity : ComponentActivity() {
    private lateinit var app: MainActivity

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        app = application as MainActivity


        setContent { uiPreview() }
    }

    @Composable
    fun createScaffold() {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        val items = listOf("News", "Me")
        ModalNavigationDrawer(
            drawerState = drawerState ,
            drawerContent = {
        ModalDrawerSheet {
            getNavigationDrawer()
        } }) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Puppie2",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth(),
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch {
                                drawerState.apply { if (isClosed) open() else close()
                                }
                            }
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Menu,
                                    contentDescription = "Menu")
                            }
                        })
                },
                bottomBar = {
                    BottomAppBar {
                        items.forEach { i ->
                            NavigationBarItem(
                                selected = true,
                                onClick = { /*TODO*/ },
                                icon = { getIcon(name = i) },
                                label = { Text(text = i) },
                                modifier =Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { context.startActivity(Intent(context, NewPostActivit::class.java))},
                    ) {
                        Icon(Icons.Rounded.Add, "Add")

                    }
                }
            ) { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    getCard()
                }
            }
        }
        }

    @Preview
    @Composable
    fun uiPreview() {
        createScaffold()

    }
    @Composable
    fun getCard(){

        Column(
            Modifier
                .clickable { }
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(bitmap = ImageBitmap(1,1), contentDescription = "")
                Text(text = "Test Text")
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "2023-12-12")
            }
            Spacer(modifier = Modifier.size(16.dp))
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(width = 240.dp, height = 100.dp)) {

                Image(bitmap = ImageBitmap(1,1), contentDescription ="" )

            }
        }
    }
    @Composable
    fun getNavigationDrawer(){
            ModalDrawerSheet {
                Text(text = "Navigation Menu",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),)
                Divider()
                NavigationDrawerItem(label = {
                    Text(text = "Profile", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())},
                    selected =  true,
                    onClick = { /*TODO*/ })
                Divider()
                NavigationDrawerItem(label = {
                    Text(text = "Settings", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())},
                    selected =  false,
                    onClick = { /*TODO*/ })
                Divider()
                NavigationDrawerItem(label = {
                    Text(text = "About us", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())},
                    selected =  false,
                    onClick = { /*TODO*/ })
                }
    }


    @Composable
    fun getIcon(name: String) {

        when (name) {
            "News" -> Icon(Icons.Rounded.Feed, "Feed")
            "List" -> Icon(Icons.Rounded.List, "List")
        }

    }
}