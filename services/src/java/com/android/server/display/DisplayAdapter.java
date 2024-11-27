package com.android.server.display;

/* loaded from: classes.dex */
abstract class DisplayAdapter {
    public static final int DISPLAY_DEVICE_EVENT_ADDED = 1;
    public static final int DISPLAY_DEVICE_EVENT_CHANGED = 2;
    public static final int DISPLAY_DEVICE_EVENT_REMOVED = 3;
    private static final java.util.concurrent.atomic.AtomicInteger NEXT_DISPLAY_MODE_ID = new java.util.concurrent.atomic.AtomicInteger(1);
    private final android.content.Context mContext;
    private final com.android.server.display.feature.DisplayManagerFlags mFeatureFlags;
    private final android.os.Handler mHandler;
    private final com.android.server.display.DisplayAdapter.Listener mListener;
    private final java.lang.String mName;
    private final com.android.server.display.DisplayManagerService.SyncRoot mSyncRoot;

    public interface Listener {
        void onDisplayDeviceEvent(com.android.server.display.DisplayDevice displayDevice, int i);

        void onTraversalRequested();
    }

    DisplayAdapter(com.android.server.display.DisplayManagerService.SyncRoot syncRoot, android.content.Context context, android.os.Handler handler, com.android.server.display.DisplayAdapter.Listener listener, java.lang.String str, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        this.mSyncRoot = syncRoot;
        this.mContext = context;
        this.mHandler = handler;
        this.mListener = listener;
        this.mName = str;
        this.mFeatureFlags = displayManagerFlags;
    }

    public final com.android.server.display.DisplayManagerService.SyncRoot getSyncRoot() {
        return this.mSyncRoot;
    }

    public final android.content.Context getContext() {
        return this.mContext;
    }

    public final android.os.Handler getHandler() {
        return this.mHandler;
    }

    public final java.lang.String getName() {
        return this.mName;
    }

    public final com.android.server.display.feature.DisplayManagerFlags getFeatureFlags() {
        return this.mFeatureFlags;
    }

    public void registerLocked() {
    }

    public void dumpLocked(java.io.PrintWriter printWriter) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendDisplayDeviceEventLocked$0(com.android.server.display.DisplayDevice displayDevice, int i) {
        this.mListener.onDisplayDeviceEvent(displayDevice, i);
    }

    protected final void sendDisplayDeviceEventLocked(final com.android.server.display.DisplayDevice displayDevice, final int i) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.DisplayAdapter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.DisplayAdapter.this.lambda$sendDisplayDeviceEventLocked$0(displayDevice, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendTraversalRequestLocked$1() {
        this.mListener.onTraversalRequested();
    }

    protected final void sendTraversalRequestLocked() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.DisplayAdapter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.DisplayAdapter.this.lambda$sendTraversalRequestLocked$1();
            }
        });
    }

    public static android.view.Display.Mode createMode(int i, int i2, float f) {
        return createMode(i, i2, f, f, new float[0], new int[0]);
    }

    public static android.view.Display.Mode createMode(int i, int i2, float f, float f2, float[] fArr, int[] iArr) {
        return new android.view.Display.Mode(NEXT_DISPLAY_MODE_ID.getAndIncrement(), i, i2, f, f2, fArr, iArr);
    }
}
