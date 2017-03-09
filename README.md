# Android-MDNS

## Installation

Add the following to your root build.gradle

```gradle
   allprojects {
      repositories {
         ...
         maven { url 'https://jitpack.io' }
      }
   }
```

Add the dependency
```gradle
   dependencies {
      compile 'com.github.cicdevelopmentnz:Android-MDNS:v0.0.1'
   }
```

## Usage

### Initialization

```java
   MDN mdns = new MDNS(this);
```

### Advertiser 

```java
   MDNSAdvertisement ad = new MDNSAdvertisement("shouldertap", "udp", "Shouldertap", 3000); //serviceType, serviceProtocol, serviceName, servicePort
   
   mdns.advertise(ad).subscribe(
      mdnsStatus -> {
   
      }, err -> {
         //Error advertising service
      }, () -> {
         //Advertising started
      }
   );

```

### Scanner

```java
   mdns.scan("_shouldertap._udp").subscribe(
      nsdService -> {
         //Found NsdServiceInfo
      }, err -> {
         //Error 
      }
   );

```
