package android.os;

/* loaded from: classes3.dex */
public class BestClock extends android.os.SimpleClock {
    private static final java.lang.String TAG = "BestClock";
    private final java.time.Clock[] clocks;

    public BestClock(java.time.ZoneId zoneId, java.time.Clock... clockArr) {
        super(zoneId);
        this.clocks = clockArr;
    }

    @Override // android.os.SimpleClock, java.time.Clock, java.time.InstantSource
    public long millis() {
        java.time.Clock[] clockArr = this.clocks;
        int length = clockArr.length;
        for (int i = 0; i < length; i++) {
            try {
                return clockArr[i].millis();
            } catch (java.time.DateTimeException e) {
                android.util.Log.w(TAG, e.toString());
            }
        }
        throw new java.time.DateTimeException("No clocks in " + java.util.Arrays.toString(this.clocks) + " were able to provide time");
    }
}
