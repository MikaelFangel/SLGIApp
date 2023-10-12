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
                imageUrl = if (eventState.newEventImageURL != "") eventState.newEventImageURL else getRandomImageUrl(),
                fireleader = eventState.newEventFireLeader,
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

    fun onFireLeaderChange(fireleader: String){
        _uiState.update { currentState ->
            currentState.copy(
                newEventFireLeader = fireleader
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
    
    private fun getRandomImageUrl(): String {
        val urls = listOf(
            "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1964&q=80",
            "https://images.unsplash.com/photo-1574169208507-84376144848b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2079&q=80",
            "https://images.unsplash.com/photo-1563089145-599997674d42?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
            "https://images.unsplash.com/photo-1567095761054-7a02e69e5c43?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1974&q=80",
            "https://images.unsplash.com/photo-1567359781514-3b964e2b04d6?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2068&q=80",
            "https://images.unsplash.com/photo-1557672172-298e090bd0f1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1974&q=80",
            "https://images.unsplash.com/photo-1604076913837-52ab5629fba9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1974&q=80",
            "https://images.unsplash.com/photo-1506259091721-347e791bab0f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80"
        )

        return urls.random()
    }
}