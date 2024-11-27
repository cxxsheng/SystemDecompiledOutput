package android.service.remotelockscreenvalidation;

/* loaded from: classes3.dex */
public interface IRemoteLockscreenValidationService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService";

    void validateLockscreenGuess(byte[] bArr, android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback iRemoteLockscreenValidationCallback) throws android.os.RemoteException;

    public static class Default implements android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService {
        @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService
        public void validateLockscreenGuess(byte[] bArr, android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback iRemoteLockscreenValidationCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService {
        static final int TRANSACTION_validateLockscreenGuess = 1;

        public Stub() {
            attachInterface(this, android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService.DESCRIPTOR);
        }

        public static android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService)) {
                return (android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService) queryLocalInterface;
            }
            return new android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "validateLockscreenGuess";
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
                parcel.enforceInterface(android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    byte[] createByteArray = parcel.createByteArray();
                    android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback asInterface = android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    validateLockscreenGuess(createByteArray, asInterface);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService.DESCRIPTOR;
            }

            @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService
            public void validateLockscreenGuess(byte[] bArr, android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback iRemoteLockscreenValidationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iRemoteLockscreenValidationCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
