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

class MDNSAdvertiser(private val mContext: Context) : NsdManager.RegistrationListener {

    private var nsdManager : NsdManager? = null
    private var observer: FlowableEmitter<MDNSStatus>? = null
    private var dObserver: FlowableEmitter<MDNSStatus>? = null

    init {
        this.nsdManager = mContext.getSystemService(Context.NSD_SERVICE) as NsdManager
    }

    fun advertise(advertisement: MDNSAdvertisement): Flowable<MDNSStatus> {
        return Flowable.create({
            subscriber ->
            this.observer = subscriber
            val serviceInfo = NsdServiceInfo()
            serviceInfo.serviceName = advertisement.serviceName
            serviceInfo.port = advertisement.servicePort
            serviceInfo.serviceType = advertisement.serviceType
            nsdManager?.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, this)
        }, BackpressureStrategy.BUFFER)
    }

    fun stop(nsdManager: NsdManager): Flowable<MDNSStatus> {
        return Flowable.create({
            subscriber ->
            this.dObserver = subscriber
            nsdManager.unregisterService(this)
        }, BackpressureStrategy.BUFFER)
    }

    override fun onRegistrationFailed(nsdServiceInfo: NsdServiceInfo, i: Int) {
        this.observer?.onError(Throwable("Failed to register"))
    }

    override fun onUnregistrationFailed(nsdServiceInfo: NsdServiceInfo, i: Int) {
        this.dObserver?.onError(Throwable("Failed to unregister"))
    }

    override fun onServiceRegistered(nsdServiceInfo: NsdServiceInfo) {
        observer?.onNext(MDNSStatus("Service Registered"))
        this.observer?.onComplete()
    }

    override fun onServiceUnregistered(nsdServiceInfo: NsdServiceInfo) {
        observer?.onNext(MDNSStatus("Service Unregistered"))
        this.dObserver?.onComplete()
    }
}
