package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content.friends

import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Branch
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content.ContentComponent
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content.ContentComponentHolder

class FriendsComponentHolder(parent: ContentComponentHolder) :
    Branch<ContentComponent, FriendsComponent>(parent) {

    override fun create(parent: ContentComponent): FriendsComponent {
        return FriendsComponent(parent)
    }
}