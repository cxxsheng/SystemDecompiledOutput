package android.app.people;

/* loaded from: classes.dex */
public interface IConversationListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.people.IConversationListener";

    void onConversationUpdate(android.app.people.ConversationChannel conversationChannel) throws android.os.RemoteException;

    public static class Default implements android.app.people.IConversationListener {
        @Override // android.app.people.IConversationListener
        public void onConversationUpdate(android.app.people.ConversationChannel conversationChannel) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.people.IConversationListener {
        static final int TRANSACTION_onConversationUpdate = 1;

        public Stub() {
            attachInterface(this, android.app.people.IConversationListener.DESCRIPTOR);
        }

        public static android.app.people.IConversationListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.people.IConversationListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.people.IConversationListener)) {
                return (android.app.people.IConversationListener) queryLocalInterface;
            }
            return new android.app.people.IConversationListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConversationUpdate";
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
                parcel.enforceInterface(android.app.people.IConversationListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.people.IConversationListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.people.ConversationChannel conversationChannel = (android.app.people.ConversationChannel) parcel.readTypedObject(android.app.people.ConversationChannel.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConversationUpdate(conversationChannel);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.people.IConversationListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.people.IConversationListener.DESCRIPTOR;
            }

            @Override // android.app.people.IConversationListener
            public void onConversationUpdate(android.app.people.ConversationChannel conversationChannel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.people.IConversationListener.DESCRIPTOR);
                    obtain.writeTypedObject(conversationChannel, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
