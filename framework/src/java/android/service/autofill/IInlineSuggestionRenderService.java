package android.service.autofill;

/* loaded from: classes3.dex */
public interface IInlineSuggestionRenderService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.autofill.IInlineSuggestionRenderService";

    void destroySuggestionViews(int i, int i2) throws android.os.RemoteException;

    void getInlineSuggestionsRendererInfo(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void renderSuggestion(android.service.autofill.IInlineSuggestionUiCallback iInlineSuggestionUiCallback, android.service.autofill.InlinePresentation inlinePresentation, int i, int i2, android.os.IBinder iBinder, int i3, int i4, int i5) throws android.os.RemoteException;

    public static class Default implements android.service.autofill.IInlineSuggestionRenderService {
        @Override // android.service.autofill.IInlineSuggestionRenderService
        public void renderSuggestion(android.service.autofill.IInlineSuggestionUiCallback iInlineSuggestionUiCallback, android.service.autofill.InlinePresentation inlinePresentation, int i, int i2, android.os.IBinder iBinder, int i3, int i4, int i5) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionRenderService
        public void getInlineSuggestionsRendererInfo(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionRenderService
        public void destroySuggestionViews(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.autofill.IInlineSuggestionRenderService {
        static final int TRANSACTION_destroySuggestionViews = 3;
        static final int TRANSACTION_getInlineSuggestionsRendererInfo = 2;
        static final int TRANSACTION_renderSuggestion = 1;

        public Stub() {
            attachInterface(this, android.service.autofill.IInlineSuggestionRenderService.DESCRIPTOR);
        }

        public static android.service.autofill.IInlineSuggestionRenderService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.autofill.IInlineSuggestionRenderService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.autofill.IInlineSuggestionRenderService)) {
                return (android.service.autofill.IInlineSuggestionRenderService) queryLocalInterface;
            }
            return new android.service.autofill.IInlineSuggestionRenderService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "renderSuggestion";
                case 2:
                    return "getInlineSuggestionsRendererInfo";
                case 3:
                    return "destroySuggestionViews";
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
                parcel.enforceInterface(android.service.autofill.IInlineSuggestionRenderService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.autofill.IInlineSuggestionRenderService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.autofill.IInlineSuggestionUiCallback asInterface = android.service.autofill.IInlineSuggestionUiCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.service.autofill.InlinePresentation inlinePresentation = (android.service.autofill.InlinePresentation) parcel.readTypedObject(android.service.autofill.InlinePresentation.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    renderSuggestion(asInterface, inlinePresentation, readInt, readInt2, readStrongBinder, readInt3, readInt4, readInt5);
                    return true;
                case 2:
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getInlineSuggestionsRendererInfo(remoteCallback);
                    return true;
                case 3:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroySuggestionViews(readInt6, readInt7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.autofill.IInlineSuggestionRenderService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.autofill.IInlineSuggestionRenderService.DESCRIPTOR;
            }

            @Override // android.service.autofill.IInlineSuggestionRenderService
            public void renderSuggestion(android.service.autofill.IInlineSuggestionUiCallback iInlineSuggestionUiCallback, android.service.autofill.InlinePresentation inlinePresentation, int i, int i2, android.os.IBinder iBinder, int i3, int i4, int i5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IInlineSuggestionRenderService.DESCRIPTOR);
                    obtain.writeStrongInterface(iInlineSuggestionUiCallback);
                    obtain.writeTypedObject(inlinePresentation, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionRenderService
            public void getInlineSuggestionsRendererInfo(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IInlineSuggestionRenderService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionRenderService
            public void destroySuggestionViews(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IInlineSuggestionRenderService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
