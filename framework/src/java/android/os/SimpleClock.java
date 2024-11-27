package android.os;

/* loaded from: classes3.dex */
public abstract class SimpleClock extends java.time.Clock {
    private final java.time.ZoneId zone;

    @Override // java.time.Clock, java.time.InstantSource
    public abstract long millis();

    public SimpleClock(java.time.ZoneId zoneId) {
        this.zone = zoneId;
    }

    @Override // java.time.Clock
    public java.time.ZoneId getZone() {
        return this.zone;
    }

    @Override // java.time.Clock, java.time.InstantSource
    public java.time.Clock withZone(java.time.ZoneId zoneId) {
        return new android.os.SimpleClock(zoneId) { // from class: android.os.SimpleClock.1
            @Override // android.os.SimpleClock, java.time.Clock, java.time.InstantSource
            public long millis() {
                return android.os.SimpleClock.this.millis();
            }
        };
    }

    @Override // java.time.Clock, java.time.InstantSource
    public java.time.Instant instant() {
        return java.time.Instant.ofEpochMilli(millis());
    }
}
