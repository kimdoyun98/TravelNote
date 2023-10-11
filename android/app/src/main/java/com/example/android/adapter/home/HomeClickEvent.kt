package com.example.android.adapter.home

interface HomeClickEvent {
    fun readCommentClickEvent(posting_id : Int)

    fun writeCommentClickEvent()
}