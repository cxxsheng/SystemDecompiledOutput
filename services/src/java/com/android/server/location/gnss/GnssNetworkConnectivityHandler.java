package com.android.server.location.gnss;

/* loaded from: classes2.dex */
class GnssNetworkConnectivityHandler {
    private static final int AGNSS_NET_CAPABILITY_NOT_METERED = 1;
    private static final int AGNSS_NET_CAPABILITY_NOT_ROAMING = 2;
    private static final int AGPS_DATA_CONNECTION_CLOSED = 0;
    private static final int AGPS_DATA_CONNECTION_OPEN = 2;
    private static final int AGPS_DATA_CONNECTION_OPENING = 1;
    public static final int AGPS_TYPE_C2K = 2;
    private static final int AGPS_TYPE_EIMS = 3;
    private static final int AGPS_TYPE_IMS = 4;
    public static final int AGPS_TYPE_SUPL = 1;
    private static final int APN_INVALID = 0;
    private static final int APN_IPV4 = 1;
    private static final int APN_IPV4V6 = 3;
    private static final int APN_IPV6 = 2;
    private static final int GPS_AGPS_DATA_CONNECTED = 3;
    private static final int GPS_AGPS_DATA_CONN_DONE = 4;
    private static final int GPS_AGPS_DATA_CONN_FAILED = 5;
    private static final int GPS_RELEASE_AGPS_DATA_CONN = 2;
    private static final int GPS_REQUEST_AGPS_DATA_CONN = 1;
    private static final int HASH_MAP_INITIAL_CAPACITY_TO_TRACK_CONNECTED_NETWORKS = 5;
    private static final int SUPL_NETWORK_REQUEST_TIMEOUT_MILLIS = 20000;
    static final java.lang.String TAG = "GnssNetworkConnectivityHandler";
    private static final java.lang.String WAKELOCK_KEY = "GnssNetworkConnectivityHandler";
    private static final long WAKELOCK_TIMEOUT_MILLIS = 60000;
    private java.net.InetAddress mAGpsDataConnectionIpAddr;
    private int mAGpsDataConnectionState;
    private int mAGpsType;
    private final android.net.ConnectivityManager mConnMgr;
    private final android.content.Context mContext;
    private final com.android.server.location.gnss.GnssNetworkConnectivityHandler.GnssNetworkListener mGnssNetworkListener;
    private final android.os.Handler mHandler;
    private android.net.ConnectivityManager.NetworkCallback mNetworkConnectivityCallback;
    private final com.android.internal.location.GpsNetInitiatedHandler mNiHandler;
    private java.util.HashMap<java.lang.Integer, com.android.server.location.gnss.GnssNetworkConnectivityHandler.SubIdPhoneStateListener> mPhoneStateListeners;
    private android.net.ConnectivityManager.NetworkCallback mSuplConnectivityCallback;
    private final android.os.PowerManager.WakeLock mWakeLock;
    private static final boolean DEBUG = android.util.Log.isLoggable("GnssNetworkConnectivityHandler", 3);
    private static final boolean VERBOSE = android.util.Log.isLoggable("GnssNetworkConnectivityHandler", 2);
    private static final long SUPL_CONNECTION_TIMEOUT_MILLIS = java.util.concurrent.TimeUnit.MINUTES.toMillis(1);
    private java.util.HashMap<android.net.Network, com.android.server.location.gnss.GnssNetworkConnectivityHandler.NetworkAttributes> mAvailableNetworkAttributes = new java.util.HashMap<>(5);
    private int mActiveSubId = -1;
    private final java.lang.Object mSuplConnectionReleaseOnTimeoutToken = new java.lang.Object();
    private final android.telephony.SubscriptionManager.OnSubscriptionsChangedListener mOnSubscriptionsChangeListener = new android.telephony.SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.server.location.gnss.GnssNetworkConnectivityHandler.1
        @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
        public void onSubscriptionsChanged() {
            android.telephony.TelephonyManager createForSubscriptionId;
            if (com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.mPhoneStateListeners == null) {
                com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.mPhoneStateListeners = new java.util.HashMap(2, 1.0f);
            }
            android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.mContext.getSystemService(android.telephony.SubscriptionManager.class);
            android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.mContext.getSystemService(android.telephony.TelephonyManager.class);
            if (subscriptionManager != null && telephonyManager != null) {
                java.util.List<android.telephony.SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager.createForAllUserProfiles().getActiveSubscriptionInfoList();
                java.util.HashSet hashSet = new java.util.HashSet();
                if (activeSubscriptionInfoList != null) {
                    if (com.android.server.location.gnss.GnssNetworkConnectivityHandler.DEBUG) {
                        android.util.Log.d("GnssNetworkConnectivityHandler", "Active Sub List size: " + activeSubscriptionInfoList.size());
                    }
                    for (android.telephony.SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                        hashSet.add(java.lang.Integer.valueOf(subscriptionInfo.getSubscriptionId()));
                        if (!com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.mPhoneStateListeners.containsKey(java.lang.Integer.valueOf(subscriptionInfo.getSubscriptionId())) && (createForSubscriptionId = telephonyManager.createForSubscriptionId(subscriptionInfo.getSubscriptionId())) != null) {
                            if (com.android.server.location.gnss.GnssNetworkConnectivityHandler.DEBUG) {
                                android.util.Log.d("GnssNetworkConnectivityHandler", "Listener sub" + subscriptionInfo.getSubscriptionId());
                            }
                            com.android.server.location.gnss.GnssNetworkConnectivityHandler.SubIdPhoneStateListener subIdPhoneStateListener = com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.new SubIdPhoneStateListener(java.lang.Integer.valueOf(subscriptionInfo.getSubscriptionId()));
                            com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.mPhoneStateListeners.put(java.lang.Integer.valueOf(subscriptionInfo.getSubscriptionId()), subIdPhoneStateListener);
                            createForSubscriptionId.listen(subIdPhoneStateListener, 2048);
                        }
                    }
                }
                java.util.Iterator it = com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.mPhoneStateListeners.entrySet().iterator();
                while (it.hasNext()) {
                    java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                    if (!hashSet.contains(entry.getKey())) {
                        android.telephony.TelephonyManager createForSubscriptionId2 = telephonyManager.createForSubscriptionId(((java.lang.Integer) entry.getKey()).intValue());
                        if (createForSubscriptionId2 == null) {
                            android.util.Log.e("GnssNetworkConnectivityHandler", "Telephony Manager for Sub " + entry.getKey() + " null");
                        } else {
                            if (com.android.server.location.gnss.GnssNetworkConnectivityHandler.DEBUG) {
                                android.util.Log.d("GnssNetworkConnectivityHandler", "unregister listener sub " + entry.getKey());
                            }
                            createForSubscriptionId2.listen((android.telephony.PhoneStateListener) entry.getValue(), 0);
                            it.remove();
                        }
                    }
                }
                if (!hashSet.contains(java.lang.Integer.valueOf(com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.mActiveSubId))) {
                    com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.mActiveSubId = -1;
                }
            }
        }
    };

    interface GnssNetworkListener {
        void onNetworkAvailable();
    }

    private native void native_agps_data_conn_closed();

    private native void native_agps_data_conn_failed();

    private native void native_agps_data_conn_open(long j, java.lang.String str, int i);

    private static native boolean native_is_agps_ril_supported();

    private native void native_update_network_state(boolean z, int i, boolean z2, boolean z3, java.lang.String str, long j, short s);

    private static class NetworkAttributes {
        private java.lang.String mApn;
        private android.net.NetworkCapabilities mCapabilities;
        private int mType;

        private NetworkAttributes() {
            this.mType = -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean hasCapabilitiesChanged(android.net.NetworkCapabilities networkCapabilities, android.net.NetworkCapabilities networkCapabilities2) {
            return networkCapabilities == null || networkCapabilities2 == null || hasCapabilityChanged(networkCapabilities, networkCapabilities2, 18) || hasCapabilityChanged(networkCapabilities, networkCapabilities2, 11);
        }

        private static boolean hasCapabilityChanged(android.net.NetworkCapabilities networkCapabilities, android.net.NetworkCapabilities networkCapabilities2, int i) {
            return networkCapabilities.hasCapability(i) != networkCapabilities2.hasCapability(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static short getCapabilityFlags(android.net.NetworkCapabilities networkCapabilities) {
            short s;
            if (!networkCapabilities.hasCapability(18)) {
                s = 0;
            } else {
                s = (short) 2;
            }
            if (networkCapabilities.hasCapability(11)) {
                return (short) (s | 1);
            }
            return s;
        }
    }

    GnssNetworkConnectivityHandler(android.content.Context context, com.android.server.location.gnss.GnssNetworkConnectivityHandler.GnssNetworkListener gnssNetworkListener, android.os.Looper looper, com.android.internal.location.GpsNetInitiatedHandler gpsNetInitiatedHandler) {
        this.mContext = context;
        this.mGnssNetworkListener = gnssNetworkListener;
        android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) this.mContext.getSystemService(android.telephony.SubscriptionManager.class);
        if (subscriptionManager != null) {
            subscriptionManager.addOnSubscriptionsChangedListener(this.mOnSubscriptionsChangeListener);
        }
        this.mWakeLock = ((android.os.PowerManager) context.getSystemService("power")).newWakeLock(1, "GnssNetworkConnectivityHandler");
        this.mHandler = new android.os.Handler(looper);
        this.mNiHandler = gpsNetInitiatedHandler;
        this.mConnMgr = (android.net.ConnectivityManager) this.mContext.getSystemService("connectivity");
        this.mSuplConnectivityCallback = null;
    }

    private final class SubIdPhoneStateListener extends android.telephony.PhoneStateListener {
        private java.lang.Integer mSubId;

        SubIdPhoneStateListener(java.lang.Integer num) {
            this.mSubId = num;
        }

        public void onPreciseCallStateChanged(android.telephony.PreciseCallState preciseCallState) {
            if (1 == preciseCallState.getForegroundCallState() || 3 == preciseCallState.getForegroundCallState()) {
                com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.mActiveSubId = this.mSubId.intValue();
                if (com.android.server.location.gnss.GnssNetworkConnectivityHandler.DEBUG) {
                    android.util.Log.d("GnssNetworkConnectivityHandler", "mActiveSubId: " + com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.mActiveSubId);
                }
            }
        }
    }

    void registerNetworkCallbacks() {
        android.net.NetworkRequest.Builder builder = new android.net.NetworkRequest.Builder();
        builder.addCapability(12);
        builder.addCapability(16);
        builder.removeCapability(15);
        android.net.NetworkRequest build = builder.build();
        this.mNetworkConnectivityCallback = createNetworkConnectivityCallback();
        this.mConnMgr.registerNetworkCallback(build, this.mNetworkConnectivityCallback, this.mHandler);
    }

    void unregisterNetworkCallbacks() {
        this.mConnMgr.unregisterNetworkCallback(this.mNetworkConnectivityCallback);
        this.mNetworkConnectivityCallback = null;
    }

    boolean isDataNetworkConnected() {
        android.net.NetworkInfo activeNetworkInfo = this.mConnMgr.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    int getActiveSubId() {
        return this.mActiveSubId;
    }

    void onReportAGpsStatus(final int i, int i2, final byte[] bArr) {
        if (DEBUG) {
            android.util.Log.d("GnssNetworkConnectivityHandler", "AGPS_DATA_CONNECTION: " + agpsDataConnStatusAsString(i2));
        }
        switch (i2) {
            case 1:
                runOnHandler(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssNetworkConnectivityHandler$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.lambda$onReportAGpsStatus$0(i, bArr);
                    }
                });
                break;
            case 2:
                runOnHandler(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssNetworkConnectivityHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.lambda$onReportAGpsStatus$1();
                    }
                });
                break;
            case 3:
            case 4:
            case 5:
                break;
            default:
                android.util.Log.w("GnssNetworkConnectivityHandler", "Received unknown AGPS status: " + i2);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReportAGpsStatus$1() {
        handleReleaseSuplConnection(2);
    }

    private android.net.ConnectivityManager.NetworkCallback createNetworkConnectivityCallback() {
        return new android.net.ConnectivityManager.NetworkCallback() { // from class: com.android.server.location.gnss.GnssNetworkConnectivityHandler.2
            private java.util.HashMap<android.net.Network, android.net.NetworkCapabilities> mAvailableNetworkCapabilities = new java.util.HashMap<>(5);

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onCapabilitiesChanged(android.net.Network network, android.net.NetworkCapabilities networkCapabilities) {
                if (!com.android.server.location.gnss.GnssNetworkConnectivityHandler.NetworkAttributes.hasCapabilitiesChanged(this.mAvailableNetworkCapabilities.get(network), networkCapabilities)) {
                    if (com.android.server.location.gnss.GnssNetworkConnectivityHandler.VERBOSE) {
                        android.util.Log.v("GnssNetworkConnectivityHandler", "Relevant network capabilities unchanged. Capabilities: " + networkCapabilities);
                        return;
                    }
                    return;
                }
                this.mAvailableNetworkCapabilities.put(network, networkCapabilities);
                if (com.android.server.location.gnss.GnssNetworkConnectivityHandler.DEBUG) {
                    android.util.Log.d("GnssNetworkConnectivityHandler", "Network connected/capabilities updated. Available networks count: " + this.mAvailableNetworkCapabilities.size());
                }
                com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.mGnssNetworkListener.onNetworkAvailable();
                com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.handleUpdateNetworkState(network, true, networkCapabilities);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(android.net.Network network) {
                if (this.mAvailableNetworkCapabilities.remove(network) == null) {
                    android.util.Log.w("GnssNetworkConnectivityHandler", "Incorrectly received network callback onLost() before onCapabilitiesChanged() for network: " + network);
                    return;
                }
                android.util.Log.i("GnssNetworkConnectivityHandler", "Network connection lost. Available networks count: " + this.mAvailableNetworkCapabilities.size());
                com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.handleUpdateNetworkState(network, false, null);
            }
        };
    }

    private android.net.ConnectivityManager.NetworkCallback createSuplConnectivityCallback() {
        return new android.net.ConnectivityManager.NetworkCallback() { // from class: com.android.server.location.gnss.GnssNetworkConnectivityHandler.3
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLinkPropertiesChanged(android.net.Network network, android.net.LinkProperties linkProperties) {
                if (com.android.server.location.gnss.GnssNetworkConnectivityHandler.DEBUG) {
                    android.util.Log.d("GnssNetworkConnectivityHandler", "SUPL network connection available.");
                }
                com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.handleSuplConnectionAvailable(network, linkProperties);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(android.net.Network network) {
                android.util.Log.i("GnssNetworkConnectivityHandler", "SUPL network connection lost.");
                com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.handleReleaseSuplConnection(2);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onUnavailable() {
                android.util.Log.i("GnssNetworkConnectivityHandler", "SUPL network connection request timed out.");
                com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.handleReleaseSuplConnection(5);
            }
        };
    }

    private void runOnHandler(java.lang.Runnable runnable) {
        this.mWakeLock.acquire(60000L);
        if (!this.mHandler.post(runEventAndReleaseWakeLock(runnable))) {
            this.mWakeLock.release();
        }
    }

    private java.lang.Runnable runEventAndReleaseWakeLock(final java.lang.Runnable runnable) {
        return new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssNetworkConnectivityHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.lambda$runEventAndReleaseWakeLock$2(runnable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runEventAndReleaseWakeLock$2(java.lang.Runnable runnable) {
        try {
            runnable.run();
        } finally {
            this.mWakeLock.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUpdateNetworkState(android.net.Network network, boolean z, android.net.NetworkCapabilities networkCapabilities) {
        boolean z2;
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class);
        boolean z3 = false;
        if (telephonyManager == null) {
            z2 = false;
        } else {
            if (z && telephonyManager.getDataEnabled()) {
                z3 = true;
            }
            z2 = z3;
        }
        com.android.server.location.gnss.GnssNetworkConnectivityHandler.NetworkAttributes updateTrackedNetworksState = updateTrackedNetworksState(z, network, networkCapabilities);
        java.lang.String str = updateTrackedNetworksState.mApn;
        int i = updateTrackedNetworksState.mType;
        android.net.NetworkCapabilities networkCapabilities2 = updateTrackedNetworksState.mCapabilities;
        android.util.Log.i("GnssNetworkConnectivityHandler", java.lang.String.format("updateNetworkState, state=%s, connected=%s, network=%s, capabilityFlags=%d, availableNetworkCount: %d", agpsDataConnStateAsString(), java.lang.Boolean.valueOf(z), network, java.lang.Short.valueOf(com.android.server.location.gnss.GnssNetworkConnectivityHandler.NetworkAttributes.getCapabilityFlags(networkCapabilities2)), java.lang.Integer.valueOf(this.mAvailableNetworkAttributes.size())));
        if (native_is_agps_ril_supported()) {
            boolean z4 = !networkCapabilities2.hasTransport(18);
            if (str == null) {
                str = "";
            }
            native_update_network_state(z, i, z4, z2, str, network.getNetworkHandle(), com.android.server.location.gnss.GnssNetworkConnectivityHandler.NetworkAttributes.getCapabilityFlags(networkCapabilities2));
            return;
        }
        if (DEBUG) {
            android.util.Log.d("GnssNetworkConnectivityHandler", "Skipped network state update because GPS HAL AGPS-RIL is not  supported");
        }
    }

    private com.android.server.location.gnss.GnssNetworkConnectivityHandler.NetworkAttributes updateTrackedNetworksState(boolean z, android.net.Network network, android.net.NetworkCapabilities networkCapabilities) {
        if (!z) {
            return this.mAvailableNetworkAttributes.remove(network);
        }
        com.android.server.location.gnss.GnssNetworkConnectivityHandler.NetworkAttributes networkAttributes = this.mAvailableNetworkAttributes.get(network);
        if (networkAttributes != null) {
            networkAttributes.mCapabilities = networkCapabilities;
            return networkAttributes;
        }
        com.android.server.location.gnss.GnssNetworkConnectivityHandler.NetworkAttributes networkAttributes2 = new com.android.server.location.gnss.GnssNetworkConnectivityHandler.NetworkAttributes();
        networkAttributes2.mCapabilities = networkCapabilities;
        android.net.NetworkInfo networkInfo = this.mConnMgr.getNetworkInfo(network);
        if (networkInfo != null) {
            networkAttributes2.mApn = networkInfo.getExtraInfo();
            networkAttributes2.mType = networkInfo.getType();
        }
        this.mAvailableNetworkAttributes.put(network, networkAttributes2);
        return networkAttributes2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSuplConnectionAvailable(android.net.Network network, android.net.LinkProperties linkProperties) {
        java.lang.String str;
        android.net.NetworkInfo networkInfo = this.mConnMgr.getNetworkInfo(network);
        if (networkInfo == null) {
            str = null;
        } else {
            str = networkInfo.getExtraInfo();
        }
        if (DEBUG) {
            android.util.Log.d("GnssNetworkConnectivityHandler", java.lang.String.format("handleSuplConnectionAvailable: state=%s, suplNetwork=%s, info=%s", agpsDataConnStateAsString(), network, networkInfo));
        }
        if (this.mAGpsDataConnectionState == 1) {
            if (str == null) {
                str = "dummy-apn";
            }
            if (this.mAGpsDataConnectionIpAddr != null) {
                setRouting();
            }
            int linkIpType = getLinkIpType(linkProperties);
            if (DEBUG) {
                android.util.Log.d("GnssNetworkConnectivityHandler", java.lang.String.format("native_agps_data_conn_open: mAgpsApn=%s, mApnIpType=%s", str, java.lang.Integer.valueOf(linkIpType)));
            }
            native_agps_data_conn_open(network.getNetworkHandle(), str, linkIpType);
            this.mAGpsDataConnectionState = 2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleRequestSuplConnection, reason: merged with bridge method [inline-methods] */
    public void lambda$onReportAGpsStatus$0(int i, byte[] bArr) {
        this.mAGpsDataConnectionIpAddr = null;
        this.mAGpsType = i;
        if (bArr != null) {
            if (VERBOSE) {
                android.util.Log.v("GnssNetworkConnectivityHandler", "Received SUPL IP addr[]: " + java.util.Arrays.toString(bArr));
            }
            try {
                this.mAGpsDataConnectionIpAddr = java.net.InetAddress.getByAddress(bArr);
                if (DEBUG) {
                    android.util.Log.d("GnssNetworkConnectivityHandler", "IP address converted to: " + this.mAGpsDataConnectionIpAddr);
                }
            } catch (java.net.UnknownHostException e) {
                android.util.Log.e("GnssNetworkConnectivityHandler", "Bad IP Address: " + java.util.Arrays.toString(bArr), e);
            }
        }
        if (DEBUG) {
            android.util.Log.d("GnssNetworkConnectivityHandler", java.lang.String.format("requestSuplConnection, state=%s, agpsType=%s, address=%s", agpsDataConnStateAsString(), agpsTypeAsString(i), this.mAGpsDataConnectionIpAddr));
        }
        if (this.mAGpsDataConnectionState != 0) {
            return;
        }
        this.mAGpsDataConnectionState = 1;
        android.net.NetworkRequest.Builder builder = new android.net.NetworkRequest.Builder();
        builder.addCapability(getNetworkCapability(this.mAGpsType));
        builder.addTransportType(0);
        if (this.mNiHandler.getInEmergency() && this.mActiveSubId >= 0) {
            if (DEBUG) {
                android.util.Log.d("GnssNetworkConnectivityHandler", "Adding Network Specifier: " + java.lang.Integer.toString(this.mActiveSubId));
            }
            builder.setNetworkSpecifier(java.lang.Integer.toString(this.mActiveSubId));
            builder.removeCapability(13);
        }
        android.net.NetworkRequest build = builder.build();
        if (this.mSuplConnectivityCallback != null) {
            this.mConnMgr.unregisterNetworkCallback(this.mSuplConnectivityCallback);
        }
        this.mSuplConnectivityCallback = createSuplConnectivityCallback();
        try {
            this.mConnMgr.requestNetwork(build, this.mSuplConnectivityCallback, this.mHandler, SUPL_NETWORK_REQUEST_TIMEOUT_MILLIS);
            if (android.location.flags.Flags.releaseSuplConnectionOnTimeout()) {
                this.mHandler.removeCallbacksAndMessages(this.mSuplConnectionReleaseOnTimeoutToken);
                this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssNetworkConnectivityHandler$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.gnss.GnssNetworkConnectivityHandler.this.lambda$handleRequestSuplConnection$3();
                    }
                }, this.mSuplConnectionReleaseOnTimeoutToken, SUPL_CONNECTION_TIMEOUT_MILLIS);
            }
        } catch (java.lang.RuntimeException e2) {
            android.util.Log.e("GnssNetworkConnectivityHandler", "Failed to request network.", e2);
            this.mSuplConnectivityCallback = null;
            handleReleaseSuplConnection(5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleRequestSuplConnection$3() {
        handleReleaseSuplConnection(2);
    }

    private int getNetworkCapability(int i) {
        switch (i) {
            case 1:
            case 2:
                return 1;
            case 3:
                return 10;
            case 4:
                return 4;
            default:
                throw new java.lang.IllegalArgumentException("agpsType: " + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReleaseSuplConnection(int i) {
        if (DEBUG) {
            android.util.Log.d("GnssNetworkConnectivityHandler", java.lang.String.format("releaseSuplConnection, state=%s, status=%s", agpsDataConnStateAsString(), agpsDataConnStatusAsString(i)));
        }
        if (android.location.flags.Flags.releaseSuplConnectionOnTimeout()) {
            this.mHandler.removeCallbacksAndMessages(this.mSuplConnectionReleaseOnTimeoutToken);
        }
        if (this.mAGpsDataConnectionState == 0) {
        }
        this.mAGpsDataConnectionState = 0;
        if (this.mSuplConnectivityCallback != null) {
            this.mConnMgr.unregisterNetworkCallback(this.mSuplConnectivityCallback);
            this.mSuplConnectivityCallback = null;
        }
        switch (i) {
            case 2:
                native_agps_data_conn_closed();
                break;
            case 5:
                native_agps_data_conn_failed();
                break;
            default:
                android.util.Log.e("GnssNetworkConnectivityHandler", "Invalid status to release SUPL connection: " + i);
                break;
        }
    }

    private void setRouting() {
        if (!this.mConnMgr.requestRouteToHostAddress(3, this.mAGpsDataConnectionIpAddr)) {
            android.util.Log.e("GnssNetworkConnectivityHandler", "Error requesting route to host: " + this.mAGpsDataConnectionIpAddr);
            return;
        }
        if (DEBUG) {
            android.util.Log.d("GnssNetworkConnectivityHandler", "Successfully requested route to host: " + this.mAGpsDataConnectionIpAddr);
        }
    }

    private void ensureInHandlerThread() {
        if (this.mHandler != null && android.os.Looper.myLooper() == this.mHandler.getLooper()) {
        } else {
            throw new java.lang.IllegalStateException("This method must run on the Handler thread.");
        }
    }

    private java.lang.String agpsDataConnStateAsString() {
        switch (this.mAGpsDataConnectionState) {
            case 0:
                return "CLOSED";
            case 1:
                return "OPENING";
            case 2:
                return "OPEN";
            default:
                return "<Unknown>(" + this.mAGpsDataConnectionState + ")";
        }
    }

    private java.lang.String agpsDataConnStatusAsString(int i) {
        switch (i) {
            case 1:
                return "REQUEST";
            case 2:
                return "RELEASE";
            case 3:
                return "CONNECTED";
            case 4:
                return "DONE";
            case 5:
                return "FAILED";
            default:
                return "<Unknown>(" + i + ")";
        }
    }

    private java.lang.String agpsTypeAsString(int i) {
        switch (i) {
            case 1:
                return "SUPL";
            case 2:
                return "C2K";
            case 3:
                return "EIMS";
            case 4:
                return "IMS";
            default:
                return "<Unknown>(" + i + ")";
        }
    }

    private int getLinkIpType(android.net.LinkProperties linkProperties) {
        ensureInHandlerThread();
        java.util.Iterator<android.net.LinkAddress> it = linkProperties.getLinkAddresses().iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            java.net.InetAddress address = it.next().getAddress();
            if (address instanceof java.net.Inet4Address) {
                z = true;
            } else if (address instanceof java.net.Inet6Address) {
                z2 = true;
            }
            if (DEBUG) {
                android.util.Log.d("GnssNetworkConnectivityHandler", "LinkAddress : " + address.toString());
            }
        }
        if (z && z2) {
            return 3;
        }
        if (z) {
            return 1;
        }
        return z2 ? 2 : 0;
    }

    protected boolean isNativeAgpsRilSupported() {
        return native_is_agps_ril_supported();
    }
}
