package com.voldemar.stampnet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.voldemar.stampnet.authorization.AuthorizationActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    private var timer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }

    @Preview
    @Composable
    fun MainContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFF6A00)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                contentDescription = ""
            )
        }
    }

    override fun onResume() {
        super.onResume()
        timer?.cancel()
        timer = object : CountDownTimer(2000, 2000) {
            override fun onTick(p0: Long) {
                TODO("Not yet implemented")
            }

            override fun onFinish() {
                passToLoginPage()
            }

        }
        timer?.start()
    }


    private fun passToLoginPage() {
        val intent = Intent(this, AuthorizationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }


}