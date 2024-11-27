package android.service.textclassifier;

/* loaded from: classes3.dex */
public interface ITextClassifierService extends android.os.IInterface {
    void onClassifyText(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextClassification.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException;

    void onConnectedStateChanged(int i) throws android.os.RemoteException;

    void onCreateTextClassificationSession(android.view.textclassifier.TextClassificationContext textClassificationContext, android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) throws android.os.RemoteException;

    void onDestroyTextClassificationSession(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) throws android.os.RemoteException;

    void onDetectLanguage(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextLanguage.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException;

    void onGenerateLinks(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextLinks.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException;

    void onSelectionEvent(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.SelectionEvent selectionEvent) throws android.os.RemoteException;

    void onSuggestConversationActions(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.ConversationActions.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException;

    void onSuggestSelection(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextSelection.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException;

    void onTextClassifierEvent(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextClassifierEvent textClassifierEvent) throws android.os.RemoteException;

    public static class Default implements android.service.textclassifier.ITextClassifierService {
        @Override // android.service.textclassifier.ITextClassifierService
        public void onSuggestSelection(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextSelection.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onClassifyText(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextClassification.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onGenerateLinks(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextLinks.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onSelectionEvent(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.SelectionEvent selectionEvent) throws android.os.RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onTextClassifierEvent(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextClassifierEvent textClassifierEvent) throws android.os.RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onCreateTextClassificationSession(android.view.textclassifier.TextClassificationContext textClassificationContext, android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) throws android.os.RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onDestroyTextClassificationSession(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) throws android.os.RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onDetectLanguage(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextLanguage.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onSuggestConversationActions(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.ConversationActions.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onConnectedStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.textclassifier.ITextClassifierService {
        public static final java.lang.String DESCRIPTOR = "android.service.textclassifier.ITextClassifierService";
        static final int TRANSACTION_onClassifyText = 2;
        static final int TRANSACTION_onConnectedStateChanged = 10;
        static final int TRANSACTION_onCreateTextClassificationSession = 6;
        static final int TRANSACTION_onDestroyTextClassificationSession = 7;
        static final int TRANSACTION_onDetectLanguage = 8;
        static final int TRANSACTION_onGenerateLinks = 3;
        static final int TRANSACTION_onSelectionEvent = 4;
        static final int TRANSACTION_onSuggestConversationActions = 9;
        static final int TRANSACTION_onSuggestSelection = 1;
        static final int TRANSACTION_onTextClassifierEvent = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.textclassifier.ITextClassifierService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.textclassifier.ITextClassifierService)) {
                return (android.service.textclassifier.ITextClassifierService) queryLocalInterface;
            }
            return new android.service.textclassifier.ITextClassifierService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSuggestSelection";
                case 2:
                    return "onClassifyText";
                case 3:
                    return "onGenerateLinks";
                case 4:
                    return "onSelectionEvent";
                case 5:
                    return "onTextClassifierEvent";
                case 6:
                    return "onCreateTextClassificationSession";
                case 7:
                    return "onDestroyTextClassificationSession";
                case 8:
                    return "onDetectLanguage";
                case 9:
                    return "onSuggestConversationActions";
                case 10:
                    return "onConnectedStateChanged";
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
                    android.view.textclassifier.TextClassificationSessionId textClassificationSessionId = (android.view.textclassifier.TextClassificationSessionId) parcel.readTypedObject(android.view.textclassifier.TextClassificationSessionId.CREATOR);
                    android.view.textclassifier.TextSelection.Request request = (android.view.textclassifier.TextSelection.Request) parcel.readTypedObject(android.view.textclassifier.TextSelection.Request.CREATOR);
                    android.service.textclassifier.ITextClassifierCallback asInterface = android.service.textclassifier.ITextClassifierCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSuggestSelection(textClassificationSessionId, request, asInterface);
                    return true;
                case 2:
                    android.view.textclassifier.TextClassificationSessionId textClassificationSessionId2 = (android.view.textclassifier.TextClassificationSessionId) parcel.readTypedObject(android.view.textclassifier.TextClassificationSessionId.CREATOR);
                    android.view.textclassifier.TextClassification.Request request2 = (android.view.textclassifier.TextClassification.Request) parcel.readTypedObject(android.view.textclassifier.TextClassification.Request.CREATOR);
                    android.service.textclassifier.ITextClassifierCallback asInterface2 = android.service.textclassifier.ITextClassifierCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onClassifyText(textClassificationSessionId2, request2, asInterface2);
                    return true;
                case 3:
                    android.view.textclassifier.TextClassificationSessionId textClassificationSessionId3 = (android.view.textclassifier.TextClassificationSessionId) parcel.readTypedObject(android.view.textclassifier.TextClassificationSessionId.CREATOR);
                    android.view.textclassifier.TextLinks.Request request3 = (android.view.textclassifier.TextLinks.Request) parcel.readTypedObject(android.view.textclassifier.TextLinks.Request.CREATOR);
                    android.service.textclassifier.ITextClassifierCallback asInterface3 = android.service.textclassifier.ITextClassifierCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onGenerateLinks(textClassificationSessionId3, request3, asInterface3);
                    return true;
                case 4:
                    android.view.textclassifier.TextClassificationSessionId textClassificationSessionId4 = (android.view.textclassifier.TextClassificationSessionId) parcel.readTypedObject(android.view.textclassifier.TextClassificationSessionId.CREATOR);
                    android.view.textclassifier.SelectionEvent selectionEvent = (android.view.textclassifier.SelectionEvent) parcel.readTypedObject(android.view.textclassifier.SelectionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSelectionEvent(textClassificationSessionId4, selectionEvent);
                    return true;
                case 5:
                    android.view.textclassifier.TextClassificationSessionId textClassificationSessionId5 = (android.view.textclassifier.TextClassificationSessionId) parcel.readTypedObject(android.view.textclassifier.TextClassificationSessionId.CREATOR);
                    android.view.textclassifier.TextClassifierEvent textClassifierEvent = (android.view.textclassifier.TextClassifierEvent) parcel.readTypedObject(android.view.textclassifier.TextClassifierEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTextClassifierEvent(textClassificationSessionId5, textClassifierEvent);
                    return true;
                case 6:
                    android.view.textclassifier.TextClassificationContext textClassificationContext = (android.view.textclassifier.TextClassificationContext) parcel.readTypedObject(android.view.textclassifier.TextClassificationContext.CREATOR);
                    android.view.textclassifier.TextClassificationSessionId textClassificationSessionId6 = (android.view.textclassifier.TextClassificationSessionId) parcel.readTypedObject(android.view.textclassifier.TextClassificationSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCreateTextClassificationSession(textClassificationContext, textClassificationSessionId6);
                    return true;
                case 7:
                    android.view.textclassifier.TextClassificationSessionId textClassificationSessionId7 = (android.view.textclassifier.TextClassificationSessionId) parcel.readTypedObject(android.view.textclassifier.TextClassificationSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDestroyTextClassificationSession(textClassificationSessionId7);
                    return true;
                case 8:
                    android.view.textclassifier.TextClassificationSessionId textClassificationSessionId8 = (android.view.textclassifier.TextClassificationSessionId) parcel.readTypedObject(android.view.textclassifier.TextClassificationSessionId.CREATOR);
                    android.view.textclassifier.TextLanguage.Request request4 = (android.view.textclassifier.TextLanguage.Request) parcel.readTypedObject(android.view.textclassifier.TextLanguage.Request.CREATOR);
                    android.service.textclassifier.ITextClassifierCallback asInterface4 = android.service.textclassifier.ITextClassifierCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onDetectLanguage(textClassificationSessionId8, request4, asInterface4);
                    return true;
                case 9:
                    android.view.textclassifier.TextClassificationSessionId textClassificationSessionId9 = (android.view.textclassifier.TextClassificationSessionId) parcel.readTypedObject(android.view.textclassifier.TextClassificationSessionId.CREATOR);
                    android.view.textclassifier.ConversationActions.Request request5 = (android.view.textclassifier.ConversationActions.Request) parcel.readTypedObject(android.view.textclassifier.ConversationActions.Request.CREATOR);
                    android.service.textclassifier.ITextClassifierCallback asInterface5 = android.service.textclassifier.ITextClassifierCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSuggestConversationActions(textClassificationSessionId9, request5, asInterface5);
                    return true;
                case 10:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onConnectedStateChanged(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.textclassifier.ITextClassifierService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.textclassifier.ITextClassifierService.Stub.DESCRIPTOR;
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onSuggestSelection(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextSelection.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.textclassifier.ITextClassifierService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(textClassificationSessionId, 0);
                    obtain.writeTypedObject(request, 0);
                    obtain.writeStrongInterface(iTextClassifierCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onClassifyText(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextClassification.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.textclassifier.ITextClassifierService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(textClassificationSessionId, 0);
                    obtain.writeTypedObject(request, 0);
                    obtain.writeStrongInterface(iTextClassifierCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onGenerateLinks(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextLinks.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.textclassifier.ITextClassifierService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(textClassificationSessionId, 0);
                    obtain.writeTypedObject(request, 0);
                    obtain.writeStrongInterface(iTextClassifierCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onSelectionEvent(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.SelectionEvent selectionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.textclassifier.ITextClassifierService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(textClassificationSessionId, 0);
                    obtain.writeTypedObject(selectionEvent, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onTextClassifierEvent(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextClassifierEvent textClassifierEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.textclassifier.ITextClassifierService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(textClassificationSessionId, 0);
                    obtain.writeTypedObject(textClassifierEvent, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onCreateTextClassificationSession(android.view.textclassifier.TextClassificationContext textClassificationContext, android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.textclassifier.ITextClassifierService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(textClassificationContext, 0);
                    obtain.writeTypedObject(textClassificationSessionId, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onDestroyTextClassificationSession(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.textclassifier.ITextClassifierService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(textClassificationSessionId, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onDetectLanguage(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextLanguage.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.textclassifier.ITextClassifierService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(textClassificationSessionId, 0);
                    obtain.writeTypedObject(request, 0);
                    obtain.writeStrongInterface(iTextClassifierCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onSuggestConversationActions(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.ConversationActions.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.textclassifier.ITextClassifierService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(textClassificationSessionId, 0);
                    obtain.writeTypedObject(request, 0);
                    obtain.writeStrongInterface(iTextClassifierCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onConnectedStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.textclassifier.ITextClassifierService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
