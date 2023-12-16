package activities

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.wit.puppie2.databinding.LoginLayoutBinding
import org.wit.puppie2.main.MainActivity
import timber.log.Timber.Forest.i

class LoginActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var bind: LoginLayoutBinding
    private lateinit var app: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = LoginLayoutBinding.inflate(layoutInflater)
        setContentView(bind?.root)
        auth = Firebase.auth

        app = application as MainActivity

        var username = bind.appUsername.text.toString()
        var password = bind.appPassword.text.toString()

        signIn(username, password)
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