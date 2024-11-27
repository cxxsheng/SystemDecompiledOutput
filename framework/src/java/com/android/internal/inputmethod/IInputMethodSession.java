package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public interface IInputMethodSession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.inputmethod.IInputMethodSession";

    void appPrivateCommand(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void displayCompletions(android.view.inputmethod.CompletionInfo[] completionInfoArr) throws android.os.RemoteException;

    void finishInput() throws android.os.RemoteException;

    void finishSession() throws android.os.RemoteException;

    void invalidateInput(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) throws android.os.RemoteException;

    void removeImeSurface() throws android.os.RemoteException;

    void updateCursor(android.graphics.Rect rect) throws android.os.RemoteException;

    void updateCursorAnchorInfo(android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo) throws android.os.RemoteException;

    void updateExtractedText(int i, android.view.inputmethod.ExtractedText extractedText) throws android.os.RemoteException;

    void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException;

    void viewClicked(boolean z) throws android.os.RemoteException;

    public static class Default implements com.android.internal.inputmethod.IInputMethodSession {
        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void updateExtractedText(int i, android.view.inputmethod.ExtractedText extractedText) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void viewClicked(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void updateCursor(android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void displayCompletions(android.view.inputmethod.CompletionInfo[] completionInfoArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void appPrivateCommand(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void finishSession() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void updateCursorAnchorInfo(android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void removeImeSurface() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void finishInput() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void invalidateInput(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.inputmethod.IInputMethodSession {
        static final int TRANSACTION_appPrivateCommand = 6;
        static final int TRANSACTION_displayCompletions = 5;
        static final int TRANSACTION_finishInput = 10;
        static final int TRANSACTION_finishSession = 7;
        static final int TRANSACTION_invalidateInput = 11;
        static final int TRANSACTION_removeImeSurface = 9;
        static final int TRANSACTION_updateCursor = 4;
        static final int TRANSACTION_updateCursorAnchorInfo = 8;
        static final int TRANSACTION_updateExtractedText = 1;
        static final int TRANSACTION_updateSelection = 2;
        static final int TRANSACTION_viewClicked = 3;

        public Stub() {
            attachInterface(this, com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
        }

        public static com.android.internal.inputmethod.IInputMethodSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.inputmethod.IInputMethodSession)) {
                return (com.android.internal.inputmethod.IInputMethodSession) queryLocalInterface;
            }
            return new com.android.internal.inputmethod.IInputMethodSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateExtractedText";
                case 2:
                    return "updateSelection";
                case 3:
                    return "viewClicked";
                case 4:
                    return "updateCursor";
                case 5:
                    return "displayCompletions";
                case 6:
                    return "appPrivateCommand";
                case 7:
                    return "finishSession";
                case 8:
                    return "updateCursorAnchorInfo";
                case 9:
                    return "removeImeSurface";
                case 10:
                    return "finishInput";
                case 11:
                    return "invalidateInput";
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
                parcel.enforceInterface(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.view.inputmethod.ExtractedText extractedText = (android.view.inputmethod.ExtractedText) parcel.readTypedObject(android.view.inputmethod.ExtractedText.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateExtractedText(readInt, extractedText);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateSelection(readInt2, readInt3, readInt4, readInt5, readInt6, readInt7);
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    viewClicked(readBoolean);
                    return true;
                case 4:
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCursor(rect);
                    return true;
                case 5:
                    android.view.inputmethod.CompletionInfo[] completionInfoArr = (android.view.inputmethod.CompletionInfo[]) parcel.createTypedArray(android.view.inputmethod.CompletionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    displayCompletions(completionInfoArr);
                    return true;
                case 6:
                    java.lang.String readString = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    appPrivateCommand(readString, bundle);
                    return true;
                case 7:
                    finishSession();
                    return true;
                case 8:
                    android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo = (android.view.inputmethod.CursorAnchorInfo) parcel.readTypedObject(android.view.inputmethod.CursorAnchorInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCursorAnchorInfo(cursorAnchorInfo);
                    return true;
                case 9:
                    removeImeSurface();
                    return true;
                case 10:
                    finishInput();
                    return true;
                case 11:
                    android.view.inputmethod.EditorInfo editorInfo = (android.view.inputmethod.EditorInfo) parcel.readTypedObject(android.view.inputmethod.EditorInfo.CREATOR);
                    com.android.internal.inputmethod.IRemoteInputConnection asInterface = com.android.internal.inputmethod.IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    invalidateInput(editorInfo, asInterface, readInt8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.inputmethod.IInputMethodSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void updateExtractedText(int i, android.view.inputmethod.ExtractedText extractedText) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(extractedText, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void viewClicked(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void updateCursor(android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void displayCompletions(android.view.inputmethod.CompletionInfo[] completionInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
                    obtain.writeTypedArray(completionInfoArr, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void appPrivateCommand(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void finishSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void updateCursorAnchorInfo(android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
                    obtain.writeTypedObject(cursorAnchorInfo, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void removeImeSurface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void finishInput() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void invalidateInput(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodSession.DESCRIPTOR);
                    obtain.writeTypedObject(editorInfo, 0);
                    obtain.writeStrongInterface(iRemoteInputConnection);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
