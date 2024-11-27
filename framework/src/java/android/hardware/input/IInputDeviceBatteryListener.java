package android.hardware.input;

/* loaded from: classes2.dex */
public interface IInputDeviceBatteryListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.input.IInputDeviceBatteryListener";

    void onBatteryStateChanged(android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState) throws android.os.RemoteException;

    public static class Default implements android.hardware.input.IInputDeviceBatteryListener {
        @Override // android.hardware.input.IInputDeviceBatteryListener
        public void onBatteryStateChanged(android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.input.IInputDeviceBatteryListener {
        static final int TRANSACTION_onBatteryStateChanged = 1;

        public Stub() {
            attachInterface(this, android.hardware.input.IInputDeviceBatteryListener.DESCRIPTOR);
        }

        public static android.hardware.input.IInputDeviceBatteryListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.input.IInputDeviceBatteryListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.input.IInputDeviceBatteryListener)) {
                return (android.hardware.input.IInputDeviceBatteryListener) queryLocalInterface;
            }
            return new android.hardware.input.IInputDeviceBatteryListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onBatteryStateChanged";
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
                parcel.enforceInterface(android.hardware.input.IInputDeviceBatteryListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.input.IInputDeviceBatteryListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState = (android.hardware.input.IInputDeviceBatteryState) parcel.readTypedObject(android.hardware.input.IInputDeviceBatteryState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBatteryStateChanged(iInputDeviceBatteryState);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.input.IInputDeviceBatteryListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.input.IInputDeviceBatteryListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IInputDeviceBatteryListener
            public void onBatteryStateChanged(android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputDeviceBatteryListener.DESCRIPTOR);
                    obtain.writeTypedObject(iInputDeviceBatteryState, 0);
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
