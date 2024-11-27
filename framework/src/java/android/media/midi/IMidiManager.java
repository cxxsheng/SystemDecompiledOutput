package android.media.midi;

/* loaded from: classes2.dex */
public interface IMidiManager extends android.os.IInterface {
    void closeDevice(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException;

    android.media.midi.MidiDeviceStatus getDeviceStatus(android.media.midi.MidiDeviceInfo midiDeviceInfo) throws android.os.RemoteException;

    android.media.midi.MidiDeviceInfo[] getDevices() throws android.os.RemoteException;

    android.media.midi.MidiDeviceInfo[] getDevicesForTransport(int i) throws android.os.RemoteException;

    android.media.midi.MidiDeviceInfo getServiceDeviceInfo(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void openBluetoothDevice(android.os.IBinder iBinder, android.bluetooth.BluetoothDevice bluetoothDevice, android.media.midi.IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws android.os.RemoteException;

    void openDevice(android.os.IBinder iBinder, android.media.midi.MidiDeviceInfo midiDeviceInfo, android.media.midi.IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws android.os.RemoteException;

    android.media.midi.MidiDeviceInfo registerDeviceServer(android.media.midi.IMidiDeviceServer iMidiDeviceServer, int i, int i2, java.lang.String[] strArr, java.lang.String[] strArr2, android.os.Bundle bundle, int i3, int i4) throws android.os.RemoteException;

    void registerListener(android.os.IBinder iBinder, android.media.midi.IMidiDeviceListener iMidiDeviceListener) throws android.os.RemoteException;

    void setDeviceStatus(android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.media.midi.MidiDeviceStatus midiDeviceStatus) throws android.os.RemoteException;

    void unregisterDeviceServer(android.media.midi.IMidiDeviceServer iMidiDeviceServer) throws android.os.RemoteException;

    void unregisterListener(android.os.IBinder iBinder, android.media.midi.IMidiDeviceListener iMidiDeviceListener) throws android.os.RemoteException;

    void updateTotalBytes(android.media.midi.IMidiDeviceServer iMidiDeviceServer, int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.media.midi.IMidiManager {
        @Override // android.media.midi.IMidiManager
        public android.media.midi.MidiDeviceInfo[] getDevices() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiManager
        public android.media.midi.MidiDeviceInfo[] getDevicesForTransport(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiManager
        public void registerListener(android.os.IBinder iBinder, android.media.midi.IMidiDeviceListener iMidiDeviceListener) throws android.os.RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public void unregisterListener(android.os.IBinder iBinder, android.media.midi.IMidiDeviceListener iMidiDeviceListener) throws android.os.RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public void openDevice(android.os.IBinder iBinder, android.media.midi.MidiDeviceInfo midiDeviceInfo, android.media.midi.IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws android.os.RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public void openBluetoothDevice(android.os.IBinder iBinder, android.bluetooth.BluetoothDevice bluetoothDevice, android.media.midi.IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws android.os.RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public void closeDevice(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public android.media.midi.MidiDeviceInfo registerDeviceServer(android.media.midi.IMidiDeviceServer iMidiDeviceServer, int i, int i2, java.lang.String[] strArr, java.lang.String[] strArr2, android.os.Bundle bundle, int i3, int i4) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiManager
        public void unregisterDeviceServer(android.media.midi.IMidiDeviceServer iMidiDeviceServer) throws android.os.RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public android.media.midi.MidiDeviceInfo getServiceDeviceInfo(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiManager
        public android.media.midi.MidiDeviceStatus getDeviceStatus(android.media.midi.MidiDeviceInfo midiDeviceInfo) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiManager
        public void setDeviceStatus(android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.media.midi.MidiDeviceStatus midiDeviceStatus) throws android.os.RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public void updateTotalBytes(android.media.midi.IMidiDeviceServer iMidiDeviceServer, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.midi.IMidiManager {
        public static final java.lang.String DESCRIPTOR = "android.media.midi.IMidiManager";
        static final int TRANSACTION_closeDevice = 7;
        static final int TRANSACTION_getDeviceStatus = 11;
        static final int TRANSACTION_getDevices = 1;
        static final int TRANSACTION_getDevicesForTransport = 2;
        static final int TRANSACTION_getServiceDeviceInfo = 10;
        static final int TRANSACTION_openBluetoothDevice = 6;
        static final int TRANSACTION_openDevice = 5;
        static final int TRANSACTION_registerDeviceServer = 8;
        static final int TRANSACTION_registerListener = 3;
        static final int TRANSACTION_setDeviceStatus = 12;
        static final int TRANSACTION_unregisterDeviceServer = 9;
        static final int TRANSACTION_unregisterListener = 4;
        static final int TRANSACTION_updateTotalBytes = 13;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.midi.IMidiManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.midi.IMidiManager)) {
                return (android.media.midi.IMidiManager) queryLocalInterface;
            }
            return new android.media.midi.IMidiManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDevices";
                case 2:
                    return "getDevicesForTransport";
                case 3:
                    return "registerListener";
                case 4:
                    return "unregisterListener";
                case 5:
                    return "openDevice";
                case 6:
                    return "openBluetoothDevice";
                case 7:
                    return "closeDevice";
                case 8:
                    return "registerDeviceServer";
                case 9:
                    return "unregisterDeviceServer";
                case 10:
                    return "getServiceDeviceInfo";
                case 11:
                    return "getDeviceStatus";
                case 12:
                    return "setDeviceStatus";
                case 13:
                    return "updateTotalBytes";
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
                    android.media.midi.MidiDeviceInfo[] devices = getDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devices, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.midi.MidiDeviceInfo[] devicesForTransport = getDevicesForTransport(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devicesForTransport, 1);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.media.midi.IMidiDeviceListener asInterface = android.media.midi.IMidiDeviceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerListener(readStrongBinder, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.media.midi.IMidiDeviceListener asInterface2 = android.media.midi.IMidiDeviceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterListener(readStrongBinder2, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    android.media.midi.MidiDeviceInfo midiDeviceInfo = (android.media.midi.MidiDeviceInfo) parcel.readTypedObject(android.media.midi.MidiDeviceInfo.CREATOR);
                    android.media.midi.IMidiDeviceOpenCallback asInterface3 = android.media.midi.IMidiDeviceOpenCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    openDevice(readStrongBinder3, midiDeviceInfo, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    android.bluetooth.BluetoothDevice bluetoothDevice = (android.bluetooth.BluetoothDevice) parcel.readTypedObject(android.bluetooth.BluetoothDevice.CREATOR);
                    android.media.midi.IMidiDeviceOpenCallback asInterface4 = android.media.midi.IMidiDeviceOpenCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    openBluetoothDevice(readStrongBinder4, bluetoothDevice, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closeDevice(readStrongBinder5, readStrongBinder6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.media.midi.IMidiDeviceServer asInterface5 = android.media.midi.IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.midi.MidiDeviceInfo registerDeviceServer = registerDeviceServer(asInterface5, readInt2, readInt3, createStringArray, createStringArray2, bundle, readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerDeviceServer, 1);
                    return true;
                case 9:
                    android.media.midi.IMidiDeviceServer asInterface6 = android.media.midi.IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDeviceServer(asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.media.midi.MidiDeviceInfo serviceDeviceInfo = getServiceDeviceInfo(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceDeviceInfo, 1);
                    return true;
                case 11:
                    android.media.midi.MidiDeviceInfo midiDeviceInfo2 = (android.media.midi.MidiDeviceInfo) parcel.readTypedObject(android.media.midi.MidiDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.media.midi.MidiDeviceStatus deviceStatus = getDeviceStatus(midiDeviceInfo2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceStatus, 1);
                    return true;
                case 12:
                    android.media.midi.IMidiDeviceServer asInterface7 = android.media.midi.IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    android.media.midi.MidiDeviceStatus midiDeviceStatus = (android.media.midi.MidiDeviceStatus) parcel.readTypedObject(android.media.midi.MidiDeviceStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceStatus(asInterface7, midiDeviceStatus);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.media.midi.IMidiDeviceServer asInterface8 = android.media.midi.IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateTotalBytes(asInterface8, readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.midi.IMidiManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.midi.IMidiManager.Stub.DESCRIPTOR;
            }

            @Override // android.media.midi.IMidiManager
            public android.media.midi.MidiDeviceInfo[] getDevices() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.midi.MidiDeviceInfo[]) obtain2.createTypedArray(android.media.midi.MidiDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public android.media.midi.MidiDeviceInfo[] getDevicesForTransport(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.midi.MidiDeviceInfo[]) obtain2.createTypedArray(android.media.midi.MidiDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void registerListener(android.os.IBinder iBinder, android.media.midi.IMidiDeviceListener iMidiDeviceListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iMidiDeviceListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void unregisterListener(android.os.IBinder iBinder, android.media.midi.IMidiDeviceListener iMidiDeviceListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iMidiDeviceListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void openDevice(android.os.IBinder iBinder, android.media.midi.MidiDeviceInfo midiDeviceInfo, android.media.midi.IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(midiDeviceInfo, 0);
                    obtain.writeStrongInterface(iMidiDeviceOpenCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void openBluetoothDevice(android.os.IBinder iBinder, android.bluetooth.BluetoothDevice bluetoothDevice, android.media.midi.IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bluetoothDevice, 0);
                    obtain.writeStrongInterface(iMidiDeviceOpenCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void closeDevice(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public android.media.midi.MidiDeviceInfo registerDeviceServer(android.media.midi.IMidiDeviceServer iMidiDeviceServer, int i, int i2, java.lang.String[] strArr, java.lang.String[] strArr2, android.os.Bundle bundle, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMidiDeviceServer);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    obtain.writeStringArray(strArr2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.midi.MidiDeviceInfo) obtain2.readTypedObject(android.media.midi.MidiDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void unregisterDeviceServer(android.media.midi.IMidiDeviceServer iMidiDeviceServer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMidiDeviceServer);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public android.media.midi.MidiDeviceInfo getServiceDeviceInfo(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.midi.MidiDeviceInfo) obtain2.readTypedObject(android.media.midi.MidiDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public android.media.midi.MidiDeviceStatus getDeviceStatus(android.media.midi.MidiDeviceInfo midiDeviceInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(midiDeviceInfo, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.midi.MidiDeviceStatus) obtain2.readTypedObject(android.media.midi.MidiDeviceStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void setDeviceStatus(android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.media.midi.MidiDeviceStatus midiDeviceStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMidiDeviceServer);
                    obtain.writeTypedObject(midiDeviceStatus, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void updateTotalBytes(android.media.midi.IMidiDeviceServer iMidiDeviceServer, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMidiDeviceServer);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
