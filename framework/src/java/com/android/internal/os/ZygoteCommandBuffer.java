package com.android.internal.os;

/* loaded from: classes4.dex */
class ZygoteCommandBuffer implements java.lang.AutoCloseable {
    private long mNativeBuffer;
    private final int mNativeSocket;
    private final android.net.LocalSocket mSocket;

    private static native void freeNativeBuffer(long j);

    private static native long getNativeBuffer(int i);

    private static native void insert(long j, java.lang.String str);

    private static native boolean nativeForkRepeatedly(long j, int i, int i2, int i3, java.lang.String str);

    private static native int nativeGetCount(long j);

    private static native java.lang.String nativeNextArg(long j);

    private static native void nativeReadFullyAndReset(long j);

    ZygoteCommandBuffer(android.net.LocalSocket localSocket) {
        this.mSocket = localSocket;
        if (localSocket == null) {
            this.mNativeSocket = -1;
        } else {
            this.mNativeSocket = this.mSocket.getFileDescriptor().getInt$();
        }
        this.mNativeBuffer = getNativeBuffer(this.mNativeSocket);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    ZygoteCommandBuffer(java.lang.String[] strArr) {
        this((android.net.LocalSocket) null);
        setCommand(strArr);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        freeNativeBuffer(this.mNativeBuffer);
        this.mNativeBuffer = 0L;
    }

    int getCount() {
        try {
            return nativeGetCount(this.mNativeBuffer);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this.mSocket);
        }
    }

    private void setCommand(java.lang.String[] strArr) {
        insert(this.mNativeBuffer, java.lang.Integer.toString(strArr.length));
        for (java.lang.String str : strArr) {
            insert(this.mNativeBuffer, str);
        }
    }

    java.lang.String nextArg() {
        try {
            return nativeNextArg(this.mNativeBuffer);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this.mSocket);
        }
    }

    void readFullyAndReset() {
        try {
            nativeReadFullyAndReset(this.mNativeBuffer);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this.mSocket);
        }
    }

    boolean forkRepeatedly(java.io.FileDescriptor fileDescriptor, int i, int i2, java.lang.String str) {
        try {
            return nativeForkRepeatedly(this.mNativeBuffer, fileDescriptor.getInt$(), i, i2, str);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this.mSocket);
            java.lang.ref.Reference.reachabilityFence(fileDescriptor);
        }
    }
}
