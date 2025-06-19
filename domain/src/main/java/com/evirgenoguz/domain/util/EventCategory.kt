package com.evirgenoguz.domain.util

enum class EventCategory(val value: String, val imageUrl: String) {
    SPORT(
        "SPORT_EVENT",
        "https://firebasestorage.googleapis.com/v0/b/togather-6bdca.firebasestorage.app/o/event_category%2Fsport_event.png?alt=media"
    ),
    DAILY(
        "DAILY_EVENT",
        "https://firebasestorage.googleapis.com/v0/b/togather-6bdca.firebasestorage.app/o/event_category%2Fother.png?alt=media"
    ),
    MEETING(
        "MEETING_EVENT",
        "https://firebasestorage.googleapis.com/v0/b/togather-6bdca.firebasestorage.app/o/event_category%2Fmeeting_event.png?alt=media&"
    ),
    BUSINESS(
        "BUSINESS_EVENT",
        "https://firebasestorage.googleapis.com/v0/b/togather-6bdca.firebasestorage.app/o/event_category%2Fbusiness_event.png?alt=media"
    ),
    OTHER(
        "OTHER",
        "https://firebasestorage.googleapis.com/v0/b/togather-6bdca.firebasestorage.app/o/event_category%2Fother.png?alt=media"
    );

    companion object {
        fun getValue(categoryString: String): String {
            return when (categoryString) {
                "SPORT" -> SPORT.value
                "DAILY" -> DAILY.value
                "MEETING" -> MEETING.value
                else -> OTHER.value
            }
        }

        fun getImageUrl(categoryString: String?): String {
            return when (categoryString) {
                "SPORT_EVENT" -> SPORT.imageUrl
                "DAILY_EVENT" -> DAILY.imageUrl
                "MEETING_EVENT" -> MEETING.imageUrl
                else -> OTHER.imageUrl
            }
        }
    }
}