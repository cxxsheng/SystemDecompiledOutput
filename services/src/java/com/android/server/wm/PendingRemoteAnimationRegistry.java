package com.android.server.wm;

/* loaded from: classes3.dex */
class PendingRemoteAnimationRegistry {
    static final long TIMEOUT_MS = 3000;
    private final android.util.ArrayMap<java.lang.String, com.android.server.wm.PendingRemoteAnimationRegistry.Entry> mEntries = new android.util.ArrayMap<>();
    private final android.os.Handler mHandler;
    private final com.android.server.wm.WindowManagerGlobalLock mLock;

    PendingRemoteAnimationRegistry(com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock, android.os.Handler handler) {
        this.mLock = windowManagerGlobalLock;
        this.mHandler = handler;
    }

    void addPendingAnimation(java.lang.String str, android.view.RemoteAnimationAdapter remoteAnimationAdapter, @android.annotation.Nullable android.os.IBinder iBinder) {
        this.mEntries.put(str, new com.android.server.wm.PendingRemoteAnimationRegistry.Entry(str, remoteAnimationAdapter, iBinder));
    }

    android.app.ActivityOptions overrideOptionsIfNeeded(java.lang.String str, @android.annotation.Nullable android.app.ActivityOptions activityOptions) {
        com.android.server.wm.PendingRemoteAnimationRegistry.Entry entry = this.mEntries.get(str);
        if (entry == null) {
            return activityOptions;
        }
        if (activityOptions == null) {
            activityOptions = android.app.ActivityOptions.makeRemoteAnimation(entry.adapter);
        } else {
            activityOptions.setRemoteAnimationAdapter(entry.adapter);
        }
        android.os.IBinder iBinder = entry.launchCookie;
        if (iBinder != null) {
            activityOptions.setLaunchCookie(iBinder);
        }
        this.mEntries.remove(str);
        return activityOptions;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Entry {
        final android.view.RemoteAnimationAdapter adapter;

        @android.annotation.Nullable
        final android.os.IBinder launchCookie;
        final java.lang.String packageName;

        Entry(final java.lang.String str, android.view.RemoteAnimationAdapter remoteAnimationAdapter, @android.annotation.Nullable android.os.IBinder iBinder) {
            this.packageName = str;
            this.adapter = remoteAnimationAdapter;
            this.launchCookie = iBinder;
            com.android.server.wm.PendingRemoteAnimationRegistry.this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.wm.PendingRemoteAnimationRegistry$Entry$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.PendingRemoteAnimationRegistry.Entry.this.lambda$new$0(str);
                }
            }, 3000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(java.lang.String str) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.PendingRemoteAnimationRegistry.this.mLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (((com.android.server.wm.PendingRemoteAnimationRegistry.Entry) com.android.server.wm.PendingRemoteAnimationRegistry.this.mEntries.get(str)) == this) {
                        com.android.server.wm.PendingRemoteAnimationRegistry.this.mEntries.remove(str);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    }
}
