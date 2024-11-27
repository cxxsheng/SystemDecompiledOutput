package com.android.internal.telephony;

/* loaded from: classes5.dex */
public final class NetworkScanResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.telephony.NetworkScanResult> CREATOR = new android.os.Parcelable.Creator<com.android.internal.telephony.NetworkScanResult>() { // from class: com.android.internal.telephony.NetworkScanResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.telephony.NetworkScanResult createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.telephony.NetworkScanResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.telephony.NetworkScanResult[] newArray(int i) {
            return new com.android.internal.telephony.NetworkScanResult[i];
        }
    };
    public static final int SCAN_STATUS_COMPLETE = 2;
    public static final int SCAN_STATUS_PARTIAL = 1;
    public java.util.List<android.telephony.CellInfo> networkInfos;
    public int scanError;
    public int scanStatus;

    public NetworkScanResult(int i, int i2, java.util.List<android.telephony.CellInfo> list) {
        this.scanStatus = i;
        this.scanError = i2;
        this.networkInfos = list;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.scanStatus);
        parcel.writeInt(this.scanError);
        parcel.writeParcelableList(this.networkInfos, i);
    }

    private NetworkScanResult(android.os.Parcel parcel) {
        this.scanStatus = parcel.readInt();
        this.scanError = parcel.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readParcelableList(arrayList, java.lang.Object.class.getClassLoader(), android.telephony.CellInfo.class);
        this.networkInfos = arrayList;
    }

    public boolean equals(java.lang.Object obj) {
        try {
            com.android.internal.telephony.NetworkScanResult networkScanResult = (com.android.internal.telephony.NetworkScanResult) obj;
            return obj != null && this.scanStatus == networkScanResult.scanStatus && this.scanError == networkScanResult.scanError && this.networkInfos.equals(networkScanResult.networkInfos);
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    public java.lang.String toString() {
        return "{" + ("scanStatus=" + this.scanStatus) + (", scanError=" + this.scanError) + (", networkInfos=" + this.networkInfos) + "}";
    }

    public int hashCode() {
        return (this.scanStatus * 31) + (this.scanError * 23) + (java.util.Objects.hashCode(this.networkInfos) * 37);
    }
}
