package android.telephony.ims;

/* loaded from: classes3.dex */
public class ImsManager {
    public static final java.lang.String ACTION_FORBIDDEN_NO_SERVICE_AUTHORIZATION = "com.android.internal.intent.action.ACTION_FORBIDDEN_NO_SERVICE_AUTHORIZATION";
    public static final java.lang.String ACTION_WFC_IMS_REGISTRATION_ERROR = "android.telephony.ims.action.WFC_IMS_REGISTRATION_ERROR";
    public static final java.lang.String EXTRA_WFC_REGISTRATION_FAILURE_MESSAGE = "android.telephony.ims.extra.WFC_REGISTRATION_FAILURE_MESSAGE";
    public static final java.lang.String EXTRA_WFC_REGISTRATION_FAILURE_TITLE = "android.telephony.ims.extra.WFC_REGISTRATION_FAILURE_TITLE";
    private final android.content.Context mContext;
    private static final android.telephony.BinderCacheManager<com.android.internal.telephony.ITelephony> sTelephonyCache = new android.telephony.BinderCacheManager<>(new android.telephony.BinderCacheManager.BinderInterfaceFactory() { // from class: android.telephony.ims.ImsManager$$ExternalSyntheticLambda0
        @Override // android.telephony.BinderCacheManager.BinderInterfaceFactory
        public final java.lang.Object create() {
            com.android.internal.telephony.ITelephony iTelephonyInterface;
            iTelephonyInterface = android.telephony.ims.ImsManager.getITelephonyInterface();
            return iTelephonyInterface;
        }
    });
    private static final android.telephony.BinderCacheManager<android.telephony.ims.aidl.IImsRcsController> sRcsCache = new android.telephony.BinderCacheManager<>(new android.telephony.BinderCacheManager.BinderInterfaceFactory() { // from class: android.telephony.ims.ImsManager$$ExternalSyntheticLambda1
        @Override // android.telephony.BinderCacheManager.BinderInterfaceFactory
        public final java.lang.Object create() {
            android.telephony.ims.aidl.IImsRcsController iImsRcsControllerInterface;
            iImsRcsControllerInterface = android.telephony.ims.ImsManager.getIImsRcsControllerInterface();
            return iImsRcsControllerInterface;
        }
    });

    public ImsManager(android.content.Context context) {
        this.mContext = context;
    }

    public android.telephony.ims.ImsRcsManager getImsRcsManager(int i) {
        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid subscription ID: " + i);
        }
        return new android.telephony.ims.ImsRcsManager(this.mContext, i, sRcsCache, sTelephonyCache);
    }

    public android.telephony.ims.ImsMmTelManager getImsMmTelManager(int i) {
        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid subscription ID: " + i);
        }
        return new android.telephony.ims.ImsMmTelManager(this.mContext, i, sTelephonyCache);
    }

    @android.annotation.SystemApi
    public android.telephony.ims.SipDelegateManager getSipDelegateManager(int i) {
        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid subscription ID: " + i);
        }
        return new android.telephony.ims.SipDelegateManager(this.mContext, i, sRcsCache, sTelephonyCache);
    }

    public android.telephony.ims.ProvisioningManager getProvisioningManager(int i) {
        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid subscription ID: " + i);
        }
        return new android.telephony.ims.ProvisioningManager(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.telephony.ims.aidl.IImsRcsController getIImsRcsControllerInterface() {
        return android.telephony.ims.aidl.IImsRcsController.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyImsServiceRegisterer().get());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.internal.telephony.ITelephony getITelephonyInterface() {
        return com.android.internal.telephony.ITelephony.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
    }
}
