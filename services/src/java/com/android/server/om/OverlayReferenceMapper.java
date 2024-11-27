package com.android.server.om;

/* loaded from: classes2.dex */
public class OverlayReferenceMapper {
    private static final java.lang.String TAG = "OverlayReferenceMapper";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDeferRebuild;

    @android.annotation.NonNull
    private final com.android.server.om.OverlayReferenceMapper.Provider mProvider;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>>> mActorToTargetToOverlays = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, java.util.Set<java.lang.String>> mActorPkgToPkgs = new android.util.ArrayMap<>();

    public interface Provider {
        @android.annotation.Nullable
        java.lang.String getActorPkg(@android.annotation.NonNull java.lang.String str);

        @android.annotation.NonNull
        java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getTargetToOverlayables(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage);
    }

    public OverlayReferenceMapper(boolean z, @android.annotation.Nullable com.android.server.om.OverlayReferenceMapper.Provider provider) {
        this.mDeferRebuild = z;
        this.mProvider = provider == null ? new com.android.server.om.OverlayReferenceMapper.Provider() { // from class: com.android.server.om.OverlayReferenceMapper.1
            @Override // com.android.server.om.OverlayReferenceMapper.Provider
            @android.annotation.Nullable
            public java.lang.String getActorPkg(java.lang.String str) {
                return (java.lang.String) com.android.server.om.OverlayActorEnforcer.getPackageNameForActor(str, com.android.server.SystemConfig.getInstance().getNamedActors()).first;
            }

            @Override // com.android.server.om.OverlayReferenceMapper.Provider
            @android.annotation.NonNull
            public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getTargetToOverlayables(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
                java.lang.String overlayTarget = androidPackage.getOverlayTarget();
                if (android.text.TextUtils.isEmpty(overlayTarget)) {
                    return java.util.Collections.emptyMap();
                }
                java.lang.String overlayTargetOverlayableName = androidPackage.getOverlayTargetOverlayableName();
                java.util.HashMap hashMap = new java.util.HashMap();
                java.util.HashSet hashSet = new java.util.HashSet();
                hashSet.add(overlayTargetOverlayableName);
                hashMap.put(overlayTarget, hashSet);
                return hashMap;
            }
        } : provider;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getActorPkgToPkgs() {
        return this.mActorPkgToPkgs;
    }

    public boolean isValidActor(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        boolean z;
        synchronized (this.mLock) {
            try {
                ensureMapBuilt();
                java.util.Set<java.lang.String> set = this.mActorPkgToPkgs.get(str2);
                z = set != null && set.contains(str);
            } finally {
            }
        }
        return z;
    }

    public android.util.ArraySet<java.lang.String> addPkg(com.android.server.pm.pkg.AndroidPackage androidPackage, java.util.Map<java.lang.String, com.android.server.pm.pkg.AndroidPackage> map) {
        android.util.ArraySet<java.lang.String> arraySet;
        synchronized (this.mLock) {
            try {
                arraySet = new android.util.ArraySet<>();
                if (!androidPackage.getOverlayables().isEmpty()) {
                    addTarget(androidPackage, map, arraySet);
                }
                if (!this.mProvider.getTargetToOverlayables(androidPackage).isEmpty()) {
                    addOverlay(androidPackage, map, arraySet);
                }
                if (!this.mDeferRebuild) {
                    rebuild();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arraySet;
    }

    public android.util.ArraySet<java.lang.String> removePkg(java.lang.String str) {
        android.util.ArraySet<java.lang.String> arraySet;
        synchronized (this.mLock) {
            try {
                arraySet = new android.util.ArraySet<>();
                removeTarget(str, arraySet);
                removeOverlay(str, arraySet);
                if (!this.mDeferRebuild) {
                    rebuild();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arraySet;
    }

    private void removeTarget(java.lang.String str, @android.annotation.NonNull java.util.Collection<java.lang.String> collection) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mActorToTargetToOverlays.size() - 1; size >= 0; size--) {
                    android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> valueAt = this.mActorToTargetToOverlays.valueAt(size);
                    if (valueAt.containsKey(str)) {
                        valueAt.remove(str);
                        collection.add(this.mProvider.getActorPkg(this.mActorToTargetToOverlays.keyAt(size)));
                        if (valueAt.isEmpty()) {
                            this.mActorToTargetToOverlays.removeAt(size);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void addTarget(com.android.server.pm.pkg.AndroidPackage androidPackage, java.util.Map<java.lang.String, com.android.server.pm.pkg.AndroidPackage> map, @android.annotation.NonNull java.util.Collection<java.lang.String> collection) {
        synchronized (this.mLock) {
            try {
                java.lang.String packageName = androidPackage.getPackageName();
                removeTarget(packageName, collection);
                java.util.Map overlayables = androidPackage.getOverlayables();
                for (java.lang.String str : overlayables.keySet()) {
                    java.lang.String str2 = (java.lang.String) overlayables.get(str);
                    addTargetToMap(str2, packageName, collection);
                    for (com.android.server.pm.pkg.AndroidPackage androidPackage2 : map.values()) {
                        java.util.Set<java.lang.String> set = this.mProvider.getTargetToOverlayables(androidPackage2).get(packageName);
                        if (!com.android.internal.util.CollectionUtils.isEmpty(set)) {
                            if (set.contains(str)) {
                                addOverlayToMap(str2, packageName, androidPackage2.getPackageName(), collection);
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void removeOverlay(java.lang.String str, @android.annotation.NonNull java.util.Collection<java.lang.String> collection) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mActorToTargetToOverlays.size() - 1; size >= 0; size--) {
                    android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> valueAt = this.mActorToTargetToOverlays.valueAt(size);
                    for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                        if (valueAt.valueAt(size2).remove(str)) {
                            collection.add(this.mProvider.getActorPkg(this.mActorToTargetToOverlays.keyAt(size)));
                        }
                    }
                    if (valueAt.isEmpty()) {
                        this.mActorToTargetToOverlays.removeAt(size);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void addOverlay(com.android.server.pm.pkg.AndroidPackage androidPackage, java.util.Map<java.lang.String, com.android.server.pm.pkg.AndroidPackage> map, @android.annotation.NonNull java.util.Collection<java.lang.String> collection) {
        synchronized (this.mLock) {
            try {
                java.lang.String packageName = androidPackage.getPackageName();
                removeOverlay(packageName, collection);
                for (java.util.Map.Entry<java.lang.String, java.util.Set<java.lang.String>> entry : this.mProvider.getTargetToOverlayables(androidPackage).entrySet()) {
                    java.lang.String key = entry.getKey();
                    java.util.Set<java.lang.String> value = entry.getValue();
                    com.android.server.pm.pkg.AndroidPackage androidPackage2 = map.get(key);
                    if (androidPackage2 != null) {
                        java.lang.String packageName2 = androidPackage2.getPackageName();
                        java.util.Map overlayables = androidPackage2.getOverlayables();
                        java.util.Iterator<java.lang.String> it = value.iterator();
                        while (it.hasNext()) {
                            java.lang.String str = (java.lang.String) overlayables.get(it.next());
                            if (!android.text.TextUtils.isEmpty(str)) {
                                addOverlayToMap(str, packageName2, packageName, collection);
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void rebuildIfDeferred() {
        synchronized (this.mLock) {
            try {
                if (this.mDeferRebuild) {
                    rebuild();
                    this.mDeferRebuild = false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void ensureMapBuilt() {
        if (this.mDeferRebuild) {
            rebuildIfDeferred();
            android.util.Slog.w(TAG, "The actor map was queried before the system was ready, which mayresult in decreased performance.");
        }
    }

    private void rebuild() {
        synchronized (this.mLock) {
            try {
                this.mActorPkgToPkgs.clear();
                for (java.lang.String str : this.mActorToTargetToOverlays.keySet()) {
                    java.lang.String actorPkg = this.mProvider.getActorPkg(str);
                    if (!android.text.TextUtils.isEmpty(actorPkg)) {
                        android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> arrayMap = this.mActorToTargetToOverlays.get(str);
                        java.util.HashSet hashSet = new java.util.HashSet();
                        for (java.lang.String str2 : arrayMap.keySet()) {
                            android.util.ArraySet<java.lang.String> arraySet = arrayMap.get(str2);
                            hashSet.add(str2);
                            hashSet.addAll(arraySet);
                        }
                        this.mActorPkgToPkgs.put(actorPkg, hashSet);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void addTargetToMap(java.lang.String str, java.lang.String str2, @android.annotation.NonNull java.util.Collection<java.lang.String> collection) {
        android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> arrayMap = this.mActorToTargetToOverlays.get(str);
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap<>();
            this.mActorToTargetToOverlays.put(str, arrayMap);
        }
        if (arrayMap.get(str2) == null) {
            arrayMap.put(str2, new android.util.ArraySet<>());
        }
        collection.add(this.mProvider.getActorPkg(str));
    }

    private void addOverlayToMap(java.lang.String str, java.lang.String str2, java.lang.String str3, @android.annotation.NonNull java.util.Collection<java.lang.String> collection) {
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> arrayMap = this.mActorToTargetToOverlays.get(str);
                if (arrayMap == null) {
                    arrayMap = new android.util.ArrayMap<>();
                    this.mActorToTargetToOverlays.put(str, arrayMap);
                }
                android.util.ArraySet<java.lang.String> arraySet = arrayMap.get(str2);
                if (arraySet == null) {
                    arraySet = new android.util.ArraySet<>();
                    arrayMap.put(str2, arraySet);
                }
                arraySet.add(str3);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        collection.add(this.mProvider.getActorPkg(str));
    }
}
