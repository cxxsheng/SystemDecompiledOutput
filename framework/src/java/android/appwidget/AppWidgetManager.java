package android.appwidget;

/* loaded from: classes.dex */
public class AppWidgetManager {
    public static final java.lang.String ACTION_APPWIDGET_BIND = "android.appwidget.action.APPWIDGET_BIND";
    public static final java.lang.String ACTION_APPWIDGET_CONFIGURE = "android.appwidget.action.APPWIDGET_CONFIGURE";
    public static final java.lang.String ACTION_APPWIDGET_DELETED = "android.appwidget.action.APPWIDGET_DELETED";
    public static final java.lang.String ACTION_APPWIDGET_DISABLED = "android.appwidget.action.APPWIDGET_DISABLED";
    public static final java.lang.String ACTION_APPWIDGET_ENABLED = "android.appwidget.action.APPWIDGET_ENABLED";
    public static final java.lang.String ACTION_APPWIDGET_ENABLE_AND_UPDATE = "android.appwidget.action.APPWIDGET_ENABLE_AND_UPDATE";
    public static final java.lang.String ACTION_APPWIDGET_HOST_RESTORED = "android.appwidget.action.APPWIDGET_HOST_RESTORED";
    public static final java.lang.String ACTION_APPWIDGET_OPTIONS_CHANGED = "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS";
    public static final java.lang.String ACTION_APPWIDGET_PICK = "android.appwidget.action.APPWIDGET_PICK";
    public static final java.lang.String ACTION_APPWIDGET_RESTORED = "android.appwidget.action.APPWIDGET_RESTORED";
    public static final java.lang.String ACTION_APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final java.lang.String ACTION_KEYGUARD_APPWIDGET_PICK = "android.appwidget.action.KEYGUARD_APPWIDGET_PICK";
    public static final java.lang.String EXTRA_APPWIDGET_ID = "appWidgetId";
    public static final java.lang.String EXTRA_APPWIDGET_IDS = "appWidgetIds";
    public static final java.lang.String EXTRA_APPWIDGET_OLD_IDS = "appWidgetOldIds";
    public static final java.lang.String EXTRA_APPWIDGET_OPTIONS = "appWidgetOptions";
    public static final java.lang.String EXTRA_APPWIDGET_PREVIEW = "appWidgetPreview";
    public static final java.lang.String EXTRA_APPWIDGET_PROVIDER = "appWidgetProvider";
    public static final java.lang.String EXTRA_APPWIDGET_PROVIDER_PROFILE = "appWidgetProviderProfile";
    public static final java.lang.String EXTRA_CATEGORY_FILTER = "categoryFilter";
    public static final java.lang.String EXTRA_CUSTOM_EXTRAS = "customExtras";
    public static final java.lang.String EXTRA_CUSTOM_INFO = "customInfo";
    public static final java.lang.String EXTRA_CUSTOM_SORT = "customSort";
    public static final java.lang.String EXTRA_HOST_ID = "hostId";
    public static final int INVALID_APPWIDGET_ID = 0;
    public static final java.lang.String META_DATA_APPWIDGET_PROVIDER = "android.appwidget.provider";
    public static final java.lang.String OPTION_APPWIDGET_HOST_CATEGORY = "appWidgetCategory";
    public static final java.lang.String OPTION_APPWIDGET_MAX_HEIGHT = "appWidgetMaxHeight";
    public static final java.lang.String OPTION_APPWIDGET_MAX_WIDTH = "appWidgetMaxWidth";
    public static final java.lang.String OPTION_APPWIDGET_MIN_HEIGHT = "appWidgetMinHeight";
    public static final java.lang.String OPTION_APPWIDGET_MIN_WIDTH = "appWidgetMinWidth";
    public static final java.lang.String OPTION_APPWIDGET_RESTORE_COMPLETED = "appWidgetRestoreCompleted";
    public static final java.lang.String OPTION_APPWIDGET_SIZES = "appWidgetSizes";
    private static final java.lang.String TAG = "AppWidgetManager";
    private static java.util.concurrent.Executor sUpdateExecutor;
    private final android.content.Context mContext;
    private final android.util.DisplayMetrics mDisplayMetrics;
    private boolean mHasPostedLegacyLists = false;
    private final java.lang.String mPackageName;
    private final com.android.internal.appwidget.IAppWidgetService mService;

    public static android.appwidget.AppWidgetManager getInstance(android.content.Context context) {
        return (android.appwidget.AppWidgetManager) context.getSystemService(android.content.Context.APPWIDGET_SERVICE);
    }

    public AppWidgetManager(android.content.Context context, com.android.internal.appwidget.IAppWidgetService iAppWidgetService) {
        this.mContext = context;
        this.mPackageName = context.getOpPackageName();
        this.mService = iAppWidgetService;
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        if (this.mService == null) {
            return;
        }
        com.android.internal.os.BackgroundThread.getExecutor().execute(new java.lang.Runnable() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                android.appwidget.AppWidgetManager.this.lambda$new$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3() {
        try {
            this.mService.notifyProviderInheritance((android.content.ComponentName[]) getInstalledProvidersForPackage(this.mPackageName, null).stream().filter(new java.util.function.Predicate() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return java.util.Objects.nonNull((android.appwidget.AppWidgetProviderInfo) obj);
                }
            }).map(new java.util.function.Function() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.content.ComponentName componentName;
                    componentName = ((android.appwidget.AppWidgetProviderInfo) obj).provider;
                    return componentName;
                }
            }).filter(new java.util.function.Predicate() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return android.appwidget.AppWidgetManager.lambda$new$1((android.content.ComponentName) obj);
                }
            }).toArray(new java.util.function.IntFunction() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda5
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i) {
                    return android.appwidget.AppWidgetManager.lambda$new$2(i);
                }
            }));
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Notify service of inheritance info", e);
        }
    }

    static /* synthetic */ boolean lambda$new$1(android.content.ComponentName componentName) {
        try {
            return android.appwidget.AppWidgetProvider.class.isAssignableFrom(java.lang.Class.forName(componentName.getClassName()));
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    static /* synthetic */ android.content.ComponentName[] lambda$new$2(int i) {
        return new android.content.ComponentName[i];
    }

    private void tryAdapterConversion(final com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer<android.widget.RemoteViews> remoteExceptionIgnoringConsumer, android.widget.RemoteViews remoteViews, final java.lang.String str) {
        if (android.appwidget.flags.Flags.remoteAdapterConversion()) {
            boolean z = this.mHasPostedLegacyLists || (remoteViews != null && remoteViews.hasLegacyLists());
            this.mHasPostedLegacyLists = z;
            if (z) {
                final android.widget.RemoteViews remoteViews2 = new android.widget.RemoteViews(remoteViews);
                java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.appwidget.AppWidgetManager.lambda$tryAdapterConversion$4(android.widget.RemoteViews.this, remoteExceptionIgnoringConsumer, str);
                    }
                };
                if (android.os.Looper.getMainLooper() == android.os.Looper.myLooper()) {
                    createUpdateExecutorIfNull().execute(runnable);
                    return;
                } else {
                    runnable.run();
                    return;
                }
            }
        }
        try {
            remoteExceptionIgnoringConsumer.acceptOrThrow(remoteViews);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$tryAdapterConversion$4(android.widget.RemoteViews remoteViews, com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer remoteExceptionIgnoringConsumer, java.lang.String str) {
        try {
            remoteViews.collectAllIntents().get();
            remoteExceptionIgnoringConsumer.acceptOrThrow(remoteViews);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, str, e);
        }
    }

    public void updateAppWidget(final int[] iArr, android.widget.RemoteViews remoteViews) {
        if (this.mService == null) {
            return;
        }
        tryAdapterConversion(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda11
            @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
            public final void acceptOrThrow(java.lang.Object obj) {
                android.appwidget.AppWidgetManager.this.lambda$updateAppWidget$5(iArr, (android.widget.RemoteViews) obj);
            }
        }, remoteViews, "Error updating app widget views in background");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAppWidget$5(int[] iArr, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
        this.mService.updateAppWidgetIds(this.mPackageName, iArr, remoteViews);
    }

    public void updateAppWidgetOptions(int i, android.os.Bundle bundle) {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.updateAppWidgetOptions(this.mPackageName, i, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.Bundle getAppWidgetOptions(int i) {
        if (this.mService == null) {
            return android.os.Bundle.EMPTY;
        }
        try {
            return this.mService.getAppWidgetOptions(this.mPackageName, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateAppWidget(int i, android.widget.RemoteViews remoteViews) {
        if (this.mService == null) {
            return;
        }
        updateAppWidget(new int[]{i}, remoteViews);
    }

    public void partiallyUpdateAppWidget(final int[] iArr, android.widget.RemoteViews remoteViews) {
        if (this.mService == null) {
            return;
        }
        tryAdapterConversion(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda10
            @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
            public final void acceptOrThrow(java.lang.Object obj) {
                android.appwidget.AppWidgetManager.this.lambda$partiallyUpdateAppWidget$6(iArr, (android.widget.RemoteViews) obj);
            }
        }, remoteViews, "Error partially updating app widget views in background");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$partiallyUpdateAppWidget$6(int[] iArr, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
        this.mService.partiallyUpdateAppWidgetIds(this.mPackageName, iArr, remoteViews);
    }

    public void partiallyUpdateAppWidget(int i, android.widget.RemoteViews remoteViews) {
        if (this.mService == null) {
            return;
        }
        partiallyUpdateAppWidget(new int[]{i}, remoteViews);
    }

    public void updateAppWidget(final android.content.ComponentName componentName, android.widget.RemoteViews remoteViews) {
        if (this.mService == null) {
            return;
        }
        tryAdapterConversion(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda6
            @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
            public final void acceptOrThrow(java.lang.Object obj) {
                android.appwidget.AppWidgetManager.this.lambda$updateAppWidget$7(componentName, (android.widget.RemoteViews) obj);
            }
        }, remoteViews, "Error updating app widget view using provider in background");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAppWidget$7(android.content.ComponentName componentName, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
        this.mService.updateAppWidgetProvider(componentName, remoteViews);
    }

    public void updateAppWidgetProviderInfo(android.content.ComponentName componentName, java.lang.String str) {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.updateAppWidgetProviderInfo(componentName, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyAppWidgetViewDataChanged(final int[] iArr, final int i) {
        if (this.mService == null) {
            return;
        }
        if (android.appwidget.flags.Flags.remoteAdapterConversion()) {
            if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) {
                this.mHasPostedLegacyLists = true;
                createUpdateExecutorIfNull().execute(new java.lang.Runnable() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.appwidget.AppWidgetManager.this.lambda$notifyAppWidgetViewDataChanged$8(iArr, i);
                    }
                });
                return;
            } else {
                lambda$notifyAppWidgetViewDataChanged$8(iArr, i);
                return;
            }
        }
        try {
            this.mService.notifyAppWidgetViewDataChanged(this.mPackageName, iArr, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: notifyCollectionWidgetChange, reason: merged with bridge method [inline-methods] */
    public void lambda$notifyAppWidgetViewDataChanged$8(int[] iArr, final int i) {
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (final int i2 : iArr) {
                arrayList.add(java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.appwidget.AppWidgetManager.this.lambda$notifyCollectionWidgetChange$9(i2, i);
                    }
                }));
            }
            java.util.concurrent.CompletableFuture.allOf((java.util.concurrent.CompletableFuture[]) arrayList.toArray(new java.util.function.IntFunction() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i3) {
                    return android.appwidget.AppWidgetManager.lambda$notifyCollectionWidgetChange$10(i3);
                }
            })).join();
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error notifying changes for all widgets", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyCollectionWidgetChange$9(int i, int i2) {
        try {
            android.widget.RemoteViews appWidgetViews = this.mService.getAppWidgetViews(this.mPackageName, i);
            if (appWidgetViews.replaceRemoteCollections(i2)) {
                updateAppWidget(i, appWidgetViews);
            }
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error notifying changes in RemoteViews", e);
        }
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture[] lambda$notifyCollectionWidgetChange$10(int i) {
        return new java.util.concurrent.CompletableFuture[i];
    }

    public void notifyAppWidgetViewDataChanged(int i, int i2) {
        if (this.mService == null) {
            return;
        }
        notifyAppWidgetViewDataChanged(new int[]{i}, i2);
    }

    public java.util.List<android.appwidget.AppWidgetProviderInfo> getInstalledProvidersForProfile(android.os.UserHandle userHandle) {
        if (this.mService == null) {
            return java.util.Collections.emptyList();
        }
        return getInstalledProvidersForProfile(1, userHandle, null);
    }

    public java.util.List<android.appwidget.AppWidgetProviderInfo> getInstalledProvidersForPackage(java.lang.String str, android.os.UserHandle userHandle) {
        if (str == null) {
            throw new java.lang.NullPointerException("A non-null package must be passed to this method. If you want all widgets regardless of package, see getInstalledProvidersForProfile(UserHandle)");
        }
        if (this.mService == null) {
            return java.util.Collections.emptyList();
        }
        return getInstalledProvidersForProfile(1, userHandle, str);
    }

    public java.util.List<android.appwidget.AppWidgetProviderInfo> getInstalledProviders() {
        if (this.mService == null) {
            return java.util.Collections.emptyList();
        }
        return getInstalledProvidersForProfile(1, null, null);
    }

    public java.util.List<android.appwidget.AppWidgetProviderInfo> getInstalledProviders(int i) {
        if (this.mService == null) {
            return java.util.Collections.emptyList();
        }
        return getInstalledProvidersForProfile(i, null, null);
    }

    public java.util.List<android.appwidget.AppWidgetProviderInfo> getInstalledProvidersForProfile(int i, android.os.UserHandle userHandle, java.lang.String str) {
        if (this.mService == null) {
            return java.util.Collections.emptyList();
        }
        if (userHandle == null) {
            userHandle = this.mContext.getUser();
        }
        try {
            android.content.pm.ParceledListSlice installedProvidersForProfile = this.mService.getInstalledProvidersForProfile(i, userHandle.getIdentifier(), str);
            if (installedProvidersForProfile == null) {
                return java.util.Collections.emptyList();
            }
            java.util.Iterator it = installedProvidersForProfile.getList().iterator();
            while (it.hasNext()) {
                ((android.appwidget.AppWidgetProviderInfo) it.next()).updateDimensions(this.mDisplayMetrics);
            }
            return installedProvidersForProfile.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.appwidget.AppWidgetProviderInfo getAppWidgetInfo(int i) {
        if (this.mService == null) {
            return null;
        }
        try {
            android.appwidget.AppWidgetProviderInfo appWidgetInfo = this.mService.getAppWidgetInfo(this.mPackageName, i);
            if (appWidgetInfo != null) {
                appWidgetInfo.updateDimensions(this.mDisplayMetrics);
            }
            return appWidgetInfo;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void bindAppWidgetId(int i, android.content.ComponentName componentName) {
        if (this.mService == null) {
            return;
        }
        bindAppWidgetId(i, componentName, null);
    }

    public void bindAppWidgetId(int i, android.content.ComponentName componentName, android.os.Bundle bundle) {
        if (this.mService == null) {
            return;
        }
        bindAppWidgetIdIfAllowed(i, this.mContext.getUser(), componentName, bundle);
    }

    public boolean bindAppWidgetIdIfAllowed(int i, android.content.ComponentName componentName) {
        if (this.mService == null) {
            return false;
        }
        return bindAppWidgetIdIfAllowed(i, this.mContext.getUserId(), componentName, (android.os.Bundle) null);
    }

    public boolean bindAppWidgetIdIfAllowed(int i, android.content.ComponentName componentName, android.os.Bundle bundle) {
        if (this.mService == null) {
            return false;
        }
        return bindAppWidgetIdIfAllowed(i, this.mContext.getUserId(), componentName, bundle);
    }

    public boolean bindAppWidgetIdIfAllowed(int i, android.os.UserHandle userHandle, android.content.ComponentName componentName, android.os.Bundle bundle) {
        if (this.mService == null) {
            return false;
        }
        return bindAppWidgetIdIfAllowed(i, userHandle.getIdentifier(), componentName, bundle);
    }

    public boolean hasBindAppWidgetPermission(java.lang.String str, int i) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.hasBindAppWidgetPermission(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasBindAppWidgetPermission(java.lang.String str) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.hasBindAppWidgetPermission(str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBindAppWidgetPermission(java.lang.String str, boolean z) {
        if (this.mService == null) {
            return;
        }
        setBindAppWidgetPermission(str, this.mContext.getUserId(), z);
    }

    public void setBindAppWidgetPermission(java.lang.String str, int i, boolean z) {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.setBindAppWidgetPermission(str, i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean bindRemoteViewsService(android.content.Context context, int i, android.content.Intent intent, android.app.IServiceConnection iServiceConnection, int i2) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.bindRemoteViewsService(context.getOpPackageName(), i, intent, context.getIApplicationThread(), context.getActivityToken(), iServiceConnection, java.lang.Integer.toUnsignedLong(i2));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getAppWidgetIds(android.content.ComponentName componentName) {
        if (this.mService == null) {
            return new int[0];
        }
        try {
            return this.mService.getAppWidgetIds(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBoundWidgetPackage(java.lang.String str, int i) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.isBoundWidgetPackage(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean bindAppWidgetIdIfAllowed(int i, int i2, android.content.ComponentName componentName, android.os.Bundle bundle) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.bindAppWidgetId(this.mPackageName, i, i2, componentName, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRequestPinAppWidgetSupported() {
        try {
            return this.mService.isRequestPinAppWidgetSupported();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestPinAppWidget(android.content.ComponentName componentName, android.app.PendingIntent pendingIntent) {
        return requestPinAppWidget(componentName, null, pendingIntent);
    }

    public boolean requestPinAppWidget(android.content.ComponentName componentName, android.os.Bundle bundle, android.app.PendingIntent pendingIntent) {
        try {
            return this.mService.requestPinAppWidget(this.mPackageName, componentName, bundle, pendingIntent == null ? null : pendingIntent.getIntentSender());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void noteAppWidgetTapped(int i) {
        try {
            this.mService.noteAppWidgetTapped(this.mPackageName, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWidgetPreview(android.content.ComponentName componentName, int i, android.widget.RemoteViews remoteViews) {
        try {
            this.mService.setWidgetPreview(componentName, i, remoteViews);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.widget.RemoteViews getWidgetPreview(android.content.ComponentName componentName, android.os.UserHandle userHandle, int i) {
        if (userHandle == null) {
            try {
                userHandle = this.mContext.getUser();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mService.getWidgetPreview(this.mPackageName, componentName, userHandle.getIdentifier(), i);
    }

    public void removeWidgetPreview(android.content.ComponentName componentName, int i) {
        try {
            this.mService.removeWidgetPreview(componentName, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static java.util.concurrent.Executor createUpdateExecutorIfNull() {
        if (sUpdateExecutor == null) {
            sUpdateExecutor = new android.os.HandlerExecutor(createAndStartNewHandler("widget_manager_update_helper_thread", -2));
        }
        return sUpdateExecutor;
    }

    private static android.os.Handler createAndStartNewHandler(java.lang.String str, int i) {
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(str, i);
        handlerThread.start();
        return handlerThread.getThreadHandler();
    }
}
