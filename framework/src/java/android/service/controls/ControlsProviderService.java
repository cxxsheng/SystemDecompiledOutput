package android.service.controls;

/* loaded from: classes3.dex */
public abstract class ControlsProviderService extends android.app.Service {
    public static final java.lang.String ACTION_ADD_CONTROL = "android.service.controls.action.ADD_CONTROL";
    public static final java.lang.String CALLBACK_BUNDLE = "CALLBACK_BUNDLE";
    public static final java.lang.String CALLBACK_TOKEN = "CALLBACK_TOKEN";
    public static final int CONTROLS_SURFACE_ACTIVITY_PANEL = 0;
    public static final int CONTROLS_SURFACE_DREAM = 1;
    public static final java.lang.String EXTRA_CONTROL = "android.service.controls.extra.CONTROL";
    public static final java.lang.String EXTRA_CONTROLS_SURFACE = "android.service.controls.extra.CONTROLS_SURFACE";
    public static final java.lang.String EXTRA_LOCKSCREEN_ALLOW_TRIVIAL_CONTROLS = "android.service.controls.extra.LOCKSCREEN_ALLOW_TRIVIAL_CONTROLS";
    public static final java.lang.String META_DATA_PANEL_ACTIVITY = "android.service.controls.META_DATA_PANEL_ACTIVITY";
    public static final java.lang.String SERVICE_CONTROLS = "android.service.controls.ControlsProviderService";
    public static final java.lang.String TAG = "ControlsProviderService";
    private android.service.controls.ControlsProviderService.RequestHandler mHandler;
    private android.os.IBinder mToken;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ControlsSurface {
    }

    public abstract java.util.concurrent.Flow.Publisher<android.service.controls.Control> createPublisherFor(java.util.List<java.lang.String> list);

    public abstract java.util.concurrent.Flow.Publisher<android.service.controls.Control> createPublisherForAllAvailable();

    public abstract void performControlAction(java.lang.String str, android.service.controls.actions.ControlAction controlAction, java.util.function.Consumer<java.lang.Integer> consumer);

    public java.util.concurrent.Flow.Publisher<android.service.controls.Control> createPublisherForSuggested() {
        return null;
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        this.mHandler = new android.service.controls.ControlsProviderService.RequestHandler(android.os.Looper.getMainLooper());
        this.mToken = intent.getBundleExtra(CALLBACK_BUNDLE).getBinder(CALLBACK_TOKEN);
        return new android.service.controls.IControlsProvider.Stub() { // from class: android.service.controls.ControlsProviderService.1
            @Override // android.service.controls.IControlsProvider
            public void load(android.service.controls.IControlsSubscriber iControlsSubscriber) {
                android.service.controls.ControlsProviderService.this.mHandler.obtainMessage(1, iControlsSubscriber).sendToTarget();
            }

            @Override // android.service.controls.IControlsProvider
            public void loadSuggested(android.service.controls.IControlsSubscriber iControlsSubscriber) {
                android.service.controls.ControlsProviderService.this.mHandler.obtainMessage(4, iControlsSubscriber).sendToTarget();
            }

            @Override // android.service.controls.IControlsProvider
            public void subscribe(java.util.List<java.lang.String> list, android.service.controls.IControlsSubscriber iControlsSubscriber) {
                android.service.controls.ControlsProviderService.this.mHandler.obtainMessage(2, new android.service.controls.ControlsProviderService.SubscribeMessage(list, iControlsSubscriber)).sendToTarget();
            }

            @Override // android.service.controls.IControlsProvider
            public void action(java.lang.String str, android.service.controls.actions.ControlActionWrapper controlActionWrapper, android.service.controls.IControlsActionCallback iControlsActionCallback) {
                android.service.controls.ControlsProviderService.this.mHandler.obtainMessage(3, new android.service.controls.ControlsProviderService.ActionMessage(str, controlActionWrapper.getWrappedAction(), iControlsActionCallback)).sendToTarget();
            }
        };
    }

    @Override // android.app.Service
    public final boolean onUnbind(android.content.Intent intent) {
        this.mHandler = null;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class RequestHandler extends android.os.Handler {
        private static final int MSG_ACTION = 3;
        private static final int MSG_LOAD = 1;
        private static final int MSG_LOAD_SUGGESTED = 4;
        private static final int MSG_SUBSCRIBE = 2;

        RequestHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.service.controls.ControlsProviderService.this.createPublisherForAllAvailable().subscribe(new android.service.controls.ControlsProviderService.SubscriberProxy(true, android.service.controls.ControlsProviderService.this.mToken, (android.service.controls.IControlsSubscriber) message.obj));
                    break;
                case 2:
                    android.service.controls.ControlsProviderService.SubscribeMessage subscribeMessage = (android.service.controls.ControlsProviderService.SubscribeMessage) message.obj;
                    android.service.controls.ControlsProviderService.this.createPublisherFor(subscribeMessage.mControlIds).subscribe(new android.service.controls.ControlsProviderService.SubscriberProxy(android.service.controls.ControlsProviderService.this, false, android.service.controls.ControlsProviderService.this.mToken, subscribeMessage.mSubscriber));
                    break;
                case 3:
                    android.service.controls.ControlsProviderService.ActionMessage actionMessage = (android.service.controls.ControlsProviderService.ActionMessage) message.obj;
                    android.service.controls.ControlsProviderService.this.performControlAction(actionMessage.mControlId, actionMessage.mAction, consumerFor(actionMessage.mControlId, actionMessage.mCb));
                    break;
                case 4:
                    android.service.controls.ControlsProviderService.SubscriberProxy subscriberProxy = new android.service.controls.ControlsProviderService.SubscriberProxy(true, android.service.controls.ControlsProviderService.this.mToken, (android.service.controls.IControlsSubscriber) message.obj);
                    java.util.concurrent.Flow.Publisher<android.service.controls.Control> createPublisherForSuggested = android.service.controls.ControlsProviderService.this.createPublisherForSuggested();
                    if (createPublisherForSuggested == null) {
                        android.util.Log.i(android.service.controls.ControlsProviderService.TAG, "No publisher provided for suggested controls");
                        subscriberProxy.onComplete();
                        break;
                    } else {
                        createPublisherForSuggested.subscribe(subscriberProxy);
                        break;
                    }
            }
        }

        private java.util.function.Consumer<java.lang.Integer> consumerFor(final java.lang.String str, final android.service.controls.IControlsActionCallback iControlsActionCallback) {
            return new java.util.function.Consumer() { // from class: android.service.controls.ControlsProviderService$RequestHandler$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.controls.ControlsProviderService.RequestHandler.this.lambda$consumerFor$0(iControlsActionCallback, str, (java.lang.Integer) obj);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$consumerFor$0(android.service.controls.IControlsActionCallback iControlsActionCallback, java.lang.String str, java.lang.Integer num) {
            com.android.internal.util.Preconditions.checkNotNull(num);
            if (!android.service.controls.actions.ControlAction.isValidResponse(num.intValue())) {
                android.util.Log.e(android.service.controls.ControlsProviderService.TAG, "Not valid response result: " + num);
                num = 0;
            }
            try {
                iControlsActionCallback.accept(android.service.controls.ControlsProviderService.this.mToken, str, num.intValue());
            } catch (android.os.RemoteException e) {
                e.rethrowAsRuntimeException();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isStatelessControl(android.service.controls.Control control) {
        return control.getStatus() == 0 && control.getControlTemplate().getTemplateType() == 0 && android.text.TextUtils.isEmpty(control.getStatusText());
    }

    private static class SubscriberProxy implements java.util.concurrent.Flow.Subscriber<android.service.controls.Control> {
        private android.content.Context mContext;
        private android.service.controls.IControlsSubscriber mCs;
        private boolean mEnforceStateless;
        private android.service.controls.ControlsProviderService.SubscriptionAdapter mSubscription;
        private android.os.IBinder mToken;

        SubscriberProxy(boolean z, android.os.IBinder iBinder, android.service.controls.IControlsSubscriber iControlsSubscriber) {
            this.mEnforceStateless = z;
            this.mToken = iBinder;
            this.mCs = iControlsSubscriber;
        }

        SubscriberProxy(android.content.Context context, boolean z, android.os.IBinder iBinder, android.service.controls.IControlsSubscriber iControlsSubscriber) {
            this(z, iBinder, iControlsSubscriber);
            this.mContext = context;
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onSubscribe(java.util.concurrent.Flow.Subscription subscription) {
            try {
                android.service.controls.ControlsProviderService.SubscriptionAdapter subscriptionAdapter = new android.service.controls.ControlsProviderService.SubscriptionAdapter(subscription);
                this.mCs.onSubscribe(this.mToken, subscriptionAdapter);
                this.mSubscription = subscriptionAdapter;
            } catch (android.os.RemoteException e) {
                handleRemoteException(e);
            }
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onNext(android.service.controls.Control control) {
            com.android.internal.util.Preconditions.checkNotNull(control);
            try {
                if (this.mEnforceStateless && !android.service.controls.ControlsProviderService.isStatelessControl(control)) {
                    android.util.Log.w(android.service.controls.ControlsProviderService.TAG, "onNext(): control is not stateless. Use the Control.StatelessBuilder() to build the control.");
                    control = new android.service.controls.Control.StatelessBuilder(control).build();
                }
                if (this.mContext != null) {
                    control.getControlTemplate().prepareTemplateForBinder(this.mContext);
                }
                this.mCs.onNext(this.mToken, control);
            } catch (android.os.RemoteException e) {
                handleRemoteException(e);
            }
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onError(java.lang.Throwable th) {
            try {
                this.mCs.onError(this.mToken, th.toString());
                this.mSubscription = null;
            } catch (android.os.RemoteException e) {
                handleRemoteException(e);
            }
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onComplete() {
            try {
                this.mCs.onComplete(this.mToken);
                this.mSubscription = null;
            } catch (android.os.RemoteException e) {
                handleRemoteException(e);
            }
        }

        private void handleRemoteException(android.os.RemoteException remoteException) {
            if (remoteException instanceof android.os.DeadObjectException) {
                android.service.controls.ControlsProviderService.SubscriptionAdapter subscriptionAdapter = this.mSubscription;
                if (subscriptionAdapter != null) {
                    subscriptionAdapter.cancel();
                    return;
                }
                return;
            }
            remoteException.rethrowAsRuntimeException();
        }
    }

    public static void requestAddControl(android.content.Context context, android.content.ComponentName componentName, android.service.controls.Control control) {
        com.android.internal.util.Preconditions.checkNotNull(context);
        com.android.internal.util.Preconditions.checkNotNull(componentName);
        com.android.internal.util.Preconditions.checkNotNull(control);
        java.lang.String string = context.getString(com.android.internal.R.string.config_controlsPackage);
        android.content.Intent intent = new android.content.Intent(ACTION_ADD_CONTROL);
        intent.putExtra(android.content.Intent.EXTRA_COMPONENT_NAME, componentName);
        intent.setPackage(string);
        if (isStatelessControl(control)) {
            intent.putExtra(EXTRA_CONTROL, control);
        } else {
            intent.putExtra(EXTRA_CONTROL, new android.service.controls.Control.StatelessBuilder(control).build());
        }
        context.sendBroadcast(intent, android.Manifest.permission.BIND_CONTROLS);
    }

    private static class SubscriptionAdapter extends android.service.controls.IControlsSubscription.Stub {
        final java.util.concurrent.Flow.Subscription mSubscription;

        SubscriptionAdapter(java.util.concurrent.Flow.Subscription subscription) {
            this.mSubscription = subscription;
        }

        @Override // android.service.controls.IControlsSubscription
        public void request(long j) {
            this.mSubscription.request(j);
        }

        @Override // android.service.controls.IControlsSubscription
        public void cancel() {
            this.mSubscription.cancel();
        }
    }

    private static class ActionMessage {
        final android.service.controls.actions.ControlAction mAction;
        final android.service.controls.IControlsActionCallback mCb;
        final java.lang.String mControlId;

        ActionMessage(java.lang.String str, android.service.controls.actions.ControlAction controlAction, android.service.controls.IControlsActionCallback iControlsActionCallback) {
            this.mControlId = str;
            this.mAction = controlAction;
            this.mCb = iControlsActionCallback;
        }
    }

    private static class SubscribeMessage {
        final java.util.List<java.lang.String> mControlIds;
        final android.service.controls.IControlsSubscriber mSubscriber;

        SubscribeMessage(java.util.List<java.lang.String> list, android.service.controls.IControlsSubscriber iControlsSubscriber) {
            this.mControlIds = list;
            this.mSubscriber = iControlsSubscriber;
        }
    }
}
