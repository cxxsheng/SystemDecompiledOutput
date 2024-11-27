package android.media;

/* loaded from: classes2.dex */
class AudioHandle {
    private final int mId;

    AudioHandle(int i) {
        this.mId = i;
    }

    int id() {
        return this.mId;
    }

    public boolean equals(java.lang.Object obj) {
        return obj != null && (obj instanceof android.media.AudioHandle) && this.mId == ((android.media.AudioHandle) obj).id();
    }

    public int hashCode() {
        return this.mId;
    }

    public java.lang.String toString() {
        return java.lang.Integer.toString(this.mId);
    }
}
