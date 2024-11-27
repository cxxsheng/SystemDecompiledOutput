package android.media.midi;

/* loaded from: classes2.dex */
public interface IMidiDeviceOpenCallback extends android.os.IInterface {
    void onDeviceOpened(android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.media.midi.IMidiDeviceOpenCallback {
        @Override // android.media.midi.IMidiDeviceOpenCallback
        public void onDeviceOpened(android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.midi.IMidiDeviceOpenCallback {
        public static final java.lang.String DESCRIPTOR = "android.media.midi.IMidiDeviceOpenCallback";
        static final int TRANSACTION_onDeviceOpened = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.midi.IMidiDeviceOpenCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.midi.IMidiDeviceOpenCallback)) {
                return (android.media.midi.IMidiDeviceOpenCallback) queryLocalInterface;
            }
            return new android.media.midi.IMidiDeviceOpenCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDeviceOpened";
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
                    android.media.midi.IMidiDeviceServer asInterface = android.media.midi.IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onDeviceOpened(asInterface, readStrongBinder);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.midi.IMidiDeviceOpenCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.midi.IMidiDeviceOpenCallback.Stub.DESCRIPTOR;
            }

            @Override // android.media.midi.IMidiDeviceOpenCallback
            public void onDeviceOpened(android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.midi.IMidiDeviceOpenCallback.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMidiDeviceServer);
                    obtain.writeStrongBinder(iBinder);
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
