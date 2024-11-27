package com.android.server.print;

/* loaded from: classes2.dex */
public final class PrintManagerService extends com.android.server.SystemService {
    private static final java.lang.String LOG_TAG = "PrintManagerService";
    private final com.android.server.print.PrintManagerService.PrintManagerImpl mPrintManagerImpl;

    public PrintManagerService(android.content.Context context) {
        super(context);
        this.mPrintManagerImpl = new com.android.server.print.PrintManagerService.PrintManagerImpl(context);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("print", this.mPrintManagerImpl);
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        this.mPrintManagerImpl.handleUserUnlocked(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        this.mPrintManagerImpl.handleUserStopped(targetUser.getUserIdentifier());
    }

    class PrintManagerImpl extends android.print.IPrintManager.Stub {
        private static final int BACKGROUND_USER_ID = -10;
        private final android.content.Context mContext;
        private final android.os.UserManager mUserManager;
        private final java.lang.Object mLock = new java.lang.Object();
        private final android.util.SparseArray<com.android.server.print.UserState> mUserStates = new android.util.SparseArray<>();

        PrintManagerImpl(android.content.Context context) {
            this.mContext = context;
            this.mUserManager = (android.os.UserManager) context.getSystemService("user");
            registerContentObservers();
            registerBroadcastReceivers();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new com.android.server.print.PrintShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public android.os.Bundle print(java.lang.String str, android.print.IPrintDocumentAdapter iPrintDocumentAdapter, android.print.PrintAttributes printAttributes, java.lang.String str2, int i, int i2) {
            long clearCallingIdentity;
            java.util.Objects.requireNonNull(iPrintDocumentAdapter);
            if (!isPrintingEnabled()) {
                android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
                int callingUserId = android.os.UserHandle.getCallingUserId();
                clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.lang.CharSequence printingDisabledReasonForUser = devicePolicyManagerInternal.getPrintingDisabledReasonForUser(callingUserId);
                    if (printingDisabledReasonForUser != null) {
                        android.widget.Toast.makeText(this.mContext, android.os.Looper.getMainLooper(), printingDisabledReasonForUser, 1).show();
                    }
                    try {
                        iPrintDocumentAdapter.start();
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(com.android.server.print.PrintManagerService.LOG_TAG, "Error calling IPrintDocumentAdapter.start()");
                    }
                    try {
                        iPrintDocumentAdapter.finish();
                    } catch (android.os.RemoteException e2) {
                        android.util.Log.e(com.android.server.print.PrintManagerService.LOG_TAG, "Error calling IPrintDocumentAdapter.finish()");
                    }
                    return null;
                } finally {
                }
            }
            java.lang.String str3 = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
            java.lang.String str4 = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str2);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return null;
                    }
                    int resolveCallingAppEnforcingPermissions = resolveCallingAppEnforcingPermissions(i);
                    java.lang.String resolveCallingPackageNameEnforcingSecurity = resolveCallingPackageNameEnforcingSecurity(str4);
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        return orCreateUserStateLocked.print(str3, iPrintDocumentAdapter, printAttributes, resolveCallingPackageNameEnforcingSecurity, resolveCallingAppEnforcingPermissions);
                    } finally {
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public java.util.List<android.print.PrintJobInfo> getPrintJobInfos(int i, int i2) {
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return null;
                    }
                    int resolveCallingAppEnforcingPermissions = resolveCallingAppEnforcingPermissions(i);
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        return orCreateUserStateLocked.getPrintJobInfos(resolveCallingAppEnforcingPermissions);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.print.PrintJobInfo getPrintJobInfo(android.print.PrintJobId printJobId, int i, int i2) {
            if (printJobId == null) {
                return null;
            }
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return null;
                    }
                    int resolveCallingAppEnforcingPermissions = resolveCallingAppEnforcingPermissions(i);
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        return orCreateUserStateLocked.getPrintJobInfo(printJobId, resolveCallingAppEnforcingPermissions);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.graphics.drawable.Icon getCustomPrinterIcon(android.print.PrinterId printerId, int i) {
            java.util.Objects.requireNonNull(printerId);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return null;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        return validateIconUserBoundary(orCreateUserStateLocked.getCustomPrinterIcon(printerId));
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private android.graphics.drawable.Icon validateIconUserBoundary(android.graphics.drawable.Icon icon) {
            java.lang.String encodedUserInfo;
            if (icon != null && ((icon.getType() == 4 || icon.getType() == 6) && (encodedUserInfo = icon.getUri().getEncodedUserInfo()) != null)) {
                int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(java.lang.Integer.parseInt(encodedUserInfo));
                synchronized (this.mLock) {
                    try {
                        if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                            return null;
                        }
                    } finally {
                    }
                }
            }
            return icon;
        }

        public void cancelPrintJob(android.print.PrintJobId printJobId, int i, int i2) {
            if (printJobId == null) {
                return;
            }
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    int resolveCallingAppEnforcingPermissions = resolveCallingAppEnforcingPermissions(i);
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.cancelPrintJob(printJobId, resolveCallingAppEnforcingPermissions);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void restartPrintJob(android.print.PrintJobId printJobId, int i, int i2) {
            if (printJobId == null || !isPrintingEnabled()) {
                return;
            }
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    int resolveCallingAppEnforcingPermissions = resolveCallingAppEnforcingPermissions(i);
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.restartPrintJob(printJobId, resolveCallingAppEnforcingPermissions);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public java.util.List<android.printservice.PrintServiceInfo> getPrintServices(int i, int i2) {
            com.android.internal.util.Preconditions.checkFlagsArgument(i, 3);
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRINT_SERVICES", null);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return null;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        return orCreateUserStateLocked.getPrintServices(i);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setPrintServiceEnabled(android.content.ComponentName componentName, boolean z, int i) {
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            int appId = android.os.UserHandle.getAppId(android.os.Binder.getCallingUid());
            if (appId != 1000) {
                try {
                    if (appId != android.os.UserHandle.getAppId(this.mContext.getPackageManager().getPackageUidAsUser("com.android.printspooler", resolveCallingUserEnforcingPermissions))) {
                        throw new java.lang.SecurityException("Only system and print spooler can call this");
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Log.e(com.android.server.print.PrintManagerService.LOG_TAG, "Could not verify caller", e);
                    return;
                }
            }
            java.util.Objects.requireNonNull(componentName);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.setPrintServiceEnabled(componentName, z);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean isPrintServiceEnabled(android.content.ComponentName componentName, int i) {
            boolean z;
            java.lang.String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(android.os.Binder.getCallingUid());
            int i2 = 0;
            while (true) {
                if (i2 >= packagesForUid.length) {
                    z = false;
                    break;
                }
                if (!packagesForUid[i2].equals(componentName.getPackageName())) {
                    i2++;
                } else {
                    z = true;
                    break;
                }
            }
            if (!z) {
                throw new java.lang.SecurityException("PrintService does not share UID with caller.");
            }
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return false;
                    }
                    return getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false).isPrintServiceEnabled(componentName);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public java.util.List<android.printservice.recommendation.RecommendationInfo> getPrintServiceRecommendations(int i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRINT_SERVICE_RECOMMENDATIONS", null);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return null;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        return orCreateUserStateLocked.getPrintServiceRecommendations();
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void createPrinterDiscoverySession(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) {
            java.util.Objects.requireNonNull(iPrinterDiscoveryObserver);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.createPrinterDiscoverySession(iPrinterDiscoveryObserver);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void destroyPrinterDiscoverySession(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) {
            java.util.Objects.requireNonNull(iPrinterDiscoveryObserver);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.destroyPrinterDiscoverySession(iPrinterDiscoveryObserver);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void startPrinterDiscovery(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, java.util.List<android.print.PrinterId> list, int i) {
            java.util.Objects.requireNonNull(iPrinterDiscoveryObserver);
            if (list != null) {
                list = (java.util.List) com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "PrinterId");
            }
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.startPrinterDiscovery(iPrinterDiscoveryObserver, list);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void stopPrinterDiscovery(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) {
            java.util.Objects.requireNonNull(iPrinterDiscoveryObserver);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.stopPrinterDiscovery(iPrinterDiscoveryObserver);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void validatePrinters(java.util.List<android.print.PrinterId> list, int i) {
            java.util.List<android.print.PrinterId> list2 = (java.util.List) com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "PrinterId");
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.validatePrinters(list2);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void startPrinterStateTracking(android.print.PrinterId printerId, int i) {
            java.util.Objects.requireNonNull(printerId);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.startPrinterStateTracking(printerId);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void stopPrinterStateTracking(android.print.PrinterId printerId, int i) {
            java.util.Objects.requireNonNull(printerId);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.stopPrinterStateTracking(printerId);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void addPrintJobStateChangeListener(android.print.IPrintJobStateChangeListener iPrintJobStateChangeListener, int i, int i2) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(iPrintJobStateChangeListener);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    int resolveCallingAppEnforcingPermissions = resolveCallingAppEnforcingPermissions(i);
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.addPrintJobStateChangeListener(iPrintJobStateChangeListener, resolveCallingAppEnforcingPermissions);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void removePrintJobStateChangeListener(android.print.IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) {
            java.util.Objects.requireNonNull(iPrintJobStateChangeListener);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.removePrintJobStateChangeListener(iPrintJobStateChangeListener);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void addPrintServicesChangeListener(android.print.IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(iPrintServicesChangeListener);
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRINT_SERVICES", null);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.addPrintServicesChangeListener(iPrintServicesChangeListener);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void removePrintServicesChangeListener(android.print.IPrintServicesChangeListener iPrintServicesChangeListener, int i) {
            java.util.Objects.requireNonNull(iPrintServicesChangeListener);
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRINT_SERVICES", null);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.removePrintServicesChangeListener(iPrintServicesChangeListener);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void addPrintServiceRecommendationsChangeListener(android.printservice.recommendation.IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(iRecommendationsChangeListener);
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRINT_SERVICE_RECOMMENDATIONS", null);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.addPrintServiceRecommendationsChangeListener(iRecommendationsChangeListener);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void removePrintServiceRecommendationsChangeListener(android.printservice.recommendation.IRecommendationsChangeListener iRecommendationsChangeListener, int i) {
            java.util.Objects.requireNonNull(iRecommendationsChangeListener);
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRINT_SERVICE_RECOMMENDATIONS", null);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    com.android.server.print.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.removePrintServiceRecommendationsChangeListener(iRecommendationsChangeListener);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            java.lang.String str;
            java.util.Objects.requireNonNull(fileDescriptor);
            if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, com.android.server.print.PrintManagerService.LOG_TAG, printWriter)) {
                int i = 0;
                boolean z = false;
                while (i < strArr.length && (str = strArr[i]) != null && str.length() > 0 && str.charAt(0) == '-') {
                    i++;
                    if ("--proto".equals(str)) {
                        z = true;
                    } else {
                        printWriter.println("Unknown argument: " + str + "; use -h for help");
                    }
                }
                java.util.ArrayList<com.android.server.print.UserState> arrayList = new java.util.ArrayList<>();
                synchronized (this.mLock) {
                    try {
                        int size = this.mUserStates.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            arrayList.add(this.mUserStates.valueAt(i2));
                        }
                    } finally {
                    }
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (z) {
                        dump(new com.android.internal.util.dump.DualDumpOutputStream(new android.util.proto.ProtoOutputStream(fileDescriptor)), arrayList);
                    } else {
                        printWriter.println("PRINT MANAGER STATE (dumpsys print)");
                        dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(printWriter, "  ")), arrayList);
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public boolean getBindInstantServiceAllowed(int i) {
            com.android.server.print.UserState orCreateUserStateLocked;
            int callingUid = android.os.Binder.getCallingUid();
            if (callingUid != 2000 && callingUid != 0) {
                throw new java.lang.SecurityException("Can only be called by uid 2000 or 0");
            }
            synchronized (this.mLock) {
                orCreateUserStateLocked = getOrCreateUserStateLocked(i, false);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return orCreateUserStateLocked.getBindInstantServiceAllowed();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setBindInstantServiceAllowed(int i, boolean z) {
            com.android.server.print.UserState orCreateUserStateLocked;
            int callingUid = android.os.Binder.getCallingUid();
            if (callingUid != 2000 && callingUid != 0) {
                throw new java.lang.SecurityException("Can only be called by uid 2000 or 0");
            }
            synchronized (this.mLock) {
                orCreateUserStateLocked = getOrCreateUserStateLocked(i, false);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                orCreateUserStateLocked.setBindInstantServiceAllowed(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private boolean isPrintingEnabled() {
            return !this.mUserManager.hasUserRestriction("no_printing", android.os.Binder.getCallingUserHandle());
        }

        private void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, @android.annotation.NonNull java.util.ArrayList<com.android.server.print.UserState> arrayList) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                long start = dualDumpOutputStream.start("user_states", 2246267895809L);
                arrayList.get(i).dump(dualDumpOutputStream);
                dualDumpOutputStream.end(start);
            }
            dualDumpOutputStream.flush();
        }

        private void registerContentObservers() {
            final android.net.Uri uriFor = android.provider.Settings.Secure.getUriFor("disabled_print_services");
            this.mContext.getContentResolver().registerContentObserver(uriFor, false, new android.database.ContentObserver(com.android.internal.os.BackgroundThread.getHandler()) { // from class: com.android.server.print.PrintManagerService.PrintManagerImpl.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z, android.net.Uri uri, int i) {
                    int i2;
                    if (uriFor.equals(uri)) {
                        synchronized (com.android.server.print.PrintManagerService.PrintManagerImpl.this.mLock) {
                            try {
                                int size = com.android.server.print.PrintManagerService.PrintManagerImpl.this.mUserStates.size();
                                while (i2 < size) {
                                    i2 = (i == -1 || i == com.android.server.print.PrintManagerService.PrintManagerImpl.this.mUserStates.keyAt(i2)) ? 0 : i2 + 1;
                                    ((com.android.server.print.UserState) com.android.server.print.PrintManagerService.PrintManagerImpl.this.mUserStates.valueAt(i2)).updateIfNeededLocked();
                                }
                            } finally {
                            }
                        }
                    }
                }
            }, -1);
        }

        private void registerBroadcastReceivers() {
            new com.android.internal.content.PackageMonitor() { // from class: com.android.server.print.PrintManagerService.PrintManagerImpl.2
                private boolean hasPrintService(java.lang.String str) {
                    android.content.Intent intent = new android.content.Intent("android.printservice.PrintService");
                    intent.setPackage(str);
                    java.util.List queryIntentServicesAsUser = com.android.server.print.PrintManagerService.PrintManagerImpl.this.mContext.getPackageManager().queryIntentServicesAsUser(intent, 276824068, getChangingUserId());
                    return (queryIntentServicesAsUser == null || queryIntentServicesAsUser.isEmpty()) ? false : true;
                }

                private boolean hadPrintService(@android.annotation.NonNull com.android.server.print.UserState userState, java.lang.String str) {
                    java.util.List<android.printservice.PrintServiceInfo> printServices = userState.getPrintServices(3);
                    if (printServices == null) {
                        return false;
                    }
                    int size = printServices.size();
                    for (int i = 0; i < size; i++) {
                        if (printServices.get(i).getResolveInfo().serviceInfo.packageName.equals(str)) {
                            return true;
                        }
                    }
                    return false;
                }

                public void onPackageModified(java.lang.String str) {
                    if (com.android.server.print.PrintManagerService.PrintManagerImpl.this.mUserManager.isUserUnlockingOrUnlocked(getChangingUserId())) {
                        boolean z = false;
                        com.android.server.print.UserState orCreateUserStateLocked = com.android.server.print.PrintManagerService.PrintManagerImpl.this.getOrCreateUserStateLocked(getChangingUserId(), false, false);
                        synchronized (com.android.server.print.PrintManagerService.PrintManagerImpl.this.mLock) {
                            try {
                                if (!hadPrintService(orCreateUserStateLocked, str)) {
                                    if (hasPrintService(str)) {
                                    }
                                }
                                orCreateUserStateLocked.updateIfNeededLocked();
                                z = true;
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                        if (z) {
                            orCreateUserStateLocked.prunePrintServices();
                        }
                    }
                }

                public void onPackageRemoved(java.lang.String str, int i) {
                    if (com.android.server.print.PrintManagerService.PrintManagerImpl.this.mUserManager.isUserUnlockingOrUnlocked(getChangingUserId())) {
                        boolean z = false;
                        com.android.server.print.UserState orCreateUserStateLocked = com.android.server.print.PrintManagerService.PrintManagerImpl.this.getOrCreateUserStateLocked(getChangingUserId(), false, false);
                        synchronized (com.android.server.print.PrintManagerService.PrintManagerImpl.this.mLock) {
                            try {
                                if (hadPrintService(orCreateUserStateLocked, str)) {
                                    orCreateUserStateLocked.updateIfNeededLocked();
                                    z = true;
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                        if (z) {
                            orCreateUserStateLocked.prunePrintServices();
                        }
                    }
                }

                public boolean onHandleForceStop(android.content.Intent intent, java.lang.String[] strArr, int i, boolean z) {
                    if (!com.android.server.print.PrintManagerService.PrintManagerImpl.this.mUserManager.isUserUnlockingOrUnlocked(getChangingUserId())) {
                        return false;
                    }
                    synchronized (com.android.server.print.PrintManagerService.PrintManagerImpl.this.mLock) {
                        try {
                            com.android.server.print.UserState orCreateUserStateLocked = com.android.server.print.PrintManagerService.PrintManagerImpl.this.getOrCreateUserStateLocked(getChangingUserId(), false, false);
                            java.util.List<android.printservice.PrintServiceInfo> printServices = orCreateUserStateLocked.getPrintServices(1);
                            if (printServices == null) {
                                return false;
                            }
                            java.util.Iterator<android.printservice.PrintServiceInfo> it = printServices.iterator();
                            boolean z2 = false;
                            while (it.hasNext()) {
                                java.lang.String packageName = it.next().getComponentName().getPackageName();
                                int length = strArr.length;
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= length) {
                                        break;
                                    }
                                    if (!packageName.equals(strArr[i2])) {
                                        i2++;
                                    } else {
                                        if (!z) {
                                            return true;
                                        }
                                        z2 = true;
                                    }
                                }
                            }
                            if (z2) {
                                orCreateUserStateLocked.updateIfNeededLocked();
                            }
                            return false;
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }

                public void onPackageAdded(java.lang.String str, int i) {
                    if (com.android.server.print.PrintManagerService.PrintManagerImpl.this.mUserManager.isUserUnlockingOrUnlocked(getChangingUserId())) {
                        synchronized (com.android.server.print.PrintManagerService.PrintManagerImpl.this.mLock) {
                            try {
                                if (hasPrintService(str)) {
                                    com.android.server.print.PrintManagerService.PrintManagerImpl.this.getOrCreateUserStateLocked(getChangingUserId(), false, false).updateIfNeededLocked();
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                    }
                }
            }.register(this.mContext, com.android.internal.os.BackgroundThread.getHandler().getLooper(), android.os.UserHandle.ALL, true);
        }

        private com.android.server.print.UserState getOrCreateUserStateLocked(int i, boolean z) {
            return getOrCreateUserStateLocked(i, z, true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.print.UserState getOrCreateUserStateLocked(int i, boolean z, boolean z2) {
            return getOrCreateUserStateLocked(i, z, z2, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.print.UserState getOrCreateUserStateLocked(int i, boolean z, boolean z2, boolean z3) {
            if (z2 && !this.mUserManager.isUserUnlockingOrUnlocked(i)) {
                throw new java.lang.IllegalStateException("User " + i + " must be unlocked for printing to be available");
            }
            com.android.server.print.UserState userState = this.mUserStates.get(i);
            if (userState == null) {
                userState = new com.android.server.print.UserState(this.mContext, i, this.mLock, z);
                this.mUserStates.put(i, userState);
            } else if (z3) {
                userState.updateIfNeededLocked();
            }
            if (!z) {
                userState.increasePriority();
            }
            return userState;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleUserUnlocked(final int i) {
            com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.print.PrintManagerService.PrintManagerImpl.3
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.print.UserState orCreateUserStateLocked;
                    if (com.android.server.print.PrintManagerService.PrintManagerImpl.this.mUserManager.isUserUnlockingOrUnlocked(i)) {
                        synchronized (com.android.server.print.PrintManagerService.PrintManagerImpl.this.mLock) {
                            orCreateUserStateLocked = com.android.server.print.PrintManagerService.PrintManagerImpl.this.getOrCreateUserStateLocked(i, true, false, true);
                        }
                        orCreateUserStateLocked.removeObsoletePrintJobs();
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleUserStopped(final int i) {
            com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.print.PrintManagerService.PrintManagerImpl.4
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (com.android.server.print.PrintManagerService.PrintManagerImpl.this.mLock) {
                        try {
                            com.android.server.print.UserState userState = (com.android.server.print.UserState) com.android.server.print.PrintManagerService.PrintManagerImpl.this.mUserStates.get(i);
                            if (userState != null) {
                                userState.destroyLocked();
                                com.android.server.print.PrintManagerService.PrintManagerImpl.this.mUserStates.remove(i);
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        }

        private int resolveCallingProfileParentLocked(int i) {
            if (i == getCurrentUserId()) {
                return i;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.content.pm.UserInfo profileParent = this.mUserManager.getProfileParent(i);
                if (profileParent != null) {
                    return profileParent.getUserHandle().getIdentifier();
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return -10;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private int resolveCallingAppEnforcingPermissions(int i) {
            int callingUid = android.os.Binder.getCallingUid();
            if (callingUid == 0) {
                return i;
            }
            int appId = android.os.UserHandle.getAppId(callingUid);
            if (i == appId || appId == 2000 || appId == 1000) {
                return i;
            }
            if (this.mContext.checkCallingPermission("com.android.printspooler.permission.ACCESS_ALL_PRINT_JOBS") != 0) {
                throw new java.lang.SecurityException("Call from app " + appId + " as app " + i + " without com.android.printspooler.permission.ACCESS_ALL_PRINT_JOBS");
            }
            return i;
        }

        private int resolveCallingUserEnforcingPermissions(int i) {
            try {
                return android.app.ActivityManager.getService().handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, true, true, "", (java.lang.String) null);
            } catch (android.os.RemoteException e) {
                return i;
            }
        }

        @android.annotation.NonNull
        private java.lang.String resolveCallingPackageNameEnforcingSecurity(@android.annotation.NonNull java.lang.String str) {
            for (java.lang.String str2 : this.mContext.getPackageManager().getPackagesForUid(android.os.Binder.getCallingUid())) {
                if (str.equals(str2)) {
                    return str;
                }
            }
            throw new java.lang.IllegalArgumentException("packageName has to belong to the caller");
        }

        private int getCurrentUserId() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return android.app.ActivityManager.getCurrentUser();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }
}
