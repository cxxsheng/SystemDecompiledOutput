package android.hardware.hdmi;

/* loaded from: classes2.dex */
public interface IHdmiControlStatusChangeListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.hdmi.IHdmiControlStatusChangeListener";

    void onStatusChange(int i, boolean z) throws android.os.RemoteException;

    public static class Default implements android.hardware.hdmi.IHdmiControlStatusChangeListener {
        @Override // android.hardware.hdmi.IHdmiControlStatusChangeListener
        public void onStatusChange(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.hdmi.IHdmiControlStatusChangeListener {
        static final int TRANSACTION_onStatusChange = 1;

        public Stub() {
            attachInterface(this, android.hardware.hdmi.IHdmiControlStatusChangeListener.DESCRIPTOR);
        }

        public static android.hardware.hdmi.IHdmiControlStatusChangeListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.hdmi.IHdmiControlStatusChangeListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.hdmi.IHdmiControlStatusChangeListener)) {
                return (android.hardware.hdmi.IHdmiControlStatusChangeListener) queryLocalInterface;
            }
            return new android.hardware.hdmi.IHdmiControlStatusChangeListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStatusChange";
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
                parcel.enforceInterface(android.hardware.hdmi.IHdmiControlStatusChangeListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.hdmi.IHdmiControlStatusChangeListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onStatusChange(readInt, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.hdmi.IHdmiControlStatusChangeListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.hdmi.IHdmiControlStatusChangeListener.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiControlStatusChangeListener
            public void onStatusChange(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlStatusChangeListener.DESCRIPTOR);
                    obtain.writeInt(i);
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
