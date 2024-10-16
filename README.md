# WeatherApp
This Android project, built using Kotlin and the [OpenWeather API](https://openweathermap.org/) , provides users with detailed weather information for a selected city. It displays current weather conditions, pollution levels, and a 5-day weather forecast. The app aims to offer a comprehensive view of weather and air quality in a simple interface.

# Screen Shot
<div align="center">
    <img src="https://github.com/user-attachments/assets/d4dbb466-4dfa-4818-910b-b7394782be79" data-canonical-src="https://gyazo.com/eb5c5741b6a9a16c692170a41a49c858.png" width="300" height="600" />
    <img src="https://github.com/user-attachments/assets/1ce7a992-88e5-4e2f-9e28-567a5dd929a0" data-canonical-src="https://gyazo.com/eb5c5741b6a9a16c692170a41a49c858.png" width="300" height="600" />
</div>

[w3.webm](https://github.com/user-attachments/assets/b98d6f12-6edd-42a0-929f-15aa07ecd164)

# Technologies

* MVVM Architecture
* Kotlin Coroutines
* Room Database
* Kotlin Flow
* LiveData
* Dynamic Query
* Single activity pattern
* REST API
* Safe Args
* Kotlin Parcelize
* Base Activity ,Fragment
* Dependency injection

# Built with
[Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.

[Room](https://developer.android.com/training/data-storage/room) - Save data in a local database using Room.

[Font Calligraphy](https://github.com/InflationX/ViewPump) - View Inflation you can intercept.

[Retrofit](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/) - RESTful API and networking client.

[Hilt](https://dagger.dev/hilt/) - Dependency injection.

[ViewBinding](https://developer.android.com/topic/libraries/view-binding) - View binding is a feature that allows you to more easily write code that interacts with views.

[Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - A collections of libraries that help you design robust, testable, and maintainable apps.

[ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel) - UI related data holder, lifecycle aware.

[Navigation component](https://developer.android.com/guide/navigation) - Fragment routing handler.

[Coroutines](https://developer.android.com/kotlin/coroutines) - Concurrency design pattern for asynchronous programming.

[Flow](https://developer.android.com/kotlin/flow) - Stream of value that returns from suspend function.

[Coil](https://github.com/coil-kt/coil) - Image loading.

[DynamicSize](https://github.com/MrNouri/DynamicSizes) - New units of measure for support all of screen devices.

# Architectures

![android-mvvm-architecture](https://github.com/user-attachments/assets/9d9a470c-b022-4b78-94a1-6c60efa6fa24)

* **View**:  The purpose of this layer is to inform the ViewModel about the user’s action. This layer observes the ViewModel and does not contain any kind of application logic.
  
* **ViewModel**: It exposes those data streams which are relevant to the View. Moreover, it serves as a link between the Model and the View.

* **Model**: This layer is responsible for the abstraction of the data sources. Model and ViewModel work together to get and save the data.

# Contact
Have a project? DM me at

hossein.arabameri69@gmail.com

# Acknowledgments
Special thanks to [Mr Mohammad Nouri](https://github.com/MrNouri) for providing the course that helped me.

