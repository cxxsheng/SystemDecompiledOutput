package android.hardware.face;

/* loaded from: classes2.dex */
public interface IFaceAuthenticatorsRegisteredCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.face.IFaceAuthenticatorsRegisteredCallback";

    void onAllAuthenticatorsRegistered(java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list) throws android.os.RemoteException;

    public static class Default implements android.hardware.face.IFaceAuthenticatorsRegisteredCallback {
        @Override // android.hardware.face.IFaceAuthenticatorsRegisteredCallback
        public void onAllAuthenticatorsRegistered(java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.face.IFaceAuthenticatorsRegisteredCallback {
        static final int TRANSACTION_onAllAuthenticatorsRegistered = 1;

        public Stub() {
            attachInterface(this, android.hardware.face.IFaceAuthenticatorsRegisteredCallback.DESCRIPTOR);
        }

        public static android.hardware.face.IFaceAuthenticatorsRegisteredCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.face.IFaceAuthenticatorsRegisteredCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.face.IFaceAuthenticatorsRegisteredCallback)) {
                return (android.hardware.face.IFaceAuthenticatorsRegisteredCallback) queryLocalInterface;
            }
            return new android.hardware.face.IFaceAuthenticatorsRegisteredCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAllAuthenticatorsRegistered";
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
                parcel.enforceInterface(android.hardware.face.IFaceAuthenticatorsRegisteredCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.face.IFaceAuthenticatorsRegisteredCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.hardware.face.FaceSensorPropertiesInternal.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAllAuthenticatorsRegistered(createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.face.IFaceAuthenticatorsRegisteredCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.face.IFaceAuthenticatorsRegisteredCallback.DESCRIPTOR;
            }

            @Override // android.hardware.face.IFaceAuthenticatorsRegisteredCallback
            public void onAllAuthenticatorsRegistered(java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceAuthenticatorsRegisteredCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
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
