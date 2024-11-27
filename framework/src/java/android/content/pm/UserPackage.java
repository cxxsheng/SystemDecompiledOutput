package android.content.pm;

/* loaded from: classes.dex */
public final class UserPackage {
    private static final boolean ENABLE_CACHING = true;
    static final int MAX_NUM_CACHED_ENTRIES_PER_USER = 1000;
    public final java.lang.String packageName;
    public final int userId;
    private static final java.lang.Object sCacheLock = new java.lang.Object();
    private static final android.util.SparseArrayMap<java.lang.String, android.content.pm.UserPackage> sCache = new android.util.SparseArrayMap<>();
    private static int[] sUserIds = libcore.util.EmptyArray.INT;

    private UserPackage(int i, java.lang.String str) {
        this.userId = i;
        this.packageName = str;
    }

    public java.lang.String toString() {
        return "<" + this.userId + ">" + this.packageName;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.content.pm.UserPackage)) {
            return false;
        }
        android.content.pm.UserPackage userPackage = (android.content.pm.UserPackage) obj;
        return this.userId == userPackage.userId && java.util.Objects.equals(this.packageName, userPackage.packageName);
    }

    public int hashCode() {
        return ((0 + this.userId) * 31) + this.packageName.hashCode();
    }

    public static android.content.pm.UserPackage of(int i, java.lang.String str) {
        synchronized (sCacheLock) {
            if (!com.android.internal.util.ArrayUtils.contains(sUserIds, i)) {
                return new android.content.pm.UserPackage(i, str);
            }
            android.content.pm.UserPackage userPackage = sCache.get(i, str);
            if (userPackage == null) {
                maybePurgeRandomEntriesLocked(i);
                java.lang.String intern = str.intern();
                userPackage = new android.content.pm.UserPackage(i, intern);
                sCache.add(i, intern, userPackage);
            }
            return userPackage;
        }
    }

    public static void removeFromCache(int i, java.lang.String str) {
        synchronized (sCacheLock) {
            sCache.delete(i, str);
        }
    }

    public static void setValidUserIds(int[] iArr) {
        int[] iArr2 = (int[]) iArr.clone();
        synchronized (sCacheLock) {
            sUserIds = iArr2;
            for (int numMaps = sCache.numMaps() - 1; numMaps >= 0; numMaps--) {
                if (!com.android.internal.util.ArrayUtils.contains(iArr2, sCache.keyAt(numMaps))) {
                    sCache.deleteAt(numMaps);
                }
            }
        }
    }

    public static int numEntriesForUser(int i) {
        int numElementsForKey;
        synchronized (sCacheLock) {
            numElementsForKey = sCache.numElementsForKey(i);
        }
        return numElementsForKey;
    }

    private static void maybePurgeRandomEntriesLocked(int i) {
        int numElementsForKeyAt;
        int indexOfKey = sCache.indexOfKey(i);
        if (indexOfKey < 0 || (numElementsForKeyAt = sCache.numElementsForKeyAt(indexOfKey)) < 1000) {
            return;
        }
        java.util.Random random = new java.util.Random();
        int max = java.lang.Math.max(1, 10);
        int i2 = 0;
        for (numElementsForKeyAt = sCache.numElementsForKeyAt(indexOfKey); i2 < max && numElementsForKeyAt > 0; numElementsForKeyAt--) {
            sCache.deleteAt(indexOfKey, random.nextInt(numElementsForKeyAt));
            i2++;
        }
    }
}
