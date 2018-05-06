package io.github.aleksandersh.mysocialnetworkphotos.presentation.photo

import android.graphics.Bitmap
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.PhotoInfo
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.strategy.HandleOnceStrategy

class PhotoViewState {

    val startPostponedTransition = ObservableField<Boolean>(HandleOnceStrategy())
    val photo = ObservableField<Bitmap>()
    val photoInfo = ObservableField<PhotoInfo>()
}