package android.mtp;

/* loaded from: classes2.dex */
public class MtpStorage {
    private final java.lang.String mDescription;
    private final java.util.function.Supplier<java.lang.Boolean> mIsHostWindows;
    private final long mMaxFileSize;
    private final java.lang.String mPath;
    private final boolean mRemovable;
    private final int mStorageId;
    private final java.lang.String mVolumeName;

    public MtpStorage(android.os.storage.StorageVolume storageVolume, int i, java.util.function.Supplier<java.lang.Boolean> supplier) {
        this.mStorageId = i;
        this.mPath = storageVolume.getPath();
        this.mDescription = storageVolume.getDescription(null);
        this.mRemovable = storageVolume.isRemovable();
        this.mMaxFileSize = storageVolume.getMaxFileSize();
        this.mVolumeName = storageVolume.getMediaStoreVolumeName();
        this.mIsHostWindows = supplier;
    }

    public final int getStorageId() {
        return this.mStorageId;
    }

    public final java.lang.String getPath() {
        return this.mPath;
    }

    public final java.lang.String getDescription() {
        return this.mDescription;
    }

    public final boolean isRemovable() {
        return this.mRemovable;
    }

    public long getMaxFileSize() {
        return this.mMaxFileSize;
    }

    public java.lang.String getVolumeName() {
        return this.mVolumeName;
    }

    public boolean isHostWindows() {
        return this.mIsHostWindows.get().booleanValue();
    }
}
