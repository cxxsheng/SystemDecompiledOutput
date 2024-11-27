package android.view;

/* loaded from: classes4.dex */
public interface IInputFilter extends android.os.IInterface {
    void filterInputEvent(android.view.InputEvent inputEvent, int i) throws android.os.RemoteException;

    void install(android.view.IInputFilterHost iInputFilterHost) throws android.os.RemoteException;

    void uninstall() throws android.os.RemoteException;

    public static class Default implements android.view.IInputFilter {
        @Override // android.view.IInputFilter
        public void install(android.view.IInputFilterHost iInputFilterHost) throws android.os.RemoteException {
        }

        @Override // android.view.IInputFilter
        public void uninstall() throws android.os.RemoteException {
        }

        @Override // android.view.IInputFilter
        public void filterInputEvent(android.view.InputEvent inputEvent, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IInputFilter {
        public static final java.lang.String DESCRIPTOR = "android.view.IInputFilter";
        static final int TRANSACTION_filterInputEvent = 3;
        static final int TRANSACTION_install = 1;
        static final int TRANSACTION_uninstall = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.IInputFilter asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IInputFilter)) {
                return (android.view.IInputFilter) queryLocalInterface;
            }
            return new android.view.IInputFilter.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "install";
                case 2:
                    return "uninstall";
                case 3:
                    return "filterInputEvent";
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
                    android.view.IInputFilterHost asInterface = android.view.IInputFilterHost.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    install(asInterface);
                    return true;
                case 2:
                    uninstall();
                    return true;
                case 3:
                    android.view.InputEvent inputEvent = (android.view.InputEvent) parcel.readTypedObject(android.view.InputEvent.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    filterInputEvent(inputEvent, readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IInputFilter {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IInputFilter.Stub.DESCRIPTOR;
            }

            @Override // android.view.IInputFilter
            public void install(android.view.IInputFilterHost iInputFilterHost) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IInputFilter.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputFilterHost);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IInputFilter
            public void uninstall() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IInputFilter.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IInputFilter
            public void filterInputEvent(android.view.InputEvent inputEvent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IInputFilter.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    obtain.writeInt(i);
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
