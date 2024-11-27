package android.view;

/* loaded from: classes4.dex */
public interface ISensitiveContentProtectionManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.ISensitiveContentProtectionManager";

    void setSensitiveContentProtection(android.os.IBinder iBinder, java.lang.String str, boolean z) throws android.os.RemoteException;

    public static class Default implements android.view.ISensitiveContentProtectionManager {
        @Override // android.view.ISensitiveContentProtectionManager
        public void setSensitiveContentProtection(android.os.IBinder iBinder, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.ISensitiveContentProtectionManager {
        static final int TRANSACTION_setSensitiveContentProtection = 1;

        public Stub() {
            attachInterface(this, android.view.ISensitiveContentProtectionManager.DESCRIPTOR);
        }

        public static android.view.ISensitiveContentProtectionManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.ISensitiveContentProtectionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.ISensitiveContentProtectionManager)) {
                return (android.view.ISensitiveContentProtectionManager) queryLocalInterface;
            }
            return new android.view.ISensitiveContentProtectionManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setSensitiveContentProtection";
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
                parcel.enforceInterface(android.view.ISensitiveContentProtectionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.ISensitiveContentProtectionManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.String readString = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSensitiveContentProtection(readStrongBinder, readString, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.ISensitiveContentProtectionManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.ISensitiveContentProtectionManager.DESCRIPTOR;
            }

            @Override // android.view.ISensitiveContentProtectionManager
            public void setSensitiveContentProtection(android.os.IBinder iBinder, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.ISensitiveContentProtectionManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
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
