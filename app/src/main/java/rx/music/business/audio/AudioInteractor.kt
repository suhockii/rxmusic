package rx.music.business.audio

import io.reactivex.Single
import rx.music.network.models.Audio
import rx.music.network.models.AudioResponse
import rx.music.network.models.BaseResponse

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioInteractor {

    fun getAudio(ownerId: String?,
                 count: String,
                 offset: String): Single<BaseResponse<AudioResponse>>
    fun handleAudio(audio: Audio): Single<Audio>
    val isNotAuthorized: Boolean
}