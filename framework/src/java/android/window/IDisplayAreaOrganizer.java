package android.window;

/* loaded from: classes4.dex */
public interface IDisplayAreaOrganizer extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IDisplayAreaOrganizer";

    void onDisplayAreaAppeared(android.window.DisplayAreaInfo displayAreaInfo, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException;

    void onDisplayAreaInfoChanged(android.window.DisplayAreaInfo displayAreaInfo) throws android.os.RemoteException;

    void onDisplayAreaVanished(android.window.DisplayAreaInfo displayAreaInfo) throws android.os.RemoteException;

    public static class Default implements android.window.IDisplayAreaOrganizer {
        @Override // android.window.IDisplayAreaOrganizer
        public void onDisplayAreaAppeared(android.window.DisplayAreaInfo displayAreaInfo, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
        }

        @Override // android.window.IDisplayAreaOrganizer
        public void onDisplayAreaVanished(android.window.DisplayAreaInfo displayAreaInfo) throws android.os.RemoteException {
        }

        @Override // android.window.IDisplayAreaOrganizer
        public void onDisplayAreaInfoChanged(android.window.DisplayAreaInfo displayAreaInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IDisplayAreaOrganizer {
        static final int TRANSACTION_onDisplayAreaAppeared = 1;
        static final int TRANSACTION_onDisplayAreaInfoChanged = 3;
        static final int TRANSACTION_onDisplayAreaVanished = 2;

        public Stub() {
            attachInterface(this, android.window.IDisplayAreaOrganizer.DESCRIPTOR);
        }

        public static android.window.IDisplayAreaOrganizer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IDisplayAreaOrganizer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IDisplayAreaOrganizer)) {
                return (android.window.IDisplayAreaOrganizer) queryLocalInterface;
            }
            return new android.window.IDisplayAreaOrganizer.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDisplayAreaAppeared";
                case 2:
                    return "onDisplayAreaVanished";
                case 3:
                    return "onDisplayAreaInfoChanged";
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
                parcel.enforceInterface(android.window.IDisplayAreaOrganizer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IDisplayAreaOrganizer.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.window.DisplayAreaInfo displayAreaInfo = (android.window.DisplayAreaInfo) parcel.readTypedObject(android.window.DisplayAreaInfo.CREATOR);
                    android.view.SurfaceControl surfaceControl = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDisplayAreaAppeared(displayAreaInfo, surfaceControl);
                    return true;
                case 2:
                    android.window.DisplayAreaInfo displayAreaInfo2 = (android.window.DisplayAreaInfo) parcel.readTypedObject(android.window.DisplayAreaInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDisplayAreaVanished(displayAreaInfo2);
                    return true;
                case 3:
                    android.window.DisplayAreaInfo displayAreaInfo3 = (android.window.DisplayAreaInfo) parcel.readTypedObject(android.window.DisplayAreaInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDisplayAreaInfoChanged(displayAreaInfo3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IDisplayAreaOrganizer {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IDisplayAreaOrganizer.DESCRIPTOR;
            }

            @Override // android.window.IDisplayAreaOrganizer
            public void onDisplayAreaAppeared(android.window.DisplayAreaInfo displayAreaInfo, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IDisplayAreaOrganizer.DESCRIPTOR);
                    obtain.writeTypedObject(displayAreaInfo, 0);
                    obtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizer
            public void onDisplayAreaVanished(android.window.DisplayAreaInfo displayAreaInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IDisplayAreaOrganizer.DESCRIPTOR);
                    obtain.writeTypedObject(displayAreaInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizer
            public void onDisplayAreaInfoChanged(android.window.DisplayAreaInfo displayAreaInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IDisplayAreaOrganizer.DESCRIPTOR);
                    obtain.writeTypedObject(displayAreaInfo, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
