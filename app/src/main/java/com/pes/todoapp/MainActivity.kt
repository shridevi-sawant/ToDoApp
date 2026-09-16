package com.pes.todoapp


import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.pes.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {

    val TAG = "MainActivity"

    // one time execution
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        enableEdgeToEdge()
        // setContent - API - sets UI content for the screen/activity
        setContent {
            // Step 1 - Creating NavHostController
            val navC = rememberNavController()

            ToDoAppTheme {

                Scaffold(modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            // navigate to 'addTodo'
                            navC.navigate("addTodo")
                        }) {
                            Icon(Icons.Default.Add,
                                contentDescription = null)
                        }
                    },
                    bottomBar = {
                        MyBottomAppBar(navController = navC)
                    },
                    topBar = {
                        MyTopAppBar(navController = navC)
                             },) { innerPadding ->

                    NavHost(navC,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)) {
                        // create Navigation graph - list of possible destination
                    // screens to be navigated
                        composable(route = "home") {
                            HomeScreen()
                        }

                        composable(route = "addTodo") {
                            AddItemScreen()
                        }

                        composable(route = "list") {
                            ToDoListScreen()
                        }
                    }

                }
            }
        }
    }

    // lifecycle method - automatically executed by ActivityManager
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    // save your data in onPause
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {

        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

} // Activity class completed


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(modifier: Modifier = Modifier,
                navController: NavHostController) {
    TopAppBar(title = { Text("ToDoApp") },
          actions = {
              IconButton(onClick = {}) {
                  Icon(Icons.Default.Person,
                      contentDescription = "Profile")
              }
          },
        navigationIcon = {
            IconButton(onClick = {
                // pop to the previous screen
                navController.popBackStack()
            }) {
                Icon(Icons.Default.ArrowBackIosNew,
                    "Back")
            }
        })
}

@Composable
fun MyBottomAppBar(modifier: Modifier = Modifier,
                   navController: NavHostController) {

    BottomAppBar() {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            IconButton(onClick = {
                navController.navigate("home")
            }) {
                Icon(Icons.Default.Home,
                    contentDescription = "Home")
            }
            IconButton(onClick = {
                // navigate to 'list' via navController
                navController.navigate(route = "list")
            }) {
                Icon(Icons.Default.FilterList,
                    contentDescription = "TodoList")
            }
            IconButton(onClick = {
                // navigate to 'addTodo' - pushes to backstack
                navController.navigate("addTodo")
            }) {
                Icon(Icons.Default.AddBox,
                    contentDescription = "Add New ToDo")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        color = Color.Blue,
        fontSize = 24.sp,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoAppTheme {
        Greeting("Demo")

    }
}

// no return, starts with Capital letter,
// can get re-executed - recomposition
@Composable
fun ImageDemo(){

    Image(painter = painterResource(R.drawable.ic_launcher_foreground),
        "")
}

@Composable
fun ButtonDemo(){
    // Button, OutlinedButton, ElevatedButton, IconButton
    ElevatedButton(onClick = {

    }) {
        Text("Click ME")
    }
}

@Preview
@Composable
fun PreviewImage(){
    //ImageDemo()
    ButtonDemo()
}