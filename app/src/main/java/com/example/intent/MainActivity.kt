package com.example.intent

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            window.setDecorFitsSystemWindows(false)
        else
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        super.onCreate(savedInstanceState)
        setContent {
            // Calling the composable function
            // to display element and its contents
            MainContent(MainViewModel())
        }
    }
}

//    lateinit var mainViewModel : MainViewModel
var arrangement: Arrangement.Vertical = Arrangement.Bottom

@Composable
fun MainContent2(mainViewModel: MainViewModel) {
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
        Text(
            text = "Seja bem vindo\nSeja bem vindo\nSeja bem vindo\nSeja bem vindo\nSeja bem vindo\n",
            color = Color.Black,
            fontSize = 40.sp
        )
        Button(
            onClick = { mainViewModel.focusTrue() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff0f9d58)),
        ) {
            Text(text = "Entrar", color = Color.White, fontSize = 20.sp)
        }


    }
    if (mainViewModel.expand)
        MyContent(MainViewModel())
}


// Creating a composable
// function to display Top Bar
@Composable
fun MainContent(mainViewModel: MainViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GFG | Main Activity", color = Color.White) },
                backgroundColor = Color(0xff0f9d58)
            )
        },
        content = { MainContent2(mainViewModel) }
    )
}

// Creating a composable function to
// create two Images and a spacer between them
// Calling this function as content in the above function
@Composable
fun MyContent(mainViewModel: MainViewModel) {
    val focusManager = LocalFocusManager.current

    if (mainViewModel.focus || mainViewModel.focus1) arrangement =
        Arrangement.Top else if (!mainViewModel.focus && !mainViewModel.focus1) arrangement = Arrangement.Bottom


    // Fetching the Local Context
    val mContext = LocalContext.current
    Column(Modifier.fillMaxSize(), verticalArrangement = arrangement) {
        Card(
            backgroundColor = (Color.White),
            shape = RoundedCornerShape(20.dp),
            elevation = 16.dp,
            modifier = Modifier
                .border(
                    BorderStroke(0.dp, Color.White),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(top = 30.dp)
                .fillMaxWidth()
                .height(430.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Creating a Button that on-click
                // implements an Intent to go to SecondActivity
                LoginTextField(
                    label = "E-mail", text = mainViewModel.text1,
                    onChange = {
                        mainViewModel.changeText1(it)
                    },
                    keyboardType = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    mainViewModel = MainViewModel(),
                    modifier = Modifier
                        .onFocusEvent { state ->
                            mainViewModel.focusChange(state.isFocused)
                        }
                        .fillMaxWidth()
                        .padding(16.dp),
                )
                LoginTextField(
                    label = "Senha", text = mainViewModel.text2,
                    onChange = {
                        mainViewModel.changeText2(it)
                    },
                    keyboardType = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    mainViewModel = MainViewModel(),
                    modifier = Modifier
                        .onFocusEvent { state ->
                            mainViewModel.focusChange1(state.isFocused)
                        }
                        .fillMaxWidth()
                        .padding(16.dp),
                )
                Button(
                    onClick = {
                        if (mainViewModel.validateLogin()) {
                            val intent = Intent(mContext, SecondActivity::class.java)
                            intent.putExtra("NAME", mainViewModel.text1)
                            mContext.startActivity(intent)
                        } else Toast.makeText(mContext, R.string.ShowToast, Toast.LENGTH_LONG)
                            .show()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)),
                ) {
                    Text("Entrar", color = Color.White)
                }
            }
        }

    }


}

// For displaying preview in
// the Android Studio IDE emulator
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainContent(MainViewModel())
}

@Composable
fun LoginTextField(
    label: String,
    keyboardType: KeyboardOptions,
    keyboardActions: KeyboardActions,
    text: String,
    onChange: (String) -> Unit,
    mainViewModel: MainViewModel,
    modifier: Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = text,
            onValueChange = onChange,
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent
            ),
            label = { Text(text = label) },
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardType,

            )

    }

}
