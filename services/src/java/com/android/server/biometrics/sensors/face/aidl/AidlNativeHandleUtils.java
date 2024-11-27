package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public final class AidlNativeHandleUtils {
    @android.annotation.Nullable
    public static android.hardware.common.NativeHandle dup(@android.annotation.Nullable android.os.NativeHandle nativeHandle) throws java.io.IOException {
        if (nativeHandle == null) {
            return null;
        }
        android.hardware.common.NativeHandle nativeHandle2 = new android.hardware.common.NativeHandle();
        java.io.FileDescriptor[] fileDescriptors = nativeHandle.getFileDescriptors();
        nativeHandle2.ints = (int[]) nativeHandle.getInts().clone();
        nativeHandle2.fds = new android.os.ParcelFileDescriptor[fileDescriptors.length];
        for (int i = 0; i < fileDescriptors.length; i++) {
            nativeHandle2.fds[i] = android.os.ParcelFileDescriptor.dup(fileDescriptors[i]);
        }
        return nativeHandle2;
    }

    public static void close(@android.annotation.Nullable android.hardware.common.NativeHandle nativeHandle) throws java.io.IOException {
        if (nativeHandle != null) {
            for (android.os.ParcelFileDescriptor parcelFileDescriptor : nativeHandle.fds) {
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
            }
        }
    }
}
