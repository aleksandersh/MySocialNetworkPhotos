package io.github.aleksandersh.mysocialnetworkphotos.domain.repository

interface PhotoRepository {

    fun loadPhotoPreview(url: String): ByteArray

    fun loadPhoto(url: String): ByteArray
}