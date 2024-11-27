package com.android.server.backup.params;

/* loaded from: classes.dex */
public class AdbRestoreParams extends com.android.server.backup.params.AdbParams {
    public AdbRestoreParams(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        this.fd = parcelFileDescriptor;
    }
}
