package com.android.server.connectivity;

/* loaded from: classes.dex */
public final class IpConnectivityMetrics extends com.android.server.SystemService {
    private static final boolean DBG = false;
    private static final int DEFAULT_BUFFER_SIZE = 2000;
    private static final int DEFAULT_LOG_SIZE = 500;
    private static final int ERROR_RATE_LIMITED = -1;
    private static final int MAXIMUM_BUFFER_SIZE = 20000;
    private static final int MAXIMUM_CONNECT_LATENCY_RECORDS = 20000;
    private static final int NYC = 0;
    private static final int NYC_MR1 = 1;
    private static final int NYC_MR2 = 2;
    private static final java.lang.String SERVICE_NAME = "connmetrics";
    public static final int VERSION = 2;

    @com.android.internal.annotations.VisibleForTesting
    public final com.android.server.connectivity.IpConnectivityMetrics.Impl impl;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.Class<?>, com.android.internal.util.TokenBucket> mBuckets;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.ArrayList<android.net.ConnectivityMetricsEvent> mBuffer;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mCapacity;
    private final java.util.function.ToIntFunction<android.content.Context> mCapacityGetter;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.connectivity.DefaultNetworkMetrics mDefaultNetworkMetrics;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mDropped;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.internal.util.RingBuffer<android.net.ConnectivityMetricsEvent> mEventLog;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.connectivity.NetdEventListenerService mNetdListener;
    private static final java.lang.String TAG = com.android.server.connectivity.IpConnectivityMetrics.class.getSimpleName();
    private static final java.util.function.ToIntFunction<android.content.Context> READ_BUFFER_SIZE = new java.util.function.ToIntFunction() { // from class: com.android.server.connectivity.IpConnectivityMetrics$$ExternalSyntheticLambda1
        @Override // java.util.function.ToIntFunction
        public final int applyAsInt(java.lang.Object obj) {
            int lambda$static$1;
            lambda$static$1 = com.android.server.connectivity.IpConnectivityMetrics.lambda$static$1((android.content.Context) obj);
            return lambda$static$1;
        }
    };

    public interface Logger {
        com.android.server.connectivity.DefaultNetworkMetrics defaultNetworkMetrics();
    }

    public IpConnectivityMetrics(android.content.Context context, java.util.function.ToIntFunction<android.content.Context> toIntFunction) {
        super(context);
        this.mLock = new java.lang.Object();
        this.impl = new com.android.server.connectivity.IpConnectivityMetrics.Impl();
        this.mEventLog = new com.android.internal.util.RingBuffer<>(android.net.ConnectivityMetricsEvent.class, 500);
        this.mBuckets = makeRateLimitingBuckets();
        this.mDefaultNetworkMetrics = new com.android.server.connectivity.DefaultNetworkMetrics();
        this.mCapacityGetter = toIntFunction;
        initBuffer();
    }

    public IpConnectivityMetrics(android.content.Context context) {
        this(context, READ_BUFFER_SIZE);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            this.mNetdListener = new com.android.server.connectivity.NetdEventListenerService(getContext());
            publishBinderService(SERVICE_NAME, this.impl);
            publishBinderService(com.android.server.connectivity.NetdEventListenerService.SERVICE_NAME, this.mNetdListener);
            com.android.server.LocalServices.addService(com.android.server.connectivity.IpConnectivityMetrics.Logger.class, new com.android.server.connectivity.IpConnectivityMetrics.LoggerImpl());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public int bufferCapacity() {
        return this.mCapacityGetter.applyAsInt(getContext());
    }

    private void initBuffer() {
        synchronized (this.mLock) {
            this.mDropped = 0;
            this.mCapacity = bufferCapacity();
            this.mBuffer = new java.util.ArrayList<>(this.mCapacity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int append(android.net.ConnectivityMetricsEvent connectivityMetricsEvent) {
        synchronized (this.mLock) {
            try {
                this.mEventLog.append(connectivityMetricsEvent);
                int size = this.mCapacity - this.mBuffer.size();
                if (connectivityMetricsEvent == null) {
                    return size;
                }
                if (isRateLimited(connectivityMetricsEvent)) {
                    return -1;
                }
                if (size == 0) {
                    this.mDropped++;
                    return 0;
                }
                this.mBuffer.add(connectivityMetricsEvent);
                return size - 1;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isRateLimited(android.net.ConnectivityMetricsEvent connectivityMetricsEvent) {
        com.android.internal.util.TokenBucket tokenBucket = this.mBuckets.get(connectivityMetricsEvent.data.getClass());
        return (tokenBucket == null || tokenBucket.get()) ? false : true;
    }

    private java.lang.String flushEncodedOutput() {
        java.util.ArrayList<android.net.ConnectivityMetricsEvent> arrayList;
        int i;
        synchronized (this.mLock) {
            arrayList = this.mBuffer;
            i = this.mDropped;
            initBuffer();
        }
        java.util.List<com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent> proto = com.android.server.connectivity.IpConnectivityEventBuilder.toProto(arrayList);
        this.mDefaultNetworkMetrics.flushEvents(proto);
        if (this.mNetdListener != null) {
            this.mNetdListener.flushStatistics(proto);
        }
        try {
            return android.util.Base64.encodeToString(com.android.server.connectivity.IpConnectivityEventBuilder.serialize(i, proto), 0);
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "could not serialize events", e);
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cmdFlush(java.io.PrintWriter printWriter) {
        printWriter.print(flushEncodedOutput());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cmdList(java.io.PrintWriter printWriter) {
        printWriter.println("metrics events:");
        java.util.Iterator<android.net.ConnectivityMetricsEvent> it = getEvents().iterator();
        while (it.hasNext()) {
            printWriter.println(it.next().toString());
        }
        printWriter.println("");
        if (this.mNetdListener != null) {
            this.mNetdListener.list(printWriter);
        }
        printWriter.println("");
        this.mDefaultNetworkMetrics.listEvents(printWriter);
    }

    private java.util.List<com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent> listEventsAsProtos() {
        java.util.List<com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent> proto = com.android.server.connectivity.IpConnectivityEventBuilder.toProto(getEvents());
        if (this.mNetdListener != null) {
            proto.addAll(this.mNetdListener.listAsProtos());
        }
        proto.addAll(this.mDefaultNetworkMetrics.listEventsAsProto());
        return proto;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cmdListAsTextProto(final java.io.PrintWriter printWriter) {
        listEventsAsProtos().forEach(new java.util.function.Consumer() { // from class: com.android.server.connectivity.IpConnectivityMetrics$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.connectivity.IpConnectivityMetrics.lambda$cmdListAsTextProto$0(printWriter, (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$cmdListAsTextProto$0(java.io.PrintWriter printWriter, com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent) {
        printWriter.print(ipConnectivityEvent.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cmdListAsBinaryProto(java.io.OutputStream outputStream) {
        int i;
        synchronized (this.mLock) {
            i = this.mDropped;
        }
        try {
            outputStream.write(com.android.server.connectivity.IpConnectivityEventBuilder.serialize(i, listEventsAsProtos()));
            outputStream.flush();
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "could not serialize events", e);
        }
    }

    private java.util.List<android.net.ConnectivityMetricsEvent> getEvents() {
        java.util.List<android.net.ConnectivityMetricsEvent> asList;
        synchronized (this.mLock) {
            asList = java.util.Arrays.asList((android.net.ConnectivityMetricsEvent[]) this.mEventLog.toArray());
        }
        return asList;
    }

    public final class Impl extends android.net.IIpConnectivityMetrics.Stub {
        static final java.lang.String CMD_DEFAULT = "";
        static final java.lang.String CMD_FLUSH = "flush";
        static final java.lang.String CMD_LIST = "list";
        static final java.lang.String CMD_PROTO = "proto";
        static final java.lang.String CMD_PROTO_BIN = "--proto";

        public Impl() {
        }

        public int logEvent(android.net.ConnectivityMetricsEvent connectivityMetricsEvent) {
            android.net.NetworkStack.checkNetworkStackPermission(com.android.server.connectivity.IpConnectivityMetrics.this.getContext());
            return com.android.server.connectivity.IpConnectivityMetrics.this.append(connectivityMetricsEvent);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0025, code lost:
        
            if (r5.equals(com.android.server.connectivity.IpConnectivityMetrics.Impl.CMD_FLUSH) != false) goto L22;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            enforceDumpPermission();
            char c = 0;
            java.lang.String str = strArr.length > 0 ? strArr[0] : "";
            switch (str.hashCode()) {
                case -1616754616:
                    if (str.equals("--proto")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3322014:
                    if (str.equals(CMD_LIST)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 97532676:
                    break;
                case 106940904:
                    if (str.equals(CMD_PROTO)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    com.android.server.connectivity.IpConnectivityMetrics.this.cmdFlush(printWriter);
                    break;
                case 1:
                    com.android.server.connectivity.IpConnectivityMetrics.this.cmdListAsTextProto(printWriter);
                    break;
                case 2:
                    com.android.server.connectivity.IpConnectivityMetrics.this.cmdListAsBinaryProto(new java.io.FileOutputStream(fileDescriptor));
                    break;
                default:
                    com.android.server.connectivity.IpConnectivityMetrics.this.cmdList(printWriter);
                    break;
            }
        }

        private void enforceDumpPermission() {
            enforcePermission("android.permission.DUMP");
        }

        private void enforcePermission(java.lang.String str) {
            com.android.server.connectivity.IpConnectivityMetrics.this.getContext().enforceCallingOrSelfPermission(str, "IpConnectivityMetrics");
        }

        private void enforceNetdEventListeningPermission() {
            int callingUid = android.os.Binder.getCallingUid();
            if (callingUid != 1000) {
                throw new java.lang.SecurityException(java.lang.String.format("Uid %d has no permission to listen for netd events.", java.lang.Integer.valueOf(callingUid)));
            }
        }

        public boolean addNetdEventCallback(int i, android.net.INetdEventCallback iNetdEventCallback) {
            enforceNetdEventListeningPermission();
            if (com.android.server.connectivity.IpConnectivityMetrics.this.mNetdListener == null) {
                return false;
            }
            return com.android.server.connectivity.IpConnectivityMetrics.this.mNetdListener.addNetdEventCallback(i, iNetdEventCallback);
        }

        public boolean removeNetdEventCallback(int i) {
            enforceNetdEventListeningPermission();
            if (com.android.server.connectivity.IpConnectivityMetrics.this.mNetdListener == null) {
                return true;
            }
            return com.android.server.connectivity.IpConnectivityMetrics.this.mNetdListener.removeNetdEventCallback(i);
        }

        public void logDefaultNetworkValidity(boolean z) {
            android.net.NetworkStack.checkNetworkStackPermission(com.android.server.connectivity.IpConnectivityMetrics.this.getContext());
            com.android.server.connectivity.IpConnectivityMetrics.this.mDefaultNetworkMetrics.logDefaultNetworkValidity(android.os.SystemClock.elapsedRealtime(), z);
        }

        public void logDefaultNetworkEvent(android.net.Network network, int i, boolean z, android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities, android.net.Network network2, int i2, android.net.LinkProperties linkProperties2, android.net.NetworkCapabilities networkCapabilities2) {
            android.net.NetworkStack.checkNetworkStackPermission(com.android.server.connectivity.IpConnectivityMetrics.this.getContext());
            com.android.server.connectivity.IpConnectivityMetrics.this.mDefaultNetworkMetrics.logDefaultNetworkEvent(android.os.SystemClock.elapsedRealtime(), network, i, z, linkProperties, networkCapabilities, network2, i2, linkProperties2, networkCapabilities2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$static$1(android.content.Context context) {
        int i = android.provider.Settings.Global.getInt(context.getContentResolver(), "connectivity_metrics_buffer_size", 2000);
        if (i <= 0) {
            return 2000;
        }
        return java.lang.Math.min(i, 20000);
    }

    private static android.util.ArrayMap<java.lang.Class<?>, com.android.internal.util.TokenBucket> makeRateLimitingBuckets() {
        android.util.ArrayMap<java.lang.Class<?>, com.android.internal.util.TokenBucket> arrayMap = new android.util.ArrayMap<>();
        arrayMap.put(android.net.metrics.ApfProgramEvent.class, new com.android.internal.util.TokenBucket(60000, 50));
        return arrayMap;
    }

    private class LoggerImpl implements com.android.server.connectivity.IpConnectivityMetrics.Logger {
        private LoggerImpl() {
        }

        @Override // com.android.server.connectivity.IpConnectivityMetrics.Logger
        public com.android.server.connectivity.DefaultNetworkMetrics defaultNetworkMetrics() {
            return com.android.server.connectivity.IpConnectivityMetrics.this.mDefaultNetworkMetrics;
        }
    }
}
