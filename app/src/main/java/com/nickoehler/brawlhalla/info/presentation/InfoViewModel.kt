package com.nickoehler.brawlhalla.info.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.info.presentation.model.InfoAction
import com.nickoehler.brawlhalla.info.presentation.model.InfoEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class InfoViewModel : ViewModel() {

    private val _events = Channel<InfoEvent>()
    val events = _events.receiveAsFlow()

    fun onInfoAction(action: InfoAction) {
        when (action) {
            is InfoAction.ViewIntent -> sendEvent(action.uri)
        }
    }

    private fun sendEvent(uri: Uri) {
        viewModelScope.launch {
            println("BEFORE")
            _events.send(
                InfoEvent.ViewIntent(
                    uri
                )
            )
            println("AFTER")
            println(_events)
            println(events)

        }
    }
}