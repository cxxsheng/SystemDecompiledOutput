package android.window;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public abstract class TaskFpsCallback {
    public abstract void onFpsReported(float f);

    private static void dispatchOnFpsReported(android.window.ITaskFpsCallback iTaskFpsCallback, float f) {
        try {
            iTaskFpsCallback.onFpsReported(f);
        } catch (android.os.RemoteException e) {
        }
    }
}
