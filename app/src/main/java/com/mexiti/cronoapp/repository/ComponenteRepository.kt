package com.mexiti.cronoapp.repository

import com.mexiti.cronoapp.model.Componente
import com.mexiti.cronoapp.room.ComponenteDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ComponenteRepository @Inject constructor(private val componenteDatabaseDao: ComponenteDatabaseDao) {
    suspend fun addComponente(componente: Componente) = componenteDatabaseDao.insert(componente)

    suspend fun updateComponente(componente: Componente) = componenteDatabaseDao.update(componente)

    suspend fun deleteComponente(componente: Componente) = componenteDatabaseDao.delete(componente)

    fun getAllComponentes(): Flow<List<Componente>> = componenteDatabaseDao
        .getComponentes()
        .flowOn(Dispatchers.IO)
        .conflate()

    fun getComponenteById(id: Long): Flow<Componente> = componenteDatabaseDao
        .getComponenteById(id)
        .flowOn(Dispatchers.IO)
        .conflate()
}

