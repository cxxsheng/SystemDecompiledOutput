package android.window;

/* loaded from: classes4.dex */
public interface IDisplayAreaOrganizerController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IDisplayAreaOrganizerController";

    android.window.DisplayAreaAppearedInfo createTaskDisplayArea(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, int i, int i2, java.lang.String str) throws android.os.RemoteException;

    void deleteTaskDisplayArea(android.window.WindowContainerToken windowContainerToken) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.window.DisplayAreaAppearedInfo> registerOrganizer(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, int i) throws android.os.RemoteException;

    void unregisterOrganizer(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer) throws android.os.RemoteException;

    public static class Default implements android.window.IDisplayAreaOrganizerController {
        @Override // android.window.IDisplayAreaOrganizerController
        public android.content.pm.ParceledListSlice<android.window.DisplayAreaAppearedInfo> registerOrganizer(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.window.IDisplayAreaOrganizerController
        public void unregisterOrganizer(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer) throws android.os.RemoteException {
        }

        @Override // android.window.IDisplayAreaOrganizerController
        public android.window.DisplayAreaAppearedInfo createTaskDisplayArea(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.window.IDisplayAreaOrganizerController
        public void deleteTaskDisplayArea(android.window.WindowContainerToken windowContainerToken) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IDisplayAreaOrganizerController {
        static final int TRANSACTION_createTaskDisplayArea = 3;
        static final int TRANSACTION_deleteTaskDisplayArea = 4;
        static final int TRANSACTION_registerOrganizer = 1;
        static final int TRANSACTION_unregisterOrganizer = 2;

        public Stub() {
            attachInterface(this, android.window.IDisplayAreaOrganizerController.DESCRIPTOR);
        }

        public static android.window.IDisplayAreaOrganizerController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IDisplayAreaOrganizerController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IDisplayAreaOrganizerController)) {
                return (android.window.IDisplayAreaOrganizerController) queryLocalInterface;
            }
            return new android.window.IDisplayAreaOrganizerController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerOrganizer";
                case 2:
                    return "unregisterOrganizer";
                case 3:
                    return "createTaskDisplayArea";
                case 4:
                    return "deleteTaskDisplayArea";
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
                parcel.enforceInterface(android.window.IDisplayAreaOrganizerController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IDisplayAreaOrganizerController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.window.IDisplayAreaOrganizer asInterface = android.window.IDisplayAreaOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.window.DisplayAreaAppearedInfo> registerOrganizer = registerOrganizer(asInterface, readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerOrganizer, 1);
                    return true;
                case 2:
                    android.window.IDisplayAreaOrganizer asInterface2 = android.window.IDisplayAreaOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterOrganizer(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.window.IDisplayAreaOrganizer asInterface3 = android.window.IDisplayAreaOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.window.DisplayAreaAppearedInfo createTaskDisplayArea = createTaskDisplayArea(asInterface3, readInt2, readInt3, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createTaskDisplayArea, 1);
                    return true;
                case 4:
                    android.window.WindowContainerToken windowContainerToken = (android.window.WindowContainerToken) parcel.readTypedObject(android.window.WindowContainerToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteTaskDisplayArea(windowContainerToken);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IDisplayAreaOrganizerController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IDisplayAreaOrganizerController.DESCRIPTOR;
            }

            @Override // android.window.IDisplayAreaOrganizerController
            public android.content.pm.ParceledListSlice<android.window.DisplayAreaAppearedInfo> registerOrganizer(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IDisplayAreaOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayAreaOrganizer);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizerController
            public void unregisterOrganizer(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IDisplayAreaOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayAreaOrganizer);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizerController
            public android.window.DisplayAreaAppearedInfo createTaskDisplayArea(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IDisplayAreaOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayAreaOrganizer);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.window.DisplayAreaAppearedInfo) obtain2.readTypedObject(android.window.DisplayAreaAppearedInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizerController
            public void deleteTaskDisplayArea(android.window.WindowContainerToken windowContainerToken) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IDisplayAreaOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
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
