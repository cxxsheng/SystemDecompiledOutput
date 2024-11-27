package android.os;

/* loaded from: classes3.dex */
public interface ISystemConfig extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.ISystemConfig";

    java.util.List<android.content.ComponentName> getDefaultVrComponents() throws android.os.RemoteException;

    java.util.List<java.lang.String> getDisabledUntilUsedPreinstalledCarrierApps() throws android.os.RemoteException;

    java.util.Map getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() throws android.os.RemoteException;

    java.util.Map getDisabledUntilUsedPreinstalledCarrierAssociatedApps() throws android.os.RemoteException;

    java.util.List<android.content.ComponentName> getEnabledComponentOverrides(java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.content.pm.SignedPackageParcel> getEnhancedConfirmationTrustedInstallers() throws android.os.RemoteException;

    java.util.List<android.content.pm.SignedPackageParcel> getEnhancedConfirmationTrustedPackages() throws android.os.RemoteException;

    java.util.List<java.lang.String> getPreventUserDisablePackages() throws android.os.RemoteException;

    int[] getSystemPermissionUids(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.os.ISystemConfig {
        @Override // android.os.ISystemConfig
        public java.util.List<java.lang.String> getDisabledUntilUsedPreinstalledCarrierApps() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public java.util.Map getDisabledUntilUsedPreinstalledCarrierAssociatedApps() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public java.util.Map getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public int[] getSystemPermissionUids(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public java.util.List<android.content.ComponentName> getEnabledComponentOverrides(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public java.util.List<android.content.ComponentName> getDefaultVrComponents() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public java.util.List<java.lang.String> getPreventUserDisablePackages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public java.util.List<android.content.pm.SignedPackageParcel> getEnhancedConfirmationTrustedPackages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public java.util.List<android.content.pm.SignedPackageParcel> getEnhancedConfirmationTrustedInstallers() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.ISystemConfig {
        static final int TRANSACTION_getDefaultVrComponents = 6;
        static final int TRANSACTION_getDisabledUntilUsedPreinstalledCarrierApps = 1;
        static final int TRANSACTION_getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries = 3;
        static final int TRANSACTION_getDisabledUntilUsedPreinstalledCarrierAssociatedApps = 2;
        static final int TRANSACTION_getEnabledComponentOverrides = 5;
        static final int TRANSACTION_getEnhancedConfirmationTrustedInstallers = 9;
        static final int TRANSACTION_getEnhancedConfirmationTrustedPackages = 8;
        static final int TRANSACTION_getPreventUserDisablePackages = 7;
        static final int TRANSACTION_getSystemPermissionUids = 4;

        public Stub() {
            attachInterface(this, android.os.ISystemConfig.DESCRIPTOR);
        }

        public static android.os.ISystemConfig asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.ISystemConfig.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.ISystemConfig)) {
                return (android.os.ISystemConfig) queryLocalInterface;
            }
            return new android.os.ISystemConfig.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDisabledUntilUsedPreinstalledCarrierApps";
                case 2:
                    return "getDisabledUntilUsedPreinstalledCarrierAssociatedApps";
                case 3:
                    return "getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries";
                case 4:
                    return "getSystemPermissionUids";
                case 5:
                    return "getEnabledComponentOverrides";
                case 6:
                    return "getDefaultVrComponents";
                case 7:
                    return "getPreventUserDisablePackages";
                case 8:
                    return "getEnhancedConfirmationTrustedPackages";
                case 9:
                    return "getEnhancedConfirmationTrustedInstallers";
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
                parcel.enforceInterface(android.os.ISystemConfig.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.ISystemConfig.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.List<java.lang.String> disabledUntilUsedPreinstalledCarrierApps = getDisabledUntilUsedPreinstalledCarrierApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(disabledUntilUsedPreinstalledCarrierApps);
                    return true;
                case 2:
                    java.util.Map disabledUntilUsedPreinstalledCarrierAssociatedApps = getDisabledUntilUsedPreinstalledCarrierAssociatedApps();
                    parcel2.writeNoException();
                    parcel2.writeMap(disabledUntilUsedPreinstalledCarrierAssociatedApps);
                    return true;
                case 3:
                    java.util.Map disabledUntilUsedPreinstalledCarrierAssociatedAppEntries = getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries();
                    parcel2.writeNoException();
                    parcel2.writeMap(disabledUntilUsedPreinstalledCarrierAssociatedAppEntries);
                    return true;
                case 4:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] systemPermissionUids = getSystemPermissionUids(readString);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(systemPermissionUids);
                    return true;
                case 5:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.content.ComponentName> enabledComponentOverrides = getEnabledComponentOverrides(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledComponentOverrides, 1);
                    return true;
                case 6:
                    java.util.List<android.content.ComponentName> defaultVrComponents = getDefaultVrComponents();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(defaultVrComponents, 1);
                    return true;
                case 7:
                    java.util.List<java.lang.String> preventUserDisablePackages = getPreventUserDisablePackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(preventUserDisablePackages);
                    return true;
                case 8:
                    java.util.List<android.content.pm.SignedPackageParcel> enhancedConfirmationTrustedPackages = getEnhancedConfirmationTrustedPackages();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enhancedConfirmationTrustedPackages, 1);
                    return true;
                case 9:
                    java.util.List<android.content.pm.SignedPackageParcel> enhancedConfirmationTrustedInstallers = getEnhancedConfirmationTrustedInstallers();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enhancedConfirmationTrustedInstallers, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.ISystemConfig {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.ISystemConfig.DESCRIPTOR;
            }

            @Override // android.os.ISystemConfig
            public java.util.List<java.lang.String> getDisabledUntilUsedPreinstalledCarrierApps() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public java.util.Map getDisabledUntilUsedPreinstalledCarrierAssociatedApps() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public java.util.Map getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public int[] getSystemPermissionUids(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.ISystemConfig.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public java.util.List<android.content.ComponentName> getEnabledComponentOverrides(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.ISystemConfig.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public java.util.List<android.content.ComponentName> getDefaultVrComponents() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public java.util.List<java.lang.String> getPreventUserDisablePackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public java.util.List<android.content.pm.SignedPackageParcel> getEnhancedConfirmationTrustedPackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.pm.SignedPackageParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public java.util.List<android.content.pm.SignedPackageParcel> getEnhancedConfirmationTrustedInstallers() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.pm.SignedPackageParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
