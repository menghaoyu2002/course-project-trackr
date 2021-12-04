package com.trackr.trackr_app.ui.person_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Event
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.trackr.trackr_app.R
import com.trackr.trackr_app.ui.shared.EventList
import com.trackr.trackr_app.ui.shared.InputWidget
import com.trackr.trackr_app.ui.shared.PersonList
import com.trackr.trackr_app.ui.theme.Rubik
import com.trackr.trackr_app.viewmodels.AllPersonsScreenViewModel
import com.trackr.trackr_app.viewmodels.PersonDetailsScreenViewModel
import com.trackr.trackr_app.viewmodels.PersonOutput
import com.trackr.trackr_app.viewmodels.TrackrEventOutput

@Composable
fun PersonDetailsScreenActivity(
        viewModel: PersonDetailsScreenViewModel,
        navController: NavHostController
) {
    val confirmDelete = remember {mutableStateOf(false)}
    val viewMode = remember {mutableStateOf(true)}
    Scaffold(
            backgroundColor = MaterialTheme.colors.background,
            bottomBar = {
                BottomBar(viewModel, confirmDelete, viewMode, navController)
            }
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            if (viewMode.value) {
                ViewMode(
                    viewModel,
                    navController
                )
            } else {
                EditMode(
                    viewModel
                )
            }
        }
    }
}


@Composable
fun ViewMode(
    viewModel: PersonDetailsScreenViewModel,
    navController: NavHostController
) {
    val eventList by viewModel.personEvents.observeAsState(listOf())
    Column {
        Text("First Name:",
            fontFamily = Rubik,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            viewModel.firstName.value,
            fontFamily = Rubik,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 2.dp, bottom = 10.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text("Last Name:",
            fontFamily = Rubik,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            viewModel.lastName.value,
            fontFamily = Rubik,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 2.dp, bottom = 25.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        EventFeed(
            Modifier
                .padding(0.dp, 15.dp)
                .weight(2f),
            viewModel.firstName.value + "'s Events:",
            eventList,
            navController
        )
    }
}

@Composable
fun EditMode(
    viewModel: PersonDetailsScreenViewModel
) {
    Column {
        InputWidget(
            title = "First Name",
            widgets = listOf {
                TextField(
                    value = viewModel.firstName.value,
                    onValueChange = { viewModel.editFirstName(it) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = MaterialTheme.colors.onBackground,
                    ),
                    placeholder = { Text("First Name", fontFamily = Rubik) }
                )
            }
        )
        InputWidget(
            title = "Last Name",
            widgets = listOf {
                TextField(
                    value = viewModel.lastName.value,
                    onValueChange = { viewModel.editLastName(it) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = MaterialTheme.colors.onBackground,
                    ),
                    placeholder = { Text("Last Name", fontFamily = Rubik) }
                )
            }
        )
    }
}


@Composable
fun EventFeed(modifier: Modifier,
               title: String,
               events: List<TrackrEventOutput>,
               nav: NavHostController) {
    Column(
        modifier = modifier,
    ) {
        Text(
            title,
            fontFamily = Rubik,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 5.dp),
        )
        if (events.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.no_events),
                    fontFamily = Rubik,
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            EventList(
                events,
                Modifier.fillMaxWidth(),
                nav,
            )
        }
    }
}

@Composable
fun BottomBar(viewModel: PersonDetailsScreenViewModel,
              confirmDelete: MutableState<Boolean>,
              viewMode: MutableState<Boolean>,
              navController: NavHostController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
    ) {

        BottomNavigationItem(
            selected = false,
            icon = { Icon(
                Icons.Filled.Edit,
                "Edit Person",
                tint = MaterialTheme.colors.onBackground
            )
            },
            label = {Text("Edit Person", fontFamily = Rubik)},
            onClick = {viewModel.editPerson()
                viewMode.value = !viewMode.value}
        )
        BottomNavigationItem(
            selected = false,
            icon = { Icon(
                    Icons.Filled.Delete,
                    "Delete Person",
                    tint = MaterialTheme.colors.onBackground,
                )
            },
            label = { Text(text = "Delete Person") },
            onClick = {confirmDelete.value = true}
        )
    }
    if (confirmDelete.value) {
        AlertDialog(
            onDismissRequest = {
                confirmDelete.value = false
            },
            title = {
                Text(text = "Are You Sure You Want to Delete This Person?")
            },
            text = {
                Text(text = "This will delete all events associated with this person")
            },
            confirmButton = {
                Button(
                    onClick = {
                        confirmDelete.value = false
                        viewModel.deletePerson()
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text("Delete Person")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        confirmDelete.value = false
                    }) {
                    Text("Go Back")
                }
            }
        )
    }
}


