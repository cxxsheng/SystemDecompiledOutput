package com.android.server.net;

/* compiled from: NetlinkTracker.java */
/* loaded from: classes5.dex */
class DnsServerRepository {
    public static final int NUM_CURRENT_SERVERS = 3;
    public static final int NUM_SERVERS = 12;
    public static final java.lang.String TAG = "DnsServerRepository";
    private java.util.Set<java.net.InetAddress> mCurrentServers = new java.util.HashSet();
    private java.util.ArrayList<com.android.server.net.DnsServerEntry> mAllServers = new java.util.ArrayList<>(12);
    private java.util.HashMap<java.net.InetAddress, com.android.server.net.DnsServerEntry> mIndex = new java.util.HashMap<>(12);

    public synchronized void setDnsServersOn(android.net.LinkProperties linkProperties) {
        linkProperties.setDnsServers(this.mCurrentServers);
    }

    public synchronized boolean addServers(long j, java.lang.String[] strArr) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long j2 = (j * 1000) + currentTimeMillis;
        for (java.lang.String str : strArr) {
            try {
                java.net.InetAddress parseNumericAddress = java.net.InetAddress.parseNumericAddress(str);
                if (!updateExistingEntry(parseNumericAddress, j2) && j2 > currentTimeMillis) {
                    com.android.server.net.DnsServerEntry dnsServerEntry = new com.android.server.net.DnsServerEntry(parseNumericAddress, j2);
                    this.mAllServers.add(dnsServerEntry);
                    this.mIndex.put(parseNumericAddress, dnsServerEntry);
                }
            } catch (java.lang.IllegalArgumentException e) {
            }
        }
        java.util.Collections.sort(this.mAllServers);
        return updateCurrentServers();
    }

    private synchronized boolean updateExistingEntry(java.net.InetAddress inetAddress, long j) {
        com.android.server.net.DnsServerEntry dnsServerEntry = this.mIndex.get(inetAddress);
        if (dnsServerEntry == null) {
            return false;
        }
        dnsServerEntry.expiry = j;
        return true;
    }

    private synchronized boolean updateCurrentServers() {
        boolean z;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        z = false;
        for (int size = this.mAllServers.size() - 1; size >= 0 && (size >= 12 || this.mAllServers.get(size).expiry < currentTimeMillis); size--) {
            com.android.server.net.DnsServerEntry remove = this.mAllServers.remove(size);
            this.mIndex.remove(remove.address);
            z |= this.mCurrentServers.remove(remove.address);
        }
        java.util.Iterator<com.android.server.net.DnsServerEntry> it = this.mAllServers.iterator();
        while (it.hasNext()) {
            com.android.server.net.DnsServerEntry next = it.next();
            if (this.mCurrentServers.size() >= 3) {
                break;
            }
            z |= this.mCurrentServers.add(next.address);
        }
        return z;
    }
}
