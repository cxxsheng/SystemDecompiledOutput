package android.service.autofill;

/* loaded from: classes3.dex */
public interface IInlineSuggestionUiCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.autofill.IInlineSuggestionUiCallback";

    void onClick() throws android.os.RemoteException;

    void onContent(android.service.autofill.IInlineSuggestionUi iInlineSuggestionUi, android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws android.os.RemoteException;

    void onError() throws android.os.RemoteException;

    void onLongClick() throws android.os.RemoteException;

    void onStartIntentSender(android.content.IntentSender intentSender) throws android.os.RemoteException;

    void onTransferTouchFocusToImeWindow(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    public static class Default implements android.service.autofill.IInlineSuggestionUiCallback {
        @Override // android.service.autofill.IInlineSuggestionUiCallback
        public void onClick() throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionUiCallback
        public void onLongClick() throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionUiCallback
        public void onContent(android.service.autofill.IInlineSuggestionUi iInlineSuggestionUi, android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionUiCallback
        public void onError() throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionUiCallback
        public void onTransferTouchFocusToImeWindow(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionUiCallback
        public void onStartIntentSender(android.content.IntentSender intentSender) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.autofill.IInlineSuggestionUiCallback {
        static final int TRANSACTION_onClick = 1;
        static final int TRANSACTION_onContent = 3;
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onLongClick = 2;
        static final int TRANSACTION_onStartIntentSender = 6;
        static final int TRANSACTION_onTransferTouchFocusToImeWindow = 5;

        public Stub() {
            attachInterface(this, android.service.autofill.IInlineSuggestionUiCallback.DESCRIPTOR);
        }

        public static android.service.autofill.IInlineSuggestionUiCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.autofill.IInlineSuggestionUiCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.autofill.IInlineSuggestionUiCallback)) {
                return (android.service.autofill.IInlineSuggestionUiCallback) queryLocalInterface;
            }
            return new android.service.autofill.IInlineSuggestionUiCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onClick";
                case 2:
                    return "onLongClick";
                case 3:
                    return "onContent";
                case 4:
                    return "onError";
                case 5:
                    return "onTransferTouchFocusToImeWindow";
                case 6:
                    return "onStartIntentSender";
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
                parcel.enforceInterface(android.service.autofill.IInlineSuggestionUiCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.autofill.IInlineSuggestionUiCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onClick();
                    return true;
                case 2:
                    onLongClick();
                    return true;
                case 3:
                    android.service.autofill.IInlineSuggestionUi asInterface = android.service.autofill.IInlineSuggestionUi.Stub.asInterface(parcel.readStrongBinder());
                    android.view.SurfaceControlViewHost.SurfacePackage surfacePackage = (android.view.SurfaceControlViewHost.SurfacePackage) parcel.readTypedObject(android.view.SurfaceControlViewHost.SurfacePackage.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onContent(asInterface, surfacePackage, readInt, readInt2);
                    return true;
                case 4:
                    onError();
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTransferTouchFocusToImeWindow(readStrongBinder, readInt3);
                    return true;
                case 6:
                    android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    onStartIntentSender(intentSender);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.autofill.IInlineSuggestionUiCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.autofill.IInlineSuggestionUiCallback.DESCRIPTOR;
            }

            @Override // android.service.autofill.IInlineSuggestionUiCallback
            public void onClick() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IInlineSuggestionUiCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionUiCallback
            public void onLongClick() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IInlineSuggestionUiCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionUiCallback
            public void onContent(android.service.autofill.IInlineSuggestionUi iInlineSuggestionUi, android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IInlineSuggestionUiCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iInlineSuggestionUi);
                    obtain.writeTypedObject(surfacePackage, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionUiCallback
            public void onError() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IInlineSuggestionUiCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionUiCallback
            public void onTransferTouchFocusToImeWindow(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IInlineSuggestionUiCallback.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionUiCallback
            public void onStartIntentSender(android.content.IntentSender intentSender) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IInlineSuggestionUiCallback.DESCRIPTOR);
                    obtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
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
