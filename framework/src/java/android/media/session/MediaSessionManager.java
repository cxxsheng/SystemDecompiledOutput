package android.media.session;

/* loaded from: classes2.dex */
public final class MediaSessionManager {

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int RESULT_MEDIA_KEY_HANDLED = 1;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int RESULT_MEDIA_KEY_NOT_HANDLED = 0;
    private static final java.lang.String TAG = "SessionManager";
    private final android.media.MediaCommunicationManager mCommunicationManager;
    private android.content.Context mContext;
    private android.media.session.MediaSession.Token mCurMediaKeyEventSession;
    private android.media.session.MediaSessionManager.OnMediaKeyListenerImpl mOnMediaKeyListener;
    private android.media.session.MediaSessionManager.OnVolumeKeyLongPressListenerImpl mOnVolumeKeyLongPressListener;
    private final android.media.session.MediaSessionManager.OnMediaKeyEventDispatchedListenerStub mOnMediaKeyEventDispatchedListenerStub = new android.media.session.MediaSessionManager.OnMediaKeyEventDispatchedListenerStub();
    private final android.media.session.MediaSessionManager.OnMediaKeyEventSessionChangedListenerStub mOnMediaKeyEventSessionChangedListenerStub = new android.media.session.MediaSessionManager.OnMediaKeyEventSessionChangedListenerStub();
    private final android.media.session.MediaSessionManager.RemoteSessionCallbackStub mRemoteSessionCallbackStub = new android.media.session.MediaSessionManager.RemoteSessionCallbackStub();
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.ArrayMap<android.media.session.MediaSessionManager.OnActiveSessionsChangedListener, android.media.session.MediaSessionManager.SessionsChangedWrapper> mListeners = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.media.session.MediaSessionManager.OnSession2TokensChangedListener, android.media.session.MediaSessionManager.Session2TokensChangedWrapper> mSession2TokensListeners = new android.util.ArrayMap<>();
    private final java.util.Map<android.media.session.MediaSessionManager.OnMediaKeyEventDispatchedListener, java.util.concurrent.Executor> mOnMediaKeyEventDispatchedListeners = new java.util.HashMap();
    private final java.util.Map<android.media.session.MediaSessionManager.OnMediaKeyEventSessionChangedListener, java.util.concurrent.Executor> mMediaKeyEventSessionChangedCallbacks = new java.util.HashMap();
    private java.lang.String mCurMediaKeyEventSessionPackage = "";
    private final java.util.Map<android.media.session.MediaSessionManager.RemoteSessionCallback, java.util.concurrent.Executor> mRemoteSessionCallbacks = new android.util.ArrayMap();
    private final android.media.session.ISessionManager mService = android.media.session.ISessionManager.Stub.asInterface(android.media.MediaFrameworkPlatformInitializer.getMediaServiceManager().getMediaSessionServiceRegisterer().get());

    public interface OnActiveSessionsChangedListener {
        void onActiveSessionsChanged(java.util.List<android.media.session.MediaController> list);
    }

    @android.annotation.SystemApi
    public interface OnMediaKeyEventDispatchedListener {
        void onMediaKeyEventDispatched(android.view.KeyEvent keyEvent, java.lang.String str, android.media.session.MediaSession.Token token);
    }

    public interface OnMediaKeyEventSessionChangedListener {
        void onMediaKeyEventSessionChanged(java.lang.String str, android.media.session.MediaSession.Token token);
    }

    @android.annotation.SystemApi
    public interface OnMediaKeyListener {
        boolean onMediaKey(android.view.KeyEvent keyEvent);
    }

    public interface OnSession2TokensChangedListener {
        void onSession2TokensChanged(java.util.List<android.media.Session2Token> list);
    }

    @android.annotation.SystemApi
    public interface OnVolumeKeyLongPressListener {
        void onVolumeKeyLongPress(android.view.KeyEvent keyEvent);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public interface RemoteSessionCallback {
        void onDefaultRemoteSessionChanged(android.media.session.MediaSession.Token token);

        void onVolumeChanged(android.media.session.MediaSession.Token token, int i);
    }

    public MediaSessionManager(android.content.Context context) {
        this.mContext = context;
        this.mCommunicationManager = (android.media.MediaCommunicationManager) context.getSystemService(android.content.Context.MEDIA_COMMUNICATION_SERVICE);
    }

    public android.media.session.ISession createSession(android.media.session.MediaSession.CallbackStub callbackStub, java.lang.String str, android.os.Bundle bundle) {
        java.util.Objects.requireNonNull(callbackStub, "cbStub shouldn't be null");
        java.util.Objects.requireNonNull(str, "tag shouldn't be null");
        try {
            return this.mService.createSession(this.mContext.getPackageName(), callbackStub, str, bundle, android.os.UserHandle.myUserId());
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @java.lang.Deprecated
    public void notifySession2Created(android.media.Session2Token session2Token) {
    }

    public java.util.List<android.media.session.MediaController> getActiveSessions(android.content.ComponentName componentName) {
        return getActiveSessionsForUser(componentName, android.os.UserHandle.myUserId());
    }

    public android.media.session.MediaSession.Token getMediaKeyEventSession() {
        try {
            return this.mService.getMediaKeyEventSession(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to get media key event session", e);
            return null;
        }
    }

    public java.lang.String getMediaKeyEventSessionPackageName() {
        try {
            java.lang.String mediaKeyEventSessionPackageName = this.mService.getMediaKeyEventSessionPackageName(this.mContext.getPackageName());
            return mediaKeyEventSessionPackageName != null ? mediaKeyEventSessionPackageName : "";
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to get media key event session package name", e);
            return "";
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public java.util.List<android.media.session.MediaController> getActiveSessionsForUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(userHandle, "userHandle shouldn't be null");
        return getActiveSessionsForUser(componentName, userHandle.getIdentifier());
    }

    private java.util.List<android.media.session.MediaController> getActiveSessionsForUser(android.content.ComponentName componentName, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            java.util.List<android.media.session.MediaSession.Token> sessions = this.mService.getSessions(componentName, i);
            int size = sessions.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(new android.media.session.MediaController(this.mContext, sessions.get(i2)));
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to get active sessions: ", e);
        }
        return arrayList;
    }

    public java.util.List<android.media.Session2Token> getSession2Tokens() {
        return this.mCommunicationManager.getSession2Tokens();
    }

    public void addOnActiveSessionsChangedListener(android.media.session.MediaSessionManager.OnActiveSessionsChangedListener onActiveSessionsChangedListener, android.content.ComponentName componentName) {
        addOnActiveSessionsChangedListener(onActiveSessionsChangedListener, componentName, null);
    }

    public void addOnActiveSessionsChangedListener(android.media.session.MediaSessionManager.OnActiveSessionsChangedListener onActiveSessionsChangedListener, android.content.ComponentName componentName, android.os.Handler handler) {
        addOnActiveSessionsChangedListener(onActiveSessionsChangedListener, componentName, android.os.UserHandle.myUserId(), handler == null ? null : new android.os.HandlerExecutor(handler));
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void addOnActiveSessionsChangedListener(android.content.ComponentName componentName, android.os.UserHandle userHandle, java.util.concurrent.Executor executor, android.media.session.MediaSessionManager.OnActiveSessionsChangedListener onActiveSessionsChangedListener) {
        java.util.Objects.requireNonNull(userHandle, "userHandle shouldn't be null");
        java.util.Objects.requireNonNull(executor, "executor shouldn't be null");
        addOnActiveSessionsChangedListener(onActiveSessionsChangedListener, componentName, userHandle.getIdentifier(), executor);
    }

    private void addOnActiveSessionsChangedListener(android.media.session.MediaSessionManager.OnActiveSessionsChangedListener onActiveSessionsChangedListener, android.content.ComponentName componentName, int i, java.util.concurrent.Executor executor) {
        java.util.Objects.requireNonNull(onActiveSessionsChangedListener, "sessionListener shouldn't be null");
        if (executor == null) {
            executor = new android.os.HandlerExecutor(new android.os.Handler());
        }
        synchronized (this.mLock) {
            if (this.mListeners.get(onActiveSessionsChangedListener) != null) {
                android.util.Log.w(TAG, "Attempted to add session listener twice, ignoring.");
                return;
            }
            android.media.session.MediaSessionManager.SessionsChangedWrapper sessionsChangedWrapper = new android.media.session.MediaSessionManager.SessionsChangedWrapper(this.mContext, onActiveSessionsChangedListener, executor);
            try {
                this.mService.addSessionsListener(sessionsChangedWrapper.mStub, componentName, i);
                this.mListeners.put(onActiveSessionsChangedListener, sessionsChangedWrapper);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error in addOnActiveSessionsChangedListener.", e);
            }
        }
    }

    public void removeOnActiveSessionsChangedListener(android.media.session.MediaSessionManager.OnActiveSessionsChangedListener onActiveSessionsChangedListener) {
        java.util.Objects.requireNonNull(onActiveSessionsChangedListener, "sessionListener shouldn't be null");
        synchronized (this.mLock) {
            android.media.session.MediaSessionManager.SessionsChangedWrapper remove = this.mListeners.remove(onActiveSessionsChangedListener);
            if (remove != null) {
                try {
                    try {
                        this.mService.removeSessionsListener(remove.mStub);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(TAG, "Error in removeOnActiveSessionsChangedListener.", e);
                    }
                } finally {
                    remove.release();
                }
            }
        }
    }

    public void addOnSession2TokensChangedListener(android.media.session.MediaSessionManager.OnSession2TokensChangedListener onSession2TokensChangedListener) {
        addOnSession2TokensChangedListener(android.os.UserHandle.myUserId(), onSession2TokensChangedListener, new android.os.HandlerExecutor(new android.os.Handler()));
    }

    public void addOnSession2TokensChangedListener(android.media.session.MediaSessionManager.OnSession2TokensChangedListener onSession2TokensChangedListener, android.os.Handler handler) {
        java.util.Objects.requireNonNull(handler, "handler shouldn't be null");
        addOnSession2TokensChangedListener(android.os.UserHandle.myUserId(), onSession2TokensChangedListener, new android.os.HandlerExecutor(handler));
    }

    public void addOnSession2TokensChangedListener(android.os.UserHandle userHandle, android.media.session.MediaSessionManager.OnSession2TokensChangedListener onSession2TokensChangedListener, java.util.concurrent.Executor executor) {
        java.util.Objects.requireNonNull(userHandle, "userHandle shouldn't be null");
        java.util.Objects.requireNonNull(executor, "executor shouldn't be null");
        addOnSession2TokensChangedListener(userHandle.getIdentifier(), onSession2TokensChangedListener, executor);
    }

    private void addOnSession2TokensChangedListener(int i, android.media.session.MediaSessionManager.OnSession2TokensChangedListener onSession2TokensChangedListener, java.util.concurrent.Executor executor) {
        java.util.Objects.requireNonNull(onSession2TokensChangedListener, "listener shouldn't be null");
        synchronized (this.mLock) {
            if (this.mSession2TokensListeners.get(onSession2TokensChangedListener) != null) {
                android.util.Log.w(TAG, "Attempted to add session listener twice, ignoring.");
                return;
            }
            android.media.session.MediaSessionManager.Session2TokensChangedWrapper session2TokensChangedWrapper = new android.media.session.MediaSessionManager.Session2TokensChangedWrapper(onSession2TokensChangedListener, executor);
            try {
                this.mService.addSession2TokensListener(session2TokensChangedWrapper.getStub(), i);
                this.mSession2TokensListeners.put(onSession2TokensChangedListener, session2TokensChangedWrapper);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error in addSessionTokensListener.", e);
                e.rethrowFromSystemServer();
            }
        }
    }

    public void removeOnSession2TokensChangedListener(android.media.session.MediaSessionManager.OnSession2TokensChangedListener onSession2TokensChangedListener) {
        android.media.session.MediaSessionManager.Session2TokensChangedWrapper remove;
        java.util.Objects.requireNonNull(onSession2TokensChangedListener, "listener shouldn't be null");
        synchronized (this.mLock) {
            remove = this.mSession2TokensListeners.remove(onSession2TokensChangedListener);
        }
        if (remove != null) {
            try {
                this.mService.removeSession2TokensListener(remove.getStub());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error in removeSessionTokensListener.", e);
                e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void registerRemoteSessionCallback(java.util.concurrent.Executor executor, android.media.session.MediaSessionManager.RemoteSessionCallback remoteSessionCallback) {
        boolean z;
        java.util.Objects.requireNonNull(executor, "executor shouldn't be null");
        java.util.Objects.requireNonNull(remoteSessionCallback, "callback shouldn't be null");
        synchronized (this.mLock) {
            int size = this.mRemoteSessionCallbacks.size();
            this.mRemoteSessionCallbacks.put(remoteSessionCallback, executor);
            if (size == 0) {
                z = true;
                if (this.mRemoteSessionCallbacks.size() == 1) {
                }
            }
            z = false;
        }
        if (z) {
            try {
                this.mService.registerRemoteSessionCallback(this.mRemoteSessionCallbackStub);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to register remote volume controller callback", e);
            }
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void unregisterRemoteSessionCallback(android.media.session.MediaSessionManager.RemoteSessionCallback remoteSessionCallback) {
        boolean z;
        java.util.Objects.requireNonNull(remoteSessionCallback, "callback shouldn't be null");
        synchronized (this.mLock) {
            if (this.mRemoteSessionCallbacks.remove(remoteSessionCallback) != null && this.mRemoteSessionCallbacks.size() == 0) {
                z = true;
            } else {
                z = false;
            }
        }
        if (z) {
            try {
                this.mService.unregisterRemoteSessionCallback(this.mRemoteSessionCallbackStub);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to unregister remote volume controller callback", e);
            }
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void dispatchMediaKeyEvent(android.view.KeyEvent keyEvent, boolean z) {
        dispatchMediaKeyEventInternal(keyEvent, false, z);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void dispatchMediaKeyEventAsSystemService(android.view.KeyEvent keyEvent) {
        dispatchMediaKeyEventInternal(keyEvent, true, true);
    }

    private void dispatchMediaKeyEventInternal(android.view.KeyEvent keyEvent, boolean z, boolean z2) {
        java.util.Objects.requireNonNull(keyEvent, "keyEvent shouldn't be null");
        try {
            this.mService.dispatchMediaKeyEvent(this.mContext.getPackageName(), z, keyEvent, z2);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public boolean dispatchMediaKeyEventToSessionAsSystemService(android.view.KeyEvent keyEvent, android.media.session.MediaSession.Token token) {
        java.util.Objects.requireNonNull(token, "sessionToken shouldn't be null");
        java.util.Objects.requireNonNull(keyEvent, "keyEvent shouldn't be null");
        if (!android.view.KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
            return false;
        }
        try {
            return this.mService.dispatchMediaKeyEventToSessionAsSystemService(this.mContext.getPackageName(), keyEvent, token);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to send key event.", e);
            return false;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void dispatchVolumeKeyEvent(android.view.KeyEvent keyEvent, int i, boolean z) {
        dispatchVolumeKeyEventInternal(keyEvent, i, z, false);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void dispatchVolumeKeyEventAsSystemService(android.view.KeyEvent keyEvent, int i) {
        dispatchVolumeKeyEventInternal(keyEvent, i, false, true);
    }

    private void dispatchVolumeKeyEventInternal(android.view.KeyEvent keyEvent, int i, boolean z, boolean z2) {
        java.util.Objects.requireNonNull(keyEvent, "keyEvent shouldn't be null");
        try {
            this.mService.dispatchVolumeKeyEvent(this.mContext.getPackageName(), this.mContext.getOpPackageName(), z2, keyEvent, i, z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to send volume key event.", e);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void dispatchVolumeKeyEventToSessionAsSystemService(android.view.KeyEvent keyEvent, android.media.session.MediaSession.Token token) {
        java.util.Objects.requireNonNull(token, "sessionToken shouldn't be null");
        java.util.Objects.requireNonNull(keyEvent, "keyEvent shouldn't be null");
        try {
            this.mService.dispatchVolumeKeyEventToSessionAsSystemService(this.mContext.getPackageName(), this.mContext.getOpPackageName(), keyEvent, token);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error calling dispatchVolumeKeyEventAsSystemService", e);
        }
    }

    public void dispatchAdjustVolume(int i, int i2, int i3) {
        try {
            this.mService.dispatchAdjustVolume(this.mContext.getPackageName(), this.mContext.getOpPackageName(), i, i2, i3);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to send adjust volume.", e);
        }
    }

    public boolean isTrustedForMediaControl(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        java.util.Objects.requireNonNull(remoteUserInfo, "userInfo shouldn't be null");
        if (remoteUserInfo.getPackageName() == null) {
            return false;
        }
        try {
            return this.mService.isTrusted(remoteUserInfo.getPackageName(), remoteUserInfo.getPid(), remoteUserInfo.getUid());
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Cannot communicate with the service.", e);
            return false;
        }
    }

    public boolean isGlobalPriorityActive() {
        try {
            return this.mService.isGlobalPriorityActive();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to check if the global priority is active.", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public void setOnVolumeKeyLongPressListener(android.media.session.MediaSessionManager.OnVolumeKeyLongPressListener onVolumeKeyLongPressListener, android.os.Handler handler) {
        synchronized (this.mLock) {
            try {
                try {
                    if (onVolumeKeyLongPressListener == null) {
                        this.mOnVolumeKeyLongPressListener = null;
                        this.mService.setOnVolumeKeyLongPressListener(null);
                    } else {
                        if (handler == null) {
                            handler = new android.os.Handler();
                        }
                        this.mOnVolumeKeyLongPressListener = new android.media.session.MediaSessionManager.OnVolumeKeyLongPressListenerImpl(onVolumeKeyLongPressListener, handler);
                        this.mService.setOnVolumeKeyLongPressListener(this.mOnVolumeKeyLongPressListener);
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "Failed to set volume key long press listener", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.SystemApi
    public void setOnMediaKeyListener(android.media.session.MediaSessionManager.OnMediaKeyListener onMediaKeyListener, android.os.Handler handler) {
        synchronized (this.mLock) {
            try {
                try {
                    if (onMediaKeyListener == null) {
                        this.mOnMediaKeyListener = null;
                        this.mService.setOnMediaKeyListener(null);
                    } else {
                        if (handler == null) {
                            handler = new android.os.Handler();
                        }
                        this.mOnMediaKeyListener = new android.media.session.MediaSessionManager.OnMediaKeyListenerImpl(onMediaKeyListener, handler);
                        this.mService.setOnMediaKeyListener(this.mOnMediaKeyListener);
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "Failed to set media key listener", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.SystemApi
    public void addOnMediaKeyEventDispatchedListener(java.util.concurrent.Executor executor, android.media.session.MediaSessionManager.OnMediaKeyEventDispatchedListener onMediaKeyEventDispatchedListener) {
        java.util.Objects.requireNonNull(executor, "executor shouldn't be null");
        java.util.Objects.requireNonNull(onMediaKeyEventDispatchedListener, "listener shouldn't be null");
        synchronized (this.mLock) {
            try {
                this.mOnMediaKeyEventDispatchedListeners.put(onMediaKeyEventDispatchedListener, executor);
                if (this.mOnMediaKeyEventDispatchedListeners.size() == 1) {
                    this.mService.addOnMediaKeyEventDispatchedListener(this.mOnMediaKeyEventDispatchedListenerStub);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to set media key listener", e);
            }
        }
    }

    @android.annotation.SystemApi
    public void removeOnMediaKeyEventDispatchedListener(android.media.session.MediaSessionManager.OnMediaKeyEventDispatchedListener onMediaKeyEventDispatchedListener) {
        java.util.Objects.requireNonNull(onMediaKeyEventDispatchedListener, "listener shouldn't be null");
        synchronized (this.mLock) {
            try {
                this.mOnMediaKeyEventDispatchedListeners.remove(onMediaKeyEventDispatchedListener);
                if (this.mOnMediaKeyEventDispatchedListeners.size() == 0) {
                    this.mService.removeOnMediaKeyEventDispatchedListener(this.mOnMediaKeyEventDispatchedListenerStub);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to set media key event dispatched listener", e);
            }
        }
    }

    public void addOnMediaKeyEventSessionChangedListener(java.util.concurrent.Executor executor, final android.media.session.MediaSessionManager.OnMediaKeyEventSessionChangedListener onMediaKeyEventSessionChangedListener) {
        java.util.Objects.requireNonNull(executor, "executor shouldn't be null");
        java.util.Objects.requireNonNull(onMediaKeyEventSessionChangedListener, "listener shouldn't be null");
        synchronized (this.mLock) {
            try {
                if (this.mMediaKeyEventSessionChangedCallbacks.isEmpty()) {
                    this.mService.addOnMediaKeyEventSessionChangedListener(this.mOnMediaKeyEventSessionChangedListenerStub, this.mContext.getPackageName());
                }
                this.mMediaKeyEventSessionChangedCallbacks.put(onMediaKeyEventSessionChangedListener, executor);
                executor.execute(new java.lang.Runnable() { // from class: android.media.session.MediaSessionManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.session.MediaSessionManager.this.lambda$addOnMediaKeyEventSessionChangedListener$0(onMediaKeyEventSessionChangedListener);
                    }
                });
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to add MediaKeyEventSessionChangedListener", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOnMediaKeyEventSessionChangedListener$0(android.media.session.MediaSessionManager.OnMediaKeyEventSessionChangedListener onMediaKeyEventSessionChangedListener) {
        onMediaKeyEventSessionChangedListener.onMediaKeyEventSessionChanged(this.mCurMediaKeyEventSessionPackage, this.mCurMediaKeyEventSession);
    }

    public void removeOnMediaKeyEventSessionChangedListener(android.media.session.MediaSessionManager.OnMediaKeyEventSessionChangedListener onMediaKeyEventSessionChangedListener) {
        java.util.Objects.requireNonNull(onMediaKeyEventSessionChangedListener, "listener shouldn't be null");
        synchronized (this.mLock) {
            try {
                if (this.mMediaKeyEventSessionChangedCallbacks.remove(onMediaKeyEventSessionChangedListener) != null && this.mMediaKeyEventSessionChangedCallbacks.isEmpty()) {
                    this.mService.removeOnMediaKeyEventSessionChangedListener(this.mOnMediaKeyEventSessionChangedListenerStub);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to remove MediaKeyEventSessionChangedListener", e);
            }
        }
    }

    public void setCustomMediaKeyDispatcher(java.lang.String str) {
        try {
            this.mService.setCustomMediaKeyDispatcher(str);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to set custom media key dispatcher name", e);
        }
    }

    public void setCustomMediaSessionPolicyProvider(java.lang.String str) {
        try {
            this.mService.setCustomMediaSessionPolicyProvider(str);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to set custom session policy provider name", e);
        }
    }

    public boolean hasCustomMediaKeyDispatcher(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "componentName shouldn't be null");
        try {
            return this.mService.hasCustomMediaKeyDispatcher(str);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to check if custom media key dispatcher with given component name exists", e);
            return false;
        }
    }

    public boolean hasCustomMediaSessionPolicyProvider(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "componentName shouldn't be null");
        try {
            return this.mService.hasCustomMediaSessionPolicyProvider(str);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to check if custom media session policy provider with given component name exists", e);
            return false;
        }
    }

    public int getSessionPolicies(android.media.session.MediaSession.Token token) {
        try {
            return this.mService.getSessionPolicies(token);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to get session policies", e);
            return 0;
        }
    }

    public void setSessionPolicies(android.media.session.MediaSession.Token token, int i) {
        try {
            this.mService.setSessionPolicies(token, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to set session policies", e);
        }
    }

    public static final class RemoteUserInfo {
        private final java.lang.String mPackageName;
        private final int mPid;
        private final int mUid;

        public RemoteUserInfo(java.lang.String str, int i, int i2) {
            this.mPackageName = str;
            this.mPid = i;
            this.mUid = i2;
        }

        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        public int getPid() {
            return this.mPid;
        }

        public int getUid() {
            return this.mUid;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.media.session.MediaSessionManager.RemoteUserInfo)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo = (android.media.session.MediaSessionManager.RemoteUserInfo) obj;
            return android.text.TextUtils.equals(this.mPackageName, remoteUserInfo.mPackageName) && this.mPid == remoteUserInfo.mPid && this.mUid == remoteUserInfo.mUid;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mPackageName, java.lang.Integer.valueOf(this.mPid), java.lang.Integer.valueOf(this.mUid));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SessionsChangedWrapper {
        private android.content.Context mContext;
        private java.util.concurrent.Executor mExecutor;
        private android.media.session.MediaSessionManager.OnActiveSessionsChangedListener mListener;
        private final android.media.session.IActiveSessionsListener.Stub mStub = new android.media.session.MediaSessionManager.SessionsChangedWrapper.AnonymousClass1();

        public SessionsChangedWrapper(android.content.Context context, android.media.session.MediaSessionManager.OnActiveSessionsChangedListener onActiveSessionsChangedListener, java.util.concurrent.Executor executor) {
            this.mContext = context;
            this.mListener = onActiveSessionsChangedListener;
            this.mExecutor = executor;
        }

        /* renamed from: android.media.session.MediaSessionManager$SessionsChangedWrapper$1, reason: invalid class name */
        class AnonymousClass1 extends android.media.session.IActiveSessionsListener.Stub {
            AnonymousClass1() {
            }

            @Override // android.media.session.IActiveSessionsListener
            public void onActiveSessionsChanged(final java.util.List<android.media.session.MediaSession.Token> list) {
                if (android.media.session.MediaSessionManager.SessionsChangedWrapper.this.mExecutor != null) {
                    android.media.session.MediaSessionManager.SessionsChangedWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.session.MediaSessionManager$SessionsChangedWrapper$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.session.MediaSessionManager.SessionsChangedWrapper.AnonymousClass1.this.lambda$onActiveSessionsChanged$0(list);
                        }
                    });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onActiveSessionsChanged$0(java.util.List list) {
                android.media.session.MediaSessionManager.SessionsChangedWrapper.this.callOnActiveSessionsChangedListener(list);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void callOnActiveSessionsChangedListener(java.util.List<android.media.session.MediaSession.Token> list) {
            android.content.Context context = this.mContext;
            if (context != null) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    arrayList.add(new android.media.session.MediaController(context, list.get(i)));
                }
                android.media.session.MediaSessionManager.OnActiveSessionsChangedListener onActiveSessionsChangedListener = this.mListener;
                if (onActiveSessionsChangedListener != null) {
                    onActiveSessionsChangedListener.onActiveSessionsChanged(arrayList);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void release() {
            this.mListener = null;
            this.mContext = null;
            this.mExecutor = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Session2TokensChangedWrapper {
        private final java.util.concurrent.Executor mExecutor;
        private final android.media.session.MediaSessionManager.OnSession2TokensChangedListener mListener;
        private final android.media.session.ISession2TokensListener.Stub mStub = new android.media.session.MediaSessionManager.Session2TokensChangedWrapper.AnonymousClass1();

        /* renamed from: android.media.session.MediaSessionManager$Session2TokensChangedWrapper$1, reason: invalid class name */
        class AnonymousClass1 extends android.media.session.ISession2TokensListener.Stub {
            AnonymousClass1() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onSession2TokensChanged$0(java.util.List list) {
                android.media.session.MediaSessionManager.Session2TokensChangedWrapper.this.mListener.onSession2TokensChanged(list);
            }

            @Override // android.media.session.ISession2TokensListener
            public void onSession2TokensChanged(final java.util.List<android.media.Session2Token> list) {
                android.media.session.MediaSessionManager.Session2TokensChangedWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.session.MediaSessionManager$Session2TokensChangedWrapper$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.session.MediaSessionManager.Session2TokensChangedWrapper.AnonymousClass1.this.lambda$onSession2TokensChanged$0(list);
                    }
                });
            }
        }

        Session2TokensChangedWrapper(android.media.session.MediaSessionManager.OnSession2TokensChangedListener onSession2TokensChangedListener, java.util.concurrent.Executor executor) {
            this.mListener = onSession2TokensChangedListener;
            this.mExecutor = executor;
        }

        public android.media.session.ISession2TokensListener.Stub getStub() {
            return this.mStub;
        }
    }

    private static final class OnVolumeKeyLongPressListenerImpl extends android.media.session.IOnVolumeKeyLongPressListener.Stub {
        private android.os.Handler mHandler;
        private android.media.session.MediaSessionManager.OnVolumeKeyLongPressListener mListener;

        public OnVolumeKeyLongPressListenerImpl(android.media.session.MediaSessionManager.OnVolumeKeyLongPressListener onVolumeKeyLongPressListener, android.os.Handler handler) {
            this.mListener = onVolumeKeyLongPressListener;
            this.mHandler = handler;
        }

        @Override // android.media.session.IOnVolumeKeyLongPressListener
        public void onVolumeKeyLongPress(final android.view.KeyEvent keyEvent) {
            if (this.mListener == null || this.mHandler == null) {
                android.util.Log.w(android.media.session.MediaSessionManager.TAG, "Failed to call volume key long-press listener. Either mListener or mHandler is null");
            } else {
                this.mHandler.post(new java.lang.Runnable() { // from class: android.media.session.MediaSessionManager.OnVolumeKeyLongPressListenerImpl.1
                    @Override // java.lang.Runnable
                    public void run() {
                        android.media.session.MediaSessionManager.OnVolumeKeyLongPressListenerImpl.this.mListener.onVolumeKeyLongPress(keyEvent);
                    }
                });
            }
        }
    }

    private static final class OnMediaKeyListenerImpl extends android.media.session.IOnMediaKeyListener.Stub {
        private android.os.Handler mHandler;
        private android.media.session.MediaSessionManager.OnMediaKeyListener mListener;

        public OnMediaKeyListenerImpl(android.media.session.MediaSessionManager.OnMediaKeyListener onMediaKeyListener, android.os.Handler handler) {
            this.mListener = onMediaKeyListener;
            this.mHandler = handler;
        }

        @Override // android.media.session.IOnMediaKeyListener
        public void onMediaKey(final android.view.KeyEvent keyEvent, final android.os.ResultReceiver resultReceiver) {
            if (this.mListener == null || this.mHandler == null) {
                android.util.Log.w(android.media.session.MediaSessionManager.TAG, "Failed to call media key listener. Either mListener or mHandler is null");
            } else {
                this.mHandler.post(new java.lang.Runnable() { // from class: android.media.session.MediaSessionManager.OnMediaKeyListenerImpl.1
                    @Override // java.lang.Runnable
                    public void run() {
                        boolean onMediaKey = android.media.session.MediaSessionManager.OnMediaKeyListenerImpl.this.mListener.onMediaKey(keyEvent);
                        android.util.Log.d(android.media.session.MediaSessionManager.TAG, "The media key listener is returned " + onMediaKey);
                        if (resultReceiver != null) {
                            resultReceiver.send(onMediaKey ? 1 : 0, null);
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class OnMediaKeyEventDispatchedListenerStub extends android.media.session.IOnMediaKeyEventDispatchedListener.Stub {
        private OnMediaKeyEventDispatchedListenerStub() {
        }

        @Override // android.media.session.IOnMediaKeyEventDispatchedListener
        public void onMediaKeyEventDispatched(final android.view.KeyEvent keyEvent, final java.lang.String str, final android.media.session.MediaSession.Token token) {
            synchronized (android.media.session.MediaSessionManager.this.mLock) {
                for (final java.util.Map.Entry entry : android.media.session.MediaSessionManager.this.mOnMediaKeyEventDispatchedListeners.entrySet()) {
                    ((java.util.concurrent.Executor) entry.getValue()).execute(new java.lang.Runnable() { // from class: android.media.session.MediaSessionManager$OnMediaKeyEventDispatchedListenerStub$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ((android.media.session.MediaSessionManager.OnMediaKeyEventDispatchedListener) entry.getKey()).onMediaKeyEventDispatched(keyEvent, str, token);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class OnMediaKeyEventSessionChangedListenerStub extends android.media.session.IOnMediaKeyEventSessionChangedListener.Stub {
        private OnMediaKeyEventSessionChangedListenerStub() {
        }

        @Override // android.media.session.IOnMediaKeyEventSessionChangedListener
        public void onMediaKeyEventSessionChanged(final java.lang.String str, final android.media.session.MediaSession.Token token) {
            synchronized (android.media.session.MediaSessionManager.this.mLock) {
                android.media.session.MediaSessionManager.this.mCurMediaKeyEventSessionPackage = str;
                android.media.session.MediaSessionManager.this.mCurMediaKeyEventSession = token;
                for (final java.util.Map.Entry entry : android.media.session.MediaSessionManager.this.mMediaKeyEventSessionChangedCallbacks.entrySet()) {
                    ((java.util.concurrent.Executor) entry.getValue()).execute(new java.lang.Runnable() { // from class: android.media.session.MediaSessionManager$OnMediaKeyEventSessionChangedListenerStub$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ((android.media.session.MediaSessionManager.OnMediaKeyEventSessionChangedListener) entry.getKey()).onMediaKeyEventSessionChanged(str, token);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class RemoteSessionCallbackStub extends android.media.IRemoteSessionCallback.Stub {
        private RemoteSessionCallbackStub() {
        }

        @Override // android.media.IRemoteSessionCallback
        public void onVolumeChanged(final android.media.session.MediaSession.Token token, final int i) {
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            synchronized (android.media.session.MediaSessionManager.this.mLock) {
                arrayMap.putAll(android.media.session.MediaSessionManager.this.mRemoteSessionCallbacks);
            }
            java.util.Iterator it = arrayMap.entrySet().iterator();
            while (it.hasNext()) {
                final java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                ((java.util.concurrent.Executor) entry.getValue()).execute(new java.lang.Runnable() { // from class: android.media.session.MediaSessionManager$RemoteSessionCallbackStub$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((android.media.session.MediaSessionManager.RemoteSessionCallback) entry.getKey()).onVolumeChanged(token, i);
                    }
                });
            }
        }

        @Override // android.media.IRemoteSessionCallback
        public void onSessionChanged(final android.media.session.MediaSession.Token token) {
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            synchronized (android.media.session.MediaSessionManager.this.mLock) {
                arrayMap.putAll(android.media.session.MediaSessionManager.this.mRemoteSessionCallbacks);
            }
            java.util.Iterator it = arrayMap.entrySet().iterator();
            while (it.hasNext()) {
                final java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                ((java.util.concurrent.Executor) entry.getValue()).execute(new java.lang.Runnable() { // from class: android.media.session.MediaSessionManager$RemoteSessionCallbackStub$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((android.media.session.MediaSessionManager.RemoteSessionCallback) entry.getKey()).onDefaultRemoteSessionChanged(token);
                    }
                });
            }
        }
    }
}
