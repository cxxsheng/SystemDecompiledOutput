package android.app.usage;

/* loaded from: classes.dex */
public class StorageStatsManager {
    private final android.content.Context mContext;
    private final android.app.usage.IStorageStatsManager mService;

    public StorageStatsManager(android.content.Context context, android.app.usage.IStorageStatsManager iStorageStatsManager) {
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
        this.mService = (android.app.usage.IStorageStatsManager) java.util.Objects.requireNonNull(iStorageStatsManager);
    }

    public boolean isQuotaSupported(java.util.UUID uuid) {
        try {
            return this.mService.isQuotaSupported(android.os.storage.StorageManager.convert(uuid), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public boolean isQuotaSupported(java.lang.String str) {
        return isQuotaSupported(android.os.storage.StorageManager.convert(str));
    }

    public boolean isReservedSupported(java.util.UUID uuid) {
        try {
            return this.mService.isReservedSupported(android.os.storage.StorageManager.convert(uuid), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getTotalBytes(java.util.UUID uuid) throws java.io.IOException {
        try {
            return this.mService.getTotalBytes(android.os.storage.StorageManager.convert(uuid), this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public long getTotalBytes(java.lang.String str) throws java.io.IOException {
        return getTotalBytes(android.os.storage.StorageManager.convert(str));
    }

    public long getFreeBytes(java.util.UUID uuid) throws java.io.IOException {
        try {
            return this.mService.getFreeBytes(android.os.storage.StorageManager.convert(uuid), this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public long getFreeBytes(java.lang.String str) throws java.io.IOException {
        return getFreeBytes(android.os.storage.StorageManager.convert(str));
    }

    public long getCacheBytes(java.util.UUID uuid) throws java.io.IOException {
        try {
            return this.mService.getCacheBytes(android.os.storage.StorageManager.convert(uuid), this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public long getCacheBytes(java.lang.String str) throws java.io.IOException {
        return getCacheBytes(android.os.storage.StorageManager.convert(str));
    }

    public android.app.usage.StorageStats queryStatsForPackage(java.util.UUID uuid, java.lang.String str, android.os.UserHandle userHandle) throws android.content.pm.PackageManager.NameNotFoundException, java.io.IOException {
        try {
            return this.mService.queryStatsForPackage(android.os.storage.StorageManager.convert(uuid), str, userHandle.getIdentifier(), this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public android.app.usage.StorageStats queryStatsForPackage(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.content.pm.PackageManager.NameNotFoundException, java.io.IOException {
        return queryStatsForPackage(android.os.storage.StorageManager.convert(str), str2, userHandle);
    }

    public android.app.usage.StorageStats queryStatsForUid(java.util.UUID uuid, int i) throws java.io.IOException {
        try {
            return this.mService.queryStatsForUid(android.os.storage.StorageManager.convert(uuid), i, this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public android.app.usage.StorageStats queryStatsForUid(java.lang.String str, int i) throws java.io.IOException {
        return queryStatsForUid(android.os.storage.StorageManager.convert(str), i);
    }

    public android.app.usage.StorageStats queryStatsForUser(java.util.UUID uuid, android.os.UserHandle userHandle) throws java.io.IOException {
        try {
            return this.mService.queryStatsForUser(android.os.storage.StorageManager.convert(uuid), userHandle.getIdentifier(), this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public android.app.usage.StorageStats queryStatsForUser(java.lang.String str, android.os.UserHandle userHandle) throws java.io.IOException {
        return queryStatsForUser(android.os.storage.StorageManager.convert(str), userHandle);
    }

    public android.app.usage.ExternalStorageStats queryExternalStatsForUser(java.util.UUID uuid, android.os.UserHandle userHandle) throws java.io.IOException {
        try {
            return this.mService.queryExternalStatsForUser(android.os.storage.StorageManager.convert(uuid), userHandle.getIdentifier(), this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public android.app.usage.ExternalStorageStats queryExternalStatsForUser(java.lang.String str, android.os.UserHandle userHandle) throws java.io.IOException {
        return queryExternalStatsForUser(android.os.storage.StorageManager.convert(str), userHandle);
    }

    public long getCacheQuotaBytes(java.lang.String str, int i) {
        try {
            return this.mService.getCacheQuotaBytes(str, i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Collection<android.os.storage.CrateInfo> queryCratesForUid(java.util.UUID uuid, int i) throws java.io.IOException, android.content.pm.PackageManager.NameNotFoundException {
        try {
            return ((android.content.pm.ParceledListSlice) java.util.Objects.requireNonNull(this.mService.queryCratesForUid(android.os.storage.StorageManager.convert(uuid), i, this.mContext.getOpPackageName()))).getList();
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public java.util.Collection<android.os.storage.CrateInfo> queryCratesForPackage(java.util.UUID uuid, java.lang.String str, android.os.UserHandle userHandle) throws android.content.pm.PackageManager.NameNotFoundException, java.io.IOException {
        try {
            return ((android.content.pm.ParceledListSlice) java.util.Objects.requireNonNull(this.mService.queryCratesForPackage(android.os.storage.StorageManager.convert(uuid), str, userHandle.getIdentifier(), this.mContext.getOpPackageName()))).getList();
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public java.util.Collection<android.os.storage.CrateInfo> queryCratesForUser(java.util.UUID uuid, android.os.UserHandle userHandle) throws android.content.pm.PackageManager.NameNotFoundException, java.io.IOException {
        try {
            return ((android.content.pm.ParceledListSlice) java.util.Objects.requireNonNull(this.mService.queryCratesForUser(android.os.storage.StorageManager.convert(uuid), userHandle.getIdentifier(), this.mContext.getOpPackageName()))).getList();
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }
}
