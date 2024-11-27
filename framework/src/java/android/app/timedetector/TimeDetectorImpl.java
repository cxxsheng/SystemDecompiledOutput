package android.app.timedetector;

/* loaded from: classes.dex */
public final class TimeDetectorImpl implements android.app.timedetector.TimeDetector {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "timedetector.TimeDetector";
    private final android.app.timedetector.ITimeDetectorService mITimeDetectorService = android.app.timedetector.ITimeDetectorService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("time_detector"));

    @Override // android.app.timedetector.TimeDetector
    public void suggestTelephonyTime(android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion) {
        try {
            this.mITimeDetectorService.suggestTelephonyTime(telephonyTimeSuggestion);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.timedetector.TimeDetector
    public boolean suggestManualTime(android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion) {
        try {
            return this.mITimeDetectorService.suggestManualTime(manualTimeSuggestion);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
