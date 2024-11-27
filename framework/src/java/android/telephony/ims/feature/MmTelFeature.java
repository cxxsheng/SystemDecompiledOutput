package android.telephony.ims.feature;

/* loaded from: classes3.dex */
public class MmTelFeature extends android.telephony.ims.feature.ImsFeature {

    @android.annotation.SystemApi
    public static final int AUDIO_HANDLER_ANDROID = 0;

    @android.annotation.SystemApi
    public static final int AUDIO_HANDLER_BASEBAND = 1;
    public static final int EPS_FALLBACK_REASON_INVALID = -1;
    public static final int EPS_FALLBACK_REASON_NO_NETWORK_RESPONSE = 2;
    public static final int EPS_FALLBACK_REASON_NO_NETWORK_TRIGGER = 1;

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_IS_UNKNOWN_CALL = "android.telephony.ims.feature.extra.IS_UNKNOWN_CALL";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_IS_USSD = "android.telephony.ims.feature.extra.IS_USSD";
    public static final int IMS_TRAFFIC_DIRECTION_INCOMING = 0;
    public static final int IMS_TRAFFIC_DIRECTION_OUTGOING = 1;
    public static final int IMS_TRAFFIC_TYPE_EMERGENCY = 0;
    public static final int IMS_TRAFFIC_TYPE_EMERGENCY_SMS = 1;
    public static final int IMS_TRAFFIC_TYPE_NONE = -1;
    public static final int IMS_TRAFFIC_TYPE_REGISTRATION = 5;
    public static final int IMS_TRAFFIC_TYPE_SMS = 4;
    public static final int IMS_TRAFFIC_TYPE_UT_XCAP = 6;
    public static final int IMS_TRAFFIC_TYPE_VIDEO = 3;
    public static final int IMS_TRAFFIC_TYPE_VOICE = 2;
    private static final java.lang.String LOG_TAG = "MmTelFeature";

    @android.annotation.SystemApi
    public static final int PROCESS_CALL_CSFB = 1;

    @android.annotation.SystemApi
    public static final int PROCESS_CALL_IMS = 0;
    private java.util.concurrent.Executor mExecutor;
    private android.telephony.ims.aidl.IImsMmTelListener mListener;
    private android.telephony.ims.stub.ImsSmsImplBase mSmsImpl;
    private java.util.HashMap<android.telephony.ims.feature.ImsTrafficSessionCallback, android.telephony.ims.feature.MmTelFeature.ImsTrafficSessionCallbackWrapper> mTrafficCallbacks = new java.util.HashMap<>();
    private final android.telephony.ims.aidl.IImsMmTelFeature mImsMMTelBinder = new android.telephony.ims.feature.MmTelFeature.AnonymousClass1();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EpsFallbackReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsAudioHandler {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsTrafficDirection {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsTrafficType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProcessCallResult {
    }

    @android.annotation.SystemApi
    public MmTelFeature() {
    }

    @android.annotation.SystemApi
    public MmTelFeature(java.util.concurrent.Executor executor) {
        this.mExecutor = executor;
    }

    /* renamed from: android.telephony.ims.feature.MmTelFeature$1, reason: invalid class name */
    class AnonymousClass1 extends android.telephony.ims.aidl.IImsMmTelFeature.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$0(android.telephony.ims.aidl.IImsMmTelListener iImsMmTelListener) {
            android.telephony.ims.feature.MmTelFeature.this.setListener(iImsMmTelListener);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setListener(final android.telephony.ims.aidl.IImsMmTelListener iImsMmTelListener) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$setListener$0(iImsMmTelListener);
                }
            }, "setListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$getFeatureState$1() {
            return java.lang.Integer.valueOf(android.telephony.ims.feature.MmTelFeature.this.getFeatureState());
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int getFeatureState() throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda16
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$getFeatureState$1;
                    lambda$getFeatureState$1 = android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$getFeatureState$1();
                    return lambda$getFeatureState$1;
                }
            }, "getFeatureState")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.telephony.ims.ImsCallProfile lambda$createCallProfile$2(int i, int i2) {
            return android.telephony.ims.feature.MmTelFeature.this.createCallProfile(i, i2);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public android.telephony.ims.ImsCallProfile createCallProfile(final int i, final int i2) throws android.os.RemoteException {
            return (android.telephony.ims.ImsCallProfile) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.telephony.ims.ImsCallProfile lambda$createCallProfile$2;
                    lambda$createCallProfile$2 = android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$createCallProfile$2(i, i2);
                    return lambda$createCallProfile$2;
                }
            }, "createCallProfile");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$changeOfferedRtpHeaderExtensionTypes$3(java.util.List list) {
            android.telephony.ims.feature.MmTelFeature.this.changeOfferedRtpHeaderExtensionTypes(new android.util.ArraySet(list));
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void changeOfferedRtpHeaderExtensionTypes(final java.util.List<android.telephony.ims.RtpHeaderExtensionType> list) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda32
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$changeOfferedRtpHeaderExtensionTypes$3(list);
                }
            }, "changeOfferedRtpHeaderExtensionTypes");
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public com.android.ims.internal.IImsCallSession createCallSession(final android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            com.android.ims.internal.IImsCallSession iImsCallSession = (com.android.ims.internal.IImsCallSession) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda26
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    com.android.ims.internal.IImsCallSession lambda$createCallSession$4;
                    lambda$createCallSession$4 = android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$createCallSession$4(imsCallProfile, atomicReference);
                    return lambda$createCallSession$4;
                }
            }, "createCallSession");
            if (atomicReference.get() != null) {
                throw ((android.os.RemoteException) atomicReference.get());
            }
            return iImsCallSession;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.ims.internal.IImsCallSession lambda$createCallSession$4(android.telephony.ims.ImsCallProfile imsCallProfile, java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                return android.telephony.ims.feature.MmTelFeature.this.createCallSessionInterface(imsCallProfile);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
                return null;
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int shouldProcessCall(final java.lang.String[] strArr) {
            java.lang.Integer num = (java.lang.Integer) executeMethodAsyncForResultNoException(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$shouldProcessCall$5;
                    lambda$shouldProcessCall$5 = android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$shouldProcessCall$5(strArr);
                    return lambda$shouldProcessCall$5;
                }
            }, "shouldProcessCall");
            if (num != null) {
                return num.intValue();
            }
            return 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$shouldProcessCall$5(java.lang.String[] strArr) {
            return java.lang.Integer.valueOf(android.telephony.ims.feature.MmTelFeature.this.shouldProcessCall(strArr));
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public com.android.ims.internal.IImsUt getUtInterface() throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            com.android.ims.internal.IImsUt iImsUt = (com.android.ims.internal.IImsUt) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda15
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    com.android.ims.internal.IImsUt lambda$getUtInterface$6;
                    lambda$getUtInterface$6 = android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$getUtInterface$6(atomicReference);
                    return lambda$getUtInterface$6;
                }
            }, "getUtInterface");
            if (atomicReference.get() != null) {
                throw ((android.os.RemoteException) atomicReference.get());
            }
            return iImsUt;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.ims.internal.IImsUt lambda$getUtInterface$6(java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                return android.telephony.ims.feature.MmTelFeature.this.getUtInterface();
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
                return null;
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public com.android.ims.internal.IImsEcbm getEcbmInterface() throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            com.android.ims.internal.IImsEcbm iImsEcbm = (com.android.ims.internal.IImsEcbm) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda8
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    com.android.ims.internal.IImsEcbm lambda$getEcbmInterface$7;
                    lambda$getEcbmInterface$7 = android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$getEcbmInterface$7(atomicReference);
                    return lambda$getEcbmInterface$7;
                }
            }, "getEcbmInterface");
            if (atomicReference.get() != null) {
                throw ((android.os.RemoteException) atomicReference.get());
            }
            return iImsEcbm;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.ims.internal.IImsEcbm lambda$getEcbmInterface$7(java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                return android.telephony.ims.feature.MmTelFeature.this.getEcbmInterface();
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setUiTtyMode$8(int i, android.os.Message message) {
            android.telephony.ims.feature.MmTelFeature.this.setUiTtyMode(i, message);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setUiTtyMode(final int i, final android.os.Message message) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda31
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$setUiTtyMode$8(i, message);
                }
            }, "setUiTtyMode");
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public com.android.ims.internal.IImsMultiEndpoint getMultiEndpointInterface() throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            com.android.ims.internal.IImsMultiEndpoint iImsMultiEndpoint = (com.android.ims.internal.IImsMultiEndpoint) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    com.android.ims.internal.IImsMultiEndpoint lambda$getMultiEndpointInterface$9;
                    lambda$getMultiEndpointInterface$9 = android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$getMultiEndpointInterface$9(atomicReference);
                    return lambda$getMultiEndpointInterface$9;
                }
            }, "getMultiEndpointInterface");
            if (atomicReference.get() != null) {
                throw ((android.os.RemoteException) atomicReference.get());
            }
            return iImsMultiEndpoint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.ims.internal.IImsMultiEndpoint lambda$getMultiEndpointInterface$9(java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                return android.telephony.ims.feature.MmTelFeature.this.getMultiEndpointInterface();
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$queryCapabilityStatus$10() {
            return java.lang.Integer.valueOf(android.telephony.ims.feature.MmTelFeature.this.queryCapabilityStatus().mCapabilities);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int queryCapabilityStatus() {
            java.lang.Integer num = (java.lang.Integer) executeMethodAsyncForResultNoException(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda24
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$queryCapabilityStatus$10;
                    lambda$queryCapabilityStatus$10 = android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$queryCapabilityStatus$10();
                    return lambda$queryCapabilityStatus$10;
                }
            }, "queryCapabilityStatus");
            if (num != null) {
                return num.intValue();
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addCapabilityCallback$11(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            android.telephony.ims.feature.MmTelFeature.this.addCapabilityCallback(iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void addCapabilityCallback(final android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$addCapabilityCallback$11(iImsCapabilityCallback);
                }
            }, "addCapabilityCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeCapabilityCallback$12(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            android.telephony.ims.feature.MmTelFeature.this.removeCapabilityCallback(iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void removeCapabilityCallback(final android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$removeCapabilityCallback$12(iImsCapabilityCallback);
                }
            }, "removeCapabilityCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$changeCapabilitiesConfiguration$13(android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            android.telephony.ims.feature.MmTelFeature.this.requestChangeEnabledCapabilities(capabilityChangeRequest, iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void changeCapabilitiesConfiguration(final android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, final android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$changeCapabilitiesConfiguration$13(capabilityChangeRequest, iImsCapabilityCallback);
                }
            }, "changeCapabilitiesConfiguration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$queryCapabilityConfiguration$14(int i, int i2, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            android.telephony.ims.feature.MmTelFeature.this.queryCapabilityConfigurationInternal(i, i2, iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void queryCapabilityConfiguration(final int i, final int i2, final android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$queryCapabilityConfiguration$14(i, i2, iImsCapabilityCallback);
                }
            }, "queryCapabilityConfiguration");
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setMediaQualityThreshold(final int i, final android.telephony.ims.MediaThreshold mediaThreshold) {
            if (mediaThreshold != null) {
                executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$setMediaQualityThreshold$15(i, mediaThreshold);
                    }
                }, "setMediaQualityThreshold");
            } else {
                executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$setMediaQualityThreshold$16(i);
                    }
                }, "clearMediaQualityThreshold");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setMediaQualityThreshold$15(int i, android.telephony.ims.MediaThreshold mediaThreshold) {
            android.telephony.ims.feature.MmTelFeature.this.setMediaThreshold(i, mediaThreshold);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setMediaQualityThreshold$16(int i) {
            android.telephony.ims.feature.MmTelFeature.this.clearMediaThreshold(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.telephony.ims.MediaQualityStatus lambda$queryMediaQualityStatus$17(int i) {
            return android.telephony.ims.feature.MmTelFeature.this.queryMediaQualityStatus(i);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public android.telephony.ims.MediaQualityStatus queryMediaQualityStatus(final int i) throws android.os.RemoteException {
            return (android.telephony.ims.MediaQualityStatus) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda35
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.telephony.ims.MediaQualityStatus lambda$queryMediaQualityStatus$17;
                    lambda$queryMediaQualityStatus$17 = android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$queryMediaQualityStatus$17(i);
                    return lambda$queryMediaQualityStatus$17;
                }
            }, "queryMediaQualityStatus");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSmsListener$18(android.telephony.ims.aidl.IImsSmsListener iImsSmsListener) {
            android.telephony.ims.feature.MmTelFeature.this.setSmsListener(iImsSmsListener);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setSmsListener(final android.telephony.ims.aidl.IImsSmsListener iImsSmsListener) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$setSmsListener$18(iImsSmsListener);
                }
            }, "setSmsListener", android.telephony.ims.feature.MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendSms$19(int i, int i2, java.lang.String str, java.lang.String str2, boolean z, byte[] bArr) {
            android.telephony.ims.feature.MmTelFeature.this.sendSms(i, i2, str, str2, z, bArr);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void sendSms(final int i, final int i2, final java.lang.String str, final java.lang.String str2, final boolean z, final byte[] bArr) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$sendSms$19(i, i2, str, str2, z, bArr);
                }
            }, "sendSms", android.telephony.ims.feature.MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMemoryAvailable$20(int i) {
            android.telephony.ims.feature.MmTelFeature.this.onMemoryAvailable(i);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void onMemoryAvailable(final int i) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$onMemoryAvailable$20(i);
                }
            }, "onMemoryAvailable", android.telephony.ims.feature.MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$acknowledgeSms$21(int i, int i2, int i3) {
            android.telephony.ims.feature.MmTelFeature.this.acknowledgeSms(i, i2, i3);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void acknowledgeSms(final int i, final int i2, final int i3) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$acknowledgeSms$21(i, i2, i3);
                }
            }, "acknowledgeSms", android.telephony.ims.feature.MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$acknowledgeSmsWithPdu$22(int i, int i2, int i3, byte[] bArr) {
            android.telephony.ims.feature.MmTelFeature.this.acknowledgeSms(i, i2, i3, bArr);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void acknowledgeSmsWithPdu(final int i, final int i2, final int i3, final byte[] bArr) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$acknowledgeSmsWithPdu$22(i, i2, i3, bArr);
                }
            }, "acknowledgeSms", android.telephony.ims.feature.MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$acknowledgeSmsReport$23(int i, int i2, int i3) {
            android.telephony.ims.feature.MmTelFeature.this.acknowledgeSmsReport(i, i2, i3);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void acknowledgeSmsReport(final int i, final int i2, final int i3) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$acknowledgeSmsReport$23(i, i2, i3);
                }
            }, "acknowledgeSmsReport", android.telephony.ims.feature.MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.String lambda$getSmsFormat$24() {
            return android.telephony.ims.feature.MmTelFeature.this.getSmsFormat();
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public java.lang.String getSmsFormat() {
            return (java.lang.String) executeMethodAsyncForResultNoException(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda27
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getSmsFormat$24;
                    lambda$getSmsFormat$24 = android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$getSmsFormat$24();
                    return lambda$getSmsFormat$24;
                }
            }, "getSmsFormat", android.telephony.ims.feature.MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSmsReady$25() {
            android.telephony.ims.feature.MmTelFeature.this.onSmsReady();
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void onSmsReady() {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$onSmsReady$25();
                }
            }, "onSmsReady", android.telephony.ims.feature.MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccStarted(final android.telephony.ims.aidl.ISrvccStartedCallback iSrvccStartedCallback) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$notifySrvccStarted$27(iSrvccStartedCallback);
                }
            }, "notifySrvccStarted");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifySrvccStarted$27(final android.telephony.ims.aidl.ISrvccStartedCallback iSrvccStartedCallback) {
            android.telephony.ims.feature.MmTelFeature.this.notifySrvccStarted(new java.util.function.Consumer() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda25
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.lambda$notifySrvccStarted$26(android.telephony.ims.aidl.ISrvccStartedCallback.this, (java.util.List) obj);
                }
            });
        }

        static /* synthetic */ void lambda$notifySrvccStarted$26(android.telephony.ims.aidl.ISrvccStartedCallback iSrvccStartedCallback, java.util.List list) {
            try {
                iSrvccStartedCallback.onSrvccCallNotified(list);
            } catch (java.lang.Exception e) {
                android.util.Log.e(android.telephony.ims.feature.MmTelFeature.LOG_TAG, "onSrvccCallNotified e=" + e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccCompleted() {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$notifySrvccCompleted$28();
                }
            }, "notifySrvccCompleted");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifySrvccCompleted$28() {
            android.telephony.ims.feature.MmTelFeature.this.notifySrvccCompleted();
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccFailed() {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$notifySrvccFailed$29();
                }
            }, "notifySrvccFailed");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifySrvccFailed$29() {
            android.telephony.ims.feature.MmTelFeature.this.notifySrvccFailed();
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccCanceled() {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda34
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.MmTelFeature.AnonymousClass1.this.lambda$notifySrvccCanceled$30();
                }
            }, "notifySrvccCanceled");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifySrvccCanceled$30() {
            android.telephony.ims.feature.MmTelFeature.this.notifySrvccCanceled();
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setTerminalBasedCallWaitingStatus(boolean z) throws android.os.RemoteException {
            synchronized (android.telephony.ims.feature.MmTelFeature.this.mLock) {
                try {
                    try {
                        android.telephony.ims.feature.MmTelFeature.this.setTerminalBasedCallWaitingStatus(z);
                    } catch (android.os.ServiceSpecificException e) {
                        throw new android.os.ServiceSpecificException(e.errorCode, e.getMessage());
                    } catch (java.lang.Exception e2) {
                        throw new android.os.RemoteException(e2.getMessage());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void executeMethodAsync(final java.lang.Runnable runnable, java.lang.String str) throws android.os.RemoteException {
            try {
                java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda30
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, android.telephony.ims.feature.MmTelFeature.this.mExecutor).join();
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
                android.util.Log.w(android.telephony.ims.feature.MmTelFeature.LOG_TAG, "MmTelFeature Binder - " + str + " exception: " + e.getMessage());
                throw new android.os.RemoteException(e.getMessage());
            }
        }

        private void executeMethodAsyncNoException(final java.lang.Runnable runnable, java.lang.String str) {
            try {
                java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda36
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, android.telephony.ims.feature.MmTelFeature.this.mExecutor).join();
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
                android.util.Log.w(android.telephony.ims.feature.MmTelFeature.LOG_TAG, "MmTelFeature Binder - " + str + " exception: " + e.getMessage());
            }
        }

        private void executeMethodAsyncNoException(final java.lang.Runnable runnable, java.lang.String str, java.util.concurrent.Executor executor) {
            try {
                java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda33
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, executor).join();
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
                android.util.Log.w(android.telephony.ims.feature.MmTelFeature.LOG_TAG, "MmTelFeature Binder - " + str + " exception: " + e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final java.util.function.Supplier<T> supplier, java.lang.String str) throws android.os.RemoteException {
            try {
                return (T) java.util.concurrent.CompletableFuture.supplyAsync(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda9
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.lang.Object runWithCleanCallingIdentity;
                        runWithCleanCallingIdentity = com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity((java.util.function.Supplier<java.lang.Object>) supplier);
                        return runWithCleanCallingIdentity;
                    }
                }, android.telephony.ims.feature.MmTelFeature.this.mExecutor).get();
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                android.util.Log.w(android.telephony.ims.feature.MmTelFeature.LOG_TAG, "MmTelFeature Binder - " + str + " exception: " + e.getMessage());
                throw new android.os.RemoteException(e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResultNoException(final java.util.function.Supplier<T> supplier, java.lang.String str) {
            try {
                return (T) java.util.concurrent.CompletableFuture.supplyAsync(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda6
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.lang.Object runWithCleanCallingIdentity;
                        runWithCleanCallingIdentity = com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity((java.util.function.Supplier<java.lang.Object>) supplier);
                        return runWithCleanCallingIdentity;
                    }
                }, android.telephony.ims.feature.MmTelFeature.this.mExecutor).get();
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                android.util.Log.w(android.telephony.ims.feature.MmTelFeature.LOG_TAG, "MmTelFeature Binder - " + str + " exception: " + e.getMessage());
                return null;
            }
        }

        private <T> T executeMethodAsyncForResultNoException(final java.util.function.Supplier<T> supplier, java.lang.String str, java.util.concurrent.Executor executor) {
            try {
                return (T) java.util.concurrent.CompletableFuture.supplyAsync(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda14
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.lang.Object runWithCleanCallingIdentity;
                        runWithCleanCallingIdentity = com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity((java.util.function.Supplier<java.lang.Object>) supplier);
                        return runWithCleanCallingIdentity;
                    }
                }, executor).get();
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                android.util.Log.w(android.telephony.ims.feature.MmTelFeature.LOG_TAG, "MmTelFeature Binder - " + str + " exception: " + e.getMessage());
                return null;
            }
        }
    }

    public static class MmTelCapabilities extends android.telephony.ims.feature.ImsFeature.Capabilities {
        public static final int CAPABILITY_TYPE_CALL_COMPOSER = 16;
        public static final int CAPABILITY_TYPE_CALL_COMPOSER_BUSINESS_ONLY = 32;
        public static final int CAPABILITY_TYPE_MAX = 33;
        public static final int CAPABILITY_TYPE_NONE = 0;
        public static final int CAPABILITY_TYPE_SMS = 8;
        public static final int CAPABILITY_TYPE_UT = 4;
        public static final int CAPABILITY_TYPE_VIDEO = 2;
        public static final int CAPABILITY_TYPE_VOICE = 1;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface MmTelCapability {
        }

        @android.annotation.SystemApi
        public MmTelCapabilities() {
        }

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public MmTelCapabilities(android.telephony.ims.feature.ImsFeature.Capabilities capabilities) {
            this.mCapabilities = capabilities.mCapabilities;
        }

        @android.annotation.SystemApi
        public MmTelCapabilities(int i) {
            super(i);
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        @android.annotation.SystemApi
        public final void addCapabilities(int i) {
            super.addCapabilities(i);
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        @android.annotation.SystemApi
        public final void removeCapabilities(int i) {
            super.removeCapabilities(i);
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        public final boolean isCapable(int i) {
            return super.isCapable(i);
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        public java.lang.String toString() {
            return "MmTel Capabilities - [Voice: " + isCapable(1) + " Video: " + isCapable(2) + " UT: " + isCapable(4) + " SMS: " + isCapable(8) + " CALL_COMPOSER: " + isCapable(16) + " BUSINESS_COMPOSER_ONLY: " + isCapable(32) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static class Listener extends android.telephony.ims.aidl.IImsMmTelListener.Stub {
        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public android.telephony.ims.aidl.IImsCallSessionListener onIncomingCall(com.android.ims.internal.IImsCallSession iImsCallSession, java.lang.String str, android.os.Bundle bundle) {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onRejectedCall(android.telephony.ims.ImsCallProfile imsCallProfile, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onVoiceMessageCountUpdate(int i) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onAudioModeIsVoipChanged(int i) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onTriggerEpsFallback(int i) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onStartImsTrafficSession(int i, int i2, int i3, int i4, android.telephony.ims.aidl.IImsTrafficSessionCallback iImsTrafficSessionCallback) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onModifyImsTrafficSession(int i, int i2) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onStopImsTrafficSession(int i) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onMediaQualityStatusChanged(android.telephony.ims.MediaQualityStatus mediaQualityStatus) {
        }
    }

    public static class ImsTrafficSessionCallbackWrapper {
        public static final int INVALID_TOKEN = -1;
        private static final int MAX_TOKEN = 65536;
        private static final java.util.concurrent.atomic.AtomicInteger sTokenGenerator = new java.util.concurrent.atomic.AtomicInteger();
        private android.telephony.ims.feature.MmTelFeature.ImsTrafficSessionCallbackWrapper.IImsTrafficSessionCallbackStub mCallback;
        private android.telephony.ims.feature.ImsTrafficSessionCallback mImsTrafficSessionCallback;
        private int mToken;

        private ImsTrafficSessionCallbackWrapper(android.telephony.ims.feature.ImsTrafficSessionCallback imsTrafficSessionCallback) {
            this.mCallback = null;
            this.mToken = -1;
            this.mImsTrafficSessionCallback = imsTrafficSessionCallback;
        }

        final void update(java.util.concurrent.Executor executor) {
            if (executor == null) {
                throw new java.lang.IllegalArgumentException("ImsTrafficSessionCallback Executor must be non-null");
            }
            if (this.mCallback == null) {
                this.mCallback = new android.telephony.ims.feature.MmTelFeature.ImsTrafficSessionCallbackWrapper.IImsTrafficSessionCallbackStub(this.mImsTrafficSessionCallback, executor);
                this.mToken = generateToken();
            } else {
                this.mCallback.update(executor);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class IImsTrafficSessionCallbackStub extends android.telephony.ims.aidl.IImsTrafficSessionCallback.Stub {
            private java.util.concurrent.Executor mExecutor;
            private java.lang.ref.WeakReference<android.telephony.ims.feature.ImsTrafficSessionCallback> mImsTrafficSessionCallbackWeakRef;

            IImsTrafficSessionCallbackStub(android.telephony.ims.feature.ImsTrafficSessionCallback imsTrafficSessionCallback, java.util.concurrent.Executor executor) {
                this.mImsTrafficSessionCallbackWeakRef = new java.lang.ref.WeakReference<>(imsTrafficSessionCallback);
                this.mExecutor = executor;
            }

            void update(java.util.concurrent.Executor executor) {
                this.mExecutor = executor;
            }

            @Override // android.telephony.ims.aidl.IImsTrafficSessionCallback
            public void onReady() {
                final android.telephony.ims.feature.ImsTrafficSessionCallback imsTrafficSessionCallback = this.mImsTrafficSessionCallbackWeakRef.get();
                if (imsTrafficSessionCallback == null) {
                    return;
                }
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.ims.feature.MmTelFeature$ImsTrafficSessionCallbackWrapper$IImsTrafficSessionCallbackStub$$ExternalSyntheticLambda3
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        android.telephony.ims.feature.MmTelFeature.ImsTrafficSessionCallbackWrapper.IImsTrafficSessionCallbackStub.this.lambda$onReady$1(imsTrafficSessionCallback);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onReady$1(final android.telephony.ims.feature.ImsTrafficSessionCallback imsTrafficSessionCallback) throws java.lang.Exception {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$ImsTrafficSessionCallbackWrapper$IImsTrafficSessionCallbackStub$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.feature.ImsTrafficSessionCallback.this.onReady();
                    }
                });
            }

            @Override // android.telephony.ims.aidl.IImsTrafficSessionCallback
            public void onError(final android.telephony.ims.feature.ConnectionFailureInfo connectionFailureInfo) {
                final android.telephony.ims.feature.ImsTrafficSessionCallback imsTrafficSessionCallback = this.mImsTrafficSessionCallbackWeakRef.get();
                if (imsTrafficSessionCallback == null) {
                    return;
                }
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.ims.feature.MmTelFeature$ImsTrafficSessionCallbackWrapper$IImsTrafficSessionCallbackStub$$ExternalSyntheticLambda2
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        android.telephony.ims.feature.MmTelFeature.ImsTrafficSessionCallbackWrapper.IImsTrafficSessionCallbackStub.this.lambda$onError$3(imsTrafficSessionCallback, connectionFailureInfo);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onError$3(final android.telephony.ims.feature.ImsTrafficSessionCallback imsTrafficSessionCallback, final android.telephony.ims.feature.ConnectionFailureInfo connectionFailureInfo) throws java.lang.Exception {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$ImsTrafficSessionCallbackWrapper$IImsTrafficSessionCallbackStub$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.feature.ImsTrafficSessionCallback.this.onError(connectionFailureInfo);
                    }
                });
            }
        }

        final android.telephony.ims.feature.MmTelFeature.ImsTrafficSessionCallbackWrapper.IImsTrafficSessionCallbackStub getCallbackBinder() {
            return this.mCallback;
        }

        final int getToken() {
            return this.mToken;
        }

        final void reset() {
            this.mCallback = null;
            this.mToken = -1;
        }

        private static int generateToken() {
            int incrementAndGet = sTokenGenerator.incrementAndGet();
            if (incrementAndGet == 65536) {
                sTokenGenerator.set(0);
            }
            return incrementAndGet;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setListener(android.telephony.ims.aidl.IImsMmTelListener iImsMmTelListener) {
        synchronized (this.mLock) {
            this.mListener = iImsMmTelListener;
            if (this.mListener != null) {
                lambda$initialize$0();
            }
        }
    }

    private android.telephony.ims.aidl.IImsMmTelListener getListener() {
        android.telephony.ims.aidl.IImsMmTelListener iImsMmTelListener;
        synchronized (this.mLock) {
            iImsMmTelListener = this.mListener;
        }
        return iImsMmTelListener;
    }

    @Override // android.telephony.ims.feature.ImsFeature
    @android.annotation.SystemApi
    public final android.telephony.ims.feature.MmTelFeature.MmTelCapabilities queryCapabilityStatus() {
        return new android.telephony.ims.feature.MmTelFeature.MmTelCapabilities(super.queryCapabilityStatus());
    }

    @android.annotation.SystemApi
    public final void notifyCapabilitiesStatusChanged(android.telephony.ims.feature.MmTelFeature.MmTelCapabilities mmTelCapabilities) {
        if (mmTelCapabilities == null) {
            throw new java.lang.IllegalArgumentException("MmTelCapabilities must be non-null!");
        }
        super.notifyCapabilitiesStatusChanged((android.telephony.ims.feature.ImsFeature.Capabilities) mmTelCapabilities);
    }

    @android.annotation.SystemApi
    public final void notifyMediaQualityStatusChanged(android.telephony.ims.MediaQualityStatus mediaQualityStatus) {
        if (mediaQualityStatus == null) {
            throw new java.lang.IllegalArgumentException("MediaQualityStatus must be non-null!");
        }
        android.util.Log.i(LOG_TAG, "notifyMediaQualityStatusChanged " + mediaQualityStatus);
        android.telephony.ims.aidl.IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new java.lang.IllegalStateException("Session is not available.");
        }
        try {
            listener.onMediaQualityStatusChanged(mediaQualityStatus);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public final void notifyIncomingCall(android.telephony.ims.stub.ImsCallSessionImplBase imsCallSessionImplBase, android.os.Bundle bundle) {
        if (imsCallSessionImplBase == null || bundle == null) {
            throw new java.lang.IllegalArgumentException("ImsCallSessionImplBase and Bundle can not be null.");
        }
        android.telephony.ims.aidl.IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new java.lang.IllegalStateException("Session is not available.");
        }
        try {
            imsCallSessionImplBase.setDefaultExecutor(this.mExecutor);
            listener.onIncomingCall(imsCallSessionImplBase.getServiceImpl(), null, bundle);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.SystemApi
    public final android.telephony.ims.ImsCallSessionListener notifyIncomingCall(android.telephony.ims.stub.ImsCallSessionImplBase imsCallSessionImplBase, java.lang.String str, android.os.Bundle bundle) {
        if (imsCallSessionImplBase == null || str == null || bundle == null) {
            throw new java.lang.IllegalArgumentException("ImsCallSessionImplBase, callId, and Bundle can not be null.");
        }
        android.telephony.ims.aidl.IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new java.lang.IllegalStateException("Session is not available.");
        }
        try {
            imsCallSessionImplBase.setDefaultExecutor(this.mExecutor);
            android.telephony.ims.aidl.IImsCallSessionListener onIncomingCall = listener.onIncomingCall(imsCallSessionImplBase.getServiceImpl(), str, bundle);
            if (onIncomingCall != null) {
                android.telephony.ims.ImsCallSessionListener imsCallSessionListener = new android.telephony.ims.ImsCallSessionListener(onIncomingCall);
                imsCallSessionListener.setDefaultExecutor(this.mExecutor);
                return imsCallSessionListener;
            }
            return null;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.SystemApi
    public final void notifyRejectedCall(android.telephony.ims.ImsCallProfile imsCallProfile, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        if (imsCallProfile == null || imsReasonInfo == null) {
            throw new java.lang.IllegalArgumentException("ImsCallProfile and ImsReasonInfo must not be null.");
        }
        android.telephony.ims.aidl.IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new java.lang.IllegalStateException("Session is not available.");
        }
        try {
            listener.onRejectedCall(imsCallProfile, imsReasonInfo);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public final void notifyIncomingCallSession(com.android.ims.internal.IImsCallSession iImsCallSession, android.os.Bundle bundle) {
        android.telephony.ims.aidl.IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new java.lang.IllegalStateException("Session is not available.");
        }
        try {
            listener.onIncomingCall(iImsCallSession, null, bundle);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.SystemApi
    public final void notifyVoiceMessageCountUpdate(int i) {
        android.telephony.ims.aidl.IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new java.lang.IllegalStateException("Session is not available.");
        }
        try {
            listener.onVoiceMessageCountUpdate(i);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.SystemApi
    public final void setCallAudioHandler(int i) {
        android.telephony.ims.aidl.IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new java.lang.IllegalStateException("Session is not available.");
        }
        try {
            listener.onAudioModeIsVoipChanged(i);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public final void triggerEpsFallback(int i) {
        android.telephony.ims.aidl.IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new java.lang.IllegalStateException("Session is not available.");
        }
        try {
            listener.onTriggerEpsFallback(i);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public final void startImsTrafficSession(int i, int i2, int i3, java.util.concurrent.Executor executor, android.telephony.ims.feature.ImsTrafficSessionCallback imsTrafficSessionCallback) {
        android.telephony.ims.aidl.IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new java.lang.IllegalStateException("Session is not available.");
        }
        android.telephony.ims.feature.MmTelFeature.ImsTrafficSessionCallbackWrapper imsTrafficSessionCallbackWrapper = this.mTrafficCallbacks.get(imsTrafficSessionCallback);
        if (imsTrafficSessionCallbackWrapper == null) {
            imsTrafficSessionCallbackWrapper = new android.telephony.ims.feature.MmTelFeature.ImsTrafficSessionCallbackWrapper(imsTrafficSessionCallback);
            this.mTrafficCallbacks.put(imsTrafficSessionCallback, imsTrafficSessionCallbackWrapper);
        }
        try {
            imsTrafficSessionCallbackWrapper.update(executor);
            listener.onStartImsTrafficSession(imsTrafficSessionCallbackWrapper.getToken(), i, i2, i3, imsTrafficSessionCallbackWrapper.getCallbackBinder());
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public final void modifyImsTrafficSession(int i, android.telephony.ims.feature.ImsTrafficSessionCallback imsTrafficSessionCallback) {
        android.telephony.ims.aidl.IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new java.lang.IllegalStateException("Session is not available.");
        }
        android.telephony.ims.feature.MmTelFeature.ImsTrafficSessionCallbackWrapper imsTrafficSessionCallbackWrapper = this.mTrafficCallbacks.get(imsTrafficSessionCallback);
        if (imsTrafficSessionCallbackWrapper == null) {
            throw new java.lang.IllegalStateException("Unknown ImsTrafficSessionCallback instance.");
        }
        try {
            listener.onModifyImsTrafficSession(imsTrafficSessionCallbackWrapper.getToken(), i);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public final void stopImsTrafficSession(android.telephony.ims.feature.ImsTrafficSessionCallback imsTrafficSessionCallback) {
        android.telephony.ims.aidl.IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new java.lang.IllegalStateException("Session is not available.");
        }
        android.telephony.ims.feature.MmTelFeature.ImsTrafficSessionCallbackWrapper imsTrafficSessionCallbackWrapper = this.mTrafficCallbacks.get(imsTrafficSessionCallback);
        if (imsTrafficSessionCallbackWrapper == null) {
            throw new java.lang.IllegalStateException("Unknown ImsTrafficSessionCallback instance.");
        }
        try {
            listener.onStopImsTrafficSession(imsTrafficSessionCallbackWrapper.getToken());
            imsTrafficSessionCallbackWrapper.reset();
            this.mTrafficCallbacks.remove(imsTrafficSessionCallback);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @Override // android.telephony.ims.feature.ImsFeature
    @android.annotation.SystemApi
    public boolean queryCapabilityConfiguration(int i, int i2) {
        return false;
    }

    @Override // android.telephony.ims.feature.ImsFeature
    @android.annotation.SystemApi
    public void changeEnabledCapabilities(android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, android.telephony.ims.feature.ImsFeature.CapabilityCallbackProxy capabilityCallbackProxy) {
    }

    @android.annotation.SystemApi
    public void setMediaThreshold(int i, android.telephony.ims.MediaThreshold mediaThreshold) {
        android.util.Log.d(LOG_TAG, "setMediaThreshold is not supported." + mediaThreshold);
    }

    @android.annotation.SystemApi
    public void clearMediaThreshold(int i) {
        android.util.Log.d(LOG_TAG, "clearMediaThreshold is not supported." + i);
    }

    @android.annotation.SystemApi
    public android.telephony.ims.MediaQualityStatus queryMediaQualityStatus(int i) {
        android.util.Log.d(LOG_TAG, "queryMediaQualityStatus is not supported." + i);
        return null;
    }

    @android.annotation.SystemApi
    public android.telephony.ims.ImsCallProfile createCallProfile(int i, int i2) {
        return null;
    }

    @android.annotation.SystemApi
    public void changeOfferedRtpHeaderExtensionTypes(java.util.Set<android.telephony.ims.RtpHeaderExtensionType> set) {
    }

    public com.android.ims.internal.IImsCallSession createCallSessionInterface(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        android.telephony.ims.stub.ImsCallSessionImplBase createCallSession = createCallSession(imsCallProfile);
        if (createCallSession != null) {
            createCallSession.setDefaultExecutor(this.mExecutor);
            return createCallSession.getServiceImpl();
        }
        return null;
    }

    @android.annotation.SystemApi
    public android.telephony.ims.stub.ImsCallSessionImplBase createCallSession(android.telephony.ims.ImsCallProfile imsCallProfile) {
        return null;
    }

    @android.annotation.SystemApi
    public int shouldProcessCall(java.lang.String[] strArr) {
        return 0;
    }

    protected com.android.ims.internal.IImsUt getUtInterface() throws android.os.RemoteException {
        android.telephony.ims.stub.ImsUtImplBase ut = getUt();
        if (ut != null) {
            ut.setDefaultExecutor(this.mExecutor);
            return ut.getInterface();
        }
        return null;
    }

    protected com.android.ims.internal.IImsEcbm getEcbmInterface() throws android.os.RemoteException {
        android.telephony.ims.stub.ImsEcbmImplBase ecbm = getEcbm();
        if (ecbm != null) {
            ecbm.setDefaultExecutor(this.mExecutor);
            return ecbm.getImsEcbm();
        }
        return null;
    }

    public com.android.ims.internal.IImsMultiEndpoint getMultiEndpointInterface() throws android.os.RemoteException {
        android.telephony.ims.stub.ImsMultiEndpointImplBase multiEndpoint = getMultiEndpoint();
        if (multiEndpoint != null) {
            multiEndpoint.setDefaultExecutor(this.mExecutor);
            return multiEndpoint.getIImsMultiEndpoint();
        }
        return null;
    }

    public android.telephony.ims.stub.ImsSmsImplBase getImsSmsImpl() {
        android.telephony.ims.stub.ImsSmsImplBase imsSmsImplBase;
        synchronized (this.mLock) {
            if (this.mSmsImpl == null) {
                this.mSmsImpl = getSmsImplementation();
                this.mSmsImpl.setDefaultExecutor(this.mExecutor);
            }
            imsSmsImplBase = this.mSmsImpl;
        }
        return imsSmsImplBase;
    }

    @android.annotation.SystemApi
    public android.telephony.ims.stub.ImsUtImplBase getUt() {
        return new android.telephony.ims.stub.ImsUtImplBase();
    }

    @android.annotation.SystemApi
    public android.telephony.ims.stub.ImsEcbmImplBase getEcbm() {
        return new android.telephony.ims.stub.ImsEcbmImplBase();
    }

    @android.annotation.SystemApi
    public android.telephony.ims.stub.ImsMultiEndpointImplBase getMultiEndpoint() {
        return new android.telephony.ims.stub.ImsMultiEndpointImplBase();
    }

    @android.annotation.SystemApi
    public void setUiTtyMode(int i, android.os.Message message) {
    }

    @android.annotation.SystemApi
    public void setTerminalBasedCallWaitingStatus(boolean z) {
        throw new android.os.ServiceSpecificException(2, "Not implemented on device.");
    }

    @android.annotation.SystemApi
    public void notifySrvccStarted(java.util.function.Consumer<java.util.List<android.telephony.ims.SrvccCall>> consumer) {
    }

    @android.annotation.SystemApi
    public void notifySrvccCompleted() {
    }

    @android.annotation.SystemApi
    public void notifySrvccFailed() {
    }

    @android.annotation.SystemApi
    public void notifySrvccCanceled() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSmsListener(android.telephony.ims.aidl.IImsSmsListener iImsSmsListener) {
        getImsSmsImpl().registerSmsListener(iImsSmsListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSms(int i, int i2, java.lang.String str, java.lang.String str2, boolean z, byte[] bArr) {
        getImsSmsImpl().sendSms(i, i2, str, str2, z, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMemoryAvailable(int i) {
        getImsSmsImpl().onMemoryAvailable(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acknowledgeSms(int i, int i2, int i3) {
        getImsSmsImpl().acknowledgeSms(i, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acknowledgeSms(int i, int i2, int i3, byte[] bArr) {
        getImsSmsImpl().acknowledgeSms(i, i2, i3, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acknowledgeSmsReport(int i, int i2, int i3) {
        getImsSmsImpl().acknowledgeSmsReport(i, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSmsReady() {
        getImsSmsImpl().onReady();
    }

    @android.annotation.SystemApi
    public android.telephony.ims.stub.ImsSmsImplBase getSmsImplementation() {
        return new android.telephony.ims.stub.ImsSmsImplBase();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getSmsFormat() {
        return getImsSmsImpl().getSmsFormat();
    }

    @Override // android.telephony.ims.feature.ImsFeature
    @android.annotation.SystemApi
    public void onFeatureRemoved() {
    }

    @Override // android.telephony.ims.feature.ImsFeature
    @android.annotation.SystemApi
    /* renamed from: onFeatureReady */
    public void lambda$initialize$0() {
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public final android.telephony.ims.aidl.IImsMmTelFeature getBinder() {
        return this.mImsMMTelBinder;
    }

    public final void setDefaultExecutor(java.util.concurrent.Executor executor) {
        if (this.mExecutor == null) {
            this.mExecutor = executor;
        }
    }
}
