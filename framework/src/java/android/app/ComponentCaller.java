package android.app;

/* loaded from: classes.dex */
public final class ComponentCaller {
    private final android.os.IBinder mActivityToken;
    private final android.os.IBinder mCallerToken;

    public ComponentCaller(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        this.mActivityToken = iBinder;
        this.mCallerToken = iBinder2;
    }

    public int getUid() {
        return android.app.ActivityClient.getInstance().getActivityCallerUid(this.mActivityToken, this.mCallerToken);
    }

    public java.lang.String getPackage() {
        return android.app.ActivityClient.getInstance().getActivityCallerPackage(this.mActivityToken, this.mCallerToken);
    }

    public int checkContentUriPermission(android.net.Uri uri, int i) {
        return android.app.ActivityClient.getInstance().checkActivityCallerContentUriPermission(this.mActivityToken, this.mCallerToken, uri, i);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.app.ComponentCaller)) {
            return false;
        }
        android.app.ComponentCaller componentCaller = (android.app.ComponentCaller) obj;
        return this.mActivityToken == componentCaller.mActivityToken && this.mCallerToken == componentCaller.mCallerToken;
    }

    public int hashCode() {
        return ((527 + java.util.Objects.hashCode(this.mActivityToken)) * 31) + java.util.Objects.hashCode(this.mCallerToken);
    }
}
