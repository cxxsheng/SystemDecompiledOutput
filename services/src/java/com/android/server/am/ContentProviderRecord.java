package com.android.server.am;

/* loaded from: classes.dex */
final class ContentProviderRecord implements android.content.ComponentName.WithComponentName {
    static final int MAX_RETRY_COUNT = 3;
    final android.content.pm.ApplicationInfo appInfo;
    final java.util.ArrayList<com.android.server.am.ContentProviderConnection> connections = new java.util.ArrayList<>();
    int externalProcessNoHandleCount;
    android.util.ArrayMap<android.os.IBinder, com.android.server.am.ContentProviderRecord.ExternalProcessHandle> externalProcessTokenToHandle;
    public final android.content.pm.ProviderInfo info;
    com.android.server.am.ProcessRecord launchingApp;
    int mRestartCount;
    final android.content.ComponentName name;
    public boolean noReleaseNeeded;
    com.android.server.am.ProcessRecord proc;
    public android.content.IContentProvider provider;
    final com.android.server.am.ActivityManagerService service;
    java.lang.String shortStringName;
    final boolean singleton;
    java.lang.String stringName;
    final int uid;

    public ContentProviderRecord(com.android.server.am.ActivityManagerService activityManagerService, android.content.pm.ProviderInfo providerInfo, android.content.pm.ApplicationInfo applicationInfo, android.content.ComponentName componentName, boolean z) {
        this.service = activityManagerService;
        this.info = providerInfo;
        this.uid = applicationInfo.uid;
        this.appInfo = applicationInfo;
        this.name = componentName;
        this.singleton = z;
        this.noReleaseNeeded = (this.uid == 0 || this.uid == 1000) && (componentName == null || !"com.android.settings".equals(componentName.getPackageName()));
    }

    public ContentProviderRecord(com.android.server.am.ContentProviderRecord contentProviderRecord) {
        this.service = contentProviderRecord.service;
        this.info = contentProviderRecord.info;
        this.uid = contentProviderRecord.uid;
        this.appInfo = contentProviderRecord.appInfo;
        this.name = contentProviderRecord.name;
        this.singleton = contentProviderRecord.singleton;
        this.noReleaseNeeded = contentProviderRecord.noReleaseNeeded;
    }

    public android.app.ContentProviderHolder newHolder(com.android.server.am.ContentProviderConnection contentProviderConnection, boolean z) {
        android.app.ContentProviderHolder contentProviderHolder = new android.app.ContentProviderHolder(this.info);
        contentProviderHolder.provider = this.provider;
        contentProviderHolder.noReleaseNeeded = this.noReleaseNeeded;
        contentProviderHolder.connection = contentProviderConnection;
        contentProviderHolder.mLocal = z;
        return contentProviderHolder;
    }

    public void setProcess(com.android.server.am.ProcessRecord processRecord) {
        this.proc = processRecord;
        for (int size = this.connections.size() - 1; size >= 0; size--) {
            com.android.server.am.ContentProviderConnection contentProviderConnection = this.connections.get(size);
            if (processRecord != null) {
                contentProviderConnection.startAssociationIfNeeded();
            } else {
                contentProviderConnection.stopAssociation();
            }
        }
        if (this.externalProcessTokenToHandle != null) {
            for (int size2 = this.externalProcessTokenToHandle.size() - 1; size2 >= 0; size2--) {
                com.android.server.am.ContentProviderRecord.ExternalProcessHandle valueAt = this.externalProcessTokenToHandle.valueAt(size2);
                if (processRecord != null) {
                    valueAt.startAssociationIfNeeded(this);
                } else {
                    valueAt.stopAssociation();
                }
            }
        }
    }

    public boolean canRunHere(com.android.server.am.ProcessRecord processRecord) {
        return (this.info.multiprocess || this.info.processName.equals(processRecord.processName)) && this.uid == processRecord.info.uid;
    }

    public void addExternalProcessHandleLocked(android.os.IBinder iBinder, int i, java.lang.String str) {
        if (iBinder == null) {
            this.externalProcessNoHandleCount++;
            return;
        }
        if (this.externalProcessTokenToHandle == null) {
            this.externalProcessTokenToHandle = new android.util.ArrayMap<>();
        }
        com.android.server.am.ContentProviderRecord.ExternalProcessHandle externalProcessHandle = this.externalProcessTokenToHandle.get(iBinder);
        if (externalProcessHandle == null) {
            externalProcessHandle = new com.android.server.am.ContentProviderRecord.ExternalProcessHandle(iBinder, i, str);
            this.externalProcessTokenToHandle.put(iBinder, externalProcessHandle);
            externalProcessHandle.startAssociationIfNeeded(this);
        }
        externalProcessHandle.mAcquisitionCount++;
    }

    public boolean removeExternalProcessHandleLocked(android.os.IBinder iBinder) {
        boolean z;
        com.android.server.am.ContentProviderRecord.ExternalProcessHandle externalProcessHandle;
        if (hasExternalProcessHandles()) {
            if (this.externalProcessTokenToHandle != null && (externalProcessHandle = this.externalProcessTokenToHandle.get(iBinder)) != null) {
                externalProcessHandle.mAcquisitionCount--;
                if (externalProcessHandle.mAcquisitionCount != 0) {
                    z = true;
                } else {
                    removeExternalProcessHandleInternalLocked(iBinder);
                    return true;
                }
            } else {
                z = false;
            }
            if (!z) {
                this.externalProcessNoHandleCount--;
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeExternalProcessHandleInternalLocked(android.os.IBinder iBinder) {
        com.android.server.am.ContentProviderRecord.ExternalProcessHandle externalProcessHandle = this.externalProcessTokenToHandle.get(iBinder);
        externalProcessHandle.unlinkFromOwnDeathLocked();
        externalProcessHandle.stopAssociation();
        this.externalProcessTokenToHandle.remove(iBinder);
        if (this.externalProcessTokenToHandle.size() == 0) {
            this.externalProcessTokenToHandle = null;
        }
    }

    public boolean hasExternalProcessHandles() {
        return this.externalProcessTokenToHandle != null || this.externalProcessNoHandleCount > 0;
    }

    public boolean hasConnectionOrHandle() {
        return !this.connections.isEmpty() || hasExternalProcessHandles();
    }

    void onProviderPublishStatusLocked(boolean z) {
        int size = this.connections.size();
        for (int i = 0; i < size; i++) {
            com.android.server.am.ContentProviderConnection contentProviderConnection = this.connections.get(i);
            if (contentProviderConnection.waiting && contentProviderConnection.client != null) {
                com.android.server.am.ProcessRecord processRecord = contentProviderConnection.client;
                if (!z) {
                    if (this.launchingApp == null) {
                        android.util.Slog.w("ActivityManager", "Unable to launch app " + this.appInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.appInfo.uid + " for provider " + this.info.authority + ": launching app became null");
                        com.android.server.am.EventLogTags.writeAmProviderLostProcess(android.os.UserHandle.getUserId(this.appInfo.uid), this.appInfo.packageName, this.appInfo.uid, this.info.authority);
                    } else {
                        android.util.Slog.wtf("ActivityManager", "Timeout waiting for provider " + this.appInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.appInfo.uid + " for provider " + this.info.authority + " caller=" + processRecord);
                    }
                }
                android.app.IApplicationThread thread = processRecord.getThread();
                if (thread != null) {
                    try {
                        thread.notifyContentProviderPublishStatus(newHolder(z ? contentProviderConnection : null, false), this.info.authority, contentProviderConnection.mExpectedUserId, z);
                    } catch (android.os.RemoteException e) {
                    }
                }
            }
            contentProviderConnection.waiting = false;
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        if (z) {
            printWriter.print(str);
            printWriter.print("package=");
            printWriter.print(this.info.applicationInfo.packageName);
            printWriter.print(" process=");
            printWriter.println(this.info.processName);
        }
        printWriter.print(str);
        printWriter.print("proc=");
        printWriter.println(this.proc);
        if (this.launchingApp != null) {
            printWriter.print(str);
            printWriter.print("launchingApp=");
            printWriter.println(this.launchingApp);
        }
        if (z) {
            printWriter.print(str);
            printWriter.print("uid=");
            printWriter.print(this.uid);
            printWriter.print(" provider=");
            printWriter.println(this.provider);
        }
        if (this.singleton) {
            printWriter.print(str);
            printWriter.print("singleton=");
            printWriter.println(this.singleton);
        }
        printWriter.print(str);
        printWriter.print("authority=");
        printWriter.println(this.info.authority);
        if (z && (this.info.isSyncable || this.info.multiprocess || this.info.initOrder != 0)) {
            printWriter.print(str);
            printWriter.print("isSyncable=");
            printWriter.print(this.info.isSyncable);
            printWriter.print(" multiprocess=");
            printWriter.print(this.info.multiprocess);
            printWriter.print(" initOrder=");
            printWriter.println(this.info.initOrder);
        }
        if (z) {
            if (hasExternalProcessHandles()) {
                printWriter.print(str);
                printWriter.print("externals:");
                if (this.externalProcessTokenToHandle != null) {
                    printWriter.print(" w/token=");
                    printWriter.print(this.externalProcessTokenToHandle.size());
                }
                if (this.externalProcessNoHandleCount > 0) {
                    printWriter.print(" notoken=");
                    printWriter.print(this.externalProcessNoHandleCount);
                }
                printWriter.println();
            }
        } else if (this.connections.size() > 0 || this.externalProcessNoHandleCount > 0) {
            printWriter.print(str);
            printWriter.print(this.connections.size());
            printWriter.print(" connections, ");
            printWriter.print(this.externalProcessNoHandleCount);
            printWriter.println(" external handles");
        }
        if (this.connections.size() > 0) {
            if (z) {
                printWriter.print(str);
                printWriter.println("Connections:");
            }
            for (int i = 0; i < this.connections.size(); i++) {
                com.android.server.am.ContentProviderConnection contentProviderConnection = this.connections.get(i);
                printWriter.print(str);
                printWriter.print("  -> ");
                printWriter.println(contentProviderConnection.toClientString());
                if (contentProviderConnection.provider != this) {
                    printWriter.print(str);
                    printWriter.print("    *** WRONG PROVIDER: ");
                    printWriter.println(contentProviderConnection.provider);
                }
            }
        }
    }

    public java.lang.String toString() {
        if (this.stringName != null) {
            return this.stringName;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("ContentProviderRecord{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(" u");
        sb.append(android.os.UserHandle.getUserId(this.uid));
        sb.append(' ');
        sb.append(this.name.flattenToShortString());
        sb.append('}');
        java.lang.String sb2 = sb.toString();
        this.stringName = sb2;
        return sb2;
    }

    public java.lang.String toShortString() {
        if (this.shortStringName != null) {
            return this.shortStringName;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append('/');
        sb.append(this.name.flattenToShortString());
        java.lang.String sb2 = sb.toString();
        this.shortStringName = sb2;
        return sb2;
    }

    private class ExternalProcessHandle implements android.os.IBinder.DeathRecipient {
        private static final java.lang.String LOG_TAG = "ExternalProcessHanldle";
        int mAcquisitionCount;
        com.android.internal.app.procstats.AssociationState.SourceState mAssociation;
        final java.lang.String mOwningProcessName;
        final int mOwningUid;
        private java.lang.Object mProcStatsLock;
        final android.os.IBinder mToken;

        public ExternalProcessHandle(android.os.IBinder iBinder, int i, java.lang.String str) {
            this.mToken = iBinder;
            this.mOwningUid = i;
            this.mOwningProcessName = str;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Couldn't register for death for token: " + this.mToken, e);
            }
        }

        public void unlinkFromOwnDeathLocked() {
            this.mToken.unlinkToDeath(this, 0);
        }

        public void startAssociationIfNeeded(com.android.server.am.ContentProviderRecord contentProviderRecord) {
            if (this.mAssociation == null && contentProviderRecord.proc != null) {
                if (contentProviderRecord.appInfo.uid != this.mOwningUid || !contentProviderRecord.info.processName.equals(this.mOwningProcessName)) {
                    com.android.internal.app.procstats.ProcessStats.ProcessStateHolder processStateHolder = contentProviderRecord.proc.getPkgList().get(contentProviderRecord.name.getPackageName());
                    if (processStateHolder == null) {
                        android.util.Slog.wtf("ActivityManager", "No package in referenced provider " + contentProviderRecord.name.toShortString() + ": proc=" + contentProviderRecord.proc);
                        return;
                    }
                    if (processStateHolder.pkg == null) {
                        android.util.Slog.wtf("ActivityManager", "Inactive holder in referenced provider " + contentProviderRecord.name.toShortString() + ": proc=" + contentProviderRecord.proc);
                        return;
                    }
                    this.mProcStatsLock = contentProviderRecord.proc.mService.mProcessStats.mLock;
                    synchronized (this.mProcStatsLock) {
                        this.mAssociation = processStateHolder.pkg.getAssociationStateLocked(processStateHolder.state, contentProviderRecord.name.getClassName()).startSource(this.mOwningUid, this.mOwningProcessName, (java.lang.String) null);
                    }
                }
            }
        }

        public void stopAssociation() {
            if (this.mAssociation != null) {
                synchronized (this.mProcStatsLock) {
                    this.mAssociation.stop();
                }
                this.mAssociation = null;
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.ContentProviderRecord.this.service;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    if (com.android.server.am.ContentProviderRecord.this.hasExternalProcessHandles() && com.android.server.am.ContentProviderRecord.this.externalProcessTokenToHandle.get(this.mToken) != null) {
                        com.android.server.am.ContentProviderRecord.this.removeExternalProcessHandleInternalLocked(this.mToken);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    public android.content.ComponentName getComponentName() {
        return this.name;
    }
}
