package android.content.pm;

/* loaded from: classes.dex */
public final class SharedLibraryInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.SharedLibraryInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.SharedLibraryInfo>() { // from class: android.content.pm.SharedLibraryInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.SharedLibraryInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.SharedLibraryInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.SharedLibraryInfo[] newArray(int i) {
            return new android.content.pm.SharedLibraryInfo[i];
        }
    };
    public static final int TYPE_BUILTIN = 0;
    public static final int TYPE_DYNAMIC = 1;
    public static final int TYPE_SDK_PACKAGE = 3;
    public static final int TYPE_STATIC = 2;
    public static final int VERSION_UNDEFINED = -1;
    private final java.util.List<java.lang.String> mCodePaths;
    private final android.content.pm.VersionedPackage mDeclaringPackage;
    private java.util.List<android.content.pm.SharedLibraryInfo> mDependencies;
    private final java.util.List<android.content.pm.VersionedPackage> mDependentPackages;
    private final boolean mIsNative;
    private final java.lang.String mName;
    private final java.util.List<android.content.pm.VersionedPackage> mOptionalDependentPackages;
    private final java.lang.String mPackageName;
    private final java.lang.String mPath;
    private final int mType;
    private final long mVersion;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public SharedLibraryInfo(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, java.lang.String str3, long j, int i, android.content.pm.VersionedPackage versionedPackage, java.util.List<android.content.pm.VersionedPackage> list2, java.util.List<android.content.pm.SharedLibraryInfo> list3, boolean z) {
        this.mPath = str;
        this.mPackageName = str2;
        this.mCodePaths = list;
        this.mName = str3;
        this.mVersion = j;
        this.mType = i;
        this.mDeclaringPackage = versionedPackage;
        this.mDependentPackages = list2;
        this.mDependencies = list3;
        this.mIsNative = z;
        this.mOptionalDependentPackages = null;
    }

    public SharedLibraryInfo(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, java.lang.String str3, long j, int i, android.content.pm.VersionedPackage versionedPackage, java.util.List<android.content.pm.SharedLibraryInfo> list2, boolean z, android.util.Pair<java.util.List<android.content.pm.VersionedPackage>, java.util.List<java.lang.Boolean>> pair) {
        this.mPath = str;
        this.mPackageName = str2;
        this.mCodePaths = list;
        this.mName = str3;
        this.mVersion = j;
        this.mType = i;
        this.mDeclaringPackage = versionedPackage;
        this.mDependencies = list2;
        this.mIsNative = z;
        java.util.List<android.content.pm.VersionedPackage> list3 = pair.first;
        java.util.List<java.lang.Boolean> list4 = pair.second;
        this.mDependentPackages = list3;
        java.util.ArrayList arrayList = null;
        if (this.mType == 3 && android.content.pm.Flags.sdkLibIndependence() && list3 != null && list4 != null && list3.size() == list4.size()) {
            for (int i2 = 0; i2 < list3.size(); i2++) {
                android.content.pm.VersionedPackage versionedPackage2 = list3.get(i2);
                if (list4.get(i2).booleanValue()) {
                    arrayList = arrayList == null ? new java.util.ArrayList() : arrayList;
                    arrayList.add(versionedPackage2);
                }
            }
        }
        this.mOptionalDependentPackages = arrayList;
    }

    private SharedLibraryInfo(android.os.Parcel parcel) {
        this.mPath = parcel.readString8();
        this.mPackageName = parcel.readString8();
        if (parcel.readInt() != 0) {
            this.mCodePaths = java.util.Arrays.asList(parcel.createString8Array());
        } else {
            this.mCodePaths = null;
        }
        this.mName = parcel.readString8();
        this.mVersion = parcel.readLong();
        this.mType = parcel.readInt();
        this.mDeclaringPackage = (android.content.pm.VersionedPackage) parcel.readParcelable(null, android.content.pm.VersionedPackage.class);
        this.mDependentPackages = parcel.readArrayList(null, android.content.pm.VersionedPackage.class);
        this.mDependencies = parcel.createTypedArrayList(CREATOR);
        this.mIsNative = parcel.readBoolean();
        this.mOptionalDependentPackages = parcel.readParcelableList(new java.util.ArrayList(), android.content.pm.VersionedPackage.class.getClassLoader(), android.content.pm.VersionedPackage.class);
    }

    public int getType() {
        return this.mType;
    }

    public boolean isNative() {
        return this.mIsNative;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String getPath() {
        return this.mPath;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.util.List<java.lang.String> getAllCodePaths() {
        if (getPath() != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(getPath());
            return arrayList;
        }
        return (java.util.List) java.util.Objects.requireNonNull(this.mCodePaths);
    }

    public void addDependency(android.content.pm.SharedLibraryInfo sharedLibraryInfo) {
        if (sharedLibraryInfo == null) {
            return;
        }
        if (this.mDependencies == null) {
            this.mDependencies = new java.util.ArrayList();
        }
        this.mDependencies.add(sharedLibraryInfo);
    }

    public void clearDependencies() {
        this.mDependencies = null;
    }

    public java.util.List<android.content.pm.SharedLibraryInfo> getDependencies() {
        return this.mDependencies;
    }

    @java.lang.Deprecated
    public int getVersion() {
        return (int) (this.mVersion < 0 ? this.mVersion : this.mVersion & 2147483647L);
    }

    public long getLongVersion() {
        return this.mVersion;
    }

    public boolean isBuiltin() {
        return this.mType == 0;
    }

    public boolean isDynamic() {
        return this.mType == 1;
    }

    public boolean isStatic() {
        return this.mType == 2;
    }

    public boolean isSdk() {
        return this.mType == 3;
    }

    public android.content.pm.VersionedPackage getDeclaringPackage() {
        return this.mDeclaringPackage;
    }

    public java.util.List<android.content.pm.VersionedPackage> getDependentPackages() {
        if (this.mDependentPackages == null) {
            return java.util.Collections.emptyList();
        }
        return this.mDependentPackages;
    }

    public java.util.List<android.content.pm.VersionedPackage> getOptionalDependentPackages() {
        if (this.mOptionalDependentPackages == null) {
            return java.util.Collections.emptyList();
        }
        return this.mOptionalDependentPackages;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "SharedLibraryInfo{name:" + this.mName + ", type:" + typeToString(this.mType) + ", version:" + this.mVersion + (!getDependentPackages().isEmpty() ? " has dependents" : "") + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mPath);
        parcel.writeString8(this.mPackageName);
        if (this.mCodePaths != null) {
            parcel.writeInt(1);
            parcel.writeString8Array((java.lang.String[]) this.mCodePaths.toArray(new java.lang.String[this.mCodePaths.size()]));
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString8(this.mName);
        parcel.writeLong(this.mVersion);
        parcel.writeInt(this.mType);
        parcel.writeParcelable(this.mDeclaringPackage, i);
        parcel.writeList(this.mDependentPackages);
        parcel.writeTypedList(this.mDependencies);
        parcel.writeBoolean(this.mIsNative);
        parcel.writeParcelableList(this.mOptionalDependentPackages, i);
    }

    private static java.lang.String typeToString(int i) {
        switch (i) {
            case 0:
                return "builtin";
            case 1:
                return "dynamic";
            case 2:
                return "static";
            case 3:
                return "sdk";
            default:
                return "unknown";
        }
    }
}
