package android.media;

/* loaded from: classes2.dex */
public class AudioRecordingMonitorImpl implements android.media.AudioRecordingMonitor {
    private static final int MSG_RECORDING_CONFIG_CHANGE = 1;
    private static final java.lang.String TAG = "android.media.AudioRecordingMonitor";
    private static android.media.IAudioService sService;
    private final android.media.AudioRecordingMonitorClient mClient;
    private volatile android.os.Handler mRecordingCallbackHandler;
    private android.os.HandlerThread mRecordingCallbackHandlerThread;
    private final java.lang.Object mRecordCallbackLock = new java.lang.Object();
    private java.util.LinkedList<android.media.AudioRecordingMonitorImpl.AudioRecordingCallbackInfo> mRecordCallbackList = new java.util.LinkedList<>();
    private final android.media.IRecordingConfigDispatcher mRecordingCallback = new android.media.IRecordingConfigDispatcher.Stub() { // from class: android.media.AudioRecordingMonitorImpl.1
        @Override // android.media.IRecordingConfigDispatcher
        public void dispatchRecordingConfigChange(java.util.List<android.media.AudioRecordingConfiguration> list) {
            android.media.AudioRecordingConfiguration myConfig = android.media.AudioRecordingMonitorImpl.this.getMyConfig(list);
            if (myConfig != null) {
                synchronized (android.media.AudioRecordingMonitorImpl.this.mRecordCallbackLock) {
                    if (android.media.AudioRecordingMonitorImpl.this.mRecordingCallbackHandler != null) {
                        android.media.AudioRecordingMonitorImpl.this.mRecordingCallbackHandler.sendMessage(android.media.AudioRecordingMonitorImpl.this.mRecordingCallbackHandler.obtainMessage(1, myConfig));
                    }
                }
            }
        }
    };

    AudioRecordingMonitorImpl(android.media.AudioRecordingMonitorClient audioRecordingMonitorClient) {
        this.mClient = audioRecordingMonitorClient;
    }

    @Override // android.media.AudioRecordingMonitor
    public void registerAudioRecordingCallback(java.util.concurrent.Executor executor, android.media.AudioManager.AudioRecordingCallback audioRecordingCallback) {
        if (audioRecordingCallback == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioRecordingCallback");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Illegal null Executor");
        }
        synchronized (this.mRecordCallbackLock) {
            java.util.Iterator<android.media.AudioRecordingMonitorImpl.AudioRecordingCallbackInfo> it = this.mRecordCallbackList.iterator();
            while (it.hasNext()) {
                if (it.next().mCb == audioRecordingCallback) {
                    throw new java.lang.IllegalArgumentException("AudioRecordingCallback already registered");
                }
            }
            beginRecordingCallbackHandling();
            this.mRecordCallbackList.add(new android.media.AudioRecordingMonitorImpl.AudioRecordingCallbackInfo(executor, audioRecordingCallback));
        }
    }

    @Override // android.media.AudioRecordingMonitor
    public void unregisterAudioRecordingCallback(android.media.AudioManager.AudioRecordingCallback audioRecordingCallback) {
        if (audioRecordingCallback == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioRecordingCallback argument");
        }
        synchronized (this.mRecordCallbackLock) {
            java.util.Iterator<android.media.AudioRecordingMonitorImpl.AudioRecordingCallbackInfo> it = this.mRecordCallbackList.iterator();
            while (it.hasNext()) {
                android.media.AudioRecordingMonitorImpl.AudioRecordingCallbackInfo next = it.next();
                if (next.mCb == audioRecordingCallback) {
                    this.mRecordCallbackList.remove(next);
                    if (this.mRecordCallbackList.size() == 0) {
                        endRecordingCallbackHandling();
                    }
                }
            }
            throw new java.lang.IllegalArgumentException("AudioRecordingCallback was not registered");
        }
    }

    @Override // android.media.AudioRecordingMonitor
    public android.media.AudioRecordingConfiguration getActiveRecordingConfiguration() {
        try {
            return getMyConfig(getService().getActiveRecordingConfigurations());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class AudioRecordingCallbackInfo {
        final android.media.AudioManager.AudioRecordingCallback mCb;
        final java.util.concurrent.Executor mExecutor;

        AudioRecordingCallbackInfo(java.util.concurrent.Executor executor, android.media.AudioManager.AudioRecordingCallback audioRecordingCallback) {
            this.mExecutor = executor;
            this.mCb = audioRecordingCallback;
        }
    }

    private void beginRecordingCallbackHandling() {
        if (this.mRecordingCallbackHandlerThread == null) {
            this.mRecordingCallbackHandlerThread = new android.os.HandlerThread("android.media.AudioRecordingMonitor.RecordingCallback");
            this.mRecordingCallbackHandlerThread.start();
            android.os.Looper looper = this.mRecordingCallbackHandlerThread.getLooper();
            if (looper != null) {
                this.mRecordingCallbackHandler = new android.media.AudioRecordingMonitorImpl.AnonymousClass2(looper);
                try {
                    getService().registerRecordingCallback(this.mRecordingCallback);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    /* renamed from: android.media.AudioRecordingMonitorImpl$2, reason: invalid class name */
    class AnonymousClass2 extends android.os.Handler {
        AnonymousClass2(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    if (message.obj == null) {
                        return;
                    }
                    final java.util.ArrayList arrayList = new java.util.ArrayList();
                    arrayList.add((android.media.AudioRecordingConfiguration) message.obj);
                    synchronized (android.media.AudioRecordingMonitorImpl.this.mRecordCallbackLock) {
                        if (android.media.AudioRecordingMonitorImpl.this.mRecordCallbackList.size() == 0) {
                            return;
                        }
                        java.util.LinkedList linkedList = new java.util.LinkedList(android.media.AudioRecordingMonitorImpl.this.mRecordCallbackList);
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            java.util.Iterator it = linkedList.iterator();
                            while (it.hasNext()) {
                                final android.media.AudioRecordingMonitorImpl.AudioRecordingCallbackInfo audioRecordingCallbackInfo = (android.media.AudioRecordingMonitorImpl.AudioRecordingCallbackInfo) it.next();
                                audioRecordingCallbackInfo.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.AudioRecordingMonitorImpl$2$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        android.media.AudioRecordingMonitorImpl.AudioRecordingCallbackInfo.this.mCb.onRecordingConfigChanged(arrayList);
                                    }
                                });
                            }
                            return;
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                default:
                    android.util.Log.e(android.media.AudioRecordingMonitorImpl.TAG, "Unknown event " + message.what);
                    return;
            }
        }
    }

    private void endRecordingCallbackHandling() {
        if (this.mRecordingCallbackHandlerThread != null) {
            try {
                getService().unregisterRecordingCallback(this.mRecordingCallback);
                this.mRecordingCallbackHandlerThread.quit();
                this.mRecordingCallbackHandlerThread = null;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    android.media.AudioRecordingConfiguration getMyConfig(java.util.List<android.media.AudioRecordingConfiguration> list) {
        int portId = this.mClient.getPortId();
        for (android.media.AudioRecordingConfiguration audioRecordingConfiguration : list) {
            if (audioRecordingConfiguration.getClientPortId() == portId) {
                return audioRecordingConfiguration;
            }
        }
        return null;
    }

    private static android.media.IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        sService = android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio"));
        return sService;
    }
}
