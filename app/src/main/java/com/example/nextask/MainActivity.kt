package com.example.nextask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nextask.ui.login.LoginScreen
import com.example.nextask.ui.registration.RegisterScreen
import com.example.nextask.ui.theme.NexTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NexTaskTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onLoginClick = { username, password ->

                            println("Logging in with: $username and $password")
                        },
                        onSignUpClick = {}
                    )
                }
            }
        }
    }
}



/*@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    //preview
    NexTaskTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RegisterScreen(
                onSignUpClick = { username, email, password ->
                    // function
                },
                onSigninClick = {},
            )
        }
    }
}*/

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    //preview
    NexTaskTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginScreen(
                onLoginClick = { username, password ->
                    // function
                },
                onSignUpClick = {},
            )
        }
    }
}
