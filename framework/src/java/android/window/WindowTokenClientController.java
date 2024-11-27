package android.window;

/* loaded from: classes4.dex */
public class WindowTokenClientController {
    private static final java.lang.String TAG = android.window.WindowTokenClientController.class.getSimpleName();
    private static android.window.WindowTokenClientController sController;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.app.IApplicationThread mAppThread = android.app.ActivityThread.currentActivityThread().getApplicationThread();
    private final android.util.ArrayMap<android.os.IBinder, android.window.WindowTokenClient> mWindowTokenClientMap = new android.util.ArrayMap<>();

    public static android.window.WindowTokenClientController getInstance() {
        android.window.WindowTokenClientController windowTokenClientController;
        synchronized (android.window.WindowTokenClientController.class) {
            if (sController == null) {
                sController = new android.window.WindowTokenClientController();
            }
            windowTokenClientController = sController;
        }
        return windowTokenClientController;
    }

    public static void overrideForTesting(android.window.WindowTokenClientController windowTokenClientController) {
        synchronized (android.window.WindowTokenClientController.class) {
            sController = windowTokenClientController;
        }
    }

    public static android.window.WindowTokenClientController createInstanceForTesting() {
        return new android.window.WindowTokenClientController();
    }

    private WindowTokenClientController() {
    }

    public android.content.Context getWindowContext(android.os.IBinder iBinder) {
        android.window.WindowTokenClient windowTokenClient;
        synchronized (this.mLock) {
            windowTokenClient = this.mWindowTokenClientMap.get(iBinder);
        }
        if (windowTokenClient != null) {
            return windowTokenClient.getContext();
        }
        return null;
    }

    public boolean attachToDisplayArea(android.window.WindowTokenClient windowTokenClient, int i, int i2, android.os.Bundle bundle) {
        try {
            android.window.WindowContextInfo attachWindowContextToDisplayArea = getWindowManagerService().attachWindowContextToDisplayArea(this.mAppThread, windowTokenClient, i, i2, bundle);
            if (attachWindowContextToDisplayArea == null) {
                return false;
            }
            onWindowContextTokenAttached(windowTokenClient, attachWindowContextToDisplayArea, false);
            return true;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean attachToDisplayContent(android.window.WindowTokenClient windowTokenClient, int i) {
        android.view.IWindowManager windowManagerService = getWindowManagerService();
        if (windowManagerService == null) {
            return false;
        }
        try {
            android.window.WindowContextInfo attachWindowContextToDisplayContent = windowManagerService.attachWindowContextToDisplayContent(this.mAppThread, windowTokenClient, i);
            if (attachWindowContextToDisplayContent == null) {
                return false;
            }
            onWindowContextTokenAttached(windowTokenClient, attachWindowContextToDisplayContent, false);
            return true;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean attachToWindowToken(android.window.WindowTokenClient windowTokenClient, android.os.IBinder iBinder) {
        try {
            android.window.WindowContextInfo attachWindowContextToWindowToken = getWindowManagerService().attachWindowContextToWindowToken(this.mAppThread, windowTokenClient, iBinder);
            if (attachWindowContextToWindowToken == null) {
                return false;
            }
            onWindowContextTokenAttached(windowTokenClient, attachWindowContextToWindowToken, true);
            return true;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void detachIfNeeded(android.window.WindowTokenClient windowTokenClient) {
        synchronized (this.mLock) {
            if (this.mWindowTokenClientMap.remove(windowTokenClient) == null) {
                return;
            }
            try {
                getWindowManagerService().detachWindowContext(windowTokenClient);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private void onWindowContextTokenAttached(android.window.WindowTokenClient windowTokenClient, android.window.WindowContextInfo windowContextInfo, boolean z) {
        synchronized (this.mLock) {
            this.mWindowTokenClientMap.put(windowTokenClient, windowTokenClient);
        }
        if (z) {
            windowTokenClient.postOnConfigurationChanged(windowContextInfo.getConfiguration(), windowContextInfo.getDisplayId());
        } else {
            windowTokenClient.onConfigurationChanged(windowContextInfo.getConfiguration(), windowContextInfo.getDisplayId(), false);
        }
    }

    public void onWindowContextInfoChanged(android.os.IBinder iBinder, android.window.WindowContextInfo windowContextInfo) {
        android.window.WindowTokenClient windowTokenClient = getWindowTokenClient(iBinder);
        if (windowTokenClient != null) {
            windowTokenClient.onConfigurationChanged(windowContextInfo.getConfiguration(), windowContextInfo.getDisplayId());
        }
    }

    public void onWindowContextWindowRemoved(android.os.IBinder iBinder) {
        android.window.WindowTokenClient windowTokenClient = getWindowTokenClient(iBinder);
        if (windowTokenClient != null) {
            windowTokenClient.onWindowTokenRemoved();
        }
    }

    private android.window.WindowTokenClient getWindowTokenClient(android.os.IBinder iBinder) {
        android.window.WindowTokenClient windowTokenClient;
        synchronized (this.mLock) {
            windowTokenClient = this.mWindowTokenClientMap.get(iBinder);
        }
        if (windowTokenClient == null) {
            android.util.Log.w(TAG, "Can't find attached WindowTokenClient for " + iBinder);
        }
        return windowTokenClient;
    }

    public android.view.IWindowManager getWindowManagerService() {
        return android.view.WindowManagerGlobal.getWindowManagerService();
    }
}
