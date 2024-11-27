package android.media;

/* loaded from: classes2.dex */
public class AudioPatch {
    private final android.media.AudioHandle mHandle;
    private final android.media.AudioPortConfig[] mSinks;
    private final android.media.AudioPortConfig[] mSources;

    AudioPatch(android.media.AudioHandle audioHandle, android.media.AudioPortConfig[] audioPortConfigArr, android.media.AudioPortConfig[] audioPortConfigArr2) {
        this.mHandle = audioHandle;
        this.mSources = audioPortConfigArr;
        this.mSinks = audioPortConfigArr2;
    }

    public android.media.AudioPortConfig[] sources() {
        return this.mSources;
    }

    public android.media.AudioPortConfig[] sinks() {
        return this.mSinks;
    }

    public int id() {
        return this.mHandle.id();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("mHandle: ");
        sb.append(this.mHandle.toString());
        sb.append(" mSources: {");
        for (android.media.AudioPortConfig audioPortConfig : this.mSources) {
            sb.append(audioPortConfig.toString());
            sb.append(", ");
        }
        sb.append("} mSinks: {");
        for (android.media.AudioPortConfig audioPortConfig2 : this.mSinks) {
            sb.append(audioPortConfig2.toString());
            sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }
}
