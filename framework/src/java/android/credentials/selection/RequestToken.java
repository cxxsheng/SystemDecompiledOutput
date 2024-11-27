package android.credentials.selection;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class RequestToken {
    private final android.os.IBinder mToken;

    public android.os.IBinder getToken() {
        return this.mToken;
    }

    public RequestToken(android.os.IBinder iBinder) {
        this.mToken = iBinder;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.credentials.selection.RequestToken)) {
            return false;
        }
        return this.mToken.equals(((android.credentials.selection.RequestToken) obj).mToken);
    }

    public int hashCode() {
        return this.mToken.hashCode();
    }
}
