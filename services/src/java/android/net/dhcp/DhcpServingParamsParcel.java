package android.net.dhcp;

/* loaded from: classes.dex */
public class DhcpServingParamsParcel implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.dhcp.DhcpServingParamsParcel> CREATOR = new android.os.Parcelable.Creator<android.net.dhcp.DhcpServingParamsParcel>() { // from class: android.net.dhcp.DhcpServingParamsParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.dhcp.DhcpServingParamsParcel createFromParcel(android.os.Parcel parcel) {
            android.net.dhcp.DhcpServingParamsParcel dhcpServingParamsParcel = new android.net.dhcp.DhcpServingParamsParcel();
            dhcpServingParamsParcel.readFromParcel(parcel);
            return dhcpServingParamsParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.dhcp.DhcpServingParamsParcel[] newArray(int i) {
            return new android.net.dhcp.DhcpServingParamsParcel[i];
        }
    };
    public int[] defaultRouters;
    public int[] dnsServers;
    public int[] excludedAddrs;
    public int serverAddr = 0;
    public int serverAddrPrefixLength = 0;
    public long dhcpLeaseTimeSecs = 0;
    public int linkMtu = 0;
    public boolean metered = false;
    public int singleClientAddr = 0;
    public boolean changePrefixOnDecline = false;
    public int leasesSubnetPrefixLength = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.serverAddr);
        parcel.writeInt(this.serverAddrPrefixLength);
        parcel.writeIntArray(this.defaultRouters);
        parcel.writeIntArray(this.dnsServers);
        parcel.writeIntArray(this.excludedAddrs);
        parcel.writeLong(this.dhcpLeaseTimeSecs);
        parcel.writeInt(this.linkMtu);
        parcel.writeBoolean(this.metered);
        parcel.writeInt(this.singleClientAddr);
        parcel.writeBoolean(this.changePrefixOnDecline);
        parcel.writeInt(this.leasesSubnetPrefixLength);
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
            this.serverAddr = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.serverAddrPrefixLength = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.defaultRouters = parcel.createIntArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.dnsServers = parcel.createIntArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.excludedAddrs = parcel.createIntArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.dhcpLeaseTimeSecs = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.linkMtu = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.metered = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.singleClientAddr = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.changePrefixOnDecline = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.leasesSubnetPrefixLength = parcel.readInt();
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
        stringJoiner.add("serverAddr: " + this.serverAddr);
        stringJoiner.add("serverAddrPrefixLength: " + this.serverAddrPrefixLength);
        stringJoiner.add("defaultRouters: " + java.util.Arrays.toString(this.defaultRouters));
        stringJoiner.add("dnsServers: " + java.util.Arrays.toString(this.dnsServers));
        stringJoiner.add("excludedAddrs: " + java.util.Arrays.toString(this.excludedAddrs));
        stringJoiner.add("dhcpLeaseTimeSecs: " + this.dhcpLeaseTimeSecs);
        stringJoiner.add("linkMtu: " + this.linkMtu);
        stringJoiner.add("metered: " + this.metered);
        stringJoiner.add("singleClientAddr: " + this.singleClientAddr);
        stringJoiner.add("changePrefixOnDecline: " + this.changePrefixOnDecline);
        stringJoiner.add("leasesSubnetPrefixLength: " + this.leasesSubnetPrefixLength);
        return "DhcpServingParamsParcel" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
