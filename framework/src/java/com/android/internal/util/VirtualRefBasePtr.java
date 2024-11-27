package com.android.internal.util;

/* loaded from: classes5.dex */
public final class VirtualRefBasePtr {
    private long mNativePtr;

    private static native void nDecStrong(long j);

    private static native void nIncStrong(long j);

    public VirtualRefBasePtr(long j) {
        this.mNativePtr = j;
        nIncStrong(this.mNativePtr);
    }

    public long get() {
        return this.mNativePtr;
    }

    public void release() {
        if (this.mNativePtr != 0) {
            nDecStrong(this.mNativePtr);
            this.mNativePtr = 0L;
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }
}
