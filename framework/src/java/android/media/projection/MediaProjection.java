package android.media.projection;

/* loaded from: classes2.dex */
public final class MediaProjection {
    static final long MEDIA_PROJECTION_REQUIRES_CALLBACK = 269849258;
    private static final java.lang.String TAG = "MediaProjection";
    private final java.util.Map<android.media.projection.MediaProjection.Callback, android.media.projection.MediaProjection.CallbackRecord> mCallbacks;
    private final android.content.Context mContext;
    private final android.hardware.display.DisplayManager mDisplayManager;
    private final android.media.projection.IMediaProjection mImpl;

    public MediaProjection(android.content.Context context, android.media.projection.IMediaProjection iMediaProjection) {
        this(context, iMediaProjection, (android.hardware.display.DisplayManager) context.getSystemService(android.hardware.display.DisplayManager.class));
    }

    public MediaProjection(android.content.Context context, android.media.projection.IMediaProjection iMediaProjection, android.hardware.display.DisplayManager displayManager) {
        this.mCallbacks = new android.util.ArrayMap();
        this.mContext = context;
        this.mImpl = iMediaProjection;
        try {
            this.mImpl.start(new android.media.projection.MediaProjection.MediaProjectionCallback());
            this.mDisplayManager = displayManager;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Content Recording: Failed to start media projection", e);
            throw new java.lang.RuntimeException("Failed to start media projection", e);
        }
    }

    public void registerCallback(android.media.projection.MediaProjection.Callback callback, android.os.Handler handler) {
        try {
            android.media.projection.MediaProjection.Callback callback2 = (android.media.projection.MediaProjection.Callback) java.util.Objects.requireNonNull(callback);
            if (handler == null) {
                handler = new android.os.Handler();
            }
            this.mCallbacks.put(callback2, new android.media.projection.MediaProjection.CallbackRecord(callback2, handler));
        } catch (java.lang.NullPointerException e) {
            android.util.Log.e(TAG, "Content Recording: cannot register null Callback", e);
            throw e;
        } catch (java.lang.RuntimeException e2) {
            android.util.Log.e(TAG, "Content Recording: failed to create new Handler to register Callback", e2);
        }
    }

    public void unregisterCallback(android.media.projection.MediaProjection.Callback callback) {
        try {
            this.mCallbacks.remove((android.media.projection.MediaProjection.Callback) java.util.Objects.requireNonNull(callback));
        } catch (java.lang.NullPointerException e) {
            android.util.Log.d(TAG, "Content Recording: cannot unregister null Callback", e);
            throw e;
        }
    }

    public android.hardware.display.VirtualDisplay createVirtualDisplay(java.lang.String str, int i, int i2, int i3, boolean z, android.view.Surface surface, android.hardware.display.VirtualDisplay.Callback callback, android.os.Handler handler) {
        int i4;
        if (!z) {
            i4 = 18;
        } else {
            i4 = 22;
        }
        android.hardware.display.VirtualDisplayConfig.Builder flags = new android.hardware.display.VirtualDisplayConfig.Builder(str, i, i2, i3).setFlags(i4);
        if (surface != null) {
            flags.setSurface(surface);
        }
        return createVirtualDisplay(flags, callback, handler);
    }

    public android.hardware.display.VirtualDisplay createVirtualDisplay(java.lang.String str, int i, int i2, int i3, int i4, android.view.Surface surface, android.hardware.display.VirtualDisplay.Callback callback, android.os.Handler handler) {
        if (shouldMediaProjectionRequireCallback() && this.mCallbacks.isEmpty()) {
            java.lang.IllegalStateException illegalStateException = new java.lang.IllegalStateException("Must register a callback before starting capture, to manage resources in response to MediaProjection states.");
            android.util.Log.e(TAG, "Content Recording: no callback registered for virtual display", illegalStateException);
            throw illegalStateException;
        }
        android.hardware.display.VirtualDisplayConfig.Builder flags = new android.hardware.display.VirtualDisplayConfig.Builder(str, i, i2, i3).setFlags(i4);
        if (surface != null) {
            flags.setSurface(surface);
        }
        return createVirtualDisplay(flags, callback, handler);
    }

    public android.hardware.display.VirtualDisplay createVirtualDisplay(android.hardware.display.VirtualDisplayConfig.Builder builder, android.hardware.display.VirtualDisplay.Callback callback, android.os.Handler handler) {
        builder.setWindowManagerMirroringEnabled(true);
        android.hardware.display.VirtualDisplay createVirtualDisplay = this.mDisplayManager.createVirtualDisplay(this, builder.build(), callback, handler);
        if (createVirtualDisplay == null) {
            android.util.Slog.w(TAG, "Failed to create virtual display.");
            return null;
        }
        return createVirtualDisplay;
    }

    private boolean shouldMediaProjectionRequireCallback() {
        return android.app.compat.CompatChanges.isChangeEnabled(MEDIA_PROJECTION_REQUIRES_CALLBACK);
    }

    public void stop() {
        try {
            android.util.Log.d(TAG, "Content Recording: stopping projection");
            this.mImpl.stop();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Unable to stop projection", e);
        }
    }

    public android.media.projection.IMediaProjection getProjection() {
        return this.mImpl;
    }

    public static abstract class Callback {
        public void onStop() {
        }

        public void onCapturedContentResize(int i, int i2) {
        }

        public void onCapturedContentVisibilityChanged(boolean z) {
        }
    }

    private final class MediaProjectionCallback extends android.media.projection.IMediaProjectionCallback.Stub {
        private MediaProjectionCallback() {
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onStop() {
            android.util.Slog.v(android.media.projection.MediaProjection.TAG, "Dispatch stop to " + android.media.projection.MediaProjection.this.mCallbacks.size() + " callbacks.");
            java.util.Iterator it = android.media.projection.MediaProjection.this.mCallbacks.values().iterator();
            while (it.hasNext()) {
                ((android.media.projection.MediaProjection.CallbackRecord) it.next()).onStop();
            }
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onCapturedContentResize(int i, int i2) {
            java.util.Iterator it = android.media.projection.MediaProjection.this.mCallbacks.values().iterator();
            while (it.hasNext()) {
                ((android.media.projection.MediaProjection.CallbackRecord) it.next()).onCapturedContentResize(i, i2);
            }
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onCapturedContentVisibilityChanged(boolean z) {
            java.util.Iterator it = android.media.projection.MediaProjection.this.mCallbacks.values().iterator();
            while (it.hasNext()) {
                ((android.media.projection.MediaProjection.CallbackRecord) it.next()).onCapturedContentVisibilityChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CallbackRecord extends android.media.projection.MediaProjection.Callback {
        private final android.media.projection.MediaProjection.Callback mCallback;
        private final android.os.Handler mHandler;

        public CallbackRecord(android.media.projection.MediaProjection.Callback callback, android.os.Handler handler) {
            this.mCallback = callback;
            this.mHandler = handler;
        }

        @Override // android.media.projection.MediaProjection.Callback
        public void onStop() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.projection.MediaProjection.CallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    android.media.projection.MediaProjection.CallbackRecord.this.mCallback.onStop();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCapturedContentResize$0(int i, int i2) {
            this.mCallback.onCapturedContentResize(i, i2);
        }

        @Override // android.media.projection.MediaProjection.Callback
        public void onCapturedContentResize(final int i, final int i2) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.projection.MediaProjection$CallbackRecord$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.projection.MediaProjection.CallbackRecord.this.lambda$onCapturedContentResize$0(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCapturedContentVisibilityChanged$1(boolean z) {
            this.mCallback.onCapturedContentVisibilityChanged(z);
        }

        @Override // android.media.projection.MediaProjection.Callback
        public void onCapturedContentVisibilityChanged(final boolean z) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.projection.MediaProjection$CallbackRecord$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.projection.MediaProjection.CallbackRecord.this.lambda$onCapturedContentVisibilityChanged$1(z);
                }
            });
        }
    }
}
