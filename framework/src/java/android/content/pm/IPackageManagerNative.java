package android.content.pm;

/* loaded from: classes.dex */
public interface IPackageManagerNative extends android.os.IInterface {
    public static final int LOCATION_PRODUCT = 4;
    public static final int LOCATION_SYSTEM = 1;
    public static final int LOCATION_VENDOR = 2;

    java.lang.String getInstallerForPackage(java.lang.String str) throws android.os.RemoteException;

    int getLocationFlags(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getModuleMetadataPackageName() throws android.os.RemoteException;

    java.lang.String[] getNamesForUids(int[] iArr) throws android.os.RemoteException;

    android.content.pm.StagedApexInfo getStagedApexInfo(java.lang.String str) throws android.os.RemoteException;

    java.lang.String[] getStagedApexModuleNames() throws android.os.RemoteException;

    int getTargetSdkVersionForPackage(java.lang.String str) throws android.os.RemoteException;

    long getVersionCodeForPackage(java.lang.String str) throws android.os.RemoteException;

    boolean hasSha256SigningCertificate(java.lang.String str, byte[] bArr) throws android.os.RemoteException;

    boolean hasSystemFeature(java.lang.String str, int i) throws android.os.RemoteException;

    boolean[] isAudioPlaybackCaptureAllowed(java.lang.String[] strArr) throws android.os.RemoteException;

    boolean isPackageDebuggable(java.lang.String str) throws android.os.RemoteException;

    void registerStagedApexObserver(android.content.pm.IStagedApexObserver iStagedApexObserver) throws android.os.RemoteException;

    void unregisterStagedApexObserver(android.content.pm.IStagedApexObserver iStagedApexObserver) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IPackageManagerNative {
        @Override // android.content.pm.IPackageManagerNative
        public java.lang.String[] getNamesForUids(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public java.lang.String getInstallerForPackage(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public long getVersionCodeForPackage(java.lang.String str) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.content.pm.IPackageManagerNative
        public boolean[] isAudioPlaybackCaptureAllowed(java.lang.String[] strArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public int getLocationFlags(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManagerNative
        public int getTargetSdkVersionForPackage(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManagerNative
        public java.lang.String getModuleMetadataPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public boolean hasSha256SigningCertificate(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManagerNative
        public boolean isPackageDebuggable(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManagerNative
        public boolean hasSystemFeature(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManagerNative
        public void registerStagedApexObserver(android.content.pm.IStagedApexObserver iStagedApexObserver) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManagerNative
        public void unregisterStagedApexObserver(android.content.pm.IStagedApexObserver iStagedApexObserver) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManagerNative
        public java.lang.String[] getStagedApexModuleNames() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public android.content.pm.StagedApexInfo getStagedApexInfo(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IPackageManagerNative {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.IPackageManagerNative";
        static final int TRANSACTION_getInstallerForPackage = 2;
        static final int TRANSACTION_getLocationFlags = 5;
        static final int TRANSACTION_getModuleMetadataPackageName = 7;
        static final int TRANSACTION_getNamesForUids = 1;
        static final int TRANSACTION_getStagedApexInfo = 14;
        static final int TRANSACTION_getStagedApexModuleNames = 13;
        static final int TRANSACTION_getTargetSdkVersionForPackage = 6;
        static final int TRANSACTION_getVersionCodeForPackage = 3;
        static final int TRANSACTION_hasSha256SigningCertificate = 8;
        static final int TRANSACTION_hasSystemFeature = 10;
        static final int TRANSACTION_isAudioPlaybackCaptureAllowed = 4;
        static final int TRANSACTION_isPackageDebuggable = 9;
        static final int TRANSACTION_registerStagedApexObserver = 11;
        static final int TRANSACTION_unregisterStagedApexObserver = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.pm.IPackageManagerNative asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IPackageManagerNative)) {
                return (android.content.pm.IPackageManagerNative) queryLocalInterface;
            }
            return new android.content.pm.IPackageManagerNative.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
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
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] namesForUids = getNamesForUids(createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(namesForUids);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String installerForPackage = getInstallerForPackage(readString);
                    parcel2.writeNoException();
                    parcel2.writeString(installerForPackage);
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long versionCodeForPackage = getVersionCodeForPackage(readString2);
                    parcel2.writeNoException();
                    parcel2.writeLong(versionCodeForPackage);
                    return true;
                case 4:
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    boolean[] isAudioPlaybackCaptureAllowed = isAudioPlaybackCaptureAllowed(createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeBooleanArray(isAudioPlaybackCaptureAllowed);
                    return true;
                case 5:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int locationFlags = getLocationFlags(readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(locationFlags);
                    return true;
                case 6:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int targetSdkVersionForPackage = getTargetSdkVersionForPackage(readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(targetSdkVersionForPackage);
                    return true;
                case 7:
                    java.lang.String moduleMetadataPackageName = getModuleMetadataPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(moduleMetadataPackageName);
                    return true;
                case 8:
                    java.lang.String readString5 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean hasSha256SigningCertificate = hasSha256SigningCertificate(readString5, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSha256SigningCertificate);
                    return true;
                case 9:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPackageDebuggable = isPackageDebuggable(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageDebuggable);
                    return true;
                case 10:
                    java.lang.String readString7 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasSystemFeature = hasSystemFeature(readString7, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSystemFeature);
                    return true;
                case 11:
                    android.content.pm.IStagedApexObserver asInterface = android.content.pm.IStagedApexObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStagedApexObserver(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.content.pm.IStagedApexObserver asInterface2 = android.content.pm.IStagedApexObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStagedApexObserver(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    java.lang.String[] stagedApexModuleNames = getStagedApexModuleNames();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(stagedApexModuleNames);
                    return true;
                case 14:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.StagedApexInfo stagedApexInfo = getStagedApexInfo(readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(stagedApexInfo, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IPackageManagerNative {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageManagerNative
            public java.lang.String[] getNamesForUids(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public java.lang.String getInstallerForPackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public long getVersionCodeForPackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean[] isAudioPlaybackCaptureAllowed(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createBooleanArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public int getLocationFlags(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public int getTargetSdkVersionForPackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public java.lang.String getModuleMetadataPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean hasSha256SigningCertificate(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean isPackageDebuggable(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean hasSystemFeature(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public void registerStagedApexObserver(android.content.pm.IStagedApexObserver iStagedApexObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStagedApexObserver);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public void unregisterStagedApexObserver(android.content.pm.IStagedApexObserver iStagedApexObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStagedApexObserver);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public java.lang.String[] getStagedApexModuleNames() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public android.content.pm.StagedApexInfo getStagedApexInfo(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManagerNative.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.StagedApexInfo) obtain2.readTypedObject(android.content.pm.StagedApexInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
