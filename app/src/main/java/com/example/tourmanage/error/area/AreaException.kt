package com.example.tourmanage.error.area

sealed class TourMangeException(message: String): Exception(message) {
    class AreaException(message: String) : Exception(message)
    class AreaNullException(message: String) : Exception(message)
    class GetFestivalException(message: String) : Exception(message)
    class LocationBasedNullException(message: String) : Exception(message)
    class DetailInfoNullException(message: String) : Exception(message)
    class DetailCommonInfoNullException(message: String) : Exception(message)
    class DetailImageInfoNullException(message: String) : Exception(message)
    class SearchInfoNullException(message: String) : Exception(message)
    class StayInfoNullException(message: String) : Exception(message)
    class AddFavorException(message: String) : Exception(message)
    class GetFavorException(message: String) : Exception(message)
}
