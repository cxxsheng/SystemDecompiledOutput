package android.hardware.hdmi;

/* loaded from: classes2.dex */
public interface IHdmiDeviceEventListener extends android.os.IInterface {
    void onStatusChanged(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.hdmi.IHdmiDeviceEventListener {
        @Override // android.hardware.hdmi.IHdmiDeviceEventListener
        public void onStatusChanged(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.hdmi.IHdmiDeviceEventListener {
        public static final java.lang.String DESCRIPTOR = "android.hardware.hdmi.IHdmiDeviceEventListener";
        static final int TRANSACTION_onStatusChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.hdmi.IHdmiDeviceEventListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.hdmi.IHdmiDeviceEventListener)) {
                return (android.hardware.hdmi.IHdmiDeviceEventListener) queryLocalInterface;
            }
            return new android.hardware.hdmi.IHdmiDeviceEventListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStatusChanged";
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
                    android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo = (android.hardware.hdmi.HdmiDeviceInfo) parcel.readTypedObject(android.hardware.hdmi.HdmiDeviceInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStatusChanged(hdmiDeviceInfo, readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.hdmi.IHdmiDeviceEventListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.hdmi.IHdmiDeviceEventListener.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiDeviceEventListener
            public void onStatusChanged(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiDeviceEventListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(hdmiDeviceInfo, 0);
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
