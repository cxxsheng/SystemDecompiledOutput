package android.net.networkstack.aidl;

/* loaded from: classes.dex */
public class NetworkMonitorParameters implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.networkstack.aidl.NetworkMonitorParameters> CREATOR = new android.os.Parcelable.Creator<android.net.networkstack.aidl.NetworkMonitorParameters>() { // from class: android.net.networkstack.aidl.NetworkMonitorParameters.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.networkstack.aidl.NetworkMonitorParameters createFromParcel(android.os.Parcel parcel) {
            android.net.networkstack.aidl.NetworkMonitorParameters networkMonitorParameters = new android.net.networkstack.aidl.NetworkMonitorParameters();
            networkMonitorParameters.readFromParcel(parcel);
            return networkMonitorParameters;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.networkstack.aidl.NetworkMonitorParameters[] newArray(int i) {
            return new android.net.networkstack.aidl.NetworkMonitorParameters[i];
        }
    };
    public android.net.LinkProperties linkProperties;
    public android.net.NetworkAgentConfig networkAgentConfig;
    public android.net.NetworkCapabilities networkCapabilities;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.networkAgentConfig, i);
        parcel.writeTypedObject(this.networkCapabilities, i);
        parcel.writeTypedObject(this.linkProperties, i);
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
            this.networkAgentConfig = (android.net.NetworkAgentConfig) parcel.readTypedObject(android.net.NetworkAgentConfig.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.networkCapabilities = (android.net.NetworkCapabilities) parcel.readTypedObject(android.net.NetworkCapabilities.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.linkProperties = (android.net.LinkProperties) parcel.readTypedObject(android.net.LinkProperties.CREATOR);
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
        stringJoiner.add("networkAgentConfig: " + java.util.Objects.toString(this.networkAgentConfig));
        stringJoiner.add("networkCapabilities: " + java.util.Objects.toString(this.networkCapabilities));
        stringJoiner.add("linkProperties: " + java.util.Objects.toString(this.linkProperties));
        return "NetworkMonitorParameters" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.net.networkstack.aidl.NetworkMonitorParameters)) {
            return false;
        }
        android.net.networkstack.aidl.NetworkMonitorParameters networkMonitorParameters = (android.net.networkstack.aidl.NetworkMonitorParameters) obj;
        if (java.util.Objects.deepEquals(this.networkAgentConfig, networkMonitorParameters.networkAgentConfig) && java.util.Objects.deepEquals(this.networkCapabilities, networkMonitorParameters.networkCapabilities) && java.util.Objects.deepEquals(this.linkProperties, networkMonitorParameters.linkProperties)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.networkAgentConfig, this.networkCapabilities, this.linkProperties).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.networkAgentConfig) | 0 | describeContents(this.networkCapabilities) | describeContents(this.linkProperties);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
