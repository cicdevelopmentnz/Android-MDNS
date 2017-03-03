package nz.co.cic.mdns;

/**
 * Created by dipshit on 3/03/17.
 */

public interface MDNSScanResultListener {

    void onResult(MDNSAdvertisement mdnsAdvertisement);
    void onLost(MDNSAdvertisement mdnsAdvertisement);
}
