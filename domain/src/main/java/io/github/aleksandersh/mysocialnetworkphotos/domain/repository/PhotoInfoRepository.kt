package io.github.aleksandersh.mysocialnetworkphotos.domain.repository

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.PhotoInfo

interface PhotoInfoRepository {

    fun getPhotoInfo(photoId: String): PhotoInfo
}