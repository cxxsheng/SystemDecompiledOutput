package android.content.rollback;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class RollbackInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.rollback.RollbackInfo> CREATOR = new android.os.Parcelable.Creator<android.content.rollback.RollbackInfo>() { // from class: android.content.rollback.RollbackInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.rollback.RollbackInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.rollback.RollbackInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.rollback.RollbackInfo[] newArray(int i) {
            return new android.content.rollback.RollbackInfo[i];
        }
    };
    private final java.util.List<android.content.pm.VersionedPackage> mCausePackages;
    private int mCommittedSessionId;
    private final boolean mIsStaged;
    private final java.util.List<android.content.rollback.PackageRollbackInfo> mPackages;
    private final int mRollbackId;
    private int mRollbackImpactLevel;

    public RollbackInfo(int i, java.util.List<android.content.rollback.PackageRollbackInfo> list, boolean z, java.util.List<android.content.pm.VersionedPackage> list2, int i2, int i3) {
        this.mRollbackId = i;
        this.mPackages = list;
        this.mIsStaged = z;
        this.mCausePackages = list2;
        this.mCommittedSessionId = i2;
        this.mRollbackImpactLevel = i3;
    }

    public RollbackInfo(int i, java.util.List<android.content.rollback.PackageRollbackInfo> list, boolean z, java.util.List<android.content.pm.VersionedPackage> list2, int i2) {
        this(i, list, z, list2, i2, 0);
    }

    private RollbackInfo(android.os.Parcel parcel) {
        this.mRollbackId = parcel.readInt();
        this.mPackages = parcel.createTypedArrayList(android.content.rollback.PackageRollbackInfo.CREATOR);
        this.mIsStaged = parcel.readBoolean();
        this.mCausePackages = parcel.createTypedArrayList(android.content.pm.VersionedPackage.CREATOR);
        this.mCommittedSessionId = parcel.readInt();
        this.mRollbackImpactLevel = parcel.readInt();
    }

    public int getRollbackId() {
        return this.mRollbackId;
    }

    public java.util.List<android.content.rollback.PackageRollbackInfo> getPackages() {
        return this.mPackages;
    }

    public boolean isStaged() {
        return this.mIsStaged;
    }

    public int getCommittedSessionId() {
        return this.mCommittedSessionId;
    }

    public void setCommittedSessionId(int i) {
        this.mCommittedSessionId = i;
    }

    public java.util.List<android.content.pm.VersionedPackage> getCausePackages() {
        return this.mCausePackages;
    }

    public int getRollbackImpactLevel() {
        return this.mRollbackImpactLevel;
    }

    public void setRollbackImpactLevel(int i) {
        this.mRollbackImpactLevel = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRollbackId);
        parcel.writeTypedList(this.mPackages);
        parcel.writeBoolean(this.mIsStaged);
        parcel.writeTypedList(this.mCausePackages);
        parcel.writeInt(this.mCommittedSessionId);
        parcel.writeInt(this.mRollbackImpactLevel);
    }
}
