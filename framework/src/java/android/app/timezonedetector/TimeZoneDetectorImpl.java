package android.app.timezonedetector;

/* loaded from: classes.dex */
public final class TimeZoneDetectorImpl implements android.app.timezonedetector.TimeZoneDetector {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "timezonedetector.TimeZoneDetector";
    private final android.app.timezonedetector.ITimeZoneDetectorService mITimeZoneDetectorService = android.app.timezonedetector.ITimeZoneDetectorService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("time_zone_detector"));

    @Override // android.app.timezonedetector.TimeZoneDetector
    public boolean suggestManualTimeZone(android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion) {
        try {
            return this.mITimeZoneDetectorService.suggestManualTimeZone(manualTimeZoneSuggestion);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.timezonedetector.TimeZoneDetector
    public void suggestTelephonyTimeZone(android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion) {
        try {
            this.mITimeZoneDetectorService.suggestTelephonyTimeZone(telephonyTimeZoneSuggestion);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
