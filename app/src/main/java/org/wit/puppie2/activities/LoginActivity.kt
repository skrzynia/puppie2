package org.wit.puppie2.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.wit.puppie2.main.MainActivity
import timber.log.Timber.Forest.i

class LoginActivity: ComponentActivity(){

    private lateinit var auth: FirebaseAuth
    private lateinit var app: MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        app = application as MainActivity


        setContent {
            Column(modifier = Modifier.fillMaxSize()){
                createTitle()
                createEmailInput(email = "")
                createPasswordInput(password = "")
                Row(modifier = Modifier
                    .offset(y = 300.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly){
                    showLoginButton()
                    showRegisterButton()
                }

            }

        }
    }
    @Composable
    fun createTitle() {

        val gradientColors = listOf(Cyan, Blue, Color.Companion.Magenta)
        Text(text = "Puppie 2",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset(y= 100.dp)
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
    fun createEmailInput(email:String){

        var email by remember { mutableStateOf("")}

        OutlinedTextField(value = email,
            onValueChange = {email = it},
            label = {Text("E-mail")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .offset(y = 200.dp)
                .fillMaxWidth())
    }
    @Composable
    fun createPasswordInput(password: String){
        var password by remember { mutableStateOf("") }

        OutlinedTextField(value = password,
            onValueChange ={password = it},
            label = {Text("Password")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .offset(y = 200.dp)
                .fillMaxWidth())
    }
    @Composable
    fun showLoginButton(){
        FilledTonalButton(onClick = { /*TODO*/ }) {
            Text(text = "Login")
        }
    }
    @Composable
    fun showRegisterButton(){
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Register")
        }
    }

    @Preview
    @Composable
    fun uiPreview() {
        Column(modifier = Modifier.fillMaxSize()){
            createTitle()
            createEmailInput(email = "skrzynia777@gmail.com")
            createPasswordInput(password = "")
            Row(modifier = Modifier
                .offset(y = 300.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly){
                showLoginButton()
                showRegisterButton()
            }

        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {
                task ->
                if (task.isSuccessful) {
                    i("Sign in with email:success")
                    val user = auth.currentUser
                    updateUI(user)
                }else {
                    i("Sign in with email: failure")
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

        }
    }

    private fun reload() {
    }
    private fun updateUI(user: FirebaseUser?) {
    }

}