package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class CellNetworkScanResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.telephony.CellNetworkScanResult> CREATOR = new android.os.Parcelable.Creator<com.android.internal.telephony.CellNetworkScanResult>() { // from class: com.android.internal.telephony.CellNetworkScanResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.telephony.CellNetworkScanResult createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.telephony.CellNetworkScanResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.telephony.CellNetworkScanResult[] newArray(int i) {
            return new com.android.internal.telephony.CellNetworkScanResult[i];
        }
    };
    public static final int STATUS_RADIO_GENERIC_FAILURE = 3;
    public static final int STATUS_RADIO_NOT_AVAILABLE = 2;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNKNOWN_ERROR = 4;
    private final java.util.List<com.android.internal.telephony.OperatorInfo> mOperators;
    private final int mStatus;

    public CellNetworkScanResult(int i, java.util.List<com.android.internal.telephony.OperatorInfo> list) {
        this.mStatus = i;
        this.mOperators = list;
    }

    private CellNetworkScanResult(android.os.Parcel parcel) {
        this.mStatus = parcel.readInt();
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mOperators = new java.util.ArrayList();
            for (int i = 0; i < readInt; i++) {
                this.mOperators.add(com.android.internal.telephony.OperatorInfo.CREATOR.createFromParcel(parcel));
            }
            return;
        }
        this.mOperators = null;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public java.util.List<com.android.internal.telephony.OperatorInfo> getOperators() {
        return this.mOperators;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        if (this.mOperators != null && this.mOperators.size() > 0) {
            parcel.writeInt(this.mOperators.size());
            java.util.Iterator<com.android.internal.telephony.OperatorInfo> it = this.mOperators.iterator();
            while (it.hasNext()) {
                it.next().writeToParcel(parcel, i);
            }
            return;
        }
        parcel.writeInt(0);
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("CellNetworkScanResult: {");
        stringBuffer.append(" status:").append(this.mStatus);
        if (this.mOperators != null) {
            java.util.Iterator<com.android.internal.telephony.OperatorInfo> it = this.mOperators.iterator();
            while (it.hasNext()) {
                stringBuffer.append(" network:").append(it.next());
            }
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }
}
