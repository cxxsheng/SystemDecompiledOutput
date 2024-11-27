package android.content.pm;

/* loaded from: classes.dex */
public interface IOnAppsChangedListener extends android.os.IInterface {
    void onPackageAdded(android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException;

    void onPackageChanged(android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException;

    void onPackageLoadingProgressChanged(android.os.UserHandle userHandle, java.lang.String str, float f) throws android.os.RemoteException;

    void onPackageRemoved(android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException;

    void onPackagesAvailable(android.os.UserHandle userHandle, java.lang.String[] strArr, boolean z) throws android.os.RemoteException;

    void onPackagesSuspended(android.os.UserHandle userHandle, java.lang.String[] strArr, android.os.Bundle bundle) throws android.os.RemoteException;

    void onPackagesUnavailable(android.os.UserHandle userHandle, java.lang.String[] strArr, boolean z) throws android.os.RemoteException;

    void onPackagesUnsuspended(android.os.UserHandle userHandle, java.lang.String[] strArr) throws android.os.RemoteException;

    void onShortcutChanged(android.os.UserHandle userHandle, java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IOnAppsChangedListener {
        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackageRemoved(android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackageAdded(android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackageChanged(android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackagesAvailable(android.os.UserHandle userHandle, java.lang.String[] strArr, boolean z) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackagesUnavailable(android.os.UserHandle userHandle, java.lang.String[] strArr, boolean z) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackagesSuspended(android.os.UserHandle userHandle, java.lang.String[] strArr, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackagesUnsuspended(android.os.UserHandle userHandle, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onShortcutChanged(android.os.UserHandle userHandle, java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackageLoadingProgressChanged(android.os.UserHandle userHandle, java.lang.String str, float f) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IOnAppsChangedListener {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.IOnAppsChangedListener";
        static final int TRANSACTION_onPackageAdded = 2;
        static final int TRANSACTION_onPackageChanged = 3;
        static final int TRANSACTION_onPackageLoadingProgressChanged = 9;
        static final int TRANSACTION_onPackageRemoved = 1;
        static final int TRANSACTION_onPackagesAvailable = 4;
        static final int TRANSACTION_onPackagesSuspended = 6;
        static final int TRANSACTION_onPackagesUnavailable = 5;
        static final int TRANSACTION_onPackagesUnsuspended = 7;
        static final int TRANSACTION_onShortcutChanged = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.pm.IOnAppsChangedListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IOnAppsChangedListener)) {
                return (android.content.pm.IOnAppsChangedListener) queryLocalInterface;
            }
            return new android.content.pm.IOnAppsChangedListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onPackageRemoved";
                case 2:
                    return "onPackageAdded";
                case 3:
                    return "onPackageChanged";
                case 4:
                    return "onPackagesAvailable";
                case 5:
                    return "onPackagesUnavailable";
                case 6:
                    return "onPackagesSuspended";
                case 7:
                    return "onPackagesUnsuspended";
                case 8:
                    return "onShortcutChanged";
                case 9:
                    return "onPackageLoadingProgressChanged";
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
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPackageRemoved(userHandle, readString);
                    return true;
                case 2:
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPackageAdded(userHandle2, readString2);
                    return true;
                case 3:
                    android.os.UserHandle userHandle3 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPackageChanged(userHandle3, readString3);
                    return true;
                case 4:
                    android.os.UserHandle userHandle4 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onPackagesAvailable(userHandle4, createStringArray, readBoolean);
                    return true;
                case 5:
                    android.os.UserHandle userHandle5 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onPackagesUnavailable(userHandle5, createStringArray2, readBoolean2);
                    return true;
                case 6:
                    android.os.UserHandle userHandle6 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPackagesSuspended(userHandle6, createStringArray3, bundle);
                    return true;
                case 7:
                    android.os.UserHandle userHandle7 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String[] createStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    onPackagesUnsuspended(userHandle7, createStringArray4);
                    return true;
                case 8:
                    android.os.UserHandle userHandle8 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String readString4 = parcel.readString();
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onShortcutChanged(userHandle8, readString4, parceledListSlice);
                    return true;
                case 9:
                    android.os.UserHandle userHandle9 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String readString5 = parcel.readString();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onPackageLoadingProgressChanged(userHandle9, readString5, readFloat);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IOnAppsChangedListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IOnAppsChangedListener.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageRemoved(android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOnAppsChangedListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageAdded(android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOnAppsChangedListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageChanged(android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOnAppsChangedListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesAvailable(android.os.UserHandle userHandle, java.lang.String[] strArr, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOnAppsChangedListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesUnavailable(android.os.UserHandle userHandle, java.lang.String[] strArr, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOnAppsChangedListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesSuspended(android.os.UserHandle userHandle, java.lang.String[] strArr, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOnAppsChangedListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesUnsuspended(android.os.UserHandle userHandle, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOnAppsChangedListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onShortcutChanged(android.os.UserHandle userHandle, java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOnAppsChangedListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageLoadingProgressChanged(android.os.UserHandle userHandle, java.lang.String str, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOnAppsChangedListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    obtain.writeFloat(f);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
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
