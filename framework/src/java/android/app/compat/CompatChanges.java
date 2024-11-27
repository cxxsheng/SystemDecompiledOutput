package android.app.compat;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class CompatChanges {
    private static final android.app.compat.ChangeIdStateCache QUERY_CACHE = new android.app.compat.ChangeIdStateCache();

    private CompatChanges() {
    }

    public static boolean isChangeEnabled(long j) {
        return android.compat.Compatibility.isChangeEnabled(j);
    }

    public static boolean isChangeEnabled(long j, java.lang.String str, android.os.UserHandle userHandle) {
        return QUERY_CACHE.query(android.app.compat.ChangeIdStateQuery.byPackageName(j, str, userHandle.getIdentifier())).booleanValue();
    }

    public static boolean isChangeEnabled(long j, int i) {
        return QUERY_CACHE.query(android.app.compat.ChangeIdStateQuery.byUid(j, i)).booleanValue();
    }

    public static void putAllPackageOverrides(java.util.Map<java.lang.String, java.util.Map<java.lang.Long, android.app.compat.PackageOverride>> map) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (java.lang.String str : map.keySet()) {
            arrayMap.put(str, new com.android.internal.compat.CompatibilityOverrideConfig(map.get(str)));
        }
        try {
            QUERY_CACHE.getPlatformCompatService().putAllOverridesOnReleaseBuilds(new com.android.internal.compat.CompatibilityOverridesByPackageConfig(arrayMap));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static void putPackageOverrides(java.lang.String str, java.util.Map<java.lang.Long, android.app.compat.PackageOverride> map) {
        try {
            QUERY_CACHE.getPlatformCompatService().putOverridesOnReleaseBuilds(new com.android.internal.compat.CompatibilityOverrideConfig(map), str);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static void removeAllPackageOverrides(java.util.Map<java.lang.String, java.util.Set<java.lang.Long>> map) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (java.lang.String str : map.keySet()) {
            arrayMap.put(str, new com.android.internal.compat.CompatibilityOverridesToRemoveConfig(map.get(str)));
        }
        try {
            QUERY_CACHE.getPlatformCompatService().removeAllOverridesOnReleaseBuilds(new com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig(arrayMap));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static void removePackageOverrides(java.lang.String str, java.util.Set<java.lang.Long> set) {
        try {
            QUERY_CACHE.getPlatformCompatService().removeOverridesOnReleaseBuilds(new com.android.internal.compat.CompatibilityOverridesToRemoveConfig(set), str);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
