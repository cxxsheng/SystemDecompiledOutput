package android.hardware.lights;

/* loaded from: classes2.dex */
public final class SystemLightsManager extends android.hardware.lights.LightsManager {
    private static final java.lang.String TAG = "LightsManager";
    private final android.hardware.lights.ILightsManager mService;

    public SystemLightsManager(android.content.Context context) throws android.os.ServiceManager.ServiceNotFoundException {
        this(context, android.hardware.lights.ILightsManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.LIGHTS_SERVICE)));
    }

    public SystemLightsManager(android.content.Context context, android.hardware.lights.ILightsManager iLightsManager) {
        this.mService = (android.hardware.lights.ILightsManager) com.android.internal.util.Preconditions.checkNotNull(iLightsManager);
    }

    @Override // android.hardware.lights.LightsManager
    public java.util.List<android.hardware.lights.Light> getLights() {
        try {
            return this.mService.getLights();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.hardware.lights.LightsManager
    public android.hardware.lights.LightState getLightState(android.hardware.lights.Light light) {
        com.android.internal.util.Preconditions.checkNotNull(light);
        try {
            return this.mService.getLightState(light.getId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.hardware.lights.LightsManager
    public android.hardware.lights.LightsManager.LightsSession openSession() {
        try {
            android.hardware.lights.SystemLightsManager.SystemLightsSession systemLightsSession = new android.hardware.lights.SystemLightsManager.SystemLightsSession();
            this.mService.openSession(systemLightsSession.getToken(), 0);
            return systemLightsSession;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.hardware.lights.LightsManager
    public android.hardware.lights.LightsManager.LightsSession openSession(int i) {
        try {
            android.hardware.lights.SystemLightsManager.SystemLightsSession systemLightsSession = new android.hardware.lights.SystemLightsManager.SystemLightsSession();
            this.mService.openSession(systemLightsSession.getToken(), i);
            return systemLightsSession;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final class SystemLightsSession extends android.hardware.lights.LightsManager.LightsSession implements java.lang.AutoCloseable {
        private final android.util.CloseGuard mCloseGuard;
        private boolean mClosed;

        private SystemLightsSession() {
            this.mCloseGuard = new android.util.CloseGuard();
            this.mClosed = false;
            this.mCloseGuard.open("SystemLightsSession.close");
        }

        @Override // android.hardware.lights.LightsManager.LightsSession
        public void requestLights(android.hardware.lights.LightsRequest lightsRequest) {
            com.android.internal.util.Preconditions.checkNotNull(lightsRequest);
            if (!this.mClosed) {
                try {
                    java.util.List<java.lang.Integer> lights = lightsRequest.getLights();
                    java.util.List<android.hardware.lights.LightState> lightStates = lightsRequest.getLightStates();
                    int[] iArr = new int[lights.size()];
                    for (int i = 0; i < lights.size(); i++) {
                        iArr[i] = lights.get(i).intValue();
                    }
                    android.hardware.lights.LightState[] lightStateArr = new android.hardware.lights.LightState[lightStates.size()];
                    for (int i2 = 0; i2 < lightStates.size(); i2++) {
                        lightStateArr[i2] = lightStates.get(i2);
                    }
                    android.hardware.lights.SystemLightsManager.this.mService.setLightStates(getToken(), iArr, lightStateArr);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.hardware.lights.LightsManager.LightsSession, java.lang.AutoCloseable
        public void close() {
            if (!this.mClosed) {
                try {
                    android.hardware.lights.SystemLightsManager.this.mService.closeSession(getToken());
                    this.mClosed = true;
                    this.mCloseGuard.close();
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
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
