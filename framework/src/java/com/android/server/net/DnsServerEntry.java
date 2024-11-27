package com.android.server.net;

/* compiled from: NetlinkTracker.java */
/* loaded from: classes5.dex */
class DnsServerEntry implements java.lang.Comparable<com.android.server.net.DnsServerEntry> {
    public final java.net.InetAddress address;
    public long expiry;

    public DnsServerEntry(java.net.InetAddress inetAddress, long j) throws java.lang.IllegalArgumentException {
        this.address = inetAddress;
        this.expiry = j;
    }

    @Override // java.lang.Comparable
    public int compareTo(com.android.server.net.DnsServerEntry dnsServerEntry) {
        return java.lang.Long.compare(dnsServerEntry.expiry, this.expiry);
    }
}
