package ameri.hossein.weatherapp.data.database

import ameri.hossein.weatherapp.utils.CITIES_TABLE
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CITIES_TABLE)
data class CitiesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String? = null,
    var lat: Double? = null,
    var lon: Double? = null
)