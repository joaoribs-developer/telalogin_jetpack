package com.example.intent

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.intent.ui.theme.ui.theme.IntentTheme

class SecondActivity : ComponentActivity() {
    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Calling the composable function
            // to display element and its contents
            MainContent2()
        }


    }


    // Creating a composable
// function to display Top Bar
    @Composable
    fun MainContent2() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("GFG | Second Activity", color = Color.White) },
                    backgroundColor = Color(0xff0f9d58)
                )
            },
            content = { MyContent2() }
        )
    }

    // Creating a composable function to
// create two Images and a spacer between them
// Calling this function as content in the above function
    @Composable
    fun MyContent2() {
        val intent = getIntent()
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Creating a Text
            Text("Hello ${intent.getStringExtra("NAME")}", fontSize = 50.sp)
        }
    }

    // For displaying preview in
// the Android Studio IDE emulator
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview2() {
        MainContent2()
    }
}