package com.trackr.trackr_app.ui.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.trackr.trackr_app.ui.shared.InputWidget
import com.trackr.trackr_app.ui.shared.InteractiveDropdownWidget
import com.trackr.trackr_app.ui.theme.Rubik
import com.trackr.trackr_app.viewmodels.EditScreenViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*


@Composable
fun EditScreenActivity(
    viewModel: EditScreenViewModel,
    nav: NavHostController,
) {
    val eventDate by viewModel.eventDate
    val chosenReminder by viewModel.chosenReminder
    val eventName by viewModel.eventName
    val personName by viewModel.personName

    Scaffold(
    ) {
        Column(
            Modifier.padding(20.dp)
        ) {
            Text("$personName's $eventName",
                fontFamily = Rubik,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text( "on " +
                    eventDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()) + ", " +
                    eventDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " +
                    eventDate.dayOfMonth,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 5.dp, bottom = 25.dp)
            )
            Text(text = "Type of event:", Modifier.padding(bottom = 5.dp), fontWeight = FontWeight.Bold)
            Row(
                Modifier
                    .selectableGroup()
                    .padding(top = 5.dp, bottom = 20.dp)) {
                RadioButton(
                    selected = eventName == "Birthday",
                    onClick = { viewModel.editEventName("Birthday") }
                )
                Text(text = "Birthday", Modifier.padding(start = 5.dp, end = 20.dp))
                RadioButton(
                    selected = eventName == "Anniversary",
                    onClick = { viewModel.editEventName("Anniversary") }
                )
                Text(text = "Anniversary", Modifier.padding(start = 5.dp))
            }
            InputWidget(title = "Date", widgets = listOf(
                {InteractiveDropdownWidget(
                    setter = {month: String -> viewModel.changeMonth(month)},
                    getter = {eventDate.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())},
                    options = viewModel.getMonths()
                )
                },
                {InteractiveDropdownWidget(
                    setter = {day: Int -> viewModel.changeDay(day)},
                    getter = {eventDate.dayOfMonth},
                    options = (1..eventDate.lengthOfMonth()).map{it}
                )
                }
            )
            )
            InputWidget(title = "Remind Me") {
                InteractiveDropdownWidget(
                    setter = {reminder: String -> viewModel.changeReminderInterval(reminder)},
                    getter = {chosenReminder},
                    options = viewModel.getReminderIntervals()
                )
            }
            Button(
                onClick = {
                    viewModel.editEvent()
                    nav.popBackStack()
                },
                Modifier.padding(top = 20.dp),
            ) {
                Text("Save Changes")
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Edit Event",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
            Button(
                onClick = {
                    viewModel.deleteEvent()
                    nav.popBackStack()
                },
                modifier = Modifier.padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(text = "DELETE event",
                    Modifier.padding(bottom = 5.dp),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}