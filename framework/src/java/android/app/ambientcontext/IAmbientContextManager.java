package android.app.ambientcontext;

/* loaded from: classes.dex */
public interface IAmbientContextManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.ambientcontext.IAmbientContextManager";

    void queryServiceStatus(int[] iArr, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void registerObserver(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, android.app.PendingIntent pendingIntent, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void registerObserverWithCallback(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.app.ambientcontext.IAmbientContextObserver iAmbientContextObserver) throws android.os.RemoteException;

    void startConsentActivity(int[] iArr, java.lang.String str) throws android.os.RemoteException;

    void unregisterObserver(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.app.ambientcontext.IAmbientContextManager {
        @Override // android.app.ambientcontext.IAmbientContextManager
        public void registerObserver(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, android.app.PendingIntent pendingIntent, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void registerObserverWithCallback(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.app.ambientcontext.IAmbientContextObserver iAmbientContextObserver) throws android.os.RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void unregisterObserver(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void queryServiceStatus(int[] iArr, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void startConsentActivity(int[] iArr, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.ambientcontext.IAmbientContextManager {
        static final int TRANSACTION_queryServiceStatus = 4;
        static final int TRANSACTION_registerObserver = 1;
        static final int TRANSACTION_registerObserverWithCallback = 2;
        static final int TRANSACTION_startConsentActivity = 5;
        static final int TRANSACTION_unregisterObserver = 3;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.app.ambientcontext.IAmbientContextManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.app.ambientcontext.IAmbientContextManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.ambientcontext.IAmbientContextManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.ambientcontext.IAmbientContextManager)) {
                return (android.app.ambientcontext.IAmbientContextManager) queryLocalInterface;
            }
            return new android.app.ambientcontext.IAmbientContextManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerObserver";
                case 2:
                    return "registerObserverWithCallback";
                case 3:
                    return "unregisterObserver";
                case 4:
                    return "queryServiceStatus";
                case 5:
                    return "startConsentActivity";
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
                parcel.enforceInterface(android.app.ambientcontext.IAmbientContextManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.ambientcontext.IAmbientContextManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest = (android.app.ambientcontext.AmbientContextEventRequest) parcel.readTypedObject(android.app.ambientcontext.AmbientContextEventRequest.CREATOR);
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerObserver(ambientContextEventRequest, pendingIntent, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest2 = (android.app.ambientcontext.AmbientContextEventRequest) parcel.readTypedObject(android.app.ambientcontext.AmbientContextEventRequest.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.app.ambientcontext.IAmbientContextObserver asInterface = android.app.ambientcontext.IAmbientContextObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerObserverWithCallback(ambientContextEventRequest2, readString, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterObserver(readString2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int[] createIntArray = parcel.createIntArray();
                    java.lang.String readString3 = parcel.readString();
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    queryServiceStatus(createIntArray, readString3, remoteCallback2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int[] createIntArray2 = parcel.createIntArray();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startConsentActivity(createIntArray2, readString4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.ambientcontext.IAmbientContextManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.ambientcontext.IAmbientContextManager.DESCRIPTOR;
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void registerObserver(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, android.app.PendingIntent pendingIntent, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.ambientcontext.IAmbientContextManager.DESCRIPTOR);
                    obtain.writeTypedObject(ambientContextEventRequest, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void registerObserverWithCallback(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.app.ambientcontext.IAmbientContextObserver iAmbientContextObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.ambientcontext.IAmbientContextManager.DESCRIPTOR);
                    obtain.writeTypedObject(ambientContextEventRequest, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iAmbientContextObserver);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void unregisterObserver(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.ambientcontext.IAmbientContextManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void queryServiceStatus(int[] iArr, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.ambientcontext.IAmbientContextManager.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void startConsentActivity(int[] iArr, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.ambientcontext.IAmbientContextManager.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void unregisterObserver_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_AMBIENT_CONTEXT_EVENT, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
