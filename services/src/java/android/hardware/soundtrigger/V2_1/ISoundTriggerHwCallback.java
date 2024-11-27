package android.hardware.soundtrigger.V2_1;

/* loaded from: classes.dex */
public interface ISoundTriggerHwCallback extends android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback {
    public static final java.lang.String kInterfaceName = "android.hardware.soundtrigger@2.1::ISoundTriggerHwCallback";

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
    android.os.IHwBinder asBinder();

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
    android.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
    java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
    java.lang.String interfaceDescriptor() throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    void phraseRecognitionCallback_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent phraseRecognitionEvent, int i) throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    void recognitionCallback_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent recognitionEvent, int i) throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    void soundModelCallback_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.ModelEvent modelEvent, int i) throws android.os.RemoteException;

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    static android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback)) {
            return (android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback) queryLocalInterface;
        }
        android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.Proxy proxy = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.Proxy(iHwBinder);
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

    static android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class RecognitionEvent {
        public android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.RecognitionEvent header = new android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.RecognitionEvent();
        public android.os.HidlMemory data = null;

        public final java.lang.String toString() {
            return "{.header = " + this.header + ", .data = " + this.data + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(160L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 160, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent recognitionEvent = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent();
                recognitionEvent.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 160);
                arrayList.add(recognitionEvent);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.header.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
            long j2 = j + 120;
            try {
                this.data = hwParcel.readEmbeddedHidlMemory(hwBlob.getFieldHandle(j2), hwBlob.handle(), j2).dup();
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(160);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 160);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 160);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            this.header.writeEmbeddedToBlob(hwBlob, 0 + j);
            hwBlob.putHidlMemory(j + 120, this.data);
        }
    }

    public static final class PhraseRecognitionEvent {
        public android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent common = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent();
        public java.util.ArrayList<android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra> phraseExtras = new java.util.ArrayList<>();

        public final java.lang.String toString() {
            return "{.common = " + this.common + ", .phraseExtras = " + this.phraseExtras + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(176L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent phraseRecognitionEvent = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent();
                phraseRecognitionEvent.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT);
                arrayList.add(phraseRecognitionEvent);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.common.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
            long j2 = j + 160;
            int int32 = hwBlob.getInt32(8 + j2);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, hwBlob.handle(), j2 + 0, true);
            this.phraseExtras.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra phraseRecognitionExtra = new android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra();
                phraseRecognitionExtra.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
                this.phraseExtras.add(phraseRecognitionExtra);
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            this.common.writeEmbeddedToBlob(hwBlob, j + 0);
            int size = this.phraseExtras.size();
            long j2 = j + 160;
            hwBlob.putInt32(8 + j2, size);
            hwBlob.putBool(12 + j2, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 32);
            for (int i = 0; i < size; i++) {
                this.phraseExtras.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
            }
            hwBlob.putBlob(j2 + 0, hwBlob2);
        }
    }

    public static final class ModelEvent {
        public android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.ModelEvent header = new android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.ModelEvent();
        public android.os.HidlMemory data = null;

        public final java.lang.String toString() {
            return "{.header = " + this.header + ", .data = " + this.data + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.ModelEvent> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.ModelEvent> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.ModelEvent modelEvent = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.ModelEvent();
                modelEvent.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
                arrayList.add(modelEvent);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.header.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
            long j2 = j + 24;
            try {
                this.data = hwParcel.readEmbeddedHidlMemory(hwBlob.getFieldHandle(j2), hwBlob.handle(), j2).dup();
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(64);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.ModelEvent> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 64);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            this.header.writeEmbeddedToBlob(hwBlob, 0 + j);
            hwBlob.putHidlMemory(j + 24, this.data);
        }
    }

    public static final class Proxy implements android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            java.util.Objects.requireNonNull(iHwBinder);
            this.mRemote = iHwBinder;
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.soundtrigger@2.1::ISoundTriggerHwCallback]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback
        public void recognitionCallback(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.RecognitionEvent recognitionEvent, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.kInterfaceName);
            recognitionEvent.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback
        public void phraseRecognitionCallback(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.PhraseRecognitionEvent phraseRecognitionEvent, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.kInterfaceName);
            phraseRecognitionEvent.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback
        public void soundModelCallback(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.ModelEvent modelEvent, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.kInterfaceName);
            modelEvent.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback
        public void recognitionCallback_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent recognitionEvent, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.kInterfaceName);
            recognitionEvent.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback
        public void phraseRecognitionCallback_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent phraseRecognitionEvent, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.kInterfaceName);
            phraseRecognitionEvent.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback
        public void soundModelCallback_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.ModelEvent modelEvent, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.kInterfaceName);
            modelEvent.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
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

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback {
        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.kInterfaceName, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.kInterfaceName, android.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.kInterfaceName;
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{-24, -56, 108, 105, -60, 56, -38, -115, 21, 73, -123, 108, 27, -77, -30, -47, -72, -38, 82, 114, 47, com.android.server.usb.descriptors.UsbASFormat.EXT_FORMAT_TYPE_II, 53, -1, 73, -93, 15, 44, -50, -111, 116, 44}, new byte[]{26, 110, 43, -46, -119, -14, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_HUB, 49, -59, 38, -78, 25, 22, -111, 15, 29, 76, 67, 107, 122, -53, -107, 86, -28, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_INTERFACE, 61, -28, -50, -114, 108, -62, -28}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_PHYSICAL, -17, 5, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_INTERFACE, -13, -51, 105, 87, 19, -109, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_INTERFACE, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public final android.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.hidl.base.V1_0.DebugInfo debugInfo = new android.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback, android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback, android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.kInterfaceName.equals(str)) {
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

        public void onTransact(int i, android.os.HwParcel hwParcel, android.os.HwParcel hwParcel2, int i2) throws android.os.RemoteException {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.kInterfaceName);
                    android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.RecognitionEvent recognitionEvent = new android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.RecognitionEvent();
                    recognitionEvent.readFromParcel(hwParcel);
                    recognitionCallback(recognitionEvent, hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.kInterfaceName);
                    android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.PhraseRecognitionEvent phraseRecognitionEvent = new android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.PhraseRecognitionEvent();
                    phraseRecognitionEvent.readFromParcel(hwParcel);
                    phraseRecognitionCallback(phraseRecognitionEvent, hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.kInterfaceName);
                    android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.ModelEvent modelEvent = new android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.ModelEvent();
                    modelEvent.readFromParcel(hwParcel);
                    soundModelCallback(modelEvent, hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.kInterfaceName);
                    android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent recognitionEvent2 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent();
                    recognitionEvent2.readFromParcel(hwParcel);
                    recognitionCallback_2_1(recognitionEvent2, hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.kInterfaceName);
                    android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent phraseRecognitionEvent2 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent();
                    phraseRecognitionEvent2.readFromParcel(hwParcel);
                    phraseRecognitionCallback_2_1(phraseRecognitionEvent2, hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.kInterfaceName);
                    android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.ModelEvent modelEvent2 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.ModelEvent();
                    modelEvent2.readFromParcel(hwParcel);
                    soundModelCallback_2_1(modelEvent2, hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
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
