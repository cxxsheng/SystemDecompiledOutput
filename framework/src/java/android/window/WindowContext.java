package android.window;

/* loaded from: classes4.dex */
public class WindowContext extends android.content.ContextWrapper implements android.window.WindowProvider {
    private final android.content.ComponentCallbacksController mCallbacksController;
    private final android.window.WindowContextController mController;
    private final android.os.Bundle mOptions;
    private final int mType;
    private final android.view.WindowManager mWindowManager;

    public WindowContext(android.content.Context context, int i, android.os.Bundle bundle) {
        super(context);
        this.mCallbacksController = new android.content.ComponentCallbacksController();
        this.mType = i;
        this.mOptions = bundle;
        this.mWindowManager = android.view.WindowManagerImpl.createWindowContextWindowManager(this);
        this.mController = new android.window.WindowContextController((android.window.WindowTokenClient) getWindowContextToken());
        java.lang.ref.Reference.reachabilityFence(this);
    }

    public void attachToDisplayArea() {
        this.mController.attachToDisplayArea(this.mType, getDisplayId(), this.mOptions);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public java.lang.Object getSystemService(java.lang.String str) {
        if (android.content.Context.WINDOW_SERVICE.equals(str)) {
            return this.mWindowManager;
        }
        return super.getSystemService(str);
    }

    protected void finalize() throws java.lang.Throwable {
        release();
        super.finalize();
    }

    public void release() {
        this.mController.detachIfNeeded();
        destroy();
    }

    @Override // android.content.Context
    public void destroy() {
        try {
            this.mCallbacksController.clearCallbacks();
            getBaseContext().destroy();
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        this.mCallbacksController.registerCallbacks(componentCallbacks);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        this.mCallbacksController.unregisterCallbacks(componentCallbacks);
    }

    public void dispatchConfigurationChanged(android.content.res.Configuration configuration) {
        this.mCallbacksController.dispatchConfigurationChanged(configuration);
    }

    @Override // android.window.WindowProvider
    public int getWindowType() {
        return this.mType;
    }

    @Override // android.window.WindowProvider
    public android.os.Bundle getWindowContextOptions() {
        return this.mOptions;
    }
}
