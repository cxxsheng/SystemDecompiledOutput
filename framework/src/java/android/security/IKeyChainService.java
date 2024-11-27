package android.security;

/* loaded from: classes3.dex */
public interface IKeyChainService extends android.os.IInterface {
    boolean containsCaAlias(java.lang.String str) throws android.os.RemoteException;

    boolean containsKeyPair(java.lang.String str) throws android.os.RemoteException;

    boolean deleteCaCertificate(java.lang.String str) throws android.os.RemoteException;

    int generateKeyPair(java.lang.String str, android.security.keystore.ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec) throws android.os.RemoteException;

    java.util.List<java.lang.String> getCaCertificateChainAliases(java.lang.String str, boolean z) throws android.os.RemoteException;

    byte[] getCaCertificates(java.lang.String str) throws android.os.RemoteException;

    byte[] getCertificate(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getCredentialManagementAppPackageName() throws android.os.RemoteException;

    android.security.AppUriAuthenticationPolicy getCredentialManagementAppPolicy() throws android.os.RemoteException;

    byte[] getEncodedCaCertificate(java.lang.String str, boolean z) throws android.os.RemoteException;

    int[] getGrants(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getPredefinedAliasForPackageAndUri(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException;

    android.content.pm.StringParceledListSlice getSystemCaAliases() throws android.os.RemoteException;

    android.content.pm.StringParceledListSlice getUserCaAliases() throws android.os.RemoteException;

    java.lang.String getWifiKeyGrantAsUser(java.lang.String str) throws android.os.RemoteException;

    boolean hasCredentialManagementApp() throws android.os.RemoteException;

    boolean hasGrant(int i, java.lang.String str) throws android.os.RemoteException;

    java.lang.String installCaCertificate(byte[] bArr) throws android.os.RemoteException;

    boolean installKeyPair(byte[] bArr, byte[] bArr2, byte[] bArr3, java.lang.String str, int i) throws android.os.RemoteException;

    boolean isCredentialManagementApp(java.lang.String str) throws android.os.RemoteException;

    boolean isUserSelectable(java.lang.String str) throws android.os.RemoteException;

    void removeCredentialManagementApp() throws android.os.RemoteException;

    boolean removeKeyPair(java.lang.String str) throws android.os.RemoteException;

    java.lang.String requestPrivateKey(java.lang.String str) throws android.os.RemoteException;

    boolean reset() throws android.os.RemoteException;

    void setCredentialManagementApp(java.lang.String str, android.security.AppUriAuthenticationPolicy appUriAuthenticationPolicy) throws android.os.RemoteException;

    boolean setGrant(int i, java.lang.String str, boolean z) throws android.os.RemoteException;

    boolean setKeyPairCertificate(java.lang.String str, byte[] bArr, byte[] bArr2) throws android.os.RemoteException;

    void setUserSelectable(java.lang.String str, boolean z) throws android.os.RemoteException;

    public static class Default implements android.security.IKeyChainService {
        @Override // android.security.IKeyChainService
        public java.lang.String requestPrivateKey(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public byte[] getCertificate(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public byte[] getCaCertificates(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean isUserSelectable(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public void setUserSelectable(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.security.IKeyChainService
        public int generateKeyPair(java.lang.String str, android.security.keystore.ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.security.IKeyChainService
        public boolean setKeyPairCertificate(java.lang.String str, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public java.lang.String installCaCertificate(byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean installKeyPair(byte[] bArr, byte[] bArr2, byte[] bArr3, java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean removeKeyPair(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean containsKeyPair(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public int[] getGrants(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean deleteCaCertificate(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean reset() throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public android.content.pm.StringParceledListSlice getUserCaAliases() throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public android.content.pm.StringParceledListSlice getSystemCaAliases() throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean containsCaAlias(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public byte[] getEncodedCaCertificate(java.lang.String str, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public java.util.List<java.lang.String> getCaCertificateChainAliases(java.lang.String str, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public void setCredentialManagementApp(java.lang.String str, android.security.AppUriAuthenticationPolicy appUriAuthenticationPolicy) throws android.os.RemoteException {
        }

        @Override // android.security.IKeyChainService
        public boolean hasCredentialManagementApp() throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public java.lang.String getCredentialManagementAppPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public android.security.AppUriAuthenticationPolicy getCredentialManagementAppPolicy() throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public java.lang.String getPredefinedAliasForPackageAndUri(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public void removeCredentialManagementApp() throws android.os.RemoteException {
        }

        @Override // android.security.IKeyChainService
        public boolean isCredentialManagementApp(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean setGrant(int i, java.lang.String str, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean hasGrant(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public java.lang.String getWifiKeyGrantAsUser(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.IKeyChainService {
        public static final java.lang.String DESCRIPTOR = "android.security.IKeyChainService";
        static final int TRANSACTION_containsCaAlias = 17;
        static final int TRANSACTION_containsKeyPair = 11;
        static final int TRANSACTION_deleteCaCertificate = 13;
        static final int TRANSACTION_generateKeyPair = 6;
        static final int TRANSACTION_getCaCertificateChainAliases = 19;
        static final int TRANSACTION_getCaCertificates = 3;
        static final int TRANSACTION_getCertificate = 2;
        static final int TRANSACTION_getCredentialManagementAppPackageName = 22;
        static final int TRANSACTION_getCredentialManagementAppPolicy = 23;
        static final int TRANSACTION_getEncodedCaCertificate = 18;
        static final int TRANSACTION_getGrants = 12;
        static final int TRANSACTION_getPredefinedAliasForPackageAndUri = 24;
        static final int TRANSACTION_getSystemCaAliases = 16;
        static final int TRANSACTION_getUserCaAliases = 15;
        static final int TRANSACTION_getWifiKeyGrantAsUser = 29;
        static final int TRANSACTION_hasCredentialManagementApp = 21;
        static final int TRANSACTION_hasGrant = 28;
        static final int TRANSACTION_installCaCertificate = 8;
        static final int TRANSACTION_installKeyPair = 9;
        static final int TRANSACTION_isCredentialManagementApp = 26;
        static final int TRANSACTION_isUserSelectable = 4;
        static final int TRANSACTION_removeCredentialManagementApp = 25;
        static final int TRANSACTION_removeKeyPair = 10;
        static final int TRANSACTION_requestPrivateKey = 1;
        static final int TRANSACTION_reset = 14;
        static final int TRANSACTION_setCredentialManagementApp = 20;
        static final int TRANSACTION_setGrant = 27;
        static final int TRANSACTION_setKeyPairCertificate = 7;
        static final int TRANSACTION_setUserSelectable = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.security.IKeyChainService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.IKeyChainService)) {
                return (android.security.IKeyChainService) queryLocalInterface;
            }
            return new android.security.IKeyChainService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "requestPrivateKey";
                case 2:
                    return "getCertificate";
                case 3:
                    return "getCaCertificates";
                case 4:
                    return "isUserSelectable";
                case 5:
                    return "setUserSelectable";
                case 6:
                    return "generateKeyPair";
                case 7:
                    return "setKeyPairCertificate";
                case 8:
                    return "installCaCertificate";
                case 9:
                    return "installKeyPair";
                case 10:
                    return "removeKeyPair";
                case 11:
                    return "containsKeyPair";
                case 12:
                    return "getGrants";
                case 13:
                    return "deleteCaCertificate";
                case 14:
                    return "reset";
                case 15:
                    return "getUserCaAliases";
                case 16:
                    return "getSystemCaAliases";
                case 17:
                    return "containsCaAlias";
                case 18:
                    return "getEncodedCaCertificate";
                case 19:
                    return "getCaCertificateChainAliases";
                case 20:
                    return "setCredentialManagementApp";
                case 21:
                    return "hasCredentialManagementApp";
                case 22:
                    return "getCredentialManagementAppPackageName";
                case 23:
                    return "getCredentialManagementAppPolicy";
                case 24:
                    return "getPredefinedAliasForPackageAndUri";
                case 25:
                    return "removeCredentialManagementApp";
                case 26:
                    return "isCredentialManagementApp";
                case 27:
                    return "setGrant";
                case 28:
                    return "hasGrant";
                case 29:
                    return "getWifiKeyGrantAsUser";
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
                    parcel.enforceNoDataAvail();
                    java.lang.String requestPrivateKey = requestPrivateKey(readString);
                    parcel2.writeNoException();
                    parcel2.writeString(requestPrivateKey);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] certificate = getCertificate(readString2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(certificate);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] caCertificates = getCaCertificates(readString3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(caCertificates);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUserSelectable = isUserSelectable(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserSelectable);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUserSelectable(readString5, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString6 = parcel.readString();
                    android.security.keystore.ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec = (android.security.keystore.ParcelableKeyGenParameterSpec) parcel.readTypedObject(android.security.keystore.ParcelableKeyGenParameterSpec.CREATOR);
                    parcel.enforceNoDataAvail();
                    int generateKeyPair = generateKeyPair(readString6, parcelableKeyGenParameterSpec);
                    parcel2.writeNoException();
                    parcel2.writeInt(generateKeyPair);
                    return true;
                case 7:
                    java.lang.String readString7 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean keyPairCertificate = setKeyPairCertificate(readString7, createByteArray, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(keyPairCertificate);
                    return true;
                case 8:
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    java.lang.String installCaCertificate = installCaCertificate(createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeString(installCaCertificate);
                    return true;
                case 9:
                    byte[] createByteArray4 = parcel.createByteArray();
                    byte[] createByteArray5 = parcel.createByteArray();
                    byte[] createByteArray6 = parcel.createByteArray();
                    java.lang.String readString8 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean installKeyPair = installKeyPair(createByteArray4, createByteArray5, createByteArray6, readString8, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(installKeyPair);
                    return true;
                case 10:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeKeyPair = removeKeyPair(readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeKeyPair);
                    return true;
                case 11:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean containsKeyPair = containsKeyPair(readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(containsKeyPair);
                    return true;
                case 12:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] grants = getGrants(readString11);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(grants);
                    return true;
                case 13:
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteCaCertificate = deleteCaCertificate(readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteCaCertificate);
                    return true;
                case 14:
                    boolean reset = reset();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reset);
                    return true;
                case 15:
                    android.content.pm.StringParceledListSlice userCaAliases = getUserCaAliases();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userCaAliases, 1);
                    return true;
                case 16:
                    android.content.pm.StringParceledListSlice systemCaAliases = getSystemCaAliases();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemCaAliases, 1);
                    return true;
                case 17:
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean containsCaAlias = containsCaAlias(readString13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(containsCaAlias);
                    return true;
                case 18:
                    java.lang.String readString14 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] encodedCaCertificate = getEncodedCaCertificate(readString14, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(encodedCaCertificate);
                    return true;
                case 19:
                    java.lang.String readString15 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> caCertificateChainAliases = getCaCertificateChainAliases(readString15, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(caCertificateChainAliases);
                    return true;
                case 20:
                    java.lang.String readString16 = parcel.readString();
                    android.security.AppUriAuthenticationPolicy appUriAuthenticationPolicy = (android.security.AppUriAuthenticationPolicy) parcel.readTypedObject(android.security.AppUriAuthenticationPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCredentialManagementApp(readString16, appUriAuthenticationPolicy);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    boolean hasCredentialManagementApp = hasCredentialManagementApp();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasCredentialManagementApp);
                    return true;
                case 22:
                    java.lang.String credentialManagementAppPackageName = getCredentialManagementAppPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(credentialManagementAppPackageName);
                    return true;
                case 23:
                    android.security.AppUriAuthenticationPolicy credentialManagementAppPolicy = getCredentialManagementAppPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialManagementAppPolicy, 1);
                    return true;
                case 24:
                    java.lang.String readString17 = parcel.readString();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String predefinedAliasForPackageAndUri = getPredefinedAliasForPackageAndUri(readString17, uri);
                    parcel2.writeNoException();
                    parcel2.writeString(predefinedAliasForPackageAndUri);
                    return true;
                case 25:
                    removeCredentialManagementApp();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isCredentialManagementApp = isCredentialManagementApp(readString18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCredentialManagementApp);
                    return true;
                case 27:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString19 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean grant = setGrant(readInt2, readString19, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(grant);
                    return true;
                case 28:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasGrant = hasGrant(readInt3, readString20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasGrant);
                    return true;
                case 29:
                    java.lang.String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String wifiKeyGrantAsUser = getWifiKeyGrantAsUser(readString21);
                    parcel2.writeNoException();
                    parcel2.writeString(wifiKeyGrantAsUser);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.IKeyChainService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.IKeyChainService.Stub.DESCRIPTOR;
            }

            @Override // android.security.IKeyChainService
            public java.lang.String requestPrivateKey(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCertificate(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCaCertificates(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean isUserSelectable(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public void setUserSelectable(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public int generateKeyPair(java.lang.String str, android.security.keystore.ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelableKeyGenParameterSpec, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean setKeyPairCertificate(java.lang.String str, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public java.lang.String installCaCertificate(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean installKeyPair(byte[] bArr, byte[] bArr2, byte[] bArr3, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean removeKeyPair(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean containsKeyPair(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public int[] getGrants(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean deleteCaCertificate(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean reset() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public android.content.pm.StringParceledListSlice getUserCaAliases() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.StringParceledListSlice) obtain2.readTypedObject(android.content.pm.StringParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public android.content.pm.StringParceledListSlice getSystemCaAliases() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.StringParceledListSlice) obtain2.readTypedObject(android.content.pm.StringParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean containsCaAlias(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getEncodedCaCertificate(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public java.util.List<java.lang.String> getCaCertificateChainAliases(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public void setCredentialManagementApp(java.lang.String str, android.security.AppUriAuthenticationPolicy appUriAuthenticationPolicy) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(appUriAuthenticationPolicy, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean hasCredentialManagementApp() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public java.lang.String getCredentialManagementAppPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public android.security.AppUriAuthenticationPolicy getCredentialManagementAppPolicy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.security.AppUriAuthenticationPolicy) obtain2.readTypedObject(android.security.AppUriAuthenticationPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public java.lang.String getPredefinedAliasForPackageAndUri(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public void removeCredentialManagementApp() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean isCredentialManagementApp(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean setGrant(int i, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean hasGrant(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public java.lang.String getWifiKeyGrantAsUser(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.IKeyChainService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 28;
        }
    }
}
