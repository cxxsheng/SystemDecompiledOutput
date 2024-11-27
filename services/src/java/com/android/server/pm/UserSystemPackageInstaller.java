package com.android.server.pm;

/* loaded from: classes2.dex */
class UserSystemPackageInstaller {
    private static final boolean DEBUG = false;
    static final java.lang.String PACKAGE_WHITELIST_MODE_PROP = "persist.debug.user.package_whitelist_mode";
    private static final java.lang.String TAG = com.android.server.pm.UserSystemPackageInstaller.class.getSimpleName();
    static final int USER_TYPE_PACKAGE_WHITELIST_MODE_DEVICE_DEFAULT = -1;
    public static final int USER_TYPE_PACKAGE_WHITELIST_MODE_DISABLE = 0;
    public static final int USER_TYPE_PACKAGE_WHITELIST_MODE_ENFORCE = 1;
    public static final int USER_TYPE_PACKAGE_WHITELIST_MODE_IGNORE_OTA = 16;
    public static final int USER_TYPE_PACKAGE_WHITELIST_MODE_IMPLICIT_WHITELIST = 4;
    public static final int USER_TYPE_PACKAGE_WHITELIST_MODE_IMPLICIT_WHITELIST_SYSTEM = 8;
    public static final int USER_TYPE_PACKAGE_WHITELIST_MODE_LOG = 2;
    static final int USER_TYPE_PACKAGE_WHITELIST_MODE_NONE = -1000;
    private final com.android.server.pm.UserManagerService mUm;
    private final java.lang.String[] mUserTypes;
    private final android.util.ArrayMap<java.lang.String, java.lang.Long> mWhitelistedPackagesForUserTypes;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PackageWhitelistMode {
    }

    UserSystemPackageInstaller(com.android.server.pm.UserManagerService userManagerService, android.util.ArrayMap<java.lang.String, com.android.server.pm.UserTypeDetails> arrayMap) {
        this.mUm = userManagerService;
        this.mUserTypes = getAndSortKeysFromMap(arrayMap);
        if (this.mUserTypes.length > 64) {
            throw new java.lang.IllegalArgumentException("Device contains " + arrayMap.size() + " user types. However, UserSystemPackageInstaller does not work if there are more than 64 user types.");
        }
        this.mWhitelistedPackagesForUserTypes = determineWhitelistedPackagesForUserTypes(com.android.server.SystemConfig.getInstance());
    }

    @com.android.internal.annotations.VisibleForTesting
    UserSystemPackageInstaller(com.android.server.pm.UserManagerService userManagerService, android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap, java.lang.String[] strArr) {
        this.mUm = userManagerService;
        this.mUserTypes = strArr;
        this.mWhitelistedPackagesForUserTypes = arrayMap;
    }

    boolean installWhitelistedSystemPackages(final boolean z, boolean z2, @android.annotation.Nullable final android.util.ArraySet<java.lang.String> arraySet) {
        int whitelistMode = getWhitelistMode();
        checkWhitelistedSystemPackages(whitelistMode);
        boolean z3 = z2 && !isIgnoreOtaMode(whitelistMode);
        if (!z3 && !z) {
            return false;
        }
        if (z && !isEnforceMode(whitelistMode)) {
            return false;
        }
        java.lang.String str = TAG;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Reviewing whitelisted packages due to ");
        sb.append(z ? "[firstBoot]" : "");
        sb.append(z3 ? "[upgrade]" : "");
        android.util.Slog.i(str, sb.toString());
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        final android.util.SparseArrayMap sparseArrayMap = new android.util.SparseArrayMap();
        for (final int i : this.mUm.getUserIds()) {
            final java.util.Set<java.lang.String> installablePackagesForUserId = getInstallablePackagesForUserId(i);
            final boolean z4 = z3;
            packageManagerInternal.forEachPackageState(new java.util.function.Consumer() { // from class: com.android.server.pm.UserSystemPackageInstaller$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.UserSystemPackageInstaller.lambda$installWhitelistedSystemPackages$0(installablePackagesForUserId, i, z, z4, arraySet, sparseArrayMap, (com.android.server.pm.pkg.PackageStateInternal) obj);
                }
            });
        }
        packageManagerInternal.commitPackageStateMutation(null, new java.util.function.Consumer() { // from class: com.android.server.pm.UserSystemPackageInstaller$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.UserSystemPackageInstaller.lambda$installWhitelistedSystemPackages$1(sparseArrayMap, (com.android.server.pm.pkg.mutate.PackageStateMutator) obj);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$installWhitelistedSystemPackages$0(java.util.Set set, int i, boolean z, boolean z2, android.util.ArraySet arraySet, android.util.SparseArrayMap sparseArrayMap, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (packageStateInternal.getPkg() == null || !packageStateInternal.isSystem()) {
            return;
        }
        boolean z3 = (set == null || set.contains(packageStateInternal.getPackageName())) && !packageStateInternal.getTransientState().isHiddenUntilInstalled();
        if (packageStateInternal.getUserStateOrDefault(i).isInstalled() != z3 && shouldChangeInstallationState(packageStateInternal, z3, i, z, z2, arraySet)) {
            sparseArrayMap.add(i, packageStateInternal.getPackageName(), java.lang.Boolean.valueOf(z3));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$installWhitelistedSystemPackages$1(android.util.SparseArrayMap sparseArrayMap, com.android.server.pm.pkg.mutate.PackageStateMutator packageStateMutator) {
        int i;
        for (int i2 = 0; i2 < sparseArrayMap.numMaps(); i2++) {
            int keyAt = sparseArrayMap.keyAt(i2);
            int numElementsForKey = sparseArrayMap.numElementsForKey(keyAt);
            for (int i3 = 0; i3 < numElementsForKey; i3++) {
                java.lang.String str = (java.lang.String) sparseArrayMap.keyAt(i2, i3);
                boolean booleanValue = ((java.lang.Boolean) sparseArrayMap.valueAt(i2, i3)).booleanValue();
                com.android.server.pm.pkg.mutate.PackageUserStateWrite installed = packageStateMutator.forPackage(str).userState(keyAt).setInstalled(booleanValue);
                if (booleanValue) {
                    i = 0;
                } else {
                    i = 1;
                }
                installed.setUninstallReason(i);
                java.lang.String str2 = TAG + "CommitDebug";
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append(booleanValue ? "Installed " : "Uninstalled ");
                sb.append(str);
                sb.append(" for user ");
                sb.append(keyAt);
                android.util.Slog.i(str2, sb.toString());
            }
        }
    }

    private static boolean shouldChangeInstallationState(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, boolean z, int i, boolean z2, boolean z3, @android.annotation.Nullable android.util.ArraySet<java.lang.String> arraySet) {
        return z ? packageStateInternal.getUserStateOrDefault(i).getUninstallReason() == 1 : z2 || (z3 && !arraySet.contains(packageStateInternal.getPackageName()));
    }

    private void checkWhitelistedSystemPackages(int i) {
        if (!isLogMode(i) && !isEnforceMode(i)) {
            return;
        }
        android.util.Slog.v(TAG, "Checking that all system packages are whitelisted.");
        java.util.List<java.lang.String> packagesWhitelistWarnings = getPackagesWhitelistWarnings();
        int size = packagesWhitelistWarnings.size();
        if (size == 0) {
            android.util.Slog.v(TAG, "checkWhitelistedSystemPackages(mode=" + modeToString(i) + ") has no warnings");
        } else {
            android.util.Slog.w(TAG, "checkWhitelistedSystemPackages(mode=" + modeToString(i) + ") has " + size + " warnings:");
            for (int i2 = 0; i2 < size; i2++) {
                android.util.Slog.w(TAG, packagesWhitelistWarnings.get(i2));
            }
        }
        if (isImplicitWhitelistMode(i) && !isLogMode(i)) {
            return;
        }
        java.util.List<java.lang.String> packagesWhitelistErrors = getPackagesWhitelistErrors(i);
        int size2 = packagesWhitelistErrors.size();
        if (size2 == 0) {
            android.util.Slog.v(TAG, "checkWhitelistedSystemPackages(mode=" + modeToString(i) + ") has no errors");
            return;
        }
        android.util.Slog.e(TAG, "checkWhitelistedSystemPackages(mode=" + modeToString(i) + ") has " + size2 + " errors:");
        boolean isImplicitWhitelistMode = isImplicitWhitelistMode(i) ^ true;
        for (int i3 = 0; i3 < size2; i3++) {
            java.lang.String str = packagesWhitelistErrors.get(i3);
            if (isImplicitWhitelistMode) {
                android.util.Slog.wtf(TAG, str);
            } else {
                android.util.Slog.e(TAG, str);
            }
        }
    }

    @android.annotation.NonNull
    private java.util.List<java.lang.String> getPackagesWhitelistWarnings() {
        java.util.Set<java.lang.String> whitelistedSystemPackages = getWhitelistedSystemPackages();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        for (java.lang.String str : whitelistedSystemPackages) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = packageManagerInternal.getPackageStateInternal(str);
            com.android.server.pm.pkg.AndroidPackage androidPackage = packageStateInternal == null ? null : packageStateInternal.getAndroidPackage();
            if (androidPackage == null) {
                arrayList.add(java.lang.String.format("%s is allowlisted but not present.", str));
            } else if (!packageStateInternal.isSystem()) {
                arrayList.add(java.lang.String.format("%s is allowlisted and present but not a system package.", str));
            } else if (shouldUseOverlayTargetName(androidPackage)) {
                arrayList.add(java.lang.String.format("%s is allowlisted unnecessarily since it's a static overlay.", str));
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    private java.util.List<java.lang.String> getPackagesWhitelistErrors(int i) {
        if ((!isEnforceMode(i) || isImplicitWhitelistMode(i)) && !isLogMode(i)) {
            return java.util.Collections.emptyList();
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        final java.util.Set<java.lang.String> whitelistedSystemPackages = getWhitelistedSystemPackages();
        final android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        packageManagerInternal.forEachPackageState(new java.util.function.Consumer() { // from class: com.android.server.pm.UserSystemPackageInstaller$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.UserSystemPackageInstaller.lambda$getPackagesWhitelistErrors$2(whitelistedSystemPackages, packageManagerInternal, arrayList, (com.android.server.pm.pkg.PackageStateInternal) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getPackagesWhitelistErrors$2(java.util.Set set, android.content.pm.PackageManagerInternal packageManagerInternal, java.util.List list, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageStateInternal.getAndroidPackage();
        if (androidPackage == null || !packageStateInternal.isSystem() || androidPackage.isApex()) {
            return;
        }
        java.lang.String manifestPackageName = androidPackage.getManifestPackageName();
        if (!set.contains(manifestPackageName) && !shouldUseOverlayTargetName(packageManagerInternal.getPackage(manifestPackageName))) {
            list.add(java.lang.String.format("System package %s is not whitelisted using 'install-in-user-type' in SystemConfig for any user types!", manifestPackageName));
        }
    }

    boolean isEnforceMode() {
        return isEnforceMode(getWhitelistMode());
    }

    boolean isIgnoreOtaMode() {
        return isIgnoreOtaMode(getWhitelistMode());
    }

    boolean isLogMode() {
        return isLogMode(getWhitelistMode());
    }

    boolean isImplicitWhitelistMode() {
        return isImplicitWhitelistMode(getWhitelistMode());
    }

    boolean isImplicitWhitelistSystemMode() {
        return isImplicitWhitelistSystemMode(getWhitelistMode());
    }

    private static boolean shouldUseOverlayTargetName(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return androidPackage.isOverlayIsStatic();
    }

    private static boolean isEnforceMode(int i) {
        return (i & 1) != 0;
    }

    private static boolean isIgnoreOtaMode(int i) {
        return (i & 16) != 0;
    }

    private static boolean isLogMode(int i) {
        return (i & 2) != 0;
    }

    private static boolean isImplicitWhitelistMode(int i) {
        return (i & 4) != 0;
    }

    private static boolean isImplicitWhitelistSystemMode(int i) {
        return (i & 8) != 0;
    }

    private int getWhitelistMode() {
        int i = android.os.SystemProperties.getInt(PACKAGE_WHITELIST_MODE_PROP, -1);
        if (i != -1) {
            return i;
        }
        return getDeviceDefaultWhitelistMode();
    }

    private int getDeviceDefaultWhitelistMode() {
        return android.content.res.Resources.getSystem().getInteger(android.R.integer.config_timeDetectorAutoUpdateDiffMillis);
    }

    @android.annotation.NonNull
    static java.lang.String modeToString(int i) {
        switch (i) {
            case -1000:
                return "NONE";
            case -1:
                return "DEVICE_DEFAULT";
            default:
                return android.util.DebugUtils.flagsToString(com.android.server.pm.UserSystemPackageInstaller.class, "USER_TYPE_PACKAGE_WHITELIST_MODE_", i);
        }
    }

    @android.annotation.Nullable
    private java.util.Set<java.lang.String> getInstallablePackagesForUserId(int i) {
        return getInstallablePackagesForUserType(this.mUm.getUserInfo(i).userType);
    }

    @android.annotation.Nullable
    java.util.Set<java.lang.String> getInstallablePackagesForUserType(java.lang.String str) {
        int whitelistMode = getWhitelistMode();
        if (!isEnforceMode(whitelistMode)) {
            return null;
        }
        final boolean z = isImplicitWhitelistMode(whitelistMode) || (isImplicitWhitelistSystemMode(whitelistMode) && this.mUm.isUserTypeSubtypeOfSystem(str));
        final java.util.Set<java.lang.String> whitelistedPackagesForUserType = getWhitelistedPackagesForUserType(str);
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).forEachPackageState(new java.util.function.Consumer() { // from class: com.android.server.pm.UserSystemPackageInstaller$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.UserSystemPackageInstaller.this.lambda$getInstallablePackagesForUserType$3(whitelistedPackagesForUserType, z, arraySet, (com.android.server.pm.pkg.PackageStateInternal) obj);
            }
        });
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getInstallablePackagesForUserType$3(java.util.Set set, boolean z, java.util.Set set2, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageStateInternal.getAndroidPackage();
        if (androidPackage != null && packageStateInternal.isSystem() && shouldInstallPackage(androidPackage, this.mWhitelistedPackagesForUserTypes, set, z)) {
            set2.add(androidPackage.getPackageName());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean shouldInstallPackage(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap, @android.annotation.NonNull java.util.Set<java.lang.String> set, boolean z) {
        java.lang.String overlayTarget = shouldUseOverlayTargetName(androidPackage) ? androidPackage.getOverlayTarget() : androidPackage.getManifestPackageName();
        return (z && !arrayMap.containsKey(overlayTarget)) || set.contains(overlayTarget) || androidPackage.isApex();
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    java.util.Set<java.lang.String> getWhitelistedPackagesForUserType(java.lang.String str) {
        long userTypeMask = getUserTypeMask(str);
        android.util.ArraySet arraySet = new android.util.ArraySet(this.mWhitelistedPackagesForUserTypes.size());
        for (int i = 0; i < this.mWhitelistedPackagesForUserTypes.size(); i++) {
            java.lang.String keyAt = this.mWhitelistedPackagesForUserTypes.keyAt(i);
            if ((this.mWhitelistedPackagesForUserTypes.valueAt(i).longValue() & userTypeMask) != 0) {
                arraySet.add(keyAt);
            }
        }
        return arraySet;
    }

    private java.util.Set<java.lang.String> getWhitelistedSystemPackages() {
        return this.mWhitelistedPackagesForUserTypes.keySet();
    }

    @com.android.internal.annotations.VisibleForTesting
    android.util.ArrayMap<java.lang.String, java.lang.Long> determineWhitelistedPackagesForUserTypes(com.android.server.SystemConfig systemConfig) {
        java.util.Map<java.lang.String, java.lang.Long> baseTypeBitSets = getBaseTypeBitSets();
        android.util.ArrayMap<java.lang.String, java.util.Set<java.lang.String>> andClearPackageToUserTypeWhitelist = systemConfig.getAndClearPackageToUserTypeWhitelist();
        android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap = new android.util.ArrayMap<>(andClearPackageToUserTypeWhitelist.size() + 1);
        for (int i = 0; i < andClearPackageToUserTypeWhitelist.size(); i++) {
            java.lang.String intern = andClearPackageToUserTypeWhitelist.keyAt(i).intern();
            long typesBitSet = getTypesBitSet(andClearPackageToUserTypeWhitelist.valueAt(i), baseTypeBitSets);
            if (typesBitSet != 0) {
                arrayMap.put(intern, java.lang.Long.valueOf(typesBitSet));
            }
        }
        android.util.ArrayMap<java.lang.String, java.util.Set<java.lang.String>> andClearPackageToUserTypeBlacklist = systemConfig.getAndClearPackageToUserTypeBlacklist();
        for (int i2 = 0; i2 < andClearPackageToUserTypeBlacklist.size(); i2++) {
            java.lang.String intern2 = andClearPackageToUserTypeBlacklist.keyAt(i2).intern();
            long typesBitSet2 = getTypesBitSet(andClearPackageToUserTypeBlacklist.valueAt(i2), baseTypeBitSets);
            java.lang.Long l = arrayMap.get(intern2);
            if (l != null) {
                arrayMap.put(intern2, java.lang.Long.valueOf((~typesBitSet2) & l.longValue()));
            } else if (typesBitSet2 != 0) {
                arrayMap.put(intern2, 0L);
            }
        }
        arrayMap.put(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, -1L);
        return arrayMap;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getUserTypeMask(java.lang.String str) {
        if (java.util.Arrays.binarySearch(this.mUserTypes, str) >= 0) {
            return 1 << r3;
        }
        return 0L;
    }

    private java.util.Map<java.lang.String, java.lang.Long> getBaseTypeBitSets() {
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        for (int i = 0; i < this.mUserTypes.length; i++) {
            if (this.mUm.isUserTypeSubtypeOfFull(this.mUserTypes[i])) {
                j |= 1 << i;
            }
            if (this.mUm.isUserTypeSubtypeOfSystem(this.mUserTypes[i])) {
                j3 |= 1 << i;
            }
            if (this.mUm.isUserTypeSubtypeOfProfile(this.mUserTypes[i])) {
                j2 |= 1 << i;
            }
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(3);
        arrayMap.put("FULL", java.lang.Long.valueOf(j));
        arrayMap.put("SYSTEM", java.lang.Long.valueOf(j3));
        arrayMap.put("PROFILE", java.lang.Long.valueOf(j2));
        return arrayMap;
    }

    private long getTypesBitSet(java.lang.Iterable<java.lang.String> iterable, java.util.Map<java.lang.String, java.lang.Long> map) {
        long j = 0;
        for (java.lang.String str : iterable) {
            java.lang.Long l = map.get(str);
            if (l != null) {
                j |= l.longValue();
            } else {
                long userTypeMask = getUserTypeMask(str);
                if (userTypeMask != 0) {
                    j |= userTypeMask;
                } else {
                    android.util.Slog.w(TAG, "SystemConfig contained an invalid user type: " + str);
                }
            }
        }
        return j;
    }

    private static java.lang.String[] getAndSortKeysFromMap(android.util.ArrayMap<java.lang.String, ?> arrayMap) {
        java.lang.String[] strArr = new java.lang.String[arrayMap.size()];
        for (int i = 0; i < arrayMap.size(); i++) {
            strArr[i] = arrayMap.keyAt(i);
        }
        java.util.Arrays.sort(strArr);
        return strArr;
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        int whitelistMode = getWhitelistMode();
        indentingPrintWriter.println("Whitelisted packages per user type");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("Mode: ");
        indentingPrintWriter.print(whitelistMode);
        indentingPrintWriter.print(isEnforceMode(whitelistMode) ? " (enforced)" : "");
        indentingPrintWriter.print(isLogMode(whitelistMode) ? " (logged)" : "");
        indentingPrintWriter.print(isImplicitWhitelistMode(whitelistMode) ? " (implicit)" : "");
        indentingPrintWriter.print(isIgnoreOtaMode(whitelistMode) ? " (ignore OTAs)" : "");
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Legend");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mUserTypes.length; i++) {
            indentingPrintWriter.println(i + " -> " + this.mUserTypes[i]);
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.increaseIndent();
        int size = this.mWhitelistedPackagesForUserTypes.size();
        if (size == 0) {
            indentingPrintWriter.println("No packages");
            indentingPrintWriter.decreaseIndent();
            return;
        }
        indentingPrintWriter.print(size);
        indentingPrintWriter.println(" packages:");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < size; i2++) {
            indentingPrintWriter.print(this.mWhitelistedPackagesForUserTypes.keyAt(i2));
            indentingPrintWriter.print(": ");
            long longValue = this.mWhitelistedPackagesForUserTypes.valueAt(i2).longValue();
            for (int i3 = 0; i3 < this.mUserTypes.length; i3++) {
                if (((1 << i3) & longValue) != 0) {
                    indentingPrintWriter.print(i3);
                    indentingPrintWriter.print(" ");
                }
            }
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.increaseIndent();
        dumpPackageWhitelistProblems(indentingPrintWriter, whitelistMode, true, false);
        indentingPrintWriter.decreaseIndent();
    }

    void dumpPackageWhitelistProblems(android.util.IndentingPrintWriter indentingPrintWriter, int i, boolean z, boolean z2) {
        if (i == -1000) {
            i = getWhitelistMode();
        } else if (i == -1) {
            i = getDeviceDefaultWhitelistMode();
        }
        if (z2) {
            i &= -3;
        }
        android.util.Slog.v(TAG, "dumpPackageWhitelistProblems(): using mode " + modeToString(i));
        showIssues(indentingPrintWriter, z, getPackagesWhitelistErrors(i), "errors");
        if (z2) {
            return;
        }
        showIssues(indentingPrintWriter, z, getPackagesWhitelistWarnings(), "warnings");
    }

    private static void showIssues(android.util.IndentingPrintWriter indentingPrintWriter, boolean z, java.util.List<java.lang.String> list, java.lang.String str) {
        int size = list.size();
        if (size == 0) {
            if (z) {
                indentingPrintWriter.print("No ");
                indentingPrintWriter.println(str);
                return;
            }
            return;
        }
        if (z) {
            indentingPrintWriter.print(size);
            indentingPrintWriter.print(' ');
            indentingPrintWriter.println(str);
            indentingPrintWriter.increaseIndent();
        }
        for (int i = 0; i < size; i++) {
            indentingPrintWriter.println(list.get(i));
        }
        if (z) {
            indentingPrintWriter.decreaseIndent();
        }
    }
}
