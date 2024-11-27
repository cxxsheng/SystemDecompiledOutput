package android.net.ipmemorystore;

/* loaded from: classes.dex */
public class NetworkAttributes {
    private static final boolean DBG = true;
    private static final float NULL_MATCH_WEIGHT = 0.25f;

    @com.android.internal.annotations.VisibleForTesting
    public static final float TOTAL_WEIGHT = 850.0f;
    private static final float TOTAL_WEIGHT_CUTOFF = 520.0f;
    private static final float WEIGHT_ASSIGNEDV4ADDR = 300.0f;
    private static final float WEIGHT_ASSIGNEDV4ADDREXPIRY = 0.0f;
    private static final float WEIGHT_CLUSTER = 300.0f;
    private static final float WEIGHT_DNSADDRESSES = 200.0f;
    private static final float WEIGHT_MTU = 50.0f;
    private static final float WEIGHT_V6PROVLOSSQUIRK = 0.0f;

    @android.annotation.Nullable
    public final java.net.Inet4Address assignedV4Address;

    @android.annotation.Nullable
    public final java.lang.Long assignedV4AddressExpiry;

    @android.annotation.Nullable
    public final java.lang.String cluster;

    @android.annotation.Nullable
    public final java.util.List<java.net.InetAddress> dnsAddresses;

    @android.annotation.Nullable
    public final android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirk ipv6ProvisioningLossQuirk;

    @android.annotation.Nullable
    public final java.lang.Integer mtu;

    @com.android.internal.annotations.VisibleForTesting
    public NetworkAttributes(@android.annotation.Nullable java.net.Inet4Address inet4Address, @android.annotation.Nullable java.lang.Long l, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.util.List<java.net.InetAddress> list, @android.annotation.Nullable java.lang.Integer num, @android.annotation.Nullable android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirk iPv6ProvisioningLossQuirk) {
        if (num != null && num.intValue() < 0) {
            throw new java.lang.IllegalArgumentException("MTU can't be negative");
        }
        if (l != null && l.longValue() <= 0) {
            throw new java.lang.IllegalArgumentException("lease expiry can't be negative or zero");
        }
        this.assignedV4Address = inet4Address;
        this.assignedV4AddressExpiry = l;
        this.cluster = str;
        this.dnsAddresses = list == null ? null : java.util.Collections.unmodifiableList(new java.util.ArrayList(list));
        this.mtu = num;
        this.ipv6ProvisioningLossQuirk = iPv6ProvisioningLossQuirk;
    }

    @com.android.internal.annotations.VisibleForTesting
    public NetworkAttributes(@android.annotation.NonNull android.net.ipmemorystore.NetworkAttributesParcelable networkAttributesParcelable) {
        this((java.net.Inet4Address) getByAddressOrNull(networkAttributesParcelable.assignedV4Address), networkAttributesParcelable.assignedV4AddressExpiry > 0 ? java.lang.Long.valueOf(networkAttributesParcelable.assignedV4AddressExpiry) : null, networkAttributesParcelable.cluster, blobArrayToInetAddressList(networkAttributesParcelable.dnsAddresses), networkAttributesParcelable.mtu >= 0 ? java.lang.Integer.valueOf(networkAttributesParcelable.mtu) : null, android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirk.fromStableParcelable(networkAttributesParcelable.ipv6ProvisioningLossQuirk));
    }

    @android.annotation.Nullable
    private static java.net.InetAddress getByAddressOrNull(@android.annotation.Nullable byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            return java.net.InetAddress.getByAddress(bArr);
        } catch (java.net.UnknownHostException e) {
            return null;
        }
    }

    @android.annotation.Nullable
    private static java.util.List<java.net.InetAddress> blobArrayToInetAddressList(@android.annotation.Nullable android.net.ipmemorystore.Blob[] blobArr) {
        if (blobArr == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(blobArr.length);
        for (android.net.ipmemorystore.Blob blob : blobArr) {
            java.net.InetAddress byAddressOrNull = getByAddressOrNull(blob.data);
            if (byAddressOrNull != null) {
                arrayList.add(byAddressOrNull);
            }
        }
        return arrayList;
    }

    @android.annotation.Nullable
    private static android.net.ipmemorystore.Blob[] inetAddressListToBlobArray(@android.annotation.Nullable java.util.List<java.net.InetAddress> list) {
        if (list == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < list.size(); i++) {
            java.net.InetAddress inetAddress = list.get(i);
            if (inetAddress != null) {
                android.net.ipmemorystore.Blob blob = new android.net.ipmemorystore.Blob();
                blob.data = inetAddress.getAddress();
                arrayList.add(blob);
            }
        }
        return (android.net.ipmemorystore.Blob[]) arrayList.toArray(new android.net.ipmemorystore.Blob[0]);
    }

    @android.annotation.NonNull
    public android.net.ipmemorystore.NetworkAttributesParcelable toParcelable() {
        android.net.ipmemorystore.NetworkAttributesParcelable networkAttributesParcelable = new android.net.ipmemorystore.NetworkAttributesParcelable();
        networkAttributesParcelable.assignedV4Address = this.assignedV4Address == null ? null : this.assignedV4Address.getAddress();
        networkAttributesParcelable.assignedV4AddressExpiry = this.assignedV4AddressExpiry == null ? 0L : this.assignedV4AddressExpiry.longValue();
        networkAttributesParcelable.cluster = this.cluster;
        networkAttributesParcelable.dnsAddresses = inetAddressListToBlobArray(this.dnsAddresses);
        networkAttributesParcelable.mtu = this.mtu == null ? -1 : this.mtu.intValue();
        networkAttributesParcelable.ipv6ProvisioningLossQuirk = this.ipv6ProvisioningLossQuirk != null ? this.ipv6ProvisioningLossQuirk.toStableParcelable() : null;
        return networkAttributesParcelable;
    }

    private float samenessContribution(float f, @android.annotation.Nullable java.lang.Object obj, @android.annotation.Nullable java.lang.Object obj2) {
        if (obj == null) {
            if (obj2 == null) {
                return f * NULL_MATCH_WEIGHT;
            }
            return 0.0f;
        }
        if (java.util.Objects.equals(obj, obj2)) {
            return f;
        }
        return 0.0f;
    }

    public float getNetworkGroupSamenessConfidence(@android.annotation.NonNull android.net.ipmemorystore.NetworkAttributes networkAttributes) {
        float samenessContribution = samenessContribution(300.0f, this.assignedV4Address, networkAttributes.assignedV4Address) + samenessContribution(0.0f, this.assignedV4AddressExpiry, networkAttributes.assignedV4AddressExpiry) + samenessContribution(300.0f, this.cluster, networkAttributes.cluster) + samenessContribution(WEIGHT_DNSADDRESSES, this.dnsAddresses, networkAttributes.dnsAddresses) + samenessContribution(WEIGHT_MTU, this.mtu, networkAttributes.mtu) + samenessContribution(0.0f, this.ipv6ProvisioningLossQuirk, networkAttributes.ipv6ProvisioningLossQuirk);
        if (samenessContribution < TOTAL_WEIGHT_CUTOFF) {
            return samenessContribution / 1040.0f;
        }
        return (((samenessContribution - TOTAL_WEIGHT_CUTOFF) / 330.0f) / 2.0f) + 0.5f;
    }

    public static class Builder {

        @android.annotation.Nullable
        private java.net.Inet4Address mAssignedAddress;

        @android.annotation.Nullable
        private java.lang.Long mAssignedAddressExpiry;

        @android.annotation.Nullable
        private java.lang.String mCluster;

        @android.annotation.Nullable
        private java.util.List<java.net.InetAddress> mDnsAddresses;

        @android.annotation.Nullable
        private android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirk mIpv6ProvLossQuirk;

        @android.annotation.Nullable
        private java.lang.Integer mMtu;

        public Builder() {
        }

        public Builder(@android.annotation.NonNull android.net.ipmemorystore.NetworkAttributes networkAttributes) {
            this.mAssignedAddress = networkAttributes.assignedV4Address;
            this.mAssignedAddressExpiry = networkAttributes.assignedV4AddressExpiry;
            this.mCluster = networkAttributes.cluster;
            this.mDnsAddresses = new java.util.ArrayList(networkAttributes.dnsAddresses);
            this.mMtu = networkAttributes.mtu;
            this.mIpv6ProvLossQuirk = networkAttributes.ipv6ProvisioningLossQuirk;
        }

        public android.net.ipmemorystore.NetworkAttributes.Builder setAssignedV4Address(@android.annotation.Nullable java.net.Inet4Address inet4Address) {
            this.mAssignedAddress = inet4Address;
            return this;
        }

        public android.net.ipmemorystore.NetworkAttributes.Builder setAssignedV4AddressExpiry(@android.annotation.Nullable java.lang.Long l) {
            if (l != null && l.longValue() <= 0) {
                throw new java.lang.IllegalArgumentException("lease expiry can't be negative or zero");
            }
            this.mAssignedAddressExpiry = l;
            return this;
        }

        public android.net.ipmemorystore.NetworkAttributes.Builder setCluster(@android.annotation.Nullable java.lang.String str) {
            this.mCluster = str;
            return this;
        }

        public android.net.ipmemorystore.NetworkAttributes.Builder setDnsAddresses(@android.annotation.Nullable java.util.List<java.net.InetAddress> list) {
            if (list != null) {
                java.util.Iterator<java.net.InetAddress> it = list.iterator();
                while (it.hasNext()) {
                    if (it.next() == null) {
                        throw new java.lang.IllegalArgumentException("Null DNS address");
                    }
                }
            }
            this.mDnsAddresses = list;
            return this;
        }

        public android.net.ipmemorystore.NetworkAttributes.Builder setMtu(@android.annotation.Nullable java.lang.Integer num) {
            if (num != null && num.intValue() < 0) {
                throw new java.lang.IllegalArgumentException("MTU can't be negative");
            }
            this.mMtu = num;
            return this;
        }

        public android.net.ipmemorystore.NetworkAttributes.Builder setIpv6ProvLossQuirk(@android.annotation.Nullable android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirk iPv6ProvisioningLossQuirk) {
            this.mIpv6ProvLossQuirk = iPv6ProvisioningLossQuirk;
            return this;
        }

        public android.net.ipmemorystore.NetworkAttributes build() {
            return new android.net.ipmemorystore.NetworkAttributes(this.mAssignedAddress, this.mAssignedAddressExpiry, this.mCluster, this.mDnsAddresses, this.mMtu, this.mIpv6ProvLossQuirk);
        }
    }

    public boolean isEmpty() {
        return this.assignedV4Address == null && this.assignedV4AddressExpiry == null && this.cluster == null && this.dnsAddresses == null && this.mtu == null && this.ipv6ProvisioningLossQuirk == null;
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (!(obj instanceof android.net.ipmemorystore.NetworkAttributes)) {
            return false;
        }
        android.net.ipmemorystore.NetworkAttributes networkAttributes = (android.net.ipmemorystore.NetworkAttributes) obj;
        return java.util.Objects.equals(this.assignedV4Address, networkAttributes.assignedV4Address) && java.util.Objects.equals(this.assignedV4AddressExpiry, networkAttributes.assignedV4AddressExpiry) && java.util.Objects.equals(this.cluster, networkAttributes.cluster) && java.util.Objects.equals(this.dnsAddresses, networkAttributes.dnsAddresses) && java.util.Objects.equals(this.mtu, networkAttributes.mtu) && java.util.Objects.equals(this.ipv6ProvisioningLossQuirk, networkAttributes.ipv6ProvisioningLossQuirk);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.assignedV4Address, this.assignedV4AddressExpiry, this.cluster, this.dnsAddresses, this.mtu, this.ipv6ProvisioningLossQuirk);
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(" ", "{", "}");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (this.assignedV4Address != null) {
            stringJoiner.add("assignedV4Addr :");
            stringJoiner.add(this.assignedV4Address.toString());
        } else {
            arrayList.add("assignedV4Addr");
        }
        if (this.assignedV4AddressExpiry != null) {
            stringJoiner.add("assignedV4AddressExpiry :");
            stringJoiner.add(this.assignedV4AddressExpiry.toString());
        } else {
            arrayList.add("assignedV4AddressExpiry");
        }
        if (this.cluster != null) {
            stringJoiner.add("cluster :");
            stringJoiner.add(this.cluster);
        } else {
            arrayList.add("cluster");
        }
        if (this.dnsAddresses != null) {
            stringJoiner.add("dnsAddr : [");
            java.util.Iterator<java.net.InetAddress> it = this.dnsAddresses.iterator();
            while (it.hasNext()) {
                stringJoiner.add(it.next().getHostAddress());
            }
            stringJoiner.add("]");
        } else {
            arrayList.add("dnsAddr");
        }
        if (this.mtu != null) {
            stringJoiner.add("mtu :");
            stringJoiner.add(this.mtu.toString());
        } else {
            arrayList.add("mtu");
        }
        if (this.ipv6ProvisioningLossQuirk != null) {
            stringJoiner.add("ipv6ProvisioningLossQuirk : [");
            stringJoiner.add(this.ipv6ProvisioningLossQuirk.toString());
            stringJoiner.add("]");
        } else {
            arrayList.add("ipv6ProvisioningLossQuirk");
        }
        if (!arrayList.isEmpty()) {
            stringJoiner.add("; Null fields : [");
            java.util.Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                stringJoiner.add((java.lang.String) it2.next());
            }
            stringJoiner.add("]");
        }
        return stringJoiner.toString();
    }
}
