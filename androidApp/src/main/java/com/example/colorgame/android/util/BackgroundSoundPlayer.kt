package com.example.colorgame.android.util

import android.content.Context
import android.media.MediaPlayer
import com.example.colorgame.android.R


object BackgroundSoundPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun start(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.embrace_effect).apply {
                isLooping = true
                setVolume(0.3f, 0.3f)
            }
            mediaPlayer?.start()
        }
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
