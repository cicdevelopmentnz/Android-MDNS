package nz.co.cic.mdns;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

import org.jdeferred.Deferred;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

/**
 * Created by dipshit on 3/03/17.
 */

public class MDNSScanner implements NsdManager.DiscoveryListener {

    private Context c;
    private Deferred deferred;
    private MDNSScanResultListener scanResultListener;
    private NsdManager nsdManager;

    public MDNSScanner(Context c, MDNSScanResultListener scanResultListener){
        this.c = c;
        this.scanResultListener = scanResultListener;
    }

    public void scan(NsdManager manager, String type, Deferred deferred){
        this.nsdManager = manager;
        this.deferred = deferred;
        manager.discoverServices(type, NsdManager.PROTOCOL_DNS_SD, this);
    }

    public void stop(NsdManager manager, Deferred deferred){
        this.deferred = deferred;
        manager.stopServiceDiscovery(this);
    }

    @Override
    public void onStartDiscoveryFailed(String s, int i) {
        deferred.reject(i);
    }

    @Override
    public void onStopDiscoveryFailed(String s, int i) {
        deferred.reject(i);
    }

    @Override
    public void onDiscoveryStarted(String s) {
        deferred.resolve(s);
    }

    @Override
    public void onDiscoveryStopped(String s) {
        deferred.resolve(s);
    }

    @Override
    public void onServiceFound(NsdServiceInfo nsdServiceInfo) {
        Deferred deferred = new DeferredObject();
        Promise p = deferred.promise();
        p.done(new DoneCallback() {
            @Override
            public void onDone(Object result) {
                scanResultListener.onResult(new MDNSAdvertisement((NsdServiceInfo) result));
            }
        }).fail(new FailCallback() {
            @Override
            public void onFail(Object result) {
                System.out.println("Failed to resolve " + result);
            }
        });
        resolveService(nsdServiceInfo, deferred);
    }

    @Override
    public void onServiceLost(NsdServiceInfo nsdServiceInfo) {
        this.scanResultListener.onLost(new MDNSAdvertisement(nsdServiceInfo));
    }

    public void resolveService(NsdServiceInfo serviceInfo, final Deferred deferred){
        this.nsdManager.resolveService(serviceInfo, new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {
                    deferred.reject(i);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {
                deferred.resolve(nsdServiceInfo);
            }
        });
    }
}
