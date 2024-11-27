package android.content.pm;

/* loaded from: classes.dex */
public interface IPackageInstaller extends android.os.IInterface {
    void abandonSession(int i) throws android.os.RemoteException;

    void bypassNextAllowedApexUpdateCheck(boolean z) throws android.os.RemoteException;

    void bypassNextStagedInstallerCheck(boolean z) throws android.os.RemoteException;

    void checkInstallConstraints(java.lang.String str, java.util.List<java.lang.String> list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    int createSession(android.content.pm.PackageInstaller.SessionParams sessionParams, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void disableVerificationForUid(int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getAllSessions(int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getMySessions(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.PackageInstaller.SessionInfo getSessionInfo(int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getStagedSessions() throws android.os.RemoteException;

    void installExistingPackage(java.lang.String str, int i, int i2, android.content.IntentSender intentSender, int i3, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void installPackageArchived(android.content.pm.ArchivedPackageParcel archivedPackageParcel, android.content.pm.PackageInstaller.SessionParams sessionParams, android.content.IntentSender intentSender, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException;

    android.content.pm.IPackageInstallerSession openSession(int i) throws android.os.RemoteException;

    void registerCallback(android.content.pm.IPackageInstallerCallback iPackageInstallerCallback, int i) throws android.os.RemoteException;

    void reportUnarchivalStatus(int i, int i2, long j, android.app.PendingIntent pendingIntent, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void requestArchive(java.lang.String str, java.lang.String str2, int i, android.content.IntentSender intentSender, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void requestUnarchive(java.lang.String str, java.lang.String str2, android.content.IntentSender intentSender, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void setAllowUnlimitedSilentUpdates(java.lang.String str) throws android.os.RemoteException;

    void setPermissionsResult(int i, boolean z) throws android.os.RemoteException;

    void setSilentUpdatesThrottleTime(long j) throws android.os.RemoteException;

    void uninstall(android.content.pm.VersionedPackage versionedPackage, java.lang.String str, int i, android.content.IntentSender intentSender, int i2) throws android.os.RemoteException;

    void uninstallExistingPackage(android.content.pm.VersionedPackage versionedPackage, java.lang.String str, android.content.IntentSender intentSender, int i) throws android.os.RemoteException;

    void unregisterCallback(android.content.pm.IPackageInstallerCallback iPackageInstallerCallback) throws android.os.RemoteException;

    void updateSessionAppIcon(int i, android.graphics.Bitmap bitmap) throws android.os.RemoteException;

    void updateSessionAppLabel(int i, java.lang.String str) throws android.os.RemoteException;

    void waitForInstallConstraints(java.lang.String str, java.util.List<java.lang.String> list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, android.content.IntentSender intentSender, long j) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IPackageInstaller {
        @Override // android.content.pm.IPackageInstaller
        public int createSession(android.content.pm.PackageInstaller.SessionParams sessionParams, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageInstaller
        public void updateSessionAppIcon(int i, android.graphics.Bitmap bitmap) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void updateSessionAppLabel(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void abandonSession(int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public android.content.pm.IPackageInstallerSession openSession(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public android.content.pm.PackageInstaller.SessionInfo getSessionInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public android.content.pm.ParceledListSlice getAllSessions(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public android.content.pm.ParceledListSlice getMySessions(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public android.content.pm.ParceledListSlice getStagedSessions() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public void registerCallback(android.content.pm.IPackageInstallerCallback iPackageInstallerCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void unregisterCallback(android.content.pm.IPackageInstallerCallback iPackageInstallerCallback) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void uninstall(android.content.pm.VersionedPackage versionedPackage, java.lang.String str, int i, android.content.IntentSender intentSender, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void uninstallExistingPackage(android.content.pm.VersionedPackage versionedPackage, java.lang.String str, android.content.IntentSender intentSender, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void installExistingPackage(java.lang.String str, int i, int i2, android.content.IntentSender intentSender, int i3, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void setPermissionsResult(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void bypassNextStagedInstallerCheck(boolean z) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void bypassNextAllowedApexUpdateCheck(boolean z) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void disableVerificationForUid(int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void setAllowUnlimitedSilentUpdates(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void setSilentUpdatesThrottleTime(long j) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void checkInstallConstraints(java.lang.String str, java.util.List<java.lang.String> list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void waitForInstallConstraints(java.lang.String str, java.util.List<java.lang.String> list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, android.content.IntentSender intentSender, long j) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void requestArchive(java.lang.String str, java.lang.String str2, int i, android.content.IntentSender intentSender, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void requestUnarchive(java.lang.String str, java.lang.String str2, android.content.IntentSender intentSender, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void installPackageArchived(android.content.pm.ArchivedPackageParcel archivedPackageParcel, android.content.pm.PackageInstaller.SessionParams sessionParams, android.content.IntentSender intentSender, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void reportUnarchivalStatus(int i, int i2, long j, android.app.PendingIntent pendingIntent, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IPackageInstaller {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.IPackageInstaller";
        static final int TRANSACTION_abandonSession = 4;
        static final int TRANSACTION_bypassNextAllowedApexUpdateCheck = 17;
        static final int TRANSACTION_bypassNextStagedInstallerCheck = 16;
        static final int TRANSACTION_checkInstallConstraints = 21;
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_disableVerificationForUid = 18;
        static final int TRANSACTION_getAllSessions = 7;
        static final int TRANSACTION_getMySessions = 8;
        static final int TRANSACTION_getSessionInfo = 6;
        static final int TRANSACTION_getStagedSessions = 9;
        static final int TRANSACTION_installExistingPackage = 14;
        static final int TRANSACTION_installPackageArchived = 25;
        static final int TRANSACTION_openSession = 5;
        static final int TRANSACTION_registerCallback = 10;
        static final int TRANSACTION_reportUnarchivalStatus = 26;
        static final int TRANSACTION_requestArchive = 23;
        static final int TRANSACTION_requestUnarchive = 24;
        static final int TRANSACTION_setAllowUnlimitedSilentUpdates = 19;
        static final int TRANSACTION_setPermissionsResult = 15;
        static final int TRANSACTION_setSilentUpdatesThrottleTime = 20;
        static final int TRANSACTION_uninstall = 12;
        static final int TRANSACTION_uninstallExistingPackage = 13;
        static final int TRANSACTION_unregisterCallback = 11;
        static final int TRANSACTION_updateSessionAppIcon = 2;
        static final int TRANSACTION_updateSessionAppLabel = 3;
        static final int TRANSACTION_waitForInstallConstraints = 22;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.content.pm.IPackageInstaller asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IPackageInstaller)) {
                return (android.content.pm.IPackageInstaller) queryLocalInterface;
            }
            return new android.content.pm.IPackageInstaller.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createSession";
                case 2:
                    return "updateSessionAppIcon";
                case 3:
                    return "updateSessionAppLabel";
                case 4:
                    return "abandonSession";
                case 5:
                    return "openSession";
                case 6:
                    return "getSessionInfo";
                case 7:
                    return "getAllSessions";
                case 8:
                    return "getMySessions";
                case 9:
                    return "getStagedSessions";
                case 10:
                    return "registerCallback";
                case 11:
                    return "unregisterCallback";
                case 12:
                    return "uninstall";
                case 13:
                    return "uninstallExistingPackage";
                case 14:
                    return "installExistingPackage";
                case 15:
                    return "setPermissionsResult";
                case 16:
                    return "bypassNextStagedInstallerCheck";
                case 17:
                    return "bypassNextAllowedApexUpdateCheck";
                case 18:
                    return "disableVerificationForUid";
                case 19:
                    return "setAllowUnlimitedSilentUpdates";
                case 20:
                    return "setSilentUpdatesThrottleTime";
                case 21:
                    return "checkInstallConstraints";
                case 22:
                    return "waitForInstallConstraints";
                case 23:
                    return "requestArchive";
                case 24:
                    return "requestUnarchive";
                case 25:
                    return "installPackageArchived";
                case 26:
                    return "reportUnarchivalStatus";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.content.pm.PackageInstaller.SessionParams sessionParams = (android.content.pm.PackageInstaller.SessionParams) parcel.readTypedObject(android.content.pm.PackageInstaller.SessionParams.CREATOR);
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int createSession = createSession(sessionParams, readString, readString2, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(createSession);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.graphics.Bitmap bitmap = (android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSessionAppIcon(readInt2, bitmap);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateSessionAppLabel(readInt3, readString3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    abandonSession(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.IPackageInstallerSession openSession = openSession(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openSession);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.PackageInstaller.SessionInfo sessionInfo = getSessionInfo(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionInfo, 1);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice allSessions = getAllSessions(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allSessions, 1);
                    return true;
                case 8:
                    java.lang.String readString4 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice mySessions = getMySessions(readString4, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mySessions, 1);
                    return true;
                case 9:
                    android.content.pm.ParceledListSlice stagedSessions = getStagedSessions();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(stagedSessions, 1);
                    return true;
                case 10:
                    android.content.pm.IPackageInstallerCallback asInterface = android.content.pm.IPackageInstallerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.content.pm.IPackageInstallerCallback asInterface2 = android.content.pm.IPackageInstallerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.content.pm.VersionedPackage versionedPackage = (android.content.pm.VersionedPackage) parcel.readTypedObject(android.content.pm.VersionedPackage.CREATOR);
                    java.lang.String readString5 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    uninstall(versionedPackage, readString5, readInt10, intentSender, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.content.pm.VersionedPackage versionedPackage2 = (android.content.pm.VersionedPackage) parcel.readTypedObject(android.content.pm.VersionedPackage.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    android.content.IntentSender intentSender2 = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    uninstallExistingPackage(versionedPackage2, readString6, intentSender2, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    java.lang.String readString7 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    android.content.IntentSender intentSender3 = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    int readInt15 = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    installExistingPackage(readString7, readInt13, readInt14, intentSender3, readInt15, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt16 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPermissionsResult(readInt16, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    bypassNextStagedInstallerCheck(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    bypassNextAllowedApexUpdateCheck(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableVerificationForUid(readInt17);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAllowUnlimitedSilentUpdates(readString8);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setSilentUpdatesThrottleTime(readLong);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    java.lang.String readString9 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    android.content.pm.PackageInstaller.InstallConstraints installConstraints = (android.content.pm.PackageInstaller.InstallConstraints) parcel.readTypedObject(android.content.pm.PackageInstaller.InstallConstraints.CREATOR);
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    checkInstallConstraints(readString9, createStringArrayList2, installConstraints, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    java.lang.String readString10 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList3 = parcel.createStringArrayList();
                    android.content.pm.PackageInstaller.InstallConstraints installConstraints2 = (android.content.pm.PackageInstaller.InstallConstraints) parcel.readTypedObject(android.content.pm.PackageInstaller.InstallConstraints.CREATOR);
                    android.content.IntentSender intentSender4 = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    waitForInstallConstraints(readString10, createStringArrayList3, installConstraints2, intentSender4, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    android.content.IntentSender intentSender5 = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestArchive(readString11, readString12, readInt18, intentSender5, userHandle);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    android.content.IntentSender intentSender6 = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestUnarchive(readString13, readString14, intentSender6, userHandle2);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    android.content.pm.ArchivedPackageParcel archivedPackageParcel = (android.content.pm.ArchivedPackageParcel) parcel.readTypedObject(android.content.pm.ArchivedPackageParcel.CREATOR);
                    android.content.pm.PackageInstaller.SessionParams sessionParams2 = (android.content.pm.PackageInstaller.SessionParams) parcel.readTypedObject(android.content.pm.PackageInstaller.SessionParams.CREATOR);
                    android.content.IntentSender intentSender7 = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    java.lang.String readString15 = parcel.readString();
                    android.os.UserHandle userHandle3 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    installPackageArchived(archivedPackageParcel, sessionParams2, intentSender7, readString15, userHandle3);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    android.os.UserHandle userHandle4 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportUnarchivalStatus(readInt19, readInt20, readLong3, pendingIntent, userHandle4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IPackageInstaller {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IPackageInstaller.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageInstaller
            public int createSession(android.content.pm.PackageInstaller.SessionParams sessionParams, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(sessionParams, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void updateSessionAppIcon(int i, android.graphics.Bitmap bitmap) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void updateSessionAppLabel(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void abandonSession(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public android.content.pm.IPackageInstallerSession openSession(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.content.pm.IPackageInstallerSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public android.content.pm.PackageInstaller.SessionInfo getSessionInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.PackageInstaller.SessionInfo) obtain2.readTypedObject(android.content.pm.PackageInstaller.SessionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public android.content.pm.ParceledListSlice getAllSessions(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public android.content.pm.ParceledListSlice getMySessions(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public android.content.pm.ParceledListSlice getStagedSessions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void registerCallback(android.content.pm.IPackageInstallerCallback iPackageInstallerCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPackageInstallerCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void unregisterCallback(android.content.pm.IPackageInstallerCallback iPackageInstallerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPackageInstallerCallback);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void uninstall(android.content.pm.VersionedPackage versionedPackage, java.lang.String str, int i, android.content.IntentSender intentSender, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(versionedPackage, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void uninstallExistingPackage(android.content.pm.VersionedPackage versionedPackage, java.lang.String str, android.content.IntentSender intentSender, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(versionedPackage, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void installExistingPackage(java.lang.String str, int i, int i2, android.content.IntentSender intentSender, int i3, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeInt(i3);
                    obtain.writeStringList(list);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setPermissionsResult(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void bypassNextStagedInstallerCheck(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void bypassNextAllowedApexUpdateCheck(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void disableVerificationForUid(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setAllowUnlimitedSilentUpdates(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setSilentUpdatesThrottleTime(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void checkInstallConstraints(java.lang.String str, java.util.List<java.lang.String> list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(installConstraints, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void waitForInstallConstraints(java.lang.String str, java.util.List<java.lang.String> list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, android.content.IntentSender intentSender, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(installConstraints, 0);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void requestArchive(java.lang.String str, java.lang.String str2, int i, android.content.IntentSender intentSender, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void requestUnarchive(java.lang.String str, java.lang.String str2, android.content.IntentSender intentSender, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void installPackageArchived(android.content.pm.ArchivedPackageParcel archivedPackageParcel, android.content.pm.PackageInstaller.SessionParams sessionParams, android.content.IntentSender intentSender, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(archivedPackageParcel, 0);
                    obtain.writeTypedObject(sessionParams, 0);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void reportUnarchivalStatus(int i, int i2, long j, android.app.PendingIntent pendingIntent, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstaller.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setPermissionsResult_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.INSTALL_PACKAGES, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 25;
        }
    }
}
