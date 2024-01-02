package org.wit.puppie2.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.wit.puppie2.utilities.Helpers

class SettingsActivity: ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        database = Firebase.firestore

        setContent {

            var name by remember {
                mutableStateOf("")
            }
            var surname by remember {
                mutableStateOf("")
            }
            TopAppBar {
                TextButton(onClick = { reload() }) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = "Arrow_Back")
                }

            }

            Column(modifier = Modifier
                .offset(y = 100.dp)
                .fillMaxWidth()
                .fillMaxHeight()) {
                OutlinedTextField(label = { Text("Name")}, value = name, onValueChange = {name = it}, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(label = { Text(text = "Surname")}, value = surname, onValueChange = {surname = it}, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = auth.currentUser?.email.toString(), onValueChange = {}, enabled= false, modifier = Modifier.fillMaxWidth())
                getButton(name = name , surname = surname )
                Toast.makeText(baseContext, "User details updated", Toast.LENGTH_SHORT).show()

            }
        }
    }


    @Composable
     fun getButton(name:String, surname:String){


//        var name = remember {
//            mutableStateOf("")
//        }
//        var surname = remember {
//            mutableStateOf("")
//        }

        Button(onClick = {database.collection("people").document(Helpers().getHashedString(auth.currentUser?.email.toString())
        ).update("name",name, "surname", surname)}) {
            Text(text = "Save")

        }

    }

    fun reload(){
        val intent = Intent(this, MainScreenActivity::class.java)
        startActivity(intent)
    }
}