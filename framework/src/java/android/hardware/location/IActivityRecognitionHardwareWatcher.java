package android.hardware.location;

@java.lang.Deprecated
/* loaded from: classes2.dex */
public interface IActivityRecognitionHardwareWatcher extends android.os.IInterface {
    void onInstanceChanged(android.hardware.location.IActivityRecognitionHardware iActivityRecognitionHardware) throws android.os.RemoteException;

    public static class Default implements android.hardware.location.IActivityRecognitionHardwareWatcher {
        @Override // android.hardware.location.IActivityRecognitionHardwareWatcher
        public void onInstanceChanged(android.hardware.location.IActivityRecognitionHardware iActivityRecognitionHardware) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.location.IActivityRecognitionHardwareWatcher {
        public static final java.lang.String DESCRIPTOR = "android.hardware.location.IActivityRecognitionHardwareWatcher";
        static final int TRANSACTION_onInstanceChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.location.IActivityRecognitionHardwareWatcher asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.location.IActivityRecognitionHardwareWatcher)) {
                return (android.hardware.location.IActivityRecognitionHardwareWatcher) queryLocalInterface;
            }
            return new android.hardware.location.IActivityRecognitionHardwareWatcher.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onInstanceChanged";
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
                    android.hardware.location.IActivityRecognitionHardware asInterface = android.hardware.location.IActivityRecognitionHardware.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onInstanceChanged(asInterface);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.location.IActivityRecognitionHardwareWatcher {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.location.IActivityRecognitionHardwareWatcher.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IActivityRecognitionHardwareWatcher
            public void onInstanceChanged(android.hardware.location.IActivityRecognitionHardware iActivityRecognitionHardware) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IActivityRecognitionHardwareWatcher.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iActivityRecognitionHardware);
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
