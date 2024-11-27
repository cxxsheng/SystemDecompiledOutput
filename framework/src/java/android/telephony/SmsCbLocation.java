package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SmsCbLocation implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.SmsCbLocation> CREATOR = new android.os.Parcelable.Creator<android.telephony.SmsCbLocation>() { // from class: android.telephony.SmsCbLocation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SmsCbLocation createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.SmsCbLocation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SmsCbLocation[] newArray(int i) {
            return new android.telephony.SmsCbLocation[i];
        }
    };
    private final int mCid;
    private final int mLac;
    private final java.lang.String mPlmn;

    public SmsCbLocation() {
        this.mPlmn = "";
        this.mLac = -1;
        this.mCid = -1;
    }

    public SmsCbLocation(java.lang.String str) {
        this.mPlmn = str;
        this.mLac = -1;
        this.mCid = -1;
    }

    public SmsCbLocation(java.lang.String str, int i, int i2) {
        this.mPlmn = str;
        this.mLac = i;
        this.mCid = i2;
    }

    public SmsCbLocation(android.os.Parcel parcel) {
        this.mPlmn = parcel.readString();
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
    }

    public java.lang.String getPlmn() {
        return this.mPlmn;
    }

    public int getLac() {
        return this.mLac;
    }

    public int getCid() {
        return this.mCid;
    }

    public int hashCode() {
        return (((this.mPlmn.hashCode() * 31) + this.mLac) * 31) + this.mCid;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof android.telephony.SmsCbLocation)) {
            return false;
        }
        android.telephony.SmsCbLocation smsCbLocation = (android.telephony.SmsCbLocation) obj;
        if (this.mPlmn.equals(smsCbLocation.mPlmn) && this.mLac == smsCbLocation.mLac && this.mCid == smsCbLocation.mCid) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        return '[' + this.mPlmn + ',' + this.mLac + ',' + this.mCid + ']';
    }

    public boolean isInLocationArea(android.telephony.SmsCbLocation smsCbLocation) {
        if (this.mCid != -1 && this.mCid != smsCbLocation.mCid) {
            return false;
        }
        if (this.mLac == -1 || this.mLac == smsCbLocation.mLac) {
            return this.mPlmn.equals(smsCbLocation.mPlmn);
        }
        return false;
    }

    public boolean isInLocationArea(java.lang.String str, int i, int i2) {
        if (!this.mPlmn.equals(str)) {
            return false;
        }
        if (this.mLac == -1 || this.mLac == i) {
            return this.mCid == -1 || this.mCid == i2;
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPlmn);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
