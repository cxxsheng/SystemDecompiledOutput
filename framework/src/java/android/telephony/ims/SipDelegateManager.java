package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class SipDelegateManager {
    public static final int DENIED_REASON_INVALID = 4;
    public static final int DENIED_REASON_IN_USE_BY_ANOTHER_DELEGATE = 1;
    public static final int DENIED_REASON_NOT_ALLOWED = 2;
    public static final int DENIED_REASON_SINGLE_REGISTRATION_NOT_ALLOWED = 3;
    public static final int DENIED_REASON_UNKNOWN = 0;
    public static final int MESSAGE_FAILURE_REASON_DELEGATE_CLOSED = 2;
    public static final int MESSAGE_FAILURE_REASON_DELEGATE_DEAD = 1;
    public static final int MESSAGE_FAILURE_REASON_INTERNAL_DELEGATE_STATE_TRANSITION = 11;
    public static final int MESSAGE_FAILURE_REASON_INVALID_BODY_CONTENT = 5;
    public static final int MESSAGE_FAILURE_REASON_INVALID_FEATURE_TAG = 6;
    public static final int MESSAGE_FAILURE_REASON_INVALID_HEADER_FIELDS = 4;
    public static final int MESSAGE_FAILURE_REASON_INVALID_START_LINE = 3;
    public static final int MESSAGE_FAILURE_REASON_NETWORK_NOT_AVAILABLE = 8;
    public static final int MESSAGE_FAILURE_REASON_NOT_REGISTERED = 9;
    public static final int MESSAGE_FAILURE_REASON_STALE_IMS_CONFIGURATION = 10;
    public static final android.util.ArrayMap<java.lang.Integer, java.lang.String> MESSAGE_FAILURE_REASON_STRING_MAP = new android.util.ArrayMap<>(11);
    public static final int MESSAGE_FAILURE_REASON_TAG_NOT_ENABLED_FOR_DELEGATE = 7;
    public static final int MESSAGE_FAILURE_REASON_UNKNOWN = 0;
    public static final int SIP_DELEGATE_DESTROY_REASON_REQUESTED_BY_APP = 2;
    public static final int SIP_DELEGATE_DESTROY_REASON_SERVICE_DEAD = 1;
    public static final int SIP_DELEGATE_DESTROY_REASON_SUBSCRIPTION_TORN_DOWN = 4;
    public static final int SIP_DELEGATE_DESTROY_REASON_UNKNOWN = 0;
    public static final int SIP_DELEGATE_DESTROY_REASON_USER_DISABLED_RCS = 3;
    private final android.telephony.BinderCacheManager<android.telephony.ims.aidl.IImsRcsController> mBinderCache;
    private final android.content.Context mContext;
    private final int mSubId;
    private final android.telephony.BinderCacheManager<com.android.internal.telephony.ITelephony> mTelephonyBinderCache;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeniedReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MessageFailureReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SipDelegateDestroyReason {
    }

    static {
        MESSAGE_FAILURE_REASON_STRING_MAP.append(0, "MESSAGE_FAILURE_REASON_UNKNOWN");
        MESSAGE_FAILURE_REASON_STRING_MAP.append(1, "MESSAGE_FAILURE_REASON_DELEGATE_DEAD");
        MESSAGE_FAILURE_REASON_STRING_MAP.append(2, "MESSAGE_FAILURE_REASON_DELEGATE_CLOSED");
        MESSAGE_FAILURE_REASON_STRING_MAP.append(4, "MESSAGE_FAILURE_REASON_INVALID_HEADER_FIELDS");
        MESSAGE_FAILURE_REASON_STRING_MAP.append(5, "MESSAGE_FAILURE_REASON_INVALID_BODY_CONTENT");
        MESSAGE_FAILURE_REASON_STRING_MAP.append(6, "MESSAGE_FAILURE_REASON_INVALID_FEATURE_TAG");
        MESSAGE_FAILURE_REASON_STRING_MAP.append(7, "MESSAGE_FAILURE_REASON_TAG_NOT_ENABLED_FOR_DELEGATE");
        MESSAGE_FAILURE_REASON_STRING_MAP.append(8, "MESSAGE_FAILURE_REASON_NETWORK_NOT_AVAILABLE");
        MESSAGE_FAILURE_REASON_STRING_MAP.append(9, "MESSAGE_FAILURE_REASON_NOT_REGISTERED");
        MESSAGE_FAILURE_REASON_STRING_MAP.append(10, "MESSAGE_FAILURE_REASON_STALE_IMS_CONFIGURATION");
        MESSAGE_FAILURE_REASON_STRING_MAP.append(11, "MESSAGE_FAILURE_REASON_INTERNAL_DELEGATE_STATE_TRANSITION");
    }

    public SipDelegateManager(android.content.Context context, int i, android.telephony.BinderCacheManager<android.telephony.ims.aidl.IImsRcsController> binderCacheManager, android.telephony.BinderCacheManager<com.android.internal.telephony.ITelephony> binderCacheManager2) {
        this.mContext = context;
        this.mSubId = i;
        this.mBinderCache = binderCacheManager;
        this.mTelephonyBinderCache = binderCacheManager2;
    }

    public boolean isSupported() throws android.telephony.ims.ImsException {
        try {
            android.telephony.ims.aidl.IImsRcsController binder = this.mBinderCache.getBinder();
            if (binder == null) {
                throw new android.telephony.ims.ImsException("Telephony server is down", 1);
            }
            return binder.isSipDelegateSupported(this.mSubId);
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public void createSipDelegate(android.telephony.ims.DelegateRequest delegateRequest, java.util.concurrent.Executor executor, android.telephony.ims.stub.DelegateConnectionStateCallback delegateConnectionStateCallback, android.telephony.ims.stub.DelegateConnectionMessageCallback delegateConnectionMessageCallback) throws android.telephony.ims.ImsException {
        java.util.Objects.requireNonNull(delegateRequest, "The DelegateRequest must not be null.");
        java.util.Objects.requireNonNull(executor, "The Executor must not be null.");
        java.util.Objects.requireNonNull(delegateConnectionStateCallback, "The DelegateConnectionStateCallback must not be null.");
        java.util.Objects.requireNonNull(delegateConnectionMessageCallback, "The DelegateConnectionMessageCallback must not be null.");
        try {
            final android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper sipDelegateConnectionAidlWrapper = new android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper(executor, delegateConnectionStateCallback, delegateConnectionMessageCallback);
            android.telephony.BinderCacheManager<android.telephony.ims.aidl.IImsRcsController> binderCacheManager = this.mBinderCache;
            java.util.Objects.requireNonNull(sipDelegateConnectionAidlWrapper);
            android.telephony.ims.aidl.IImsRcsController listenOnBinder = binderCacheManager.listenOnBinder(sipDelegateConnectionAidlWrapper, new java.lang.Runnable() { // from class: android.telephony.ims.SipDelegateManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.binderDied();
                }
            });
            if (listenOnBinder == null) {
                throw new android.telephony.ims.ImsException("Telephony server is down", 1);
            }
            listenOnBinder.createSipDelegate(this.mSubId, delegateRequest, this.mContext.getOpPackageName(), sipDelegateConnectionAidlWrapper.getStateCallbackBinder(), sipDelegateConnectionAidlWrapper.getMessageCallbackBinder());
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public void destroySipDelegate(android.telephony.ims.SipDelegateConnection sipDelegateConnection, int i) {
        java.util.Objects.requireNonNull(sipDelegateConnection, "SipDelegateConnection can not be null.");
        if (sipDelegateConnection instanceof android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper) {
            android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper sipDelegateConnectionAidlWrapper = (android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper) sipDelegateConnection;
            try {
                this.mBinderCache.removeRunnable(sipDelegateConnectionAidlWrapper).destroySipDelegate(this.mSubId, sipDelegateConnectionAidlWrapper.getSipDelegateBinder(), i);
                return;
            } catch (android.os.RemoteException e) {
                try {
                    sipDelegateConnectionAidlWrapper.getStateCallbackBinder().onDestroyed(2);
                    return;
                } catch (android.os.RemoteException e2) {
                    return;
                }
            }
        }
        throw new java.lang.IllegalArgumentException("Unknown SipDelegateConnection implementation passed into this method");
    }

    public void triggerFullNetworkRegistration(android.telephony.ims.SipDelegateConnection sipDelegateConnection, int i, java.lang.String str) {
        java.util.Objects.requireNonNull(sipDelegateConnection, "SipDelegateConnection can not be null.");
        if (sipDelegateConnection instanceof android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper) {
            try {
                this.mBinderCache.getBinder().triggerNetworkRegistration(this.mSubId, ((android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper) sipDelegateConnection).getSipDelegateBinder(), i, str);
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        throw new java.lang.IllegalArgumentException("Unknown SipDelegateConnection implementation passed into this method");
    }

    public void registerImsStateCallback(java.util.concurrent.Executor executor, android.telephony.ims.ImsStateCallback imsStateCallback) throws android.telephony.ims.ImsException {
        java.util.Objects.requireNonNull(imsStateCallback, "Must include a non-null ImsStateCallback.");
        java.util.Objects.requireNonNull(executor, "Must include a non-null Executor.");
        imsStateCallback.init(executor);
        android.telephony.BinderCacheManager<com.android.internal.telephony.ITelephony> binderCacheManager = this.mTelephonyBinderCache;
        java.util.Objects.requireNonNull(imsStateCallback);
        com.android.internal.telephony.ITelephony listenOnBinder = binderCacheManager.listenOnBinder(imsStateCallback, new android.telephony.ims.ImsMmTelManager$$ExternalSyntheticLambda3(imsStateCallback));
        if (listenOnBinder == null) {
            throw new android.telephony.ims.ImsException("Telephony server is down", 1);
        }
        try {
            listenOnBinder.registerImsStateCallback(this.mSubId, 2, imsStateCallback.getCallbackBinder(), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public void unregisterImsStateCallback(android.telephony.ims.ImsStateCallback imsStateCallback) {
        java.util.Objects.requireNonNull(imsStateCallback, "Must include a non-null ImsStateCallback.");
        com.android.internal.telephony.ITelephony removeRunnable = this.mTelephonyBinderCache.removeRunnable(imsStateCallback);
        if (removeRunnable != null) {
            try {
                removeRunnable.unregisterImsStateCallback(imsStateCallback.getCallbackBinder());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void registerSipDialogStateCallback(java.util.concurrent.Executor executor, final android.telephony.ims.SipDialogStateCallback sipDialogStateCallback) throws android.telephony.ims.ImsException {
        java.util.Objects.requireNonNull(sipDialogStateCallback, "Must include a non-null SipDialogStateCallback.");
        java.util.Objects.requireNonNull(executor, "Must include a non-null Executor.");
        sipDialogStateCallback.attachExecutor(executor);
        try {
            android.telephony.BinderCacheManager<android.telephony.ims.aidl.IImsRcsController> binderCacheManager = this.mBinderCache;
            java.util.Objects.requireNonNull(sipDialogStateCallback);
            android.telephony.ims.aidl.IImsRcsController listenOnBinder = binderCacheManager.listenOnBinder(sipDialogStateCallback, new java.lang.Runnable() { // from class: android.telephony.ims.SipDelegateManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.SipDialogStateCallback.this.binderDied();
                }
            });
            if (listenOnBinder == null) {
                throw new android.telephony.ims.ImsException("Telephony server is down", 1);
            }
            listenOnBinder.registerSipDialogStateCallback(this.mSubId, sipDialogStateCallback.getCallbackBinder());
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        } catch (java.lang.IllegalStateException e3) {
            throw new java.lang.IllegalStateException(e3.getMessage());
        }
    }

    public void unregisterSipDialogStateCallback(android.telephony.ims.SipDialogStateCallback sipDialogStateCallback) throws android.telephony.ims.ImsException {
        java.util.Objects.requireNonNull(sipDialogStateCallback, "Must include a non-null SipDialogStateCallback.");
        android.telephony.ims.aidl.IImsRcsController removeRunnable = this.mBinderCache.removeRunnable(sipDialogStateCallback);
        try {
            if (removeRunnable == null) {
                throw new android.telephony.ims.ImsException("Telephony server is down", 1);
            }
            removeRunnable.unregisterSipDialogStateCallback(this.mSubId, sipDialogStateCallback.getCallbackBinder());
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        } catch (java.lang.IllegalStateException e3) {
            throw new java.lang.IllegalStateException(e3.getMessage());
        }
    }
}
