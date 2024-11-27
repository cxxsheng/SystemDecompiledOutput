package android.net;

/* loaded from: classes2.dex */
public abstract class NetworkSpecifier {
    @android.annotation.SystemApi
    public boolean canBeSatisfiedBy(android.net.NetworkSpecifier networkSpecifier) {
        return false;
    }

    @android.annotation.SystemApi
    public android.net.NetworkSpecifier redact() {
        return this;
    }
}
