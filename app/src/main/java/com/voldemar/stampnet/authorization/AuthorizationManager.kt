package com.voldemar.stampnet.authorization

import com.google.firebase.auth.FirebaseAuth

object AuthorizationManager {
     val auth: FirebaseAuth = FirebaseAuth.getInstance()
}