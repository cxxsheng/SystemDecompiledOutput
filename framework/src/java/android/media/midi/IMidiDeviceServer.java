package android.media.midi;

/* loaded from: classes2.dex */
public interface IMidiDeviceServer extends android.os.IInterface {
    void closeDevice() throws android.os.RemoteException;

    void closePort(android.os.IBinder iBinder) throws android.os.RemoteException;

    int connectPorts(android.os.IBinder iBinder, java.io.FileDescriptor fileDescriptor, int i) throws android.os.RemoteException;

    android.media.midi.MidiDeviceInfo getDeviceInfo() throws android.os.RemoteException;

    java.io.FileDescriptor openInputPort(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    java.io.FileDescriptor openOutputPort(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void setDeviceInfo(android.media.midi.MidiDeviceInfo midiDeviceInfo) throws android.os.RemoteException;

    public static class Default implements android.media.midi.IMidiDeviceServer {
        @Override // android.media.midi.IMidiDeviceServer
        public java.io.FileDescriptor openInputPort(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiDeviceServer
        public java.io.FileDescriptor openOutputPort(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiDeviceServer
        public void closePort(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.media.midi.IMidiDeviceServer
        public void closeDevice() throws android.os.RemoteException {
        }

        @Override // android.media.midi.IMidiDeviceServer
        public int connectPorts(android.os.IBinder iBinder, java.io.FileDescriptor fileDescriptor, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.midi.IMidiDeviceServer
        public android.media.midi.MidiDeviceInfo getDeviceInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiDeviceServer
        public void setDeviceInfo(android.media.midi.MidiDeviceInfo midiDeviceInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.midi.IMidiDeviceServer {
        public static final java.lang.String DESCRIPTOR = "android.media.midi.IMidiDeviceServer";
        static final int TRANSACTION_closeDevice = 4;
        static final int TRANSACTION_closePort = 3;
        static final int TRANSACTION_connectPorts = 5;
        static final int TRANSACTION_getDeviceInfo = 6;
        static final int TRANSACTION_openInputPort = 1;
        static final int TRANSACTION_openOutputPort = 2;
        static final int TRANSACTION_setDeviceInfo = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.midi.IMidiDeviceServer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.midi.IMidiDeviceServer)) {
                return (android.media.midi.IMidiDeviceServer) queryLocalInterface;
            }
            return new android.media.midi.IMidiDeviceServer.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "openInputPort";
                case 2:
                    return "openOutputPort";
                case 3:
                    return "closePort";
                case 4:
                    return "closeDevice";
                case 5:
                    return "connectPorts";
                case 6:
                    return "getDeviceInfo";
                case 7:
                    return "setDeviceInfo";
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
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.io.FileDescriptor openInputPort = openInputPort(readStrongBinder, readInt);
                    parcel2.writeNoException();
                    parcel2.writeRawFileDescriptor(openInputPort);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.io.FileDescriptor openOutputPort = openOutputPort(readStrongBinder2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeRawFileDescriptor(openOutputPort);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closePort(readStrongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    closeDevice();
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    java.io.FileDescriptor readRawFileDescriptor = parcel.readRawFileDescriptor();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int connectPorts = connectPorts(readStrongBinder4, readRawFileDescriptor, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(connectPorts);
                    return true;
                case 6:
                    android.media.midi.MidiDeviceInfo deviceInfo = getDeviceInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceInfo, 1);
                    return true;
                case 7:
                    android.media.midi.MidiDeviceInfo midiDeviceInfo = (android.media.midi.MidiDeviceInfo) parcel.readTypedObject(android.media.midi.MidiDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceInfo(midiDeviceInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.midi.IMidiDeviceServer {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.midi.IMidiDeviceServer.Stub.DESCRIPTOR;
            }

            @Override // android.media.midi.IMidiDeviceServer
            public java.io.FileDescriptor openInputPort(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiDeviceServer.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readRawFileDescriptor();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public java.io.FileDescriptor openOutputPort(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiDeviceServer.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readRawFileDescriptor();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public void closePort(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiDeviceServer.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public void closeDevice() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiDeviceServer.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public int connectPorts(android.os.IBinder iBinder, java.io.FileDescriptor fileDescriptor, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiDeviceServer.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public android.media.midi.MidiDeviceInfo getDeviceInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiDeviceServer.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.midi.MidiDeviceInfo) obtain2.readTypedObject(android.media.midi.MidiDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public void setDeviceInfo(android.media.midi.MidiDeviceInfo midiDeviceInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiDeviceServer.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(midiDeviceInfo, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
