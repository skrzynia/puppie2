package activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.wit.puppie2.databinding.RegisterLayoutBinding
import org.wit.puppie2.main.MainActivity
import timber.log.Timber

class RegisterActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var bind: RegisterLayoutBinding
    private lateinit var app: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = RegisterLayoutBinding.inflate(layoutInflater)

        app = application as MainActivity

        val username = bind.regUsername.text.toString()
        val password = bind.regPassword.text.toString()

        createAccount(username, password)


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