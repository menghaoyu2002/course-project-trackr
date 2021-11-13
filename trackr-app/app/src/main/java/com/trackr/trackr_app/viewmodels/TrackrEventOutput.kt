package com.trackr.trackr_app.viewmodels

import com.trackr.trackr_app.model.Person
import com.trackr.trackr_app.model.TrackrEvent
import java.time.LocalDate

class TrackrEventOutput(event: TrackrEvent, person: Person) {
    val id = event.id
    val personId = event.person_id
    val firstName = person.first_name
    val lastName = person.last_name
    val type = event.type
    val date: LocalDate = LocalDate.ofEpochDay(event.date)
    val reminderInterval = event.reminder_interval
    val repeatStrategy = event.repeat_strategy
}