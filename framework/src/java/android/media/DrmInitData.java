package android.media;

/* loaded from: classes2.dex */
public abstract class DrmInitData {
    @java.lang.Deprecated
    public abstract android.media.DrmInitData.SchemeInitData get(java.util.UUID uuid);

    DrmInitData() {
    }

    public int getSchemeInitDataCount() {
        return 0;
    }

    public android.media.DrmInitData.SchemeInitData getSchemeInitDataAt(int i) {
        throw new java.lang.IndexOutOfBoundsException();
    }

    public static final class SchemeInitData {
        public static final java.util.UUID UUID_NIL = new java.util.UUID(0, 0);
        public final byte[] data;
        public final java.lang.String mimeType;
        public final java.util.UUID uuid;

        public SchemeInitData(java.util.UUID uuid, java.lang.String str, byte[] bArr) {
            this.uuid = (java.util.UUID) java.util.Objects.requireNonNull(uuid);
            this.mimeType = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.data = (byte[]) java.util.Objects.requireNonNull(bArr);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.media.DrmInitData.SchemeInitData)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            android.media.DrmInitData.SchemeInitData schemeInitData = (android.media.DrmInitData.SchemeInitData) obj;
            return this.uuid.equals(schemeInitData.uuid) && this.mimeType.equals(schemeInitData.mimeType) && java.util.Arrays.equals(this.data, schemeInitData.data);
        }

        public int hashCode() {
            return this.uuid.hashCode() + ((this.mimeType.hashCode() + (java.util.Arrays.hashCode(this.data) * 31)) * 31);
        }
    }
}
