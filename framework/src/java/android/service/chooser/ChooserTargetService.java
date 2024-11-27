package android.service.chooser;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class ChooserTargetService extends android.app.Service {
    public static final java.lang.String BIND_PERMISSION = "android.permission.BIND_CHOOSER_TARGET_SERVICE";
    private static final boolean DEBUG = false;
    public static final java.lang.String META_DATA_NAME = "android.service.chooser.chooser_target_service";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.chooser.ChooserTargetService";
    private final java.lang.String TAG = android.service.chooser.ChooserTargetService.class.getSimpleName() + '[' + getClass().getSimpleName() + ']';
    private android.service.chooser.ChooserTargetService.IChooserTargetServiceWrapper mWrapper = null;

    public abstract java.util.List<android.service.chooser.ChooserTarget> onGetChooserTargets(android.content.ComponentName componentName, android.content.IntentFilter intentFilter);

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        if (this.mWrapper == null) {
            this.mWrapper = new android.service.chooser.ChooserTargetService.IChooserTargetServiceWrapper();
        }
        return this.mWrapper;
    }

    private class IChooserTargetServiceWrapper extends android.service.chooser.IChooserTargetService.Stub {
        private IChooserTargetServiceWrapper() {
        }

        @Override // android.service.chooser.IChooserTargetService
        public void getChooserTargets(android.content.ComponentName componentName, android.content.IntentFilter intentFilter, android.service.chooser.IChooserTargetResult iChooserTargetResult) throws android.os.RemoteException {
            long clearCallingIdentity = clearCallingIdentity();
            try {
                java.util.List<android.service.chooser.ChooserTarget> onGetChooserTargets = android.service.chooser.ChooserTargetService.this.onGetChooserTargets(componentName, intentFilter);
                restoreCallingIdentity(clearCallingIdentity);
                iChooserTargetResult.sendResult(onGetChooserTargets);
            } catch (java.lang.Throwable th) {
                restoreCallingIdentity(clearCallingIdentity);
                iChooserTargetResult.sendResult(null);
                throw th;
            }
        }
    }
}
