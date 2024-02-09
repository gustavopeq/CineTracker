package com.projects.moviemanager.common.util

object Constants {

    const val API_KEY = "1d1b00098614494b1604f69cca5ec62d"
    const val BASE_URL_MOVIEDB = "https://api.themoviedb.org/3/"
    const val BASE_500_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    const val BASE_300_IMAGE_URL = "https://image.tmdb.org/t/p/w300"
    const val BASE_ORIGINAL_IMAGE_URL = "https://image.tmdb.org/t/p/original"
    const val MISSING_PROFILE_PICTURE_URL = "https://www.kindpng.com/picc/m/144-1447559_profile-icon-missing-profile-picture-icon-hd-png.png"
    const val BASE_YOUTUBE_THUMBAIL_URL = "https://img.youtube.com/vi/"
    const val YOUTUBE_THUMBAIL_RESOLUTION = "/maxresdefault.jpg"

    // This index is the position where the video Key will have to be inserted on the url above
    const val YOUTUBE_THUMBNAIL_URL_STRING_INDEX = 27

    const val BASE_URL_YOUTUBE_VIDEO = "http://www.youtube.com/watch?v="

    const val PAGE_SIZE = 20
    const val PREFETCH_DISTANCE = PAGE_SIZE * 2
    const val ENGLISH_LANGUAGE_CODE = "en-US"

    const val UNSELECTED_OPTION_INDEX = -1
}
