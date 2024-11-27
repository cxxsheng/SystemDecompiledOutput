package android.util.apk;

/* loaded from: classes3.dex */
interface DataSource {
    void feedIntoDataDigester(android.util.apk.DataDigester dataDigester, long j, int i) throws java.io.IOException, java.security.DigestException;

    long size();

    static android.util.apk.DataSource create(java.io.FileDescriptor fileDescriptor, long j, long j2) {
        if (android.os.incremental.IncrementalManager.isIncrementalFileFd(fileDescriptor)) {
            return new android.util.apk.ReadFileDataSource(fileDescriptor, j, j2);
        }
        return new android.util.apk.MemoryMappedFileDataSource(fileDescriptor, j, j2);
    }
}
