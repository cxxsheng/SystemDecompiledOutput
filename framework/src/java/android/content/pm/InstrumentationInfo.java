package android.content.pm;

/* loaded from: classes.dex */
public class InstrumentationInfo extends android.content.pm.PackageItemInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.InstrumentationInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.InstrumentationInfo>() { // from class: android.content.pm.InstrumentationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.InstrumentationInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.InstrumentationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.InstrumentationInfo[] newArray(int i) {
            return new android.content.pm.InstrumentationInfo[i];
        }
    };
    public java.lang.String credentialProtectedDataDir;
    public java.lang.String dataDir;
    public java.lang.String deviceProtectedDataDir;
    public boolean functionalTest;
    public boolean handleProfiling;
    public java.lang.String nativeLibraryDir;
    public java.lang.String primaryCpuAbi;
    public java.lang.String publicSourceDir;
    public java.lang.String secondaryCpuAbi;
    public java.lang.String secondaryNativeLibraryDir;
    public java.lang.String sourceDir;
    public android.util.SparseArray<int[]> splitDependencies;
    public java.lang.String[] splitNames;
    public java.lang.String[] splitPublicSourceDirs;
    public java.lang.String[] splitSourceDirs;
    public java.lang.String targetPackage;
    public java.lang.String targetProcesses;

    public InstrumentationInfo() {
    }

    public InstrumentationInfo(android.content.pm.InstrumentationInfo instrumentationInfo) {
        super(instrumentationInfo);
        this.targetPackage = instrumentationInfo.targetPackage;
        this.targetProcesses = instrumentationInfo.targetProcesses;
        this.sourceDir = instrumentationInfo.sourceDir;
        this.publicSourceDir = instrumentationInfo.publicSourceDir;
        this.splitNames = instrumentationInfo.splitNames;
        this.splitSourceDirs = instrumentationInfo.splitSourceDirs;
        this.splitPublicSourceDirs = instrumentationInfo.splitPublicSourceDirs;
        this.splitDependencies = instrumentationInfo.splitDependencies;
        this.dataDir = instrumentationInfo.dataDir;
        this.deviceProtectedDataDir = instrumentationInfo.deviceProtectedDataDir;
        this.credentialProtectedDataDir = instrumentationInfo.credentialProtectedDataDir;
        this.primaryCpuAbi = instrumentationInfo.primaryCpuAbi;
        this.secondaryCpuAbi = instrumentationInfo.secondaryCpuAbi;
        this.nativeLibraryDir = instrumentationInfo.nativeLibraryDir;
        this.secondaryNativeLibraryDir = instrumentationInfo.secondaryNativeLibraryDir;
        this.handleProfiling = instrumentationInfo.handleProfiling;
        this.functionalTest = instrumentationInfo.functionalTest;
    }

    public java.lang.String toString() {
        return "InstrumentationInfo{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.packageName + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.targetPackage);
        parcel.writeString8(this.targetProcesses);
        parcel.writeString8(this.sourceDir);
        parcel.writeString8(this.publicSourceDir);
        parcel.writeString8Array(this.splitNames);
        parcel.writeString8Array(this.splitSourceDirs);
        parcel.writeString8Array(this.splitPublicSourceDirs);
        parcel.writeSparseArray(this.splitDependencies);
        parcel.writeString8(this.dataDir);
        parcel.writeString8(this.deviceProtectedDataDir);
        parcel.writeString8(this.credentialProtectedDataDir);
        parcel.writeString8(this.primaryCpuAbi);
        parcel.writeString8(this.secondaryCpuAbi);
        parcel.writeString8(this.nativeLibraryDir);
        parcel.writeString8(this.secondaryNativeLibraryDir);
        parcel.writeInt(this.handleProfiling ? 1 : 0);
        parcel.writeInt(this.functionalTest ? 1 : 0);
    }

    private InstrumentationInfo(android.os.Parcel parcel) {
        super(parcel);
        this.targetPackage = parcel.readString8();
        this.targetProcesses = parcel.readString8();
        this.sourceDir = parcel.readString8();
        this.publicSourceDir = parcel.readString8();
        this.splitNames = parcel.createString8Array();
        this.splitSourceDirs = parcel.createString8Array();
        this.splitPublicSourceDirs = parcel.createString8Array();
        this.splitDependencies = parcel.readSparseArray(null);
        this.dataDir = parcel.readString8();
        this.deviceProtectedDataDir = parcel.readString8();
        this.credentialProtectedDataDir = parcel.readString8();
        this.primaryCpuAbi = parcel.readString8();
        this.secondaryCpuAbi = parcel.readString8();
        this.nativeLibraryDir = parcel.readString8();
        this.secondaryNativeLibraryDir = parcel.readString8();
        this.handleProfiling = parcel.readInt() != 0;
        this.functionalTest = parcel.readInt() != 0;
    }

    public void copyTo(android.content.pm.ApplicationInfo applicationInfo) {
        applicationInfo.packageName = this.packageName;
        applicationInfo.sourceDir = this.sourceDir;
        applicationInfo.publicSourceDir = this.publicSourceDir;
        applicationInfo.splitNames = this.splitNames;
        applicationInfo.splitSourceDirs = this.splitSourceDirs;
        applicationInfo.splitPublicSourceDirs = this.splitPublicSourceDirs;
        applicationInfo.splitDependencies = this.splitDependencies;
        applicationInfo.dataDir = this.dataDir;
        applicationInfo.deviceProtectedDataDir = this.deviceProtectedDataDir;
        applicationInfo.credentialProtectedDataDir = this.credentialProtectedDataDir;
        applicationInfo.primaryCpuAbi = this.primaryCpuAbi;
        applicationInfo.secondaryCpuAbi = this.secondaryCpuAbi;
        applicationInfo.nativeLibraryDir = this.nativeLibraryDir;
        applicationInfo.secondaryNativeLibraryDir = this.secondaryNativeLibraryDir;
    }
}
