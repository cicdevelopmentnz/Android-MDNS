package nz.co.cic.mdns;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

import org.jdeferred.Deferred;

/**
 * Created by dipshit on 3/03/17.
 */

public class MDNSAdvertiser implements NsdManager.RegistrationListener {

    private Context mContext;
    private Deferred deferred;

    public MDNSAdvertiser(Context c){
        this.mContext = c;
    }

    public void advertise(NsdManager nsdManager, MDNSAdvertisement advertisement, Deferred deferred){
        this.deferred = deferred;
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName(advertisement.serviceName);
        serviceInfo.setPort(advertisement.servicePort);
        serviceInfo.setServiceType(advertisement.serviceType);
        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, this);
    }

    public void stop(NsdManager nsdManager, Deferred deferred){
        this.deferred = deferred;
        nsdManager.unregisterService(this);
    }

    @Override
    public void onRegistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
        this.deferred.reject(i);
    }

    @Override
    public void onUnregistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
        this.deferred.reject(i);
    }

    @Override
    public void onServiceRegistered(NsdServiceInfo nsdServiceInfo) {
        this.deferred.resolve(nsdServiceInfo);
    }

    @Override
    public void onServiceUnregistered(NsdServiceInfo nsdServiceInfo) {
        this.deferred.resolve(nsdServiceInfo);
    }
}
