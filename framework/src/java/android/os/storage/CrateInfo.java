package android.os.storage;

/* loaded from: classes3.dex */
public final class CrateInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.storage.CrateInfo> CREATOR = new android.os.Parcelable.Creator<android.os.storage.CrateInfo>() { // from class: android.os.storage.CrateInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.storage.CrateInfo createFromParcel(android.os.Parcel parcel) {
            android.os.storage.CrateInfo crateInfo = new android.os.storage.CrateInfo();
            crateInfo.readFromParcel(parcel);
            return crateInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.storage.CrateInfo[] newArray(int i) {
            return new android.os.storage.CrateInfo[i];
        }
    };
    private static final java.lang.String TAG = "CrateInfo";
    private long mExpiration;
    private java.lang.String mId;
    private java.lang.CharSequence mLabel;
    private java.lang.String mPackageName;
    private int mUid;

    private CrateInfo() {
        this.mExpiration = 0L;
    }

    public CrateInfo(java.lang.CharSequence charSequence, long j) {
        com.android.internal.util.Preconditions.checkStringNotEmpty(charSequence, "Label should not be either null or empty string");
        com.android.internal.util.Preconditions.checkArgumentNonnegative(j, "Expiration should be non negative number");
        this.mLabel = charSequence;
        this.mExpiration = j;
    }

    public CrateInfo(java.lang.CharSequence charSequence) {
        this(charSequence, 0L);
    }

    public java.lang.CharSequence getLabel() {
        if (android.text.TextUtils.isEmpty(this.mLabel)) {
            return this.mId;
        }
        return this.mLabel;
    }

    public long getExpirationMillis() {
        return this.mExpiration;
    }

    public void setExpiration(long j) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(j);
        this.mExpiration = j;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof android.os.storage.CrateInfo) {
            android.os.storage.CrateInfo crateInfo = (android.os.storage.CrateInfo) obj;
            if (!android.text.TextUtils.isEmpty(this.mId) && android.text.TextUtils.equals(this.mId, crateInfo.mId)) {
                return true;
            }
        }
        return super.equals(obj);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeCharSequence(this.mLabel);
        parcel.writeLong(this.mExpiration);
        parcel.writeInt(this.mUid);
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mId);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        if (parcel == null) {
            return;
        }
        this.mLabel = parcel.readCharSequence();
        this.mExpiration = parcel.readLong();
        this.mUid = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mId = parcel.readString();
    }

    public static android.os.storage.CrateInfo copyFrom(int i, java.lang.String str, java.lang.String str2) {
        if (!android.os.UserHandle.isApp(i) || android.text.TextUtils.isEmpty(str) || android.text.TextUtils.isEmpty(str2)) {
            return null;
        }
        android.os.storage.CrateInfo crateInfo = new android.os.storage.CrateInfo(str2, 0L);
        crateInfo.mUid = i;
        crateInfo.mPackageName = str;
        crateInfo.mId = str2;
        return crateInfo;
    }
}
