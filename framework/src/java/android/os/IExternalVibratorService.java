package android.os;

/* loaded from: classes3.dex */
public interface IExternalVibratorService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IExternalVibratorService";

    android.os.ExternalVibrationScale onExternalVibrationStart(android.os.ExternalVibration externalVibration) throws android.os.RemoteException;

    void onExternalVibrationStop(android.os.ExternalVibration externalVibration) throws android.os.RemoteException;

    public static class Default implements android.os.IExternalVibratorService {
        @Override // android.os.IExternalVibratorService
        public android.os.ExternalVibrationScale onExternalVibrationStart(android.os.ExternalVibration externalVibration) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IExternalVibratorService
        public void onExternalVibrationStop(android.os.ExternalVibration externalVibration) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IExternalVibratorService {
        static final int TRANSACTION_onExternalVibrationStart = 1;
        static final int TRANSACTION_onExternalVibrationStop = 2;

        public Stub() {
            attachInterface(this, android.os.IExternalVibratorService.DESCRIPTOR);
        }

        public static android.os.IExternalVibratorService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IExternalVibratorService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IExternalVibratorService)) {
                return (android.os.IExternalVibratorService) queryLocalInterface;
            }
            return new android.os.IExternalVibratorService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onExternalVibrationStart";
                case 2:
                    return "onExternalVibrationStop";
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
                parcel.enforceInterface(android.os.IExternalVibratorService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IExternalVibratorService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ExternalVibration externalVibration = (android.os.ExternalVibration) parcel.readTypedObject(android.os.ExternalVibration.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.ExternalVibrationScale onExternalVibrationStart = onExternalVibrationStart(externalVibration);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(onExternalVibrationStart, 1);
                    return true;
                case 2:
                    android.os.ExternalVibration externalVibration2 = (android.os.ExternalVibration) parcel.readTypedObject(android.os.ExternalVibration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onExternalVibrationStop(externalVibration2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IExternalVibratorService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IExternalVibratorService.DESCRIPTOR;
            }

            @Override // android.os.IExternalVibratorService
            public android.os.ExternalVibrationScale onExternalVibrationStart(android.os.ExternalVibration externalVibration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IExternalVibratorService.DESCRIPTOR);
                    obtain.writeTypedObject(externalVibration, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ExternalVibrationScale) obtain2.readTypedObject(android.os.ExternalVibrationScale.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IExternalVibratorService
            public void onExternalVibrationStop(android.os.ExternalVibration externalVibration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IExternalVibratorService.DESCRIPTOR);
                    obtain.writeTypedObject(externalVibration, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
