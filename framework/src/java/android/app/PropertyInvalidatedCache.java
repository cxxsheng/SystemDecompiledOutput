package android.app;

/* loaded from: classes.dex */
public class PropertyInvalidatedCache<Query, Result> {
    private static final boolean DEBUG = false;
    public static final java.lang.String MODULE_BLUETOOTH = "bluetooth";
    public static final java.lang.String MODULE_SYSTEM = "system_server";
    public static final java.lang.String MODULE_TELEPHONY = "telephony";
    public static final java.lang.String MODULE_TEST = "test";
    static final java.lang.String NAME_CONTAINS = "-name-has=";
    static final java.lang.String NAME_LIKE = "-name-like=";
    private static final int NONCE_BYPASS = 3;
    private static final int NONCE_CORKED = 2;
    private static final int NONCE_DISABLED = 1;
    private static final int NONCE_UNSET = 0;
    static final java.lang.String PROPERTY_CONTAINS = "-property-has=";
    private static final int PROPERTY_FAILURE_RETRY_DELAY_MILLIS = 200;
    private static final int PROPERTY_FAILURE_RETRY_LIMIT = 5;
    static final java.lang.String PROPERTY_LIKE = "-property-like=";
    private static final java.lang.String TAG = "PropertyInvalidatedCache";
    private static final boolean VERIFY = false;
    private final java.util.LinkedHashMap<Query, Result> mCache;
    private final java.lang.String mCacheName;
    private long mClears;
    private android.app.PropertyInvalidatedCache.QueryHandler<Query, Result> mComputer;
    private boolean mDisabled;
    private long mHighWaterMark;
    private long mHits;
    private long mLastSeenNonce;
    private final java.lang.Object mLock;
    private final int mMaxEntries;
    private long mMissOverflow;
    private long mMisses;
    private volatile android.os.SystemProperties.Handle mPropertyHandle;
    private final java.lang.String mPropertyName;
    private long[] mSkips;
    private static final java.lang.String[] sNonceName = {"unset", "disabled", "corked", "bypass"};
    private static final java.lang.Object sCorkLock = new java.lang.Object();
    private static final java.util.HashMap<java.lang.String, java.lang.Long> sCorkedInvalidates = new java.util.HashMap<>();
    private static final java.util.HashMap<java.lang.String, java.lang.Integer> sCorks = new java.util.HashMap<>();
    private static final java.lang.Object sGlobalLock = new java.lang.Object();
    private static final java.util.HashSet<java.lang.String> sDisabledKeys = new java.util.HashSet<>();
    private static final java.util.WeakHashMap<android.app.PropertyInvalidatedCache, java.lang.Void> sCaches = new java.util.WeakHashMap<>();
    private static final java.util.HashMap<java.lang.String, java.lang.Long> sInvalidates = new java.util.HashMap<>();
    private static boolean sEnabled = true;
    private static volatile boolean sTesting = false;
    private static final java.util.HashMap<java.lang.String, java.lang.Long> sTestingPropertyMap = new java.util.HashMap<>();

    public static abstract class QueryHandler<Q, R> {
        public abstract R apply(Q q);

        public boolean shouldBypassCache(Q q) {
            return false;
        }
    }

    public static java.lang.String createPropertyName(java.lang.String str, java.lang.String str2) {
        int i;
        char[] charArray = str2.toCharArray();
        int i2 = 0;
        for (int i3 = 1; i3 < charArray.length; i3++) {
            if (java.lang.Character.isUpperCase(charArray[i3])) {
                i2++;
            }
        }
        char[] cArr = new char[charArray.length + i2];
        int i4 = 0;
        for (int i5 = 0; i5 < charArray.length; i5++) {
            if (java.lang.Character.isJavaIdentifierPart(charArray[i5])) {
                if (java.lang.Character.isUpperCase(charArray[i5])) {
                    if (i5 > 0) {
                        cArr[i4] = '_';
                        i4++;
                    }
                    i = i4 + 1;
                    cArr[i4] = java.lang.Character.toLowerCase(charArray[i5]);
                } else {
                    i = i4 + 1;
                    cArr[i4] = charArray[i5];
                }
                i4 = i;
            } else {
                throw new java.lang.IllegalArgumentException("invalid api name");
            }
        }
        return "cache_key." + str + android.media.MediaMetrics.SEPARATOR + new java.lang.String(cArr);
    }

    private static boolean isReservedNonce(long j) {
        return j >= 0 && j <= 3;
    }

    private static class DefaultComputer<Query, Result> extends android.app.PropertyInvalidatedCache.QueryHandler<Query, Result> {
        final android.app.PropertyInvalidatedCache<Query, Result> mCache;

        DefaultComputer(android.app.PropertyInvalidatedCache<Query, Result> propertyInvalidatedCache) {
            this.mCache = propertyInvalidatedCache;
        }

        @Override // android.app.PropertyInvalidatedCache.QueryHandler
        public Result apply(Query query) {
            return this.mCache.recompute(query);
        }
    }

    public PropertyInvalidatedCache(int i, java.lang.String str) {
        this(i, str, str);
    }

    public PropertyInvalidatedCache(int i, java.lang.String str, java.lang.String str2) {
        this.mLock = new java.lang.Object();
        this.mHits = 0L;
        this.mMisses = 0L;
        this.mSkips = new long[]{0, 0, 0, 0};
        this.mMissOverflow = 0L;
        this.mHighWaterMark = 0L;
        this.mClears = 0L;
        this.mLastSeenNonce = 0L;
        this.mDisabled = false;
        this.mPropertyName = str;
        this.mCacheName = str2;
        this.mMaxEntries = i;
        this.mComputer = new android.app.PropertyInvalidatedCache.DefaultComputer(this);
        this.mCache = createMap();
        registerCache();
    }

    public PropertyInvalidatedCache(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, android.app.PropertyInvalidatedCache.QueryHandler<Query, Result> queryHandler) {
        this.mLock = new java.lang.Object();
        this.mHits = 0L;
        this.mMisses = 0L;
        this.mSkips = new long[]{0, 0, 0, 0};
        this.mMissOverflow = 0L;
        this.mHighWaterMark = 0L;
        this.mClears = 0L;
        this.mLastSeenNonce = 0L;
        this.mDisabled = false;
        this.mPropertyName = createPropertyName(str, str2);
        this.mCacheName = str3;
        this.mMaxEntries = i;
        this.mComputer = queryHandler;
        this.mCache = createMap();
        registerCache();
    }

    private java.util.LinkedHashMap<Query, Result> createMap() {
        return new java.util.LinkedHashMap<Query, Result>(2, 0.75f, true) { // from class: android.app.PropertyInvalidatedCache.1
            @Override // java.util.LinkedHashMap
            protected boolean removeEldestEntry(java.util.Map.Entry entry) {
                int size = size();
                long j = size;
                if (j > android.app.PropertyInvalidatedCache.this.mHighWaterMark) {
                    android.app.PropertyInvalidatedCache.this.mHighWaterMark = j;
                }
                if (size > android.app.PropertyInvalidatedCache.this.mMaxEntries) {
                    android.app.PropertyInvalidatedCache.this.mMissOverflow++;
                    return true;
                }
                return false;
            }
        };
    }

    private void registerCache() {
        synchronized (sGlobalLock) {
            if (sDisabledKeys.contains(this.mCacheName)) {
                disableInstance();
            }
            sCaches.put(this, null);
        }
    }

    public static void setTestMode(boolean z) {
        sTesting = z;
        synchronized (sTestingPropertyMap) {
            sTestingPropertyMap.clear();
        }
    }

    private static void testPropertyName(java.lang.String str) {
        synchronized (sTestingPropertyMap) {
            sTestingPropertyMap.put(str, 0L);
        }
    }

    public void testPropertyName() {
        testPropertyName(this.mPropertyName);
    }

    private long getCurrentNonce() {
        if (sTesting) {
            synchronized (sTestingPropertyMap) {
                java.lang.Long l = sTestingPropertyMap.get(this.mPropertyName);
                if (l != null) {
                    return l.longValue();
                }
            }
        }
        android.os.SystemProperties.Handle handle = this.mPropertyHandle;
        if (handle == null) {
            handle = android.os.SystemProperties.find(this.mPropertyName);
            if (handle == null) {
                return 0L;
            }
            this.mPropertyHandle = handle;
        }
        return handle.getLong(0L);
    }

    private static void setNonce(java.lang.String str, long j) {
        if (sTesting) {
            synchronized (sTestingPropertyMap) {
                if (sTestingPropertyMap.get(str) != null) {
                    sTestingPropertyMap.put(str, java.lang.Long.valueOf(j));
                    return;
                }
            }
        }
        java.lang.RuntimeException runtimeException = null;
        for (int i = 0; i < 5; i++) {
            try {
                android.os.SystemProperties.set(str, java.lang.Long.toString(j));
                if (i > 0) {
                    android.util.Log.w(TAG, "Nonce set after " + i + " tries");
                    return;
                }
                return;
            } catch (java.lang.RuntimeException e) {
                if (runtimeException == null) {
                    runtimeException = e;
                }
                try {
                    java.lang.Thread.sleep(200L);
                } catch (java.lang.InterruptedException e2) {
                }
            }
        }
        throw runtimeException;
    }

    private static long getNonce(java.lang.String str) {
        if (sTesting) {
            synchronized (sTestingPropertyMap) {
                java.lang.Long l = sTestingPropertyMap.get(str);
                if (l != null) {
                    return l.longValue();
                }
            }
        }
        return android.os.SystemProperties.getLong(str, 0L);
    }

    public final void clear() {
        synchronized (this.mLock) {
            this.mCache.clear();
            this.mClears++;
        }
    }

    public Result recompute(Query query) {
        return this.mComputer.apply(query);
    }

    public boolean bypass(Query query) {
        return this.mComputer.shouldBypassCache(query);
    }

    public boolean resultEquals(Result result, Result result2) {
        if (result2 != null) {
            return java.util.Objects.equals(result, result2);
        }
        return true;
    }

    protected Result refresh(Result result, Query query) {
        return result;
    }

    public final void disableInstance() {
        synchronized (this.mLock) {
            this.mDisabled = true;
            clear();
        }
    }

    private static final void disableLocal(java.lang.String str) {
        synchronized (sGlobalLock) {
            if (sDisabledKeys.contains(str)) {
                return;
            }
            for (android.app.PropertyInvalidatedCache propertyInvalidatedCache : sCaches.keySet()) {
                if (str.equals(propertyInvalidatedCache.mCacheName)) {
                    propertyInvalidatedCache.disableInstance();
                }
            }
            sDisabledKeys.add(str);
        }
    }

    public final void forgetDisableLocal() {
        synchronized (sGlobalLock) {
            sDisabledKeys.remove(this.mCacheName);
        }
    }

    public void disableLocal() {
        disableForCurrentProcess();
    }

    public void disableForCurrentProcess() {
        disableLocal(this.mCacheName);
    }

    public static void disableForCurrentProcess(java.lang.String str) {
        disableLocal(str);
    }

    public final boolean isDisabled() {
        return this.mDisabled || !sEnabled;
    }

    public Result query(Query query) {
        Result result;
        long currentNonce = !isDisabled() ? getCurrentNonce() : 1L;
        if (bypass(query)) {
            currentNonce = 3;
        }
        while (!isReservedNonce(currentNonce)) {
            synchronized (this.mLock) {
                if (currentNonce == this.mLastSeenNonce) {
                    result = this.mCache.get(query);
                    if (result != null) {
                        this.mHits++;
                    }
                } else {
                    clear();
                    this.mLastSeenNonce = currentNonce;
                    result = null;
                }
            }
            if (result != null) {
                Result refresh = refresh(result, query);
                if (refresh != result) {
                    long currentNonce2 = getCurrentNonce();
                    if (currentNonce != currentNonce2) {
                        currentNonce = currentNonce2;
                    } else {
                        synchronized (this.mLock) {
                            if (currentNonce == this.mLastSeenNonce) {
                                if (refresh == null) {
                                    this.mCache.remove(query);
                                } else {
                                    this.mCache.put(query, refresh);
                                }
                            }
                        }
                        return maybeCheckConsistency(query, refresh);
                    }
                } else {
                    return maybeCheckConsistency(query, result);
                }
            } else {
                Result recompute = recompute(query);
                synchronized (this.mLock) {
                    if (this.mLastSeenNonce == currentNonce && recompute != null) {
                        this.mCache.put(query, recompute);
                    }
                    this.mMisses++;
                }
                return maybeCheckConsistency(query, recompute);
            }
        }
        if (!this.mDisabled) {
            synchronized (this.mLock) {
                long[] jArr = this.mSkips;
                int i = (int) currentNonce;
                jArr[i] = jArr[i] + 1;
            }
        }
        return recompute(query);
    }

    private static final class NoPreloadHolder {
        private static final java.util.concurrent.atomic.AtomicLong sNextNonce = new java.util.concurrent.atomic.AtomicLong(new java.util.Random().nextLong());

        private NoPreloadHolder() {
        }

        public static long next() {
            return sNextNonce.getAndIncrement();
        }
    }

    public final void disableSystemWide() {
        disableSystemWide(this.mPropertyName);
    }

    private static void disableSystemWide(java.lang.String str) {
        if (!sEnabled) {
            return;
        }
        setNonce(str, 1L);
    }

    public void invalidateCache() {
        invalidateCache(this.mPropertyName);
    }

    public static void invalidateCache(java.lang.String str, java.lang.String str2) {
        invalidateCache(createPropertyName(str, str2));
    }

    public static void invalidateCache(java.lang.String str) {
        if (!sEnabled) {
            return;
        }
        synchronized (sCorkLock) {
            java.lang.Integer num = sCorks.get(str);
            if (num != null && num.intValue() > 0) {
                sCorkedInvalidates.put(str, java.lang.Long.valueOf(sCorkedInvalidates.getOrDefault(str, 0L).longValue() + 1));
            } else {
                invalidateCacheLocked(str);
            }
        }
    }

    private static void invalidateCacheLocked(java.lang.String str) {
        long next;
        if (getNonce(str) == 1) {
            return;
        }
        do {
            next = android.app.PropertyInvalidatedCache.NoPreloadHolder.next();
        } while (isReservedNonce(next));
        setNonce(str, next);
        sInvalidates.put(str, java.lang.Long.valueOf(sInvalidates.getOrDefault(str, 0L).longValue() + 1));
    }

    public static void corkInvalidations(java.lang.String str) {
        if (!sEnabled) {
            return;
        }
        synchronized (sCorkLock) {
            int intValue = sCorks.getOrDefault(str, 0).intValue();
            if (intValue == 0) {
                long nonce = getNonce(str);
                if (nonce != 0 && nonce != 1) {
                    setNonce(str, 2L);
                }
            } else {
                sCorkedInvalidates.put(str, java.lang.Long.valueOf(sCorkedInvalidates.getOrDefault(str, 0L).longValue() + 1));
            }
            sCorks.put(str, java.lang.Integer.valueOf(intValue + 1));
        }
    }

    public static void uncorkInvalidations(java.lang.String str) {
        if (!sEnabled) {
            return;
        }
        synchronized (sCorkLock) {
            int intValue = sCorks.getOrDefault(str, 0).intValue();
            if (intValue < 1) {
                throw new java.lang.AssertionError("cork underflow: " + str);
            }
            if (intValue != 1) {
                sCorks.put(str, java.lang.Integer.valueOf(intValue - 1));
            } else {
                sCorks.remove(str);
                invalidateCacheLocked(str);
            }
        }
    }

    public static final class AutoCorker {
        public static final int DEFAULT_AUTO_CORK_DELAY_MS = 50;
        private final int mAutoCorkDelayMs;
        private android.os.Handler mHandler;
        private final java.lang.Object mLock;
        private final java.lang.String mPropertyName;
        private long mUncorkDeadlineMs;

        public AutoCorker(java.lang.String str) {
            this(str, 50);
        }

        public AutoCorker(java.lang.String str, int i) {
            this.mLock = new java.lang.Object();
            this.mUncorkDeadlineMs = -1L;
            this.mPropertyName = str;
            this.mAutoCorkDelayMs = i;
        }

        public void autoCork() {
            if (android.os.Looper.getMainLooper() == null) {
                android.app.PropertyInvalidatedCache.invalidateCache(this.mPropertyName);
                return;
            }
            synchronized (this.mLock) {
                boolean z = this.mUncorkDeadlineMs >= 0;
                this.mUncorkDeadlineMs = android.os.SystemClock.uptimeMillis() + this.mAutoCorkDelayMs;
                if (!z) {
                    getHandlerLocked().sendEmptyMessageAtTime(0, this.mUncorkDeadlineMs);
                    android.app.PropertyInvalidatedCache.corkInvalidations(this.mPropertyName);
                } else {
                    synchronized (android.app.PropertyInvalidatedCache.sCorkLock) {
                        android.app.PropertyInvalidatedCache.sCorkedInvalidates.put(this.mPropertyName, java.lang.Long.valueOf(((java.lang.Long) android.app.PropertyInvalidatedCache.sCorkedInvalidates.getOrDefault(this.mPropertyName, 0L)).longValue() + 1));
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleMessage(android.os.Message message) {
            synchronized (this.mLock) {
                if (this.mUncorkDeadlineMs < 0) {
                    return;
                }
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                if (this.mUncorkDeadlineMs > uptimeMillis) {
                    this.mUncorkDeadlineMs = uptimeMillis + this.mAutoCorkDelayMs;
                    getHandlerLocked().sendEmptyMessageAtTime(0, this.mUncorkDeadlineMs);
                } else {
                    this.mUncorkDeadlineMs = -1L;
                    android.app.PropertyInvalidatedCache.uncorkInvalidations(this.mPropertyName);
                }
            }
        }

        private android.os.Handler getHandlerLocked() {
            if (this.mHandler == null) {
                this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: android.app.PropertyInvalidatedCache.AutoCorker.1
                    @Override // android.os.Handler
                    public void handleMessage(android.os.Message message) {
                        android.app.PropertyInvalidatedCache.AutoCorker.this.handleMessage(message);
                    }
                };
            }
            return this.mHandler;
        }
    }

    private Result maybeCheckConsistency(Query query, Result result) {
        return result;
    }

    public final java.lang.String cacheName() {
        return this.mCacheName;
    }

    public final java.lang.String propertyName() {
        return this.mPropertyName;
    }

    protected java.lang.String queryToString(Query query) {
        return java.util.Objects.toString(query);
    }

    public static void disableForTestMode() {
        android.util.Log.d(TAG, "disabling all caches in the process");
        sEnabled = false;
    }

    public boolean getDisabledState() {
        return isDisabled();
    }

    public int size() {
        int size;
        synchronized (this.mLock) {
            size = this.mCache.size();
        }
        return size;
    }

    private static java.util.ArrayList<android.app.PropertyInvalidatedCache> getActiveCaches() {
        return new java.util.ArrayList<>(sCaches.keySet());
    }

    private static java.util.ArrayList<java.util.Map.Entry<java.lang.String, java.lang.Integer>> getActiveCorks() {
        java.util.ArrayList<java.util.Map.Entry<java.lang.String, java.lang.Integer>> arrayList;
        synchronized (sCorkLock) {
            arrayList = new java.util.ArrayList<>(sCorks.entrySet());
        }
        return arrayList;
    }

    private static boolean anyDetailed(java.lang.String[] strArr) {
        for (java.lang.String str : strArr) {
            if (str.startsWith(NAME_CONTAINS) || str.startsWith(NAME_LIKE) || str.startsWith(PROPERTY_CONTAINS) || str.startsWith(PROPERTY_LIKE)) {
                return true;
            }
        }
        return false;
    }

    private static boolean chooses(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) {
        if (str.startsWith(str2)) {
            java.lang.String substring = str.substring(str2.length());
            if (z) {
                return str3.contains(substring);
            }
            return str3.matches(substring);
        }
        return false;
    }

    private boolean showDetailed(java.lang.String[] strArr) {
        for (java.lang.String str : strArr) {
            if (chooses(str, NAME_CONTAINS, cacheName(), true) || chooses(str, NAME_LIKE, cacheName(), false) || chooses(str, PROPERTY_CONTAINS, this.mPropertyName, true) || chooses(str, PROPERTY_LIKE, this.mPropertyName, false)) {
                return true;
            }
        }
        return false;
    }

    private void dumpContents(java.io.PrintWriter printWriter, boolean z, java.lang.String[] strArr) {
        long longValue;
        long longValue2;
        if (z && !showDetailed(strArr)) {
            return;
        }
        synchronized (sCorkLock) {
            longValue = sInvalidates.getOrDefault(this.mPropertyName, 0L).longValue();
            longValue2 = sCorkedInvalidates.getOrDefault(this.mPropertyName, 0L).longValue();
        }
        synchronized (this.mLock) {
            printWriter.println(android.text.TextUtils.formatSimple("  Cache Name: %s", cacheName()));
            printWriter.println(android.text.TextUtils.formatSimple("    Property: %s", this.mPropertyName));
            printWriter.println(android.text.TextUtils.formatSimple("    Hits: %d, Misses: %d, Skips: %d, Clears: %d", java.lang.Long.valueOf(this.mHits), java.lang.Long.valueOf(this.mMisses), java.lang.Long.valueOf(this.mSkips[2] + this.mSkips[0] + this.mSkips[1] + this.mSkips[3]), java.lang.Long.valueOf(this.mClears)));
            printWriter.println(android.text.TextUtils.formatSimple("    Skip-corked: %d, Skip-unset: %d, Skip-bypass: %d, Skip-other: %d", java.lang.Long.valueOf(this.mSkips[2]), java.lang.Long.valueOf(this.mSkips[0]), java.lang.Long.valueOf(this.mSkips[3]), java.lang.Long.valueOf(this.mSkips[1])));
            printWriter.println(android.text.TextUtils.formatSimple("    Nonce: 0x%016x, Invalidates: %d, CorkedInvalidates: %d", java.lang.Long.valueOf(this.mLastSeenNonce), java.lang.Long.valueOf(longValue), java.lang.Long.valueOf(longValue2)));
            printWriter.println(android.text.TextUtils.formatSimple("    Current Size: %d, Max Size: %d, HW Mark: %d, Overflows: %d", java.lang.Integer.valueOf(this.mCache.size()), java.lang.Integer.valueOf(this.mMaxEntries), java.lang.Long.valueOf(this.mHighWaterMark), java.lang.Long.valueOf(this.mMissOverflow)));
            printWriter.println(android.text.TextUtils.formatSimple("    Enabled: %s", this.mDisabled ? "false" : "true"));
            printWriter.println("");
            if (z) {
                java.util.Set<java.util.Map.Entry<Query, Result>> entrySet = this.mCache.entrySet();
                if (entrySet.size() == 0) {
                    return;
                }
                printWriter.println("    Contents:");
                for (java.util.Map.Entry<Query, Result> entry : entrySet) {
                    printWriter.println(android.text.TextUtils.formatSimple("      Key: %s\n      Value: %s\n", java.util.Objects.toString(entry.getKey()), java.util.Objects.toString(entry.getValue())));
                }
            }
        }
    }

    private static void dumpCorkInfo(java.io.PrintWriter printWriter) {
        java.util.ArrayList<java.util.Map.Entry<java.lang.String, java.lang.Integer>> activeCorks = getActiveCorks();
        if (activeCorks.size() > 0) {
            printWriter.println("  Corking Status:");
            for (int i = 0; i < activeCorks.size(); i++) {
                java.util.Map.Entry<java.lang.String, java.lang.Integer> entry = activeCorks.get(i);
                printWriter.println(android.text.TextUtils.formatSimple("    Property Name: %s Count: %d", entry.getKey(), entry.getValue()));
            }
        }
    }

    private static void dumpCacheInfo(java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.util.ArrayList<android.app.PropertyInvalidatedCache> activeCaches;
        if (!sEnabled) {
            printWriter.println("  Caching is disabled in this process.");
            return;
        }
        boolean anyDetailed = anyDetailed(strArr);
        synchronized (sGlobalLock) {
            activeCaches = getActiveCaches();
            if (!anyDetailed) {
                dumpCorkInfo(printWriter);
            }
        }
        for (int i = 0; i < activeCaches.size(); i++) {
            activeCaches.get(i).dumpContents(printWriter, anyDetailed, strArr);
        }
    }

    public static void dumpCacheInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        java.io.PrintWriter printWriter = new java.io.PrintWriter(byteArrayOutputStream);
        dumpCacheInfo(printWriter, strArr);
        printWriter.close();
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor());
            byteArrayOutputStream.writeTo(fileOutputStream);
            fileOutputStream.close();
            byteArrayOutputStream.close();
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Failed to dump PropertyInvalidatedCache instances");
        }
    }

    public static void onTrimMemory() {
        java.util.ArrayList<android.app.PropertyInvalidatedCache> activeCaches;
        synchronized (sGlobalLock) {
            activeCaches = getActiveCaches();
        }
        for (int i = 0; i < activeCaches.size(); i++) {
            activeCaches.get(i).clear();
        }
    }
}
