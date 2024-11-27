package com.android.internal.textservice;

/* loaded from: classes5.dex */
public interface ISpellCheckerSessionListener extends android.os.IInterface {
    void onGetSentenceSuggestions(android.view.textservice.SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) throws android.os.RemoteException;

    void onGetSuggestions(android.view.textservice.SuggestionsInfo[] suggestionsInfoArr) throws android.os.RemoteException;

    public static class Default implements com.android.internal.textservice.ISpellCheckerSessionListener {
        @Override // com.android.internal.textservice.ISpellCheckerSessionListener
        public void onGetSuggestions(android.view.textservice.SuggestionsInfo[] suggestionsInfoArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.textservice.ISpellCheckerSessionListener
        public void onGetSentenceSuggestions(android.view.textservice.SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.textservice.ISpellCheckerSessionListener {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.textservice.ISpellCheckerSessionListener";
        static final int TRANSACTION_onGetSentenceSuggestions = 2;
        static final int TRANSACTION_onGetSuggestions = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.textservice.ISpellCheckerSessionListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.textservice.ISpellCheckerSessionListener)) {
                return (com.android.internal.textservice.ISpellCheckerSessionListener) queryLocalInterface;
            }
            return new com.android.internal.textservice.ISpellCheckerSessionListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onGetSuggestions";
                case 2:
                    return "onGetSentenceSuggestions";
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
                    android.view.textservice.SuggestionsInfo[] suggestionsInfoArr = (android.view.textservice.SuggestionsInfo[]) parcel.createTypedArray(android.view.textservice.SuggestionsInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGetSuggestions(suggestionsInfoArr);
                    return true;
                case 2:
                    android.view.textservice.SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr = (android.view.textservice.SentenceSuggestionsInfo[]) parcel.createTypedArray(android.view.textservice.SentenceSuggestionsInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGetSentenceSuggestions(sentenceSuggestionsInfoArr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.textservice.ISpellCheckerSessionListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.textservice.ISpellCheckerSessionListener.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.textservice.ISpellCheckerSessionListener
            public void onGetSuggestions(android.view.textservice.SuggestionsInfo[] suggestionsInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ISpellCheckerSessionListener.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(suggestionsInfoArr, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ISpellCheckerSessionListener
            public void onGetSentenceSuggestions(android.view.textservice.SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ISpellCheckerSessionListener.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(sentenceSuggestionsInfoArr, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
