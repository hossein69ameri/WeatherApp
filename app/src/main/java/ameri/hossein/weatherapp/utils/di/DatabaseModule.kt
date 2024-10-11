package ameri.hossein.weatherapp.utils.di

import ameri.hossein.weatherapp.data.database.CitiesDatabase
import ameri.hossein.weatherapp.utils.CITIES_DATABASE
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, CitiesDatabase::class.java, CITIES_DATABASE
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(database: CitiesDatabase) = database.dao()
}