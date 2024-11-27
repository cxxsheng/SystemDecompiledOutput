package android.content.rollback;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class PackageRollbackInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.rollback.PackageRollbackInfo> CREATOR = new android.os.Parcelable.Creator<android.content.rollback.PackageRollbackInfo>() { // from class: android.content.rollback.PackageRollbackInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.rollback.PackageRollbackInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.rollback.PackageRollbackInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.rollback.PackageRollbackInfo[] newArray(int i) {
            return new android.content.rollback.PackageRollbackInfo[i];
        }
    };
    private final boolean mIsApex;
    private final boolean mIsApkInApex;
    private final java.util.List<java.lang.Integer> mPendingBackups;
    private final java.util.ArrayList<android.content.rollback.PackageRollbackInfo.RestoreInfo> mPendingRestores;
    private final int mRollbackDataPolicy;
    private final java.util.List<java.lang.Integer> mSnapshottedUsers;
    private final android.content.pm.VersionedPackage mVersionRolledBackFrom;
    private final android.content.pm.VersionedPackage mVersionRolledBackTo;

    public static class RestoreInfo {
        public final int appId;
        public final java.lang.String seInfo;
        public final int userId;

        public RestoreInfo(int i, int i2, java.lang.String str) {
            this.userId = i;
            this.appId = i2;
            this.seInfo = str;
        }
    }

    public java.lang.String getPackageName() {
        return this.mVersionRolledBackFrom.getPackageName();
    }

    public android.content.pm.VersionedPackage getVersionRolledBackFrom() {
        return this.mVersionRolledBackFrom;
    }

    public android.content.pm.VersionedPackage getVersionRolledBackTo() {
        return this.mVersionRolledBackTo;
    }

    public void addPendingBackup(int i) {
        this.mPendingBackups.add(java.lang.Integer.valueOf(i));
    }

    public java.util.List<java.lang.Integer> getPendingBackups() {
        return this.mPendingBackups;
    }

    public java.util.ArrayList<android.content.rollback.PackageRollbackInfo.RestoreInfo> getPendingRestores() {
        return this.mPendingRestores;
    }

    public android.content.rollback.PackageRollbackInfo.RestoreInfo getRestoreInfo(int i) {
        java.util.Iterator<android.content.rollback.PackageRollbackInfo.RestoreInfo> it = this.mPendingRestores.iterator();
        while (it.hasNext()) {
            android.content.rollback.PackageRollbackInfo.RestoreInfo next = it.next();
            if (next.userId == i) {
                return next;
            }
        }
        return null;
    }

    public void removeRestoreInfo(android.content.rollback.PackageRollbackInfo.RestoreInfo restoreInfo) {
        this.mPendingRestores.remove(restoreInfo);
    }

    public boolean isApex() {
        return this.mIsApex;
    }

    public int getRollbackDataPolicy() {
        return this.mRollbackDataPolicy;
    }

    public boolean isApkInApex() {
        return this.mIsApkInApex;
    }

    public java.util.List<java.lang.Integer> getSnapshottedUsers() {
        return this.mSnapshottedUsers;
    }

    public void removePendingBackup(int i) {
        this.mPendingBackups.remove(java.lang.Integer.valueOf(i));
    }

    public void removePendingRestoreInfo(int i) {
        removeRestoreInfo(getRestoreInfo(i));
    }

    public PackageRollbackInfo(android.content.pm.VersionedPackage versionedPackage, android.content.pm.VersionedPackage versionedPackage2, java.util.List<java.lang.Integer> list, java.util.ArrayList<android.content.rollback.PackageRollbackInfo.RestoreInfo> arrayList, boolean z, boolean z2, java.util.List<java.lang.Integer> list2) {
        this(versionedPackage, versionedPackage2, list, arrayList, z, z2, list2, 0);
    }

    public PackageRollbackInfo(android.content.pm.VersionedPackage versionedPackage, android.content.pm.VersionedPackage versionedPackage2, java.util.List<java.lang.Integer> list, java.util.ArrayList<android.content.rollback.PackageRollbackInfo.RestoreInfo> arrayList, boolean z, boolean z2, java.util.List<java.lang.Integer> list2, int i) {
        this.mVersionRolledBackFrom = versionedPackage;
        this.mVersionRolledBackTo = versionedPackage2;
        this.mPendingBackups = list;
        this.mPendingRestores = arrayList;
        this.mIsApex = z;
        this.mRollbackDataPolicy = i;
        this.mIsApkInApex = z2;
        this.mSnapshottedUsers = list2;
    }

    private PackageRollbackInfo(android.os.Parcel parcel) {
        this.mVersionRolledBackFrom = android.content.pm.VersionedPackage.CREATOR.createFromParcel(parcel);
        this.mVersionRolledBackTo = android.content.pm.VersionedPackage.CREATOR.createFromParcel(parcel);
        this.mIsApex = parcel.readBoolean();
        this.mIsApkInApex = parcel.readBoolean();
        this.mPendingRestores = null;
        this.mPendingBackups = null;
        this.mSnapshottedUsers = null;
        this.mRollbackDataPolicy = 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mVersionRolledBackFrom.writeToParcel(parcel, i);
        this.mVersionRolledBackTo.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mIsApex);
        parcel.writeBoolean(this.mIsApkInApex);
    }
}
