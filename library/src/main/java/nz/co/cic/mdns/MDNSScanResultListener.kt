package nz.co.cic.mdns

/**
 * Created by dipshit on 3/03/17.
 */

interface MDNSScanResultListener {

    fun onResult(mdnsAdvertisement: MDNSAdvertisement)
    fun onLost(mdnsAdvertisement: MDNSAdvertisement)
}
