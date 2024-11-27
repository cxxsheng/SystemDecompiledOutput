package com.android.server.autofill;

/* loaded from: classes.dex */
final class AutofillManagerServiceImpl extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.autofill.AutofillManagerServiceImpl, com.android.server.autofill.AutofillManagerService> {
    private static final int MAX_ABANDONED_SESSION_MILLIS = 30000;
    private static final int MAX_SESSION_ID_CREATE_TRIES = 2048;
    private static final java.lang.String TAG = "AutofillManagerServiceImpl";
    private static final java.util.Random sRandom = new java.util.Random();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.service.autofill.FillEventHistory mAugmentedAutofillEventHistory;
    private final com.android.server.autofill.AutofillManagerService.AutofillCompatState mAutofillCompatState;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.RemoteCallbackList<android.view.autofill.IAutoFillManagerClient> mClients;
    private final com.android.server.contentcapture.ContentCaptureManagerInternal mContentCaptureManagerInternal;
    private final com.android.server.autofill.AutofillManagerService.DisabledInfoCache mDisabledInfoCache;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.service.autofill.FillEventHistory mEventHistory;
    private final com.android.server.autofill.FieldClassificationStrategy mFieldClassificationStrategy;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.service.autofill.AutofillServiceInfo mInfo;
    private final com.android.server.inputmethod.InputMethodManagerInternal mInputMethodManagerInternal;
    private long mLastPrune;
    private final com.android.internal.logging.MetricsLogger mMetricsLogger;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.autofill.RemoteAugmentedAutofillService mRemoteAugmentedAutofillService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.content.pm.ServiceInfo mRemoteAugmentedAutofillServiceInfo;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.autofill.RemoteFieldClassificationService mRemoteFieldClassificationService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.content.pm.ServiceInfo mRemoteFieldClassificationServiceInfo;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.autofill.RemoteInlineSuggestionRenderService mRemoteInlineSuggestionRenderService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.autofill.Session> mSessions;
    private final com.android.server.autofill.ui.AutoFillUI mUi;
    private final android.util.LocalLog mUiLatencyHistory;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.service.autofill.UserData mUserData;
    private final android.util.LocalLog mWtfHistory;

    AutofillManagerServiceImpl(com.android.server.autofill.AutofillManagerService autofillManagerService, java.lang.Object obj, android.util.LocalLog localLog, android.util.LocalLog localLog2, int i, com.android.server.autofill.ui.AutoFillUI autoFillUI, com.android.server.autofill.AutofillManagerService.AutofillCompatState autofillCompatState, boolean z, com.android.server.autofill.AutofillManagerService.DisabledInfoCache disabledInfoCache) {
        super(autofillManagerService, obj, i);
        this.mMetricsLogger = new com.android.internal.logging.MetricsLogger();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
        this.mSessions = new android.util.SparseArray<>();
        this.mLastPrune = 0L;
        this.mUiLatencyHistory = localLog;
        this.mWtfHistory = localLog2;
        this.mUi = autoFillUI;
        this.mFieldClassificationStrategy = new com.android.server.autofill.FieldClassificationStrategy(getContext(), i);
        this.mAutofillCompatState = autofillCompatState;
        this.mInputMethodManagerInternal = (com.android.server.inputmethod.InputMethodManagerInternal) com.android.server.LocalServices.getService(com.android.server.inputmethod.InputMethodManagerInternal.class);
        this.mContentCaptureManagerInternal = (com.android.server.contentcapture.ContentCaptureManagerInternal) com.android.server.LocalServices.getService(com.android.server.contentcapture.ContentCaptureManagerInternal.class);
        this.mDisabledInfoCache = disabledInfoCache;
        updateLocked(z);
    }

    boolean sendActivityAssistDataToContentCapture(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.Bundle bundle) {
        if (this.mContentCaptureManagerInternal != null) {
            this.mContentCaptureManagerInternal.sendActivityAssistData(getUserId(), iBinder, bundle);
            return true;
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onBackKeyPressed() {
        com.android.server.autofill.RemoteAugmentedAutofillService remoteAugmentedAutofillServiceLocked = getRemoteAugmentedAutofillServiceLocked();
        if (remoteAugmentedAutofillServiceLocked != null) {
            remoteAugmentedAutofillServiceLocked.onDestroyAutofillWindowsRequest();
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected boolean updateLocked(boolean z) {
        forceRemoveAllSessionsLocked();
        boolean updateLocked = super.updateLocked(z);
        if (updateLocked) {
            if (!isEnabledLocked()) {
                for (int size = this.mSessions.size() - 1; size >= 0; size--) {
                    this.mSessions.valueAt(size).removeFromServiceLocked();
                }
            }
            sendStateToClients(false);
        }
        updateRemoteAugmentedAutofillService();
        getRemoteInlineSuggestionRenderServiceLocked();
        return updateLocked;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        boolean z;
        java.util.Iterator it = getContext().getPackageManager().queryIntentServicesAsUser(new android.content.Intent("android.service.autofill.AutofillService"), 8388736, this.mUserId).iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (((android.content.pm.ResolveInfo) it.next()).serviceInfo.getComponentName().equals(componentName)) {
                z = true;
                break;
            }
        }
        if (!z) {
            android.util.Slog.w(TAG, "Autofill service from '" + componentName.getPackageName() + "' doesnot have intent filter android.service.autofill.AutofillService");
            throw new java.lang.SecurityException("Service does not declare intent filter android.service.autofill.AutofillService");
        }
        this.mInfo = new android.service.autofill.AutofillServiceInfo(getContext(), componentName, this.mUserId);
        return this.mInfo.getServiceInfo();
    }

    @android.annotation.Nullable
    java.lang.String[] getUrlBarResourceIdsForCompatMode(@android.annotation.NonNull java.lang.String str) {
        return this.mAutofillCompatState.getUrlBarResourceIds(str, this.mUserId);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int addClientLocked(android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, android.content.ComponentName componentName) {
        if (this.mClients == null) {
            this.mClients = new android.os.RemoteCallbackList<>();
        }
        this.mClients.register(iAutoFillManagerClient);
        if (isEnabledLocked()) {
            return 1;
        }
        if (componentName != null && isAugmentedAutofillServiceAvailableLocked() && isWhitelistedForAugmentedAutofillLocked(componentName)) {
            return 8;
        }
        return 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void removeClientLocked(android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient) {
        if (this.mClients != null) {
            this.mClients.unregister(iAutoFillManagerClient);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void setAuthenticationResultLocked(android.os.Bundle bundle, int i, int i2, int i3) {
        com.android.server.autofill.Session session;
        if (isEnabledLocked() && (session = this.mSessions.get(i)) != null && i3 == session.uid) {
            synchronized (session.mLock) {
                session.setAuthenticationResultLocked(bundle, i2);
            }
        }
    }

    void setHasCallback(int i, int i2, boolean z) {
        com.android.server.autofill.Session session;
        if (isEnabledLocked() && (session = this.mSessions.get(i)) != null && i2 == session.uid) {
            synchronized (this.mLock) {
                session.setHasCallbackLocked(z);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long startSessionLocked(@android.annotation.NonNull android.os.IBinder iBinder, int i, int i2, @android.annotation.NonNull android.os.IBinder iBinder2, @android.annotation.NonNull android.view.autofill.AutofillId autofillId, @android.annotation.NonNull android.graphics.Rect rect, @android.annotation.Nullable android.view.autofill.AutofillValue autofillValue, boolean z, @android.annotation.NonNull android.content.ComponentName componentName, boolean z2, boolean z3, int i3) {
        boolean z4;
        boolean z5 = (i3 & 8) != 0;
        if (isEnabledLocked() || z5) {
            if (!z5 && isAutofillDisabledLocked(componentName)) {
                if (isWhitelistedForAugmentedAutofillLocked(componentName)) {
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(TAG, "startSession(" + componentName + "): disabled by service but whitelisted for augmented autofill");
                    }
                    z4 = true;
                } else {
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(TAG, "startSession(" + componentName + "): ignored because disabled by service and not whitelisted for augmented autofill");
                    }
                    try {
                        android.view.autofill.IAutoFillManagerClient.Stub.asInterface(iBinder2).setSessionFinished(4, (java.util.List) null);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Could not notify " + componentName + " that it's disabled: " + e);
                    }
                    return 2147483647L;
                }
            } else {
                z4 = z5;
            }
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "startSession(): token=" + iBinder + ", flags=" + i3 + ", forAugmentedAutofillOnly=" + z4);
            }
            pruneAbandonedSessionsLocked();
            boolean z6 = z4;
            com.android.server.autofill.Session createSessionByTokenLocked = createSessionByTokenLocked(iBinder, i, i2, iBinder2, z, componentName, z2, z3, z4, i3);
            if (createSessionByTokenLocked == null) {
                return 2147483647L;
            }
            ((com.android.server.autofill.AutofillManagerService) this.mMaster).logRequestLocked("id=" + createSessionByTokenLocked.id + " uid=" + i2 + " a=" + componentName.toShortString() + " s=" + (this.mInfo == null ? null : this.mInfo.getServiceInfo().packageName) + " u=" + this.mUserId + " i=" + autofillId + " b=" + rect + " hc=" + z + " f=" + i3 + " aa=" + z6);
            synchronized (createSessionByTokenLocked.mLock) {
                createSessionByTokenLocked.updateLocked(autofillId, rect, autofillValue, 1, i3);
            }
            if (z6) {
                return createSessionByTokenLocked.id | 4294967296L;
            }
            return createSessionByTokenLocked.id;
        }
        return 0L;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void pruneAbandonedSessionsLocked() {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (this.mLastPrune < currentTimeMillis - 30000) {
            this.mLastPrune = currentTimeMillis;
            if (this.mSessions.size() > 0) {
                new com.android.server.autofill.AutofillManagerServiceImpl.PruneTask().execute(new java.lang.Void[0]);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void setAutofillFailureLocked(int i, int i2, @android.annotation.NonNull java.util.List<android.view.autofill.AutofillId> list) {
        if (!isEnabledLocked()) {
            return;
        }
        com.android.server.autofill.Session session = this.mSessions.get(i);
        if (session == null || i2 != session.uid) {
            android.util.Slog.v(TAG, "setAutofillFailure(): no session for " + i + "(" + i2 + ")");
            return;
        }
        session.setAutofillFailureLocked(list);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void finishSessionLocked(int i, int i2, int i3) {
        if (!isEnabledLocked()) {
            return;
        }
        com.android.server.autofill.Session session = this.mSessions.get(i);
        if (session == null || i2 != session.uid) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "finishSessionLocked(): no session for " + i + "(" + i2 + ")");
                return;
            }
            return;
        }
        com.android.server.autofill.Session.SaveResult showSaveLocked = session.showSaveLocked();
        session.logContextCommittedLocked(showSaveLocked.getNoSaveUiReason(), i3);
        if (showSaveLocked.isLogSaveShown()) {
            session.logSaveUiShown();
        }
        boolean isRemoveSession = showSaveLocked.isRemoveSession();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "finishSessionLocked(): session finished on save? " + isRemoveSession);
        }
        if (isRemoveSession) {
            session.removeFromServiceLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void cancelSessionLocked(int i, int i2) {
        if (!isEnabledLocked()) {
            return;
        }
        com.android.server.autofill.Session session = this.mSessions.get(i);
        if (session == null || i2 != session.uid) {
            android.util.Slog.w(TAG, "cancelSessionLocked(): no session for " + i + "(" + i2 + ")");
            return;
        }
        session.removeFromServiceLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void disableOwnedAutofillServicesLocked(int i) {
        android.util.Slog.i(TAG, "disableOwnedServices(" + i + "): " + this.mInfo);
        if (this.mInfo == null) {
            return;
        }
        android.content.pm.ServiceInfo serviceInfo = this.mInfo.getServiceInfo();
        if (serviceInfo.applicationInfo.uid != i) {
            android.util.Slog.w(TAG, "disableOwnedServices(): ignored when called by UID " + i + " instead of " + serviceInfo.applicationInfo.uid + " for service " + this.mInfo);
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String componentNameLocked = getComponentNameLocked();
            android.content.ComponentName componentName = serviceInfo.getComponentName();
            if (componentName.equals(android.content.ComponentName.unflattenFromString(componentNameLocked))) {
                this.mMetricsLogger.action(1135, componentName.getPackageName());
                android.provider.Settings.Secure.putStringForUser(getContext().getContentResolver(), "autofill_service", null, this.mUserId);
                forceRemoveAllSessionsLocked();
            } else {
                android.util.Slog.w(TAG, "disableOwnedServices(): ignored because current service (" + serviceInfo + ") does not match Settings (" + componentNameLocked + ")");
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.autofill.Session createSessionByTokenLocked(@android.annotation.NonNull android.os.IBinder iBinder, int i, int i2, @android.annotation.NonNull android.os.IBinder iBinder2, boolean z, @android.annotation.NonNull android.content.ComponentName componentName, boolean z2, boolean z3, boolean z4, int i3) {
        android.content.ComponentName componentName2;
        com.android.server.autofill.AutofillManagerServiceImpl autofillManagerServiceImpl = this;
        int i4 = 0;
        while (true) {
            i4++;
            if (i4 > 2048) {
                android.util.Slog.w(TAG, "Cannot create session in 2048 tries");
                return null;
            }
            int abs = java.lang.Math.abs(sRandom.nextInt());
            if (abs == 0 || abs == Integer.MAX_VALUE || autofillManagerServiceImpl.mSessions.indexOfKey(abs) >= 0) {
                autofillManagerServiceImpl = autofillManagerServiceImpl;
            } else {
                autofillManagerServiceImpl.assertCallerLocked(componentName, z2);
                if (autofillManagerServiceImpl.mInfo == null) {
                    componentName2 = null;
                } else {
                    componentName2 = autofillManagerServiceImpl.mInfo.getServiceInfo().getComponentName();
                }
                com.android.server.autofill.Session session = new com.android.server.autofill.Session(this, autofillManagerServiceImpl.mUi, getContext(), autofillManagerServiceImpl.mHandler, autofillManagerServiceImpl.mUserId, autofillManagerServiceImpl.mLock, abs, i, i2, iBinder, iBinder2, z, autofillManagerServiceImpl.mUiLatencyHistory, autofillManagerServiceImpl.mWtfHistory, componentName2, componentName, z2, z3, z4, i3, autofillManagerServiceImpl.mInputMethodManagerInternal, (i3 & 2048) != 0);
                this.mSessions.put(session.id, session);
                return session;
            }
        }
    }

    private void assertCallerLocked(@android.annotation.NonNull android.content.ComponentName componentName, boolean z) {
        java.lang.String str;
        java.lang.String packageName = componentName.getPackageName();
        android.content.pm.PackageManager packageManager = getContext().getPackageManager();
        int callingUid = android.os.Binder.getCallingUid();
        try {
            int packageUidAsUser = packageManager.getPackageUidAsUser(packageName, android.os.UserHandle.getCallingUserId());
            if (callingUid != packageUidAsUser && !((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).hasRunningActivity(callingUid, packageName)) {
                java.lang.String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
                if (packagesForUid != null) {
                    str = packagesForUid[0];
                } else {
                    str = "uid-" + callingUid;
                }
                android.util.Slog.w(TAG, "App (package=" + str + ", UID=" + callingUid + ") passed component (" + componentName + ") owned by UID " + packageUidAsUser);
                android.metrics.LogMaker addTaggedData = new android.metrics.LogMaker(948).setPackageName(str).addTaggedData(908, getServicePackageName()).addTaggedData(949, componentName.flattenToShortString());
                if (z) {
                    addTaggedData.addTaggedData(1414, 1);
                }
                this.mMetricsLogger.write(addTaggedData);
                throw new java.lang.SecurityException("Invalid component: " + componentName);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.SecurityException("Could not verify UID for " + componentName);
        }
    }

    boolean restoreSession(int i, int i2, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2) {
        com.android.server.autofill.Session session = this.mSessions.get(i);
        if (session == null || i2 != session.uid) {
            return false;
        }
        session.switchActivity(iBinder, iBinder2);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean updateSessionLocked(int i, int i2, android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i3, int i4) {
        com.android.server.autofill.Session session = this.mSessions.get(i);
        if (session != null && session.uid == i2) {
            session.updateLocked(autofillId, rect, autofillValue, i3, i4);
            return false;
        }
        if ((i4 & 1) != 0) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "restarting session " + i + " due to manual request on " + autofillId);
            }
            return true;
        }
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "updateSessionLocked(): session gone for " + i + "(" + i2 + ")");
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void removeSessionLocked(int i) {
        this.mSessions.remove(i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    java.util.ArrayList<com.android.server.autofill.Session> getPreviousSessionsLocked(@android.annotation.NonNull com.android.server.autofill.Session session) {
        int size = this.mSessions.size();
        java.util.ArrayList<com.android.server.autofill.Session> arrayList = null;
        for (int i = 0; i < size; i++) {
            com.android.server.autofill.Session valueAt = this.mSessions.valueAt(i);
            if (valueAt.taskId == session.taskId && valueAt.id != session.id && (valueAt.getSaveInfoFlagsLocked() & 4) != 0) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList<>(size);
                }
                arrayList.add(valueAt);
            }
        }
        return arrayList;
    }

    void handleSessionSave(com.android.server.autofill.Session session) {
        synchronized (this.mLock) {
            try {
                if (this.mSessions.get(session.id) == null) {
                    android.util.Slog.w(TAG, "handleSessionSave(): already gone: " + session.id);
                    return;
                }
                session.callSaveLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onPendingSaveUi(int i, @android.annotation.NonNull android.os.IBinder iBinder) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "onPendingSaveUi(" + i + "): " + iBinder);
        }
        synchronized (this.mLock) {
            try {
                for (int size = this.mSessions.size() - 1; size >= 0; size--) {
                    com.android.server.autofill.Session valueAt = this.mSessions.valueAt(size);
                    if (valueAt.isSaveUiPendingForTokenLocked(iBinder)) {
                        valueAt.onPendingSaveUi(i, iBinder);
                        return;
                    }
                }
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(TAG, "No pending Save UI for token " + iBinder + " and operation " + android.util.DebugUtils.flagsToString(android.view.autofill.AutofillManager.class, "PENDING_UI_OPERATION_", i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void handlePackageUpdateLocked(@android.annotation.NonNull java.lang.String str) {
        android.content.pm.ServiceInfo serviceInfo = this.mFieldClassificationStrategy.getServiceInfo();
        if (serviceInfo != null && serviceInfo.packageName.equals(str)) {
            resetExtServiceLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void resetExtServiceLocked() {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "reset autofill service in ExtServices.");
        }
        this.mFieldClassificationStrategy.reset();
        if (this.mRemoteInlineSuggestionRenderService != null) {
            this.mRemoteInlineSuggestionRenderService.destroy();
            this.mRemoteInlineSuggestionRenderService = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void destroyLocked() {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "destroyLocked()");
        }
        resetExtServiceLocked();
        int size = this.mSessions.size();
        android.util.ArraySet arraySet = new android.util.ArraySet(size);
        for (int i = 0; i < size; i++) {
            com.android.server.autofill.RemoteFillService destroyLocked = this.mSessions.valueAt(i).destroyLocked();
            if (destroyLocked != null) {
                arraySet.add(destroyLocked);
            }
        }
        this.mSessions.clear();
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            ((com.android.server.autofill.RemoteFillService) arraySet.valueAt(i2)).destroy();
        }
        sendStateToClients(true);
        if (this.mClients != null) {
            this.mClients.kill();
            this.mClients = null;
        }
    }

    void setLastResponse(int i, @android.annotation.NonNull android.service.autofill.FillResponse fillResponse) {
        synchronized (this.mLock) {
            this.mEventHistory = new android.service.autofill.FillEventHistory(i, fillResponse.getClientState());
        }
    }

    void setLastAugmentedAutofillResponse(int i) {
        synchronized (this.mLock) {
            this.mAugmentedAutofillEventHistory = new android.service.autofill.FillEventHistory(i, null);
        }
    }

    void resetLastResponse() {
        synchronized (this.mLock) {
            this.mEventHistory = null;
        }
    }

    void resetLastAugmentedAutofillResponse() {
        synchronized (this.mLock) {
            this.mAugmentedAutofillEventHistory = null;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isValidEventLocked(java.lang.String str, int i) {
        if (this.mEventHistory == null) {
            android.util.Slog.w(TAG, str + ": not logging event because history is null");
            return false;
        }
        if (i != this.mEventHistory.getSessionId()) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, str + ": not logging event for session " + i + " because tracked session is " + this.mEventHistory.getSessionId());
            }
            return false;
        }
        return true;
    }

    void setAuthenticationSelected(int i, @android.annotation.Nullable android.os.Bundle bundle, int i2) {
        synchronized (this.mLock) {
            try {
                if (isValidEventLocked("setAuthenticationSelected()", i)) {
                    this.mEventHistory.addEvent(new android.service.autofill.FillEventHistory.Event(2, null, bundle, null, null, null, null, null, null, null, null, 0, i2));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void logDatasetAuthenticationSelected(@android.annotation.Nullable java.lang.String str, int i, @android.annotation.Nullable android.os.Bundle bundle, int i2) {
        synchronized (this.mLock) {
            try {
                if (isValidEventLocked("logDatasetAuthenticationSelected()", i)) {
                    this.mEventHistory.addEvent(new android.service.autofill.FillEventHistory.Event(1, str, bundle, null, null, null, null, null, null, null, null, 0, i2));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void logSaveShown(int i, @android.annotation.Nullable android.os.Bundle bundle) {
        synchronized (this.mLock) {
            try {
                if (isValidEventLocked("logSaveShown()", i)) {
                    this.mEventHistory.addEvent(new android.service.autofill.FillEventHistory.Event(3, null, bundle, null, null, null, null, null, null, null, null));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void logDatasetSelected(@android.annotation.Nullable java.lang.String str, int i, @android.annotation.Nullable android.os.Bundle bundle, int i2) {
        synchronized (this.mLock) {
            try {
                if (isValidEventLocked("logDatasetSelected()", i)) {
                    this.mEventHistory.addEvent(new android.service.autofill.FillEventHistory.Event(0, str, bundle, null, null, null, null, null, null, null, null, 0, i2));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void logDatasetShown(int i, @android.annotation.Nullable android.os.Bundle bundle, int i2) {
        synchronized (this.mLock) {
            try {
                if (isValidEventLocked("logDatasetShown", i)) {
                    this.mEventHistory.addEvent(new android.service.autofill.FillEventHistory.Event(5, null, bundle, null, null, null, null, null, null, null, null, 0, i2));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void logViewEntered(int i, @android.annotation.Nullable android.os.Bundle bundle) {
        synchronized (this.mLock) {
            try {
                if (isValidEventLocked("logViewEntered", i)) {
                    if (this.mEventHistory.getEvents() != null) {
                        java.util.Iterator<android.service.autofill.FillEventHistory.Event> it = this.mEventHistory.getEvents().iterator();
                        while (it.hasNext()) {
                            if (it.next().getType() == 6) {
                                android.util.Slog.v(TAG, "logViewEntered: already logged TYPE_VIEW_REQUESTED_AUTOFILL");
                                return;
                            }
                        }
                    }
                    this.mEventHistory.addEvent(new android.service.autofill.FillEventHistory.Event(6, null, bundle, null, null, null, null, null, null, null, null));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void logAugmentedAutofillAuthenticationSelected(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable android.os.Bundle bundle) {
        synchronized (this.mLock) {
            try {
                if (this.mAugmentedAutofillEventHistory != null && this.mAugmentedAutofillEventHistory.getSessionId() == i) {
                    this.mAugmentedAutofillEventHistory.addEvent(new android.service.autofill.FillEventHistory.Event(1, str, bundle, null, null, null, null, null, null, null, null));
                }
            } finally {
            }
        }
    }

    void logAugmentedAutofillSelected(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable android.os.Bundle bundle) {
        synchronized (this.mLock) {
            try {
                if (this.mAugmentedAutofillEventHistory != null && this.mAugmentedAutofillEventHistory.getSessionId() == i) {
                    this.mAugmentedAutofillEventHistory.addEvent(new android.service.autofill.FillEventHistory.Event(0, str, bundle, null, null, null, null, null, null, null, null));
                }
            } finally {
            }
        }
    }

    void logAugmentedAutofillShown(int i, @android.annotation.Nullable android.os.Bundle bundle) {
        synchronized (this.mLock) {
            try {
                if (this.mAugmentedAutofillEventHistory != null && this.mAugmentedAutofillEventHistory.getSessionId() == i) {
                    this.mAugmentedAutofillEventHistory.addEvent(new android.service.autofill.FillEventHistory.Event(5, null, bundle, null, null, null, null, null, null, null, null, 0, 2));
                }
            } finally {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void logContextCommittedLocked(int i, @android.annotation.Nullable android.os.Bundle bundle, @android.annotation.Nullable java.util.ArrayList<java.lang.String> arrayList, @android.annotation.Nullable android.util.ArraySet<java.lang.String> arraySet, @android.annotation.Nullable java.util.ArrayList<android.view.autofill.AutofillId> arrayList2, @android.annotation.Nullable java.util.ArrayList<java.lang.String> arrayList3, @android.annotation.Nullable java.util.ArrayList<android.view.autofill.AutofillId> arrayList4, @android.annotation.Nullable java.util.ArrayList<java.util.ArrayList<java.lang.String>> arrayList5, @android.annotation.NonNull android.content.ComponentName componentName, boolean z) {
        logContextCommittedLocked(i, bundle, arrayList, arraySet, arrayList2, arrayList3, arrayList4, arrayList5, null, null, componentName, z, 0);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void logContextCommittedLocked(int i, @android.annotation.Nullable android.os.Bundle bundle, @android.annotation.Nullable java.util.ArrayList<java.lang.String> arrayList, @android.annotation.Nullable android.util.ArraySet<java.lang.String> arraySet, @android.annotation.Nullable java.util.ArrayList<android.view.autofill.AutofillId> arrayList2, @android.annotation.Nullable java.util.ArrayList<java.lang.String> arrayList3, @android.annotation.Nullable java.util.ArrayList<android.view.autofill.AutofillId> arrayList4, @android.annotation.Nullable java.util.ArrayList<java.util.ArrayList<java.lang.String>> arrayList5, @android.annotation.Nullable java.util.ArrayList<android.view.autofill.AutofillId> arrayList6, @android.annotation.Nullable java.util.ArrayList<android.service.autofill.FieldClassification> arrayList7, @android.annotation.NonNull android.content.ComponentName componentName, boolean z, int i2) {
        android.view.autofill.AutofillId[] autofillIdArr;
        android.service.autofill.FieldClassification[] fieldClassificationArr;
        if (isValidEventLocked("logDatasetNotSelected()", i)) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "logContextCommitted() with FieldClassification: id=" + i + ", selectedDatasets=" + arrayList + ", ignoredDatasetIds=" + arraySet + ", changedAutofillIds=" + arrayList2 + ", changedDatasetIds=" + arrayList3 + ", manuallyFilledFieldIds=" + arrayList4 + ", detectedFieldIds=" + arrayList6 + ", detectedFieldClassifications=" + arrayList7 + ", appComponentName=" + componentName.toShortString() + ", compatMode=" + z + ", saveDialogNotShowReason=" + i2);
            }
            if (arrayList6 == null) {
                autofillIdArr = null;
                fieldClassificationArr = null;
            } else {
                int size = arrayList6.size();
                android.view.autofill.AutofillId[] autofillIdArr2 = new android.view.autofill.AutofillId[size];
                arrayList6.toArray(autofillIdArr2);
                android.service.autofill.FieldClassification[] fieldClassificationArr2 = new android.service.autofill.FieldClassification[arrayList7.size()];
                arrayList7.toArray(fieldClassificationArr2);
                float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                int i3 = 0;
                int i4 = 0;
                while (i3 < size) {
                    java.util.List<android.service.autofill.FieldClassification.Match> matches = fieldClassificationArr2[i3].getMatches();
                    android.service.autofill.FieldClassification[] fieldClassificationArr3 = fieldClassificationArr2;
                    int size2 = matches.size();
                    i4 += size2;
                    for (int i5 = 0; i5 < size2; i5++) {
                        f += matches.get(i5).getScore();
                    }
                    i3++;
                    fieldClassificationArr2 = fieldClassificationArr3;
                }
                this.mMetricsLogger.write(com.android.server.autofill.Helper.newLogMaker(1273, componentName, getServicePackageName(), i, z).setCounterValue(size).addTaggedData(1274, java.lang.Integer.valueOf((int) ((f * 100.0f) / i4))));
                autofillIdArr = autofillIdArr2;
                fieldClassificationArr = fieldClassificationArr2;
            }
            this.mEventHistory.addEvent(new android.service.autofill.FillEventHistory.Event(4, null, bundle, arrayList, arraySet, arrayList2, arrayList3, arrayList4, arrayList5, autofillIdArr, fieldClassificationArr, i2));
        }
    }

    android.service.autofill.FillEventHistory getFillEventHistory(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mEventHistory != null && isCalledByServiceLocked("getFillEventHistory", i)) {
                    return this.mEventHistory;
                }
                if (this.mAugmentedAutofillEventHistory != null && isCalledByAugmentedAutofillServiceLocked("getFillEventHistory", i)) {
                    return this.mAugmentedAutofillEventHistory;
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.service.autofill.UserData getUserData() {
        android.service.autofill.UserData userData;
        synchronized (this.mLock) {
            userData = this.mUserData;
        }
        return userData;
    }

    android.service.autofill.UserData getUserData(int i) {
        synchronized (this.mLock) {
            try {
                if (isCalledByServiceLocked("getUserData", i)) {
                    return this.mUserData;
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setUserData(int i, android.service.autofill.UserData userData) {
        synchronized (this.mLock) {
            try {
                if (isCalledByServiceLocked("setUserData", i)) {
                    this.mUserData = userData;
                    this.mMetricsLogger.write(new android.metrics.LogMaker(1272).setPackageName(getServicePackageName()).addTaggedData(914, java.lang.Integer.valueOf(this.mUserData == null ? 0 : this.mUserData.getCategoryIds().length)));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isCalledByServiceLocked(@android.annotation.NonNull java.lang.String str, int i) {
        int serviceUidLocked = getServiceUidLocked();
        if (serviceUidLocked != i) {
            android.util.Slog.w(TAG, str + "() called by UID " + i + ", but service UID is " + serviceUidLocked);
            return false;
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int getSupportedSmartSuggestionModesLocked() {
        return ((com.android.server.autofill.AutofillManagerService) this.mMaster).getSupportedSmartSuggestionModesLocked();
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void dumpLocked(java.lang.String str, java.io.PrintWriter printWriter) {
        super.dumpLocked(str, printWriter);
        java.lang.String str2 = str + "  ";
        printWriter.print(str);
        printWriter.print("UID: ");
        printWriter.println(getServiceUidLocked());
        printWriter.print(str);
        printWriter.print("Autofill Service Info: ");
        if (this.mInfo == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println();
            this.mInfo.dump(str2, printWriter);
        }
        printWriter.print(str);
        printWriter.print("Default component: ");
        printWriter.println(getContext().getString(android.R.string.config_defaultAccessibilityService));
        printWriter.println();
        printWriter.print(str);
        printWriter.println("mAugmentedAutofillName: ");
        printWriter.print(str2);
        ((com.android.server.autofill.AutofillManagerService) this.mMaster).mAugmentedAutofillResolver.dumpShort(printWriter, this.mUserId);
        printWriter.println();
        if (this.mRemoteAugmentedAutofillService != null) {
            printWriter.print(str);
            printWriter.println("RemoteAugmentedAutofillService: ");
            this.mRemoteAugmentedAutofillService.dump(str2, printWriter);
        }
        if (this.mRemoteAugmentedAutofillServiceInfo != null) {
            printWriter.print(str);
            printWriter.print("RemoteAugmentedAutofillServiceInfo: ");
            printWriter.println(this.mRemoteAugmentedAutofillServiceInfo);
        }
        printWriter.println();
        printWriter.print(str);
        printWriter.println("mFieldClassificationService for system detection");
        printWriter.print(str2);
        printWriter.print("Default component: ");
        printWriter.println(getContext().getString(android.R.string.config_defaultContextualSearchLegacyEnabled));
        printWriter.print(str2);
        ((com.android.server.autofill.AutofillManagerService) this.mMaster).mFieldClassificationResolver.dumpShort(printWriter, this.mUserId);
        printWriter.println();
        if (this.mRemoteFieldClassificationService != null) {
            printWriter.print(str);
            printWriter.println("RemoteFieldClassificationService: ");
            this.mRemoteFieldClassificationService.dump(str2, printWriter);
        } else {
            printWriter.print(str);
            printWriter.println("mRemoteFieldClassificationService: null");
        }
        if (this.mRemoteFieldClassificationServiceInfo != null) {
            printWriter.print(str);
            printWriter.print("RemoteFieldClassificationServiceInfo: ");
            printWriter.println(this.mRemoteFieldClassificationServiceInfo);
        } else {
            printWriter.print(str);
            printWriter.println("mRemoteFieldClassificationServiceInfo: null");
        }
        printWriter.println();
        printWriter.print(str);
        printWriter.print("Field classification enabled: ");
        printWriter.println(isFieldClassificationEnabledLocked());
        printWriter.print(str);
        printWriter.print("Compat pkgs: ");
        android.util.ArrayMap<java.lang.String, java.lang.Long> compatibilityPackagesLocked = getCompatibilityPackagesLocked();
        if (compatibilityPackagesLocked == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println(compatibilityPackagesLocked);
        }
        printWriter.print(str);
        printWriter.print("Inline Suggestions Enabled: ");
        printWriter.println(isInlineSuggestionsEnabledLocked());
        printWriter.print(str);
        printWriter.print("Last prune: ");
        printWriter.println(this.mLastPrune);
        this.mDisabledInfoCache.dump(this.mUserId, str, printWriter);
        int size = this.mSessions.size();
        if (size == 0) {
            printWriter.print(str);
            printWriter.println("No sessions");
        } else {
            printWriter.print(str);
            printWriter.print(size);
            printWriter.println(" sessions:");
            int i = 0;
            while (i < size) {
                printWriter.print(str);
                printWriter.print("#");
                int i2 = i + 1;
                printWriter.println(i2);
                this.mSessions.valueAt(i).dumpLocked(str2, printWriter);
                i = i2;
            }
        }
        printWriter.print(str);
        printWriter.print("Clients: ");
        if (this.mClients == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println();
            this.mClients.dump(printWriter, str2);
        }
        if (this.mEventHistory == null || this.mEventHistory.getEvents() == null || this.mEventHistory.getEvents().size() == 0) {
            printWriter.print(str);
            printWriter.println("No event on last fill response");
        } else {
            printWriter.print(str);
            printWriter.println("Events of last fill response:");
            printWriter.print(str);
            int size2 = this.mEventHistory.getEvents().size();
            for (int i3 = 0; i3 < size2; i3++) {
                android.service.autofill.FillEventHistory.Event event = this.mEventHistory.getEvents().get(i3);
                printWriter.println("  " + i3 + ": eventType=" + event.getType() + " datasetId=" + event.getDatasetId());
            }
        }
        printWriter.print(str);
        printWriter.print("User data: ");
        if (this.mUserData == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println();
            this.mUserData.dump(str2, printWriter);
        }
        printWriter.print(str);
        printWriter.println("Field Classification strategy: ");
        this.mFieldClassificationStrategy.dump(str2, printWriter);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void forceRemoveAllSessionsLocked() {
        int size = this.mSessions.size();
        if (size == 0) {
            this.mUi.destroyAll(null, null, false);
            return;
        }
        for (int i = size - 1; i >= 0; i--) {
            this.mSessions.valueAt(i).forceRemoveFromServiceLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void forceRemoveForAugmentedOnlySessionsLocked() {
        for (int size = this.mSessions.size() - 1; size >= 0; size--) {
            this.mSessions.valueAt(size).forceRemoveFromServiceIfForAugmentedOnlyLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void forceRemoveFinishedSessionsLocked() {
        for (int size = this.mSessions.size() - 1; size >= 0; size--) {
            com.android.server.autofill.Session valueAt = this.mSessions.valueAt(size);
            if (valueAt.isSaveUiShowingLocked()) {
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(TAG, "destroyFinishedSessionsLocked(): " + valueAt.id);
                }
                valueAt.forceRemoveFromServiceLocked();
            } else {
                valueAt.destroyAugmentedAutofillWindowsLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void listSessionsLocked(java.util.ArrayList<java.lang.String> arrayList) {
        java.lang.String flattenToShortString;
        java.lang.String flattenToShortString2;
        int size = this.mSessions.size();
        if (size <= 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            int keyAt = this.mSessions.keyAt(i);
            if (this.mInfo == null) {
                flattenToShortString = "no_svc";
            } else {
                flattenToShortString = this.mInfo.getServiceInfo().getComponentName().flattenToShortString();
            }
            if (this.mRemoteAugmentedAutofillServiceInfo == null) {
                flattenToShortString2 = "no_aug";
            } else {
                flattenToShortString2 = this.mRemoteAugmentedAutofillServiceInfo.getComponentName().flattenToShortString();
            }
            arrayList.add(java.lang.String.format("%d:%s:%s", java.lang.Integer.valueOf(keyAt), flattenToShortString, flattenToShortString2));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    android.util.ArrayMap<java.lang.String, java.lang.Long> getCompatibilityPackagesLocked() {
        if (this.mInfo != null) {
            return this.mInfo.getCompatibilityPackages();
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isInlineSuggestionsEnabledLocked() {
        if (this.mInfo != null) {
            return this.mInfo.isInlineSuggestionsEnabled();
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void requestSavedPasswordCount(com.android.internal.os.IResultReceiver iResultReceiver) {
        new com.android.server.autofill.RemoteFillService(getContext(), this.mInfo.getServiceInfo().getComponentName(), this.mUserId, null, ((com.android.server.autofill.AutofillManagerService) this.mMaster).isInstantServiceAllowed()).onSavedPasswordCountRequest(iResultReceiver);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    com.android.server.autofill.RemoteAugmentedAutofillService getRemoteAugmentedAutofillServiceLocked() {
        if (this.mRemoteAugmentedAutofillService == null) {
            java.lang.String serviceName = ((com.android.server.autofill.AutofillManagerService) this.mMaster).mAugmentedAutofillResolver.getServiceName(this.mUserId);
            if (serviceName == null) {
                if (((com.android.server.autofill.AutofillManagerService) this.mMaster).verbose) {
                    android.util.Slog.v(TAG, "getRemoteAugmentedAutofillServiceLocked(): not set");
                }
                return null;
            }
            android.util.Pair<android.content.pm.ServiceInfo, android.content.ComponentName> componentName = com.android.server.autofill.RemoteAugmentedAutofillService.getComponentName(serviceName, this.mUserId, ((com.android.server.autofill.AutofillManagerService) this.mMaster).mAugmentedAutofillResolver.isTemporary(this.mUserId));
            if (componentName == null) {
                return null;
            }
            this.mRemoteAugmentedAutofillServiceInfo = (android.content.pm.ServiceInfo) componentName.first;
            android.content.ComponentName componentName2 = (android.content.ComponentName) componentName.second;
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "getRemoteAugmentedAutofillServiceLocked(): " + componentName2);
            }
            this.mRemoteAugmentedAutofillService = new com.android.server.autofill.RemoteAugmentedAutofillService(getContext(), this.mRemoteAugmentedAutofillServiceInfo.applicationInfo.uid, componentName2, this.mUserId, new com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks() { // from class: com.android.server.autofill.AutofillManagerServiceImpl.1
                @Override // com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks
                public void resetLastResponse() {
                    com.android.server.autofill.AutofillManagerServiceImpl.this.resetLastAugmentedAutofillResponse();
                }

                @Override // com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks
                public void setLastResponse(int i) {
                    com.android.server.autofill.AutofillManagerServiceImpl.this.setLastAugmentedAutofillResponse(i);
                }

                @Override // com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks
                public void logAugmentedAutofillShown(int i, android.os.Bundle bundle) {
                    com.android.server.autofill.AutofillManagerServiceImpl.this.logAugmentedAutofillShown(i, bundle);
                }

                @Override // com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks
                public void logAugmentedAutofillSelected(int i, java.lang.String str, android.os.Bundle bundle) {
                    com.android.server.autofill.AutofillManagerServiceImpl.this.logAugmentedAutofillSelected(i, str, bundle);
                }

                @Override // com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks
                public void logAugmentedAutofillAuthenticationSelected(int i, java.lang.String str, android.os.Bundle bundle) {
                    com.android.server.autofill.AutofillManagerServiceImpl.this.logAugmentedAutofillAuthenticationSelected(i, str, bundle);
                }

                public void onServiceDied(@android.annotation.NonNull com.android.server.autofill.RemoteAugmentedAutofillService remoteAugmentedAutofillService) {
                    android.util.Slog.w(com.android.server.autofill.AutofillManagerServiceImpl.TAG, "remote augmented autofill service died");
                    com.android.server.autofill.RemoteAugmentedAutofillService remoteAugmentedAutofillService2 = com.android.server.autofill.AutofillManagerServiceImpl.this.mRemoteAugmentedAutofillService;
                    if (remoteAugmentedAutofillService2 != null) {
                        remoteAugmentedAutofillService2.unbind();
                    }
                    com.android.server.autofill.AutofillManagerServiceImpl.this.mRemoteAugmentedAutofillService = null;
                }
            }, ((com.android.server.autofill.AutofillManagerService) this.mMaster).isInstantServiceAllowed(), ((com.android.server.autofill.AutofillManagerService) this.mMaster).verbose, ((com.android.server.autofill.AutofillManagerService) this.mMaster).mAugmentedServiceIdleUnbindTimeoutMs, ((com.android.server.autofill.AutofillManagerService) this.mMaster).mAugmentedServiceRequestTimeoutMs);
        }
        return this.mRemoteAugmentedAutofillService;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    com.android.server.autofill.RemoteAugmentedAutofillService getRemoteAugmentedAutofillServiceIfCreatedLocked() {
        return this.mRemoteAugmentedAutofillService;
    }

    void updateRemoteAugmentedAutofillService() {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteAugmentedAutofillService != null) {
                    if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(TAG, "updateRemoteAugmentedAutofillService(): destroying old remote service");
                    }
                    forceRemoveForAugmentedOnlySessionsLocked();
                    this.mRemoteAugmentedAutofillService.unbind();
                    this.mRemoteAugmentedAutofillService = null;
                    this.mRemoteAugmentedAutofillServiceInfo = null;
                    resetAugmentedAutofillWhitelistLocked();
                }
                boolean isAugmentedAutofillServiceAvailableLocked = isAugmentedAutofillServiceAvailableLocked();
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "updateRemoteAugmentedAutofillService(): " + isAugmentedAutofillServiceAvailableLocked);
                }
                if (isAugmentedAutofillServiceAvailableLocked) {
                    this.mRemoteAugmentedAutofillService = getRemoteAugmentedAutofillServiceLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isAugmentedAutofillServiceAvailableLocked() {
        if (((com.android.server.autofill.AutofillManagerService) this.mMaster).verbose) {
            android.util.Slog.v(TAG, "isAugmentedAutofillService(): setupCompleted=" + isSetupCompletedLocked() + ", disabled=" + isDisabledByUserRestrictionsLocked() + ", augmentedService=" + ((com.android.server.autofill.AutofillManagerService) this.mMaster).mAugmentedAutofillResolver.getServiceName(this.mUserId));
        }
        if (!isSetupCompletedLocked() || isDisabledByUserRestrictionsLocked() || ((com.android.server.autofill.AutofillManagerService) this.mMaster).mAugmentedAutofillResolver.getServiceName(this.mUserId) == null) {
            return false;
        }
        return true;
    }

    boolean isAugmentedAutofillServiceForUserLocked(int i) {
        return this.mRemoteAugmentedAutofillServiceInfo != null && this.mRemoteAugmentedAutofillServiceInfo.applicationInfo.uid == i;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean setAugmentedAutofillWhitelistLocked(@android.annotation.Nullable java.util.List<java.lang.String> list, @android.annotation.Nullable java.util.List<android.content.ComponentName> list2, int i) {
        java.lang.String str;
        if (!isCalledByAugmentedAutofillServiceLocked("setAugmentedAutofillWhitelistLocked", i)) {
            return false;
        }
        if (((com.android.server.autofill.AutofillManagerService) this.mMaster).verbose) {
            android.util.Slog.v(TAG, "setAugmentedAutofillWhitelistLocked(packages=" + list + ", activities=" + list2 + ")");
        }
        allowlistForAugmentedAutofillPackages(list, list2);
        if (this.mRemoteAugmentedAutofillServiceInfo != null) {
            str = this.mRemoteAugmentedAutofillServiceInfo.getComponentName().flattenToShortString();
        } else {
            android.util.Slog.e(TAG, "setAugmentedAutofillWhitelistLocked(): no service");
            str = "N/A";
        }
        android.metrics.LogMaker addTaggedData = new android.metrics.LogMaker(1721).addTaggedData(908, str);
        if (list != null) {
            addTaggedData.addTaggedData(1722, java.lang.Integer.valueOf(list.size()));
        }
        if (list2 != null) {
            addTaggedData.addTaggedData(1723, java.lang.Integer.valueOf(list2.size()));
        }
        this.mMetricsLogger.write(addTaggedData);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isCalledByAugmentedAutofillServiceLocked(@android.annotation.NonNull java.lang.String str, int i) {
        if (getRemoteAugmentedAutofillServiceLocked() == null) {
            android.util.Slog.w(TAG, str + "() called by UID " + i + ", but there is no augmented autofill service defined for user " + getUserId());
            return false;
        }
        if (getAugmentedAutofillServiceUidLocked() != i) {
            android.util.Slog.w(TAG, str + "() called by UID " + i + ", but service UID is " + getAugmentedAutofillServiceUidLocked() + " for user " + getUserId());
            return false;
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int getAugmentedAutofillServiceUidLocked() {
        if (this.mRemoteAugmentedAutofillServiceInfo == null) {
            if (((com.android.server.autofill.AutofillManagerService) this.mMaster).verbose) {
                android.util.Slog.v(TAG, "getAugmentedAutofillServiceUid(): no mRemoteAugmentedAutofillServiceInfo");
                return -1;
            }
            return -1;
        }
        return this.mRemoteAugmentedAutofillServiceInfo.applicationInfo.uid;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isWhitelistedForAugmentedAutofillLocked(@android.annotation.NonNull android.content.ComponentName componentName) {
        return ((com.android.server.autofill.AutofillManagerService) this.mMaster).mAugmentedAutofillState.isWhitelisted(this.mUserId, componentName);
    }

    private void allowlistForAugmentedAutofillPackages(@android.annotation.Nullable java.util.List<java.lang.String> list, @android.annotation.Nullable java.util.List<android.content.ComponentName> list2) {
        synchronized (this.mLock) {
            try {
                if (((com.android.server.autofill.AutofillManagerService) this.mMaster).verbose) {
                    android.util.Slog.v(TAG, "whitelisting packages: " + list + "and activities: " + list2);
                }
                ((com.android.server.autofill.AutofillManagerService) this.mMaster).mAugmentedAutofillState.setWhitelist(this.mUserId, list, list2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void resetAugmentedAutofillWhitelistLocked() {
        if (((com.android.server.autofill.AutofillManagerService) this.mMaster).verbose) {
            android.util.Slog.v(TAG, "resetting augmented autofill whitelist");
        }
        ((com.android.server.autofill.AutofillManagerService) this.mMaster).mAugmentedAutofillState.resetWhitelist(this.mUserId);
    }

    private void sendStateToClients(boolean z) {
        int i;
        boolean z2;
        boolean isEnabledLocked;
        synchronized (this.mLock) {
            try {
                if (this.mClients == null) {
                    return;
                }
                android.os.RemoteCallbackList<android.view.autofill.IAutoFillManagerClient> remoteCallbackList = this.mClients;
                int beginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i2 = 0; i2 < beginBroadcast; i2++) {
                    try {
                        android.view.autofill.IAutoFillManagerClient broadcastItem = remoteCallbackList.getBroadcastItem(i2);
                        try {
                            synchronized (this.mLock) {
                                i = 1;
                                if (!z) {
                                    try {
                                        if (!isClientSessionDestroyedLocked(broadcastItem)) {
                                            z2 = false;
                                            isEnabledLocked = isEnabledLocked();
                                        }
                                    } catch (java.lang.Throwable th) {
                                        throw th;
                                    }
                                }
                                z2 = true;
                                isEnabledLocked = isEnabledLocked();
                            }
                            if (!isEnabledLocked) {
                                i = 0;
                            }
                            if (z2) {
                                i |= 2;
                            }
                            if (z) {
                                i |= 4;
                            }
                            if (com.android.server.autofill.Helper.sDebug) {
                                i |= 8;
                            }
                            if (com.android.server.autofill.Helper.sVerbose) {
                                i |= 16;
                            }
                            broadcastItem.setState(i);
                        } catch (android.os.RemoteException e) {
                        }
                    } finally {
                        remoteCallbackList.finishBroadcast();
                    }
                }
            } finally {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isClientSessionDestroyedLocked(android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient) {
        int size = this.mSessions.size();
        for (int i = 0; i < size; i++) {
            com.android.server.autofill.Session valueAt = this.mSessions.valueAt(i);
            if (valueAt.getClient().equals(iAutoFillManagerClient)) {
                return valueAt.isDestroyed();
            }
        }
        return true;
    }

    void disableAutofillForApp(@android.annotation.NonNull java.lang.String str, long j, int i, boolean z) {
        synchronized (this.mLock) {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + j;
            if (elapsedRealtime < 0) {
                elapsedRealtime = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            }
            this.mDisabledInfoCache.addDisabledAppLocked(this.mUserId, str, elapsedRealtime);
            this.mMetricsLogger.write(com.android.server.autofill.Helper.newLogMaker(1231, str, getServicePackageName(), i, z).addTaggedData(1145, java.lang.Integer.valueOf(j > 2147483647L ? Integer.MAX_VALUE : (int) j)));
        }
    }

    void disableAutofillForActivity(@android.annotation.NonNull android.content.ComponentName componentName, long j, int i, boolean z) {
        int i2;
        synchronized (this.mLock) {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + j;
            if (elapsedRealtime < 0) {
                elapsedRealtime = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            }
            this.mDisabledInfoCache.addDisabledActivityLocked(this.mUserId, componentName, elapsedRealtime);
            if (j > 2147483647L) {
                i2 = Integer.MAX_VALUE;
            } else {
                i2 = (int) j;
            }
            this.mMetricsLogger.write(com.android.server.autofill.Helper.newLogMaker(1232, componentName, getServicePackageName(), i, z).addTaggedData(1145, java.lang.Integer.valueOf(i2)));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isAutofillDisabledLocked(@android.annotation.NonNull android.content.ComponentName componentName) {
        return this.mDisabledInfoCache.isAutofillDisabledLocked(this.mUserId, componentName);
    }

    boolean isFieldClassificationEnabled(int i) {
        synchronized (this.mLock) {
            try {
                if (!isCalledByServiceLocked("isFieldClassificationEnabled", i)) {
                    return false;
                }
                return isFieldClassificationEnabledLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isFieldClassificationEnabledLocked() {
        return android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "autofill_field_classification", 1, this.mUserId) == 1;
    }

    com.android.server.autofill.FieldClassificationStrategy getFieldClassificationStrategy() {
        return this.mFieldClassificationStrategy;
    }

    java.lang.String[] getAvailableFieldClassificationAlgorithms(int i) {
        synchronized (this.mLock) {
            try {
                if (isCalledByServiceLocked("getFCAlgorithms()", i)) {
                    return this.mFieldClassificationStrategy.getAvailableAlgorithms();
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    java.lang.String getDefaultFieldClassificationAlgorithm(int i) {
        synchronized (this.mLock) {
            try {
                if (isCalledByServiceLocked("getDefaultFCAlgorithm()", i)) {
                    return this.mFieldClassificationStrategy.getDefaultAlgorithm();
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    com.android.server.autofill.RemoteInlineSuggestionRenderService getRemoteInlineSuggestionRenderServiceLocked() {
        if (this.mRemoteInlineSuggestionRenderService == null) {
            android.content.ComponentName serviceComponentName = com.android.server.autofill.RemoteInlineSuggestionRenderService.getServiceComponentName(getContext(), this.mUserId);
            if (serviceComponentName == null) {
                android.util.Slog.w(TAG, "No valid component found for InlineSuggestionRenderService");
                return null;
            }
            this.mRemoteInlineSuggestionRenderService = new com.android.server.autofill.RemoteInlineSuggestionRenderService(getContext(), serviceComponentName, "android.service.autofill.InlineSuggestionRenderService", this.mUserId, new com.android.server.autofill.AutofillManagerServiceImpl.InlineSuggestionRenderCallbacksImpl(), ((com.android.server.autofill.AutofillManagerService) this.mMaster).isBindInstantServiceAllowed(), ((com.android.server.autofill.AutofillManagerService) this.mMaster).verbose);
        }
        return this.mRemoteInlineSuggestionRenderService;
    }

    private class InlineSuggestionRenderCallbacksImpl implements com.android.server.autofill.RemoteInlineSuggestionRenderService.InlineSuggestionRenderCallbacks {
        private InlineSuggestionRenderCallbacksImpl() {
        }

        public void onServiceDied(@android.annotation.NonNull com.android.server.autofill.RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderService) {
            android.util.Slog.w(com.android.server.autofill.AutofillManagerServiceImpl.TAG, "remote service died: " + remoteInlineSuggestionRenderService);
            com.android.server.autofill.AutofillManagerServiceImpl.this.mRemoteInlineSuggestionRenderService = null;
        }
    }

    void onSwitchInputMethod() {
        synchronized (this.mLock) {
            try {
                int size = this.mSessions.size();
                for (int i = 0; i < size; i++) {
                    this.mSessions.valueAt(i).onSwitchInputMethodLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    com.android.server.autofill.RemoteFieldClassificationService getRemoteFieldClassificationServiceLocked() {
        if (this.mRemoteFieldClassificationService == null) {
            java.lang.String serviceName = ((com.android.server.autofill.AutofillManagerService) this.mMaster).mFieldClassificationResolver.getServiceName(this.mUserId);
            if (serviceName == null) {
                if (((com.android.server.autofill.AutofillManagerService) this.mMaster).verbose) {
                    android.util.Slog.v(TAG, "getRemoteFieldClassificationServiceLocked(): not set");
                }
                return null;
            }
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "getRemoteFieldClassificationServiceLocked serviceName: " + serviceName);
            }
            android.util.Pair<android.content.pm.ServiceInfo, android.content.ComponentName> componentName = com.android.server.autofill.RemoteFieldClassificationService.getComponentName(serviceName, this.mUserId, ((com.android.server.autofill.AutofillManagerService) this.mMaster).mFieldClassificationResolver.isTemporary(this.mUserId));
            if (componentName == null) {
                android.util.Slog.w(TAG, "RemoteFieldClassificationService.getComponentName returned null with serviceName: " + serviceName);
                return null;
            }
            this.mRemoteFieldClassificationServiceInfo = (android.content.pm.ServiceInfo) componentName.first;
            android.content.ComponentName componentName2 = (android.content.ComponentName) componentName.second;
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "getRemoteFieldClassificationServiceLocked(): " + componentName2);
            }
            this.mRemoteFieldClassificationService = new com.android.server.autofill.RemoteFieldClassificationService(getContext(), componentName2, this.mRemoteFieldClassificationServiceInfo.applicationInfo.uid, this.mUserId);
        }
        return this.mRemoteFieldClassificationService;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    com.android.server.autofill.RemoteFieldClassificationService getRemoteFieldClassificationServiceIfCreatedLocked() {
        return this.mRemoteFieldClassificationService;
    }

    public boolean isPccClassificationEnabled() {
        boolean isPccClassificationEnabledInternal = isPccClassificationEnabledInternal();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "pccEnabled: " + isPccClassificationEnabledInternal);
        }
        return isPccClassificationEnabledInternal;
    }

    public boolean isPccClassificationEnabledInternal() {
        boolean z;
        if (!((com.android.server.autofill.AutofillManagerService) this.mMaster).isPccClassificationFlagEnabled()) {
            return false;
        }
        synchronized (this.mLock) {
            z = getRemoteFieldClassificationServiceLocked() != null;
        }
        return z;
    }

    public boolean isAutofillCredmanIntegrationEnabled() {
        return ((com.android.server.autofill.AutofillManagerService) this.mMaster).isAutofillCredmanIntegrationEnabled();
    }

    void updateRemoteFieldClassificationService() {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteFieldClassificationService != null) {
                    if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(TAG, "updateRemoteFieldClassificationService(): destroying old remote service");
                    }
                    this.mRemoteFieldClassificationService.unbind();
                    this.mRemoteFieldClassificationService = null;
                    this.mRemoteFieldClassificationServiceInfo = null;
                }
                boolean isFieldClassificationServiceAvailableLocked = isFieldClassificationServiceAvailableLocked();
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "updateRemoteFieldClassificationService(): " + isFieldClassificationServiceAvailableLocked);
                }
                if (isFieldClassificationServiceAvailableLocked) {
                    this.mRemoteFieldClassificationService = getRemoteFieldClassificationServiceLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isFieldClassificationServiceAvailableLocked() {
        if (((com.android.server.autofill.AutofillManagerService) this.mMaster).verbose) {
            android.util.Slog.v(TAG, "isFieldClassificationService(): setupCompleted=" + isSetupCompletedLocked() + ", disabled=" + isDisabledByUserRestrictionsLocked() + ", augmentedService=" + ((com.android.server.autofill.AutofillManagerService) this.mMaster).mFieldClassificationResolver.getServiceName(this.mUserId));
        }
        if (!isSetupCompletedLocked() || isDisabledByUserRestrictionsLocked() || ((com.android.server.autofill.AutofillManagerService) this.mMaster).mFieldClassificationResolver.getServiceName(this.mUserId) == null) {
            return false;
        }
        return true;
    }

    boolean isRemoteClassificationServiceForUserLocked(int i) {
        return this.mRemoteFieldClassificationServiceInfo != null && this.mRemoteFieldClassificationServiceInfo.applicationInfo.uid == i;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("AutofillManagerServiceImpl: [userId=");
        sb.append(this.mUserId);
        sb.append(", component=");
        sb.append(this.mInfo != null ? this.mInfo.getServiceInfo().getComponentName() : null);
        sb.append("]");
        return sb.toString();
    }

    private class PruneTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void> {
        private PruneTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public java.lang.Void doInBackground(java.lang.Void... voidArr) {
            int size;
            android.util.SparseArray sparseArray;
            int i;
            synchronized (com.android.server.autofill.AutofillManagerServiceImpl.this.mLock) {
                try {
                    size = com.android.server.autofill.AutofillManagerServiceImpl.this.mSessions.size();
                    sparseArray = new android.util.SparseArray(size);
                    for (int i2 = 0; i2 < size; i2++) {
                        com.android.server.autofill.Session session = (com.android.server.autofill.Session) com.android.server.autofill.AutofillManagerServiceImpl.this.mSessions.valueAt(i2);
                        sparseArray.put(session.id, session.getActivityTokenLocked());
                    }
                } finally {
                }
            }
            com.android.server.wm.ActivityTaskManagerInternal activityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
            int i3 = 0;
            while (i3 < size) {
                if (activityTaskManagerInternal.getActivityName((android.os.IBinder) sparseArray.valueAt(i3)) != null) {
                    sparseArray.removeAt(i3);
                    i3--;
                    size--;
                }
                i3++;
            }
            synchronized (com.android.server.autofill.AutofillManagerServiceImpl.this.mLock) {
                for (i = 0; i < size; i++) {
                    try {
                        com.android.server.autofill.Session session2 = (com.android.server.autofill.Session) com.android.server.autofill.AutofillManagerServiceImpl.this.mSessions.get(sparseArray.keyAt(i));
                        if (session2 != null && sparseArray.valueAt(i) == session2.getActivityTokenLocked()) {
                            if (session2.isSaveUiShowingLocked()) {
                                if (com.android.server.autofill.Helper.sVerbose) {
                                    android.util.Slog.v(com.android.server.autofill.AutofillManagerServiceImpl.TAG, "Session " + session2.id + " is saving");
                                }
                            } else {
                                if (com.android.server.autofill.Helper.sDebug) {
                                    android.util.Slog.i(com.android.server.autofill.AutofillManagerServiceImpl.TAG, "Prune session " + session2.id + " (" + session2.getActivityTokenLocked() + ")");
                                }
                                session2.removeFromServiceLocked();
                            }
                        }
                    } finally {
                    }
                }
            }
            return null;
        }
    }
}
