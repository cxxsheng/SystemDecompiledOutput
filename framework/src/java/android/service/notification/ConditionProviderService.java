package android.service.notification;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class ConditionProviderService extends android.app.Service {

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_RULE_ID = "android.service.notification.extra.RULE_ID";

    @java.lang.Deprecated
    public static final java.lang.String META_DATA_CONFIGURATION_ACTIVITY = "android.service.zen.automatic.configurationActivity";

    @java.lang.Deprecated
    public static final java.lang.String META_DATA_RULE_INSTANCE_LIMIT = "android.service.zen.automatic.ruleInstanceLimit";

    @java.lang.Deprecated
    public static final java.lang.String META_DATA_RULE_TYPE = "android.service.zen.automatic.ruleType";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.notification.ConditionProviderService";
    private final java.lang.String TAG = android.service.notification.ConditionProviderService.class.getSimpleName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + getClass().getSimpleName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    private final android.service.notification.ConditionProviderService.H mHandler = new android.service.notification.ConditionProviderService.H();
    boolean mIsConnected;
    private android.app.INotificationManager mNoMan;
    private android.service.notification.ConditionProviderService.Provider mProvider;

    public abstract void onConnected();

    public abstract void onSubscribe(android.net.Uri uri);

    public abstract void onUnsubscribe(android.net.Uri uri);

    public void onRequestConditions(int i) {
    }

    private final android.app.INotificationManager getNotificationInterface() {
        if (this.mNoMan == null) {
            this.mNoMan = android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification"));
        }
        return this.mNoMan;
    }

    public static final void requestRebind(android.content.ComponentName componentName) {
        try {
            android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification")).requestBindProvider(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void requestUnbind() {
        try {
            getNotificationInterface().requestUnbindProvider(this.mProvider);
            this.mIsConnected = false;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public final void notifyCondition(android.service.notification.Condition condition) {
        if (condition == null) {
            return;
        }
        notifyConditions(condition);
    }

    @java.lang.Deprecated
    public final void notifyConditions(android.service.notification.Condition... conditionArr) {
        if (!isBound() || conditionArr == null) {
            return;
        }
        try {
            getNotificationInterface().notifyConditions(getPackageName(), this.mProvider, conditionArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (this.mProvider == null) {
            this.mProvider = new android.service.notification.ConditionProviderService.Provider();
        }
        return this.mProvider;
    }

    public boolean isBound() {
        if (!this.mIsConnected) {
            android.util.Log.w(this.TAG, "Condition provider service not yet bound.");
        }
        return this.mIsConnected;
    }

    private final class Provider extends android.service.notification.IConditionProvider.Stub {
        private Provider() {
        }

        @Override // android.service.notification.IConditionProvider
        public void onConnected() {
            android.service.notification.ConditionProviderService.this.mIsConnected = true;
            android.service.notification.ConditionProviderService.this.mHandler.obtainMessage(1).sendToTarget();
        }

        @Override // android.service.notification.IConditionProvider
        public void onSubscribe(android.net.Uri uri) {
            android.service.notification.ConditionProviderService.this.mHandler.obtainMessage(3, uri).sendToTarget();
        }

        @Override // android.service.notification.IConditionProvider
        public void onUnsubscribe(android.net.Uri uri) {
            android.service.notification.ConditionProviderService.this.mHandler.obtainMessage(4, uri).sendToTarget();
        }
    }

    private final class H extends android.os.Handler {
        private static final int ON_CONNECTED = 1;
        private static final int ON_SUBSCRIBE = 3;
        private static final int ON_UNSUBSCRIBE = 4;

        private H() {
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (!android.service.notification.ConditionProviderService.this.mIsConnected) {
                return;
            }
            java.lang.String str = null;
            try {
                switch (message.what) {
                    case 1:
                        str = "onConnected";
                        android.service.notification.ConditionProviderService.this.onConnected();
                        break;
                    case 3:
                        str = "onSubscribe";
                        android.service.notification.ConditionProviderService.this.onSubscribe((android.net.Uri) message.obj);
                        break;
                    case 4:
                        str = "onUnsubscribe";
                        android.service.notification.ConditionProviderService.this.onUnsubscribe((android.net.Uri) message.obj);
                        break;
                }
            } catch (java.lang.Throwable th) {
                android.util.Log.w(android.service.notification.ConditionProviderService.this.TAG, "Error running " + str, th);
            }
        }
    }
}
