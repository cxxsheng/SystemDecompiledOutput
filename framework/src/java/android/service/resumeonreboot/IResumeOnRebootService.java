package android.service.resumeonreboot;

/* loaded from: classes3.dex */
public interface IResumeOnRebootService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.resumeonreboot.IResumeOnRebootService";

    void unwrap(byte[] bArr, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void wrapSecret(byte[] bArr, long j, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    public static class Default implements android.service.resumeonreboot.IResumeOnRebootService {
        @Override // android.service.resumeonreboot.IResumeOnRebootService
        public void wrapSecret(byte[] bArr, long j, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.resumeonreboot.IResumeOnRebootService
        public void unwrap(byte[] bArr, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.resumeonreboot.IResumeOnRebootService {
        static final int TRANSACTION_unwrap = 2;
        static final int TRANSACTION_wrapSecret = 1;

        public Stub() {
            attachInterface(this, android.service.resumeonreboot.IResumeOnRebootService.DESCRIPTOR);
        }

        public static android.service.resumeonreboot.IResumeOnRebootService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.resumeonreboot.IResumeOnRebootService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.resumeonreboot.IResumeOnRebootService)) {
                return (android.service.resumeonreboot.IResumeOnRebootService) queryLocalInterface;
            }
            return new android.service.resumeonreboot.IResumeOnRebootService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "wrapSecret";
                case 2:
                    return "unwrap";
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
                parcel.enforceInterface(android.service.resumeonreboot.IResumeOnRebootService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.resumeonreboot.IResumeOnRebootService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    byte[] createByteArray = parcel.createByteArray();
                    long readLong = parcel.readLong();
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    wrapSecret(createByteArray, readLong, remoteCallback);
                    return true;
                case 2:
                    byte[] createByteArray2 = parcel.createByteArray();
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    unwrap(createByteArray2, remoteCallback2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.resumeonreboot.IResumeOnRebootService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.resumeonreboot.IResumeOnRebootService.DESCRIPTOR;
            }

            @Override // android.service.resumeonreboot.IResumeOnRebootService
            public void wrapSecret(byte[] bArr, long j, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.resumeonreboot.IResumeOnRebootService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.resumeonreboot.IResumeOnRebootService
            public void unwrap(byte[] bArr, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.resumeonreboot.IResumeOnRebootService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
