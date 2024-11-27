package android.net;

/* loaded from: classes.dex */
public class InitialConfigurationParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.InitialConfigurationParcelable> CREATOR = new android.os.Parcelable.Creator<android.net.InitialConfigurationParcelable>() { // from class: android.net.InitialConfigurationParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.InitialConfigurationParcelable createFromParcel(android.os.Parcel parcel) {
            android.net.InitialConfigurationParcelable initialConfigurationParcelable = new android.net.InitialConfigurationParcelable();
            initialConfigurationParcelable.readFromParcel(parcel);
            return initialConfigurationParcelable;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.InitialConfigurationParcelable[] newArray(int i) {
            return new android.net.InitialConfigurationParcelable[i];
        }
    };
    public android.net.IpPrefix[] directlyConnectedRoutes;
    public java.lang.String[] dnsServers;
    public java.lang.String gateway;
    public android.net.LinkAddress[] ipAddresses;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.ipAddresses, i);
        parcel.writeTypedArray(this.directlyConnectedRoutes, i);
        parcel.writeStringArray(this.dnsServers);
        parcel.writeString(this.gateway);
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
            this.ipAddresses = (android.net.LinkAddress[]) parcel.createTypedArray(android.net.LinkAddress.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.directlyConnectedRoutes = (android.net.IpPrefix[]) parcel.createTypedArray(android.net.IpPrefix.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.dnsServers = parcel.createStringArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.gateway = parcel.readString();
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
        stringJoiner.add("ipAddresses: " + java.util.Arrays.toString(this.ipAddresses));
        stringJoiner.add("directlyConnectedRoutes: " + java.util.Arrays.toString(this.directlyConnectedRoutes));
        stringJoiner.add("dnsServers: " + java.util.Arrays.toString(this.dnsServers));
        stringJoiner.add("gateway: " + java.util.Objects.toString(this.gateway));
        return "InitialConfigurationParcelable" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.ipAddresses) | 0 | describeContents(this.directlyConnectedRoutes);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
