package com.projects.moviemanager.common.domain.models.content

import com.projects.moviemanager.network.models.content.common.ProviderResponse

data class StreamProvider(
    val providerName: String,
    val logoPath: String
)

fun ProviderResponse.toStreamProvider(): StreamProvider {
    return StreamProvider(
        providerName = this.provider_name.orEmpty(),
        logoPath = this.logo_path.orEmpty()
    )
}
