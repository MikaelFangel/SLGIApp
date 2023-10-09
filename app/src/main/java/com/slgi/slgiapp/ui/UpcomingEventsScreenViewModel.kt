package com.slgi.slgiapp.ui

import com.google.firebase.Timestamp
import com.slgi.slgiapp.data.Event
import com.slgi.slgiapp.data.EventRepository
import com.slgi.slgiapp.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

class UpcomingEventsScreenViewModel(
    private val eventRepository: EventRepository
) {
    val events = eventRepository.events
    private val _uiState = MutableStateFlow(EventScreenState())
    val uiState = _uiState.asStateFlow()

    suspend fun toggleParticipation(id: String) = eventRepository.toggleParticipation(id)
    fun getParticipantFlow(eventId: String): Flow<List<User>>? =
        eventRepository.getParticipantFlow(eventId)

    fun getParticipants(eventId: String) = eventRepository.getParticipants(eventId)
    fun createEvent() {
        val eventState = uiState.value
        val addedTime: Instant = Instant.ofEpochSecond(eventState.newEventDate.seconds)
                                .plus(eventState.newEventHours!!.toLong(), ChronoUnit.HOURS)
                                .plus(eventState.newEventMinutes!!.toLong(), ChronoUnit.MINUTES)
        val newtimestamp = Timestamp(addedTime.epochSecond, 0)
        eventRepository.createEvent(
            Event(
                name = eventState.newEventName,
                description = eventState.newEventDescription,
                imageUrl = eventState.newEventImageURL,
                fireleader = "",
                dateAndTime = newtimestamp
            )
        )
    }

    fun showCreateDialog(){
        _uiState.update { currentState ->
            currentState.copy(
                displayCreateDialog = true
            )
        }
    }
    fun showDateDialog(){
        _uiState.update { currentState ->
            currentState.copy(
                displayDateDialog = true
            )
        }
    }
    fun onEventNameChange(name: String){
        _uiState.update { currentState ->
            currentState.copy(
                newEventName = name
            )
        }
    }
    fun onEventDescChange(desc: String){
        _uiState.update { currentState ->
            currentState.copy(
                newEventDescription = desc
            )
        }
    }
    fun onEventImgUrlChange(url: String){
        _uiState.update { currentState ->
            currentState.copy(
                newEventImageURL = url
            )
        }
    }
    fun dismissCreateDialog(){
        _uiState.update { currentState ->
            currentState.copy(
                displayCreateDialog = false,
                displayDateDialog = false,
                displayTimeDialog = false,
                newEventName = "",
                newEventDescription = "",
                newEventImageURL = "",
                newEventFireLeader = "",
                newEventHours = null,
                newEventMinutes = null,
                newEventDate = Timestamp.now()
            )
        }
    }
    fun dismissDateDialog(){
        _uiState.update { currentState ->
            currentState.copy(
                displayDateDialog = false
            )
        }
    }
    fun dismissTimeDialog(){
        _uiState.update { currentState ->
            currentState.copy(
                displayTimeDialog = false
            )
        }
    }
    fun setNewEventDate(time: Long){
        _uiState.update { currentState ->
            currentState.copy(
                newEventDate = Timestamp(Date(time))
            )
        }
    }
    fun showTimeDialog(){
        _uiState.update { currentState ->
            currentState.copy(
                displayTimeDialog = true
            )
        }
    }

    fun setNewEventTime(hour: Int, minute: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                newEventHours = hour,
                newEventMinutes = minute
            )
        }
    }
}