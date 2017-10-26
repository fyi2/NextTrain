package org.sherman.tony.nexttrain.models


class TrainStatus() {
    var direction_id: Int? = null
    var sch_arr_time: Long? = null
    var route: String? = null

    constructor(direction_id: Int, sch_arr_time: Long, station: String): this() {
        this.direction_id = direction_id
        this.sch_arr_time = sch_arr_time
        this.route = station
    }
}