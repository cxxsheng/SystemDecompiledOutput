package android.hardware.lights;

/* loaded from: classes2.dex */
public abstract class LightsManager {

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int LIGHT_TYPE_MICROPHONE = 8;
    private static final java.lang.String TAG = "LightsManager";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LightType {
    }

    public abstract android.hardware.lights.LightState getLightState(android.hardware.lights.Light light);

    public abstract java.util.List<android.hardware.lights.Light> getLights();

    public abstract android.hardware.lights.LightsManager.LightsSession openSession();

    public abstract android.hardware.lights.LightsManager.LightsSession openSession(int i);

    public static abstract class LightsSession implements java.lang.AutoCloseable {
        private final android.os.IBinder mToken = new android.os.Binder();

        @Override // java.lang.AutoCloseable
        public abstract void close();

        public abstract void requestLights(android.hardware.lights.LightsRequest lightsRequest);

        public android.os.IBinder getToken() {
            return this.mToken;
        }
    }
}
