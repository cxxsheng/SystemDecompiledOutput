package android.app.ambientcontext;

/* loaded from: classes.dex */
public interface IAmbientContextObserver extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.ambientcontext.IAmbientContextObserver";

    void onEvents(java.util.List<android.app.ambientcontext.AmbientContextEvent> list) throws android.os.RemoteException;

    void onRegistrationComplete(int i) throws android.os.RemoteException;

    public static class Default implements android.app.ambientcontext.IAmbientContextObserver {
        @Override // android.app.ambientcontext.IAmbientContextObserver
        public void onEvents(java.util.List<android.app.ambientcontext.AmbientContextEvent> list) throws android.os.RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextObserver
        public void onRegistrationComplete(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.ambientcontext.IAmbientContextObserver {
        static final int TRANSACTION_onEvents = 1;
        static final int TRANSACTION_onRegistrationComplete = 2;

        public Stub() {
            attachInterface(this, android.app.ambientcontext.IAmbientContextObserver.DESCRIPTOR);
        }

        public static android.app.ambientcontext.IAmbientContextObserver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.ambientcontext.IAmbientContextObserver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.ambientcontext.IAmbientContextObserver)) {
                return (android.app.ambientcontext.IAmbientContextObserver) queryLocalInterface;
            }
            return new android.app.ambientcontext.IAmbientContextObserver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onEvents";
                case 2:
                    return "onRegistrationComplete";
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
                parcel.enforceInterface(android.app.ambientcontext.IAmbientContextObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.ambientcontext.IAmbientContextObserver.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.app.ambientcontext.AmbientContextEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEvents(createTypedArrayList);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRegistrationComplete(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.ambientcontext.IAmbientContextObserver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.ambientcontext.IAmbientContextObserver.DESCRIPTOR;
            }

            @Override // android.app.ambientcontext.IAmbientContextObserver
            public void onEvents(java.util.List<android.app.ambientcontext.AmbientContextEvent> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ambientcontext.IAmbientContextObserver.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextObserver
            public void onRegistrationComplete(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ambientcontext.IAmbientContextObserver.DESCRIPTOR);
                    obtain.writeInt(i);
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
