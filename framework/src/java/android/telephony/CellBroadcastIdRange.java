package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class CellBroadcastIdRange implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.CellBroadcastIdRange> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellBroadcastIdRange>() { // from class: android.telephony.CellBroadcastIdRange.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellBroadcastIdRange createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CellBroadcastIdRange(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellBroadcastIdRange[] newArray(int i) {
            return new android.telephony.CellBroadcastIdRange[i];
        }
    };
    private int mEndId;
    private boolean mIsEnabled;
    private int mStartId;
    private int mType;

    public CellBroadcastIdRange(int i, int i2, int i3, boolean z) throws java.lang.IllegalArgumentException {
        if (i < 0 || i2 < 0 || i > 65535 || i2 > 65535) {
            throw new java.lang.IllegalArgumentException("invalid id");
        }
        if (i2 < i) {
            throw new java.lang.IllegalArgumentException("endId must be greater than or equal to startId");
        }
        this.mStartId = i;
        this.mEndId = i2;
        this.mType = i3;
        this.mIsEnabled = z;
    }

    public int getStartId() {
        return this.mStartId;
    }

    public int getEndId() {
        return this.mEndId;
    }

    public int getType() {
        return this.mType;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStartId);
        parcel.writeInt(this.mEndId);
        parcel.writeInt(this.mType);
        parcel.writeBoolean(this.mIsEnabled);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mStartId), java.lang.Integer.valueOf(this.mEndId), java.lang.Integer.valueOf(this.mType), java.lang.Boolean.valueOf(this.mIsEnabled));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.CellBroadcastIdRange)) {
            return false;
        }
        android.telephony.CellBroadcastIdRange cellBroadcastIdRange = (android.telephony.CellBroadcastIdRange) obj;
        return this.mStartId == cellBroadcastIdRange.mStartId && this.mEndId == cellBroadcastIdRange.mEndId && this.mType == cellBroadcastIdRange.mType && this.mIsEnabled == cellBroadcastIdRange.mIsEnabled;
    }

    public java.lang.String toString() {
        return "CellBroadcastIdRange[" + this.mStartId + ", " + this.mEndId + ", " + this.mType + ", " + this.mIsEnabled + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
