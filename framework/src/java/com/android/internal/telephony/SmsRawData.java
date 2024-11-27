package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class SmsRawData implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.telephony.SmsRawData> CREATOR = new android.os.Parcelable.Creator<com.android.internal.telephony.SmsRawData>() { // from class: com.android.internal.telephony.SmsRawData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.telephony.SmsRawData createFromParcel(android.os.Parcel parcel) {
            byte[] bArr = new byte[parcel.readInt()];
            parcel.readByteArray(bArr);
            return new com.android.internal.telephony.SmsRawData(bArr);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.telephony.SmsRawData[] newArray(int i) {
            return new com.android.internal.telephony.SmsRawData[i];
        }
    };
    byte[] data;

    public SmsRawData(byte[] bArr) {
        this.data = bArr;
    }

    public byte[] getBytes() {
        return this.data;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.data.length);
        parcel.writeByteArray(this.data);
    }
}
