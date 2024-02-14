package com.projects.moviemanager.network.models.content.common

data class WatchProvidersResponse(
    val id: Int,
    val results: Map<String, CountryProviderResponse>?
)

data class CountryProviderResponse(
    val flatrate: List<ProviderResponse>?
)

data class ProviderResponse(
    val logo_path: String?,
    val provider_name: String?
)
