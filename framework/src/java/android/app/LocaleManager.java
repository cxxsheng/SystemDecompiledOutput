package android.app;

/* loaded from: classes.dex */
public class LocaleManager {
    private static final java.lang.String TAG = "LocaleManager";
    private android.content.Context mContext;
    private android.app.ILocaleManager mService;

    public LocaleManager(android.content.Context context, android.app.ILocaleManager iLocaleManager) {
        this.mContext = context;
        this.mService = iLocaleManager;
    }

    public void setApplicationLocales(android.os.LocaleList localeList) {
        setApplicationLocales(this.mContext.getPackageName(), localeList, false);
    }

    @android.annotation.SystemApi
    public void setApplicationLocales(java.lang.String str, android.os.LocaleList localeList) {
        setApplicationLocales(str, localeList, true);
    }

    private void setApplicationLocales(java.lang.String str, android.os.LocaleList localeList, boolean z) {
        try {
            this.mService.setApplicationLocales(str, this.mContext.getUserId(), localeList, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.LocaleList getApplicationLocales() {
        return getApplicationLocales(this.mContext.getPackageName());
    }

    public android.os.LocaleList getApplicationLocales(java.lang.String str) {
        try {
            return this.mService.getApplicationLocales(str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.LocaleList getSystemLocales() {
        try {
            return this.mService.getSystemLocales();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSystemLocales(android.os.LocaleList localeList) {
        try {
            android.content.res.Configuration configuration = new android.content.res.Configuration();
            configuration.setLocales(localeList);
            android.app.ActivityManager.getService().updatePersistentConfiguration(configuration);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setOverrideLocaleConfig(android.app.LocaleConfig localeConfig) {
        try {
            this.mService.setOverrideLocaleConfig(this.mContext.getPackageName(), this.mContext.getUserId(), localeConfig);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.LocaleConfig getOverrideLocaleConfig() {
        try {
            return this.mService.getOverrideLocaleConfig(this.mContext.getPackageName(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
