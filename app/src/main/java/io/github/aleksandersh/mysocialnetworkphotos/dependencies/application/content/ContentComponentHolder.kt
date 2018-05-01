package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content

import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Branch
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.ApplicationComponent
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.ApplicationComponentHolder
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content.friends.FriendsComponentHolder

class ContentComponentHolder(parent: ApplicationComponentHolder) :
    Branch<ApplicationComponent, ContentComponent>(parent) {

    val friendsComponent: FriendsComponentHolder by lazy { FriendsComponentHolder(this) }

    override fun create(parent: ApplicationComponent): ContentComponent {
        return ContentComponent(parent)
    }
}