package io.github.aleksandersh.mysocialnetworkphotos.domain.usecase

import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.PhotoRepository

class PhotoInteractor(private val photoRepository: PhotoRepository) {

    fun loadPhotoPreview(url: String): ByteArray {
        return photoRepository.loadPhotoPreview(url)
    }

    fun loadPhoto(url: String): ByteArray {
        return photoRepository.loadPhoto(url)
    }
}