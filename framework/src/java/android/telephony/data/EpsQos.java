package android.telephony.data;

/* loaded from: classes3.dex */
public final class EpsQos extends android.telephony.data.Qos implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.data.EpsQos> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.EpsQos>() { // from class: android.telephony.data.EpsQos.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.EpsQos createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.EpsQos(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.EpsQos[] newArray(int i) {
            return new android.telephony.data.EpsQos[i];
        }
    };
    int qosClassId;

    public EpsQos(android.telephony.data.Qos.QosBandwidth qosBandwidth, android.telephony.data.Qos.QosBandwidth qosBandwidth2, int i) {
        super(1, qosBandwidth, qosBandwidth2);
        this.qosClassId = i;
    }

    private EpsQos(android.os.Parcel parcel) {
        super(parcel);
        this.qosClassId = parcel.readInt();
    }

    public int getQci() {
        return this.qosClassId;
    }

    public static android.telephony.data.EpsQos createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.data.EpsQos(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(1, parcel, i);
        parcel.writeInt(this.qosClassId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.telephony.data.Qos
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), java.lang.Integer.valueOf(this.qosClassId));
    }

    @Override // android.telephony.data.Qos
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.telephony.data.EpsQos)) {
            return false;
        }
        android.telephony.data.EpsQos epsQos = (android.telephony.data.EpsQos) obj;
        if (this.qosClassId == epsQos.qosClassId && super.equals(epsQos)) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        return "EpsQos { qosClassId=" + this.qosClassId + " downlink=" + this.downlink + " uplink=" + this.uplink + "}";
    }
}
