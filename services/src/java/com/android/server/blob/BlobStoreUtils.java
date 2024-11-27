package com.android.server.blob;

/* loaded from: classes.dex */
class BlobStoreUtils {
    private static final java.lang.String DESC_RES_TYPE_STRING = "string";
    private static final java.lang.Object sLock = new java.lang.Object();
    private static android.os.Handler sRevocableFdHandler;

    BlobStoreUtils() {
    }

    @android.annotation.Nullable
    static android.content.res.Resources getPackageResources(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.lang.String str, int i) {
        try {
            return context.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().getResourcesForApplication(str);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.d(com.android.server.blob.BlobStoreConfig.TAG, "Unknown package in user " + i + ": " + str, e);
            return null;
        }
    }

    static int getDescriptionResourceId(@android.annotation.NonNull android.content.res.Resources resources, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        return resources.getIdentifier(str, DESC_RES_TYPE_STRING, str2);
    }

    static int getDescriptionResourceId(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        android.content.res.Resources packageResources = getPackageResources(context, str2, i);
        if (packageResources == null) {
            return 0;
        }
        return getDescriptionResourceId(packageResources, str, str2);
    }

    @android.annotation.NonNull
    static java.lang.String formatTime(long j) {
        return android.text.format.TimeMigrationUtils.formatMillisWithFixedFormat(j);
    }

    @android.annotation.NonNull
    static android.os.Handler getRevocableFdHandler() {
        synchronized (sLock) {
            try {
                if (sRevocableFdHandler != null) {
                    return sRevocableFdHandler;
                }
                android.os.HandlerThread handlerThread = new android.os.HandlerThread("BlobFuseLooper");
                handlerThread.start();
                sRevocableFdHandler = new android.os.Handler(handlerThread.getLooper());
                return sRevocableFdHandler;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
