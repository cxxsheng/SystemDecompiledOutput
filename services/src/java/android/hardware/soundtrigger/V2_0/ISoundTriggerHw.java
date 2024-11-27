package android.hardware.soundtrigger.V2_0;

/* loaded from: classes.dex */
public interface ISoundTriggerHw extends android.hidl.base.V1_0.IBase {
    public static final java.lang.String kInterfaceName = "android.hardware.soundtrigger@2.0::ISoundTriggerHw";

    @java.lang.FunctionalInterface
    public interface getPropertiesCallback {
        void onValues(int i, android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties properties);
    }

    @java.lang.FunctionalInterface
    public interface loadPhraseSoundModelCallback {
        void onValues(int i, int i2);
    }

    @java.lang.FunctionalInterface
    public interface loadSoundModelCallback {
        void onValues(int i, int i2);
    }

    @Override // android.hidl.base.V1_0.IBase
    android.os.IHwBinder asBinder();

    @Override // android.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    android.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    void getProperties(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.getPropertiesCallback getpropertiescallback) throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    java.lang.String interfaceDescriptor() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    void loadPhraseSoundModel(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel phraseSoundModel, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback iSoundTriggerHwCallback, int i, android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadPhraseSoundModelCallback loadphrasesoundmodelcallback) throws android.os.RemoteException;

    void loadSoundModel(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel soundModel, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback iSoundTriggerHwCallback, int i, android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadSoundModelCallback loadsoundmodelcallback) throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    int startRecognition(int i, android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig recognitionConfig, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback iSoundTriggerHwCallback, int i2) throws android.os.RemoteException;

    int stopAllRecognitions() throws android.os.RemoteException;

    int stopRecognition(int i) throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    int unloadSoundModel(int i) throws android.os.RemoteException;

    static android.hardware.soundtrigger.V2_0.ISoundTriggerHw asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.soundtrigger.V2_0.ISoundTriggerHw)) {
            return (android.hardware.soundtrigger.V2_0.ISoundTriggerHw) queryLocalInterface;
        }
        android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Proxy proxy = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Proxy(iHwBinder);
        try {
            java.util.Iterator<java.lang.String> it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (it.next().equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (android.os.RemoteException e) {
        }
        return null;
    }

    static android.hardware.soundtrigger.V2_0.ISoundTriggerHw castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.soundtrigger.V2_0.ISoundTriggerHw getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.soundtrigger.V2_0.ISoundTriggerHw getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.soundtrigger.V2_0.ISoundTriggerHw getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.soundtrigger.V2_0.ISoundTriggerHw getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class Properties {
        public java.lang.String implementor = new java.lang.String();
        public java.lang.String description = new java.lang.String();
        public int version = 0;
        public android.hardware.audio.common.V2_0.Uuid uuid = new android.hardware.audio.common.V2_0.Uuid();
        public int maxSoundModels = 0;
        public int maxKeyPhrases = 0;
        public int maxUsers = 0;
        public int recognitionModes = 0;
        public boolean captureTransition = false;
        public int maxBufferMs = 0;
        public boolean concurrentCapture = false;
        public boolean triggerInEvent = false;
        public int powerConsumptionMw = 0;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties.class) {
                return false;
            }
            android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties properties = (android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties) obj;
            if (android.os.HidlSupport.deepEquals(this.implementor, properties.implementor) && android.os.HidlSupport.deepEquals(this.description, properties.description) && this.version == properties.version && android.os.HidlSupport.deepEquals(this.uuid, properties.uuid) && this.maxSoundModels == properties.maxSoundModels && this.maxKeyPhrases == properties.maxKeyPhrases && this.maxUsers == properties.maxUsers && this.recognitionModes == properties.recognitionModes && this.captureTransition == properties.captureTransition && this.maxBufferMs == properties.maxBufferMs && this.concurrentCapture == properties.concurrentCapture && this.triggerInEvent == properties.triggerInEvent && this.powerConsumptionMw == properties.powerConsumptionMw) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.implementor)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.description)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.version))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.uuid)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxSoundModels))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxKeyPhrases))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxUsers))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.recognitionModes))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.captureTransition))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxBufferMs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.concurrentCapture))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.triggerInEvent))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.powerConsumptionMw))));
        }

        public final java.lang.String toString() {
            return "{.implementor = " + this.implementor + ", .description = " + this.description + ", .version = " + this.version + ", .uuid = " + this.uuid + ", .maxSoundModels = " + this.maxSoundModels + ", .maxKeyPhrases = " + this.maxKeyPhrases + ", .maxUsers = " + this.maxUsers + ", .recognitionModes = " + this.recognitionModes + ", .captureTransition = " + this.captureTransition + ", .maxBufferMs = " + this.maxBufferMs + ", .concurrentCapture = " + this.concurrentCapture + ", .triggerInEvent = " + this.triggerInEvent + ", .powerConsumptionMw = " + this.powerConsumptionMw + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties properties = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties();
                properties.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
                arrayList.add(properties);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            long j2 = j + 0;
            this.implementor = hwBlob.getString(j2);
            hwParcel.readEmbeddedBuffer(this.implementor.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
            long j3 = j + 16;
            this.description = hwBlob.getString(j3);
            hwParcel.readEmbeddedBuffer(this.description.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
            this.version = hwBlob.getInt32(j + 32);
            this.uuid.readEmbeddedFromParcel(hwParcel, hwBlob, j + 36);
            this.maxSoundModels = hwBlob.getInt32(j + 52);
            this.maxKeyPhrases = hwBlob.getInt32(j + 56);
            this.maxUsers = hwBlob.getInt32(j + 60);
            this.recognitionModes = hwBlob.getInt32(j + 64);
            this.captureTransition = hwBlob.getBool(j + 68);
            this.maxBufferMs = hwBlob.getInt32(j + 72);
            this.concurrentCapture = hwBlob.getBool(j + 76);
            this.triggerInEvent = hwBlob.getBool(j + 77);
            this.powerConsumptionMw = hwBlob.getInt32(j + 80);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(88);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 88);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putString(0 + j, this.implementor);
            hwBlob.putString(16 + j, this.description);
            hwBlob.putInt32(32 + j, this.version);
            this.uuid.writeEmbeddedToBlob(hwBlob, 36 + j);
            hwBlob.putInt32(52 + j, this.maxSoundModels);
            hwBlob.putInt32(56 + j, this.maxKeyPhrases);
            hwBlob.putInt32(60 + j, this.maxUsers);
            hwBlob.putInt32(64 + j, this.recognitionModes);
            hwBlob.putBool(68 + j, this.captureTransition);
            hwBlob.putInt32(72 + j, this.maxBufferMs);
            hwBlob.putBool(76 + j, this.concurrentCapture);
            hwBlob.putBool(77 + j, this.triggerInEvent);
            hwBlob.putInt32(j + 80, this.powerConsumptionMw);
        }
    }

    public static final class SoundModel {
        public int type = 0;
        public android.hardware.audio.common.V2_0.Uuid uuid = new android.hardware.audio.common.V2_0.Uuid();
        public android.hardware.audio.common.V2_0.Uuid vendorUuid = new android.hardware.audio.common.V2_0.Uuid();
        public java.util.ArrayList<java.lang.Byte> data = new java.util.ArrayList<>();

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel.class) {
                return false;
            }
            android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel soundModel = (android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel) obj;
            if (this.type == soundModel.type && android.os.HidlSupport.deepEquals(this.uuid, soundModel.uuid) && android.os.HidlSupport.deepEquals(this.vendorUuid, soundModel.vendorUuid) && android.os.HidlSupport.deepEquals(this.data, soundModel.data)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.uuid)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.vendorUuid)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.data)));
        }

        public final java.lang.String toString() {
            return "{.type = " + android.hardware.soundtrigger.V2_0.SoundModelType.toString(this.type) + ", .uuid = " + this.uuid + ", .vendorUuid = " + this.vendorUuid + ", .data = " + this.data + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel soundModel = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel();
                soundModel.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
                arrayList.add(soundModel);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.type = hwBlob.getInt32(j + 0);
            this.uuid.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
            this.vendorUuid.readEmbeddedFromParcel(hwParcel, hwBlob, j + 20);
            long j2 = j + 40;
            int int32 = hwBlob.getInt32(8 + j2);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 1, hwBlob.handle(), j2 + 0, true);
            this.data.clear();
            for (int i = 0; i < int32; i++) {
                this.data.add(java.lang.Byte.valueOf(readEmbeddedBuffer.getInt8(i * 1)));
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(56);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 56);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt32(j + 0, this.type);
            this.uuid.writeEmbeddedToBlob(hwBlob, 4 + j);
            this.vendorUuid.writeEmbeddedToBlob(hwBlob, 20 + j);
            int size = this.data.size();
            long j2 = j + 40;
            hwBlob.putInt32(8 + j2, size);
            hwBlob.putBool(12 + j2, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 1);
            for (int i = 0; i < size; i++) {
                hwBlob2.putInt8(i * 1, this.data.get(i).byteValue());
            }
            hwBlob.putBlob(j2 + 0, hwBlob2);
        }
    }

    public static final class Phrase {
        public int id = 0;
        public int recognitionModes = 0;
        public java.util.ArrayList<java.lang.Integer> users = new java.util.ArrayList<>();
        public java.lang.String locale = new java.lang.String();
        public java.lang.String text = new java.lang.String();

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase.class) {
                return false;
            }
            android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase phrase = (android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase) obj;
            if (this.id == phrase.id && this.recognitionModes == phrase.recognitionModes && android.os.HidlSupport.deepEquals(this.users, phrase.users) && android.os.HidlSupport.deepEquals(this.locale, phrase.locale) && android.os.HidlSupport.deepEquals(this.text, phrase.text)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.id))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.recognitionModes))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.users)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.locale)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.text)));
        }

        public final java.lang.String toString() {
            return "{.id = " + this.id + ", .recognitionModes = " + this.recognitionModes + ", .users = " + this.users + ", .locale = " + this.locale + ", .text = " + this.text + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase phrase = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase();
                phrase.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
                arrayList.add(phrase);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.id = hwBlob.getInt32(j + 0);
            this.recognitionModes = hwBlob.getInt32(j + 4);
            long j2 = j + 8;
            int int32 = hwBlob.getInt32(8 + j2);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j2 + 0, true);
            this.users.clear();
            for (int i = 0; i < int32; i++) {
                this.users.add(java.lang.Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
            }
            long j3 = j + 24;
            this.locale = hwBlob.getString(j3);
            hwParcel.readEmbeddedBuffer(this.locale.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
            long j4 = j + 40;
            this.text = hwBlob.getString(j4);
            hwParcel.readEmbeddedBuffer(this.text.getBytes().length + 1, hwBlob.handle(), j4 + 0, false);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(56);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 56);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt32(j + 0, this.id);
            hwBlob.putInt32(4 + j, this.recognitionModes);
            int size = this.users.size();
            long j2 = j + 8;
            hwBlob.putInt32(8 + j2, size);
            hwBlob.putBool(12 + j2, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 4);
            for (int i = 0; i < size; i++) {
                hwBlob2.putInt32(i * 4, this.users.get(i).intValue());
            }
            hwBlob.putBlob(j2 + 0, hwBlob2);
            hwBlob.putString(24 + j, this.locale);
            hwBlob.putString(j + 40, this.text);
        }
    }

    public static final class PhraseSoundModel {
        public android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel common = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel();
        public java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase> phrases = new java.util.ArrayList<>();

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel.class) {
                return false;
            }
            android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel phraseSoundModel = (android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel) obj;
            if (android.os.HidlSupport.deepEquals(this.common, phraseSoundModel.common) && android.os.HidlSupport.deepEquals(this.phrases, phraseSoundModel.phrases)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.common)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.phrases)));
        }

        public final java.lang.String toString() {
            return "{.common = " + this.common + ", .phrases = " + this.phrases + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel phraseSoundModel = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel();
                phraseSoundModel.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
                arrayList.add(phraseSoundModel);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.common.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
            long j2 = j + 56;
            int int32 = hwBlob.getInt32(8 + j2);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, hwBlob.handle(), j2 + 0, true);
            this.phrases.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase phrase = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase();
                phrase.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
                this.phrases.add(phrase);
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(72);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 72);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            this.common.writeEmbeddedToBlob(hwBlob, j + 0);
            int size = this.phrases.size();
            long j2 = j + 56;
            hwBlob.putInt32(8 + j2, size);
            hwBlob.putBool(12 + j2, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 56);
            for (int i = 0; i < size; i++) {
                this.phrases.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
            }
            hwBlob.putBlob(j2 + 0, hwBlob2);
        }
    }

    public static final class RecognitionConfig {
        public int captureHandle = 0;
        public int captureDevice = 0;
        public boolean captureRequested = false;
        public java.util.ArrayList<android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra> phrases = new java.util.ArrayList<>();
        public java.util.ArrayList<java.lang.Byte> data = new java.util.ArrayList<>();

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig.class) {
                return false;
            }
            android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig recognitionConfig = (android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig) obj;
            if (this.captureHandle == recognitionConfig.captureHandle && this.captureDevice == recognitionConfig.captureDevice && this.captureRequested == recognitionConfig.captureRequested && android.os.HidlSupport.deepEquals(this.phrases, recognitionConfig.phrases) && android.os.HidlSupport.deepEquals(this.data, recognitionConfig.data)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.captureHandle))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.captureDevice))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.captureRequested))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.phrases)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.data)));
        }

        public final java.lang.String toString() {
            return "{.captureHandle = " + this.captureHandle + ", .captureDevice = " + android.hardware.audio.common.V2_0.AudioDevice.toString(this.captureDevice) + ", .captureRequested = " + this.captureRequested + ", .phrases = " + this.phrases + ", .data = " + this.data + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig recognitionConfig = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig();
                recognitionConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
                arrayList.add(recognitionConfig);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.captureHandle = hwBlob.getInt32(j + 0);
            this.captureDevice = hwBlob.getInt32(j + 4);
            this.captureRequested = hwBlob.getBool(j + 8);
            long j2 = j + 16;
            int int32 = hwBlob.getInt32(j2 + 8);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, hwBlob.handle(), j2 + 0, true);
            this.phrases.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra phraseRecognitionExtra = new android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra();
                phraseRecognitionExtra.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
                this.phrases.add(phraseRecognitionExtra);
            }
            long j3 = j + 32;
            int int322 = hwBlob.getInt32(8 + j3);
            android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 1, hwBlob.handle(), j3 + 0, true);
            this.data.clear();
            for (int i2 = 0; i2 < int322; i2++) {
                this.data.add(java.lang.Byte.valueOf(readEmbeddedBuffer2.getInt8(i2 * 1)));
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(48);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 48);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt32(j + 0, this.captureHandle);
            hwBlob.putInt32(j + 4, this.captureDevice);
            hwBlob.putBool(j + 8, this.captureRequested);
            int size = this.phrases.size();
            long j2 = j + 16;
            hwBlob.putInt32(j2 + 8, size);
            hwBlob.putBool(j2 + 12, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 32);
            for (int i = 0; i < size; i++) {
                this.phrases.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
            }
            hwBlob.putBlob(j2 + 0, hwBlob2);
            int size2 = this.data.size();
            long j3 = j + 32;
            hwBlob.putInt32(8 + j3, size2);
            hwBlob.putBool(j3 + 12, false);
            android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 1);
            for (int i2 = 0; i2 < size2; i2++) {
                hwBlob3.putInt8(i2 * 1, this.data.get(i2).byteValue());
            }
            hwBlob.putBlob(j3 + 0, hwBlob3);
        }
    }

    public static final class Proxy implements android.hardware.soundtrigger.V2_0.ISoundTriggerHw {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            java.util.Objects.requireNonNull(iHwBinder);
            this.mRemote = iHwBinder;
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.soundtrigger@2.0::ISoundTriggerHw]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
        public void getProperties(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.getPropertiesCallback getpropertiescallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                int readInt32 = hwParcel2.readInt32();
                android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties properties = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties();
                properties.readFromParcel(hwParcel2);
                getpropertiescallback.onValues(readInt32, properties);
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
        public void loadSoundModel(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel soundModel, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback iSoundTriggerHwCallback, int i, android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadSoundModelCallback loadsoundmodelcallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
            soundModel.writeToParcel(hwParcel);
            hwParcel.writeStrongBinder(iSoundTriggerHwCallback == null ? null : iSoundTriggerHwCallback.asBinder());
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                loadsoundmodelcallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
        public void loadPhraseSoundModel(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel phraseSoundModel, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback iSoundTriggerHwCallback, int i, android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadPhraseSoundModelCallback loadphrasesoundmodelcallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
            phraseSoundModel.writeToParcel(hwParcel);
            hwParcel.writeStrongBinder(iSoundTriggerHwCallback == null ? null : iSoundTriggerHwCallback.asBinder());
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                loadphrasesoundmodelcallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
        public int unloadSoundModel(int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
        public int startRecognition(int i, android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig recognitionConfig, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback iSoundTriggerHwCallback, int i2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
            hwParcel.writeInt32(i);
            recognitionConfig.writeToParcel(hwParcel);
            hwParcel.writeStrongBinder(iSoundTriggerHwCallback == null ? null : iSoundTriggerHwCallback.asBinder());
            hwParcel.writeInt32(i2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
        public int stopRecognition(int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
        public int stopAllRecognitions() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256067662, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
            hwParcel.writeNativeHandle(nativeHandle);
            hwParcel.writeStringVector(arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256131655, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public java.lang.String interfaceDescriptor() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256136003, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readString();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                java.util.ArrayList<byte[]> arrayList = new java.util.ArrayList<>();
                android.os.HwBlob readBuffer = hwParcel2.readBuffer(16L);
                int int32 = readBuffer.getInt32(8L);
                android.os.HwBlob readEmbeddedBuffer = hwParcel2.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    readEmbeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public void setHALInstrumentation() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256462420, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public void ping() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256921159, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public android.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(257049926, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hidl.base.V1_0.DebugInfo debugInfo = new android.hidl.base.V1_0.DebugInfo();
                debugInfo.readFromParcel(hwParcel2);
                return debugInfo;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public void notifySyspropsChanged() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(257120595, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.soundtrigger.V2_0.ISoundTriggerHw {
        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName, android.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName;
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{91, -17, -64, 25, -53, -23, 73, 83, 102, 30, 44, -37, -107, -29, -49, 100, -11, -27, 101, -62, -108, 3, -31, -62, -38, -20, -62, -66, 68, -32, -91, 92}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_PHYSICAL, -17, 5, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_INTERFACE, -13, -51, 105, 87, 19, -109, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_INTERFACE, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final android.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.hidl.base.V1_0.DebugInfo debugInfo = new android.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName.equals(str)) {
                return this;
            }
            return null;
        }

        public void registerAsService(java.lang.String str) throws android.os.RemoteException {
            registerService(str);
        }

        public java.lang.String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        public void onTransact(int i, android.os.HwParcel hwParcel, final android.os.HwParcel hwParcel2, int i2) throws android.os.RemoteException {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
                    getProperties(new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.getPropertiesCallback() { // from class: android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Stub.1
                        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw.getPropertiesCallback
                        public void onValues(int i3, android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties properties) {
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeInt32(i3);
                            properties.writeToParcel(hwParcel2);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
                    android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel soundModel = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel();
                    soundModel.readFromParcel(hwParcel);
                    loadSoundModel(soundModel, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.asInterface(hwParcel.readStrongBinder()), hwParcel.readInt32(), new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadSoundModelCallback() { // from class: android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Stub.2
                        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadSoundModelCallback
                        public void onValues(int i3, int i4) {
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeInt32(i3);
                            hwParcel2.writeInt32(i4);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
                    android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel phraseSoundModel = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel();
                    phraseSoundModel.readFromParcel(hwParcel);
                    loadPhraseSoundModel(phraseSoundModel, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.asInterface(hwParcel.readStrongBinder()), hwParcel.readInt32(), new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadPhraseSoundModelCallback() { // from class: android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Stub.3
                        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadPhraseSoundModelCallback
                        public void onValues(int i3, int i4) {
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeInt32(i3);
                            hwParcel2.writeInt32(i4);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
                    int unloadSoundModel = unloadSoundModel(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(unloadSoundModel);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
                    int readInt32 = hwParcel.readInt32();
                    android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig recognitionConfig = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig();
                    recognitionConfig.readFromParcel(hwParcel);
                    int startRecognition = startRecognition(readInt32, recognitionConfig, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.asInterface(hwParcel.readStrongBinder()), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(startRecognition);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
                    int stopRecognition = stopRecognition(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(stopRecognition);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName);
                    int stopAllRecognitions = stopAllRecognitions();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(stopAllRecognitions);
                    hwParcel2.send();
                    return;
                case 256067662:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    java.util.ArrayList<java.lang.String> interfaceChain = interfaceChain();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(interfaceChain);
                    hwParcel2.send();
                    return;
                case 256131655:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    debug(hwParcel.readNativeHandle(), hwParcel.readStringVector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 256136003:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    java.lang.String interfaceDescriptor = interfaceDescriptor();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeString(interfaceDescriptor);
                    hwParcel2.send();
                    return;
                case 256398152:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    java.util.ArrayList<byte[]> hashChain = getHashChain();
                    hwParcel2.writeStatus(0);
                    android.os.HwBlob hwBlob = new android.os.HwBlob(16);
                    int size = hashChain.size();
                    hwBlob.putInt32(8L, size);
                    hwBlob.putBool(12L, false);
                    android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 32);
                    for (int i3 = 0; i3 < size; i3++) {
                        long j = i3 * 32;
                        byte[] bArr = hashChain.get(i3);
                        if (bArr == null || bArr.length != 32) {
                            throw new java.lang.IllegalArgumentException("Array element is not of the expected length");
                        }
                        hwBlob2.putInt8Array(j, bArr);
                    }
                    hwBlob.putBlob(0L, hwBlob2);
                    hwParcel2.writeBuffer(hwBlob);
                    hwParcel2.send();
                    return;
                case 256462420:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    setHALInstrumentation();
                    return;
                case 256660548:
                default:
                    return;
                case 256921159:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    ping();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 257049926:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    android.hidl.base.V1_0.DebugInfo debugInfo = getDebugInfo();
                    hwParcel2.writeStatus(0);
                    debugInfo.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 257120595:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    notifySyspropsChanged();
                    return;
            }
        }
    }
}
