package android.service.contentcapture;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class ContentCaptureService extends android.app.Service {
    public static final java.lang.String PROTECTION_SERVICE_INTERFACE = "android.service.contentcapture.ContentProtectionService";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.contentcapture.ContentCaptureService";
    public static final java.lang.String SERVICE_META_DATA = "android.content_capture";
    private static final java.lang.String TAG = android.service.contentcapture.ContentCaptureService.class.getSimpleName();
    private android.service.contentcapture.IContentCaptureServiceCallback mContentCaptureServiceCallback;
    private android.service.contentcapture.IContentProtectionAllowlistCallback mContentProtectionAllowlistCallback;
    private android.os.Handler mHandler;
    private long mLastCallerMismatchLog;
    private final android.service.contentcapture.ContentCaptureService.LocalDataShareAdapterResourceManager mDataShareAdapterResourceManager = new android.service.contentcapture.ContentCaptureService.LocalDataShareAdapterResourceManager();
    private long mCallerMismatchTimeout = 1000;
    private final android.service.contentcapture.IContentCaptureService mContentCaptureServerInterface = new android.service.contentcapture.ContentCaptureService.AnonymousClass1();
    private final android.service.contentcapture.IContentProtectionService mContentProtectionServerInterface = new android.service.contentcapture.ContentCaptureService.AnonymousClass2();
    private final android.view.contentcapture.IContentCaptureDirectManager mContentCaptureClientInterface = new android.service.contentcapture.ContentCaptureService.AnonymousClass3();
    private final android.util.SparseIntArray mSessionUids = new android.util.SparseIntArray();

    /* renamed from: android.service.contentcapture.ContentCaptureService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.contentcapture.IContentCaptureService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onConnected(android.os.IBinder iBinder, boolean z, boolean z2) {
            android.view.contentcapture.ContentCaptureHelper.sVerbose = z;
            android.view.contentcapture.ContentCaptureHelper.sDebug = z2;
            android.service.contentcapture.ContentCaptureService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.contentcapture.ContentCaptureService) obj).handleOnConnected((android.os.IBinder) obj2);
                }
            }, android.service.contentcapture.ContentCaptureService.this, iBinder));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDisconnected() {
            android.service.contentcapture.ContentCaptureService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.contentcapture.ContentCaptureService) obj).handleOnDisconnected();
                }
            }, android.service.contentcapture.ContentCaptureService.this));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onSessionStarted(android.view.contentcapture.ContentCaptureContext contentCaptureContext, int i, int i2, com.android.internal.os.IResultReceiver iResultReceiver, int i3) {
            android.service.contentcapture.ContentCaptureService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HexConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda6
                @Override // com.android.internal.util.function.HexConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                    ((android.service.contentcapture.ContentCaptureService) obj).handleOnCreateSession((android.view.contentcapture.ContentCaptureContext) obj2, ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), (com.android.internal.os.IResultReceiver) obj5, ((java.lang.Integer) obj6).intValue());
                }
            }, android.service.contentcapture.ContentCaptureService.this, contentCaptureContext, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), iResultReceiver, java.lang.Integer.valueOf(i3)));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onActivitySnapshot(int i, android.service.contentcapture.SnapshotData snapshotData) {
            android.service.contentcapture.ContentCaptureService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.contentcapture.ContentCaptureService) obj).handleOnActivitySnapshot(((java.lang.Integer) obj2).intValue(), (android.service.contentcapture.SnapshotData) obj3);
                }
            }, android.service.contentcapture.ContentCaptureService.this, java.lang.Integer.valueOf(i), snapshotData));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onSessionFinished(int i) {
            android.service.contentcapture.ContentCaptureService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.contentcapture.ContentCaptureService) obj).handleFinishSession(((java.lang.Integer) obj2).intValue());
                }
            }, android.service.contentcapture.ContentCaptureService.this, java.lang.Integer.valueOf(i)));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDataRemovalRequest(android.view.contentcapture.DataRemovalRequest dataRemovalRequest) {
            android.service.contentcapture.ContentCaptureService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.contentcapture.ContentCaptureService) obj).handleOnDataRemovalRequest((android.view.contentcapture.DataRemovalRequest) obj2);
                }
            }, android.service.contentcapture.ContentCaptureService.this, dataRemovalRequest));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDataShared(android.view.contentcapture.DataShareRequest dataShareRequest, android.service.contentcapture.IDataShareCallback iDataShareCallback) {
            android.service.contentcapture.ContentCaptureService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.contentcapture.ContentCaptureService) obj).handleOnDataShared((android.view.contentcapture.DataShareRequest) obj2, (android.service.contentcapture.IDataShareCallback) obj3);
                }
            }, android.service.contentcapture.ContentCaptureService.this, dataShareRequest, iDataShareCallback));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onActivityEvent(android.service.contentcapture.ActivityEvent activityEvent) {
            android.service.contentcapture.ContentCaptureService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.contentcapture.ContentCaptureService) obj).handleOnActivityEvent((android.service.contentcapture.ActivityEvent) obj2);
                }
            }, android.service.contentcapture.ContentCaptureService.this, activityEvent));
        }
    }

    /* renamed from: android.service.contentcapture.ContentCaptureService$2, reason: invalid class name */
    class AnonymousClass2 extends android.service.contentcapture.IContentProtectionService.Stub {
        AnonymousClass2() {
        }

        @Override // android.service.contentcapture.IContentProtectionService
        public void onLoginDetected(android.content.pm.ParceledListSlice parceledListSlice) {
            android.service.contentcapture.ContentCaptureService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.contentcapture.ContentCaptureService$2$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.contentcapture.ContentCaptureService) obj).handleOnLoginDetected(((java.lang.Integer) obj2).intValue(), (android.content.pm.ParceledListSlice) obj3);
                }
            }, android.service.contentcapture.ContentCaptureService.this, java.lang.Integer.valueOf(android.os.Binder.getCallingUid()), parceledListSlice));
        }

        @Override // android.service.contentcapture.IContentProtectionService
        public void onUpdateAllowlistRequest(android.os.IBinder iBinder) {
            android.service.contentcapture.ContentCaptureService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.contentcapture.ContentCaptureService$2$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.contentcapture.ContentCaptureService) obj).handleOnUpdateAllowlistRequest(((java.lang.Integer) obj2).intValue(), (android.os.IBinder) obj3);
                }
            }, android.service.contentcapture.ContentCaptureService.this, java.lang.Integer.valueOf(android.os.Binder.getCallingUid()), iBinder));
        }
    }

    /* renamed from: android.service.contentcapture.ContentCaptureService$3, reason: invalid class name */
    class AnonymousClass3 extends android.view.contentcapture.IContentCaptureDirectManager.Stub {
        AnonymousClass3() {
        }

        @Override // android.view.contentcapture.IContentCaptureDirectManager
        public void sendEvents(android.content.pm.ParceledListSlice parceledListSlice, int i, android.content.ContentCaptureOptions contentCaptureOptions) {
            android.service.contentcapture.ContentCaptureService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: android.service.contentcapture.ContentCaptureService$3$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((android.service.contentcapture.ContentCaptureService) obj).handleSendEvents(((java.lang.Integer) obj2).intValue(), (android.content.pm.ParceledListSlice) obj3, ((java.lang.Integer) obj4).intValue(), (android.content.ContentCaptureOptions) obj5);
                }
            }, android.service.contentcapture.ContentCaptureService.this, java.lang.Integer.valueOf(android.os.Binder.getCallingUid()), parceledListSlice, java.lang.Integer.valueOf(i), contentCaptureOptions));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mContentCaptureServerInterface.asBinder();
        }
        if (PROTECTION_SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mContentProtectionServerInterface.asBinder();
        }
        android.util.Log.w(TAG, "Tried to bind to wrong intent (should be android.service.contentcapture.ContentCaptureService or android.service.contentcapture.ContentProtectionService): " + intent);
        return null;
    }

    public final void setContentCaptureWhitelist(java.util.Set<java.lang.String> set, java.util.Set<android.content.ComponentName> set2) {
        android.service.contentcapture.IContentCaptureServiceCallback iContentCaptureServiceCallback = this.mContentCaptureServiceCallback;
        android.service.contentcapture.IContentProtectionAllowlistCallback iContentProtectionAllowlistCallback = this.mContentProtectionAllowlistCallback;
        if (iContentCaptureServiceCallback == null && iContentProtectionAllowlistCallback == null) {
            android.util.Log.w(TAG, "setContentCaptureWhitelist(): missing both server callbacks");
            return;
        }
        if (iContentCaptureServiceCallback != null) {
            if (iContentProtectionAllowlistCallback != null) {
                throw new java.lang.IllegalStateException("Have both server callbacks");
            }
            try {
                iContentCaptureServiceCallback.setContentCaptureWhitelist(android.view.contentcapture.ContentCaptureHelper.toList(set), android.view.contentcapture.ContentCaptureHelper.toList(set2));
                return;
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        try {
            iContentProtectionAllowlistCallback.setAllowlist(android.view.contentcapture.ContentCaptureHelper.toList(set));
        } catch (android.os.RemoteException e2) {
            e2.rethrowFromSystemServer();
        }
    }

    public final void setContentCaptureConditions(java.lang.String str, java.util.Set<android.view.contentcapture.ContentCaptureCondition> set) {
        android.service.contentcapture.IContentCaptureServiceCallback iContentCaptureServiceCallback = this.mContentCaptureServiceCallback;
        if (iContentCaptureServiceCallback == null) {
            android.util.Log.w(TAG, "setContentCaptureConditions(): no server callback");
            return;
        }
        try {
            iContentCaptureServiceCallback.setContentCaptureConditions(str, android.view.contentcapture.ContentCaptureHelper.toList(set));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void onConnected() {
        android.util.Slog.i(TAG, "bound to " + getClass().getName());
    }

    public void onCreateContentCaptureSession(android.view.contentcapture.ContentCaptureContext contentCaptureContext, android.view.contentcapture.ContentCaptureSessionId contentCaptureSessionId) {
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "onCreateContentCaptureSession(id=" + contentCaptureSessionId + ", ctx=" + contentCaptureContext + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    /* renamed from: onContentCaptureEvent, reason: merged with bridge method [inline-methods] */
    public void lambda$handleOnLoginDetected$0(android.view.contentcapture.ContentCaptureSessionId contentCaptureSessionId, android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "onContentCaptureEventsRequest(id=" + contentCaptureSessionId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public void onDataRemovalRequest(android.view.contentcapture.DataRemovalRequest dataRemovalRequest) {
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "onDataRemovalRequest()");
        }
    }

    @android.annotation.SystemApi
    public void onDataShareRequest(android.view.contentcapture.DataShareRequest dataShareRequest, android.service.contentcapture.DataShareCallback dataShareCallback) {
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "onDataShareRequest()");
        }
    }

    public void onActivitySnapshot(android.view.contentcapture.ContentCaptureSessionId contentCaptureSessionId, android.service.contentcapture.SnapshotData snapshotData) {
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "onActivitySnapshot(id=" + contentCaptureSessionId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public void onActivityEvent(android.service.contentcapture.ActivityEvent activityEvent) {
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "onActivityEvent(): " + activityEvent);
        }
    }

    public void onDestroyContentCaptureSession(android.view.contentcapture.ContentCaptureSessionId contentCaptureSessionId) {
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "onDestroyContentCaptureSession(id=" + contentCaptureSessionId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public final void disableSelf() {
        if (android.view.contentcapture.ContentCaptureHelper.sDebug) {
            android.util.Log.d(TAG, "disableSelf()");
        }
        android.service.contentcapture.IContentCaptureServiceCallback iContentCaptureServiceCallback = this.mContentCaptureServiceCallback;
        if (iContentCaptureServiceCallback == null) {
            android.util.Log.w(TAG, "disableSelf(): no server callback");
            return;
        }
        try {
            iContentCaptureServiceCallback.disableSelf();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void onDisconnected() {
        android.util.Slog.i(TAG, "unbinding from " + getClass().getName());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.print("Debug: ");
        printWriter.print(android.view.contentcapture.ContentCaptureHelper.sDebug);
        printWriter.print(" Verbose: ");
        printWriter.println(android.view.contentcapture.ContentCaptureHelper.sVerbose);
        int size = this.mSessionUids.size();
        printWriter.print("Number sessions: ");
        printWriter.println(size);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                printWriter.print("  ");
                printWriter.print(this.mSessionUids.keyAt(i));
                printWriter.print(": uid=");
                printWriter.println(this.mSessionUids.valueAt(i));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnConnected(android.os.IBinder iBinder) {
        this.mContentCaptureServiceCallback = android.service.contentcapture.IContentCaptureServiceCallback.Stub.asInterface(iBinder);
        onConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDisconnected() {
        onDisconnected();
        this.mContentCaptureServiceCallback = null;
        this.mContentProtectionAllowlistCallback = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnCreateSession(android.view.contentcapture.ContentCaptureContext contentCaptureContext, int i, int i2, com.android.internal.os.IResultReceiver iResultReceiver, int i3) {
        int i4;
        this.mSessionUids.put(i, i2);
        onCreateContentCaptureSession(contentCaptureContext, new android.view.contentcapture.ContentCaptureSessionId(i));
        int flags = contentCaptureContext.getFlags();
        if ((flags & 2) == 0) {
            i4 = 0;
        } else {
            i4 = 32;
        }
        if ((flags & 1) != 0) {
            i4 |= 64;
        }
        if (i4 != 0) {
            i3 = i4 | 4;
        }
        setClientState(iResultReceiver, i3, this.mContentCaptureClientInterface.asBinder());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSendEvents(int i, android.content.pm.ParceledListSlice<android.view.contentcapture.ContentCaptureEvent> parceledListSlice, int i2, android.content.ContentCaptureOptions contentCaptureOptions) {
        java.util.List list = parceledListSlice.getList();
        if (list.isEmpty()) {
            android.util.Log.w(TAG, "handleSendEvents() received empty list of events");
            return;
        }
        android.service.contentcapture.FlushMetrics flushMetrics = new android.service.contentcapture.FlushMetrics();
        android.content.ComponentName componentName = null;
        android.view.contentcapture.ContentCaptureSessionId contentCaptureSessionId = null;
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            android.view.contentcapture.ContentCaptureEvent contentCaptureEvent = (android.view.contentcapture.ContentCaptureEvent) list.get(i4);
            if (handleIsRightCallerFor(contentCaptureEvent, i)) {
                int sessionId = contentCaptureEvent.getSessionId();
                if (sessionId != i3) {
                    android.view.contentcapture.ContentCaptureSessionId contentCaptureSessionId2 = new android.view.contentcapture.ContentCaptureSessionId(sessionId);
                    if (i4 != 0) {
                        writeFlushMetrics(sessionId, componentName, flushMetrics, contentCaptureOptions, i2);
                        flushMetrics.reset();
                    }
                    i3 = sessionId;
                    contentCaptureSessionId = contentCaptureSessionId2;
                }
                android.view.contentcapture.ContentCaptureContext contentCaptureContext = contentCaptureEvent.getContentCaptureContext();
                if (componentName == null && contentCaptureContext != null) {
                    componentName = contentCaptureContext.getActivityComponent();
                }
                switch (contentCaptureEvent.getType()) {
                    case -2:
                        this.mSessionUids.delete(sessionId);
                        onDestroyContentCaptureSession(contentCaptureSessionId);
                        flushMetrics.sessionFinished++;
                        break;
                    case -1:
                        contentCaptureContext.setParentSessionId(contentCaptureEvent.getParentSessionId());
                        this.mSessionUids.put(sessionId, i);
                        onCreateContentCaptureSession(contentCaptureContext, contentCaptureSessionId);
                        flushMetrics.sessionStarted++;
                        break;
                    case 0:
                    default:
                        lambda$handleOnLoginDetected$0(contentCaptureSessionId, contentCaptureEvent);
                        break;
                    case 1:
                        lambda$handleOnLoginDetected$0(contentCaptureSessionId, contentCaptureEvent);
                        flushMetrics.viewAppearedCount++;
                        break;
                    case 2:
                        lambda$handleOnLoginDetected$0(contentCaptureSessionId, contentCaptureEvent);
                        flushMetrics.viewDisappearedCount++;
                        break;
                    case 3:
                        lambda$handleOnLoginDetected$0(contentCaptureSessionId, contentCaptureEvent);
                        flushMetrics.viewTextChangedCount++;
                        break;
                }
            }
        }
        writeFlushMetrics(i3, componentName, flushMetrics, contentCaptureOptions, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnLoginDetected(int i, android.content.pm.ParceledListSlice<android.view.contentcapture.ContentCaptureEvent> parceledListSlice) {
        if (i != 1000) {
            android.util.Log.e(TAG, "handleOnLoginDetected() not allowed for uid: " + i);
            return;
        }
        java.util.List list = parceledListSlice.getList();
        int sessionId = list.isEmpty() ? 0 : ((android.view.contentcapture.ContentCaptureEvent) list.get(0)).getSessionId();
        final android.view.contentcapture.ContentCaptureSessionId contentCaptureSessionId = new android.view.contentcapture.ContentCaptureSessionId(sessionId);
        android.view.contentcapture.ContentCaptureEvent contentCaptureEvent = new android.view.contentcapture.ContentCaptureEvent(sessionId, 7);
        contentCaptureEvent.setSelectionIndex(0, list.size());
        lambda$handleOnLoginDetected$0(contentCaptureSessionId, contentCaptureEvent);
        list.forEach(new java.util.function.Consumer() { // from class: android.service.contentcapture.ContentCaptureService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.service.contentcapture.ContentCaptureService.this.lambda$handleOnLoginDetected$0(contentCaptureSessionId, (android.view.contentcapture.ContentCaptureEvent) obj);
            }
        });
        lambda$handleOnLoginDetected$0(contentCaptureSessionId, new android.view.contentcapture.ContentCaptureEvent(sessionId, 8));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnUpdateAllowlistRequest(int i, android.os.IBinder iBinder) {
        if (i != 1000) {
            android.util.Log.e(TAG, "handleOnUpdateAllowlistRequest() not allowed for uid: " + i);
        } else {
            this.mContentProtectionAllowlistCallback = android.service.contentcapture.IContentProtectionAllowlistCallback.Stub.asInterface(iBinder);
            onConnected();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnActivitySnapshot(int i, android.service.contentcapture.SnapshotData snapshotData) {
        onActivitySnapshot(new android.view.contentcapture.ContentCaptureSessionId(i), snapshotData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFinishSession(int i) {
        this.mSessionUids.delete(i);
        onDestroyContentCaptureSession(new android.view.contentcapture.ContentCaptureSessionId(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDataRemovalRequest(android.view.contentcapture.DataRemovalRequest dataRemovalRequest) {
        onDataRemovalRequest(dataRemovalRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDataShared(android.view.contentcapture.DataShareRequest dataShareRequest, final android.service.contentcapture.IDataShareCallback iDataShareCallback) {
        onDataShareRequest(dataShareRequest, new android.service.contentcapture.DataShareCallback() { // from class: android.service.contentcapture.ContentCaptureService.4
            @Override // android.service.contentcapture.DataShareCallback
            public void onAccept(java.util.concurrent.Executor executor, android.service.contentcapture.DataShareReadAdapter dataShareReadAdapter) {
                java.util.Objects.requireNonNull(dataShareReadAdapter);
                java.util.Objects.requireNonNull(executor);
                try {
                    iDataShareCallback.accept(new android.service.contentcapture.ContentCaptureService.DataShareReadAdapterDelegate(executor, dataShareReadAdapter, android.service.contentcapture.ContentCaptureService.this.mDataShareAdapterResourceManager));
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.contentcapture.ContentCaptureService.TAG, "Failed to accept data sharing", e);
                }
            }

            @Override // android.service.contentcapture.DataShareCallback
            public void onReject() {
                try {
                    iDataShareCallback.reject();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.contentcapture.ContentCaptureService.TAG, "Failed to reject data sharing", e);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnActivityEvent(android.service.contentcapture.ActivityEvent activityEvent) {
        onActivityEvent(activityEvent);
    }

    private boolean handleIsRightCallerFor(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent, int i) {
        int parentSessionId;
        switch (contentCaptureEvent.getType()) {
            case -2:
            case -1:
                parentSessionId = contentCaptureEvent.getParentSessionId();
                break;
            default:
                parentSessionId = contentCaptureEvent.getSessionId();
                break;
        }
        if (this.mSessionUids.indexOfKey(parentSessionId) < 0) {
            if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                android.util.Log.v(TAG, "handleIsRightCallerFor(" + contentCaptureEvent + "): no session for " + parentSessionId + ": " + this.mSessionUids);
            }
            return false;
        }
        int i2 = this.mSessionUids.get(parentSessionId);
        if (i2 != i) {
            android.util.Log.e(TAG, "invalid call from UID " + i + ": session " + parentSessionId + " belongs to " + i2);
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            if (currentTimeMillis - this.mLastCallerMismatchLog > this.mCallerMismatchTimeout) {
                com.android.internal.util.FrameworkStatsLog.write(206, getPackageManager().getNameForUid(i2), getPackageManager().getNameForUid(i));
                this.mLastCallerMismatchLog = currentTimeMillis;
            }
            return false;
        }
        return true;
    }

    public static void setClientState(com.android.internal.os.IResultReceiver iResultReceiver, int i, android.os.IBinder iBinder) {
        android.os.Bundle bundle;
        if (iBinder != null) {
            try {
                bundle = new android.os.Bundle();
                bundle.putBinder("binder", iBinder);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Error async reporting result to client: " + e);
                return;
            }
        } else {
            bundle = null;
        }
        iResultReceiver.send(i, bundle);
    }

    private void writeFlushMetrics(int i, android.content.ComponentName componentName, android.service.contentcapture.FlushMetrics flushMetrics, android.content.ContentCaptureOptions contentCaptureOptions, int i2) {
        if (this.mContentCaptureServiceCallback == null) {
            android.util.Log.w(TAG, "writeSessionFlush(): no server callback");
            return;
        }
        try {
            this.mContentCaptureServiceCallback.writeSessionFlush(i, componentName, flushMetrics, contentCaptureOptions, i2);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to write flush metrics: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DataShareReadAdapterDelegate extends android.service.contentcapture.IDataShareReadAdapter.Stub {
        private final java.lang.Object mLock = new java.lang.Object();
        private final java.lang.ref.WeakReference<android.service.contentcapture.ContentCaptureService.LocalDataShareAdapterResourceManager> mResourceManagerReference;

        DataShareReadAdapterDelegate(java.util.concurrent.Executor executor, android.service.contentcapture.DataShareReadAdapter dataShareReadAdapter, android.service.contentcapture.ContentCaptureService.LocalDataShareAdapterResourceManager localDataShareAdapterResourceManager) {
            java.util.Objects.requireNonNull(executor);
            java.util.Objects.requireNonNull(dataShareReadAdapter);
            java.util.Objects.requireNonNull(localDataShareAdapterResourceManager);
            localDataShareAdapterResourceManager.initializeForDelegate(this, dataShareReadAdapter, executor);
            this.mResourceManagerReference = new java.lang.ref.WeakReference<>(localDataShareAdapterResourceManager);
        }

        @Override // android.service.contentcapture.IDataShareReadAdapter
        public void start(final android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
            synchronized (this.mLock) {
                executeAdapterMethodLocked(new java.util.function.Consumer() { // from class: android.service.contentcapture.ContentCaptureService$DataShareReadAdapterDelegate$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((android.service.contentcapture.DataShareReadAdapter) obj).onStart(android.os.ParcelFileDescriptor.this);
                    }
                }, "onStart");
            }
        }

        @Override // android.service.contentcapture.IDataShareReadAdapter
        public void error(final int i) throws android.os.RemoteException {
            synchronized (this.mLock) {
                executeAdapterMethodLocked(new java.util.function.Consumer() { // from class: android.service.contentcapture.ContentCaptureService$DataShareReadAdapterDelegate$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((android.service.contentcapture.DataShareReadAdapter) obj).onError(i);
                    }
                }, "onError");
                clearHardReferences();
            }
        }

        @Override // android.service.contentcapture.IDataShareReadAdapter
        public void finish() throws android.os.RemoteException {
            synchronized (this.mLock) {
                clearHardReferences();
            }
        }

        private void executeAdapterMethodLocked(final java.util.function.Consumer<android.service.contentcapture.DataShareReadAdapter> consumer, java.lang.String str) {
            android.service.contentcapture.ContentCaptureService.LocalDataShareAdapterResourceManager localDataShareAdapterResourceManager = this.mResourceManagerReference.get();
            if (localDataShareAdapterResourceManager == null) {
                android.util.Slog.w(android.service.contentcapture.ContentCaptureService.TAG, "Can't execute " + str + "(), resource manager has been GC'ed");
                return;
            }
            final android.service.contentcapture.DataShareReadAdapter adapter = localDataShareAdapterResourceManager.getAdapter(this);
            java.util.concurrent.Executor executor = localDataShareAdapterResourceManager.getExecutor(this);
            if (adapter == null || executor == null) {
                android.util.Slog.w(android.service.contentcapture.ContentCaptureService.TAG, "Can't execute " + str + "(), references are null");
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                executor.execute(new java.lang.Runnable() { // from class: android.service.contentcapture.ContentCaptureService$DataShareReadAdapterDelegate$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(adapter);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private void clearHardReferences() {
            android.service.contentcapture.ContentCaptureService.LocalDataShareAdapterResourceManager localDataShareAdapterResourceManager = this.mResourceManagerReference.get();
            if (localDataShareAdapterResourceManager == null) {
                android.util.Slog.w(android.service.contentcapture.ContentCaptureService.TAG, "Can't clear references, resource manager has been GC'ed");
            } else {
                localDataShareAdapterResourceManager.clearHardReferences(this);
            }
        }
    }

    private static class LocalDataShareAdapterResourceManager {
        private java.util.Map<android.service.contentcapture.ContentCaptureService.DataShareReadAdapterDelegate, android.service.contentcapture.DataShareReadAdapter> mDataShareReadAdapterHardReferences;
        private java.util.Map<android.service.contentcapture.ContentCaptureService.DataShareReadAdapterDelegate, java.util.concurrent.Executor> mExecutorHardReferences;

        private LocalDataShareAdapterResourceManager() {
            this.mDataShareReadAdapterHardReferences = new java.util.HashMap();
            this.mExecutorHardReferences = new java.util.HashMap();
        }

        void initializeForDelegate(android.service.contentcapture.ContentCaptureService.DataShareReadAdapterDelegate dataShareReadAdapterDelegate, android.service.contentcapture.DataShareReadAdapter dataShareReadAdapter, java.util.concurrent.Executor executor) {
            this.mDataShareReadAdapterHardReferences.put(dataShareReadAdapterDelegate, dataShareReadAdapter);
            this.mExecutorHardReferences.put(dataShareReadAdapterDelegate, executor);
        }

        java.util.concurrent.Executor getExecutor(android.service.contentcapture.ContentCaptureService.DataShareReadAdapterDelegate dataShareReadAdapterDelegate) {
            return this.mExecutorHardReferences.get(dataShareReadAdapterDelegate);
        }

        android.service.contentcapture.DataShareReadAdapter getAdapter(android.service.contentcapture.ContentCaptureService.DataShareReadAdapterDelegate dataShareReadAdapterDelegate) {
            return this.mDataShareReadAdapterHardReferences.get(dataShareReadAdapterDelegate);
        }

        void clearHardReferences(android.service.contentcapture.ContentCaptureService.DataShareReadAdapterDelegate dataShareReadAdapterDelegate) {
            this.mDataShareReadAdapterHardReferences.remove(dataShareReadAdapterDelegate);
            this.mExecutorHardReferences.remove(dataShareReadAdapterDelegate);
        }
    }
}
