package android.hardware.hdmi;

/* loaded from: classes2.dex */
public interface IHdmiHotplugEventListener extends android.os.IInterface {
    void onReceived(android.hardware.hdmi.HdmiHotplugEvent hdmiHotplugEvent) throws android.os.RemoteException;

    public static class Default implements android.hardware.hdmi.IHdmiHotplugEventListener {
        @Override // android.hardware.hdmi.IHdmiHotplugEventListener
        public void onReceived(android.hardware.hdmi.HdmiHotplugEvent hdmiHotplugEvent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.hdmi.IHdmiHotplugEventListener {
        public static final java.lang.String DESCRIPTOR = "android.hardware.hdmi.IHdmiHotplugEventListener";
        static final int TRANSACTION_onReceived = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.hdmi.IHdmiHotplugEventListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.hdmi.IHdmiHotplugEventListener)) {
                return (android.hardware.hdmi.IHdmiHotplugEventListener) queryLocalInterface;
            }
            return new android.hardware.hdmi.IHdmiHotplugEventListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onReceived";
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
                    android.hardware.hdmi.HdmiHotplugEvent hdmiHotplugEvent = (android.hardware.hdmi.HdmiHotplugEvent) parcel.readTypedObject(android.hardware.hdmi.HdmiHotplugEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onReceived(hdmiHotplugEvent);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.hdmi.IHdmiHotplugEventListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.hdmi.IHdmiHotplugEventListener.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiHotplugEventListener
            public void onReceived(android.hardware.hdmi.HdmiHotplugEvent hdmiHotplugEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiHotplugEventListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(hdmiHotplugEvent, 0);
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
