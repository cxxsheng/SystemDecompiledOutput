package android.content.pm;

/* loaded from: classes.dex */
public final class ProviderInfo extends android.content.pm.ComponentInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.ProviderInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.ProviderInfo>() { // from class: android.content.pm.ProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ProviderInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.ProviderInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ProviderInfo[] newArray(int i) {
            return new android.content.pm.ProviderInfo[i];
        }
    };
    public static final int FLAG_SINGLE_USER = 1073741824;
    public static final int FLAG_SYSTEM_USER_ONLY = 536870912;
    public static final int FLAG_VISIBLE_TO_INSTANT_APP = 1048576;
    public java.lang.String authority;
    public int flags;
    public boolean forceUriPermissions;
    public boolean grantUriPermissions;
    public int initOrder;

    @java.lang.Deprecated
    public boolean isSyncable;
    public boolean multiprocess;
    public android.content.pm.PathPermission[] pathPermissions;
    public java.lang.String readPermission;
    public android.os.PatternMatcher[] uriPermissionPatterns;
    public java.lang.String writePermission;

    public ProviderInfo() {
        this.authority = null;
        this.readPermission = null;
        this.writePermission = null;
        this.grantUriPermissions = false;
        this.forceUriPermissions = false;
        this.uriPermissionPatterns = null;
        this.pathPermissions = null;
        this.multiprocess = false;
        this.initOrder = 0;
        this.flags = 0;
        this.isSyncable = false;
    }

    public ProviderInfo(android.content.pm.ProviderInfo providerInfo) {
        super(providerInfo);
        this.authority = null;
        this.readPermission = null;
        this.writePermission = null;
        this.grantUriPermissions = false;
        this.forceUriPermissions = false;
        this.uriPermissionPatterns = null;
        this.pathPermissions = null;
        this.multiprocess = false;
        this.initOrder = 0;
        this.flags = 0;
        this.isSyncable = false;
        this.authority = providerInfo.authority;
        this.readPermission = providerInfo.readPermission;
        this.writePermission = providerInfo.writePermission;
        this.grantUriPermissions = providerInfo.grantUriPermissions;
        this.forceUriPermissions = providerInfo.forceUriPermissions;
        this.uriPermissionPatterns = providerInfo.uriPermissionPatterns;
        this.pathPermissions = providerInfo.pathPermissions;
        this.multiprocess = providerInfo.multiprocess;
        this.initOrder = providerInfo.initOrder;
        this.flags = providerInfo.flags;
        this.isSyncable = providerInfo.isSyncable;
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        dump(printer, str, 3);
    }

    public void dump(android.util.Printer printer, java.lang.String str, int i) {
        super.dumpFront(printer, str);
        printer.println(str + "authority=" + this.authority);
        printer.println(str + "flags=0x" + java.lang.Integer.toHexString(this.flags));
        super.dumpBack(printer, str, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.pm.ComponentInfo, android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.authority);
        parcel.writeString8(this.readPermission);
        parcel.writeString8(this.writePermission);
        parcel.writeInt(this.grantUriPermissions ? 1 : 0);
        parcel.writeInt(this.forceUriPermissions ? 1 : 0);
        parcel.writeTypedArray(this.uriPermissionPatterns, i);
        parcel.writeTypedArray(this.pathPermissions, i);
        parcel.writeInt(this.multiprocess ? 1 : 0);
        parcel.writeInt(this.initOrder);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.isSyncable ? 1 : 0);
    }

    public java.lang.String toString() {
        return "ContentProviderInfo{name=" + this.authority + " className=" + this.name + "}";
    }

    private ProviderInfo(android.os.Parcel parcel) {
        super(parcel);
        this.authority = null;
        this.readPermission = null;
        this.writePermission = null;
        this.grantUriPermissions = false;
        this.forceUriPermissions = false;
        this.uriPermissionPatterns = null;
        this.pathPermissions = null;
        this.multiprocess = false;
        this.initOrder = 0;
        this.flags = 0;
        this.isSyncable = false;
        this.authority = parcel.readString8();
        this.readPermission = parcel.readString8();
        this.writePermission = parcel.readString8();
        this.grantUriPermissions = parcel.readInt() != 0;
        this.forceUriPermissions = parcel.readInt() != 0;
        this.uriPermissionPatterns = (android.os.PatternMatcher[]) parcel.createTypedArray(android.os.PatternMatcher.CREATOR);
        this.pathPermissions = (android.content.pm.PathPermission[]) parcel.createTypedArray(android.content.pm.PathPermission.CREATOR);
        this.multiprocess = parcel.readInt() != 0;
        this.initOrder = parcel.readInt();
        this.flags = parcel.readInt();
        this.isSyncable = parcel.readInt() != 0;
    }
}
