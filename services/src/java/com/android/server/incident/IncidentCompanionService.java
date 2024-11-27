package com.android.server.incident;

/* loaded from: classes2.dex */
public class IncidentCompanionService extends com.android.server.SystemService {
    static final java.lang.String TAG = "IncidentCompanionService";
    private com.android.server.incident.PendingReports mPendingReports;
    private static java.lang.String[] RESTRICTED_IMAGE_DUMP_ARGS = {"--hal", "--restricted_image"};
    private static final java.lang.String[] DUMP_AND_USAGE_STATS_PERMISSIONS = {"android.permission.DUMP", "android.permission.PACKAGE_USAGE_STATS"};

    private final class BinderService extends android.os.IIncidentCompanion.Stub {
        private BinderService() {
        }

        public void authorizeReport(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, android.os.IIncidentAuthListener iIncidentAuthListener) {
            enforceRequestAuthorizationPermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.incident.IncidentCompanionService.this.mPendingReports.authorizeReport(i, str, str2, str3, i2, iIncidentAuthListener);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void cancelAuthorization(android.os.IIncidentAuthListener iIncidentAuthListener) {
            enforceRequestAuthorizationPermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.incident.IncidentCompanionService.this.mPendingReports.cancelAuthorization(iIncidentAuthListener);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendReportReadyBroadcast(java.lang.String str, java.lang.String str2) {
            enforceRequestAuthorizationPermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.content.Context context = com.android.server.incident.IncidentCompanionService.this.getContext();
                int currentUserIfAdmin = com.android.server.incident.IncidentCompanionService.getCurrentUserIfAdmin();
                if (currentUserIfAdmin == -10000) {
                    return;
                }
                android.content.Intent intent = new android.content.Intent("android.intent.action.INCIDENT_REPORT_READY");
                intent.setComponent(new android.content.ComponentName(str, str2));
                android.util.Log.d(com.android.server.incident.IncidentCompanionService.TAG, "sendReportReadyBroadcast sending currentUser=" + currentUserIfAdmin + " userHandle=" + android.os.UserHandle.of(currentUserIfAdmin) + " intent=" + intent);
                context.sendBroadcastAsUserMultiplePermissions(intent, android.os.UserHandle.of(currentUserIfAdmin), com.android.server.incident.IncidentCompanionService.DUMP_AND_USAGE_STATS_PERMISSIONS);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.util.List<java.lang.String> getPendingReports() {
            enforceAuthorizePermission();
            return com.android.server.incident.IncidentCompanionService.this.mPendingReports.getPendingReports();
        }

        public void approveReport(java.lang.String str) {
            enforceAuthorizePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.incident.IncidentCompanionService.this.mPendingReports.approveReport(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void denyReport(java.lang.String str) {
            enforceAuthorizePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.incident.IncidentCompanionService.this.mPendingReports.denyReport(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.util.List<java.lang.String> getIncidentReportList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            enforceAccessReportsPermissions(null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.incident.IncidentCompanionService.this.getIIncidentManager().getIncidentReportList(str, str2);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void deleteIncidentReports(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            if (str == null || str2 == null || str3 == null || str.length() == 0 || str2.length() == 0 || str3.length() == 0) {
                throw new java.lang.RuntimeException("Invalid pkg, cls or id");
            }
            enforceAccessReportsPermissions(str);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.incident.IncidentCompanionService.this.getIIncidentManager().deleteIncidentReports(str, str2, str3);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void deleteAllIncidentReports(java.lang.String str) throws android.os.RemoteException {
            if (str == null || str.length() == 0) {
                throw new java.lang.RuntimeException("Invalid pkg");
            }
            enforceAccessReportsPermissions(str);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.incident.IncidentCompanionService.this.getIIncidentManager().deleteAllIncidentReports(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.os.IncidentManager.IncidentReport getIncidentReport(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            if (str == null || str2 == null || str3 == null || str.length() == 0 || str2.length() == 0 || str3.length() == 0) {
                throw new java.lang.RuntimeException("Invalid pkg, cls or id");
            }
            enforceAccessReportsPermissions(str);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.incident.IncidentCompanionService.this.getIIncidentManager().getIncidentReport(str, str2, str3);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (!com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.incident.IncidentCompanionService.this.getContext(), com.android.server.incident.IncidentCompanionService.TAG, printWriter)) {
                return;
            }
            if (strArr.length == 1 && "--restricted_image".equals(strArr[0])) {
                dumpRestrictedImages(fileDescriptor);
            } else {
                com.android.server.incident.IncidentCompanionService.this.mPendingReports.dump(fileDescriptor, printWriter, strArr);
            }
        }

        private void dumpRestrictedImages(java.io.FileDescriptor fileDescriptor) {
            if (!android.os.Build.IS_ENG && !android.os.Build.IS_USERDEBUG) {
                return;
            }
            for (java.lang.String str : com.android.server.incident.IncidentCompanionService.this.getContext().getResources().getStringArray(android.R.array.config_reduceBrightColorsCoefficients)) {
                android.util.Log.d(com.android.server.incident.IncidentCompanionService.TAG, "Looking up service " + str);
                android.os.IBinder service = android.os.ServiceManager.getService(str);
                if (service != null) {
                    android.util.Log.d(com.android.server.incident.IncidentCompanionService.TAG, "Calling dump on service: " + str);
                    try {
                        service.dump(fileDescriptor, com.android.server.incident.IncidentCompanionService.RESTRICTED_IMAGE_DUMP_ARGS);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(com.android.server.incident.IncidentCompanionService.TAG, "dump --restricted_image of " + str + " threw", e);
                    }
                }
            }
        }

        private void enforceRequestAuthorizationPermission() {
            com.android.server.incident.IncidentCompanionService.this.getContext().enforceCallingOrSelfPermission("android.permission.REQUEST_INCIDENT_REPORT_APPROVAL", null);
        }

        private void enforceAuthorizePermission() {
            com.android.server.incident.IncidentCompanionService.this.getContext().enforceCallingOrSelfPermission("android.permission.APPROVE_INCIDENT_REPORTS", null);
        }

        private void enforceAccessReportsPermissions(java.lang.String str) {
            if (com.android.server.incident.IncidentCompanionService.this.getContext().checkCallingPermission("android.permission.APPROVE_INCIDENT_REPORTS") != 0) {
                com.android.server.incident.IncidentCompanionService.this.getContext().enforceCallingOrSelfPermission("android.permission.DUMP", null);
                com.android.server.incident.IncidentCompanionService.this.getContext().enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", null);
                if (str != null) {
                    enforceCallerIsSameApp(str);
                }
            }
        }

        private void enforceCallerIsSameApp(java.lang.String str) throws java.lang.SecurityException {
            try {
                int callingUid = android.os.Binder.getCallingUid();
                android.content.pm.ApplicationInfo applicationInfoAsUser = com.android.server.incident.IncidentCompanionService.this.getContext().getPackageManager().getApplicationInfoAsUser(str, 0, android.os.UserHandle.getCallingUserId());
                if (applicationInfoAsUser == null) {
                    throw new java.lang.SecurityException("Unknown package " + str);
                }
                if (!android.os.UserHandle.isSameApp(applicationInfoAsUser.uid, callingUid)) {
                    throw new java.lang.SecurityException("Calling uid " + callingUid + " gave package " + str + " which is owned by uid " + applicationInfoAsUser.uid);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throw new java.lang.SecurityException("Unknown package " + str + "\n" + e);
            }
        }
    }

    public IncidentCompanionService(android.content.Context context) {
        super(context);
        this.mPendingReports = new com.android.server.incident.PendingReports(context);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("incidentcompanion", new com.android.server.incident.IncidentCompanionService.BinderService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        super.onBootPhase(i);
        switch (i) {
            case 1000:
                this.mPendingReports.onBootCompleted();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.IIncidentManager getIIncidentManager() throws android.os.RemoteException {
        return android.os.IIncidentManager.Stub.asInterface(android.os.ServiceManager.getService("incident"));
    }

    public static int getCurrentUserIfAdmin() {
        try {
            android.content.pm.UserInfo currentUser = android.app.ActivityManager.getService().getCurrentUser();
            if (currentUser == null) {
                android.util.Log.w(TAG, "No current user.  Nobody to approve the report. The report will be denied.");
                return com.android.server.am.ProcessList.INVALID_ADJ;
            }
            if (!currentUser.isAdmin()) {
                android.util.Log.w(TAG, "Only an admin user running in foreground can approve bugreports, but the current foreground user is not an admin user. The report will be denied.");
                return com.android.server.am.ProcessList.INVALID_ADJ;
            }
            return currentUser.id;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }
}
