package ameri.hossein.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import ameri.hossein.weatherapp.utils.CITIES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {
    @Insert(onConflict = REPLACE)
    suspend fun saveCity(entity: CitiesEntity)

    @Delete
    suspend fun deleteCity(entity: CitiesEntity)

    @Query("SELECT * FROM $CITIES_TABLE")
    fun loadCities(): Flow<List<CitiesEntity>>
}