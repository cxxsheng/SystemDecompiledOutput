package com.android.ims;

/* loaded from: classes4.dex */
public class RcsTypeIdPair implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.RcsTypeIdPair> CREATOR = new android.os.Parcelable.Creator<com.android.ims.RcsTypeIdPair>() { // from class: com.android.ims.RcsTypeIdPair.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.RcsTypeIdPair createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.RcsTypeIdPair(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.RcsTypeIdPair[] newArray(int i) {
            return new com.android.ims.RcsTypeIdPair[i];
        }
    };
    private int mId;
    private int mType;

    public RcsTypeIdPair(int i, int i2) {
        this.mType = i;
        this.mId = i2;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int i) {
        this.mType = i;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        this.mId = i;
    }

    public RcsTypeIdPair(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        this.mId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mId);
    }
}
