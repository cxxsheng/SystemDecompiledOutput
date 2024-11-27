package com.android.server.permission.access.appop;

/* compiled from: PackageAppOpPolicy.kt */
/* loaded from: classes2.dex */
public final class PackageAppOpPolicy extends com.android.server.permission.access.appop.BaseAppOpPolicy {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.appop.PackageAppOpPolicy.Companion Companion = new com.android.server.permission.access.appop.PackageAppOpPolicy.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.appop.PackageAppOpPolicy.class.getSimpleName();

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.appop.PackageAppOpMigration migration;

    @org.jetbrains.annotations.NotNull
    private volatile com.android.server.permission.access.immutable.IndexedListSet<com.android.server.permission.access.appop.PackageAppOpPolicy.OnAppOpModeChangedListener> onAppOpModeChangedListeners;

    @org.jetbrains.annotations.NotNull
    private final java.lang.Object onAppOpModeChangedListenersLock;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.appop.PackageAppOpUpgrade upgrade;

    /* compiled from: PackageAppOpPolicy.kt */
    public static abstract class OnAppOpModeChangedListener {
        public abstract void onAppOpModeChanged(@org.jetbrains.annotations.NotNull java.lang.String str, int i, @org.jetbrains.annotations.NotNull java.lang.String str2, int i2, int i3);

        public abstract void onStateMutated();
    }

    public PackageAppOpPolicy() {
        super(new com.android.server.permission.access.appop.PackageAppOpPersistence());
        this.migration = new com.android.server.permission.access.appop.PackageAppOpMigration();
        this.upgrade = new com.android.server.permission.access.appop.PackageAppOpUpgrade(this);
        this.onAppOpModeChangedListeners = new com.android.server.permission.access.immutable.MutableIndexedListSet(null, 1, null);
        this.onAppOpModeChangedListenersLock = new java.lang.Object();
    }

    @Override // com.android.server.permission.access.SchemePolicy
    @org.jetbrains.annotations.NotNull
    public java.lang.String getSubjectScheme() {
        return com.android.server.pm.Settings.ATTR_PACKAGE;
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onStateMutated(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$onStateMutated) {
        com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv = this.onAppOpModeChangedListeners;
        int size = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            com.android.server.permission.access.appop.PackageAppOpPolicy.OnAppOpModeChangedListener it = $this$forEachIndexed$iv.elementAt(index$iv);
            it.onStateMutated();
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onPackageRemoved(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageRemoved, @org.jetbrains.annotations.NotNull java.lang.String packageName, int appId) {
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv = $this$onPackageRemoved.getNewState().getUserStates();
        int size = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            $this$forEachIndexed$iv.keyAt(index$iv);
            com.android.server.permission.access.UserState userState = $this$forEachIndexed$iv.valueAt(index$iv);
            int userStateIndex = index$iv;
            int packageNameIndex = userState.getPackageAppOpModes().indexOfKey(packageName);
            if (packageNameIndex >= 0) {
                com.android.server.permission.access.MutableAccessState.mutateUserStateAt$default($this$onPackageRemoved.getNewState(), userStateIndex, 0, 2, null).mutatePackageAppOpModes().removeAt(packageNameIndex);
            }
        }
    }

    @org.jetbrains.annotations.Nullable
    public final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> getAppOpModes(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$getAppOpModes, @org.jetbrains.annotations.NotNull java.lang.String packageName, int userId) {
        com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> packageAppOpModes;
        com.android.server.permission.access.UserState userState = $this$getAppOpModes.getState().getUserStates().get(userId);
        if (userState == null || (packageAppOpModes = userState.getPackageAppOpModes()) == null) {
            return null;
        }
        return packageAppOpModes.get(packageName);
    }

    public final boolean removeAppOpModes(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$removeAppOpModes, @org.jetbrains.annotations.NotNull java.lang.String packageName, int userId) {
        int packageNameIndex;
        int userStateIndex = $this$removeAppOpModes.getNewState().getUserStates().indexOfKey(userId);
        if (userStateIndex < 0 || (packageNameIndex = $this$removeAppOpModes.getNewState().getUserStates().valueAt(userStateIndex).getPackageAppOpModes().indexOfKey(packageName)) < 0) {
            return false;
        }
        com.android.server.permission.access.MutableAccessState.mutateUserStateAt$default($this$removeAppOpModes.getNewState(), userStateIndex, 0, 2, null).mutatePackageAppOpModes().removeAt(packageNameIndex);
        return true;
    }

    public final int getAppOpMode(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$getAppOpMode, @org.jetbrains.annotations.NotNull java.lang.String packageName, int userId, @org.jetbrains.annotations.NotNull java.lang.String appOpName) {
        com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> packageAppOpModes;
        com.android.server.permission.access.UserState userState = $this$getAppOpMode.getState().getUserStates().get(userId);
        return ((java.lang.Number) com.android.server.permission.access.immutable.IndexedMapExtensionsKt.getWithDefault((userState == null || (packageAppOpModes = userState.getPackageAppOpModes()) == null) ? null : packageAppOpModes.get(packageName), appOpName, java.lang.Integer.valueOf(android.app.AppOpsManager.opToDefaultMode(appOpName)))).intValue();
    }

    public final boolean setAppOpMode(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$setAppOpMode, @org.jetbrains.annotations.NotNull java.lang.String packageName, int userId, @org.jetbrains.annotations.NotNull java.lang.String appOpName, int mode) {
        if (!$this$setAppOpMode.getNewState().getUserStates().contains(userId)) {
            android.util.Slog.e(LOG_TAG, "Unable to set app op mode for missing user " + userId);
            return false;
        }
        int defaultMode = android.app.AppOpsManager.opToDefaultMode(appOpName);
        com.android.server.permission.access.UserState userState = $this$setAppOpMode.getNewState().getUserStates().get(userId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        int oldMode = ((java.lang.Number) com.android.server.permission.access.immutable.IndexedMapExtensionsKt.getWithDefault(userState.getPackageAppOpModes().get(packageName), appOpName, java.lang.Integer.valueOf(defaultMode))).intValue();
        if (oldMode == mode) {
            return false;
        }
        com.android.server.permission.access.MutableUserState mutateUserState$default = com.android.server.permission.access.MutableAccessState.mutateUserState$default($this$setAppOpMode.getNewState(), userId, 0, 2, null);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(mutateUserState$default);
        com.android.server.permission.access.immutable.MutableIndexedReferenceMap packageAppOpModes = mutateUserState$default.mutatePackageAppOpModes();
        com.android.server.permission.access.immutable.Immutable it$iv = packageAppOpModes.mutate(packageName);
        if (it$iv == null) {
            it$iv = new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null);
            packageAppOpModes.put(packageName, it$iv);
        }
        com.android.server.permission.access.immutable.MutableIndexedMap appOpModes = (com.android.server.permission.access.immutable.MutableIndexedMap) it$iv;
        com.android.server.permission.access.immutable.IndexedMapExtensionsKt.putWithDefault(appOpModes, appOpName, java.lang.Integer.valueOf(mode), java.lang.Integer.valueOf(defaultMode));
        if (appOpModes.isEmpty()) {
            packageAppOpModes.remove(packageName);
        }
        com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv = this.onAppOpModeChangedListeners;
        int size = $this$forEachIndexed$iv.getSize();
        int index$iv = 0;
        while (index$iv < size) {
            com.android.server.permission.access.appop.PackageAppOpPolicy.OnAppOpModeChangedListener it = $this$forEachIndexed$iv.elementAt(index$iv);
            it.onAppOpModeChanged(packageName, userId, appOpName, oldMode, mode);
            index$iv++;
            size = size;
            $this$forEachIndexed$iv = $this$forEachIndexed$iv;
        }
        return true;
    }

    public final void addOnAppOpModeChangedListener(@org.jetbrains.annotations.NotNull com.android.server.permission.access.appop.PackageAppOpPolicy.OnAppOpModeChangedListener listener) {
        synchronized (this.onAppOpModeChangedListenersLock) {
            this.onAppOpModeChangedListeners = com.android.server.permission.access.immutable.IndexedListSetExtensionsKt.plus(this.onAppOpModeChangedListeners, listener);
            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void migrateUserState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        com.android.server.permission.access.appop.PackageAppOpMigration $this$migrateUserState_u24lambda_u246 = this.migration;
        $this$migrateUserState_u24lambda_u246.migrateUserState(state, userId);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void upgradePackageState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$upgradePackageState, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState, int userId, int version) {
        com.android.server.permission.access.appop.PackageAppOpUpgrade $this$upgradePackageState_u24lambda_u247 = this.upgrade;
        $this$upgradePackageState_u24lambda_u247.upgradePackageState($this$upgradePackageState, packageState, userId, version);
    }

    /* compiled from: PackageAppOpPolicy.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
