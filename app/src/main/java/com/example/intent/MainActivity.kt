package com.example.intent

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            window.setDecorFitsSystemWindows(false)
        else
            window.setFlags(
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER,
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER
            )
        super.onCreate(savedInstanceState)
        setContent {
            // Calling the composable function
            // to display element and its contents
            MainContent()
        }
    }
    @Composable
    fun MainContent2() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1B1811)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Localized description",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { mainViewModel.focusFalse() }
            )
        }
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Seja bem vindo", color = Color.Black, fontSize = 40.sp)
                Button(
                    onClick = { mainViewModel.focusTrue() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff0f9d58)),
                    ){
                    Text(text = "Entrar", color = Color.White, fontSize = 20.sp)
                }


        }
        if (mainViewModel.focus)
        MyContent()
    }


    // Creating a composable
// function to display Top Bar
    @Composable
    fun MainContent() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("GFG | Main Activity", color = Color.White) },
                    backgroundColor = Color(0xff0f9d58)
                )
            },
            content = { MainContent2() }
        )
    }

    // Creating a composable function to
// create two Images and a spacer between them
// Calling this function as content in the above function
    @Composable
    fun MyContent() {
        val focusManager = LocalFocusManager.current

        // Fetching the Local Context
        val mContext = LocalContext.current

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Creating a Button that on-click
            // implements an Intent to go to SecondActivity
            LoginTextField(label = "E-mail", text = mainViewModel.text1, onChange = {
                mainViewModel.changeText1(it)
            }, keyboardType =  KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ), )
//            Spacer(modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//                .background(Color.Gray))
            LoginTextField(label = "Senha", text = mainViewModel.text2, onChange = {
                mainViewModel.changeText2(it)
            },keyboardType =  KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ), )
            Button(
                onClick = {
                    if (mainViewModel.validateLogin()) {
                        val intent = Intent(mContext, SecondActivity::class.java)
                        intent.putExtra("NAME",mainViewModel.text1)
                        mContext.startActivity(intent)
                    }
                    else Toast.makeText(mContext, R.string.ShowToast, Toast.LENGTH_LONG).show()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)),
            ) {
                Text("Entrar", color = Color.White)
            }
        }
    }

    // For displaying preview in
// the Android Studio IDE emulator
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MainContent()
    }

    @Composable
    fun LoginTextField(
        label: String,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardType: KeyboardOptions,
        keyboardActions: KeyboardActions,
        text: String,
        onChange: (String) -> Unit
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = text,
                onValueChange = onChange,
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent
                ),
                label = { Text(text = label) },
                keyboardActions = keyboardActions,
//                visualTransformation = visualTransformation,
                keyboardOptions = keyboardType,

            )

        }

    }
}