package android.hardware.usb;

/* loaded from: classes2.dex */
public interface IUsbOperationInternal extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.usb.IUsbOperationInternal";

    void onOperationComplete(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.usb.IUsbOperationInternal {
        @Override // android.hardware.usb.IUsbOperationInternal
        public void onOperationComplete(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.usb.IUsbOperationInternal {
        static final int TRANSACTION_onOperationComplete = 1;

        public Stub() {
            attachInterface(this, android.hardware.usb.IUsbOperationInternal.DESCRIPTOR);
        }

        public static android.hardware.usb.IUsbOperationInternal asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.usb.IUsbOperationInternal.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.usb.IUsbOperationInternal)) {
                return (android.hardware.usb.IUsbOperationInternal) queryLocalInterface;
            }
            return new android.hardware.usb.IUsbOperationInternal.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onOperationComplete";
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
                parcel.enforceInterface(android.hardware.usb.IUsbOperationInternal.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.usb.IUsbOperationInternal.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onOperationComplete(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.usb.IUsbOperationInternal {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.usb.IUsbOperationInternal.DESCRIPTOR;
            }

            @Override // android.hardware.usb.IUsbOperationInternal
            public void onOperationComplete(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbOperationInternal.DESCRIPTOR);
                    obtain.writeInt(i);
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
