package com.android.server.usage;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public interface StorageStatsManagerLocal {

    public interface StorageStatsAugmenter {
        void augmentStatsForPackageForUser(@android.annotation.NonNull android.content.pm.PackageStats packageStats, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle, boolean z);

        void augmentStatsForUid(@android.annotation.NonNull android.content.pm.PackageStats packageStats, int i, boolean z);

        void augmentStatsForUser(@android.annotation.NonNull android.content.pm.PackageStats packageStats, @android.annotation.NonNull android.os.UserHandle userHandle);
    }

    void registerStorageStatsAugmenter(@android.annotation.NonNull com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter storageStatsAugmenter, @android.annotation.NonNull java.lang.String str);
}
