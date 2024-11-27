package com.android.server.contentprotection;

/* loaded from: classes.dex */
public class ContentProtectionAllowlistManager {
    private static final java.lang.String TAG = "ContentProtectionAllowlistManager";

    @android.annotation.NonNull
    private final com.android.server.contentcapture.ContentCaptureManagerService mContentCaptureManagerService;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;
    private boolean mStarted;
    private final long mTimeoutMs;

    @android.annotation.Nullable
    private java.time.Instant mUpdatePendingUntil;
    private final java.lang.Object mHandlerToken = new java.lang.Object();
    private final java.lang.Object mLock = new java.lang.Object();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Set<java.lang.String> mAllowedPackages = java.util.Set.of();

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    final com.android.internal.content.PackageMonitor mPackageMonitor = createPackageMonitor();

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    final android.service.contentcapture.IContentProtectionAllowlistCallback mAllowlistCallback = createAllowlistCallback();

    public ContentProtectionAllowlistManager(@android.annotation.NonNull com.android.server.contentcapture.ContentCaptureManagerService contentCaptureManagerService, @android.annotation.NonNull android.os.Handler handler, long j) {
        this.mContentCaptureManagerService = contentCaptureManagerService;
        this.mHandler = handler;
        this.mTimeoutMs = j;
    }

    public void start(long j) {
        if (this.mStarted) {
            return;
        }
        this.mStarted = true;
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.contentprotection.ContentProtectionAllowlistManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.contentprotection.ContentProtectionAllowlistManager.this.handleInitialUpdate();
            }
        }, this.mHandlerToken, j);
    }

    public void stop() {
        try {
            this.mPackageMonitor.unregister();
        } catch (java.lang.IllegalStateException e) {
        }
        this.mHandler.removeCallbacksAndMessages(this.mHandlerToken);
        this.mUpdatePendingUntil = null;
        this.mStarted = false;
    }

    public boolean isAllowed(@android.annotation.NonNull java.lang.String str) {
        java.util.Set<java.lang.String> set;
        synchronized (this.mLock) {
            set = this.mAllowedPackages;
        }
        return set.contains(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUpdateAllowlistResponse(@android.annotation.NonNull java.util.List<java.lang.String> list) {
        synchronized (this.mLock) {
            this.mAllowedPackages = (java.util.Set) list.stream().collect(java.util.stream.Collectors.toUnmodifiableSet());
        }
        this.mUpdatePendingUntil = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInitialUpdate() {
        handlePackagesChanged();
        this.mPackageMonitor.register(this.mContentCaptureManagerService.getContext(), android.os.UserHandle.ALL, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackagesChanged() {
        com.android.server.contentprotection.RemoteContentProtectionService createRemoteContentProtectionService;
        if ((this.mUpdatePendingUntil != null && java.time.Instant.now().isBefore(this.mUpdatePendingUntil)) || (createRemoteContentProtectionService = this.mContentCaptureManagerService.createRemoteContentProtectionService()) == null) {
            return;
        }
        this.mHandler.removeCallbacksAndMessages(this.mHandlerToken);
        this.mUpdatePendingUntil = java.time.Instant.now().plusMillis(this.mTimeoutMs);
        try {
            createRemoteContentProtectionService.onUpdateAllowlistRequest(this.mAllowlistCallback);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to call remote service", e);
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    protected com.android.internal.content.PackageMonitor createPackageMonitor() {
        return new com.android.server.contentprotection.ContentProtectionAllowlistManager.ContentProtectionPackageMonitor();
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    protected android.service.contentcapture.IContentProtectionAllowlistCallback createAllowlistCallback() {
        return new com.android.server.contentprotection.ContentProtectionAllowlistManager.ContentProtectionAllowlistCallback();
    }

    private final class ContentProtectionPackageMonitor extends com.android.internal.content.PackageMonitor {
        private ContentProtectionPackageMonitor() {
        }

        public void onSomePackagesChanged() {
            com.android.server.contentprotection.ContentProtectionAllowlistManager.this.handlePackagesChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class ContentProtectionAllowlistCallback extends android.service.contentcapture.IContentProtectionAllowlistCallback.Stub {
        private ContentProtectionAllowlistCallback() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setAllowlist$0(java.util.List list) {
            com.android.server.contentprotection.ContentProtectionAllowlistManager.this.handleUpdateAllowlistResponse(list);
        }

        public void setAllowlist(final java.util.List<java.lang.String> list) {
            com.android.server.contentprotection.ContentProtectionAllowlistManager.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.contentprotection.ContentProtectionAllowlistManager$ContentProtectionAllowlistCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.contentprotection.ContentProtectionAllowlistManager.ContentProtectionAllowlistCallback.this.lambda$setAllowlist$0(list);
                }
            });
        }
    }
}
