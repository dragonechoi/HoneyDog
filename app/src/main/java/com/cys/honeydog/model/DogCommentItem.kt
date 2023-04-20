package com.cys.honeydog.model

class DogCommentItem {
    var id: String = ""
    var imgUrl: String = ""
    var nickname: String = ""
    var comment: String = ""
    var no: Int = 0
    var DogCommentNum :Int = 0

    constructor(id: String, imageUrl: String, nickname: String, comment: String, no: Int,DogCommentNum:Int) {
        this.id = id
        this.imgUrl = imgUrl
        this.nickname = nickname
        this.comment = comment
        this.no = no
        this.DogCommentNum = DogCommentNum
    }

    constructor()
}