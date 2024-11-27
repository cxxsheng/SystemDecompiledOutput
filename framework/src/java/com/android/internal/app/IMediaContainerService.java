package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IMediaContainerService extends android.os.IInterface {
    long calculateInstalledSize(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int copyPackage(java.lang.String str, com.android.internal.os.IParcelFileDescriptorFactory iParcelFileDescriptorFactory) throws android.os.RemoteException;

    android.content.pm.PackageInfoLite getMinimalPackageInfo(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    android.content.res.ObbInfo getObbInfo(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IMediaContainerService {
        @Override // com.android.internal.app.IMediaContainerService
        public int copyPackage(java.lang.String str, com.android.internal.os.IParcelFileDescriptorFactory iParcelFileDescriptorFactory) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IMediaContainerService
        public android.content.pm.PackageInfoLite getMinimalPackageInfo(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IMediaContainerService
        public android.content.res.ObbInfo getObbInfo(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IMediaContainerService
        public long calculateInstalledSize(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IMediaContainerService {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IMediaContainerService";
        static final int TRANSACTION_calculateInstalledSize = 4;
        static final int TRANSACTION_copyPackage = 1;
        static final int TRANSACTION_getMinimalPackageInfo = 2;
        static final int TRANSACTION_getObbInfo = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.app.IMediaContainerService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IMediaContainerService)) {
                return (com.android.internal.app.IMediaContainerService) queryLocalInterface;
            }
            return new com.android.internal.app.IMediaContainerService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "copyPackage";
                case 2:
                    return "getMinimalPackageInfo";
                case 3:
                    return "getObbInfo";
                case 4:
                    return "calculateInstalledSize";
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
                    java.lang.String readString = parcel.readString();
                    com.android.internal.os.IParcelFileDescriptorFactory asInterface = com.android.internal.os.IParcelFileDescriptorFactory.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int copyPackage = copyPackage(readString, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(copyPackage);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.PackageInfoLite minimalPackageInfo = getMinimalPackageInfo(readString2, readInt, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(minimalPackageInfo, 1);
                    return true;
                case 3:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.res.ObbInfo obbInfo = getObbInfo(readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(obbInfo, 1);
                    return true;
                case 4:
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long calculateInstalledSize = calculateInstalledSize(readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeLong(calculateInstalledSize);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IMediaContainerService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IMediaContainerService.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IMediaContainerService
            public int copyPackage(java.lang.String str, com.android.internal.os.IParcelFileDescriptorFactory iParcelFileDescriptorFactory) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IMediaContainerService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iParcelFileDescriptorFactory);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IMediaContainerService
            public android.content.pm.PackageInfoLite getMinimalPackageInfo(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IMediaContainerService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.PackageInfoLite) obtain2.readTypedObject(android.content.pm.PackageInfoLite.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IMediaContainerService
            public android.content.res.ObbInfo getObbInfo(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IMediaContainerService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.res.ObbInfo) obtain2.readTypedObject(android.content.res.ObbInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IMediaContainerService
            public long calculateInstalledSize(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IMediaContainerService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
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
