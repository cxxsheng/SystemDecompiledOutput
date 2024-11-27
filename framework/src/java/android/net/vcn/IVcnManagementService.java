package android.net.vcn;

/* loaded from: classes2.dex */
public interface IVcnManagementService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.vcn.IVcnManagementService";

    void addVcnUnderlyingNetworkPolicyListener(android.net.vcn.IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) throws android.os.RemoteException;

    void clearVcnConfig(android.os.ParcelUuid parcelUuid, java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.os.ParcelUuid> getConfiguredSubscriptionGroups(java.lang.String str) throws android.os.RemoteException;

    android.net.vcn.VcnUnderlyingNetworkPolicy getUnderlyingNetworkPolicy(android.net.NetworkCapabilities networkCapabilities, android.net.LinkProperties linkProperties) throws android.os.RemoteException;

    void registerVcnStatusCallback(android.os.ParcelUuid parcelUuid, android.net.vcn.IVcnStatusCallback iVcnStatusCallback, java.lang.String str) throws android.os.RemoteException;

    void removeVcnUnderlyingNetworkPolicyListener(android.net.vcn.IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) throws android.os.RemoteException;

    void setVcnConfig(android.os.ParcelUuid parcelUuid, android.net.vcn.VcnConfig vcnConfig, java.lang.String str) throws android.os.RemoteException;

    void unregisterVcnStatusCallback(android.net.vcn.IVcnStatusCallback iVcnStatusCallback) throws android.os.RemoteException;

    public static class Default implements android.net.vcn.IVcnManagementService {
        @Override // android.net.vcn.IVcnManagementService
        public void setVcnConfig(android.os.ParcelUuid parcelUuid, android.net.vcn.VcnConfig vcnConfig, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.vcn.IVcnManagementService
        public void clearVcnConfig(android.os.ParcelUuid parcelUuid, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.vcn.IVcnManagementService
        public java.util.List<android.os.ParcelUuid> getConfiguredSubscriptionGroups(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.vcn.IVcnManagementService
        public void addVcnUnderlyingNetworkPolicyListener(android.net.vcn.IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) throws android.os.RemoteException {
        }

        @Override // android.net.vcn.IVcnManagementService
        public void removeVcnUnderlyingNetworkPolicyListener(android.net.vcn.IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) throws android.os.RemoteException {
        }

        @Override // android.net.vcn.IVcnManagementService
        public android.net.vcn.VcnUnderlyingNetworkPolicy getUnderlyingNetworkPolicy(android.net.NetworkCapabilities networkCapabilities, android.net.LinkProperties linkProperties) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.vcn.IVcnManagementService
        public void registerVcnStatusCallback(android.os.ParcelUuid parcelUuid, android.net.vcn.IVcnStatusCallback iVcnStatusCallback, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.vcn.IVcnManagementService
        public void unregisterVcnStatusCallback(android.net.vcn.IVcnStatusCallback iVcnStatusCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.vcn.IVcnManagementService {
        static final int TRANSACTION_addVcnUnderlyingNetworkPolicyListener = 4;
        static final int TRANSACTION_clearVcnConfig = 2;
        static final int TRANSACTION_getConfiguredSubscriptionGroups = 3;
        static final int TRANSACTION_getUnderlyingNetworkPolicy = 6;
        static final int TRANSACTION_registerVcnStatusCallback = 7;
        static final int TRANSACTION_removeVcnUnderlyingNetworkPolicyListener = 5;
        static final int TRANSACTION_setVcnConfig = 1;
        static final int TRANSACTION_unregisterVcnStatusCallback = 8;

        public Stub() {
            attachInterface(this, android.net.vcn.IVcnManagementService.DESCRIPTOR);
        }

        public static android.net.vcn.IVcnManagementService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.vcn.IVcnManagementService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.vcn.IVcnManagementService)) {
                return (android.net.vcn.IVcnManagementService) queryLocalInterface;
            }
            return new android.net.vcn.IVcnManagementService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setVcnConfig";
                case 2:
                    return "clearVcnConfig";
                case 3:
                    return "getConfiguredSubscriptionGroups";
                case 4:
                    return "addVcnUnderlyingNetworkPolicyListener";
                case 5:
                    return "removeVcnUnderlyingNetworkPolicyListener";
                case 6:
                    return "getUnderlyingNetworkPolicy";
                case 7:
                    return "registerVcnStatusCallback";
                case 8:
                    return "unregisterVcnStatusCallback";
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
                parcel.enforceInterface(android.net.vcn.IVcnManagementService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.vcn.IVcnManagementService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ParcelUuid parcelUuid = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    android.net.vcn.VcnConfig vcnConfig = (android.net.vcn.VcnConfig) parcel.readTypedObject(android.net.vcn.VcnConfig.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setVcnConfig(parcelUuid, vcnConfig, readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.os.ParcelUuid parcelUuid2 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearVcnConfig(parcelUuid2, readString2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.os.ParcelUuid> configuredSubscriptionGroups = getConfiguredSubscriptionGroups(readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(configuredSubscriptionGroups, 1);
                    return true;
                case 4:
                    android.net.vcn.IVcnUnderlyingNetworkPolicyListener asInterface = android.net.vcn.IVcnUnderlyingNetworkPolicyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addVcnUnderlyingNetworkPolicyListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.net.vcn.IVcnUnderlyingNetworkPolicyListener asInterface2 = android.net.vcn.IVcnUnderlyingNetworkPolicyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeVcnUnderlyingNetworkPolicyListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.net.NetworkCapabilities networkCapabilities = (android.net.NetworkCapabilities) parcel.readTypedObject(android.net.NetworkCapabilities.CREATOR);
                    android.net.LinkProperties linkProperties = (android.net.LinkProperties) parcel.readTypedObject(android.net.LinkProperties.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.net.vcn.VcnUnderlyingNetworkPolicy underlyingNetworkPolicy = getUnderlyingNetworkPolicy(networkCapabilities, linkProperties);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(underlyingNetworkPolicy, 1);
                    return true;
                case 7:
                    android.os.ParcelUuid parcelUuid3 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    android.net.vcn.IVcnStatusCallback asInterface3 = android.net.vcn.IVcnStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerVcnStatusCallback(parcelUuid3, asInterface3, readString4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.net.vcn.IVcnStatusCallback asInterface4 = android.net.vcn.IVcnStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterVcnStatusCallback(asInterface4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.vcn.IVcnManagementService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.vcn.IVcnManagementService.DESCRIPTOR;
            }

            @Override // android.net.vcn.IVcnManagementService
            public void setVcnConfig(android.os.ParcelUuid parcelUuid, android.net.vcn.VcnConfig vcnConfig, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.vcn.IVcnManagementService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeTypedObject(vcnConfig, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public void clearVcnConfig(android.os.ParcelUuid parcelUuid, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.vcn.IVcnManagementService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public java.util.List<android.os.ParcelUuid> getConfiguredSubscriptionGroups(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.vcn.IVcnManagementService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.ParcelUuid.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public void addVcnUnderlyingNetworkPolicyListener(android.net.vcn.IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.vcn.IVcnManagementService.DESCRIPTOR);
                    obtain.writeStrongInterface(iVcnUnderlyingNetworkPolicyListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public void removeVcnUnderlyingNetworkPolicyListener(android.net.vcn.IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.vcn.IVcnManagementService.DESCRIPTOR);
                    obtain.writeStrongInterface(iVcnUnderlyingNetworkPolicyListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public android.net.vcn.VcnUnderlyingNetworkPolicy getUnderlyingNetworkPolicy(android.net.NetworkCapabilities networkCapabilities, android.net.LinkProperties linkProperties) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.vcn.IVcnManagementService.DESCRIPTOR);
                    obtain.writeTypedObject(networkCapabilities, 0);
                    obtain.writeTypedObject(linkProperties, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.vcn.VcnUnderlyingNetworkPolicy) obtain2.readTypedObject(android.net.vcn.VcnUnderlyingNetworkPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public void registerVcnStatusCallback(android.os.ParcelUuid parcelUuid, android.net.vcn.IVcnStatusCallback iVcnStatusCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.vcn.IVcnManagementService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeStrongInterface(iVcnStatusCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public void unregisterVcnStatusCallback(android.net.vcn.IVcnStatusCallback iVcnStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.vcn.IVcnManagementService.DESCRIPTOR);
                    obtain.writeStrongInterface(iVcnStatusCallback);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
