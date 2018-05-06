package io.github.aleksandersh.mysocialnetworkphotos.presentation.content

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.SessionInteractor
import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.ZeroScreenData
import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.mysocialnetworkphotos.utils.TestSchedulersProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ContentPresenterTest {

    @Mock
    private lateinit var resourceManager: ResourceManager
    @Mock
    private lateinit var sessionInteractor: SessionInteractor
    @Mock
    private lateinit var lifecycleOwner: LifecycleOwner
    @Mock
    private lateinit var lifecycle: Lifecycle

    private lateinit var schedulersProvider: SchedulersProvider

    private lateinit var presenter: ContentPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        schedulersProvider = TestSchedulersProvider()

        presenter = ContentPresenter(resourceManager, schedulersProvider, sessionInteractor)
    }

    @Test
    fun checkSession_sessionExists() {
        val localPresenter =
            ContentPresenter(resourceManager, schedulersProvider, sessionInteractor)
        val stringRes = "stringRes"
        val loadingScreen = ZeroScreenData(stringRes, stringRes, false, true)

        var zeroScreenData: ZeroScreenData? = null
        var screen: Screen? = null
        var contentScreen: Boolean? = null

        whenever(sessionInteractor.checkSessionExists()).thenReturn(true)
        whenever(lifecycleOwner.lifecycle).thenReturn(lifecycle)
        whenever(resourceManager.getString(any())).thenReturn("anyString")

        localPresenter.viewState.screen.subscribe(lifecycleOwner) { screen = it }
        localPresenter.viewState.zeroScreenData.subscribe(lifecycleOwner) { zeroScreenData = it }
        localPresenter.viewState.contentScreen.subscribe(lifecycleOwner) { contentScreen = it }

        localPresenter.onActivityCreated()

        assert(zeroScreenData == loadingScreen)
        assert(screen == Screen.FRIENDS)
        assert(contentScreen == true)
    }

    @Test
    fun checkSession_sessionNotExists() {
        val localPresenter =
            ContentPresenter(resourceManager, schedulersProvider, sessionInteractor)
        val stringRes = "stringRes"
        val loadingScreen = ZeroScreenData(stringRes, stringRes, false, true)

        var zeroScreenData: ZeroScreenData? = null
        var screen: Screen? = null

        whenever(sessionInteractor.checkSessionExists()).thenReturn(false)
        whenever(lifecycleOwner.lifecycle).thenReturn(lifecycle)
        whenever(resourceManager.getString(any())).thenReturn("anyString")

        localPresenter.viewState.screen.subscribe(lifecycleOwner) { screen = it }
        localPresenter.viewState.zeroScreenData.subscribe(lifecycleOwner) { zeroScreenData = it }

        localPresenter.onActivityCreated()

        assert(zeroScreenData == loadingScreen)
        assert(screen == Screen.AUTHORIZATION)
    }

    @Test
    fun checkSession_error() {
        val localPresenter =
            ContentPresenter(resourceManager, schedulersProvider, sessionInteractor)
        val stringRes = "stringRes"
        val errorScreen = ZeroScreenData(stringRes, stringRes, true, false)

        var zeroScreenData: ZeroScreenData? = null
        var screen: Screen? = null

        whenever(sessionInteractor.checkSessionExists()).thenReturn(false)
        whenever(lifecycleOwner.lifecycle).thenReturn(lifecycle)
        whenever(resourceManager.getString(any())).thenReturn("anyString")

        localPresenter.viewState.screen.subscribe(lifecycleOwner) { screen = it }
        localPresenter.viewState.zeroScreenData.subscribe(lifecycleOwner) { zeroScreenData = it }

        localPresenter.onActivityCreated()

        assert(zeroScreenData == errorScreen)
        assert(screen == null)
    }
}