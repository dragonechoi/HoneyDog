package com.cys.honeydog.network

data class RetRofitHospital constructor(var total:Int , var display:Int,var items : MutableList<AniMalHospital>)

//아이템 한개의 데이터 클래스
data class AniMalHospital(
    var title:String,
    var link:String,
    var image:String,
    var address:String,
    var telephone:String

    //더 있지만 필요한것만

)