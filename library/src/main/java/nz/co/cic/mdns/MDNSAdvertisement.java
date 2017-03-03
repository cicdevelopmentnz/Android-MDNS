package nz.co.cic.mdns;

import android.net.nsd.NsdServiceInfo;

/**
 * Created by dipshit on 3/03/17.
 */

public class MDNSAdvertisement {

    public String protocol;
    public String serviceType;
    public String serviceName;
    public String serviceHost;
    public int servicePort;

    public MDNSAdvertisement(String protocol, String type, String serviceName, int servicePort){
        this.protocol = protocol;
        this.serviceName = serviceName;
        this.serviceType = "_" + protocol + "._" + type ;
        this.servicePort = servicePort;
    }

    public MDNSAdvertisement(NsdServiceInfo serviceInfo){
        this.serviceType = serviceInfo.getServiceType();
        this.serviceName = serviceInfo.getServiceName();
        this.servicePort = serviceInfo.getPort();
        this.serviceHost = serviceInfo.getHost().getHostAddress().toString();
    }
}
