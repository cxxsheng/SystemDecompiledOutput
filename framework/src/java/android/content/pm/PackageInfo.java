package android.content.pm;

/* loaded from: classes.dex */
public class PackageInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.PackageInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageInfo>() { // from class: android.content.pm.PackageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.PackageInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.PackageInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.PackageInfo[] newArray(int i) {
            return new android.content.pm.PackageInfo[i];
        }
    };
    public static final int INSTALL_LOCATION_AUTO = 0;
    public static final int INSTALL_LOCATION_INTERNAL_ONLY = 1;
    public static final int INSTALL_LOCATION_PREFER_EXTERNAL = 2;
    public static final int INSTALL_LOCATION_UNSPECIFIED = -1;
    public static final int REQUESTED_PERMISSION_GRANTED = 2;
    public static final int REQUESTED_PERMISSION_IMPLICIT = 4;
    public static final int REQUESTED_PERMISSION_NEVER_FOR_LOCATION = 65536;
    public static final int REQUESTED_PERMISSION_REQUIRED = 1;
    public android.content.pm.ActivityInfo[] activities;
    public android.content.pm.ApplicationInfo applicationInfo;
    public android.content.pm.Attribution[] attributions;
    public int baseRevisionCode;
    public int compileSdkVersion;
    public java.lang.String compileSdkVersionCodename;
    public android.content.pm.ConfigurationInfo[] configPreferences;
    public boolean coreApp;
    public android.content.pm.FeatureGroupInfo[] featureGroups;
    public long firstInstallTime;
    public int[] gids;
    public int installLocation;
    public android.content.pm.InstrumentationInfo[] instrumentation;
    public boolean isActiveApex;
    public boolean isApex;
    public boolean isStub;
    public long lastUpdateTime;
    private java.lang.String mApexPackageName;
    private long mArchiveTimeMillis;
    public boolean mOverlayIsStatic;
    public java.lang.String overlayCategory;
    public int overlayPriority;
    public java.lang.String overlayTarget;
    public java.lang.String packageName;
    public android.content.pm.PermissionInfo[] permissions;
    public android.content.pm.ProviderInfo[] providers;
    public android.content.pm.ActivityInfo[] receivers;
    public android.content.pm.FeatureInfo[] reqFeatures;
    public java.lang.String[] requestedPermissions;
    public int[] requestedPermissionsFlags;
    public java.lang.String requiredAccountType;
    public boolean requiredForAllUsers;
    public java.lang.String restrictedAccountType;
    public android.content.pm.ServiceInfo[] services;
    public java.lang.String sharedUserId;
    public int sharedUserLabel;

    @java.lang.Deprecated
    public android.content.pm.Signature[] signatures;
    public android.content.pm.SigningInfo signingInfo;
    public java.lang.String[] splitNames;
    public int[] splitRevisionCodes;
    public java.lang.String targetOverlayableName;

    @java.lang.Deprecated
    public int versionCode;
    public int versionCodeMajor;
    public java.lang.String versionName;

    public long getLongVersionCode() {
        return composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    public void setLongVersionCode(long j) {
        this.versionCodeMajor = (int) (j >> 32);
        this.versionCode = (int) j;
    }

    public static long composeLongVersionCode(int i, int i2) {
        return (i2 & 4294967295L) | (i << 32);
    }

    public PackageInfo() {
        this.installLocation = 1;
    }

    public boolean isOverlayPackage() {
        return this.overlayTarget != null;
    }

    public boolean isStaticOverlayPackage() {
        return this.overlayTarget != null && this.mOverlayIsStatic;
    }

    public long getArchiveTimeMillis() {
        return this.mArchiveTimeMillis;
    }

    public void setArchiveTimeMillis(long j) {
        this.mArchiveTimeMillis = j;
    }

    public java.lang.String getApexPackageName() {
        return this.mApexPackageName;
    }

    public void setApexPackageName(java.lang.String str) {
        this.mApexPackageName = str;
    }

    public java.lang.String toString() {
        return "PackageInfo{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.packageName + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        boolean allowSquashing = parcel.allowSquashing();
        parcel.writeString8(this.packageName);
        parcel.writeString8Array(this.splitNames);
        parcel.writeInt(this.versionCode);
        parcel.writeInt(this.versionCodeMajor);
        parcel.writeString8(this.versionName);
        parcel.writeInt(this.baseRevisionCode);
        parcel.writeIntArray(this.splitRevisionCodes);
        parcel.writeString8(this.sharedUserId);
        parcel.writeInt(this.sharedUserLabel);
        if (this.applicationInfo != null) {
            parcel.writeInt(1);
            this.applicationInfo.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.firstInstallTime);
        parcel.writeLong(this.lastUpdateTime);
        parcel.writeIntArray(this.gids);
        parcel.writeTypedArray(this.activities, i);
        parcel.writeTypedArray(this.receivers, i);
        parcel.writeTypedArray(this.services, i);
        parcel.writeTypedArray(this.providers, i);
        parcel.writeTypedArray(this.instrumentation, i);
        parcel.writeTypedArray(this.permissions, i);
        parcel.writeString8Array(this.requestedPermissions);
        parcel.writeIntArray(this.requestedPermissionsFlags);
        parcel.writeTypedArray(this.signatures, i);
        parcel.writeTypedArray(this.configPreferences, i);
        parcel.writeTypedArray(this.reqFeatures, i);
        parcel.writeTypedArray(this.featureGroups, i);
        parcel.writeTypedArray(this.attributions, i);
        parcel.writeInt(this.installLocation);
        parcel.writeInt(this.isStub ? 1 : 0);
        parcel.writeInt(this.coreApp ? 1 : 0);
        parcel.writeInt(this.requiredForAllUsers ? 1 : 0);
        parcel.writeString8(this.restrictedAccountType);
        parcel.writeString8(this.requiredAccountType);
        parcel.writeString8(this.overlayTarget);
        parcel.writeString8(this.overlayCategory);
        parcel.writeInt(this.overlayPriority);
        parcel.writeBoolean(this.mOverlayIsStatic);
        parcel.writeInt(this.compileSdkVersion);
        parcel.writeString8(this.compileSdkVersionCodename);
        if (this.signingInfo != null) {
            parcel.writeInt(1);
            this.signingInfo.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBoolean(this.isApex);
        parcel.writeBoolean(this.isActiveApex);
        parcel.writeLong(this.mArchiveTimeMillis);
        if (this.mApexPackageName != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mApexPackageName);
        } else {
            parcel.writeInt(0);
        }
        parcel.restoreAllowSquashing(allowSquashing);
    }

    private PackageInfo(android.os.Parcel parcel) {
        this.installLocation = 1;
        this.packageName = parcel.readString8();
        this.splitNames = parcel.createString8Array();
        this.versionCode = parcel.readInt();
        this.versionCodeMajor = parcel.readInt();
        this.versionName = parcel.readString8();
        this.baseRevisionCode = parcel.readInt();
        this.splitRevisionCodes = parcel.createIntArray();
        this.sharedUserId = parcel.readString8();
        this.sharedUserLabel = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.applicationInfo = android.content.pm.ApplicationInfo.CREATOR.createFromParcel(parcel);
        }
        this.firstInstallTime = parcel.readLong();
        this.lastUpdateTime = parcel.readLong();
        this.gids = parcel.createIntArray();
        this.activities = (android.content.pm.ActivityInfo[]) parcel.createTypedArray(android.content.pm.ActivityInfo.CREATOR);
        this.receivers = (android.content.pm.ActivityInfo[]) parcel.createTypedArray(android.content.pm.ActivityInfo.CREATOR);
        this.services = (android.content.pm.ServiceInfo[]) parcel.createTypedArray(android.content.pm.ServiceInfo.CREATOR);
        this.providers = (android.content.pm.ProviderInfo[]) parcel.createTypedArray(android.content.pm.ProviderInfo.CREATOR);
        this.instrumentation = (android.content.pm.InstrumentationInfo[]) parcel.createTypedArray(android.content.pm.InstrumentationInfo.CREATOR);
        this.permissions = (android.content.pm.PermissionInfo[]) parcel.createTypedArray(android.content.pm.PermissionInfo.CREATOR);
        this.requestedPermissions = parcel.createString8Array();
        this.requestedPermissionsFlags = parcel.createIntArray();
        this.signatures = (android.content.pm.Signature[]) parcel.createTypedArray(android.content.pm.Signature.CREATOR);
        this.configPreferences = (android.content.pm.ConfigurationInfo[]) parcel.createTypedArray(android.content.pm.ConfigurationInfo.CREATOR);
        this.reqFeatures = (android.content.pm.FeatureInfo[]) parcel.createTypedArray(android.content.pm.FeatureInfo.CREATOR);
        this.featureGroups = (android.content.pm.FeatureGroupInfo[]) parcel.createTypedArray(android.content.pm.FeatureGroupInfo.CREATOR);
        this.attributions = (android.content.pm.Attribution[]) parcel.createTypedArray(android.content.pm.Attribution.CREATOR);
        this.installLocation = parcel.readInt();
        this.isStub = parcel.readInt() != 0;
        this.coreApp = parcel.readInt() != 0;
        this.requiredForAllUsers = parcel.readInt() != 0;
        this.restrictedAccountType = parcel.readString8();
        this.requiredAccountType = parcel.readString8();
        this.overlayTarget = parcel.readString8();
        this.overlayCategory = parcel.readString8();
        this.overlayPriority = parcel.readInt();
        this.mOverlayIsStatic = parcel.readBoolean();
        this.compileSdkVersion = parcel.readInt();
        this.compileSdkVersionCodename = parcel.readString8();
        if (parcel.readInt() != 0) {
            this.signingInfo = android.content.pm.SigningInfo.CREATOR.createFromParcel(parcel);
        }
        this.isApex = parcel.readBoolean();
        this.isActiveApex = parcel.readBoolean();
        this.mArchiveTimeMillis = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.mApexPackageName = parcel.readString8();
        }
    }
}
