package android.window;

/* loaded from: classes4.dex */
public interface IWindowlessStartingSurfaceCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IWindowlessStartingSurfaceCallback";

    void onSurfaceAdded(android.view.SurfaceControl surfaceControl) throws android.os.RemoteException;

    public static class Default implements android.window.IWindowlessStartingSurfaceCallback {
        @Override // android.window.IWindowlessStartingSurfaceCallback
        public void onSurfaceAdded(android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IWindowlessStartingSurfaceCallback {
        static final int TRANSACTION_onSurfaceAdded = 1;

        public Stub() {
            attachInterface(this, android.window.IWindowlessStartingSurfaceCallback.DESCRIPTOR);
        }

        public static android.window.IWindowlessStartingSurfaceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IWindowlessStartingSurfaceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IWindowlessStartingSurfaceCallback)) {
                return (android.window.IWindowlessStartingSurfaceCallback) queryLocalInterface;
            }
            return new android.window.IWindowlessStartingSurfaceCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSurfaceAdded";
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
                parcel.enforceInterface(android.window.IWindowlessStartingSurfaceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IWindowlessStartingSurfaceCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.SurfaceControl surfaceControl = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSurfaceAdded(surfaceControl);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IWindowlessStartingSurfaceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IWindowlessStartingSurfaceCallback.DESCRIPTOR;
            }

            @Override // android.window.IWindowlessStartingSurfaceCallback
            public void onSurfaceAdded(android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowlessStartingSurfaceCallback.DESCRIPTOR);
                    obtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
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
