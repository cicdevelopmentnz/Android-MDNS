package nz.co.cic.mdns

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import io.reactivex.Flowable


/**
 * Created by dipshit on 3/03/17.
 */

class MDNS(private val mContext:Context) {
 private val nsdManager:NsdManager
 private val mdnsAdvertiser:MDNSAdvertiser?
 private var mdnsScanner:MDNSScanner? = null

 init{
  this.nsdManager = mContext.getSystemService(Context.NSD_SERVICE) as NsdManager
  this.mdnsAdvertiser = MDNSAdvertiser(this.mContext)
 }

 fun advertise(mdnsAdvertisement:MDNSAdvertisement) : Flowable<MDNSStatus>{
  return this.mdnsAdvertiser!!.advertise(mdnsAdvertisement)
 }

 fun stopAdvertising() : Flowable<MDNSStatus>? {
   return this.mdnsAdvertiser?.stop(nsdManager)
 }

 fun scan(type:String) : Flowable<NsdServiceInfo> {
  this.mdnsScanner = MDNSScanner(this.mContext)
  return this.mdnsScanner!!.scan(nsdManager, type)
 }

 fun stopScan() {
  if (this.mdnsScanner != null)
  {
   this.mdnsScanner!!.stop(nsdManager)
  }
 }
}
