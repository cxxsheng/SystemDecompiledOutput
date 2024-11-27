package com.android.server.pm;

/* loaded from: classes2.dex */
final class SharedLibraryUtils {
    SharedLibraryUtils() {
    }

    public static boolean addSharedLibraryToPackageVersionMap(java.util.Map<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> map, android.content.pm.SharedLibraryInfo sharedLibraryInfo) {
        java.lang.String name = sharedLibraryInfo.getName();
        if (map.containsKey(name)) {
            if (sharedLibraryInfo.getType() != 2 || map.get(name).indexOfKey(sharedLibraryInfo.getLongVersion()) >= 0) {
                return false;
            }
        } else {
            map.put(name, new com.android.server.utils.WatchedLongSparseArray<>());
        }
        map.get(name).put(sharedLibraryInfo.getLongVersion(), sharedLibraryInfo);
        return true;
    }

    @android.annotation.Nullable
    public static android.content.pm.SharedLibraryInfo getSharedLibraryInfo(java.lang.String str, long j, java.util.Map<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> map, @android.annotation.Nullable java.util.Map<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> map2) {
        android.content.pm.SharedLibraryInfo sharedLibraryInfo;
        if (map2 != null) {
            com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> watchedLongSparseArray = map2.get(str);
            if (watchedLongSparseArray == null) {
                sharedLibraryInfo = null;
            } else {
                sharedLibraryInfo = watchedLongSparseArray.get(j);
            }
            if (sharedLibraryInfo != null) {
                return sharedLibraryInfo;
            }
        }
        com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> watchedLongSparseArray2 = map.get(str);
        if (watchedLongSparseArray2 == null) {
            return null;
        }
        return watchedLongSparseArray2.get(j);
    }

    public static java.util.List<android.content.pm.SharedLibraryInfo> findSharedLibraries(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (!packageStateInternal.getTransientState().getUsesLibraryInfos().isEmpty()) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.HashSet hashSet = new java.util.HashSet();
            java.util.Iterator<com.android.server.pm.pkg.SharedLibraryWrapper> it = packageStateInternal.getTransientState().getUsesLibraryInfos().iterator();
            while (it.hasNext()) {
                findSharedLibrariesRecursive(it.next().getInfo(), arrayList, hashSet);
            }
            return arrayList;
        }
        return java.util.Collections.emptyList();
    }

    private static void findSharedLibrariesRecursive(android.content.pm.SharedLibraryInfo sharedLibraryInfo, java.util.ArrayList<android.content.pm.SharedLibraryInfo> arrayList, java.util.Set<java.lang.String> set) {
        if (!set.contains(sharedLibraryInfo.getName())) {
            set.add(sharedLibraryInfo.getName());
            arrayList.add(sharedLibraryInfo);
            if (sharedLibraryInfo.getDependencies() != null) {
                java.util.Iterator it = sharedLibraryInfo.getDependencies().iterator();
                while (it.hasNext()) {
                    findSharedLibrariesRecursive((android.content.pm.SharedLibraryInfo) it.next(), arrayList, set);
                }
            }
        }
    }
}
