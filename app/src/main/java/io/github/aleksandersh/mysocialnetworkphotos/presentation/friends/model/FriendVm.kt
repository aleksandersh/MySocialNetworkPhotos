package io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.model

import android.os.Parcel
import android.os.Parcelable

data class FriendVm(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val smallPhotoUrl: String,
    val bigPhotoUrl: String,
    val photoId: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(smallPhotoUrl)
        parcel.writeString(bigPhotoUrl)
        parcel.writeString(photoId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FriendVm> {

        override fun createFromParcel(parcel: Parcel): FriendVm {
            return FriendVm(parcel)
        }

        override fun newArray(size: Int): Array<FriendVm?> {
            return arrayOfNulls(size)
        }
    }
}