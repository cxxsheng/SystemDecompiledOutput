package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class LockTaskPolicy extends android.app.admin.PolicyValue<android.app.admin.LockTaskPolicy> {
    public static final android.os.Parcelable.Creator<android.app.admin.LockTaskPolicy> CREATOR = new android.os.Parcelable.Creator<android.app.admin.LockTaskPolicy>() { // from class: android.app.admin.LockTaskPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.LockTaskPolicy createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.LockTaskPolicy(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.LockTaskPolicy[] newArray(int i) {
            return new android.app.admin.LockTaskPolicy[i];
        }
    };
    public static final int DEFAULT_LOCK_TASK_FLAG = 16;
    private int mFlags;
    private java.util.Set<java.lang.String> mPackages;

    public java.util.Set<java.lang.String> getPackages() {
        return this.mPackages;
    }

    public int getFlags() {
        return this.mFlags;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.app.admin.PolicyValue
    public android.app.admin.LockTaskPolicy getValue() {
        return this;
    }

    public LockTaskPolicy(java.util.Set<java.lang.String> set) {
        this.mPackages = new java.util.HashSet();
        this.mFlags = 16;
        if (set != null) {
            setPackagesInternal(set);
        }
        setValue(this);
    }

    public LockTaskPolicy(int i) {
        this.mPackages = new java.util.HashSet();
        this.mFlags = 16;
        this.mFlags = i;
        setValue(this);
    }

    public LockTaskPolicy(java.util.Set<java.lang.String> set, int i) {
        this.mPackages = new java.util.HashSet();
        this.mFlags = 16;
        if (set != null) {
            setPackagesInternal(set);
        }
        this.mFlags = i;
        setValue(this);
    }

    private LockTaskPolicy(android.os.Parcel parcel) {
        this.mPackages = new java.util.HashSet();
        this.mFlags = 16;
        int readInt = parcel.readInt();
        this.mPackages = new java.util.HashSet();
        for (int i = 0; i < readInt; i++) {
            this.mPackages.add(parcel.readString());
        }
        this.mFlags = parcel.readInt();
        setValue(this);
    }

    public LockTaskPolicy(android.app.admin.LockTaskPolicy lockTaskPolicy) {
        this.mPackages = new java.util.HashSet();
        this.mFlags = 16;
        this.mPackages = new java.util.HashSet(lockTaskPolicy.mPackages);
        this.mFlags = lockTaskPolicy.mFlags;
        setValue(this);
    }

    public void setPackages(java.util.Set<java.lang.String> set) {
        java.util.Objects.requireNonNull(set);
        setPackagesInternal(set);
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    private void setPackagesInternal(java.util.Set<java.lang.String> set) {
        if (android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled()) {
            java.util.Iterator<java.lang.String> it = set.iterator();
            while (it.hasNext()) {
                android.app.admin.PolicySizeVerifier.enforceMaxPackageNameLength(it.next());
            }
        }
        this.mPackages = new java.util.HashSet(set);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.admin.LockTaskPolicy lockTaskPolicy = (android.app.admin.LockTaskPolicy) obj;
        if (java.util.Objects.equals(this.mPackages, lockTaskPolicy.mPackages) && this.mFlags == lockTaskPolicy.mFlags) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPackages, java.lang.Integer.valueOf(this.mFlags));
    }

    public java.lang.String toString() {
        return "LockTaskPolicy {mPackages= " + java.lang.String.join(", ", this.mPackages) + "; mFlags= " + this.mFlags + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPackages.size());
        java.util.Iterator<java.lang.String> it = this.mPackages.iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
        parcel.writeInt(this.mFlags);
    }
}
