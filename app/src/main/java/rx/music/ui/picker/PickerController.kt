package rx.music.ui.picker

import android.support.v7.widget.LinearLayoutManager
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.controller_picker.view.*
import me.base.MoxyController
import me.extensions.onClick
import me.extensions.toMain
import rx.music.R
import rx.music.net.models.Audio
import rx.music.ui.audio.AudioAdapter
import rx.music.ui.audio.AudioPresenter
import rx.music.ui.audio.AudioView
import rx.music.ui.audio.InfiniteScrollListener

/** Created by Maksim Sukhotski on 5/6/2017. */
class PickerController : MoxyController(), AudioView {
    @InjectPresenter lateinit var audioPresenter: AudioPresenter

    private var adapter: AudioAdapter? = AudioAdapter(realm.where(Audio::class.java).findAll(),
            onClick = { audio, position ->
                run {
                    audioPresenter.handleAudio(audio, position)
                }
            })

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_picker, container, false)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
    }

    override fun onViewBound(view: View) = with(view) {
        descriptionTextView.movementMethod = LinkMovementMethod.getInstance()
        dialogContainer.onClick {
            handleBack()
            router.popCurrentController()
        }
        val layoutManager = LinearLayoutManager(activity)
        pickerRecycler.adapter = adapter
        pickerRecycler.setHasFixedSize(true)
        pickerRecycler.layoutManager = layoutManager
        pickerRecycler.addOnScrollListener(InfiniteScrollListener({
            audioPresenter.getAudio(offset = adapter?.itemCount!!)
        }, layoutManager))
    }

//    override fun showAudio(audioResponse: Response<AudioResponse>): Unit = adapter!!.addAndNotify(audioResponse.response?.items)

//    override fun showAuthController() = router.setRoot(RouterTransaction.with(AuthController())
//            .pushChangeHandler(HorizontalChangeHandler())
//            .popChangeHandler(HorizontalChangeHandler()))

    override fun handleBack(): Boolean {
        if (!activity!!.toMain().isAnimate) {
            activity!!.toMain().isAnimate = true
            view?.postDelayed({
                activity!!.toMain().resetSlidingPanel()
            }, 200)
            return super.handleBack()
        }
        return false
    }

    override fun showPlayer(audio: Audio) = activity!!.toMain().mainPresenter.updatePlayer(audio)

    override fun showSelectedPos(position: Int) = adapter!!.selectAndNotify(position)
}