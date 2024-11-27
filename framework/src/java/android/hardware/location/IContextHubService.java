package android.hardware.location;

/* loaded from: classes2.dex */
public interface IContextHubService extends android.os.IInterface {
    android.hardware.location.IContextHubClient createClient(int i, android.hardware.location.IContextHubClientCallback iContextHubClientCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.hardware.location.IContextHubClient createPendingIntentClient(int i, android.app.PendingIntent pendingIntent, long j, java.lang.String str) throws android.os.RemoteException;

    void disableNanoApp(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws android.os.RemoteException;

    void enableNanoApp(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws android.os.RemoteException;

    int[] findNanoAppOnHub(int i, android.hardware.location.NanoAppFilter nanoAppFilter) throws android.os.RemoteException;

    int[] getContextHubHandles() throws android.os.RemoteException;

    android.hardware.location.ContextHubInfo getContextHubInfo(int i) throws android.os.RemoteException;

    java.util.List<android.hardware.location.ContextHubInfo> getContextHubs() throws android.os.RemoteException;

    android.hardware.location.NanoAppInstanceInfo getNanoAppInstanceInfo(int i) throws android.os.RemoteException;

    long[] getPreloadedNanoAppIds(android.hardware.location.ContextHubInfo contextHubInfo) throws android.os.RemoteException;

    int loadNanoApp(int i, android.hardware.location.NanoApp nanoApp) throws android.os.RemoteException;

    void loadNanoAppOnHub(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, android.hardware.location.NanoAppBinary nanoAppBinary) throws android.os.RemoteException;

    void queryNanoApps(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback) throws android.os.RemoteException;

    int registerCallback(android.hardware.location.IContextHubCallback iContextHubCallback) throws android.os.RemoteException;

    int sendMessage(int i, int i2, android.hardware.location.ContextHubMessage contextHubMessage) throws android.os.RemoteException;

    boolean setTestMode(boolean z) throws android.os.RemoteException;

    int unloadNanoApp(int i) throws android.os.RemoteException;

    void unloadNanoAppFromHub(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws android.os.RemoteException;

    public static class Default implements android.hardware.location.IContextHubService {
        @Override // android.hardware.location.IContextHubService
        public int registerCallback(android.hardware.location.IContextHubCallback iContextHubCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubService
        public int[] getContextHubHandles() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public android.hardware.location.ContextHubInfo getContextHubInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public int loadNanoApp(int i, android.hardware.location.NanoApp nanoApp) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubService
        public int unloadNanoApp(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubService
        public android.hardware.location.NanoAppInstanceInfo getNanoAppInstanceInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public int[] findNanoAppOnHub(int i, android.hardware.location.NanoAppFilter nanoAppFilter) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public int sendMessage(int i, int i2, android.hardware.location.ContextHubMessage contextHubMessage) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubService
        public android.hardware.location.IContextHubClient createClient(int i, android.hardware.location.IContextHubClientCallback iContextHubClientCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public android.hardware.location.IContextHubClient createPendingIntentClient(int i, android.app.PendingIntent pendingIntent, long j, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public java.util.List<android.hardware.location.ContextHubInfo> getContextHubs() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public void loadNanoAppOnHub(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, android.hardware.location.NanoAppBinary nanoAppBinary) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public void unloadNanoAppFromHub(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public void enableNanoApp(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public void disableNanoApp(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public void queryNanoApps(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public long[] getPreloadedNanoAppIds(android.hardware.location.ContextHubInfo contextHubInfo) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public boolean setTestMode(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.location.IContextHubService {
        public static final java.lang.String DESCRIPTOR = "android.hardware.location.IContextHubService";
        static final int TRANSACTION_createClient = 9;
        static final int TRANSACTION_createPendingIntentClient = 10;
        static final int TRANSACTION_disableNanoApp = 15;
        static final int TRANSACTION_enableNanoApp = 14;
        static final int TRANSACTION_findNanoAppOnHub = 7;
        static final int TRANSACTION_getContextHubHandles = 2;
        static final int TRANSACTION_getContextHubInfo = 3;
        static final int TRANSACTION_getContextHubs = 11;
        static final int TRANSACTION_getNanoAppInstanceInfo = 6;
        static final int TRANSACTION_getPreloadedNanoAppIds = 17;
        static final int TRANSACTION_loadNanoApp = 4;
        static final int TRANSACTION_loadNanoAppOnHub = 12;
        static final int TRANSACTION_queryNanoApps = 16;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_sendMessage = 8;
        static final int TRANSACTION_setTestMode = 18;
        static final int TRANSACTION_unloadNanoApp = 5;
        static final int TRANSACTION_unloadNanoAppFromHub = 13;
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

        public static android.hardware.location.IContextHubService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.location.IContextHubService)) {
                return (android.hardware.location.IContextHubService) queryLocalInterface;
            }
            return new android.hardware.location.IContextHubService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "getContextHubHandles";
                case 3:
                    return "getContextHubInfo";
                case 4:
                    return "loadNanoApp";
                case 5:
                    return "unloadNanoApp";
                case 6:
                    return "getNanoAppInstanceInfo";
                case 7:
                    return "findNanoAppOnHub";
                case 8:
                    return "sendMessage";
                case 9:
                    return "createClient";
                case 10:
                    return "createPendingIntentClient";
                case 11:
                    return "getContextHubs";
                case 12:
                    return "loadNanoAppOnHub";
                case 13:
                    return "unloadNanoAppFromHub";
                case 14:
                    return "enableNanoApp";
                case 15:
                    return "disableNanoApp";
                case 16:
                    return "queryNanoApps";
                case 17:
                    return "getPreloadedNanoAppIds";
                case 18:
                    return "setTestMode";
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
                    android.hardware.location.IContextHubCallback asInterface = android.hardware.location.IContextHubCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int registerCallback = registerCallback(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerCallback);
                    return true;
                case 2:
                    int[] contextHubHandles = getContextHubHandles();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(contextHubHandles);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.location.ContextHubInfo contextHubInfo = getContextHubInfo(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contextHubInfo, 1);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    android.hardware.location.NanoApp nanoApp = (android.hardware.location.NanoApp) parcel.readTypedObject(android.hardware.location.NanoApp.CREATOR);
                    parcel.enforceNoDataAvail();
                    int loadNanoApp = loadNanoApp(readInt2, nanoApp);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadNanoApp);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int unloadNanoApp = unloadNanoApp(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(unloadNanoApp);
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.location.NanoAppInstanceInfo nanoAppInstanceInfo = getNanoAppInstanceInfo(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(nanoAppInstanceInfo, 1);
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    android.hardware.location.NanoAppFilter nanoAppFilter = (android.hardware.location.NanoAppFilter) parcel.readTypedObject(android.hardware.location.NanoAppFilter.CREATOR);
                    parcel.enforceNoDataAvail();
                    int[] findNanoAppOnHub = findNanoAppOnHub(readInt5, nanoAppFilter);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(findNanoAppOnHub);
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    android.hardware.location.ContextHubMessage contextHubMessage = (android.hardware.location.ContextHubMessage) parcel.readTypedObject(android.hardware.location.ContextHubMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    int sendMessage = sendMessage(readInt6, readInt7, contextHubMessage);
                    parcel2.writeNoException();
                    parcel2.writeInt(sendMessage);
                    return true;
                case 9:
                    int readInt8 = parcel.readInt();
                    android.hardware.location.IContextHubClientCallback asInterface2 = android.hardware.location.IContextHubClientCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.location.IContextHubClient createClient = createClient(readInt8, asInterface2, readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createClient);
                    return true;
                case 10:
                    int readInt9 = parcel.readInt();
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    long readLong = parcel.readLong();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.location.IContextHubClient createPendingIntentClient = createPendingIntentClient(readInt9, pendingIntent, readLong, readString3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createPendingIntentClient);
                    return true;
                case 11:
                    java.util.List<android.hardware.location.ContextHubInfo> contextHubs = getContextHubs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(contextHubs, 1);
                    return true;
                case 12:
                    int readInt10 = parcel.readInt();
                    android.hardware.location.IContextHubTransactionCallback asInterface3 = android.hardware.location.IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.location.NanoAppBinary nanoAppBinary = (android.hardware.location.NanoAppBinary) parcel.readTypedObject(android.hardware.location.NanoAppBinary.CREATOR);
                    parcel.enforceNoDataAvail();
                    loadNanoAppOnHub(readInt10, asInterface3, nanoAppBinary);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt11 = parcel.readInt();
                    android.hardware.location.IContextHubTransactionCallback asInterface4 = android.hardware.location.IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    unloadNanoAppFromHub(readInt11, asInterface4, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt12 = parcel.readInt();
                    android.hardware.location.IContextHubTransactionCallback asInterface5 = android.hardware.location.IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    enableNanoApp(readInt12, asInterface5, readLong3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt13 = parcel.readInt();
                    android.hardware.location.IContextHubTransactionCallback asInterface6 = android.hardware.location.IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    disableNanoApp(readInt13, asInterface6, readLong4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt14 = parcel.readInt();
                    android.hardware.location.IContextHubTransactionCallback asInterface7 = android.hardware.location.IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    queryNanoApps(readInt14, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.hardware.location.ContextHubInfo contextHubInfo2 = (android.hardware.location.ContextHubInfo) parcel.readTypedObject(android.hardware.location.ContextHubInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long[] preloadedNanoAppIds = getPreloadedNanoAppIds(contextHubInfo2);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(preloadedNanoAppIds);
                    return true;
                case 18:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean testMode = setTestMode(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(testMode);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.location.IContextHubService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.location.IContextHubService.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IContextHubService
            public int registerCallback(android.hardware.location.IContextHubCallback iContextHubCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iContextHubCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public int[] getContextHubHandles() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public android.hardware.location.ContextHubInfo getContextHubInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.location.ContextHubInfo) obtain2.readTypedObject(android.hardware.location.ContextHubInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public int loadNanoApp(int i, android.hardware.location.NanoApp nanoApp) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(nanoApp, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public int unloadNanoApp(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public android.hardware.location.NanoAppInstanceInfo getNanoAppInstanceInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.location.NanoAppInstanceInfo) obtain2.readTypedObject(android.hardware.location.NanoAppInstanceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public int[] findNanoAppOnHub(int i, android.hardware.location.NanoAppFilter nanoAppFilter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(nanoAppFilter, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public int sendMessage(int i, int i2, android.hardware.location.ContextHubMessage contextHubMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(contextHubMessage, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public android.hardware.location.IContextHubClient createClient(int i, android.hardware.location.IContextHubClientCallback iContextHubClientCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iContextHubClientCallback);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.location.IContextHubClient.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public android.hardware.location.IContextHubClient createPendingIntentClient(int i, android.app.PendingIntent pendingIntent, long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.location.IContextHubClient.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public java.util.List<android.hardware.location.ContextHubInfo> getContextHubs() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.location.ContextHubInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void loadNanoAppOnHub(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, android.hardware.location.NanoAppBinary nanoAppBinary) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iContextHubTransactionCallback);
                    obtain.writeTypedObject(nanoAppBinary, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void unloadNanoAppFromHub(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iContextHubTransactionCallback);
                    obtain.writeLong(j);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void enableNanoApp(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iContextHubTransactionCallback);
                    obtain.writeLong(j);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void disableNanoApp(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iContextHubTransactionCallback);
                    obtain.writeLong(j);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void queryNanoApps(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iContextHubTransactionCallback);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public long[] getPreloadedNanoAppIds(android.hardware.location.ContextHubInfo contextHubInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(contextHubInfo, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public boolean setTestMode(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void registerCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void getContextHubHandles_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void getContextHubInfo_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void loadNanoApp_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void unloadNanoApp_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void getNanoAppInstanceInfo_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void findNanoAppOnHub_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void sendMessage_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void createClient_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void createPendingIntentClient_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void getContextHubs_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void loadNanoAppOnHub_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void unloadNanoAppFromHub_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void enableNanoApp_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void disableNanoApp_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void queryNanoApps_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void getPreloadedNanoAppIds_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void setTestMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
