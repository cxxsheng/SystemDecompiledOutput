package com.android.server.security;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public final class FileIntegrity {
    private FileIntegrity() {
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
    public static void setUpFsVerity(@android.annotation.NonNull java.io.File file) throws java.io.IOException {
        android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(file, 268435456);
        try {
            setUpFsVerity(open);
            if (open != null) {
                open.close();
            }
        } catch (java.lang.Throwable th) {
            if (open != null) {
                try {
                    open.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
    public static void setUpFsVerity(@android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        com.android.internal.security.VerityUtils.setUpFsverity(parcelFileDescriptor.getFd());
    }
}
