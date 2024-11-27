package com.android.server.om;

/* loaded from: classes2.dex */
final class OverlayManagerServiceImpl {
    private static final int FLAG_OVERLAY_IS_BEING_REPLACED = 2;
    private static final int FLAG_SYSTEM_UPDATE_UNINSTALL = 4;

    @java.lang.Deprecated
    private static final int FLAG_TARGET_IS_BEING_REPLACED = 1;
    private final java.lang.String[] mDefaultOverlays;
    private final com.android.server.om.IdmapManager mIdmapManager;
    private final com.android.internal.content.om.OverlayConfig mOverlayConfig;
    private final com.android.server.om.PackageManagerHelper mPackageManager;
    private final com.android.server.om.OverlayManagerSettings mSettings;

    private boolean mustReinitializeOverlay(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable android.content.om.OverlayInfo overlayInfo) {
        boolean isPackageConfiguredMutable;
        if (overlayInfo == null || !java.util.Objects.equals(androidPackage.getOverlayTarget(), overlayInfo.targetPackageName) || !java.util.Objects.equals(androidPackage.getOverlayTargetOverlayableName(), overlayInfo.targetOverlayableName) || overlayInfo.isFabricated || (isPackageConfiguredMutable = isPackageConfiguredMutable(androidPackage)) != overlayInfo.isMutable) {
            return true;
        }
        if (!isPackageConfiguredMutable && isPackageConfiguredEnabled(androidPackage) != overlayInfo.isEnabled()) {
            return true;
        }
        return false;
    }

    private boolean mustReinitializeOverlay(@android.annotation.NonNull android.os.FabricatedOverlayInfo fabricatedOverlayInfo, @android.annotation.Nullable android.content.om.OverlayInfo overlayInfo) {
        if (overlayInfo == null || !java.util.Objects.equals(fabricatedOverlayInfo.targetPackageName, overlayInfo.targetPackageName) || !java.util.Objects.equals(fabricatedOverlayInfo.targetOverlayable, overlayInfo.targetOverlayableName)) {
            return true;
        }
        return false;
    }

    OverlayManagerServiceImpl(@android.annotation.NonNull com.android.server.om.PackageManagerHelper packageManagerHelper, @android.annotation.NonNull com.android.server.om.IdmapManager idmapManager, @android.annotation.NonNull com.android.server.om.OverlayManagerSettings overlayManagerSettings, @android.annotation.NonNull com.android.internal.content.om.OverlayConfig overlayConfig, @android.annotation.NonNull java.lang.String[] strArr) {
        this.mPackageManager = packageManagerHelper;
        this.mIdmapManager = idmapManager;
        this.mSettings = overlayManagerSettings;
        this.mOverlayConfig = overlayConfig;
        this.mDefaultOverlays = strArr;
    }

    @android.annotation.NonNull
    android.util.ArraySet<android.content.pm.UserPackage> updateOverlaysForUser(int i) {
        android.util.ArraySet<android.content.pm.UserPackage> arraySet = new android.util.ArraySet<>();
        final android.util.ArrayMap<java.lang.String, com.android.server.pm.pkg.PackageState> initializeForUser = this.mPackageManager.initializeForUser(i);
        com.android.internal.util.CollectionUtils.addAll(arraySet, removeOverlaysForUser(new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerServiceImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$updateOverlaysForUser$0;
                lambda$updateOverlaysForUser$0 = com.android.server.om.OverlayManagerServiceImpl.lambda$updateOverlaysForUser$0(initializeForUser, (android.content.om.OverlayInfo) obj);
                return lambda$updateOverlaysForUser$0;
            }
        }, i));
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        java.util.Iterator<com.android.server.pm.pkg.PackageState> it = initializeForUser.values().iterator();
        while (it.hasNext()) {
            com.android.server.pm.pkg.AndroidPackage androidPackage = it.next().getAndroidPackage();
            java.lang.String overlayTarget = androidPackage == null ? null : androidPackage.getOverlayTarget();
            if (!android.text.TextUtils.isEmpty(overlayTarget)) {
                arraySet2.add(overlayTarget);
            }
        }
        int size = initializeForUser.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.pm.pkg.PackageState valueAt = initializeForUser.valueAt(i2);
            com.android.server.pm.pkg.AndroidPackage androidPackage2 = valueAt.getAndroidPackage();
            if (androidPackage2 != null) {
                java.lang.String packageName = valueAt.getPackageName();
                try {
                    com.android.internal.util.CollectionUtils.addAll(arraySet, updatePackageOverlays(androidPackage2, i, 0));
                    if (arraySet2.contains(packageName)) {
                        arraySet.add(android.content.pm.UserPackage.of(i, packageName));
                    }
                } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e) {
                    android.util.Slog.e("OverlayManager", "failed to initialize overlays of '" + packageName + "' for user " + i + "", e);
                }
            }
        }
        for (android.os.FabricatedOverlayInfo fabricatedOverlayInfo : getFabricatedOverlayInfos()) {
            try {
                com.android.internal.util.CollectionUtils.addAll(arraySet, registerFabricatedOverlay(fabricatedOverlayInfo, i));
            } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e2) {
                android.util.Slog.e("OverlayManager", "failed to initialize fabricated overlay of '" + fabricatedOverlayInfo.path + "' for user " + i + "", e2);
            }
        }
        android.util.ArraySet arraySet3 = new android.util.ArraySet();
        android.util.ArrayMap<java.lang.String, java.util.List<android.content.om.OverlayInfo>> overlaysForUser = this.mSettings.getOverlaysForUser(i);
        int size2 = overlaysForUser.size();
        for (int i3 = 0; i3 < size2; i3++) {
            java.util.List<android.content.om.OverlayInfo> valueAt2 = overlaysForUser.valueAt(i3);
            int size3 = valueAt2 != null ? valueAt2.size() : 0;
            for (int i4 = 0; i4 < size3; i4++) {
                android.content.om.OverlayInfo overlayInfo = valueAt2.get(i4);
                if (overlayInfo.isEnabled()) {
                    arraySet3.add(overlayInfo.category);
                }
            }
        }
        for (java.lang.String str : this.mDefaultOverlays) {
            try {
                android.content.om.OverlayIdentifier overlayIdentifier = new android.content.om.OverlayIdentifier(str);
                android.content.om.OverlayInfo overlayInfo2 = this.mSettings.getOverlayInfo(overlayIdentifier, i);
                if (!arraySet3.contains(overlayInfo2.category)) {
                    android.util.Slog.w("OverlayManager", "Enabling default overlay '" + str + "' for target '" + overlayInfo2.targetPackageName + "' in category '" + overlayInfo2.category + "' for user " + i);
                    this.mSettings.setEnabled(overlayIdentifier, i, true);
                    if (updateState(overlayInfo2, i, 0)) {
                        com.android.internal.util.CollectionUtils.add(arraySet, android.content.pm.UserPackage.of(overlayInfo2.userId, overlayInfo2.targetPackageName));
                    }
                }
            } catch (com.android.server.om.OverlayManagerSettings.BadKeyException e3) {
                android.util.Slog.e("OverlayManager", "Failed to set default overlay '" + str + "' for user " + i, e3);
            }
        }
        cleanStaleResourceCache();
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateOverlaysForUser$0(android.util.ArrayMap arrayMap, android.content.om.OverlayInfo overlayInfo) {
        return !arrayMap.containsKey(overlayInfo.packageName);
    }

    void onUserRemoved(int i) {
        this.mSettings.removeUser(i);
    }

    @android.annotation.NonNull
    java.util.Set<android.content.pm.UserPackage> onPackageAdded(@android.annotation.NonNull java.lang.String str, int i) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        arraySet.add(android.content.pm.UserPackage.of(i, str));
        arraySet.addAll(reconcileSettingsForPackage(str, i, 0));
        return arraySet;
    }

    @android.annotation.NonNull
    java.util.Set<android.content.pm.UserPackage> onPackageChanged(@android.annotation.NonNull java.lang.String str, int i) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        return reconcileSettingsForPackage(str, i, 0);
    }

    @android.annotation.NonNull
    java.util.Set<android.content.pm.UserPackage> onPackageReplacing(@android.annotation.NonNull java.lang.String str, boolean z, int i) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        int i2;
        if (!z) {
            i2 = 2;
        } else {
            i2 = 6;
        }
        return reconcileSettingsForPackage(str, i, i2);
    }

    @android.annotation.NonNull
    java.util.Set<android.content.pm.UserPackage> onPackageReplaced(@android.annotation.NonNull java.lang.String str, int i) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        return reconcileSettingsForPackage(str, i, 0);
    }

    @android.annotation.NonNull
    java.util.Set<android.content.pm.UserPackage> onPackageRemoved(@android.annotation.NonNull final java.lang.String str, int i) {
        return com.android.internal.util.CollectionUtils.addAll(updateOverlaysForTarget(str, i, 0), removeOverlaysForUser(new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerServiceImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onPackageRemoved$1;
                lambda$onPackageRemoved$1 = com.android.server.om.OverlayManagerServiceImpl.lambda$onPackageRemoved$1(str, (android.content.om.OverlayInfo) obj);
                return lambda$onPackageRemoved$1;
            }
        }, i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onPackageRemoved$1(java.lang.String str, android.content.om.OverlayInfo overlayInfo) {
        return str.equals(overlayInfo.packageName);
    }

    @android.annotation.NonNull
    private java.util.Set<android.content.pm.UserPackage> removeOverlaysForUser(@android.annotation.NonNull final java.util.function.Predicate<android.content.om.OverlayInfo> predicate, final int i) {
        java.util.List<android.content.om.OverlayInfo> removeIf = this.mSettings.removeIf(new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerServiceImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeOverlaysForUser$2;
                lambda$removeOverlaysForUser$2 = com.android.server.om.OverlayManagerServiceImpl.lambda$removeOverlaysForUser$2(i, predicate, (android.content.om.OverlayInfo) obj);
                return lambda$removeOverlaysForUser$2;
            }
        });
        java.util.Set<android.content.pm.UserPackage> emptySet = java.util.Collections.emptySet();
        int size = removeIf.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.om.OverlayInfo overlayInfo = removeIf.get(i2);
            emptySet = com.android.internal.util.CollectionUtils.add(emptySet, android.content.pm.UserPackage.of(i, overlayInfo.targetPackageName));
            removeIdmapIfPossible(overlayInfo);
        }
        return emptySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeOverlaysForUser$2(int i, java.util.function.Predicate predicate, android.content.om.OverlayInfo overlayInfo) {
        return i == overlayInfo.userId && predicate.test(overlayInfo);
    }

    @android.annotation.NonNull
    private java.util.Set<android.content.pm.UserPackage> updateOverlaysForTarget(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        boolean remove;
        java.util.List<android.content.om.OverlayInfo> overlaysForTarget = this.mSettings.getOverlaysForTarget(str, i);
        int size = overlaysForTarget.size();
        boolean z = false;
        for (int i3 = 0; i3 < size; i3++) {
            android.content.om.OverlayInfo overlayInfo = overlaysForTarget.get(i3);
            try {
                remove = updateState(overlayInfo, i, i2);
            } catch (com.android.server.om.OverlayManagerSettings.BadKeyException e) {
                android.util.Slog.e("OverlayManager", "failed to update settings", e);
                remove = this.mSettings.remove(overlayInfo.getOverlayIdentifier(), i);
            }
            z |= remove;
        }
        if (!z) {
            return java.util.Collections.emptySet();
        }
        return java.util.Set.of(android.content.pm.UserPackage.of(i, str));
    }

    @android.annotation.NonNull
    private java.util.Set<android.content.pm.UserPackage> updatePackageOverlays(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        if (androidPackage.getOverlayTarget() == null) {
            return java.util.Collections.emptySet();
        }
        java.util.Set<android.content.pm.UserPackage> emptySet = java.util.Collections.emptySet();
        android.content.om.OverlayIdentifier overlayIdentifier = new android.content.om.OverlayIdentifier(androidPackage.getPackageName());
        int packageConfiguredPriority = getPackageConfiguredPriority(androidPackage);
        try {
            android.content.om.OverlayInfo nullableOverlayInfo = this.mSettings.getNullableOverlayInfo(overlayIdentifier, i);
            if (mustReinitializeOverlay(androidPackage, nullableOverlayInfo)) {
                if (nullableOverlayInfo != null) {
                    emptySet = com.android.internal.util.CollectionUtils.add(emptySet, android.content.pm.UserPackage.of(i, nullableOverlayInfo.targetPackageName));
                }
                nullableOverlayInfo = this.mSettings.init(overlayIdentifier, i, androidPackage.getOverlayTarget(), androidPackage.getOverlayTargetOverlayableName(), ((com.android.server.pm.pkg.AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath(), isPackageConfiguredMutable(androidPackage), isPackageConfiguredEnabled(androidPackage), getPackageConfiguredPriority(androidPackage), androidPackage.getOverlayCategory(), false);
            } else if (packageConfiguredPriority != nullableOverlayInfo.priority) {
                this.mSettings.setPriority(overlayIdentifier, i, packageConfiguredPriority);
                emptySet = com.android.internal.util.CollectionUtils.add(emptySet, android.content.pm.UserPackage.of(i, nullableOverlayInfo.targetPackageName));
            }
            if (updateState(nullableOverlayInfo, i, i2)) {
                return com.android.internal.util.CollectionUtils.add(emptySet, android.content.pm.UserPackage.of(i, nullableOverlayInfo.targetPackageName));
            }
            return emptySet;
        } catch (com.android.server.om.OverlayManagerSettings.BadKeyException e) {
            throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("failed to update settings", e);
        }
    }

    @android.annotation.NonNull
    private java.util.Set<android.content.pm.UserPackage> reconcileSettingsForPackage(@android.annotation.NonNull java.lang.String str, int i, int i2) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        java.util.Set addAll = com.android.internal.util.CollectionUtils.addAll(java.util.Collections.emptySet(), updateOverlaysForTarget(str, i, i2));
        com.android.server.pm.pkg.PackageState packageStateForUser = this.mPackageManager.getPackageStateForUser(str, i);
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
        if (androidPackage == null) {
            return onPackageRemoved(str, i);
        }
        return com.android.internal.util.CollectionUtils.addAll(addAll, updatePackageOverlays(androidPackage, i, i2));
    }

    android.content.om.OverlayInfo getOverlayInfo(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) {
        try {
            return this.mSettings.getOverlayInfo(overlayIdentifier, i);
        } catch (com.android.server.om.OverlayManagerSettings.BadKeyException e) {
            return null;
        }
    }

    java.util.List<android.content.om.OverlayInfo> getOverlayInfosForTarget(@android.annotation.NonNull java.lang.String str, int i) {
        return this.mSettings.getOverlaysForTarget(str, i);
    }

    java.util.Map<java.lang.String, java.util.List<android.content.om.OverlayInfo>> getOverlaysForUser(int i) {
        return this.mSettings.getOverlaysForUser(i);
    }

    @android.annotation.NonNull
    java.util.Set<android.content.pm.UserPackage> setEnabled(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, boolean z, int i) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        try {
            android.content.om.OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(overlayIdentifier, i);
            if (!overlayInfo.isMutable) {
                throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("cannot enable immutable overlay packages in runtime");
            }
            if (this.mSettings.setEnabled(overlayIdentifier, i, z) | updateState(overlayInfo, i, 0)) {
                return java.util.Set.of(android.content.pm.UserPackage.of(i, overlayInfo.targetPackageName));
            }
            return java.util.Set.of();
        } catch (com.android.server.om.OverlayManagerSettings.BadKeyException e) {
            throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("failed to update settings", e);
        }
    }

    java.util.Optional<android.content.pm.UserPackage> setEnabledExclusive(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, boolean z, int i) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        try {
            android.content.om.OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(overlayIdentifier, i);
            if (!overlayInfo.isMutable) {
                throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("cannot enable immutable overlay packages in runtime");
            }
            java.util.List<android.content.om.OverlayInfo> overlayInfosForTarget = getOverlayInfosForTarget(overlayInfo.targetPackageName, i);
            overlayInfosForTarget.remove(overlayInfo);
            boolean z2 = false;
            for (int i2 = 0; i2 < overlayInfosForTarget.size(); i2++) {
                android.content.om.OverlayInfo overlayInfo2 = overlayInfosForTarget.get(i2);
                android.content.om.OverlayIdentifier overlayIdentifier2 = overlayInfo2.getOverlayIdentifier();
                if (overlayInfo2.isMutable && (!z || java.util.Objects.equals(overlayInfo2.category, overlayInfo.category))) {
                    z2 = z2 | this.mSettings.setEnabled(overlayIdentifier2, i, false) | updateState(overlayInfo2, i, 0);
                }
            }
            if (this.mSettings.setEnabled(overlayIdentifier, i, true) | z2 | updateState(overlayInfo, i, 0)) {
                return java.util.Optional.of(android.content.pm.UserPackage.of(i, overlayInfo.targetPackageName));
            }
            return java.util.Optional.empty();
        } catch (com.android.server.om.OverlayManagerSettings.BadKeyException e) {
            throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("failed to update settings", e);
        }
    }

    @android.annotation.NonNull
    java.util.Set<android.content.pm.UserPackage> registerFabricatedOverlay(@android.annotation.NonNull android.os.FabricatedOverlayInternal fabricatedOverlayInternal) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        if (android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(fabricatedOverlayInternal.overlayName, false, true) != null) {
            throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("overlay name can only consist of alphanumeric characters, '_', and '.'");
        }
        android.os.FabricatedOverlayInfo createFabricatedOverlay = this.mIdmapManager.createFabricatedOverlay(fabricatedOverlayInternal);
        if (createFabricatedOverlay == null) {
            throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("failed to create fabricated overlay");
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (int i : this.mSettings.getUsers()) {
            arraySet.addAll(registerFabricatedOverlay(createFabricatedOverlay, i));
        }
        return arraySet;
    }

    @android.annotation.NonNull
    private java.util.Set<android.content.pm.UserPackage> registerFabricatedOverlay(@android.annotation.NonNull android.os.FabricatedOverlayInfo fabricatedOverlayInfo, int i) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        android.content.om.OverlayIdentifier overlayIdentifier = new android.content.om.OverlayIdentifier(fabricatedOverlayInfo.packageName, fabricatedOverlayInfo.overlayName);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        android.content.om.OverlayInfo nullableOverlayInfo = this.mSettings.getNullableOverlayInfo(overlayIdentifier, i);
        if (nullableOverlayInfo != null && !nullableOverlayInfo.isFabricated) {
            throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("non-fabricated overlay with name '" + nullableOverlayInfo.overlayName + "' already present in '" + nullableOverlayInfo.packageName + "'");
        }
        try {
            if (mustReinitializeOverlay(fabricatedOverlayInfo, nullableOverlayInfo)) {
                if (nullableOverlayInfo != null) {
                    arraySet.add(android.content.pm.UserPackage.of(i, nullableOverlayInfo.targetPackageName));
                }
                nullableOverlayInfo = this.mSettings.init(overlayIdentifier, i, fabricatedOverlayInfo.targetPackageName, fabricatedOverlayInfo.targetOverlayable, fabricatedOverlayInfo.path, true, false, Integer.MAX_VALUE, null, true);
            } else {
                this.mSettings.setBaseCodePath(overlayIdentifier, i, fabricatedOverlayInfo.path);
            }
            if (updateState(nullableOverlayInfo, i, 0)) {
                arraySet.add(android.content.pm.UserPackage.of(i, nullableOverlayInfo.targetPackageName));
            }
            return arraySet;
        } catch (com.android.server.om.OverlayManagerSettings.BadKeyException e) {
            throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("failed to update settings", e);
        }
    }

    @android.annotation.NonNull
    java.util.Set<android.content.pm.UserPackage> unregisterFabricatedOverlay(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (int i : this.mSettings.getUsers()) {
            arraySet.addAll(unregisterFabricatedOverlay(overlayIdentifier, i));
        }
        return arraySet;
    }

    @android.annotation.NonNull
    private java.util.Set<android.content.pm.UserPackage> unregisterFabricatedOverlay(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) {
        android.content.om.OverlayInfo nullableOverlayInfo = this.mSettings.getNullableOverlayInfo(overlayIdentifier, i);
        if (nullableOverlayInfo != null) {
            this.mSettings.remove(overlayIdentifier, i);
            if (nullableOverlayInfo.isEnabled()) {
                return java.util.Set.of(android.content.pm.UserPackage.of(i, nullableOverlayInfo.targetPackageName));
            }
        }
        return java.util.Set.of();
    }

    private void cleanStaleResourceCache() {
        java.util.Set<java.lang.String> allBaseCodePaths = this.mSettings.getAllBaseCodePaths();
        for (android.os.FabricatedOverlayInfo fabricatedOverlayInfo : this.mIdmapManager.getFabricatedOverlayInfos()) {
            if (!allBaseCodePaths.contains(fabricatedOverlayInfo.path)) {
                this.mIdmapManager.deleteFabricatedOverlay(fabricatedOverlayInfo.path);
            }
        }
    }

    @android.annotation.NonNull
    private java.util.List<android.os.FabricatedOverlayInfo> getFabricatedOverlayInfos() {
        final java.util.Set<java.lang.String> allBaseCodePaths = this.mSettings.getAllBaseCodePaths();
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mIdmapManager.getFabricatedOverlayInfos());
        arrayList.removeIf(new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerServiceImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getFabricatedOverlayInfos$3;
                lambda$getFabricatedOverlayInfos$3 = com.android.server.om.OverlayManagerServiceImpl.lambda$getFabricatedOverlayInfos$3(allBaseCodePaths, (android.os.FabricatedOverlayInfo) obj);
                return lambda$getFabricatedOverlayInfos$3;
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getFabricatedOverlayInfos$3(java.util.Set set, android.os.FabricatedOverlayInfo fabricatedOverlayInfo) {
        return !set.contains(fabricatedOverlayInfo.path);
    }

    private boolean isPackageConfiguredMutable(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return this.mOverlayConfig.isMutable(androidPackage.getPackageName());
    }

    private int getPackageConfiguredPriority(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return this.mOverlayConfig.getPriority(androidPackage.getPackageName());
    }

    private boolean isPackageConfiguredEnabled(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return this.mOverlayConfig.isEnabled(androidPackage.getPackageName());
    }

    java.util.Optional<android.content.pm.UserPackage> setPriority(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, @android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier2, int i) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        try {
            android.content.om.OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(overlayIdentifier, i);
            if (!overlayInfo.isMutable) {
                throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("cannot change priority of an immutable overlay package at runtime");
            }
            if (this.mSettings.setPriority(overlayIdentifier, overlayIdentifier2, i)) {
                return java.util.Optional.of(android.content.pm.UserPackage.of(i, overlayInfo.targetPackageName));
            }
            return java.util.Optional.empty();
        } catch (com.android.server.om.OverlayManagerSettings.BadKeyException e) {
            throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("failed to update settings", e);
        }
    }

    java.util.Set<android.content.pm.UserPackage> setHighestPriority(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        try {
            android.content.om.OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(overlayIdentifier, i);
            if (!overlayInfo.isMutable) {
                throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("cannot change priority of an immutable overlay package at runtime");
            }
            if (this.mSettings.setHighestPriority(overlayIdentifier, i)) {
                return java.util.Set.of(android.content.pm.UserPackage.of(i, overlayInfo.targetPackageName));
            }
            return java.util.Set.of();
        } catch (com.android.server.om.OverlayManagerSettings.BadKeyException e) {
            throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("failed to update settings", e);
        }
    }

    java.util.Optional<android.content.pm.UserPackage> setLowestPriority(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        try {
            android.content.om.OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(overlayIdentifier, i);
            if (!overlayInfo.isMutable) {
                throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("cannot change priority of an immutable overlay package at runtime");
            }
            if (this.mSettings.setLowestPriority(overlayIdentifier, i)) {
                return java.util.Optional.of(android.content.pm.UserPackage.of(i, overlayInfo.targetPackageName));
            }
            return java.util.Optional.empty();
        } catch (com.android.server.om.OverlayManagerSettings.BadKeyException e) {
            throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("failed to update settings", e);
        }
    }

    void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.om.DumpState dumpState) {
        android.util.Pair pair;
        android.content.om.OverlayIdentifier overlayIdentifier;
        android.content.om.OverlayInfo nullableOverlayInfo;
        if (dumpState.getPackageName() != null && (nullableOverlayInfo = this.mSettings.getNullableOverlayInfo((overlayIdentifier = new android.content.om.OverlayIdentifier(dumpState.getPackageName(), dumpState.getOverlayName())), 0)) != null) {
            pair = new android.util.Pair(overlayIdentifier, nullableOverlayInfo.baseCodePath);
        } else {
            pair = null;
        }
        this.mSettings.dump(printWriter, dumpState);
        if (dumpState.getField() == null) {
            for (android.util.Pair<android.content.om.OverlayIdentifier, java.lang.String> pair2 : pair != null ? java.util.Set.of(pair) : this.mSettings.getAllIdentifiersAndBaseCodePaths()) {
                printWriter.println("IDMAP OF " + pair2.first);
                java.lang.String dumpIdmap = this.mIdmapManager.dumpIdmap((java.lang.String) pair2.second);
                if (dumpIdmap != null) {
                    printWriter.println(dumpIdmap);
                } else {
                    android.content.om.OverlayInfo nullableOverlayInfo2 = this.mSettings.getNullableOverlayInfo((android.content.om.OverlayIdentifier) pair2.first, 0);
                    printWriter.println((nullableOverlayInfo2 == null || this.mIdmapManager.idmapExists(nullableOverlayInfo2)) ? "<internal error>" : "<missing idmap>");
                }
            }
        }
        if (pair == null) {
            printWriter.println("Default overlays: " + android.text.TextUtils.join(";", this.mDefaultOverlays));
        }
        if (dumpState.getPackageName() == null) {
            this.mOverlayConfig.dump(printWriter);
        }
    }

    @android.annotation.NonNull
    java.lang.String[] getDefaultOverlayPackages() {
        return this.mDefaultOverlays;
    }

    void removeIdmapForOverlay(android.content.om.OverlayIdentifier overlayIdentifier, int i) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
        try {
            removeIdmapIfPossible(this.mSettings.getOverlayInfo(overlayIdentifier, i));
        } catch (com.android.server.om.OverlayManagerSettings.BadKeyException e) {
            throw new com.android.server.om.OverlayManagerServiceImpl.OperationFailedException("failed to update settings", e);
        }
    }

    android.content.pm.overlay.OverlayPaths getEnabledOverlayPaths(@android.annotation.NonNull java.lang.String str, int i, final boolean z) {
        final android.content.pm.overlay.OverlayPaths.Builder builder = new android.content.pm.overlay.OverlayPaths.Builder();
        this.mSettings.forEachMatching(i, null, str, new java.util.function.Consumer() { // from class: com.android.server.om.OverlayManagerServiceImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.om.OverlayManagerServiceImpl.lambda$getEnabledOverlayPaths$4(z, builder, (android.content.om.OverlayInfo) obj);
            }
        });
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getEnabledOverlayPaths$4(boolean z, android.content.pm.overlay.OverlayPaths.Builder builder, android.content.om.OverlayInfo overlayInfo) {
        if (!overlayInfo.isEnabled()) {
            return;
        }
        if (!z && !overlayInfo.isMutable) {
            return;
        }
        if (overlayInfo.isFabricated()) {
            builder.addNonApkPath(overlayInfo.baseCodePath);
        } else {
            builder.addApkPath(overlayInfo.baseCodePath);
        }
    }

    private boolean updateState(@android.annotation.NonNull android.content.om.CriticalOverlayInfo criticalOverlayInfo, int i, int i2) throws com.android.server.om.OverlayManagerSettings.BadKeyException {
        int i3;
        android.content.om.OverlayIdentifier overlayIdentifier = criticalOverlayInfo.getOverlayIdentifier();
        com.android.server.pm.pkg.PackageState packageStateForUser = this.mPackageManager.getPackageStateForUser(criticalOverlayInfo.getTargetPackageName(), i);
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
        com.android.server.pm.pkg.PackageState packageStateForUser2 = this.mPackageManager.getPackageStateForUser(criticalOverlayInfo.getPackageName(), i);
        com.android.server.pm.pkg.AndroidPackage androidPackage2 = packageStateForUser2 != null ? packageStateForUser2.getAndroidPackage() : null;
        if (androidPackage2 == null) {
            removeIdmapIfPossible(this.mSettings.getOverlayInfo(overlayIdentifier, i));
            return this.mSettings.remove(overlayIdentifier, i);
        }
        boolean category = this.mSettings.setCategory(overlayIdentifier, i, androidPackage2.getOverlayCategory()) | false;
        if (!criticalOverlayInfo.isFabricated()) {
            category |= this.mSettings.setBaseCodePath(overlayIdentifier, i, ((com.android.server.pm.pkg.AndroidPackageSplit) androidPackage2.getSplits().get(0)).getPath());
        }
        android.content.om.OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(overlayIdentifier, i);
        if (androidPackage != null && ((!com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(criticalOverlayInfo.getTargetPackageName()) && !"lineageos.platform".equals(criticalOverlayInfo.getTargetPackageName())) || isPackageConfiguredMutable(androidPackage2))) {
            int createIdmap = this.mIdmapManager.createIdmap(androidPackage, packageStateForUser2, androidPackage2, overlayInfo.baseCodePath, overlayIdentifier.getOverlayName(), i);
            category |= (createIdmap & 2) != 0;
            i3 = createIdmap;
        } else {
            i3 = 0;
        }
        int state = this.mSettings.getState(overlayIdentifier, i);
        int calculateNewState = calculateNewState(overlayInfo, androidPackage, i, i2, i3);
        if (state != calculateNewState) {
            return category | this.mSettings.setState(overlayIdentifier, i, calculateNewState);
        }
        return category;
    }

    private int calculateNewState(@android.annotation.NonNull android.content.om.OverlayInfo overlayInfo, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2, int i3) throws com.android.server.om.OverlayManagerSettings.BadKeyException {
        if ((i2 & 1) != 0) {
            return 4;
        }
        if ((i2 & 2) != 0) {
            return 5;
        }
        if ((i2 & 4) != 0) {
            return 7;
        }
        if (androidPackage == null) {
            return 0;
        }
        if ((i3 & 1) != 0 || this.mIdmapManager.idmapExists(overlayInfo)) {
            return this.mSettings.getEnabled(overlayInfo.getOverlayIdentifier(), i) ? 3 : 2;
        }
        return 1;
    }

    private void removeIdmapIfPossible(@android.annotation.NonNull android.content.om.OverlayInfo overlayInfo) {
        if (!this.mIdmapManager.idmapExists(overlayInfo)) {
            return;
        }
        for (int i : this.mSettings.getUsers()) {
            try {
                android.content.om.OverlayInfo overlayInfo2 = this.mSettings.getOverlayInfo(overlayInfo.getOverlayIdentifier(), i);
                if (overlayInfo2 != null && overlayInfo2.isEnabled()) {
                    return;
                }
            } catch (com.android.server.om.OverlayManagerSettings.BadKeyException e) {
            }
        }
        this.mIdmapManager.removeIdmap(overlayInfo, overlayInfo.userId);
    }

    static final class OperationFailedException extends java.lang.Exception {
        OperationFailedException(@android.annotation.NonNull java.lang.String str) {
            super(str);
        }

        OperationFailedException(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.Throwable th) {
            super(str, th);
        }
    }

    com.android.internal.content.om.OverlayConfig getOverlayConfig() {
        return this.mOverlayConfig;
    }
}
