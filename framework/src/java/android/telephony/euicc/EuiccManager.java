package android.telephony.euicc;

/* loaded from: classes3.dex */
public class EuiccManager {

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_CONVERT_TO_EMBEDDED_SUBSCRIPTION = "android.telephony.euicc.action.CONVERT_TO_EMBEDDED_SUBSCRIPTION";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_DELETE_SUBSCRIPTION_PRIVILEGED = "android.telephony.euicc.action.DELETE_SUBSCRIPTION_PRIVILEGED";
    public static final java.lang.String ACTION_MANAGE_EMBEDDED_SUBSCRIPTIONS = "android.telephony.euicc.action.MANAGE_EMBEDDED_SUBSCRIPTIONS";
    public static final java.lang.String ACTION_NOTIFY_CARRIER_SETUP_INCOMPLETE = "android.telephony.euicc.action.NOTIFY_CARRIER_SETUP_INCOMPLETE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_OTA_STATUS_CHANGED = "android.telephony.euicc.action.OTA_STATUS_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_PROVISION_EMBEDDED_SUBSCRIPTION = "android.telephony.euicc.action.PROVISION_EMBEDDED_SUBSCRIPTION";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_RENAME_SUBSCRIPTION_PRIVILEGED = "android.telephony.euicc.action.RENAME_SUBSCRIPTION_PRIVILEGED";
    public static final java.lang.String ACTION_RESOLVE_ERROR = "android.telephony.euicc.action.RESOLVE_ERROR";
    public static final java.lang.String ACTION_START_EUICC_ACTIVATION = "android.telephony.euicc.action.START_EUICC_ACTIVATION";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_TOGGLE_SUBSCRIPTION_PRIVILEGED = "android.telephony.euicc.action.TOGGLE_SUBSCRIPTION_PRIVILEGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_TRANSFER_EMBEDDED_SUBSCRIPTIONS = "android.telephony.euicc.action.TRANSFER_EMBEDDED_SUBSCRIPTIONS";
    public static final int EMBEDDED_SUBSCRIPTION_RESULT_ERROR = 2;
    public static final int EMBEDDED_SUBSCRIPTION_RESULT_OK = 0;
    public static final int EMBEDDED_SUBSCRIPTION_RESULT_RESOLVABLE_ERROR = 1;
    public static final int ERROR_ADDRESS_MISSING = 10011;
    public static final int ERROR_CARRIER_LOCKED = 10000;
    public static final int ERROR_CERTIFICATE_ERROR = 10012;
    public static final int ERROR_CONNECTION_ERROR = 10014;
    public static final int ERROR_DISALLOWED_BY_PPR = 10010;
    public static final int ERROR_EUICC_INSUFFICIENT_MEMORY = 10004;
    public static final int ERROR_EUICC_MISSING = 10006;
    public static final int ERROR_INCOMPATIBLE_CARRIER = 10003;
    public static final int ERROR_INSTALL_PROFILE = 10009;
    public static final int ERROR_INVALID_ACTIVATION_CODE = 10001;
    public static final int ERROR_INVALID_CONFIRMATION_CODE = 10002;
    public static final int ERROR_INVALID_PORT = 10017;
    public static final int ERROR_INVALID_RESPONSE = 10015;
    public static final int ERROR_NO_PROFILES_AVAILABLE = 10013;
    public static final int ERROR_OPERATION_BUSY = 10016;
    public static final int ERROR_SIM_MISSING = 10008;
    public static final int ERROR_TIME_OUT = 10005;
    public static final int ERROR_UNSUPPORTED_VERSION = 10007;

    @android.annotation.SystemApi
    public static final int EUICC_ACTIVATION_TYPE_ACCOUNT_REQUIRED = 4;

    @android.annotation.SystemApi
    public static final int EUICC_ACTIVATION_TYPE_BACKUP = 2;

    @android.annotation.SystemApi
    public static final int EUICC_ACTIVATION_TYPE_DEFAULT = 1;

    @android.annotation.SystemApi
    public static final int EUICC_ACTIVATION_TYPE_TRANSFER = 3;
    public static final long EUICC_MEMORY_FIELD_UNAVAILABLE = -1;

    @android.annotation.SystemApi
    public static final int EUICC_OTA_FAILED = 2;

    @android.annotation.SystemApi
    public static final int EUICC_OTA_IN_PROGRESS = 1;

    @android.annotation.SystemApi
    public static final int EUICC_OTA_NOT_NEEDED = 4;

    @android.annotation.SystemApi
    public static final int EUICC_OTA_STATUS_UNAVAILABLE = 5;

    @android.annotation.SystemApi
    public static final int EUICC_OTA_SUCCEEDED = 3;

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ACTIVATION_TYPE = "android.telephony.euicc.extra.ACTIVATION_TYPE";
    public static final java.lang.String EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE";
    public static final java.lang.String EXTRA_EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTION = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTION";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTIONS = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTIONS";
    public static final java.lang.String EXTRA_EMBEDDED_SUBSCRIPTION_ERROR_CODE = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_ERROR_CODE";
    public static final java.lang.String EXTRA_EMBEDDED_SUBSCRIPTION_OPERATION_CODE = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_OPERATION_CODE";
    public static final java.lang.String EXTRA_EMBEDDED_SUBSCRIPTION_RESOLUTION_ACTION = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_RESOLUTION_ACTION";
    public static final java.lang.String EXTRA_EMBEDDED_SUBSCRIPTION_RESOLUTION_CALLBACK_INTENT = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_RESOLUTION_CALLBACK_INTENT";
    public static final java.lang.String EXTRA_EMBEDDED_SUBSCRIPTION_RESOLUTION_INTENT = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_RESOLUTION_INTENT";
    public static final java.lang.String EXTRA_EMBEDDED_SUBSCRIPTION_SMDX_REASON_CODE = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_SMDX_REASON_CODE";
    public static final java.lang.String EXTRA_EMBEDDED_SUBSCRIPTION_SMDX_SUBJECT_CODE = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_SMDX_SUBJECT_CODE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ENABLE_SUBSCRIPTION = "android.telephony.euicc.extra.ENABLE_SUBSCRIPTION";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_FORCE_PROVISION = "android.telephony.euicc.extra.FORCE_PROVISION";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_FROM_SUBSCRIPTION_ID = "android.telephony.euicc.extra.FROM_SUBSCRIPTION_ID";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PHYSICAL_SLOT_ID = "android.telephony.euicc.extra.PHYSICAL_SLOT_ID";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_SUBSCRIPTION_ID = "android.telephony.euicc.extra.SUBSCRIPTION_ID";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_SUBSCRIPTION_NICKNAME = "android.telephony.euicc.extra.SUBSCRIPTION_NICKNAME";
    public static final java.lang.String EXTRA_USE_QR_SCANNER = "android.telephony.euicc.extra.USE_QR_SCANNER";
    public static final long INACTIVE_PORT_AVAILABILITY_CHECK = 240273417;
    public static final java.lang.String META_DATA_CARRIER_ICON = "android.telephony.euicc.carriericon";
    public static final int OPERATION_APDU = 8;
    public static final int OPERATION_DOWNLOAD = 5;
    public static final int OPERATION_EUICC_CARD = 3;
    public static final int OPERATION_EUICC_GSMA = 7;
    public static final int OPERATION_HTTP = 11;
    public static final int OPERATION_METADATA = 6;
    public static final int OPERATION_SIM_SLOT = 2;
    public static final int OPERATION_SMDX = 9;
    public static final int OPERATION_SMDX_SUBJECT_REASON_CODE = 10;
    public static final int OPERATION_SWITCH = 4;
    public static final int OPERATION_SYSTEM = 1;
    public static final long SHOULD_RESOLVE_PORT_INDEX_FOR_APPS = 224562872;
    public static final long SWITCH_WITHOUT_PORT_INDEX_EXCEPTION_ON_DISABLE = 218393363;
    private static final java.lang.String TAG = "EuiccManager";
    private int mCardId;
    private final android.content.Context mContext;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EuiccActivationType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OperationCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OtaStatus {
    }

    public EuiccManager(android.content.Context context) {
        this.mContext = context;
        this.mCardId = getCardIdForDefaultEuicc();
    }

    private EuiccManager(android.content.Context context, int i) {
        this.mContext = context;
        this.mCardId = i;
    }

    public android.telephony.euicc.EuiccManager createForCardId(int i) {
        return new android.telephony.euicc.EuiccManager(this.mContext, i);
    }

    public boolean isEnabled() {
        return getIEuiccController() != null && refreshCardIdIfUninitialized();
    }

    public java.lang.String getEid() {
        if (!isEnabled()) {
            return null;
        }
        try {
            return getIEuiccController().getEid(this.mCardId, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getAvailableMemoryInBytes() {
        if (!isEnabled()) {
            return -1L;
        }
        try {
            return getIEuiccController().getAvailableMemoryInBytes(this.mCardId, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getOtaStatus() {
        if (!isEnabled()) {
            return 5;
        }
        try {
            return getIEuiccController().getOtaStatus(this.mCardId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void downloadSubscription(android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, android.app.PendingIntent pendingIntent) {
        if (!isEnabled()) {
            sendUnavailableError(pendingIntent);
            return;
        }
        try {
            getIEuiccController().downloadSubscription(this.mCardId, downloadableSubscription, z, this.mContext.getOpPackageName(), null, pendingIntent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startResolutionActivity(android.app.Activity activity, int i, android.content.Intent intent, android.app.PendingIntent pendingIntent) throws android.content.IntentSender.SendIntentException {
        android.app.PendingIntent pendingIntent2 = (android.app.PendingIntent) intent.getParcelableExtra(EXTRA_EMBEDDED_SUBSCRIPTION_RESOLUTION_INTENT, android.app.PendingIntent.class);
        if (pendingIntent2 == null) {
            throw new java.lang.IllegalArgumentException("Invalid result intent");
        }
        android.content.Intent intent2 = new android.content.Intent();
        intent2.putExtra(EXTRA_EMBEDDED_SUBSCRIPTION_RESOLUTION_CALLBACK_INTENT, pendingIntent);
        activity.startIntentSenderForResult(pendingIntent2.getIntentSender(), i, intent2, 0, 0, 0);
    }

    @android.annotation.SystemApi
    public void continueOperation(android.content.Intent intent, android.os.Bundle bundle) {
        if (!isEnabled()) {
            android.app.PendingIntent pendingIntent = (android.app.PendingIntent) intent.getParcelableExtra(EXTRA_EMBEDDED_SUBSCRIPTION_RESOLUTION_CALLBACK_INTENT, android.app.PendingIntent.class);
            if (pendingIntent != null) {
                sendUnavailableError(pendingIntent);
                return;
            }
            return;
        }
        try {
            getIEuiccController().continueOperation(this.mCardId, intent, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void getDownloadableSubscriptionMetadata(android.telephony.euicc.DownloadableSubscription downloadableSubscription, android.app.PendingIntent pendingIntent) {
        if (!isEnabled()) {
            sendUnavailableError(pendingIntent);
            return;
        }
        try {
            getIEuiccController().getDownloadableSubscriptionMetadata(this.mCardId, downloadableSubscription, this.mContext.getOpPackageName(), pendingIntent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void getDefaultDownloadableSubscriptionList(android.app.PendingIntent pendingIntent) {
        if (!isEnabled()) {
            sendUnavailableError(pendingIntent);
            return;
        }
        try {
            getIEuiccController().getDefaultDownloadableSubscriptionList(this.mCardId, this.mContext.getOpPackageName(), pendingIntent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.telephony.euicc.EuiccInfo getEuiccInfo() {
        if (!isEnabled()) {
            return null;
        }
        try {
            return getIEuiccController().getEuiccInfo(this.mCardId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void deleteSubscription(int i, android.app.PendingIntent pendingIntent) {
        if (!isEnabled()) {
            sendUnavailableError(pendingIntent);
            return;
        }
        try {
            getIEuiccController().deleteSubscription(this.mCardId, i, this.mContext.getOpPackageName(), pendingIntent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void switchToSubscription(int i, android.app.PendingIntent pendingIntent) {
        if (!isEnabled()) {
            sendUnavailableError(pendingIntent);
            return;
        }
        if (i == -1) {
            try {
                if (getIEuiccController().isCompatChangeEnabled(this.mContext.getOpPackageName(), SWITCH_WITHOUT_PORT_INDEX_EXCEPTION_ON_DISABLE)) {
                    android.util.Log.e(TAG, "switchToSubscription without portIndex is not allowed for disable operation");
                    throw new java.lang.IllegalArgumentException("Must use switchToSubscription with portIndex API for disable operation");
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        getIEuiccController().switchToSubscription(this.mCardId, i, this.mContext.getOpPackageName(), pendingIntent);
    }

    public void switchToSubscription(int i, int i2, android.app.PendingIntent pendingIntent) {
        if (!isEnabled()) {
            sendUnavailableError(pendingIntent);
            return;
        }
        try {
            if (!(this.mContext.checkCallingOrSelfPermission(android.Manifest.permission.WRITE_EMBEDDED_SUBSCRIPTIONS) == 0) && !getIEuiccController().hasCarrierPrivilegesForPackageOnAnyPhone(this.mContext.getOpPackageName())) {
                android.util.Log.e(TAG, "Not permitted to use switchToSubscription with portIndex");
                throw new java.lang.SecurityException("Must have carrier privileges to use switchToSubscription with portIndex");
            }
            getIEuiccController().switchToSubscriptionWithPort(this.mCardId, i, i2, this.mContext.getOpPackageName(), pendingIntent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateSubscriptionNickname(int i, java.lang.String str, android.app.PendingIntent pendingIntent) {
        if (!isEnabled()) {
            sendUnavailableError(pendingIntent);
            return;
        }
        try {
            getIEuiccController().updateSubscriptionNickname(this.mCardId, i, str, this.mContext.getOpPackageName(), pendingIntent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void eraseSubscriptions(android.app.PendingIntent pendingIntent) {
        if (!isEnabled()) {
            sendUnavailableError(pendingIntent);
            return;
        }
        try {
            getIEuiccController().eraseSubscriptions(this.mCardId, pendingIntent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void eraseSubscriptions(int i, android.app.PendingIntent pendingIntent) {
        if (!isEnabled()) {
            sendUnavailableError(pendingIntent);
            return;
        }
        try {
            getIEuiccController().eraseSubscriptionsWithOptions(this.mCardId, i, pendingIntent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void retainSubscriptionsForFactoryReset(android.app.PendingIntent pendingIntent) {
        if (!isEnabled()) {
            sendUnavailableError(pendingIntent);
            return;
        }
        try {
            getIEuiccController().retainSubscriptionsForFactoryReset(this.mCardId, pendingIntent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setSupportedCountries(java.util.List<java.lang.String> list) {
        if (!isEnabled()) {
            return;
        }
        try {
            getIEuiccController().setSupportedCountries(true, (java.util.List) list.stream().map(new android.telephony.euicc.EuiccManager$$ExternalSyntheticLambda0()).collect(java.util.stream.Collectors.toList()));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setUnsupportedCountries(java.util.List<java.lang.String> list) {
        if (!isEnabled()) {
            return;
        }
        try {
            getIEuiccController().setSupportedCountries(false, (java.util.List) list.stream().map(new android.telephony.euicc.EuiccManager$$ExternalSyntheticLambda0()).collect(java.util.stream.Collectors.toList()));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.String> getSupportedCountries() {
        if (!isEnabled()) {
            return java.util.Collections.emptyList();
        }
        try {
            return getIEuiccController().getSupportedCountries(true);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.String> getUnsupportedCountries() {
        if (!isEnabled()) {
            return java.util.Collections.emptyList();
        }
        try {
            return getIEuiccController().getSupportedCountries(false);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isSupportedCountry(java.lang.String str) {
        if (!isEnabled()) {
            return false;
        }
        try {
            return getIEuiccController().isSupportedCountry(str.toUpperCase(java.util.Locale.ROOT));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean refreshCardIdIfUninitialized() {
        if (this.mCardId == -2) {
            this.mCardId = getCardIdForDefaultEuicc();
        }
        return this.mCardId != -2;
    }

    private static void sendUnavailableError(android.app.PendingIntent pendingIntent) {
        try {
            pendingIntent.send(2);
        } catch (android.app.PendingIntent.CanceledException e) {
        }
    }

    private static com.android.internal.telephony.euicc.IEuiccController getIEuiccController() {
        return com.android.internal.telephony.euicc.IEuiccController.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getEuiccControllerService().get());
    }

    private int getCardIdForDefaultEuicc() {
        if (com.android.internal.telephony.flags.Flags.enforceTelephonyFeatureMappingForPublicApis()) {
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager != null && packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_TELEPHONY_EUICC)) {
                return ((android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class)).getCardIdForDefaultEuicc();
            }
            return -2;
        }
        return ((android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class)).getCardIdForDefaultEuicc();
    }

    public boolean isSimPortAvailable(int i) {
        try {
            return getIEuiccController().isSimPortAvailable(this.mCardId, i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setPsimConversionSupportedCarriers(java.util.Set<java.lang.Integer> set) {
        if (!isEnabled()) {
            throw new java.lang.IllegalStateException("Euicc is not enabled");
        }
        try {
            getIEuiccController().setPsimConversionSupportedCarriers(set.stream().mapToInt(new android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public boolean isPsimConversionSupported(int i) {
        if (!isEnabled()) {
            return false;
        }
        try {
            return getIEuiccController().isPsimConversionSupported(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }
}
