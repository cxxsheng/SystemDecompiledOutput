package android.content.om;

/* loaded from: classes.dex */
public final class OverlayInfo implements android.content.om.CriticalOverlayInfo, android.os.Parcelable {
    public static final java.lang.String CATEGORY_THEME = "android.theme";
    public static final android.os.Parcelable.Creator<android.content.om.OverlayInfo> CREATOR = new android.os.Parcelable.Creator<android.content.om.OverlayInfo>() { // from class: android.content.om.OverlayInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.om.OverlayInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.om.OverlayInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.om.OverlayInfo[] newArray(int i) {
            return new android.content.om.OverlayInfo[i];
        }
    };
    public static final int STATE_DISABLED = 2;
    public static final int STATE_ENABLED = 3;

    @java.lang.Deprecated
    public static final int STATE_ENABLED_IMMUTABLE = 6;
    public static final int STATE_MISSING_TARGET = 0;
    public static final int STATE_NO_IDMAP = 1;
    public static final int STATE_OVERLAY_IS_BEING_REPLACED = 5;
    public static final int STATE_SYSTEM_UPDATE_UNINSTALL = 7;

    @java.lang.Deprecated
    public static final int STATE_TARGET_IS_BEING_REPLACED = 4;
    public static final int STATE_UNKNOWN = -1;
    public final java.lang.String baseCodePath;
    public final java.lang.String category;
    public final boolean isFabricated;
    public final boolean isMutable;
    private android.content.om.OverlayIdentifier mIdentifierCached;
    public final java.lang.String overlayName;
    public final java.lang.String packageName;
    public final int priority;
    public final int state;
    public final java.lang.String targetOverlayableName;
    public final java.lang.String targetPackageName;
    public final int userId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface State {
    }

    public OverlayInfo(android.content.om.OverlayInfo overlayInfo, int i) {
        this(overlayInfo.packageName, overlayInfo.overlayName, overlayInfo.targetPackageName, overlayInfo.targetOverlayableName, overlayInfo.category, overlayInfo.baseCodePath, i, overlayInfo.userId, overlayInfo.priority, overlayInfo.isMutable, overlayInfo.isFabricated);
    }

    public OverlayInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, int i, int i2, int i3, boolean z) {
        this(str, null, str2, str3, str4, str5, i, i2, i3, z, false);
    }

    public OverlayInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, int i, int i2, int i3, boolean z, boolean z2) {
        this.packageName = str;
        this.overlayName = str2;
        this.targetPackageName = str3;
        this.targetOverlayableName = str4;
        this.category = str5;
        this.baseCodePath = str6;
        this.state = i;
        this.userId = i2;
        this.priority = i3;
        this.isMutable = z;
        this.isFabricated = z2;
        ensureValidState();
    }

    public OverlayInfo(android.os.Parcel parcel) {
        this.packageName = parcel.readString();
        this.overlayName = parcel.readString();
        this.targetPackageName = parcel.readString();
        this.targetOverlayableName = parcel.readString();
        this.category = parcel.readString();
        this.baseCodePath = parcel.readString();
        this.state = parcel.readInt();
        this.userId = parcel.readInt();
        this.priority = parcel.readInt();
        this.isMutable = parcel.readBoolean();
        this.isFabricated = parcel.readBoolean();
        ensureValidState();
    }

    @Override // android.content.om.CriticalOverlayInfo
    @android.annotation.SystemApi
    public java.lang.String getPackageName() {
        return this.packageName;
    }

    @Override // android.content.om.CriticalOverlayInfo
    public java.lang.String getOverlayName() {
        return this.overlayName;
    }

    @Override // android.content.om.CriticalOverlayInfo
    public java.lang.String getTargetPackageName() {
        return this.targetPackageName;
    }

    @android.annotation.SystemApi
    public java.lang.String getCategory() {
        return this.category;
    }

    @android.annotation.SystemApi
    public int getUserId() {
        return this.userId;
    }

    @Override // android.content.om.CriticalOverlayInfo
    public java.lang.String getTargetOverlayableName() {
        return this.targetOverlayableName;
    }

    @Override // android.content.om.CriticalOverlayInfo
    public boolean isFabricated() {
        return this.isFabricated;
    }

    public java.lang.String getBaseCodePath() {
        return this.baseCodePath;
    }

    @Override // android.content.om.CriticalOverlayInfo
    public android.content.om.OverlayIdentifier getOverlayIdentifier() {
        if (this.mIdentifierCached == null) {
            this.mIdentifierCached = new android.content.om.OverlayIdentifier(this.packageName, this.overlayName);
        }
        return this.mIdentifierCached;
    }

    private void ensureValidState() {
        if (this.packageName == null) {
            throw new java.lang.IllegalArgumentException("packageName must not be null");
        }
        if (this.targetPackageName == null) {
            throw new java.lang.IllegalArgumentException("targetPackageName must not be null");
        }
        if (this.baseCodePath == null) {
            throw new java.lang.IllegalArgumentException("baseCodePath must not be null");
        }
        switch (this.state) {
            case -1:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return;
            default:
                throw new java.lang.IllegalArgumentException("State " + this.state + " is not a valid state");
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.overlayName);
        parcel.writeString(this.targetPackageName);
        parcel.writeString(this.targetOverlayableName);
        parcel.writeString(this.category);
        parcel.writeString(this.baseCodePath);
        parcel.writeInt(this.state);
        parcel.writeInt(this.userId);
        parcel.writeInt(this.priority);
        parcel.writeBoolean(this.isMutable);
        parcel.writeBoolean(this.isFabricated);
    }

    @android.annotation.SystemApi
    public boolean isEnabled() {
        switch (this.state) {
            case 3:
            case 6:
                return true;
            default:
                return false;
        }
    }

    public static java.lang.String stateToString(int i) {
        switch (i) {
            case -1:
                return "STATE_UNKNOWN";
            case 0:
                return "STATE_MISSING_TARGET";
            case 1:
                return "STATE_NO_IDMAP";
            case 2:
                return "STATE_DISABLED";
            case 3:
                return "STATE_ENABLED";
            case 4:
                return "STATE_TARGET_IS_BEING_REPLACED";
            case 5:
                return "STATE_OVERLAY_IS_BEING_REPLACED";
            case 6:
                return "STATE_ENABLED_IMMUTABLE";
            default:
                return "<unknown state>";
        }
    }

    public int hashCode() {
        return ((((((((((((((this.userId + 31) * 31) + this.state) * 31) + (this.packageName == null ? 0 : this.packageName.hashCode())) * 31) + (this.overlayName == null ? 0 : this.overlayName.hashCode())) * 31) + (this.targetPackageName == null ? 0 : this.targetPackageName.hashCode())) * 31) + (this.targetOverlayableName == null ? 0 : this.targetOverlayableName.hashCode())) * 31) + (this.category == null ? 0 : this.category.hashCode())) * 31) + (this.baseCodePath != null ? this.baseCodePath.hashCode() : 0);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.om.OverlayInfo overlayInfo = (android.content.om.OverlayInfo) obj;
        if (this.userId == overlayInfo.userId && this.state == overlayInfo.state && this.packageName.equals(overlayInfo.packageName) && java.util.Objects.equals(this.overlayName, overlayInfo.overlayName) && this.targetPackageName.equals(overlayInfo.targetPackageName) && java.util.Objects.equals(this.targetOverlayableName, overlayInfo.targetOverlayableName) && java.util.Objects.equals(this.category, overlayInfo.category) && this.baseCodePath.equals(overlayInfo.baseCodePath)) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        return "OverlayInfo {packageName=" + this.packageName + ", overlayName=" + this.overlayName + ", targetPackage=" + this.targetPackageName + ", targetOverlayable=" + this.targetOverlayableName + ", state=" + this.state + " (" + stateToString(this.state) + "),, userId=" + this.userId + " }";
    }
}
