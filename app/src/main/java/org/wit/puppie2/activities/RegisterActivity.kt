package org.wit.puppie2.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.widget.ThemedSpinnerAdapter.Helper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import org.wit.puppie2.dao.FStorage
import org.wit.puppie2.main.MainActivity
import org.wit.puppie2.models.Person
import org.wit.puppie2.utilities.Helpers
import timber.log.Timber.Forest.i
import java.util.UUID

class RegisterActivity: ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var app: MainActivity
    private lateinit var firebase: FStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        firebase = FStorage()



        setContent {
            uiPreview()
        }
    }


    @Composable
    fun createTitle() {

        val gradientColors = listOf(Color.Cyan, Color.Blue, Color.Companion.Magenta)
        Text(text = "Puppie 2",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset(y = 100.dp)
                .fillMaxWidth(),
            fontSize = 30.sp,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            )

        )
    }

    @Composable
    fun createEmailInput(email:String, onValueChange: (String) -> Unit){

        OutlinedTextField(value = email,
            onValueChange = onValueChange,
            label = { Text("E-mail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .offset(y = 200.dp)
                .fillMaxWidth())
    }
    @Composable
    fun createPasswordInput(password: String, onValueChange: (String) -> Unit){

        OutlinedTextField(value = password,
            onValueChange = onValueChange,
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .offset(y = 200.dp)
                .fillMaxWidth())
    }

    @Composable
    fun repeatPassword(password: String){
        var password by remember {
            mutableStateOf("")
        }

        OutlinedTextField(value = password,
            onValueChange ={password = it},
            label = { Text("Repeat Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .offset(y = 200.dp)
                .fillMaxWidth())
    }


    @Composable
    fun showRegisterButton(email: String, password: String){
        Button(onClick = { registerNewAccount(email, password)}) {
            Text(text = "Register")
        }
    }

    @Preview
    @Composable
    fun uiPreview() {

        var email by rememberSaveable {
            mutableStateOf("")
        }
        var password by rememberSaveable {
            mutableStateOf("")
        }
        TopAppBar {
            TextButton(onClick = { backToLogin() }) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = "Arrow_Back")
            }

        }
        Column(modifier = Modifier.fillMaxSize()){
            createTitle()
            createEmailInput(email = email, onValueChange = {email = it})
            createPasswordInput(password = password, onValueChange = {password = it})
            repeatPassword(password = "")
            Row(modifier = Modifier
                .offset(y = 300.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly){
                showRegisterButton(email, password)
            }

        }
    }


    private fun registerNewAccount(email: String, password: String){
        createAccount(email, password)
        i("Email = " + email + "Password = " + password)
        val person = Person(email)
        val hashed = Helpers().getHashedString(auth.currentUser?.email.toString())
        firebase.writeNewUser(personId = hashed,person=person.copy(name = "", surname = "",email=email, myPlace = listOf()))

    }

    private fun createAccount(email:String, password:String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {
                    task ->
                if (task.isSuccessful) {
                    i("Create user with email:success")
                    Toast.makeText(baseContext, "User Registration success!!!!!", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    i("Create user with email: failure")
                    Toast.makeText(baseContext, "User registration failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, MainScreenActivity::class.java)
        startActivity(intent)
    }

    private fun backToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun reload() {

    }
}