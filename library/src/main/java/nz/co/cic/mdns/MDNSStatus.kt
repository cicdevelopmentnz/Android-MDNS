package nz.co.cic.mdns

/**
 * Created by dipshit on 9/03/17.
 */

class MDNSStatus (status : String) {
    private var status:String? = null
    init {
        this.status = status
    }

    fun getStatus() : String? {
        return status
    }
}
