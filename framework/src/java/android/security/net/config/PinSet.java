package android.security.net.config;

/* loaded from: classes3.dex */
public final class PinSet {
    public static final android.security.net.config.PinSet EMPTY_PINSET = new android.security.net.config.PinSet(java.util.Collections.emptySet(), Long.MAX_VALUE);
    public final long expirationTime;
    public final java.util.Set<android.security.net.config.Pin> pins;

    public PinSet(java.util.Set<android.security.net.config.Pin> set, long j) {
        if (set == null) {
            throw new java.lang.NullPointerException("pins must not be null");
        }
        this.pins = set;
        this.expirationTime = j;
    }

    java.util.Set<java.lang.String> getPinAlgorithms() {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator<android.security.net.config.Pin> it = this.pins.iterator();
        while (it.hasNext()) {
            arraySet.add(it.next().digestAlgorithm);
        }
        return arraySet;
    }
}
