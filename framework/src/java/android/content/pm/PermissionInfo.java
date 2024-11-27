package android.content.pm;

/* loaded from: classes.dex */
public class PermissionInfo extends android.content.pm.PackageItemInfo implements android.os.Parcelable {
    public static final int FLAG_COSTS_MONEY = 1;
    public static final int FLAG_HARD_RESTRICTED = 4;
    public static final int FLAG_IMMUTABLY_RESTRICTED = 16;
    public static final int FLAG_INSTALLED = 1073741824;

    @android.annotation.SystemApi
    public static final int FLAG_REMOVED = 2;
    public static final int FLAG_SOFT_RESTRICTED = 8;
    public static final int PROTECTION_DANGEROUS = 1;
    public static final int PROTECTION_FLAG_APPOP = 64;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_APP_PREDICTOR = 2097152;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_COMPANION = 8388608;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_CONFIGURATOR = 524288;
    public static final int PROTECTION_FLAG_DEVELOPMENT = 32;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_DOCUMENTER = 262144;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_INCIDENT_REPORT_APPROVER = 1048576;
    public static final int PROTECTION_FLAG_INSTALLER = 256;
    public static final int PROTECTION_FLAG_INSTANT = 4096;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_KNOWN_SIGNER = 134217728;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_MODULE = 4194304;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_OEM = 16384;
    public static final int PROTECTION_FLAG_PRE23 = 128;
    public static final int PROTECTION_FLAG_PREINSTALLED = 1024;
    public static final int PROTECTION_FLAG_PRIVILEGED = 16;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_RECENTS = 33554432;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_RETAIL_DEMO = 16777216;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_ROLE = 67108864;
    public static final int PROTECTION_FLAG_RUNTIME_ONLY = 8192;
    public static final int PROTECTION_FLAG_SETUP = 2048;

    @java.lang.Deprecated
    public static final int PROTECTION_FLAG_SYSTEM = 16;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_SYSTEM_TEXT_CLASSIFIER = 65536;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_VENDOR_PRIVILEGED = 32768;
    public static final int PROTECTION_FLAG_VERIFIER = 512;

    @android.annotation.SystemApi
    public static final int PROTECTION_FLAG_WELLBEING = 131072;
    public static final int PROTECTION_INTERNAL = 4;

    @java.lang.Deprecated
    public static final int PROTECTION_MASK_BASE = 15;

    @java.lang.Deprecated
    public static final int PROTECTION_MASK_FLAGS = 65520;
    public static final int PROTECTION_NORMAL = 0;
    public static final int PROTECTION_SIGNATURE = 2;

    @java.lang.Deprecated
    public static final int PROTECTION_SIGNATURE_OR_SYSTEM = 3;

    @android.annotation.SystemApi
    public final java.lang.String backgroundPermission;
    public int descriptionRes;
    public int flags;
    public java.lang.String group;

    @android.annotation.SystemApi
    public java.util.Set<java.lang.String> knownCerts;
    public java.lang.CharSequence nonLocalizedDescription;

    @java.lang.Deprecated
    public int protectionLevel;

    @android.annotation.SystemApi
    public int requestRes;
    private static final com.android.internal.util.Parcelling.BuiltIn.ForStringSet sForStringSet = (com.android.internal.util.Parcelling.BuiltIn.ForStringSet) com.android.internal.util.Parcelling.Cache.getOrCreate(com.android.internal.util.Parcelling.BuiltIn.ForStringSet.class);
    public static final android.os.Parcelable.Creator<android.content.pm.PermissionInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PermissionInfo>() { // from class: android.content.pm.PermissionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.PermissionInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.PermissionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.PermissionInfo[] newArray(int i) {
            return new android.content.pm.PermissionInfo[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Protection {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProtectionFlags {
    }

    public static int fixProtectionLevel(int i) {
        if (i == 3) {
            i = 18;
        }
        if ((32768 & i) != 0 && (i & 16) == 0) {
            return i & (-32769);
        }
        return i;
    }

    public static java.lang.String protectionToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        switch (i & 15) {
            case 0:
                sb.append(android.graphics.FontListParser.STYLE_NORMAL);
                break;
            case 1:
                sb.append("dangerous");
                break;
            case 2:
                sb.append("signature");
                break;
            case 3:
                sb.append("signatureOrSystem");
                break;
            case 4:
                sb.append("internal");
                break;
            default:
                sb.append("????");
                break;
        }
        if ((i & 16) != 0) {
            sb.append("|privileged");
        }
        if ((i & 32) != 0) {
            sb.append("|development");
        }
        if ((i & 64) != 0) {
            sb.append("|appop");
        }
        if ((i & 128) != 0) {
            sb.append("|pre23");
        }
        if ((i & 256) != 0) {
            sb.append("|installer");
        }
        if ((i & 512) != 0) {
            sb.append("|verifier");
        }
        if ((i & 1024) != 0) {
            sb.append("|preinstalled");
        }
        if ((i & 2048) != 0) {
            sb.append("|setup");
        }
        if ((i & 4096) != 0) {
            sb.append("|instant");
        }
        if ((i & 8192) != 0) {
            sb.append("|runtime");
        }
        if ((i & 16384) != 0) {
            sb.append("|oem");
        }
        if ((32768 & i) != 0) {
            sb.append("|vendorPrivileged");
        }
        if ((65536 & i) != 0) {
            sb.append("|textClassifier");
        }
        if ((524288 & i) != 0) {
            sb.append("|configurator");
        }
        if ((1048576 & i) != 0) {
            sb.append("|incidentReportApprover");
        }
        if ((2097152 & i) != 0) {
            sb.append("|appPredictor");
        }
        if ((8388608 & i) != 0) {
            sb.append("|companion");
        }
        if ((16777216 & i) != 0) {
            sb.append("|retailDemo");
        }
        if ((33554432 & i) != 0) {
            sb.append("|recents");
        }
        if ((67108864 & i) != 0) {
            sb.append("|role");
        }
        if ((134217728 & i) != 0) {
            sb.append("|knownSigner");
        }
        if ((i & 4194304) != 0) {
            sb.append("|module");
        }
        return sb.toString();
    }

    public static java.lang.String flagsToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        while (i != 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            switch (numberOfTrailingZeros) {
                case 1:
                    sb.append("costsMoney");
                    break;
                case 2:
                    sb.append(android.os.Environment.MEDIA_REMOVED);
                    break;
                case 4:
                    sb.append("hardRestricted");
                    break;
                case 8:
                    sb.append("softRestricted");
                    break;
                case 16:
                    sb.append("immutablyRestricted");
                    break;
                case 1073741824:
                    sb.append("installed");
                    break;
                default:
                    sb.append(numberOfTrailingZeros);
                    break;
            }
            if (i != 0) {
                sb.append(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            }
        }
        return sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).toString();
    }

    public PermissionInfo(java.lang.String str) {
        this.knownCerts = java.util.Collections.emptySet();
        this.backgroundPermission = str;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @java.lang.Deprecated
    public PermissionInfo() {
        this((java.lang.String) null);
    }

    @java.lang.Deprecated
    public PermissionInfo(android.content.pm.PermissionInfo permissionInfo) {
        super(permissionInfo);
        this.knownCerts = java.util.Collections.emptySet();
        this.protectionLevel = permissionInfo.protectionLevel;
        this.flags = permissionInfo.flags;
        this.group = permissionInfo.group;
        this.backgroundPermission = permissionInfo.backgroundPermission;
        this.descriptionRes = permissionInfo.descriptionRes;
        this.requestRes = permissionInfo.requestRes;
        this.nonLocalizedDescription = permissionInfo.nonLocalizedDescription;
        this.knownCerts = permissionInfo.knownCerts;
    }

    public java.lang.CharSequence loadDescription(android.content.pm.PackageManager packageManager) {
        java.lang.CharSequence text;
        if (this.nonLocalizedDescription != null) {
            return this.nonLocalizedDescription;
        }
        if (this.descriptionRes == 0 || (text = packageManager.getText(this.packageName, this.descriptionRes, null)) == null) {
            return null;
        }
        return text;
    }

    public int getProtection() {
        return this.protectionLevel & 15;
    }

    public int getProtectionFlags() {
        return this.protectionLevel & (-16);
    }

    public java.lang.String toString() {
        return "PermissionInfo{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.name + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.protectionLevel);
        parcel.writeInt(this.flags);
        parcel.writeString8(this.group);
        parcel.writeString8(this.backgroundPermission);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.requestRes);
        android.text.TextUtils.writeToParcel(this.nonLocalizedDescription, parcel, i);
        sForStringSet.parcel(this.knownCerts, parcel, i);
    }

    public int calculateFootprint() {
        int length = this.name.length();
        if (this.nonLocalizedLabel != null) {
            length += this.nonLocalizedLabel.length();
        }
        if (this.nonLocalizedDescription != null) {
            return length + this.nonLocalizedDescription.length();
        }
        return length;
    }

    public boolean isHardRestricted() {
        return (this.flags & 4) != 0;
    }

    public boolean isSoftRestricted() {
        return (this.flags & 8) != 0;
    }

    public boolean isRestricted() {
        return isHardRestricted() || isSoftRestricted();
    }

    public boolean isAppOp() {
        return (this.protectionLevel & 64) != 0;
    }

    public boolean isRuntime() {
        return getProtection() == 1;
    }

    private PermissionInfo(android.os.Parcel parcel) {
        super(parcel);
        this.knownCerts = java.util.Collections.emptySet();
        this.protectionLevel = parcel.readInt();
        this.flags = parcel.readInt();
        this.group = parcel.readString8();
        this.backgroundPermission = parcel.readString8();
        this.descriptionRes = parcel.readInt();
        this.requestRes = parcel.readInt();
        this.nonLocalizedDescription = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.knownCerts = sForStringSet.unparcel(parcel);
    }
}
