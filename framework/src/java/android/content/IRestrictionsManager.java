package android.content;

/* loaded from: classes.dex */
public interface IRestrictionsManager extends android.os.IInterface {
    android.content.Intent createLocalApprovalIntent() throws android.os.RemoteException;

    android.os.Bundle getApplicationRestrictions(java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.os.Bundle> getApplicationRestrictionsPerAdminForUser(int i, java.lang.String str) throws android.os.RemoteException;

    boolean hasRestrictionsProvider() throws android.os.RemoteException;

    void notifyPermissionResponse(java.lang.String str, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException;

    void requestPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException;

    public static class Default implements android.content.IRestrictionsManager {
        @Override // android.content.IRestrictionsManager
        public android.os.Bundle getApplicationRestrictions(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IRestrictionsManager
        public java.util.List<android.os.Bundle> getApplicationRestrictionsPerAdminForUser(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IRestrictionsManager
        public boolean hasRestrictionsProvider() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.IRestrictionsManager
        public void requestPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
        }

        @Override // android.content.IRestrictionsManager
        public void notifyPermissionResponse(java.lang.String str, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
        }

        @Override // android.content.IRestrictionsManager
        public android.content.Intent createLocalApprovalIntent() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.IRestrictionsManager {
        public static final java.lang.String DESCRIPTOR = "android.content.IRestrictionsManager";
        static final int TRANSACTION_createLocalApprovalIntent = 6;
        static final int TRANSACTION_getApplicationRestrictions = 1;
        static final int TRANSACTION_getApplicationRestrictionsPerAdminForUser = 2;
        static final int TRANSACTION_hasRestrictionsProvider = 3;
        static final int TRANSACTION_notifyPermissionResponse = 5;
        static final int TRANSACTION_requestPermission = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.IRestrictionsManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.IRestrictionsManager)) {
                return (android.content.IRestrictionsManager) queryLocalInterface;
            }
            return new android.content.IRestrictionsManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getApplicationRestrictions";
                case 2:
                    return "getApplicationRestrictionsPerAdminForUser";
                case 3:
                    return "hasRestrictionsProvider";
                case 4:
                    return "requestPermission";
                case 5:
                    return "notifyPermissionResponse";
                case 6:
                    return "createLocalApprovalIntent";
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
                    android.os.Bundle applicationRestrictions = getApplicationRestrictions(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationRestrictions, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.os.Bundle> applicationRestrictionsPerAdminForUser = getApplicationRestrictionsPerAdminForUser(readInt, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(applicationRestrictionsPerAdminForUser, 1);
                    return true;
                case 3:
                    boolean hasRestrictionsProvider = hasRestrictionsProvider();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasRestrictionsProvider);
                    return true;
                case 4:
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestPermission(readString3, readString4, readString5, persistableBundle);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String readString6 = parcel.readString();
                    android.os.PersistableBundle persistableBundle2 = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyPermissionResponse(readString6, persistableBundle2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.content.Intent createLocalApprovalIntent = createLocalApprovalIntent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createLocalApprovalIntent, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.IRestrictionsManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.IRestrictionsManager.Stub.DESCRIPTOR;
            }

            @Override // android.content.IRestrictionsManager
            public android.os.Bundle getApplicationRestrictions(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IRestrictionsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRestrictionsManager
            public java.util.List<android.os.Bundle> getApplicationRestrictionsPerAdminForUser(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IRestrictionsManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRestrictionsManager
            public boolean hasRestrictionsProvider() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IRestrictionsManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRestrictionsManager
            public void requestPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IRestrictionsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRestrictionsManager
            public void notifyPermissionResponse(java.lang.String str, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IRestrictionsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRestrictionsManager
            public android.content.Intent createLocalApprovalIntent() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IRestrictionsManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.Intent) obtain2.readTypedObject(android.content.Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
