package com.voldemar.stampnet.authorization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

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
    }
}