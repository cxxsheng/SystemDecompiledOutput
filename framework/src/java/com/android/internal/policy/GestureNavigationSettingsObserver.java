package com.android.internal.policy;

/* loaded from: classes5.dex */
public class GestureNavigationSettingsObserver extends android.database.ContentObserver {
    private android.content.Context mContext;
    private android.os.Handler mMainHandler;
    private java.lang.Runnable mOnChangeRunnable;
    private final android.provider.DeviceConfig.OnPropertiesChangedListener mOnPropertiesChangedListener;

    public GestureNavigationSettingsObserver(android.os.Handler handler, android.content.Context context, java.lang.Runnable runnable) {
        super(handler);
        this.mOnPropertiesChangedListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.internal.policy.GestureNavigationSettingsObserver.1
            public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                if ("systemui".equals(properties.getNamespace()) && com.android.internal.policy.GestureNavigationSettingsObserver.this.mOnChangeRunnable != null) {
                    com.android.internal.policy.GestureNavigationSettingsObserver.this.mOnChangeRunnable.run();
                }
            }
        };
        this.mMainHandler = handler;
        this.mContext = context;
        this.mOnChangeRunnable = runnable;
    }

    public void register() {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT), false, this, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT), false, this, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.USER_SETUP_COMPLETE), false, this, -1);
        android.provider.DeviceConfig.addOnPropertiesChangedListener("systemui", new java.util.concurrent.Executor() { // from class: com.android.internal.policy.GestureNavigationSettingsObserver$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.Executor
            public final void execute(java.lang.Runnable runnable) {
                com.android.internal.policy.GestureNavigationSettingsObserver.this.lambda$register$0(runnable);
            }
        }, this.mOnPropertiesChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$register$0(java.lang.Runnable runnable) {
        this.mMainHandler.post(runnable);
    }

    public void registerForCallingUser() {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT), false, this);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT), false, this);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.USER_SETUP_COMPLETE), false, this);
        android.provider.DeviceConfig.addOnPropertiesChangedListener("systemui", new java.util.concurrent.Executor() { // from class: com.android.internal.policy.GestureNavigationSettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Executor
            public final void execute(java.lang.Runnable runnable) {
                com.android.internal.policy.GestureNavigationSettingsObserver.this.lambda$registerForCallingUser$1(runnable);
            }
        }, this.mOnPropertiesChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerForCallingUser$1(java.lang.Runnable runnable) {
        this.mMainHandler.post(runnable);
    }

    public void unregister() {
        this.mContext.getContentResolver().unregisterContentObserver(this);
        android.provider.DeviceConfig.removeOnPropertiesChangedListener(this.mOnPropertiesChangedListener);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        super.onChange(z);
        if (this.mOnChangeRunnable != null) {
            this.mOnChangeRunnable.run();
        }
    }

    public int getLeftSensitivity(android.content.res.Resources resources) {
        return (int) (getUnscaledInset(resources) * android.provider.Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT, 1.0f, -2));
    }

    public int getLeftSensitivityForCallingUser(android.content.res.Resources resources) {
        return (int) (getUnscaledInset(resources) * android.provider.Settings.Secure.getFloat(this.mContext.getContentResolver(), android.provider.Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT, 1.0f));
    }

    public int getRightSensitivity(android.content.res.Resources resources) {
        return (int) (getUnscaledInset(resources) * android.provider.Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT, 1.0f, -2));
    }

    public int getRightSensitivityForCallingUser(android.content.res.Resources resources) {
        return (int) (getUnscaledInset(resources) * android.provider.Settings.Secure.getFloat(this.mContext.getContentResolver(), android.provider.Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT, 1.0f));
    }

    public boolean areNavigationButtonForcedVisible() {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.USER_SETUP_COMPLETE, 0, -2) == 0;
    }

    private float getUnscaledInset(android.content.res.Resources resources) {
        android.util.DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float dimension = resources.getDimension(com.android.internal.R.dimen.config_backGestureInset) / displayMetrics.density;
        if (dimension > 0.0f) {
            dimension = android.provider.DeviceConfig.getFloat("systemui", com.android.internal.config.sysui.SystemUiDeviceConfigFlags.BACK_GESTURE_EDGE_WIDTH, dimension);
        }
        return android.util.TypedValue.applyDimension(1, dimension, displayMetrics);
    }
}
