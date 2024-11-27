package android.flags;

/* loaded from: classes.dex */
public final class SyncableFlag implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.flags.SyncableFlag> CREATOR = new android.os.Parcelable.Creator<android.flags.SyncableFlag>() { // from class: android.flags.SyncableFlag.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.flags.SyncableFlag createFromParcel(android.os.Parcel parcel) {
            return new android.flags.SyncableFlag(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readBoolean(), parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.flags.SyncableFlag[] newArray(int i) {
            return new android.flags.SyncableFlag[i];
        }
    };
    private final boolean mDynamic;
    private final java.lang.String mName;
    private final java.lang.String mNamespace;
    private final boolean mOverridden;
    private final java.lang.String mValue;

    public SyncableFlag(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) {
        this(str, str2, str3, z, false);
    }

    public SyncableFlag(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, boolean z2) {
        this.mNamespace = str;
        this.mName = str2;
        this.mValue = str3;
        this.mDynamic = z;
        this.mOverridden = z2;
    }

    public java.lang.String getNamespace() {
        return this.mNamespace;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String getValue() {
        return this.mValue;
    }

    public boolean isDynamic() {
        return this.mDynamic;
    }

    public boolean isOverridden() {
        return this.mOverridden;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mNamespace);
        parcel.writeString(this.mName);
        parcel.writeString(this.mValue);
        parcel.writeBoolean(this.mDynamic);
        parcel.writeBoolean(this.mOverridden);
    }

    public java.lang.String toString() {
        return getNamespace() + android.media.MediaMetrics.SEPARATOR + getName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + getValue() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
