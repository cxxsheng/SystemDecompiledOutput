package android.content.pm;

/* loaded from: classes.dex */
public interface IBackgroundInstallControlService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.content.pm.IBackgroundInstallControlService";

    android.content.pm.ParceledListSlice getBackgroundInstalledPackages(long j, int i) throws android.os.RemoteException;

    void registerBackgroundInstallCallback(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException;

    void unregisterBackgroundInstallCallback(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IBackgroundInstallControlService {
        @Override // android.content.pm.IBackgroundInstallControlService
        public android.content.pm.ParceledListSlice getBackgroundInstalledPackages(long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IBackgroundInstallControlService
        public void registerBackgroundInstallCallback(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IBackgroundInstallControlService
        public void unregisterBackgroundInstallCallback(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IBackgroundInstallControlService {
        static final int TRANSACTION_getBackgroundInstalledPackages = 1;
        static final int TRANSACTION_registerBackgroundInstallCallback = 2;
        static final int TRANSACTION_unregisterBackgroundInstallCallback = 3;

        public Stub() {
            attachInterface(this, android.content.pm.IBackgroundInstallControlService.DESCRIPTOR);
        }

        public static android.content.pm.IBackgroundInstallControlService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.content.pm.IBackgroundInstallControlService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IBackgroundInstallControlService)) {
                return (android.content.pm.IBackgroundInstallControlService) queryLocalInterface;
            }
            return new android.content.pm.IBackgroundInstallControlService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getBackgroundInstalledPackages";
                case 2:
                    return "registerBackgroundInstallCallback";
                case 3:
                    return "unregisterBackgroundInstallCallback";
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
                parcel.enforceInterface(android.content.pm.IBackgroundInstallControlService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.content.pm.IBackgroundInstallControlService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice backgroundInstalledPackages = getBackgroundInstalledPackages(readLong, readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(backgroundInstalledPackages, 1);
                    return true;
                case 2:
                    android.os.IRemoteCallback asInterface = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBackgroundInstallCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.os.IRemoteCallback asInterface2 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterBackgroundInstallCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IBackgroundInstallControlService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IBackgroundInstallControlService.DESCRIPTOR;
            }

            @Override // android.content.pm.IBackgroundInstallControlService
            public android.content.pm.ParceledListSlice getBackgroundInstalledPackages(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IBackgroundInstallControlService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IBackgroundInstallControlService
            public void registerBackgroundInstallCallback(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IBackgroundInstallControlService.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IBackgroundInstallControlService
            public void unregisterBackgroundInstallCallback(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IBackgroundInstallControlService.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
