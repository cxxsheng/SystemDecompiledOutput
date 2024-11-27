package android.app;

/* loaded from: classes.dex */
public interface IGrammaticalInflectionManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IGrammaticalInflectionManager";

    int getSystemGrammaticalGender(android.content.AttributionSource attributionSource, int i) throws android.os.RemoteException;

    void setRequestedApplicationGrammaticalGender(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void setSystemWideGrammaticalGender(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.app.IGrammaticalInflectionManager {
        @Override // android.app.IGrammaticalInflectionManager
        public void setRequestedApplicationGrammaticalGender(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IGrammaticalInflectionManager
        public void setSystemWideGrammaticalGender(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IGrammaticalInflectionManager
        public int getSystemGrammaticalGender(android.content.AttributionSource attributionSource, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IGrammaticalInflectionManager {
        static final int TRANSACTION_getSystemGrammaticalGender = 3;
        static final int TRANSACTION_setRequestedApplicationGrammaticalGender = 1;
        static final int TRANSACTION_setSystemWideGrammaticalGender = 2;

        public Stub() {
            attachInterface(this, android.app.IGrammaticalInflectionManager.DESCRIPTOR);
        }

        public static android.app.IGrammaticalInflectionManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IGrammaticalInflectionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IGrammaticalInflectionManager)) {
                return (android.app.IGrammaticalInflectionManager) queryLocalInterface;
            }
            return new android.app.IGrammaticalInflectionManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setRequestedApplicationGrammaticalGender";
                case 2:
                    return "setSystemWideGrammaticalGender";
                case 3:
                    return "getSystemGrammaticalGender";
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
                parcel.enforceInterface(android.app.IGrammaticalInflectionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IGrammaticalInflectionManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRequestedApplicationGrammaticalGender(readString, readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSystemWideGrammaticalGender(readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.content.AttributionSource attributionSource = (android.content.AttributionSource) parcel.readTypedObject(android.content.AttributionSource.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int systemGrammaticalGender = getSystemGrammaticalGender(attributionSource, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(systemGrammaticalGender);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IGrammaticalInflectionManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IGrammaticalInflectionManager.DESCRIPTOR;
            }

            @Override // android.app.IGrammaticalInflectionManager
            public void setRequestedApplicationGrammaticalGender(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGrammaticalInflectionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGrammaticalInflectionManager
            public void setSystemWideGrammaticalGender(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGrammaticalInflectionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGrammaticalInflectionManager
            public int getSystemGrammaticalGender(android.content.AttributionSource attributionSource, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGrammaticalInflectionManager.DESCRIPTOR);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
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
