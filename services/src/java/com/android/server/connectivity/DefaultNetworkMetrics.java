package com.android.server.connectivity;

/* loaded from: classes.dex */
public class DefaultNetworkMetrics {
    private static final int ROLLING_LOG_SIZE = 64;

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.net.metrics.DefaultNetworkEvent mCurrentDefaultNetwork;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mIsCurrentlyValid;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mLastTransports;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mLastValidationTimeMs;
    public final long creationTimeMs = android.os.SystemClock.elapsedRealtime();

    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.List<android.net.metrics.DefaultNetworkEvent> mEvents = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.internal.util.RingBuffer<android.net.metrics.DefaultNetworkEvent> mEventsLog = new com.android.internal.util.RingBuffer<>(android.net.metrics.DefaultNetworkEvent.class, 64);

    public DefaultNetworkMetrics() {
        newDefaultNetwork(this.creationTimeMs, null, 0, false, null, null);
    }

    public synchronized void listEvents(java.io.PrintWriter printWriter) {
        try {
            printWriter.println("default network events:");
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            for (android.net.metrics.DefaultNetworkEvent defaultNetworkEvent : (android.net.metrics.DefaultNetworkEvent[]) this.mEventsLog.toArray()) {
                printEvent(currentTimeMillis, printWriter, defaultNetworkEvent);
            }
            this.mCurrentDefaultNetwork.updateDuration(elapsedRealtime);
            if (this.mIsCurrentlyValid) {
                updateValidationTime(elapsedRealtime);
                this.mLastValidationTimeMs = elapsedRealtime;
            }
            printEvent(currentTimeMillis, printWriter, this.mCurrentDefaultNetwork);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized java.util.List<com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent> listEventsAsProto() {
        java.util.ArrayList arrayList;
        arrayList = new java.util.ArrayList();
        for (android.net.metrics.DefaultNetworkEvent defaultNetworkEvent : (android.net.metrics.DefaultNetworkEvent[]) this.mEventsLog.toArray()) {
            arrayList.add(com.android.server.connectivity.IpConnectivityEventBuilder.toProto(defaultNetworkEvent));
        }
        return arrayList;
    }

    public synchronized void flushEvents(java.util.List<com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent> list) {
        try {
            java.util.Iterator<android.net.metrics.DefaultNetworkEvent> it = this.mEvents.iterator();
            while (it.hasNext()) {
                list.add(com.android.server.connectivity.IpConnectivityEventBuilder.toProto(it.next()));
            }
            this.mEvents.clear();
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void logDefaultNetworkValidity(long j, boolean z) {
        if (!z) {
            try {
                if (this.mIsCurrentlyValid) {
                    this.mIsCurrentlyValid = false;
                    updateValidationTime(j);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z && !this.mIsCurrentlyValid) {
            this.mIsCurrentlyValid = true;
            this.mLastValidationTimeMs = j;
        }
    }

    private void updateValidationTime(long j) {
        this.mCurrentDefaultNetwork.validatedMs += j - this.mLastValidationTimeMs;
    }

    public synchronized void logDefaultNetworkEvent(long j, android.net.Network network, int i, boolean z, android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities, android.net.Network network2, int i2, android.net.LinkProperties linkProperties2, android.net.NetworkCapabilities networkCapabilities2) {
        logCurrentDefaultNetwork(j, network2, i2, linkProperties2, networkCapabilities2);
        newDefaultNetwork(j, network, i, z, linkProperties, networkCapabilities);
    }

    private void logCurrentDefaultNetwork(long j, android.net.Network network, int i, android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities) {
        if (this.mIsCurrentlyValid) {
            updateValidationTime(j);
        }
        android.net.metrics.DefaultNetworkEvent defaultNetworkEvent = this.mCurrentDefaultNetwork;
        defaultNetworkEvent.updateDuration(j);
        defaultNetworkEvent.previousTransports = this.mLastTransports;
        if (network != null) {
            fillLinkInfo(defaultNetworkEvent, network, linkProperties, networkCapabilities);
            defaultNetworkEvent.finalScore = i;
        }
        if (defaultNetworkEvent.transports != 0) {
            this.mLastTransports = defaultNetworkEvent.transports;
        }
        this.mEvents.add(defaultNetworkEvent);
        this.mEventsLog.append(defaultNetworkEvent);
    }

    private void newDefaultNetwork(long j, android.net.Network network, int i, boolean z, android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities) {
        android.net.metrics.DefaultNetworkEvent defaultNetworkEvent = new android.net.metrics.DefaultNetworkEvent(j);
        defaultNetworkEvent.durationMs = j;
        if (network != null) {
            fillLinkInfo(defaultNetworkEvent, network, linkProperties, networkCapabilities);
            defaultNetworkEvent.initialScore = i;
            if (z) {
                this.mIsCurrentlyValid = true;
                this.mLastValidationTimeMs = j;
            }
        } else {
            this.mIsCurrentlyValid = false;
        }
        this.mCurrentDefaultNetwork = defaultNetworkEvent;
    }

    private static void fillLinkInfo(android.net.metrics.DefaultNetworkEvent defaultNetworkEvent, android.net.Network network, android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities) {
        defaultNetworkEvent.netId = network.getNetId();
        defaultNetworkEvent.transports = (int) (defaultNetworkEvent.transports | com.android.internal.util.BitUtils.packBits(networkCapabilities.getTransportTypes()));
        boolean z = false;
        defaultNetworkEvent.ipv4 |= linkProperties.hasIpv4Address() && linkProperties.hasIpv4DefaultRoute();
        boolean z2 = defaultNetworkEvent.ipv6;
        if (linkProperties.hasGlobalIpv6Address() && linkProperties.hasIpv6DefaultRoute()) {
            z = true;
        }
        defaultNetworkEvent.ipv6 = z2 | z;
    }

    private static void printEvent(long j, java.io.PrintWriter printWriter, android.net.metrics.DefaultNetworkEvent defaultNetworkEvent) {
        long j2 = j - defaultNetworkEvent.durationMs;
        printWriter.println(java.lang.String.format("%tT.%tL: %s", java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j2), defaultNetworkEvent));
    }
}
