package android.media;

/* loaded from: classes2.dex */
public final class MediaCas implements java.lang.AutoCloseable {
    public static final int PLUGIN_STATUS_PHYSICAL_MODULE_CHANGED = 0;
    public static final int PLUGIN_STATUS_SESSION_NUMBER_CHANGED = 1;
    public static final int SCRAMBLING_MODE_AES128 = 9;
    public static final int SCRAMBLING_MODE_AES_CBC = 14;
    public static final int SCRAMBLING_MODE_AES_ECB = 10;
    public static final int SCRAMBLING_MODE_AES_SCTE52 = 11;
    public static final int SCRAMBLING_MODE_DVB_CISSA_V1 = 6;
    public static final int SCRAMBLING_MODE_DVB_CSA1 = 1;
    public static final int SCRAMBLING_MODE_DVB_CSA2 = 2;
    public static final int SCRAMBLING_MODE_DVB_CSA3_ENHANCE = 5;
    public static final int SCRAMBLING_MODE_DVB_CSA3_MINIMAL = 4;
    public static final int SCRAMBLING_MODE_DVB_CSA3_STANDARD = 3;
    public static final int SCRAMBLING_MODE_DVB_IDSA = 7;
    public static final int SCRAMBLING_MODE_MULTI2 = 8;
    public static final int SCRAMBLING_MODE_RESERVED = 0;
    public static final int SCRAMBLING_MODE_TDES_ECB = 12;
    public static final int SCRAMBLING_MODE_TDES_SCTE52 = 13;
    public static final int SESSION_USAGE_LIVE = 0;
    public static final int SESSION_USAGE_PLAYBACK = 1;
    public static final int SESSION_USAGE_RECORD = 2;
    public static final int SESSION_USAGE_TIMESHIFT = 3;
    private static final java.lang.String TAG = "MediaCas";
    private static final android.util.Singleton<android.hardware.cas.IMediaCasService> sService = new android.util.Singleton<android.hardware.cas.IMediaCasService>() { // from class: android.media.MediaCas.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public android.hardware.cas.IMediaCasService create() {
            try {
                android.util.Log.d(android.media.MediaCas.TAG, "Trying to get AIDL service");
                android.hardware.cas.IMediaCasService asInterface = android.hardware.cas.IMediaCasService.Stub.asInterface(android.os.ServiceManager.waitForDeclaredService(android.hardware.cas.IMediaCasService.DESCRIPTOR + "/default"));
                if (asInterface != null) {
                    return asInterface;
                }
                return null;
            } catch (java.lang.Exception e) {
                android.util.Log.d(android.media.MediaCas.TAG, "Failed to get cas AIDL service");
                return null;
            }
        }
    };
    private static final android.util.Singleton<android.hardware.cas.V1_0.IMediaCasService> sServiceHidl = new android.util.Singleton<android.hardware.cas.V1_0.IMediaCasService>() { // from class: android.media.MediaCas.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public android.hardware.cas.V1_0.IMediaCasService create() {
            try {
                android.util.Log.d(android.media.MediaCas.TAG, "Trying to get cas@1.2 service");
                android.hardware.cas.V1_2.IMediaCasService service = android.hardware.cas.V1_2.IMediaCasService.getService(true);
                if (service != null) {
                    return service;
                }
            } catch (java.lang.Exception e) {
                android.util.Log.d(android.media.MediaCas.TAG, "Failed to get cas@1.2 service");
            }
            try {
                android.util.Log.d(android.media.MediaCas.TAG, "Trying to get cas@1.1 service");
                android.hardware.cas.V1_1.IMediaCasService service2 = android.hardware.cas.V1_1.IMediaCasService.getService(true);
                if (service2 != null) {
                    return service2;
                }
            } catch (java.lang.Exception e2) {
                android.util.Log.d(android.media.MediaCas.TAG, "Failed to get cas@1.1 service");
            }
            try {
                android.util.Log.d(android.media.MediaCas.TAG, "Trying to get cas@1.0 service");
                return android.hardware.cas.V1_0.IMediaCasService.getService(true);
            } catch (java.lang.Exception e3) {
                android.util.Log.d(android.media.MediaCas.TAG, "Failed to get cas@1.0 service");
                return null;
            }
        }
    };
    private int mCasSystemId;
    private int mClientId;
    private android.media.MediaCas.EventHandler mEventHandler;
    private android.os.HandlerThread mHandlerThread;
    private android.media.MediaCas.EventListener mListener;
    private int mPriorityHint;
    private java.lang.String mTvInputServiceSessionId;
    private int mUserId;
    private android.hardware.cas.ICas mICas = null;
    private android.hardware.cas.V1_0.ICas mICasHidl = null;
    private android.hardware.cas.V1_1.ICas mICasHidl11 = null;
    private android.hardware.cas.V1_2.ICas mICasHidl12 = null;
    private android.media.tv.tunerresourcemanager.TunerResourceManager mTunerResourceManager = null;
    private final java.util.Map<android.media.MediaCas.Session, java.lang.Integer> mSessionMap = new java.util.HashMap();
    private final android.hardware.cas.ICasListener.Stub mBinder = new android.hardware.cas.ICasListener.Stub() { // from class: android.media.MediaCas.3
        @Override // android.hardware.cas.ICasListener
        public void onEvent(int i, int i2, byte[] bArr) throws android.os.RemoteException {
            if (android.media.MediaCas.this.mEventHandler != null) {
                android.media.MediaCas.this.mEventHandler.sendMessage(android.media.MediaCas.this.mEventHandler.obtainMessage(0, i, i2, bArr));
            }
        }

        @Override // android.hardware.cas.ICasListener
        public void onSessionEvent(byte[] bArr, int i, int i2, byte[] bArr2) throws android.os.RemoteException {
            if (android.media.MediaCas.this.mEventHandler != null) {
                android.os.Message obtainMessage = android.media.MediaCas.this.mEventHandler.obtainMessage();
                obtainMessage.what = 1;
                obtainMessage.arg1 = i;
                obtainMessage.arg2 = i2;
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putByteArray("sessionId", bArr);
                bundle.putByteArray("data", bArr2);
                obtainMessage.setData(bundle);
                android.media.MediaCas.this.mEventHandler.sendMessage(obtainMessage);
            }
        }

        @Override // android.hardware.cas.ICasListener
        public void onStatusUpdate(byte b, int i) throws android.os.RemoteException {
            if (android.media.MediaCas.this.mEventHandler != null) {
                android.media.MediaCas.this.mEventHandler.sendMessage(android.media.MediaCas.this.mEventHandler.obtainMessage(2, b, i));
            }
        }

        @Override // android.hardware.cas.ICasListener
        public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
            return "bc51d8d70a55ec4723d3f73d0acf7003306bf69f";
        }

        @Override // android.hardware.cas.ICasListener
        public int getInterfaceVersion() throws android.os.RemoteException {
            return 1;
        }
    };
    private final android.hardware.cas.V1_2.ICasListener.Stub mBinderHidl = new android.hardware.cas.V1_2.ICasListener.Stub() { // from class: android.media.MediaCas.4
        @Override // android.hardware.cas.V1_0.ICasListener
        public void onEvent(int i, int i2, java.util.ArrayList<java.lang.Byte> arrayList) throws android.os.RemoteException {
            if (android.media.MediaCas.this.mEventHandler != null) {
                android.media.MediaCas.this.mEventHandler.sendMessage(android.media.MediaCas.this.mEventHandler.obtainMessage(0, i, i2, android.media.MediaCas.this.toBytes(arrayList)));
            }
        }

        @Override // android.hardware.cas.V1_1.ICasListener
        public void onSessionEvent(java.util.ArrayList<java.lang.Byte> arrayList, int i, int i2, java.util.ArrayList<java.lang.Byte> arrayList2) throws android.os.RemoteException {
            if (android.media.MediaCas.this.mEventHandler != null) {
                android.os.Message obtainMessage = android.media.MediaCas.this.mEventHandler.obtainMessage();
                obtainMessage.what = 1;
                obtainMessage.arg1 = i;
                obtainMessage.arg2 = i2;
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putByteArray("sessionId", android.media.MediaCas.this.toBytes(arrayList));
                bundle.putByteArray("data", android.media.MediaCas.this.toBytes(arrayList2));
                obtainMessage.setData(bundle);
                android.media.MediaCas.this.mEventHandler.sendMessage(obtainMessage);
            }
        }

        @Override // android.hardware.cas.V1_2.ICasListener
        public void onStatusUpdate(byte b, int i) throws android.os.RemoteException {
            if (android.media.MediaCas.this.mEventHandler != null) {
                android.media.MediaCas.this.mEventHandler.sendMessage(android.media.MediaCas.this.mEventHandler.obtainMessage(2, b, i));
            }
        }
    };
    private final android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener mResourceListener = new android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener() { // from class: android.media.MediaCas.5
        @Override // android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener
        public void onReclaimResources() {
            synchronized (android.media.MediaCas.this.mSessionMap) {
                java.util.Iterator it = new java.util.ArrayList(android.media.MediaCas.this.mSessionMap.keySet()).iterator();
                while (it.hasNext()) {
                    ((android.media.MediaCas.Session) it.next()).close();
                }
            }
            android.media.MediaCas.this.mEventHandler.sendMessage(android.media.MediaCas.this.mEventHandler.obtainMessage(3));
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PluginStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScramblingMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SessionUsage {
    }

    static android.hardware.cas.IMediaCasService getService() {
        return sService.get();
    }

    static android.hardware.cas.V1_0.IMediaCasService getServiceHidl() {
        return sServiceHidl.get();
    }

    private void validateInternalStates() {
        if (this.mICas == null && this.mICasHidl == null) {
            throw new java.lang.IllegalStateException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupAndRethrowIllegalState() {
        this.mICas = null;
        this.mICasHidl = null;
        this.mICasHidl11 = null;
        this.mICasHidl12 = null;
        throw new java.lang.IllegalStateException();
    }

    private class EventHandler extends android.os.Handler {
        private static final java.lang.String DATA_KEY = "data";
        private static final int MSG_CAS_EVENT = 0;
        private static final int MSG_CAS_RESOURCE_LOST = 3;
        private static final int MSG_CAS_SESSION_EVENT = 1;
        private static final int MSG_CAS_STATUS_EVENT = 2;
        private static final java.lang.String SESSION_KEY = "sessionId";

        public EventHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (message.what == 0) {
                android.media.MediaCas.this.mListener.onEvent(android.media.MediaCas.this, message.arg1, message.arg2, message.obj == null ? new byte[0] : (byte[]) message.obj);
                return;
            }
            if (message.what == 1) {
                android.os.Bundle data = message.getData();
                android.media.MediaCas.this.mListener.onSessionEvent(android.media.MediaCas.this, android.media.MediaCas.this.createFromSessionId(data.getByteArray("sessionId")), message.arg1, message.arg2, data.getByteArray("data"));
            } else {
                if (message.what == 2) {
                    if (message.arg1 == 1 && android.media.MediaCas.this.mTunerResourceManager != null) {
                        android.media.MediaCas.this.mTunerResourceManager.updateCasInfo(android.media.MediaCas.this.mCasSystemId, message.arg2);
                    }
                    android.media.MediaCas.this.mListener.onPluginStatusUpdate(android.media.MediaCas.this, message.arg1, message.arg2);
                    return;
                }
                if (message.what == 3) {
                    android.media.MediaCas.this.mListener.onResourceLost(android.media.MediaCas.this);
                }
            }
        }
    }

    public static class PluginDescriptor {
        private final int mCASystemId;
        private final java.lang.String mName;

        private PluginDescriptor() {
            this.mCASystemId = 65535;
            this.mName = null;
        }

        PluginDescriptor(android.hardware.cas.AidlCasPluginDescriptor aidlCasPluginDescriptor) {
            this.mCASystemId = aidlCasPluginDescriptor.caSystemId;
            this.mName = aidlCasPluginDescriptor.name;
        }

        PluginDescriptor(android.hardware.cas.V1_0.HidlCasPluginDescriptor hidlCasPluginDescriptor) {
            this.mCASystemId = hidlCasPluginDescriptor.caSystemId;
            this.mName = hidlCasPluginDescriptor.name;
        }

        public int getSystemId() {
            return this.mCASystemId;
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public java.lang.String toString() {
            return "PluginDescriptor {" + this.mCASystemId + ", " + this.mName + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.ArrayList<java.lang.Byte> toByteArray(byte[] bArr, int i, int i2) {
        java.util.ArrayList<java.lang.Byte> arrayList = new java.util.ArrayList<>(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(java.lang.Byte.valueOf(bArr[i + i3]));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.ArrayList<java.lang.Byte> toByteArray(byte[] bArr) {
        if (bArr == null) {
            return new java.util.ArrayList<>();
        }
        return toByteArray(bArr, 0, bArr.length);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] toBytes(java.util.ArrayList<java.lang.Byte> arrayList) {
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        byte[] bArr = new byte[size];
        for (int i = 0; i < size; i++) {
            bArr[i] = arrayList.get(i).byteValue();
        }
        return bArr;
    }

    public final class Session implements java.lang.AutoCloseable {
        boolean mIsClosed = false;
        final byte[] mSessionId;

        Session(byte[] bArr) {
            this.mSessionId = bArr;
        }

        private void validateSessionInternalStates() {
            if (android.media.MediaCas.this.mICas == null && android.media.MediaCas.this.mICasHidl == null) {
                throw new java.lang.IllegalStateException();
            }
            if (this.mIsClosed) {
                android.media.MediaCasStateException.throwExceptionIfNeeded(3);
            }
        }

        public boolean equals(java.lang.Object obj) {
            if (obj instanceof android.media.MediaCas.Session) {
                return java.util.Arrays.equals(this.mSessionId, ((android.media.MediaCas.Session) obj).mSessionId);
            }
            return false;
        }

        public void setPrivateData(byte[] bArr) throws android.media.MediaCasException {
            validateSessionInternalStates();
            try {
                if (android.media.MediaCas.this.mICas != null) {
                    try {
                        android.media.MediaCas.this.mICas.setSessionPrivateData(this.mSessionId, bArr);
                    } catch (android.os.ServiceSpecificException e) {
                        android.media.MediaCasException.throwExceptionIfNeeded(e.errorCode);
                    }
                    return;
                }
                android.media.MediaCasException.throwExceptionIfNeeded(android.media.MediaCas.this.mICasHidl.setSessionPrivateData(android.media.MediaCas.this.toByteArray(this.mSessionId), android.media.MediaCas.this.toByteArray(bArr, 0, bArr.length)));
            } catch (android.os.RemoteException e2) {
                android.media.MediaCas.this.cleanupAndRethrowIllegalState();
            }
        }

        public void processEcm(byte[] bArr, int i, int i2) throws android.media.MediaCasException {
            validateSessionInternalStates();
            try {
                if (android.media.MediaCas.this.mICas != null) {
                    try {
                        android.media.MediaCas.this.mICas.processEcm(this.mSessionId, java.util.Arrays.copyOfRange(bArr, i, i2 + i));
                    } catch (android.os.ServiceSpecificException e) {
                        android.media.MediaCasException.throwExceptionIfNeeded(e.errorCode);
                    }
                    return;
                }
                android.media.MediaCasException.throwExceptionIfNeeded(android.media.MediaCas.this.mICasHidl.processEcm(android.media.MediaCas.this.toByteArray(this.mSessionId), android.media.MediaCas.this.toByteArray(bArr, i, i2)));
            } catch (android.os.RemoteException e2) {
                android.media.MediaCas.this.cleanupAndRethrowIllegalState();
            }
        }

        public void processEcm(byte[] bArr) throws android.media.MediaCasException {
            processEcm(bArr, 0, bArr.length);
        }

        public void sendSessionEvent(int i, int i2, byte[] bArr) throws android.media.MediaCasException {
            validateSessionInternalStates();
            if (android.media.MediaCas.this.mICas != null) {
                if (bArr == null) {
                    try {
                        bArr = new byte[0];
                    } catch (android.os.RemoteException e) {
                        android.media.MediaCas.this.cleanupAndRethrowIllegalState();
                        return;
                    }
                }
                android.media.MediaCas.this.mICas.sendSessionEvent(this.mSessionId, i, i2, bArr);
                return;
            }
            if (android.media.MediaCas.this.mICasHidl11 == null) {
                android.util.Log.d(android.media.MediaCas.TAG, "Send Session Event isn't supported by cas@1.0 interface");
                throw new android.media.MediaCasException.UnsupportedCasException("Send Session Event is not supported");
            }
            try {
                android.media.MediaCasException.throwExceptionIfNeeded(android.media.MediaCas.this.mICasHidl11.sendSessionEvent(android.media.MediaCas.this.toByteArray(this.mSessionId), i, i2, android.media.MediaCas.this.toByteArray(bArr)));
            } catch (android.os.RemoteException e2) {
                android.media.MediaCas.this.cleanupAndRethrowIllegalState();
            }
        }

        public byte[] getSessionId() {
            validateSessionInternalStates();
            return this.mSessionId;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            validateSessionInternalStates();
            try {
                if (android.media.MediaCas.this.mICas != null) {
                    android.media.MediaCas.this.mICas.closeSession(this.mSessionId);
                } else {
                    android.media.MediaCasStateException.throwExceptionIfNeeded(android.media.MediaCas.this.mICasHidl.closeSession(android.media.MediaCas.this.toByteArray(this.mSessionId)));
                }
                this.mIsClosed = true;
                android.media.MediaCas.this.removeSessionFromResourceMap(this);
            } catch (android.os.RemoteException e) {
                android.media.MediaCas.this.cleanupAndRethrowIllegalState();
            }
        }
    }

    android.media.MediaCas.Session createFromSessionId(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        return new android.media.MediaCas.Session(bArr);
    }

    public static boolean isSystemIdSupported(int i) {
        android.hardware.cas.IMediaCasService iMediaCasService = sService.get();
        if (iMediaCasService != null) {
            try {
                return iMediaCasService.isSystemIdSupported(i);
            } catch (android.os.RemoteException e) {
                return false;
            }
        }
        android.hardware.cas.V1_0.IMediaCasService iMediaCasService2 = sServiceHidl.get();
        if (iMediaCasService2 != null) {
            try {
                return iMediaCasService2.isSystemIdSupported(i);
            } catch (android.os.RemoteException e2) {
            }
        }
        return false;
    }

    public static android.media.MediaCas.PluginDescriptor[] enumeratePlugins() {
        android.hardware.cas.IMediaCasService iMediaCasService = sService.get();
        if (iMediaCasService != null) {
            try {
                android.hardware.cas.AidlCasPluginDescriptor[] enumeratePlugins = iMediaCasService.enumeratePlugins();
                if (enumeratePlugins.length == 0) {
                    return null;
                }
                int length = enumeratePlugins.length;
                android.media.MediaCas.PluginDescriptor[] pluginDescriptorArr = new android.media.MediaCas.PluginDescriptor[length];
                for (int i = 0; i < length; i++) {
                    pluginDescriptorArr[i] = new android.media.MediaCas.PluginDescriptor(enumeratePlugins[i]);
                }
                return pluginDescriptorArr;
            } catch (android.os.RemoteException e) {
            }
        }
        android.hardware.cas.V1_0.IMediaCasService iMediaCasService2 = sServiceHidl.get();
        if (iMediaCasService2 != null) {
            try {
                java.util.ArrayList<android.hardware.cas.V1_0.HidlCasPluginDescriptor> enumeratePlugins2 = iMediaCasService2.enumeratePlugins();
                if (enumeratePlugins2.size() == 0) {
                    return null;
                }
                int size = enumeratePlugins2.size();
                android.media.MediaCas.PluginDescriptor[] pluginDescriptorArr2 = new android.media.MediaCas.PluginDescriptor[size];
                for (int i2 = 0; i2 < size; i2++) {
                    pluginDescriptorArr2[i2] = new android.media.MediaCas.PluginDescriptor(enumeratePlugins2.get(i2));
                }
                return pluginDescriptorArr2;
            } catch (android.os.RemoteException e2) {
            }
        }
        return null;
    }

    private void createPlugin(int i) throws android.media.MediaCasException.UnsupportedCasException {
        try {
            try {
                this.mCasSystemId = i;
                this.mUserId = android.os.Process.myUid();
                android.hardware.cas.IMediaCasService service = getService();
                if (service != null) {
                    android.util.Log.d(TAG, "Use CAS AIDL interface to create plugin");
                    this.mICas = service.createPlugin(i, this.mBinder);
                } else {
                    android.hardware.cas.V1_0.IMediaCasService serviceHidl = getServiceHidl();
                    android.hardware.cas.V1_2.IMediaCasService castFrom = android.hardware.cas.V1_2.IMediaCasService.castFrom((android.os.IHwInterface) serviceHidl);
                    if (castFrom == null) {
                        android.hardware.cas.V1_1.IMediaCasService castFrom2 = android.hardware.cas.V1_1.IMediaCasService.castFrom((android.os.IHwInterface) serviceHidl);
                        if (castFrom2 == null) {
                            android.util.Log.d(TAG, "Used cas@1_0 interface to create plugin");
                            this.mICasHidl = serviceHidl.createPlugin(i, this.mBinderHidl);
                        } else {
                            android.util.Log.d(TAG, "Used cas@1.1 interface to create plugin");
                            android.hardware.cas.V1_1.ICas createPluginExt = castFrom2.createPluginExt(i, this.mBinderHidl);
                            this.mICasHidl11 = createPluginExt;
                            this.mICasHidl = createPluginExt;
                        }
                    } else {
                        android.util.Log.d(TAG, "Used cas@1.2 interface to create plugin");
                        android.hardware.cas.V1_2.ICas castFrom3 = android.hardware.cas.V1_2.ICas.castFrom((android.os.IHwInterface) castFrom.createPluginExt(i, this.mBinderHidl));
                        this.mICasHidl12 = castFrom3;
                        this.mICasHidl11 = castFrom3;
                        this.mICasHidl = castFrom3;
                    }
                }
                if (this.mICas == null && this.mICasHidl == null) {
                    throw new android.media.MediaCasException.UnsupportedCasException("Unsupported casSystemId " + i);
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Failed to create plugin: " + e);
                this.mICas = null;
                this.mICasHidl = null;
                if (this.mICas == null && this.mICasHidl == null) {
                    throw new android.media.MediaCasException.UnsupportedCasException("Unsupported casSystemId " + i);
                }
            }
        } catch (java.lang.Throwable th) {
            if (this.mICas != null || this.mICasHidl != null) {
                throw th;
            }
            throw new android.media.MediaCasException.UnsupportedCasException("Unsupported casSystemId " + i);
        }
    }

    private void registerClient(android.content.Context context, java.lang.String str, int i) {
        this.mTunerResourceManager = (android.media.tv.tunerresourcemanager.TunerResourceManager) context.getSystemService(android.content.Context.TV_TUNER_RESOURCE_MGR_SERVICE);
        if (this.mTunerResourceManager != null) {
            int[] iArr = new int[1];
            android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile = new android.media.tv.tunerresourcemanager.ResourceClientProfile();
            resourceClientProfile.tvInputSessionId = str;
            resourceClientProfile.useCase = i;
            this.mTunerResourceManager.registerClientProfile(resourceClientProfile, context.getMainExecutor(), this.mResourceListener, iArr);
            this.mClientId = iArr[0];
        }
    }

    public MediaCas(int i) throws android.media.MediaCasException.UnsupportedCasException {
        createPlugin(i);
    }

    public MediaCas(android.content.Context context, int i, java.lang.String str, int i2) throws android.media.MediaCasException.UnsupportedCasException {
        java.util.Objects.requireNonNull(context, "context must not be null");
        createPlugin(i);
        registerClient(context, str, i2);
    }

    public MediaCas(android.content.Context context, int i, java.lang.String str, int i2, android.os.Handler handler, android.media.MediaCas.EventListener eventListener) throws android.media.MediaCasException.UnsupportedCasException {
        java.util.Objects.requireNonNull(context, "context must not be null");
        setEventListener(eventListener, handler);
        createPlugin(i);
        registerClient(context, str, i2);
    }

    android.os.IHwBinder getBinder() {
        if (this.mICas != null) {
            return null;
        }
        validateInternalStates();
        return this.mICasHidl.asBinder();
    }

    public boolean isAidlHal() {
        return this.mICas != null;
    }

    public interface EventListener {
        void onEvent(android.media.MediaCas mediaCas, int i, int i2, byte[] bArr);

        default void onSessionEvent(android.media.MediaCas mediaCas, android.media.MediaCas.Session session, int i, int i2, byte[] bArr) {
            android.util.Log.d(android.media.MediaCas.TAG, "Received MediaCas Session event");
        }

        default void onPluginStatusUpdate(android.media.MediaCas mediaCas, int i, int i2) {
            android.util.Log.d(android.media.MediaCas.TAG, "Received MediaCas Plugin Status event");
        }

        default void onResourceLost(android.media.MediaCas mediaCas) {
            android.util.Log.d(android.media.MediaCas.TAG, "Received MediaCas Resource Reclaim event");
        }
    }

    public void setEventListener(android.media.MediaCas.EventListener eventListener, android.os.Handler handler) {
        this.mListener = eventListener;
        if (this.mListener == null) {
            this.mEventHandler = null;
            return;
        }
        android.os.Looper looper = handler != null ? handler.getLooper() : null;
        if (looper == null && (looper = android.os.Looper.myLooper()) == null && (looper = android.os.Looper.getMainLooper()) == null) {
            if (this.mHandlerThread == null || !this.mHandlerThread.isAlive()) {
                this.mHandlerThread = new android.os.HandlerThread("MediaCasEventThread", -2);
                this.mHandlerThread.start();
            }
            looper = this.mHandlerThread.getLooper();
        }
        this.mEventHandler = new android.media.MediaCas.EventHandler(looper);
    }

    public void setPrivateData(byte[] bArr) throws android.media.MediaCasException {
        validateInternalStates();
        try {
            if (this.mICas != null) {
                try {
                    this.mICas.setPrivateData(bArr);
                } catch (android.os.ServiceSpecificException e) {
                    android.media.MediaCasException.throwExceptionIfNeeded(e.errorCode);
                }
            } else {
                android.media.MediaCasException.throwExceptionIfNeeded(this.mICasHidl.setPrivateData(toByteArray(bArr, 0, bArr.length)));
            }
        } catch (android.os.RemoteException e2) {
            cleanupAndRethrowIllegalState();
        }
    }

    private class OpenSessionCallback implements android.hardware.cas.V1_0.ICas.openSessionCallback {
        public android.media.MediaCas.Session mSession;
        public int mStatus;

        private OpenSessionCallback() {
        }

        @Override // android.hardware.cas.V1_0.ICas.openSessionCallback
        public void onValues(int i, java.util.ArrayList<java.lang.Byte> arrayList) {
            this.mStatus = i;
            this.mSession = android.media.MediaCas.this.createFromSessionId(android.media.MediaCas.this.toBytes(arrayList));
        }
    }

    private class OpenSession_1_2_Callback implements android.hardware.cas.V1_2.ICas.openSession_1_2Callback {
        public android.media.MediaCas.Session mSession;
        public int mStatus;

        private OpenSession_1_2_Callback() {
        }

        @Override // android.hardware.cas.V1_2.ICas.openSession_1_2Callback
        public void onValues(int i, java.util.ArrayList<java.lang.Byte> arrayList) {
            this.mStatus = i;
            this.mSession = android.media.MediaCas.this.createFromSessionId(android.media.MediaCas.this.toBytes(arrayList));
        }
    }

    private int getSessionResourceHandle() throws android.media.MediaCasException {
        validateInternalStates();
        int[] iArr = {-1};
        if (this.mTunerResourceManager != null) {
            android.media.tv.tunerresourcemanager.CasSessionRequest casSessionRequest = new android.media.tv.tunerresourcemanager.CasSessionRequest();
            casSessionRequest.clientId = this.mClientId;
            casSessionRequest.casSystemId = this.mCasSystemId;
            if (!this.mTunerResourceManager.requestCasSession(casSessionRequest, iArr)) {
                throw new android.media.MediaCasException.InsufficientResourceException("insufficient resource to Open Session");
            }
        }
        return iArr[0];
    }

    private void addSessionToResourceMap(android.media.MediaCas.Session session, int i) {
        if (i != -1) {
            synchronized (this.mSessionMap) {
                this.mSessionMap.put(session, java.lang.Integer.valueOf(i));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeSessionFromResourceMap(android.media.MediaCas.Session session) {
        synchronized (this.mSessionMap) {
            if (this.mSessionMap.get(session) != null) {
                this.mTunerResourceManager.releaseCasSession(this.mSessionMap.get(session).intValue(), this.mClientId);
                this.mSessionMap.remove(session);
            }
        }
    }

    public android.media.MediaCas.Session openSession() throws android.media.MediaCasException {
        int sessionResourceHandle = getSessionResourceHandle();
        try {
            if (this.mICas != null) {
                try {
                    android.media.MediaCas.Session createFromSessionId = createFromSessionId(this.mICas.openSessionDefault());
                    android.util.Log.d(TAG, "Write Stats Log for succeed to Open Session.");
                    com.android.internal.util.FrameworkStatsLog.write(280, this.mUserId, this.mCasSystemId, 1);
                    return createFromSessionId;
                } catch (android.os.ServiceSpecificException e) {
                    android.media.MediaCasException.throwExceptionIfNeeded(e.errorCode);
                }
            } else if (this.mICasHidl != null) {
                android.media.MediaCas.OpenSessionCallback openSessionCallback = new android.media.MediaCas.OpenSessionCallback();
                this.mICasHidl.openSession(openSessionCallback);
                android.media.MediaCasException.throwExceptionIfNeeded(openSessionCallback.mStatus);
                addSessionToResourceMap(openSessionCallback.mSession, sessionResourceHandle);
                android.util.Log.d(TAG, "Write Stats Log for succeed to Open Session.");
                com.android.internal.util.FrameworkStatsLog.write(280, this.mUserId, this.mCasSystemId, 1);
                return openSessionCallback.mSession;
            }
        } catch (android.os.RemoteException e2) {
            cleanupAndRethrowIllegalState();
        }
        android.util.Log.d(TAG, "Write Stats Log for fail to Open Session.");
        com.android.internal.util.FrameworkStatsLog.write(280, this.mUserId, this.mCasSystemId, 2);
        return null;
    }

    public android.media.MediaCas.Session openSession(int i, int i2) throws android.media.MediaCasException {
        int sessionResourceHandle = getSessionResourceHandle();
        if (this.mICas != null) {
            try {
                android.media.MediaCas.Session createFromSessionId = createFromSessionId(this.mICas.openSession(i, i2));
                addSessionToResourceMap(createFromSessionId, sessionResourceHandle);
                android.util.Log.d(TAG, "Write Stats Log for succeed to Open Session.");
                com.android.internal.util.FrameworkStatsLog.write(280, this.mUserId, this.mCasSystemId, 1);
                return createFromSessionId;
            } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                cleanupAndRethrowIllegalState();
            }
        }
        if (this.mICasHidl12 == null) {
            android.util.Log.d(TAG, "Open Session with scrambling mode is only supported by cas@1.2+ interface");
            throw new android.media.MediaCasException.UnsupportedCasException("Open Session with scrambling mode is not supported");
        }
        try {
            android.media.MediaCas.OpenSession_1_2_Callback openSession_1_2_Callback = new android.media.MediaCas.OpenSession_1_2_Callback();
            this.mICasHidl12.openSession_1_2(i, i2, openSession_1_2_Callback);
            android.media.MediaCasException.throwExceptionIfNeeded(openSession_1_2_Callback.mStatus);
            addSessionToResourceMap(openSession_1_2_Callback.mSession, sessionResourceHandle);
            android.util.Log.d(TAG, "Write Stats Log for succeed to Open Session.");
            com.android.internal.util.FrameworkStatsLog.write(280, this.mUserId, this.mCasSystemId, 1);
            return openSession_1_2_Callback.mSession;
        } catch (android.os.RemoteException e2) {
            cleanupAndRethrowIllegalState();
            android.util.Log.d(TAG, "Write Stats Log for fail to Open Session.");
            com.android.internal.util.FrameworkStatsLog.write(280, this.mUserId, this.mCasSystemId, 2);
            return null;
        }
    }

    public void processEmm(byte[] bArr, int i, int i2) throws android.media.MediaCasException {
        validateInternalStates();
        try {
            if (this.mICas != null) {
                try {
                    this.mICas.processEmm(java.util.Arrays.copyOfRange(bArr, i, i2));
                } catch (android.os.ServiceSpecificException e) {
                    android.media.MediaCasException.throwExceptionIfNeeded(e.errorCode);
                }
            } else {
                android.media.MediaCasException.throwExceptionIfNeeded(this.mICasHidl.processEmm(toByteArray(bArr, i, i2)));
            }
        } catch (android.os.RemoteException e2) {
            cleanupAndRethrowIllegalState();
        }
    }

    public void processEmm(byte[] bArr) throws android.media.MediaCasException {
        processEmm(bArr, 0, bArr.length);
    }

    public void sendEvent(int i, int i2, byte[] bArr) throws android.media.MediaCasException {
        validateInternalStates();
        try {
            if (this.mICas != null) {
                if (bArr == null) {
                    try {
                        bArr = new byte[0];
                    } catch (android.os.ServiceSpecificException e) {
                        android.media.MediaCasException.throwExceptionIfNeeded(e.errorCode);
                        return;
                    }
                }
                this.mICas.sendEvent(i, i2, bArr);
                return;
            }
            android.media.MediaCasException.throwExceptionIfNeeded(this.mICasHidl.sendEvent(i, i2, toByteArray(bArr)));
        } catch (android.os.RemoteException e2) {
            cleanupAndRethrowIllegalState();
        }
    }

    public void provision(java.lang.String str) throws android.media.MediaCasException {
        validateInternalStates();
        try {
            if (this.mICas != null) {
                try {
                    this.mICas.provision(str);
                } catch (android.os.ServiceSpecificException e) {
                    android.media.MediaCasException.throwExceptionIfNeeded(e.errorCode);
                }
            } else {
                android.media.MediaCasException.throwExceptionIfNeeded(this.mICasHidl.provision(str));
            }
        } catch (android.os.RemoteException e2) {
            cleanupAndRethrowIllegalState();
        }
    }

    public void refreshEntitlements(int i, byte[] bArr) throws android.media.MediaCasException {
        validateInternalStates();
        try {
            if (this.mICas != null) {
                if (bArr == null) {
                    try {
                        bArr = new byte[0];
                    } catch (android.os.ServiceSpecificException e) {
                        android.media.MediaCasException.throwExceptionIfNeeded(e.errorCode);
                        return;
                    }
                }
                this.mICas.refreshEntitlements(i, bArr);
                return;
            }
            android.media.MediaCasException.throwExceptionIfNeeded(this.mICasHidl.refreshEntitlements(i, toByteArray(bArr)));
        } catch (android.os.RemoteException e2) {
            cleanupAndRethrowIllegalState();
        }
    }

    public void forceResourceLost() {
        if (this.mResourceListener != null) {
            this.mResourceListener.onReclaimResources();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.mICas != null) {
            try {
                this.mICas.release();
            } catch (android.os.RemoteException e) {
            } catch (java.lang.Throwable th) {
                this.mICas = null;
                throw th;
            }
            this.mICas = null;
        } else if (this.mICasHidl != null) {
            try {
                this.mICasHidl.release();
            } catch (android.os.RemoteException e2) {
            } catch (java.lang.Throwable th2) {
                this.mICasHidl12 = null;
                this.mICasHidl11 = null;
                this.mICasHidl = null;
                throw th2;
            }
            this.mICasHidl12 = null;
            this.mICasHidl11 = null;
            this.mICasHidl = null;
        }
        if (this.mTunerResourceManager != null) {
            this.mTunerResourceManager.unregisterClientProfile(this.mClientId);
            this.mTunerResourceManager = null;
        }
        if (this.mHandlerThread != null) {
            this.mHandlerThread.quit();
            this.mHandlerThread = null;
        }
    }

    protected void finalize() {
        close();
    }
}
