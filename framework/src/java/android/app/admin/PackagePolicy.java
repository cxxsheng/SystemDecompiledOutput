package android.app.admin;

/* loaded from: classes.dex */
public final class PackagePolicy implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.PackagePolicy> CREATOR = new android.os.Parcelable.Creator<android.app.admin.PackagePolicy>() { // from class: android.app.admin.PackagePolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.PackagePolicy createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.PackagePolicy(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.PackagePolicy[] newArray(int i) {
            return new android.app.admin.PackagePolicy[i];
        }
    };
    public static final int PACKAGE_POLICY_ALLOWLIST = 3;
    public static final int PACKAGE_POLICY_ALLOWLIST_AND_SYSTEM = 2;
    public static final int PACKAGE_POLICY_BLOCKLIST = 1;
    private android.util.ArraySet<java.lang.String> mPackageNames;
    private int mPolicyType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PackagePolicyType {
    }

    public PackagePolicy(int i) {
        this(i, (java.util.Set<java.lang.String>) java.util.Collections.emptySet());
    }

    public PackagePolicy(int i, java.util.Set<java.lang.String> set) {
        if (i != 1 && i != 2 && i != 3) {
            throw new java.lang.IllegalArgumentException("Invalid policy type");
        }
        this.mPolicyType = i;
        this.mPackageNames = new android.util.ArraySet<>(set);
    }

    private PackagePolicy(android.os.Parcel parcel) {
        this.mPolicyType = parcel.readInt();
        this.mPackageNames = parcel.readArraySet(null);
    }

    public int getPolicyType() {
        return this.mPolicyType;
    }

    public java.util.Set<java.lang.String> getPackageNames() {
        return java.util.Collections.unmodifiableSet(this.mPackageNames);
    }

    public boolean isPackageAllowed(java.lang.String str, java.util.Set<java.lang.String> set) {
        if (this.mPolicyType == 1) {
            return !this.mPackageNames.contains(str);
        }
        if (this.mPackageNames.contains(str)) {
            return true;
        }
        return this.mPolicyType == 2 && set.contains(str);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPolicyType);
        parcel.writeArraySet(this.mPackageNames);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.admin.PackagePolicy)) {
            return false;
        }
        android.app.admin.PackagePolicy packagePolicy = (android.app.admin.PackagePolicy) obj;
        return this.mPolicyType == packagePolicy.mPolicyType && this.mPackageNames.equals(packagePolicy.mPackageNames);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPolicyType), this.mPackageNames);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
