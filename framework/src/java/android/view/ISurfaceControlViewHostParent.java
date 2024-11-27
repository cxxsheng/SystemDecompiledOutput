package android.view;

/* loaded from: classes4.dex */
public interface ISurfaceControlViewHostParent extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.ISurfaceControlViewHostParent";

    void forwardBackKeyToParent(android.view.KeyEvent keyEvent) throws android.os.RemoteException;

    void updateParams(android.view.WindowManager.LayoutParams[] layoutParamsArr) throws android.os.RemoteException;

    public static class Default implements android.view.ISurfaceControlViewHostParent {
        @Override // android.view.ISurfaceControlViewHostParent
        public void updateParams(android.view.WindowManager.LayoutParams[] layoutParamsArr) throws android.os.RemoteException {
        }

        @Override // android.view.ISurfaceControlViewHostParent
        public void forwardBackKeyToParent(android.view.KeyEvent keyEvent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.ISurfaceControlViewHostParent {
        static final int TRANSACTION_forwardBackKeyToParent = 2;
        static final int TRANSACTION_updateParams = 1;

        public Stub() {
            attachInterface(this, android.view.ISurfaceControlViewHostParent.DESCRIPTOR);
        }

        public static android.view.ISurfaceControlViewHostParent asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.ISurfaceControlViewHostParent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.ISurfaceControlViewHostParent)) {
                return (android.view.ISurfaceControlViewHostParent) queryLocalInterface;
            }
            return new android.view.ISurfaceControlViewHostParent.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateParams";
                case 2:
                    return "forwardBackKeyToParent";
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
                parcel.enforceInterface(android.view.ISurfaceControlViewHostParent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.ISurfaceControlViewHostParent.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.WindowManager.LayoutParams[] layoutParamsArr = (android.view.WindowManager.LayoutParams[]) parcel.createTypedArray(android.view.WindowManager.LayoutParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateParams(layoutParamsArr);
                    return true;
                case 2:
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    forwardBackKeyToParent(keyEvent);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.ISurfaceControlViewHostParent {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.ISurfaceControlViewHostParent.DESCRIPTOR;
            }

            @Override // android.view.ISurfaceControlViewHostParent
            public void updateParams(android.view.WindowManager.LayoutParams[] layoutParamsArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.ISurfaceControlViewHostParent.DESCRIPTOR);
                    obtain.writeTypedArray(layoutParamsArr, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.ISurfaceControlViewHostParent
            public void forwardBackKeyToParent(android.view.KeyEvent keyEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.ISurfaceControlViewHostParent.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
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
