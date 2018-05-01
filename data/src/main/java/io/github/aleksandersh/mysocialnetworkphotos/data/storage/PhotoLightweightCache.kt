package io.github.aleksandersh.mysocialnetworkphotos.data.storage

import android.util.LruCache

/**
 * lightweightInmemory +-50KB
 * lightweightDisk +-500KB
 */
class PhotoLightweightCache : PhotoCache {

    companion object {

        private const val INMEMORY_CACHE_SIZE = 256 * 1024 // bytes
    }

    private val inmemoryCache: LruCache<String, ByteArray> =
        InmemoryBitmapCache(INMEMORY_CACHE_SIZE)

    override fun put(key: String, photo: ByteArray) {
        inmemoryCache.put(key, photo)
    }

    override fun get(key: String): ByteArray? {
        return inmemoryCache[key]
    }

    override fun clear() {

    }

    class InmemoryBitmapCache(size: Int) : LruCache<String, ByteArray>(size) {

        override fun sizeOf(key: String, data: ByteArray): Int {
            return data.size
        }
    }
}