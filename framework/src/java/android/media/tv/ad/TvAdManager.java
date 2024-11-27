package android.media.tv.ad;

/* loaded from: classes2.dex */
public final class TvAdManager {
    public static final java.lang.String ACTION_APP_LINK_COMMAND = "android.media.tv.ad.action.APP_LINK_COMMAND";
    public static final java.lang.String APP_LINK_KEY_BACK_URI = "back_uri";
    public static final java.lang.String APP_LINK_KEY_CLASS_NAME = "class_name";
    public static final java.lang.String APP_LINK_KEY_COMMAND_TYPE = "command_type";
    public static final java.lang.String APP_LINK_KEY_PACKAGE_NAME = "package_name";
    public static final java.lang.String APP_LINK_KEY_SERVICE_ID = "service_id";
    public static final int ERROR_BLOCKED = 5;
    public static final int ERROR_ENCRYPTED = 6;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_SUPPORTED = 2;
    public static final int ERROR_RESOURCE_UNAVAILABLE = 4;
    public static final int ERROR_UNKNOWN = 1;
    public static final int ERROR_UNKNOWN_CHANNEL = 7;
    public static final int ERROR_WEAK_SIGNAL = 3;
    public static final java.lang.String INTENT_KEY_AD_SERVICE_ID = "ad_service_id";
    public static final java.lang.String INTENT_KEY_CHANNEL_URI = "channel_uri";
    public static final java.lang.String INTENT_KEY_COMMAND_TYPE = "command_type";
    public static final java.lang.String INTENT_KEY_TV_INPUT_ID = "tv_input_id";
    public static final java.lang.String SESSION_DATA_KEY_AD_BUFFER = "ad_buffer";
    public static final java.lang.String SESSION_DATA_KEY_AD_REQUEST = "ad_request";
    public static final java.lang.String SESSION_DATA_KEY_BROADCAST_INFO_REQUEST = "broadcast_info_request";
    public static final java.lang.String SESSION_DATA_KEY_REQUEST_ID = "request_id";
    public static final java.lang.String SESSION_DATA_TYPE_AD_BUFFER_READY = "ad_buffer_ready";
    public static final java.lang.String SESSION_DATA_TYPE_AD_REQUEST = "ad_request";
    public static final java.lang.String SESSION_DATA_TYPE_BROADCAST_INFO_REQUEST = "broadcast_info_request";
    public static final java.lang.String SESSION_DATA_TYPE_REMOVE_BROADCAST_INFO_REQUEST = "remove_broadcast_info_request";
    public static final int SESSION_STATE_ERROR = 3;
    public static final int SESSION_STATE_RUNNING = 2;
    public static final int SESSION_STATE_STOPPED = 1;
    private static final java.lang.String TAG = "TvAdManager";
    private int mNextSeq;
    private final android.media.tv.ad.ITvAdManager mService;
    private final int mUserId;
    private final android.util.SparseArray<android.media.tv.ad.TvAdManager.SessionCallbackRecord> mSessionCallbackRecordMap = new android.util.SparseArray<>();
    private final java.util.List<android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord> mCallbackRecords = new java.util.ArrayList();
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.media.tv.ad.ITvAdClient mClient = new android.media.tv.ad.ITvAdClient.Stub() { // from class: android.media.tv.ad.TvAdManager.1
        @Override // android.media.tv.ad.ITvAdClient
        public void onSessionCreated(java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) {
            android.media.tv.ad.TvAdManager.Session session;
            synchronized (android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap) {
                android.media.tv.ad.TvAdManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.ad.TvAdManager.SessionCallbackRecord) android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.ad.TvAdManager.TAG, "Callback not found for " + iBinder);
                    return;
                }
                if (iBinder != null) {
                    session = new android.media.tv.ad.TvAdManager.Session(iBinder, inputChannel, android.media.tv.ad.TvAdManager.this.mService, android.media.tv.ad.TvAdManager.this.mUserId, i, android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap);
                } else {
                    android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap.delete(i);
                    session = null;
                }
                sessionCallbackRecord.postSessionCreated(session);
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onSessionReleased(int i) {
            synchronized (android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap) {
                android.media.tv.ad.TvAdManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.ad.TvAdManager.SessionCallbackRecord) android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap.get(i);
                android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap.delete(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.ad.TvAdManager.TAG, "Callback not found for seq:" + i);
                } else {
                    sessionCallbackRecord.mSession.releaseInternal();
                    sessionCallbackRecord.postSessionReleased();
                }
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) {
            synchronized (android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap) {
                android.media.tv.ad.TvAdManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.ad.TvAdManager.SessionCallbackRecord) android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap.get(i5);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.ad.TvAdManager.TAG, "Callback not found for seq " + i5);
                } else {
                    sessionCallbackRecord.postLayoutSurface(i, i2, i3, i4);
                }
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentVideoBounds(int i) {
            synchronized (android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap) {
                android.media.tv.ad.TvAdManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.ad.TvAdManager.SessionCallbackRecord) android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.ad.TvAdManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestCurrentVideoBounds();
                }
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentChannelUri(int i) {
            synchronized (android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap) {
                android.media.tv.ad.TvAdManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.ad.TvAdManager.SessionCallbackRecord) android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.ad.TvAdManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestCurrentChannelUri();
                }
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestTrackInfoList(int i) {
            synchronized (android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap) {
                android.media.tv.ad.TvAdManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.ad.TvAdManager.SessionCallbackRecord) android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.ad.TvAdManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestTrackInfoList();
                }
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentTvInputId(int i) {
            synchronized (android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap) {
                android.media.tv.ad.TvAdManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.ad.TvAdManager.SessionCallbackRecord) android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.ad.TvAdManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestCurrentTvInputId();
                }
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr, int i) {
            synchronized (android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap) {
                android.media.tv.ad.TvAdManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.ad.TvAdManager.SessionCallbackRecord) android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.ad.TvAdManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestSigning(str, str2, str3, bArr);
                }
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onTvAdSessionData(java.lang.String str, android.os.Bundle bundle, int i) {
            synchronized (android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap) {
                android.media.tv.ad.TvAdManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.ad.TvAdManager.SessionCallbackRecord) android.media.tv.ad.TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.ad.TvAdManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postTvAdSessionData(str, bundle);
                }
            }
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SessionDataKey {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SessionDataType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SessionState {
    }

    public TvAdManager(android.media.tv.ad.ITvAdManager iTvAdManager, int i) {
        this.mService = iTvAdManager;
        this.mUserId = i;
        android.media.tv.ad.ITvAdManagerCallback.Stub stub = new android.media.tv.ad.ITvAdManagerCallback.Stub() { // from class: android.media.tv.ad.TvAdManager.2
            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceAdded(java.lang.String str) {
                synchronized (android.media.tv.ad.TvAdManager.this.mLock) {
                    java.util.Iterator it = android.media.tv.ad.TvAdManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord) it.next()).postAdServiceAdded(str);
                    }
                }
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceRemoved(java.lang.String str) {
                synchronized (android.media.tv.ad.TvAdManager.this.mLock) {
                    java.util.Iterator it = android.media.tv.ad.TvAdManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord) it.next()).postAdServiceRemoved(str);
                    }
                }
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceUpdated(java.lang.String str) {
                synchronized (android.media.tv.ad.TvAdManager.this.mLock) {
                    java.util.Iterator it = android.media.tv.ad.TvAdManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord) it.next()).postAdServiceUpdated(str);
                    }
                }
            }
        };
        try {
            if (this.mService != null) {
                this.mService.registerCallback(stub, this.mUserId);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.media.tv.ad.TvAdServiceInfo> getTvAdServiceList() {
        try {
            return this.mService.getTvAdServiceList(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createSession(java.lang.String str, java.lang.String str2, android.media.tv.ad.TvAdManager.SessionCallback sessionCallback, android.os.Handler handler) {
        createSessionInternal(str, str2, sessionCallback, handler);
    }

    private void createSessionInternal(java.lang.String str, java.lang.String str2, android.media.tv.ad.TvAdManager.SessionCallback sessionCallback, android.os.Handler handler) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkNotNull(str2);
        com.android.internal.util.Preconditions.checkNotNull(sessionCallback);
        com.android.internal.util.Preconditions.checkNotNull(handler);
        android.media.tv.ad.TvAdManager.SessionCallbackRecord sessionCallbackRecord = new android.media.tv.ad.TvAdManager.SessionCallbackRecord(sessionCallback, handler);
        synchronized (this.mSessionCallbackRecordMap) {
            int i = this.mNextSeq;
            this.mNextSeq = i + 1;
            this.mSessionCallbackRecordMap.put(i, sessionCallbackRecord);
            try {
                this.mService.createSession(this.mClient, str, str2, i, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void sendAppLinkCommand(java.lang.String str, android.os.Bundle bundle) {
        try {
            this.mService.sendAppLinkCommand(str, bundle, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(java.util.concurrent.Executor executor, android.media.tv.ad.TvAdManager.TvAdServiceCallback tvAdServiceCallback) {
        com.android.internal.util.Preconditions.checkNotNull(tvAdServiceCallback);
        com.android.internal.util.Preconditions.checkNotNull(executor);
        synchronized (this.mLock) {
            this.mCallbackRecords.add(new android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord(tvAdServiceCallback, executor));
        }
    }

    public void unregisterCallback(android.media.tv.ad.TvAdManager.TvAdServiceCallback tvAdServiceCallback) {
        com.android.internal.util.Preconditions.checkNotNull(tvAdServiceCallback);
        synchronized (this.mLock) {
            java.util.Iterator<android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord> it = this.mCallbackRecords.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getCallback() == tvAdServiceCallback) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public static final class Session {
        static final int DISPATCH_HANDLED = 1;
        static final int DISPATCH_IN_PROGRESS = -1;
        static final int DISPATCH_NOT_HANDLED = 0;
        private static final long INPUT_SESSION_NOT_RESPONDING_TIMEOUT = 2500;
        private final android.media.tv.ad.TvAdManager.Session.InputEventHandler mHandler;
        private android.view.InputChannel mInputChannel;
        private android.media.tv.TvInputManager.Session mInputSession;
        private final android.util.Pools.Pool<android.media.tv.ad.TvAdManager.Session.PendingEvent> mPendingEventPool;
        private final android.util.SparseArray<android.media.tv.ad.TvAdManager.Session.PendingEvent> mPendingEvents;
        private android.media.tv.ad.TvAdManager.Session.TvInputEventSender mSender;
        private final int mSeq;
        private final android.media.tv.ad.ITvAdManager mService;
        private final android.util.SparseArray<android.media.tv.ad.TvAdManager.SessionCallbackRecord> mSessionCallbackRecordMap;
        private android.os.IBinder mToken;
        private final int mUserId;

        public interface FinishedInputEventCallback {
            void onFinishedInputEvent(java.lang.Object obj, boolean z);
        }

        private Session(android.os.IBinder iBinder, android.view.InputChannel inputChannel, android.media.tv.ad.ITvAdManager iTvAdManager, int i, int i2, android.util.SparseArray<android.media.tv.ad.TvAdManager.SessionCallbackRecord> sparseArray) {
            this.mHandler = new android.media.tv.ad.TvAdManager.Session.InputEventHandler(android.os.Looper.getMainLooper());
            this.mPendingEventPool = new android.util.Pools.SimplePool(20);
            this.mPendingEvents = new android.util.SparseArray<>(20);
            this.mToken = iBinder;
            this.mInputChannel = inputChannel;
            this.mService = iTvAdManager;
            this.mUserId = i;
            this.mSeq = i2;
            this.mSessionCallbackRecordMap = sparseArray;
        }

        public android.media.tv.TvInputManager.Session getInputSession() {
            return this.mInputSession;
        }

        public void setInputSession(android.media.tv.TvInputManager.Session session) {
            this.mInputSession = session;
        }

        public void release() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.releaseSession(this.mToken, this.mUserId);
                releaseInternal();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setSurface(android.view.Surface surface) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setSurface(this.mToken, surface, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void createMediaView(android.view.View view, android.graphics.Rect rect) {
            com.android.internal.util.Preconditions.checkNotNull(view);
            com.android.internal.util.Preconditions.checkNotNull(rect);
            if (view.getWindowToken() == null) {
                throw new java.lang.IllegalStateException("view must be attached to a window");
            }
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.createMediaView(this.mToken, view.getWindowToken(), rect, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void relayoutMediaView(android.graphics.Rect rect) {
            com.android.internal.util.Preconditions.checkNotNull(rect);
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.relayoutMediaView(this.mToken, rect, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void removeMediaView() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.removeMediaView(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void dispatchSurfaceChanged(int i, int i2, int i3) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.dispatchSurfaceChanged(this.mToken, i, i2, i3, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private void flushPendingEventsLocked() {
            this.mHandler.removeMessages(3);
            int size = this.mPendingEvents.size();
            for (int i = 0; i < size; i++) {
                android.os.Message obtainMessage = this.mHandler.obtainMessage(3, this.mPendingEvents.keyAt(i), 0);
                obtainMessage.setAsynchronous(true);
                obtainMessage.sendToTarget();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void releaseInternal() {
            this.mToken = null;
            synchronized (this.mHandler) {
                if (this.mInputChannel != null) {
                    if (this.mSender != null) {
                        flushPendingEventsLocked();
                        this.mSender.dispose();
                        this.mSender = null;
                    }
                    this.mInputChannel.dispose();
                    this.mInputChannel = null;
                }
            }
            synchronized (this.mSessionCallbackRecordMap) {
                this.mSessionCallbackRecordMap.delete(this.mSeq);
            }
        }

        void startAdService() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.startAdService(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void stopAdService() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.stopAdService(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void resetAdService() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.resetAdService(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentVideoBounds(android.graphics.Rect rect) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentVideoBounds(this.mToken, rect, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentChannelUri(android.net.Uri uri) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentChannelUri(this.mToken, uri, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTrackInfoList(this.mToken, list, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentTvInputId(java.lang.String str) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentTvInputId(this.mToken, str, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendSigningResult(java.lang.String str, byte[] bArr) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendSigningResult(this.mToken, str, bArr, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyError(java.lang.String str, android.os.Bundle bundle) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyError(this.mToken, str, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTvMessage(int i, android.os.Bundle bundle) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTvMessage(this.mToken, i, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTvInputSessionData(java.lang.String str, android.os.Bundle bundle) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTvInputSessionData(this.mToken, str, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int dispatchInputEvent(android.view.InputEvent inputEvent, java.lang.Object obj, android.media.tv.ad.TvAdManager.Session.FinishedInputEventCallback finishedInputEventCallback, android.os.Handler handler) {
            com.android.internal.util.Preconditions.checkNotNull(inputEvent);
            com.android.internal.util.Preconditions.checkNotNull(finishedInputEventCallback);
            com.android.internal.util.Preconditions.checkNotNull(handler);
            synchronized (this.mHandler) {
                if (this.mInputChannel == null) {
                    return 0;
                }
                android.media.tv.ad.TvAdManager.Session.PendingEvent obtainPendingEventLocked = obtainPendingEventLocked(inputEvent, obj, finishedInputEventCallback, handler);
                if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) {
                    return sendInputEventOnMainLooperLocked(obtainPendingEventLocked);
                }
                android.os.Message obtainMessage = this.mHandler.obtainMessage(1, obtainPendingEventLocked);
                obtainMessage.setAsynchronous(true);
                this.mHandler.sendMessage(obtainMessage);
                return -1;
            }
        }

        private android.media.tv.ad.TvAdManager.Session.PendingEvent obtainPendingEventLocked(android.view.InputEvent inputEvent, java.lang.Object obj, android.media.tv.ad.TvAdManager.Session.FinishedInputEventCallback finishedInputEventCallback, android.os.Handler handler) {
            android.media.tv.ad.TvAdManager.Session.PendingEvent acquire = this.mPendingEventPool.acquire();
            if (acquire == null) {
                acquire = new android.media.tv.ad.TvAdManager.Session.PendingEvent();
            }
            acquire.mEvent = inputEvent;
            acquire.mEventToken = obj;
            acquire.mCallback = finishedInputEventCallback;
            acquire.mEventHandler = handler;
            return acquire;
        }

        private final class InputEventHandler extends android.os.Handler {
            public static final int MSG_FLUSH_INPUT_EVENT = 3;
            public static final int MSG_SEND_INPUT_EVENT = 1;
            public static final int MSG_TIMEOUT_INPUT_EVENT = 2;

            InputEventHandler(android.os.Looper looper) {
                super(looper, null, true);
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        android.media.tv.ad.TvAdManager.Session.this.sendInputEventAndReportResultOnMainLooper((android.media.tv.ad.TvAdManager.Session.PendingEvent) message.obj);
                        break;
                    case 2:
                        android.media.tv.ad.TvAdManager.Session.this.finishedInputEvent(message.arg1, false, true);
                        break;
                    case 3:
                        android.media.tv.ad.TvAdManager.Session.this.finishedInputEvent(message.arg1, false, false);
                        break;
                }
            }
        }

        void invokeFinishedInputEventCallback(android.media.tv.ad.TvAdManager.Session.PendingEvent pendingEvent, boolean z) {
            pendingEvent.mHandled = z;
            if (pendingEvent.mEventHandler.getLooper().isCurrentThread()) {
                pendingEvent.run();
                return;
            }
            android.os.Message obtain = android.os.Message.obtain(pendingEvent.mEventHandler, pendingEvent);
            obtain.setAsynchronous(true);
            obtain.sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void sendInputEventAndReportResultOnMainLooper(android.media.tv.ad.TvAdManager.Session.PendingEvent pendingEvent) {
            synchronized (this.mHandler) {
                if (sendInputEventOnMainLooperLocked(pendingEvent) == -1) {
                    return;
                }
                invokeFinishedInputEventCallback(pendingEvent, false);
            }
        }

        private int sendInputEventOnMainLooperLocked(android.media.tv.ad.TvAdManager.Session.PendingEvent pendingEvent) {
            if (this.mInputChannel != null) {
                if (this.mSender == null) {
                    this.mSender = new android.media.tv.ad.TvAdManager.Session.TvInputEventSender(this.mInputChannel, this.mHandler.getLooper());
                }
                android.view.InputEvent inputEvent = pendingEvent.mEvent;
                int sequenceNumber = inputEvent.getSequenceNumber();
                if (this.mSender.sendInputEvent(sequenceNumber, inputEvent)) {
                    this.mPendingEvents.put(sequenceNumber, pendingEvent);
                    android.os.Message obtainMessage = this.mHandler.obtainMessage(2, pendingEvent);
                    obtainMessage.setAsynchronous(true);
                    this.mHandler.sendMessageDelayed(obtainMessage, INPUT_SESSION_NOT_RESPONDING_TIMEOUT);
                    return -1;
                }
                android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "Unable to send input event to session: " + this.mToken + " dropping:" + inputEvent);
                return 0;
            }
            return 0;
        }

        void finishedInputEvent(int i, boolean z, boolean z2) {
            synchronized (this.mHandler) {
                int indexOfKey = this.mPendingEvents.indexOfKey(i);
                if (indexOfKey < 0) {
                    return;
                }
                android.media.tv.ad.TvAdManager.Session.PendingEvent valueAt = this.mPendingEvents.valueAt(indexOfKey);
                this.mPendingEvents.removeAt(indexOfKey);
                if (z2) {
                    android.util.Log.w(android.media.tv.ad.TvAdManager.TAG, "Timeout waiting for session to handle input event after 2500 ms: " + this.mToken);
                } else {
                    this.mHandler.removeMessages(2, valueAt);
                }
                invokeFinishedInputEventCallback(valueAt, z);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void recyclePendingEventLocked(android.media.tv.ad.TvAdManager.Session.PendingEvent pendingEvent) {
            pendingEvent.recycle();
            this.mPendingEventPool.release(pendingEvent);
        }

        private final class TvInputEventSender extends android.view.InputEventSender {
            TvInputEventSender(android.view.InputChannel inputChannel, android.os.Looper looper) {
                super(inputChannel, looper);
            }

            @Override // android.view.InputEventSender
            public void onInputEventFinished(int i, boolean z) {
                android.media.tv.ad.TvAdManager.Session.this.finishedInputEvent(i, z, false);
            }
        }

        private final class PendingEvent implements java.lang.Runnable {
            public android.media.tv.ad.TvAdManager.Session.FinishedInputEventCallback mCallback;
            public android.view.InputEvent mEvent;
            public android.os.Handler mEventHandler;
            public java.lang.Object mEventToken;
            public boolean mHandled;

            private PendingEvent() {
            }

            public void recycle() {
                this.mEvent = null;
                this.mEventToken = null;
                this.mCallback = null;
                this.mEventHandler = null;
                this.mHandled = false;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.mCallback.onFinishedInputEvent(this.mEventToken, this.mHandled);
                synchronized (this.mEventHandler) {
                    android.media.tv.ad.TvAdManager.Session.this.recyclePendingEventLocked(this);
                }
            }
        }
    }

    public static abstract class SessionCallback {
        public void onSessionCreated(android.media.tv.ad.TvAdManager.Session session) {
        }

        public void onSessionReleased(android.media.tv.ad.TvAdManager.Session session) {
        }

        public void onLayoutSurface(android.media.tv.ad.TvAdManager.Session session, int i, int i2, int i3, int i4) {
        }

        public void onRequestCurrentVideoBounds(android.media.tv.ad.TvAdManager.Session session) {
        }

        public void onRequestCurrentChannelUri(android.media.tv.ad.TvAdManager.Session session) {
        }

        public void onRequestTrackInfoList(android.media.tv.ad.TvAdManager.Session session) {
        }

        public void onRequestCurrentTvInputId(android.media.tv.ad.TvAdManager.Session session) {
        }

        public void onRequestSigning(android.media.tv.ad.TvAdManager.Session session, java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) {
        }
    }

    public static abstract class TvAdServiceCallback {
        public void onAdServiceAdded(java.lang.String str) {
        }

        public void onAdServiceRemoved(java.lang.String str) {
        }

        public void onAdServiceUpdated(java.lang.String str) {
        }
    }

    private static final class SessionCallbackRecord {
        private final android.os.Handler mHandler;
        private android.media.tv.ad.TvAdManager.Session mSession;
        private final android.media.tv.ad.TvAdManager.SessionCallback mSessionCallback;

        SessionCallbackRecord(android.media.tv.ad.TvAdManager.SessionCallback sessionCallback, android.os.Handler handler) {
            this.mSessionCallback = sessionCallback;
            this.mHandler = handler;
        }

        void postSessionCreated(final android.media.tv.ad.TvAdManager.Session session) {
            this.mSession = session;
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSessionCallback.onSessionCreated(session);
                }
            });
        }

        void postSessionReleased() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSessionCallback.onSessionReleased(android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postLayoutSurface(final int i, final int i2, final int i3, final int i4) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSessionCallback.onLayoutSurface(android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSession, i, i2, i3, i4);
                }
            });
        }

        void postRequestCurrentVideoBounds() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSessionCallback.onRequestCurrentVideoBounds(android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentChannelUri() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSessionCallback.onRequestCurrentChannelUri(android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestTrackInfoList() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.6
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSessionCallback.onRequestTrackInfoList(android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentTvInputId() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.7
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSessionCallback.onRequestCurrentTvInputId(android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestSigning(final java.lang.String str, final java.lang.String str2, final java.lang.String str3, final byte[] bArr) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.8
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSessionCallback.onRequestSigning(android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSession, str, str2, str3, bArr);
                }
            });
        }

        void postTvAdSessionData(final java.lang.String str, final android.os.Bundle bundle) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.9
                @Override // java.lang.Runnable
                public void run() {
                    if (android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        android.media.tv.ad.TvAdManager.SessionCallbackRecord.this.mSession.getInputSession().notifyTvAdSessionData(str, bundle);
                    }
                }
            });
        }
    }

    private static final class TvAdServiceCallbackRecord {
        private final android.media.tv.ad.TvAdManager.TvAdServiceCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        TvAdServiceCallbackRecord(android.media.tv.ad.TvAdManager.TvAdServiceCallback tvAdServiceCallback, java.util.concurrent.Executor executor) {
            this.mCallback = tvAdServiceCallback;
            this.mExecutor = executor;
        }

        public android.media.tv.ad.TvAdManager.TvAdServiceCallback getCallback() {
            return this.mCallback;
        }

        public void postAdServiceAdded(final java.lang.String str) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord.this.mCallback.onAdServiceAdded(str);
                }
            });
        }

        public void postAdServiceRemoved(final java.lang.String str) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord.this.mCallback.onAdServiceRemoved(str);
                }
            });
        }

        public void postAdServiceUpdated(final java.lang.String str) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord.this.mCallback.onAdServiceUpdated(str);
                }
            });
        }
    }
}
