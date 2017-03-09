package nz.co.cic.mdns

import android.net.nsd.NsdServiceInfo

/**
 * Created by dipshit on 3/03/17.
 */

class MDNSAdvertisement {

    var protocol: String? = null
    var serviceType: String
    var serviceName: String
    var serviceHost: String? = null
    var servicePort: Int = 0

    constructor(protocol: String, type: String, serviceName: String, servicePort: Int) {
        this.protocol = protocol
        this.serviceName = serviceName
        this.serviceType = "_$protocol._$type"
        this.servicePort = servicePort
    }

    constructor(serviceInfo: NsdServiceInfo) {
        this.serviceType = serviceInfo.serviceType
        this.serviceName = serviceInfo.serviceName
        this.servicePort = serviceInfo.port
        this.serviceHost = serviceInfo.host.hostAddress.toString()
    }
}
