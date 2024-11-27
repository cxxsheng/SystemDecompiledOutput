package android.hardware.location;

/* loaded from: classes2.dex */
public interface IActivityRecognitionHardwareClient extends android.os.IInterface {
    void onAvailabilityChanged(boolean z, android.hardware.location.IActivityRecognitionHardware iActivityRecognitionHardware) throws android.os.RemoteException;

    public static class Default implements android.hardware.location.IActivityRecognitionHardwareClient {
        @Override // android.hardware.location.IActivityRecognitionHardwareClient
        public void onAvailabilityChanged(boolean z, android.hardware.location.IActivityRecognitionHardware iActivityRecognitionHardware) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.location.IActivityRecognitionHardwareClient {
        public static final java.lang.String DESCRIPTOR = "android.hardware.location.IActivityRecognitionHardwareClient";
        static final int TRANSACTION_onAvailabilityChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.location.IActivityRecognitionHardwareClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.location.IActivityRecognitionHardwareClient)) {
                return (android.hardware.location.IActivityRecognitionHardwareClient) queryLocalInterface;
            }
            return new android.hardware.location.IActivityRecognitionHardwareClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAvailabilityChanged";
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
                    boolean readBoolean = parcel.readBoolean();
                    android.hardware.location.IActivityRecognitionHardware asInterface = android.hardware.location.IActivityRecognitionHardware.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onAvailabilityChanged(readBoolean, asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.location.IActivityRecognitionHardwareClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.location.IActivityRecognitionHardwareClient.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IActivityRecognitionHardwareClient
            public void onAvailabilityChanged(boolean z, android.hardware.location.IActivityRecognitionHardware iActivityRecognitionHardware) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IActivityRecognitionHardwareClient.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iActivityRecognitionHardware);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
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
