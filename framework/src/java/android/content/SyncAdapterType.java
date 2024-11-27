package android.content;

/* loaded from: classes.dex */
public class SyncAdapterType implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.SyncAdapterType> CREATOR = new android.os.Parcelable.Creator<android.content.SyncAdapterType>() { // from class: android.content.SyncAdapterType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.SyncAdapterType createFromParcel(android.os.Parcel parcel) {
            return new android.content.SyncAdapterType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.SyncAdapterType[] newArray(int i) {
            return new android.content.SyncAdapterType[i];
        }
    };
    public final java.lang.String accountType;
    private final boolean allowParallelSyncs;
    public final java.lang.String authority;
    private final boolean isAlwaysSyncable;
    public final boolean isKey;
    private final java.lang.String packageName;
    private final java.lang.String settingsActivity;
    private final boolean supportsUploading;
    private final boolean userVisible;

    public SyncAdapterType(java.lang.String str, java.lang.String str2, boolean z, boolean z2) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("the authority must not be empty: " + str);
        }
        if (android.text.TextUtils.isEmpty(str2)) {
            throw new java.lang.IllegalArgumentException("the accountType must not be empty: " + str2);
        }
        this.authority = str;
        this.accountType = str2;
        this.userVisible = z;
        this.supportsUploading = z2;
        this.isAlwaysSyncable = false;
        this.allowParallelSyncs = false;
        this.settingsActivity = null;
        this.isKey = false;
        this.packageName = null;
    }

    public SyncAdapterType(java.lang.String str, java.lang.String str2, boolean z, boolean z2, boolean z3, boolean z4, java.lang.String str3, java.lang.String str4) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("the authority must not be empty: " + str);
        }
        if (android.text.TextUtils.isEmpty(str2)) {
            throw new java.lang.IllegalArgumentException("the accountType must not be empty: " + str2);
        }
        this.authority = str;
        this.accountType = str2;
        this.userVisible = z;
        this.supportsUploading = z2;
        this.isAlwaysSyncable = z3;
        this.allowParallelSyncs = z4;
        this.settingsActivity = str3;
        this.isKey = false;
        this.packageName = str4;
    }

    private SyncAdapterType(java.lang.String str, java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("the authority must not be empty: " + str);
        }
        if (android.text.TextUtils.isEmpty(str2)) {
            throw new java.lang.IllegalArgumentException("the accountType must not be empty: " + str2);
        }
        this.authority = str;
        this.accountType = str2;
        this.userVisible = true;
        this.supportsUploading = true;
        this.isAlwaysSyncable = false;
        this.allowParallelSyncs = false;
        this.settingsActivity = null;
        this.isKey = true;
        this.packageName = null;
    }

    public boolean supportsUploading() {
        if (this.isKey) {
            throw new java.lang.IllegalStateException("this method is not allowed to be called when this is a key");
        }
        return this.supportsUploading;
    }

    public boolean isUserVisible() {
        if (this.isKey) {
            throw new java.lang.IllegalStateException("this method is not allowed to be called when this is a key");
        }
        return this.userVisible;
    }

    public boolean allowParallelSyncs() {
        if (this.isKey) {
            throw new java.lang.IllegalStateException("this method is not allowed to be called when this is a key");
        }
        return this.allowParallelSyncs;
    }

    public boolean isAlwaysSyncable() {
        if (this.isKey) {
            throw new java.lang.IllegalStateException("this method is not allowed to be called when this is a key");
        }
        return this.isAlwaysSyncable;
    }

    public java.lang.String getSettingsActivity() {
        if (this.isKey) {
            throw new java.lang.IllegalStateException("this method is not allowed to be called when this is a key");
        }
        return this.settingsActivity;
    }

    public java.lang.String getPackageName() {
        return this.packageName;
    }

    public static android.content.SyncAdapterType newKey(java.lang.String str, java.lang.String str2) {
        return new android.content.SyncAdapterType(str, str2);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.content.SyncAdapterType)) {
            return false;
        }
        android.content.SyncAdapterType syncAdapterType = (android.content.SyncAdapterType) obj;
        return this.authority.equals(syncAdapterType.authority) && this.accountType.equals(syncAdapterType.accountType);
    }

    public int hashCode() {
        return ((527 + this.authority.hashCode()) * 31) + this.accountType.hashCode();
    }

    public java.lang.String toString() {
        if (this.isKey) {
            return "SyncAdapterType Key {name=" + this.authority + ", type=" + this.accountType + "}";
        }
        return "SyncAdapterType {name=" + this.authority + ", type=" + this.accountType + ", userVisible=" + this.userVisible + ", supportsUploading=" + this.supportsUploading + ", isAlwaysSyncable=" + this.isAlwaysSyncable + ", allowParallelSyncs=" + this.allowParallelSyncs + ", settingsActivity=" + this.settingsActivity + ", packageName=" + this.packageName + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.isKey) {
            throw new java.lang.IllegalStateException("keys aren't parcelable");
        }
        parcel.writeString(this.authority);
        parcel.writeString(this.accountType);
        parcel.writeInt(this.userVisible ? 1 : 0);
        parcel.writeInt(this.supportsUploading ? 1 : 0);
        parcel.writeInt(this.isAlwaysSyncable ? 1 : 0);
        parcel.writeInt(this.allowParallelSyncs ? 1 : 0);
        parcel.writeString(this.settingsActivity);
        parcel.writeString(this.packageName);
    }

    public SyncAdapterType(android.os.Parcel parcel) {
        this(parcel.readString(), parcel.readString(), parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt() != 0, parcel.readString(), parcel.readString());
    }
}
