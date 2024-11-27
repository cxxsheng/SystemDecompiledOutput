package com.android.net.module.util;

/* loaded from: classes5.dex */
public final class IpRange {
    private static final int SIGNUM_POSITIVE = 1;
    private final byte[] mEndAddr;
    private final byte[] mStartAddr;

    public IpRange(java.net.InetAddress inetAddress, java.net.InetAddress inetAddress2) {
        java.util.Objects.requireNonNull(inetAddress, "startAddr must not be null");
        java.util.Objects.requireNonNull(inetAddress2, "endAddr must not be null");
        if (!inetAddress.getClass().equals(inetAddress2.getClass())) {
            throw new java.lang.IllegalArgumentException("Invalid range: Address family mismatch");
        }
        if (addrToBigInteger(inetAddress.getAddress()).compareTo(addrToBigInteger(inetAddress2.getAddress())) >= 0) {
            throw new java.lang.IllegalArgumentException("Invalid range; start address must be before end address");
        }
        this.mStartAddr = inetAddress.getAddress();
        this.mEndAddr = inetAddress2.getAddress();
    }

    public IpRange(android.net.IpPrefix ipPrefix) {
        java.util.Objects.requireNonNull(ipPrefix, "prefix must not be null");
        this.mStartAddr = ipPrefix.getRawAddress();
        this.mEndAddr = ipPrefix.getRawAddress();
        for (int prefixLength = ipPrefix.getPrefixLength(); prefixLength < this.mEndAddr.length * 8; prefixLength++) {
            byte[] bArr = this.mEndAddr;
            int i = prefixLength / 8;
            bArr[i] = (byte) (bArr[i] | ((byte) (128 >> (prefixLength % 8))));
        }
    }

    private static java.net.InetAddress getAsInetAddress(byte[] bArr) {
        try {
            return java.net.InetAddress.getByAddress(bArr);
        } catch (java.net.UnknownHostException e) {
            throw new java.lang.IllegalArgumentException("Address is invalid");
        }
    }

    public java.net.InetAddress getStartAddr() {
        return getAsInetAddress(this.mStartAddr);
    }

    public java.net.InetAddress getEndAddr() {
        return getAsInetAddress(this.mEndAddr);
    }

    public java.util.List<android.net.IpPrefix> asIpPrefixes() {
        boolean z = this.mStartAddr.length == 16;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.LinkedList linkedList = new java.util.LinkedList();
        linkedList.add(new android.net.IpPrefix(z ? getAsInetAddress(new byte[16]) : getAsInetAddress(new byte[4]), 0));
        while (!linkedList.isEmpty()) {
            android.net.IpPrefix ipPrefix = (android.net.IpPrefix) linkedList.poll();
            com.android.net.module.util.IpRange ipRange = new com.android.net.module.util.IpRange(ipPrefix);
            if (containsRange(ipRange)) {
                arrayList.add(ipPrefix);
            } else if (overlapsRange(ipRange)) {
                linkedList.addAll(getSubsetPrefixes(ipPrefix));
            }
        }
        return arrayList;
    }

    private static java.util.List<android.net.IpPrefix> getSubsetPrefixes(android.net.IpPrefix ipPrefix) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int prefixLength = ipPrefix.getPrefixLength();
        int i = prefixLength + 1;
        arrayList.add(new android.net.IpPrefix(ipPrefix.getAddress(), i));
        byte[] rawAddress = ipPrefix.getRawAddress();
        int i2 = prefixLength / 8;
        rawAddress[i2] = (byte) ((128 >> (prefixLength % 8)) ^ rawAddress[i2]);
        arrayList.add(new android.net.IpPrefix(getAsInetAddress(rawAddress), i));
        return arrayList;
    }

    public boolean containsRange(com.android.net.module.util.IpRange ipRange) {
        return addrToBigInteger(this.mStartAddr).compareTo(addrToBigInteger(ipRange.mStartAddr)) <= 0 && addrToBigInteger(this.mEndAddr).compareTo(addrToBigInteger(ipRange.mEndAddr)) >= 0;
    }

    public boolean overlapsRange(com.android.net.module.util.IpRange ipRange) {
        return addrToBigInteger(this.mStartAddr).compareTo(addrToBigInteger(ipRange.mEndAddr)) <= 0 && addrToBigInteger(ipRange.mStartAddr).compareTo(addrToBigInteger(this.mEndAddr)) <= 0;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mStartAddr)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mEndAddr)));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.net.module.util.IpRange)) {
            return false;
        }
        com.android.net.module.util.IpRange ipRange = (com.android.net.module.util.IpRange) obj;
        return java.util.Arrays.equals(this.mStartAddr, ipRange.mStartAddr) && java.util.Arrays.equals(this.mEndAddr, ipRange.mEndAddr);
    }

    private static java.math.BigInteger addrToBigInteger(byte[] bArr) {
        return new java.math.BigInteger(1, bArr);
    }
}
