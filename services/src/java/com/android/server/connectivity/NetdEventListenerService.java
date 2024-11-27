package com.android.server.connectivity;

/* loaded from: classes.dex */
public class NetdEventListenerService extends com.android.net.module.util.BaseNetdEventListener {
    private static final int CONNECT_LATENCY_BURST_LIMIT = 5000;
    private static final int CONNECT_LATENCY_FILL_RATE = 15000;
    private static final boolean DBG = false;
    private static final int METRICS_SNAPSHOT_BUFFER_SIZE = 48;
    private static final long METRICS_SNAPSHOT_SPAN_MS = 300000;
    public static final java.lang.String SERVICE_NAME = "netd_listener";

    @com.android.internal.annotations.VisibleForTesting
    static final int WAKEUP_EVENT_BUFFER_LENGTH = 1024;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String WAKEUP_EVENT_PREFIX_DELIM = ":";
    final com.android.server.connectivity.NetdEventListenerService.TransportForNetIdNetworkCallback mCallback;
    private final android.net.ConnectivityManager mCm;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.internal.util.TokenBucket mConnectTb;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mLastSnapshot;

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.net.INetdEventCallback[] mNetdEventCallbackList;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.SparseArray<android.net.metrics.NetworkMetrics> mNetworkMetrics;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.internal.util.RingBuffer<com.android.server.connectivity.NetdEventListenerService.NetworkMetricsSnapshot> mNetworkMetricsSnapshots;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.internal.util.RingBuffer<android.net.metrics.WakeupEvent> mWakeupEvents;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.ArrayMap<java.lang.String, android.net.metrics.WakeupStats> mWakeupStats;
    private static final java.lang.String TAG = com.android.server.connectivity.NetdEventListenerService.class.getSimpleName();

    @com.android.internal.annotations.GuardedBy({"this"})
    private static final int[] ALLOWED_CALLBACK_TYPES = {0, 1, 2};

    public synchronized boolean addNetdEventCallback(int i, android.net.INetdEventCallback iNetdEventCallback) {
        if (!isValidCallerType(i)) {
            android.util.Log.e(TAG, "Invalid caller type: " + i);
            return false;
        }
        this.mNetdEventCallbackList[i] = iNetdEventCallback;
        return true;
    }

    public synchronized boolean removeNetdEventCallback(int i) {
        if (!isValidCallerType(i)) {
            android.util.Log.e(TAG, "Invalid caller type: " + i);
            return false;
        }
        this.mNetdEventCallbackList[i] = null;
        return true;
    }

    private static boolean isValidCallerType(int i) {
        for (int i2 = 0; i2 < ALLOWED_CALLBACK_TYPES.length; i2++) {
            if (i == ALLOWED_CALLBACK_TYPES[i2]) {
                return true;
            }
        }
        return false;
    }

    public NetdEventListenerService(android.content.Context context) {
        this((android.net.ConnectivityManager) context.getSystemService(android.net.ConnectivityManager.class));
    }

    @com.android.internal.annotations.VisibleForTesting
    public NetdEventListenerService(android.net.ConnectivityManager connectivityManager) {
        this.mNetworkMetrics = new android.util.SparseArray<>();
        this.mNetworkMetricsSnapshots = new com.android.internal.util.RingBuffer<>(com.android.server.connectivity.NetdEventListenerService.NetworkMetricsSnapshot.class, 48);
        this.mLastSnapshot = 0L;
        this.mWakeupStats = new android.util.ArrayMap<>();
        this.mWakeupEvents = new com.android.internal.util.RingBuffer<>(android.net.metrics.WakeupEvent.class, 1024);
        this.mConnectTb = new com.android.internal.util.TokenBucket(15000, 5000);
        this.mCallback = new com.android.server.connectivity.NetdEventListenerService.TransportForNetIdNetworkCallback();
        this.mNetdEventCallbackList = new android.net.INetdEventCallback[ALLOWED_CALLBACK_TYPES.length];
        this.mCm = connectivityManager;
        this.mCm.registerNetworkCallback(new android.net.NetworkRequest.Builder().clearCapabilities().build(), this.mCallback);
    }

    private static long projectSnapshotTime(long j) {
        return (j / 300000) * 300000;
    }

    private android.net.metrics.NetworkMetrics getMetricsForNetwork(long j, int i) {
        android.net.metrics.NetworkMetrics networkMetrics = this.mNetworkMetrics.get(i);
        android.net.NetworkCapabilities networkCapabilities = this.mCallback.getNetworkCapabilities(i);
        long packBits = networkCapabilities != null ? com.android.internal.util.BitUtils.packBits(networkCapabilities.getTransportTypes()) : 0L;
        boolean z = (networkMetrics == null || networkCapabilities == null || networkMetrics.transports == packBits) ? false : true;
        collectPendingMetricsSnapshot(j, z);
        if (networkMetrics == null || z) {
            android.net.metrics.NetworkMetrics networkMetrics2 = new android.net.metrics.NetworkMetrics(i, packBits, this.mConnectTb);
            this.mNetworkMetrics.put(i, networkMetrics2);
            return networkMetrics2;
        }
        return networkMetrics;
    }

    private com.android.server.connectivity.NetdEventListenerService.NetworkMetricsSnapshot[] getNetworkMetricsSnapshots() {
        collectPendingMetricsSnapshot(java.lang.System.currentTimeMillis(), false);
        return (com.android.server.connectivity.NetdEventListenerService.NetworkMetricsSnapshot[]) this.mNetworkMetricsSnapshots.toArray();
    }

    private void collectPendingMetricsSnapshot(long j, boolean z) {
        if (!z && java.lang.Math.abs(j - this.mLastSnapshot) <= 300000) {
            return;
        }
        this.mLastSnapshot = projectSnapshotTime(j);
        com.android.server.connectivity.NetdEventListenerService.NetworkMetricsSnapshot collect = com.android.server.connectivity.NetdEventListenerService.NetworkMetricsSnapshot.collect(this.mLastSnapshot, this.mNetworkMetrics);
        if (collect.stats.isEmpty()) {
            return;
        }
        this.mNetworkMetricsSnapshots.append(collect);
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public synchronized void onDnsEvent(int i, int i2, int i3, int i4, java.lang.String str, java.lang.String[] strArr, int i5, int i6) {
        int i7;
        int i8;
        android.net.INetdEventCallback[] iNetdEventCallbackArr;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        getMetricsForNetwork(currentTimeMillis, i).addDnsResult(i2, i3, i4);
        android.net.INetdEventCallback[] iNetdEventCallbackArr2 = this.mNetdEventCallbackList;
        int length = iNetdEventCallbackArr2.length;
        int i9 = 0;
        while (i9 < length) {
            android.net.INetdEventCallback iNetdEventCallback = iNetdEventCallbackArr2[i9];
            if (iNetdEventCallback == null) {
                i7 = length;
                i8 = i9;
                iNetdEventCallbackArr = iNetdEventCallbackArr2;
            } else {
                i7 = length;
                i8 = i9;
                iNetdEventCallbackArr = iNetdEventCallbackArr2;
                try {
                    iNetdEventCallback.onDnsEvent(i, i2, i3, str, strArr, i5, currentTimeMillis, i6);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            i9 = i8 + 1;
            length = i7;
            iNetdEventCallbackArr2 = iNetdEventCallbackArr;
        }
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public synchronized void onNat64PrefixEvent(int i, boolean z, java.lang.String str, int i2) {
        for (android.net.INetdEventCallback iNetdEventCallback : this.mNetdEventCallbackList) {
            if (iNetdEventCallback != null) {
                try {
                    iNetdEventCallback.onNat64PrefixEvent(i, z, str, i2);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public synchronized void onPrivateDnsValidationEvent(int i, java.lang.String str, java.lang.String str2, boolean z) {
        for (android.net.INetdEventCallback iNetdEventCallback : this.mNetdEventCallbackList) {
            if (iNetdEventCallback != null) {
                try {
                    iNetdEventCallback.onPrivateDnsValidationEvent(i, str, str2, z);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public synchronized void onConnectEvent(int i, int i2, int i3, java.lang.String str, int i4, int i5) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        getMetricsForNetwork(currentTimeMillis, i).addConnectResult(i2, i3, str);
        for (android.net.INetdEventCallback iNetdEventCallback : this.mNetdEventCallbackList) {
            if (iNetdEventCallback != null) {
                try {
                    iNetdEventCallback.onConnectEvent(str, i4, currentTimeMillis, i5);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    private boolean hasWifiTransport(android.net.Network network) {
        return this.mCm.getNetworkCapabilities(network).hasTransport(1);
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public synchronized void onWakeupEvent(java.lang.String str, int i, int i2, int i3, byte[] bArr, java.lang.String str2, java.lang.String str3, int i4, int i5, long j) {
        try {
            java.lang.String[] split = str.split(WAKEUP_EVENT_PREFIX_DELIM);
            if (split.length != 2) {
                throw new java.lang.IllegalArgumentException("Prefix " + str + " required in format <nethandle>:<interface>");
            }
            android.net.Network fromNetworkHandle = android.net.Network.fromNetworkHandle(java.lang.Long.parseLong(split[0]));
            android.net.metrics.WakeupEvent wakeupEvent = new android.net.metrics.WakeupEvent();
            wakeupEvent.iface = split[1];
            wakeupEvent.uid = i;
            wakeupEvent.ethertype = i2;
            if (com.android.internal.util.ArrayUtils.isEmpty(bArr)) {
                if (hasWifiTransport(fromNetworkHandle)) {
                    android.util.Log.e(TAG, "Empty mac address on WiFi transport, network: " + fromNetworkHandle);
                }
                wakeupEvent.dstHwAddr = null;
            } else {
                wakeupEvent.dstHwAddr = android.net.MacAddress.fromBytes(bArr);
            }
            wakeupEvent.srcIp = str2;
            wakeupEvent.dstIp = str3;
            wakeupEvent.ipNextHeader = i3;
            wakeupEvent.srcPort = i4;
            wakeupEvent.dstPort = i5;
            if (j > 0) {
                wakeupEvent.timestampMs = j / 1000000;
            } else {
                wakeupEvent.timestampMs = java.lang.System.currentTimeMillis();
            }
            addWakeupEvent(wakeupEvent);
            android.os.BatteryStatsInternal batteryStatsInternal = (android.os.BatteryStatsInternal) com.android.server.LocalServices.getService(android.os.BatteryStatsInternal.class);
            if (batteryStatsInternal != null) {
                batteryStatsInternal.noteCpuWakingNetworkPacket(fromNetworkHandle, (android.os.SystemClock.elapsedRealtime() + wakeupEvent.timestampMs) - java.lang.System.currentTimeMillis(), wakeupEvent.uid);
            }
            com.android.internal.util.FrameworkStatsLog.write(44, i, wakeupEvent.iface, i2, java.lang.String.valueOf(wakeupEvent.dstHwAddr), str2, str3, i3, i4, i5);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public synchronized void onTcpSocketStatsEvent(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
        if (iArr.length != iArr2.length || iArr.length != iArr3.length || iArr.length != iArr4.length || iArr.length != iArr5.length) {
            android.util.Log.e(TAG, "Mismatched lengths of TCP socket stats data arrays");
            return;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        for (int i = 0; i < iArr.length; i++) {
            int i2 = iArr[i];
            getMetricsForNetwork(currentTimeMillis, i2).addTcpStatsResult(iArr2[i], iArr3[i], iArr4[i], iArr5[i]);
        }
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public int getInterfaceVersion() {
        return 1;
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public java.lang.String getInterfaceHash() {
        return android.net.metrics.INetdEventListener.HASH;
    }

    private void addWakeupEvent(android.net.metrics.WakeupEvent wakeupEvent) {
        java.lang.String str = wakeupEvent.iface;
        this.mWakeupEvents.append(wakeupEvent);
        android.net.metrics.WakeupStats wakeupStats = this.mWakeupStats.get(str);
        if (wakeupStats == null) {
            wakeupStats = new android.net.metrics.WakeupStats(str);
            this.mWakeupStats.put(str, wakeupStats);
        }
        wakeupStats.countEvent(wakeupEvent);
    }

    public synchronized void flushStatistics(java.util.List<com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent> list) {
        for (int i = 0; i < this.mNetworkMetrics.size(); i++) {
            try {
                android.net.metrics.ConnectStats connectStats = this.mNetworkMetrics.valueAt(i).connectMetrics;
                if (connectStats.eventCount != 0) {
                    list.add(com.android.server.connectivity.IpConnectivityEventBuilder.toProto(connectStats));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        for (int i2 = 0; i2 < this.mNetworkMetrics.size(); i2++) {
            android.net.metrics.DnsEvent dnsEvent = this.mNetworkMetrics.valueAt(i2).dnsMetrics;
            if (dnsEvent.eventCount != 0) {
                list.add(com.android.server.connectivity.IpConnectivityEventBuilder.toProto(dnsEvent));
            }
        }
        for (int i3 = 0; i3 < this.mWakeupStats.size(); i3++) {
            list.add(com.android.server.connectivity.IpConnectivityEventBuilder.toProto(this.mWakeupStats.valueAt(i3)));
        }
        this.mNetworkMetrics.clear();
        this.mWakeupStats.clear();
    }

    public synchronized void list(java.io.PrintWriter printWriter) {
        try {
            printWriter.println("dns/connect events:");
            for (int i = 0; i < this.mNetworkMetrics.size(); i++) {
                printWriter.println(this.mNetworkMetrics.valueAt(i).connectMetrics);
            }
            for (int i2 = 0; i2 < this.mNetworkMetrics.size(); i2++) {
                printWriter.println(this.mNetworkMetrics.valueAt(i2).dnsMetrics);
            }
            printWriter.println("");
            printWriter.println("network statistics:");
            for (com.android.server.connectivity.NetdEventListenerService.NetworkMetricsSnapshot networkMetricsSnapshot : getNetworkMetricsSnapshots()) {
                printWriter.println(networkMetricsSnapshot);
            }
            printWriter.println("");
            printWriter.println("packet wakeup events:");
            for (int i3 = 0; i3 < this.mWakeupStats.size(); i3++) {
                printWriter.println(this.mWakeupStats.valueAt(i3));
            }
            for (android.net.metrics.WakeupEvent wakeupEvent : (android.net.metrics.WakeupEvent[]) this.mWakeupEvents.toArray()) {
                printWriter.println(wakeupEvent);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized java.util.List<com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent> listAsProtos() {
        java.util.ArrayList arrayList;
        try {
            arrayList = new java.util.ArrayList();
            for (int i = 0; i < this.mNetworkMetrics.size(); i++) {
                arrayList.add(com.android.server.connectivity.IpConnectivityEventBuilder.toProto(this.mNetworkMetrics.valueAt(i).connectMetrics));
            }
            for (int i2 = 0; i2 < this.mNetworkMetrics.size(); i2++) {
                arrayList.add(com.android.server.connectivity.IpConnectivityEventBuilder.toProto(this.mNetworkMetrics.valueAt(i2).dnsMetrics));
            }
            for (int i3 = 0; i3 < this.mWakeupStats.size(); i3++) {
                arrayList.add(com.android.server.connectivity.IpConnectivityEventBuilder.toProto(this.mWakeupStats.valueAt(i3)));
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
        return arrayList;
    }

    static class NetworkMetricsSnapshot {
        public java.util.List<android.net.metrics.NetworkMetrics.Summary> stats = new java.util.ArrayList();
        public long timeMs;

        NetworkMetricsSnapshot() {
        }

        static com.android.server.connectivity.NetdEventListenerService.NetworkMetricsSnapshot collect(long j, android.util.SparseArray<android.net.metrics.NetworkMetrics> sparseArray) {
            com.android.server.connectivity.NetdEventListenerService.NetworkMetricsSnapshot networkMetricsSnapshot = new com.android.server.connectivity.NetdEventListenerService.NetworkMetricsSnapshot();
            networkMetricsSnapshot.timeMs = j;
            for (int i = 0; i < sparseArray.size(); i++) {
                android.net.metrics.NetworkMetrics.Summary pendingStats = sparseArray.valueAt(i).getPendingStats();
                if (pendingStats != null) {
                    networkMetricsSnapshot.stats.add(pendingStats);
                }
            }
            return networkMetricsSnapshot;
        }

        public java.lang.String toString() {
            java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ");
            java.util.Iterator<android.net.metrics.NetworkMetrics.Summary> it = this.stats.iterator();
            while (it.hasNext()) {
                stringJoiner.add(it.next().toString());
            }
            return java.lang.String.format("%tT.%tL: %s", java.lang.Long.valueOf(this.timeMs), java.lang.Long.valueOf(this.timeMs), stringJoiner.toString());
        }
    }

    private class TransportForNetIdNetworkCallback extends android.net.ConnectivityManager.NetworkCallback {
        private final android.util.SparseArray<android.net.NetworkCapabilities> mCapabilities;

        private TransportForNetIdNetworkCallback() {
            this.mCapabilities = new android.util.SparseArray<>();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(android.net.Network network, android.net.NetworkCapabilities networkCapabilities) {
            synchronized (this.mCapabilities) {
                this.mCapabilities.put(network.getNetId(), networkCapabilities);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(android.net.Network network) {
            synchronized (this.mCapabilities) {
                this.mCapabilities.remove(network.getNetId());
            }
        }

        @android.annotation.Nullable
        public android.net.NetworkCapabilities getNetworkCapabilities(int i) {
            android.net.NetworkCapabilities networkCapabilities;
            synchronized (this.mCapabilities) {
                networkCapabilities = this.mCapabilities.get(i);
            }
            return networkCapabilities;
        }
    }
}
