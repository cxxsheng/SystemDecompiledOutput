package android.appwidget;

/* loaded from: classes.dex */
public class AppWidgetHost {
    static final int HANDLE_APP_WIDGET_REMOVED = 5;
    static final int HANDLE_PROVIDERS_CHANGED = 3;
    static final int HANDLE_PROVIDER_CHANGED = 2;
    static final int HANDLE_UPDATE = 1;
    static final int HANDLE_VIEW_DATA_CHANGED = 4;
    static com.android.internal.appwidget.IAppWidgetService sService;
    private final android.appwidget.AppWidgetHost.Callbacks mCallbacks;
    private java.lang.String mContextOpPackageName;
    private android.util.DisplayMetrics mDisplayMetrics;
    private final android.os.Handler mHandler;
    private final int mHostId;
    private android.widget.RemoteViews.InteractionHandler mInteractionHandler;
    private final android.util.SparseArray<android.appwidget.AppWidgetHost.AppWidgetHostListener> mListeners;
    static final java.lang.Object sServiceLock = new java.lang.Object();
    static boolean sServiceInitialized = false;

    public interface AppWidgetHostListener {
        void onUpdateProviderInfo(android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo);

        void onViewDataChanged(int i);

        void updateAppWidget(android.widget.RemoteViews remoteViews);
    }

    static class Callbacks extends com.android.internal.appwidget.IAppWidgetHost.Stub {
        private final java.lang.ref.WeakReference<android.os.Handler> mWeakHandler;

        public Callbacks(android.os.Handler handler) {
            this.mWeakHandler = new java.lang.ref.WeakReference<>(handler);
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void updateAppWidget(int i, android.widget.RemoteViews remoteViews) {
            if (isLocalBinder() && remoteViews != null) {
                remoteViews = remoteViews.mo424clone();
            }
            android.os.Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(1, i, 0, remoteViews).sendToTarget();
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void providerChanged(int i, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) {
            if (isLocalBinder() && appWidgetProviderInfo != null) {
                appWidgetProviderInfo = appWidgetProviderInfo.m723clone();
            }
            android.os.Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(2, i, 0, appWidgetProviderInfo).sendToTarget();
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void appWidgetRemoved(int i) {
            android.os.Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(5, i, 0).sendToTarget();
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void providersChanged() {
            android.os.Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(3).sendToTarget();
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void viewDataChanged(int i, int i2) {
            android.os.Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(4, i, i2).sendToTarget();
        }

        private static boolean isLocalBinder() {
            return android.os.Process.myPid() == android.os.Binder.getCallingPid();
        }
    }

    class UpdateHandler extends android.os.Handler {
        public UpdateHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.appwidget.AppWidgetHost.this.updateAppWidgetView(message.arg1, (android.widget.RemoteViews) message.obj);
                    break;
                case 2:
                    android.appwidget.AppWidgetHost.this.onProviderChanged(message.arg1, (android.appwidget.AppWidgetProviderInfo) message.obj);
                    break;
                case 3:
                    android.appwidget.AppWidgetHost.this.onProvidersChanged();
                    break;
                case 4:
                    android.appwidget.AppWidgetHost.this.viewDataChanged(message.arg1, message.arg2);
                    break;
                case 5:
                    android.appwidget.AppWidgetHost.this.dispatchOnAppWidgetRemoved(message.arg1);
                    break;
            }
        }
    }

    public AppWidgetHost(android.content.Context context, int i) {
        this(context, i, null, context.getMainLooper());
    }

    private android.appwidget.AppWidgetHost.AppWidgetHostListener getListener(int i) {
        android.appwidget.AppWidgetHost.AppWidgetHostListener appWidgetHostListener;
        synchronized (this.mListeners) {
            appWidgetHostListener = this.mListeners.get(i);
        }
        return appWidgetHostListener;
    }

    public AppWidgetHost(android.content.Context context, int i, android.widget.RemoteViews.InteractionHandler interactionHandler, android.os.Looper looper) {
        this.mListeners = new android.util.SparseArray<>();
        this.mContextOpPackageName = context.getOpPackageName();
        this.mHostId = i;
        this.mInteractionHandler = interactionHandler;
        this.mHandler = new android.appwidget.AppWidgetHost.UpdateHandler(looper);
        this.mCallbacks = new android.appwidget.AppWidgetHost.Callbacks(this.mHandler);
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        bindService(context);
    }

    private static void bindService(android.content.Context context) {
        synchronized (sServiceLock) {
            if (sServiceInitialized) {
                return;
            }
            sServiceInitialized = true;
            if (context.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_APP_WIDGETS) || context.getResources().getBoolean(com.android.internal.R.bool.config_enableAppWidgetService)) {
                sService = com.android.internal.appwidget.IAppWidgetService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.APPWIDGET_SERVICE));
            }
        }
    }

    public void startListening() {
        int[] iArr;
        int i;
        if (sService == null) {
            return;
        }
        synchronized (this.mListeners) {
            int size = this.mListeners.size();
            iArr = new int[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = this.mListeners.keyAt(i2);
            }
        }
        try {
            java.util.List list = sService.startListening(this.mCallbacks, this.mContextOpPackageName, this.mHostId, iArr).getList();
            int size2 = list.size();
            for (i = 0; i < size2; i++) {
                android.appwidget.PendingHostUpdate pendingHostUpdate = (android.appwidget.PendingHostUpdate) list.get(i);
                switch (pendingHostUpdate.type) {
                    case 0:
                        updateAppWidgetView(pendingHostUpdate.appWidgetId, pendingHostUpdate.views);
                        break;
                    case 1:
                        onProviderChanged(pendingHostUpdate.appWidgetId, pendingHostUpdate.widgetInfo);
                        break;
                    case 2:
                        viewDataChanged(pendingHostUpdate.appWidgetId, pendingHostUpdate.viewId);
                        break;
                    case 3:
                        dispatchOnAppWidgetRemoved(pendingHostUpdate.appWidgetId);
                        break;
                }
            }
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("system server dead?", e);
        }
    }

    public void stopListening() {
        if (sService == null) {
            return;
        }
        try {
            sService.stopListening(this.mContextOpPackageName, this.mHostId);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("system server dead?", e);
        }
    }

    public int allocateAppWidgetId() {
        if (sService == null) {
            return -1;
        }
        try {
            return sService.allocateAppWidgetId(this.mContextOpPackageName, this.mHostId);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("system server dead?", e);
        }
    }

    public final void startAppWidgetConfigureActivityForResult(android.app.Activity activity, int i, int i2, int i3, android.os.Bundle bundle) {
        if (sService == null) {
            return;
        }
        try {
            android.content.IntentSender createAppWidgetConfigIntentSender = sService.createAppWidgetConfigIntentSender(this.mContextOpPackageName, i, i2);
            if (createAppWidgetConfigIntentSender != null) {
                activity.startIntentSenderForResult(createAppWidgetConfigIntentSender, i3, (android.content.Intent) null, 0, 0, 0, bundle);
                return;
            }
            throw new android.content.ActivityNotFoundException();
        } catch (android.content.IntentSender.SendIntentException e) {
            throw new android.content.ActivityNotFoundException();
        } catch (android.os.RemoteException e2) {
            throw new java.lang.RuntimeException("system server dead?", e2);
        }
    }

    public void setAppWidgetHidden() {
        if (sService == null) {
            return;
        }
        try {
            sService.setAppWidgetHidden(this.mContextOpPackageName, this.mHostId);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("System server dead?", e);
        }
    }

    public void setInteractionHandler(android.widget.RemoteViews.InteractionHandler interactionHandler) {
        this.mInteractionHandler = interactionHandler;
    }

    public int[] getAppWidgetIds() {
        if (sService == null) {
            return new int[0];
        }
        try {
            return sService.getAppWidgetIdsForHost(this.mContextOpPackageName, this.mHostId);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("system server dead?", e);
        }
    }

    public void deleteAppWidgetId(int i) {
        if (sService == null) {
            return;
        }
        removeListener(i);
        try {
            sService.deleteAppWidgetId(this.mContextOpPackageName, i);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("system server dead?", e);
        }
    }

    public void deleteHost() {
        if (sService == null) {
            return;
        }
        try {
            sService.deleteHost(this.mContextOpPackageName, this.mHostId);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("system server dead?", e);
        }
    }

    public static void deleteAllHosts() {
        if (sService == null) {
            return;
        }
        try {
            sService.deleteAllHosts();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("system server dead?", e);
        }
    }

    public final android.appwidget.AppWidgetHostView createView(android.content.Context context, int i, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) {
        if (sService == null) {
            return null;
        }
        android.appwidget.AppWidgetHostView onCreateView = onCreateView(context, i, appWidgetProviderInfo);
        onCreateView.setInteractionHandler(this.mInteractionHandler);
        onCreateView.setAppWidget(i, appWidgetProviderInfo);
        setListener(i, onCreateView);
        return onCreateView;
    }

    protected android.appwidget.AppWidgetHostView onCreateView(android.content.Context context, int i, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) {
        return new android.appwidget.AppWidgetHostView(context, this.mInteractionHandler);
    }

    protected void onProviderChanged(int i, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) {
        android.appwidget.AppWidgetHost.AppWidgetHostListener listener = getListener(i);
        appWidgetProviderInfo.updateDimensions(this.mDisplayMetrics);
        if (listener != null) {
            listener.onUpdateProviderInfo(appWidgetProviderInfo);
        }
    }

    void dispatchOnAppWidgetRemoved(int i) {
        removeListener(i);
        onAppWidgetRemoved(i);
    }

    public void onAppWidgetRemoved(int i) {
    }

    protected void onProvidersChanged() {
    }

    public void setListener(int i, android.appwidget.AppWidgetHost.AppWidgetHostListener appWidgetHostListener) {
        synchronized (this.mListeners) {
            this.mListeners.put(i, appWidgetHostListener);
        }
        try {
            appWidgetHostListener.updateAppWidget(sService.getAppWidgetViews(this.mContextOpPackageName, i));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("system server dead?", e);
        }
    }

    public void removeListener(int i) {
        synchronized (this.mListeners) {
            this.mListeners.remove(i);
        }
    }

    void updateAppWidgetView(int i, android.widget.RemoteViews remoteViews) {
        android.appwidget.AppWidgetHost.AppWidgetHostListener listener = getListener(i);
        if (listener != null) {
            listener.updateAppWidget(remoteViews);
        }
    }

    void viewDataChanged(int i, int i2) {
        android.appwidget.AppWidgetHost.AppWidgetHostListener listener = getListener(i);
        if (listener != null) {
            listener.onViewDataChanged(i2);
        }
    }

    protected void clearViews() {
        synchronized (this.mListeners) {
            this.mListeners.clear();
        }
    }
}
