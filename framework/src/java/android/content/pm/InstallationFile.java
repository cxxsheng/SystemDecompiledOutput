package android.content.pm;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class InstallationFile {
    private final android.content.pm.InstallationFileParcel mParcel = new android.content.pm.InstallationFileParcel();

    public InstallationFile(int i, java.lang.String str, long j, byte[] bArr, byte[] bArr2) {
        this.mParcel.location = i;
        this.mParcel.name = str;
        this.mParcel.size = j;
        this.mParcel.metadata = bArr;
        this.mParcel.signature = bArr2;
    }

    public int getLocation() {
        return this.mParcel.location;
    }

    public java.lang.String getName() {
        return this.mParcel.name;
    }

    public long getLengthBytes() {
        return this.mParcel.size;
    }

    public byte[] getMetadata() {
        return this.mParcel.metadata;
    }

    public byte[] getSignature() {
        return this.mParcel.signature;
    }

    public android.content.pm.InstallationFileParcel getData() {
        return this.mParcel;
    }
}
