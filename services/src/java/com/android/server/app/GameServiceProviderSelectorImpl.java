package com.android.server.app;

/* loaded from: classes.dex */
final class GameServiceProviderSelectorImpl implements com.android.server.app.GameServiceProviderSelector {
    private static final boolean DEBUG = false;
    private static final java.lang.String GAME_SERVICE_NODE_NAME = "game-service";
    private static final java.lang.String TAG = "GameServiceProviderSelector";
    private final android.content.pm.PackageManager mPackageManager;
    private final android.content.res.Resources mResources;

    GameServiceProviderSelectorImpl(@android.annotation.NonNull android.content.res.Resources resources, @android.annotation.NonNull android.content.pm.PackageManager packageManager) {
        this.mResources = resources;
        this.mPackageManager = packageManager;
    }

    @Override // com.android.server.app.GameServiceProviderSelector
    @android.annotation.Nullable
    public com.android.server.app.GameServiceConfiguration get(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.Nullable java.lang.String str) {
        com.android.server.app.GameServiceConfiguration gameServiceConfiguration;
        android.content.pm.ServiceInfo serviceInfo;
        android.content.ComponentName determineGameSessionServiceFromGameService;
        if (targetUser == null) {
            return null;
        }
        int i = 0;
        if (!(targetUser.isFull() && !targetUser.isManagedProfile())) {
            android.util.Slog.i(TAG, "Game Service not supported for user: " + targetUser.getUserIdentifier());
            return null;
        }
        if (android.text.TextUtils.isEmpty(str)) {
            str = this.mResources.getString(android.R.string.config_secondaryHomePackage);
            i = 1048576;
        }
        if (android.text.TextUtils.isEmpty(str)) {
            android.util.Slog.w(TAG, "No game service package defined");
            return null;
        }
        int userIdentifier = targetUser.getUserIdentifier();
        java.util.List queryIntentServicesAsUser = this.mPackageManager.queryIntentServicesAsUser(new android.content.Intent("android.service.games.action.GAME_SERVICE").setPackage(str), i | 128, userIdentifier);
        if (queryIntentServicesAsUser == null || queryIntentServicesAsUser.isEmpty()) {
            android.util.Slog.w(TAG, "No available game service found for user id: " + userIdentifier);
            return new com.android.server.app.GameServiceConfiguration(str, null);
        }
        java.util.Iterator it = queryIntentServicesAsUser.iterator();
        while (true) {
            if (!it.hasNext()) {
                gameServiceConfiguration = null;
                break;
            }
            android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) it.next();
            if (resolveInfo.serviceInfo != null && (determineGameSessionServiceFromGameService = determineGameSessionServiceFromGameService((serviceInfo = resolveInfo.serviceInfo))) != null) {
                gameServiceConfiguration = new com.android.server.app.GameServiceConfiguration(str, new com.android.server.app.GameServiceConfiguration.GameServiceComponentConfiguration(new android.os.UserHandle(userIdentifier), serviceInfo.getComponentName(), determineGameSessionServiceFromGameService));
                break;
            }
        }
        if (gameServiceConfiguration == null) {
            android.util.Slog.w(TAG, "No valid game service found for user id: " + userIdentifier);
            return new com.android.server.app.GameServiceConfiguration(str, null);
        }
        return gameServiceConfiguration;
    }

    @android.annotation.Nullable
    private android.content.ComponentName determineGameSessionServiceFromGameService(@android.annotation.NonNull android.content.pm.ServiceInfo serviceInfo) {
        int next;
        try {
            android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(this.mPackageManager, "android.game_service");
            try {
                if (loadXmlMetaData == null) {
                    android.util.Slog.w(TAG, "No android.game_service meta-data found for " + serviceInfo.getComponentName());
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                    return null;
                }
                android.content.res.Resources resourcesForApplication = this.mPackageManager.getResourcesForApplication(serviceInfo.packageName);
                android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                do {
                    next = loadXmlMetaData.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if (!GAME_SERVICE_NODE_NAME.equals(loadXmlMetaData.getName())) {
                    android.util.Slog.w(TAG, "Meta-data does not start with game-service tag");
                    loadXmlMetaData.close();
                    return null;
                }
                android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.GameService);
                java.lang.String string = obtainAttributes.getString(0);
                obtainAttributes.recycle();
                loadXmlMetaData.close();
                if (android.text.TextUtils.isEmpty(string)) {
                    android.util.Slog.w(TAG, "No gameSessionService specified");
                    return null;
                }
                android.content.ComponentName componentName = new android.content.ComponentName(serviceInfo.packageName, string);
                try {
                    this.mPackageManager.getServiceInfo(componentName, 0);
                    return componentName;
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Slog.w(TAG, "GameSessionService does not exist: " + componentName);
                    return null;
                }
            } catch (java.lang.Throwable th) {
                if (loadXmlMetaData != null) {
                    try {
                        loadXmlMetaData.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
            android.util.Slog.w("Error while parsing meta-data for " + serviceInfo.getComponentName(), e2);
            return null;
        }
    }
}
