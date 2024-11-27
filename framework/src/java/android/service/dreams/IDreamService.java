package android.service.dreams;

/* loaded from: classes3.dex */
public interface IDreamService extends android.os.IInterface {
    void attach(android.os.IBinder iBinder, boolean z, boolean z2, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException;

    void detach() throws android.os.RemoteException;

    void wakeUp() throws android.os.RemoteException;

    public static class Default implements android.service.dreams.IDreamService {
        @Override // android.service.dreams.IDreamService
        public void attach(android.os.IBinder iBinder, boolean z, boolean z2, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamService
        public void detach() throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamService
        public void wakeUp() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.dreams.IDreamService {
        public static final java.lang.String DESCRIPTOR = "android.service.dreams.IDreamService";
        static final int TRANSACTION_attach = 1;
        static final int TRANSACTION_detach = 2;
        static final int TRANSACTION_wakeUp = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.dreams.IDreamService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.dreams.IDreamService)) {
                return (android.service.dreams.IDreamService) queryLocalInterface;
            }
            return new android.service.dreams.IDreamService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "attach";
                case 2:
                    return "detach";
                case 3:
                    return "wakeUp";
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
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    android.os.IRemoteCallback asInterface = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attach(readStrongBinder, readBoolean, readBoolean2, asInterface);
                    return true;
                case 2:
                    detach();
                    return true;
                case 3:
                    wakeUp();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.dreams.IDreamService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.dreams.IDreamService.Stub.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamService
            public void attach(android.os.IBinder iBinder, boolean z, boolean z2, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamService
            public void detach() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamService
            public void wakeUp() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
