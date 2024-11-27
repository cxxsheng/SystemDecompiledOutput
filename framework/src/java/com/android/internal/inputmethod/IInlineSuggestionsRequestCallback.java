package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public interface IInlineSuggestionsRequestCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.inputmethod.IInlineSuggestionsRequestCallback";

    void onInlineSuggestionsRequest(android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, com.android.internal.inputmethod.IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback) throws android.os.RemoteException;

    void onInlineSuggestionsSessionInvalidated() throws android.os.RemoteException;

    void onInlineSuggestionsUnsupported() throws android.os.RemoteException;

    void onInputMethodFinishInput() throws android.os.RemoteException;

    void onInputMethodFinishInputView() throws android.os.RemoteException;

    void onInputMethodShowInputRequested(boolean z) throws android.os.RemoteException;

    void onInputMethodStartInput(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException;

    void onInputMethodStartInputView() throws android.os.RemoteException;

    public static class Default implements com.android.internal.inputmethod.IInlineSuggestionsRequestCallback {
        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInlineSuggestionsUnsupported() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInlineSuggestionsRequest(android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, com.android.internal.inputmethod.IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodStartInput(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodShowInputRequested(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodStartInputView() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodFinishInputView() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodFinishInput() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInlineSuggestionsSessionInvalidated() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.inputmethod.IInlineSuggestionsRequestCallback {
        static final int TRANSACTION_onInlineSuggestionsRequest = 2;
        static final int TRANSACTION_onInlineSuggestionsSessionInvalidated = 8;
        static final int TRANSACTION_onInlineSuggestionsUnsupported = 1;
        static final int TRANSACTION_onInputMethodFinishInput = 7;
        static final int TRANSACTION_onInputMethodFinishInputView = 6;
        static final int TRANSACTION_onInputMethodShowInputRequested = 4;
        static final int TRANSACTION_onInputMethodStartInput = 3;
        static final int TRANSACTION_onInputMethodStartInputView = 5;

        public Stub() {
            attachInterface(this, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR);
        }

        public static com.android.internal.inputmethod.IInlineSuggestionsRequestCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.inputmethod.IInlineSuggestionsRequestCallback)) {
                return (com.android.internal.inputmethod.IInlineSuggestionsRequestCallback) queryLocalInterface;
            }
            return new com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onInlineSuggestionsUnsupported";
                case 2:
                    return "onInlineSuggestionsRequest";
                case 3:
                    return "onInputMethodStartInput";
                case 4:
                    return "onInputMethodShowInputRequested";
                case 5:
                    return "onInputMethodStartInputView";
                case 6:
                    return "onInputMethodFinishInputView";
                case 7:
                    return "onInputMethodFinishInput";
                case 8:
                    return "onInlineSuggestionsSessionInvalidated";
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
                parcel.enforceInterface(com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onInlineSuggestionsUnsupported();
                    return true;
                case 2:
                    android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest = (android.view.inputmethod.InlineSuggestionsRequest) parcel.readTypedObject(android.view.inputmethod.InlineSuggestionsRequest.CREATOR);
                    com.android.internal.inputmethod.IInlineSuggestionsResponseCallback asInterface = com.android.internal.inputmethod.IInlineSuggestionsResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onInlineSuggestionsRequest(inlineSuggestionsRequest, asInterface);
                    return true;
                case 3:
                    android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onInputMethodStartInput(autofillId);
                    return true;
                case 4:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onInputMethodShowInputRequested(readBoolean);
                    return true;
                case 5:
                    onInputMethodStartInputView();
                    return true;
                case 6:
                    onInputMethodFinishInputView();
                    return true;
                case 7:
                    onInputMethodFinishInput();
                    return true;
                case 8:
                    onInlineSuggestionsSessionInvalidated();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.inputmethod.IInlineSuggestionsRequestCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInlineSuggestionsUnsupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInlineSuggestionsRequest(android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, com.android.internal.inputmethod.IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    obtain.writeTypedObject(inlineSuggestionsRequest, 0);
                    obtain.writeStrongInterface(iInlineSuggestionsResponseCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodStartInput(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodShowInputRequested(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodStartInputView() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodFinishInputView() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodFinishInput() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInlineSuggestionsSessionInvalidated() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
