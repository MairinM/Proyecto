package com.mexiti.cronoapp.di

import android.content.Context
import androidx.room.Room
import com.mexiti.cronoapp.room.ComponenteDataBase
import com.mexiti.cronoapp.room.ComponenteDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesComponenteDao(componenteDataBase: ComponenteDataBase): ComponenteDatabaseDao {
        return componenteDataBase.componenteDao()
    }

    @Singleton
    @Provides
    fun providesComponenteDatabase(@ApplicationContext context: Context): ComponenteDataBase {
        return Room.databaseBuilder(
            context = context,
            ComponenteDataBase::class.java,
            name = "componentes_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}


