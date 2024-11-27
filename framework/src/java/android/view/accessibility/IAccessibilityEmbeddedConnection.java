package android.view.accessibility;

/* loaded from: classes4.dex */
public interface IAccessibilityEmbeddedConnection extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.accessibility.IAccessibilityEmbeddedConnection";

    android.os.IBinder associateEmbeddedHierarchy(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void disassociateEmbeddedHierarchy() throws android.os.RemoteException;

    void setWindowMatrix(float[] fArr) throws android.os.RemoteException;

    public static class Default implements android.view.accessibility.IAccessibilityEmbeddedConnection {
        @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
        public android.os.IBinder associateEmbeddedHierarchy(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
        public void disassociateEmbeddedHierarchy() throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
        public void setWindowMatrix(float[] fArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.accessibility.IAccessibilityEmbeddedConnection {
        static final int TRANSACTION_associateEmbeddedHierarchy = 1;
        static final int TRANSACTION_disassociateEmbeddedHierarchy = 2;
        static final int TRANSACTION_setWindowMatrix = 3;

        public Stub() {
            attachInterface(this, android.view.accessibility.IAccessibilityEmbeddedConnection.DESCRIPTOR);
        }

        public static android.view.accessibility.IAccessibilityEmbeddedConnection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.accessibility.IAccessibilityEmbeddedConnection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.accessibility.IAccessibilityEmbeddedConnection)) {
                return (android.view.accessibility.IAccessibilityEmbeddedConnection) queryLocalInterface;
            }
            return new android.view.accessibility.IAccessibilityEmbeddedConnection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "associateEmbeddedHierarchy";
                case 2:
                    return "disassociateEmbeddedHierarchy";
                case 3:
                    return "setWindowMatrix";
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
                parcel.enforceInterface(android.view.accessibility.IAccessibilityEmbeddedConnection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.accessibility.IAccessibilityEmbeddedConnection.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.IBinder associateEmbeddedHierarchy = associateEmbeddedHierarchy(readStrongBinder, readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(associateEmbeddedHierarchy);
                    return true;
                case 2:
                    disassociateEmbeddedHierarchy();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    float[] createFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    setWindowMatrix(createFloatArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.accessibility.IAccessibilityEmbeddedConnection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.accessibility.IAccessibilityEmbeddedConnection.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
            public android.os.IBinder associateEmbeddedHierarchy(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityEmbeddedConnection.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
            public void disassociateEmbeddedHierarchy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityEmbeddedConnection.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
            public void setWindowMatrix(float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityEmbeddedConnection.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
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
