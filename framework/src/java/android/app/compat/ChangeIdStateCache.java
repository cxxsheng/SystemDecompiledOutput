package android.app.compat;

/* loaded from: classes.dex */
public final class ChangeIdStateCache extends android.app.PropertyInvalidatedCache<android.app.compat.ChangeIdStateQuery, java.lang.Boolean> {
    private static final java.lang.String CACHE_KEY = "cache_key.is_compat_change_enabled";
    private static final int MAX_ENTRIES = 64;
    private static boolean sDisabled = false;
    private volatile com.android.internal.compat.IPlatformCompat mPlatformCompat;

    public ChangeIdStateCache() {
        super(64, CACHE_KEY);
    }

    public static void disable() {
        sDisabled = true;
    }

    public static void invalidate() {
        if (!sDisabled) {
            android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY);
        }
    }

    com.android.internal.compat.IPlatformCompat getPlatformCompatService() {
        com.android.internal.compat.IPlatformCompat iPlatformCompat = this.mPlatformCompat;
        if (iPlatformCompat == null) {
            synchronized (this) {
                iPlatformCompat = this.mPlatformCompat;
                if (iPlatformCompat == null) {
                    iPlatformCompat = com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.PLATFORM_COMPAT_SERVICE));
                    if (iPlatformCompat == null) {
                        throw new java.lang.RuntimeException("Could not get PlatformCompatService instance!");
                    }
                    this.mPlatformCompat = iPlatformCompat;
                }
            }
        }
        return iPlatformCompat;
    }

    @Override // android.app.PropertyInvalidatedCache
    public java.lang.Boolean recompute(android.app.compat.ChangeIdStateQuery changeIdStateQuery) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                if (changeIdStateQuery.type == 0) {
                    return java.lang.Boolean.valueOf(getPlatformCompatService().isChangeEnabledByPackageName(changeIdStateQuery.changeId, changeIdStateQuery.packageName, changeIdStateQuery.userId));
                }
                if (changeIdStateQuery.type == 1) {
                    return java.lang.Boolean.valueOf(getPlatformCompatService().isChangeEnabledByUid(changeIdStateQuery.changeId, changeIdStateQuery.uid));
                }
                throw new java.lang.IllegalArgumentException("Invalid query type: " + changeIdStateQuery.type);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw new java.lang.IllegalStateException("Could not recompute value!");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
