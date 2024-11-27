package com.android.server.wm;

/* loaded from: classes3.dex */
public class ActivityStartController {
    private static final int DO_PENDING_ACTIVITY_LAUNCHES_MSG = 1;
    private static final java.lang.String TAG = "ActivityTaskManager";
    boolean mCheckedForSetup;
    private final com.android.server.wm.ActivityStarter.Factory mFactory;
    private boolean mInExecution;
    private com.android.server.wm.ActivityRecord mLastHomeActivityStartRecord;
    private int mLastHomeActivityStartResult;
    private com.android.server.wm.ActivityStarter mLastStarter;
    private final com.android.server.wm.PendingRemoteAnimationRegistry mPendingRemoteAnimationRegistry;
    private final com.android.server.wm.ActivityTaskManagerService mService;
    private final com.android.server.wm.ActivityTaskSupervisor mSupervisor;
    private com.android.server.wm.ActivityRecord[] tmpOutRecord;

    ActivityStartController(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        this(activityTaskManagerService, activityTaskManagerService.mTaskSupervisor, new com.android.server.wm.ActivityStarter.DefaultFactory(activityTaskManagerService, activityTaskManagerService.mTaskSupervisor, new com.android.server.wm.ActivityStartInterceptor(activityTaskManagerService, activityTaskManagerService.mTaskSupervisor)));
    }

    @com.android.internal.annotations.VisibleForTesting
    ActivityStartController(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor, com.android.server.wm.ActivityStarter.Factory factory) {
        this.tmpOutRecord = new com.android.server.wm.ActivityRecord[1];
        this.mCheckedForSetup = false;
        this.mInExecution = false;
        this.mService = activityTaskManagerService;
        this.mSupervisor = activityTaskSupervisor;
        this.mFactory = factory;
        this.mFactory.setController(this);
        this.mPendingRemoteAnimationRegistry = new com.android.server.wm.PendingRemoteAnimationRegistry(activityTaskManagerService.mGlobalLock, activityTaskManagerService.mH);
    }

    com.android.server.wm.ActivityStarter obtainStarter(android.content.Intent intent, java.lang.String str) {
        return this.mFactory.obtain().setIntent(intent).setReason(str);
    }

    void onExecutionStarted() {
        this.mInExecution = true;
    }

    boolean isInExecution() {
        return this.mInExecution;
    }

    void onExecutionComplete(com.android.server.wm.ActivityStarter activityStarter) {
        this.mInExecution = false;
        if (this.mLastStarter == null) {
            this.mLastStarter = this.mFactory.obtain();
        }
        this.mLastStarter.set(activityStarter);
        this.mFactory.recycle(activityStarter);
    }

    void postStartActivityProcessingForLastStarter(com.android.server.wm.ActivityRecord activityRecord, int i, com.android.server.wm.Task task) {
        if (this.mLastStarter == null) {
            return;
        }
        this.mLastStarter.postStartActivityProcessing(activityRecord, i, task);
    }

    void startHomeActivity(android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, java.lang.String str, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        android.app.ActivityOptions makeBasic = android.app.ActivityOptions.makeBasic();
        makeBasic.setLaunchWindowingMode(1);
        if (!com.android.server.wm.ActivityRecord.isResolverActivity(activityInfo.name)) {
            makeBasic.setLaunchActivityType(2);
        }
        makeBasic.setLaunchDisplayId(taskDisplayArea.getDisplayId());
        makeBasic.setLaunchTaskDisplayArea(taskDisplayArea.mRemoteToken.toWindowContainerToken());
        this.mSupervisor.beginDeferResume();
        try {
            com.android.server.wm.Task orCreateRootHomeTask = taskDisplayArea.getOrCreateRootHomeTask(true);
            this.mSupervisor.endDeferResume();
            this.mLastHomeActivityStartResult = obtainStarter(intent, "startHomeActivity: " + str).setOutActivity(this.tmpOutRecord).setCallingUid(0).setActivityInfo(activityInfo).setActivityOptions(makeBasic.toBundle()).execute();
            this.mLastHomeActivityStartRecord = this.tmpOutRecord[0];
            if (orCreateRootHomeTask.mInResumeTopActivity) {
                this.mSupervisor.scheduleResumeTopActivities();
            }
        } catch (java.lang.Throwable th) {
            this.mSupervisor.endDeferResume();
            throw th;
        }
    }

    void startSetupActivity() {
        java.lang.String str;
        if (this.mCheckedForSetup) {
            return;
        }
        android.content.ContentResolver contentResolver = this.mService.mContext.getContentResolver();
        if (this.mService.mFactoryTest != 1 && android.provider.Settings.Global.getInt(contentResolver, "device_provisioned", 0) != 0) {
            this.mCheckedForSetup = true;
            android.content.Intent intent = new android.content.Intent("android.intent.action.UPGRADE_SETUP");
            java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = this.mService.mContext.getPackageManager().queryIntentActivities(intent, 1049728);
            if (!queryIntentActivities.isEmpty()) {
                android.content.pm.ResolveInfo resolveInfo = queryIntentActivities.get(0);
                if (resolveInfo.activityInfo.metaData != null) {
                    str = resolveInfo.activityInfo.metaData.getString("android.SETUP_VERSION");
                } else {
                    str = null;
                }
                if (str == null && resolveInfo.activityInfo.applicationInfo.metaData != null) {
                    str = resolveInfo.activityInfo.applicationInfo.metaData.getString("android.SETUP_VERSION");
                }
                java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(contentResolver, "last_setup_shown", contentResolver.getUserId());
                if (str != null && !str.equals(stringForUser)) {
                    intent.setFlags(268435456);
                    intent.setComponent(new android.content.ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
                    obtainStarter(intent, "startSetupActivity").setCallingUid(0).setActivityInfo(resolveInfo.activityInfo).execute();
                }
            }
        }
    }

    int checkTargetUser(int i, boolean z, int i2, int i3, java.lang.String str) {
        if (z) {
            return this.mService.handleIncomingUser(i2, i3, i, str);
        }
        this.mService.mAmInternal.ensureNotSpecialUser(i);
        return i;
    }

    final int startActivityInPackage(int i, int i2, int i3, java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i4, int i5, com.android.server.wm.SafeActivityOptions safeActivityOptions, int i6, com.android.server.wm.Task task, java.lang.String str5, boolean z, com.android.server.am.PendingIntentRecord pendingIntentRecord, android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        return obtainStarter(intent, str5).setCallingUid(i).setRealCallingPid(i2).setRealCallingUid(i3).setCallingPackage(str).setCallingFeatureId(str2).setResolvedType(str3).setResultTo(iBinder).setResultWho(str4).setRequestCode(i4).setStartFlags(i5).setActivityOptions(safeActivityOptions).setUserId(checkTargetUser(i6, z, i2, i3, str5)).setInTask(task).setOriginatingPendingIntent(pendingIntentRecord).setBackgroundStartPrivileges(backgroundStartPrivileges).execute();
    }

    final int startActivitiesInPackage(int i, java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.content.Intent[] intentArr, java.lang.String[] strArr, android.os.IBinder iBinder, com.android.server.wm.SafeActivityOptions safeActivityOptions, int i2, boolean z, com.android.server.am.PendingIntentRecord pendingIntentRecord, android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        return startActivitiesInPackage(i, 0, -1, str, str2, intentArr, strArr, iBinder, safeActivityOptions, i2, z, pendingIntentRecord, backgroundStartPrivileges);
    }

    final int startActivitiesInPackage(int i, int i2, int i3, java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.content.Intent[] intentArr, java.lang.String[] strArr, android.os.IBinder iBinder, com.android.server.wm.SafeActivityOptions safeActivityOptions, int i4, boolean z, com.android.server.am.PendingIntentRecord pendingIntentRecord, android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        return startActivities(null, i, i2, i3, str, str2, intentArr, strArr, iBinder, safeActivityOptions, checkTargetUser(i4, z, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), "startActivityInPackage"), "startActivityInPackage", pendingIntentRecord, backgroundStartPrivileges);
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x020f, code lost:
    
        r0 = r29.mService.mWindowManager.mStartingSurfaceController;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0215, code lost:
    
        if (r39 == null) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0217, code lost:
    
        r19 = r39.getOriginalOptions();
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0220, code lost:
    
        r0.endDeferAddStartingWindow(r19);
        r29.mService.continueWindowLayout();
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x0229, code lost:
    
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        android.os.Binder.restoreCallingIdentity(r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x022f, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01fe, code lost:
    
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0200, code lost:
    
        if (r0 >= r5) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0202, code lost:
    
        r29.mFactory.recycle(r26[r0]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0209, code lost:
    
        r0 = r0 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int startActivities(android.app.IApplicationThread iApplicationThread, int i, int i2, int i3, java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.content.Intent[] intentArr, java.lang.String[] strArr, android.os.IBinder iBinder, com.android.server.wm.SafeActivityOptions safeActivityOptions, int i4, java.lang.String str3, com.android.server.am.PendingIntentRecord pendingIntentRecord, android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        int i5;
        int i6;
        android.content.Intent intent;
        com.android.server.uri.NeededUriGrants checkGrantUriPermissionFromIntent;
        android.content.Intent[] intentArr2;
        com.android.server.wm.SafeActivityOptions safeActivityOptions2;
        if (intentArr == null) {
            throw new java.lang.NullPointerException("intents is null");
        }
        if (strArr == null) {
            throw new java.lang.NullPointerException("resolvedTypes is null");
        }
        if (intentArr.length != strArr.length) {
            throw new java.lang.IllegalArgumentException("intents are length different than resolvedTypes");
        }
        int callingPid = i2 != 0 ? i2 : android.os.Binder.getCallingPid();
        int i7 = i3;
        if (i7 == -1) {
            i7 = android.os.Binder.getCallingUid();
        }
        if (i >= 0) {
            i5 = i;
            i6 = -1;
        } else if (iApplicationThread == null) {
            i6 = callingPid;
            i5 = i7;
        } else {
            i5 = -1;
            i6 = -1;
        }
        int computeResolveFilterUid = com.android.server.wm.ActivityStarter.computeResolveFilterUid(i5, i7, com.android.server.am.ProcessList.INVALID_ADJ);
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.SafeActivityOptions selectiveCloneLaunchOptions = safeActivityOptions != null ? safeActivityOptions.selectiveCloneLaunchOptions() : null;
        try {
            android.content.Intent[] intentArr3 = (android.content.Intent[]) com.android.internal.util.ArrayUtils.filterNotNull(intentArr, new java.util.function.IntFunction() { // from class: com.android.server.wm.ActivityStartController$$ExternalSyntheticLambda0
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i8) {
                    android.content.Intent[] lambda$startActivities$0;
                    lambda$startActivities$0 = com.android.server.wm.ActivityStartController.lambda$startActivities$0(i8);
                    return lambda$startActivities$0;
                }
            });
            int length = intentArr3.length;
            com.android.server.wm.ActivityStarter[] activityStarterArr = new com.android.server.wm.ActivityStarter[length];
            int i8 = 0;
            while (i8 < intentArr3.length) {
                android.content.Intent intent2 = intentArr3[i8];
                if (intent2.hasFileDescriptors()) {
                    throw new java.lang.IllegalArgumentException("File descriptors passed in Intent");
                }
                boolean z = intent2.getComponent() != null;
                android.util.SparseArray sparseArray2 = sparseArray;
                android.content.Intent intent3 = new android.content.Intent(intent2);
                intent3.removeExtendedFlags(1);
                int i9 = i8;
                com.android.server.wm.ActivityStarter[] activityStarterArr2 = activityStarterArr;
                int i10 = length;
                int i11 = computeResolveFilterUid;
                int i12 = i6;
                android.content.pm.ActivityInfo activityInfoForUser = this.mService.mAmInternal.getActivityInfoForUser(this.mSupervisor.resolveActivity(intent3, strArr[i8], 0, null, i4, i11, i12), i4);
                if (activityInfoForUser != null) {
                    try {
                        computeResolveFilterUid = i11;
                        intent = intent3;
                        checkGrantUriPermissionFromIntent = this.mSupervisor.mService.mUgmInternal.checkGrantUriPermissionFromIntent(intent, computeResolveFilterUid, activityInfoForUser.applicationInfo.packageName, android.os.UserHandle.getUserId(activityInfoForUser.applicationInfo.uid));
                        if ((activityInfoForUser.applicationInfo.privateFlags & 2) != 0) {
                            throw new java.lang.IllegalArgumentException("FLAG_CANT_SAVE_STATE not supported here");
                        }
                        sparseArray2.put(activityInfoForUser.applicationInfo.uid, activityInfoForUser.applicationInfo.packageName);
                    } catch (java.lang.SecurityException e) {
                        android.util.Slog.d(TAG, "Not allowed to start activity since no uri permission.");
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -96;
                    }
                } else {
                    computeResolveFilterUid = i11;
                    intent = intent3;
                    checkGrantUriPermissionFromIntent = null;
                }
                boolean z2 = i9 == intentArr3.length - 1;
                if (z2) {
                    intentArr2 = intentArr3;
                    safeActivityOptions2 = safeActivityOptions;
                } else {
                    intentArr2 = intentArr3;
                    safeActivityOptions2 = selectiveCloneLaunchOptions;
                }
                com.android.server.wm.ActivityStarter requestCode = obtainStarter(intent, str3).setIntentGrants(checkGrantUriPermissionFromIntent).setCaller(iApplicationThread).setResolvedType(strArr[i9]).setActivityInfo(activityInfoForUser).setRequestCode(-1);
                i6 = i12;
                activityStarterArr2[i9] = requestCode.setCallingPid(i6).setCallingUid(i5).setCallingPackage(str).setCallingFeatureId(str2).setRealCallingPid(callingPid).setRealCallingUid(i7).setActivityOptions(safeActivityOptions2).setComponentSpecified(z).setAllowPendingRemoteAnimationRegistryLookup(z2).setOriginatingPendingIntent(pendingIntentRecord).setBackgroundStartPrivileges(backgroundStartPrivileges);
                i8 = i9 + 1;
                sparseArray = sparseArray2;
                activityStarterArr = activityStarterArr2;
                length = i10;
                intentArr3 = intentArr2;
            }
            com.android.server.wm.ActivityStarter[] activityStarterArr3 = activityStarterArr;
            int i13 = length;
            android.util.SparseArray sparseArray3 = sparseArray;
            if (sparseArray3.size() > 1) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder("startActivities: different apps [");
                int size = sparseArray3.size();
                int i14 = 0;
                while (i14 < size) {
                    sb.append((java.lang.String) sparseArray3.valueAt(i14));
                    sb.append(i14 == size + (-1) ? "]" : ", ");
                    i14++;
                }
                sb.append(" from ");
                sb.append(str);
                android.util.Slog.wtf(TAG, sb.toString());
            }
            com.android.server.wm.ActivityRecord[] activityRecordArr = new com.android.server.wm.ActivityRecord[1];
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mService.deferWindowLayout();
                    this.mService.mWindowManager.mStartingSurfaceController.beginDeferAddStartingWindow();
                    android.os.IBinder iBinder2 = iBinder;
                    int i15 = 0;
                    while (true) {
                        int i16 = i13;
                        if (i15 >= i16) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return 0;
                        }
                        try {
                            int execute = activityStarterArr3[i15].setResultTo(iBinder2).setOutActivity(activityRecordArr).execute();
                            if (execute < 0) {
                                break;
                            }
                            com.android.server.wm.ActivityRecord activityRecord = activityRecordArr[0];
                            if (activityRecord == null || activityRecord.getUid() != computeResolveFilterUid) {
                                if (i15 < i16 - 1) {
                                    activityStarterArr3[i15 + 1].getIntent().addFlags(268435456);
                                }
                                iBinder2 = iBinder;
                            } else {
                                iBinder2 = activityRecord.token;
                            }
                            i15++;
                            i13 = i16;
                        } finally {
                            this.mService.mWindowManager.mStartingSurfaceController.endDeferAddStartingWindow(safeActivityOptions != null ? safeActivityOptions.getOriginalOptions() : null);
                            this.mService.continueWindowLayout();
                        }
                    }
                } finally {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.content.Intent[] lambda$startActivities$0(int i) {
        return new android.content.Intent[i];
    }

    int startActivityInTaskFragment(@android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable android.os.Bundle bundle, @android.annotation.Nullable android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable android.os.IBinder iBinder2) {
        com.android.server.wm.ActivityRecord forTokenLocked = iBinder != null ? com.android.server.wm.ActivityRecord.forTokenLocked(iBinder) : null;
        return obtainStarter(intent, "startActivityInTaskFragment").setActivityOptions(bundle).setInTaskFragment(taskFragment).setResultTo(iBinder).setRequestCode(-1).setResolvedType(intent.resolveTypeIfNeeded(this.mService.mContext.getContentResolver())).setCallingUid(i).setCallingPid(i2).setRealCallingUid(i).setRealCallingPid(i2).setUserId(forTokenLocked != null ? forTokenLocked.mUserId : this.mService.getCurrentUserId()).setErrorCallbackToken(iBinder2).execute();
    }

    boolean startExistingRecentsIfPossible(android.content.Intent intent, android.app.ActivityOptions activityOptions) {
        try {
            android.os.Trace.traceBegin(32L, "startExistingRecents");
            if (startExistingRecents(intent, activityOptions)) {
                android.os.Trace.traceEnd(32L);
                return true;
            }
            android.os.Trace.traceEnd(32L);
            return false;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(32L);
            throw th;
        }
    }

    private boolean startExistingRecents(android.content.Intent intent, android.app.ActivityOptions activityOptions) {
        com.android.server.wm.ActivityRecord activityRecord;
        com.android.server.wm.Task rootTask = this.mService.mRootWindowContainer.getDefaultTaskDisplayArea().getRootTask(0, this.mService.getRecentTasks().getRecentsComponent().equals(intent.getComponent()) ? 3 : 2);
        if (rootTask == null || (activityRecord = rootTask.topRunningActivity()) == null || ((activityRecord.isVisibleRequested() && rootTask.isTopRootTaskInDisplayArea()) || !activityRecord.attachedToProcess() || !activityRecord.mActivityComponent.equals(intent.getComponent()) || !this.mService.isCallerRecents(activityRecord.getUid()) || activityRecord.mDisplayContent.isKeyguardLocked())) {
            return false;
        }
        this.mService.mRootWindowContainer.startPowerModeLaunchIfNeeded(true, activityRecord);
        com.android.server.wm.ActivityMetricsLogger.LaunchingState notifyActivityLaunching = this.mSupervisor.getActivityMetricsLogger().notifyActivityLaunching(intent);
        com.android.server.wm.Task task = activityRecord.getTask();
        this.mService.deferWindowLayout();
        try {
            com.android.server.wm.TransitionController transitionController = activityRecord.mTransitionController;
            com.android.server.wm.Transition collectingTransition = transitionController.getCollectingTransition();
            if (collectingTransition != null) {
                collectingTransition.setRemoteAnimationApp(activityRecord.app.getThread());
                transitionController.setTransientLaunch(activityRecord, com.android.server.wm.TaskDisplayArea.getRootTaskAbove(rootTask));
            }
            task.moveToFront("startExistingRecents");
            task.mInResumeTopActivity = true;
            task.resumeTopActivity(null, activityOptions, true);
            this.mSupervisor.getActivityMetricsLogger().notifyActivityLaunched(notifyActivityLaunching, 2, false, activityRecord, activityOptions);
            task.mInResumeTopActivity = false;
            this.mService.continueWindowLayout();
            return true;
        } catch (java.lang.Throwable th) {
            task.mInResumeTopActivity = false;
            this.mService.continueWindowLayout();
            throw th;
        }
    }

    void registerRemoteAnimationForNextActivityStart(java.lang.String str, android.view.RemoteAnimationAdapter remoteAnimationAdapter, @android.annotation.Nullable android.os.IBinder iBinder) {
        this.mPendingRemoteAnimationRegistry.addPendingAnimation(str, remoteAnimationAdapter, iBinder);
    }

    com.android.server.wm.PendingRemoteAnimationRegistry getPendingRemoteAnimationRegistry() {
        return this.mPendingRemoteAnimationRegistry;
    }

    com.android.server.wm.ActivityRecord getLastStartActivity() {
        if (this.mLastStarter != null) {
            return this.mLastStarter.mStartActivity;
        }
        return null;
    }

    void dumpLastHomeActivityStartResult(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("mLastHomeActivityStartResult=");
        printWriter.println(this.mLastHomeActivityStartResult);
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2) {
        boolean z;
        boolean z2 = true;
        boolean z3 = false;
        boolean z4 = str2 != null;
        if (this.mLastHomeActivityStartRecord != null && (!z4 || str2.equals(this.mLastHomeActivityStartRecord.packageName))) {
            dumpLastHomeActivityStartResult(printWriter, str);
            printWriter.print(str);
            printWriter.println("mLastHomeActivityStartRecord:");
            this.mLastHomeActivityStartRecord.dump(printWriter, str + "  ", true);
            z = true;
        } else {
            z = false;
        }
        if (this.mLastStarter != null) {
            if (!z4 || this.mLastStarter.relatedToPackage(str2) || (this.mLastHomeActivityStartRecord != null && str2.equals(this.mLastHomeActivityStartRecord.packageName))) {
                z3 = true;
            }
            if (z3) {
                if (z) {
                    z2 = z;
                } else {
                    dumpLastHomeActivityStartResult(printWriter, str);
                }
                printWriter.print(str);
                printWriter.println("mLastStarter:");
                this.mLastStarter.dump(printWriter, str + "  ");
                if (!z4) {
                    z = z2;
                } else {
                    return;
                }
            }
        }
        if (!z) {
            printWriter.print(str);
            printWriter.println("(nothing)");
        }
    }
}
