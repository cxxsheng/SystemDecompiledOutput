package android.speech.tts;

/* loaded from: classes3.dex */
public interface ITextToSpeechService extends android.os.IInterface {
    java.lang.String[] getClientDefaultLanguage() throws android.os.RemoteException;

    java.lang.String getDefaultVoiceNameFor(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    java.lang.String[] getFeaturesForLanguage(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    java.lang.String[] getLanguage() throws android.os.RemoteException;

    java.util.List<android.speech.tts.Voice> getVoices() throws android.os.RemoteException;

    int isLanguageAvailable(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    boolean isSpeaking() throws android.os.RemoteException;

    int loadLanguage(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    int loadVoice(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException;

    int playAudio(android.os.IBinder iBinder, android.net.Uri uri, int i, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException;

    int playSilence(android.os.IBinder iBinder, long j, int i, java.lang.String str) throws android.os.RemoteException;

    void setCallback(android.os.IBinder iBinder, android.speech.tts.ITextToSpeechCallback iTextToSpeechCallback) throws android.os.RemoteException;

    int speak(android.os.IBinder iBinder, java.lang.CharSequence charSequence, int i, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException;

    int stop(android.os.IBinder iBinder) throws android.os.RemoteException;

    int synthesizeToFileDescriptor(android.os.IBinder iBinder, java.lang.CharSequence charSequence, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.speech.tts.ITextToSpeechService {
        @Override // android.speech.tts.ITextToSpeechService
        public int speak(android.os.IBinder iBinder, java.lang.CharSequence charSequence, int i, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int synthesizeToFileDescriptor(android.os.IBinder iBinder, java.lang.CharSequence charSequence, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int playAudio(android.os.IBinder iBinder, android.net.Uri uri, int i, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int playSilence(android.os.IBinder iBinder, long j, int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public boolean isSpeaking() throws android.os.RemoteException {
            return false;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int stop(android.os.IBinder iBinder) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public java.lang.String[] getLanguage() throws android.os.RemoteException {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public java.lang.String[] getClientDefaultLanguage() throws android.os.RemoteException {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int isLanguageAvailable(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public java.lang.String[] getFeaturesForLanguage(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int loadLanguage(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public void setCallback(android.os.IBinder iBinder, android.speech.tts.ITextToSpeechCallback iTextToSpeechCallback) throws android.os.RemoteException {
        }

        @Override // android.speech.tts.ITextToSpeechService
        public java.util.List<android.speech.tts.Voice> getVoices() throws android.os.RemoteException {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int loadVoice(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public java.lang.String getDefaultVoiceNameFor(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.speech.tts.ITextToSpeechService {
        public static final java.lang.String DESCRIPTOR = "android.speech.tts.ITextToSpeechService";
        static final int TRANSACTION_getClientDefaultLanguage = 8;
        static final int TRANSACTION_getDefaultVoiceNameFor = 15;
        static final int TRANSACTION_getFeaturesForLanguage = 10;
        static final int TRANSACTION_getLanguage = 7;
        static final int TRANSACTION_getVoices = 13;
        static final int TRANSACTION_isLanguageAvailable = 9;
        static final int TRANSACTION_isSpeaking = 5;
        static final int TRANSACTION_loadLanguage = 11;
        static final int TRANSACTION_loadVoice = 14;
        static final int TRANSACTION_playAudio = 3;
        static final int TRANSACTION_playSilence = 4;
        static final int TRANSACTION_setCallback = 12;
        static final int TRANSACTION_speak = 1;
        static final int TRANSACTION_stop = 6;
        static final int TRANSACTION_synthesizeToFileDescriptor = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.speech.tts.ITextToSpeechService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.speech.tts.ITextToSpeechService)) {
                return (android.speech.tts.ITextToSpeechService) queryLocalInterface;
            }
            return new android.speech.tts.ITextToSpeechService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "speak";
                case 2:
                    return "synthesizeToFileDescriptor";
                case 3:
                    return "playAudio";
                case 4:
                    return "playSilence";
                case 5:
                    return "isSpeaking";
                case 6:
                    return "stop";
                case 7:
                    return "getLanguage";
                case 8:
                    return "getClientDefaultLanguage";
                case 9:
                    return "isLanguageAvailable";
                case 10:
                    return "getFeaturesForLanguage";
                case 11:
                    return "loadLanguage";
                case 12:
                    return "setCallback";
                case 13:
                    return "getVoices";
                case 14:
                    return "loadVoice";
                case 15:
                    return "getDefaultVoiceNameFor";
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
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt = parcel.readInt();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int speak = speak(readStrongBinder, charSequence, readInt, bundle, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(speak);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    java.lang.CharSequence charSequence2 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int synthesizeToFileDescriptor = synthesizeToFileDescriptor(readStrongBinder2, charSequence2, parcelFileDescriptor, bundle2, readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(synthesizeToFileDescriptor);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt2 = parcel.readInt();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int playAudio = playAudio(readStrongBinder3, uri, readInt2, bundle3, readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(playAudio);
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    int readInt3 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int playSilence = playSilence(readStrongBinder4, readLong, readInt3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(playSilence);
                    return true;
                case 5:
                    boolean isSpeaking = isSpeaking();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSpeaking);
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int stop = stop(readStrongBinder5);
                    parcel2.writeNoException();
                    parcel2.writeInt(stop);
                    return true;
                case 7:
                    java.lang.String[] language = getLanguage();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(language);
                    return true;
                case 8:
                    java.lang.String[] clientDefaultLanguage = getClientDefaultLanguage();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(clientDefaultLanguage);
                    return true;
                case 9:
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isLanguageAvailable = isLanguageAvailable(readString5, readString6, readString7);
                    parcel2.writeNoException();
                    parcel2.writeInt(isLanguageAvailable);
                    return true;
                case 10:
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] featuresForLanguage = getFeaturesForLanguage(readString8, readString9, readString10);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(featuresForLanguage);
                    return true;
                case 11:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int loadLanguage = loadLanguage(readStrongBinder6, readString11, readString12, readString13);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadLanguage);
                    return true;
                case 12:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    android.speech.tts.ITextToSpeechCallback asInterface = android.speech.tts.ITextToSpeechCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(readStrongBinder7, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    java.util.List<android.speech.tts.Voice> voices = getVoices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(voices, 1);
                    return true;
                case 14:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int loadVoice = loadVoice(readStrongBinder8, readString14);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadVoice);
                    return true;
                case 15:
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String defaultVoiceNameFor = getDefaultVoiceNameFor(readString15, readString16, readString17);
                    parcel2.writeNoException();
                    parcel2.writeString(defaultVoiceNameFor);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.speech.tts.ITextToSpeechService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR;
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int speak(android.os.IBinder iBinder, java.lang.CharSequence charSequence, int i, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int synthesizeToFileDescriptor(android.os.IBinder iBinder, java.lang.CharSequence charSequence, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int playAudio(android.os.IBinder iBinder, android.net.Uri uri, int i, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int playSilence(android.os.IBinder iBinder, long j, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public boolean isSpeaking() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int stop(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public java.lang.String[] getLanguage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public java.lang.String[] getClientDefaultLanguage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int isLanguageAvailable(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public java.lang.String[] getFeaturesForLanguage(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int loadLanguage(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public void setCallback(android.os.IBinder iBinder, android.speech.tts.ITextToSpeechCallback iTextToSpeechCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iTextToSpeechCallback);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public java.util.List<android.speech.tts.Voice> getVoices() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.speech.tts.Voice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int loadVoice(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public java.lang.String getDefaultVoiceNameFor(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }
    }
}
