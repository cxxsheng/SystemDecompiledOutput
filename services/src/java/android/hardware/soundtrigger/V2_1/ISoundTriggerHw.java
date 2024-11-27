package android.hardware.soundtrigger.V2_1;

/* loaded from: classes.dex */
public interface ISoundTriggerHw extends android.hardware.soundtrigger.V2_0.ISoundTriggerHw {
    public static final java.lang.String kInterfaceName = "android.hardware.soundtrigger@2.1::ISoundTriggerHw";

    @java.lang.FunctionalInterface
    public interface loadPhraseSoundModel_2_1Callback {
        void onValues(int i, int i2);
    }

    @java.lang.FunctionalInterface
    public interface loadSoundModel_2_1Callback {
        void onValues(int i, int i2);
    }

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    android.os.IHwBinder asBinder();

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    android.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    java.lang.String interfaceDescriptor() throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    void loadPhraseSoundModel_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel phraseSoundModel, android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback iSoundTriggerHwCallback, int i, android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadPhraseSoundModel_2_1Callback loadphrasesoundmodel_2_1callback) throws android.os.RemoteException;

    void loadSoundModel_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel soundModel, android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback iSoundTriggerHwCallback, int i, android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadSoundModel_2_1Callback loadsoundmodel_2_1callback) throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    int startRecognition_2_1(int i, android.hardware.soundtrigger.V2_1.ISoundTriggerHw.RecognitionConfig recognitionConfig, android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback iSoundTriggerHwCallback, int i2) throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    static android.hardware.soundtrigger.V2_1.ISoundTriggerHw asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.soundtrigger.V2_1.ISoundTriggerHw)) {
            return (android.hardware.soundtrigger.V2_1.ISoundTriggerHw) queryLocalInterface;
        }
        android.hardware.soundtrigger.V2_1.ISoundTriggerHw.Proxy proxy = new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.Proxy(iHwBinder);
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

    static android.hardware.soundtrigger.V2_1.ISoundTriggerHw castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.soundtrigger.V2_1.ISoundTriggerHw getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.soundtrigger.V2_1.ISoundTriggerHw getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.soundtrigger.V2_1.ISoundTriggerHw getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.soundtrigger.V2_1.ISoundTriggerHw getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class SoundModel {
        public android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel header = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel();
        public android.os.HidlMemory data = null;

        public final java.lang.String toString() {
            return "{.header = " + this.header + ", .data = " + this.data + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel soundModel = new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel();
                soundModel.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
                arrayList.add(soundModel);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.header.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
            long j2 = j + 56;
            try {
                this.data = hwParcel.readEmbeddedHidlMemory(hwBlob.getFieldHandle(j2), hwBlob.handle(), j2).dup();
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(96);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 96);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            this.header.writeEmbeddedToBlob(hwBlob, 0 + j);
            hwBlob.putHidlMemory(j + 56, this.data);
        }
    }

    public static final class PhraseSoundModel {
        public android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel common = new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel();
        public java.util.ArrayList<android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase> phrases = new java.util.ArrayList<>();

        public final java.lang.String toString() {
            return "{.common = " + this.common + ", .phrases = " + this.phrases + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel phraseSoundModel = new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel();
                phraseSoundModel.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 112);
                arrayList.add(phraseSoundModel);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.common.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
            long j2 = j + 96;
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
            android.os.HwBlob hwBlob = new android.os.HwBlob(112);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 112);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            this.common.writeEmbeddedToBlob(hwBlob, j + 0);
            int size = this.phrases.size();
            long j2 = j + 96;
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
        public android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig header = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig();
        public android.os.HidlMemory data = null;

        public final java.lang.String toString() {
            return "{.header = " + this.header + ", .data = " + this.data + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHw.RecognitionConfig> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHw.RecognitionConfig> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_1.ISoundTriggerHw.RecognitionConfig recognitionConfig = new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.RecognitionConfig();
                recognitionConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
                arrayList.add(recognitionConfig);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.header.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
            long j2 = j + 48;
            try {
                this.data = hwParcel.readEmbeddedHidlMemory(hwBlob.getFieldHandle(j2), hwBlob.handle(), j2).dup();
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(88);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHw.RecognitionConfig> arrayList) {
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
            this.header.writeEmbeddedToBlob(hwBlob, 0 + j);
            hwBlob.putHidlMemory(j + 48, this.data);
        }
    }

    public static final class Proxy implements android.hardware.soundtrigger.V2_1.ISoundTriggerHw {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            java.util.Objects.requireNonNull(iHwBinder);
            this.mRemote = iHwBinder;
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.soundtrigger@2.1::ISoundTriggerHw]@Proxy";
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw
        public void loadSoundModel_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel soundModel, android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback iSoundTriggerHwCallback, int i, android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadSoundModel_2_1Callback loadsoundmodel_2_1callback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.kInterfaceName);
            soundModel.writeToParcel(hwParcel);
            hwParcel.writeStrongBinder(iSoundTriggerHwCallback == null ? null : iSoundTriggerHwCallback.asBinder());
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                loadsoundmodel_2_1callback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw
        public void loadPhraseSoundModel_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel phraseSoundModel, android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback iSoundTriggerHwCallback, int i, android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadPhraseSoundModel_2_1Callback loadphrasesoundmodel_2_1callback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.kInterfaceName);
            phraseSoundModel.writeToParcel(hwParcel);
            hwParcel.writeStrongBinder(iSoundTriggerHwCallback == null ? null : iSoundTriggerHwCallback.asBinder());
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                loadphrasesoundmodel_2_1callback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw
        public int startRecognition_2_1(int i, android.hardware.soundtrigger.V2_1.ISoundTriggerHw.RecognitionConfig recognitionConfig, android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback iSoundTriggerHwCallback, int i2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.kInterfaceName);
            hwParcel.writeInt32(i);
            recognitionConfig.writeToParcel(hwParcel);
            hwParcel.writeStrongBinder(iSoundTriggerHwCallback == null ? null : iSoundTriggerHwCallback.asBinder());
            hwParcel.writeInt32(i2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.soundtrigger.V2_1.ISoundTriggerHw {
        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.kInterfaceName, android.hardware.soundtrigger.V2_0.ISoundTriggerHw.kInterfaceName, android.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.soundtrigger.V2_1.ISoundTriggerHw.kInterfaceName;
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{-76, -11, 7, -76, -36, -101, 92, -43, -16, -28, 68, 89, 38, -84, -73, -39, 69, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_ENDPOINT, -82, 96, -36, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_ENDPOINT_COMPANION, 123, 57, 81, 20, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_REPORT, com.android.server.usb.descriptors.UsbASFormat.EXT_FORMAT_TYPE_III, 99, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_REPORT, 7, -74}, new byte[]{91, -17, -64, 25, -53, -23, 73, 83, 102, 30, 44, -37, -107, -29, -49, 100, -11, -27, 101, -62, -108, 3, -31, -62, -38, -20, -62, -66, 68, -32, -91, 92}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_PHYSICAL, -17, 5, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_INTERFACE, -13, -51, 105, 87, 19, -109, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_INTERFACE, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final android.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.hidl.base.V1_0.DebugInfo debugInfo = new android.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.soundtrigger.V2_1.ISoundTriggerHw.kInterfaceName.equals(str)) {
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
                    getProperties(new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.getPropertiesCallback() { // from class: android.hardware.soundtrigger.V2_1.ISoundTriggerHw.Stub.1
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
                    loadSoundModel(soundModel, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.asInterface(hwParcel.readStrongBinder()), hwParcel.readInt32(), new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadSoundModelCallback() { // from class: android.hardware.soundtrigger.V2_1.ISoundTriggerHw.Stub.2
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
                    loadPhraseSoundModel(phraseSoundModel, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.asInterface(hwParcel.readStrongBinder()), hwParcel.readInt32(), new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadPhraseSoundModelCallback() { // from class: android.hardware.soundtrigger.V2_1.ISoundTriggerHw.Stub.3
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
                case 8:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.kInterfaceName);
                    android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel soundModel2 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel();
                    soundModel2.readFromParcel(hwParcel);
                    loadSoundModel_2_1(soundModel2, android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.asInterface(hwParcel.readStrongBinder()), hwParcel.readInt32(), new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadSoundModel_2_1Callback() { // from class: android.hardware.soundtrigger.V2_1.ISoundTriggerHw.Stub.4
                        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadSoundModel_2_1Callback
                        public void onValues(int i3, int i4) {
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeInt32(i3);
                            hwParcel2.writeInt32(i4);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.kInterfaceName);
                    android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel phraseSoundModel2 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel();
                    phraseSoundModel2.readFromParcel(hwParcel);
                    loadPhraseSoundModel_2_1(phraseSoundModel2, android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.asInterface(hwParcel.readStrongBinder()), hwParcel.readInt32(), new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadPhraseSoundModel_2_1Callback() { // from class: android.hardware.soundtrigger.V2_1.ISoundTriggerHw.Stub.5
                        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadPhraseSoundModel_2_1Callback
                        public void onValues(int i3, int i4) {
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeInt32(i3);
                            hwParcel2.writeInt32(i4);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.kInterfaceName);
                    int readInt322 = hwParcel.readInt32();
                    android.hardware.soundtrigger.V2_1.ISoundTriggerHw.RecognitionConfig recognitionConfig2 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.RecognitionConfig();
                    recognitionConfig2.readFromParcel(hwParcel);
                    int startRecognition_2_1 = startRecognition_2_1(readInt322, recognitionConfig2, android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.asInterface(hwParcel.readStrongBinder()), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(startRecognition_2_1);
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
