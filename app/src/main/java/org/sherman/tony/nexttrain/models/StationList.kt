package org.sherman.tony.nexttrain.models


class StationList() {
    var id: Int? = null
    var station: String? = null
    var favorite: Int? = null
    var recent: Long? = null

    constructor(id:Int, station:String, favorite:Int,recent:Long): this(){
        this.id = id
        this.recent = recent
        this.favorite = favorite
        this.station = station
    }
}

