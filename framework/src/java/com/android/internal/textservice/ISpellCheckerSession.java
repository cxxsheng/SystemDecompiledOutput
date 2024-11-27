package com.android.internal.textservice;

/* loaded from: classes5.dex */
public interface ISpellCheckerSession extends android.os.IInterface {
    void onCancel() throws android.os.RemoteException;

    void onClose() throws android.os.RemoteException;

    void onGetSentenceSuggestionsMultiple(android.view.textservice.TextInfo[] textInfoArr, int i) throws android.os.RemoteException;

    void onGetSuggestionsMultiple(android.view.textservice.TextInfo[] textInfoArr, int i, boolean z) throws android.os.RemoteException;

    public static class Default implements com.android.internal.textservice.ISpellCheckerSession {
        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onGetSuggestionsMultiple(android.view.textservice.TextInfo[] textInfoArr, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onGetSentenceSuggestionsMultiple(android.view.textservice.TextInfo[] textInfoArr, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onCancel() throws android.os.RemoteException {
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onClose() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.textservice.ISpellCheckerSession {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.textservice.ISpellCheckerSession";
        static final int TRANSACTION_onCancel = 3;
        static final int TRANSACTION_onClose = 4;
        static final int TRANSACTION_onGetSentenceSuggestionsMultiple = 2;
        static final int TRANSACTION_onGetSuggestionsMultiple = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.textservice.ISpellCheckerSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.textservice.ISpellCheckerSession)) {
                return (com.android.internal.textservice.ISpellCheckerSession) queryLocalInterface;
            }
            return new com.android.internal.textservice.ISpellCheckerSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onGetSuggestionsMultiple";
                case 2:
                    return "onGetSentenceSuggestionsMultiple";
                case 3:
                    return "onCancel";
                case 4:
                    return "onClose";
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
                    android.view.textservice.TextInfo[] textInfoArr = (android.view.textservice.TextInfo[]) parcel.createTypedArray(android.view.textservice.TextInfo.CREATOR);
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onGetSuggestionsMultiple(textInfoArr, readInt, readBoolean);
                    return true;
                case 2:
                    android.view.textservice.TextInfo[] textInfoArr2 = (android.view.textservice.TextInfo[]) parcel.createTypedArray(android.view.textservice.TextInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onGetSentenceSuggestionsMultiple(textInfoArr2, readInt2);
                    return true;
                case 3:
                    onCancel();
                    return true;
                case 4:
                    onClose();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.textservice.ISpellCheckerSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.textservice.ISpellCheckerSession.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.textservice.ISpellCheckerSession
            public void onGetSuggestionsMultiple(android.view.textservice.TextInfo[] textInfoArr, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ISpellCheckerSession.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(textInfoArr, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ISpellCheckerSession
            public void onGetSentenceSuggestionsMultiple(android.view.textservice.TextInfo[] textInfoArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ISpellCheckerSession.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(textInfoArr, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ISpellCheckerSession
            public void onCancel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ISpellCheckerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ISpellCheckerSession
            public void onClose() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ISpellCheckerSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
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
