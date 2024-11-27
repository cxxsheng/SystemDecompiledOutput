package android.service.settings.suggestions;

/* loaded from: classes3.dex */
public interface ISuggestionService extends android.os.IInterface {
    void dismissSuggestion(android.service.settings.suggestions.Suggestion suggestion) throws android.os.RemoteException;

    java.util.List<android.service.settings.suggestions.Suggestion> getSuggestions() throws android.os.RemoteException;

    void launchSuggestion(android.service.settings.suggestions.Suggestion suggestion) throws android.os.RemoteException;

    public static class Default implements android.service.settings.suggestions.ISuggestionService {
        @Override // android.service.settings.suggestions.ISuggestionService
        public java.util.List<android.service.settings.suggestions.Suggestion> getSuggestions() throws android.os.RemoteException {
            return null;
        }

        @Override // android.service.settings.suggestions.ISuggestionService
        public void dismissSuggestion(android.service.settings.suggestions.Suggestion suggestion) throws android.os.RemoteException {
        }

        @Override // android.service.settings.suggestions.ISuggestionService
        public void launchSuggestion(android.service.settings.suggestions.Suggestion suggestion) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.settings.suggestions.ISuggestionService {
        public static final java.lang.String DESCRIPTOR = "android.service.settings.suggestions.ISuggestionService";
        static final int TRANSACTION_dismissSuggestion = 3;
        static final int TRANSACTION_getSuggestions = 2;
        static final int TRANSACTION_launchSuggestion = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.settings.suggestions.ISuggestionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.settings.suggestions.ISuggestionService)) {
                return (android.service.settings.suggestions.ISuggestionService) queryLocalInterface;
            }
            return new android.service.settings.suggestions.ISuggestionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 2:
                    return "getSuggestions";
                case 3:
                    return "dismissSuggestion";
                case 4:
                    return "launchSuggestion";
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
                case 2:
                    java.util.List<android.service.settings.suggestions.Suggestion> suggestions = getSuggestions();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(suggestions, 1);
                    return true;
                case 3:
                    android.service.settings.suggestions.Suggestion suggestion = (android.service.settings.suggestions.Suggestion) parcel.readTypedObject(android.service.settings.suggestions.Suggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    dismissSuggestion(suggestion);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.service.settings.suggestions.Suggestion suggestion2 = (android.service.settings.suggestions.Suggestion) parcel.readTypedObject(android.service.settings.suggestions.Suggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    launchSuggestion(suggestion2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.settings.suggestions.ISuggestionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.settings.suggestions.ISuggestionService.Stub.DESCRIPTOR;
            }

            @Override // android.service.settings.suggestions.ISuggestionService
            public java.util.List<android.service.settings.suggestions.Suggestion> getSuggestions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.settings.suggestions.ISuggestionService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.service.settings.suggestions.Suggestion.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.settings.suggestions.ISuggestionService
            public void dismissSuggestion(android.service.settings.suggestions.Suggestion suggestion) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.settings.suggestions.ISuggestionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(suggestion, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.settings.suggestions.ISuggestionService
            public void launchSuggestion(android.service.settings.suggestions.Suggestion suggestion) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.settings.suggestions.ISuggestionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(suggestion, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
