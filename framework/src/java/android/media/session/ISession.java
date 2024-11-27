package android.media.session;

/* loaded from: classes2.dex */
public interface ISession extends android.os.IInterface {
    void destroySession() throws android.os.RemoteException;

    android.os.IBinder getBinderForSetQueue() throws android.os.RemoteException;

    android.media.session.ISessionController getController() throws android.os.RemoteException;

    void resetQueue() throws android.os.RemoteException;

    void sendEvent(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void setActive(boolean z) throws android.os.RemoteException;

    void setCurrentVolume(int i) throws android.os.RemoteException;

    void setExtras(android.os.Bundle bundle) throws android.os.RemoteException;

    void setFlags(int i) throws android.os.RemoteException;

    void setLaunchPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void setMediaButtonBroadcastReceiver(android.content.ComponentName componentName) throws android.os.RemoteException;

    void setMediaButtonReceiver(android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void setMetadata(android.media.MediaMetadata mediaMetadata, long j, java.lang.String str) throws android.os.RemoteException;

    void setPlaybackState(android.media.session.PlaybackState playbackState) throws android.os.RemoteException;

    void setPlaybackToLocal(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException;

    void setPlaybackToRemote(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    void setQueueTitle(java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void setRatingType(int i) throws android.os.RemoteException;

    public static class Default implements android.media.session.ISession {
        @Override // android.media.session.ISession
        public void sendEvent(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public android.media.session.ISessionController getController() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISession
        public void setFlags(int i) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void setActive(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void setMediaButtonReceiver(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void setMediaButtonBroadcastReceiver(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void setLaunchPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void destroySession() throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void setMetadata(android.media.MediaMetadata mediaMetadata, long j, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void setPlaybackState(android.media.session.PlaybackState playbackState) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void resetQueue() throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public android.os.IBinder getBinderForSetQueue() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISession
        public void setQueueTitle(java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void setExtras(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void setRatingType(int i) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void setPlaybackToLocal(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void setPlaybackToRemote(int i, int i2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISession
        public void setCurrentVolume(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.session.ISession {
        public static final java.lang.String DESCRIPTOR = "android.media.session.ISession";
        static final int TRANSACTION_destroySession = 8;
        static final int TRANSACTION_getBinderForSetQueue = 12;
        static final int TRANSACTION_getController = 2;
        static final int TRANSACTION_resetQueue = 11;
        static final int TRANSACTION_sendEvent = 1;
        static final int TRANSACTION_setActive = 4;
        static final int TRANSACTION_setCurrentVolume = 18;
        static final int TRANSACTION_setExtras = 14;
        static final int TRANSACTION_setFlags = 3;
        static final int TRANSACTION_setLaunchPendingIntent = 7;
        static final int TRANSACTION_setMediaButtonBroadcastReceiver = 6;
        static final int TRANSACTION_setMediaButtonReceiver = 5;
        static final int TRANSACTION_setMetadata = 9;
        static final int TRANSACTION_setPlaybackState = 10;
        static final int TRANSACTION_setPlaybackToLocal = 16;
        static final int TRANSACTION_setPlaybackToRemote = 17;
        static final int TRANSACTION_setQueueTitle = 13;
        static final int TRANSACTION_setRatingType = 15;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.session.ISession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.session.ISession)) {
                return (android.media.session.ISession) queryLocalInterface;
            }
            return new android.media.session.ISession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "sendEvent";
                case 2:
                    return "getController";
                case 3:
                    return "setFlags";
                case 4:
                    return "setActive";
                case 5:
                    return "setMediaButtonReceiver";
                case 6:
                    return "setMediaButtonBroadcastReceiver";
                case 7:
                    return "setLaunchPendingIntent";
                case 8:
                    return "destroySession";
                case 9:
                    return "setMetadata";
                case 10:
                    return "setPlaybackState";
                case 11:
                    return "resetQueue";
                case 12:
                    return "getBinderForSetQueue";
                case 13:
                    return "setQueueTitle";
                case 14:
                    return "setExtras";
                case 15:
                    return "setRatingType";
                case 16:
                    return "setPlaybackToLocal";
                case 17:
                    return "setPlaybackToRemote";
                case 18:
                    return "setCurrentVolume";
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
                    java.lang.String readString = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendEvent(readString, bundle);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.media.session.ISessionController controller = getController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(controller);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFlags(readInt);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActive(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    setMediaButtonReceiver(pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setMediaButtonBroadcastReceiver(componentName);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.app.PendingIntent pendingIntent2 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLaunchPendingIntent(pendingIntent2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    destroySession();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.media.MediaMetadata mediaMetadata = (android.media.MediaMetadata) parcel.readTypedObject(android.media.MediaMetadata.CREATOR);
                    long readLong = parcel.readLong();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMetadata(mediaMetadata, readLong, readString2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.media.session.PlaybackState playbackState = (android.media.session.PlaybackState) parcel.readTypedObject(android.media.session.PlaybackState.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPlaybackState(playbackState);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    resetQueue();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.os.IBinder binderForSetQueue = getBinderForSetQueue();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(binderForSetQueue);
                    return true;
                case 13:
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setQueueTitle(charSequence);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setExtras(bundle2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRatingType(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.media.AudioAttributes audioAttributes = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPlaybackToLocal(audioAttributes);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPlaybackToRemote(readInt3, readInt4, readString3);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentVolume(readInt5);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.session.ISession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.session.ISession.Stub.DESCRIPTOR;
            }

            @Override // android.media.session.ISession
            public void sendEvent(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public android.media.session.ISessionController getController() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.media.session.ISessionController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setFlags(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setActive(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setMediaButtonReceiver(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setMediaButtonBroadcastReceiver(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setLaunchPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void destroySession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setMetadata(android.media.MediaMetadata mediaMetadata, long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(mediaMetadata, 0);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setPlaybackState(android.media.session.PlaybackState playbackState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(playbackState, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void resetQueue() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public android.os.IBinder getBinderForSetQueue() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setQueueTitle(java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setExtras(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setRatingType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setPlaybackToLocal(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setPlaybackToRemote(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setCurrentVolume(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
