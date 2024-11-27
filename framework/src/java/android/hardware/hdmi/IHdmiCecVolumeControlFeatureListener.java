package android.hardware.hdmi;

/* loaded from: classes2.dex */
public interface IHdmiCecVolumeControlFeatureListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener";

    void onHdmiCecVolumeControlFeature(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener {
        @Override // android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener
        public void onHdmiCecVolumeControlFeature(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener {
        static final int TRANSACTION_onHdmiCecVolumeControlFeature = 1;

        public Stub() {
            attachInterface(this, android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
        }

        public static android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener)) {
                return (android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener) queryLocalInterface;
            }
            return new android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onHdmiCecVolumeControlFeature";
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
                parcel.enforceInterface(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onHdmiCecVolumeControlFeature(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener
            public void onHdmiCecVolumeControlFeature(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
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
