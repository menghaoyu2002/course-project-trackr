package com.trackr.trackr_app.viewmodels

import androidx.lifecycle.*
import com.trackr.trackr_app.model.Person
import com.trackr.trackr_app.model.TrackrEvent
import com.trackr.trackr_app.model.User
import com.trackr.trackr_app.repository.EventRepository
import com.trackr.trackr_app.repository.PersonRepository
import com.trackr.trackr_app.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val personRepository: PersonRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    val allEvents: LiveData<List<TrackrEvent>> = eventRepository.allEvents.asLiveData()

    // TODO: This doesn't make any sense to me, I've basically just copy-pasted the add method from AddScreenViewModel
    fun editEvent(data: List<Any>, index: Int) = viewModelScope.launch {
        val months = listOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
        )

        //TODO: Unhardcode
        userRepository.insert(User(id = "1", username = "yourmom"))

        personRepository.insert(Person(id = "2", user_id = "1", first_name = "My", last_name = "Mom"))

        val date = LocalDate.of(2020, 1, 1)
        eventRepository.insert(TrackrEvent("SomeID", "2", 0,
            date.toEpochDay(), 7, 0))
    }
}
