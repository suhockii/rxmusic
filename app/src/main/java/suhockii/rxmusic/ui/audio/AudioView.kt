package suhockii.rxmusic.ui.audio

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import suhockii.rxmusic.data.net.models.AudioResponse

/** Created by Maksim Sukhotski on 4/8/2017. */
@StateStrategyType(SkipStrategy::class)
interface AudioView : MvpView {

    fun showAudio(audioResponse: AudioResponse)

    fun showAuthController()

//    @StateStrategyType(SkipStrategy::class)
//    fun showNextController()
}