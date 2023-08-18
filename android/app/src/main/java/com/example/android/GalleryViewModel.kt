package com.example.android

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.Activity.AddActivity.StringToImage.toSelectImageList

class GalleryViewModel() : ViewModel(){
    private var _currentSelectedPhoto = MutableLiveData<String>()
    var currentSelectedPhoto : LiveData<String> = _currentSelectedPhoto // ImageView

    var checkCurrentPhoto = ArrayList<CheckPhotoData>()

    private var _multiSelect = MutableLiveData<Boolean>().apply { value = false }
    val multiSelect : LiveData<Boolean> = _multiSelect

    var multiCheckPhoto = ArrayList<String>()

    /**
     * 사진 클릭 시
     */
    fun selectPhoto(path: String){
        _currentSelectedPhoto.postValue(path)

        if (multiSelect.value == false){
            if(multiCheckPhoto.isNullOrEmpty())
                multiCheckPhoto.add(0, path)
            else
                multiCheckPhoto[0] = path
        }
        else if (path in multiCheckPhoto){
            multiCheckPhoto.remove(path)
            if (!multiCheckPhoto.isNullOrEmpty())
                _currentSelectedPhoto.postValue(multiCheckPhoto[multiCheckPhoto.size-1])
        }
        else{
            multiCheckPhoto.add(path)
        }
        refreshSelectList()
    }

    /**
     * 다중 선택 시 숫자 표기
     */
    private fun refreshSelectList() {
        for (data in checkCurrentPhoto) {
            data.index.value = 0
        }

        for (i in 0 until multiCheckPhoto.size) {
            val path = multiCheckPhoto[i]
            for (data in checkCurrentPhoto) {
                if (data.path.value.equals(path)) {
                    data.index.postValue(i + 1)
                    continue
                }
            }
        }
    }

    /**
     * 선택한 갤러리와 이미지 뷰의 이미지 경로 비교를 위한 초기 세팅
     */
    fun photoList(list: ArrayList<String>){
            checkCurrentPhoto = ArrayList(list.toSelectImageList())
    }

    /**
     * 다중 선택 버튼 클릭
     */
    fun multiSelectButton(view:View){
        if (multiSelect.value == false){
            _multiSelect.postValue(true)
            view.setBackgroundColor(Color.parseColor("#0000FF"))

            if(multiCheckPhoto.isNullOrEmpty())
                currentSelectedPhoto.value?.let { multiCheckPhoto.add(0, it) }
            refreshSelectList()
        }
        else{
            _multiSelect.postValue(false)
            view.setBackgroundColor(Color.parseColor("#404040"))
            multiCheckPhoto.clear()
        }
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

object DataBindingAdapterUtil {
    @JvmStatic
    @BindingAdapter("select")
    fun select(view: View, b: Boolean) {
        view.isSelected = b
    }
}