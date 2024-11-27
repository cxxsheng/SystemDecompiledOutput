package android.hardware.devicestate;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class DeviceStateManager {
    public static final java.lang.String ACTION_SHOW_REAR_DISPLAY_OVERLAY = "com.android.intent.action.SHOW_REAR_DISPLAY_OVERLAY";
    public static final java.lang.String EXTRA_ORIGINAL_DEVICE_BASE_STATE = "original_device_base_state";
    public static final int INVALID_DEVICE_STATE = -1;
    public static final int MAXIMUM_DEVICE_STATE_IDENTIFIER = 10000;
    public static final int MINIMUM_DEVICE_STATE_IDENTIFIER = 0;
    private final android.hardware.devicestate.DeviceStateManagerGlobal mGlobal;

    public DeviceStateManager() {
        android.hardware.devicestate.DeviceStateManagerGlobal deviceStateManagerGlobal = android.hardware.devicestate.DeviceStateManagerGlobal.getInstance();
        if (deviceStateManagerGlobal == null) {
            throw new java.lang.IllegalStateException("Failed to get instance of global device state manager.");
        }
        this.mGlobal = deviceStateManagerGlobal;
    }

    @java.lang.Deprecated
    public int[] getSupportedStates() {
        return this.mGlobal.getSupportedStates();
    }

    public java.util.List<android.hardware.devicestate.DeviceState> getSupportedDeviceStates() {
        return this.mGlobal.getSupportedDeviceStates();
    }

    public void requestState(android.hardware.devicestate.DeviceStateRequest deviceStateRequest, java.util.concurrent.Executor executor, android.hardware.devicestate.DeviceStateRequest.Callback callback) {
        this.mGlobal.requestState(deviceStateRequest, executor, callback);
    }

    public void cancelStateRequest() {
        this.mGlobal.cancelStateRequest();
    }

    public void requestBaseStateOverride(android.hardware.devicestate.DeviceStateRequest deviceStateRequest, java.util.concurrent.Executor executor, android.hardware.devicestate.DeviceStateRequest.Callback callback) {
        this.mGlobal.requestBaseStateOverride(deviceStateRequest, executor, callback);
    }

    public void cancelBaseStateOverride() {
        this.mGlobal.cancelBaseStateOverride();
    }

    public void registerCallback(java.util.concurrent.Executor executor, android.hardware.devicestate.DeviceStateManager.DeviceStateCallback deviceStateCallback) {
        this.mGlobal.registerDeviceStateCallback(deviceStateCallback, executor);
    }

    public void unregisterCallback(android.hardware.devicestate.DeviceStateManager.DeviceStateCallback deviceStateCallback) {
        this.mGlobal.unregisterDeviceStateCallback(deviceStateCallback);
    }

    public interface DeviceStateCallback {
        @java.lang.Deprecated
        void onStateChanged(int i);

        @java.lang.Deprecated
        default void onSupportedStatesChanged(int[] iArr) {
        }

        default void onSupportedStatesChanged(java.util.List<android.hardware.devicestate.DeviceState> list) {
        }

        @java.lang.Deprecated
        default void onBaseStateChanged(int i) {
        }

        default void onDeviceStateChanged(android.hardware.devicestate.DeviceState deviceState) {
        }
    }

    public static class FoldStateListener implements android.hardware.devicestate.DeviceStateManager.DeviceStateCallback {
        private java.lang.Boolean lastResult;
        private final java.util.function.Consumer<java.lang.Boolean> mDelegate;
        private final android.hardware.devicestate.feature.flags.FeatureFlags mFeatureFlags;
        private final int[] mFoldedDeviceStates;

        public FoldStateListener(android.content.Context context) {
            this(context, new java.util.function.Consumer() { // from class: android.hardware.devicestate.DeviceStateManager$FoldStateListener$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.hardware.devicestate.DeviceStateManager.FoldStateListener.lambda$new$0((java.lang.Boolean) obj);
                }
            });
        }

        static /* synthetic */ void lambda$new$0(java.lang.Boolean bool) {
        }

        public FoldStateListener(android.content.Context context, java.util.function.Consumer<java.lang.Boolean> consumer) {
            this.mFoldedDeviceStates = context.getResources().getIntArray(com.android.internal.R.array.config_foldedDeviceStates);
            this.mDelegate = consumer;
            this.mFeatureFlags = new android.hardware.devicestate.feature.flags.FeatureFlagsImpl();
        }

        @Override // android.hardware.devicestate.DeviceStateManager.DeviceStateCallback
        public final void onStateChanged(int i) {
        }

        @Override // android.hardware.devicestate.DeviceStateManager.DeviceStateCallback
        public final void onDeviceStateChanged(android.hardware.devicestate.DeviceState deviceState) {
            boolean contains;
            if (this.mFeatureFlags.deviceStatePropertyApi()) {
                contains = deviceState.hasProperty(11) || com.android.internal.util.ArrayUtils.contains(this.mFoldedDeviceStates, deviceState.getIdentifier());
            } else {
                contains = com.android.internal.util.ArrayUtils.contains(this.mFoldedDeviceStates, deviceState.getIdentifier());
            }
            if (this.lastResult == null || !this.lastResult.equals(java.lang.Boolean.valueOf(contains))) {
                this.lastResult = java.lang.Boolean.valueOf(contains);
                this.mDelegate.accept(java.lang.Boolean.valueOf(contains));
            }
        }

        public java.lang.Boolean getFolded() {
            return this.lastResult;
        }
    }
}
