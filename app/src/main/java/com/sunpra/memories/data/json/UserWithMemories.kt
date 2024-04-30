package com.sunpra.memories.data.json

import androidx.room.Embedded
import androidx.room.ForeignKey
import androidx.room.Relation

data class UserWithMemories(
    @Embedded()
    val user: User,
    @Relation(
        entity = Memory::class,
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val memories: List<Memory>
)