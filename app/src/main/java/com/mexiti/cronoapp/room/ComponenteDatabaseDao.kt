package com.mexiti.cronoapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mexiti.cronoapp.model.Componente
import kotlinx.coroutines.flow.Flow

@Dao
interface ComponenteDatabaseDao {
    // Crud
    @Query("SELECT * FROM componentes")
    fun getComponentes(): Flow<List<Componente>>

    @Query("SELECT * FROM componentes WHERE id=:id")
    fun getComponenteById(id: Long): Flow<Componente>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(componente: Componente)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(componente: Componente)

    @Delete
    suspend fun delete(componente: Componente)
}