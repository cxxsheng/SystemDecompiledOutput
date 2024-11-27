package android.telephony.data;

/* loaded from: classes3.dex */
public final class QosBearerFilter implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.data.QosBearerFilter> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.QosBearerFilter>() { // from class: android.telephony.data.QosBearerFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.QosBearerFilter createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.QosBearerFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.QosBearerFilter[] newArray(int i) {
            return new android.telephony.data.QosBearerFilter[i];
        }
    };
    public static final int QOS_FILTER_DIRECTION_BIDIRECTIONAL = 2;
    public static final int QOS_FILTER_DIRECTION_DOWNLINK = 0;
    public static final int QOS_FILTER_DIRECTION_UPLINK = 1;
    public static final int QOS_MAX_PORT = 65535;
    public static final int QOS_MIN_PORT = 20;
    public static final int QOS_PROTOCOL_AH = 51;
    public static final int QOS_PROTOCOL_ESP = 50;
    public static final int QOS_PROTOCOL_TCP = 6;
    public static final int QOS_PROTOCOL_UDP = 17;
    public static final int QOS_PROTOCOL_UNSPECIFIED = -1;
    private int filterDirection;
    private long flowLabel;
    private java.util.List<android.net.LinkAddress> localAddresses;
    private android.telephony.data.QosBearerFilter.PortRange localPort;
    private int precedence;
    private int protocol;
    private java.util.List<android.net.LinkAddress> remoteAddresses;
    private android.telephony.data.QosBearerFilter.PortRange remotePort;
    private long securityParameterIndex;
    private int typeOfServiceMask;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface QosBearerFilterDirection {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface QosProtocol {
    }

    public QosBearerFilter(java.util.List<android.net.LinkAddress> list, java.util.List<android.net.LinkAddress> list2, android.telephony.data.QosBearerFilter.PortRange portRange, android.telephony.data.QosBearerFilter.PortRange portRange2, int i, int i2, long j, long j2, int i3, int i4) {
        this.localAddresses = new java.util.ArrayList();
        this.localAddresses.addAll(list);
        this.remoteAddresses = new java.util.ArrayList();
        this.remoteAddresses.addAll(list2);
        this.localPort = portRange;
        this.remotePort = portRange2;
        this.protocol = i;
        this.typeOfServiceMask = i2;
        this.flowLabel = j;
        this.securityParameterIndex = j2;
        this.filterDirection = i3;
        this.precedence = i4;
    }

    public java.util.List<android.net.LinkAddress> getLocalAddresses() {
        return this.localAddresses;
    }

    public java.util.List<android.net.LinkAddress> getRemoteAddresses() {
        return this.remoteAddresses;
    }

    public android.telephony.data.QosBearerFilter.PortRange getLocalPortRange() {
        return this.localPort;
    }

    public android.telephony.data.QosBearerFilter.PortRange getRemotePortRange() {
        return this.remotePort;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    public int getProtocol() {
        return this.protocol;
    }

    public static class PortRange implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.telephony.data.QosBearerFilter.PortRange> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.QosBearerFilter.PortRange>() { // from class: android.telephony.data.QosBearerFilter.PortRange.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.data.QosBearerFilter.PortRange createFromParcel(android.os.Parcel parcel) {
                return new android.telephony.data.QosBearerFilter.PortRange(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.data.QosBearerFilter.PortRange[] newArray(int i) {
                return new android.telephony.data.QosBearerFilter.PortRange[i];
            }
        };
        int end;
        int start;

        private PortRange(android.os.Parcel parcel) {
            this.start = parcel.readInt();
            this.end = parcel.readInt();
        }

        public PortRange(int i, int i2) {
            this.start = i;
            this.end = i2;
        }

        public int getStart() {
            return this.start;
        }

        public int getEnd() {
            return this.end;
        }

        public boolean isValid() {
            return this.start >= 20 && this.start <= 65535 && this.end >= 20 && this.end <= 65535 && this.start <= this.end;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.start);
            parcel.writeInt(this.end);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String toString() {
            return "PortRange { start=" + this.start + " end=" + this.end + "}";
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof android.telephony.data.QosBearerFilter.PortRange)) {
                return false;
            }
            android.telephony.data.QosBearerFilter.PortRange portRange = (android.telephony.data.QosBearerFilter.PortRange) obj;
            if (this.start == portRange.start && this.end == portRange.end) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.start), java.lang.Integer.valueOf(this.end));
        }
    }

    public java.lang.String toString() {
        return "QosBearerFilter { localAddresses=" + this.localAddresses + " remoteAddresses=" + this.remoteAddresses + " localPort=" + this.localPort + " remotePort=" + this.remotePort + " protocol=" + this.protocol + " typeOfServiceMask=" + this.typeOfServiceMask + " flowLabel=" + this.flowLabel + " securityParameterIndex=" + this.securityParameterIndex + " filterDirection=" + this.filterDirection + " precedence=" + this.precedence + "}";
    }

    public int hashCode() {
        return java.util.Objects.hash(this.localAddresses, this.remoteAddresses, this.localPort, this.remotePort, java.lang.Integer.valueOf(this.protocol), java.lang.Integer.valueOf(this.typeOfServiceMask), java.lang.Long.valueOf(this.flowLabel), java.lang.Long.valueOf(this.securityParameterIndex), java.lang.Integer.valueOf(this.filterDirection), java.lang.Integer.valueOf(this.precedence));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.telephony.data.QosBearerFilter)) {
            return false;
        }
        android.telephony.data.QosBearerFilter qosBearerFilter = (android.telephony.data.QosBearerFilter) obj;
        if (this.localAddresses.size() == qosBearerFilter.localAddresses.size() && this.localAddresses.containsAll(qosBearerFilter.localAddresses) && this.remoteAddresses.size() == qosBearerFilter.remoteAddresses.size() && this.remoteAddresses.containsAll(qosBearerFilter.remoteAddresses) && java.util.Objects.equals(this.localPort, qosBearerFilter.localPort) && java.util.Objects.equals(this.remotePort, qosBearerFilter.remotePort) && this.protocol == qosBearerFilter.protocol && this.typeOfServiceMask == qosBearerFilter.typeOfServiceMask && this.flowLabel == qosBearerFilter.flowLabel && this.securityParameterIndex == qosBearerFilter.securityParameterIndex && this.filterDirection == qosBearerFilter.filterDirection && this.precedence == qosBearerFilter.precedence) {
            return true;
        }
        return false;
    }

    private QosBearerFilter(android.os.Parcel parcel) {
        this.localAddresses = new java.util.ArrayList();
        parcel.readList(this.localAddresses, android.net.LinkAddress.class.getClassLoader(), android.net.LinkAddress.class);
        this.remoteAddresses = new java.util.ArrayList();
        parcel.readList(this.remoteAddresses, android.net.LinkAddress.class.getClassLoader(), android.net.LinkAddress.class);
        this.localPort = (android.telephony.data.QosBearerFilter.PortRange) parcel.readParcelable(android.telephony.data.QosBearerFilter.PortRange.class.getClassLoader(), android.telephony.data.QosBearerFilter.PortRange.class);
        this.remotePort = (android.telephony.data.QosBearerFilter.PortRange) parcel.readParcelable(android.telephony.data.QosBearerFilter.PortRange.class.getClassLoader(), android.telephony.data.QosBearerFilter.PortRange.class);
        this.protocol = parcel.readInt();
        this.typeOfServiceMask = parcel.readInt();
        this.flowLabel = parcel.readLong();
        this.securityParameterIndex = parcel.readLong();
        this.filterDirection = parcel.readInt();
        this.precedence = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeList(this.localAddresses);
        parcel.writeList(this.remoteAddresses);
        parcel.writeParcelable(this.localPort, i);
        parcel.writeParcelable(this.remotePort, i);
        parcel.writeInt(this.protocol);
        parcel.writeInt(this.typeOfServiceMask);
        parcel.writeLong(this.flowLabel);
        parcel.writeLong(this.securityParameterIndex);
        parcel.writeInt(this.filterDirection);
        parcel.writeInt(this.precedence);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
