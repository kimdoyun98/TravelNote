package com.example.android

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.Activity.AddActivity.StringToImage.toSelectImageList

class GalleryViewModel() : ViewModel(){
    private var _currentSelectedPhoto = MutableLiveData<String>()
     var checkCurrentPhoto = ArrayList<CheckPhotoData>()

    var currentSelectedPhoto : LiveData<String> = _currentSelectedPhoto

    fun selectPhoto(path: String){
        _currentSelectedPhoto.postValue(path)
    }

    fun photoList(list: ArrayList<String>){
            checkCurrentPhoto = ArrayList(list.toSelectImageList())
    }
}

data class CheckPhotoData(
    val index: MutableLiveData<Int>,
    val path: MutableLiveData<String>
){
    companion object {
        fun create(index: Int, path: String): CheckPhotoData {
            return CheckPhotoData(MutableLiveData(index), MutableLiveData(path))
        }
    }
}