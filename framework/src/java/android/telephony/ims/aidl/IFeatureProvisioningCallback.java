package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IFeatureProvisioningCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IFeatureProvisioningCallback";

    void onFeatureProvisioningChanged(int i, int i2, boolean z) throws android.os.RemoteException;

    void onRcsFeatureProvisioningChanged(int i, int i2, boolean z) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IFeatureProvisioningCallback {
        @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
        public void onFeatureProvisioningChanged(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
        public void onRcsFeatureProvisioningChanged(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IFeatureProvisioningCallback {
        static final int TRANSACTION_onFeatureProvisioningChanged = 1;
        static final int TRANSACTION_onRcsFeatureProvisioningChanged = 2;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IFeatureProvisioningCallback.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IFeatureProvisioningCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IFeatureProvisioningCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IFeatureProvisioningCallback)) {
                return (android.telephony.ims.aidl.IFeatureProvisioningCallback) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IFeatureProvisioningCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onFeatureProvisioningChanged";
                case 2:
                    return "onRcsFeatureProvisioningChanged";
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
                parcel.enforceInterface(android.telephony.ims.aidl.IFeatureProvisioningCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IFeatureProvisioningCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onFeatureProvisioningChanged(readInt, readInt2, readBoolean);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onRcsFeatureProvisioningChanged(readInt3, readInt4, readBoolean2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IFeatureProvisioningCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IFeatureProvisioningCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
            public void onFeatureProvisioningChanged(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IFeatureProvisioningCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
            public void onRcsFeatureProvisioningChanged(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IFeatureProvisioningCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
