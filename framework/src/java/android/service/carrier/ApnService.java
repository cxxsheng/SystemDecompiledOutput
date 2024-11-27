package android.service.carrier;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class ApnService extends android.app.Service {
    private static final java.lang.String LOG_TAG = "ApnService";
    private final android.service.carrier.IApnSourceService.Stub mBinder = new android.service.carrier.IApnSourceService.Stub() { // from class: android.service.carrier.ApnService.1
        @Override // android.service.carrier.IApnSourceService
        public android.content.ContentValues[] getApns(int i) {
            try {
                java.util.List<android.content.ContentValues> onRestoreApns = android.service.carrier.ApnService.this.onRestoreApns(i);
                return (android.content.ContentValues[]) onRestoreApns.toArray(new android.content.ContentValues[onRestoreApns.size()]);
            } catch (java.lang.Exception e) {
                android.util.Log.e(android.service.carrier.ApnService.LOG_TAG, "Error in getApns for subId=" + i + ": " + e.getMessage(), e);
                return null;
            }
        }
    };

    public abstract java.util.List<android.content.ContentValues> onRestoreApns(int i);

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mBinder;
    }
}
