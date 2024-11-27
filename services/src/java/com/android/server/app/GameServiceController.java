package com.android.server.app;

/* loaded from: classes.dex */
final class GameServiceController {
    private static final java.lang.String TAG = "GameServiceController";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private volatile com.android.server.app.GameServiceConfiguration.GameServiceComponentConfiguration mActiveGameServiceComponentConfiguration;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private volatile java.lang.String mActiveGameServiceProviderPackage;
    private final java.util.concurrent.Executor mBackgroundExecutor;
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private volatile com.android.server.SystemService.TargetUser mCurrentForegroundUser;

    @android.annotation.Nullable
    private android.content.BroadcastReceiver mGameServicePackageChangedReceiver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private volatile com.android.server.app.GameServiceProviderInstance mGameServiceProviderInstance;
    private final com.android.server.app.GameServiceProviderInstanceFactory mGameServiceProviderInstanceFactory;

    @android.annotation.Nullable
    private volatile java.lang.String mGameServiceProviderOverride;
    private final com.android.server.app.GameServiceProviderSelector mGameServiceProviderSelector;
    private volatile boolean mHasBootCompleted;
    private final java.lang.Object mLock = new java.lang.Object();

    GameServiceController(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull com.android.server.app.GameServiceProviderSelector gameServiceProviderSelector, @android.annotation.NonNull com.android.server.app.GameServiceProviderInstanceFactory gameServiceProviderInstanceFactory) {
        this.mContext = context;
        this.mGameServiceProviderInstanceFactory = gameServiceProviderInstanceFactory;
        this.mBackgroundExecutor = executor;
        this.mGameServiceProviderSelector = gameServiceProviderSelector;
    }

    void onBootComplete() {
        if (this.mHasBootCompleted) {
            return;
        }
        this.mHasBootCompleted = true;
        this.mBackgroundExecutor.execute(new com.android.server.app.GameServiceController$$ExternalSyntheticLambda0(this));
    }

    void notifyUserStarted(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        if (this.mCurrentForegroundUser != null) {
            return;
        }
        setCurrentForegroundUserAndEvaluateProvider(targetUser);
    }

    void notifyNewForegroundUser(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        setCurrentForegroundUserAndEvaluateProvider(targetUser);
    }

    void notifyUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        if (!(this.mCurrentForegroundUser != null && this.mCurrentForegroundUser.getUserIdentifier() == targetUser.getUserIdentifier())) {
            return;
        }
        this.mBackgroundExecutor.execute(new com.android.server.app.GameServiceController$$ExternalSyntheticLambda0(this));
    }

    void notifyUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        if (!(this.mCurrentForegroundUser != null && this.mCurrentForegroundUser.getUserIdentifier() == targetUser.getUserIdentifier())) {
            return;
        }
        setCurrentForegroundUserAndEvaluateProvider(null);
    }

    void setGameServiceProvider(@android.annotation.Nullable java.lang.String str) {
        if (!(!java.util.Objects.equals(this.mGameServiceProviderOverride, str))) {
            return;
        }
        this.mGameServiceProviderOverride = str;
        this.mBackgroundExecutor.execute(new com.android.server.app.GameServiceController$$ExternalSyntheticLambda0(this));
    }

    private void setCurrentForegroundUserAndEvaluateProvider(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser) {
        if (!(!java.util.Objects.equals(this.mCurrentForegroundUser, targetUser))) {
            return;
        }
        this.mCurrentForegroundUser = targetUser;
        this.mBackgroundExecutor.execute(new com.android.server.app.GameServiceController$$ExternalSyntheticLambda0(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void evaluateActiveGameServiceProvider() {
        java.lang.String packageName;
        com.android.server.app.GameServiceConfiguration.GameServiceComponentConfiguration gameServiceComponentConfiguration;
        if (!this.mHasBootCompleted) {
            return;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.app.GameServiceConfiguration gameServiceConfiguration = this.mGameServiceProviderSelector.get(this.mCurrentForegroundUser, this.mGameServiceProviderOverride);
                if (gameServiceConfiguration == null) {
                    packageName = null;
                } else {
                    packageName = gameServiceConfiguration.getPackageName();
                }
                if (gameServiceConfiguration == null) {
                    gameServiceComponentConfiguration = null;
                } else {
                    gameServiceComponentConfiguration = gameServiceConfiguration.getGameServiceComponentConfiguration();
                }
                evaluateGameServiceProviderPackageChangedListenerLocked(packageName);
                if (!java.util.Objects.equals(gameServiceComponentConfiguration, this.mActiveGameServiceComponentConfiguration)) {
                    if (this.mGameServiceProviderInstance != null) {
                        android.util.Slog.i(TAG, "Stopping Game Service provider: " + this.mActiveGameServiceComponentConfiguration);
                        this.mGameServiceProviderInstance.stop();
                        this.mGameServiceProviderInstance = null;
                    }
                    this.mActiveGameServiceComponentConfiguration = gameServiceComponentConfiguration;
                    if (this.mActiveGameServiceComponentConfiguration == null) {
                        return;
                    }
                    android.util.Slog.i(TAG, "Starting Game Service provider: " + this.mActiveGameServiceComponentConfiguration);
                    this.mGameServiceProviderInstance = this.mGameServiceProviderInstanceFactory.create(this.mActiveGameServiceComponentConfiguration);
                    this.mGameServiceProviderInstance.start();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void evaluateGameServiceProviderPackageChangedListenerLocked(@android.annotation.Nullable java.lang.String str) {
        if (android.text.TextUtils.equals(this.mActiveGameServiceProviderPackage, str)) {
            return;
        }
        if (this.mGameServicePackageChangedReceiver != null) {
            this.mContext.unregisterReceiver(this.mGameServicePackageChangedReceiver);
            this.mGameServicePackageChangedReceiver = null;
        }
        this.mActiveGameServiceProviderPackage = str;
        if (android.text.TextUtils.isEmpty(this.mActiveGameServiceProviderPackage)) {
            return;
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        intentFilter.addDataSchemeSpecificPart(str, 0);
        this.mGameServicePackageChangedReceiver = new com.android.server.app.GameServiceController.PackageChangedBroadcastReceiver(str);
        this.mContext.registerReceiver(this.mGameServicePackageChangedReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class PackageChangedBroadcastReceiver extends android.content.BroadcastReceiver {
        private final java.lang.String mPackageName;

        PackageChangedBroadcastReceiver(java.lang.String str) {
            this.mPackageName = str;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (!android.text.TextUtils.equals(intent.getData().getSchemeSpecificPart(), this.mPackageName)) {
                return;
            }
            java.util.concurrent.Executor executor = com.android.server.app.GameServiceController.this.mBackgroundExecutor;
            final com.android.server.app.GameServiceController gameServiceController = com.android.server.app.GameServiceController.this;
            executor.execute(new java.lang.Runnable() { // from class: com.android.server.app.GameServiceController$PackageChangedBroadcastReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.app.GameServiceController.this.evaluateActiveGameServiceProvider();
                }
            });
        }
    }
}
