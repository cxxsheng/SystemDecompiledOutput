package android.content.pm;

/* loaded from: classes.dex */
public interface IPackageInstallerSession extends android.os.IInterface {
    void abandon() throws android.os.RemoteException;

    void addChildSessionId(int i) throws android.os.RemoteException;

    void addClientProgress(float f) throws android.os.RemoteException;

    void addFile(int i, java.lang.String str, long j, byte[] bArr, byte[] bArr2) throws android.os.RemoteException;

    void close() throws android.os.RemoteException;

    void commit(android.content.IntentSender intentSender, boolean z) throws android.os.RemoteException;

    java.util.List<java.lang.String> fetchPackageNames() throws android.os.RemoteException;

    android.os.ParcelFileDescriptor getAppMetadataFd() throws android.os.RemoteException;

    int[] getChildSessionIds() throws android.os.RemoteException;

    android.content.pm.DataLoaderParamsParcel getDataLoaderParams() throws android.os.RemoteException;

    int getInstallFlags() throws android.os.RemoteException;

    java.lang.String[] getNames() throws android.os.RemoteException;

    int getParentSessionId() throws android.os.RemoteException;

    android.content.pm.verify.domain.DomainSet getPreVerifiedDomains() throws android.os.RemoteException;

    boolean isApplicationEnabledSettingPersistent() throws android.os.RemoteException;

    boolean isMultiPackage() throws android.os.RemoteException;

    boolean isRequestUpdateOwnership() throws android.os.RemoteException;

    boolean isStaged() throws android.os.RemoteException;

    android.os.ParcelFileDescriptor openRead(java.lang.String str) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor openWrite(java.lang.String str, long j, long j2) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor openWriteAppMetadata() throws android.os.RemoteException;

    void removeAppMetadata() throws android.os.RemoteException;

    void removeChildSessionId(int i) throws android.os.RemoteException;

    void removeFile(int i, java.lang.String str) throws android.os.RemoteException;

    void removeSplit(java.lang.String str) throws android.os.RemoteException;

    void requestChecksums(java.lang.String str, int i, int i2, java.util.List list, android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener) throws android.os.RemoteException;

    void requestUserPreapproval(android.content.pm.PackageInstaller.PreapprovalDetails preapprovalDetails, android.content.IntentSender intentSender) throws android.os.RemoteException;

    void seal() throws android.os.RemoteException;

    void setChecksums(java.lang.String str, android.content.pm.Checksum[] checksumArr, byte[] bArr) throws android.os.RemoteException;

    void setClientProgress(float f) throws android.os.RemoteException;

    void setPreVerifiedDomains(android.content.pm.verify.domain.DomainSet domainSet) throws android.os.RemoteException;

    void stageViaHardLink(java.lang.String str) throws android.os.RemoteException;

    void transfer(java.lang.String str) throws android.os.RemoteException;

    void write(java.lang.String str, long j, long j2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IPackageInstallerSession {
        @Override // android.content.pm.IPackageInstallerSession
        public void setClientProgress(float f) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void addClientProgress(float f) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public java.lang.String[] getNames() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public android.os.ParcelFileDescriptor openWrite(java.lang.String str, long j, long j2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public android.os.ParcelFileDescriptor openRead(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void write(java.lang.String str, long j, long j2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void stageViaHardLink(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void setChecksums(java.lang.String str, android.content.pm.Checksum[] checksumArr, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void requestChecksums(java.lang.String str, int i, int i2, java.util.List list, android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void removeSplit(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void close() throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void commit(android.content.IntentSender intentSender, boolean z) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void transfer(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void abandon() throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void seal() throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public java.util.List<java.lang.String> fetchPackageNames() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public android.content.pm.DataLoaderParamsParcel getDataLoaderParams() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void addFile(int i, java.lang.String str, long j, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void removeFile(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public boolean isMultiPackage() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public int[] getChildSessionIds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void addChildSessionId(int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void removeChildSessionId(int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public int getParentSessionId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public boolean isStaged() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public int getInstallFlags() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void requestUserPreapproval(android.content.pm.PackageInstaller.PreapprovalDetails preapprovalDetails, android.content.IntentSender intentSender) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public boolean isApplicationEnabledSettingPersistent() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public boolean isRequestUpdateOwnership() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public android.os.ParcelFileDescriptor getAppMetadataFd() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public android.os.ParcelFileDescriptor openWriteAppMetadata() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void removeAppMetadata() throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void setPreVerifiedDomains(android.content.pm.verify.domain.DomainSet domainSet) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public android.content.pm.verify.domain.DomainSet getPreVerifiedDomains() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IPackageInstallerSession {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.IPackageInstallerSession";
        static final int TRANSACTION_abandon = 14;
        static final int TRANSACTION_addChildSessionId = 22;
        static final int TRANSACTION_addClientProgress = 2;
        static final int TRANSACTION_addFile = 18;
        static final int TRANSACTION_close = 11;
        static final int TRANSACTION_commit = 12;
        static final int TRANSACTION_fetchPackageNames = 16;
        static final int TRANSACTION_getAppMetadataFd = 30;
        static final int TRANSACTION_getChildSessionIds = 21;
        static final int TRANSACTION_getDataLoaderParams = 17;
        static final int TRANSACTION_getInstallFlags = 26;
        static final int TRANSACTION_getNames = 3;
        static final int TRANSACTION_getParentSessionId = 24;
        static final int TRANSACTION_getPreVerifiedDomains = 34;
        static final int TRANSACTION_isApplicationEnabledSettingPersistent = 28;
        static final int TRANSACTION_isMultiPackage = 20;
        static final int TRANSACTION_isRequestUpdateOwnership = 29;
        static final int TRANSACTION_isStaged = 25;
        static final int TRANSACTION_openRead = 5;
        static final int TRANSACTION_openWrite = 4;
        static final int TRANSACTION_openWriteAppMetadata = 31;
        static final int TRANSACTION_removeAppMetadata = 32;
        static final int TRANSACTION_removeChildSessionId = 23;
        static final int TRANSACTION_removeFile = 19;
        static final int TRANSACTION_removeSplit = 10;
        static final int TRANSACTION_requestChecksums = 9;
        static final int TRANSACTION_requestUserPreapproval = 27;
        static final int TRANSACTION_seal = 15;
        static final int TRANSACTION_setChecksums = 8;
        static final int TRANSACTION_setClientProgress = 1;
        static final int TRANSACTION_setPreVerifiedDomains = 33;
        static final int TRANSACTION_stageViaHardLink = 7;
        static final int TRANSACTION_transfer = 13;
        static final int TRANSACTION_write = 6;
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

        public static android.content.pm.IPackageInstallerSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IPackageInstallerSession)) {
                return (android.content.pm.IPackageInstallerSession) queryLocalInterface;
            }
            return new android.content.pm.IPackageInstallerSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setClientProgress";
                case 2:
                    return "addClientProgress";
                case 3:
                    return "getNames";
                case 4:
                    return "openWrite";
                case 5:
                    return "openRead";
                case 6:
                    return "write";
                case 7:
                    return "stageViaHardLink";
                case 8:
                    return "setChecksums";
                case 9:
                    return "requestChecksums";
                case 10:
                    return "removeSplit";
                case 11:
                    return "close";
                case 12:
                    return "commit";
                case 13:
                    return "transfer";
                case 14:
                    return "abandon";
                case 15:
                    return "seal";
                case 16:
                    return "fetchPackageNames";
                case 17:
                    return "getDataLoaderParams";
                case 18:
                    return "addFile";
                case 19:
                    return "removeFile";
                case 20:
                    return "isMultiPackage";
                case 21:
                    return "getChildSessionIds";
                case 22:
                    return "addChildSessionId";
                case 23:
                    return "removeChildSessionId";
                case 24:
                    return "getParentSessionId";
                case 25:
                    return "isStaged";
                case 26:
                    return "getInstallFlags";
                case 27:
                    return "requestUserPreapproval";
                case 28:
                    return "isApplicationEnabledSettingPersistent";
                case 29:
                    return "isRequestUpdateOwnership";
                case 30:
                    return "getAppMetadataFd";
                case 31:
                    return "openWriteAppMetadata";
                case 32:
                    return "removeAppMetadata";
                case 33:
                    return "setPreVerifiedDomains";
                case 34:
                    return "getPreVerifiedDomains";
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
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setClientProgress(readFloat);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    addClientProgress(readFloat2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String[] names = getNames();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(names);
                    return true;
                case 4:
                    java.lang.String readString = parcel.readString();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor openWrite = openWrite(readString, readLong, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openWrite, 1);
                    return true;
                case 5:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor openRead = openRead(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openRead, 1);
                    return true;
                case 6:
                    java.lang.String readString3 = parcel.readString();
                    long readLong3 = parcel.readLong();
                    long readLong4 = parcel.readLong();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    write(readString3, readLong3, readLong4, parcelFileDescriptor);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stageViaHardLink(readString4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    java.lang.String readString5 = parcel.readString();
                    android.content.pm.Checksum[] checksumArr = (android.content.pm.Checksum[]) parcel.createTypedArray(android.content.pm.Checksum.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setChecksums(readString5, checksumArr, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString6 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    java.util.ArrayList readArrayList = parcel.readArrayList(getClass().getClassLoader());
                    android.content.pm.IOnChecksumsReadyListener asInterface = android.content.pm.IOnChecksumsReadyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestChecksums(readString6, readInt, readInt2, readArrayList, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSplit(readString7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    commit(intentSender, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    transfer(readString8);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    abandon();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    seal();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.util.List<java.lang.String> fetchPackageNames = fetchPackageNames();
                    parcel2.writeNoException();
                    parcel2.writeStringList(fetchPackageNames);
                    return true;
                case 17:
                    android.content.pm.DataLoaderParamsParcel dataLoaderParams = getDataLoaderParams();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dataLoaderParams, 1);
                    return true;
                case 18:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    long readLong5 = parcel.readLong();
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    addFile(readInt3, readString9, readLong5, createByteArray2, createByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt4 = parcel.readInt();
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeFile(readInt4, readString10);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    boolean isMultiPackage = isMultiPackage();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMultiPackage);
                    return true;
                case 21:
                    int[] childSessionIds = getChildSessionIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(childSessionIds);
                    return true;
                case 22:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addChildSessionId(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeChildSessionId(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int parentSessionId = getParentSessionId();
                    parcel2.writeNoException();
                    parcel2.writeInt(parentSessionId);
                    return true;
                case 25:
                    boolean isStaged = isStaged();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStaged);
                    return true;
                case 26:
                    int installFlags = getInstallFlags();
                    parcel2.writeNoException();
                    parcel2.writeInt(installFlags);
                    return true;
                case 27:
                    android.content.pm.PackageInstaller.PreapprovalDetails preapprovalDetails = (android.content.pm.PackageInstaller.PreapprovalDetails) parcel.readTypedObject(android.content.pm.PackageInstaller.PreapprovalDetails.CREATOR);
                    android.content.IntentSender intentSender2 = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestUserPreapproval(preapprovalDetails, intentSender2);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    boolean isApplicationEnabledSettingPersistent = isApplicationEnabledSettingPersistent();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationEnabledSettingPersistent);
                    return true;
                case 29:
                    boolean isRequestUpdateOwnership = isRequestUpdateOwnership();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRequestUpdateOwnership);
                    return true;
                case 30:
                    android.os.ParcelFileDescriptor appMetadataFd = getAppMetadataFd();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appMetadataFd, 1);
                    return true;
                case 31:
                    android.os.ParcelFileDescriptor openWriteAppMetadata = openWriteAppMetadata();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openWriteAppMetadata, 1);
                    return true;
                case 32:
                    removeAppMetadata();
                    parcel2.writeNoException();
                    return true;
                case 33:
                    android.content.pm.verify.domain.DomainSet domainSet = (android.content.pm.verify.domain.DomainSet) parcel.readTypedObject(android.content.pm.verify.domain.DomainSet.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreVerifiedDomains(domainSet);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    android.content.pm.verify.domain.DomainSet preVerifiedDomains = getPreVerifiedDomains();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(preVerifiedDomains, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IPackageInstallerSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void setClientProgress(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void addClientProgress(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public java.lang.String[] getNames() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public android.os.ParcelFileDescriptor openWrite(java.lang.String str, long j, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public android.os.ParcelFileDescriptor openRead(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void write(java.lang.String str, long j, long j2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void stageViaHardLink(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void setChecksums(java.lang.String str, android.content.pm.Checksum[] checksumArr, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedArray(checksumArr, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void requestChecksums(java.lang.String str, int i, int i2, java.util.List list, android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeList(list);
                    obtain.writeStrongInterface(iOnChecksumsReadyListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void removeSplit(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void commit(android.content.IntentSender intentSender, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void transfer(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void abandon() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void seal() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public java.util.List<java.lang.String> fetchPackageNames() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public android.content.pm.DataLoaderParamsParcel getDataLoaderParams() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.DataLoaderParamsParcel) obtain2.readTypedObject(android.content.pm.DataLoaderParamsParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void addFile(int i, java.lang.String str, long j, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void removeFile(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public boolean isMultiPackage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public int[] getChildSessionIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void addChildSessionId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void removeChildSessionId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public int getParentSessionId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public boolean isStaged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public int getInstallFlags() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void requestUserPreapproval(android.content.pm.PackageInstaller.PreapprovalDetails preapprovalDetails, android.content.IntentSender intentSender) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(preapprovalDetails, 0);
                    obtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public boolean isApplicationEnabledSettingPersistent() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public boolean isRequestUpdateOwnership() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public android.os.ParcelFileDescriptor getAppMetadataFd() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public android.os.ParcelFileDescriptor openWriteAppMetadata() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void removeAppMetadata() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void setPreVerifiedDomains(android.content.pm.verify.domain.DomainSet domainSet) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(domainSet, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public android.content.pm.verify.domain.DomainSet getPreVerifiedDomains() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.verify.domain.DomainSet) obtain2.readTypedObject(android.content.pm.verify.domain.DomainSet.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getDataLoaderParams_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_INSTALLER_V2, getCallingPid(), getCallingUid());
        }

        protected void addFile_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_INSTALLER_V2, getCallingPid(), getCallingUid());
        }

        protected void removeFile_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_INSTALLER_V2, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 33;
        }
    }
}
