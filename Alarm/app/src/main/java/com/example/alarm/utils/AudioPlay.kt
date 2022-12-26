package com.example.alarm.utils

import android.content.Context;
import android.media.MediaPlayer;

object AudioPlay {
    var mediaPlayer: MediaPlayer? = null
    var isPlayingAudio = false

    fun playAudio(c: Context?, id: Int) {
        mediaPlayer = MediaPlayer.create(c, id)
        mediaPlayer!!.isLooping = true
        if (!mediaPlayer!!.isPlaying) {
            isPlayingAudio = true
            mediaPlayer!!.start()
        }
    }

    fun stopAudio() {
        isPlayingAudio = false
        mediaPlayer!!.isLooping = false
        mediaPlayer!!.stop()
    }

    fun muteAudio() {
        mediaPlayer!!.setVolume(0F, 0F)
    }


    fun unmuteAudio() {
        mediaPlayer!!.setVolume(1F, 1F)
    }
}