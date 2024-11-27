package android.hardware.input;

/* loaded from: classes2.dex */
public interface IKeyboardBacklightListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.input.IKeyboardBacklightListener";

    void onBrightnessChanged(int i, android.hardware.input.IKeyboardBacklightState iKeyboardBacklightState, boolean z) throws android.os.RemoteException;

    public static class Default implements android.hardware.input.IKeyboardBacklightListener {
        @Override // android.hardware.input.IKeyboardBacklightListener
        public void onBrightnessChanged(int i, android.hardware.input.IKeyboardBacklightState iKeyboardBacklightState, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.input.IKeyboardBacklightListener {
        static final int TRANSACTION_onBrightnessChanged = 1;

        public Stub() {
            attachInterface(this, android.hardware.input.IKeyboardBacklightListener.DESCRIPTOR);
        }

        public static android.hardware.input.IKeyboardBacklightListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.input.IKeyboardBacklightListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.input.IKeyboardBacklightListener)) {
                return (android.hardware.input.IKeyboardBacklightListener) queryLocalInterface;
            }
            return new android.hardware.input.IKeyboardBacklightListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onBrightnessChanged";
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
                parcel.enforceInterface(android.hardware.input.IKeyboardBacklightListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.input.IKeyboardBacklightListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.hardware.input.IKeyboardBacklightState iKeyboardBacklightState = (android.hardware.input.IKeyboardBacklightState) parcel.readTypedObject(android.hardware.input.IKeyboardBacklightState.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onBrightnessChanged(readInt, iKeyboardBacklightState, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.input.IKeyboardBacklightListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.input.IKeyboardBacklightListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IKeyboardBacklightListener
            public void onBrightnessChanged(int i, android.hardware.input.IKeyboardBacklightState iKeyboardBacklightState, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IKeyboardBacklightListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(iKeyboardBacklightState, 0);
                    obtain.writeBoolean(z);
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