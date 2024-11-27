package android.hardware.camera2;

/* loaded from: classes.dex */
public abstract class CameraInjectionSession implements java.lang.AutoCloseable {

    public static abstract class InjectionStatusCallback {
        public static final int ERROR_INJECTION_SERVICE = 1;
        public static final int ERROR_INJECTION_SESSION = 0;
        public static final int ERROR_INJECTION_UNSUPPORTED = 2;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ErrorCode {
        }

        public abstract void onInjectionError(int i);

        public abstract void onInjectionSucceeded(android.hardware.camera2.CameraInjectionSession cameraInjectionSession);
    }

    @Override // java.lang.AutoCloseable
    public abstract void close();
}
