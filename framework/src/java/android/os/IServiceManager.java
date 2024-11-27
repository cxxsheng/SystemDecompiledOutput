package android.os;

/* loaded from: classes3.dex */
public interface IServiceManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IServiceManager";
    public static final int DUMP_FLAG_PRIORITY_ALL = 15;
    public static final int DUMP_FLAG_PRIORITY_CRITICAL = 1;
    public static final int DUMP_FLAG_PRIORITY_DEFAULT = 8;
    public static final int DUMP_FLAG_PRIORITY_HIGH = 2;
    public static final int DUMP_FLAG_PRIORITY_NORMAL = 4;
    public static final int DUMP_FLAG_PROTO = 16;

    void addService(java.lang.String str, android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException;

    android.os.IBinder checkService(java.lang.String str) throws android.os.RemoteException;

    android.os.ConnectionInfo getConnectionInfo(java.lang.String str) throws android.os.RemoteException;

    java.lang.String[] getDeclaredInstances(java.lang.String str) throws android.os.RemoteException;

    android.os.IBinder getService(java.lang.String str) throws android.os.RemoteException;

    android.os.ServiceDebugInfo[] getServiceDebugInfo() throws android.os.RemoteException;

    java.lang.String[] getUpdatableNames(java.lang.String str) throws android.os.RemoteException;

    boolean isDeclared(java.lang.String str) throws android.os.RemoteException;

    java.lang.String[] listServices(int i) throws android.os.RemoteException;

    void registerClientCallback(java.lang.String str, android.os.IBinder iBinder, android.os.IClientCallback iClientCallback) throws android.os.RemoteException;

    void registerForNotifications(java.lang.String str, android.os.IServiceCallback iServiceCallback) throws android.os.RemoteException;

    void tryUnregisterService(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException;

    void unregisterForNotifications(java.lang.String str, android.os.IServiceCallback iServiceCallback) throws android.os.RemoteException;

    java.lang.String updatableViaApex(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.os.IServiceManager {
        @Override // android.os.IServiceManager
        public android.os.IBinder getService(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public android.os.IBinder checkService(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public void addService(java.lang.String str, android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IServiceManager
        public java.lang.String[] listServices(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public void registerForNotifications(java.lang.String str, android.os.IServiceCallback iServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IServiceManager
        public void unregisterForNotifications(java.lang.String str, android.os.IServiceCallback iServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IServiceManager
        public boolean isDeclared(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IServiceManager
        public java.lang.String[] getDeclaredInstances(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public java.lang.String updatableViaApex(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public java.lang.String[] getUpdatableNames(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public android.os.ConnectionInfo getConnectionInfo(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public void registerClientCallback(java.lang.String str, android.os.IBinder iBinder, android.os.IClientCallback iClientCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IServiceManager
        public void tryUnregisterService(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IServiceManager
        public android.os.ServiceDebugInfo[] getServiceDebugInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IServiceManager {
        static final int TRANSACTION_addService = 3;
        static final int TRANSACTION_checkService = 2;
        static final int TRANSACTION_getConnectionInfo = 11;
        static final int TRANSACTION_getDeclaredInstances = 8;
        static final int TRANSACTION_getService = 1;
        static final int TRANSACTION_getServiceDebugInfo = 14;
        static final int TRANSACTION_getUpdatableNames = 10;
        static final int TRANSACTION_isDeclared = 7;
        static final int TRANSACTION_listServices = 4;
        static final int TRANSACTION_registerClientCallback = 12;
        static final int TRANSACTION_registerForNotifications = 5;
        static final int TRANSACTION_tryUnregisterService = 13;
        static final int TRANSACTION_unregisterForNotifications = 6;
        static final int TRANSACTION_updatableViaApex = 9;

        public Stub() {
            attachInterface(this, android.os.IServiceManager.DESCRIPTOR);
        }

        public static android.os.IServiceManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IServiceManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IServiceManager)) {
                return (android.os.IServiceManager) queryLocalInterface;
            }
            return new android.os.IServiceManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getService";
                case 2:
                    return "checkService";
                case 3:
                    return "addService";
                case 4:
                    return "listServices";
                case 5:
                    return "registerForNotifications";
                case 6:
                    return "unregisterForNotifications";
                case 7:
                    return "isDeclared";
                case 8:
                    return "getDeclaredInstances";
                case 9:
                    return "updatableViaApex";
                case 10:
                    return "getUpdatableNames";
                case 11:
                    return "getConnectionInfo";
                case 12:
                    return "registerClientCallback";
                case 13:
                    return "tryUnregisterService";
                case 14:
                    return "getServiceDebugInfo";
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
                parcel.enforceInterface(android.os.IServiceManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IServiceManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.IBinder service = getService(readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(service);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.IBinder checkService = checkService(readString2);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(checkService);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addService(readString3, readStrongBinder, readBoolean, readInt);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] listServices = listServices(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listServices);
                    return true;
                case 5:
                    java.lang.String readString4 = parcel.readString();
                    android.os.IServiceCallback asInterface = android.os.IServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForNotifications(readString4, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString5 = parcel.readString();
                    android.os.IServiceCallback asInterface2 = android.os.IServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForNotifications(readString5, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isDeclared = isDeclared(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeclared);
                    return true;
                case 8:
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] declaredInstances = getDeclaredInstances(readString7);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(declaredInstances);
                    return true;
                case 9:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String updatableViaApex = updatableViaApex(readString8);
                    parcel2.writeNoException();
                    parcel2.writeString(updatableViaApex);
                    return true;
                case 10:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] updatableNames = getUpdatableNames(readString9);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(updatableNames);
                    return true;
                case 11:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.ConnectionInfo connectionInfo = getConnectionInfo(readString10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(connectionInfo, 1);
                    return true;
                case 12:
                    java.lang.String readString11 = parcel.readString();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.os.IClientCallback asInterface3 = android.os.IClientCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerClientCallback(readString11, readStrongBinder2, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    java.lang.String readString12 = parcel.readString();
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    tryUnregisterService(readString12, readStrongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.os.ServiceDebugInfo[] serviceDebugInfo = getServiceDebugInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(serviceDebugInfo, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IServiceManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IServiceManager.DESCRIPTOR;
            }

            @Override // android.os.IServiceManager
            public android.os.IBinder getService(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public android.os.IBinder checkService(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void addService(java.lang.String str, android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public java.lang.String[] listServices(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void registerForNotifications(java.lang.String str, android.os.IServiceCallback iServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iServiceCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void unregisterForNotifications(java.lang.String str, android.os.IServiceCallback iServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iServiceCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public boolean isDeclared(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public java.lang.String[] getDeclaredInstances(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public java.lang.String updatableViaApex(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public java.lang.String[] getUpdatableNames(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public android.os.ConnectionInfo getConnectionInfo(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ConnectionInfo) obtain2.readTypedObject(android.os.ConnectionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void registerClientCallback(java.lang.String str, android.os.IBinder iBinder, android.os.IClientCallback iClientCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iClientCallback);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void tryUnregisterService(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public android.os.ServiceDebugInfo[] getServiceDebugInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IServiceManager.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ServiceDebugInfo[]) obtain2.createTypedArray(android.os.ServiceDebugInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }
    }
}
