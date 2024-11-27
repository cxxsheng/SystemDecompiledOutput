package android.os.storage;

/* loaded from: classes3.dex */
public abstract class StorageManagerInternal {

    public interface CloudProviderChangeListener {
        void onCloudProviderChanged(int i, java.lang.String str);
    }

    public interface ResetListener {
        void onReset(android.os.IVold iVold);
    }

    public abstract void addResetListener(android.os.storage.StorageManagerInternal.ResetListener resetListener);

    public abstract android.os.IInstalld.IFsveritySetupAuthToken createFsveritySetupAuthToken(android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws java.io.IOException;

    public abstract int enableFsverity(android.os.IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, java.lang.String str, java.lang.String str2) throws java.io.IOException;

    public abstract void freeCache(java.lang.String str, long j);

    public abstract int getExternalStorageMountMode(int i, java.lang.String str);

    public abstract java.util.List<java.lang.String> getPrimaryVolumeIds();

    public abstract boolean hasExternalStorageAccess(int i, java.lang.String str);

    public abstract boolean hasLegacyExternalStorage(int i);

    public abstract boolean isCeStoragePrepared(int i);

    public abstract boolean isExternalStorageService(int i);

    public abstract boolean isFuseMounted(int i);

    public abstract void markCeStoragePrepared(int i);

    public abstract void onAppOpsChanged(int i, int i2, java.lang.String str, int i3, int i4);

    public abstract void prepareAppDataAfterInstall(java.lang.String str, int i);

    public abstract boolean prepareStorageDirs(int i, java.util.Set<java.lang.String> set, java.lang.String str);

    public abstract void prepareUserStorageForMove(java.lang.String str, java.lang.String str2, java.util.List<android.content.pm.UserInfo> list);

    public abstract void registerCloudProviderChangeListener(android.os.storage.StorageManagerInternal.CloudProviderChangeListener cloudProviderChangeListener);

    public abstract void resetUser(int i);
}
