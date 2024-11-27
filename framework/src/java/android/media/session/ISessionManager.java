package android.media.session;

/* loaded from: classes2.dex */
public interface ISessionManager extends android.os.IInterface {
    void addOnMediaKeyEventDispatchedListener(android.media.session.IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws android.os.RemoteException;

    void addOnMediaKeyEventSessionChangedListener(android.media.session.IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, java.lang.String str) throws android.os.RemoteException;

    void addSession2TokensListener(android.media.session.ISession2TokensListener iSession2TokensListener, int i) throws android.os.RemoteException;

    void addSessionsListener(android.media.session.IActiveSessionsListener iActiveSessionsListener, android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    android.media.session.ISession createSession(java.lang.String str, android.media.session.ISessionCallback iSessionCallback, java.lang.String str2, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void dispatchAdjustVolume(java.lang.String str, java.lang.String str2, int i, int i2, int i3) throws android.os.RemoteException;

    void dispatchMediaKeyEvent(java.lang.String str, boolean z, android.view.KeyEvent keyEvent, boolean z2) throws android.os.RemoteException;

    boolean dispatchMediaKeyEventToSessionAsSystemService(java.lang.String str, android.view.KeyEvent keyEvent, android.media.session.MediaSession.Token token) throws android.os.RemoteException;

    void dispatchVolumeKeyEvent(java.lang.String str, java.lang.String str2, boolean z, android.view.KeyEvent keyEvent, int i, boolean z2) throws android.os.RemoteException;

    void dispatchVolumeKeyEventToSessionAsSystemService(java.lang.String str, java.lang.String str2, android.view.KeyEvent keyEvent, android.media.session.MediaSession.Token token) throws android.os.RemoteException;

    android.media.session.MediaSession.Token getMediaKeyEventSession(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getMediaKeyEventSessionPackageName(java.lang.String str) throws android.os.RemoteException;

    int getSessionPolicies(android.media.session.MediaSession.Token token) throws android.os.RemoteException;

    java.util.List<android.media.session.MediaSession.Token> getSessions(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    boolean hasCustomMediaKeyDispatcher(java.lang.String str) throws android.os.RemoteException;

    boolean hasCustomMediaSessionPolicyProvider(java.lang.String str) throws android.os.RemoteException;

    boolean isGlobalPriorityActive() throws android.os.RemoteException;

    boolean isTrusted(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void registerRemoteSessionCallback(android.media.IRemoteSessionCallback iRemoteSessionCallback) throws android.os.RemoteException;

    void removeOnMediaKeyEventDispatchedListener(android.media.session.IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws android.os.RemoteException;

    void removeOnMediaKeyEventSessionChangedListener(android.media.session.IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) throws android.os.RemoteException;

    void removeSession2TokensListener(android.media.session.ISession2TokensListener iSession2TokensListener) throws android.os.RemoteException;

    void removeSessionsListener(android.media.session.IActiveSessionsListener iActiveSessionsListener) throws android.os.RemoteException;

    void setCustomMediaKeyDispatcher(java.lang.String str) throws android.os.RemoteException;

    void setCustomMediaSessionPolicyProvider(java.lang.String str) throws android.os.RemoteException;

    void setOnMediaKeyListener(android.media.session.IOnMediaKeyListener iOnMediaKeyListener) throws android.os.RemoteException;

    void setOnVolumeKeyLongPressListener(android.media.session.IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) throws android.os.RemoteException;

    void setSessionPolicies(android.media.session.MediaSession.Token token, int i) throws android.os.RemoteException;

    void unregisterRemoteSessionCallback(android.media.IRemoteSessionCallback iRemoteSessionCallback) throws android.os.RemoteException;

    public static class Default implements android.media.session.ISessionManager {
        @Override // android.media.session.ISessionManager
        public android.media.session.ISession createSession(java.lang.String str, android.media.session.ISessionCallback iSessionCallback, java.lang.String str2, android.os.Bundle bundle, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionManager
        public java.util.List<android.media.session.MediaSession.Token> getSessions(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionManager
        public android.media.session.MediaSession.Token getMediaKeyEventSession(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionManager
        public java.lang.String getMediaKeyEventSessionPackageName(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionManager
        public void dispatchMediaKeyEvent(java.lang.String str, boolean z, android.view.KeyEvent keyEvent, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public boolean dispatchMediaKeyEventToSessionAsSystemService(java.lang.String str, android.view.KeyEvent keyEvent, android.media.session.MediaSession.Token token) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionManager
        public void dispatchVolumeKeyEvent(java.lang.String str, java.lang.String str2, boolean z, android.view.KeyEvent keyEvent, int i, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void dispatchVolumeKeyEventToSessionAsSystemService(java.lang.String str, java.lang.String str2, android.view.KeyEvent keyEvent, android.media.session.MediaSession.Token token) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void dispatchAdjustVolume(java.lang.String str, java.lang.String str2, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void addSessionsListener(android.media.session.IActiveSessionsListener iActiveSessionsListener, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void removeSessionsListener(android.media.session.IActiveSessionsListener iActiveSessionsListener) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void addSession2TokensListener(android.media.session.ISession2TokensListener iSession2TokensListener, int i) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void removeSession2TokensListener(android.media.session.ISession2TokensListener iSession2TokensListener) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void registerRemoteSessionCallback(android.media.IRemoteSessionCallback iRemoteSessionCallback) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void unregisterRemoteSessionCallback(android.media.IRemoteSessionCallback iRemoteSessionCallback) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public boolean isGlobalPriorityActive() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionManager
        public void addOnMediaKeyEventDispatchedListener(android.media.session.IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void removeOnMediaKeyEventDispatchedListener(android.media.session.IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void addOnMediaKeyEventSessionChangedListener(android.media.session.IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void removeOnMediaKeyEventSessionChangedListener(android.media.session.IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void setOnVolumeKeyLongPressListener(android.media.session.IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void setOnMediaKeyListener(android.media.session.IOnMediaKeyListener iOnMediaKeyListener) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public boolean isTrusted(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionManager
        public void setCustomMediaKeyDispatcher(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void setCustomMediaSessionPolicyProvider(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public boolean hasCustomMediaKeyDispatcher(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionManager
        public boolean hasCustomMediaSessionPolicyProvider(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionManager
        public int getSessionPolicies(android.media.session.MediaSession.Token token) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.session.ISessionManager
        public void setSessionPolicies(android.media.session.MediaSession.Token token, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.session.ISessionManager {
        public static final java.lang.String DESCRIPTOR = "android.media.session.ISessionManager";
        static final int TRANSACTION_addOnMediaKeyEventDispatchedListener = 17;
        static final int TRANSACTION_addOnMediaKeyEventSessionChangedListener = 19;
        static final int TRANSACTION_addSession2TokensListener = 12;
        static final int TRANSACTION_addSessionsListener = 10;
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_dispatchAdjustVolume = 9;
        static final int TRANSACTION_dispatchMediaKeyEvent = 5;
        static final int TRANSACTION_dispatchMediaKeyEventToSessionAsSystemService = 6;
        static final int TRANSACTION_dispatchVolumeKeyEvent = 7;
        static final int TRANSACTION_dispatchVolumeKeyEventToSessionAsSystemService = 8;
        static final int TRANSACTION_getMediaKeyEventSession = 3;
        static final int TRANSACTION_getMediaKeyEventSessionPackageName = 4;
        static final int TRANSACTION_getSessionPolicies = 28;
        static final int TRANSACTION_getSessions = 2;
        static final int TRANSACTION_hasCustomMediaKeyDispatcher = 26;
        static final int TRANSACTION_hasCustomMediaSessionPolicyProvider = 27;
        static final int TRANSACTION_isGlobalPriorityActive = 16;
        static final int TRANSACTION_isTrusted = 23;
        static final int TRANSACTION_registerRemoteSessionCallback = 14;
        static final int TRANSACTION_removeOnMediaKeyEventDispatchedListener = 18;
        static final int TRANSACTION_removeOnMediaKeyEventSessionChangedListener = 20;
        static final int TRANSACTION_removeSession2TokensListener = 13;
        static final int TRANSACTION_removeSessionsListener = 11;
        static final int TRANSACTION_setCustomMediaKeyDispatcher = 24;
        static final int TRANSACTION_setCustomMediaSessionPolicyProvider = 25;
        static final int TRANSACTION_setOnMediaKeyListener = 22;
        static final int TRANSACTION_setOnVolumeKeyLongPressListener = 21;
        static final int TRANSACTION_setSessionPolicies = 29;
        static final int TRANSACTION_unregisterRemoteSessionCallback = 15;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.session.ISessionManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.session.ISessionManager)) {
                return (android.media.session.ISessionManager) queryLocalInterface;
            }
            return new android.media.session.ISessionManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createSession";
                case 2:
                    return "getSessions";
                case 3:
                    return "getMediaKeyEventSession";
                case 4:
                    return "getMediaKeyEventSessionPackageName";
                case 5:
                    return "dispatchMediaKeyEvent";
                case 6:
                    return "dispatchMediaKeyEventToSessionAsSystemService";
                case 7:
                    return "dispatchVolumeKeyEvent";
                case 8:
                    return "dispatchVolumeKeyEventToSessionAsSystemService";
                case 9:
                    return "dispatchAdjustVolume";
                case 10:
                    return "addSessionsListener";
                case 11:
                    return "removeSessionsListener";
                case 12:
                    return "addSession2TokensListener";
                case 13:
                    return "removeSession2TokensListener";
                case 14:
                    return "registerRemoteSessionCallback";
                case 15:
                    return "unregisterRemoteSessionCallback";
                case 16:
                    return "isGlobalPriorityActive";
                case 17:
                    return "addOnMediaKeyEventDispatchedListener";
                case 18:
                    return "removeOnMediaKeyEventDispatchedListener";
                case 19:
                    return "addOnMediaKeyEventSessionChangedListener";
                case 20:
                    return "removeOnMediaKeyEventSessionChangedListener";
                case 21:
                    return "setOnVolumeKeyLongPressListener";
                case 22:
                    return "setOnMediaKeyListener";
                case 23:
                    return "isTrusted";
                case 24:
                    return "setCustomMediaKeyDispatcher";
                case 25:
                    return "setCustomMediaSessionPolicyProvider";
                case 26:
                    return "hasCustomMediaKeyDispatcher";
                case 27:
                    return "hasCustomMediaSessionPolicyProvider";
                case 28:
                    return "getSessionPolicies";
                case 29:
                    return "setSessionPolicies";
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
                    android.media.session.ISessionCallback asInterface = android.media.session.ISessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString2 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.session.ISession createSession = createSession(readString, asInterface, readString2, bundle, readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createSession);
                    return true;
                case 2:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.session.MediaSession.Token> sessions = getSessions(componentName, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(sessions, 1);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.media.session.MediaSession.Token mediaKeyEventSession = getMediaKeyEventSession(readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mediaKeyEventSession, 1);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String mediaKeyEventSessionPackageName = getMediaKeyEventSessionPackageName(readString4);
                    parcel2.writeNoException();
                    parcel2.writeString(mediaKeyEventSessionPackageName);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchMediaKeyEvent(readString5, readBoolean, keyEvent, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString6 = parcel.readString();
                    android.view.KeyEvent keyEvent2 = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    android.media.session.MediaSession.Token token = (android.media.session.MediaSession.Token) parcel.readTypedObject(android.media.session.MediaSession.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean dispatchMediaKeyEventToSessionAsSystemService = dispatchMediaKeyEventToSessionAsSystemService(readString6, keyEvent2, token);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dispatchMediaKeyEventToSessionAsSystemService);
                    return true;
                case 7:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    android.view.KeyEvent keyEvent3 = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    int readInt3 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchVolumeKeyEvent(readString7, readString8, readBoolean3, keyEvent3, readInt3, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    android.view.KeyEvent keyEvent4 = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    android.media.session.MediaSession.Token token2 = (android.media.session.MediaSession.Token) parcel.readTypedObject(android.media.session.MediaSession.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchVolumeKeyEventToSessionAsSystemService(readString9, readString10, keyEvent4, token2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchAdjustVolume(readString11, readString12, readInt4, readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.media.session.IActiveSessionsListener asInterface2 = android.media.session.IActiveSessionsListener.Stub.asInterface(parcel.readStrongBinder());
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addSessionsListener(asInterface2, componentName2, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.media.session.IActiveSessionsListener asInterface3 = android.media.session.IActiveSessionsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSessionsListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.media.session.ISession2TokensListener asInterface4 = android.media.session.ISession2TokensListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addSession2TokensListener(asInterface4, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.media.session.ISession2TokensListener asInterface5 = android.media.session.ISession2TokensListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSession2TokensListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.media.IRemoteSessionCallback asInterface6 = android.media.IRemoteSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRemoteSessionCallback(asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.media.IRemoteSessionCallback asInterface7 = android.media.IRemoteSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRemoteSessionCallback(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean isGlobalPriorityActive = isGlobalPriorityActive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGlobalPriorityActive);
                    return true;
                case 17:
                    android.media.session.IOnMediaKeyEventDispatchedListener asInterface8 = android.media.session.IOnMediaKeyEventDispatchedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnMediaKeyEventDispatchedListener(asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    android.media.session.IOnMediaKeyEventDispatchedListener asInterface9 = android.media.session.IOnMediaKeyEventDispatchedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnMediaKeyEventDispatchedListener(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.media.session.IOnMediaKeyEventSessionChangedListener asInterface10 = android.media.session.IOnMediaKeyEventSessionChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addOnMediaKeyEventSessionChangedListener(asInterface10, readString13);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.media.session.IOnMediaKeyEventSessionChangedListener asInterface11 = android.media.session.IOnMediaKeyEventSessionChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnMediaKeyEventSessionChangedListener(asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.media.session.IOnVolumeKeyLongPressListener asInterface12 = android.media.session.IOnVolumeKeyLongPressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setOnVolumeKeyLongPressListener(asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    android.media.session.IOnMediaKeyListener asInterface13 = android.media.session.IOnMediaKeyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setOnMediaKeyListener(asInterface13);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    java.lang.String readString14 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isTrusted = isTrusted(readString14, readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTrusted);
                    return true;
                case 24:
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCustomMediaKeyDispatcher(readString15);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCustomMediaSessionPolicyProvider(readString16);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasCustomMediaKeyDispatcher = hasCustomMediaKeyDispatcher(readString17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasCustomMediaKeyDispatcher);
                    return true;
                case 27:
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasCustomMediaSessionPolicyProvider = hasCustomMediaSessionPolicyProvider(readString18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasCustomMediaSessionPolicyProvider);
                    return true;
                case 28:
                    android.media.session.MediaSession.Token token3 = (android.media.session.MediaSession.Token) parcel.readTypedObject(android.media.session.MediaSession.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    int sessionPolicies = getSessionPolicies(token3);
                    parcel2.writeNoException();
                    parcel2.writeInt(sessionPolicies);
                    return true;
                case 29:
                    android.media.session.MediaSession.Token token4 = (android.media.session.MediaSession.Token) parcel.readTypedObject(android.media.session.MediaSession.Token.CREATOR);
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSessionPolicies(token4, readInt11);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.session.ISessionManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.session.ISessionManager.Stub.DESCRIPTOR;
            }

            @Override // android.media.session.ISessionManager
            public android.media.session.ISession createSession(java.lang.String str, android.media.session.ISessionCallback iSessionCallback, java.lang.String str2, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSessionCallback);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.media.session.ISession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public java.util.List<android.media.session.MediaSession.Token> getSessions(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.session.MediaSession.Token.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public android.media.session.MediaSession.Token getMediaKeyEventSession(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.session.MediaSession.Token) obtain2.readTypedObject(android.media.session.MediaSession.Token.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public java.lang.String getMediaKeyEventSessionPackageName(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchMediaKeyEvent(java.lang.String str, boolean z, android.view.KeyEvent keyEvent, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean dispatchMediaKeyEventToSessionAsSystemService(java.lang.String str, android.view.KeyEvent keyEvent, android.media.session.MediaSession.Token token) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchVolumeKeyEvent(java.lang.String str, java.lang.String str2, boolean z, android.view.KeyEvent keyEvent, int i, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchVolumeKeyEventToSessionAsSystemService(java.lang.String str, java.lang.String str2, android.view.KeyEvent keyEvent, android.media.session.MediaSession.Token token) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchAdjustVolume(java.lang.String str, java.lang.String str2, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addSessionsListener(android.media.session.IActiveSessionsListener iActiveSessionsListener, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iActiveSessionsListener);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeSessionsListener(android.media.session.IActiveSessionsListener iActiveSessionsListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iActiveSessionsListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addSession2TokensListener(android.media.session.ISession2TokensListener iSession2TokensListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSession2TokensListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeSession2TokensListener(android.media.session.ISession2TokensListener iSession2TokensListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSession2TokensListener);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void registerRemoteSessionCallback(android.media.IRemoteSessionCallback iRemoteSessionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteSessionCallback);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void unregisterRemoteSessionCallback(android.media.IRemoteSessionCallback iRemoteSessionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteSessionCallback);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean isGlobalPriorityActive() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addOnMediaKeyEventDispatchedListener(android.media.session.IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnMediaKeyEventDispatchedListener);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeOnMediaKeyEventDispatchedListener(android.media.session.IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnMediaKeyEventDispatchedListener);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addOnMediaKeyEventSessionChangedListener(android.media.session.IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnMediaKeyEventSessionChangedListener);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeOnMediaKeyEventSessionChangedListener(android.media.session.IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnMediaKeyEventSessionChangedListener);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setOnVolumeKeyLongPressListener(android.media.session.IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnVolumeKeyLongPressListener);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setOnMediaKeyListener(android.media.session.IOnMediaKeyListener iOnMediaKeyListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnMediaKeyListener);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean isTrusted(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setCustomMediaKeyDispatcher(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setCustomMediaSessionPolicyProvider(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean hasCustomMediaKeyDispatcher(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean hasCustomMediaSessionPolicyProvider(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public int getSessionPolicies(android.media.session.MediaSession.Token token) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setSessionPolicies(android.media.session.MediaSession.Token token, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 28;
        }
    }
}
