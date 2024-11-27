package android.security.identity;

/* loaded from: classes3.dex */
public final class AuthenticationKeyMetadata {
    private java.time.Instant mExpirationDate;
    private int mUsageCount;

    AuthenticationKeyMetadata(int i, java.time.Instant instant) {
        this.mUsageCount = i;
        this.mExpirationDate = instant;
    }

    public int getUsageCount() {
        return this.mUsageCount;
    }

    public java.time.Instant getExpirationDate() {
        return this.mExpirationDate;
    }
}
