package com.nickoehler.brawlhalla.info.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.nickoehler.brawlhalla.info.presentation.model.InfoAction

class InfoViewModel : ViewModel() {


    private fun openLink(uri: String, context: Context) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        )
    }

    fun onInfoAction(action: InfoAction) {
        when (action) {
            is InfoAction.GithubPressed -> openLink(
                "https://github.com/nickoehler",
                action.context
            )
            
            else -> {}
        }
    }
}