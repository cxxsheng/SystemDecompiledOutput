package android.service.dreams;

/* loaded from: classes3.dex */
public interface IDreamManager extends android.os.IInterface {
    void awaken() throws android.os.RemoteException;

    void dream() throws android.os.RemoteException;

    void finishSelf(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void forceAmbientDisplayEnabled(boolean z) throws android.os.RemoteException;

    android.content.ComponentName getDefaultDreamComponentForUser(int i) throws android.os.RemoteException;

    android.content.ComponentName[] getDreamComponents() throws android.os.RemoteException;

    android.content.ComponentName[] getDreamComponentsForUser(int i) throws android.os.RemoteException;

    boolean isDreaming() throws android.os.RemoteException;

    boolean isDreamingOrInPreview() throws android.os.RemoteException;

    void registerDreamOverlayService(android.content.ComponentName componentName) throws android.os.RemoteException;

    void setDreamComponents(android.content.ComponentName[] componentNameArr) throws android.os.RemoteException;

    void setDreamComponentsForUser(int i, android.content.ComponentName[] componentNameArr) throws android.os.RemoteException;

    void setSystemDreamComponent(android.content.ComponentName componentName) throws android.os.RemoteException;

    void startDozing(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    void startDreamActivity(android.content.Intent intent) throws android.os.RemoteException;

    void stopDozing(android.os.IBinder iBinder) throws android.os.RemoteException;

    void testDream(int i, android.content.ComponentName componentName) throws android.os.RemoteException;

    public static class Default implements android.service.dreams.IDreamManager {
        @Override // android.service.dreams.IDreamManager
        public void dream() throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void awaken() throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void setDreamComponents(android.content.ComponentName[] componentNameArr) throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public android.content.ComponentName[] getDreamComponents() throws android.os.RemoteException {
            return null;
        }

        @Override // android.service.dreams.IDreamManager
        public android.content.ComponentName getDefaultDreamComponentForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.service.dreams.IDreamManager
        public void testDream(int i, android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public boolean isDreaming() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.dreams.IDreamManager
        public boolean isDreamingOrInPreview() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.dreams.IDreamManager
        public void finishSelf(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void startDozing(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void stopDozing(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void forceAmbientDisplayEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public android.content.ComponentName[] getDreamComponentsForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.service.dreams.IDreamManager
        public void setDreamComponentsForUser(int i, android.content.ComponentName[] componentNameArr) throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void setSystemDreamComponent(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void registerDreamOverlayService(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void startDreamActivity(android.content.Intent intent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.dreams.IDreamManager {
        public static final java.lang.String DESCRIPTOR = "android.service.dreams.IDreamManager";
        static final int TRANSACTION_awaken = 2;
        static final int TRANSACTION_dream = 1;
        static final int TRANSACTION_finishSelf = 9;
        static final int TRANSACTION_forceAmbientDisplayEnabled = 12;
        static final int TRANSACTION_getDefaultDreamComponentForUser = 5;
        static final int TRANSACTION_getDreamComponents = 4;
        static final int TRANSACTION_getDreamComponentsForUser = 13;
        static final int TRANSACTION_isDreaming = 7;
        static final int TRANSACTION_isDreamingOrInPreview = 8;
        static final int TRANSACTION_registerDreamOverlayService = 16;
        static final int TRANSACTION_setDreamComponents = 3;
        static final int TRANSACTION_setDreamComponentsForUser = 14;
        static final int TRANSACTION_setSystemDreamComponent = 15;
        static final int TRANSACTION_startDozing = 10;
        static final int TRANSACTION_startDreamActivity = 17;
        static final int TRANSACTION_stopDozing = 11;
        static final int TRANSACTION_testDream = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.dreams.IDreamManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.dreams.IDreamManager)) {
                return (android.service.dreams.IDreamManager) queryLocalInterface;
            }
            return new android.service.dreams.IDreamManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.content.Context.DREAM_SERVICE;
                case 2:
                    return "awaken";
                case 3:
                    return "setDreamComponents";
                case 4:
                    return "getDreamComponents";
                case 5:
                    return "getDefaultDreamComponentForUser";
                case 6:
                    return "testDream";
                case 7:
                    return "isDreaming";
                case 8:
                    return "isDreamingOrInPreview";
                case 9:
                    return "finishSelf";
                case 10:
                    return "startDozing";
                case 11:
                    return "stopDozing";
                case 12:
                    return "forceAmbientDisplayEnabled";
                case 13:
                    return "getDreamComponentsForUser";
                case 14:
                    return "setDreamComponentsForUser";
                case 15:
                    return "setSystemDreamComponent";
                case 16:
                    return "registerDreamOverlayService";
                case 17:
                    return "startDreamActivity";
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
                    dream();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    awaken();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.content.ComponentName[] componentNameArr = (android.content.ComponentName[]) parcel.createTypedArray(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDreamComponents(componentNameArr);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.content.ComponentName[] dreamComponents = getDreamComponents();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(dreamComponents, 1);
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName defaultDreamComponentForUser = getDefaultDreamComponentForUser(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultDreamComponentForUser, 1);
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    testDream(readInt2, componentName);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean isDreaming = isDreaming();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDreaming);
                    return true;
                case 8:
                    boolean isDreamingOrInPreview = isDreamingOrInPreview();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDreamingOrInPreview);
                    return true;
                case 9:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishSelf(readStrongBinder, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startDozing(readStrongBinder2, readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopDozing(readStrongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceAmbientDisplayEnabled(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName[] dreamComponentsForUser = getDreamComponentsForUser(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(dreamComponentsForUser, 1);
                    return true;
                case 14:
                    int readInt6 = parcel.readInt();
                    android.content.ComponentName[] componentNameArr2 = (android.content.ComponentName[]) parcel.createTypedArray(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDreamComponentsForUser(readInt6, componentNameArr2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSystemDreamComponent(componentName2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.content.ComponentName componentName3 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerDreamOverlayService(componentName3);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    startDreamActivity(intent);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.dreams.IDreamManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.dreams.IDreamManager.Stub.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamManager
            public void dream() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void awaken() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDreamComponents(android.content.ComponentName[] componentNameArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(componentNameArr, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public android.content.ComponentName[] getDreamComponents() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName[]) obtain2.createTypedArray(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public android.content.ComponentName getDefaultDreamComponentForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void testDream(int i, android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public boolean isDreaming() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public boolean isDreamingOrInPreview() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void finishSelf(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void startDozing(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void stopDozing(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void forceAmbientDisplayEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public android.content.ComponentName[] getDreamComponentsForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName[]) obtain2.createTypedArray(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDreamComponentsForUser(int i, android.content.ComponentName[] componentNameArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(componentNameArr, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setSystemDreamComponent(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void registerDreamOverlayService(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void startDreamActivity(android.content.Intent intent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16;
        }
    }
}
