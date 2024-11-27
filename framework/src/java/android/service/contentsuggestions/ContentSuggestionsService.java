package android.service.contentsuggestions;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class ContentSuggestionsService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.service.contentsuggestions.ContentSuggestionsService";
    private static final java.lang.String TAG = android.service.contentsuggestions.ContentSuggestionsService.class.getSimpleName();
    private android.os.Handler mHandler;
    private final android.service.contentsuggestions.IContentSuggestionsService mInterface = new android.service.contentsuggestions.IContentSuggestionsService.Stub() { // from class: android.service.contentsuggestions.ContentSuggestionsService.1
        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void provideContextImage(int i, android.hardware.HardwareBuffer hardwareBuffer, int i2, android.os.Bundle bundle) {
            android.graphics.Bitmap bitmap;
            if (bundle.containsKey(android.app.contentsuggestions.ContentSuggestionsManager.EXTRA_BITMAP) && hardwareBuffer != null) {
                throw new java.lang.IllegalArgumentException("Two bitmaps provided; expected one.");
            }
            if (bundle.containsKey(android.app.contentsuggestions.ContentSuggestionsManager.EXTRA_BITMAP)) {
                bitmap = (android.graphics.Bitmap) bundle.getParcelable(android.app.contentsuggestions.ContentSuggestionsManager.EXTRA_BITMAP, android.graphics.Bitmap.class);
            } else {
                android.graphics.ColorSpace colorSpace = null;
                if (hardwareBuffer == null) {
                    bitmap = null;
                } else {
                    if (i2 >= 0 && i2 < android.graphics.ColorSpace.Named.values().length) {
                        colorSpace = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.values()[i2]);
                    }
                    android.graphics.Bitmap wrapHardwareBuffer = android.graphics.Bitmap.wrapHardwareBuffer(hardwareBuffer, colorSpace);
                    hardwareBuffer.close();
                    bitmap = wrapHardwareBuffer;
                }
            }
            android.service.contentsuggestions.ContentSuggestionsService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.service.contentsuggestions.ContentSuggestionsService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.service.contentsuggestions.ContentSuggestionsService) obj).onProcessContextImage(((java.lang.Integer) obj2).intValue(), (android.graphics.Bitmap) obj3, (android.os.Bundle) obj4);
                }
            }, android.service.contentsuggestions.ContentSuggestionsService.this, java.lang.Integer.valueOf(i), bitmap, bundle));
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void suggestContentSelections(android.app.contentsuggestions.SelectionsRequest selectionsRequest, android.app.contentsuggestions.ISelectionsCallback iSelectionsCallback) {
            android.service.contentsuggestions.ContentSuggestionsService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.contentsuggestions.ContentSuggestionsService$1$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.contentsuggestions.ContentSuggestionsService) obj).onSuggestContentSelections((android.app.contentsuggestions.SelectionsRequest) obj2, (android.app.contentsuggestions.ContentSuggestionsManager.SelectionsCallback) obj3);
                }
            }, android.service.contentsuggestions.ContentSuggestionsService.this, selectionsRequest, android.service.contentsuggestions.ContentSuggestionsService.this.wrapSelectionsCallback(iSelectionsCallback)));
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void classifyContentSelections(android.app.contentsuggestions.ClassificationsRequest classificationsRequest, android.app.contentsuggestions.IClassificationsCallback iClassificationsCallback) {
            android.service.contentsuggestions.ContentSuggestionsService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.contentsuggestions.ContentSuggestionsService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.contentsuggestions.ContentSuggestionsService) obj).onClassifyContentSelections((android.app.contentsuggestions.ClassificationsRequest) obj2, (android.app.contentsuggestions.ContentSuggestionsManager.ClassificationsCallback) obj3);
                }
            }, android.service.contentsuggestions.ContentSuggestionsService.this, classificationsRequest, android.service.contentsuggestions.ContentSuggestionsService.this.wrapClassificationCallback(iClassificationsCallback)));
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void notifyInteraction(java.lang.String str, android.os.Bundle bundle) {
            android.service.contentsuggestions.ContentSuggestionsService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.contentsuggestions.ContentSuggestionsService$1$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.contentsuggestions.ContentSuggestionsService) obj).onNotifyInteraction((java.lang.String) obj2, (android.os.Bundle) obj3);
                }
            }, android.service.contentsuggestions.ContentSuggestionsService.this, str, bundle));
        }
    };

    public abstract void onClassifyContentSelections(android.app.contentsuggestions.ClassificationsRequest classificationsRequest, android.app.contentsuggestions.ContentSuggestionsManager.ClassificationsCallback classificationsCallback);

    public abstract void onNotifyInteraction(java.lang.String str, android.os.Bundle bundle);

    public abstract void onProcessContextImage(int i, android.graphics.Bitmap bitmap, android.os.Bundle bundle);

    public abstract void onSuggestContentSelections(android.app.contentsuggestions.SelectionsRequest selectionsRequest, android.app.contentsuggestions.ContentSuggestionsManager.SelectionsCallback selectionsCallback);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        android.util.Log.w(TAG, "Tried to bind to wrong intent (should be android.service.contentsuggestions.ContentSuggestionsService: " + intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.contentsuggestions.ContentSuggestionsManager.SelectionsCallback wrapSelectionsCallback(final android.app.contentsuggestions.ISelectionsCallback iSelectionsCallback) {
        return new android.app.contentsuggestions.ContentSuggestionsManager.SelectionsCallback() { // from class: android.service.contentsuggestions.ContentSuggestionsService$$ExternalSyntheticLambda0
            @Override // android.app.contentsuggestions.ContentSuggestionsManager.SelectionsCallback
            public final void onContentSelectionsAvailable(int i, java.util.List list) {
                android.service.contentsuggestions.ContentSuggestionsService.lambda$wrapSelectionsCallback$0(android.app.contentsuggestions.ISelectionsCallback.this, i, list);
            }
        };
    }

    static /* synthetic */ void lambda$wrapSelectionsCallback$0(android.app.contentsuggestions.ISelectionsCallback iSelectionsCallback, int i, java.util.List list) {
        try {
            iSelectionsCallback.onContentSelectionsAvailable(i, list);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error sending result: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.contentsuggestions.ContentSuggestionsManager.ClassificationsCallback wrapClassificationCallback(final android.app.contentsuggestions.IClassificationsCallback iClassificationsCallback) {
        return new android.app.contentsuggestions.ContentSuggestionsManager.ClassificationsCallback() { // from class: android.service.contentsuggestions.ContentSuggestionsService$$ExternalSyntheticLambda1
            @Override // android.app.contentsuggestions.ContentSuggestionsManager.ClassificationsCallback
            public final void onContentClassificationsAvailable(int i, java.util.List list) {
                android.service.contentsuggestions.ContentSuggestionsService.lambda$wrapClassificationCallback$1(android.app.contentsuggestions.IClassificationsCallback.this, i, list);
            }
        };
    }

    static /* synthetic */ void lambda$wrapClassificationCallback$1(android.app.contentsuggestions.IClassificationsCallback iClassificationsCallback, int i, java.util.List list) {
        try {
            iClassificationsCallback.onContentClassificationsAvailable(i, list);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error sending result: " + e);
        }
    }
}
