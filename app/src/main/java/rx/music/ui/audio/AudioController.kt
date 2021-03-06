package rx.music.ui.audio

import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.controller_audio.view.*
import me.base.MoxyController
import me.extensions.main
import me.extensions.playerController
import rx.music.R
import rx.music.dagger.Dagger
import rx.music.net.models.vk.Audio


/** Created by Maksim Sukhotski on 4/8/2017. */
class AudioController : MoxyController(), AudioView {
    @ProvidePresenter fun providePresenter() = AudioPresenter(realm)
    @InjectPresenter lateinit var audioPresenter: AudioPresenter

    private lateinit var audioAdapter: AudioAdapter

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.controller_audio, container, false)

    override fun onViewBound(view: View) = with(view.audioRecycler) {
        val linearLayoutManager = LinearLayoutManager(activity)
        layoutManager = linearLayoutManager
        setHasFixedSize(true)
        addOnScrollListener(PaginationScrollListener(
                { audioPresenter.getMusicPage(audioOffset = it) },
                linearLayoutManager))
    }

    override fun showRecycler(audioAdapter: AudioAdapter) {
        this@AudioController.audioAdapter = audioAdapter
        view!!.audioRecycler.adapter = audioAdapter
    }

    override fun showPlayer(audio: Audio) =
            activity.main.playerController.playerPresenter.updatePlayer(audio)

    override fun showSelectedPos(position: Int) = audioAdapter.selectAndNotify(position)

    override fun showSnackbar(text: String) =
            Snackbar.make(view!!.audioRecycler, text, Snackbar.LENGTH_LONG).show()

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        Dagger.instance.userComponent = null
        view.audioRecycler.adapter = null
    }
}

