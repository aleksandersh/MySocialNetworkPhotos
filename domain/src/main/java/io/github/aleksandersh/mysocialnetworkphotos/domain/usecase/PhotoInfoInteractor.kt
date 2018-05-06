package io.github.aleksandersh.mysocialnetworkphotos.domain.usecase

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.PhotoInfo
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.PhotoInfoRepository

class PhotoInfoInteractor(private val photoInfoRepository: PhotoInfoRepository) {

    fun getPhotoInfo(photoId: String): PhotoInfo {
        return photoInfoRepository.getPhotoInfo(photoId)
    }
}