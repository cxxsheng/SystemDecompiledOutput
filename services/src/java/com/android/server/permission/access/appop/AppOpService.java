package com.android.server.permission.access.appop;

/* compiled from: AppOpService.kt */
/* loaded from: classes2.dex */
public final class AppOpService implements com.android.server.appop.AppOpsCheckingServiceInterface {

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.appop.AppIdAppOpPolicy appIdPolicy;

    @org.jetbrains.annotations.NotNull
    private final android.content.Context context;
    private android.os.Handler handler;

    @org.jetbrains.annotations.NotNull
    private volatile android.util.ArraySet<com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener> listeners;

    @org.jetbrains.annotations.NotNull
    private final java.lang.Object listenersLock;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.appop.PackageAppOpPolicy packagePolicy;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.AccessCheckingService service;

    public AppOpService(@org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessCheckingService service) {
        this.service = service;
        com.android.server.permission.access.SchemePolicy schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar = this.service.getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(com.android.server.pm.Settings.ATTR_PACKAGE, "app-op");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar, "null cannot be cast to non-null type com.android.server.permission.access.appop.PackageAppOpPolicy");
        this.packagePolicy = (com.android.server.permission.access.appop.PackageAppOpPolicy) schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar;
        com.android.server.permission.access.SchemePolicy schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2 = this.service.getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, "app-op");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2, "null cannot be cast to non-null type com.android.server.permission.access.appop.AppIdAppOpPolicy");
        this.appIdPolicy = (com.android.server.permission.access.appop.AppIdAppOpPolicy) schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2;
        this.context = this.service.getContext();
        this.listeners = new android.util.ArraySet<>();
        this.listenersLock = new java.lang.Object();
    }

    public final void initialize() {
        this.handler = new android.os.Handler(this.context.getMainLooper());
        this.appIdPolicy.addOnAppOpModeChangedListener(new com.android.server.permission.access.appop.AppOpService.OnAppIdAppOpModeChangedListener());
        this.packagePolicy.addOnAppOpModeChangedListener(new com.android.server.permission.access.appop.AppOpService.OnPackageAppOpModeChangedListener());
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    @com.android.internal.annotations.VisibleForTesting
    public void writeState() {
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void readState() {
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    @com.android.internal.annotations.VisibleForTesting
    public void shutdown() {
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void systemReady() {
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    @org.jetbrains.annotations.NotNull
    public android.util.SparseIntArray getNonDefaultUidModes(int uid, @org.jetbrains.annotations.NotNull java.lang.String persistentDeviceId) {
        return opNameMapToOpSparseArray(getUidModes(uid));
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    @org.jetbrains.annotations.NotNull
    public android.util.SparseIntArray getNonDefaultPackageModes(@org.jetbrains.annotations.NotNull java.lang.String packageName, int userId) {
        return opNameMapToOpSparseArray(getPackageModes(packageName, userId));
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getUidMode(int uid, @org.jetbrains.annotations.NotNull java.lang.String persistentDeviceId, int op) {
        int appId = android.os.UserHandle.getAppId(uid);
        int userId = android.os.UserHandle.getUserId(uid);
        java.lang.String opName = android.app.AppOpsManager.opToPublicName(op);
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getUidMode_u24lambda_u241 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.appop.AppIdAppOpPolicy $this$getUidMode_u24lambda_u241_u24lambda_u240 = this.appIdPolicy;
        return $this$getUidMode_u24lambda_u241_u24lambda_u240.getAppOpMode($this$getUidMode_u24lambda_u241, appId, userId, opName);
    }

    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> getUidModes(int uid) {
        int appId = android.os.UserHandle.getAppId(uid);
        int userId = android.os.UserHandle.getUserId(uid);
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getUidModes_u24lambda_u243 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.appop.AppIdAppOpPolicy $this$getUidModes_u24lambda_u243_u24lambda_u242 = this.appIdPolicy;
        com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> appOpModes = $this$getUidModes_u24lambda_u243_u24lambda_u242.getAppOpModes($this$getUidModes_u24lambda_u243, appId, userId);
        if (appOpModes != null) {
            return appOpModes.getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar();
        }
        return null;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean setUidMode(int uid, @org.jetbrains.annotations.NotNull java.lang.String persistentDeviceId, int op, int mode) {
        int appId = android.os.UserHandle.getAppId(uid);
        int userId = android.os.UserHandle.getUserId(uid);
        java.lang.String opName = android.app.AppOpsManager.opToPublicName(op);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$BooleanRef wasChanged = new com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$BooleanRef();
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        synchronized (this_$iv.stateLock) {
            try {
                com.android.server.permission.access.AccessState accessState = this_$iv.state;
                if (accessState == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    accessState = null;
                }
                com.android.server.permission.access.AccessState oldState$iv = accessState;
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$setUidMode_u24lambda_u245 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.appop.AppIdAppOpPolicy $this$setUidMode_u24lambda_u245_u24lambda_u244 = this.appIdPolicy;
                wasChanged.element = java.lang.Boolean.valueOf($this$setUidMode_u24lambda_u245_u24lambda_u244.setAppOpMode($this$setUidMode_u24lambda_u245, appId, userId, opName, mode)).booleanValue();
                this_$iv.persistence.write(newState$iv);
                this_$iv.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return wasChanged.element;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getPackageMode(@org.jetbrains.annotations.NotNull java.lang.String packageName, int op, int userId) {
        java.lang.String opName = android.app.AppOpsManager.opToPublicName(op);
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getPackageMode_u24lambda_u247 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.appop.PackageAppOpPolicy $this$getPackageMode_u24lambda_u247_u24lambda_u246 = this.packagePolicy;
        return $this$getPackageMode_u24lambda_u247_u24lambda_u246.getAppOpMode($this$getPackageMode_u24lambda_u247, packageName, userId, opName);
    }

    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> getPackageModes(java.lang.String packageName, int userId) {
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getPackageModes_u24lambda_u249 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.appop.PackageAppOpPolicy $this$getPackageModes_u24lambda_u249_u24lambda_u248 = this.packagePolicy;
        com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> appOpModes = $this$getPackageModes_u24lambda_u249_u24lambda_u248.getAppOpModes($this$getPackageModes_u24lambda_u249, packageName, userId);
        if (appOpModes != null) {
            return appOpModes.getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar();
        }
        return null;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void setPackageMode(@org.jetbrains.annotations.NotNull java.lang.String packageName, int op, int mode, int userId) {
        java.lang.String opName = android.app.AppOpsManager.opToPublicName(op);
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        synchronized (this_$iv.stateLock) {
            try {
                com.android.server.permission.access.AccessState accessState = this_$iv.state;
                if (accessState == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    accessState = null;
                }
                com.android.server.permission.access.AccessState oldState$iv = accessState;
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$setPackageMode_u24lambda_u2411 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.appop.PackageAppOpPolicy $this$setPackageMode_u24lambda_u2411_u24lambda_u2410 = this.packagePolicy;
                $this$setPackageMode_u24lambda_u2411_u24lambda_u2410.setAppOpMode($this$setPackageMode_u24lambda_u2411, packageName, userId, opName, mode);
                this_$iv.persistence.write(newState$iv);
                this_$iv.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void removeUid(int uid) {
        int appId = android.os.UserHandle.getAppId(uid);
        int userId = android.os.UserHandle.getUserId(uid);
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        synchronized (this_$iv.stateLock) {
            try {
                com.android.server.permission.access.AccessState oldState$iv = this_$iv.state;
                if (oldState$iv == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    oldState$iv = null;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$removeUid_u24lambda_u2413 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.appop.AppIdAppOpPolicy $this$removeUid_u24lambda_u2413_u24lambda_u2412 = this.appIdPolicy;
                $this$removeUid_u24lambda_u2413_u24lambda_u2412.removeAppOpModes($this$removeUid_u24lambda_u2413, appId, userId);
                this_$iv.persistence.write(newState$iv);
                this_$iv.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean removePackage(@org.jetbrains.annotations.NotNull java.lang.String packageName, int userId) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$BooleanRef wasChanged = new com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$BooleanRef();
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        synchronized (this_$iv.stateLock) {
            try {
                com.android.server.permission.access.AccessState oldState$iv = this_$iv.state;
                if (oldState$iv == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    oldState$iv = null;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$removePackage_u24lambda_u2415 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.appop.PackageAppOpPolicy $this$removePackage_u24lambda_u2415_u24lambda_u2414 = this.packagePolicy;
                wasChanged.element = java.lang.Boolean.valueOf($this$removePackage_u24lambda_u2415_u24lambda_u2414.removeAppOpModes($this$removePackage_u24lambda_u2415, packageName, userId)).booleanValue();
                this_$iv.persistence.write(newState$iv);
                this_$iv.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return wasChanged.element;
    }

    private final android.util.SparseIntArray opNameMapToOpSparseArray(android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap) {
        if (arrayMap == null) {
            return new android.util.SparseIntArray();
        }
        android.util.SparseIntArray opSparseArray = new android.util.SparseIntArray(arrayMap.size());
        int size = arrayMap.size();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            java.lang.String keyAt = arrayMap.keyAt(index$iv);
            int opMode = arrayMap.valueAt(index$iv).intValue();
            java.lang.String opName = keyAt;
            opSparseArray.put(android.app.AppOpsManager.strOpToOp(opName), opMode);
        }
        return opSparseArray;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void clearAllModes() {
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    @org.jetbrains.annotations.NotNull
    public android.util.SparseBooleanArray getForegroundOps(int uid, @org.jetbrains.annotations.NotNull java.lang.String persistentDeviceId) {
        android.util.SparseBooleanArray $this$getForegroundOps_u24lambda_u2418 = new android.util.SparseBooleanArray();
        android.util.ArrayMap $this$forEachIndexed$iv = getUidModes(uid);
        if ($this$forEachIndexed$iv != null) {
            int size = $this$forEachIndexed$iv.size();
            for (int index$iv = 0; index$iv < size; index$iv++) {
                java.lang.String keyAt = $this$forEachIndexed$iv.keyAt(index$iv);
                int mode = $this$forEachIndexed$iv.valueAt(index$iv).intValue();
                java.lang.String op = keyAt;
                if (mode == 4) {
                    int key$iv = android.app.AppOpsManager.strOpToOp(op);
                    $this$getForegroundOps_u24lambda_u2418.put(key$iv, true);
                }
            }
        }
        return $this$getForegroundOps_u24lambda_u2418;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    @org.jetbrains.annotations.NotNull
    public android.util.SparseBooleanArray getForegroundOps(@org.jetbrains.annotations.NotNull java.lang.String packageName, int userId) {
        android.util.SparseBooleanArray $this$getForegroundOps_u24lambda_u2420 = new android.util.SparseBooleanArray();
        android.util.ArrayMap $this$forEachIndexed$iv = getPackageModes(packageName, userId);
        if ($this$forEachIndexed$iv != null) {
            int size = $this$forEachIndexed$iv.size();
            for (int index$iv = 0; index$iv < size; index$iv++) {
                java.lang.String keyAt = $this$forEachIndexed$iv.keyAt(index$iv);
                int mode = $this$forEachIndexed$iv.valueAt(index$iv).intValue();
                java.lang.String op = keyAt;
                if (mode == 4) {
                    int key$iv = android.app.AppOpsManager.strOpToOp(op);
                    $this$getForegroundOps_u24lambda_u2420.put(key$iv, true);
                }
            }
        }
        return $this$getForegroundOps_u24lambda_u2420;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean addAppOpsModeChangedListener(@org.jetbrains.annotations.NotNull com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener listener) {
        boolean add;
        synchronized (this.listenersLock) {
            android.util.ArraySet newListeners = new android.util.ArraySet((android.util.ArraySet) this.listeners);
            add = newListeners.add(listener);
            this.listeners = newListeners;
        }
        return add;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean removeAppOpsModeChangedListener(@org.jetbrains.annotations.NotNull com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener listener) {
        boolean remove;
        synchronized (this.listenersLock) {
            android.util.ArraySet newListeners = new android.util.ArraySet((android.util.ArraySet) this.listeners);
            remove = newListeners.remove(listener);
            this.listeners = newListeners;
        }
        return remove;
    }

    /* compiled from: AppOpService.kt */
    public final class OnAppIdAppOpModeChangedListener extends com.android.server.permission.access.appop.AppIdAppOpPolicy.OnAppOpModeChangedListener {

        @org.jetbrains.annotations.NotNull
        private final android.util.ArrayMap<com.android.server.permission.jarjar.kotlin.Pair<java.lang.Integer, java.lang.Integer>, java.lang.Integer> pendingChanges = new android.util.ArrayMap<>();

        public OnAppIdAppOpModeChangedListener() {
        }

        @Override // com.android.server.permission.access.appop.AppIdAppOpPolicy.OnAppOpModeChangedListener
        public void onAppOpModeChanged(int appId, int userId, @org.jetbrains.annotations.NotNull java.lang.String appOpName, int oldMode, int newMode) {
            int uid = android.os.UserHandle.getUid(userId, appId);
            int appOpCode = android.app.AppOpsManager.strOpToOp(appOpName);
            com.android.server.permission.jarjar.kotlin.Pair key = new com.android.server.permission.jarjar.kotlin.Pair(java.lang.Integer.valueOf(uid), java.lang.Integer.valueOf(appOpCode));
            android.util.ArrayMap $this$set$iv = this.pendingChanges;
            $this$set$iv.put(key, java.lang.Integer.valueOf(newMode));
        }

        @Override // com.android.server.permission.access.appop.AppIdAppOpPolicy.OnAppOpModeChangedListener
        public void onStateMutated() {
            android.util.ArraySet listenersLocal = com.android.server.permission.access.appop.AppOpService.this.listeners;
            android.util.ArrayMap $this$forEachIndexed$iv = this.pendingChanges;
            int size = $this$forEachIndexed$iv.size();
            for (int index$iv = 0; index$iv < size; index$iv++) {
                com.android.server.permission.jarjar.kotlin.Pair<java.lang.Integer, java.lang.Integer> keyAt = $this$forEachIndexed$iv.keyAt(index$iv);
                int mode = $this$forEachIndexed$iv.valueAt(index$iv).intValue();
                com.android.server.permission.jarjar.kotlin.Pair<java.lang.Integer, java.lang.Integer> key = keyAt;
                android.util.ArraySet $this$forEachIndexed$iv2 = listenersLocal;
                int index$iv2 = 0;
                int size2 = $this$forEachIndexed$iv2.size();
                while (index$iv2 < size2) {
                    com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener listener = (com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener) $this$forEachIndexed$iv2.valueAt(index$iv2);
                    int uid = key.getFirst().intValue();
                    android.util.ArraySet listenersLocal2 = listenersLocal;
                    int appOpCode = key.getSecond().intValue();
                    listener.onUidModeChanged(uid, appOpCode, mode, "default:0");
                    index$iv2++;
                    $this$forEachIndexed$iv = $this$forEachIndexed$iv;
                    listenersLocal = listenersLocal2;
                }
            }
            this.pendingChanges.clear();
        }
    }

    /* compiled from: AppOpService.kt */
    private final class OnPackageAppOpModeChangedListener extends com.android.server.permission.access.appop.PackageAppOpPolicy.OnAppOpModeChangedListener {

        @org.jetbrains.annotations.NotNull
        private final android.util.ArrayMap<com.android.server.permission.jarjar.kotlin.Triple<java.lang.String, java.lang.Integer, java.lang.Integer>, java.lang.Integer> pendingChanges = new android.util.ArrayMap<>();

        public OnPackageAppOpModeChangedListener() {
        }

        @Override // com.android.server.permission.access.appop.PackageAppOpPolicy.OnAppOpModeChangedListener
        public void onAppOpModeChanged(@org.jetbrains.annotations.NotNull java.lang.String packageName, int userId, @org.jetbrains.annotations.NotNull java.lang.String appOpName, int oldMode, int newMode) {
            int appOpCode = android.app.AppOpsManager.strOpToOp(appOpName);
            com.android.server.permission.jarjar.kotlin.Triple key = new com.android.server.permission.jarjar.kotlin.Triple(packageName, java.lang.Integer.valueOf(userId), java.lang.Integer.valueOf(appOpCode));
            android.util.ArrayMap $this$set$iv = this.pendingChanges;
            $this$set$iv.put(key, java.lang.Integer.valueOf(newMode));
        }

        @Override // com.android.server.permission.access.appop.PackageAppOpPolicy.OnAppOpModeChangedListener
        public void onStateMutated() {
            android.util.ArraySet listenersLocal = com.android.server.permission.access.appop.AppOpService.this.listeners;
            android.util.ArrayMap $this$forEachIndexed$iv = this.pendingChanges;
            int size = $this$forEachIndexed$iv.size();
            for (int index$iv = 0; index$iv < size; index$iv++) {
                com.android.server.permission.jarjar.kotlin.Triple<java.lang.String, java.lang.Integer, java.lang.Integer> keyAt = $this$forEachIndexed$iv.keyAt(index$iv);
                int mode = $this$forEachIndexed$iv.valueAt(index$iv).intValue();
                com.android.server.permission.jarjar.kotlin.Triple<java.lang.String, java.lang.Integer, java.lang.Integer> key = keyAt;
                android.util.ArraySet $this$forEachIndexed$iv2 = listenersLocal;
                int index$iv2 = 0;
                int size2 = $this$forEachIndexed$iv2.size();
                while (index$iv2 < size2) {
                    com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener listener = (com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener) $this$forEachIndexed$iv2.valueAt(index$iv2);
                    java.lang.String packageName = key.getFirst();
                    android.util.ArraySet listenersLocal2 = listenersLocal;
                    int userId = key.getSecond().intValue();
                    android.util.ArrayMap $this$forEachIndexed$iv3 = $this$forEachIndexed$iv;
                    int appOpCode = key.getThird().intValue();
                    listener.onPackageModeChanged(packageName, userId, appOpCode, mode);
                    index$iv2++;
                    listenersLocal = listenersLocal2;
                    $this$forEachIndexed$iv = $this$forEachIndexed$iv3;
                }
            }
            this.pendingChanges.clear();
        }
    }
}
