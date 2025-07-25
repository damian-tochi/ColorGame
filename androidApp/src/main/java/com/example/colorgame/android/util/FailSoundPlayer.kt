package com.example.colorgame.android.util

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.example.colorgame.android.R

class FailSoundPlayer(context: Context) {
    private val soundPool: SoundPool
    private val clickSoundId: Int

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(audioAttributes)
            .build()

        clickSoundId = soundPool.load(context, R.raw.stab_sound, 1)

    }

    fun playClick() {
        soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f)
    }

    fun release() {
        soundPool.release()
    }
}


@Composable
fun rememberFailSound(context: Context): FailSoundPlayer {
    val soundPoolPlayer = remember {
        FailSoundPlayer(context)
    }
    DisposableEffect(Unit) {
        onDispose {
            soundPoolPlayer.release()
        }
    }
    return soundPoolPlayer
}
