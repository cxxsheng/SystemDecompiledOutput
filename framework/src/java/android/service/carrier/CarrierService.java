package android.service.carrier;

/* loaded from: classes3.dex */
public abstract class CarrierService extends android.app.Service {
    public static final java.lang.String CARRIER_SERVICE_INTERFACE = "android.service.carrier.CarrierService";
    private static final java.lang.String LOG_TAG = "CarrierService";
    private final android.service.carrier.ICarrierService.Stub mStubWrapper = new android.service.carrier.CarrierService.ICarrierServiceWrapper();

    @java.lang.Deprecated
    public abstract android.os.PersistableBundle onLoadConfig(android.service.carrier.CarrierIdentifier carrierIdentifier);

    public android.os.PersistableBundle onLoadConfig(int i, android.service.carrier.CarrierIdentifier carrierIdentifier) {
        return onLoadConfig(carrierIdentifier);
    }

    @java.lang.Deprecated
    public final void notifyCarrierNetworkChange(boolean z) {
        android.telephony.TelephonyRegistryManager telephonyRegistryManager = (android.telephony.TelephonyRegistryManager) getSystemService(android.content.Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.notifyCarrierNetworkChange(z);
        }
    }

    public final void notifyCarrierNetworkChange(int i, boolean z) {
        android.telephony.TelephonyRegistryManager telephonyRegistryManager = (android.telephony.TelephonyRegistryManager) getSystemService(android.telephony.TelephonyRegistryManager.class);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.notifyCarrierNetworkChange(i, z);
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mStubWrapper;
    }

    public class ICarrierServiceWrapper extends android.service.carrier.ICarrierService.Stub {
        public static final java.lang.String KEY_CONFIG_BUNDLE = "config_bundle";
        public static final int RESULT_ERROR = 1;
        public static final int RESULT_OK = 0;

        public ICarrierServiceWrapper() {
        }

        @Override // android.service.carrier.ICarrierService
        public void getCarrierConfig(int i, android.service.carrier.CarrierIdentifier carrierIdentifier, android.os.ResultReceiver resultReceiver) {
            try {
                int subscriptionId = android.telephony.SubscriptionManager.getSubscriptionId(i);
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putParcelable(KEY_CONFIG_BUNDLE, android.service.carrier.CarrierService.this.onLoadConfig(subscriptionId, carrierIdentifier));
                resultReceiver.send(0, bundle);
            } catch (java.lang.Exception e) {
                android.util.Log.e(android.service.carrier.CarrierService.LOG_TAG, "Error in onLoadConfig: " + e.getMessage(), e);
                resultReceiver.send(1, null);
            }
        }

        @Override // android.os.Binder
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            android.service.carrier.CarrierService.this.dump(fileDescriptor, printWriter, strArr);
        }
    }
}
