package android.util.jar;

/* loaded from: classes3.dex */
public final class StrictJarFile {
    private boolean closed;
    private final java.io.FileDescriptor fd;
    private final dalvik.system.CloseGuard guard;
    private final boolean isSigned;
    private final android.util.jar.StrictJarManifest manifest;
    private final long nativeHandle;
    private final android.util.jar.StrictJarVerifier verifier;

    private static native void nativeClose(long j);

    private static native java.util.zip.ZipEntry nativeFindEntry(long j, java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native java.util.zip.ZipEntry nativeNextEntry(long j);

    private static native long nativeOpenJarFile(java.lang.String str, int i) throws java.io.IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeStartIteration(long j, java.lang.String str);

    public StrictJarFile(java.lang.String str) throws java.io.IOException, java.lang.SecurityException {
        this(str, true, true);
    }

    public StrictJarFile(java.io.FileDescriptor fileDescriptor) throws java.io.IOException, java.lang.SecurityException {
        this(fileDescriptor, true, true);
    }

    public StrictJarFile(java.io.FileDescriptor fileDescriptor, boolean z, boolean z2) throws java.io.IOException, java.lang.SecurityException {
        this("[fd:" + fileDescriptor.getInt$() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END, fileDescriptor, z, z2);
    }

    public StrictJarFile(java.lang.String str, boolean z, boolean z2) throws java.io.IOException, java.lang.SecurityException {
        this(str, libcore.io.IoBridge.open(str, android.system.OsConstants.O_RDONLY), z, z2);
    }

    private StrictJarFile(java.lang.String str, java.io.FileDescriptor fileDescriptor, boolean z, boolean z2) throws java.io.IOException, java.lang.SecurityException {
        this.guard = dalvik.system.CloseGuard.get();
        this.nativeHandle = nativeOpenJarFile(str, fileDescriptor.getInt$());
        this.fd = fileDescriptor;
        boolean z3 = false;
        try {
            if (z) {
                java.util.HashMap<java.lang.String, byte[]> metaEntries = getMetaEntries();
                this.manifest = new android.util.jar.StrictJarManifest(metaEntries.get("META-INF/MANIFEST.MF"), true);
                this.verifier = new android.util.jar.StrictJarVerifier(str, this.manifest, metaEntries, z2);
                for (java.lang.String str2 : this.manifest.getEntries().keySet()) {
                    if (findEntry(str2) == null) {
                        throw new java.lang.SecurityException("File " + str2 + " in manifest does not exist");
                    }
                }
                if (this.verifier.readCertificates() && this.verifier.isSignedJar()) {
                    z3 = true;
                }
                this.isSigned = z3;
            } else {
                this.isSigned = false;
                this.manifest = null;
                this.verifier = null;
            }
            this.guard.open("close");
        } catch (java.io.IOException | java.lang.SecurityException e) {
            nativeClose(this.nativeHandle);
            libcore.io.IoUtils.closeQuietly(fileDescriptor);
            this.closed = true;
            throw e;
        }
    }

    public android.util.jar.StrictJarManifest getManifest() {
        return this.manifest;
    }

    public java.util.Iterator<java.util.zip.ZipEntry> iterator() throws java.io.IOException {
        return new android.util.jar.StrictJarFile.EntryIterator(this.nativeHandle, "");
    }

    public java.util.zip.ZipEntry findEntry(java.lang.String str) {
        return nativeFindEntry(this.nativeHandle, str);
    }

    public java.security.cert.Certificate[][] getCertificateChains(java.util.zip.ZipEntry zipEntry) {
        if (this.isSigned) {
            return this.verifier.getCertificateChains(zipEntry.getName());
        }
        return null;
    }

    @java.lang.Deprecated
    public java.security.cert.Certificate[] getCertificates(java.util.zip.ZipEntry zipEntry) {
        if (this.isSigned) {
            java.security.cert.Certificate[][] certificateChains = this.verifier.getCertificateChains(zipEntry.getName());
            int i = 0;
            for (java.security.cert.Certificate[] certificateArr : certificateChains) {
                i += certificateArr.length;
            }
            java.security.cert.Certificate[] certificateArr2 = new java.security.cert.Certificate[i];
            int i2 = 0;
            for (java.security.cert.Certificate[] certificateArr3 : certificateChains) {
                java.lang.System.arraycopy(certificateArr3, 0, certificateArr2, i2, certificateArr3.length);
                i2 += certificateArr3.length;
            }
            return certificateArr2;
        }
        return null;
    }

    public java.io.InputStream getInputStream(java.util.zip.ZipEntry zipEntry) {
        java.io.InputStream zipInputStream = getZipInputStream(zipEntry);
        if (this.isSigned) {
            android.util.jar.StrictJarVerifier.VerifierEntry initEntry = this.verifier.initEntry(zipEntry.getName());
            if (initEntry == null) {
                return zipInputStream;
            }
            return new android.util.jar.StrictJarFile.JarFileInputStream(zipInputStream, zipEntry.getSize(), initEntry);
        }
        return zipInputStream;
    }

    public void close() throws java.io.IOException {
        if (!this.closed) {
            if (this.guard != null) {
                this.guard.close();
            }
            nativeClose(this.nativeHandle);
            libcore.io.IoUtils.closeQuietly(this.fd);
            this.closed = true;
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.guard != null) {
                this.guard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }

    private java.io.InputStream getZipInputStream(java.util.zip.ZipEntry zipEntry) {
        if (zipEntry.getMethod() == 0) {
            return new android.util.jar.StrictJarFile.FDStream(this.fd, zipEntry.getDataOffset(), zipEntry.getDataOffset() + zipEntry.getSize());
        }
        return new android.util.jar.StrictJarFile.ZipInflaterInputStream(new android.util.jar.StrictJarFile.FDStream(this.fd, zipEntry.getDataOffset(), zipEntry.getDataOffset() + zipEntry.getCompressedSize()), new java.util.zip.Inflater(true), java.lang.Math.max(1024, (int) java.lang.Math.min(zipEntry.getSize(), 65535L)), zipEntry);
    }

    static final class EntryIterator implements java.util.Iterator<java.util.zip.ZipEntry> {
        private final long iterationHandle;
        private java.util.zip.ZipEntry nextEntry;

        EntryIterator(long j, java.lang.String str) throws java.io.IOException {
            this.iterationHandle = android.util.jar.StrictJarFile.nativeStartIteration(j, str);
        }

        @Override // java.util.Iterator
        public java.util.zip.ZipEntry next() {
            if (this.nextEntry != null) {
                java.util.zip.ZipEntry zipEntry = this.nextEntry;
                this.nextEntry = null;
                return zipEntry;
            }
            return android.util.jar.StrictJarFile.nativeNextEntry(this.iterationHandle);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.nextEntry != null) {
                return true;
            }
            java.util.zip.ZipEntry nativeNextEntry = android.util.jar.StrictJarFile.nativeNextEntry(this.iterationHandle);
            if (nativeNextEntry == null) {
                return false;
            }
            this.nextEntry = nativeNextEntry;
            return true;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    private java.util.HashMap<java.lang.String, byte[]> getMetaEntries() throws java.io.IOException {
        java.util.HashMap<java.lang.String, byte[]> hashMap = new java.util.HashMap<>();
        android.util.jar.StrictJarFile.EntryIterator entryIterator = new android.util.jar.StrictJarFile.EntryIterator(this.nativeHandle, "META-INF/");
        while (entryIterator.hasNext()) {
            java.util.zip.ZipEntry next = entryIterator.next();
            hashMap.put(next.getName(), libcore.io.Streams.readFully(getInputStream(next)));
        }
        return hashMap;
    }

    static final class JarFileInputStream extends java.io.FilterInputStream {
        private long count;
        private boolean done;
        private final android.util.jar.StrictJarVerifier.VerifierEntry entry;

        JarFileInputStream(java.io.InputStream inputStream, long j, android.util.jar.StrictJarVerifier.VerifierEntry verifierEntry) {
            super(inputStream);
            this.done = false;
            this.entry = verifierEntry;
            this.count = j;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read() throws java.io.IOException {
            if (this.done) {
                return -1;
            }
            if (this.count > 0) {
                int read = super.read();
                if (read != -1) {
                    this.entry.write(read);
                    this.count--;
                } else {
                    this.count = 0L;
                }
                if (this.count == 0) {
                    this.done = true;
                    this.entry.verify();
                }
                return read;
            }
            this.done = true;
            this.entry.verify();
            return -1;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
            int i3;
            if (this.done) {
                return -1;
            }
            if (this.count > 0) {
                int read = super.read(bArr, i, i2);
                if (read != -1) {
                    if (this.count >= read) {
                        i3 = read;
                    } else {
                        i3 = (int) this.count;
                    }
                    this.entry.write(bArr, i, i3);
                    this.count -= i3;
                } else {
                    this.count = 0L;
                }
                if (this.count == 0) {
                    this.done = true;
                    this.entry.verify();
                }
                return read;
            }
            this.done = true;
            this.entry.verify();
            return -1;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int available() throws java.io.IOException {
            if (this.done) {
                return 0;
            }
            return super.available();
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public long skip(long j) throws java.io.IOException {
            return libcore.io.Streams.skipByReading(this, j);
        }
    }

    public static class ZipInflaterInputStream extends java.util.zip.InflaterInputStream {
        private long bytesRead;
        private boolean closed;
        private final java.util.zip.ZipEntry entry;

        public ZipInflaterInputStream(java.io.InputStream inputStream, java.util.zip.Inflater inflater, int i, java.util.zip.ZipEntry zipEntry) {
            super(inputStream, inflater, i);
            this.bytesRead = 0L;
            this.entry = zipEntry;
        }

        @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
            try {
                int read = super.read(bArr, i, i2);
                if (read != -1) {
                    this.bytesRead += read;
                } else if (this.entry.getSize() != this.bytesRead) {
                    throw new java.io.IOException("Size mismatch on inflated file: " + this.bytesRead + " vs " + this.entry.getSize());
                }
                return read;
            } catch (java.io.IOException e) {
                throw new java.io.IOException("Error reading data for " + this.entry.getName() + " near offset " + this.bytesRead, e);
            }
        }

        @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream, java.io.InputStream
        public int available() throws java.io.IOException {
            if (this.closed || super.available() == 0) {
                return 0;
            }
            return (int) (this.entry.getSize() - this.bytesRead);
        }

        @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            super.close();
            this.closed = true;
        }
    }

    public static class FDStream extends java.io.InputStream {
        private long endOffset;
        private final java.io.FileDescriptor fd;
        private long offset;

        public FDStream(java.io.FileDescriptor fileDescriptor, long j, long j2) {
            this.fd = fileDescriptor;
            this.offset = j;
            this.endOffset = j2;
        }

        @Override // java.io.InputStream
        public int available() throws java.io.IOException {
            return this.offset < this.endOffset ? 1 : 0;
        }

        @Override // java.io.InputStream
        public int read() throws java.io.IOException {
            return libcore.io.Streams.readSingleByte(this);
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
            synchronized (this.fd) {
                long j = this.endOffset - this.offset;
                if (i2 > j) {
                    i2 = (int) j;
                }
                try {
                    android.system.Os.lseek(this.fd, this.offset, android.system.OsConstants.SEEK_SET);
                    int read = libcore.io.IoBridge.read(this.fd, bArr, i, i2);
                    if (read <= 0) {
                        return -1;
                    }
                    this.offset += read;
                    return read;
                } catch (android.system.ErrnoException e) {
                    throw new java.io.IOException(e);
                }
            }
        }

        @Override // java.io.InputStream
        public long skip(long j) throws java.io.IOException {
            if (j > this.endOffset - this.offset) {
                j = this.endOffset - this.offset;
            }
            this.offset += j;
            return j;
        }
    }
}
