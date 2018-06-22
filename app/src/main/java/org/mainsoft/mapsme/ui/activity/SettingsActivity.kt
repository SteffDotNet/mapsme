package org.mainsoft.mapsme

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Spinner
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_settings.spinnerStyle
import org.mainsoft.mapsme.mvp.presenter.SettingsPresenter
import org.mainsoft.mapsme.mvp.view.SettingsView
import org.mainsoft.mapsme.ui.activity.BaseActivity
import org.mainsoft.mapsme.util.MapUtil
import org.mainsoft.mapsme.util.SettingsUtil

class SettingsActivity : BaseActivity(), SettingsView {

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    @BindView(R.id.spinnerStyle)
    lateinit var styleSpinner: Spinner
    @BindView(R.id.seekBarZoom)
    lateinit var seekBarZoom: SeekBar

    lateinit var styleAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        ButterKnife.bind(this)
        initUI()

        spinnerStyle.onItemSelectedListener = object: OnItemSelectedListener{

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.saveStyle(MapUtil.mapStyleValues[position])
            }
        }

        seekBarZoom.setOnSeekBarChangeListener(object :OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, value: Int, p2: Boolean) {
                presenter.saveZoom(value)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    fun initUI() {
        styleAdapter = ArrayAdapter(this, R.layout.item_spinner, R.id.nameTextView, resources.getStringArray(R.array.settings_styles))
        styleSpinner.adapter = styleAdapter
        styleSpinner.setSelection(presenter.getStyleSelectPosition())
        seekBarZoom.progress = SettingsUtil.zoom.toInt()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        finish()
    }
}
