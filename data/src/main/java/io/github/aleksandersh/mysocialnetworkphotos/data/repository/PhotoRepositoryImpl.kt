package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import android.util.Base64
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.HttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.storage.PhotoCache
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.PhotoRepository

class PhotoRepositoryImpl(
    private val httpClient: HttpClient,
    private val photoLightweightCache: PhotoCache
) : PhotoRepository {

    override fun loadPhotoPreview(url: String): ByteArray {
        val key = Base64.encodeToString(url.toByteArray(), Base64.DEFAULT)
        val photo = photoLightweightCache.get(key) ?: loadFromNetwork(url)
        photoLightweightCache.put(key, photo)
        return photo
    }

    override fun loadPhoto(url: String): ByteArray {
        return loadFromNetwork(url)
    }

    private fun loadFromNetwork(url: String): ByteArray {
        return httpClient.makeRequest(HttpClient.METHOD_GET, url)
    }

    override fun clearCache() {
        photoLightweightCache.clear()
    }
}