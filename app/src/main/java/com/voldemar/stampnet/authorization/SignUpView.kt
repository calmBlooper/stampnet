package com.voldemar.stampnet.authorization

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.voldemar.stampnet.Login
import com.voldemar.stampnet.Password
import com.voldemar.stampnet.utils.getActivity

@Composable
fun SignUpView(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var login by rememberSaveable { mutableStateOf("") }
        var password1 by rememberSaveable { mutableStateOf("") }
        var password2 by rememberSaveable { mutableStateOf("") }
        Text("Do you")
        Login(login) { login = it }
        Password(password1) { password1 = it }
        Password(password2) { password2 = it }
        CreateAccountButton(
            login = login, password = password1, enabled =
            login.isNotBlank() && password1.isNotBlank() && password1 == password2
        )
    }
}

@Composable
fun CreateAccountButton(
    login: String,
    password: String,
    enabled: Boolean
) {
    val context = LocalContext.current
    Button(onClick = {
        context.getActivity()?.let { activity ->
            AuthorizationManager.auth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        val user = AuthorizationManager.auth.currentUser
                        Log.d("Stuff", "createUserWithEmail:success")
                        Toast.makeText(
                            context, "Authentication successful, user: ${user}.",
                            Toast.LENGTH_SHORT
                        ).show()
                        //   updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Stuff", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            context, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        //    updateUI(null)
                    }
                }
        }

    }, enabled = enabled) {
        Text(text = "Create account")
    }
}

@Preview
@Composable
fun SignUpViewPreview() {
    SignUpView(navController = NavController(LocalContext.current))
}