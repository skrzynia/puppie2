package org.wit.puppie2.activities

import android.app.Person
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.TextButton
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
import com.google.firebase.firestore.toObject
import org.wit.puppie2.utilities.Helpers

class ProfileActivity :ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        database = Firebase.firestore
        var document = database.collection("people").document(Helpers().getHashedString(auth.currentUser?.email.toString()))
        
        setContent{
            TopAppBar {
                TextButton(onClick = { reload() }) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = "Arrow_Back")
                }

            }
            var name by remember {
                mutableStateOf("")
            }
            var surname by remember {
                mutableStateOf("")
            }
            document.get().addOnSuccessListener {  doc ->
                name = doc.get("name").toString()
                surname = doc.get("surname").toString()
            }
            Column(modifier = Modifier
                .offset(y = 100.dp)
                .fillMaxWidth()
                .fillMaxHeight()) {
                Text(text = name)
                Text(text = surname)

            }

                    }

        }
        fun reload(){
            val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
        }
    }