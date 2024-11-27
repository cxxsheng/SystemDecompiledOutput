package android.hardware.usb;

/* loaded from: classes2.dex */
public interface IUsbSerialReader extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.usb.IUsbSerialReader";

    java.lang.String getSerial(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.hardware.usb.IUsbSerialReader {
        @Override // android.hardware.usb.IUsbSerialReader
        public java.lang.String getSerial(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.usb.IUsbSerialReader {
        static final int TRANSACTION_getSerial = 1;

        public Stub() {
            attachInterface(this, android.hardware.usb.IUsbSerialReader.DESCRIPTOR);
        }

        public static android.hardware.usb.IUsbSerialReader asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.usb.IUsbSerialReader.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.usb.IUsbSerialReader)) {
                return (android.hardware.usb.IUsbSerialReader) queryLocalInterface;
            }
            return new android.hardware.usb.IUsbSerialReader.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSerial";
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
                parcel.enforceInterface(android.hardware.usb.IUsbSerialReader.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.usb.IUsbSerialReader.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String serial = getSerial(readString);
                    parcel2.writeNoException();
                    parcel2.writeString(serial);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.usb.IUsbSerialReader {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.usb.IUsbSerialReader.DESCRIPTOR;
            }

            @Override // android.hardware.usb.IUsbSerialReader
            public java.lang.String getSerial(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbSerialReader.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
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
