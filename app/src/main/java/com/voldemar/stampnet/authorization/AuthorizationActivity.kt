package com.voldemar.stampnet.authorization

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

class AuthorizationActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "signin") {
                composable("signin") { LoginView(navController) }
                composable("signup") { SignUpView(navController = navController) }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //reload()  - wtf???
        }
    }

    // @Preview
    @Composable
    fun LoginView(navController: NavController) {
        var login by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Login(login) { login = it }
            Password(password){password=it}
            SignInButton(login,password)
            SignUpButton(navController)
        }
    }

    @Preview
    @Composable
    fun LoginViewPreview() {
        val navController = rememberNavController()
        LoginView(navController = navController)
    }

    @Composable
    fun SignUpButton(navController: NavController) {
        Button(onClick = {
                         navController.navigate("signup")
                         }, modifier = Modifier.background(color = Color.Red)) {
            Text(text = "Sign up")
        }
    }

    @Composable
    fun SignInButton(login:String,password: String) {
        Button(onClick = {
            auth.signInWithEmailAndPassword(login, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        //  updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        //    updateUI(null)
                    }
                }

        }) {
            Text(text = "Log in")
        }
    }



    companion object {
        const val TAG = "Authorization"
    }

}

@Composable
fun Login(value: String,onValueChange: (String)->Unit) {
    TextField(
        value =value,
        label = { Text(text = "Enter your username or email") },
        onValueChange = onValueChange)
}

@Composable
fun Password(value: String,onValueChange: (String)->Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    TextField(value = value,
        label = { Text(text = "Enter your password") },
        onValueChange = onValueChange,
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        }
    )
}