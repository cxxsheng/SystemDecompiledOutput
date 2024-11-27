package com.android.server.companion;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
public class CompanionApplicationController {
    static final boolean DEBUG = false;
    private static final long REBIND_TIMEOUT = 10000;
    private static final java.lang.String TAG = "CDM_CompanionApplicationController";

    @android.annotation.NonNull
    private final com.android.server.companion.AssociationStore mAssociationStore;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.companion.presence.CompanionDevicePresenceMonitor mDevicePresenceMonitor;

    @android.annotation.NonNull
    private final com.android.server.companion.presence.ObservableUuidStore mObservableUuidStore;
    private final android.os.PowerManagerInternal mPowerManagerInternal;

    @android.annotation.NonNull
    private final com.android.server.companion.CompanionApplicationController.CompanionServicesRegister mCompanionServicesRegister = new com.android.server.companion.CompanionApplicationController.CompanionServicesRegister();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mBoundCompanionApplications"})
    private final com.android.server.companion.CompanionApplicationController.AndroidPackageMap<java.util.List<com.android.server.companion.CompanionDeviceServiceConnector>> mBoundCompanionApplications = new com.android.server.companion.CompanionApplicationController.AndroidPackageMap<>();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mScheduledForRebindingCompanionApplications"})
    private final com.android.server.companion.CompanionApplicationController.AndroidPackageMap<java.lang.Boolean> mScheduledForRebindingCompanionApplications = new com.android.server.companion.CompanionApplicationController.AndroidPackageMap<>();

    CompanionApplicationController(android.content.Context context, com.android.server.companion.AssociationStore associationStore, com.android.server.companion.presence.ObservableUuidStore observableUuidStore, com.android.server.companion.presence.CompanionDevicePresenceMonitor companionDevicePresenceMonitor, android.os.PowerManagerInternal powerManagerInternal) {
        this.mContext = context;
        this.mAssociationStore = associationStore;
        this.mObservableUuidStore = observableUuidStore;
        this.mDevicePresenceMonitor = companionDevicePresenceMonitor;
        this.mPowerManagerInternal = powerManagerInternal;
    }

    void onPackagesChanged(int i) {
        this.mCompanionServicesRegister.invalidate(i);
    }

    public void bindCompanionApplication(int i, @android.annotation.NonNull java.lang.String str, boolean z) {
        java.util.List<android.content.ComponentName> forPackage = this.mCompanionServicesRegister.forPackage(i, str);
        if (forPackage.isEmpty()) {
            android.util.Slog.w(TAG, "Can not bind companion applications u" + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str + ": eligible CompanionDeviceService not found.\nA CompanionDeviceService should declare an intent-filter for \"android.companion.CompanionDeviceService\" action and require \"android.permission.BIND_COMPANION_DEVICE_SERVICE\" permission.");
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mBoundCompanionApplications) {
            try {
                if (this.mBoundCompanionApplications.containsValueForPackage(i, str)) {
                    return;
                }
                int i2 = 0;
                while (i2 < forPackage.size()) {
                    arrayList.add(com.android.server.companion.CompanionDeviceServiceConnector.newInstance(this.mContext, i, forPackage.get(i2), z, i2 == 0));
                    i2++;
                }
                this.mBoundCompanionApplications.setValueForPackage(i, str, arrayList);
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((com.android.server.companion.CompanionDeviceServiceConnector) it.next()).setListener(new com.android.server.companion.CompanionDeviceServiceConnector.Listener() { // from class: com.android.server.companion.CompanionApplicationController$$ExternalSyntheticLambda1
                        @Override // com.android.server.companion.CompanionDeviceServiceConnector.Listener
                        public final void onBindingDied(int i3, java.lang.String str2, com.android.server.companion.CompanionDeviceServiceConnector companionDeviceServiceConnector) {
                            com.android.server.companion.CompanionApplicationController.this.onBinderDied(i3, str2, companionDeviceServiceConnector);
                        }
                    });
                }
                java.util.Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    ((com.android.server.companion.CompanionDeviceServiceConnector) it2.next()).connect();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unbindCompanionApplication(int i, @android.annotation.NonNull java.lang.String str) {
        java.util.List<com.android.server.companion.CompanionDeviceServiceConnector> removePackage;
        synchronized (this.mBoundCompanionApplications) {
            removePackage = this.mBoundCompanionApplications.removePackage(i, str);
        }
        synchronized (this.mScheduledForRebindingCompanionApplications) {
            this.mScheduledForRebindingCompanionApplications.removePackage(i, str);
        }
        if (removePackage == null) {
            return;
        }
        java.util.Iterator<com.android.server.companion.CompanionDeviceServiceConnector> it = removePackage.iterator();
        while (it.hasNext()) {
            it.next().postUnbind();
        }
    }

    public boolean isCompanionApplicationBound(int i, @android.annotation.NonNull java.lang.String str) {
        boolean containsValueForPackage;
        synchronized (this.mBoundCompanionApplications) {
            containsValueForPackage = this.mBoundCompanionApplications.containsValueForPackage(i, str);
        }
        return containsValueForPackage;
    }

    private void scheduleRebinding(final int i, @android.annotation.NonNull final java.lang.String str, final com.android.server.companion.CompanionDeviceServiceConnector companionDeviceServiceConnector) {
        android.util.Slog.i(TAG, "scheduleRebinding() " + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str);
        if (isRebindingCompanionApplicationScheduled(i, str)) {
            return;
        }
        if (companionDeviceServiceConnector.isPrimary()) {
            synchronized (this.mScheduledForRebindingCompanionApplications) {
                this.mScheduledForRebindingCompanionApplications.setValueForPackage(i, str, true);
            }
        }
        android.os.Handler.getMain().postDelayed(new java.lang.Runnable() { // from class: com.android.server.companion.CompanionApplicationController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.companion.CompanionApplicationController.this.lambda$scheduleRebinding$0(i, str, companionDeviceServiceConnector);
            }
        }, 10000L);
    }

    private boolean isRebindingCompanionApplicationScheduled(int i, @android.annotation.NonNull java.lang.String str) {
        boolean containsValueForPackage;
        synchronized (this.mScheduledForRebindingCompanionApplications) {
            containsValueForPackage = this.mScheduledForRebindingCompanionApplications.containsValueForPackage(i, str);
        }
        return containsValueForPackage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onRebindingCompanionApplicationTimeout, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleRebinding$0(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.companion.CompanionDeviceServiceConnector companionDeviceServiceConnector) {
        if (companionDeviceServiceConnector.isPrimary()) {
            synchronized (this.mBoundCompanionApplications) {
                try {
                    if (!this.mBoundCompanionApplications.containsValueForPackage(i, str)) {
                        this.mBoundCompanionApplications.setValueForPackage(i, str, java.util.Collections.singletonList(companionDeviceServiceConnector));
                    }
                } finally {
                }
            }
            synchronized (this.mScheduledForRebindingCompanionApplications) {
                this.mScheduledForRebindingCompanionApplications.removePackage(i, str);
            }
        }
        companionDeviceServiceConnector.connect();
    }

    @java.lang.Deprecated
    public void notifyCompanionApplicationDeviceAppeared(android.companion.AssociationInfo associationInfo) {
        int userId = associationInfo.getUserId();
        java.lang.String packageName = associationInfo.getPackageName();
        android.util.Slog.i(TAG, "notifyDevice_Appeared() id=" + associationInfo.getId() + " u" + userId + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + packageName);
        com.android.server.companion.CompanionDeviceServiceConnector primaryServiceConnector = getPrimaryServiceConnector(userId, packageName);
        if (primaryServiceConnector == null) {
            android.util.Slog.e(TAG, "notify_CompanionApplicationDevice_Appeared(): u" + userId + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + packageName + " is NOT bound.");
            android.util.Slog.e(TAG, "Stacktrace", new java.lang.Throwable());
            return;
        }
        android.util.Log.i(TAG, "Calling onDeviceAppeared to userId=[" + userId + "] package=[" + packageName + "] associationId=[" + associationInfo.getId() + "]");
        primaryServiceConnector.postOnDeviceAppeared(associationInfo);
    }

    @java.lang.Deprecated
    public void notifyCompanionApplicationDeviceDisappeared(android.companion.AssociationInfo associationInfo) {
        int userId = associationInfo.getUserId();
        java.lang.String packageName = associationInfo.getPackageName();
        android.util.Slog.i(TAG, "notifyDevice_Disappeared() id=" + associationInfo.getId() + " u" + userId + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + packageName);
        com.android.server.companion.CompanionDeviceServiceConnector primaryServiceConnector = getPrimaryServiceConnector(userId, packageName);
        if (primaryServiceConnector == null) {
            android.util.Slog.e(TAG, "notify_CompanionApplicationDevice_Disappeared(): u" + userId + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + packageName + " is NOT bound.");
            android.util.Slog.e(TAG, "Stacktrace", new java.lang.Throwable());
            return;
        }
        android.util.Log.i(TAG, "Calling onDeviceDisappeared to userId=[" + userId + "] package=[" + packageName + "] associationId=[" + associationInfo.getId() + "]");
        primaryServiceConnector.postOnDeviceDisappeared(associationInfo);
    }

    public void notifyCompanionDevicePresenceEvent(android.companion.AssociationInfo associationInfo, int i) {
        int userId = associationInfo.getUserId();
        java.lang.String packageName = associationInfo.getPackageName();
        com.android.server.companion.CompanionDeviceServiceConnector primaryServiceConnector = getPrimaryServiceConnector(userId, packageName);
        android.companion.DevicePresenceEvent devicePresenceEvent = new android.companion.DevicePresenceEvent(associationInfo.getId(), i, (android.os.ParcelUuid) null);
        if (primaryServiceConnector == null) {
            android.util.Slog.e(TAG, "notifyCompanionApplicationDevicePresenceEvent(): u" + userId + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + packageName + " event=[ " + i + " ] is NOT bound.");
            android.util.Slog.e(TAG, "Stacktrace", new java.lang.Throwable());
            return;
        }
        android.util.Slog.i(TAG, "Calling onDevicePresenceEvent() to userId=[" + userId + "] package=[" + packageName + "] associationId=[" + associationInfo.getId() + "] event=[" + i + "]");
        primaryServiceConnector.postOnDevicePresenceEvent(devicePresenceEvent);
    }

    public void notifyUuidDevicePresenceEvent(com.android.server.companion.presence.ObservableUuid observableUuid, int i) {
        int userId = observableUuid.getUserId();
        android.os.ParcelUuid uuid = observableUuid.getUuid();
        java.lang.String packageName = observableUuid.getPackageName();
        com.android.server.companion.CompanionDeviceServiceConnector primaryServiceConnector = getPrimaryServiceConnector(userId, packageName);
        android.companion.DevicePresenceEvent devicePresenceEvent = new android.companion.DevicePresenceEvent(-1, i, uuid);
        if (primaryServiceConnector == null) {
            android.util.Slog.e(TAG, "notifyApplicationDevicePresenceChanged(): u" + userId + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + packageName + " event=[ " + i + " ] is NOT bound.");
            android.util.Slog.e(TAG, "Stacktrace", new java.lang.Throwable());
            return;
        }
        android.util.Slog.i(TAG, "Calling onDevicePresenceEvent() to userId=[" + userId + "] package=[" + packageName + "]event= [" + i + "]");
        primaryServiceConnector.postOnDevicePresenceEvent(devicePresenceEvent);
    }

    void dump(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        printWriter.append("Companion Device Application Controller: \n");
        synchronized (this.mBoundCompanionApplications) {
            try {
                printWriter.append("  Bound Companion Applications: ");
                if (this.mBoundCompanionApplications.size() == 0) {
                    printWriter.append("<empty>\n");
                } else {
                    printWriter.append("\n");
                    this.mBoundCompanionApplications.dump(printWriter);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.append("  Companion Applications Scheduled For Rebinding: ");
        if (this.mScheduledForRebindingCompanionApplications.size() == 0) {
            printWriter.append("<empty>\n");
        } else {
            printWriter.append("\n");
            this.mScheduledForRebindingCompanionApplications.dump(printWriter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBinderDied(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.companion.CompanionDeviceServiceConnector companionDeviceServiceConnector) {
        boolean isPrimary = companionDeviceServiceConnector.isPrimary();
        android.util.Slog.i(TAG, "onBinderDied() u" + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str + " isPrimary: " + isPrimary);
        if (isPrimary) {
            java.util.Iterator<android.companion.AssociationInfo> it = this.mAssociationStore.getAssociationsForPackage(i, str).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                java.lang.String deviceProfile = it.next().getDeviceProfile();
                if ("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION".equals(deviceProfile)) {
                    android.util.Slog.i(TAG, "Disable hint mode for device profile: " + deviceProfile);
                    this.mPowerManagerInternal.setPowerMode(18, false);
                    break;
                }
            }
            synchronized (this.mBoundCompanionApplications) {
                this.mBoundCompanionApplications.removePackage(i, str);
            }
        }
        if (shouldScheduleRebind(i, str, isPrimary)) {
            scheduleRebinding(i, str, companionDeviceServiceConnector);
        }
    }

    @android.annotation.Nullable
    private com.android.server.companion.CompanionDeviceServiceConnector getPrimaryServiceConnector(int i, @android.annotation.NonNull java.lang.String str) {
        java.util.List<com.android.server.companion.CompanionDeviceServiceConnector> valueForPackage;
        synchronized (this.mBoundCompanionApplications) {
            valueForPackage = this.mBoundCompanionApplications.getValueForPackage(i, str);
        }
        if (valueForPackage != null) {
            return valueForPackage.get(0);
        }
        return null;
    }

    private boolean shouldScheduleRebind(int i, java.lang.String str, boolean z) {
        boolean z2;
        java.util.List<com.android.server.companion.presence.ObservableUuid> observableUuidsForPackage = this.mObservableUuidStore.getObservableUuidsForPackage(i, str);
        boolean z3 = false;
        boolean z4 = false;
        for (android.companion.AssociationInfo associationInfo : this.mAssociationStore.getAssociationsForPackage(i, str)) {
            int id = associationInfo.getId();
            if (associationInfo.isSelfManaged()) {
                if (z && this.mDevicePresenceMonitor.isDevicePresent(id)) {
                    this.mDevicePresenceMonitor.onSelfManagedDeviceReporterBinderDied(id);
                }
                z4 = isCompanionApplicationBound(i, str);
            } else if (associationInfo.isNotifyOnDeviceNearby()) {
                z4 = true;
            }
            z3 = true;
        }
        java.util.Iterator<com.android.server.companion.presence.ObservableUuid> it = observableUuidsForPackage.iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = false;
                break;
            }
            if (this.mDevicePresenceMonitor.isDeviceUuidPresent(it.next().getUuid())) {
                z2 = true;
                break;
            }
        }
        return (z3 && z4) || z2;
    }

    private class CompanionServicesRegister extends com.android.internal.infra.PerUser<java.util.Map<java.lang.String, java.util.List<android.content.ComponentName>>> {
        private CompanionServicesRegister() {
        }

        @android.annotation.NonNull
        public synchronized java.util.Map<java.lang.String, java.util.List<android.content.ComponentName>> forUser(int i) {
            return (java.util.Map) super.forUser(i);
        }

        @android.annotation.NonNull
        synchronized java.util.List<android.content.ComponentName> forPackage(int i, @android.annotation.NonNull java.lang.String str) {
            return forUser(i).getOrDefault(str, java.util.Collections.emptyList());
        }

        synchronized void invalidate(int i) {
            remove(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @android.annotation.NonNull
        public final java.util.Map<java.lang.String, java.util.List<android.content.ComponentName>> create(int i) {
            return com.android.server.companion.utils.PackageUtils.getCompanionServicesForUser(com.android.server.companion.CompanionApplicationController.this.mContext, i);
        }
    }

    private static class AndroidPackageMap<T> extends android.util.SparseArray<java.util.Map<java.lang.String, T>> {
        private AndroidPackageMap() {
        }

        void setValueForPackage(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull T t) {
            java.util.Map<java.lang.String, T> map = get(i);
            if (map == null) {
                map = new java.util.HashMap<>();
                put(i, map);
            }
            map.put(str, t);
        }

        boolean containsValueForPackage(int i, @android.annotation.NonNull java.lang.String str) {
            java.util.Map<java.lang.String, T> map = get(i);
            return map != null && map.containsKey(str);
        }

        T getValueForPackage(int i, @android.annotation.NonNull java.lang.String str) {
            java.util.Map<java.lang.String, T> map = get(i);
            if (map != null) {
                return map.get(str);
            }
            return null;
        }

        T removePackage(int i, @android.annotation.NonNull java.lang.String str) {
            java.util.Map<java.lang.String, T> map = get(i);
            if (map == null) {
                return null;
            }
            return map.remove(str);
        }

        void dump() {
            if (size() == 0) {
                android.util.Log.d(com.android.server.companion.CompanionApplicationController.TAG, "<empty>");
                return;
            }
            for (int i = 0; i < size(); i++) {
                int keyAt = keyAt(i);
                java.util.Map<java.lang.String, T> map = get(keyAt);
                if (map.isEmpty()) {
                    android.util.Log.d(com.android.server.companion.CompanionApplicationController.TAG, "u" + keyAt + ": <empty>");
                }
                for (java.util.Map.Entry<java.lang.String, T> entry : map.entrySet()) {
                    android.util.Log.d(com.android.server.companion.CompanionApplicationController.TAG, "u" + keyAt + "\\" + entry.getKey() + " -> " + entry.getValue());
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter) {
            for (int i = 0; i < size(); i++) {
                int keyAt = keyAt(i);
                java.util.Map<java.lang.String, T> map = get(keyAt);
                if (map.isEmpty()) {
                    printWriter.append("    u").append((java.lang.CharSequence) java.lang.String.valueOf(keyAt)).append(": <empty>\n");
                }
                for (java.util.Map.Entry<java.lang.String, T> entry : map.entrySet()) {
                    printWriter.append("    u").append((java.lang.CharSequence) java.lang.String.valueOf(keyAt)).append("\\").append((java.lang.CharSequence) entry.getKey()).append(" -> ").append((java.lang.CharSequence) entry.getValue().toString()).append('\n');
                }
            }
        }
    }
}
