package android.security;

/* loaded from: classes3.dex */
public interface IFileIntegrityService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.IFileIntegrityService";

    android.os.IInstalld.IFsveritySetupAuthToken createAuthToken(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    boolean isApkVeritySupported() throws android.os.RemoteException;

    boolean isAppSourceCertificateTrusted(byte[] bArr, java.lang.String str) throws android.os.RemoteException;

    int setupFsverity(android.os.IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    public static class Default implements android.security.IFileIntegrityService {
        @Override // android.security.IFileIntegrityService
        public boolean isApkVeritySupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IFileIntegrityService
        public boolean isAppSourceCertificateTrusted(byte[] bArr, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IFileIntegrityService
        public android.os.IInstalld.IFsveritySetupAuthToken createAuthToken(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IFileIntegrityService
        public int setupFsverity(android.os.IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.IFileIntegrityService {
        static final int TRANSACTION_createAuthToken = 3;
        static final int TRANSACTION_isApkVeritySupported = 1;
        static final int TRANSACTION_isAppSourceCertificateTrusted = 2;
        static final int TRANSACTION_setupFsverity = 4;

        public Stub() {
            attachInterface(this, android.security.IFileIntegrityService.DESCRIPTOR);
        }

        public static android.security.IFileIntegrityService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.IFileIntegrityService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.IFileIntegrityService)) {
                return (android.security.IFileIntegrityService) queryLocalInterface;
            }
            return new android.security.IFileIntegrityService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isApkVeritySupported";
                case 2:
                    return "isAppSourceCertificateTrusted";
                case 3:
                    return "createAuthToken";
                case 4:
                    return "setupFsverity";
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
                parcel.enforceInterface(android.security.IFileIntegrityService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.IFileIntegrityService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean isApkVeritySupported = isApkVeritySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApkVeritySupported);
                    return true;
                case 2:
                    byte[] createByteArray = parcel.createByteArray();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAppSourceCertificateTrusted = isAppSourceCertificateTrusted(createByteArray, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppSourceCertificateTrusted);
                    return true;
                case 3:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.IInstalld.IFsveritySetupAuthToken createAuthToken = createAuthToken(parcelFileDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createAuthToken);
                    return true;
                case 4:
                    android.os.IInstalld.IFsveritySetupAuthToken asInterface = android.os.IInstalld.IFsveritySetupAuthToken.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int i3 = setupFsverity(asInterface, readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(i3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.IFileIntegrityService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.IFileIntegrityService.DESCRIPTOR;
            }

            @Override // android.security.IFileIntegrityService
            public boolean isApkVeritySupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IFileIntegrityService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IFileIntegrityService
            public boolean isAppSourceCertificateTrusted(byte[] bArr, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IFileIntegrityService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IFileIntegrityService
            public android.os.IInstalld.IFsveritySetupAuthToken createAuthToken(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IFileIntegrityService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.os.IInstalld.IFsveritySetupAuthToken.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IFileIntegrityService
            public int setupFsverity(android.os.IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IFileIntegrityService.DESCRIPTOR);
                    obtain.writeStrongInterface(iFsveritySetupAuthToken);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
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
            return 3;
        }
    }
}
