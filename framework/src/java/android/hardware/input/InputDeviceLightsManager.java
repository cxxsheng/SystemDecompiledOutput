package android.hardware.input;

/* loaded from: classes2.dex */
class InputDeviceLightsManager extends android.hardware.lights.LightsManager {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "InputDeviceLightsManager";
    private final int mDeviceId;
    private final android.hardware.input.InputManagerGlobal mGlobal = android.hardware.input.InputManagerGlobal.getInstance();
    private final java.lang.String mPackageName = android.app.ActivityThread.currentPackageName();

    InputDeviceLightsManager(int i) {
        this.mDeviceId = i;
    }

    @Override // android.hardware.lights.LightsManager
    public java.util.List<android.hardware.lights.Light> getLights() {
        return this.mGlobal.getLights(this.mDeviceId);
    }

    @Override // android.hardware.lights.LightsManager
    public android.hardware.lights.LightState getLightState(android.hardware.lights.Light light) {
        com.android.internal.util.Preconditions.checkNotNull(light);
        return this.mGlobal.getLightState(this.mDeviceId, light);
    }

    @Override // android.hardware.lights.LightsManager
    public android.hardware.lights.LightsManager.LightsSession openSession() {
        android.hardware.input.InputDeviceLightsManager.InputDeviceLightsSession inputDeviceLightsSession = new android.hardware.input.InputDeviceLightsManager.InputDeviceLightsSession();
        this.mGlobal.openLightSession(this.mDeviceId, this.mPackageName, inputDeviceLightsSession.getToken());
        return inputDeviceLightsSession;
    }

    @Override // android.hardware.lights.LightsManager
    public android.hardware.lights.LightsManager.LightsSession openSession(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    public final class InputDeviceLightsSession extends android.hardware.lights.LightsManager.LightsSession implements java.lang.AutoCloseable {
        private final android.util.CloseGuard mCloseGuard;
        private boolean mClosed;

        private InputDeviceLightsSession() {
            this.mCloseGuard = new android.util.CloseGuard();
            this.mClosed = false;
            this.mCloseGuard.open("InputDeviceLightsSession.close");
        }

        @Override // android.hardware.lights.LightsManager.LightsSession
        public void requestLights(android.hardware.lights.LightsRequest lightsRequest) {
            com.android.internal.util.Preconditions.checkNotNull(lightsRequest);
            com.android.internal.util.Preconditions.checkArgument(!this.mClosed);
            android.hardware.input.InputDeviceLightsManager.this.mGlobal.requestLights(android.hardware.input.InputDeviceLightsManager.this.mDeviceId, lightsRequest, getToken());
        }

        @Override // android.hardware.lights.LightsManager.LightsSession, java.lang.AutoCloseable
        public void close() {
            if (!this.mClosed) {
                android.hardware.input.InputDeviceLightsManager.this.mGlobal.closeLightSession(android.hardware.input.InputDeviceLightsManager.this.mDeviceId, getToken());
                this.mClosed = true;
                this.mCloseGuard.close();
            }
            java.lang.ref.Reference.reachabilityFence(this);
        }

        protected void finalize() throws java.lang.Throwable {
            try {
                this.mCloseGuard.warnIfOpen();
                close();
            } finally {
                super.finalize();
            }
        }
    }
}
