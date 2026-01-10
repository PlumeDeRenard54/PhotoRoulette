/*package com.example.photoroulette.Vue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.photoroulette.Vue.theme.PhotoRouletteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            PhotoRouletteTheme {
                PhotoRouletteApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun PhotoRouletteApp() {
    //TODO
    //NsdManager
    var user by remember { mutableStateOf<User?>(null) }
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        when (currentDestination){
        AppDestinations.PROFILE -> {
            if (user == null) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LogIn(
                        modifier = innerPadding,
                        onLogin = { newUser -> user = newUser }
                    )
                }
            } else {
                Scaffold { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Text("Welcome, ${user?.name}!")
                        Text("Email: ${user?.email}")
                    }
                }
            }
        }

        AppDestinations.HOME -> {
           Scaffold() { innerPadding ->
               Home(innerPadding,user)
           }
        }

        AppDestinations.FAVORITES -> {
            Scaffold() { innerPadding ->
                Greeting(Modifier.padding(innerPadding))
            }
        }


        }

    }
}



enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Home", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    PROFILE("Profile", Icons.Default.AccountBox),
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Text(
        text = "TODO",
        modifier = modifier
    )
}

@Composable
fun Home(modifier: PaddingValues,user: User?){
    if (user != null) {
        Column(modifier = Modifier.padding(modifier)) {
            Text("Welcome : " + user.name)
            Text("Your email : " + user.email)
        }
    }else{
        Text(modifier = Modifier.padding((modifier)), text = "Please log in")
    }
}

@Composable
fun LogIn(modifier: PaddingValues, onLogin: (User) -> Unit) {
    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(modifier)) {
        TextField(
            label = { Text("UserName") },
            onValueChange = { userName = it },
            value = userName
        )
        TextField(
            label = { Text("Email") },
            onValueChange = { userEmail = it },
            value = userEmail
        )
        Button(onClick = {
            val newUser = User(userName, userEmail)
            onLogin(newUser)
        }) {
            Text("Register")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhotoRouletteTheme {

    }
}*/
