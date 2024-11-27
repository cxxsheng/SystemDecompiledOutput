package android.os;

/* loaded from: classes3.dex */
public interface IExternalVibrationController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IExternalVibrationController";

    boolean mute() throws android.os.RemoteException;

    boolean unmute() throws android.os.RemoteException;

    public static class Default implements android.os.IExternalVibrationController {
        @Override // android.os.IExternalVibrationController
        public boolean mute() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IExternalVibrationController
        public boolean unmute() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IExternalVibrationController {
        static final int TRANSACTION_mute = 1;
        static final int TRANSACTION_unmute = 2;

        public Stub() {
            attachInterface(this, android.os.IExternalVibrationController.DESCRIPTOR);
        }

        public static android.os.IExternalVibrationController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IExternalVibrationController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IExternalVibrationController)) {
                return (android.os.IExternalVibrationController) queryLocalInterface;
            }
            return new android.os.IExternalVibrationController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.media.MediaMetrics.Value.MUTE;
                case 2:
                    return android.media.MediaMetrics.Value.UNMUTE;
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
                parcel.enforceInterface(android.os.IExternalVibrationController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IExternalVibrationController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean mute = mute();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(mute);
                    return true;
                case 2:
                    boolean unmute = unmute();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unmute);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IExternalVibrationController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IExternalVibrationController.DESCRIPTOR;
            }

            @Override // android.os.IExternalVibrationController
            public boolean mute() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IExternalVibrationController.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IExternalVibrationController
            public boolean unmute() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IExternalVibrationController.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
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
