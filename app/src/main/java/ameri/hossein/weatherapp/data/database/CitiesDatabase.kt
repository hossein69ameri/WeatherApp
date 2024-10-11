package ameri.hossein.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CitiesEntity::class], version = 2, exportSchema = false)
abstract class CitiesDatabase : RoomDatabase() {
    abstract fun dao(): CitiesDao
}