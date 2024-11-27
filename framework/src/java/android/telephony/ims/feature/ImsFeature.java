package android.telephony.ims.feature;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class ImsFeature {

    @android.annotation.SystemApi
    public static final int CAPABILITY_ERROR_GENERIC = -1;

    @android.annotation.SystemApi
    public static final int CAPABILITY_SUCCESS = 0;

    @android.annotation.SystemApi
    public static final int FEATURE_EMERGENCY_MMTEL = 0;
    public static final int FEATURE_INVALID = -1;
    public static final int FEATURE_MAX = 3;

    @android.annotation.SystemApi
    public static final int FEATURE_MMTEL = 1;

    @android.annotation.SystemApi
    public static final int FEATURE_RCS = 2;
    private static final java.lang.String LOG_TAG = "ImsFeature";

    @android.annotation.SystemApi
    public static final int STATE_INITIALIZING = 1;

    @android.annotation.SystemApi
    public static final int STATE_READY = 2;

    @android.annotation.SystemApi
    public static final int STATE_UNAVAILABLE = 0;
    protected android.content.Context mContext;
    public static final java.util.Map<java.lang.Integer, java.lang.String> FEATURE_LOG_MAP = java.util.Map.of(0, "EMERGENCY_MMTEL", 1, "MMTEL", 2, "RCS");
    public static final java.util.Map<java.lang.Integer, java.lang.String> STATE_LOG_MAP = java.util.Map.of(0, "UNAVAILABLE", 1, "INITIALIZING", 2, "READY");
    protected final java.lang.Object mLock = new java.lang.Object();
    private final com.android.internal.telephony.util.RemoteCallbackListExt<com.android.ims.internal.IImsFeatureStatusCallback> mStatusCallbacks = new com.android.internal.telephony.util.RemoteCallbackListExt<>();
    private int mState = 0;
    private int mSlotId = -1;
    private final com.android.internal.telephony.util.RemoteCallbackListExt<android.telephony.ims.aidl.IImsCapabilityCallback> mCapabilityCallbacks = new com.android.internal.telephony.util.RemoteCallbackListExt<>();
    private android.telephony.ims.feature.ImsFeature.Capabilities mCapabilityStatus = new android.telephony.ims.feature.ImsFeature.Capabilities();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FeatureType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsCapabilityError {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsState {
    }

    public abstract void changeEnabledCapabilities(android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, android.telephony.ims.feature.ImsFeature.CapabilityCallbackProxy capabilityCallbackProxy);

    protected abstract android.os.IInterface getBinder();

    public abstract void onFeatureReady();

    public abstract void onFeatureRemoved();

    public abstract boolean queryCapabilityConfiguration(int i, int i2);

    protected static class CapabilityCallbackProxy {
        private final android.telephony.ims.aidl.IImsCapabilityCallback mCallback;

        public CapabilityCallbackProxy(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            this.mCallback = iImsCapabilityCallback;
        }

        public void onChangeCapabilityConfigurationError(int i, int i2, int i3) {
            if (this.mCallback == null) {
                return;
            }
            try {
                this.mCallback.onChangeCapabilityConfigurationError(i, i2, i3);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.telephony.ims.feature.ImsFeature.LOG_TAG, "onChangeCapabilityConfigurationError called on dead binder.");
            }
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static class Capabilities {
        protected int mCapabilities;

        public Capabilities() {
            this.mCapabilities = 0;
        }

        protected Capabilities(int i) {
            this.mCapabilities = 0;
            this.mCapabilities = i;
        }

        public void addCapabilities(int i) {
            this.mCapabilities = i | this.mCapabilities;
        }

        public void removeCapabilities(int i) {
            this.mCapabilities = (~i) & this.mCapabilities;
        }

        public boolean isCapable(int i) {
            return (this.mCapabilities & i) == i;
        }

        public android.telephony.ims.feature.ImsFeature.Capabilities copy() {
            return new android.telephony.ims.feature.ImsFeature.Capabilities(this.mCapabilities);
        }

        public int getMask() {
            return this.mCapabilities;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof android.telephony.ims.feature.ImsFeature.Capabilities) && this.mCapabilities == ((android.telephony.ims.feature.ImsFeature.Capabilities) obj).mCapabilities;
        }

        public int hashCode() {
            return this.mCapabilities;
        }

        public java.lang.String toString() {
            return "Capabilities: " + java.lang.Integer.toBinaryString(this.mCapabilities);
        }
    }

    public void initialize(android.content.Context context, int i) {
        this.mContext = context;
        this.mSlotId = i;
    }

    @android.annotation.SystemApi
    public final int getSlotIndex() {
        return this.mSlotId;
    }

    @android.annotation.SystemApi
    public int getFeatureState() {
        int i;
        synchronized (this.mLock) {
            i = this.mState;
        }
        return i;
    }

    @android.annotation.SystemApi
    public final void setFeatureState(int i) {
        boolean z;
        synchronized (this.mLock) {
            if (this.mState == i) {
                z = false;
            } else {
                this.mState = i;
                z = true;
            }
        }
        if (z) {
            notifyFeatureState(i);
        }
    }

    public void addImsFeatureStatusCallback(com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        try {
            synchronized (this.mStatusCallbacks) {
                this.mStatusCallbacks.register(iImsFeatureStatusCallback);
                iImsFeatureStatusCallback.notifyImsFeatureStatus(getFeatureState());
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "Couldn't notify feature state: " + e.getMessage());
        }
    }

    public void removeImsFeatureStatusCallback(com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        synchronized (this.mStatusCallbacks) {
            this.mStatusCallbacks.unregister(iImsFeatureStatusCallback);
        }
    }

    private void notifyFeatureState(final int i) {
        synchronized (this.mStatusCallbacks) {
            this.mStatusCallbacks.broadcastAction(new java.util.function.Consumer() { // from class: android.telephony.ims.feature.ImsFeature$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.telephony.ims.feature.ImsFeature.lambda$notifyFeatureState$0(i, (com.android.ims.internal.IImsFeatureStatusCallback) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyFeatureState$0(int i, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        try {
            iImsFeatureStatusCallback.notifyImsFeatureStatus(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, e + " notifyFeatureState() - Skipping callback.");
        }
    }

    public final void addCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
        this.mCapabilityCallbacks.register(iImsCapabilityCallback);
        try {
            iImsCapabilityCallback.onCapabilitiesStatusChanged(queryCapabilityStatus().mCapabilities);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "addCapabilityCallback: error accessing callback: " + e.getMessage());
        }
    }

    final void removeCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
        this.mCapabilityCallbacks.unregister(iImsCapabilityCallback);
    }

    final void queryCapabilityConfigurationInternal(int i, int i2, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
        boolean queryCapabilityConfiguration = queryCapabilityConfiguration(i, i2);
        if (iImsCapabilityCallback != null) {
            try {
                iImsCapabilityCallback.onQueryCapabilityConfiguration(i, i2, queryCapabilityConfiguration);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "queryCapabilityConfigurationInternal called on dead binder!");
            }
        }
    }

    public android.telephony.ims.feature.ImsFeature.Capabilities queryCapabilityStatus() {
        android.telephony.ims.feature.ImsFeature.Capabilities copy;
        synchronized (this.mLock) {
            copy = this.mCapabilityStatus.copy();
        }
        return copy;
    }

    public final void requestChangeEnabledCapabilities(android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
        if (capabilityChangeRequest == null) {
            throw new java.lang.IllegalArgumentException("ImsFeature#requestChangeEnabledCapabilities called with invalid params.");
        }
        changeEnabledCapabilities(capabilityChangeRequest, new android.telephony.ims.feature.ImsFeature.CapabilityCallbackProxy(iImsCapabilityCallback));
    }

    protected final void notifyCapabilitiesStatusChanged(final android.telephony.ims.feature.ImsFeature.Capabilities capabilities) {
        synchronized (this.mLock) {
            this.mCapabilityStatus = capabilities.copy();
        }
        synchronized (this.mCapabilityCallbacks) {
            this.mCapabilityCallbacks.broadcastAction(new java.util.function.Consumer() { // from class: android.telephony.ims.feature.ImsFeature$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.telephony.ims.feature.ImsFeature.lambda$notifyCapabilitiesStatusChanged$1(android.telephony.ims.feature.ImsFeature.Capabilities.this, (android.telephony.ims.aidl.IImsCapabilityCallback) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyCapabilitiesStatusChanged$1(android.telephony.ims.feature.ImsFeature.Capabilities capabilities, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
        try {
            android.util.Log.d(LOG_TAG, "ImsFeature notifyCapabilitiesStatusChanged Capabilities = " + capabilities.mCapabilities);
            iImsCapabilityCallback.onCapabilitiesStatusChanged(capabilities.mCapabilities);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, e + " notifyCapabilitiesStatusChanged() - Skipping callback.");
        }
    }
}
