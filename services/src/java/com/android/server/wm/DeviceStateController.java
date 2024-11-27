package com.android.server.wm;

/* loaded from: classes3.dex */
final class DeviceStateController {
    private final int mConcurrentDisplayDeviceState;
    private int mCurrentState;

    @android.annotation.NonNull
    private final int[] mFoldedDeviceStates;

    @android.annotation.NonNull
    private final int[] mHalfFoldedDeviceStates;
    private final boolean mMatchBuiltInDisplayOrientationToDefaultDisplay;

    @android.annotation.NonNull
    private final int[] mOpenDeviceStates;

    @android.annotation.NonNull
    private final int[] mRearDisplayDeviceStates;

    @android.annotation.NonNull
    private final int[] mReverseRotationAroundZAxisStates;

    @android.annotation.NonNull
    private final com.android.server.wm.WindowManagerGlobalLock mWmLock;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mWmLock"})
    @com.android.internal.annotations.VisibleForTesting
    final java.util.Map<java.util.function.Consumer<com.android.server.wm.DeviceStateController.DeviceState>, java.util.concurrent.Executor> mDeviceStateCallbacks = new android.util.ArrayMap();

    @android.annotation.NonNull
    private com.android.server.wm.DeviceStateController.DeviceState mCurrentDeviceState = com.android.server.wm.DeviceStateController.DeviceState.UNKNOWN;

    public enum DeviceState {
        UNKNOWN,
        OPEN,
        FOLDED,
        HALF_FOLDED,
        REAR,
        CONCURRENT
    }

    DeviceStateController(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock) {
        this.mWmLock = windowManagerGlobalLock;
        this.mOpenDeviceStates = context.getResources().getIntArray(android.R.array.config_ntpServers);
        this.mHalfFoldedDeviceStates = context.getResources().getIntArray(android.R.array.config_force_cellular_transport_capabilities);
        this.mFoldedDeviceStates = context.getResources().getIntArray(android.R.array.config_face_acquire_vendor_keyguard_ignorelist);
        this.mRearDisplayDeviceStates = context.getResources().getIntArray(android.R.array.config_primaryCredentialProviderService);
        this.mConcurrentDisplayDeviceState = context.getResources().getInteger(android.R.integer.config_default_cellular_usage_setting);
        this.mReverseRotationAroundZAxisStates = context.getResources().getIntArray(android.R.array.config_deviceStatesOnWhichToSleep);
        this.mMatchBuiltInDisplayOrientationToDefaultDisplay = context.getResources().getBoolean(android.R.bool.config_magnification_always_on_enabled);
    }

    void registerDeviceStateCallback(@android.annotation.NonNull java.util.function.Consumer<com.android.server.wm.DeviceStateController.DeviceState> consumer, @android.annotation.NonNull java.util.concurrent.Executor executor) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mDeviceStateCallbacks.put(consumer, executor);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void unregisterDeviceStateCallback(@android.annotation.NonNull java.util.function.Consumer<com.android.server.wm.DeviceStateController.DeviceState> consumer) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mDeviceStateCallbacks.remove(consumer);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    boolean shouldReverseRotationDirectionAroundZAxis(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        if (!displayContent.isDefaultDisplay) {
            return false;
        }
        return com.android.internal.util.ArrayUtils.contains(this.mReverseRotationAroundZAxisStates, this.mCurrentState);
    }

    boolean shouldMatchBuiltInDisplayOrientationToReverseDefaultDisplay() {
        return this.mMatchBuiltInDisplayOrientationToDefaultDisplay;
    }

    public void onDeviceStateReceivedByDisplayManager(int i) {
        final com.android.server.wm.DeviceStateController.DeviceState deviceState;
        this.mCurrentState = i;
        if (com.android.internal.util.ArrayUtils.contains(this.mHalfFoldedDeviceStates, i)) {
            deviceState = com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED;
        } else if (com.android.internal.util.ArrayUtils.contains(this.mFoldedDeviceStates, i)) {
            deviceState = com.android.server.wm.DeviceStateController.DeviceState.FOLDED;
        } else if (com.android.internal.util.ArrayUtils.contains(this.mRearDisplayDeviceStates, i)) {
            deviceState = com.android.server.wm.DeviceStateController.DeviceState.REAR;
        } else if (com.android.internal.util.ArrayUtils.contains(this.mOpenDeviceStates, i)) {
            deviceState = com.android.server.wm.DeviceStateController.DeviceState.OPEN;
        } else if (i == this.mConcurrentDisplayDeviceState) {
            deviceState = com.android.server.wm.DeviceStateController.DeviceState.CONCURRENT;
        } else {
            deviceState = com.android.server.wm.DeviceStateController.DeviceState.UNKNOWN;
        }
        if (this.mCurrentDeviceState == null || !this.mCurrentDeviceState.equals(deviceState)) {
            this.mCurrentDeviceState = deviceState;
            java.util.List<android.util.Pair<java.util.function.Consumer<com.android.server.wm.DeviceStateController.DeviceState>, java.util.concurrent.Executor>> copyDeviceStateCallbacks = copyDeviceStateCallbacks();
            for (int i2 = 0; i2 < copyDeviceStateCallbacks.size(); i2++) {
                final android.util.Pair<java.util.function.Consumer<com.android.server.wm.DeviceStateController.DeviceState>, java.util.concurrent.Executor> pair = copyDeviceStateCallbacks.get(i2);
                ((java.util.concurrent.Executor) pair.second).execute(new java.lang.Runnable() { // from class: com.android.server.wm.DeviceStateController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.DeviceStateController.lambda$onDeviceStateReceivedByDisplayManager$0(pair, deviceState);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onDeviceStateReceivedByDisplayManager$0(android.util.Pair pair, com.android.server.wm.DeviceStateController.DeviceState deviceState) {
        ((java.util.function.Consumer) pair.first).accept(deviceState);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    java.util.List<android.util.Pair<java.util.function.Consumer<com.android.server.wm.DeviceStateController.DeviceState>, java.util.concurrent.Executor>> copyDeviceStateCallbacks() {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mDeviceStateCallbacks.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.wm.DeviceStateController$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        com.android.server.wm.DeviceStateController.lambda$copyDeviceStateCallbacks$1(arrayList, (java.util.function.Consumer) obj, (java.util.concurrent.Executor) obj2);
                    }
                });
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$copyDeviceStateCallbacks$1(java.util.List list, java.util.function.Consumer consumer, java.util.concurrent.Executor executor) {
        list.add(new android.util.Pair(consumer, executor));
    }
}
