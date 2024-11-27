package com.android.server.pm;

/* loaded from: classes2.dex */
final class PreferredActivityHelper {
    private static final java.lang.String TAG_DEFAULT_APPS = "da";
    private static final java.lang.String TAG_PREFERRED_BACKUP = "pa";
    private final com.android.server.pm.BroadcastHelper mBroadcastHelper;
    private final com.android.server.pm.PackageManagerService mPm;

    /* JADX INFO: Access modifiers changed from: private */
    interface BlobXmlRestorer {
        void apply(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException;
    }

    PreferredActivityHelper(com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.BroadcastHelper broadcastHelper) {
        this.mPm = packageManagerService;
        this.mBroadcastHelper = broadcastHelper;
    }

    private android.content.pm.ResolveInfo findPreferredActivityNotLocked(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, java.util.List<android.content.pm.ResolveInfo> list, boolean z, boolean z2, boolean z3, int i) {
        return findPreferredActivityNotLocked(computer, intent, str, j, list, z, z2, z3, i, android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) >= 10000);
    }

    public android.content.pm.ResolveInfo findPreferredActivityNotLocked(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, java.util.List<android.content.pm.ResolveInfo> list, boolean z, boolean z2, boolean z3, int i, boolean z4) {
        if (java.lang.Thread.holdsLock(this.mPm.mLock)) {
            android.util.Slog.wtf("PackageManager", "Calling thread " + java.lang.Thread.currentThread().getName() + " is holding mLock", new java.lang.Throwable());
        }
        if (!this.mPm.mUserManager.exists(i)) {
            return null;
        }
        com.android.server.pm.PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityInternal = computer.findPreferredActivityInternal(intent, str, j, list, z, z2, z3, i, z4);
        if (findPreferredActivityInternal.mChanged) {
            this.mPm.scheduleWritePackageRestrictions(i);
        }
        if (z3 && findPreferredActivityInternal.mPreferredResolveInfo == null) {
            android.util.Slog.v("PackageManager", "No preferred activity to return");
        }
        return findPreferredActivityInternal.mPreferredResolveInfo;
    }

    public void clearPackagePreferredActivities(java.lang.String str, int i) {
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPm.clearPackagePreferredActivitiesLPw(str, sparseBooleanArray, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (sparseBooleanArray.size() > 0) {
            updateDefaultHomeNotLocked(this.mPm.snapshotComputer(), sparseBooleanArray);
            this.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i);
            this.mPm.scheduleWritePackageRestrictions(i);
        }
    }

    public boolean updateDefaultHomeNotLocked(@android.annotation.NonNull com.android.server.pm.Computer computer, final int i) {
        if (java.lang.Thread.holdsLock(this.mPm.mLock)) {
            android.util.Slog.wtf("PackageManager", "Calling thread " + java.lang.Thread.currentThread().getName() + " is holding mLock", new java.lang.Throwable());
        }
        if (!this.mPm.isSystemReady()) {
            return false;
        }
        android.content.Intent homeIntent = computer.getHomeIntent();
        android.content.pm.ResolveInfo findPreferredActivityNotLocked = findPreferredActivityNotLocked(computer, homeIntent, null, 0L, computer.queryIntentActivitiesInternal(homeIntent, null, 786432L, i), true, false, false, i);
        java.lang.String str = (findPreferredActivityNotLocked == null || findPreferredActivityNotLocked.activityInfo == null) ? null : findPreferredActivityNotLocked.activityInfo.packageName;
        if (android.text.TextUtils.equals(this.mPm.getActiveLauncherPackageName(i), str)) {
            return false;
        }
        java.lang.String[] packagesForUid = computer.getPackagesForUid(android.os.Binder.getCallingUid());
        if ((packagesForUid == null || !com.android.internal.util.ArrayUtils.contains(packagesForUid, this.mPm.mRequiredPermissionControllerPackage)) && str != null) {
            return this.mPm.setActiveLauncherPackage(str, i, new java.util.function.Consumer() { // from class: com.android.server.pm.PreferredActivityHelper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.PreferredActivityHelper.this.lambda$updateDefaultHomeNotLocked$0(i, (java.lang.Boolean) obj);
                }
            });
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDefaultHomeNotLocked$0(int i, java.lang.Boolean bool) {
        if (bool.booleanValue()) {
            this.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i);
        }
    }

    public void addPreferredActivity(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.server.pm.WatchedIntentFilter watchedIntentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, boolean z, int i2, java.lang.String str, boolean z2) {
        int callingUid = android.os.Binder.getCallingUid();
        computer.enforceCrossUserPermission(callingUid, i2, true, false, "add preferred activity");
        if (this.mPm.mContext.checkCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS") != 0) {
            if (computer.getUidTargetSdkVersion(callingUid) < 8) {
                android.util.Slog.w("PackageManager", "Ignoring addPreferredActivity() from uid " + callingUid);
                return;
            }
            this.mPm.mContext.enforceCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS", null);
        }
        if (watchedIntentFilter.countActions() == 0) {
            android.util.Slog.w("PackageManager", "Cannot set a preferred activity with no filter actions");
            return;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.PreferredIntentResolver editPreferredActivitiesLPw = this.mPm.mSettings.editPreferredActivitiesLPw(i2);
                java.util.ArrayList<com.android.server.pm.PreferredActivity> findFilters = editPreferredActivitiesLPw.findFilters(watchedIntentFilter);
                if (z2 && findFilters != null) {
                    com.android.server.pm.Settings.removeFilters(editPreferredActivitiesLPw, watchedIntentFilter, findFilters);
                }
                editPreferredActivitiesLPw.addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) this.mPm.snapshotComputer(), (com.android.server.pm.Computer) new com.android.server.pm.PreferredActivity(watchedIntentFilter, i, componentNameArr, componentName, z));
                this.mPm.scheduleWritePackageRestrictions(i2);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (!isHomeFilter(watchedIntentFilter) || !updateDefaultHomeNotLocked(this.mPm.snapshotComputer(), i2)) {
            this.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i2);
        }
    }

    public void replacePreferredActivity(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.server.pm.WatchedIntentFilter watchedIntentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2) {
        if (watchedIntentFilter.countActions() != 1) {
            throw new java.lang.IllegalArgumentException("replacePreferredActivity expects filter to have only 1 action.");
        }
        if (watchedIntentFilter.countDataAuthorities() != 0 || watchedIntentFilter.countDataPaths() != 0 || watchedIntentFilter.countDataSchemes() > 1 || watchedIntentFilter.countDataTypes() != 0) {
            throw new java.lang.IllegalArgumentException("replacePreferredActivity expects filter to have no data authorities, paths, or types; and at most one scheme.");
        }
        int callingUid = android.os.Binder.getCallingUid();
        computer.enforceCrossUserPermission(callingUid, i2, true, false, "replace preferred activity");
        if (this.mPm.mContext.checkCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS") != 0) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    if (this.mPm.snapshotComputer().getUidTargetSdkVersion(callingUid) < 8) {
                        android.util.Slog.w("PackageManager", "Ignoring replacePreferredActivity() from uid " + android.os.Binder.getCallingUid());
                        return;
                    }
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    this.mPm.mContext.enforceCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS", null);
                } finally {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                }
            }
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock2) {
            try {
                com.android.server.pm.PreferredIntentResolver preferredActivities = this.mPm.mSettings.getPreferredActivities(i2);
                if (preferredActivities != null) {
                    java.util.ArrayList<com.android.server.pm.PreferredActivity> findFilters = preferredActivities.findFilters(watchedIntentFilter);
                    if (findFilters != null && findFilters.size() == 1) {
                        com.android.server.pm.PreferredActivity preferredActivity = findFilters.get(0);
                        if (preferredActivity.mPref.mAlways) {
                            if (preferredActivity.mPref.mComponent.equals(componentName) && preferredActivity.mPref.mMatch == (268369920 & i)) {
                                if (preferredActivity.mPref.sameSet(componentNameArr)) {
                                    return;
                                }
                            }
                        }
                    }
                    if (findFilters != null) {
                        com.android.server.pm.Settings.removeFilters(preferredActivities, watchedIntentFilter, findFilters);
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                addPreferredActivity(this.mPm.snapshotComputer(), watchedIntentFilter, i, componentNameArr, componentName, true, i2, "Replacing preferred", false);
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
    }

    public void clearPackagePreferredActivities(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (computer.getInstantAppPackageName(callingUid) != null) {
            return;
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if ((packageStateInternal == null || !computer.isCallerSameApp(str, callingUid)) && this.mPm.mContext.checkCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS") != 0) {
            if (computer.getUidTargetSdkVersion(callingUid) < 8) {
                android.util.Slog.w("PackageManager", "Ignoring clearPackagePreferredActivities() from uid " + callingUid);
                return;
            }
            this.mPm.mContext.enforceCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS", null);
        }
        if (packageStateInternal != null && computer.shouldFilterApplication(packageStateInternal, callingUid, android.os.UserHandle.getUserId(callingUid))) {
            return;
        }
        clearPackagePreferredActivities(str, android.os.UserHandle.getCallingUserId());
    }

    void updateDefaultHomeNotLocked(@android.annotation.NonNull com.android.server.pm.Computer computer, android.util.SparseBooleanArray sparseBooleanArray) {
        if (java.lang.Thread.holdsLock(this.mPm.mLock)) {
            android.util.Slog.wtf("PackageManager", "Calling thread " + java.lang.Thread.currentThread().getName() + " is holding mLock", new java.lang.Throwable());
        }
        for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
            updateDefaultHomeNotLocked(computer, sparseBooleanArray.keyAt(size));
        }
    }

    public void setHomeActivity(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.ComponentName componentName, int i) {
        if (computer.getInstantAppPackageName(android.os.Binder.getCallingUid()) != null) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        computer.getHomeActivitiesAsUser(arrayList, i);
        int size = arrayList.size();
        android.content.ComponentName[] componentNameArr = new android.content.ComponentName[size];
        boolean z = false;
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.ActivityInfo activityInfo = ((android.content.pm.ResolveInfo) arrayList.get(i2)).activityInfo;
            android.content.ComponentName componentName2 = new android.content.ComponentName(activityInfo.packageName, activityInfo.name);
            componentNameArr[i2] = componentName2;
            if (!z && componentName2.equals(componentName)) {
                z = true;
            }
        }
        if (!z) {
            throw new java.lang.IllegalArgumentException("Component " + componentName + " cannot be home on user " + i);
        }
        replacePreferredActivity(computer, getHomeFilter(), 1048576, componentNameArr, componentName, i);
    }

    private com.android.server.pm.WatchedIntentFilter getHomeFilter() {
        com.android.server.pm.WatchedIntentFilter watchedIntentFilter = new com.android.server.pm.WatchedIntentFilter("android.intent.action.MAIN");
        watchedIntentFilter.addCategory("android.intent.category.HOME");
        watchedIntentFilter.addCategory("android.intent.category.DEFAULT");
        return watchedIntentFilter;
    }

    public void addPersistentPreferredActivity(com.android.server.pm.WatchedIntentFilter watchedIntentFilter, android.content.ComponentName componentName, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 1000) {
            throw new java.lang.SecurityException("addPersistentPreferredActivity can only be run by the system");
        }
        if (!watchedIntentFilter.checkDataPathAndSchemeSpecificParts()) {
            android.util.EventLog.writeEvent(1397638484, "246749702", java.lang.Integer.valueOf(callingUid));
            throw new java.lang.IllegalArgumentException("Invalid intent data paths or scheme specific parts in the filter.");
        }
        if (watchedIntentFilter.countActions() == 0) {
            android.util.Slog.w("PackageManager", "Cannot set a preferred activity with no filter actions");
            return;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPm.mSettings.editPersistentPreferredActivitiesLPw(i).addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) this.mPm.snapshotComputer(), (com.android.server.pm.Computer) new com.android.server.pm.PersistentPreferredActivity(watchedIntentFilter, componentName, true));
                this.mPm.scheduleWritePackageRestrictions(i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (isHomeFilter(watchedIntentFilter)) {
            updateDefaultHomeNotLocked(this.mPm.snapshotComputer(), i);
        }
        this.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i);
    }

    public void clearPackagePersistentPreferredActivities(java.lang.String str, int i) {
        boolean clearPackagePersistentPreferredActivities;
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("clearPackagePersistentPreferredActivities can only be run by the system");
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                clearPackagePersistentPreferredActivities = this.mPm.mSettings.clearPackagePersistentPreferredActivities(str, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (clearPackagePersistentPreferredActivities) {
            updateDefaultHomeNotLocked(this.mPm.snapshotComputer(), i);
            this.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i);
            this.mPm.scheduleWritePackageRestrictions(i);
        }
    }

    public void clearPersistentPreferredActivity(android.content.IntentFilter intentFilter, int i) {
        boolean clearPersistentPreferredActivity;
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("clearPersistentPreferredActivity can only be run by the system");
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                clearPersistentPreferredActivity = this.mPm.mSettings.clearPersistentPreferredActivity(intentFilter, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (clearPersistentPreferredActivity) {
            updateDefaultHomeNotLocked(this.mPm.snapshotComputer(), i);
            this.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i);
            this.mPm.scheduleWritePackageRestrictions(i);
        }
    }

    private boolean isHomeFilter(@android.annotation.NonNull com.android.server.pm.WatchedIntentFilter watchedIntentFilter) {
        return watchedIntentFilter.hasAction("android.intent.action.MAIN") && watchedIntentFilter.hasCategory("android.intent.category.HOME") && watchedIntentFilter.hasCategory("android.intent.category.DEFAULT");
    }

    private void restoreFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, java.lang.String str, com.android.server.pm.PreferredActivityHelper.BlobXmlRestorer blobXmlRestorer) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int next;
        do {
            next = typedXmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2 || !str.equals(typedXmlPullParser.getName())) {
            return;
        }
        while (typedXmlPullParser.next() == 4) {
        }
        blobXmlRestorer.apply(typedXmlPullParser, i);
    }

    public byte[] getPreferredActivityBackup(int i) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Only the system may call getPreferredActivityBackup()");
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            com.android.modules.utils.TypedXmlSerializer newFastSerializer = android.util.Xml.newFastSerializer();
            newFastSerializer.setOutput(byteArrayOutputStream, java.nio.charset.StandardCharsets.UTF_8.name());
            newFastSerializer.startDocument((java.lang.String) null, true);
            newFastSerializer.startTag((java.lang.String) null, TAG_PREFERRED_BACKUP);
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    this.mPm.mSettings.writePreferredActivitiesLPr(newFastSerializer, i, true);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            newFastSerializer.endTag((java.lang.String) null, TAG_PREFERRED_BACKUP);
            newFastSerializer.endDocument();
            newFastSerializer.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    public void restorePreferredActivities(byte[] bArr, int i) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Only the system may call restorePreferredActivities()");
        }
        try {
            com.android.modules.utils.TypedXmlPullParser newFastPullParser = android.util.Xml.newFastPullParser();
            newFastPullParser.setInput(new java.io.ByteArrayInputStream(bArr), java.nio.charset.StandardCharsets.UTF_8.name());
            restoreFromXml(newFastPullParser, i, TAG_PREFERRED_BACKUP, new com.android.server.pm.PreferredActivityHelper.BlobXmlRestorer() { // from class: com.android.server.pm.PreferredActivityHelper$$ExternalSyntheticLambda2
                @Override // com.android.server.pm.PreferredActivityHelper.BlobXmlRestorer
                public final void apply(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i2) {
                    com.android.server.pm.PreferredActivityHelper.this.lambda$restorePreferredActivities$1(typedXmlPullParser, i2);
                }
            });
        } catch (java.lang.Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restorePreferredActivities$1(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPm.mSettings.readPreferredActivitiesLPw(typedXmlPullParser, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        updateDefaultHomeNotLocked(this.mPm.snapshotComputer(), i);
    }

    public byte[] getDefaultAppsBackup(int i) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Only the system may call getDefaultAppsBackup()");
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            com.android.modules.utils.TypedXmlSerializer newFastSerializer = android.util.Xml.newFastSerializer();
            newFastSerializer.setOutput(byteArrayOutputStream, java.nio.charset.StandardCharsets.UTF_8.name());
            newFastSerializer.startDocument((java.lang.String) null, true);
            newFastSerializer.startTag((java.lang.String) null, TAG_DEFAULT_APPS);
            com.android.server.pm.Settings.writeDefaultApps(newFastSerializer, this.mPm.getDefaultBrowser(i));
            newFastSerializer.endTag((java.lang.String) null, TAG_DEFAULT_APPS);
            newFastSerializer.endDocument();
            newFastSerializer.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    public void restoreDefaultApps(byte[] bArr, int i) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Only the system may call restoreDefaultApps()");
        }
        try {
            com.android.modules.utils.TypedXmlPullParser newFastPullParser = android.util.Xml.newFastPullParser();
            newFastPullParser.setInput(new java.io.ByteArrayInputStream(bArr), java.nio.charset.StandardCharsets.UTF_8.name());
            restoreFromXml(newFastPullParser, i, TAG_DEFAULT_APPS, new com.android.server.pm.PreferredActivityHelper.BlobXmlRestorer() { // from class: com.android.server.pm.PreferredActivityHelper$$ExternalSyntheticLambda1
                @Override // com.android.server.pm.PreferredActivityHelper.BlobXmlRestorer
                public final void apply(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i2) {
                    com.android.server.pm.PreferredActivityHelper.this.lambda$restoreDefaultApps$2(typedXmlPullParser, i2);
                }
            });
        } catch (java.lang.Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restoreDefaultApps$2(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String readDefaultApps = com.android.server.pm.Settings.readDefaultApps(typedXmlPullParser);
        if (readDefaultApps != null) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(readDefaultApps);
            if (packageStateInternal != null && packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                this.mPm.setDefaultBrowser(readDefaultApps, i);
                return;
            }
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    this.mPm.mSettings.setPendingDefaultBrowserLPw(readDefaultApps, i);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
    }

    public void resetApplicationPreferences(int i) {
        this.mPm.mContext.enforceCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    this.mPm.clearPackagePreferredActivitiesLPw(null, sparseBooleanArray, i);
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            if (sparseBooleanArray.size() > 0) {
                this.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i);
            }
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock2) {
                try {
                    this.mPm.mSettings.applyDefaultPreferredAppsLPw(i);
                    this.mPm.mDomainVerificationManager.clearUser(i);
                    this.mPm.mPermissionManager.resetRuntimePermissionsForUser(i);
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            updateDefaultHomeNotLocked(this.mPm.snapshotComputer(), i);
            resetNetworkPolicies(i);
            this.mPm.scheduleWritePackageRestrictions(i);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private void resetNetworkPolicies(int i) {
        ((com.android.server.net.NetworkPolicyManagerInternal) this.mPm.mInjector.getLocalService(com.android.server.net.NetworkPolicyManagerInternal.class)).resetUserState(i);
    }

    public int getPreferredActivities(@android.annotation.NonNull com.android.server.pm.Computer computer, java.util.List<android.content.IntentFilter> list, java.util.List<android.content.ComponentName> list2, java.lang.String str) {
        java.util.List<com.android.server.pm.WatchedIntentFilter> watchedIntentFilterList = com.android.server.pm.WatchedIntentFilter.toWatchedIntentFilterList(list);
        int preferredActivitiesInternal = getPreferredActivitiesInternal(computer, watchedIntentFilterList, list2, str);
        list.clear();
        for (int i = 0; i < watchedIntentFilterList.size(); i++) {
            list.add(watchedIntentFilterList.get(i).getIntentFilter());
        }
        return preferredActivitiesInternal;
    }

    private int getPreferredActivitiesInternal(@android.annotation.NonNull com.android.server.pm.Computer computer, java.util.List<com.android.server.pm.WatchedIntentFilter> list, java.util.List<android.content.ComponentName> list2, java.lang.String str) {
        int callingUserId;
        com.android.server.pm.PreferredIntentResolver preferredActivities;
        int callingUid = android.os.Binder.getCallingUid();
        if (computer.getInstantAppPackageName(callingUid) == null && (preferredActivities = computer.getPreferredActivities((callingUserId = android.os.UserHandle.getCallingUserId()))) != null) {
            java.util.Iterator<F> filterIterator = preferredActivities.filterIterator();
            while (filterIterator.hasNext()) {
                com.android.server.pm.PreferredActivity preferredActivity = (com.android.server.pm.PreferredActivity) filterIterator.next();
                if (preferredActivity != null) {
                    java.lang.String packageName = preferredActivity.mPref.mComponent.getPackageName();
                    if (str == null || (packageName.equals(str) && preferredActivity.mPref.mAlways)) {
                        if (!computer.shouldFilterApplication(computer.getPackageStateInternal(packageName), callingUid, callingUserId)) {
                            if (list != null) {
                                list.add(new com.android.server.pm.WatchedIntentFilter(preferredActivity.getIntentFilter()));
                            }
                            if (list2 != null) {
                                list2.add(preferredActivity.mPref.mComponent);
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public android.content.pm.ResolveInfo findPersistentPreferredActivity(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, int i) {
        if (!android.os.UserHandle.isSameApp(android.os.Binder.getCallingUid(), 1000)) {
            throw new java.lang.SecurityException("findPersistentPreferredActivity can only be run by the system");
        }
        if (!this.mPm.mUserManager.exists(i)) {
            return null;
        }
        int callingUid = android.os.Binder.getCallingUid();
        android.content.Intent updateIntentForResolve = com.android.server.pm.PackageManagerServiceUtils.updateIntentForResolve(intent);
        java.lang.String resolveTypeIfNeeded = updateIntentForResolve.resolveTypeIfNeeded(this.mPm.mContext.getContentResolver());
        long updateFlagsForResolve = computer.updateFlagsForResolve(0L, i, callingUid, false, computer.isImplicitImageCaptureIntentAndNotSetByDpc(updateIntentForResolve, i, resolveTypeIfNeeded, 0L));
        return computer.findPersistentPreferredActivity(updateIntentForResolve, resolveTypeIfNeeded, updateFlagsForResolve, computer.queryIntentActivitiesInternal(updateIntentForResolve, resolveTypeIfNeeded, updateFlagsForResolve, i), false, i);
    }

    public void setLastChosenActivity(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, int i, com.android.server.pm.WatchedIntentFilter watchedIntentFilter, int i2, android.content.ComponentName componentName) {
        if (computer.getInstantAppPackageName(android.os.Binder.getCallingUid()) != null) {
            return;
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        intent.setComponent(null);
        long j = i;
        findPreferredActivityNotLocked(computer, intent, str, j, computer.queryIntentActivitiesInternal(intent, str, j, callingUserId), false, true, false, callingUserId);
        addPreferredActivity(computer, watchedIntentFilter, i2, null, componentName, false, callingUserId, "Setting last chosen", false);
    }

    public android.content.pm.ResolveInfo getLastChosenActivity(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, int i) {
        if (computer.getInstantAppPackageName(android.os.Binder.getCallingUid()) != null) {
            return null;
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long j = i;
        return findPreferredActivityNotLocked(computer, intent, str, j, computer.queryIntentActivitiesInternal(intent, str, j, callingUserId), false, false, false, callingUserId);
    }
}
