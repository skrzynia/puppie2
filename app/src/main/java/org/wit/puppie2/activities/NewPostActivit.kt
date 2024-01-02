package org.wit.puppie2.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import io.realm.kotlin.types.RealmUUID
import org.wit.puppie2.dao.FStorage
import org.wit.puppie2.main.MainActivity
import org.wit.puppie2.models.Place
import org.wit.puppie2.utilities.Helpers
import timber.log.Timber.Forest.i
import java.io.File
import java.io.FileInputStream
import java.util.Objects

class NewPostActivit: ComponentActivity() {


    lateinit var storage: FirebaseStorage
    private lateinit var auth: FirebaseAuth
    private lateinit var firebase: FStorage
    private lateinit var fileName: String
    private lateinit var dataase: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        firebase = FStorage()
        dataase = Firebase.firestore
        i("Activity started")

        setContent{
            createScaffold()
        }
    }
    @Preview
    @Composable
    fun createScaffold(){

        val context = LocalContext.current
        var capturedImage by remember {
            mutableStateOf(false)
        }

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
        var pickedImage by remember {
            mutableStateOf("")
        }



        storage = Firebase.storage("gs://puppie2.appspot.com")
        var storageRef = storage.reference
        fileName = RealmUUID.random().toString()
        var imageRef = storageRef.child(fileName)
        val pickmedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                uri ->     if (uri != null) {
            i("PhotoPicker "+ "Selected URI: $uri")
            imageRef.putFile(uri)
            pickedImage = fileName
        } else {
            i("PhotoPicker", "No media selected")
        }
        }

        val capturePhoto = rememberLauncherForActivityResult(contract = (ActivityResultContracts.TakePicture()), onResult =
        {
                success -> capturedImage = success
        }
        )

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
                        IconButton(onClick = { reload()}) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "ArrowBack")
                        }
                    })
            }
        ) {
                paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                OutlinedTextField(value = name, onValueChange = {name = it}, modifier= Modifier.fillMaxWidth())
                OutlinedTextField(value = address, onValueChange = {address = it}, modifier= Modifier.fillMaxWidth())
                OutlinedTextField(value = lat, onValueChange = {lat = it}, modifier= Modifier.fillMaxWidth())
                OutlinedTextField(value = lon, onValueChange = {lon = it}, modifier= Modifier.fillMaxWidth())
                Row {
                    OutlinedButton(onClick = { pickmedia.launch(PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )) }) {
                        Icon(imageVector = Icons.Rounded.Folder, contentDescription = "Folder")
                    }
                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.PhotoCamera, contentDescription = "Photo Camera")
                    }
                }
                Button(onClick = { addPlace(name,address,lat,lon,pickedImage) }) {
                    Text(text = "Save")
                }

            }
        }
    }

    fun addPlace(name:String, address: String, lat:String, lon:String, pickedImage: String){
        val hashed = Helpers().getHashedString(auth.currentUser?.email.toString())
        i("USER = $hashed")
        val placeId = RealmUUID.random().toString()
        firebase.writeNewPlace(placeId = placeId,
            place = Place(name = name,
            address = address,
            lat = lat,
            lon = lon,
            pickedImage = pickedImage,
                user = hashed.toString()))
        dataase.collection("people").document(Helpers().getHashedString(auth.currentUser?.email.toString()))
            .update("myPlace", FieldValue.arrayUnion(placeId))
        Toast.makeText(this, "Place successfully added", Toast.LENGTH_SHORT).show()

    }

    fun reload(){
        val intent = Intent(this, MainScreenActivity::class.java)
        startActivity(intent)
    }

}