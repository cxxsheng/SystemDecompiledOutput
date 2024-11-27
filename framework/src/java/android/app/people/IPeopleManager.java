package android.app.people;

/* loaded from: classes.dex */
public interface IPeopleManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.people.IPeopleManager";

    void addOrUpdateStatus(java.lang.String str, int i, java.lang.String str2, android.app.people.ConversationStatus conversationStatus) throws android.os.RemoteException;

    void clearStatus(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void clearStatuses(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    android.app.people.ConversationChannel getConversation(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    long getLastInteraction(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getRecentConversations() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getStatuses(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    boolean isConversation(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    void registerConversationListener(java.lang.String str, int i, java.lang.String str2, android.app.people.IConversationListener iConversationListener) throws android.os.RemoteException;

    void removeAllRecentConversations() throws android.os.RemoteException;

    void removeRecentConversation(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    void unregisterConversationListener(android.app.people.IConversationListener iConversationListener) throws android.os.RemoteException;

    public static class Default implements android.app.people.IPeopleManager {
        @Override // android.app.people.IPeopleManager
        public android.app.people.ConversationChannel getConversation(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.people.IPeopleManager
        public android.content.pm.ParceledListSlice getRecentConversations() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.people.IPeopleManager
        public void removeRecentConversation(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.people.IPeopleManager
        public void removeAllRecentConversations() throws android.os.RemoteException {
        }

        @Override // android.app.people.IPeopleManager
        public boolean isConversation(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.people.IPeopleManager
        public long getLastInteraction(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.people.IPeopleManager
        public void addOrUpdateStatus(java.lang.String str, int i, java.lang.String str2, android.app.people.ConversationStatus conversationStatus) throws android.os.RemoteException {
        }

        @Override // android.app.people.IPeopleManager
        public void clearStatus(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.app.people.IPeopleManager
        public void clearStatuses(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.people.IPeopleManager
        public android.content.pm.ParceledListSlice getStatuses(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.people.IPeopleManager
        public void registerConversationListener(java.lang.String str, int i, java.lang.String str2, android.app.people.IConversationListener iConversationListener) throws android.os.RemoteException {
        }

        @Override // android.app.people.IPeopleManager
        public void unregisterConversationListener(android.app.people.IConversationListener iConversationListener) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.people.IPeopleManager {
        static final int TRANSACTION_addOrUpdateStatus = 7;
        static final int TRANSACTION_clearStatus = 8;
        static final int TRANSACTION_clearStatuses = 9;
        static final int TRANSACTION_getConversation = 1;
        static final int TRANSACTION_getLastInteraction = 6;
        static final int TRANSACTION_getRecentConversations = 2;
        static final int TRANSACTION_getStatuses = 10;
        static final int TRANSACTION_isConversation = 5;
        static final int TRANSACTION_registerConversationListener = 11;
        static final int TRANSACTION_removeAllRecentConversations = 4;
        static final int TRANSACTION_removeRecentConversation = 3;
        static final int TRANSACTION_unregisterConversationListener = 12;

        public Stub() {
            attachInterface(this, android.app.people.IPeopleManager.DESCRIPTOR);
        }

        public static android.app.people.IPeopleManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.people.IPeopleManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.people.IPeopleManager)) {
                return (android.app.people.IPeopleManager) queryLocalInterface;
            }
            return new android.app.people.IPeopleManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getConversation";
                case 2:
                    return "getRecentConversations";
                case 3:
                    return "removeRecentConversation";
                case 4:
                    return "removeAllRecentConversations";
                case 5:
                    return "isConversation";
                case 6:
                    return "getLastInteraction";
                case 7:
                    return "addOrUpdateStatus";
                case 8:
                    return "clearStatus";
                case 9:
                    return "clearStatuses";
                case 10:
                    return "getStatuses";
                case 11:
                    return "registerConversationListener";
                case 12:
                    return "unregisterConversationListener";
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
                parcel.enforceInterface(android.app.people.IPeopleManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.people.IPeopleManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.people.ConversationChannel conversation = getConversation(readString, readInt, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(conversation, 1);
                    return true;
                case 2:
                    android.content.pm.ParceledListSlice recentConversations = getRecentConversations();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentConversations, 1);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeRecentConversation(readString3, readInt2, readString4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    removeAllRecentConversations();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isConversation = isConversation(readString5, readInt3, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isConversation);
                    return true;
                case 6:
                    java.lang.String readString7 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long lastInteraction = getLastInteraction(readString7, readInt4, readString8);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastInteraction);
                    return true;
                case 7:
                    java.lang.String readString9 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    java.lang.String readString10 = parcel.readString();
                    android.app.people.ConversationStatus conversationStatus = (android.app.people.ConversationStatus) parcel.readTypedObject(android.app.people.ConversationStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    addOrUpdateStatus(readString9, readInt5, readString10, conversationStatus);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    java.lang.String readString11 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearStatus(readString11, readInt6, readString12, readString13);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString14 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearStatuses(readString14, readInt7, readString15);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString16 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice statuses = getStatuses(readString16, readInt8, readString17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(statuses, 1);
                    return true;
                case 11:
                    java.lang.String readString18 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    java.lang.String readString19 = parcel.readString();
                    android.app.people.IConversationListener asInterface = android.app.people.IConversationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerConversationListener(readString18, readInt9, readString19, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.app.people.IConversationListener asInterface2 = android.app.people.IConversationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterConversationListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.people.IPeopleManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.people.IPeopleManager.DESCRIPTOR;
            }

            @Override // android.app.people.IPeopleManager
            public android.app.people.ConversationChannel getConversation(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.people.IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.people.ConversationChannel) obtain2.readTypedObject(android.app.people.ConversationChannel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public android.content.pm.ParceledListSlice getRecentConversations() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.people.IPeopleManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void removeRecentConversation(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.people.IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void removeAllRecentConversations() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.people.IPeopleManager.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public boolean isConversation(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.people.IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public long getLastInteraction(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.people.IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void addOrUpdateStatus(java.lang.String str, int i, java.lang.String str2, android.app.people.ConversationStatus conversationStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.people.IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(conversationStatus, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void clearStatus(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.people.IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void clearStatuses(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.people.IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public android.content.pm.ParceledListSlice getStatuses(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.people.IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void registerConversationListener(java.lang.String str, int i, java.lang.String str2, android.app.people.IConversationListener iConversationListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.people.IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iConversationListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void unregisterConversationListener(android.app.people.IConversationListener iConversationListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.people.IPeopleManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iConversationListener);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
