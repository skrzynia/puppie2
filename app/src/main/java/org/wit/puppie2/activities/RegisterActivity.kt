package org.wit.puppie2.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.wit.puppie2.main.MainActivity
import timber.log.Timber

class RegisterActivity: ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var app: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainActivity
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
    fun createEmailInput(email:String){

        var email by remember { mutableStateOf("") }

        OutlinedTextField(value = email,
            onValueChange = {email = it},
            label = { Text("E-mail") },
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
    fun showRegisterButton(){
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Register")
        }
    }

    @Preview
    @Composable
    fun uiPreview() {
        TopAppBar {
            Icon(Icons.Rounded.ArrowBack, contentDescription = "Arrow_Back")
        }
        Column(modifier = Modifier.fillMaxSize()){
            createTitle()
            createEmailInput(email = "skrzynia777@gmail.com")
            createPasswordInput(password = "")
            repeatPassword(password = "")
            Row(modifier = Modifier
                .offset(y = 300.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly){
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
    private fun createAccount(email:String, password:String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {
                    task ->
                if (task.isSuccessful) {
                    Timber.i("Create user with email:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Timber.i("Create user with email: failure")
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }
            }
    }
    private fun updateUI(user: FirebaseUser?) {
    }

    private fun reload() {

    }
}