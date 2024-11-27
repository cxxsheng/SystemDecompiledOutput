package android.os;

/* loaded from: classes3.dex */
public interface ISecurityStateManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.ISecurityStateManager";

    android.os.Bundle getGlobalSecurityState() throws android.os.RemoteException;

    public static class Default implements android.os.ISecurityStateManager {
        @Override // android.os.ISecurityStateManager
        public android.os.Bundle getGlobalSecurityState() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.ISecurityStateManager {
        static final int TRANSACTION_getGlobalSecurityState = 1;

        public Stub() {
            attachInterface(this, android.os.ISecurityStateManager.DESCRIPTOR);
        }

        public static android.os.ISecurityStateManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.ISecurityStateManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.ISecurityStateManager)) {
                return (android.os.ISecurityStateManager) queryLocalInterface;
            }
            return new android.os.ISecurityStateManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getGlobalSecurityState";
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
                parcel.enforceInterface(android.os.ISecurityStateManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.ISecurityStateManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.Bundle globalSecurityState = getGlobalSecurityState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(globalSecurityState, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.ISecurityStateManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.ISecurityStateManager.DESCRIPTOR;
            }

            @Override // android.os.ISecurityStateManager
            public android.os.Bundle getGlobalSecurityState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.ISecurityStateManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
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
