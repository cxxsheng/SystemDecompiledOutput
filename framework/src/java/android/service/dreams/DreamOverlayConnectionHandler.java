package android.service.dreams;

/* loaded from: classes3.dex */
public final class DreamOverlayConnectionHandler {
    private static final int MSG_ADD_CONSUMER = 1;
    private static final int MSG_OVERLAY_CLIENT_READY = 3;
    private static final int MSG_REMOVE_CONSUMER = 2;
    private static final java.lang.String TAG = "DreamOverlayConnection";
    private final android.service.dreams.DreamOverlayConnectionHandler.OverlayConnectionCallback mCallback;
    private android.service.dreams.IDreamOverlayClient mClient;
    private final com.android.internal.util.PersistentServiceConnection<android.service.dreams.IDreamOverlay> mConnection;
    private final java.util.List<java.util.function.Consumer<android.service.dreams.IDreamOverlayClient>> mConsumers;
    private final android.os.Handler mHandler;

    DreamOverlayConnectionHandler(android.content.Context context, android.os.Looper looper, android.content.Intent intent, int i, int i2, int i3) {
        this(context, looper, intent, i, i2, i3, new android.service.dreams.DreamOverlayConnectionHandler.Injector());
    }

    public DreamOverlayConnectionHandler(android.content.Context context, android.os.Looper looper, android.content.Intent intent, int i, int i2, int i3, android.service.dreams.DreamOverlayConnectionHandler.Injector injector) {
        this.mConsumers = new java.util.ArrayList();
        this.mCallback = new android.service.dreams.DreamOverlayConnectionHandler.OverlayConnectionCallback();
        this.mHandler = new android.os.Handler(looper, new android.service.dreams.DreamOverlayConnectionHandler.OverlayHandlerCallback());
        this.mConnection = injector.buildConnection(context, this.mHandler, intent, i, i2, i3);
    }

    public boolean bind() {
        this.mConnection.addCallback(this.mCallback);
        boolean bind = this.mConnection.bind();
        if (!bind) {
            unbind();
        }
        return bind;
    }

    public void unbind() {
        this.mConnection.removeCallback(this.mCallback);
        this.mHandler.removeCallbacksAndMessages(null);
        this.mClient = null;
        this.mConsumers.clear();
        this.mConnection.unbind();
    }

    public void addConsumer(java.util.function.Consumer<android.service.dreams.IDreamOverlayClient> consumer) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, consumer));
    }

    public void removeConsumer(java.util.function.Consumer<android.service.dreams.IDreamOverlayClient> consumer) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, consumer));
        this.mHandler.removeMessages(1, consumer);
    }

    private final class OverlayHandlerCallback implements android.os.Handler.Callback {
        private OverlayHandlerCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.service.dreams.DreamOverlayConnectionHandler.this.onAddConsumer((java.util.function.Consumer) message.obj);
                    break;
                case 2:
                    android.service.dreams.DreamOverlayConnectionHandler.this.onRemoveConsumer((java.util.function.Consumer) message.obj);
                    break;
                case 3:
                    android.service.dreams.DreamOverlayConnectionHandler.this.onOverlayClientReady((android.service.dreams.IDreamOverlayClient) message.obj);
                    break;
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onOverlayClientReady(android.service.dreams.IDreamOverlayClient iDreamOverlayClient) {
        this.mClient = iDreamOverlayClient;
        java.util.Iterator<java.util.function.Consumer<android.service.dreams.IDreamOverlayClient>> it = this.mConsumers.iterator();
        while (it.hasNext()) {
            it.next().accept(this.mClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAddConsumer(java.util.function.Consumer<android.service.dreams.IDreamOverlayClient> consumer) {
        if (this.mClient != null) {
            consumer.accept(this.mClient);
        }
        this.mConsumers.add(consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRemoveConsumer(java.util.function.Consumer<android.service.dreams.IDreamOverlayClient> consumer) {
        this.mConsumers.remove(consumer);
    }

    private final class OverlayConnectionCallback implements com.android.internal.util.ObservableServiceConnection.Callback<android.service.dreams.IDreamOverlay> {
        private final android.service.dreams.IDreamOverlayClientCallback mClientCallback;

        private OverlayConnectionCallback() {
            this.mClientCallback = new android.service.dreams.IDreamOverlayClientCallback.Stub() { // from class: android.service.dreams.DreamOverlayConnectionHandler.OverlayConnectionCallback.1
                @Override // android.service.dreams.IDreamOverlayClientCallback
                public void onDreamOverlayClient(android.service.dreams.IDreamOverlayClient iDreamOverlayClient) {
                    android.service.dreams.DreamOverlayConnectionHandler.this.mHandler.sendMessage(android.service.dreams.DreamOverlayConnectionHandler.this.mHandler.obtainMessage(3, iDreamOverlayClient));
                }
            };
        }

        @Override // com.android.internal.util.ObservableServiceConnection.Callback
        public void onConnected(com.android.internal.util.ObservableServiceConnection<android.service.dreams.IDreamOverlay> observableServiceConnection, android.service.dreams.IDreamOverlay iDreamOverlay) {
            try {
                iDreamOverlay.getClient(this.mClientCallback);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.service.dreams.DreamOverlayConnectionHandler.TAG, "could not get DreamOverlayClient", e);
            }
        }

        @Override // com.android.internal.util.ObservableServiceConnection.Callback
        public void onDisconnected(com.android.internal.util.ObservableServiceConnection<android.service.dreams.IDreamOverlay> observableServiceConnection, int i) {
            android.service.dreams.DreamOverlayConnectionHandler.this.mClient = null;
            android.service.dreams.DreamOverlayConnectionHandler.this.mHandler.removeMessages(3);
        }
    }

    public static class Injector {
        public com.android.internal.util.PersistentServiceConnection<android.service.dreams.IDreamOverlay> buildConnection(android.content.Context context, android.os.Handler handler, android.content.Intent intent, int i, int i2, int i3) {
            java.util.Objects.requireNonNull(handler);
            return new com.android.internal.util.PersistentServiceConnection<>(context, new android.media.midi.MidiManager$$ExternalSyntheticLambda0(handler), handler, new com.android.internal.util.ObservableServiceConnection.ServiceTransformer() { // from class: android.service.dreams.DreamOverlayConnectionHandler$Injector$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.ObservableServiceConnection.ServiceTransformer
                public final java.lang.Object convert(android.os.IBinder iBinder) {
                    return android.service.dreams.IDreamOverlay.Stub.asInterface(iBinder);
                }
            }, intent, android.media.audio.Enums.AUDIO_FORMAT_AAC_MAIN, i, i2, i3);
        }
    }
}
