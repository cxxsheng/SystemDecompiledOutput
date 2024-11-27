package android.app;

/* loaded from: classes.dex */
public class FullscreenRequestHandler {
    public static final java.lang.String REMOTE_CALLBACK_RESULT_KEY = "result";
    public static final int RESULT_APPROVED = 0;
    public static final int RESULT_FAILED_NOT_IN_FULLSCREEN_WITH_HISTORY = 1;
    public static final int RESULT_FAILED_NOT_TOP_FOCUSED = 2;

    public @interface RequestResult {
    }

    static void requestFullscreenMode(int i, final android.os.OutcomeReceiver<java.lang.Void, java.lang.Throwable> outcomeReceiver, android.content.res.Configuration configuration, android.os.IBinder iBinder) {
        int earlyCheckRequestMatchesWindowingMode = earlyCheckRequestMatchesWindowingMode(i, configuration.windowConfiguration.getWindowingMode());
        if (earlyCheckRequestMatchesWindowingMode != 0) {
            if (outcomeReceiver != null) {
                notifyFullscreenRequestResult(outcomeReceiver, earlyCheckRequestMatchesWindowingMode);
                return;
            }
            return;
        }
        try {
            if (outcomeReceiver != null) {
                android.app.ActivityClient.getInstance().requestMultiwindowFullscreen(iBinder, i, new android.os.IRemoteCallback.Stub() { // from class: android.app.FullscreenRequestHandler.1
                    @Override // android.os.IRemoteCallback
                    public void sendResult(android.os.Bundle bundle) {
                        android.app.FullscreenRequestHandler.notifyFullscreenRequestResult(android.os.OutcomeReceiver.this, bundle.getInt("result"));
                    }
                });
            } else {
                android.app.ActivityClient.getInstance().requestMultiwindowFullscreen(iBinder, i, null);
            }
        } catch (java.lang.Throwable th) {
            if (outcomeReceiver != null) {
                outcomeReceiver.onError(th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyFullscreenRequestResult(android.os.OutcomeReceiver<java.lang.Void, java.lang.Throwable> outcomeReceiver, int i) {
        java.lang.IllegalStateException illegalStateException;
        switch (i) {
            case 1:
                illegalStateException = new java.lang.IllegalStateException("The window is not in fullscreen by calling the requestFullscreenMode API before, such that cannot be restored.");
                break;
            case 2:
                illegalStateException = new java.lang.IllegalStateException("The window is not the top focused window.");
                break;
            default:
                illegalStateException = null;
                outcomeReceiver.onResult(null);
                break;
        }
        if (illegalStateException != null) {
            outcomeReceiver.onError(illegalStateException);
        }
    }

    private static int earlyCheckRequestMatchesWindowingMode(int i, int i2) {
        if (i == 0 && i2 != 1) {
            return 1;
        }
        return 0;
    }
}
