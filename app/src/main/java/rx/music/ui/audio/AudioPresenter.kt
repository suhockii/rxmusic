package rx.music.ui.audio

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rx.music.App
import rx.music.business.audio.AudioInteractor
import rx.music.data.network.models.Audio
import javax.inject.Inject


/** Created by Maksim Sukhotski on 4/8/2017. */
@InjectViewState
class AudioPresenter : MvpPresenter<AudioView>() {

    @Inject lateinit var audioInteractor: AudioInteractor

    init {
        App.instance.userComponent?.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (audioInteractor.isNotAuthorized) viewState.showAuthController() else getAudio()
    }

    fun getAudio(ownerId: String? = null, count: String = "30", offset: String = "0") {
        audioInteractor.getAudio(ownerId, count, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    run {
                        if (it.response != null) viewState.showAudio(it.response)
                        else if (it.error.error_code == 5) viewState.showAuthController()
                    }
                })
    }

    fun handleAudio(audio: Audio) {
        audioInteractor.handleAudio(audio)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showPlayer(audio) }
                .subscribe({ handled -> viewState.showPlayer(handled) },
                        { error -> })
    }

}