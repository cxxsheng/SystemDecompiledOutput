package android.window;

/* loaded from: classes4.dex */
public class WindowTokenClient extends android.os.Binder {
    private static final java.lang.String TAG = android.window.WindowTokenClient.class.getSimpleName();
    private boolean mShouldDumpConfigForIme;
    private java.lang.ref.WeakReference<android.content.Context> mContextRef = null;
    private final android.app.ResourcesManager mResourcesManager = android.app.ResourcesManager.getInstance();
    private final android.content.res.Configuration mConfiguration = new android.content.res.Configuration();
    private final android.os.Handler mHandler = android.app.ActivityThread.currentActivityThread().getHandler();

    public void attachContext(android.content.Context context) {
        if (this.mContextRef != null) {
            throw new java.lang.IllegalStateException("Context is already attached.");
        }
        this.mContextRef = new java.lang.ref.WeakReference<>(context);
        this.mShouldDumpConfigForIme = android.os.Build.IS_DEBUGGABLE && (context instanceof android.inputmethodservice.AbstractInputMethodService);
    }

    public android.content.Context getContext() {
        if (this.mContextRef != null) {
            return this.mContextRef.get();
        }
        return null;
    }

    public void onConfigurationChanged(android.content.res.Configuration configuration, int i) {
        onConfigurationChanged(configuration, i, true);
    }

    public void postOnConfigurationChanged(android.content.res.Configuration configuration, int i) {
        this.mHandler.post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.TriConsumer() { // from class: android.window.WindowTokenClient$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.function.TriConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                android.window.WindowTokenClient.this.onConfigurationChanged((android.content.res.Configuration) obj, ((java.lang.Integer) obj2).intValue(), ((java.lang.Boolean) obj3).booleanValue());
            }
        }, configuration, java.lang.Integer.valueOf(i), true).recycleOnUse());
    }

    public void onConfigurationChanged(android.content.res.Configuration configuration, int i, boolean z) {
        boolean isDifferentDisplay;
        boolean shouldUpdateResources;
        int diffPublicOnly;
        android.content.res.Configuration configuration2;
        android.content.Context context = this.mContextRef.get();
        if (context == null) {
            return;
        }
        android.content.res.CompatibilityInfo.applyOverrideScaleIfNeeded(configuration);
        synchronized (this.mConfiguration) {
            isDifferentDisplay = android.window.ConfigurationHelper.isDifferentDisplay(context.getDisplayId(), i);
            shouldUpdateResources = android.window.ConfigurationHelper.shouldUpdateResources(this, this.mConfiguration, configuration, configuration, isDifferentDisplay, null);
            diffPublicOnly = this.mConfiguration.diffPublicOnly(configuration);
            configuration2 = this.mShouldDumpConfigForIme ? new android.content.res.Configuration(this.mConfiguration) : null;
            if (shouldUpdateResources) {
                this.mConfiguration.setTo(configuration);
            }
        }
        if (!shouldUpdateResources && this.mShouldDumpConfigForIme) {
            android.util.Log.d(TAG, "Configuration not dispatch to IME because configuration is up to date. Current config=" + context.getResources().getConfiguration() + ", reported config=" + configuration2 + ", updated config=" + configuration);
        }
        if (isDifferentDisplay) {
            context.updateDisplay(i);
        }
        if (shouldUpdateResources) {
            this.mResourcesManager.updateResourcesForActivity(this, configuration, i);
            if (z && (context instanceof android.window.WindowContext)) {
                ((android.window.WindowContext) context).dispatchConfigurationChanged(configuration);
            }
            if (z && diffPublicOnly != 0 && (context instanceof android.window.WindowProviderService)) {
                ((android.window.WindowProviderService) context).onConfigurationChanged(configuration);
            }
            android.window.ConfigurationHelper.freeTextLayoutCachesIfNeeded(diffPublicOnly);
            if (this.mShouldDumpConfigForIme) {
                if (!z) {
                    android.util.Log.d(TAG, "Only apply configuration update to Resources because shouldReportConfigChange is false.\n" + android.os.Debug.getCallers(5));
                } else if (diffPublicOnly == 0) {
                    android.util.Log.d(TAG, "Configuration not dispatch to IME because configuration has no  public difference with updated config.  Current config=" + context.getResources().getConfiguration() + ", reported config=" + configuration2 + ", updated config=" + configuration);
                }
            }
        }
    }

    public void onWindowTokenRemoved() {
        android.content.Context context = this.mContextRef.get();
        if (context != null) {
            context.destroy();
            this.mContextRef.clear();
        }
    }
}
