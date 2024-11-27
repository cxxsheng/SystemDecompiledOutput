package android.hardware.hdmi;

/* loaded from: classes2.dex */
public interface IHdmiInputChangeListener extends android.os.IInterface {
    void onChanged(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) throws android.os.RemoteException;

    public static class Default implements android.hardware.hdmi.IHdmiInputChangeListener {
        @Override // android.hardware.hdmi.IHdmiInputChangeListener
        public void onChanged(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.hdmi.IHdmiInputChangeListener {
        public static final java.lang.String DESCRIPTOR = "android.hardware.hdmi.IHdmiInputChangeListener";
        static final int TRANSACTION_onChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.hdmi.IHdmiInputChangeListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.hdmi.IHdmiInputChangeListener)) {
                return (android.hardware.hdmi.IHdmiInputChangeListener) queryLocalInterface;
            }
            return new android.hardware.hdmi.IHdmiInputChangeListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onChanged";
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
                    parcel.enforceNoDataAvail();
                    onChanged(hdmiDeviceInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.hdmi.IHdmiInputChangeListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.hdmi.IHdmiInputChangeListener.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiInputChangeListener
            public void onChanged(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiInputChangeListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(hdmiDeviceInfo, 0);
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
