package com.example.android.common

object StateUtils {
    val stateColor : HashMap<String, String> = hashMapOf(
        Pair("서울특별시","#FF0000"), //빨강
        Pair("인천광역시","#FAB2B2"), //연분홍
        Pair("경기도","#FFD0A1"), // 연주황
        Pair("강원도","#FAFAAA"), //연노랑
        Pair("충청남도","#6EE5A3"), //청록
        Pair("충청북도","#228B22"), //진청록
        Pair("경상북도","#52E4DC"), //에매랄더
        Pair("세종특별자치시","#0000CD"), //파랑
        Pair("대전광역시","#7B68EE"),
        Pair("대구광역시","#32AAFF"),
        Pair("전라북도","#FF1493"),
        Pair("경상남도","#FFD732"),
        Pair("전라남도","#800000"),
        Pair("울산광역시","#8B4513"),
        Pair("부산광역시","#F389EF"),
        Pair("광주광역시","#AD19EC"),
        Pair("제주도","#EF904C") //주황색
    )
    val stateList : ArrayList<String> = arrayListOf(
        "서울특별시", "인천광역시", "경기도", "강원도","충청남도",
        "충청북도", "경상북도", "세종특별자치시", "대전광역시", "대구광역시",
        "전라북도","경상남도", "전라남도", "울산광역시","부산광역시","광주광역시","제주도")

    val seoul : ArrayList<String> = arrayListOf(
        "종로구","중구","용산구","성동구","광진구","동대문구","중랑구","성북구",
        "강북구","도봉구","노원구","은평구","서대문구","마포구","양천구","강서구",
        "강서구","구로구","금천구","영등포구","동작구","관악구","서초구","강남구",
        "송파구","강동구")

    val Gyeonggi_do : ArrayList<String> = arrayListOf(
        "수원시", "성남시", "의정부시", "안양시", "부천시","광명시", "동두천시", "평택시", "안산시",
        "고양시", "과천시", "구리시", "남양주시", "오산시", "시흥시", "군포시", "의왕시", "하남시",
        "용인시", "파주시", "이천시", "안성시", "김포시", "화성시", "광주시", "양주시", "포천시", "여주시",
        "연쳔군", "가평군", "양평군"
    )


    val hashList : HashMap<String, ArrayList<String>> =
        hashMapOf(
            Pair("서울특별시", seoul),
            Pair("경기도", Gyeonggi_do)
        )

}