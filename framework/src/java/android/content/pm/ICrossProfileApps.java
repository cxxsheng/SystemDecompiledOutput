package android.content.pm;

/* loaded from: classes.dex */
public interface ICrossProfileApps extends android.os.IInterface {
    boolean canConfigureInteractAcrossProfiles(int i, java.lang.String str) throws android.os.RemoteException;

    boolean canInteractAcrossProfiles(java.lang.String str) throws android.os.RemoteException;

    boolean canRequestInteractAcrossProfiles(java.lang.String str) throws android.os.RemoteException;

    boolean canUserAttemptToConfigureInteractAcrossProfiles(int i, java.lang.String str) throws android.os.RemoteException;

    void clearInteractAcrossProfilesAppOps(int i) throws android.os.RemoteException;

    java.util.List<android.os.UserHandle> getTargetUserProfiles(java.lang.String str) throws android.os.RemoteException;

    void resetInteractAcrossProfilesAppOps(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void setInteractAcrossProfilesAppOp(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    void startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, int i, boolean z, android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException;

    void startActivityAsUserByIntent(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, int i, android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.content.pm.ICrossProfileApps {
        @Override // android.content.pm.ICrossProfileApps
        public void startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, int i, boolean z, android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ICrossProfileApps
        public void startActivityAsUserByIntent(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, int i, android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ICrossProfileApps
        public java.util.List<android.os.UserHandle> getTargetUserProfiles(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ICrossProfileApps
        public boolean canInteractAcrossProfiles(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.ICrossProfileApps
        public boolean canRequestInteractAcrossProfiles(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.ICrossProfileApps
        public void setInteractAcrossProfilesAppOp(int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ICrossProfileApps
        public boolean canConfigureInteractAcrossProfiles(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.ICrossProfileApps
        public boolean canUserAttemptToConfigureInteractAcrossProfiles(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.ICrossProfileApps
        public void resetInteractAcrossProfilesAppOps(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ICrossProfileApps
        public void clearInteractAcrossProfilesAppOps(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.ICrossProfileApps {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.ICrossProfileApps";
        static final int TRANSACTION_canConfigureInteractAcrossProfiles = 7;
        static final int TRANSACTION_canInteractAcrossProfiles = 4;
        static final int TRANSACTION_canRequestInteractAcrossProfiles = 5;
        static final int TRANSACTION_canUserAttemptToConfigureInteractAcrossProfiles = 8;
        static final int TRANSACTION_clearInteractAcrossProfilesAppOps = 10;
        static final int TRANSACTION_getTargetUserProfiles = 3;
        static final int TRANSACTION_resetInteractAcrossProfilesAppOps = 9;
        static final int TRANSACTION_setInteractAcrossProfilesAppOp = 6;
        static final int TRANSACTION_startActivityAsUser = 1;
        static final int TRANSACTION_startActivityAsUserByIntent = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.pm.ICrossProfileApps asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.ICrossProfileApps)) {
                return (android.content.pm.ICrossProfileApps) queryLocalInterface;
            }
            return new android.content.pm.ICrossProfileApps.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startActivityAsUser";
                case 2:
                    return "startActivityAsUserByIntent";
                case 3:
                    return "getTargetUserProfiles";
                case 4:
                    return "canInteractAcrossProfiles";
                case 5:
                    return "canRequestInteractAcrossProfiles";
                case 6:
                    return "setInteractAcrossProfilesAppOp";
                case 7:
                    return "canConfigureInteractAcrossProfiles";
                case 8:
                    return "canUserAttemptToConfigureInteractAcrossProfiles";
                case 9:
                    return "resetInteractAcrossProfilesAppOps";
                case 10:
                    return "clearInteractAcrossProfilesAppOps";
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
                    android.app.IApplicationThread asInterface = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startActivityAsUser(asInterface, readString, readString2, componentName, readInt, readBoolean, readStrongBinder, bundle);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.app.IApplicationThread asInterface2 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    int readInt2 = parcel.readInt();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startActivityAsUserByIntent(asInterface2, readString3, readString4, intent, readInt2, readStrongBinder2, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.os.UserHandle> targetUserProfiles = getTargetUserProfiles(readString5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(targetUserProfiles, 1);
                    return true;
                case 4:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canInteractAcrossProfiles = canInteractAcrossProfiles(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canInteractAcrossProfiles);
                    return true;
                case 5:
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canRequestInteractAcrossProfiles = canRequestInteractAcrossProfiles(readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canRequestInteractAcrossProfiles);
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setInteractAcrossProfilesAppOp(readInt3, readString8, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canConfigureInteractAcrossProfiles = canConfigureInteractAcrossProfiles(readInt5, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canConfigureInteractAcrossProfiles);
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canUserAttemptToConfigureInteractAcrossProfiles = canUserAttemptToConfigureInteractAcrossProfiles(readInt6, readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canUserAttemptToConfigureInteractAcrossProfiles);
                    return true;
                case 9:
                    int readInt7 = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    resetInteractAcrossProfilesAppOps(readInt7, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearInteractAcrossProfilesAppOps(readInt8);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.ICrossProfileApps {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.ICrossProfileApps.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.ICrossProfileApps
            public void startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, int i, boolean z, android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ICrossProfileApps.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public void startActivityAsUserByIntent(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, int i, android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ICrossProfileApps.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public java.util.List<android.os.UserHandle> getTargetUserProfiles(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ICrossProfileApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public boolean canInteractAcrossProfiles(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ICrossProfileApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public boolean canRequestInteractAcrossProfiles(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ICrossProfileApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public void setInteractAcrossProfilesAppOp(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ICrossProfileApps.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public boolean canConfigureInteractAcrossProfiles(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ICrossProfileApps.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public boolean canUserAttemptToConfigureInteractAcrossProfiles(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ICrossProfileApps.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public void resetInteractAcrossProfilesAppOps(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ICrossProfileApps.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public void clearInteractAcrossProfilesAppOps(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ICrossProfileApps.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
