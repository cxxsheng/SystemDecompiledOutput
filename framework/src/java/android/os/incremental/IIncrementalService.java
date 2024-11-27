package android.os.incremental;

/* loaded from: classes3.dex */
public interface IIncrementalService extends android.os.IInterface {
    public static final int BIND_PERMANENT = 1;
    public static final int BIND_TEMPORARY = 0;
    public static final int CREATE_MODE_CREATE = 4;
    public static final int CREATE_MODE_OPEN_EXISTING = 8;
    public static final int CREATE_MODE_PERMANENT_BIND = 2;
    public static final int CREATE_MODE_TEMPORARY_BIND = 1;
    public static final java.lang.String DESCRIPTOR = "android.os.incremental.IIncrementalService";
    public static final java.lang.String METRICS_DATA_LOADER_BIND_DELAY_MILLIS = "dataLoaderBindDelayMillis";
    public static final java.lang.String METRICS_DATA_LOADER_STATUS_CODE = "dataLoaderStatusCode";
    public static final java.lang.String METRICS_LAST_READ_ERROR_NUMBER = "lastReadErrorNo";
    public static final java.lang.String METRICS_LAST_READ_ERROR_UID = "lastReadErrorUid";
    public static final java.lang.String METRICS_MILLIS_SINCE_LAST_DATA_LOADER_BIND = "millisSinceLastDataLoaderBind";
    public static final java.lang.String METRICS_MILLIS_SINCE_LAST_READ_ERROR = "millisSinceLastReadError";
    public static final java.lang.String METRICS_MILLIS_SINCE_OLDEST_PENDING_READ = "millisSinceOldestPendingRead";
    public static final java.lang.String METRICS_READ_LOGS_ENABLED = "readLogsEnabled";
    public static final java.lang.String METRICS_STORAGE_HEALTH_STATUS_CODE = "storageHealthStatusCode";
    public static final java.lang.String METRICS_TOTAL_DELAYED_READS = "totalDelayedReads";
    public static final java.lang.String METRICS_TOTAL_DELAYED_READS_MILLIS = "totalDelayedReadsMillis";
    public static final java.lang.String METRICS_TOTAL_FAILED_READS = "totalFailedReads";

    boolean configureNativeBinaries(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) throws android.os.RemoteException;

    int createLinkedStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    int createStorage(java.lang.String str, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, int i) throws android.os.RemoteException;

    int deleteBindMount(int i, java.lang.String str) throws android.os.RemoteException;

    void deleteStorage(int i) throws android.os.RemoteException;

    void disallowReadLogs(int i) throws android.os.RemoteException;

    float getLoadingProgress(int i) throws android.os.RemoteException;

    byte[] getMetadataById(int i, byte[] bArr) throws android.os.RemoteException;

    byte[] getMetadataByPath(int i, java.lang.String str) throws android.os.RemoteException;

    android.os.PersistableBundle getMetrics(int i) throws android.os.RemoteException;

    int isFileFullyLoaded(int i, java.lang.String str) throws android.os.RemoteException;

    int isFullyLoaded(int i) throws android.os.RemoteException;

    int makeBindMount(int i, java.lang.String str, java.lang.String str2, int i2) throws android.os.RemoteException;

    int makeDirectories(int i, java.lang.String str) throws android.os.RemoteException;

    int makeDirectory(int i, java.lang.String str) throws android.os.RemoteException;

    int makeFile(int i, java.lang.String str, int i2, android.os.incremental.IncrementalNewFileParams incrementalNewFileParams, byte[] bArr) throws android.os.RemoteException;

    int makeFileFromRange(int i, java.lang.String str, java.lang.String str2, long j, long j2) throws android.os.RemoteException;

    int makeLink(int i, java.lang.String str, int i2, java.lang.String str2) throws android.os.RemoteException;

    void onInstallationComplete(int i) throws android.os.RemoteException;

    int openStorage(java.lang.String str) throws android.os.RemoteException;

    boolean registerLoadingProgressListener(int i, android.os.incremental.IStorageLoadingProgressListener iStorageLoadingProgressListener) throws android.os.RemoteException;

    boolean startLoading(int i, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener, android.os.incremental.StorageHealthCheckParams storageHealthCheckParams, android.os.incremental.IStorageHealthListener iStorageHealthListener, android.os.incremental.PerUidReadTimeouts[] perUidReadTimeoutsArr) throws android.os.RemoteException;

    int unlink(int i, java.lang.String str) throws android.os.RemoteException;

    boolean unregisterLoadingProgressListener(int i) throws android.os.RemoteException;

    boolean waitForNativeBinariesExtraction(int i) throws android.os.RemoteException;

    public static class Default implements android.os.incremental.IIncrementalService {
        @Override // android.os.incremental.IIncrementalService
        public int openStorage(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int createStorage(java.lang.String str, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int createLinkedStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public boolean startLoading(int i, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener, android.os.incremental.StorageHealthCheckParams storageHealthCheckParams, android.os.incremental.IStorageHealthListener iStorageHealthListener, android.os.incremental.PerUidReadTimeouts[] perUidReadTimeoutsArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.incremental.IIncrementalService
        public void onInstallationComplete(int i) throws android.os.RemoteException {
        }

        @Override // android.os.incremental.IIncrementalService
        public int makeBindMount(int i, java.lang.String str, java.lang.String str2, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int deleteBindMount(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int makeDirectory(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int makeDirectories(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int makeFile(int i, java.lang.String str, int i2, android.os.incremental.IncrementalNewFileParams incrementalNewFileParams, byte[] bArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int makeFileFromRange(int i, java.lang.String str, java.lang.String str2, long j, long j2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int makeLink(int i, java.lang.String str, int i2, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int unlink(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int isFileFullyLoaded(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int isFullyLoaded(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public float getLoadingProgress(int i) throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.os.incremental.IIncrementalService
        public byte[] getMetadataByPath(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.incremental.IIncrementalService
        public byte[] getMetadataById(int i, byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.incremental.IIncrementalService
        public void deleteStorage(int i) throws android.os.RemoteException {
        }

        @Override // android.os.incremental.IIncrementalService
        public void disallowReadLogs(int i) throws android.os.RemoteException {
        }

        @Override // android.os.incremental.IIncrementalService
        public boolean configureNativeBinaries(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.incremental.IIncrementalService
        public boolean waitForNativeBinariesExtraction(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.incremental.IIncrementalService
        public boolean registerLoadingProgressListener(int i, android.os.incremental.IStorageLoadingProgressListener iStorageLoadingProgressListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.incremental.IIncrementalService
        public boolean unregisterLoadingProgressListener(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.incremental.IIncrementalService
        public android.os.PersistableBundle getMetrics(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.incremental.IIncrementalService {
        static final int TRANSACTION_configureNativeBinaries = 21;
        static final int TRANSACTION_createLinkedStorage = 3;
        static final int TRANSACTION_createStorage = 2;
        static final int TRANSACTION_deleteBindMount = 7;
        static final int TRANSACTION_deleteStorage = 19;
        static final int TRANSACTION_disallowReadLogs = 20;
        static final int TRANSACTION_getLoadingProgress = 16;
        static final int TRANSACTION_getMetadataById = 18;
        static final int TRANSACTION_getMetadataByPath = 17;
        static final int TRANSACTION_getMetrics = 25;
        static final int TRANSACTION_isFileFullyLoaded = 14;
        static final int TRANSACTION_isFullyLoaded = 15;
        static final int TRANSACTION_makeBindMount = 6;
        static final int TRANSACTION_makeDirectories = 9;
        static final int TRANSACTION_makeDirectory = 8;
        static final int TRANSACTION_makeFile = 10;
        static final int TRANSACTION_makeFileFromRange = 11;
        static final int TRANSACTION_makeLink = 12;
        static final int TRANSACTION_onInstallationComplete = 5;
        static final int TRANSACTION_openStorage = 1;
        static final int TRANSACTION_registerLoadingProgressListener = 23;
        static final int TRANSACTION_startLoading = 4;
        static final int TRANSACTION_unlink = 13;
        static final int TRANSACTION_unregisterLoadingProgressListener = 24;
        static final int TRANSACTION_waitForNativeBinariesExtraction = 22;

        public Stub() {
            attachInterface(this, android.os.incremental.IIncrementalService.DESCRIPTOR);
        }

        public static android.os.incremental.IIncrementalService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.incremental.IIncrementalService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.incremental.IIncrementalService)) {
                return (android.os.incremental.IIncrementalService) queryLocalInterface;
            }
            return new android.os.incremental.IIncrementalService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "openStorage";
                case 2:
                    return "createStorage";
                case 3:
                    return "createLinkedStorage";
                case 4:
                    return "startLoading";
                case 5:
                    return "onInstallationComplete";
                case 6:
                    return "makeBindMount";
                case 7:
                    return "deleteBindMount";
                case 8:
                    return "makeDirectory";
                case 9:
                    return "makeDirectories";
                case 10:
                    return "makeFile";
                case 11:
                    return "makeFileFromRange";
                case 12:
                    return "makeLink";
                case 13:
                    return "unlink";
                case 14:
                    return "isFileFullyLoaded";
                case 15:
                    return "isFullyLoaded";
                case 16:
                    return "getLoadingProgress";
                case 17:
                    return "getMetadataByPath";
                case 18:
                    return "getMetadataById";
                case 19:
                    return "deleteStorage";
                case 20:
                    return "disallowReadLogs";
                case 21:
                    return "configureNativeBinaries";
                case 22:
                    return "waitForNativeBinariesExtraction";
                case 23:
                    return "registerLoadingProgressListener";
                case 24:
                    return "unregisterLoadingProgressListener";
                case 25:
                    return "getMetrics";
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
                parcel.enforceInterface(android.os.incremental.IIncrementalService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.incremental.IIncrementalService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int openStorage = openStorage(readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(openStorage);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel = (android.content.pm.DataLoaderParamsParcel) parcel.readTypedObject(android.content.pm.DataLoaderParamsParcel.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int createStorage = createStorage(readString2, dataLoaderParamsParcel, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(createStorage);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int createLinkedStorage = createLinkedStorage(readString3, readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(createLinkedStorage);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel2 = (android.content.pm.DataLoaderParamsParcel) parcel.readTypedObject(android.content.pm.DataLoaderParamsParcel.CREATOR);
                    android.content.pm.IDataLoaderStatusListener asInterface = android.content.pm.IDataLoaderStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    android.os.incremental.StorageHealthCheckParams storageHealthCheckParams = (android.os.incremental.StorageHealthCheckParams) parcel.readTypedObject(android.os.incremental.StorageHealthCheckParams.CREATOR);
                    android.os.incremental.IStorageHealthListener asInterface2 = android.os.incremental.IStorageHealthListener.Stub.asInterface(parcel.readStrongBinder());
                    android.os.incremental.PerUidReadTimeouts[] perUidReadTimeoutsArr = (android.os.incremental.PerUidReadTimeouts[]) parcel.createTypedArray(android.os.incremental.PerUidReadTimeouts.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean startLoading = startLoading(readInt4, dataLoaderParamsParcel2, asInterface, storageHealthCheckParams, asInterface2, perUidReadTimeoutsArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startLoading);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onInstallationComplete(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int makeBindMount = makeBindMount(readInt6, readString4, readString5, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeBindMount);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int deleteBindMount = deleteBindMount(readInt8, readString6);
                    parcel2.writeNoException();
                    parcel2.writeInt(deleteBindMount);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int makeDirectory = makeDirectory(readInt9, readString7);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeDirectory);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int makeDirectories = makeDirectories(readInt10, readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeDirectories);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    android.os.incremental.IncrementalNewFileParams incrementalNewFileParams = (android.os.incremental.IncrementalNewFileParams) parcel.readTypedObject(android.os.incremental.IncrementalNewFileParams.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int makeFile = makeFile(readInt11, readString9, readInt12, incrementalNewFileParams, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeFile);
                    return true;
                case 11:
                    int readInt13 = parcel.readInt();
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int makeFileFromRange = makeFileFromRange(readInt13, readString10, readString11, readLong, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeFileFromRange);
                    return true;
                case 12:
                    int readInt14 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int makeLink = makeLink(readInt14, readString12, readInt15, readString13);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeLink);
                    return true;
                case 13:
                    int readInt16 = parcel.readInt();
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int unlink = unlink(readInt16, readString14);
                    parcel2.writeNoException();
                    parcel2.writeInt(unlink);
                    return true;
                case 14:
                    int readInt17 = parcel.readInt();
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isFileFullyLoaded = isFileFullyLoaded(readInt17, readString15);
                    parcel2.writeNoException();
                    parcel2.writeInt(isFileFullyLoaded);
                    return true;
                case 15:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int isFullyLoaded = isFullyLoaded(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeInt(isFullyLoaded);
                    return true;
                case 16:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float loadingProgress = getLoadingProgress(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeFloat(loadingProgress);
                    return true;
                case 17:
                    int readInt20 = parcel.readInt();
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] metadataByPath = getMetadataByPath(readInt20, readString16);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(metadataByPath);
                    return true;
                case 18:
                    int readInt21 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] metadataById = getMetadataById(readInt21, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(metadataById);
                    return true;
                case 19:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteStorage(readInt22);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disallowReadLogs(readInt23);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt24 = parcel.readInt();
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    java.lang.String readString19 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean configureNativeBinaries = configureNativeBinaries(readInt24, readString17, readString18, readString19, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(configureNativeBinaries);
                    return true;
                case 22:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean waitForNativeBinariesExtraction = waitForNativeBinariesExtraction(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(waitForNativeBinariesExtraction);
                    return true;
                case 23:
                    int readInt26 = parcel.readInt();
                    android.os.incremental.IStorageLoadingProgressListener asInterface3 = android.os.incremental.IStorageLoadingProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerLoadingProgressListener = registerLoadingProgressListener(readInt26, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerLoadingProgressListener);
                    return true;
                case 24:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean unregisterLoadingProgressListener = unregisterLoadingProgressListener(readInt27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterLoadingProgressListener);
                    return true;
                case 25:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.PersistableBundle metrics = getMetrics(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(metrics, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.incremental.IIncrementalService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.incremental.IIncrementalService.DESCRIPTOR;
            }

            @Override // android.os.incremental.IIncrementalService
            public int openStorage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int createStorage(java.lang.String str, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(dataLoaderParamsParcel, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int createLinkedStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean startLoading(int i, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener, android.os.incremental.StorageHealthCheckParams storageHealthCheckParams, android.os.incremental.IStorageHealthListener iStorageHealthListener, android.os.incremental.PerUidReadTimeouts[] perUidReadTimeoutsArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataLoaderParamsParcel, 0);
                    obtain.writeStrongInterface(iDataLoaderStatusListener);
                    obtain.writeTypedObject(storageHealthCheckParams, 0);
                    obtain.writeStrongInterface(iStorageHealthListener);
                    obtain.writeTypedArray(perUidReadTimeoutsArr, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public void onInstallationComplete(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeBindMount(int i, java.lang.String str, java.lang.String str2, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int deleteBindMount(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeDirectory(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeDirectories(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeFile(int i, java.lang.String str, int i2, android.os.incremental.IncrementalNewFileParams incrementalNewFileParams, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(incrementalNewFileParams, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeFileFromRange(int i, java.lang.String str, java.lang.String str2, long j, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeLink(int i, java.lang.String str, int i2, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int unlink(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int isFileFullyLoaded(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int isFullyLoaded(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public float getLoadingProgress(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public byte[] getMetadataByPath(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public byte[] getMetadataById(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public void deleteStorage(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public void disallowReadLogs(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean configureNativeBinaries(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean waitForNativeBinariesExtraction(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean registerLoadingProgressListener(int i, android.os.incremental.IStorageLoadingProgressListener iStorageLoadingProgressListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iStorageLoadingProgressListener);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean unregisterLoadingProgressListener(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public android.os.PersistableBundle getMetrics(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.PersistableBundle) obtain2.readTypedObject(android.os.PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 24;
        }
    }
}
