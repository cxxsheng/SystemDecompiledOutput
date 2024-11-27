package android.hardware.location;

/* loaded from: classes2.dex */
public interface IContextHubTransactionCallback extends android.os.IInterface {
    void onQueryResponse(int i, java.util.List<android.hardware.location.NanoAppState> list) throws android.os.RemoteException;

    void onTransactionComplete(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.location.IContextHubTransactionCallback {
        @Override // android.hardware.location.IContextHubTransactionCallback
        public void onQueryResponse(int i, java.util.List<android.hardware.location.NanoAppState> list) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubTransactionCallback
        public void onTransactionComplete(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.location.IContextHubTransactionCallback {
        public static final java.lang.String DESCRIPTOR = "android.hardware.location.IContextHubTransactionCallback";
        static final int TRANSACTION_onQueryResponse = 1;
        static final int TRANSACTION_onTransactionComplete = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.location.IContextHubTransactionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.location.IContextHubTransactionCallback)) {
                return (android.hardware.location.IContextHubTransactionCallback) queryLocalInterface;
            }
            return new android.hardware.location.IContextHubTransactionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onQueryResponse";
                case 2:
                    return "onTransactionComplete";
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
                    int readInt = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.hardware.location.NanoAppState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onQueryResponse(readInt, createTypedArrayList);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTransactionComplete(readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.location.IContextHubTransactionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.location.IContextHubTransactionCallback.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onQueryResponse(int i, java.util.List<android.hardware.location.NanoAppState> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubTransactionCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onTransactionComplete(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubTransactionCallback.Stub.DESCRIPTOR);
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
