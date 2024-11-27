package android.media;

/* loaded from: classes2.dex */
public final class MediaDrm implements java.lang.AutoCloseable {
    public static final int CERTIFICATE_TYPE_NONE = 0;
    public static final int CERTIFICATE_TYPE_X509 = 1;
    private static final int DRM_EVENT = 200;
    public static final int EVENT_KEY_EXPIRED = 3;
    public static final int EVENT_KEY_REQUIRED = 2;
    public static final int EVENT_PROVISION_REQUIRED = 1;
    public static final int EVENT_SESSION_RECLAIMED = 5;
    public static final int EVENT_VENDOR_DEFINED = 4;
    private static final int EXPIRATION_UPDATE = 201;
    public static final int HDCP_LEVEL_UNKNOWN = 0;
    public static final int HDCP_NONE = 1;
    public static final int HDCP_NO_DIGITAL_OUTPUT = Integer.MAX_VALUE;
    public static final int HDCP_V1 = 2;
    public static final int HDCP_V2 = 3;
    public static final int HDCP_V2_1 = 4;
    public static final int HDCP_V2_2 = 5;
    public static final int HDCP_V2_3 = 6;
    private static final int KEY_STATUS_CHANGE = 202;
    public static final int KEY_TYPE_OFFLINE = 2;
    public static final int KEY_TYPE_RELEASE = 3;
    public static final int KEY_TYPE_STREAMING = 1;
    public static final int OFFLINE_LICENSE_STATE_RELEASED = 2;
    public static final int OFFLINE_LICENSE_STATE_UNKNOWN = 0;
    public static final int OFFLINE_LICENSE_STATE_USABLE = 1;
    private static final java.lang.String PERMISSION = "android.permission.ACCESS_DRM_CERTIFICATES";
    public static final java.lang.String PROPERTY_ALGORITHMS = "algorithms";
    public static final java.lang.String PROPERTY_DESCRIPTION = "description";
    public static final java.lang.String PROPERTY_DEVICE_UNIQUE_ID = "deviceUniqueId";
    public static final java.lang.String PROPERTY_VENDOR = "vendor";
    public static final java.lang.String PROPERTY_VERSION = "version";
    public static final int SECURITY_LEVEL_HW_SECURE_ALL = 5;
    public static final int SECURITY_LEVEL_HW_SECURE_CRYPTO = 3;
    public static final int SECURITY_LEVEL_HW_SECURE_DECODE = 4;
    public static final int SECURITY_LEVEL_MAX = 6;
    public static final int SECURITY_LEVEL_SW_SECURE_CRYPTO = 1;
    public static final int SECURITY_LEVEL_SW_SECURE_DECODE = 2;
    public static final int SECURITY_LEVEL_UNKNOWN = 0;
    private static final int SESSION_LOST_STATE = 203;
    private static final java.lang.String TAG = "MediaDrm";
    private long mNativeContext;
    private final java.util.concurrent.atomic.AtomicBoolean mClosed = new java.util.concurrent.atomic.AtomicBoolean();
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final java.util.Map<java.lang.Integer, android.media.MediaDrm.ListenerWithExecutor> mListenerMap = new java.util.concurrent.ConcurrentHashMap();
    private final java.util.Map<java.nio.ByteBuffer, android.media.MediaDrm.PlaybackComponent> mPlaybackComponentMap = new java.util.concurrent.ConcurrentHashMap();
    private final java.lang.String mAppPackageName = android.app.ActivityThread.currentOpPackageName();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ArrayProperty {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CertificateType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DrmEvent {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @java.lang.Deprecated
    public @interface HdcpLevel {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface KeyType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MediaDrmErrorCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OfflineLicenseState {
    }

    public interface OnEventListener {
        void onEvent(android.media.MediaDrm mediaDrm, byte[] bArr, int i, int i2, byte[] bArr2);
    }

    public interface OnExpirationUpdateListener {
        void onExpirationUpdate(android.media.MediaDrm mediaDrm, byte[] bArr, long j);
    }

    public interface OnKeyStatusChangeListener {
        void onKeyStatusChange(android.media.MediaDrm mediaDrm, byte[] bArr, java.util.List<android.media.MediaDrm.KeyStatus> list, boolean z);
    }

    public interface OnSessionLostStateListener {
        void onSessionLostState(android.media.MediaDrm mediaDrm, byte[] bArr);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @java.lang.Deprecated
    public @interface SecurityLevel {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StringProperty {
    }

    private native void closeSessionNative(byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native byte[] decryptNative(android.media.MediaDrm mediaDrm, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native byte[] encryptNative(android.media.MediaDrm mediaDrm, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    private native android.media.MediaDrm.KeyRequest getKeyRequestNative(byte[] bArr, byte[] bArr2, java.lang.String str, int i, java.util.HashMap<java.lang.String, java.lang.String> hashMap) throws android.media.NotProvisionedException;

    private native android.os.PersistableBundle getMetricsNative();

    private native android.media.MediaDrm.ProvisionRequest getProvisionRequestNative(int i, java.lang.String str);

    private static final native byte[] getSupportedCryptoSchemesNative();

    private static final native boolean isCryptoSchemeSupportedNative(byte[] bArr, java.lang.String str, int i);

    private static final native void native_init();

    private final native void native_setup(java.lang.Object obj, byte[] bArr, java.lang.String str);

    private native byte[] openSessionNative(int i) throws android.media.NotProvisionedException, android.media.ResourceBusyException;

    private native android.media.MediaDrm.Certificate provideProvisionResponseNative(byte[] bArr) throws android.media.DeniedByServerException;

    /* JADX INFO: Access modifiers changed from: private */
    public static final native void setCipherAlgorithmNative(android.media.MediaDrm mediaDrm, byte[] bArr, java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native void setMacAlgorithmNative(android.media.MediaDrm mediaDrm, byte[] bArr, java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native void setPlaybackId(byte[] bArr, java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native byte[] signNative(android.media.MediaDrm mediaDrm, byte[] bArr, byte[] bArr2, byte[] bArr3);

    private static final native byte[] signRSANative(android.media.MediaDrm mediaDrm, byte[] bArr, java.lang.String str, byte[] bArr2, byte[] bArr3);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native boolean verifyNative(android.media.MediaDrm mediaDrm, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    public native int getConnectedHdcpLevel();

    public native java.util.List<android.media.MediaDrm.LogMessage> getLogMessages();

    public native int getMaxHdcpLevel();

    public native int getMaxSessionCount();

    public native java.util.List<byte[]> getOfflineLicenseKeySetIds();

    public native int getOfflineLicenseState(byte[] bArr);

    public native int getOpenSessionCount();

    public native byte[] getPropertyByteArray(java.lang.String str);

    public native java.lang.String getPropertyString(java.lang.String str);

    public native byte[] getSecureStop(byte[] bArr);

    public native java.util.List<byte[]> getSecureStopIds();

    public native java.util.List<byte[]> getSecureStops();

    public native int getSecurityLevel(byte[] bArr);

    public final native void native_release();

    public native byte[] provideKeyResponse(byte[] bArr, byte[] bArr2) throws android.media.NotProvisionedException, android.media.DeniedByServerException;

    public native java.util.HashMap<java.lang.String, java.lang.String> queryKeyStatus(byte[] bArr);

    public native void releaseSecureStops(byte[] bArr);

    public native void removeAllSecureStops();

    public native void removeKeys(byte[] bArr);

    public native void removeOfflineLicense(byte[] bArr);

    public native void removeSecureStop(byte[] bArr);

    public native boolean requiresSecureDecoder(java.lang.String str, int i);

    public native void restoreKeys(byte[] bArr, byte[] bArr2);

    public native void setPropertyByteArray(java.lang.String str, byte[] bArr);

    public native void setPropertyString(java.lang.String str, java.lang.String str2);

    public static final boolean isCryptoSchemeSupported(java.util.UUID uuid) {
        return isCryptoSchemeSupportedNative(getByteArrayFromUUID(uuid), null, 0);
    }

    public static final boolean isCryptoSchemeSupported(java.util.UUID uuid, java.lang.String str) {
        return isCryptoSchemeSupportedNative(getByteArrayFromUUID(uuid), str, 0);
    }

    public static final boolean isCryptoSchemeSupported(java.util.UUID uuid, java.lang.String str, int i) {
        return isCryptoSchemeSupportedNative(getByteArrayFromUUID(uuid), str, i);
    }

    public static final java.util.List<java.util.UUID> getSupportedCryptoSchemes() {
        return getUUIDsFromByteArray(getSupportedCryptoSchemesNative());
    }

    private static final byte[] getByteArrayFromUUID(java.util.UUID uuid) {
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        byte[] bArr = new byte[16];
        for (int i = 0; i < 8; i++) {
            int i2 = (7 - i) * 8;
            bArr[i] = (byte) (mostSignificantBits >>> i2);
            bArr[i + 8] = (byte) (leastSignificantBits >>> i2);
        }
        return bArr;
    }

    private static final java.util.UUID getUUIDFromByteArray(byte[] bArr, int i) {
        long j = 0;
        long j2 = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            int i3 = i + i2;
            j = (j << 8) | (bArr[i3] & 255);
            j2 = (j2 << 8) | (bArr[i3 + 8] & 255);
        }
        return new java.util.UUID(j, j2);
    }

    private static final java.util.List<java.util.UUID> getUUIDsFromByteArray(byte[] bArr) {
        java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
        for (int i = 0; i < bArr.length; i += 16) {
            linkedHashSet.add(getUUIDFromByteArray(bArr, i));
        }
        return new java.util.ArrayList(linkedHashSet);
    }

    private android.os.Handler createHandler() {
        android.os.Looper myLooper = android.os.Looper.myLooper();
        if (myLooper != null) {
            return new android.os.Handler(myLooper);
        }
        android.os.Looper mainLooper = android.os.Looper.getMainLooper();
        if (mainLooper != null) {
            return new android.os.Handler(mainLooper);
        }
        return null;
    }

    public MediaDrm(java.util.UUID uuid) throws android.media.UnsupportedSchemeException {
        native_setup(new java.lang.ref.WeakReference(this), getByteArrayFromUUID(uuid), this.mAppPackageName);
        this.mCloseGuard.open("release");
    }

    public static final class ErrorCodes {
        public static final int ERROR_CERTIFICATE_MALFORMED = 10;
        public static final int ERROR_CERTIFICATE_MISSING = 11;
        public static final int ERROR_CRYPTO_LIBRARY = 12;
        public static final int ERROR_FRAME_TOO_LARGE = 8;
        public static final int ERROR_GENERIC_OEM = 13;
        public static final int ERROR_GENERIC_PLUGIN = 14;
        public static final int ERROR_INIT_DATA = 15;
        public static final int ERROR_INSUFFICIENT_OUTPUT_PROTECTION = 4;
        public static final int ERROR_INSUFFICIENT_SECURITY = 7;
        public static final int ERROR_KEY_EXPIRED = 2;
        public static final int ERROR_KEY_NOT_LOADED = 16;
        public static final int ERROR_LICENSE_PARSE = 17;
        public static final int ERROR_LICENSE_POLICY = 18;
        public static final int ERROR_LICENSE_RELEASE = 19;
        public static final int ERROR_LICENSE_REQUEST_REJECTED = 20;
        public static final int ERROR_LICENSE_RESTORE = 21;
        public static final int ERROR_LICENSE_STATE = 22;
        public static final int ERROR_LOST_STATE = 9;
        public static final int ERROR_MEDIA_FRAMEWORK = 23;
        public static final int ERROR_NO_KEY = 1;
        public static final int ERROR_PROVISIONING_CERTIFICATE = 24;
        public static final int ERROR_PROVISIONING_CONFIG = 25;
        public static final int ERROR_PROVISIONING_PARSE = 26;
        public static final int ERROR_PROVISIONING_REQUEST_REJECTED = 27;
        public static final int ERROR_PROVISIONING_RETRY = 28;
        public static final int ERROR_RESOURCE_BUSY = 3;
        public static final int ERROR_RESOURCE_CONTENTION = 29;
        public static final int ERROR_SECURE_STOP_RELEASE = 30;
        public static final int ERROR_SESSION_NOT_OPENED = 5;
        public static final int ERROR_STORAGE_READ = 31;
        public static final int ERROR_STORAGE_WRITE = 32;
        public static final int ERROR_UNKNOWN = 0;
        public static final int ERROR_UNSUPPORTED_OPERATION = 6;
        public static final int ERROR_ZERO_SUBSAMPLES = 33;

        private ErrorCodes() {
        }
    }

    public static final class MediaDrmStateException extends java.lang.IllegalStateException implements android.media.MediaDrmThrowable {
        private final java.lang.String mDiagnosticInfo;
        private final int mErrorCode;
        private final int mErrorContext;
        private final int mOemError;
        private final int mVendorError;

        public MediaDrmStateException(int i, java.lang.String str) {
            this(str, i, 0, 0, 0);
        }

        public MediaDrmStateException(java.lang.String str, int i, int i2, int i3, int i4) {
            super(str);
            this.mErrorCode = i;
            this.mVendorError = i2;
            this.mOemError = i3;
            this.mErrorContext = i4;
            this.mDiagnosticInfo = "android.media.MediaDrm.error_" + (i < 0 ? "neg_" : "") + java.lang.Math.abs(i);
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        @Override // android.media.MediaDrmThrowable
        public int getVendorError() {
            return this.mVendorError;
        }

        @Override // android.media.MediaDrmThrowable
        public int getOemError() {
            return this.mOemError;
        }

        @Override // android.media.MediaDrmThrowable
        public int getErrorContext() {
            return this.mErrorContext;
        }

        public boolean isTransient() {
            return this.mErrorCode == 28 || this.mErrorCode == 29;
        }

        public java.lang.String getDiagnosticInfo() {
            return this.mDiagnosticInfo;
        }
    }

    public static final class SessionException extends java.lang.RuntimeException implements android.media.MediaDrmThrowable {
        public static final int ERROR_RESOURCE_CONTENTION = 1;
        public static final int ERROR_UNKNOWN = 0;
        private final int mErrorCode;
        private final int mErrorContext;
        private final int mOemError;
        private final int mVendorError;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface SessionErrorCode {
        }

        public SessionException(int i, java.lang.String str) {
            this(str, i, 0, 0, 0);
        }

        public SessionException(java.lang.String str, int i, int i2, int i3, int i4) {
            super(str);
            this.mErrorCode = i;
            this.mVendorError = i2;
            this.mOemError = i3;
            this.mErrorContext = i4;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        @Override // android.media.MediaDrmThrowable
        public int getVendorError() {
            return this.mVendorError;
        }

        @Override // android.media.MediaDrmThrowable
        public int getOemError() {
            return this.mOemError;
        }

        @Override // android.media.MediaDrmThrowable
        public int getErrorContext() {
            return this.mErrorContext;
        }

        public boolean isTransient() {
            return this.mErrorCode == 1;
        }
    }

    public void setOnExpirationUpdateListener(android.media.MediaDrm.OnExpirationUpdateListener onExpirationUpdateListener, android.os.Handler handler) {
        setListenerWithHandler(201, handler, onExpirationUpdateListener, new android.media.MediaDrm$$ExternalSyntheticLambda0(this));
    }

    public void setOnExpirationUpdateListener(java.util.concurrent.Executor executor, android.media.MediaDrm.OnExpirationUpdateListener onExpirationUpdateListener) {
        setListenerWithExecutor(201, executor, onExpirationUpdateListener, new android.media.MediaDrm$$ExternalSyntheticLambda0(this));
    }

    public void clearOnExpirationUpdateListener() {
        clearGenericListener(201);
    }

    public void setOnKeyStatusChangeListener(android.media.MediaDrm.OnKeyStatusChangeListener onKeyStatusChangeListener, android.os.Handler handler) {
        setListenerWithHandler(202, handler, onKeyStatusChangeListener, new android.media.MediaDrm$$ExternalSyntheticLambda1(this));
    }

    public void setOnKeyStatusChangeListener(java.util.concurrent.Executor executor, android.media.MediaDrm.OnKeyStatusChangeListener onKeyStatusChangeListener) {
        setListenerWithExecutor(202, executor, onKeyStatusChangeListener, new android.media.MediaDrm$$ExternalSyntheticLambda1(this));
    }

    public void clearOnKeyStatusChangeListener() {
        clearGenericListener(202);
    }

    public void setOnSessionLostStateListener(android.media.MediaDrm.OnSessionLostStateListener onSessionLostStateListener, android.os.Handler handler) {
        setListenerWithHandler(203, handler, onSessionLostStateListener, new android.media.MediaDrm$$ExternalSyntheticLambda5(this));
    }

    public void setOnSessionLostStateListener(java.util.concurrent.Executor executor, android.media.MediaDrm.OnSessionLostStateListener onSessionLostStateListener) {
        setListenerWithExecutor(203, executor, onSessionLostStateListener, new android.media.MediaDrm$$ExternalSyntheticLambda5(this));
    }

    public void clearOnSessionLostStateListener() {
        clearGenericListener(203);
    }

    public static final class KeyStatus {
        public static final int STATUS_EXPIRED = 1;
        public static final int STATUS_INTERNAL_ERROR = 4;
        public static final int STATUS_OUTPUT_NOT_ALLOWED = 2;
        public static final int STATUS_PENDING = 3;
        public static final int STATUS_USABLE = 0;
        public static final int STATUS_USABLE_IN_FUTURE = 5;
        private final byte[] mKeyId;
        private final int mStatusCode;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface KeyStatusCode {
        }

        KeyStatus(byte[] bArr, int i) {
            this.mKeyId = bArr;
            this.mStatusCode = i;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public byte[] getKeyId() {
            return this.mKeyId;
        }
    }

    public void setOnEventListener(android.media.MediaDrm.OnEventListener onEventListener) {
        setOnEventListener(onEventListener, (android.os.Handler) null);
    }

    public void setOnEventListener(android.media.MediaDrm.OnEventListener onEventListener, android.os.Handler handler) {
        setListenerWithHandler(200, handler, onEventListener, new android.media.MediaDrm$$ExternalSyntheticLambda3(this));
    }

    public void setOnEventListener(java.util.concurrent.Executor executor, android.media.MediaDrm.OnEventListener onEventListener) {
        setListenerWithExecutor(200, executor, onEventListener, new android.media.MediaDrm$$ExternalSyntheticLambda3(this));
    }

    public void clearOnEventListener() {
        clearGenericListener(200);
    }

    private <T> void setListenerWithHandler(int i, android.os.Handler handler, T t, java.util.function.Function<T, java.util.function.Consumer<android.media.MediaDrm.ListenerArgs>> function) {
        if (t == null) {
            clearGenericListener(i);
            return;
        }
        if (handler == null) {
            handler = createHandler();
        }
        setGenericListener(i, new android.os.HandlerExecutor(handler), t, function);
    }

    private <T> void setListenerWithExecutor(int i, java.util.concurrent.Executor executor, T t, java.util.function.Function<T, java.util.function.Consumer<android.media.MediaDrm.ListenerArgs>> function) {
        if (executor == null || t == null) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("executor %s listener %s", executor, t));
        }
        setGenericListener(i, executor, t, function);
    }

    private <T> void setGenericListener(int i, java.util.concurrent.Executor executor, T t, java.util.function.Function<T, java.util.function.Consumer<android.media.MediaDrm.ListenerArgs>> function) {
        this.mListenerMap.put(java.lang.Integer.valueOf(i), new android.media.MediaDrm.ListenerWithExecutor(executor, function.apply(t)));
    }

    private void clearGenericListener(int i) {
        this.mListenerMap.remove(java.lang.Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.function.Consumer<android.media.MediaDrm.ListenerArgs> createOnEventListener(final android.media.MediaDrm.OnEventListener onEventListener) {
        return new java.util.function.Consumer() { // from class: android.media.MediaDrm$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.media.MediaDrm.this.lambda$createOnEventListener$0(onEventListener, (android.media.MediaDrm.ListenerArgs) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOnEventListener$0(android.media.MediaDrm.OnEventListener onEventListener, android.media.MediaDrm.ListenerArgs listenerArgs) {
        byte[] bArr;
        byte[] bArr2;
        byte[] bArr3 = listenerArgs.sessionId;
        if (bArr3.length != 0) {
            bArr = bArr3;
        } else {
            bArr = null;
        }
        byte[] bArr4 = listenerArgs.data;
        if (bArr4 != null && bArr4.length == 0) {
            bArr2 = null;
        } else {
            bArr2 = bArr4;
        }
        android.util.Log.i(TAG, "Drm event (" + listenerArgs.arg1 + "," + listenerArgs.arg2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        onEventListener.onEvent(this, bArr, listenerArgs.arg1, listenerArgs.arg2, bArr2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.function.Consumer<android.media.MediaDrm.ListenerArgs> createOnKeyStatusChangeListener(final android.media.MediaDrm.OnKeyStatusChangeListener onKeyStatusChangeListener) {
        return new java.util.function.Consumer() { // from class: android.media.MediaDrm$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.media.MediaDrm.this.lambda$createOnKeyStatusChangeListener$1(onKeyStatusChangeListener, (android.media.MediaDrm.ListenerArgs) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOnKeyStatusChangeListener$1(android.media.MediaDrm.OnKeyStatusChangeListener onKeyStatusChangeListener, android.media.MediaDrm.ListenerArgs listenerArgs) {
        byte[] bArr = listenerArgs.sessionId;
        if (bArr.length > 0) {
            java.util.List<android.media.MediaDrm.KeyStatus> list = listenerArgs.keyStatusList;
            boolean z = listenerArgs.hasNewUsableKey;
            android.util.Log.i(TAG, "Drm key status changed");
            onKeyStatusChangeListener.onKeyStatusChange(this, bArr, list, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.function.Consumer<android.media.MediaDrm.ListenerArgs> createOnExpirationUpdateListener(final android.media.MediaDrm.OnExpirationUpdateListener onExpirationUpdateListener) {
        return new java.util.function.Consumer() { // from class: android.media.MediaDrm$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.media.MediaDrm.this.lambda$createOnExpirationUpdateListener$2(onExpirationUpdateListener, (android.media.MediaDrm.ListenerArgs) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOnExpirationUpdateListener$2(android.media.MediaDrm.OnExpirationUpdateListener onExpirationUpdateListener, android.media.MediaDrm.ListenerArgs listenerArgs) {
        byte[] bArr = listenerArgs.sessionId;
        if (bArr.length > 0) {
            long j = listenerArgs.expirationTime;
            android.util.Log.i(TAG, "Drm key expiration update: " + j);
            onExpirationUpdateListener.onExpirationUpdate(this, bArr, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.function.Consumer<android.media.MediaDrm.ListenerArgs> createOnSessionLostStateListener(final android.media.MediaDrm.OnSessionLostStateListener onSessionLostStateListener) {
        return new java.util.function.Consumer() { // from class: android.media.MediaDrm$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.media.MediaDrm.this.lambda$createOnSessionLostStateListener$3(onSessionLostStateListener, (android.media.MediaDrm.ListenerArgs) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOnSessionLostStateListener$3(android.media.MediaDrm.OnSessionLostStateListener onSessionLostStateListener, android.media.MediaDrm.ListenerArgs listenerArgs) {
        byte[] bArr = listenerArgs.sessionId;
        android.util.Log.i(TAG, "Drm session lost state event: ");
        onSessionLostStateListener.onSessionLostState(this, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ListenerArgs {
        private final int arg1;
        private final int arg2;
        private final byte[] data;
        private final long expirationTime;
        private final boolean hasNewUsableKey;
        private final java.util.List<android.media.MediaDrm.KeyStatus> keyStatusList;
        private final byte[] sessionId;

        public ListenerArgs(int i, int i2, byte[] bArr, byte[] bArr2, long j, java.util.List<android.media.MediaDrm.KeyStatus> list, boolean z) {
            this.arg1 = i;
            this.arg2 = i2;
            this.sessionId = bArr;
            this.data = bArr2;
            this.expirationTime = j;
            this.keyStatusList = list;
            this.hasNewUsableKey = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ListenerWithExecutor {
        private final java.util.function.Consumer<android.media.MediaDrm.ListenerArgs> mConsumer;
        private final java.util.concurrent.Executor mExecutor;

        public ListenerWithExecutor(java.util.concurrent.Executor executor, java.util.function.Consumer<android.media.MediaDrm.ListenerArgs> consumer) {
            this.mExecutor = executor;
            this.mConsumer = consumer;
        }
    }

    private java.util.List<android.media.MediaDrm.KeyStatus> keyStatusListFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList(readInt);
        while (true) {
            int i = readInt - 1;
            if (readInt > 0) {
                arrayList.add(new android.media.MediaDrm.KeyStatus(parcel.createByteArray(), parcel.readInt()));
                readInt = i;
            } else {
                return arrayList;
            }
        }
    }

    private static void postEventFromNative(java.lang.Object obj, int i, final int i2, final int i3, final byte[] bArr, final byte[] bArr2, final long j, final java.util.List<android.media.MediaDrm.KeyStatus> list, final boolean z) {
        final android.media.MediaDrm mediaDrm = (android.media.MediaDrm) ((java.lang.ref.WeakReference) obj).get();
        if (mediaDrm == null) {
        }
        switch (i) {
            case 200:
            case 201:
            case 202:
            case 203:
                final android.media.MediaDrm.ListenerWithExecutor listenerWithExecutor = mediaDrm.mListenerMap.get(java.lang.Integer.valueOf(i));
                if (listenerWithExecutor != null) {
                    listenerWithExecutor.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaDrm$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.MediaDrm.lambda$postEventFromNative$4(android.media.MediaDrm.this, i2, i3, bArr, bArr2, j, list, z, listenerWithExecutor);
                        }
                    });
                    break;
                }
                break;
            default:
                android.util.Log.e(TAG, "Unknown message type " + i);
                break;
        }
    }

    static /* synthetic */ void lambda$postEventFromNative$4(android.media.MediaDrm mediaDrm, int i, int i2, byte[] bArr, byte[] bArr2, long j, java.util.List list, boolean z, android.media.MediaDrm.ListenerWithExecutor listenerWithExecutor) {
        if (mediaDrm.mNativeContext == 0) {
            android.util.Log.w(TAG, "MediaDrm went away with unhandled events");
        } else {
            listenerWithExecutor.mConsumer.accept(new android.media.MediaDrm.ListenerArgs(i, i2, bArr, bArr2, j, list, z));
        }
    }

    public byte[] openSession() throws android.media.NotProvisionedException, android.media.ResourceBusyException {
        return openSession(getMaxSecurityLevel());
    }

    public byte[] openSession(int i) throws android.media.NotProvisionedException, android.media.ResourceBusyException {
        byte[] openSessionNative = openSessionNative(i);
        this.mPlaybackComponentMap.put(java.nio.ByteBuffer.wrap(openSessionNative), new android.media.MediaDrm.PlaybackComponent(openSessionNative));
        return openSessionNative;
    }

    public void closeSession(byte[] bArr) {
        closeSessionNative(bArr);
        this.mPlaybackComponentMap.remove(java.nio.ByteBuffer.wrap(bArr));
    }

    public static final class KeyRequest {
        public static final int REQUEST_TYPE_INITIAL = 0;
        public static final int REQUEST_TYPE_NONE = 3;
        public static final int REQUEST_TYPE_RELEASE = 2;
        public static final int REQUEST_TYPE_RENEWAL = 1;
        public static final int REQUEST_TYPE_UPDATE = 4;
        private byte[] mData;
        private java.lang.String mDefaultUrl;
        private int mRequestType;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface RequestType {
        }

        KeyRequest() {
        }

        public byte[] getData() {
            if (this.mData == null) {
                throw new java.lang.RuntimeException("KeyRequest is not initialized");
            }
            return this.mData;
        }

        public java.lang.String getDefaultUrl() {
            if (this.mDefaultUrl == null) {
                throw new java.lang.RuntimeException("KeyRequest is not initialized");
            }
            return this.mDefaultUrl;
        }

        public int getRequestType() {
            return this.mRequestType;
        }
    }

    public android.media.MediaDrm.KeyRequest getKeyRequest(byte[] bArr, byte[] bArr2, java.lang.String str, int i, java.util.HashMap<java.lang.String, java.lang.String> hashMap) throws android.media.NotProvisionedException {
        java.util.HashMap<java.lang.String, java.lang.String> hashMap2;
        byte[] bArr3;
        if (hashMap == null) {
            hashMap2 = new java.util.HashMap<>();
        } else {
            hashMap2 = new java.util.HashMap<>(hashMap);
        }
        byte[] newestAvailablePackageCertificateRawBytes = getNewestAvailablePackageCertificateRawBytes();
        if (newestAvailablePackageCertificateRawBytes == null) {
            bArr3 = null;
        } else {
            bArr3 = getDigestBytes(newestAvailablePackageCertificateRawBytes, "SHA-256");
        }
        if (bArr3 != null) {
            hashMap2.put("package_certificate_hash_bytes", java.util.Base64.getEncoder().encodeToString(bArr3));
        }
        return getKeyRequestNative(bArr, bArr2, str, i, hashMap2);
    }

    private byte[] getNewestAvailablePackageCertificateRawBytes() {
        android.content.pm.PackageInfo packageInfo;
        android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
        if (currentApplication == null) {
            android.util.Log.w(TAG, "pkg cert: Application is null");
            return null;
        }
        android.content.pm.PackageManager packageManager = currentApplication.getPackageManager();
        if (packageManager == null) {
            android.util.Log.w(TAG, "pkg cert: PackageManager is null");
            return null;
        }
        try {
            packageInfo = packageManager.getPackageInfo(this.mAppPackageName, 134217728);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, this.mAppPackageName, e);
            packageInfo = null;
        }
        if (packageInfo == null || packageInfo.signingInfo == null) {
            android.util.Log.w(TAG, "pkg cert: PackageInfo or SigningInfo is null");
            return null;
        }
        android.content.pm.Signature[] apkContentsSigners = packageInfo.signingInfo.getApkContentsSigners();
        if (apkContentsSigners != null && apkContentsSigners.length == 1) {
            return apkContentsSigners[0].toByteArray();
        }
        android.util.Log.w(TAG, "pkg cert: " + apkContentsSigners.length + " signers");
        return null;
    }

    private static byte[] getDigestBytes(byte[] bArr, java.lang.String str) {
        try {
            return java.security.MessageDigest.getInstance(str).digest(bArr);
        } catch (java.security.NoSuchAlgorithmException e) {
            android.util.Log.w(TAG, str, e);
            return null;
        }
    }

    public static final class ProvisionRequest {
        private byte[] mData;
        private java.lang.String mDefaultUrl;

        ProvisionRequest() {
        }

        public byte[] getData() {
            if (this.mData == null) {
                throw new java.lang.RuntimeException("ProvisionRequest is not initialized");
            }
            return this.mData;
        }

        public java.lang.String getDefaultUrl() {
            if (this.mDefaultUrl == null) {
                throw new java.lang.RuntimeException("ProvisionRequest is not initialized");
            }
            return this.mDefaultUrl;
        }
    }

    public android.media.MediaDrm.ProvisionRequest getProvisionRequest() {
        return getProvisionRequestNative(0, "");
    }

    public void provideProvisionResponse(byte[] bArr) throws android.media.DeniedByServerException {
        provideProvisionResponseNative(bArr);
    }

    public void releaseAllSecureStops() {
        removeAllSecureStops();
    }

    public static final int getMaxSecurityLevel() {
        return 6;
    }

    public android.os.PersistableBundle getMetrics() {
        return getMetricsNative();
    }

    public final class CryptoSession {
        private byte[] mSessionId;

        CryptoSession(byte[] bArr, java.lang.String str, java.lang.String str2) {
            this.mSessionId = bArr;
            android.media.MediaDrm.setCipherAlgorithmNative(android.media.MediaDrm.this, bArr, str);
            android.media.MediaDrm.setMacAlgorithmNative(android.media.MediaDrm.this, bArr, str2);
        }

        public byte[] encrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
            return android.media.MediaDrm.encryptNative(android.media.MediaDrm.this, this.mSessionId, bArr, bArr2, bArr3);
        }

        public byte[] decrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
            return android.media.MediaDrm.decryptNative(android.media.MediaDrm.this, this.mSessionId, bArr, bArr2, bArr3);
        }

        public byte[] sign(byte[] bArr, byte[] bArr2) {
            return android.media.MediaDrm.signNative(android.media.MediaDrm.this, this.mSessionId, bArr, bArr2);
        }

        public boolean verify(byte[] bArr, byte[] bArr2, byte[] bArr3) {
            return android.media.MediaDrm.verifyNative(android.media.MediaDrm.this, this.mSessionId, bArr, bArr2, bArr3);
        }
    }

    public android.media.MediaDrm.CryptoSession getCryptoSession(byte[] bArr, java.lang.String str, java.lang.String str2) {
        return new android.media.MediaDrm.CryptoSession(bArr, str, str2);
    }

    public static final class CertificateRequest {
        private byte[] mData;
        private java.lang.String mDefaultUrl;

        CertificateRequest(byte[] bArr, java.lang.String str) {
            this.mData = bArr;
            this.mDefaultUrl = str;
        }

        public byte[] getData() {
            return this.mData;
        }

        public java.lang.String getDefaultUrl() {
            return this.mDefaultUrl;
        }
    }

    public android.media.MediaDrm.CertificateRequest getCertificateRequest(int i, java.lang.String str) {
        android.media.MediaDrm.ProvisionRequest provisionRequestNative = getProvisionRequestNative(i, str);
        return new android.media.MediaDrm.CertificateRequest(provisionRequestNative.getData(), provisionRequestNative.getDefaultUrl());
    }

    public static final class Certificate {
        private byte[] mCertificateData;
        private byte[] mWrappedKey;

        Certificate() {
        }

        public byte[] getWrappedPrivateKey() {
            if (this.mWrappedKey == null) {
                throw new java.lang.RuntimeException("Certificate is not initialized");
            }
            return this.mWrappedKey;
        }

        public byte[] getContent() {
            if (this.mCertificateData == null) {
                throw new java.lang.RuntimeException("Certificate is not initialized");
            }
            return this.mCertificateData;
        }
    }

    public android.media.MediaDrm.Certificate provideCertificateResponse(byte[] bArr) throws android.media.DeniedByServerException {
        return provideProvisionResponseNative(bArr);
    }

    public byte[] signRSA(byte[] bArr, java.lang.String str, byte[] bArr2, byte[] bArr3) {
        return signRSANative(this, bArr, str, bArr2, bArr3);
    }

    public boolean requiresSecureDecoder(java.lang.String str) {
        return requiresSecureDecoder(str, getMaxSecurityLevel());
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            release();
        } finally {
            super.finalize();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        release();
    }

    @java.lang.Deprecated
    public void release() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            native_release();
            this.mPlaybackComponentMap.clear();
        }
    }

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }

    public static final class MetricsConstants {
        public static final java.lang.String CLOSE_SESSION_ERROR_COUNT = "drm.mediadrm.close_session.error.count";
        public static final java.lang.String CLOSE_SESSION_ERROR_LIST = "drm.mediadrm.close_session.error.list";
        public static final java.lang.String CLOSE_SESSION_OK_COUNT = "drm.mediadrm.close_session.ok.count";
        public static final java.lang.String EVENT_KEY_EXPIRED_COUNT = "drm.mediadrm.event.KEY_EXPIRED.count";
        public static final java.lang.String EVENT_KEY_NEEDED_COUNT = "drm.mediadrm.event.KEY_NEEDED.count";
        public static final java.lang.String EVENT_PROVISION_REQUIRED_COUNT = "drm.mediadrm.event.PROVISION_REQUIRED.count";
        public static final java.lang.String EVENT_SESSION_RECLAIMED_COUNT = "drm.mediadrm.event.SESSION_RECLAIMED.count";
        public static final java.lang.String EVENT_VENDOR_DEFINED_COUNT = "drm.mediadrm.event.VENDOR_DEFINED.count";
        public static final java.lang.String GET_DEVICE_UNIQUE_ID_ERROR_COUNT = "drm.mediadrm.get_device_unique_id.error.count";
        public static final java.lang.String GET_DEVICE_UNIQUE_ID_ERROR_LIST = "drm.mediadrm.get_device_unique_id.error.list";
        public static final java.lang.String GET_DEVICE_UNIQUE_ID_OK_COUNT = "drm.mediadrm.get_device_unique_id.ok.count";
        public static final java.lang.String GET_KEY_REQUEST_ERROR_COUNT = "drm.mediadrm.get_key_request.error.count";
        public static final java.lang.String GET_KEY_REQUEST_ERROR_LIST = "drm.mediadrm.get_key_request.error.list";
        public static final java.lang.String GET_KEY_REQUEST_OK_COUNT = "drm.mediadrm.get_key_request.ok.count";
        public static final java.lang.String GET_KEY_REQUEST_OK_TIME_MICROS = "drm.mediadrm.get_key_request.ok.average_time_micros";
        public static final java.lang.String GET_PROVISION_REQUEST_ERROR_COUNT = "drm.mediadrm.get_provision_request.error.count";
        public static final java.lang.String GET_PROVISION_REQUEST_ERROR_LIST = "drm.mediadrm.get_provision_request.error.list";
        public static final java.lang.String GET_PROVISION_REQUEST_OK_COUNT = "drm.mediadrm.get_provision_request.ok.count";
        public static final java.lang.String KEY_STATUS_EXPIRED_COUNT = "drm.mediadrm.key_status.EXPIRED.count";
        public static final java.lang.String KEY_STATUS_INTERNAL_ERROR_COUNT = "drm.mediadrm.key_status.INTERNAL_ERROR.count";
        public static final java.lang.String KEY_STATUS_OUTPUT_NOT_ALLOWED_COUNT = "drm.mediadrm.key_status_change.OUTPUT_NOT_ALLOWED.count";
        public static final java.lang.String KEY_STATUS_PENDING_COUNT = "drm.mediadrm.key_status_change.PENDING.count";
        public static final java.lang.String KEY_STATUS_USABLE_COUNT = "drm.mediadrm.key_status_change.USABLE.count";
        public static final java.lang.String OPEN_SESSION_ERROR_COUNT = "drm.mediadrm.open_session.error.count";
        public static final java.lang.String OPEN_SESSION_ERROR_LIST = "drm.mediadrm.open_session.error.list";
        public static final java.lang.String OPEN_SESSION_OK_COUNT = "drm.mediadrm.open_session.ok.count";
        public static final java.lang.String PROVIDE_KEY_RESPONSE_ERROR_COUNT = "drm.mediadrm.provide_key_response.error.count";
        public static final java.lang.String PROVIDE_KEY_RESPONSE_ERROR_LIST = "drm.mediadrm.provide_key_response.error.list";
        public static final java.lang.String PROVIDE_KEY_RESPONSE_OK_COUNT = "drm.mediadrm.provide_key_response.ok.count";
        public static final java.lang.String PROVIDE_KEY_RESPONSE_OK_TIME_MICROS = "drm.mediadrm.provide_key_response.ok.average_time_micros";
        public static final java.lang.String PROVIDE_PROVISION_RESPONSE_ERROR_COUNT = "drm.mediadrm.provide_provision_response.error.count";
        public static final java.lang.String PROVIDE_PROVISION_RESPONSE_ERROR_LIST = "drm.mediadrm.provide_provision_response.error.list";
        public static final java.lang.String PROVIDE_PROVISION_RESPONSE_OK_COUNT = "drm.mediadrm.provide_provision_response.ok.count";
        public static final java.lang.String SESSION_END_TIMES_MS = "drm.mediadrm.session_end_times_ms";
        public static final java.lang.String SESSION_START_TIMES_MS = "drm.mediadrm.session_start_times_ms";

        private MetricsConstants() {
        }
    }

    public android.media.MediaDrm.PlaybackComponent getPlaybackComponent(byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException("sessionId is null");
        }
        return this.mPlaybackComponentMap.get(java.nio.ByteBuffer.wrap(bArr));
    }

    public final class PlaybackComponent {
        private android.media.metrics.LogSessionId mLogSessionId = android.media.metrics.LogSessionId.LOG_SESSION_ID_NONE;
        private final byte[] mSessionId;

        public PlaybackComponent(byte[] bArr) {
            this.mSessionId = bArr;
        }

        public void setLogSessionId(android.media.metrics.LogSessionId logSessionId) {
            java.util.Objects.requireNonNull(logSessionId);
            if (logSessionId.getStringId() == null) {
                throw new java.lang.IllegalArgumentException("playbackId is null");
            }
            android.media.MediaDrm.this.setPlaybackId(this.mSessionId, logSessionId.getStringId());
            this.mLogSessionId = logSessionId;
        }

        public android.media.metrics.LogSessionId getLogSessionId() {
            return this.mLogSessionId;
        }
    }

    public static final class LogMessage {
        private final java.lang.String message;
        private final int priority;
        private final long timestampMillis;

        public final long getTimestampMillis() {
            return this.timestampMillis;
        }

        public final int getPriority() {
            return this.priority;
        }

        public final java.lang.String getMessage() {
            return this.message;
        }

        private LogMessage(long j, int i, java.lang.String str) {
            this.timestampMillis = j;
            if (i < 2 || i > 7) {
                throw new java.lang.IllegalArgumentException("invalid log priority " + i);
            }
            this.priority = i;
            this.message = str;
        }

        private char logPriorityChar() {
            switch (this.priority) {
                case 2:
                    return 'V';
                case 3:
                    return 'D';
                case 4:
                    return 'I';
                case 5:
                    return 'W';
                case 6:
                    return android.text.format.DateFormat.DAY;
                case 7:
                    return 'F';
                default:
                    return 'U';
            }
        }

        public java.lang.String toString() {
            return java.lang.String.format("LogMessage{%s %c %s}", java.time.Instant.ofEpochMilli(this.timestampMillis), java.lang.Character.valueOf(logPriorityChar()), this.message);
        }
    }
}
