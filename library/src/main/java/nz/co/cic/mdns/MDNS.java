package nz.co.cic.mdns;

import android.content.Context;
import android.net.nsd.NsdManager;

import org.jdeferred.Deferred;

/**
 * Created by dipshit on 3/03/17.
 */

public class MDNS{

    private Context mContext;
    private NsdManager nsdManager;
    private MDNSAdvertiser mdnsAdvertiser;
    private MDNSScanner mdnsScanner;

    public MDNS(Context c){
        this.mContext = c;
        this.nsdManager = (NsdManager) c.getSystemService(Context.NSD_SERVICE);
        this.mdnsAdvertiser = new MDNSAdvertiser(this.mContext);
    }

    public void advertise(MDNSAdvertisement mdnsAdvertisement, Deferred deferred){
        this.mdnsAdvertiser.advertise(nsdManager, mdnsAdvertisement, deferred);
    }

    public void stopAdvertising(Deferred deferred){
        if(this.mdnsAdvertiser != null) {
            this.mdnsAdvertiser.stop(nsdManager, deferred);
        }
    }

    public void scan(String type, MDNSScanResultListener listener, Deferred deferred) {
        this.mdnsScanner = new MDNSScanner(this.mContext, listener);
        this.mdnsScanner.scan(nsdManager, type, deferred);
    }

    public void stopScan(Deferred deferred){
        if(this.mdnsScanner != null){
            this.mdnsScanner.stop(nsdManager, deferred);
        }
    }
}
