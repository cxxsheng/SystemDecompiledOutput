package android.media.midi;

/* loaded from: classes2.dex */
public interface IMidiDeviceListener extends android.os.IInterface {
    void onDeviceAdded(android.media.midi.MidiDeviceInfo midiDeviceInfo) throws android.os.RemoteException;

    void onDeviceRemoved(android.media.midi.MidiDeviceInfo midiDeviceInfo) throws android.os.RemoteException;

    void onDeviceStatusChanged(android.media.midi.MidiDeviceStatus midiDeviceStatus) throws android.os.RemoteException;

    public static class Default implements android.media.midi.IMidiDeviceListener {
        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceAdded(android.media.midi.MidiDeviceInfo midiDeviceInfo) throws android.os.RemoteException {
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceRemoved(android.media.midi.MidiDeviceInfo midiDeviceInfo) throws android.os.RemoteException {
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceStatusChanged(android.media.midi.MidiDeviceStatus midiDeviceStatus) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.midi.IMidiDeviceListener {
        public static final java.lang.String DESCRIPTOR = "android.media.midi.IMidiDeviceListener";
        static final int TRANSACTION_onDeviceAdded = 1;
        static final int TRANSACTION_onDeviceRemoved = 2;
        static final int TRANSACTION_onDeviceStatusChanged = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.midi.IMidiDeviceListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.midi.IMidiDeviceListener)) {
                return (android.media.midi.IMidiDeviceListener) queryLocalInterface;
            }
            return new android.media.midi.IMidiDeviceListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDeviceAdded";
                case 2:
                    return "onDeviceRemoved";
                case 3:
                    return "onDeviceStatusChanged";
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
                    android.media.midi.MidiDeviceInfo midiDeviceInfo = (android.media.midi.MidiDeviceInfo) parcel.readTypedObject(android.media.midi.MidiDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDeviceAdded(midiDeviceInfo);
                    return true;
                case 2:
                    android.media.midi.MidiDeviceInfo midiDeviceInfo2 = (android.media.midi.MidiDeviceInfo) parcel.readTypedObject(android.media.midi.MidiDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDeviceRemoved(midiDeviceInfo2);
                    return true;
                case 3:
                    android.media.midi.MidiDeviceStatus midiDeviceStatus = (android.media.midi.MidiDeviceStatus) parcel.readTypedObject(android.media.midi.MidiDeviceStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDeviceStatusChanged(midiDeviceStatus);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.midi.IMidiDeviceListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.midi.IMidiDeviceListener.Stub.DESCRIPTOR;
            }

            @Override // android.media.midi.IMidiDeviceListener
            public void onDeviceAdded(android.media.midi.MidiDeviceInfo midiDeviceInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiDeviceListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(midiDeviceInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceListener
            public void onDeviceRemoved(android.media.midi.MidiDeviceInfo midiDeviceInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiDeviceListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(midiDeviceInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceListener
            public void onDeviceStatusChanged(android.media.midi.MidiDeviceStatus midiDeviceStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiDeviceListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(midiDeviceStatus, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
