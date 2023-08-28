package com.slgi.slgiapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

@Entity(tableName = "EventsTable")
class EventEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "Description") val description: String,
    @ColumnInfo(name = "Image") val imageUrl: String,
    @ColumnInfo(name = "Participants") val participants: Int,
    @ColumnInfo(name = "User_Partakes") val userParticipation: Boolean,
    @ColumnInfo(name = "Time_Stamp") val dateAndTime: Timestamp,
    @ColumnInfo(name = "FireLeader") val fireLeader: String
)