package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content.photo

import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Branch
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content.ContentComponent
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content.ContentComponentHolder

class PhotoComponentHolder(parent: ContentComponentHolder) :
    Branch<ContentComponent, PhotoComponent>(parent) {

    override fun create(parent: ContentComponent): PhotoComponent {
        return PhotoComponent(parent)
    }
}