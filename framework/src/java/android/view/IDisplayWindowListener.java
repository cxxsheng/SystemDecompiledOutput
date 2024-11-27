package android.view;

/* loaded from: classes4.dex */
public interface IDisplayWindowListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.IDisplayWindowListener";

    void onDisplayAdded(int i) throws android.os.RemoteException;

    void onDisplayConfigurationChanged(int i, android.content.res.Configuration configuration) throws android.os.RemoteException;

    void onDisplayRemoved(int i) throws android.os.RemoteException;

    void onFixedRotationFinished(int i) throws android.os.RemoteException;

    void onFixedRotationStarted(int i, int i2) throws android.os.RemoteException;

    void onKeepClearAreasChanged(int i, java.util.List<android.graphics.Rect> list, java.util.List<android.graphics.Rect> list2) throws android.os.RemoteException;

    public static class Default implements android.view.IDisplayWindowListener {
        @Override // android.view.IDisplayWindowListener
        public void onDisplayAdded(int i) throws android.os.RemoteException {
        }

        @Override // android.view.IDisplayWindowListener
        public void onDisplayConfigurationChanged(int i, android.content.res.Configuration configuration) throws android.os.RemoteException {
        }

        @Override // android.view.IDisplayWindowListener
        public void onDisplayRemoved(int i) throws android.os.RemoteException {
        }

        @Override // android.view.IDisplayWindowListener
        public void onFixedRotationStarted(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.IDisplayWindowListener
        public void onFixedRotationFinished(int i) throws android.os.RemoteException {
        }

        @Override // android.view.IDisplayWindowListener
        public void onKeepClearAreasChanged(int i, java.util.List<android.graphics.Rect> list, java.util.List<android.graphics.Rect> list2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IDisplayWindowListener {
        static final int TRANSACTION_onDisplayAdded = 1;
        static final int TRANSACTION_onDisplayConfigurationChanged = 2;
        static final int TRANSACTION_onDisplayRemoved = 3;
        static final int TRANSACTION_onFixedRotationFinished = 5;
        static final int TRANSACTION_onFixedRotationStarted = 4;
        static final int TRANSACTION_onKeepClearAreasChanged = 6;

        public Stub() {
            attachInterface(this, android.view.IDisplayWindowListener.DESCRIPTOR);
        }

        public static android.view.IDisplayWindowListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.IDisplayWindowListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IDisplayWindowListener)) {
                return (android.view.IDisplayWindowListener) queryLocalInterface;
            }
            return new android.view.IDisplayWindowListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDisplayAdded";
                case 2:
                    return "onDisplayConfigurationChanged";
                case 3:
                    return "onDisplayRemoved";
                case 4:
                    return "onFixedRotationStarted";
                case 5:
                    return "onFixedRotationFinished";
                case 6:
                    return "onKeepClearAreasChanged";
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
                parcel.enforceInterface(android.view.IDisplayWindowListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.IDisplayWindowListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayAdded(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.content.res.Configuration configuration = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDisplayConfigurationChanged(readInt2, configuration);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayRemoved(readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFixedRotationStarted(readInt4, readInt5);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFixedRotationFinished(readInt6);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.graphics.Rect.CREATOR);
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onKeepClearAreasChanged(readInt7, createTypedArrayList, createTypedArrayList2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IDisplayWindowListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IDisplayWindowListener.DESCRIPTOR;
            }

            @Override // android.view.IDisplayWindowListener
            public void onDisplayAdded(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayWindowListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowListener
            public void onDisplayConfigurationChanged(int i, android.content.res.Configuration configuration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayWindowListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowListener
            public void onDisplayRemoved(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayWindowListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowListener
            public void onFixedRotationStarted(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayWindowListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowListener
            public void onFixedRotationFinished(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayWindowListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowListener
            public void onKeepClearAreasChanged(int i, java.util.List<android.graphics.Rect> list, java.util.List<android.graphics.Rect> list2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayWindowListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedList(list2, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
