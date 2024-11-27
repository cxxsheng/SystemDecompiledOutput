package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public interface IRemoteAccessibilityInputConnection extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.inputmethod.IRemoteAccessibilityInputConnection";

    void clearMetaKeyStates(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException;

    void commitText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException;

    void deleteSurroundingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException;

    void getCursorCapsMode(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void getSurroundingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void performContextMenuAction(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException;

    void performEditorAction(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException;

    void sendKeyEvent(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.KeyEvent keyEvent) throws android.os.RemoteException;

    void setSelection(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException;

    public static class Default implements com.android.internal.inputmethod.IRemoteAccessibilityInputConnection {
        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void commitText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void setSelection(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void getSurroundingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void deleteSurroundingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void sendKeyEvent(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.KeyEvent keyEvent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void performEditorAction(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void performContextMenuAction(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void getCursorCapsMode(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void clearMetaKeyStates(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.inputmethod.IRemoteAccessibilityInputConnection {
        static final int TRANSACTION_clearMetaKeyStates = 9;
        static final int TRANSACTION_commitText = 1;
        static final int TRANSACTION_deleteSurroundingText = 4;
        static final int TRANSACTION_getCursorCapsMode = 8;
        static final int TRANSACTION_getSurroundingText = 3;
        static final int TRANSACTION_performContextMenuAction = 7;
        static final int TRANSACTION_performEditorAction = 6;
        static final int TRANSACTION_sendKeyEvent = 5;
        static final int TRANSACTION_setSelection = 2;

        public Stub() {
            attachInterface(this, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
        }

        public static com.android.internal.inputmethod.IRemoteAccessibilityInputConnection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.inputmethod.IRemoteAccessibilityInputConnection)) {
                return (com.android.internal.inputmethod.IRemoteAccessibilityInputConnection) queryLocalInterface;
            }
            return new com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "commitText";
                case 2:
                    return "setSelection";
                case 3:
                    return "getSurroundingText";
                case 4:
                    return "deleteSurroundingText";
                case 5:
                    return "sendKeyEvent";
                case 6:
                    return "performEditorAction";
                case 7:
                    return "performContextMenuAction";
                case 8:
                    return "getCursorCapsMode";
                case 9:
                    return "clearMetaKeyStates";
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
                parcel.enforceInterface(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt = parcel.readInt();
                    android.view.inputmethod.TextAttribute textAttribute = (android.view.inputmethod.TextAttribute) parcel.readTypedObject(android.view.inputmethod.TextAttribute.CREATOR);
                    parcel.enforceNoDataAvail();
                    commitText(inputConnectionCommandHeader, charSequence, readInt, textAttribute);
                    return true;
                case 2:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader2 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSelection(inputConnectionCommandHeader2, readInt2, readInt3);
                    return true;
                case 3:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader3 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSurroundingText(inputConnectionCommandHeader3, readInt4, readInt5, readInt6, androidFuture);
                    return true;
                case 4:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader4 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteSurroundingText(inputConnectionCommandHeader4, readInt7, readInt8);
                    return true;
                case 5:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader5 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendKeyEvent(inputConnectionCommandHeader5, keyEvent);
                    return true;
                case 6:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader6 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    performEditorAction(inputConnectionCommandHeader6, readInt9);
                    return true;
                case 7:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader7 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    performContextMenuAction(inputConnectionCommandHeader7, readInt10);
                    return true;
                case 8:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader8 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt11 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture2 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCursorCapsMode(inputConnectionCommandHeader8, readInt11, androidFuture2);
                    return true;
                case 9:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader9 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearMetaKeyStates(inputConnectionCommandHeader9, readInt12);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.inputmethod.IRemoteAccessibilityInputConnection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void commitText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeTypedObject(textAttribute, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void setSelection(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void getSurroundingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void deleteSurroundingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void sendKeyEvent(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.KeyEvent keyEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void performEditorAction(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void performContextMenuAction(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void getCursorCapsMode(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void clearMetaKeyStates(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
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
