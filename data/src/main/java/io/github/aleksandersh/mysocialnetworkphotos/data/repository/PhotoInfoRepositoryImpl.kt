package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import io.github.aleksandersh.mysocialnetworkphotos.data.datasource.PhotoInfoHttpDatasource
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.PhotoInfo
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.PhotoInfoRepository

class PhotoInfoRepositoryImpl(
    private val photoInfoHttpDatasource: PhotoInfoHttpDatasource
) : PhotoInfoRepository {

    override fun getPhotoInfo(photoId: String): PhotoInfo {
        return photoInfoHttpDatasource.getPhotoInfo(photoId)
    }
}