package android.content.pm;

/* loaded from: classes.dex */
public class UserInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.UserInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.UserInfo>() { // from class: android.content.pm.UserInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.UserInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.UserInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.UserInfo[] newArray(int i) {
            return new android.content.pm.UserInfo[i];
        }
    };
    public static final int FLAG_ADMIN = 2;

    @java.lang.Deprecated
    public static final int FLAG_DEMO = 512;
    public static final int FLAG_DISABLED = 64;
    public static final int FLAG_EPHEMERAL = 256;
    public static final int FLAG_EPHEMERAL_ON_CREATE = 8192;
    public static final int FLAG_FOR_TESTING = 32768;
    public static final int FLAG_FULL = 1024;

    @java.lang.Deprecated
    public static final int FLAG_GUEST = 4;
    public static final int FLAG_INITIALIZED = 16;
    public static final int FLAG_MAIN = 16384;

    @java.lang.Deprecated
    public static final int FLAG_MANAGED_PROFILE = 32;

    @java.lang.Deprecated
    public static final int FLAG_PRIMARY = 1;
    public static final int FLAG_PROFILE = 4096;
    public static final int FLAG_QUIET_MODE = 128;

    @java.lang.Deprecated
    public static final int FLAG_RESTRICTED = 8;
    public static final int FLAG_SYSTEM = 2048;
    public static final int NO_PROFILE_GROUP_ID = -10000;
    public boolean convertedFromPreCreated;
    public long creationTime;
    public int flags;
    public boolean guestToRemove;
    public java.lang.String iconPath;
    public int id;
    public java.lang.String lastLoggedInFingerprint;
    public long lastLoggedInTime;
    public java.lang.String name;
    public boolean partial;
    public boolean preCreated;
    public int profileBadge;
    public int profileGroupId;
    public int restrictedProfileParentId;
    public int serialNumber;
    public java.lang.String userType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UserInfoFlag {
    }

    public UserInfo(int i, java.lang.String str, int i2) {
        this(i, str, null, i2);
    }

    public UserInfo(int i, java.lang.String str, java.lang.String str2, int i2) {
        this(i, str, str2, i2, getDefaultUserType(i2));
    }

    public UserInfo(int i, java.lang.String str, java.lang.String str2, int i2, java.lang.String str3) {
        this.id = i;
        this.name = str;
        this.flags = i2;
        this.userType = str3;
        this.iconPath = str2;
        this.profileGroupId = -10000;
        this.restrictedProfileParentId = -10000;
    }

    public static java.lang.String getDefaultUserType(int i) {
        if ((i & 2048) != 0) {
            throw new java.lang.IllegalArgumentException("Cannot getDefaultUserType for flags " + java.lang.Integer.toHexString(i) + " because it corresponds to a SYSTEM user type.");
        }
        switch (i & 556) {
            case 0:
                return android.os.UserManager.USER_TYPE_FULL_SECONDARY;
            case 4:
                return android.os.UserManager.USER_TYPE_FULL_GUEST;
            case 8:
                return android.os.UserManager.USER_TYPE_FULL_RESTRICTED;
            case 32:
                return android.os.UserManager.USER_TYPE_PROFILE_MANAGED;
            case 512:
                return android.os.UserManager.USER_TYPE_FULL_DEMO;
            default:
                throw new java.lang.IllegalArgumentException("Cannot getDefaultUserType for flags " + java.lang.Integer.toHexString(i) + " because it doesn't correspond to a valid user type.");
        }
    }

    @java.lang.Deprecated
    public boolean isPrimary() {
        return (this.flags & 1) == 1;
    }

    public boolean isAdmin() {
        return (this.flags & 2) == 2;
    }

    public boolean isGuest() {
        return android.os.UserManager.isUserTypeGuest(this.userType);
    }

    public boolean isRestricted() {
        return android.os.UserManager.isUserTypeRestricted(this.userType);
    }

    public boolean isProfile() {
        return (this.flags & 4096) != 0;
    }

    public boolean isManagedProfile() {
        return android.os.UserManager.isUserTypeManagedProfile(this.userType);
    }

    public boolean isCloneProfile() {
        return android.os.UserManager.isUserTypeCloneProfile(this.userType);
    }

    public boolean isCommunalProfile() {
        return android.os.UserManager.isUserTypeCommunalProfile(this.userType);
    }

    public boolean isPrivateProfile() {
        return android.os.UserManager.isUserTypePrivateProfile(this.userType);
    }

    public boolean isEnabled() {
        return (this.flags & 64) != 64;
    }

    public boolean isQuietModeEnabled() {
        return (this.flags & 128) == 128;
    }

    public boolean isEphemeral() {
        return (this.flags & 256) == 256;
    }

    public boolean isForTesting() {
        return (this.flags & 32768) == 32768;
    }

    public boolean isInitialized() {
        return (this.flags & 16) == 16;
    }

    public boolean isDemo() {
        return android.os.UserManager.isUserTypeDemo(this.userType) || (this.flags & 512) != 0;
    }

    public boolean isFull() {
        return (this.flags & 1024) == 1024;
    }

    public boolean isMain() {
        return (this.flags & 16384) == 16384;
    }

    public boolean supportsSwitchTo() {
        if (this.partial || !isEnabled() || this.preCreated) {
            return false;
        }
        return isFull() || canSwitchToHeadlessSystemUser();
    }

    private boolean canSwitchToHeadlessSystemUser() {
        return android.os.UserManager.USER_TYPE_SYSTEM_HEADLESS.equals(this.userType) && android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_canSwitchToHeadlessSystemUser);
    }

    @java.lang.Deprecated
    public boolean supportsSwitchToByUser() {
        return supportsSwitchTo();
    }

    public boolean canHaveProfile() {
        if (isProfile() || isGuest() || isRestricted()) {
            return false;
        }
        return isMain();
    }

    @java.lang.Deprecated
    public UserInfo() {
    }

    public UserInfo(android.content.pm.UserInfo userInfo) {
        this.name = userInfo.name;
        this.iconPath = userInfo.iconPath;
        this.id = userInfo.id;
        this.flags = userInfo.flags;
        this.userType = userInfo.userType;
        this.serialNumber = userInfo.serialNumber;
        this.creationTime = userInfo.creationTime;
        this.lastLoggedInTime = userInfo.lastLoggedInTime;
        this.lastLoggedInFingerprint = userInfo.lastLoggedInFingerprint;
        this.partial = userInfo.partial;
        this.preCreated = userInfo.preCreated;
        this.convertedFromPreCreated = userInfo.convertedFromPreCreated;
        this.profileGroupId = userInfo.profileGroupId;
        this.restrictedProfileParentId = userInfo.restrictedProfileParentId;
        this.guestToRemove = userInfo.guestToRemove;
        this.profileBadge = userInfo.profileBadge;
    }

    public android.os.UserHandle getUserHandle() {
        return android.os.UserHandle.of(this.id);
    }

    public java.lang.String toString() {
        return "UserInfo{" + this.id + ":" + this.name + ":" + java.lang.Integer.toHexString(this.flags) + "}";
    }

    public java.lang.String toFullString() {
        return "UserInfo[id=" + this.id + ", name=" + this.name + ", type=" + this.userType + ", flags=" + flagsToString(this.flags) + (this.preCreated ? " (pre-created)" : "") + (this.convertedFromPreCreated ? " (converted)" : "") + (this.partial ? " (partial)" : "") + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static java.lang.String flagsToString(int i) {
        return android.util.DebugUtils.flagsToString(android.content.pm.UserInfo.class, "FLAG_", i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString8(this.name);
        parcel.writeString8(this.iconPath);
        parcel.writeInt(this.flags);
        parcel.writeString8(this.userType);
        parcel.writeInt(this.serialNumber);
        parcel.writeLong(this.creationTime);
        parcel.writeLong(this.lastLoggedInTime);
        parcel.writeString8(this.lastLoggedInFingerprint);
        parcel.writeBoolean(this.partial);
        parcel.writeBoolean(this.preCreated);
        parcel.writeInt(this.profileGroupId);
        parcel.writeBoolean(this.guestToRemove);
        parcel.writeInt(this.restrictedProfileParentId);
        parcel.writeInt(this.profileBadge);
    }

    private UserInfo(android.os.Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString8();
        this.iconPath = parcel.readString8();
        this.flags = parcel.readInt();
        this.userType = parcel.readString8();
        this.serialNumber = parcel.readInt();
        this.creationTime = parcel.readLong();
        this.lastLoggedInTime = parcel.readLong();
        this.lastLoggedInFingerprint = parcel.readString8();
        this.partial = parcel.readBoolean();
        this.preCreated = parcel.readBoolean();
        this.profileGroupId = parcel.readInt();
        this.guestToRemove = parcel.readBoolean();
        this.restrictedProfileParentId = parcel.readInt();
        this.profileBadge = parcel.readInt();
    }
}
