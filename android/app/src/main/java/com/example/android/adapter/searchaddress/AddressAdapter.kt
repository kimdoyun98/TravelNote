package com.example.android.adapter.searchaddress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.databinding.AddressSearchItemBinding
import com.example.android.retrofit.DTO.AddressDTO

class AddressAdapter : RecyclerView.Adapter<AddressAdapter.ViewHolder>(){
    private lateinit var binding : AddressSearchItemBinding
    private var addressArray = ArrayList<AddressDTO.Result.Juso>()

    /**
     * Item Click Listener
     */
    interface OnItemClickListener {
        fun onItemClicked(address: String?, address2: String?)
    }

    // OnItemClickListener 참조 변수 선언
    private var itemClickListener: OnItemClickListener? = null

    // OnItemClickListener 전달 메소드
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        init {
            binding.root.setOnClickListener(View.OnClickListener {
                var pos : Int = adapterPosition
                itemClickListener?.onItemClicked(addressArray[pos].jibunAddr, addressArray[pos].roadAddr);
            })
        }


        fun bind(data: AddressDTO.Result.Juso){
            binding.address.text = data.jibunAddr
            binding.address2.text = data.roadAddr
        }
    }

    fun setAddress(array: ArrayList<AddressDTO.Result.Juso>?){
        if (array != null) {
            addressArray = array
        }
        //notifyDataSetChanged() //TODO 데이터 꼬임 현상
        notifyItemRangeChanged(0, 10)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = AddressSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(addressArray[position])
    }

    override fun getItemCount(): Int {
        return addressArray.size
    }
}