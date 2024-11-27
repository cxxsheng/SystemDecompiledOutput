package android.window;

/* loaded from: classes4.dex */
public abstract class WindowProviderService extends android.app.Service implements android.window.WindowProvider {
    private static final java.lang.String TAG = android.window.WindowProviderService.class.getSimpleName();
    private boolean mInitialized;
    private android.view.WindowManager mWindowManager;
    private final android.window.WindowTokenClient mWindowToken = new android.window.WindowTokenClient();
    private final android.window.WindowContextController mController = new android.window.WindowContextController(this.mWindowToken);
    private final android.content.ComponentCallbacksController mCallbacksController = new android.content.ComponentCallbacksController();
    private final android.os.Bundle mOptions = new android.os.Bundle();

    public abstract int getWindowType();

    public static boolean isWindowProviderService(android.os.Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return bundle.getBoolean(android.window.WindowProvider.KEY_IS_WINDOW_PROVIDER_SERVICE, false);
    }

    public WindowProviderService() {
        this.mOptions.putBoolean(android.window.WindowProvider.KEY_IS_WINDOW_PROVIDER_SERVICE, true);
    }

    public android.os.Bundle getWindowContextOptions() {
        return this.mOptions;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        this.mCallbacksController.registerCallbacks(componentCallbacks);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        this.mCallbacksController.unregisterCallbacks(componentCallbacks);
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        this.mCallbacksController.dispatchConfigurationChanged(configuration);
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onLowMemory() {
        this.mCallbacksController.dispatchLowMemory();
    }

    @Override // android.app.Service, android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        this.mCallbacksController.dispatchTrimMemory(i);
    }

    public int getInitialDisplayId() {
        return 0;
    }

    public final void attachToWindowToken(android.os.IBinder iBinder) {
        this.mController.attachToWindowToken(iBinder);
    }

    @Override // android.app.Service
    public final android.content.Context createServiceBaseContext(android.app.ActivityThread activityThread, android.app.LoadedApk loadedApk) {
        android.content.Context createServiceBaseContext = super.createServiceBaseContext(activityThread, loadedApk);
        android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) createServiceBaseContext.getSystemService(android.hardware.display.DisplayManager.class);
        int initialDisplayId = getInitialDisplayId();
        android.view.Display display = displayManager.getDisplay(initialDisplayId);
        if (display == null) {
            android.util.Log.e(TAG, "Display with id " + initialDisplayId + " not found, falling back to DEFAULT_DISPLAY");
            display = displayManager.getDisplay(0);
        }
        return createServiceBaseContext.createTokenContext(this.mWindowToken, display);
    }

    @Override // android.app.Service, android.content.ContextWrapper
    protected void attachBaseContext(android.content.Context context) {
        super.attachBaseContext(context);
        if (!this.mInitialized) {
            this.mWindowToken.attachContext(this);
            this.mController.attachToDisplayArea(getWindowType(), getDisplayId(), getWindowContextOptions());
            this.mWindowManager = android.view.WindowManagerImpl.createWindowContextWindowManager(this);
            this.mInitialized = true;
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public java.lang.Object getSystemService(java.lang.String str) {
        if (android.content.Context.WINDOW_SERVICE.equals(str)) {
            return this.mWindowManager;
        }
        return super.getSystemService(str);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        this.mController.detachIfNeeded();
        this.mCallbacksController.clearCallbacks();
    }
}
