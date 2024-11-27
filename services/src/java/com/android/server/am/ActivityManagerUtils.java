package com.android.server.am;

/* loaded from: classes.dex */
public class ActivityManagerUtils {
    private static java.lang.Integer sAndroidIdHash;

    @com.android.internal.annotations.GuardedBy({"sHashCache"})
    private static final android.util.ArrayMap<java.lang.String, java.lang.Integer> sHashCache = new android.util.ArrayMap<>();
    private static java.lang.String sInjectedAndroidId;

    private ActivityManagerUtils() {
    }

    @com.android.internal.annotations.VisibleForTesting
    static void injectAndroidIdForTest(java.lang.String str) {
        sInjectedAndroidId = str;
        sAndroidIdHash = null;
    }

    @com.android.internal.annotations.VisibleForTesting
    static int getAndroidIdHash() {
        if (sAndroidIdHash == null) {
            android.content.ContentResolver contentResolver = android.app.ActivityThread.currentApplication().getContentResolver();
            java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(contentResolver, "android_id", contentResolver.getUserId());
            if (sInjectedAndroidId != null) {
                stringForUser = sInjectedAndroidId;
            }
            sAndroidIdHash = java.lang.Integer.valueOf(getUnsignedHashUnCached(stringForUser));
        }
        return sAndroidIdHash.intValue();
    }

    @com.android.internal.annotations.VisibleForTesting
    static int getUnsignedHashCached(java.lang.String str) {
        synchronized (sHashCache) {
            try {
                java.lang.Integer num = sHashCache.get(str);
                if (num != null) {
                    return num.intValue();
                }
                int unsignedHashUnCached = getUnsignedHashUnCached(str);
                sHashCache.put(str.intern(), java.lang.Integer.valueOf(unsignedHashUnCached));
                return unsignedHashUnCached;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static int getUnsignedHashUnCached(java.lang.String str) {
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes());
            return unsignedIntFromBytes(messageDigest.digest());
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static int unsignedIntFromBytes(byte[] bArr) {
        return (extractByte(bArr, 3) | extractByte(bArr, 0) | extractByte(bArr, 1) | extractByte(bArr, 2)) & Integer.MAX_VALUE;
    }

    private static int extractByte(byte[] bArr, int i) {
        return (bArr[i] & 255) << (i * 8);
    }

    public static boolean shouldSamplePackageForAtom(java.lang.String str, float f) {
        if (f <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return false;
        }
        return f >= 1.0f || ((double) (getUnsignedHashCached(str) ^ getAndroidIdHash())) / 2.147483647E9d <= ((double) f);
    }

    public static void logUnsafeIntentEvent(int i, int i2, android.content.Intent intent, java.lang.String str, boolean z) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.UNSAFE_INTENT_EVENT_REPORTED, i, i2, intent.getComponent() == null ? null : intent.getComponent().flattenToString(), intent.getPackage(), intent.getAction(), intent.getCategories() == null ? new java.lang.String[0] : (java.lang.String[]) intent.getCategories().toArray(new java.util.function.IntFunction() { // from class: com.android.server.am.ActivityManagerUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i3) {
                java.lang.String[] lambda$logUnsafeIntentEvent$0;
                lambda$logUnsafeIntentEvent$0 = com.android.server.am.ActivityManagerUtils.lambda$logUnsafeIntentEvent$0(i3);
                return lambda$logUnsafeIntentEvent$0;
            }
        }), str, intent.getScheme(), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$logUnsafeIntentEvent$0(int i) {
        return new java.lang.String[i];
    }
}
