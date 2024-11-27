package android.apex;

/* loaded from: classes.dex */
public interface IApexService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.apex.IApexService";

    void abortStagedSession(int i) throws android.os.RemoteException;

    long calculateSizeForCompressedApex(android.apex.CompressedApexInfoList compressedApexInfoList) throws android.os.RemoteException;

    void destroyCeSnapshots(int i, int i2) throws android.os.RemoteException;

    void destroyCeSnapshotsNotSpecified(int i, int[] iArr) throws android.os.RemoteException;

    void destroyDeSnapshots(int i) throws android.os.RemoteException;

    android.apex.ApexInfo getActivePackage(java.lang.String str) throws android.os.RemoteException;

    android.apex.ApexInfo[] getActivePackages() throws android.os.RemoteException;

    android.apex.ApexInfo[] getAllPackages() throws android.os.RemoteException;

    android.apex.ApexSessionInfo[] getSessions() throws android.os.RemoteException;

    android.apex.ApexInfo[] getStagedApexInfos(android.apex.ApexSessionParams apexSessionParams) throws android.os.RemoteException;

    android.apex.ApexSessionInfo getStagedSessionInfo(int i) throws android.os.RemoteException;

    android.apex.ApexInfo installAndActivatePackage(java.lang.String str, boolean z) throws android.os.RemoteException;

    void markBootCompleted() throws android.os.RemoteException;

    void markStagedSessionReady(int i) throws android.os.RemoteException;

    void markStagedSessionSuccessful(int i) throws android.os.RemoteException;

    void recollectDataApex(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void recollectPreinstalledData(java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void remountPackages() throws android.os.RemoteException;

    void reserveSpaceForCompressedApex(android.apex.CompressedApexInfoList compressedApexInfoList) throws android.os.RemoteException;

    void restoreCeData(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    void resumeRevertIfNeeded() throws android.os.RemoteException;

    void revertActiveSessions() throws android.os.RemoteException;

    void snapshotCeData(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    void stagePackages(java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void submitStagedSession(android.apex.ApexSessionParams apexSessionParams, android.apex.ApexInfoList apexInfoList) throws android.os.RemoteException;

    void unstagePackages(java.util.List<java.lang.String> list) throws android.os.RemoteException;

    public static class Default implements android.apex.IApexService {
        @Override // android.apex.IApexService
        public void submitStagedSession(android.apex.ApexSessionParams apexSessionParams, android.apex.ApexInfoList apexInfoList) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void markStagedSessionReady(int i) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void markStagedSessionSuccessful(int i) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public android.apex.ApexSessionInfo[] getSessions() throws android.os.RemoteException {
            return null;
        }

        @Override // android.apex.IApexService
        public android.apex.ApexSessionInfo getStagedSessionInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.apex.IApexService
        public android.apex.ApexInfo[] getStagedApexInfos(android.apex.ApexSessionParams apexSessionParams) throws android.os.RemoteException {
            return null;
        }

        @Override // android.apex.IApexService
        public android.apex.ApexInfo[] getActivePackages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.apex.IApexService
        public android.apex.ApexInfo[] getAllPackages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.apex.IApexService
        public void abortStagedSession(int i) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void revertActiveSessions() throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void snapshotCeData(int i, int i2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void restoreCeData(int i, int i2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void destroyDeSnapshots(int i) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void destroyCeSnapshots(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void destroyCeSnapshotsNotSpecified(int i, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void unstagePackages(java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public android.apex.ApexInfo getActivePackage(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.apex.IApexService
        public void stagePackages(java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void resumeRevertIfNeeded() throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void remountPackages() throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void recollectPreinstalledData(java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void recollectDataApex(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public void markBootCompleted() throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public long calculateSizeForCompressedApex(android.apex.CompressedApexInfoList compressedApexInfoList) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.apex.IApexService
        public void reserveSpaceForCompressedApex(android.apex.CompressedApexInfoList compressedApexInfoList) throws android.os.RemoteException {
        }

        @Override // android.apex.IApexService
        public android.apex.ApexInfo installAndActivatePackage(java.lang.String str, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.apex.IApexService {
        static final int TRANSACTION_abortStagedSession = 9;
        static final int TRANSACTION_calculateSizeForCompressedApex = 24;
        static final int TRANSACTION_destroyCeSnapshots = 14;
        static final int TRANSACTION_destroyCeSnapshotsNotSpecified = 15;
        static final int TRANSACTION_destroyDeSnapshots = 13;
        static final int TRANSACTION_getActivePackage = 17;
        static final int TRANSACTION_getActivePackages = 7;
        static final int TRANSACTION_getAllPackages = 8;
        static final int TRANSACTION_getSessions = 4;
        static final int TRANSACTION_getStagedApexInfos = 6;
        static final int TRANSACTION_getStagedSessionInfo = 5;
        static final int TRANSACTION_installAndActivatePackage = 26;
        static final int TRANSACTION_markBootCompleted = 23;
        static final int TRANSACTION_markStagedSessionReady = 2;
        static final int TRANSACTION_markStagedSessionSuccessful = 3;
        static final int TRANSACTION_recollectDataApex = 22;
        static final int TRANSACTION_recollectPreinstalledData = 21;
        static final int TRANSACTION_remountPackages = 20;
        static final int TRANSACTION_reserveSpaceForCompressedApex = 25;
        static final int TRANSACTION_restoreCeData = 12;
        static final int TRANSACTION_resumeRevertIfNeeded = 19;
        static final int TRANSACTION_revertActiveSessions = 10;
        static final int TRANSACTION_snapshotCeData = 11;
        static final int TRANSACTION_stagePackages = 18;
        static final int TRANSACTION_submitStagedSession = 1;
        static final int TRANSACTION_unstagePackages = 16;

        public Stub() {
            attachInterface(this, android.apex.IApexService.DESCRIPTOR);
        }

        public static android.apex.IApexService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.apex.IApexService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.apex.IApexService)) {
                return (android.apex.IApexService) queryLocalInterface;
            }
            return new android.apex.IApexService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.apex.IApexService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.apex.IApexService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.apex.ApexSessionParams apexSessionParams = (android.apex.ApexSessionParams) parcel.readTypedObject(android.apex.ApexSessionParams.CREATOR);
                    android.apex.ApexInfoList apexInfoList = new android.apex.ApexInfoList();
                    submitStagedSession(apexSessionParams, apexInfoList);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(apexInfoList, 1);
                    return true;
                case 2:
                    markStagedSessionReady(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 3:
                    markStagedSessionSuccessful(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.apex.ApexSessionInfo[] sessions = getSessions();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(sessions, 1);
                    return true;
                case 5:
                    android.apex.ApexSessionInfo stagedSessionInfo = getStagedSessionInfo(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(stagedSessionInfo, 1);
                    return true;
                case 6:
                    android.apex.ApexInfo[] stagedApexInfos = getStagedApexInfos((android.apex.ApexSessionParams) parcel.readTypedObject(android.apex.ApexSessionParams.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(stagedApexInfos, 1);
                    return true;
                case 7:
                    android.apex.ApexInfo[] activePackages = getActivePackages();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(activePackages, 1);
                    return true;
                case 8:
                    android.apex.ApexInfo[] allPackages = getAllPackages();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allPackages, 1);
                    return true;
                case 9:
                    abortStagedSession(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 10:
                    revertActiveSessions();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    snapshotCeData(parcel.readInt(), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 12:
                    restoreCeData(parcel.readInt(), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 13:
                    destroyDeSnapshots(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 14:
                    destroyCeSnapshots(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 15:
                    destroyCeSnapshotsNotSpecified(parcel.readInt(), parcel.createIntArray());
                    parcel2.writeNoException();
                    return true;
                case 16:
                    unstagePackages(parcel.createStringArrayList());
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.apex.ApexInfo activePackage = getActivePackage(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activePackage, 1);
                    return true;
                case 18:
                    stagePackages(parcel.createStringArrayList());
                    parcel2.writeNoException();
                    return true;
                case 19:
                    resumeRevertIfNeeded();
                    parcel2.writeNoException();
                    return true;
                case 20:
                    remountPackages();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    recollectPreinstalledData(parcel.createStringArrayList());
                    parcel2.writeNoException();
                    return true;
                case 22:
                    recollectDataApex(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 23:
                    markBootCompleted();
                    parcel2.writeNoException();
                    return true;
                case 24:
                    long calculateSizeForCompressedApex = calculateSizeForCompressedApex((android.apex.CompressedApexInfoList) parcel.readTypedObject(android.apex.CompressedApexInfoList.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeLong(calculateSizeForCompressedApex);
                    return true;
                case 25:
                    reserveSpaceForCompressedApex((android.apex.CompressedApexInfoList) parcel.readTypedObject(android.apex.CompressedApexInfoList.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 26:
                    android.apex.ApexInfo installAndActivatePackage = installAndActivatePackage(parcel.readString(), parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installAndActivatePackage, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.apex.IApexService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.apex.IApexService.DESCRIPTOR;
            }

            @Override // android.apex.IApexService
            public void submitStagedSession(android.apex.ApexSessionParams apexSessionParams, android.apex.ApexInfoList apexInfoList) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeTypedObject(apexSessionParams, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        apexInfoList.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void markStagedSessionReady(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void markStagedSessionSuccessful(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public android.apex.ApexSessionInfo[] getSessions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.apex.ApexSessionInfo[]) obtain2.createTypedArray(android.apex.ApexSessionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public android.apex.ApexSessionInfo getStagedSessionInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.apex.ApexSessionInfo) obtain2.readTypedObject(android.apex.ApexSessionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public android.apex.ApexInfo[] getStagedApexInfos(android.apex.ApexSessionParams apexSessionParams) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeTypedObject(apexSessionParams, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.apex.ApexInfo[]) obtain2.createTypedArray(android.apex.ApexInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public android.apex.ApexInfo[] getActivePackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.apex.ApexInfo[]) obtain2.createTypedArray(android.apex.ApexInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public android.apex.ApexInfo[] getAllPackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.apex.ApexInfo[]) obtain2.createTypedArray(android.apex.ApexInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void abortStagedSession(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void revertActiveSessions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void snapshotCeData(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void restoreCeData(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void destroyDeSnapshots(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void destroyCeSnapshots(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void destroyCeSnapshotsNotSpecified(int i, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void unstagePackages(java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public android.apex.ApexInfo getActivePackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.apex.ApexInfo) obtain2.readTypedObject(android.apex.ApexInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void stagePackages(java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void resumeRevertIfNeeded() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void remountPackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void recollectPreinstalledData(java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void recollectDataApex(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void markBootCompleted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public long calculateSizeForCompressedApex(android.apex.CompressedApexInfoList compressedApexInfoList) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeTypedObject(compressedApexInfoList, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void reserveSpaceForCompressedApex(android.apex.CompressedApexInfoList compressedApexInfoList) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeTypedObject(compressedApexInfoList, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public android.apex.ApexInfo installAndActivatePackage(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apex.IApexService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.apex.ApexInfo) obtain2.readTypedObject(android.apex.ApexInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
