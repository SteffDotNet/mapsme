package org.mainsoft.mapsme.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import org.mainsoft.mapsme.mvp.view.SettingsView
import org.mainsoft.mapsme.util.MapUtil
import org.mainsoft.mapsme.util.SettingsUtil

@InjectViewState
class SettingsPresenter : BasePresenter<SettingsView>() {

    fun saveStyle(style: String){
        SettingsUtil.style = style
        SettingsUtil.saveSettings()
    }

    fun saveZoom(zoom: Int){
        SettingsUtil.zoom = zoom.toDouble()
        SettingsUtil.saveSettings()
    }

    fun applyDefaultSettings(){
        SettingsUtil.applyDefaultSettings()
    }

    fun getStyleSelectPosition(): Int{
        val currentStyle = SettingsUtil.style
        for(pos in 0 .. MapUtil.mapStyleValues.size){
            if(currentStyle.equals(MapUtil.mapStyleValues[pos])){
                return pos
            }
        }
        return 0
    }
}