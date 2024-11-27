package android.view;

/* loaded from: classes4.dex */
public interface IDisplayChangeWindowController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.IDisplayChangeWindowController";

    void onDisplayChange(int i, int i2, int i3, android.window.DisplayAreaInfo displayAreaInfo, android.view.IDisplayChangeWindowCallback iDisplayChangeWindowCallback) throws android.os.RemoteException;

    public static class Default implements android.view.IDisplayChangeWindowController {
        @Override // android.view.IDisplayChangeWindowController
        public void onDisplayChange(int i, int i2, int i3, android.window.DisplayAreaInfo displayAreaInfo, android.view.IDisplayChangeWindowCallback iDisplayChangeWindowCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IDisplayChangeWindowController {
        static final int TRANSACTION_onDisplayChange = 1;

        public Stub() {
            attachInterface(this, android.view.IDisplayChangeWindowController.DESCRIPTOR);
        }

        public static android.view.IDisplayChangeWindowController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.IDisplayChangeWindowController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IDisplayChangeWindowController)) {
                return (android.view.IDisplayChangeWindowController) queryLocalInterface;
            }
            return new android.view.IDisplayChangeWindowController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDisplayChange";
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
                parcel.enforceInterface(android.view.IDisplayChangeWindowController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.IDisplayChangeWindowController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    android.window.DisplayAreaInfo displayAreaInfo = (android.window.DisplayAreaInfo) parcel.readTypedObject(android.window.DisplayAreaInfo.CREATOR);
                    android.view.IDisplayChangeWindowCallback asInterface = android.view.IDisplayChangeWindowCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onDisplayChange(readInt, readInt2, readInt3, displayAreaInfo, asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IDisplayChangeWindowController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IDisplayChangeWindowController.DESCRIPTOR;
            }

            @Override // android.view.IDisplayChangeWindowController
            public void onDisplayChange(int i, int i2, int i3, android.window.DisplayAreaInfo displayAreaInfo, android.view.IDisplayChangeWindowCallback iDisplayChangeWindowCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayChangeWindowController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(displayAreaInfo, 0);
                    obtain.writeStrongInterface(iDisplayChangeWindowCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
