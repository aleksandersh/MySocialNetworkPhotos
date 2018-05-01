package io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model

data class ZeroScreenData(
    val title: String,
    val subtitle: String? = null,
    val retry: Boolean = false,
    val progress: Boolean = false
)