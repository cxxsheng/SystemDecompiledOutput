package android.net.wifi;

/* loaded from: classes2.dex */
public class WifiNetworkScoreCache extends android.net.INetworkScoreCache.Stub {
    private static final int DEFAULT_MAX_CACHE_SIZE = 100;
    public static final int INVALID_NETWORK_SCORE = -128;
    private final android.util.LruCache<java.lang.String, android.net.ScoredNetwork> mCache;
    private final android.content.Context mContext;
    private android.net.wifi.WifiNetworkScoreCache.CacheListener mListener;
    private final java.lang.Object mLock;
    private static final java.lang.String TAG = "WifiNetworkScoreCache";
    private static final boolean DBG = android.util.Log.isLoggable(TAG, 3);

    public WifiNetworkScoreCache(android.content.Context context) {
        this(context, null);
    }

    public WifiNetworkScoreCache(android.content.Context context, android.net.wifi.WifiNetworkScoreCache.CacheListener cacheListener) {
        this(context, cacheListener, 100);
    }

    public WifiNetworkScoreCache(android.content.Context context, android.net.wifi.WifiNetworkScoreCache.CacheListener cacheListener, int i) {
        this.mLock = new java.lang.Object();
        this.mContext = context.getApplicationContext();
        this.mListener = cacheListener;
        this.mCache = new android.util.LruCache<>(i);
    }

    @Override // android.net.INetworkScoreCache
    public final void updateScores(java.util.List<android.net.ScoredNetwork> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (DBG) {
            android.util.Log.d(TAG, "updateScores list size=" + list.size());
        }
        synchronized (this.mLock) {
            boolean z = false;
            for (android.net.ScoredNetwork scoredNetwork : list) {
                java.lang.String buildNetworkKey = buildNetworkKey(scoredNetwork);
                if (buildNetworkKey == null) {
                    if (DBG) {
                        android.util.Log.d(TAG, "Failed to build network key for ScoredNetwork" + scoredNetwork);
                    }
                } else {
                    this.mCache.put(buildNetworkKey, scoredNetwork);
                    z = true;
                }
            }
            if (this.mListener != null && z) {
                this.mListener.post(list);
            }
        }
    }

    @Override // android.net.INetworkScoreCache
    public final void clearScores() {
        synchronized (this.mLock) {
            this.mCache.evictAll();
        }
    }

    public boolean isScoredNetwork(android.net.wifi.ScanResult scanResult) {
        return getScoredNetwork(scanResult) != null;
    }

    public boolean hasScoreCurve(android.net.wifi.ScanResult scanResult) {
        android.net.ScoredNetwork scoredNetwork = getScoredNetwork(scanResult);
        return (scoredNetwork == null || scoredNetwork.rssiCurve == null) ? false : true;
    }

    public int getNetworkScore(android.net.wifi.ScanResult scanResult) {
        android.net.ScoredNetwork scoredNetwork = getScoredNetwork(scanResult);
        if (scoredNetwork != null && scoredNetwork.rssiCurve != null) {
            byte lookupScore = scoredNetwork.rssiCurve.lookupScore(scanResult.level);
            if (!DBG) {
                return lookupScore;
            }
            android.util.Log.d(TAG, "getNetworkScore found scored network " + scoredNetwork.networkKey + " score " + java.lang.Integer.toString(lookupScore) + " RSSI " + scanResult.level);
            return lookupScore;
        }
        return -128;
    }

    public boolean getMeteredHint(android.net.wifi.ScanResult scanResult) {
        android.net.ScoredNetwork scoredNetwork = getScoredNetwork(scanResult);
        return scoredNetwork != null && scoredNetwork.meteredHint;
    }

    public int getNetworkScore(android.net.wifi.ScanResult scanResult, boolean z) {
        android.net.ScoredNetwork scoredNetwork = getScoredNetwork(scanResult);
        if (scoredNetwork != null && scoredNetwork.rssiCurve != null) {
            byte lookupScore = scoredNetwork.rssiCurve.lookupScore(scanResult.level, z);
            if (!DBG) {
                return lookupScore;
            }
            android.util.Log.d(TAG, "getNetworkScore found scored network " + scoredNetwork.networkKey + " score " + java.lang.Integer.toString(lookupScore) + " RSSI " + scanResult.level + " isActiveNetwork " + z);
            return lookupScore;
        }
        return -128;
    }

    public android.net.ScoredNetwork getScoredNetwork(android.net.wifi.ScanResult scanResult) {
        android.net.ScoredNetwork scoredNetwork;
        java.lang.String buildNetworkKey = buildNetworkKey(scanResult);
        if (buildNetworkKey == null) {
            return null;
        }
        synchronized (this.mLock) {
            scoredNetwork = this.mCache.get(buildNetworkKey);
        }
        return scoredNetwork;
    }

    public android.net.ScoredNetwork getScoredNetwork(android.net.NetworkKey networkKey) {
        android.net.ScoredNetwork scoredNetwork;
        java.lang.String buildNetworkKey = buildNetworkKey(networkKey);
        if (buildNetworkKey == null) {
            if (DBG) {
                android.util.Log.d(TAG, "Could not build key string for Network Key: " + networkKey);
                return null;
            }
            return null;
        }
        synchronized (this.mLock) {
            scoredNetwork = this.mCache.get(buildNetworkKey);
        }
        return scoredNetwork;
    }

    private java.lang.String buildNetworkKey(android.net.ScoredNetwork scoredNetwork) {
        if (scoredNetwork == null) {
            return null;
        }
        return buildNetworkKey(scoredNetwork.networkKey);
    }

    private java.lang.String buildNetworkKey(android.net.NetworkKey networkKey) {
        java.lang.String str;
        if (networkKey == null || networkKey.wifiKey == null || networkKey.type != 1 || (str = networkKey.wifiKey.ssid) == null) {
            return null;
        }
        if (networkKey.wifiKey.bssid != null) {
            return str + networkKey.wifiKey.bssid;
        }
        return str;
    }

    private java.lang.String buildNetworkKey(android.net.wifi.ScanResult scanResult) {
        if (scanResult == null || scanResult.SSID == null) {
            return null;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder("\"");
        sb.append(scanResult.SSID);
        sb.append("\"");
        if (scanResult.BSSID != null) {
            sb.append(scanResult.BSSID);
        }
        return sb.toString();
    }

    @Override // android.os.Binder
    protected final void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        this.mContext.enforceCallingOrSelfPermission(android.Manifest.permission.DUMP, TAG);
        printWriter.println(java.lang.String.format("WifiNetworkScoreCache (%s/%d)", this.mContext.getPackageName(), java.lang.Integer.valueOf(android.os.Process.myUid())));
        printWriter.println("  All score curves:");
        synchronized (this.mLock) {
            java.util.Iterator<android.net.ScoredNetwork> it = this.mCache.snapshot().values().iterator();
            while (it.hasNext()) {
                printWriter.println("    " + it.next());
            }
            printWriter.println("  Network scores for latest ScanResults:");
            for (android.net.wifi.ScanResult scanResult : ((android.net.wifi.WifiManager) this.mContext.getSystemService("wifi")).getScanResults()) {
                printWriter.println("    " + buildNetworkKey(scanResult) + ": " + getNetworkScore(scanResult));
            }
        }
    }

    public void registerListener(android.net.wifi.WifiNetworkScoreCache.CacheListener cacheListener) {
        synchronized (this.mLock) {
            this.mListener = cacheListener;
        }
    }

    public void unregisterListener() {
        synchronized (this.mLock) {
            this.mListener = null;
        }
    }

    public static abstract class CacheListener {
        private android.os.Handler mHandler;

        public abstract void networkCacheUpdated(java.util.List<android.net.ScoredNetwork> list);

        public CacheListener(android.os.Handler handler) {
            java.util.Objects.requireNonNull(handler);
            this.mHandler = handler;
        }

        void post(final java.util.List<android.net.ScoredNetwork> list) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.net.wifi.WifiNetworkScoreCache.CacheListener.1
                @Override // java.lang.Runnable
                public void run() {
                    android.net.wifi.WifiNetworkScoreCache.CacheListener.this.networkCacheUpdated(list);
                }
            });
        }
    }
}
