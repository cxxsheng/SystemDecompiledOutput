package android.app;

/* loaded from: classes.dex */
public interface IInstrumentationWatcher extends android.os.IInterface {
    void instrumentationFinished(android.content.ComponentName componentName, int i, android.os.Bundle bundle) throws android.os.RemoteException;

    void instrumentationStatus(android.content.ComponentName componentName, int i, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.app.IInstrumentationWatcher {
        @Override // android.app.IInstrumentationWatcher
        public void instrumentationStatus(android.content.ComponentName componentName, int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.IInstrumentationWatcher
        public void instrumentationFinished(android.content.ComponentName componentName, int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IInstrumentationWatcher {
        public static final java.lang.String DESCRIPTOR = "android.app.IInstrumentationWatcher";
        static final int TRANSACTION_instrumentationFinished = 2;
        static final int TRANSACTION_instrumentationStatus = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IInstrumentationWatcher asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IInstrumentationWatcher)) {
                return (android.app.IInstrumentationWatcher) queryLocalInterface;
            }
            return new android.app.IInstrumentationWatcher.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "instrumentationStatus";
                case 2:
                    return "instrumentationFinished";
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
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt = parcel.readInt();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    instrumentationStatus(componentName, readInt, bundle);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt2 = parcel.readInt();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    instrumentationFinished(componentName2, readInt2, bundle2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IInstrumentationWatcher {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IInstrumentationWatcher.Stub.DESCRIPTOR;
            }

            @Override // android.app.IInstrumentationWatcher
            public void instrumentationStatus(android.content.ComponentName componentName, int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IInstrumentationWatcher.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IInstrumentationWatcher
            public void instrumentationFinished(android.content.ComponentName componentName, int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IInstrumentationWatcher.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
