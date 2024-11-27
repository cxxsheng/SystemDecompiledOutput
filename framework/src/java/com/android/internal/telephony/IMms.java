package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface IMms extends android.os.IInterface {
    android.net.Uri addMultimediaMessageDraft(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException;

    android.net.Uri addTextMessageDraft(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    boolean archiveStoredConversation(java.lang.String str, long j, boolean z) throws android.os.RemoteException;

    boolean deleteStoredConversation(java.lang.String str, long j) throws android.os.RemoteException;

    boolean deleteStoredMessage(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException;

    void downloadMessage(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, android.os.Bundle bundle, android.app.PendingIntent pendingIntent, long j, java.lang.String str3) throws android.os.RemoteException;

    boolean getAutoPersisting() throws android.os.RemoteException;

    android.net.Uri importMultimediaMessage(java.lang.String str, android.net.Uri uri, java.lang.String str2, long j, boolean z, boolean z2) throws android.os.RemoteException;

    android.net.Uri importTextMessage(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, long j, boolean z, boolean z2) throws android.os.RemoteException;

    void sendMessage(int i, java.lang.String str, android.net.Uri uri, java.lang.String str2, android.os.Bundle bundle, android.app.PendingIntent pendingIntent, long j, java.lang.String str3) throws android.os.RemoteException;

    void sendStoredMessage(int i, java.lang.String str, android.net.Uri uri, android.os.Bundle bundle, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void setAutoPersisting(java.lang.String str, boolean z) throws android.os.RemoteException;

    boolean updateStoredMessageStatus(java.lang.String str, android.net.Uri uri, android.content.ContentValues contentValues) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.IMms {
        @Override // com.android.internal.telephony.IMms
        public void sendMessage(int i, java.lang.String str, android.net.Uri uri, java.lang.String str2, android.os.Bundle bundle, android.app.PendingIntent pendingIntent, long j, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IMms
        public void downloadMessage(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, android.os.Bundle bundle, android.app.PendingIntent pendingIntent, long j, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IMms
        public android.net.Uri importTextMessage(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, long j, boolean z, boolean z2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IMms
        public android.net.Uri importMultimediaMessage(java.lang.String str, android.net.Uri uri, java.lang.String str2, long j, boolean z, boolean z2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IMms
        public boolean deleteStoredMessage(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IMms
        public boolean deleteStoredConversation(java.lang.String str, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IMms
        public boolean updateStoredMessageStatus(java.lang.String str, android.net.Uri uri, android.content.ContentValues contentValues) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IMms
        public boolean archiveStoredConversation(java.lang.String str, long j, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IMms
        public android.net.Uri addTextMessageDraft(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IMms
        public android.net.Uri addMultimediaMessageDraft(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IMms
        public void sendStoredMessage(int i, java.lang.String str, android.net.Uri uri, android.os.Bundle bundle, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IMms
        public void setAutoPersisting(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IMms
        public boolean getAutoPersisting() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.IMms {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.IMms";
        static final int TRANSACTION_addMultimediaMessageDraft = 10;
        static final int TRANSACTION_addTextMessageDraft = 9;
        static final int TRANSACTION_archiveStoredConversation = 8;
        static final int TRANSACTION_deleteStoredConversation = 6;
        static final int TRANSACTION_deleteStoredMessage = 5;
        static final int TRANSACTION_downloadMessage = 2;
        static final int TRANSACTION_getAutoPersisting = 13;
        static final int TRANSACTION_importMultimediaMessage = 4;
        static final int TRANSACTION_importTextMessage = 3;
        static final int TRANSACTION_sendMessage = 1;
        static final int TRANSACTION_sendStoredMessage = 11;
        static final int TRANSACTION_setAutoPersisting = 12;
        static final int TRANSACTION_updateStoredMessageStatus = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telephony.IMms asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.IMms)) {
                return (com.android.internal.telephony.IMms) queryLocalInterface;
            }
            return new com.android.internal.telephony.IMms.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "sendMessage";
                case 2:
                    return "downloadMessage";
                case 3:
                    return "importTextMessage";
                case 4:
                    return "importMultimediaMessage";
                case 5:
                    return "deleteStoredMessage";
                case 6:
                    return "deleteStoredConversation";
                case 7:
                    return "updateStoredMessageStatus";
                case 8:
                    return "archiveStoredConversation";
                case 9:
                    return "addTextMessageDraft";
                case 10:
                    return "addMultimediaMessageDraft";
                case 11:
                    return "sendStoredMessage";
                case 12:
                    return "setAutoPersisting";
                case 13:
                    return "getAutoPersisting";
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
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    long readLong = parcel.readLong();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendMessage(readInt, readString, uri, readString2, bundle, pendingIntent, readLong, readString3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.app.PendingIntent pendingIntent2 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    long readLong2 = parcel.readLong();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    downloadMessage(readInt2, readString4, readString5, uri2, bundle2, pendingIntent2, readLong2, readString6);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    long readLong3 = parcel.readLong();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.net.Uri importTextMessage = importTextMessage(readString7, readString8, readInt3, readString9, readLong3, readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importTextMessage, 1);
                    return true;
                case 4:
                    java.lang.String readString10 = parcel.readString();
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    java.lang.String readString11 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.net.Uri importMultimediaMessage = importMultimediaMessage(readString10, uri3, readString11, readLong4, readBoolean3, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importMultimediaMessage, 1);
                    return true;
                case 5:
                    java.lang.String readString12 = parcel.readString();
                    android.net.Uri uri4 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean deleteStoredMessage = deleteStoredMessage(readString12, uri4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteStoredMessage);
                    return true;
                case 6:
                    java.lang.String readString13 = parcel.readString();
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean deleteStoredConversation = deleteStoredConversation(readString13, readLong5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteStoredConversation);
                    return true;
                case 7:
                    java.lang.String readString14 = parcel.readString();
                    android.net.Uri uri5 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.content.ContentValues contentValues = (android.content.ContentValues) parcel.readTypedObject(android.content.ContentValues.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateStoredMessageStatus = updateStoredMessageStatus(readString14, uri5, contentValues);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateStoredMessageStatus);
                    return true;
                case 8:
                    java.lang.String readString15 = parcel.readString();
                    long readLong6 = parcel.readLong();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean archiveStoredConversation = archiveStoredConversation(readString15, readLong6, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(archiveStoredConversation);
                    return true;
                case 9:
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.net.Uri addTextMessageDraft = addTextMessageDraft(readString16, readString17, readString18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addTextMessageDraft, 1);
                    return true;
                case 10:
                    java.lang.String readString19 = parcel.readString();
                    android.net.Uri uri6 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.net.Uri addMultimediaMessageDraft = addMultimediaMessageDraft(readString19, uri6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addMultimediaMessageDraft, 1);
                    return true;
                case 11:
                    int readInt4 = parcel.readInt();
                    java.lang.String readString20 = parcel.readString();
                    android.net.Uri uri7 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.app.PendingIntent pendingIntent3 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendStoredMessage(readInt4, readString20, uri7, bundle3, pendingIntent3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    java.lang.String readString21 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoPersisting(readString21, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean autoPersisting = getAutoPersisting();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoPersisting);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.IMms {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.IMms.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IMms
            public void sendMessage(int i, java.lang.String str, android.net.Uri uri, java.lang.String str2, android.os.Bundle bundle, android.app.PendingIntent pendingIntent, long j, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeLong(j);
                    obtain.writeString(str3);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public void downloadMessage(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, android.os.Bundle bundle, android.app.PendingIntent pendingIntent, long j, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeLong(j);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public android.net.Uri importTextMessage(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, long j, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.Uri) obtain2.readTypedObject(android.net.Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public android.net.Uri importMultimediaMessage(java.lang.String str, android.net.Uri uri, java.lang.String str2, long j, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.Uri) obtain2.readTypedObject(android.net.Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean deleteStoredMessage(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean deleteStoredConversation(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean updateStoredMessageStatus(java.lang.String str, android.net.Uri uri, android.content.ContentValues contentValues) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(contentValues, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean archiveStoredConversation(java.lang.String str, long j, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public android.net.Uri addTextMessageDraft(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.Uri) obtain2.readTypedObject(android.net.Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public android.net.Uri addMultimediaMessageDraft(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.Uri) obtain2.readTypedObject(android.net.Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public void sendStoredMessage(int i, java.lang.String str, android.net.Uri uri, android.os.Bundle bundle, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public void setAutoPersisting(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean getAutoPersisting() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IMms.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
