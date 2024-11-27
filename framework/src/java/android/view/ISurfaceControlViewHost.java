package android.view;

/* loaded from: classes4.dex */
public interface ISurfaceControlViewHost extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.ISurfaceControlViewHost";

    void attachParentInterface(android.view.ISurfaceControlViewHostParent iSurfaceControlViewHostParent) throws android.os.RemoteException;

    android.window.ISurfaceSyncGroup getSurfaceSyncGroup() throws android.os.RemoteException;

    void onConfigurationChanged(android.content.res.Configuration configuration) throws android.os.RemoteException;

    void onDispatchDetachedFromWindow() throws android.os.RemoteException;

    void onInsetsChanged(android.view.InsetsState insetsState, android.graphics.Rect rect) throws android.os.RemoteException;

    public static class Default implements android.view.ISurfaceControlViewHost {
        @Override // android.view.ISurfaceControlViewHost
        public void onConfigurationChanged(android.content.res.Configuration configuration) throws android.os.RemoteException {
        }

        @Override // android.view.ISurfaceControlViewHost
        public void onDispatchDetachedFromWindow() throws android.os.RemoteException {
        }

        @Override // android.view.ISurfaceControlViewHost
        public void onInsetsChanged(android.view.InsetsState insetsState, android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.view.ISurfaceControlViewHost
        public android.window.ISurfaceSyncGroup getSurfaceSyncGroup() throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.ISurfaceControlViewHost
        public void attachParentInterface(android.view.ISurfaceControlViewHostParent iSurfaceControlViewHostParent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.ISurfaceControlViewHost {
        static final int TRANSACTION_attachParentInterface = 5;
        static final int TRANSACTION_getSurfaceSyncGroup = 4;
        static final int TRANSACTION_onConfigurationChanged = 1;
        static final int TRANSACTION_onDispatchDetachedFromWindow = 2;
        static final int TRANSACTION_onInsetsChanged = 3;

        public Stub() {
            attachInterface(this, android.view.ISurfaceControlViewHost.DESCRIPTOR);
        }

        public static android.view.ISurfaceControlViewHost asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.ISurfaceControlViewHost.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.ISurfaceControlViewHost)) {
                return (android.view.ISurfaceControlViewHost) queryLocalInterface;
            }
            return new android.view.ISurfaceControlViewHost.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConfigurationChanged";
                case 2:
                    return "onDispatchDetachedFromWindow";
                case 3:
                    return "onInsetsChanged";
                case 4:
                    return "getSurfaceSyncGroup";
                case 5:
                    return "attachParentInterface";
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
                parcel.enforceInterface(android.view.ISurfaceControlViewHost.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.ISurfaceControlViewHost.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.content.res.Configuration configuration = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConfigurationChanged(configuration);
                    return true;
                case 2:
                    onDispatchDetachedFromWindow();
                    return true;
                case 3:
                    android.view.InsetsState insetsState = (android.view.InsetsState) parcel.readTypedObject(android.view.InsetsState.CREATOR);
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onInsetsChanged(insetsState, rect);
                    return true;
                case 4:
                    android.window.ISurfaceSyncGroup surfaceSyncGroup = getSurfaceSyncGroup();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(surfaceSyncGroup);
                    return true;
                case 5:
                    android.view.ISurfaceControlViewHostParent asInterface = android.view.ISurfaceControlViewHostParent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachParentInterface(asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.ISurfaceControlViewHost {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.ISurfaceControlViewHost.DESCRIPTOR;
            }

            @Override // android.view.ISurfaceControlViewHost
            public void onConfigurationChanged(android.content.res.Configuration configuration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.ISurfaceControlViewHost.DESCRIPTOR);
                    obtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.ISurfaceControlViewHost
            public void onDispatchDetachedFromWindow() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.ISurfaceControlViewHost.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.ISurfaceControlViewHost
            public void onInsetsChanged(android.view.InsetsState insetsState, android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.ISurfaceControlViewHost.DESCRIPTOR);
                    obtain.writeTypedObject(insetsState, 0);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.ISurfaceControlViewHost
            public android.window.ISurfaceSyncGroup getSurfaceSyncGroup() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.ISurfaceControlViewHost.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.window.ISurfaceSyncGroup.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.ISurfaceControlViewHost
            public void attachParentInterface(android.view.ISurfaceControlViewHostParent iSurfaceControlViewHostParent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.ISurfaceControlViewHost.DESCRIPTOR);
                    obtain.writeStrongInterface(iSurfaceControlViewHostParent);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
