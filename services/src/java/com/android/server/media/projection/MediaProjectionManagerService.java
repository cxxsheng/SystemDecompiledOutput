package com.android.server.media.projection;

/* loaded from: classes2.dex */
public final class MediaProjectionManagerService extends com.android.server.SystemService implements com.android.server.Watchdog.Monitor {

    @com.android.internal.annotations.VisibleForTesting
    static final long MEDIA_PROJECTION_PREVENTS_REUSING_CONSENT = 266201607;
    private static final boolean REQUIRE_FG_SERVICE_FOR_PROJECTION = true;
    private static final java.lang.String TAG = "MediaProjectionManagerService";
    private final android.app.ActivityManagerInternal mActivityManagerInternal;
    private final android.app.AppOpsManager mAppOps;
    private final com.android.server.media.projection.MediaProjectionManagerService.CallbackDelegate mCallbackDelegate;
    private final com.android.server.media.projection.MediaProjectionManagerService.Clock mClock;
    private final android.content.Context mContext;
    private final java.util.Map<android.os.IBinder, android.os.IBinder.DeathRecipient> mDeathEaters;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;
    private final com.android.server.media.projection.MediaProjectionManagerService.Injector mInjector;
    private final java.lang.Object mLock;
    private final com.android.server.media.projection.MediaProjectionMetricsLogger mMediaProjectionMetricsLogger;
    private android.media.MediaRouter.RouteInfo mMediaRouteInfo;
    private final android.media.MediaRouter mMediaRouter;
    private final com.android.server.media.projection.MediaProjectionManagerService.MediaRouterCallback mMediaRouterCallback;
    private final android.content.pm.PackageManager mPackageManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.media.projection.MediaProjectionManagerService.MediaProjection mProjectionGrant;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.IBinder mProjectionToken;
    private final com.android.server.wm.WindowManagerInternal mWmInternal;

    @com.android.internal.annotations.VisibleForTesting
    interface Clock {
        long uptimeMillis();
    }

    public MediaProjectionManagerService(android.content.Context context) {
        this(context, new com.android.server.media.projection.MediaProjectionManagerService.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    MediaProjectionManagerService(android.content.Context context, com.android.server.media.projection.MediaProjectionManagerService.Injector injector) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mContext = context;
        this.mInjector = injector;
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mClock = injector.createClock();
        this.mDeathEaters = new android.util.ArrayMap();
        this.mCallbackDelegate = new com.android.server.media.projection.MediaProjectionManagerService.CallbackDelegate(injector.createCallbackLooper());
        this.mAppOps = (android.app.AppOpsManager) this.mContext.getSystemService("appops");
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mPackageManager = this.mContext.getPackageManager();
        this.mWmInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        this.mMediaRouter = (android.media.MediaRouter) this.mContext.getSystemService("media_router");
        this.mMediaRouterCallback = new com.android.server.media.projection.MediaProjectionManagerService.MediaRouterCallback();
        this.mMediaProjectionMetricsLogger = injector.mediaProjectionMetricsLogger(context);
        com.android.server.Watchdog.getInstance().addMonitor(this);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        boolean shouldMediaProjectionPreventReusingConsent(com.android.server.media.projection.MediaProjectionManagerService.MediaProjection mediaProjection) {
            return android.app.compat.CompatChanges.isChangeEnabled(com.android.server.media.projection.MediaProjectionManagerService.MEDIA_PROJECTION_PREVENTS_REUSING_CONSENT, mediaProjection.packageName, android.os.UserHandle.getUserHandleForUid(mediaProjection.uid));
        }

        com.android.server.media.projection.MediaProjectionManagerService.Clock createClock() {
            return new com.android.server.media.projection.MediaProjectionManagerService.Clock() { // from class: com.android.server.media.projection.MediaProjectionManagerService$Injector$$ExternalSyntheticLambda0
                @Override // com.android.server.media.projection.MediaProjectionManagerService.Clock
                public final long uptimeMillis() {
                    return android.os.SystemClock.uptimeMillis();
                }
            };
        }

        android.os.Looper createCallbackLooper() {
            return android.os.Looper.getMainLooper();
        }

        com.android.server.media.projection.MediaProjectionMetricsLogger mediaProjectionMetricsLogger(android.content.Context context) {
            return com.android.server.media.projection.MediaProjectionMetricsLogger.getInstance(context);
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("media_projection", new com.android.server.media.projection.MediaProjectionManagerService.BinderService(this.mContext), false);
        this.mMediaRouter.addCallback(4, this.mMediaRouterCallback, 8);
        this.mActivityManagerInternal.registerProcessObserver(new android.app.IProcessObserver.Stub() { // from class: com.android.server.media.projection.MediaProjectionManagerService.1
            public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            }

            public void onProcessStarted(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
            }

            public void onForegroundServicesChanged(int i, int i2, int i3) {
                com.android.server.media.projection.MediaProjectionManagerService.this.handleForegroundServicesChanged(i, i2, i3);
            }

            public void onProcessDied(int i, int i2) {
            }
        });
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        this.mMediaRouter.rebindAsUser(targetUser2.getUserIdentifier());
        synchronized (this.mLock) {
            try {
                if (this.mProjectionGrant != null) {
                    android.util.Slog.d(TAG, "Content Recording: Stopped MediaProjection due to user switching");
                    this.mProjectionGrant.stop();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.Watchdog.Monitor
    public void monitor() {
        synchronized (this.mLock) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleForegroundServicesChanged(int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                if (this.mProjectionGrant == null || this.mProjectionGrant.uid != i2) {
                    return;
                }
                if (this.mProjectionGrant.requiresForegroundService()) {
                    if (this.mActivityManagerInternal.hasRunningForegroundService(i2, 32)) {
                        return;
                    }
                    synchronized (this.mLock) {
                        try {
                            android.util.Slog.d(TAG, "Content Recording: Stopped MediaProjection due to foreground service change");
                            if (this.mProjectionGrant != null) {
                                this.mProjectionGrant.stop();
                            }
                        } finally {
                        }
                    }
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startProjectionLocked(com.android.server.media.projection.MediaProjectionManagerService.MediaProjection mediaProjection) {
        if (this.mProjectionGrant != null) {
            android.util.Slog.d(TAG, "Content Recording: Stopped MediaProjection to start new incoming projection");
            this.mProjectionGrant.stop();
        }
        if (this.mMediaRouteInfo != null) {
            this.mMediaRouter.getFallbackRoute().select();
        }
        this.mProjectionToken = mediaProjection.asBinder();
        this.mProjectionGrant = mediaProjection;
        dispatchStart(mediaProjection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopProjectionLocked(com.android.server.media.projection.MediaProjectionManagerService.MediaProjection mediaProjection) {
        int i;
        android.util.Slog.d(TAG, "Content Recording: Stopped active MediaProjection and dispatching stop to callbacks");
        android.view.ContentRecordingSession contentRecordingSession = mediaProjection.mSession;
        if (contentRecordingSession != null) {
            i = contentRecordingSession.getTargetUid();
        } else {
            i = -2;
        }
        this.mMediaProjectionMetricsLogger.logStopped(mediaProjection.uid, i);
        this.mProjectionToken = null;
        this.mProjectionGrant = null;
        dispatchStop(mediaProjection);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.media.projection.MediaProjectionInfo addCallback(final android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
        android.media.projection.MediaProjectionInfo projectionInfo;
        android.os.IBinder.DeathRecipient deathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.media.projection.MediaProjectionManagerService.2
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                com.android.server.media.projection.MediaProjectionManagerService.this.removeCallback(iMediaProjectionWatcherCallback);
            }
        };
        synchronized (this.mLock) {
            try {
                this.mCallbackDelegate.add(iMediaProjectionWatcherCallback);
                linkDeathRecipientLocked(iMediaProjectionWatcherCallback, deathRecipient);
                projectionInfo = this.mProjectionGrant != null ? this.mProjectionGrant.getProjectionInfo() : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return projectionInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCallback(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
        synchronized (this.mLock) {
            unlinkDeathRecipientLocked(iMediaProjectionWatcherCallback);
            this.mCallbackDelegate.remove(iMediaProjectionWatcherCallback);
        }
    }

    private void linkDeathRecipientLocked(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback, android.os.IBinder.DeathRecipient deathRecipient) {
        try {
            android.os.IBinder asBinder = iMediaProjectionWatcherCallback.asBinder();
            asBinder.linkToDeath(deathRecipient, 0);
            this.mDeathEaters.put(asBinder, deathRecipient);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to link to death for media projection monitoring callback", e);
        }
    }

    private void unlinkDeathRecipientLocked(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
        android.os.IBinder asBinder = iMediaProjectionWatcherCallback.asBinder();
        android.os.IBinder.DeathRecipient remove = this.mDeathEaters.remove(asBinder);
        if (remove != null) {
            asBinder.unlinkToDeath(remove, 0);
        }
    }

    private void dispatchStart(com.android.server.media.projection.MediaProjectionManagerService.MediaProjection mediaProjection) {
        this.mCallbackDelegate.dispatchStart(mediaProjection);
    }

    private void dispatchStop(com.android.server.media.projection.MediaProjectionManagerService.MediaProjection mediaProjection) {
        this.mCallbackDelegate.dispatchStop(mediaProjection);
    }

    private void dispatchSessionSet(@android.annotation.NonNull android.media.projection.MediaProjectionInfo mediaProjectionInfo, @android.annotation.Nullable android.view.ContentRecordingSession contentRecordingSession) {
        this.mCallbackDelegate.dispatchSession(mediaProjectionInfo, contentRecordingSession);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean setContentRecordingSession(@android.annotation.Nullable android.view.ContentRecordingSession contentRecordingSession) {
        java.lang.String str;
        boolean contentRecordingSession2 = this.mWmInternal.setContentRecordingSession(contentRecordingSession);
        synchronized (this.mLock) {
            try {
                if (!contentRecordingSession2) {
                    if (this.mProjectionGrant != null) {
                        if (contentRecordingSession != null) {
                            str = android.view.ContentRecordingSession.recordContentToString(contentRecordingSession.getContentToRecord());
                        } else {
                            str = "none";
                        }
                        android.util.Slog.w(TAG, "Content Recording: Stopped MediaProjection due to failing to set ContentRecordingSession - id= " + this.mProjectionGrant.getVirtualDisplayId() + "type=" + str);
                        this.mProjectionGrant.stop();
                    }
                    return false;
                }
                if (this.mProjectionGrant != null) {
                    this.mProjectionGrant.mSession = contentRecordingSession;
                    if (contentRecordingSession != null) {
                        this.mMediaProjectionMetricsLogger.logInProgress(this.mProjectionGrant.uid, contentRecordingSession.getTargetUid());
                    }
                    dispatchSessionSet(this.mProjectionGrant.getProjectionInfo(), contentRecordingSession);
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isCurrentProjection(android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                if (this.mProjectionToken == null) {
                    return false;
                }
                return this.mProjectionToken.equals(iBinder);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void requestConsentForInvalidProjection() {
        android.content.Intent buildReviewGrantedConsentIntentLocked;
        int i;
        synchronized (this.mLock) {
            buildReviewGrantedConsentIntentLocked = buildReviewGrantedConsentIntentLocked();
            i = this.mProjectionGrant.uid;
        }
        android.util.Slog.v(TAG, "Reusing token: Reshow dialog for due to invalid projection.");
        this.mContext.startActivityAsUser(buildReviewGrantedConsentIntentLocked, android.os.UserHandle.getUserHandleForUid(i));
    }

    private android.content.Intent buildReviewGrantedConsentIntentLocked() {
        return new android.content.Intent().setComponent(android.content.ComponentName.unflattenFromString(this.mContext.getResources().getString(android.R.string.config_iccHotswapPromptForRestartDialogComponent))).putExtra("extra_media_projection_user_consent_required", true).putExtra("extra_media_projection_package_reusing_consent", this.mProjectionGrant.packageName).setFlags(276824064);
    }

    @com.android.internal.annotations.VisibleForTesting
    void notifyPermissionRequestInitiated(int i, int i2) {
        this.mMediaProjectionMetricsLogger.logInitiated(i, i2);
    }

    @com.android.internal.annotations.VisibleForTesting
    void notifyPermissionRequestDisplayed(int i) {
        this.mMediaProjectionMetricsLogger.logPermissionRequestDisplayed(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    void notifyPermissionRequestCancelled(int i) {
        this.mMediaProjectionMetricsLogger.logProjectionPermissionRequestCancelled(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    void notifyAppSelectorDisplayed(int i) {
        this.mMediaProjectionMetricsLogger.logAppSelectorDisplayed(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    void notifyWindowingModeChanged(int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                if (this.mProjectionGrant == null) {
                    android.util.Slog.i(TAG, "Cannot log MediaProjectionTargetChanged atom due to null projection");
                } else {
                    this.mMediaProjectionMetricsLogger.logChangedWindowingMode(i, this.mProjectionGrant.uid, i2, i3);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setUserReviewGrantedConsentResult(@android.media.projection.ReviewGrantedConsentResult int i, @android.annotation.Nullable android.media.projection.IMediaProjection iMediaProjection) {
        android.os.IBinder asBinder;
        synchronized (this.mLock) {
            boolean z = true;
            if (i != 1 && i != 2) {
                z = false;
            }
            android.os.IBinder iBinder = null;
            if (z) {
                if (iMediaProjection == null) {
                    asBinder = null;
                } else {
                    try {
                        asBinder = iMediaProjection.asBinder();
                    } finally {
                    }
                }
                if (!isCurrentProjection(asBinder)) {
                    android.util.Slog.v(TAG, "Reusing token: Ignore consent result of " + i + " for a token that isn't current");
                    return;
                }
            }
            if (this.mProjectionGrant == null) {
                android.util.Slog.w(TAG, "Reusing token: Can't review consent with no ongoing projection.");
                return;
            }
            if (this.mProjectionGrant.mSession != null && this.mProjectionGrant.mSession.isWaitingForConsent()) {
                android.util.Slog.v(TAG, "Reusing token: Handling user consent result " + i);
                switch (i) {
                    case -1:
                    case 0:
                        setReviewedConsentSessionLocked(null);
                        if (this.mProjectionGrant != null) {
                            android.util.Slog.w(TAG, "Content Recording: Stopped MediaProjection due to user consent result of CANCEL - id= " + this.mProjectionGrant.getVirtualDisplayId());
                            this.mProjectionGrant.stop();
                            break;
                        }
                        break;
                    case 1:
                        setReviewedConsentSessionLocked(android.view.ContentRecordingSession.createDisplaySession(0));
                        break;
                    case 2:
                        if (this.mProjectionGrant.getLaunchCookie() != null) {
                            iBinder = this.mProjectionGrant.getLaunchCookie().binder;
                        }
                        setReviewedConsentSessionLocked(android.view.ContentRecordingSession.createTaskSession(iBinder));
                        break;
                }
                return;
            }
            android.util.Slog.w(TAG, "Reusing token: Ignore consent result " + i + " if not waiting for the result.");
        }
    }

    private void setReviewedConsentSessionLocked(@android.annotation.Nullable android.view.ContentRecordingSession contentRecordingSession) {
        if (contentRecordingSession != null) {
            contentRecordingSession.setWaitingForConsent(false);
            contentRecordingSession.setVirtualDisplayId(this.mProjectionGrant.mVirtualDisplayId);
        }
        android.util.Slog.v(TAG, "Reusing token: Processed consent so set the session " + contentRecordingSession);
        if (!setContentRecordingSession(contentRecordingSession)) {
            android.util.Slog.e(TAG, "Reusing token: Failed to set session for reused consent, so stop");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.media.projection.MediaProjectionManagerService.MediaProjection createProjectionInternal(int i, java.lang.String str, int i2, boolean z, android.os.UserHandle userHandle) {
        try {
            android.content.pm.ApplicationInfo applicationInfoAsUser = this.mPackageManager.getApplicationInfoAsUser(str, android.content.pm.PackageManager.ApplicationInfoFlags.of(0L), userHandle);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.projection.MediaProjectionManagerService.MediaProjection mediaProjection = new com.android.server.media.projection.MediaProjectionManagerService.MediaProjection(i2, i, str, applicationInfoAsUser.targetSdkVersion, applicationInfoAsUser.isPrivilegedApp());
                if (z) {
                    this.mAppOps.setMode(46, mediaProjection.uid, mediaProjection.packageName, 0);
                }
                return mediaProjection;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.IllegalArgumentException("No package matching :" + str);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.media.projection.MediaProjectionManagerService.MediaProjection getProjectionInternal(int i, java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (this.mProjectionGrant != null && this.mProjectionGrant.mSession != null && this.mProjectionGrant.mSession.isWaitingForConsent()) {
                    if (this.mProjectionGrant.uid != i || !java.util.Objects.equals(this.mProjectionGrant.packageName, str)) {
                        android.util.Slog.e(TAG, "Reusing token: Not possible to reuse the current projection instance due to package details mismatching");
                        return null;
                    }
                    android.util.Slog.v(TAG, "Reusing token: getProjection can reuse the current projection");
                    return this.mProjectionGrant;
                }
                android.util.Slog.e(TAG, "Reusing token: Not possible to reuse the current projection instance");
                return null;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.media.projection.MediaProjectionInfo getActiveProjectionInfo() {
        synchronized (this.mLock) {
            try {
                if (this.mProjectionGrant == null) {
                    return null;
                }
                return this.mProjectionGrant.getProjectionInfo();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("MEDIA PROJECTION MANAGER (dumpsys media_projection)");
        synchronized (this.mLock) {
            try {
                printWriter.println("Media Projection: ");
                if (this.mProjectionGrant != null) {
                    this.mProjectionGrant.dump(printWriter);
                } else {
                    printWriter.println("null");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private final class BinderService extends android.media.projection.IMediaProjectionManager.Stub {
        BinderService(android.content.Context context) {
            super(android.os.PermissionEnforcer.fromContext(context));
        }

        public boolean hasProjectionPermission(int i, java.lang.String str) {
            boolean z;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!checkPermission(str, "android.permission.CAPTURE_VIDEO_OUTPUT")) {
                    if (com.android.server.media.projection.MediaProjectionManagerService.this.mAppOps.noteOpNoThrow(46, i, str) != 0) {
                        z = false;
                        return z | false;
                    }
                }
                z = true;
                return z | false;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.media.projection.IMediaProjection createProjection(int i, java.lang.String str, int i2, boolean z) {
            if (com.android.server.media.projection.MediaProjectionManagerService.this.mContext.checkCallingPermission("android.permission.MANAGE_MEDIA_PROJECTION") != 0) {
                throw new java.lang.SecurityException("Requires MANAGE_MEDIA_PROJECTION in order to grant projection permission");
            }
            if (str == null || str.isEmpty()) {
                throw new java.lang.IllegalArgumentException("package name must not be empty");
            }
            return com.android.server.media.projection.MediaProjectionManagerService.this.createProjectionInternal(i, str, i2, z, android.os.Binder.getCallingUserHandle());
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public android.media.projection.IMediaProjection getProjection(int i, java.lang.String str) {
            getProjection_enforcePermission();
            if (str == null || str.isEmpty()) {
                throw new java.lang.IllegalArgumentException("package name must not be empty");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.media.projection.MediaProjectionManagerService.this.getProjectionInternal(i, str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public boolean isCurrentProjection(android.media.projection.IMediaProjection iMediaProjection) {
            isCurrentProjection_enforcePermission();
            return com.android.server.media.projection.MediaProjectionManagerService.this.isCurrentProjection(iMediaProjection == null ? null : iMediaProjection.asBinder());
        }

        public android.media.projection.MediaProjectionInfo getActiveProjectionInfo() {
            if (com.android.server.media.projection.MediaProjectionManagerService.this.mContext.checkCallingPermission("android.permission.MANAGE_MEDIA_PROJECTION") != 0) {
                throw new java.lang.SecurityException("Requires MANAGE_MEDIA_PROJECTION in order to get active projection info");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.media.projection.MediaProjectionManagerService.this.getActiveProjectionInfo();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public void stopActiveProjection() {
            stopActiveProjection_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.media.projection.MediaProjectionManagerService.this.mLock) {
                    try {
                        if (com.android.server.media.projection.MediaProjectionManagerService.this.mProjectionGrant != null) {
                            android.util.Slog.d(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Content Recording: Stopping active projection");
                            com.android.server.media.projection.MediaProjectionManagerService.this.mProjectionGrant.stop();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public void notifyActiveProjectionCapturedContentResized(int i, int i2) {
            notifyActiveProjectionCapturedContentResized_enforcePermission();
            synchronized (com.android.server.media.projection.MediaProjectionManagerService.this.mLock) {
                try {
                    if (isCurrentProjection(com.android.server.media.projection.MediaProjectionManagerService.this.mProjectionGrant)) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            synchronized (com.android.server.media.projection.MediaProjectionManagerService.this.mLock) {
                                try {
                                    if (com.android.server.media.projection.MediaProjectionManagerService.this.mProjectionGrant != null && com.android.server.media.projection.MediaProjectionManagerService.this.mCallbackDelegate != null) {
                                        com.android.server.media.projection.MediaProjectionManagerService.this.mCallbackDelegate.dispatchResize(com.android.server.media.projection.MediaProjectionManagerService.this.mProjectionGrant, i, i2);
                                    }
                                } finally {
                                }
                            }
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } finally {
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) {
            notifyActiveProjectionCapturedContentVisibilityChanged_enforcePermission();
            synchronized (com.android.server.media.projection.MediaProjectionManagerService.this.mLock) {
                try {
                    if (isCurrentProjection(com.android.server.media.projection.MediaProjectionManagerService.this.mProjectionGrant)) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            synchronized (com.android.server.media.projection.MediaProjectionManagerService.this.mLock) {
                                try {
                                    if (com.android.server.media.projection.MediaProjectionManagerService.this.mProjectionGrant != null && com.android.server.media.projection.MediaProjectionManagerService.this.mCallbackDelegate != null) {
                                        com.android.server.media.projection.MediaProjectionManagerService.this.mCallbackDelegate.dispatchVisibilityChanged(com.android.server.media.projection.MediaProjectionManagerService.this.mProjectionGrant, z);
                                    }
                                } finally {
                                }
                            }
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } finally {
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public android.media.projection.MediaProjectionInfo addCallback(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
            addCallback_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.media.projection.MediaProjectionManagerService.this.addCallback(iMediaProjectionWatcherCallback);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeCallback(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
            if (com.android.server.media.projection.MediaProjectionManagerService.this.mContext.checkCallingPermission("android.permission.MANAGE_MEDIA_PROJECTION") != 0) {
                throw new java.lang.SecurityException("Requires MANAGE_MEDIA_PROJECTION in order to remove projection callbacks");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.projection.MediaProjectionManagerService.this.removeCallback(iMediaProjectionWatcherCallback);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public boolean setContentRecordingSession(@android.annotation.Nullable android.view.ContentRecordingSession contentRecordingSession, @android.annotation.NonNull android.media.projection.IMediaProjection iMediaProjection) {
            setContentRecordingSession_enforcePermission();
            synchronized (com.android.server.media.projection.MediaProjectionManagerService.this.mLock) {
                if (!isCurrentProjection(iMediaProjection)) {
                    throw new java.lang.SecurityException("Unable to set ContentRecordingSession on non-current MediaProjection");
                }
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.media.projection.MediaProjectionManagerService.this.setContentRecordingSession(contentRecordingSession);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public void requestConsentForInvalidProjection(@android.annotation.NonNull android.media.projection.IMediaProjection iMediaProjection) {
            requestConsentForInvalidProjection_enforcePermission();
            synchronized (com.android.server.media.projection.MediaProjectionManagerService.this.mLock) {
                try {
                    if (!isCurrentProjection(iMediaProjection)) {
                        android.util.Slog.v(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Reusing token: Won't request consent again for a token that isn't current");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        com.android.server.media.projection.MediaProjectionManagerService.this.requestConsentForInvalidProjection();
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public void setUserReviewGrantedConsentResult(@android.media.projection.ReviewGrantedConsentResult int i, @android.annotation.Nullable android.media.projection.IMediaProjection iMediaProjection) {
            setUserReviewGrantedConsentResult_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.projection.MediaProjectionManagerService.this.setUserReviewGrantedConsentResult(i, iMediaProjection);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public void notifyPermissionRequestInitiated(int i, int i2) {
            notifyPermissionRequestInitiated_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.projection.MediaProjectionManagerService.this.notifyPermissionRequestInitiated(i, i2);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public void notifyPermissionRequestDisplayed(int i) {
            notifyPermissionRequestDisplayed_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.projection.MediaProjectionManagerService.this.notifyPermissionRequestDisplayed(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public void notifyPermissionRequestCancelled(int i) {
            notifyPermissionRequestCancelled_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.projection.MediaProjectionManagerService.this.notifyPermissionRequestCancelled(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public void notifyAppSelectorDisplayed(int i) {
            notifyAppSelectorDisplayed_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.projection.MediaProjectionManagerService.this.notifyAppSelectorDisplayed(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public void notifyWindowingModeChanged(int i, int i2, int i3) {
            notifyWindowingModeChanged_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.projection.MediaProjectionManagerService.this.notifyWindowingModeChanged(i, i2, i3);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.media.projection.MediaProjectionManagerService.this.mContext, com.android.server.media.projection.MediaProjectionManagerService.TAG, printWriter)) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.media.projection.MediaProjectionManagerService.this.dump(printWriter);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        private boolean checkPermission(java.lang.String str, java.lang.String str2) {
            return com.android.server.media.projection.MediaProjectionManagerService.this.mContext.getPackageManager().checkPermission(str2, str) == 0;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class MediaProjection extends android.media.projection.IMediaProjection.Stub {
        private android.media.projection.IMediaProjectionCallback mCallback;
        private final long mCreateTimeMs;
        private android.os.IBinder.DeathRecipient mDeathEater;
        private final boolean mIsPrivileged;
        private boolean mRestoreSystemAlertWindow;
        private android.view.ContentRecordingSession mSession;
        private final int mTargetSdkVersion;
        private android.os.IBinder mToken;
        private final int mType;
        public final java.lang.String packageName;
        public final int uid;
        public final android.os.UserHandle userHandle;
        final long mDefaultTimeoutMs = java.time.Duration.ofMinutes(5).toMillis();
        private android.app.ActivityOptions.LaunchCookie mLaunchCookie = null;
        private long mTimeoutMs = this.mDefaultTimeoutMs;
        private int mCountStarts = 0;
        private int mVirtualDisplayId = -1;

        MediaProjection(int i, int i2, java.lang.String str, int i3, boolean z) {
            this.mType = i;
            this.uid = i2;
            this.packageName = str;
            this.userHandle = new android.os.UserHandle(android.os.UserHandle.getUserId(i2));
            this.mTargetSdkVersion = i3;
            this.mIsPrivileged = z;
            this.mCreateTimeMs = com.android.server.media.projection.MediaProjectionManagerService.this.mClock.uptimeMillis();
            com.android.server.media.projection.MediaProjectionManagerService.this.mActivityManagerInternal.notifyMediaProjectionEvent(i2, asBinder(), 0);
        }

        int getVirtualDisplayId() {
            return this.mVirtualDisplayId;
        }

        public boolean canProjectVideo() {
            return this.mType == 1 || this.mType == 0;
        }

        public boolean canProjectSecureVideo() {
            return false;
        }

        public boolean canProjectAudio() {
            return this.mType == 1 || this.mType == 2 || this.mType == 0;
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public int applyVirtualDisplayFlags(int i) {
            applyVirtualDisplayFlags_enforcePermission();
            if (this.mType == 0) {
                return (i & (-9)) | 18;
            }
            if (this.mType == 1) {
                return (i & (-18)) | 10;
            }
            if (this.mType == 2) {
                return (i & (-9)) | 19;
            }
            throw new java.lang.RuntimeException("Unknown MediaProjection type");
        }

        public void start(final android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) {
            if (iMediaProjectionCallback == null) {
                throw new java.lang.IllegalArgumentException("callback must not be null");
            }
            android.util.Slog.v(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Start the token instance " + this);
            boolean hasRunningForegroundService = com.android.server.media.projection.MediaProjectionManagerService.this.mActivityManagerInternal.hasRunningForegroundService(this.uid, 32);
            synchronized (com.android.server.media.projection.MediaProjectionManagerService.this.mLock) {
                try {
                    if (com.android.server.media.projection.MediaProjectionManagerService.this.isCurrentProjection(asBinder())) {
                        android.util.Slog.w(com.android.server.media.projection.MediaProjectionManagerService.TAG, "UID " + android.os.Binder.getCallingUid() + " attempted to start already started MediaProjection");
                        this.mCountStarts = this.mCountStarts + 1;
                        return;
                    }
                    if (requiresForegroundService() && !hasRunningForegroundService) {
                        throw new java.lang.SecurityException("Media projections require a foreground service of type ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION");
                    }
                    try {
                        this.mToken = iMediaProjectionCallback.asBinder();
                        this.mDeathEater = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.media.projection.MediaProjectionManagerService$MediaProjection$$ExternalSyntheticLambda1
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                com.android.server.media.projection.MediaProjectionManagerService.MediaProjection.this.lambda$start$0(iMediaProjectionCallback);
                            }
                        };
                        this.mToken.linkToDeath(this.mDeathEater, 0);
                        if (this.mType == 0) {
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                try {
                                    if (com.android.internal.util.ArrayUtils.contains(com.android.server.media.projection.MediaProjectionManagerService.this.mPackageManager.getPackageInfoAsUser(this.packageName, 4096, android.os.UserHandle.getUserId(this.uid)).requestedPermissions, "android.permission.SYSTEM_ALERT_WINDOW") && com.android.server.media.projection.MediaProjectionManagerService.this.mAppOps.unsafeCheckOpRawNoThrow(24, this.uid, this.packageName) == 3) {
                                        com.android.server.media.projection.MediaProjectionManagerService.this.mAppOps.setMode(24, this.uid, this.packageName, 0);
                                        this.mRestoreSystemAlertWindow = true;
                                    }
                                } finally {
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                }
                            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                android.util.Slog.w(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Package not found, aborting MediaProjection", e);
                                return;
                            }
                        }
                        com.android.server.media.projection.MediaProjectionManagerService.this.startProjectionLocked(this);
                        this.mCallback = iMediaProjectionCallback;
                        registerCallback(this.mCallback);
                        this.mCountStarts++;
                    } catch (android.os.RemoteException e2) {
                        android.util.Slog.w(com.android.server.media.projection.MediaProjectionManagerService.TAG, "MediaProjectionCallbacks must be valid, aborting MediaProjection", e2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$0(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) {
            android.util.Slog.d(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Content Recording: MediaProjection stopped by Binder death - id= " + this.mVirtualDisplayId);
            com.android.server.media.projection.MediaProjectionManagerService.this.mCallbackDelegate.remove(iMediaProjectionCallback);
            stop();
        }

        public void stop() {
            synchronized (com.android.server.media.projection.MediaProjectionManagerService.this.mLock) {
                try {
                    if (!com.android.server.media.projection.MediaProjectionManagerService.this.isCurrentProjection(asBinder())) {
                        android.util.Slog.w(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Attempted to stop inactive MediaProjection (uid=" + android.os.Binder.getCallingUid() + ", pid=" + android.os.Binder.getCallingPid() + ")");
                        return;
                    }
                    if (this.mRestoreSystemAlertWindow) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            if (com.android.server.media.projection.MediaProjectionManagerService.this.mAppOps.unsafeCheckOpRawNoThrow(24, this.uid, this.packageName) == 0) {
                                com.android.server.media.projection.MediaProjectionManagerService.this.mAppOps.setMode(24, this.uid, this.packageName, 3);
                            }
                            this.mRestoreSystemAlertWindow = false;
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (java.lang.Throwable th) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    android.util.Slog.d(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Content Recording: handling stopping this projection token createTime= " + this.mCreateTimeMs + " countStarts= " + this.mCountStarts);
                    com.android.server.media.projection.MediaProjectionManagerService.this.stopProjectionLocked(this);
                    this.mToken.unlinkToDeath(this.mDeathEater, 0);
                    this.mToken = null;
                    unregisterCallback(this.mCallback);
                    this.mCallback = null;
                    com.android.server.media.projection.MediaProjectionManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.projection.MediaProjectionManagerService$MediaProjection$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.media.projection.MediaProjectionManagerService.MediaProjection.this.lambda$stop$1();
                        }
                    });
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stop$1() {
            com.android.server.media.projection.MediaProjectionManagerService.this.mActivityManagerInternal.notifyMediaProjectionEvent(this.uid, asBinder(), 1);
        }

        public void registerCallback(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) {
            if (iMediaProjectionCallback == null) {
                throw new java.lang.IllegalArgumentException("callback must not be null");
            }
            com.android.server.media.projection.MediaProjectionManagerService.this.mCallbackDelegate.add(iMediaProjectionCallback);
        }

        public void unregisterCallback(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) {
            if (iMediaProjectionCallback == null) {
                throw new java.lang.IllegalArgumentException("callback must not be null");
            }
            com.android.server.media.projection.MediaProjectionManagerService.this.mCallbackDelegate.remove(iMediaProjectionCallback);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public void setLaunchCookie(android.app.ActivityOptions.LaunchCookie launchCookie) {
            setLaunchCookie_enforcePermission();
            this.mLaunchCookie = launchCookie;
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public android.app.ActivityOptions.LaunchCookie getLaunchCookie() {
            getLaunchCookie_enforcePermission();
            return this.mLaunchCookie;
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public boolean isValid() {
            isValid_enforcePermission();
            synchronized (com.android.server.media.projection.MediaProjectionManagerService.this.mLock) {
                try {
                    if (((((com.android.server.media.projection.MediaProjectionManagerService.this.mClock.uptimeMillis() - this.mCreateTimeMs) > this.mTimeoutMs ? 1 : ((com.android.server.media.projection.MediaProjectionManagerService.this.mClock.uptimeMillis() - this.mCreateTimeMs) == this.mTimeoutMs ? 0 : -1)) > 0) || this.mCountStarts > 1 || (this.mVirtualDisplayId != -1)) ? false : true) {
                        return true;
                    }
                    if (!com.android.server.media.projection.MediaProjectionManagerService.this.mInjector.shouldMediaProjectionPreventReusingConsent(com.android.server.media.projection.MediaProjectionManagerService.this.mProjectionGrant)) {
                        return false;
                    }
                    android.util.Slog.v(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Reusing token: Throw exception due to invalid projection.");
                    com.android.server.media.projection.MediaProjectionManagerService.this.mProjectionGrant.stop();
                    throw new java.lang.SecurityException("Don't re-use the resultData to retrieve the same projection instance, and don't use a token that has timed out. Don't take multiple captures by invoking MediaProjection#createVirtualDisplay multiple times on the same instance.");
                } finally {
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_MEDIA_PROJECTION")
        public void notifyVirtualDisplayCreated(int i) {
            notifyVirtualDisplayCreated_enforcePermission();
            synchronized (com.android.server.media.projection.MediaProjectionManagerService.this.mLock) {
                try {
                    this.mVirtualDisplayId = i;
                    if (this.mSession != null && this.mSession.getVirtualDisplayId() == -1) {
                        android.util.Slog.v(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Virtual display now created, so update session with the virtual display id");
                        this.mSession.setVirtualDisplayId(this.mVirtualDisplayId);
                        if (!com.android.server.media.projection.MediaProjectionManagerService.this.setContentRecordingSession(this.mSession)) {
                            android.util.Slog.e(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Failed to set session for virtual display id");
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.media.projection.MediaProjectionInfo getProjectionInfo() {
            return new android.media.projection.MediaProjectionInfo(this.packageName, this.userHandle, this.mLaunchCookie);
        }

        boolean requiresForegroundService() {
            return this.mTargetSdkVersion >= 29 && !this.mIsPrivileged;
        }

        public void dump(java.io.PrintWriter printWriter) {
            printWriter.println("(" + this.packageName + ", uid=" + this.uid + "): " + com.android.server.media.projection.MediaProjectionManagerService.typeToString(this.mType));
        }
    }

    private class MediaRouterCallback extends android.media.MediaRouter.SimpleCallback {
        private MediaRouterCallback() {
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteSelected(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo) {
            synchronized (com.android.server.media.projection.MediaProjectionManagerService.this.mLock) {
                if ((i & 4) != 0) {
                    try {
                        com.android.server.media.projection.MediaProjectionManagerService.this.mMediaRouteInfo = routeInfo;
                        if (com.android.server.media.projection.MediaProjectionManagerService.this.mProjectionGrant != null) {
                            android.util.Slog.d(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Content Recording: Stopped MediaProjection due to route type of REMOTE_DISPLAY not selected");
                            com.android.server.media.projection.MediaProjectionManagerService.this.mProjectionGrant.stop();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteUnselected(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo) {
            if (com.android.server.media.projection.MediaProjectionManagerService.this.mMediaRouteInfo == routeInfo) {
                com.android.server.media.projection.MediaProjectionManagerService.this.mMediaRouteInfo = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallbackDelegate {
        private android.os.Handler mHandler;
        private final java.lang.Object mLock = new java.lang.Object();
        private java.util.Map<android.os.IBinder, android.media.projection.IMediaProjectionCallback> mClientCallbacks = new android.util.ArrayMap();
        private java.util.Map<android.os.IBinder, android.media.projection.IMediaProjectionWatcherCallback> mWatcherCallbacks = new android.util.ArrayMap();

        CallbackDelegate(android.os.Looper looper) {
            this.mHandler = new android.os.Handler(looper, null, true);
        }

        public void add(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) {
            synchronized (this.mLock) {
                this.mClientCallbacks.put(iMediaProjectionCallback.asBinder(), iMediaProjectionCallback);
            }
        }

        public void add(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
            synchronized (this.mLock) {
                this.mWatcherCallbacks.put(iMediaProjectionWatcherCallback.asBinder(), iMediaProjectionWatcherCallback);
            }
        }

        public void remove(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) {
            synchronized (this.mLock) {
                this.mClientCallbacks.remove(iMediaProjectionCallback.asBinder());
            }
        }

        public void remove(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
            synchronized (this.mLock) {
                this.mWatcherCallbacks.remove(iMediaProjectionWatcherCallback.asBinder());
            }
        }

        public void dispatchStart(com.android.server.media.projection.MediaProjectionManagerService.MediaProjection mediaProjection) {
            if (mediaProjection == null) {
                android.util.Slog.e(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Tried to dispatch start notification for a null media projection. Ignoring!");
                return;
            }
            synchronized (this.mLock) {
                try {
                    for (android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback : this.mWatcherCallbacks.values()) {
                        this.mHandler.post(new com.android.server.media.projection.MediaProjectionManagerService.WatcherStartCallback(mediaProjection.getProjectionInfo(), iMediaProjectionWatcherCallback));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void dispatchStop(com.android.server.media.projection.MediaProjectionManagerService.MediaProjection mediaProjection) {
            if (mediaProjection == null) {
                android.util.Slog.e(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Tried to dispatch stop notification for a null media projection. Ignoring!");
                return;
            }
            synchronized (this.mLock) {
                try {
                    java.util.Iterator<android.media.projection.IMediaProjectionCallback> it = this.mClientCallbacks.values().iterator();
                    while (it.hasNext()) {
                        this.mHandler.post(new com.android.server.media.projection.MediaProjectionManagerService.ClientStopCallback(it.next()));
                    }
                    for (android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback : this.mWatcherCallbacks.values()) {
                        this.mHandler.post(new com.android.server.media.projection.MediaProjectionManagerService.WatcherStopCallback(mediaProjection.getProjectionInfo(), iMediaProjectionWatcherCallback));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void dispatchSession(@android.annotation.NonNull android.media.projection.MediaProjectionInfo mediaProjectionInfo, @android.annotation.Nullable android.view.ContentRecordingSession contentRecordingSession) {
            synchronized (this.mLock) {
                try {
                    java.util.Iterator<android.media.projection.IMediaProjectionWatcherCallback> it = this.mWatcherCallbacks.values().iterator();
                    while (it.hasNext()) {
                        this.mHandler.post(new com.android.server.media.projection.MediaProjectionManagerService.WatcherSessionCallback(it.next(), mediaProjectionInfo, contentRecordingSession));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void dispatchResize(com.android.server.media.projection.MediaProjectionManagerService.MediaProjection mediaProjection, final int i, final int i2) {
            if (mediaProjection == null) {
                android.util.Slog.e(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Tried to dispatch resize notification for a null media projection. Ignoring!");
                return;
            }
            synchronized (this.mLock) {
                try {
                    for (final android.media.projection.IMediaProjectionCallback iMediaProjectionCallback : this.mClientCallbacks.values()) {
                        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.projection.MediaProjectionManagerService$CallbackDelegate$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.media.projection.MediaProjectionManagerService.CallbackDelegate.lambda$dispatchResize$0(iMediaProjectionCallback, i, i2);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$dispatchResize$0(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback, int i, int i2) {
            try {
                iMediaProjectionCallback.onCapturedContentResize(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Failed to notify media projection has resized to " + i + " x " + i2, e);
            }
        }

        public void dispatchVisibilityChanged(com.android.server.media.projection.MediaProjectionManagerService.MediaProjection mediaProjection, final boolean z) {
            if (mediaProjection == null) {
                android.util.Slog.e(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Tried to dispatch visibility changed notification for a null media projection. Ignoring!");
                return;
            }
            synchronized (this.mLock) {
                try {
                    for (final android.media.projection.IMediaProjectionCallback iMediaProjectionCallback : this.mClientCallbacks.values()) {
                        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.projection.MediaProjectionManagerService$CallbackDelegate$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.media.projection.MediaProjectionManagerService.CallbackDelegate.lambda$dispatchVisibilityChanged$1(iMediaProjectionCallback, z);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$dispatchVisibilityChanged$1(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback, boolean z) {
            try {
                iMediaProjectionCallback.onCapturedContentVisibilityChanged(z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Failed to notify media projection has captured content visibility change to " + z, e);
            }
        }
    }

    private static final class WatcherStartCallback implements java.lang.Runnable {
        private android.media.projection.IMediaProjectionWatcherCallback mCallback;
        private android.media.projection.MediaProjectionInfo mInfo;

        public WatcherStartCallback(android.media.projection.MediaProjectionInfo mediaProjectionInfo, android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
            this.mInfo = mediaProjectionInfo;
            this.mCallback = iMediaProjectionWatcherCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mCallback.onStart(this.mInfo);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Failed to notify media projection has started", e);
            }
        }
    }

    private static final class WatcherStopCallback implements java.lang.Runnable {
        private android.media.projection.IMediaProjectionWatcherCallback mCallback;
        private android.media.projection.MediaProjectionInfo mInfo;

        public WatcherStopCallback(android.media.projection.MediaProjectionInfo mediaProjectionInfo, android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
            this.mInfo = mediaProjectionInfo;
            this.mCallback = iMediaProjectionWatcherCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mCallback.onStop(this.mInfo);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Failed to notify media projection has stopped", e);
            }
        }
    }

    private static final class ClientStopCallback implements java.lang.Runnable {
        private android.media.projection.IMediaProjectionCallback mCallback;

        public ClientStopCallback(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) {
            this.mCallback = iMediaProjectionCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mCallback.onStop();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Failed to notify media projection has stopped", e);
            }
        }
    }

    private static final class WatcherSessionCallback implements java.lang.Runnable {
        private final android.media.projection.IMediaProjectionWatcherCallback mCallback;
        private final android.media.projection.MediaProjectionInfo mProjectionInfo;
        private final android.view.ContentRecordingSession mSession;

        WatcherSessionCallback(@android.annotation.NonNull android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback, @android.annotation.NonNull android.media.projection.MediaProjectionInfo mediaProjectionInfo, @android.annotation.Nullable android.view.ContentRecordingSession contentRecordingSession) {
            this.mCallback = iMediaProjectionWatcherCallback;
            this.mProjectionInfo = mediaProjectionInfo;
            this.mSession = contentRecordingSession;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mCallback.onRecordingSessionSet(this.mProjectionInfo, this.mSession);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.projection.MediaProjectionManagerService.TAG, "Failed to notify content recording session changed", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String typeToString(int i) {
        switch (i) {
            case 0:
                return "TYPE_SCREEN_CAPTURE";
            case 1:
                return "TYPE_MIRRORING";
            case 2:
                return "TYPE_PRESENTATION";
            default:
                return java.lang.Integer.toString(i);
        }
    }
}
