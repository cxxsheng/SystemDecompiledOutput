package android.net;

/* loaded from: classes.dex */
public class DataStallReportParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.DataStallReportParcelable> CREATOR = new android.os.Parcelable.Creator<android.net.DataStallReportParcelable>() { // from class: android.net.DataStallReportParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.DataStallReportParcelable createFromParcel(android.os.Parcel parcel) {
            android.net.DataStallReportParcelable dataStallReportParcelable = new android.net.DataStallReportParcelable();
            dataStallReportParcelable.readFromParcel(parcel);
            return dataStallReportParcelable;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.DataStallReportParcelable[] newArray(int i) {
            return new android.net.DataStallReportParcelable[i];
        }
    };
    public long timestampMillis = 0;
    public int detectionMethod = 1;
    public int tcpPacketFailRate = 2;
    public int tcpMetricsCollectionPeriodMillis = 3;
    public int dnsConsecutiveTimeouts = 4;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.timestampMillis);
        parcel.writeInt(this.detectionMethod);
        parcel.writeInt(this.tcpPacketFailRate);
        parcel.writeInt(this.tcpMetricsCollectionPeriodMillis);
        parcel.writeInt(this.dnsConsecutiveTimeouts);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timestampMillis = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.detectionMethod = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.tcpPacketFailRate = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.tcpMetricsCollectionPeriodMillis = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.dnsConsecutiveTimeouts = parcel.readInt();
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("timestampMillis: " + this.timestampMillis);
        stringJoiner.add("detectionMethod: " + this.detectionMethod);
        stringJoiner.add("tcpPacketFailRate: " + this.tcpPacketFailRate);
        stringJoiner.add("tcpMetricsCollectionPeriodMillis: " + this.tcpMetricsCollectionPeriodMillis);
        stringJoiner.add("dnsConsecutiveTimeouts: " + this.dnsConsecutiveTimeouts);
        return "DataStallReportParcelable" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
