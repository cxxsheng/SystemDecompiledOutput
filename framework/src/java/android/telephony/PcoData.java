package android.telephony;

/* loaded from: classes3.dex */
public class PcoData implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.PcoData> CREATOR = new android.os.Parcelable.Creator() { // from class: android.telephony.PcoData.1
        @Override // android.os.Parcelable.Creator
        public android.telephony.PcoData createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.PcoData(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public android.telephony.PcoData[] newArray(int i) {
            return new android.telephony.PcoData[i];
        }
    };
    public final java.lang.String bearerProto;
    public final int cid;
    public final byte[] contents;
    public final int pcoId;

    public PcoData(int i, java.lang.String str, int i2, byte[] bArr) {
        this.cid = i;
        this.bearerProto = str;
        this.pcoId = i2;
        this.contents = bArr;
    }

    public PcoData(android.os.Parcel parcel) {
        this.cid = parcel.readInt();
        this.bearerProto = parcel.readString();
        this.pcoId = parcel.readInt();
        this.contents = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.cid);
        parcel.writeString(this.bearerProto);
        parcel.writeInt(this.pcoId);
        parcel.writeByteArray(this.contents);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "PcoData(" + this.cid + ", " + this.bearerProto + ", " + this.pcoId + ", contents[" + this.contents.length + "])";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.PcoData pcoData = (android.telephony.PcoData) obj;
        if (this.cid == pcoData.cid && this.pcoId == pcoData.pcoId && java.util.Objects.equals(this.bearerProto, pcoData.bearerProto) && java.util.Arrays.equals(this.contents, pcoData.contents)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (java.util.Objects.hash(java.lang.Integer.valueOf(this.cid), this.bearerProto, java.lang.Integer.valueOf(this.pcoId)) * 31) + java.util.Arrays.hashCode(this.contents);
    }
}
