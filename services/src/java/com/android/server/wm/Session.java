package com.android.server.wm;

/* loaded from: classes3.dex */
class Session extends android.view.IWindowSession.Stub implements android.os.IBinder.DeathRecipient {
    private final java.util.ArrayList<com.android.server.wm.WindowState> mAddedWindows;
    private com.android.server.wm.AlertWindowNotification mAlertWindowNotification;
    private final android.util.ArraySet<com.android.server.wm.WindowSurfaceController> mAlertWindowSurfaces;
    final android.view.IWindowSessionCallback mCallback;
    final boolean mCanAddInternalSystemWindow;
    final boolean mCanAlwaysUpdateWallpaper;
    final boolean mCanCreateSystemApplicationOverlay;
    boolean mCanForceShowingInsets;
    final boolean mCanHideNonSystemOverlayWindows;
    final boolean mCanSetUnrestrictedGestureExclusion;
    private final boolean mCanStartTasksFromRecents;
    private boolean mClientDead;
    private final com.android.server.wm.DragDropController mDragDropController;
    private final android.view.InsetsSourceControl.Array mDummyControls;
    private float mLastReportedAnimatorScale;
    protected java.lang.String mPackageName;
    final int mPid;

    @android.annotation.NonNull
    final com.android.server.wm.WindowProcessController mProcess;
    private java.lang.String mRelayoutTag;
    final com.android.server.wm.WindowManagerService mService;
    final boolean mSetsUnrestrictedKeepClearAreas;
    private boolean mShowingAlertWindowNotificationAllowed;
    private final java.lang.String mStringName;
    android.view.SurfaceSession mSurfaceSession;
    final int mUid;

    public Session(com.android.server.wm.WindowManagerService windowManagerService, android.view.IWindowSessionCallback iWindowSessionCallback) {
        this(windowManagerService, iWindowSessionCallback, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
    }

    @com.android.internal.annotations.VisibleForTesting
    Session(com.android.server.wm.WindowManagerService windowManagerService, android.view.IWindowSessionCallback iWindowSessionCallback, int i, int i2) {
        this.mAddedWindows = new java.util.ArrayList<>();
        this.mAlertWindowSurfaces = new android.util.ArraySet<>();
        this.mClientDead = false;
        this.mDummyControls = new android.view.InsetsSourceControl.Array();
        this.mService = windowManagerService;
        this.mCallback = iWindowSessionCallback;
        this.mPid = i;
        this.mUid = i2;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mLastReportedAnimatorScale = windowManagerService.getCurrentAnimatorScale();
                this.mProcess = windowManagerService.mAtmService.mProcessMap.getProcess(this.mPid);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        if (this.mProcess == null) {
            throw new java.lang.IllegalStateException("Unknown pid=" + this.mPid + " uid=" + this.mUid);
        }
        this.mCanAddInternalSystemWindow = windowManagerService.mContext.checkCallingOrSelfPermission("android.permission.INTERNAL_SYSTEM_WINDOW") == 0;
        this.mCanForceShowingInsets = windowManagerService.mAtmService.isCallerRecents(this.mUid) || windowManagerService.mContext.checkCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE") == 0;
        this.mCanHideNonSystemOverlayWindows = windowManagerService.mContext.checkCallingOrSelfPermission("android.permission.HIDE_NON_SYSTEM_OVERLAY_WINDOWS") == 0 || windowManagerService.mContext.checkCallingOrSelfPermission("android.permission.HIDE_OVERLAY_WINDOWS") == 0;
        this.mCanCreateSystemApplicationOverlay = windowManagerService.mContext.checkCallingOrSelfPermission("android.permission.SYSTEM_APPLICATION_OVERLAY") == 0;
        this.mCanStartTasksFromRecents = windowManagerService.mContext.checkCallingOrSelfPermission("android.permission.START_TASKS_FROM_RECENTS") == 0;
        this.mSetsUnrestrictedKeepClearAreas = windowManagerService.mContext.checkCallingOrSelfPermission("android.permission.SET_UNRESTRICTED_KEEP_CLEAR_AREAS") == 0;
        this.mCanSetUnrestrictedGestureExclusion = windowManagerService.mContext.checkCallingOrSelfPermission("android.permission.SET_UNRESTRICTED_GESTURE_EXCLUSION") == 0;
        this.mCanAlwaysUpdateWallpaper = com.android.window.flags.Flags.alwaysUpdateWallpaperPermission() && windowManagerService.mContext.checkCallingOrSelfPermission("android.permission.ALWAYS_UPDATE_WALLPAPER") == 0;
        this.mShowingAlertWindowNotificationAllowed = this.mService.mShowAlertWindowNotifications;
        this.mDragDropController = this.mService.mDragDropController;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Session{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(" ");
        sb.append(this.mPid);
        if (this.mUid < 10000) {
            sb.append(":");
            sb.append(this.mUid);
        } else {
            sb.append(":u");
            sb.append(android.os.UserHandle.getUserId(this.mUid));
            sb.append('a');
            sb.append(android.os.UserHandle.getAppId(this.mUid));
        }
        sb.append("}");
        this.mStringName = sb.toString();
        try {
            this.mCallback.asBinder().linkToDeath(this, 0);
        } catch (android.os.RemoteException e) {
            this.mClientDead = true;
        }
    }

    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (java.lang.RuntimeException e) {
            if (!(e instanceof java.lang.SecurityException)) {
                android.util.Slog.wtf("WindowManager", "Window Session Crash", e);
            }
            throw e;
        }
    }

    boolean isClientDead() {
        return this.mClientDead;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mCallback.asBinder().unlinkToDeath(this, 0);
                this.mClientDead = true;
                try {
                    for (int size = this.mAddedWindows.size() - 1; size >= 0; size--) {
                        com.android.server.wm.WindowState windowState = this.mAddedWindows.get(size);
                        android.util.Slog.i("WindowManager", "WIN DEATH: " + windowState);
                        if (windowState.mActivityRecord != null && windowState.mActivityRecord.findMainWindow() == windowState) {
                            this.mService.mSnapshotController.onAppDied(windowState.mActivityRecord);
                        }
                        windowState.removeIfPossible();
                    }
                    killSessionLocked();
                } catch (java.lang.Throwable th) {
                    killSessionLocked();
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public int addToDisplay(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, android.view.InputChannel inputChannel, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.graphics.Rect rect, float[] fArr) {
        return this.mService.addWindow(this, iWindow, layoutParams, i, i2, android.os.UserHandle.getUserId(this.mUid), i3, inputChannel, insetsState, array, rect, fArr);
    }

    public int addToDisplayAsUser(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, android.view.InputChannel inputChannel, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.graphics.Rect rect, float[] fArr) {
        return this.mService.addWindow(this, iWindow, layoutParams, i, i2, i3, i4, inputChannel, insetsState, array, rect, fArr);
    }

    public int addToDisplayWithoutInputChannel(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, android.view.InsetsState insetsState, android.graphics.Rect rect, float[] fArr) {
        return this.mService.addWindow(this, iWindow, layoutParams, i, i2, android.os.UserHandle.getUserId(this.mUid), android.view.WindowInsets.Type.defaultVisible(), null, insetsState, this.mDummyControls, rect, fArr);
    }

    public void remove(android.os.IBinder iBinder) {
        this.mService.removeClientToken(this, iBinder);
    }

    public boolean cancelDraw(android.view.IWindow iWindow) {
        return this.mService.cancelDraw(this, iWindow);
    }

    public int relayout(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, android.window.ClientWindowFrames clientWindowFrames, android.util.MergedConfiguration mergedConfiguration, android.view.SurfaceControl surfaceControl, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.os.Bundle bundle) {
        android.os.Trace.traceBegin(32L, this.mRelayoutTag);
        int relayoutWindow = this.mService.relayoutWindow(this, iWindow, layoutParams, i, i2, i3, i4, i5, i6, clientWindowFrames, mergedConfiguration, surfaceControl, insetsState, array, bundle);
        android.os.Trace.traceEnd(32L);
        return relayoutWindow;
    }

    public void relayoutAsync(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6) {
        relayout(iWindow, layoutParams, i, i2, i3, i4, i5, i6, null, null, null, null, null, null);
    }

    public boolean outOfMemory(android.view.IWindow iWindow) {
        return this.mService.outOfMemoryWindow(this, iWindow);
    }

    public void setInsets(android.view.IWindow iWindow, int i, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Region region) {
        this.mService.setInsetsWindow(this, iWindow, i, rect, rect2, region);
    }

    public void clearTouchableRegion(android.view.IWindow iWindow) {
        this.mService.clearTouchableRegion(this, iWindow);
    }

    public void finishDrawing(android.view.IWindow iWindow, @android.annotation.Nullable android.view.SurfaceControl.Transaction transaction, int i) {
        if (android.os.Trace.isTagEnabled(32L)) {
            android.os.Trace.traceBegin(32L, "finishDrawing: " + this.mPackageName);
        }
        this.mService.finishDrawingWindow(this, iWindow, transaction, i);
        android.os.Trace.traceEnd(32L);
    }

    public boolean performHapticFeedback(int i, boolean z, boolean z2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mService.mPolicy.performHapticFeedback(this.mUid, this.mPackageName, i, z, null, z2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void performHapticFeedbackAsync(int i, boolean z, boolean z2) {
        performHapticFeedback(i, z, z2);
    }

    public android.os.IBinder performDrag(android.view.IWindow iWindow, int i, android.view.SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, android.content.ClipData clipData) {
        validateAndResolveDragMimeTypeExtras(clipData, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), this.mPackageName);
        validateDragFlags(i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mDragDropController.performDrag(this.mPid, this.mUid, iWindow, i, surfaceControl, i2, i3, i4, f, f2, f3, f4, clipData);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean dropForAccessibility(android.view.IWindow iWindow, int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mDragDropController.dropForAccessibility(iWindow, i, i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void validateDragFlags(int i) {
        if ((i & 2048) != 0 && !this.mCanStartTasksFromRecents) {
            throw new java.lang.SecurityException("Requires START_TASKS_FROM_RECENTS permission");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void validateAndResolveDragMimeTypeExtras(android.content.ClipData clipData, int i, int i2, java.lang.String str) {
        android.content.ClipDescription description = clipData != null ? clipData.getDescription() : null;
        if (description == null) {
            return;
        }
        boolean hasMimeType = description.hasMimeType("application/vnd.android.activity");
        boolean hasMimeType2 = description.hasMimeType("application/vnd.android.shortcut");
        boolean hasMimeType3 = description.hasMimeType("application/vnd.android.task");
        int i3 = (hasMimeType ? 1 : 0) + (hasMimeType2 ? 1 : 0) + (hasMimeType3 ? 1 : 0);
        if (i3 == 0) {
            return;
        }
        if (i3 > 1) {
            throw new java.lang.IllegalArgumentException("Can not specify more than one of activity, shortcut, or task mime types");
        }
        if (clipData.getItemCount() == 0) {
            throw new java.lang.IllegalArgumentException("Unexpected number of items (none)");
        }
        int i4 = 0;
        for (int i5 = 0; i5 < clipData.getItemCount(); i5++) {
            if (clipData.getItemAt(i5).getIntent() == null) {
                throw new java.lang.IllegalArgumentException("Unexpected item, expected an intent");
            }
        }
        if (hasMimeType) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            while (i4 < clipData.getItemCount()) {
                try {
                    android.content.ClipData.Item itemAt = clipData.getItemAt(i4);
                    android.content.Intent intent = itemAt.getIntent();
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) intent.getParcelableExtra("android.intent.extra.PENDING_INTENT");
                    android.os.UserHandle userHandle = (android.os.UserHandle) intent.getParcelableExtra("android.intent.extra.USER");
                    if (pendingIntent == null || userHandle == null) {
                        throw new java.lang.IllegalArgumentException("Clip data must include the pending intent to launch and its associated user to launch for.");
                    }
                    itemAt.setActivityInfo(this.mService.mAtmService.resolveActivityInfoForIntent(this.mService.mAmInternal.getIntentForIntentSender(pendingIntent.getIntentSender().getTarget()), null, userHandle.getIdentifier(), i, i2));
                    i4++;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return;
        }
        if (hasMimeType2) {
            if (!this.mCanStartTasksFromRecents) {
                throw new java.lang.SecurityException("Requires START_TASKS_FROM_RECENTS permission");
            }
            for (int i6 = 0; i6 < clipData.getItemCount(); i6++) {
                android.content.ClipData.Item itemAt2 = clipData.getItemAt(i6);
                android.content.Intent intent2 = itemAt2.getIntent();
                java.lang.String stringExtra = intent2.getStringExtra("android.intent.extra.shortcut.ID");
                java.lang.String stringExtra2 = intent2.getStringExtra("android.intent.extra.PACKAGE_NAME");
                android.os.UserHandle userHandle2 = (android.os.UserHandle) intent2.getParcelableExtra("android.intent.extra.USER");
                if (android.text.TextUtils.isEmpty(stringExtra) || android.text.TextUtils.isEmpty(stringExtra2) || userHandle2 == null) {
                    throw new java.lang.IllegalArgumentException("Clip item must include the package name, shortcut id, and the user to launch for.");
                }
                android.content.Intent[] createShortcutIntents = ((android.content.pm.ShortcutServiceInternal) com.android.server.LocalServices.getService(android.content.pm.ShortcutServiceInternal.class)).createShortcutIntents(android.os.UserHandle.getUserId(i), str, stringExtra2, stringExtra, userHandle2.getIdentifier(), i2, i);
                if (createShortcutIntents == null || createShortcutIntents.length == 0) {
                    throw new java.lang.IllegalArgumentException("Invalid shortcut id");
                }
                itemAt2.setActivityInfo(this.mService.mAtmService.resolveActivityInfoForIntent(createShortcutIntents[0], null, userHandle2.getIdentifier(), i, i2));
            }
            return;
        }
        if (hasMimeType3) {
            if (!this.mCanStartTasksFromRecents) {
                throw new java.lang.SecurityException("Requires START_TASKS_FROM_RECENTS permission");
            }
            while (i4 < clipData.getItemCount()) {
                android.content.ClipData.Item itemAt3 = clipData.getItemAt(i4);
                int intExtra = itemAt3.getIntent().getIntExtra("android.intent.extra.TASK_ID", -1);
                if (intExtra == -1) {
                    throw new java.lang.IllegalArgumentException("Clip item must include the task id.");
                }
                com.android.server.wm.Task anyTaskForId = this.mService.mRoot.anyTaskForId(intExtra);
                if (anyTaskForId == null) {
                    throw new java.lang.IllegalArgumentException("Invalid task id.");
                }
                if (anyTaskForId.getRootActivity() != null) {
                    itemAt3.setActivityInfo(anyTaskForId.getRootActivity().info);
                } else {
                    itemAt3.setActivityInfo(this.mService.mAtmService.resolveActivityInfoForIntent(anyTaskForId.intent, null, anyTaskForId.mUserId, i, i2));
                }
                i4++;
            }
        }
    }

    public void reportDropResult(android.view.IWindow iWindow, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDragDropController.reportDropResult(iWindow, z);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void cancelDragAndDrop(android.os.IBinder iBinder, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDragDropController.cancelDragAndDrop(iBinder, z);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dragRecipientEntered(android.view.IWindow iWindow) {
        this.mDragDropController.dragRecipientEntered(iWindow);
    }

    public void dragRecipientExited(android.view.IWindow iWindow) {
        this.mDragDropController.dragRecipientExited(iWindow);
    }

    public boolean startMovingTask(android.view.IWindow iWindow, float f, float f2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mService.mTaskPositioningController.startMovingTask(iWindow, f, f2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void finishMovingTask(android.view.IWindow iWindow) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mService.mTaskPositioningController.finishTaskPositioning(iWindow);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void reportSystemGestureExclusionChanged(android.view.IWindow iWindow, java.util.List<android.graphics.Rect> list) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mService.reportSystemGestureExclusionChanged(this, iWindow, list);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void reportDecorViewGestureInterceptionChanged(android.view.IWindow iWindow, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mService.reportDecorViewGestureChanged(this, iWindow, z);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void reportKeepClearAreasChanged(android.view.IWindow iWindow, java.util.List<android.graphics.Rect> list, java.util.List<android.graphics.Rect> list2) {
        if (!this.mSetsUnrestrictedKeepClearAreas && !list2.isEmpty()) {
            list2 = java.util.Collections.emptyList();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mService.reportKeepClearAreasChanged(this, iWindow, list, list2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void actionOnWallpaper(android.os.IBinder iBinder, java.util.function.BiConsumer<com.android.server.wm.WallpaperController, com.android.server.wm.WindowState> biConsumer) {
        com.android.server.wm.WindowState windowForClientLocked = this.mService.windowForClientLocked(this, iBinder, true);
        biConsumer.accept(windowForClientLocked.getDisplayContent().mWallpaperController, windowForClientLocked);
    }

    public void setWallpaperPosition(android.os.IBinder iBinder, final float f, final float f2, final float f3, final float f4) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    actionOnWallpaper(iBinder, new java.util.function.BiConsumer() { // from class: com.android.server.wm.Session$$ExternalSyntheticLambda5
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            ((com.android.server.wm.WallpaperController) obj).setWindowWallpaperPosition((com.android.server.wm.WindowState) obj2, f, f2, f3, f4);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setWallpaperZoomOut(android.os.IBinder iBinder, final float f) {
        if (java.lang.Float.compare(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, f) > 0 || java.lang.Float.compare(1.0f, f) < 0 || java.lang.Float.isNaN(f)) {
            throw new java.lang.IllegalArgumentException("Zoom must be a valid float between 0 and 1: " + f);
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    actionOnWallpaper(iBinder, new java.util.function.BiConsumer() { // from class: com.android.server.wm.Session$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            ((com.android.server.wm.WallpaperController) obj).setWallpaperZoomOut((com.android.server.wm.WindowState) obj2, f);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setShouldZoomOutWallpaper(android.os.IBinder iBinder, final boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                actionOnWallpaper(iBinder, new java.util.function.BiConsumer() { // from class: com.android.server.wm.Session$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.wm.WallpaperController) obj).setShouldZoomOutWallpaper((com.android.server.wm.WindowState) obj2, z);
                    }
                });
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void wallpaperOffsetsComplete(final android.os.IBinder iBinder) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                actionOnWallpaper(iBinder, new java.util.function.BiConsumer() { // from class: com.android.server.wm.Session$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.wm.WallpaperController) obj).wallpaperOffsetsComplete(iBinder);
                    }
                });
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setWallpaperDisplayOffset(android.os.IBinder iBinder, final int i, final int i2) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    actionOnWallpaper(iBinder, new java.util.function.BiConsumer() { // from class: com.android.server.wm.Session$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            ((com.android.server.wm.WallpaperController) obj).setWindowWallpaperDisplayOffset((com.android.server.wm.WindowState) obj2, i, i2);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void sendWallpaperCommand(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.wm.WindowState windowForClientLocked = this.mService.windowForClientLocked(this, iBinder, true);
                    com.android.server.wm.WallpaperController wallpaperController = windowForClientLocked.getDisplayContent().mWallpaperController;
                    if (!this.mCanAlwaysUpdateWallpaper) {
                        if (windowForClientLocked != wallpaperController.getWallpaperTarget()) {
                            if (windowForClientLocked == wallpaperController.getPrevWallpaperTarget()) {
                            }
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    wallpaperController.sendWindowWallpaperCommandUnchecked(windowForClientLocked, str, i, i2, i3, bundle, z);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void wallpaperCommandComplete(final android.os.IBinder iBinder, android.os.Bundle bundle) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                actionOnWallpaper(iBinder, new java.util.function.BiConsumer() { // from class: com.android.server.wm.Session$$ExternalSyntheticLambda4
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.wm.WallpaperController) obj).wallpaperCommandComplete(iBinder);
                    }
                });
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void onRectangleOnScreenRequested(android.os.IBinder iBinder, android.graphics.Rect rect) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mService.onRectangleOnScreenRequested(iBinder, rect);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public android.view.IWindowId getWindowId(android.os.IBinder iBinder) {
        return this.mService.getWindowId(iBinder);
    }

    public void pokeDrawLock(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mService.pokeDrawLock(this, iBinder);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void updatePointerIcon(android.view.IWindow iWindow) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mService.updatePointerIcon(iWindow);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void updateTapExcludeRegion(android.view.IWindow iWindow, android.graphics.Region region) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mService.updateTapExcludeRegion(iWindow, region);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void updateRequestedVisibleTypes(android.view.IWindow iWindow, int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowForClientLocked = this.mService.windowForClientLocked(this, iWindow, false);
                if (windowForClientLocked != null) {
                    windowForClientLocked.setRequestedVisibleTypes(i);
                    windowForClientLocked.getDisplayContent().getInsetsPolicy().onRequestedVisibleTypesChanged(windowForClientLocked);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void onWindowAdded(com.android.server.wm.WindowState windowState) {
        if (this.mPackageName == null) {
            this.mPackageName = this.mProcess.mInfo.packageName;
            this.mRelayoutTag = "relayoutWindow: " + this.mPackageName;
        }
        if (this.mSurfaceSession == null) {
            this.mSurfaceSession = new android.view.SurfaceSession();
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, -1594708154257031561L, 0, null, java.lang.String.valueOf(this.mSurfaceSession));
            this.mService.mSessions.add(this);
            if (this.mLastReportedAnimatorScale != this.mService.getCurrentAnimatorScale()) {
                this.mService.dispatchNewAnimatorScaleLocked(this);
            }
            this.mProcess.mWindowSession = this;
        }
        this.mAddedWindows.add(windowState);
    }

    void onWindowRemoved(com.android.server.wm.WindowState windowState) {
        this.mAddedWindows.remove(windowState);
        if (this.mAddedWindows.isEmpty()) {
            killSessionLocked();
        }
    }

    boolean hasWindow() {
        return !this.mAddedWindows.isEmpty();
    }

    void onWindowSurfaceVisibilityChanged(com.android.server.wm.WindowSurfaceController windowSurfaceController, boolean z, int i) {
        boolean remove;
        if (!android.view.WindowManager.LayoutParams.isSystemAlertWindowType(i)) {
            return;
        }
        boolean z2 = (this.mCanAddInternalSystemWindow || this.mCanCreateSystemApplicationOverlay) ? false : true;
        if (z) {
            remove = this.mAlertWindowSurfaces.add(windowSurfaceController);
            if (i == 2038) {
                com.android.internal.os.logging.MetricsLoggerWrapper.logAppOverlayEnter(this.mUid, this.mPackageName, remove, i, false);
            } else if (z2) {
                com.android.internal.os.logging.MetricsLoggerWrapper.logAppOverlayEnter(this.mUid, this.mPackageName, remove, i, true);
            }
        } else {
            remove = this.mAlertWindowSurfaces.remove(windowSurfaceController);
            if (i == 2038) {
                com.android.internal.os.logging.MetricsLoggerWrapper.logAppOverlayExit(this.mUid, this.mPackageName, remove, i, false);
            } else if (z2) {
                com.android.internal.os.logging.MetricsLoggerWrapper.logAppOverlayExit(this.mUid, this.mPackageName, remove, i, true);
            }
        }
        if (remove && z2) {
            if (this.mAlertWindowSurfaces.isEmpty()) {
                cancelAlertWindowNotification();
            } else if (this.mAlertWindowNotification == null) {
                this.mAlertWindowNotification = new com.android.server.wm.AlertWindowNotification(this.mService, this.mPackageName, android.os.UserHandle.getUserId(this.mUid));
                if (this.mShowingAlertWindowNotificationAllowed) {
                    this.mAlertWindowNotification.post();
                }
            }
        }
        if (remove && this.mPid != com.android.server.wm.WindowManagerService.MY_PID) {
            setHasOverlayUi(!this.mAlertWindowSurfaces.isEmpty());
        }
    }

    void setShowingAlertWindowNotificationAllowed(boolean z) {
        this.mShowingAlertWindowNotificationAllowed = z;
        if (this.mAlertWindowNotification != null) {
            if (z) {
                this.mAlertWindowNotification.post();
            } else {
                this.mAlertWindowNotification.cancel(false);
            }
        }
    }

    private void killSessionLocked() {
        if (!this.mClientDead) {
            return;
        }
        this.mService.mSessions.remove(this);
        if (this.mSurfaceSession == null) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, 2638961674625826260L, 0, null, java.lang.String.valueOf(this.mSurfaceSession));
        try {
            this.mSurfaceSession.kill();
        } catch (java.lang.Exception e) {
            android.util.Slog.w("WindowManager", "Exception thrown when killing surface session " + this.mSurfaceSession + " in session " + this + ": " + e.toString());
        }
        this.mSurfaceSession = null;
        this.mAddedWindows.clear();
        this.mAlertWindowSurfaces.clear();
        setHasOverlayUi(false);
        cancelAlertWindowNotification();
    }

    @com.android.internal.annotations.VisibleForTesting
    void setHasOverlayUi(boolean z) {
        this.mService.mH.obtainMessage(58, this.mPid, z ? 1 : 0).sendToTarget();
    }

    private void cancelAlertWindowNotification() {
        if (this.mAlertWindowNotification == null) {
            return;
        }
        this.mAlertWindowNotification.cancel(true);
        this.mAlertWindowNotification = null;
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("numWindow=");
        printWriter.print(this.mAddedWindows.size());
        printWriter.print(" mCanAddInternalSystemWindow=");
        printWriter.print(this.mCanAddInternalSystemWindow);
        printWriter.print(" mAlertWindowSurfaces=");
        printWriter.print(this.mAlertWindowSurfaces);
        printWriter.print(" mClientDead=");
        printWriter.print(this.mClientDead);
        printWriter.print(" mSurfaceSession=");
        printWriter.println(this.mSurfaceSession);
        printWriter.print(str);
        printWriter.print("mPackageName=");
        printWriter.println(this.mPackageName);
    }

    public java.lang.String toString() {
        return this.mStringName;
    }

    boolean hasAlertWindowSurfaces(com.android.server.wm.DisplayContent displayContent) {
        for (int size = this.mAlertWindowSurfaces.size() - 1; size >= 0; size--) {
            if (this.mAlertWindowSurfaces.valueAt(size).mAnimator.mWin.getDisplayContent() == displayContent) {
                return true;
            }
        }
        return false;
    }

    public void grantInputChannel(int i, android.view.SurfaceControl surfaceControl, android.os.IBinder iBinder, @android.annotation.Nullable android.window.InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, android.os.IBinder iBinder2, android.window.InputTransferToken inputTransferToken2, java.lang.String str, android.view.InputChannel inputChannel) {
        if (inputTransferToken == null && !this.mCanAddInternalSystemWindow) {
            throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mService.grantInputChannel(this, this.mUid, this.mPid, i, surfaceControl, iBinder, inputTransferToken, i2, this.mCanAddInternalSystemWindow ? i3 : 0, i4, i5, iBinder2, inputTransferToken2, str, inputChannel);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void updateInputChannel(android.os.IBinder iBinder, int i, android.view.SurfaceControl surfaceControl, int i2, int i3, int i4, android.graphics.Region region) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mService.updateInputChannel(iBinder, i, surfaceControl, i2, this.mCanAddInternalSystemWindow ? i3 : 0, i4, region);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void grantEmbeddedWindowFocus(android.view.IWindow iWindow, android.window.InputTransferToken inputTransferToken, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (iWindow == null) {
                if (!this.mCanAddInternalSystemWindow) {
                    throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
                }
                this.mService.grantEmbeddedWindowFocus(this, inputTransferToken, z);
            } else {
                this.mService.grantEmbeddedWindowFocus(this, iWindow, inputTransferToken, z);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean moveFocusToAdjacentWindow(android.view.IWindow iWindow, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowForClientLocked = this.mService.windowForClientLocked(this, iWindow, false);
                    if (windowForClientLocked != null) {
                        boolean moveFocusToAdjacentWindow = this.mService.moveFocusToAdjacentWindow(windowForClientLocked, i);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return moveFocusToAdjacentWindow;
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void generateDisplayHash(android.view.IWindow iWindow, android.graphics.Rect rect, java.lang.String str, android.os.RemoteCallback remoteCallback) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mService.generateDisplayHash(this, iWindow, rect, str, remoteCallback);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setOnBackInvokedCallbackInfo(android.view.IWindow iWindow, android.window.OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowForClientLocked = this.mService.windowForClientLocked(this, iWindow, false);
                if (windowForClientLocked == null) {
                    android.util.Slog.i("WindowManager", "setOnBackInvokedCallback(): No window state for package:" + this.mPackageName);
                } else {
                    windowForClientLocked.setOnBackInvokedCallbackInfo(onBackInvokedCallbackInfo);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }
}
