package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class CallInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.telephony.CallInfo> CREATOR = new android.os.Parcelable.Creator<com.android.internal.telephony.CallInfo>() { // from class: com.android.internal.telephony.CallInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.telephony.CallInfo createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.telephony.CallInfo(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.telephony.CallInfo[] newArray(int i) {
            return new com.android.internal.telephony.CallInfo[i];
        }
    };
    private java.lang.String handle;

    public CallInfo(java.lang.String str) {
        this.handle = str;
    }

    public java.lang.String getHandle() {
        return this.handle;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.handle);
    }
}
