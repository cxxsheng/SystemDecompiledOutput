package android.telephony.ims;

/* loaded from: classes3.dex */
public class RcsUceAdapter {

    @java.lang.Deprecated
    public static final int CAPABILITY_TYPE_OPTIONS_UCE = 1;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int CAPABILITY_TYPE_PRESENCE_UCE = 2;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_ETAG_EXPIRED = 1;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_2G = 7;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_3G = 6;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_EHRPD = 4;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_HSPAPLUS = 5;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_INTERNET_PDN = 12;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_IWLAN = 9;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_LTE_VOPS_DISABLED = 2;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_LTE_VOPS_ENABLED = 3;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_NR5G_VOPS_DISABLED = 10;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_NR5G_VOPS_ENABLED = 11;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_WLAN = 8;

    @android.annotation.SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_UNKNOWN = 0;

    @android.annotation.SystemApi
    public static final int ERROR_FORBIDDEN = 6;

    @android.annotation.SystemApi
    public static final int ERROR_GENERIC_FAILURE = 1;

    @android.annotation.SystemApi
    public static final int ERROR_INSUFFICIENT_MEMORY = 10;

    @android.annotation.SystemApi
    public static final int ERROR_LOST_NETWORK = 11;

    @android.annotation.SystemApi
    public static final int ERROR_NOT_AUTHORIZED = 5;

    @android.annotation.SystemApi
    public static final int ERROR_NOT_AVAILABLE = 3;

    @android.annotation.SystemApi
    public static final int ERROR_NOT_ENABLED = 2;

    @android.annotation.SystemApi
    public static final int ERROR_NOT_FOUND = 7;

    @android.annotation.SystemApi
    public static final int ERROR_NOT_REGISTERED = 4;

    @android.annotation.SystemApi
    public static final int ERROR_REQUEST_TIMEOUT = 9;

    @android.annotation.SystemApi
    public static final int ERROR_REQUEST_TOO_LARGE = 8;

    @android.annotation.SystemApi
    public static final int ERROR_SERVER_UNAVAILABLE = 12;

    @android.annotation.SystemApi
    public static final int PUBLISH_STATE_NOT_PUBLISHED = 2;

    @android.annotation.SystemApi
    public static final int PUBLISH_STATE_OK = 1;

    @android.annotation.SystemApi
    public static final int PUBLISH_STATE_OTHER_ERROR = 6;

    @android.annotation.SystemApi
    public static final int PUBLISH_STATE_PUBLISHING = 7;

    @android.annotation.SystemApi
    public static final int PUBLISH_STATE_RCS_PROVISION_ERROR = 4;

    @android.annotation.SystemApi
    public static final int PUBLISH_STATE_REQUEST_TIMEOUT = 5;

    @android.annotation.SystemApi
    public static final int PUBLISH_STATE_VOICE_PROVISION_ERROR = 3;
    private static final java.lang.String TAG = "RcsUceAdapter";
    private final android.content.Context mContext;
    private final java.util.Map<android.telephony.ims.RcsUceAdapter.OnPublishStateChangedListener, android.telephony.ims.RcsUceAdapter.PublishStateCallbackAdapter> mPublishStateCallbacks = new java.util.HashMap();
    private final int mSubId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PublishState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @java.lang.Deprecated
    public @interface RcsImsCapabilityFlag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StackPublishTriggerType {
    }

    @android.annotation.SystemApi
    public interface OnPublishStateChangedListener {
        @java.lang.Deprecated
        void onPublishStateChange(int i);

        default void onPublishStateChange(android.telephony.ims.PublishAttributes publishAttributes) {
            onPublishStateChange(publishAttributes.getPublishState());
        }
    }

    public static class PublishStateCallbackAdapter {
        private final android.telephony.ims.RcsUceAdapter.PublishStateCallbackAdapter.PublishStateBinder mBinder;

        /* JADX INFO: Access modifiers changed from: private */
        static class PublishStateBinder extends android.telephony.ims.aidl.IRcsUcePublishStateCallback.Stub {
            private final java.util.concurrent.Executor mExecutor;
            private final android.telephony.ims.RcsUceAdapter.OnPublishStateChangedListener mPublishStateChangeListener;

            PublishStateBinder(java.util.concurrent.Executor executor, android.telephony.ims.RcsUceAdapter.OnPublishStateChangedListener onPublishStateChangedListener) {
                this.mExecutor = executor;
                this.mPublishStateChangeListener = onPublishStateChangedListener;
            }

            @Override // android.telephony.ims.aidl.IRcsUcePublishStateCallback
            public void onPublishUpdated(final android.telephony.ims.PublishAttributes publishAttributes) {
                if (this.mPublishStateChangeListener == null) {
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RcsUceAdapter$PublishStateCallbackAdapter$PublishStateBinder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.RcsUceAdapter.PublishStateCallbackAdapter.PublishStateBinder.this.lambda$onPublishUpdated$0(publishAttributes);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onPublishUpdated$0(android.telephony.ims.PublishAttributes publishAttributes) {
                this.mPublishStateChangeListener.onPublishStateChange(publishAttributes);
            }
        }

        public PublishStateCallbackAdapter(java.util.concurrent.Executor executor, android.telephony.ims.RcsUceAdapter.OnPublishStateChangedListener onPublishStateChangedListener) {
            this.mBinder = new android.telephony.ims.RcsUceAdapter.PublishStateCallbackAdapter.PublishStateBinder(executor, onPublishStateChangedListener);
        }

        public final android.telephony.ims.aidl.IRcsUcePublishStateCallback getBinder() {
            return this.mBinder;
        }
    }

    @android.annotation.SystemApi
    public interface CapabilitiesCallback {
        void onCapabilitiesReceived(java.util.List<android.telephony.ims.RcsContactUceCapability> list);

        default void onComplete() {
        }

        default void onError(int i, long j) {
        }

        default void onComplete(android.telephony.ims.SipDetails sipDetails) {
            onComplete();
        }

        default void onError(int i, long j, android.telephony.ims.SipDetails sipDetails) {
            onError(i, j);
        }
    }

    RcsUceAdapter(android.content.Context context, int i) {
        this.mContext = context;
        this.mSubId = i;
    }

    @android.annotation.SystemApi
    public void requestCapabilities(java.util.Collection<android.net.Uri> collection, java.util.concurrent.Executor executor, android.telephony.ims.RcsUceAdapter.CapabilitiesCallback capabilitiesCallback) throws android.telephony.ims.ImsException {
        if (capabilitiesCallback == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null CapabilitiesCallback.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        if (collection == null) {
            throw new java.lang.IllegalArgumentException("Must include non-null contact number list.");
        }
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.e(TAG, "requestCapabilities: IImsRcsController is null");
            throw new android.telephony.ims.ImsException("Can not find remote IMS service", 1);
        }
        try {
            iImsRcsController.requestCapabilities(this.mSubId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), new java.util.ArrayList(collection), new android.telephony.ims.RcsUceAdapter.AnonymousClass1(executor, capabilitiesCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling IImsRcsController#requestCapabilities", e);
            throw new android.telephony.ims.ImsException("Remote IMS Service is not available", 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.toString(), e2.errorCode);
        }
    }

    /* renamed from: android.telephony.ims.RcsUceAdapter$1, reason: invalid class name */
    class AnonymousClass1 extends android.telephony.ims.aidl.IRcsUceControllerCallback.Stub {
        final /* synthetic */ android.telephony.ims.RcsUceAdapter.CapabilitiesCallback val$c;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass1(java.util.concurrent.Executor executor, android.telephony.ims.RcsUceAdapter.CapabilitiesCallback capabilitiesCallback) {
            this.val$executor = executor;
            this.val$c = capabilitiesCallback;
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onCapabilitiesReceived(final java.util.List<android.telephony.ims.RcsContactUceCapability> list) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.ims.RcsUceAdapter.CapabilitiesCallback capabilitiesCallback = this.val$c;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RcsUceAdapter$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.RcsUceAdapter.CapabilitiesCallback.this.onCapabilitiesReceived(list);
                    }
                });
            } finally {
                restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onComplete(final android.telephony.ims.SipDetails sipDetails) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.ims.RcsUceAdapter.CapabilitiesCallback capabilitiesCallback = this.val$c;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RcsUceAdapter$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.RcsUceAdapter.CapabilitiesCallback.this.onComplete(sipDetails);
                    }
                });
            } finally {
                restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onError(final int i, final long j, final android.telephony.ims.SipDetails sipDetails) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.ims.RcsUceAdapter.CapabilitiesCallback capabilitiesCallback = this.val$c;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RcsUceAdapter$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.RcsUceAdapter.CapabilitiesCallback.this.onError(i, j, sipDetails);
                    }
                });
            } finally {
                restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @android.annotation.SystemApi
    public void requestAvailability(android.net.Uri uri, java.util.concurrent.Executor executor, android.telephony.ims.RcsUceAdapter.CapabilitiesCallback capabilitiesCallback) throws android.telephony.ims.ImsException {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        if (uri == null) {
            throw new java.lang.IllegalArgumentException("Must include non-null contact number.");
        }
        if (capabilitiesCallback == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null CapabilitiesCallback.");
        }
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.e(TAG, "requestAvailability: IImsRcsController is null");
            throw new android.telephony.ims.ImsException("Cannot find remote IMS service", 1);
        }
        try {
            iImsRcsController.requestAvailability(this.mSubId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), uri, new android.telephony.ims.RcsUceAdapter.AnonymousClass2(executor, capabilitiesCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling IImsRcsController#requestAvailability", e);
            throw new android.telephony.ims.ImsException("Remote IMS Service is not available", 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.toString(), e2.errorCode);
        }
    }

    /* renamed from: android.telephony.ims.RcsUceAdapter$2, reason: invalid class name */
    class AnonymousClass2 extends android.telephony.ims.aidl.IRcsUceControllerCallback.Stub {
        final /* synthetic */ android.telephony.ims.RcsUceAdapter.CapabilitiesCallback val$c;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass2(java.util.concurrent.Executor executor, android.telephony.ims.RcsUceAdapter.CapabilitiesCallback capabilitiesCallback) {
            this.val$executor = executor;
            this.val$c = capabilitiesCallback;
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onCapabilitiesReceived(final java.util.List<android.telephony.ims.RcsContactUceCapability> list) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.ims.RcsUceAdapter.CapabilitiesCallback capabilitiesCallback = this.val$c;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RcsUceAdapter$2$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.RcsUceAdapter.CapabilitiesCallback.this.onCapabilitiesReceived(list);
                    }
                });
            } finally {
                restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onComplete(final android.telephony.ims.SipDetails sipDetails) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.ims.RcsUceAdapter.CapabilitiesCallback capabilitiesCallback = this.val$c;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RcsUceAdapter$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.RcsUceAdapter.CapabilitiesCallback.this.onComplete(sipDetails);
                    }
                });
            } finally {
                restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onError(final int i, final long j, final android.telephony.ims.SipDetails sipDetails) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.ims.RcsUceAdapter.CapabilitiesCallback capabilitiesCallback = this.val$c;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RcsUceAdapter$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.RcsUceAdapter.CapabilitiesCallback.this.onError(i, j, sipDetails);
                    }
                });
            } finally {
                restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @android.annotation.SystemApi
    public int getUcePublishState() throws android.telephony.ims.ImsException {
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.e(TAG, "getUcePublishState: IImsRcsController is null");
            throw new android.telephony.ims.ImsException("Can not find remote IMS service", 1);
        }
        try {
            return iImsRcsController.getUcePublishState(this.mSubId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling IImsRcsController#getUcePublishState", e);
            throw new android.telephony.ims.ImsException("Remote IMS Service is not available", 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @android.annotation.SystemApi
    public void addOnPublishStateChangedListener(java.util.concurrent.Executor executor, android.telephony.ims.RcsUceAdapter.OnPublishStateChangedListener onPublishStateChangedListener) throws android.telephony.ims.ImsException {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        if (onPublishStateChangedListener == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null OnPublishStateChangedListener.");
        }
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.e(TAG, "addOnPublishStateChangedListener : IImsRcsController is null");
            throw new android.telephony.ims.ImsException("Cannot find remote IMS service", 1);
        }
        try {
            iImsRcsController.registerUcePublishStateCallback(this.mSubId, addPublishStateCallback(executor, onPublishStateChangedListener).getBinder());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling IImsRcsController#registerUcePublishStateCallback", e);
            throw new android.telephony.ims.ImsException("Remote IMS Service is not available", 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @android.annotation.SystemApi
    public void removeOnPublishStateChangedListener(android.telephony.ims.RcsUceAdapter.OnPublishStateChangedListener onPublishStateChangedListener) throws android.telephony.ims.ImsException {
        if (onPublishStateChangedListener == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null OnPublishStateChangedListener.");
        }
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.e(TAG, "removeOnPublishStateChangedListener: IImsRcsController is null");
            throw new android.telephony.ims.ImsException("Cannot find remote IMS service", 1);
        }
        android.telephony.ims.RcsUceAdapter.PublishStateCallbackAdapter removePublishStateCallback = removePublishStateCallback(onPublishStateChangedListener);
        if (removePublishStateCallback == null) {
            return;
        }
        try {
            iImsRcsController.unregisterUcePublishStateCallback(this.mSubId, removePublishStateCallback.getBinder());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling IImsRcsController#unregisterUcePublishStateCallback", e);
            throw new android.telephony.ims.ImsException("Remote IMS Service is not available", 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public boolean isUceSettingEnabled() throws android.telephony.ims.ImsException {
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.e(TAG, "isUceSettingEnabled: IImsRcsController is null");
            throw new android.telephony.ims.ImsException("Can not find remote IMS service", 1);
        }
        try {
            return iImsRcsController.isUceSettingEnabled(this.mSubId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling IImsRcsController#isUceSettingEnabled", e);
            throw new android.telephony.ims.ImsException("Remote IMS Service is not available", 1);
        }
    }

    @android.annotation.SystemApi
    public void setUceSettingEnabled(boolean z) throws android.telephony.ims.ImsException {
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.e(TAG, "setUceSettingEnabled: IImsRcsController is null");
            throw new android.telephony.ims.ImsException("Can not find remote IMS service", 1);
        }
        try {
            iImsRcsController.setUceSettingEnabled(this.mSubId, z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling IImsRcsController#setUceSettingEnabled", e);
            throw new android.telephony.ims.ImsException("Remote IMS Service is not available", 1);
        }
    }

    private android.telephony.ims.RcsUceAdapter.PublishStateCallbackAdapter addPublishStateCallback(java.util.concurrent.Executor executor, android.telephony.ims.RcsUceAdapter.OnPublishStateChangedListener onPublishStateChangedListener) {
        android.telephony.ims.RcsUceAdapter.PublishStateCallbackAdapter publishStateCallbackAdapter = new android.telephony.ims.RcsUceAdapter.PublishStateCallbackAdapter(executor, onPublishStateChangedListener);
        synchronized (this.mPublishStateCallbacks) {
            this.mPublishStateCallbacks.put(onPublishStateChangedListener, publishStateCallbackAdapter);
        }
        return publishStateCallbackAdapter;
    }

    private android.telephony.ims.RcsUceAdapter.PublishStateCallbackAdapter removePublishStateCallback(android.telephony.ims.RcsUceAdapter.OnPublishStateChangedListener onPublishStateChangedListener) {
        android.telephony.ims.RcsUceAdapter.PublishStateCallbackAdapter remove;
        synchronized (this.mPublishStateCallbacks) {
            remove = this.mPublishStateCallbacks.remove(onPublishStateChangedListener);
        }
        return remove;
    }

    private android.telephony.ims.aidl.IImsRcsController getIImsRcsController() {
        return android.telephony.ims.aidl.IImsRcsController.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyImsServiceRegisterer().get());
    }
}
