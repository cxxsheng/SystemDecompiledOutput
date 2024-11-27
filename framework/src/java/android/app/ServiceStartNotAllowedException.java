package android.app;

/* loaded from: classes.dex */
public abstract class ServiceStartNotAllowedException extends java.lang.IllegalStateException {
    ServiceStartNotAllowedException(java.lang.String str) {
        super(str);
    }

    public static android.app.ServiceStartNotAllowedException newInstance(boolean z, java.lang.String str) {
        if (z) {
            return new android.app.ForegroundServiceStartNotAllowedException(str);
        }
        return new android.app.BackgroundServiceStartNotAllowedException(str);
    }

    @Override // java.lang.Throwable
    public synchronized java.lang.Throwable getCause() {
        return null;
    }
}
