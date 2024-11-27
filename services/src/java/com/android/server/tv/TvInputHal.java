package com.android.server.tv;

/* loaded from: classes2.dex */
final class TvInputHal implements android.os.Handler.Callback {
    private static final boolean DEBUG = false;
    public static final int ERROR_NO_INIT = -1;
    public static final int ERROR_STALE_CONFIG = -2;
    public static final int ERROR_UNKNOWN = -3;
    public static final int EVENT_DEVICE_AVAILABLE = 1;
    public static final int EVENT_DEVICE_UNAVAILABLE = 2;
    public static final int EVENT_FIRST_FRAME_CAPTURED = 4;
    public static final int EVENT_STREAM_CONFIGURATION_CHANGED = 3;
    public static final int EVENT_TV_MESSAGE = 5;
    public static final int SUCCESS = 0;
    private static final java.lang.String TAG = com.android.server.tv.TvInputHal.class.getSimpleName();
    private final com.android.server.tv.TvInputHal.Callback mCallback;
    private final java.lang.Object mLock = new java.lang.Object();
    private long mPtr = 0;
    private final android.util.SparseIntArray mStreamConfigGenerations = new android.util.SparseIntArray();
    private final android.util.SparseArray<android.media.tv.TvStreamConfig[]> mStreamConfigs = new android.util.SparseArray<>();
    private final android.os.Handler mHandler = new android.os.Handler(this);

    public interface Callback {
        void onDeviceAvailable(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo, android.media.tv.TvStreamConfig[] tvStreamConfigArr);

        void onDeviceUnavailable(int i);

        void onFirstFrameCaptured(int i, int i2);

        void onStreamConfigurationChanged(int i, android.media.tv.TvStreamConfig[] tvStreamConfigArr, int i2);

        void onTvMessage(int i, int i2, android.os.Bundle bundle);
    }

    private static native int nativeAddOrUpdateStream(long j, int i, int i2, android.view.Surface surface);

    private static native void nativeClose(long j);

    private static native android.media.tv.TvStreamConfig[] nativeGetStreamConfigs(long j, int i, int i2);

    private native long nativeOpen(android.os.MessageQueue messageQueue);

    private static native int nativeRemoveStream(long j, int i, int i2);

    private static native int nativeSetTvMessageEnabled(long j, int i, int i2, int i3, boolean z);

    public TvInputHal(com.android.server.tv.TvInputHal.Callback callback) {
        this.mCallback = callback;
    }

    public void init() {
        synchronized (this.mLock) {
            this.mPtr = nativeOpen(this.mHandler.getLooper().getQueue());
        }
    }

    public int addOrUpdateStream(int i, android.view.Surface surface, android.media.tv.TvStreamConfig tvStreamConfig) {
        synchronized (this.mLock) {
            try {
                if (this.mPtr == 0) {
                    return -1;
                }
                if (this.mStreamConfigGenerations.get(i, 0) != tvStreamConfig.getGeneration()) {
                    return -2;
                }
                return nativeAddOrUpdateStream(this.mPtr, i, tvStreamConfig.getStreamId(), surface) == 0 ? 0 : -3;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int setTvMessageEnabled(int i, android.media.tv.TvStreamConfig tvStreamConfig, int i2, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mPtr == 0) {
                    return -1;
                }
                if (this.mStreamConfigGenerations.get(i, 0) != tvStreamConfig.getGeneration()) {
                    return -2;
                }
                return nativeSetTvMessageEnabled(this.mPtr, i, tvStreamConfig.getStreamId(), i2, z) == 0 ? 0 : -3;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int removeStream(int i, android.media.tv.TvStreamConfig tvStreamConfig) {
        synchronized (this.mLock) {
            try {
                if (this.mPtr == 0) {
                    return -1;
                }
                if (this.mStreamConfigGenerations.get(i, 0) != tvStreamConfig.getGeneration()) {
                    return -2;
                }
                return nativeRemoveStream(this.mPtr, i, tvStreamConfig.getStreamId()) == 0 ? 0 : -3;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void close() {
        synchronized (this.mLock) {
            try {
                if (this.mPtr != 0) {
                    nativeClose(this.mPtr);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void retrieveStreamConfigsLocked(int i) {
        int i2 = this.mStreamConfigGenerations.get(i, 0) + 1;
        this.mStreamConfigs.put(i, nativeGetStreamConfigs(this.mPtr, i, i2));
        this.mStreamConfigGenerations.put(i, i2);
    }

    private void deviceAvailableFromNative(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) {
        this.mHandler.obtainMessage(1, tvInputHardwareInfo).sendToTarget();
    }

    private void deviceUnavailableFromNative(int i) {
        this.mHandler.obtainMessage(2, i, 0).sendToTarget();
    }

    private void streamConfigsChangedFromNative(int i, int i2) {
        this.mHandler.obtainMessage(3, i, i2).sendToTarget();
    }

    private void firstFrameCapturedFromNative(int i, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, i, i2));
    }

    private void tvMessageReceivedFromNative(int i, int i2, android.os.Bundle bundle) {
        this.mHandler.obtainMessage(5, i, i2, bundle).sendToTarget();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message message) {
        android.media.tv.TvStreamConfig[] tvStreamConfigArr;
        android.media.tv.TvStreamConfig[] tvStreamConfigArr2;
        switch (message.what) {
            case 1:
                android.media.tv.TvInputHardwareInfo tvInputHardwareInfo = (android.media.tv.TvInputHardwareInfo) message.obj;
                synchronized (this.mLock) {
                    retrieveStreamConfigsLocked(tvInputHardwareInfo.getDeviceId());
                    tvStreamConfigArr = this.mStreamConfigs.get(tvInputHardwareInfo.getDeviceId());
                }
                this.mCallback.onDeviceAvailable(tvInputHardwareInfo, tvStreamConfigArr);
                return true;
            case 2:
                this.mCallback.onDeviceUnavailable(message.arg1);
                return true;
            case 3:
                int i = message.arg1;
                int i2 = message.arg2;
                synchronized (this.mLock) {
                    retrieveStreamConfigsLocked(i);
                    tvStreamConfigArr2 = this.mStreamConfigs.get(i);
                }
                this.mCallback.onStreamConfigurationChanged(i, tvStreamConfigArr2, i2);
                return true;
            case 4:
                this.mCallback.onFirstFrameCaptured(message.arg1, message.arg2);
                return true;
            case 5:
                this.mCallback.onTvMessage(message.arg1, message.arg2, (android.os.Bundle) message.obj);
                return true;
            default:
                android.util.Slog.e(TAG, "Unknown event: " + message);
                return false;
        }
    }
}
