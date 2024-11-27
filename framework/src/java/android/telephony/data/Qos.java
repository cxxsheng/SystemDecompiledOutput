package android.telephony.data;

/* loaded from: classes3.dex */
public abstract class Qos implements android.os.Parcelable {
    static final int QOS_TYPE_EPS = 1;
    static final int QOS_TYPE_NR = 2;
    final android.telephony.data.Qos.QosBandwidth downlink;
    final int type;
    final android.telephony.data.Qos.QosBandwidth uplink;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface QosType {
    }

    Qos(int i, android.telephony.data.Qos.QosBandwidth qosBandwidth, android.telephony.data.Qos.QosBandwidth qosBandwidth2) {
        this.type = i;
        this.downlink = qosBandwidth;
        this.uplink = qosBandwidth2;
    }

    public android.telephony.data.Qos.QosBandwidth getDownlinkBandwidth() {
        return this.downlink;
    }

    public android.telephony.data.Qos.QosBandwidth getUplinkBandwidth() {
        return this.uplink;
    }

    public static class QosBandwidth implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.telephony.data.Qos.QosBandwidth> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.Qos.QosBandwidth>() { // from class: android.telephony.data.Qos.QosBandwidth.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.data.Qos.QosBandwidth createFromParcel(android.os.Parcel parcel) {
                return new android.telephony.data.Qos.QosBandwidth(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.data.Qos.QosBandwidth[] newArray(int i) {
                return new android.telephony.data.Qos.QosBandwidth[i];
            }
        };
        int guaranteedBitrateKbps;
        int maxBitrateKbps;

        public QosBandwidth(int i, int i2) {
            this.maxBitrateKbps = i;
            this.guaranteedBitrateKbps = i2;
        }

        private QosBandwidth(android.os.Parcel parcel) {
            this.maxBitrateKbps = parcel.readInt();
            this.guaranteedBitrateKbps = parcel.readInt();
        }

        public int getMaxBitrateKbps() {
            return this.maxBitrateKbps;
        }

        public int getGuaranteedBitrateKbps() {
            return this.guaranteedBitrateKbps;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.maxBitrateKbps);
            parcel.writeInt(this.guaranteedBitrateKbps);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.maxBitrateKbps), java.lang.Integer.valueOf(this.guaranteedBitrateKbps));
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof android.telephony.data.Qos.QosBandwidth)) {
                return false;
            }
            android.telephony.data.Qos.QosBandwidth qosBandwidth = (android.telephony.data.Qos.QosBandwidth) obj;
            if (this.maxBitrateKbps == qosBandwidth.maxBitrateKbps && this.guaranteedBitrateKbps == qosBandwidth.guaranteedBitrateKbps) {
                return true;
            }
            return false;
        }

        public java.lang.String toString() {
            return "Bandwidth { maxBitrateKbps=" + this.maxBitrateKbps + " guaranteedBitrateKbps=" + this.guaranteedBitrateKbps + "}";
        }
    }

    protected Qos(android.os.Parcel parcel) {
        this.type = parcel.readInt();
        this.downlink = (android.telephony.data.Qos.QosBandwidth) parcel.readParcelable(android.telephony.data.Qos.QosBandwidth.class.getClassLoader(), android.telephony.data.Qos.QosBandwidth.class);
        this.uplink = (android.telephony.data.Qos.QosBandwidth) parcel.readParcelable(android.telephony.data.Qos.QosBandwidth.class.getClassLoader(), android.telephony.data.Qos.QosBandwidth.class);
    }

    public void writeToParcel(int i, android.os.Parcel parcel, int i2) {
        parcel.writeInt(i);
        parcel.writeParcelable(this.downlink, i2);
        parcel.writeParcelable(this.uplink, i2);
    }

    public int getType() {
        return this.type;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.downlink, this.uplink);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        android.telephony.data.Qos qos = (android.telephony.data.Qos) obj;
        return this.type == qos.type && this.downlink.equals(qos.downlink) && this.uplink.equals(qos.uplink);
    }
}
