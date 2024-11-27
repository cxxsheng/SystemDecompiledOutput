package android.telephony.data;

/* loaded from: classes3.dex */
public final class NrQos extends android.telephony.data.Qos implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.data.NrQos> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.NrQos>() { // from class: android.telephony.data.NrQos.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.NrQos createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.NrQos(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.NrQos[] newArray(int i) {
            return new android.telephony.data.NrQos[i];
        }
    };
    int averagingWindowMs;
    int fiveQi;
    int qosFlowId;

    public NrQos(android.telephony.data.Qos.QosBandwidth qosBandwidth, android.telephony.data.Qos.QosBandwidth qosBandwidth2, int i, int i2, int i3) {
        super(2, qosBandwidth, qosBandwidth2);
        this.qosFlowId = i;
        this.fiveQi = i2;
        this.averagingWindowMs = i3;
    }

    private NrQos(android.os.Parcel parcel) {
        super(parcel);
        this.qosFlowId = parcel.readInt();
        this.fiveQi = parcel.readInt();
        this.averagingWindowMs = parcel.readInt();
    }

    public static android.telephony.data.NrQos createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.data.NrQos(parcel);
    }

    public int get5Qi() {
        return this.fiveQi;
    }

    public int getQfi() {
        return this.qosFlowId;
    }

    public int getAveragingWindow() {
        return this.averagingWindowMs;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(2, parcel, i);
        parcel.writeInt(this.qosFlowId);
        parcel.writeInt(this.fiveQi);
        parcel.writeInt(this.averagingWindowMs);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.telephony.data.Qos
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), java.lang.Integer.valueOf(this.qosFlowId), java.lang.Integer.valueOf(this.fiveQi), java.lang.Integer.valueOf(this.averagingWindowMs));
    }

    @Override // android.telephony.data.Qos
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.telephony.data.NrQos)) {
            return false;
        }
        android.telephony.data.NrQos nrQos = (android.telephony.data.NrQos) obj;
        if (!super.equals(nrQos)) {
            return false;
        }
        if (this.qosFlowId == nrQos.qosFlowId && this.fiveQi == nrQos.fiveQi && this.averagingWindowMs == nrQos.averagingWindowMs) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        return "NrQos { fiveQi=" + this.fiveQi + " downlink=" + this.downlink + " uplink=" + this.uplink + " qosFlowId=" + this.qosFlowId + " averagingWindowMs=" + this.averagingWindowMs + "}";
    }
}
