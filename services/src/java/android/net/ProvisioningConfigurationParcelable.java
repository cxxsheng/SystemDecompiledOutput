package android.net;

/* loaded from: classes.dex */
public class ProvisioningConfigurationParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.ProvisioningConfigurationParcelable> CREATOR = new android.os.Parcelable.Creator<android.net.ProvisioningConfigurationParcelable>() { // from class: android.net.ProvisioningConfigurationParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.ProvisioningConfigurationParcelable createFromParcel(android.os.Parcel parcel) {
            android.net.ProvisioningConfigurationParcelable provisioningConfigurationParcelable = new android.net.ProvisioningConfigurationParcelable();
            provisioningConfigurationParcelable.readFromParcel(parcel);
            return provisioningConfigurationParcelable;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.ProvisioningConfigurationParcelable[] newArray(int i) {
            return new android.net.ProvisioningConfigurationParcelable[i];
        }
    };
    public android.net.apf.ApfCapabilities apfCapabilities;
    public java.lang.String displayName;
    public android.net.InitialConfigurationParcelable initialConfig;
    public android.net.Layer2InformationParcelable layer2Info;
    public android.net.Network network;
    public java.util.List<android.net.networkstack.aidl.dhcp.DhcpOption> options;
    public android.net.ScanResultInfoParcelable scanResultInfo;
    public android.net.StaticIpConfiguration staticIpConfig;

    @java.lang.Deprecated
    public boolean enableIPv4 = false;

    @java.lang.Deprecated
    public boolean enableIPv6 = false;
    public boolean usingMultinetworkPolicyTracker = false;
    public boolean usingIpReachabilityMonitor = false;
    public int requestedPreDhcpActionMs = 0;
    public int provisioningTimeoutMs = 0;
    public int ipv6AddrGenMode = 0;
    public boolean enablePreconnection = false;
    public int ipv4ProvisioningMode = 0;
    public int ipv6ProvisioningMode = 0;
    public boolean uniqueEui64AddressesOnly = false;
    public int creatorUid = 0;
    public int hostnameSetting = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.enableIPv4);
        parcel.writeBoolean(this.enableIPv6);
        parcel.writeBoolean(this.usingMultinetworkPolicyTracker);
        parcel.writeBoolean(this.usingIpReachabilityMonitor);
        parcel.writeInt(this.requestedPreDhcpActionMs);
        parcel.writeTypedObject(this.initialConfig, i);
        parcel.writeTypedObject(this.staticIpConfig, i);
        parcel.writeTypedObject(this.apfCapabilities, i);
        parcel.writeInt(this.provisioningTimeoutMs);
        parcel.writeInt(this.ipv6AddrGenMode);
        parcel.writeTypedObject(this.network, i);
        parcel.writeString(this.displayName);
        parcel.writeBoolean(this.enablePreconnection);
        parcel.writeTypedObject(this.scanResultInfo, i);
        parcel.writeTypedObject(this.layer2Info, i);
        android.net.ProvisioningConfigurationParcelable._Parcel.writeTypedList(parcel, this.options, i);
        parcel.writeInt(this.ipv4ProvisioningMode);
        parcel.writeInt(this.ipv6ProvisioningMode);
        parcel.writeBoolean(this.uniqueEui64AddressesOnly);
        parcel.writeInt(this.creatorUid);
        parcel.writeInt(this.hostnameSetting);
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
            this.enableIPv4 = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.enableIPv6 = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.usingMultinetworkPolicyTracker = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.usingIpReachabilityMonitor = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.requestedPreDhcpActionMs = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.initialConfig = (android.net.InitialConfigurationParcelable) parcel.readTypedObject(android.net.InitialConfigurationParcelable.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.staticIpConfig = (android.net.StaticIpConfiguration) parcel.readTypedObject(android.net.StaticIpConfiguration.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.apfCapabilities = (android.net.apf.ApfCapabilities) parcel.readTypedObject(android.net.apf.ApfCapabilities.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.provisioningTimeoutMs = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.ipv6AddrGenMode = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.network = (android.net.Network) parcel.readTypedObject(android.net.Network.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.displayName = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.enablePreconnection = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.scanResultInfo = (android.net.ScanResultInfoParcelable) parcel.readTypedObject(android.net.ScanResultInfoParcelable.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.layer2Info = (android.net.Layer2InformationParcelable) parcel.readTypedObject(android.net.Layer2InformationParcelable.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.options = parcel.createTypedArrayList(android.net.networkstack.aidl.dhcp.DhcpOption.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.ipv4ProvisioningMode = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.ipv6ProvisioningMode = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.uniqueEui64AddressesOnly = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.creatorUid = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.hostnameSetting = parcel.readInt();
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
        stringJoiner.add("enableIPv4: " + this.enableIPv4);
        stringJoiner.add("enableIPv6: " + this.enableIPv6);
        stringJoiner.add("usingMultinetworkPolicyTracker: " + this.usingMultinetworkPolicyTracker);
        stringJoiner.add("usingIpReachabilityMonitor: " + this.usingIpReachabilityMonitor);
        stringJoiner.add("requestedPreDhcpActionMs: " + this.requestedPreDhcpActionMs);
        stringJoiner.add("initialConfig: " + java.util.Objects.toString(this.initialConfig));
        stringJoiner.add("staticIpConfig: " + java.util.Objects.toString(this.staticIpConfig));
        stringJoiner.add("apfCapabilities: " + java.util.Objects.toString(this.apfCapabilities));
        stringJoiner.add("provisioningTimeoutMs: " + this.provisioningTimeoutMs);
        stringJoiner.add("ipv6AddrGenMode: " + this.ipv6AddrGenMode);
        stringJoiner.add("network: " + java.util.Objects.toString(this.network));
        stringJoiner.add("displayName: " + java.util.Objects.toString(this.displayName));
        stringJoiner.add("enablePreconnection: " + this.enablePreconnection);
        stringJoiner.add("scanResultInfo: " + java.util.Objects.toString(this.scanResultInfo));
        stringJoiner.add("layer2Info: " + java.util.Objects.toString(this.layer2Info));
        stringJoiner.add("options: " + java.util.Objects.toString(this.options));
        stringJoiner.add("ipv4ProvisioningMode: " + this.ipv4ProvisioningMode);
        stringJoiner.add("ipv6ProvisioningMode: " + this.ipv6ProvisioningMode);
        stringJoiner.add("uniqueEui64AddressesOnly: " + this.uniqueEui64AddressesOnly);
        stringJoiner.add("creatorUid: " + this.creatorUid);
        stringJoiner.add("hostnameSetting: " + this.hostnameSetting);
        return "ProvisioningConfigurationParcelable" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.initialConfig) | 0 | describeContents(this.staticIpConfig) | describeContents(this.apfCapabilities) | describeContents(this.network) | describeContents(this.scanResultInfo) | describeContents(this.layer2Info) | describeContents(this.options);
    }

    private int describeContents(java.lang.Object obj) {
        int i = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.util.Collection) {
            java.util.Iterator it = ((java.util.Collection) obj).iterator();
            while (it.hasNext()) {
                i |= describeContents(it.next());
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }

    static class _Parcel {
        _Parcel() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends android.os.Parcelable> void writeTypedList(android.os.Parcel parcel, java.util.List<T> list, int i) {
            if (list == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeTypedObject(list.get(i2), i);
            }
        }
    }
}
