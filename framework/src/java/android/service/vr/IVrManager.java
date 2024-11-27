package android.service.vr;

/* loaded from: classes3.dex */
public interface IVrManager extends android.os.IInterface {
    boolean getPersistentVrModeEnabled() throws android.os.RemoteException;

    int getVr2dDisplayId() throws android.os.RemoteException;

    boolean getVrModeState() throws android.os.RemoteException;

    void registerListener(android.service.vr.IVrStateCallbacks iVrStateCallbacks) throws android.os.RemoteException;

    void registerPersistentVrStateListener(android.service.vr.IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws android.os.RemoteException;

    void setAndBindCompositor(java.lang.String str) throws android.os.RemoteException;

    void setPersistentVrModeEnabled(boolean z) throws android.os.RemoteException;

    void setStandbyEnabled(boolean z) throws android.os.RemoteException;

    void setVr2dDisplayProperties(android.app.Vr2dDisplayProperties vr2dDisplayProperties) throws android.os.RemoteException;

    void unregisterListener(android.service.vr.IVrStateCallbacks iVrStateCallbacks) throws android.os.RemoteException;

    void unregisterPersistentVrStateListener(android.service.vr.IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws android.os.RemoteException;

    public static class Default implements android.service.vr.IVrManager {
        @Override // android.service.vr.IVrManager
        public void registerListener(android.service.vr.IVrStateCallbacks iVrStateCallbacks) throws android.os.RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public void unregisterListener(android.service.vr.IVrStateCallbacks iVrStateCallbacks) throws android.os.RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public void registerPersistentVrStateListener(android.service.vr.IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws android.os.RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public void unregisterPersistentVrStateListener(android.service.vr.IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws android.os.RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public boolean getVrModeState() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.vr.IVrManager
        public boolean getPersistentVrModeEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.vr.IVrManager
        public void setPersistentVrModeEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public void setVr2dDisplayProperties(android.app.Vr2dDisplayProperties vr2dDisplayProperties) throws android.os.RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public int getVr2dDisplayId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.service.vr.IVrManager
        public void setAndBindCompositor(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public void setStandbyEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.vr.IVrManager {
        public static final java.lang.String DESCRIPTOR = "android.service.vr.IVrManager";
        static final int TRANSACTION_getPersistentVrModeEnabled = 6;
        static final int TRANSACTION_getVr2dDisplayId = 9;
        static final int TRANSACTION_getVrModeState = 5;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_registerPersistentVrStateListener = 3;
        static final int TRANSACTION_setAndBindCompositor = 10;
        static final int TRANSACTION_setPersistentVrModeEnabled = 7;
        static final int TRANSACTION_setStandbyEnabled = 11;
        static final int TRANSACTION_setVr2dDisplayProperties = 8;
        static final int TRANSACTION_unregisterListener = 2;
        static final int TRANSACTION_unregisterPersistentVrStateListener = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.vr.IVrManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.vr.IVrManager)) {
                return (android.service.vr.IVrManager) queryLocalInterface;
            }
            return new android.service.vr.IVrManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerListener";
                case 2:
                    return "unregisterListener";
                case 3:
                    return "registerPersistentVrStateListener";
                case 4:
                    return "unregisterPersistentVrStateListener";
                case 5:
                    return "getVrModeState";
                case 6:
                    return "getPersistentVrModeEnabled";
                case 7:
                    return "setPersistentVrModeEnabled";
                case 8:
                    return "setVr2dDisplayProperties";
                case 9:
                    return "getVr2dDisplayId";
                case 10:
                    return "setAndBindCompositor";
                case 11:
                    return "setStandbyEnabled";
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
                    android.service.vr.IVrStateCallbacks asInterface = android.service.vr.IVrStateCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.service.vr.IVrStateCallbacks asInterface2 = android.service.vr.IVrStateCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.service.vr.IPersistentVrStateCallbacks asInterface3 = android.service.vr.IPersistentVrStateCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPersistentVrStateListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.service.vr.IPersistentVrStateCallbacks asInterface4 = android.service.vr.IPersistentVrStateCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPersistentVrStateListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean vrModeState = getVrModeState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(vrModeState);
                    return true;
                case 6:
                    boolean persistentVrModeEnabled = getPersistentVrModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(persistentVrModeEnabled);
                    return true;
                case 7:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPersistentVrModeEnabled(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.app.Vr2dDisplayProperties vr2dDisplayProperties = (android.app.Vr2dDisplayProperties) parcel.readTypedObject(android.app.Vr2dDisplayProperties.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVr2dDisplayProperties(vr2dDisplayProperties);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int vr2dDisplayId = getVr2dDisplayId();
                    parcel2.writeNoException();
                    parcel2.writeInt(vr2dDisplayId);
                    return true;
                case 10:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAndBindCompositor(readString);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStandbyEnabled(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.vr.IVrManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.vr.IVrManager.Stub.DESCRIPTOR;
            }

            @Override // android.service.vr.IVrManager
            public void registerListener(android.service.vr.IVrStateCallbacks iVrStateCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.vr.IVrManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVrStateCallbacks);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void unregisterListener(android.service.vr.IVrStateCallbacks iVrStateCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.vr.IVrManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVrStateCallbacks);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void registerPersistentVrStateListener(android.service.vr.IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.vr.IVrManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPersistentVrStateCallbacks);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void unregisterPersistentVrStateListener(android.service.vr.IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.vr.IVrManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPersistentVrStateCallbacks);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public boolean getVrModeState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.vr.IVrManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public boolean getPersistentVrModeEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.vr.IVrManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void setPersistentVrModeEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.vr.IVrManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void setVr2dDisplayProperties(android.app.Vr2dDisplayProperties vr2dDisplayProperties) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.vr.IVrManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(vr2dDisplayProperties, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public int getVr2dDisplayId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.vr.IVrManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void setAndBindCompositor(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.vr.IVrManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void setStandbyEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.vr.IVrManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
