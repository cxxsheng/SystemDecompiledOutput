package com.android.server.permission.access.appop;

/* compiled from: AppIdAppOpPolicy.kt */
/* loaded from: classes2.dex */
public final class AppIdAppOpPolicy extends com.android.server.permission.access.appop.BaseAppOpPolicy {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.appop.AppIdAppOpPolicy.Companion Companion = new com.android.server.permission.access.appop.AppIdAppOpPolicy.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.appop.AppIdAppOpPolicy.class.getSimpleName();

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.appop.AppIdAppOpMigration migration;

    @org.jetbrains.annotations.NotNull
    private volatile com.android.server.permission.access.immutable.IndexedListSet<com.android.server.permission.access.appop.AppIdAppOpPolicy.OnAppOpModeChangedListener> onAppOpModeChangedListeners;

    @org.jetbrains.annotations.NotNull
    private final java.lang.Object onAppOpModeChangedListenersLock;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.appop.AppIdAppOpUpgrade upgrade;

    /* compiled from: AppIdAppOpPolicy.kt */
    public static abstract class OnAppOpModeChangedListener {
        public abstract void onAppOpModeChanged(int i, int i2, @org.jetbrains.annotations.NotNull java.lang.String str, int i3, int i4);

        public abstract void onStateMutated();
    }

    public AppIdAppOpPolicy() {
        super(new com.android.server.permission.access.appop.AppIdAppOpPersistence());
        this.migration = new com.android.server.permission.access.appop.AppIdAppOpMigration();
        this.upgrade = new com.android.server.permission.access.appop.AppIdAppOpUpgrade(this);
        this.onAppOpModeChangedListeners = new com.android.server.permission.access.immutable.MutableIndexedListSet(null, 1, null);
        this.onAppOpModeChangedListenersLock = new java.lang.Object();
    }

    @Override // com.android.server.permission.access.SchemePolicy
    @org.jetbrains.annotations.NotNull
    public java.lang.String getSubjectScheme() {
        return com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID;
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onStateMutated(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$onStateMutated) {
        com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv = this.onAppOpModeChangedListeners;
        int size = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            com.android.server.permission.access.appop.AppIdAppOpPolicy.OnAppOpModeChangedListener it = $this$forEachIndexed$iv.elementAt(index$iv);
            it.onStateMutated();
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onAppIdRemoved(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onAppIdRemoved, int appId) {
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv = $this$onAppIdRemoved.getNewState().getUserStates();
        int size = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            $this$forEachIndexed$iv.keyAt(index$iv);
            com.android.server.permission.access.UserState userState = $this$forEachIndexed$iv.valueAt(index$iv);
            int userStateIndex = index$iv;
            int appIdIndex = userState.getAppIdAppOpModes().indexOfKey(appId);
            if (appIdIndex >= 0) {
                com.android.server.permission.access.MutableAccessState.mutateUserStateAt$default($this$onAppIdRemoved.getNewState(), userStateIndex, 0, 2, null).mutateAppIdAppOpModes().removeAt(appIdIndex);
            }
        }
    }

    @org.jetbrains.annotations.Nullable
    public final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> getAppOpModes(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$getAppOpModes, int appId, int userId) {
        com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> appIdAppOpModes;
        com.android.server.permission.access.UserState userState = $this$getAppOpModes.getState().getUserStates().get(userId);
        if (userState == null || (appIdAppOpModes = userState.getAppIdAppOpModes()) == null) {
            return null;
        }
        return appIdAppOpModes.get(appId);
    }

    public final boolean removeAppOpModes(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$removeAppOpModes, int appId, int userId) {
        int appIdIndex;
        int userStateIndex = $this$removeAppOpModes.getNewState().getUserStates().indexOfKey(userId);
        if (userStateIndex >= 0 && (appIdIndex = $this$removeAppOpModes.getNewState().getUserStates().valueAt(userStateIndex).getAppIdAppOpModes().indexOfKey(appId)) >= 0) {
            com.android.server.permission.access.MutableAccessState.mutateUserStateAt$default($this$removeAppOpModes.getNewState(), userStateIndex, 0, 2, null).mutateAppIdAppOpModes().removeAt(appIdIndex);
            return true;
        }
        return false;
    }

    public final int getAppOpMode(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$getAppOpMode, int appId, int userId, @org.jetbrains.annotations.NotNull java.lang.String appOpName) {
        com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> appIdAppOpModes;
        com.android.server.permission.access.UserState userState = $this$getAppOpMode.getState().getUserStates().get(userId);
        return ((java.lang.Number) com.android.server.permission.access.immutable.IndexedMapExtensionsKt.getWithDefault((userState == null || (appIdAppOpModes = userState.getAppIdAppOpModes()) == null) ? null : appIdAppOpModes.get(appId), appOpName, java.lang.Integer.valueOf(android.app.AppOpsManager.opToDefaultMode(appOpName)))).intValue();
    }

    public final boolean setAppOpMode(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$setAppOpMode, int appId, int userId, @org.jetbrains.annotations.NotNull java.lang.String appOpName, int mode) {
        if (!$this$setAppOpMode.getNewState().getUserStates().contains(userId)) {
            android.util.Slog.e(LOG_TAG, "Unable to set app op mode for missing user " + userId);
            return false;
        }
        int defaultMode = android.app.AppOpsManager.opToDefaultMode(appOpName);
        com.android.server.permission.access.UserState userState = $this$setAppOpMode.getNewState().getUserStates().get(userId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        int oldMode = ((java.lang.Number) com.android.server.permission.access.immutable.IndexedMapExtensionsKt.getWithDefault(userState.getAppIdAppOpModes().get(appId), appOpName, java.lang.Integer.valueOf(defaultMode))).intValue();
        if (oldMode == mode) {
            return false;
        }
        com.android.server.permission.access.MutableUserState mutateUserState$default = com.android.server.permission.access.MutableAccessState.mutateUserState$default($this$setAppOpMode.getNewState(), userId, 0, 2, null);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(mutateUserState$default);
        com.android.server.permission.access.immutable.MutableIntReferenceMap appIdAppOpModes = mutateUserState$default.mutateAppIdAppOpModes();
        com.android.server.permission.access.immutable.Immutable it$iv = appIdAppOpModes.mutate(appId);
        if (it$iv == null) {
            it$iv = new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null);
            appIdAppOpModes.put(appId, it$iv);
        }
        com.android.server.permission.access.immutable.MutableIndexedMap appOpModes = (com.android.server.permission.access.immutable.MutableIndexedMap) it$iv;
        com.android.server.permission.access.immutable.IndexedMapExtensionsKt.putWithDefault(appOpModes, appOpName, java.lang.Integer.valueOf(mode), java.lang.Integer.valueOf(defaultMode));
        if (appOpModes.isEmpty()) {
            com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt.minusAssign(appIdAppOpModes, appId);
        }
        com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv = this.onAppOpModeChangedListeners;
        int size = $this$forEachIndexed$iv.getSize();
        int index$iv = 0;
        while (index$iv < size) {
            com.android.server.permission.access.appop.AppIdAppOpPolicy.OnAppOpModeChangedListener it = $this$forEachIndexed$iv.elementAt(index$iv);
            it.onAppOpModeChanged(appId, userId, appOpName, oldMode, mode);
            index$iv++;
            size = size;
            $this$forEachIndexed$iv = $this$forEachIndexed$iv;
        }
        return true;
    }

    public final void addOnAppOpModeChangedListener(@org.jetbrains.annotations.NotNull com.android.server.permission.access.appop.AppIdAppOpPolicy.OnAppOpModeChangedListener listener) {
        synchronized (this.onAppOpModeChangedListenersLock) {
            this.onAppOpModeChangedListeners = com.android.server.permission.access.immutable.IndexedListSetExtensionsKt.plus(this.onAppOpModeChangedListeners, listener);
            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void migrateUserState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        com.android.server.permission.access.appop.AppIdAppOpMigration $this$migrateUserState_u24lambda_u246 = this.migration;
        $this$migrateUserState_u24lambda_u246.migrateUserState(state, userId);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void upgradePackageState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$upgradePackageState, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState, int userId, int version) {
        com.android.server.permission.access.appop.AppIdAppOpUpgrade $this$upgradePackageState_u24lambda_u247 = this.upgrade;
        $this$upgradePackageState_u24lambda_u247.upgradePackageState($this$upgradePackageState, packageState, userId, version);
    }

    /* compiled from: AppIdAppOpPolicy.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
