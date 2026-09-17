package com.pes.todoapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


// parent composable - stateful composable
// parent holding state - State Hoisting
@Composable
fun AddItemScreen(modifier: Modifier = Modifier) {

    println("AddItemScreen: Composition started...")

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // executed only if key changes
        // asynchronous task  - coroutine - executed by different thread
        // fetch data from REST API
        println("AddItemScreen: starting to fetch data- launchedEffect")
    }

    //
    DisposableEffect(Unit) {
        //registration
        println("AddItemScreen: DisposableEffect - register ")

        onDispose {
            // executed after the composition is left
            // unregistration
            println("AddItemScreen: unregistration")
        }
    }

    // state - observable - recomposition is triggered when the value is changed
    var title by remember {
        mutableStateOf("")
    }

    // saves the state across configuration changes
    var selectedPriority by rememberSaveable {
        mutableStateOf("")
    }

    var tobeNotified by remember {
        mutableStateOf(false)
    }

    //var title = "" // non-observable
    var priorities = arrayOf("High", "Medium", "Low")

    val ctx = LocalContext.current

    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.Gray),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text("Add New ToDO Item")
        TextField(value = title, onValueChange = {
            // it - holds the current text
            title = it
        }, maxLines = 4, label = {
            Text ("ToDO Title")
        })

        PrioritySelector(priorities,
            selectedPriority = selectedPriority,
            onSelection = {
                selectedPriority = it
            }, modifier = Modifier
                .background(Color.Yellow)
                .padding(20.dp)
            )

        NotificationSection(tobeNotified, onSelection = {
            tobeNotified = it
        })

        Button( enabled = title.isNotEmpty(),
            onClick = {

                Toast.makeText(ctx, "Adding Item $title",
                    Toast.LENGTH_LONG).show()

                // launching coroutine manually
                scope.launch {
                    // async task - send data to API
                    println("AddItemScreen: button click started the task")
                }
        }) {
            Text("Add Item")
        }
    }
}


@Composable
fun NotificationSection(selection: Boolean,
                        onSelection: (Boolean) -> Unit,
                        modifier: Modifier = Modifier) {

    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically) {
        Switch(checked = selection, onCheckedChange = {
            onSelection(it)
        })
        Text("Reminde Me")
    }
}

// custom composables - UI
// 1. modifier parameter - default argument
// 2. apply modifier to the first composable inside function
// Child composable - stateless composable
@Composable
fun PrioritySelector(priorities: Array<String>,
                     selectedPriority: String,
                     onSelection: (String) -> Unit,
                        modifier: Modifier = Modifier) {
    Column(modifier = modifier) {

        Text("Select Priority")
        for (p in priorities) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedPriority == p,
                    onClick = {
                        onSelection(p)
                    })

                Text(p)
            }
        }
    }
}

@Preview(showBackground = true,
    showSystemUi = true)
@Composable
private fun PreviewAdd() {
    AddItemScreen()
}