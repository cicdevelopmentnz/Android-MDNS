# Android-MDNS

## Usage

```java

   MDNS mdns = new MDNS(Context);
   
   MDNSAdvertisement ad = new MDNSAdvertisement("shouldertap", "udp", "Shouldertap", 3000); //serviceType, serviceProtocol, serviceName, servicePort

   Deferred deferred = new DeferredObject();
   Promise p = deferred.promise();
   //Handle promise here
   mdns.advertise(ad, deferred);

   mdns.scan("_shouldertap._udp", MDNSScanResultListener, deferredObject);

```
