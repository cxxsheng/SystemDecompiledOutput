package com.android.server.incident;

/* loaded from: classes2.dex */
class PendingReports {
    static final java.lang.String TAG = "IncidentCompanionService";
    private final android.app.AppOpsManager mAppOpsManager;
    private final android.content.Context mContext;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.permission.PermissionManager mPermissionManager;
    private final android.os.Handler mHandler = new android.os.Handler();
    private final com.android.server.incident.RequestQueue mRequestQueue = new com.android.server.incident.RequestQueue(this.mHandler);
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.ArrayList<com.android.server.incident.PendingReports.PendingReportRec> mPending = new java.util.ArrayList<>();
    private int mNextPendingId = 1;

    private final class PendingReportRec {
        public long addedRealtime;
        public long addedWalltime;
        public java.lang.String callingPackage;
        public int flags;
        public int id;
        public android.os.IIncidentAuthListener listener;
        public java.lang.String receiverClass;
        public java.lang.String reportId;

        PendingReportRec(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, android.os.IIncidentAuthListener iIncidentAuthListener) {
            int i2 = com.android.server.incident.PendingReports.this.mNextPendingId;
            com.android.server.incident.PendingReports.this.mNextPendingId = i2 + 1;
            this.id = i2;
            this.callingPackage = str;
            this.flags = i;
            this.listener = iIncidentAuthListener;
            this.addedRealtime = android.os.SystemClock.elapsedRealtime();
            this.addedWalltime = java.lang.System.currentTimeMillis();
            this.receiverClass = str2;
            this.reportId = str3;
        }

        android.net.Uri getUri() {
            android.net.Uri.Builder appendQueryParameter = new android.net.Uri.Builder().scheme(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT).authority("android.os.IncidentManager").path("/pending").appendQueryParameter("id", java.lang.Integer.toString(this.id)).appendQueryParameter("pkg", this.callingPackage).appendQueryParameter("flags", java.lang.Integer.toString(this.flags)).appendQueryParameter("t", java.lang.Long.toString(this.addedWalltime));
            if (this.receiverClass != null && this.receiverClass.length() > 0) {
                appendQueryParameter.appendQueryParameter("receiver", this.receiverClass);
            }
            if (this.reportId != null && this.reportId.length() > 0) {
                appendQueryParameter.appendQueryParameter(com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD, this.reportId);
            }
            return appendQueryParameter.build();
        }
    }

    PendingReports(android.content.Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mAppOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        this.mPermissionManager = (android.permission.PermissionManager) context.getSystemService(android.permission.PermissionManager.class);
    }

    public void authorizeReport(final int i, final java.lang.String str, final java.lang.String str2, final java.lang.String str3, final int i2, final android.os.IIncidentAuthListener iIncidentAuthListener) {
        this.mRequestQueue.enqueue(iIncidentAuthListener.asBinder(), true, new java.lang.Runnable() { // from class: com.android.server.incident.PendingReports$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.incident.PendingReports.this.lambda$authorizeReport$0(i, str, str2, str3, i2, iIncidentAuthListener);
            }
        });
    }

    public void cancelAuthorization(final android.os.IIncidentAuthListener iIncidentAuthListener) {
        this.mRequestQueue.enqueue(iIncidentAuthListener.asBinder(), false, new java.lang.Runnable() { // from class: com.android.server.incident.PendingReports$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.incident.PendingReports.this.lambda$cancelAuthorization$1(iIncidentAuthListener);
            }
        });
    }

    public java.util.List<java.lang.String> getPendingReports() {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            try {
                int size = this.mPending.size();
                arrayList = new java.util.ArrayList(size);
                for (int i = 0; i < size; i++) {
                    arrayList.add(this.mPending.get(i).getUri().toString());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public void approveReport(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                com.android.server.incident.PendingReports.PendingReportRec findAndRemovePendingReportRecLocked = findAndRemovePendingReportRecLocked(str);
                if (findAndRemovePendingReportRecLocked == null) {
                    android.util.Log.e(TAG, "confirmApproved: Couldn't find record for uri: " + str);
                    return;
                }
                sendBroadcast();
                android.util.Log.i(TAG, "Approved report: " + str);
                try {
                    findAndRemovePendingReportRecLocked.listener.onReportApproved();
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(TAG, "Failed calling back for approval for: " + str, e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void denyReport(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                com.android.server.incident.PendingReports.PendingReportRec findAndRemovePendingReportRecLocked = findAndRemovePendingReportRecLocked(str);
                if (findAndRemovePendingReportRecLocked == null) {
                    android.util.Log.e(TAG, "confirmDenied: Couldn't find record for uri: " + str);
                    return;
                }
                sendBroadcast();
                android.util.Log.i(TAG, "Denied report: " + str);
                try {
                    findAndRemovePendingReportRecLocked.listener.onReportDenied();
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(TAG, "Failed calling back for denial for: " + str, e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (strArr.length == 0) {
            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            synchronized (this.mLock) {
                try {
                    int size = this.mPending.size();
                    printWriter.println("mPending: (" + size + ")");
                    for (int i = 0; i < size; i++) {
                        com.android.server.incident.PendingReports.PendingReportRec pendingReportRec = this.mPending.get(i);
                        printWriter.println(java.lang.String.format("  %11d %s: %s", java.lang.Long.valueOf(pendingReportRec.addedRealtime), simpleDateFormat.format(new java.util.Date(pendingReportRec.addedWalltime)), pendingReportRec.getUri().toString()));
                    }
                } finally {
                }
            }
        }
    }

    public void onBootCompleted() {
        this.mRequestQueue.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: authorizeReportImpl, reason: merged with bridge method [inline-methods] */
    public void lambda$authorizeReport$0(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, final android.os.IIncidentAuthListener iIncidentAuthListener) {
        boolean z;
        com.android.server.incident.PendingReports.PendingReportRec pendingReportRec;
        if (i != 0 && !isPackageInUid(i, str)) {
            android.util.Log.w(TAG, "Calling uid " + i + " doesn't match package " + str);
            denyReportBeforeAddingRec(iIncidentAuthListener, str);
            return;
        }
        final int currentUserIfAdmin = getCurrentUserIfAdmin();
        int userId = android.os.UserHandle.getUserId(i);
        if (currentUserIfAdmin == -10000 || !isSameProfileGroupUser(userId, currentUserIfAdmin)) {
            android.util.Log.w(TAG, "Calling user " + userId + " doesn't belong to the same profile group of the current admin user " + currentUserIfAdmin);
            denyReportBeforeAddingRec(iIncidentAuthListener, str);
            return;
        }
        final android.content.ComponentName approverComponent = getApproverComponent(currentUserIfAdmin);
        if (approverComponent == null) {
            denyReportBeforeAddingRec(iIncidentAuthListener, str);
            return;
        }
        if ((android.os.Build.IS_USERDEBUG || android.os.Build.IS_ENG) && (i2 & 2) != 0) {
            z = this.mPermissionManager.checkPermissionForDataDelivery("android.permission.CAPTURE_CONSENTLESS_BUGREPORT_ON_USERDEBUG_BUILD", new android.content.AttributionSource.Builder(i).setPackageName(str).build(), (java.lang.String) null) == 0;
        } else {
            z = false;
        }
        if (z) {
            try {
                android.util.Log.d(TAG, "approving consentless report: " + new com.android.server.incident.PendingReports.PendingReportRec(str, str2, str3, i2, iIncidentAuthListener).getUri());
                iIncidentAuthListener.onReportApproved();
                return;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "authorizeReportImpl listener.onReportApproved RemoteException: ", e);
            }
        }
        synchronized (this.mLock) {
            pendingReportRec = new com.android.server.incident.PendingReports.PendingReportRec(str, str2, str3, i2, iIncidentAuthListener);
            this.mPending.add(pendingReportRec);
        }
        try {
            iIncidentAuthListener.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.incident.PendingReports$$ExternalSyntheticLambda2
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    com.android.server.incident.PendingReports.this.lambda$authorizeReportImpl$2(iIncidentAuthListener, approverComponent, currentUserIfAdmin);
                }
            }, 0);
        } catch (android.os.RemoteException e2) {
            android.util.Log.e(TAG, "Remote died while trying to register death listener: " + pendingReportRec.getUri());
            cancelReportImpl(iIncidentAuthListener, approverComponent, currentUserIfAdmin);
        }
        sendBroadcast(approverComponent, currentUserIfAdmin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authorizeReportImpl$2(android.os.IIncidentAuthListener iIncidentAuthListener, android.content.ComponentName componentName, int i) {
        android.util.Log.i(TAG, "Got death notification listener=" + iIncidentAuthListener);
        cancelReportImpl(iIncidentAuthListener, componentName, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: cancelReportImpl, reason: merged with bridge method [inline-methods] */
    public void lambda$cancelAuthorization$1(android.os.IIncidentAuthListener iIncidentAuthListener) {
        int currentUserIfAdmin = getCurrentUserIfAdmin();
        android.content.ComponentName approverComponent = getApproverComponent(currentUserIfAdmin);
        if (currentUserIfAdmin != -10000 && approverComponent != null) {
            cancelReportImpl(iIncidentAuthListener, approverComponent, currentUserIfAdmin);
        }
    }

    private void cancelReportImpl(android.os.IIncidentAuthListener iIncidentAuthListener, android.content.ComponentName componentName, int i) {
        synchronized (this.mLock) {
            removePendingReportRecLocked(iIncidentAuthListener);
        }
        sendBroadcast(componentName, i);
    }

    private void sendBroadcast() {
        android.content.ComponentName approverComponent;
        int currentUserIfAdmin = getCurrentUserIfAdmin();
        if (currentUserIfAdmin == -10000 || (approverComponent = getApproverComponent(currentUserIfAdmin)) == null) {
            return;
        }
        sendBroadcast(approverComponent, currentUserIfAdmin);
    }

    private void sendBroadcast(android.content.ComponentName componentName, int i) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.PENDING_INCIDENT_REPORTS_CHANGED");
        intent.setComponent(componentName);
        intent.addFlags(268435456);
        intent.addFlags(16777216);
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setBackgroundActivityStartsAllowed(true);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.of(i), "android.permission.APPROVE_INCIDENT_REPORTS", makeBasic.toBundle());
    }

    private com.android.server.incident.PendingReports.PendingReportRec findAndRemovePendingReportRecLocked(java.lang.String str) {
        try {
            int parseInt = java.lang.Integer.parseInt(android.net.Uri.parse(str).getQueryParameter("id"));
            java.util.Iterator<com.android.server.incident.PendingReports.PendingReportRec> it = this.mPending.iterator();
            while (it.hasNext()) {
                com.android.server.incident.PendingReports.PendingReportRec next = it.next();
                if (next.id == parseInt) {
                    it.remove();
                    return next;
                }
            }
            return null;
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.w(TAG, "Can't parse id from: " + str);
            return null;
        }
    }

    private void removePendingReportRecLocked(android.os.IIncidentAuthListener iIncidentAuthListener) {
        java.util.Iterator<com.android.server.incident.PendingReports.PendingReportRec> it = this.mPending.iterator();
        while (it.hasNext()) {
            com.android.server.incident.PendingReports.PendingReportRec next = it.next();
            if (next.listener.asBinder() == iIncidentAuthListener.asBinder()) {
                android.util.Log.i(TAG, "  ...Removed PendingReportRec index=" + it + ": " + next.getUri());
                it.remove();
            }
        }
    }

    private void denyReportBeforeAddingRec(android.os.IIncidentAuthListener iIncidentAuthListener, java.lang.String str) {
        try {
            iIncidentAuthListener.onReportDenied();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed calling back for denial for " + str, e);
        }
    }

    private int getCurrentUserIfAdmin() {
        return com.android.server.incident.IncidentCompanionService.getCurrentUserIfAdmin();
    }

    private android.content.ComponentName getApproverComponent(int i) {
        java.util.List queryBroadcastReceiversAsUser = this.mPackageManager.queryBroadcastReceiversAsUser(new android.content.Intent("android.intent.action.PENDING_INCIDENT_REPORTS_CHANGED"), 1835008, i);
        if (queryBroadcastReceiversAsUser.size() == 1) {
            return ((android.content.pm.ResolveInfo) queryBroadcastReceiversAsUser.get(0)).getComponentInfo().getComponentName();
        }
        android.util.Log.w(TAG, "Didn't find exactly one BroadcastReceiver to handle android.intent.action.PENDING_INCIDENT_REPORTS_CHANGED. The report will be denied. size=" + queryBroadcastReceiversAsUser.size() + ": matches=" + queryBroadcastReceiversAsUser);
        return null;
    }

    private boolean isPackageInUid(int i, java.lang.String str) {
        try {
            this.mAppOpsManager.checkPackage(i, str);
            return true;
        } catch (java.lang.SecurityException e) {
            return false;
        }
    }

    private boolean isSameProfileGroupUser(int i, int i2) {
        return android.os.UserManager.get(this.mContext).isSameProfileGroup(i, i2);
    }
}
