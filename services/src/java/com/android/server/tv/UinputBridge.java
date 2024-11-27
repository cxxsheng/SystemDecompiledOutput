package com.android.server.tv;

/* loaded from: classes2.dex */
public final class UinputBridge {
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private long mPtr;
    private android.os.IBinder mToken;

    private static native void nativeClear(long j);

    private static native void nativeClose(long j);

    private static native long nativeGamepadOpen(java.lang.String str, java.lang.String str2);

    private static native long nativeOpen(java.lang.String str, java.lang.String str2, int i, int i2, int i3);

    private static native void nativeSendGamepadAxisValue(long j, int i, float f);

    private static native void nativeSendGamepadKey(long j, int i, boolean z);

    private static native void nativeSendKey(long j, int i, boolean z);

    private static native void nativeSendPointerDown(long j, int i, int i2, int i3);

    private static native void nativeSendPointerSync(long j);

    private static native void nativeSendPointerUp(long j, int i);

    public UinputBridge(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3) throws java.io.IOException {
        if (i < 1 || i2 < 1) {
            throw new java.lang.IllegalArgumentException("Touchpad must be at least 1x1.");
        }
        if (i3 < 1 || i3 > 32) {
            throw new java.lang.IllegalArgumentException("Touchpad must support between 1 and 32 pointers.");
        }
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("Token cannot be null");
        }
        this.mPtr = nativeOpen(str, iBinder.toString(), i, i2, i3);
        if (this.mPtr == 0) {
            throw new java.io.IOException("Could not open uinput device " + str);
        }
        this.mToken = iBinder;
        this.mCloseGuard.open("close");
    }

    private UinputBridge(android.os.IBinder iBinder, long j) {
        this.mPtr = j;
        this.mToken = iBinder;
        this.mCloseGuard.open("close");
    }

    public static com.android.server.tv.UinputBridge openGamepad(android.os.IBinder iBinder, java.lang.String str) throws java.io.IOException {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("Token cannot be null");
        }
        long nativeGamepadOpen = nativeGamepadOpen(str, iBinder.toString());
        if (nativeGamepadOpen == 0) {
            throw new java.io.IOException("Could not open uinput device " + str);
        }
        return new com.android.server.tv.UinputBridge(iBinder, nativeGamepadOpen);
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            this.mCloseGuard.warnIfOpen();
            close(this.mToken);
        } finally {
            this.mToken = null;
            super.finalize();
        }
    }

    public void close(android.os.IBinder iBinder) {
        if (isTokenValid(iBinder) && this.mPtr != 0) {
            clear(iBinder);
            nativeClose(this.mPtr);
            this.mPtr = 0L;
            this.mCloseGuard.close();
        }
    }

    public android.os.IBinder getToken() {
        return this.mToken;
    }

    protected boolean isTokenValid(android.os.IBinder iBinder) {
        return this.mToken.equals(iBinder);
    }

    public void sendKeyDown(android.os.IBinder iBinder, int i) {
        if (isTokenValid(iBinder)) {
            nativeSendKey(this.mPtr, i, true);
        }
    }

    public void sendKeyUp(android.os.IBinder iBinder, int i) {
        if (isTokenValid(iBinder)) {
            nativeSendKey(this.mPtr, i, false);
        }
    }

    public void sendPointerDown(android.os.IBinder iBinder, int i, int i2, int i3) {
        if (isTokenValid(iBinder)) {
            nativeSendPointerDown(this.mPtr, i, i2, i3);
        }
    }

    public void sendPointerUp(android.os.IBinder iBinder, int i) {
        if (isTokenValid(iBinder)) {
            nativeSendPointerUp(this.mPtr, i);
        }
    }

    public void sendPointerSync(android.os.IBinder iBinder) {
        if (isTokenValid(iBinder)) {
            nativeSendPointerSync(this.mPtr);
        }
    }

    public void sendGamepadKey(android.os.IBinder iBinder, int i, boolean z) {
        if (isTokenValid(iBinder)) {
            nativeSendGamepadKey(this.mPtr, i, z);
        }
    }

    public void sendGamepadAxisValue(android.os.IBinder iBinder, int i, float f) {
        if (isTokenValid(iBinder)) {
            nativeSendGamepadAxisValue(this.mPtr, i, f);
        }
    }

    public void clear(android.os.IBinder iBinder) {
        if (isTokenValid(iBinder)) {
            nativeClear(this.mPtr);
        }
    }
}
