package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class OverlayableSystemBooleanResourceWrapper implements java.lang.AutoCloseable {
    private static final java.lang.String SYSTEM_PACKAGE_NAME = "android";
    private static final java.lang.String TAG = "OverlayableSystemBooleanResourceWrapper";

    @android.annotation.NonNull
    private final java.util.concurrent.atomic.AtomicReference<java.lang.Runnable> mCleanerRef;
    private final int mUserId;

    @android.annotation.NonNull
    private final java.util.concurrent.atomic.AtomicBoolean mValueRef;

    @android.annotation.NonNull
    static com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper create(@android.annotation.NonNull final android.content.Context context, final int i, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull final java.util.function.Consumer<com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper> consumer) {
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(evaluate(context, i));
        java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
        final com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper overlayableSystemBooleanResourceWrapper = new com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper(context.getUserId(), atomicBoolean, atomicReference);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.OVERLAY_CHANGED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        intentFilter.addDataSchemeSpecificPart("android", 0);
        final android.content.BroadcastReceiver broadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                boolean evaluate = com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper.evaluate(context, i);
                if (evaluate != atomicBoolean.getAndSet(evaluate)) {
                    consumer.accept(overlayableSystemBooleanResourceWrapper);
                }
            }
        };
        context.registerReceiver(broadcastReceiver, intentFilter, null, handler, 4);
        atomicReference.set(new java.lang.Runnable() { // from class: com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                context.unregisterReceiver(broadcastReceiver);
            }
        });
        atomicBoolean.set(evaluate(context, i));
        return overlayableSystemBooleanResourceWrapper;
    }

    private OverlayableSystemBooleanResourceWrapper(int i, @android.annotation.NonNull java.util.concurrent.atomic.AtomicBoolean atomicBoolean, @android.annotation.NonNull java.util.concurrent.atomic.AtomicReference<java.lang.Runnable> atomicReference) {
        this.mUserId = i;
        this.mValueRef = atomicBoolean;
        this.mCleanerRef = atomicReference;
    }

    boolean get() {
        return this.mValueRef.get();
    }

    int getUserId() {
        return this.mUserId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean evaluate(@android.annotation.NonNull android.content.Context context, int i) {
        try {
            return context.getPackageManager().getResourcesForApplication("android").getBoolean(i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "getResourcesForApplication(\"android\") failed", e);
            return false;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        java.lang.Runnable andSet = this.mCleanerRef.getAndSet(null);
        if (andSet != null) {
            andSet.run();
        }
    }
}
