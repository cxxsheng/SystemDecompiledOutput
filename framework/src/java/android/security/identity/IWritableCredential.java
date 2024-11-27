package android.security.identity;

/* loaded from: classes3.dex */
public interface IWritableCredential extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.identity.IWritableCredential";

    byte[] getCredentialKeyCertificateChain(byte[] bArr) throws android.os.RemoteException;

    byte[] personalize(android.security.identity.AccessControlProfileParcel[] accessControlProfileParcelArr, android.security.identity.EntryNamespaceParcel[] entryNamespaceParcelArr, long j) throws android.os.RemoteException;

    public static class Default implements android.security.identity.IWritableCredential {
        @Override // android.security.identity.IWritableCredential
        public byte[] getCredentialKeyCertificateChain(byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.IWritableCredential
        public byte[] personalize(android.security.identity.AccessControlProfileParcel[] accessControlProfileParcelArr, android.security.identity.EntryNamespaceParcel[] entryNamespaceParcelArr, long j) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.identity.IWritableCredential {
        static final int TRANSACTION_getCredentialKeyCertificateChain = 1;
        static final int TRANSACTION_personalize = 2;

        public Stub() {
            attachInterface(this, android.security.identity.IWritableCredential.DESCRIPTOR);
        }

        public static android.security.identity.IWritableCredential asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.identity.IWritableCredential.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.identity.IWritableCredential)) {
                return (android.security.identity.IWritableCredential) queryLocalInterface;
            }
            return new android.security.identity.IWritableCredential.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCredentialKeyCertificateChain";
                case 2:
                    return "personalize";
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
                parcel.enforceInterface(android.security.identity.IWritableCredential.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.identity.IWritableCredential.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] credentialKeyCertificateChain = getCredentialKeyCertificateChain(createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(credentialKeyCertificateChain);
                    return true;
                case 2:
                    android.security.identity.AccessControlProfileParcel[] accessControlProfileParcelArr = (android.security.identity.AccessControlProfileParcel[]) parcel.createTypedArray(android.security.identity.AccessControlProfileParcel.CREATOR);
                    android.security.identity.EntryNamespaceParcel[] entryNamespaceParcelArr = (android.security.identity.EntryNamespaceParcel[]) parcel.createTypedArray(android.security.identity.EntryNamespaceParcel.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    byte[] personalize = personalize(accessControlProfileParcelArr, entryNamespaceParcelArr, readLong);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(personalize);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.identity.IWritableCredential {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.identity.IWritableCredential.DESCRIPTOR;
            }

            @Override // android.security.identity.IWritableCredential
            public byte[] getCredentialKeyCertificateChain(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.IWritableCredential.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.IWritableCredential
            public byte[] personalize(android.security.identity.AccessControlProfileParcel[] accessControlProfileParcelArr, android.security.identity.EntryNamespaceParcel[] entryNamespaceParcelArr, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.IWritableCredential.DESCRIPTOR);
                    obtain.writeTypedArray(accessControlProfileParcelArr, 0);
                    obtain.writeTypedArray(entryNamespaceParcelArr, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
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
