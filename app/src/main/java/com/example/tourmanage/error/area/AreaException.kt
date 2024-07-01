package com.example.tourmanage.error.area

sealed class TourMangeException(message: String): Exception(message) {
    class AreaException(message: String): Exception(message)

    class AreaNullException(message: String): Exception(message)

    class FestivalNullException(message: String): Exception(message)
}




