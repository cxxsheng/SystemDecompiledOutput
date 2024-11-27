package android.service.watchdog;

/* loaded from: classes3.dex */
public interface IExplicitHealthCheckService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.watchdog.IExplicitHealthCheckService";

    void cancel(java.lang.String str) throws android.os.RemoteException;

    void getRequestedPackages(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void getSupportedPackages(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void request(java.lang.String str) throws android.os.RemoteException;

    void setCallback(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    public static class Default implements android.service.watchdog.IExplicitHealthCheckService {
        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void setCallback(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void request(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void cancel(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void getSupportedPackages(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void getRequestedPackages(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.watchdog.IExplicitHealthCheckService {
        static final int TRANSACTION_cancel = 3;
        static final int TRANSACTION_getRequestedPackages = 5;
        static final int TRANSACTION_getSupportedPackages = 4;
        static final int TRANSACTION_request = 2;
        static final int TRANSACTION_setCallback = 1;

        public Stub() {
            attachInterface(this, android.service.watchdog.IExplicitHealthCheckService.DESCRIPTOR);
        }

        public static android.service.watchdog.IExplicitHealthCheckService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.watchdog.IExplicitHealthCheckService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.watchdog.IExplicitHealthCheckService)) {
                return (android.service.watchdog.IExplicitHealthCheckService) queryLocalInterface;
            }
            return new android.service.watchdog.IExplicitHealthCheckService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setCallback";
                case 2:
                    return "request";
                case 3:
                    return "cancel";
                case 4:
                    return "getSupportedPackages";
                case 5:
                    return "getRequestedPackages";
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
                parcel.enforceInterface(android.service.watchdog.IExplicitHealthCheckService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.watchdog.IExplicitHealthCheckService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallback(remoteCallback);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    request(readString);
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancel(readString2);
                    return true;
                case 4:
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSupportedPackages(remoteCallback2);
                    return true;
                case 5:
                    android.os.RemoteCallback remoteCallback3 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getRequestedPackages(remoteCallback3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.watchdog.IExplicitHealthCheckService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.watchdog.IExplicitHealthCheckService.DESCRIPTOR;
            }

            @Override // android.service.watchdog.IExplicitHealthCheckService
            public void setCallback(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.watchdog.IExplicitHealthCheckService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.watchdog.IExplicitHealthCheckService
            public void request(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.watchdog.IExplicitHealthCheckService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.watchdog.IExplicitHealthCheckService
            public void cancel(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.watchdog.IExplicitHealthCheckService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.watchdog.IExplicitHealthCheckService
            public void getSupportedPackages(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.watchdog.IExplicitHealthCheckService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.watchdog.IExplicitHealthCheckService
            public void getRequestedPackages(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.watchdog.IExplicitHealthCheckService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
