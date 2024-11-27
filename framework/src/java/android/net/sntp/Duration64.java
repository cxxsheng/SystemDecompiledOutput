package android.net.sntp;

/* loaded from: classes2.dex */
public final class Duration64 {
    public static final android.net.sntp.Duration64 ZERO = new android.net.sntp.Duration64(0);
    private final long mBits;

    private Duration64(long j) {
        this.mBits = j;
    }

    public static android.net.sntp.Duration64 between(android.net.sntp.Timestamp64 timestamp64, android.net.sntp.Timestamp64 timestamp642) {
        return new android.net.sntp.Duration64(((timestamp642.getFractionBits() & 4294967295L) | (timestamp642.getEraSeconds() << 32)) - ((timestamp64.getEraSeconds() << 32) | (timestamp64.getFractionBits() & 4294967295L)));
    }

    public java.time.Duration plus(android.net.sntp.Duration64 duration64) {
        return toDuration().plus(duration64.toDuration());
    }

    public static android.net.sntp.Duration64 fromDuration(java.time.Duration duration) {
        long seconds = duration.getSeconds();
        if (seconds < -2147483648L || seconds > 2147483647L) {
            throw new java.lang.IllegalArgumentException();
        }
        return new android.net.sntp.Duration64((seconds << 32) | (android.net.sntp.Timestamp64.nanosToFractionBits(duration.getNano()) & 4294967295L));
    }

    public java.time.Duration toDuration() {
        return java.time.Duration.ofSeconds(getSeconds(), getNanos());
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mBits == ((android.net.sntp.Duration64) obj).mBits) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mBits));
    }

    public java.lang.String toString() {
        java.time.Duration duration = toDuration();
        return java.lang.Long.toHexString(this.mBits) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + duration.getSeconds() + "s " + duration.getNano() + "ns)";
    }

    public int getSeconds() {
        return (int) (this.mBits >> 32);
    }

    public int getNanos() {
        return android.net.sntp.Timestamp64.fractionBitsToNanos((int) (this.mBits & 4294967295L));
    }
}
