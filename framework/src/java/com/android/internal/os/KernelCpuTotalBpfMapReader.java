package com.android.internal.os;

/* loaded from: classes4.dex */
public final class KernelCpuTotalBpfMapReader {
    private static native long[] readInternal();

    private KernelCpuTotalBpfMapReader() {
    }

    public static long[] read() {
        if (!com.android.internal.os.KernelCpuBpfTracking.startTracking()) {
            return null;
        }
        return readInternal();
    }
}
