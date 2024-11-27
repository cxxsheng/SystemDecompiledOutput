package android.media.midi;

/* loaded from: classes2.dex */
public interface IBluetoothMidiService extends android.os.IInterface {
    android.os.IBinder addBluetoothDevice(android.bluetooth.BluetoothDevice bluetoothDevice) throws android.os.RemoteException;

    public static class Default implements android.media.midi.IBluetoothMidiService {
        @Override // android.media.midi.IBluetoothMidiService
        public android.os.IBinder addBluetoothDevice(android.bluetooth.BluetoothDevice bluetoothDevice) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.midi.IBluetoothMidiService {
        public static final java.lang.String DESCRIPTOR = "android.media.midi.IBluetoothMidiService";
        static final int TRANSACTION_addBluetoothDevice = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.midi.IBluetoothMidiService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.midi.IBluetoothMidiService)) {
                return (android.media.midi.IBluetoothMidiService) queryLocalInterface;
            }
            return new android.media.midi.IBluetoothMidiService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addBluetoothDevice";
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
                    android.bluetooth.BluetoothDevice bluetoothDevice = (android.bluetooth.BluetoothDevice) parcel.readTypedObject(android.bluetooth.BluetoothDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.IBinder addBluetoothDevice = addBluetoothDevice(bluetoothDevice);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(addBluetoothDevice);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.midi.IBluetoothMidiService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.midi.IBluetoothMidiService.Stub.DESCRIPTOR;
            }

            @Override // android.media.midi.IBluetoothMidiService
            public android.os.IBinder addBluetoothDevice(android.bluetooth.BluetoothDevice bluetoothDevice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IBluetoothMidiService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bluetoothDevice, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
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
