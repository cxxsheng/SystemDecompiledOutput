package android.window;

/* loaded from: classes4.dex */
public class TransitionMetrics {
    private static final android.util.Singleton<android.window.TransitionMetrics> sTransitionMetrics = new android.util.Singleton<android.window.TransitionMetrics>() { // from class: android.window.TransitionMetrics.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public android.window.TransitionMetrics create() {
            return new android.window.TransitionMetrics(android.window.WindowOrganizer.getTransitionMetricsReporter());
        }
    };
    private final android.window.ITransitionMetricsReporter mTransitionMetricsReporter;

    private TransitionMetrics(android.window.ITransitionMetricsReporter iTransitionMetricsReporter) {
        this.mTransitionMetricsReporter = iTransitionMetricsReporter;
    }

    public void reportAnimationStart(android.os.IBinder iBinder) {
        try {
            this.mTransitionMetricsReporter.reportAnimationStart(iBinder, android.os.SystemClock.elapsedRealtime());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static android.window.TransitionMetrics getInstance() {
        return sTransitionMetrics.get();
    }
}
