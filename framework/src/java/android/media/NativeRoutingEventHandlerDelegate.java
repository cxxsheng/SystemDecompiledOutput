package android.media;

/* loaded from: classes2.dex */
class NativeRoutingEventHandlerDelegate {
    private android.media.AudioRouting mAudioRouting;
    private android.os.Handler mHandler;
    private android.media.AudioRouting.OnRoutingChangedListener mOnRoutingChangedListener;

    NativeRoutingEventHandlerDelegate(android.media.AudioRouting audioRouting, android.media.AudioRouting.OnRoutingChangedListener onRoutingChangedListener, android.os.Handler handler) {
        this.mAudioRouting = audioRouting;
        this.mOnRoutingChangedListener = onRoutingChangedListener;
        this.mHandler = handler;
    }

    void notifyClient() {
        if (this.mHandler != null) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.NativeRoutingEventHandlerDelegate.1
                @Override // java.lang.Runnable
                public void run() {
                    if (android.media.NativeRoutingEventHandlerDelegate.this.mOnRoutingChangedListener != null) {
                        android.media.NativeRoutingEventHandlerDelegate.this.mOnRoutingChangedListener.onRoutingChanged(android.media.NativeRoutingEventHandlerDelegate.this.mAudioRouting);
                    }
                }
            });
        }
    }
}
