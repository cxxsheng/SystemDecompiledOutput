package com.android.server.contentcapture;

/* loaded from: classes.dex */
final class ContentCapturePerUserService extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.contentcapture.ContentCapturePerUserService, com.android.server.contentcapture.ContentCaptureManagerService> implements com.android.server.contentcapture.RemoteContentCaptureService.ContentCaptureServiceCallbacks {
    static final int EVENT_LOG_CONNECT_STATE_CONNECTED = 1;
    private static final int EVENT_LOG_CONNECT_STATE_DIED = 0;
    static final int EVENT_LOG_CONNECT_STATE_DISCONNECTED = 2;
    private static final java.lang.String TAG = com.android.server.contentcapture.ContentCapturePerUserService.class.getSimpleName();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, android.util.ArraySet<android.view.contentcapture.ContentCaptureCondition>> mConditionsByPkg;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.service.contentcapture.ContentCaptureServiceInfo mInfo;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    com.android.server.contentcapture.RemoteContentCaptureService mRemoteService;
    private final com.android.server.contentcapture.ContentCapturePerUserService.ContentCaptureServiceRemoteCallback mRemoteServiceCallback;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.contentcapture.ContentCaptureServerSession> mSessions;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mZombie;

    ContentCapturePerUserService(@android.annotation.NonNull com.android.server.contentcapture.ContentCaptureManagerService contentCaptureManagerService, @android.annotation.NonNull java.lang.Object obj, boolean z, int i) {
        super(contentCaptureManagerService, obj, i);
        this.mSessions = new android.util.SparseArray<>();
        this.mRemoteServiceCallback = new com.android.server.contentcapture.ContentCapturePerUserService.ContentCaptureServiceRemoteCallback();
        this.mConditionsByPkg = new android.util.ArrayMap<>();
        updateRemoteServiceLocked(z);
    }

    private void updateRemoteServiceLocked(boolean z) {
        if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).verbose) {
            android.util.Slog.v(TAG, "updateRemoteService(disabled=" + z + ")");
        }
        if (this.mRemoteService != null) {
            if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).debug) {
                android.util.Slog.d(TAG, "updateRemoteService(): destroying old remote service");
            }
            this.mRemoteService.destroy();
            this.mRemoteService = null;
            resetContentCaptureWhitelistLocked();
        }
        android.content.ComponentName updateServiceInfoLocked = updateServiceInfoLocked();
        if (updateServiceInfoLocked == null) {
            if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).debug) {
                android.util.Slog.d(TAG, "updateRemoteService(): no service component name");
            }
        } else if (!z) {
            if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).debug) {
                android.util.Slog.d(TAG, "updateRemoteService(): creating new remote service for " + updateServiceInfoLocked);
            }
            this.mRemoteService = new com.android.server.contentcapture.RemoteContentCaptureService(((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).getContext(), "android.service.contentcapture.ContentCaptureService", updateServiceInfoLocked, this.mRemoteServiceCallback, this.mUserId, this, ((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).isBindInstantServiceAllowed(), ((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).verbose, ((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).mDevCfgIdleUnbindTimeoutMs);
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        this.mInfo = new android.service.contentcapture.ContentCaptureServiceInfo(getContext(), componentName, isTemporaryServiceSetLocked(), this.mUserId);
        return this.mInfo.getServiceInfo();
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected boolean updateLocked(boolean z) {
        boolean updateLocked = super.updateLocked(z);
        if (updateLocked) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                this.mSessions.valueAt(i).setContentCaptureEnabledLocked(!z);
            }
        }
        destroyLocked();
        updateRemoteServiceLocked(z);
        return updateLocked;
    }

    public void onServiceDied(@android.annotation.NonNull com.android.server.contentcapture.RemoteContentCaptureService remoteContentCaptureService) {
        android.util.Slog.w(TAG, "remote service died: " + remoteContentCaptureService);
        synchronized (this.mLock) {
            this.mZombie = true;
            com.android.server.contentcapture.ContentCaptureMetricsLogger.writeServiceEvent(16, getServiceComponentName());
            android.util.EventLog.writeEvent(com.android.server.contentcapture.EventLogTags.CC_CONNECT_STATE_CHANGED, java.lang.Integer.valueOf(this.mUserId), 0, 0);
        }
    }

    void onConnected() {
        synchronized (this.mLock) {
            try {
                if (this.mZombie) {
                    if (this.mRemoteService == null) {
                        android.util.Slog.w(TAG, "Cannot ressurect sessions because remote service is null");
                    } else {
                        this.mZombie = false;
                        resurrectSessionsLocked();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void resurrectSessionsLocked() {
        int size = this.mSessions.size();
        if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).debug) {
            android.util.Slog.d(TAG, "Ressurrecting remote service (" + this.mRemoteService + ") on " + size + " sessions");
        }
        for (int i = 0; i < size; i++) {
            this.mSessions.valueAt(i).resurrectLocked();
        }
    }

    void onPackageUpdatingLocked() {
        int size = this.mSessions.size();
        if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).debug) {
            android.util.Slog.d(TAG, "Pausing " + size + " sessions while package is updating");
        }
        for (int i = 0; i < size; i++) {
            this.mSessions.valueAt(i).pauseLocked();
        }
    }

    void onPackageUpdatedLocked() {
        updateRemoteServiceLocked(!isEnabledLocked());
        resurrectSessionsLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void startSessionLocked(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2, @android.annotation.NonNull android.content.pm.ActivityPresentationInfo activityPresentationInfo, int i, int i2, int i3, @android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) {
        if (activityPresentationInfo == null) {
            android.util.Slog.w(TAG, "basic activity info is null");
            android.service.contentcapture.ContentCaptureService.setClientState(iResultReceiver, 260, (android.os.IBinder) null);
            return;
        }
        int i4 = activityPresentationInfo.taskId;
        int i5 = activityPresentationInfo.displayId;
        android.content.ComponentName componentName = activityPresentationInfo.componentName;
        boolean z = ((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).mGlobalContentCaptureOptions.isWhitelisted(this.mUserId, componentName) || ((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).mGlobalContentCaptureOptions.isWhitelisted(this.mUserId, componentName.getPackageName());
        android.content.ComponentName serviceComponentName = getServiceComponentName();
        boolean isEnabledLocked = isEnabledLocked();
        if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).mRequestsHistory != null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("id=");
            sb.append(i);
            sb.append(" uid=");
            sb.append(i2);
            sb.append(" a=");
            sb.append(android.content.ComponentName.flattenToShortString(componentName));
            sb.append(" t=");
            sb.append(i4);
            sb.append(" d=");
            sb.append(i5);
            sb.append(" s=");
            sb.append(android.content.ComponentName.flattenToShortString(serviceComponentName));
            sb.append(" u=");
            sb.append(this.mUserId);
            sb.append(" f=");
            sb.append(i3);
            sb.append(isEnabledLocked ? "" : " (disabled)");
            sb.append(" w=");
            sb.append(z);
            ((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).mRequestsHistory.log(sb.toString());
        }
        if (!isEnabledLocked) {
            android.service.contentcapture.ContentCaptureService.setClientState(iResultReceiver, 20, (android.os.IBinder) null);
            com.android.server.contentcapture.ContentCaptureMetricsLogger.writeSessionEvent(i, 3, 20, serviceComponentName, false);
            return;
        }
        if (serviceComponentName == null) {
            if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).debug) {
                android.util.Slog.d(TAG, "startSession(" + iBinder + "): hold your horses");
                return;
            }
            return;
        }
        if (!z) {
            if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).debug) {
                android.util.Slog.d(TAG, "startSession(" + componentName + "): package or component not whitelisted");
            }
            android.service.contentcapture.ContentCaptureService.setClientState(iResultReceiver, 516, (android.os.IBinder) null);
            com.android.server.contentcapture.ContentCaptureMetricsLogger.writeSessionEvent(i, 3, 516, serviceComponentName, false);
            return;
        }
        com.android.server.contentcapture.ContentCaptureServerSession contentCaptureServerSession = this.mSessions.get(i);
        if (contentCaptureServerSession != null) {
            android.util.Slog.w(TAG, "startSession(id=" + contentCaptureServerSession + ", token=" + iBinder + ": ignoring because it already exists for " + contentCaptureServerSession.mActivityToken);
            android.service.contentcapture.ContentCaptureService.setClientState(iResultReceiver, 12, (android.os.IBinder) null);
            com.android.server.contentcapture.ContentCaptureMetricsLogger.writeSessionEvent(i, 3, 12, serviceComponentName, false);
            return;
        }
        if (this.mRemoteService == null) {
            updateRemoteServiceLocked(false);
        }
        if (this.mRemoteService == null) {
            android.util.Slog.w(TAG, "startSession(id=" + contentCaptureServerSession + ", token=" + iBinder + ": ignoring because service is not set");
            android.service.contentcapture.ContentCaptureService.setClientState(iResultReceiver, 20, (android.os.IBinder) null);
            com.android.server.contentcapture.ContentCaptureMetricsLogger.writeSessionEvent(i, 3, 20, serviceComponentName, false);
            return;
        }
        this.mRemoteService.ensureBoundLocked();
        com.android.server.contentcapture.ContentCaptureServerSession contentCaptureServerSession2 = new com.android.server.contentcapture.ContentCaptureServerSession(this.mLock, iBinder, new android.app.assist.ActivityId(i4, iBinder2), this, componentName, iResultReceiver, i4, i5, i, i2, i3);
        if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).verbose) {
            android.util.Slog.v(TAG, "startSession(): new session for " + android.content.ComponentName.flattenToShortString(componentName) + " and id " + i);
        }
        this.mSessions.put(i, contentCaptureServerSession2);
        contentCaptureServerSession2.notifySessionStartedLocked(iResultReceiver);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void finishSessionLocked(int i) {
        if (!isEnabledLocked()) {
            return;
        }
        com.android.server.contentcapture.ContentCaptureServerSession contentCaptureServerSession = this.mSessions.get(i);
        if (contentCaptureServerSession == null) {
            if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).debug) {
                android.util.Slog.d(TAG, "finishSession(): no session with id" + i);
                return;
            }
            return;
        }
        if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).verbose) {
            android.util.Slog.v(TAG, "finishSession(): id=" + i);
        }
        contentCaptureServerSession.removeSelfLocked(true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void removeDataLocked(@android.annotation.NonNull android.view.contentcapture.DataRemovalRequest dataRemovalRequest) {
        if (!isEnabledLocked()) {
            return;
        }
        assertCallerLocked(dataRemovalRequest.getPackageName());
        this.mRemoteService.onDataRemovalRequest(dataRemovalRequest);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onDataSharedLocked(@android.annotation.NonNull android.view.contentcapture.DataShareRequest dataShareRequest, android.service.contentcapture.IDataShareCallback.Stub stub) {
        if (!isEnabledLocked()) {
            return;
        }
        assertCallerLocked(dataShareRequest.getPackageName());
        this.mRemoteService.onDataShareRequest(dataShareRequest, stub);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    public android.content.ComponentName getServiceSettingsActivityLocked() {
        java.lang.String settingsActivity;
        if (this.mInfo == null || (settingsActivity = this.mInfo.getSettingsActivity()) == null) {
            return null;
        }
        return new android.content.ComponentName(this.mInfo.getServiceInfo().packageName, settingsActivity);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void assertCallerLocked(@android.annotation.NonNull java.lang.String str) {
        java.lang.String str2;
        android.content.pm.PackageManager packageManager = getContext().getPackageManager();
        int callingUid = android.os.Binder.getCallingUid();
        try {
            int packageUidAsUser = packageManager.getPackageUidAsUser(str, android.os.UserHandle.getCallingUserId());
            if (callingUid != packageUidAsUser && !((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).hasRunningActivity(callingUid, str)) {
                android.service.voice.VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity = ((android.service.voice.VoiceInteractionManagerInternal) com.android.server.LocalServices.getService(android.service.voice.VoiceInteractionManagerInternal.class)).getHotwordDetectionServiceIdentity();
                if (!(hotwordDetectionServiceIdentity != null && callingUid == hotwordDetectionServiceIdentity.getIsolatedUid() && packageUidAsUser == hotwordDetectionServiceIdentity.getOwnerUid())) {
                    java.lang.String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
                    if (packagesForUid != null) {
                        str2 = packagesForUid[0];
                    } else {
                        str2 = "uid-" + callingUid;
                    }
                    android.util.Slog.w(TAG, "App (package=" + str2 + ", UID=" + callingUid + ") passed package (" + str + ") owned by UID " + packageUidAsUser);
                    throw new java.lang.SecurityException("Invalid package: " + str);
                }
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.SecurityException("Could not verify UID for " + str);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean sendActivityAssistDataLocked(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.Bundle bundle) {
        int sessionId = getSessionId(iBinder);
        android.service.contentcapture.SnapshotData snapshotData = new android.service.contentcapture.SnapshotData(bundle.getBundle("data"), (android.app.assist.AssistStructure) bundle.getParcelable(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_STRUCTURE, android.app.assist.AssistStructure.class), (android.app.assist.AssistContent) bundle.getParcelable(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT, android.app.assist.AssistContent.class));
        if (sessionId != 0) {
            this.mSessions.get(sessionId).sendActivitySnapshotLocked(snapshotData);
            return true;
        }
        if (this.mRemoteService == null) {
            return false;
        }
        this.mRemoteService.onActivitySnapshotRequest(0, snapshotData);
        android.util.Slog.d(TAG, "Notified activity assist data for activity: " + iBinder + " without a session Id");
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void removeSessionLocked(int i) {
        this.mSessions.remove(i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean isContentCaptureServiceForUserLocked(int i) {
        return i == getServiceUidLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.contentcapture.ContentCaptureServerSession getSession(@android.annotation.NonNull android.os.IBinder iBinder) {
        for (int i = 0; i < this.mSessions.size(); i++) {
            com.android.server.contentcapture.ContentCaptureServerSession valueAt = this.mSessions.valueAt(i);
            if (valueAt.mActivityToken.equals(iBinder)) {
                return valueAt;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void destroyLocked() {
        if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).debug) {
            android.util.Slog.d(TAG, "destroyLocked()");
        }
        if (this.mRemoteService != null) {
            this.mRemoteService.destroy();
        }
        destroySessionsLocked();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void destroySessionsLocked() {
        int size = this.mSessions.size();
        for (int i = 0; i < size; i++) {
            this.mSessions.valueAt(i).destroyLocked(true);
        }
        this.mSessions.clear();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void listSessionsLocked(java.util.ArrayList<java.lang.String> arrayList) {
        int size = this.mSessions.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(this.mSessions.valueAt(i).toShortString());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    android.util.ArraySet<android.view.contentcapture.ContentCaptureCondition> getContentCaptureConditionsLocked(@android.annotation.NonNull java.lang.String str) {
        return this.mConditionsByPkg.get(str);
    }

    @android.annotation.Nullable
    android.util.ArraySet<java.lang.String> getContentCaptureAllowlist() {
        android.util.ArraySet<java.lang.String> whitelistedPackages;
        synchronized (this.mLock) {
            whitelistedPackages = ((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).mGlobalContentCaptureOptions.getWhitelistedPackages(this.mUserId);
        }
        return whitelistedPackages;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onActivityEventLocked(@android.annotation.NonNull android.app.assist.ActivityId activityId, @android.annotation.NonNull android.content.ComponentName componentName, int i) {
        if (this.mRemoteService == null) {
            if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).debug) {
                android.util.Slog.d(this.mTag, "onActivityEvent(): no remote service");
            }
        } else {
            if (this.mRemoteService.getServiceInterface() == null) {
                if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).debug) {
                    android.util.Slog.d(this.mTag, "onActivityEvent(): remote service is dead or unbound");
                    return;
                }
                return;
            }
            android.service.contentcapture.ActivityEvent activityEvent = new android.service.contentcapture.ActivityEvent(activityId, componentName, i);
            if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).verbose) {
                android.util.Slog.v(this.mTag, "onActivityEvent(): " + activityEvent);
            }
            this.mRemoteService.onActivityLifecycleEvent(activityEvent);
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    protected void dumpLocked(java.lang.String str, java.io.PrintWriter printWriter) {
        super.dumpLocked(str, printWriter);
        java.lang.String str2 = str + "  ";
        printWriter.print(str);
        printWriter.print("Service Info: ");
        if (this.mInfo == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println();
            this.mInfo.dump(str2, printWriter);
        }
        printWriter.print(str);
        printWriter.print("Zombie: ");
        printWriter.println(this.mZombie);
        if (this.mRemoteService != null) {
            printWriter.print(str);
            printWriter.println("remote service:");
            this.mRemoteService.dump(str2, printWriter);
        }
        if (this.mSessions.size() == 0) {
            printWriter.print(str);
            printWriter.println("no sessions");
            return;
        }
        int size = this.mSessions.size();
        printWriter.print(str);
        printWriter.print("number sessions: ");
        printWriter.println(size);
        for (int i = 0; i < size; i++) {
            printWriter.print(str);
            printWriter.print("#");
            printWriter.println(i);
            this.mSessions.valueAt(i).dumpLocked(str2, printWriter);
            printWriter.println();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int getSessionId(@android.annotation.NonNull android.os.IBinder iBinder) {
        for (int i = 0; i < this.mSessions.size(); i++) {
            if (this.mSessions.valueAt(i).isActivitySession(iBinder)) {
                return this.mSessions.keyAt(i);
            }
        }
        return 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void resetContentCaptureWhitelistLocked() {
        if (((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).verbose) {
            android.util.Slog.v(TAG, "resetting content capture whitelist");
        }
        ((com.android.server.contentcapture.ContentCaptureManagerService) this.mMaster).mGlobalContentCaptureOptions.resetWhitelist(this.mUserId);
    }

    private final class ContentCaptureServiceRemoteCallback extends android.service.contentcapture.IContentCaptureServiceCallback.Stub {
        private ContentCaptureServiceRemoteCallback() {
        }

        public void setContentCaptureWhitelist(java.util.List<java.lang.String> list, java.util.List<android.content.ComponentName> list2) {
            if (((com.android.server.contentcapture.ContentCaptureManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mMaster).verbose) {
                java.lang.String str = com.android.server.contentcapture.ContentCapturePerUserService.TAG;
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("setContentCaptureWhitelist(");
                sb.append(list == null ? "null_packages" : list.size() + " packages");
                sb.append(", ");
                sb.append(list2 == null ? "null_activities" : list2.size() + " activities");
                sb.append(") for user ");
                sb.append(((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mUserId);
                android.util.Slog.v(str, sb.toString());
            }
            android.util.ArraySet<java.lang.String> whitelistedPackages = ((com.android.server.contentcapture.ContentCaptureManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mMaster).mGlobalContentCaptureOptions.getWhitelistedPackages(((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mUserId);
            android.util.EventLog.writeEvent(com.android.server.contentcapture.EventLogTags.CC_CURRENT_ALLOWLIST, java.lang.Integer.valueOf(((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mUserId), java.lang.Integer.valueOf(com.android.internal.util.CollectionUtils.size(whitelistedPackages)));
            ((com.android.server.contentcapture.ContentCaptureManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mMaster).mGlobalContentCaptureOptions.setWhitelist(((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mUserId, list, list2);
            android.util.EventLog.writeEvent(com.android.server.contentcapture.EventLogTags.CC_SET_ALLOWLIST, java.lang.Integer.valueOf(((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mUserId), java.lang.Integer.valueOf(com.android.internal.util.CollectionUtils.size(list)), java.lang.Integer.valueOf(com.android.internal.util.CollectionUtils.size(list2)));
            com.android.server.contentcapture.ContentCaptureMetricsLogger.writeSetWhitelistEvent(com.android.server.contentcapture.ContentCapturePerUserService.this.getServiceComponentName(), list, list2);
            updateContentCaptureOptions(whitelistedPackages);
            int size = com.android.server.contentcapture.ContentCapturePerUserService.this.mSessions.size();
            if (size <= 0) {
                return;
            }
            android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray(size);
            for (int i = 0; i < size; i++) {
                com.android.server.contentcapture.ContentCaptureServerSession contentCaptureServerSession = (com.android.server.contentcapture.ContentCaptureServerSession) com.android.server.contentcapture.ContentCapturePerUserService.this.mSessions.valueAt(i);
                if (!((com.android.server.contentcapture.ContentCaptureManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mMaster).mGlobalContentCaptureOptions.isWhitelisted(((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mUserId, contentCaptureServerSession.appComponentName)) {
                    int keyAt = com.android.server.contentcapture.ContentCapturePerUserService.this.mSessions.keyAt(i);
                    if (((com.android.server.contentcapture.ContentCaptureManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mMaster).debug) {
                        android.util.Slog.d(com.android.server.contentcapture.ContentCapturePerUserService.TAG, "marking session " + keyAt + " (" + contentCaptureServerSession.appComponentName + ") for un-whitelisting");
                    }
                    sparseBooleanArray.append(keyAt, true);
                }
            }
            int size2 = sparseBooleanArray.size();
            if (size2 <= 0) {
                return;
            }
            synchronized (com.android.server.contentcapture.ContentCapturePerUserService.this.mLock) {
                for (int i2 = 0; i2 < size2; i2++) {
                    try {
                        int keyAt2 = sparseBooleanArray.keyAt(i2);
                        if (((com.android.server.contentcapture.ContentCaptureManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mMaster).debug) {
                            android.util.Slog.d(com.android.server.contentcapture.ContentCapturePerUserService.TAG, "un-whitelisting " + keyAt2);
                        }
                        ((com.android.server.contentcapture.ContentCaptureServerSession) com.android.server.contentcapture.ContentCapturePerUserService.this.mSessions.get(keyAt2)).setContentCaptureEnabledLocked(false);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public void setContentCaptureConditions(java.lang.String str, java.util.List<android.view.contentcapture.ContentCaptureCondition> list) {
            java.lang.String str2;
            if (((com.android.server.contentcapture.ContentCaptureManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mMaster).verbose) {
                java.lang.String str3 = com.android.server.contentcapture.ContentCapturePerUserService.TAG;
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("setContentCaptureConditions(");
                sb.append(str);
                sb.append("): ");
                if (list == null) {
                    str2 = "null";
                } else {
                    str2 = list.size() + " conditions";
                }
                sb.append(str2);
                android.util.Slog.v(str3, sb.toString());
            }
            synchronized (com.android.server.contentcapture.ContentCapturePerUserService.this.mLock) {
                try {
                    if (list == null) {
                        com.android.server.contentcapture.ContentCapturePerUserService.this.mConditionsByPkg.remove(str);
                    } else {
                        com.android.server.contentcapture.ContentCapturePerUserService.this.mConditionsByPkg.put(str, new android.util.ArraySet(list));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void disableSelf() {
            if (((com.android.server.contentcapture.ContentCaptureManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mMaster).verbose) {
                android.util.Slog.v(com.android.server.contentcapture.ContentCapturePerUserService.TAG, "disableSelf()");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.provider.Settings.Secure.putStringForUser(com.android.server.contentcapture.ContentCapturePerUserService.this.getContext().getContentResolver(), "content_capture_enabled", "0", ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mUserId);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                com.android.server.contentcapture.ContentCaptureMetricsLogger.writeServiceEvent(4, com.android.server.contentcapture.ContentCapturePerUserService.this.getServiceComponentName());
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void writeSessionFlush(int i, android.content.ComponentName componentName, android.service.contentcapture.FlushMetrics flushMetrics, android.content.ContentCaptureOptions contentCaptureOptions, int i2) {
            com.android.server.contentcapture.ContentCaptureMetricsLogger.writeSessionFlush(i, com.android.server.contentcapture.ContentCapturePerUserService.this.getServiceComponentName(), flushMetrics, contentCaptureOptions, i2);
        }

        private void updateContentCaptureOptions(@android.annotation.Nullable android.util.ArraySet<java.lang.String> arraySet) {
            android.util.ArraySet whitelistedPackages = ((com.android.server.contentcapture.ContentCaptureManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mMaster).mGlobalContentCaptureOptions.getWhitelistedPackages(((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mUserId);
            android.util.EventLog.writeEvent(com.android.server.contentcapture.EventLogTags.CC_CURRENT_ALLOWLIST, java.lang.Integer.valueOf(((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mUserId), java.lang.Integer.valueOf(com.android.internal.util.CollectionUtils.size(whitelistedPackages)));
            if (arraySet != null && whitelistedPackages != null) {
                whitelistedPackages.removeAll((android.util.ArraySet) arraySet);
            }
            int size = com.android.internal.util.CollectionUtils.size(whitelistedPackages);
            android.util.EventLog.writeEvent(com.android.server.contentcapture.EventLogTags.CC_UPDATE_OPTIONS, java.lang.Integer.valueOf(((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mUserId), java.lang.Integer.valueOf(size));
            for (int i = 0; i < size; i++) {
                java.lang.String str = (java.lang.String) whitelistedPackages.valueAt(i);
                ((com.android.server.contentcapture.ContentCaptureManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mMaster).updateOptions(str, ((com.android.server.contentcapture.ContentCaptureManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mMaster).mGlobalContentCaptureOptions.getOptions(((com.android.server.infra.AbstractPerUserSystemService) com.android.server.contentcapture.ContentCapturePerUserService.this).mUserId, str));
            }
        }
    }
}
