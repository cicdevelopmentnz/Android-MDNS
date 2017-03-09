package nz.co.cic.mdns

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter

/**
 * Created by dipshit on 3/03/17.
 */

class MDNSScanner(private val c: Context) : NsdManager.DiscoveryListener {
    private var scanObserver : FlowableEmitter<NsdServiceInfo>? = null
    private var nsdManager: NsdManager? = null



    fun scan(manager: NsdManager, type: String): Flowable<NsdServiceInfo> {
        return Flowable.create({
            subscriber ->
            this.scanObserver = subscriber
            this.nsdManager = manager
            manager.discoverServices(type, NsdManager.PROTOCOL_DNS_SD, this)

        }, BackpressureStrategy.BUFFER)
    }

    fun stop(manager: NsdManager) {
        manager.stopServiceDiscovery(this)
    }

    override fun onStartDiscoveryFailed(s: String, i: Int) {
        //deferred!!.reject(i)
    }

    override fun onStopDiscoveryFailed(s: String, i: Int) {
        //deferred!!.reject(i)
    }

    override fun onDiscoveryStarted(s: String) {
       // deferred!!.resolve(s)
    }

    override fun onDiscoveryStopped(s: String) {
        //deferred!!.resolve(s)
    }

    override fun onServiceFound(nsdServiceInfo: NsdServiceInfo) {

        resolveService(nsdServiceInfo).subscribe({
            resolvedService ->
                this.scanObserver?.onNext(resolvedService)
        }, {
            err ->
                this.scanObserver?.onError(err)
        })

    }

    override fun onServiceLost(nsdServiceInfo: NsdServiceInfo) {
        //this.scanResultListener.onLost(MDNSAdvertisement(nsdServiceInfo))
    }

    fun resolveService(serviceInfo: NsdServiceInfo): Flowable<NsdServiceInfo>{
        return Flowable.create({
            subscriber ->

            this.nsdManager!!.resolveService(serviceInfo, object: NsdManager.ResolveListener{
                override fun onServiceResolved(serviceInfo: NsdServiceInfo?) {
                    subscriber.onNext(serviceInfo)
                }

                override fun onResolveFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
                    subscriber.onError(Throwable("error: " + errorCode))
                }
            })
        }, BackpressureStrategy.BUFFER)
    }
}
