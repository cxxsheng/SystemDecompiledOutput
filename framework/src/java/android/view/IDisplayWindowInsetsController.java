package android.view;

/* loaded from: classes4.dex */
public interface IDisplayWindowInsetsController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.IDisplayWindowInsetsController";

    void hideInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException;

    void insetsChanged(android.view.InsetsState insetsState) throws android.os.RemoteException;

    void insetsControlChanged(android.view.InsetsState insetsState, android.view.InsetsSourceControl[] insetsSourceControlArr) throws android.os.RemoteException;

    void showInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException;

    void topFocusedWindowChanged(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    public static class Default implements android.view.IDisplayWindowInsetsController {
        @Override // android.view.IDisplayWindowInsetsController
        public void topFocusedWindowChanged(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IDisplayWindowInsetsController
        public void insetsChanged(android.view.InsetsState insetsState) throws android.os.RemoteException {
        }

        @Override // android.view.IDisplayWindowInsetsController
        public void insetsControlChanged(android.view.InsetsState insetsState, android.view.InsetsSourceControl[] insetsSourceControlArr) throws android.os.RemoteException {
        }

        @Override // android.view.IDisplayWindowInsetsController
        public void showInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException {
        }

        @Override // android.view.IDisplayWindowInsetsController
        public void hideInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IDisplayWindowInsetsController {
        static final int TRANSACTION_hideInsets = 5;
        static final int TRANSACTION_insetsChanged = 2;
        static final int TRANSACTION_insetsControlChanged = 3;
        static final int TRANSACTION_showInsets = 4;
        static final int TRANSACTION_topFocusedWindowChanged = 1;

        public Stub() {
            attachInterface(this, android.view.IDisplayWindowInsetsController.DESCRIPTOR);
        }

        public static android.view.IDisplayWindowInsetsController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.IDisplayWindowInsetsController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IDisplayWindowInsetsController)) {
                return (android.view.IDisplayWindowInsetsController) queryLocalInterface;
            }
            return new android.view.IDisplayWindowInsetsController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "topFocusedWindowChanged";
                case 2:
                    return "insetsChanged";
                case 3:
                    return "insetsControlChanged";
                case 4:
                    return "showInsets";
                case 5:
                    return "hideInsets";
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
                parcel.enforceInterface(android.view.IDisplayWindowInsetsController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.IDisplayWindowInsetsController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    topFocusedWindowChanged(componentName, readInt);
                    return true;
                case 2:
                    android.view.InsetsState insetsState = (android.view.InsetsState) parcel.readTypedObject(android.view.InsetsState.CREATOR);
                    parcel.enforceNoDataAvail();
                    insetsChanged(insetsState);
                    return true;
                case 3:
                    android.view.InsetsState insetsState2 = (android.view.InsetsState) parcel.readTypedObject(android.view.InsetsState.CREATOR);
                    android.view.InsetsSourceControl[] insetsSourceControlArr = (android.view.InsetsSourceControl[]) parcel.createTypedArray(android.view.InsetsSourceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    insetsControlChanged(insetsState2, insetsSourceControlArr);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    android.view.inputmethod.ImeTracker.Token token = (android.view.inputmethod.ImeTracker.Token) parcel.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    showInsets(readInt2, readBoolean, token);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    android.view.inputmethod.ImeTracker.Token token2 = (android.view.inputmethod.ImeTracker.Token) parcel.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    hideInsets(readInt3, readBoolean2, token2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IDisplayWindowInsetsController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IDisplayWindowInsetsController.DESCRIPTOR;
            }

            @Override // android.view.IDisplayWindowInsetsController
            public void topFocusedWindowChanged(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayWindowInsetsController.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowInsetsController
            public void insetsChanged(android.view.InsetsState insetsState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayWindowInsetsController.DESCRIPTOR);
                    obtain.writeTypedObject(insetsState, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowInsetsController
            public void insetsControlChanged(android.view.InsetsState insetsState, android.view.InsetsSourceControl[] insetsSourceControlArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayWindowInsetsController.DESCRIPTOR);
                    obtain.writeTypedObject(insetsState, 0);
                    obtain.writeTypedArray(insetsSourceControlArr, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowInsetsController
            public void showInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayWindowInsetsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowInsetsController
            public void hideInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayWindowInsetsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
