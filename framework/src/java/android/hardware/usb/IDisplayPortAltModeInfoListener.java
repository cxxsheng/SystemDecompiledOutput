package android.hardware.usb;

/* loaded from: classes2.dex */
public interface IDisplayPortAltModeInfoListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.usb.IDisplayPortAltModeInfoListener";

    void onDisplayPortAltModeInfoChanged(java.lang.String str, android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo) throws android.os.RemoteException;

    public static class Default implements android.hardware.usb.IDisplayPortAltModeInfoListener {
        @Override // android.hardware.usb.IDisplayPortAltModeInfoListener
        public void onDisplayPortAltModeInfoChanged(java.lang.String str, android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.usb.IDisplayPortAltModeInfoListener {
        static final int TRANSACTION_onDisplayPortAltModeInfoChanged = 1;

        public Stub() {
            attachInterface(this, android.hardware.usb.IDisplayPortAltModeInfoListener.DESCRIPTOR);
        }

        public static android.hardware.usb.IDisplayPortAltModeInfoListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.usb.IDisplayPortAltModeInfoListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.usb.IDisplayPortAltModeInfoListener)) {
                return (android.hardware.usb.IDisplayPortAltModeInfoListener) queryLocalInterface;
            }
            return new android.hardware.usb.IDisplayPortAltModeInfoListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDisplayPortAltModeInfoChanged";
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
                parcel.enforceInterface(android.hardware.usb.IDisplayPortAltModeInfoListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.usb.IDisplayPortAltModeInfoListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo = (android.hardware.usb.DisplayPortAltModeInfo) parcel.readTypedObject(android.hardware.usb.DisplayPortAltModeInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDisplayPortAltModeInfoChanged(readString, displayPortAltModeInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.usb.IDisplayPortAltModeInfoListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.usb.IDisplayPortAltModeInfoListener.DESCRIPTOR;
            }

            @Override // android.hardware.usb.IDisplayPortAltModeInfoListener
            public void onDisplayPortAltModeInfoChanged(java.lang.String str, android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IDisplayPortAltModeInfoListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(displayPortAltModeInfo, 0);
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
