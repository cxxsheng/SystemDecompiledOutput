package android.media.audiopolicy;

/* loaded from: classes2.dex */
public class AudioVolumeGroupChangeHandler {
    private static final int AUDIOVOLUMEGROUP_EVENT_NEW_LISTENER = 4;
    private static final int AUDIOVOLUMEGROUP_EVENT_VOLUME_CHANGED = 1000;
    private static final java.lang.String TAG = "AudioVolumeGroupChangeHandler";
    private android.os.Handler mHandler;
    private android.os.HandlerThread mHandlerThread;
    private long mJniCallback;
    private final java.util.ArrayList<android.media.AudioManager.VolumeGroupCallback> mListeners = new java.util.ArrayList<>();

    private native void native_finalize();

    private native void native_setup(java.lang.Object obj);

    public void init() {
        synchronized (this) {
            if (this.mHandler != null) {
                return;
            }
            this.mHandlerThread = new android.os.HandlerThread(TAG);
            this.mHandlerThread.start();
            if (this.mHandlerThread.getLooper() == null) {
                this.mHandler = null;
            } else {
                this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper()) { // from class: android.media.audiopolicy.AudioVolumeGroupChangeHandler.1
                    @Override // android.os.Handler
                    public void handleMessage(android.os.Message message) {
                        java.util.ArrayList arrayList;
                        synchronized (this) {
                            if (message.what == 4) {
                                arrayList = new java.util.ArrayList();
                                if (android.media.audiopolicy.AudioVolumeGroupChangeHandler.this.mListeners.contains(message.obj)) {
                                    arrayList.add((android.media.AudioManager.VolumeGroupCallback) message.obj);
                                }
                            } else {
                                arrayList = (java.util.ArrayList) android.media.audiopolicy.AudioVolumeGroupChangeHandler.this.mListeners.clone();
                            }
                        }
                        if (arrayList.isEmpty()) {
                            return;
                        }
                        switch (message.what) {
                            case 1000:
                                for (int i = 0; i < arrayList.size(); i++) {
                                    ((android.media.AudioManager.VolumeGroupCallback) arrayList.get(i)).onAudioVolumeGroupChanged(message.arg1, message.arg2);
                                }
                                return;
                            default:
                                return;
                        }
                    }
                };
                native_setup(new java.lang.ref.WeakReference(this));
            }
        }
    }

    protected void finalize() {
        native_finalize();
        if (this.mHandlerThread.isAlive()) {
            this.mHandlerThread.quit();
        }
    }

    public void registerListener(android.media.AudioManager.VolumeGroupCallback volumeGroupCallback) {
        com.android.internal.util.Preconditions.checkNotNull(volumeGroupCallback, "volume group callback shall not be null");
        synchronized (this) {
            this.mListeners.add(volumeGroupCallback);
        }
        if (this.mHandler != null) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(4, 0, 0, volumeGroupCallback));
        }
    }

    public void unregisterListener(android.media.AudioManager.VolumeGroupCallback volumeGroupCallback) {
        com.android.internal.util.Preconditions.checkNotNull(volumeGroupCallback, "volume group callback shall not be null");
        synchronized (this) {
            this.mListeners.remove(volumeGroupCallback);
        }
    }

    android.os.Handler handler() {
        return this.mHandler;
    }

    private static void postEventFromNative(java.lang.Object obj, int i, int i2, int i3, java.lang.Object obj2) {
        android.os.Handler handler;
        android.media.audiopolicy.AudioVolumeGroupChangeHandler audioVolumeGroupChangeHandler = (android.media.audiopolicy.AudioVolumeGroupChangeHandler) ((java.lang.ref.WeakReference) obj).get();
        if (audioVolumeGroupChangeHandler != null && audioVolumeGroupChangeHandler != null && (handler = audioVolumeGroupChangeHandler.handler()) != null) {
            handler.sendMessage(handler.obtainMessage(i, i2, i3, obj2));
        }
    }
}
