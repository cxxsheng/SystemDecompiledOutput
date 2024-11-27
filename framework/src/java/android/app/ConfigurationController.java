package android.app;

/* loaded from: classes.dex */
class ConfigurationController {
    private static final java.lang.String TAG = "ConfigurationController";
    private final android.app.ActivityThreadInternal mActivityThread;
    private android.content.res.Configuration mCompatConfiguration;
    private android.content.res.Configuration mConfiguration;
    private android.content.res.Configuration mPendingConfiguration;
    private final android.app.ResourcesManager mResourcesManager = android.app.ResourcesManager.getInstance();

    ConfigurationController(android.app.ActivityThreadInternal activityThreadInternal) {
        this.mActivityThread = activityThreadInternal;
    }

    android.content.res.Configuration updatePendingConfiguration(android.content.res.Configuration configuration) {
        synchronized (this.mResourcesManager) {
            if (this.mPendingConfiguration != null && !this.mPendingConfiguration.isOtherSeqNewer(configuration)) {
                return null;
            }
            this.mPendingConfiguration = configuration;
            return this.mPendingConfiguration;
        }
    }

    android.content.res.Configuration getPendingConfiguration(boolean z) {
        android.content.res.Configuration configuration;
        synchronized (this.mResourcesManager) {
            configuration = null;
            if (this.mPendingConfiguration != null) {
                android.content.res.Configuration configuration2 = this.mPendingConfiguration;
                if (z) {
                    this.mPendingConfiguration = null;
                }
                configuration = configuration2;
            }
        }
        return configuration;
    }

    void setCompatConfiguration(android.content.res.Configuration configuration) {
        this.mCompatConfiguration = new android.content.res.Configuration(configuration);
    }

    android.content.res.Configuration getCompatConfiguration() {
        return this.mCompatConfiguration;
    }

    final android.content.res.Configuration applyCompatConfiguration() {
        android.content.res.Configuration configuration = this.mConfiguration;
        int i = configuration.densityDpi;
        if (this.mCompatConfiguration == null) {
            this.mCompatConfiguration = new android.content.res.Configuration();
        }
        this.mCompatConfiguration.setTo(this.mConfiguration);
        if (this.mResourcesManager.applyCompatConfiguration(i, this.mCompatConfiguration)) {
            return this.mCompatConfiguration;
        }
        return configuration;
    }

    void setConfiguration(android.content.res.Configuration configuration) {
        this.mConfiguration = new android.content.res.Configuration(configuration);
    }

    android.content.res.Configuration getConfiguration() {
        return this.mConfiguration;
    }

    void handleConfigurationChanged(android.content.res.Configuration configuration) {
        android.os.Trace.traceBegin(64L, "configChanged");
        handleConfigurationChanged(configuration, null);
        android.os.Trace.traceEnd(64L);
    }

    void handleConfigurationChanged(android.content.res.CompatibilityInfo compatibilityInfo) {
        handleConfigurationChanged(this.mConfiguration, compatibilityInfo);
        android.view.WindowManagerGlobal.getInstance().reportNewConfiguration(this.mConfiguration);
    }

    void handleConfigurationChanged(android.content.res.Configuration configuration, android.content.res.CompatibilityInfo compatibilityInfo) {
        android.content.res.Resources.Theme theme = this.mActivityThread.getSystemContext().getTheme();
        android.app.ContextImpl systemUiContextNoCreate = this.mActivityThread.getSystemUiContextNoCreate();
        android.content.res.Resources.Theme theme2 = systemUiContextNoCreate != null ? systemUiContextNoCreate.getTheme() : null;
        synchronized (this.mResourcesManager) {
            if (this.mPendingConfiguration != null) {
                if (!this.mPendingConfiguration.isOtherSeqNewer(configuration)) {
                    configuration = this.mPendingConfiguration;
                    updateDefaultDensity(configuration.densityDpi);
                }
                this.mPendingConfiguration = null;
            }
            if (configuration == null) {
                return;
            }
            boolean z = this.mConfiguration != null && this.mConfiguration.diffPublicOnly(configuration) == 0;
            android.app.Application application = this.mActivityThread.getApplication();
            application.getResources();
            this.mResourcesManager.applyConfigurationToResources(configuration, compatibilityInfo);
            updateLocaleListFromAppContext(application.getApplicationContext());
            if (this.mConfiguration == null) {
                this.mConfiguration = new android.content.res.Configuration();
            }
            if (this.mConfiguration.isOtherSeqNewer(configuration) || compatibilityInfo != null) {
                int updateFrom = this.mConfiguration.updateFrom(configuration);
                android.content.res.Configuration applyCompatConfiguration = applyCompatConfiguration();
                android.graphics.HardwareRenderer.sendDeviceConfigurationForDebugging(applyCompatConfiguration);
                if ((theme.getChangingConfigurations() & updateFrom) != 0) {
                    theme.rebase();
                }
                if (theme2 != null && (theme2.getChangingConfigurations() & updateFrom) != 0) {
                    theme2.rebase();
                }
                java.util.ArrayList<android.content.ComponentCallbacks2> collectComponentCallbacks = this.mActivityThread.collectComponentCallbacks(false);
                android.window.ConfigurationHelper.freeTextLayoutCachesIfNeeded(updateFrom);
                if (collectComponentCallbacks != null) {
                    int size = collectComponentCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        android.content.ComponentCallbacks2 componentCallbacks2 = collectComponentCallbacks.get(i);
                        if (!z) {
                            performConfigurationChanged(componentCallbacks2, applyCompatConfiguration);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void performConfigurationChanged(android.content.ComponentCallbacks2 componentCallbacks2, android.content.res.Configuration configuration) {
        android.content.res.Configuration configuration2;
        if (!(componentCallbacks2 instanceof android.view.ContextThemeWrapper)) {
            configuration2 = null;
        } else {
            configuration2 = ((android.view.ContextThemeWrapper) componentCallbacks2).getOverrideConfiguration();
        }
        componentCallbacks2.onConfigurationChanged(createNewConfigAndUpdateIfNotNull(configuration, configuration2));
    }

    void updateDefaultDensity(int i) {
        if (!this.mActivityThread.isInDensityCompatMode() && i != 0 && i != android.util.DisplayMetrics.DENSITY_DEVICE) {
            android.util.DisplayMetrics.DENSITY_DEVICE = i;
            android.graphics.Bitmap.setDefaultDensity(i);
        }
    }

    int getCurDefaultDisplayDpi() {
        return this.mConfiguration.densityDpi;
    }

    void updateLocaleListFromAppContext(android.content.Context context) {
        java.util.Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        android.os.LocaleList locales = this.mResourcesManager.getConfiguration().getLocales();
        int size = locales.size();
        for (int i = 0; i < size; i++) {
            if (locale.equals(locales.get(i))) {
                android.os.LocaleList.setDefault(locales, i);
                return;
            }
        }
        android.os.LocaleList.setDefault(new android.os.LocaleList(locale, locales));
    }

    static android.content.res.Configuration createNewConfigAndUpdateIfNotNull(android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        if (configuration2 == null) {
            return configuration;
        }
        android.content.res.Configuration configuration3 = new android.content.res.Configuration(configuration);
        configuration3.updateFrom(configuration2);
        return configuration3;
    }
}
