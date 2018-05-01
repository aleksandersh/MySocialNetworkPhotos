package io.github.aleksandersh.mysocialnetworkphotos.data.storage

interface PhotoCache {

    fun put(key: String, photo: ByteArray)

    fun get(key: String): ByteArray?

    fun clear()
}