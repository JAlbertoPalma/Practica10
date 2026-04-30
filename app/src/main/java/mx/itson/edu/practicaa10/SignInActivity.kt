package mx.itson.edu.practicaa10

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import mx.itson.edu.practicaa10.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SignInActivity"
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signInAppCompatButton.setOnClickListener {
            val mEmail = binding.emailEditText.text.toString()
            val mPassword = binding.passwordEditText.text.toString()

            when {
                mEmail.isEmpty() || mPassword.isEmpty() -> {
                    Toast.makeText(
                        baseContext,
                        "Mail o password incorrectos.",  // ← sin "text ="
                        Toast.LENGTH_SHORT               // ← sin "duration ="
                    ).show()
                }
                else -> {
                    signIn(mEmail, mPassword)
                }
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    reload()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",  // ← sin "text ="
                        Toast.LENGTH_SHORT         // ← sin "duration ="
                    ).show()
                }
            }
    }

    private fun reload() {
        val intent = Intent(this, MainActivity::class.java)  // ← sin "packageContext =" ni "cls ="
        startActivity(intent)
    }
}