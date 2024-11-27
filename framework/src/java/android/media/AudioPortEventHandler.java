package android.media;

/* loaded from: classes2.dex */
class AudioPortEventHandler {
    private static final int AUDIOPORT_EVENT_NEW_LISTENER = 4;
    private static final int AUDIOPORT_EVENT_PATCH_LIST_UPDATED = 2;
    private static final int AUDIOPORT_EVENT_PORT_LIST_UPDATED = 1;
    private static final int AUDIOPORT_EVENT_SERVICE_DIED = 3;
    private static final long RESCHEDULE_MESSAGE_DELAY_MS = 100;
    private static final java.lang.String TAG = "AudioPortEventHandler";
    private android.os.Handler mHandler;
    private android.os.HandlerThread mHandlerThread;
    private long mJniCallback;
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.ArrayList<android.media.AudioManager.OnAudioPortUpdateListener> mListeners = new java.util.ArrayList<>();

    private native void native_finalize();

    private native void native_setup(java.lang.Object obj);

    AudioPortEventHandler() {
    }

    void init() {
        synchronized (this.mLock) {
            if (this.mHandler != null) {
                return;
            }
            this.mHandlerThread = new android.os.HandlerThread(TAG);
            this.mHandlerThread.start();
            if (this.mHandlerThread.getLooper() != null) {
                this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper()) { // from class: android.media.AudioPortEventHandler.1
                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    /* JADX WARN: Removed duplicated region for block: B:39:0x00ba A[LOOP:1: B:37:0x00b4->B:39:0x00ba, LOOP_END] */
                    @Override // android.os.Handler
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public void handleMessage(android.os.Message message) {
                        java.util.ArrayList arrayList;
                        synchronized (android.media.AudioPortEventHandler.this.mLock) {
                            if (message.what == 4) {
                                arrayList = new java.util.ArrayList();
                                if (android.media.AudioPortEventHandler.this.mListeners.contains(message.obj)) {
                                    arrayList.add((android.media.AudioManager.OnAudioPortUpdateListener) message.obj);
                                }
                            } else {
                                arrayList = (java.util.ArrayList) android.media.AudioPortEventHandler.this.mListeners.clone();
                            }
                        }
                        if (message.what == 1 || message.what == 2 || message.what == 3) {
                            android.media.AudioManager.resetAudioPortGeneration();
                        }
                        if (arrayList.isEmpty()) {
                            return;
                        }
                        java.util.ArrayList arrayList2 = new java.util.ArrayList();
                        java.util.ArrayList arrayList3 = new java.util.ArrayList();
                        if (message.what != 3 && android.media.AudioManager.updateAudioPortCache(arrayList2, arrayList3, null) != 0) {
                            sendMessageDelayed(obtainMessage(message.what, message.obj), android.media.AudioPortEventHandler.RESCHEDULE_MESSAGE_DELAY_MS);
                            return;
                        }
                        int i = 0;
                        switch (message.what) {
                            case 1:
                            case 4:
                                android.media.AudioPort[] audioPortArr = (android.media.AudioPort[]) arrayList2.toArray(new android.media.AudioPort[0]);
                                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                    ((android.media.AudioManager.OnAudioPortUpdateListener) arrayList.get(i2)).onAudioPortListUpdate(audioPortArr);
                                }
                                if (message.what == 1) {
                                    return;
                                }
                                android.media.AudioPatch[] audioPatchArr = (android.media.AudioPatch[]) arrayList3.toArray(new android.media.AudioPatch[0]);
                                while (i < arrayList.size()) {
                                    ((android.media.AudioManager.OnAudioPortUpdateListener) arrayList.get(i)).onAudioPatchListUpdate(audioPatchArr);
                                    i++;
                                }
                                return;
                            case 2:
                                android.media.AudioPatch[] audioPatchArr2 = (android.media.AudioPatch[]) arrayList3.toArray(new android.media.AudioPatch[0]);
                                while (i < arrayList.size()) {
                                }
                                return;
                            case 3:
                                while (i < arrayList.size()) {
                                    ((android.media.AudioManager.OnAudioPortUpdateListener) arrayList.get(i)).onServiceDied();
                                    i++;
                                }
                                return;
                            default:
                                return;
                        }
                    }
                };
                native_setup(new java.lang.ref.WeakReference(this));
            } else {
                this.mHandler = null;
            }
        }
    }

    protected void finalize() {
        native_finalize();
        if (this.mHandlerThread.isAlive()) {
            this.mHandlerThread.quit();
        }
    }

    void registerListener(android.media.AudioManager.OnAudioPortUpdateListener onAudioPortUpdateListener) {
        synchronized (this.mLock) {
            this.mListeners.add(onAudioPortUpdateListener);
        }
        if (this.mHandler != null) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(4, 0, 0, onAudioPortUpdateListener));
        }
    }

    void unregisterListener(android.media.AudioManager.OnAudioPortUpdateListener onAudioPortUpdateListener) {
        synchronized (this.mLock) {
            this.mListeners.remove(onAudioPortUpdateListener);
        }
    }

    android.os.Handler handler() {
        return this.mHandler;
    }

    private static void postEventFromNative(java.lang.Object obj, int i, int i2, int i3, java.lang.Object obj2) {
        android.os.Handler handler;
        android.media.AudioPortEventHandler audioPortEventHandler = (android.media.AudioPortEventHandler) ((java.lang.ref.WeakReference) obj).get();
        if (audioPortEventHandler != null && audioPortEventHandler != null && (handler = audioPortEventHandler.handler()) != null) {
            android.os.Message obtainMessage = handler.obtainMessage(i, i2, i3, obj2);
            if (i != 4) {
                handler.removeMessages(i);
            }
            handler.sendMessage(obtainMessage);
        }
    }
}
