package org.wit.puppie2.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Feed
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.launch
import org.wit.puppie2.dao.FStorage
import org.wit.puppie2.main.MainActivity
import org.wit.puppie2.models.Place
import org.wit.puppie2.utilities.Helpers
import timber.log.Timber

class MeActivity:ComponentActivity() {
    private lateinit var app: MainActivity
    private lateinit var storage: FStorage
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private lateinit var firabaseStorage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = FStorage()
        database = Firebase.firestore
        firabaseStorage = Firebase.storage("gs://puppie2.appspot.com").reference


        app = application as MainActivity

        setContent {  createScaffold()}
    }

    @Composable
    fun createScaffold() {

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val scrollState = rememberScrollState()

        auth = Firebase.auth
        Timber.i("AUTH USER! + ${Helpers().getHashedString(auth.currentUser?.email.toString())}")
        storage.getPerson(Helpers().getHashedString(auth.currentUser?.email.toString()))

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
                                onClick = { moveToActivity(i)},
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
                Column(modifier = Modifier
                    .padding(paddingValues)
//                    .verticalScroll(scrollState)
                ) {

                    var list = mutableStateListOf<Place?>()

                    var name by remember {
                        mutableStateOf("")
                    }

                    var address by remember {
                        mutableStateOf("")
                    }

                    var lat by remember {
                        mutableStateOf("")
                    }

                    var lon by remember {
                        mutableStateOf("")
                    }

                    var createdAt by remember {
                        mutableStateOf("")
                    }

                    var pickedImage by remember {
                        mutableStateOf("")
                    }
                    var url by remember {
                        mutableStateOf("")
                    }
                    var user by remember {
                        mutableStateOf("")
                    }
                    database.collection("places").whereEqualTo("user",Helpers().getHashedString(auth.currentUser?.email.toString())).get().addOnSuccessListener {
                            documents -> for (doc in documents) {
                        Timber.i("PLACE IN LIST = ${doc.data.get("name")}")
                        name = doc.data.get("name").toString()
                        address = doc.data.get("address").toString()
                        lat = doc.data.get("lat").toString()
                        lon = doc.data.get("lon").toString()
                        createdAt = doc.data.get("createdAt").toString()
                        pickedImage = doc.data.get("pickedImage").toString()
                        user = doc.data.get("user").toString()


                        list.add(
                            Place(
                                name = name,
                                address = address,
                                lat = lat,
                                lon = lon,
                                createdAt = createdAt,
                                pickedImage = url,
                                user = user))

                    }
                    }.addOnFailureListener{
                        Timber.i("FAILURE IN GET ALL PLACES = $it")
                    }
                    LazyColumn {


                        itemsIndexed(list) {index, item ->


                            list[index]?.let {

                                firabaseStorage.child(pickedImage).downloadUrl.addOnSuccessListener {
                                    url = it.toString()!!
                                    Timber.i("URL = ${url}")
                                }.addOnFailureListener{
                                    Timber.i("DOWNLOADING URL FAILED")
                                }
                                Timber.i("INSIDE LIST = ${it.pickedImage}")
                                getCard(place = Place(name = it.name,
                                    address = it.address,
                                    lat = it.lat,
                                    lon = it.lon,
                                    createdAt = it.createdAt,
                                    pickedImage = url,
                                    user = it.user))
                            }
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun getNavigationDrawer(){
        ModalDrawerSheet {
            Text(
                text = "Navigation Menu",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Divider()
            NavigationDrawerItem(label = {
                Text(text = "Profile", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())},
                selected =  false,
                onClick = { moveToActivity("Profile")})
            Divider()
            NavigationDrawerItem(label = {
                Text(text = "Settings", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())},
                selected =  false,
                onClick = { moveToActivity("Settings")})
            Divider()
            NavigationDrawerItem(label = {
                Text(text = "About us", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())},
                selected =  false,
                onClick = { moveToActivity("About")})
        }
    }


    @Composable
    fun getIcon(name: String) {

        when (name) {
            "News" -> Icon(Icons.Rounded.Feed, "Feed")
            "Me" -> Icon(Icons.Rounded.List, "List")
        }

    }

    @Composable
    fun getCard(place: Place){
        Timber.i("Inside getCard = $place")
        var showDialog by remember {
            mutableStateOf(false)
        }


        Card(modifier = Modifier.padding(5.dp)) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RectangleShape
                    )
                    .border(BorderStroke(1.dp, Color.LightGray))
            ){
                Column(
                    Modifier
                        .clickable { }
                        .fillMaxWidth()
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {

                        Text(modifier = Modifier.fillMaxWidth(), text = place.name!!, textAlign = TextAlign.Center)


                    }

                    AsyncImage(model = place.pickedImage, contentDescription = "Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.25f))
                    Row {
                        Icon(imageVector = Icons.Rounded.LocationOn, contentDescription = "location_on")
                        Text(text = place.address!!, textAlign = TextAlign.Left)
                    }

                    Button(onClick = { showDialog = true }, modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()) {
                        Text(text = "Details", textAlign = TextAlign.Center)
                    }
                    Timber.i("ShowDialog = $showDialog")
                    if (showDialog){
                        getDialog(place = place, showDialog)
                    }



//            ElevatedCard(
//                elevation = CardDefaults.cardElevation(
//                    defaultElevation = 4.dp
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .size(width = 240.dp, height = 100.dp)) {
//
//
//
//            }
                }
            }
        }


    }
    @Composable
    fun getDialog(place: Place, showDialog:Boolean){

        if (showDialog) {
            Dialog(onDismissRequest = { }, properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AsyncImage(
                            model = place.pickedImage,
                            contentDescription = "Image",
                            modifier = Modifier
                                .height(160.dp)
                        )
                        Text(text = place.address!!)
                        Text(text = "Lorem ipsum")
                        AsyncImage(
                            model = "https://www.silverdisc.co.uk/sites/default/files/sd_importer/lion_jpg_21.jpg",
                            contentDescription = "Weather",
                            modifier = Modifier
                                .height(160.dp)
                        )
                        TextButton(
                            onClick = { reload() },
                            modifier = Modifier.padding(30.dp)
                        ) {
                            Timber.i("showDialog = ${showDialog}")
                            Text(text = "Go back")
                        }
                    }
                }
            }
        } else return

    }

    fun moveToActivity(activity: String){
        var intent: Intent = Intent()
        when (activity) {
            "Profile" -> intent = Intent(this, ProfileActivity::class.java)
            "About" -> intent = Intent(this, AboutActivity::class.java)
            "Settings" -> intent = Intent(this,SettingsActivity::class.java)
            "News" -> intent = Intent(this, MainScreenActivity::class.java)
            "Me" -> reload()
        }
        startActivity(intent)
    }

    private fun reload(){
        val intent = Intent(this, MeActivity::class.java)
        startActivity(intent)
    }


}