package suhockii.rxmusic.data.repositories.audio

import io.reactivex.Single
import suhockii.rxmusic.data.net.models.AudioResponse
import suhockii.rxmusic.data.net.models.BaseResponse

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioRepository {

    fun getAudio(ownerId: String,
                 count: String,
                 offset: String): Single<BaseResponse<AudioResponse>>
}