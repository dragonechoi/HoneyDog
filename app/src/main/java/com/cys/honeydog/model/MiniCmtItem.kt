package com.cys.honeydog.model

import android.widget.TextView

data class MiniCmtItem(var title: String? = null,
                       var postText: String? = null,
                       var nickname: String? = null,
                       var profileUrl: String? = null,
                       var userId: String? = null,
                       var imageUri: String? = null,
                       var no : Int?=null)
