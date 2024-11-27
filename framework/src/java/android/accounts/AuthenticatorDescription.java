package android.accounts;

/* loaded from: classes.dex */
public class AuthenticatorDescription implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.accounts.AuthenticatorDescription> CREATOR = new android.os.Parcelable.Creator<android.accounts.AuthenticatorDescription>() { // from class: android.accounts.AuthenticatorDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accounts.AuthenticatorDescription createFromParcel(android.os.Parcel parcel) {
            return new android.accounts.AuthenticatorDescription(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accounts.AuthenticatorDescription[] newArray(int i) {
            return new android.accounts.AuthenticatorDescription[i];
        }
    };
    public final int accountPreferencesId;
    public final boolean customTokens;
    public final int iconId;
    public final int labelId;
    public final java.lang.String packageName;
    public final int smallIconId;
    public final java.lang.String type;

    public AuthenticatorDescription(java.lang.String str, java.lang.String str2, int i, int i2, int i3, int i4, boolean z) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("type cannot be null");
        }
        if (str2 == null) {
            throw new java.lang.IllegalArgumentException("packageName cannot be null");
        }
        this.type = str;
        this.packageName = str2;
        this.labelId = i;
        this.iconId = i2;
        this.smallIconId = i3;
        this.accountPreferencesId = i4;
        this.customTokens = z;
    }

    public AuthenticatorDescription(java.lang.String str, java.lang.String str2, int i, int i2, int i3, int i4) {
        this(str, str2, i, i2, i3, i4, false);
    }

    public static android.accounts.AuthenticatorDescription newKey(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("type cannot be null");
        }
        return new android.accounts.AuthenticatorDescription(str);
    }

    private AuthenticatorDescription(java.lang.String str) {
        this.type = str;
        this.packageName = null;
        this.labelId = 0;
        this.iconId = 0;
        this.smallIconId = 0;
        this.accountPreferencesId = 0;
        this.customTokens = false;
    }

    private AuthenticatorDescription(android.os.Parcel parcel) {
        this.type = parcel.readString();
        this.packageName = parcel.readString();
        this.labelId = parcel.readInt();
        this.iconId = parcel.readInt();
        this.smallIconId = parcel.readInt();
        this.accountPreferencesId = parcel.readInt();
        this.customTokens = parcel.readByte() == 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return this.type.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof android.accounts.AuthenticatorDescription) {
            return this.type.equals(((android.accounts.AuthenticatorDescription) obj).type);
        }
        return false;
    }

    public java.lang.String toString() {
        return "AuthenticatorDescription {type=" + this.type + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.type);
        parcel.writeString(this.packageName);
        parcel.writeInt(this.labelId);
        parcel.writeInt(this.iconId);
        parcel.writeInt(this.smallIconId);
        parcel.writeInt(this.accountPreferencesId);
        parcel.writeByte(this.customTokens ? (byte) 1 : (byte) 0);
    }
}
