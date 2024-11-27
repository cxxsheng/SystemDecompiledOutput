package android.service.voice;

/* loaded from: classes3.dex */
public interface IVoiceInteractionSession extends android.os.IInterface {
    void closeSystemDialogs() throws android.os.RemoteException;

    void destroy() throws android.os.RemoteException;

    void handleAssist(int i, android.os.IBinder iBinder, android.os.Bundle bundle, android.app.assist.AssistStructure assistStructure, android.app.assist.AssistContent assistContent, int i2, int i3) throws android.os.RemoteException;

    void handleScreenshot(android.graphics.Bitmap bitmap) throws android.os.RemoteException;

    void hide() throws android.os.RemoteException;

    void notifyVisibleActivityInfoChanged(android.service.voice.VisibleActivityInfo visibleActivityInfo, int i) throws android.os.RemoteException;

    void onLockscreenShown() throws android.os.RemoteException;

    void show(android.os.Bundle bundle, int i, com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) throws android.os.RemoteException;

    void taskFinished(android.content.Intent intent, int i) throws android.os.RemoteException;

    void taskStarted(android.content.Intent intent, int i) throws android.os.RemoteException;

    public static class Default implements android.service.voice.IVoiceInteractionSession {
        @Override // android.service.voice.IVoiceInteractionSession
        public void show(android.os.Bundle bundle, int i, com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void hide() throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void handleAssist(int i, android.os.IBinder iBinder, android.os.Bundle bundle, android.app.assist.AssistStructure assistStructure, android.app.assist.AssistContent assistContent, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void handleScreenshot(android.graphics.Bitmap bitmap) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void taskStarted(android.content.Intent intent, int i) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void taskFinished(android.content.Intent intent, int i) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void closeSystemDialogs() throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void onLockscreenShown() throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void destroy() throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void notifyVisibleActivityInfoChanged(android.service.voice.VisibleActivityInfo visibleActivityInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.voice.IVoiceInteractionSession {
        public static final java.lang.String DESCRIPTOR = "android.service.voice.IVoiceInteractionSession";
        static final int TRANSACTION_closeSystemDialogs = 7;
        static final int TRANSACTION_destroy = 9;
        static final int TRANSACTION_handleAssist = 3;
        static final int TRANSACTION_handleScreenshot = 4;
        static final int TRANSACTION_hide = 2;
        static final int TRANSACTION_notifyVisibleActivityInfoChanged = 10;
        static final int TRANSACTION_onLockscreenShown = 8;
        static final int TRANSACTION_show = 1;
        static final int TRANSACTION_taskFinished = 6;
        static final int TRANSACTION_taskStarted = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.voice.IVoiceInteractionSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.voice.IVoiceInteractionSession)) {
                return (android.service.voice.IVoiceInteractionSession) queryLocalInterface;
            }
            return new android.service.voice.IVoiceInteractionSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.view.ThreadedRenderer.OVERDRAW_PROPERTY_SHOW;
                case 2:
                    return "hide";
                case 3:
                    return "handleAssist";
                case 4:
                    return "handleScreenshot";
                case 5:
                    return "taskStarted";
                case 6:
                    return "taskFinished";
                case 7:
                    return "closeSystemDialogs";
                case 8:
                    return "onLockscreenShown";
                case 9:
                    return "destroy";
                case 10:
                    return "notifyVisibleActivityInfoChanged";
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
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt = parcel.readInt();
                    com.android.internal.app.IVoiceInteractionSessionShowCallback asInterface = com.android.internal.app.IVoiceInteractionSessionShowCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    show(bundle, readInt, asInterface);
                    return true;
                case 2:
                    hide();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.app.assist.AssistStructure assistStructure = (android.app.assist.AssistStructure) parcel.readTypedObject(android.app.assist.AssistStructure.CREATOR);
                    android.app.assist.AssistContent assistContent = (android.app.assist.AssistContent) parcel.readTypedObject(android.app.assist.AssistContent.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    handleAssist(readInt2, readStrongBinder, bundle2, assistStructure, assistContent, readInt3, readInt4);
                    return true;
                case 4:
                    android.graphics.Bitmap bitmap = (android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleScreenshot(bitmap);
                    return true;
                case 5:
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    taskStarted(intent, readInt5);
                    return true;
                case 6:
                    android.content.Intent intent2 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    taskFinished(intent2, readInt6);
                    return true;
                case 7:
                    closeSystemDialogs();
                    return true;
                case 8:
                    onLockscreenShown();
                    return true;
                case 9:
                    destroy();
                    return true;
                case 10:
                    android.service.voice.VisibleActivityInfo visibleActivityInfo = (android.service.voice.VisibleActivityInfo) parcel.readTypedObject(android.service.voice.VisibleActivityInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVisibleActivityInfoChanged(visibleActivityInfo, readInt7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.voice.IVoiceInteractionSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.voice.IVoiceInteractionSession.Stub.DESCRIPTOR;
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void show(android.os.Bundle bundle, int i, com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVoiceInteractionSessionShowCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void hide() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void handleAssist(int i, android.os.IBinder iBinder, android.os.Bundle bundle, android.app.assist.AssistStructure assistStructure, android.app.assist.AssistContent assistContent, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(assistStructure, 0);
                    obtain.writeTypedObject(assistContent, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void handleScreenshot(android.graphics.Bitmap bitmap) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void taskStarted(android.content.Intent intent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void taskFinished(android.content.Intent intent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void closeSystemDialogs() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void onLockscreenShown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void destroy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void notifyVisibleActivityInfoChanged(android.service.voice.VisibleActivityInfo visibleActivityInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(visibleActivityInfo, 0);
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
