package android.content.pm.verify.domain;

/* loaded from: classes.dex */
public interface IDomainVerificationManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.content.pm.verify.domain.IDomainVerificationManager";

    android.content.pm.verify.domain.DomainVerificationInfo getDomainVerificationInfo(java.lang.String str) throws android.os.RemoteException;

    android.content.pm.verify.domain.DomainVerificationUserState getDomainVerificationUserState(java.lang.String str, int i) throws android.os.RemoteException;

    java.util.List<android.content.pm.verify.domain.DomainOwner> getOwnersForDomain(java.lang.String str, int i) throws android.os.RemoteException;

    android.os.Bundle getUriRelativeFilterGroups(java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    java.util.List<java.lang.String> queryValidVerificationPackageNames() throws android.os.RemoteException;

    void setDomainVerificationLinkHandlingAllowed(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    int setDomainVerificationStatus(java.lang.String str, android.content.pm.verify.domain.DomainSet domainSet, int i) throws android.os.RemoteException;

    int setDomainVerificationUserSelection(java.lang.String str, android.content.pm.verify.domain.DomainSet domainSet, boolean z, int i) throws android.os.RemoteException;

    void setUriRelativeFilterGroups(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.content.pm.verify.domain.IDomainVerificationManager {
        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public java.util.List<java.lang.String> queryValidVerificationPackageNames() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public android.content.pm.verify.domain.DomainVerificationInfo getDomainVerificationInfo(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public android.content.pm.verify.domain.DomainVerificationUserState getDomainVerificationUserState(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public java.util.List<android.content.pm.verify.domain.DomainOwner> getOwnersForDomain(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public int setDomainVerificationStatus(java.lang.String str, android.content.pm.verify.domain.DomainSet domainSet, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public void setDomainVerificationLinkHandlingAllowed(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public int setDomainVerificationUserSelection(java.lang.String str, android.content.pm.verify.domain.DomainSet domainSet, boolean z, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public void setUriRelativeFilterGroups(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public android.os.Bundle getUriRelativeFilterGroups(java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.verify.domain.IDomainVerificationManager {
        static final int TRANSACTION_getDomainVerificationInfo = 2;
        static final int TRANSACTION_getDomainVerificationUserState = 3;
        static final int TRANSACTION_getOwnersForDomain = 4;
        static final int TRANSACTION_getUriRelativeFilterGroups = 9;
        static final int TRANSACTION_queryValidVerificationPackageNames = 1;
        static final int TRANSACTION_setDomainVerificationLinkHandlingAllowed = 6;
        static final int TRANSACTION_setDomainVerificationStatus = 5;
        static final int TRANSACTION_setDomainVerificationUserSelection = 7;
        static final int TRANSACTION_setUriRelativeFilterGroups = 8;

        public Stub() {
            attachInterface(this, android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
        }

        public static android.content.pm.verify.domain.IDomainVerificationManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.verify.domain.IDomainVerificationManager)) {
                return (android.content.pm.verify.domain.IDomainVerificationManager) queryLocalInterface;
            }
            return new android.content.pm.verify.domain.IDomainVerificationManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "queryValidVerificationPackageNames";
                case 2:
                    return "getDomainVerificationInfo";
                case 3:
                    return "getDomainVerificationUserState";
                case 4:
                    return "getOwnersForDomain";
                case 5:
                    return "setDomainVerificationStatus";
                case 6:
                    return "setDomainVerificationLinkHandlingAllowed";
                case 7:
                    return "setDomainVerificationUserSelection";
                case 8:
                    return "setUriRelativeFilterGroups";
                case 9:
                    return "getUriRelativeFilterGroups";
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
                parcel.enforceInterface(android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.List<java.lang.String> queryValidVerificationPackageNames = queryValidVerificationPackageNames();
                    parcel2.writeNoException();
                    parcel2.writeStringList(queryValidVerificationPackageNames);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.verify.domain.DomainVerificationInfo domainVerificationInfo = getDomainVerificationInfo(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(domainVerificationInfo, 1);
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.verify.domain.DomainVerificationUserState domainVerificationUserState = getDomainVerificationUserState(readString2, readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(domainVerificationUserState, 1);
                    return true;
                case 4:
                    java.lang.String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.content.pm.verify.domain.DomainOwner> ownersForDomain = getOwnersForDomain(readString3, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(ownersForDomain, 1);
                    return true;
                case 5:
                    java.lang.String readString4 = parcel.readString();
                    android.content.pm.verify.domain.DomainSet domainSet = (android.content.pm.verify.domain.DomainSet) parcel.readTypedObject(android.content.pm.verify.domain.DomainSet.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int domainVerificationStatus = setDomainVerificationStatus(readString4, domainSet, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(domainVerificationStatus);
                    return true;
                case 6:
                    java.lang.String readString5 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDomainVerificationLinkHandlingAllowed(readString5, readBoolean, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString6 = parcel.readString();
                    android.content.pm.verify.domain.DomainSet domainSet2 = (android.content.pm.verify.domain.DomainSet) parcel.readTypedObject(android.content.pm.verify.domain.DomainSet.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int domainVerificationUserSelection = setDomainVerificationUserSelection(readString6, domainSet2, readBoolean2, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(domainVerificationUserSelection);
                    return true;
                case 8:
                    java.lang.String readString7 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUriRelativeFilterGroups(readString7, bundle);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString8 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle uriRelativeFilterGroups = getUriRelativeFilterGroups(readString8, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(uriRelativeFilterGroups, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.verify.domain.IDomainVerificationManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR;
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public java.util.List<java.lang.String> queryValidVerificationPackageNames() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public android.content.pm.verify.domain.DomainVerificationInfo getDomainVerificationInfo(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.verify.domain.DomainVerificationInfo) obtain2.readTypedObject(android.content.pm.verify.domain.DomainVerificationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public android.content.pm.verify.domain.DomainVerificationUserState getDomainVerificationUserState(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.verify.domain.DomainVerificationUserState) obtain2.readTypedObject(android.content.pm.verify.domain.DomainVerificationUserState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public java.util.List<android.content.pm.verify.domain.DomainOwner> getOwnersForDomain(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.pm.verify.domain.DomainOwner.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public int setDomainVerificationStatus(java.lang.String str, android.content.pm.verify.domain.DomainSet domainSet, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(domainSet, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public void setDomainVerificationLinkHandlingAllowed(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public int setDomainVerificationUserSelection(java.lang.String str, android.content.pm.verify.domain.DomainSet domainSet, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(domainSet, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public void setUriRelativeFilterGroups(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public android.os.Bundle getUriRelativeFilterGroups(java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.verify.domain.IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(9, obtain, obtain2, 0);
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
            return 8;
        }
    }
}
