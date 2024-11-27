package android.mtp;

/* loaded from: classes2.dex */
public final class MtpStorageInfo {
    private java.lang.String mDescription;
    private long mFreeSpace;
    private long mMaxCapacity;
    private int mStorageId;
    private java.lang.String mVolumeIdentifier;

    private MtpStorageInfo() {
    }

    public final int getStorageId() {
        return this.mStorageId;
    }

    public final long getMaxCapacity() {
        return this.mMaxCapacity;
    }

    public final long getFreeSpace() {
        return this.mFreeSpace;
    }

    public final java.lang.String getDescription() {
        return this.mDescription;
    }

    public final java.lang.String getVolumeIdentifier() {
        return this.mVolumeIdentifier;
    }
}
