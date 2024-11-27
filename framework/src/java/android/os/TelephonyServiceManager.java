package android.os;

/* loaded from: classes3.dex */
public class TelephonyServiceManager {

    public static final class ServiceRegisterer {
        private final java.lang.String mServiceName;

        public ServiceRegisterer(java.lang.String str) {
            this.mServiceName = str;
        }

        public void register(android.os.IBinder iBinder) {
            android.os.ServiceManager.addService(this.mServiceName, iBinder);
        }

        public android.os.IBinder get() {
            return android.os.ServiceManager.getService(this.mServiceName);
        }

        public android.os.IBinder getOrThrow() throws android.os.TelephonyServiceManager.ServiceNotFoundException {
            try {
                return android.os.ServiceManager.getServiceOrThrow(this.mServiceName);
            } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                throw new android.os.TelephonyServiceManager.ServiceNotFoundException(this.mServiceName);
            }
        }

        public android.os.IBinder tryGet() {
            return android.os.ServiceManager.checkService(this.mServiceName);
        }
    }

    public static class ServiceNotFoundException extends android.os.ServiceManager.ServiceNotFoundException {
        public ServiceNotFoundException(java.lang.String str) {
            super(str);
        }
    }

    public android.os.TelephonyServiceManager.ServiceRegisterer getTelephonyServiceRegisterer() {
        return new android.os.TelephonyServiceManager.ServiceRegisterer("phone");
    }

    public android.os.TelephonyServiceManager.ServiceRegisterer getTelephonyImsServiceRegisterer() {
        return new android.os.TelephonyServiceManager.ServiceRegisterer(android.content.Context.TELEPHONY_IMS_SERVICE);
    }

    public android.os.TelephonyServiceManager.ServiceRegisterer getTelephonyRcsMessageServiceRegisterer() {
        return new android.os.TelephonyServiceManager.ServiceRegisterer(android.content.Context.TELEPHONY_RCS_MESSAGE_SERVICE);
    }

    public android.os.TelephonyServiceManager.ServiceRegisterer getSubscriptionServiceRegisterer() {
        return new android.os.TelephonyServiceManager.ServiceRegisterer("isub");
    }

    public android.os.TelephonyServiceManager.ServiceRegisterer getPhoneSubServiceRegisterer() {
        return new android.os.TelephonyServiceManager.ServiceRegisterer("iphonesubinfo");
    }

    public android.os.TelephonyServiceManager.ServiceRegisterer getOpportunisticNetworkServiceRegisterer() {
        return new android.os.TelephonyServiceManager.ServiceRegisterer("ions");
    }

    public android.os.TelephonyServiceManager.ServiceRegisterer getCarrierConfigServiceRegisterer() {
        return new android.os.TelephonyServiceManager.ServiceRegisterer("carrier_config");
    }

    public android.os.TelephonyServiceManager.ServiceRegisterer getSmsServiceRegisterer() {
        return new android.os.TelephonyServiceManager.ServiceRegisterer("isms");
    }

    public android.os.TelephonyServiceManager.ServiceRegisterer getEuiccControllerService() {
        return new android.os.TelephonyServiceManager.ServiceRegisterer("econtroller");
    }

    public android.os.TelephonyServiceManager.ServiceRegisterer getEuiccCardControllerServiceRegisterer() {
        return new android.os.TelephonyServiceManager.ServiceRegisterer("euicc_card_controller");
    }

    public android.os.TelephonyServiceManager.ServiceRegisterer getIccPhoneBookServiceRegisterer() {
        return new android.os.TelephonyServiceManager.ServiceRegisterer("simphonebook");
    }
}
