package android.util.jar;

/* loaded from: classes3.dex */
public class StrictJarManifest implements java.lang.Cloneable {
    static final int LINE_LENGTH_LIMIT = 72;
    private java.util.HashMap<java.lang.String, android.util.jar.StrictJarManifest.Chunk> chunks;
    private final java.util.HashMap<java.lang.String, java.util.jar.Attributes> entries;
    private final java.util.jar.Attributes mainAttributes;
    private int mainEnd;
    private static final byte[] LINE_SEPARATOR = {13, 10};
    private static final byte[] VALUE_SEPARATOR = {58, com.android.net.module.util.NetworkStackConstants.TCPHDR_URG};
    static final java.util.jar.Attributes.Name ATTRIBUTE_NAME_NAME = new java.util.jar.Attributes.Name("Name");

    static final class Chunk {
        final int end;
        final int start;

        Chunk(int i, int i2) {
            this.start = i;
            this.end = i2;
        }
    }

    public StrictJarManifest() {
        this.entries = new java.util.HashMap<>();
        this.mainAttributes = new java.util.jar.Attributes();
    }

    public StrictJarManifest(java.io.InputStream inputStream) throws java.io.IOException {
        this();
        read(libcore.io.Streams.readFully(inputStream));
    }

    public StrictJarManifest(android.util.jar.StrictJarManifest strictJarManifest) {
        this.mainAttributes = (java.util.jar.Attributes) strictJarManifest.mainAttributes.clone();
        this.entries = (java.util.HashMap) ((java.util.HashMap) strictJarManifest.getEntries()).clone();
    }

    StrictJarManifest(byte[] bArr, boolean z) throws java.io.IOException {
        this();
        if (z) {
            this.chunks = new java.util.HashMap<>();
        }
        read(bArr);
    }

    public void clear() {
        this.entries.clear();
        this.mainAttributes.clear();
    }

    public java.util.jar.Attributes getAttributes(java.lang.String str) {
        return getEntries().get(str);
    }

    public java.util.Map<java.lang.String, java.util.jar.Attributes> getEntries() {
        return this.entries;
    }

    public java.util.jar.Attributes getMainAttributes() {
        return this.mainAttributes;
    }

    public java.lang.Object clone() {
        return new android.util.jar.StrictJarManifest(this);
    }

    public void write(java.io.OutputStream outputStream) throws java.io.IOException {
        write(this, outputStream);
    }

    public void read(java.io.InputStream inputStream) throws java.io.IOException {
        read(libcore.io.Streams.readFullyNoClose(inputStream));
    }

    private void read(byte[] bArr) throws java.io.IOException {
        if (bArr.length == 0) {
            return;
        }
        android.util.jar.StrictJarManifestReader strictJarManifestReader = new android.util.jar.StrictJarManifestReader(bArr, this.mainAttributes);
        this.mainEnd = strictJarManifestReader.getEndOfMainSection();
        strictJarManifestReader.readEntries(this.entries, this.chunks);
    }

    public int hashCode() {
        return this.mainAttributes.hashCode() ^ getEntries().hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        android.util.jar.StrictJarManifest strictJarManifest = (android.util.jar.StrictJarManifest) obj;
        if (!this.mainAttributes.equals(strictJarManifest.mainAttributes)) {
            return false;
        }
        return getEntries().equals(strictJarManifest.getEntries());
    }

    android.util.jar.StrictJarManifest.Chunk getChunk(java.lang.String str) {
        return this.chunks.get(str);
    }

    void removeChunks() {
        this.chunks = null;
    }

    int getMainAttributesEnd() {
        return this.mainEnd;
    }

    static void write(android.util.jar.StrictJarManifest strictJarManifest, java.io.OutputStream outputStream) throws java.io.IOException {
        java.nio.charset.CharsetEncoder newEncoder = java.nio.charset.StandardCharsets.UTF_8.newEncoder();
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(72);
        java.util.jar.Attributes.Name name = java.util.jar.Attributes.Name.MANIFEST_VERSION;
        java.lang.String value = strictJarManifest.mainAttributes.getValue(name);
        if (value == null) {
            name = java.util.jar.Attributes.Name.SIGNATURE_VERSION;
            value = strictJarManifest.mainAttributes.getValue(name);
        }
        if (value != null) {
            writeEntry(outputStream, name, value, newEncoder, allocate);
            java.util.Iterator<java.lang.Object> it = strictJarManifest.mainAttributes.keySet().iterator();
            while (it.hasNext()) {
                java.util.jar.Attributes.Name name2 = (java.util.jar.Attributes.Name) it.next();
                if (!name2.equals(name)) {
                    writeEntry(outputStream, name2, strictJarManifest.mainAttributes.getValue(name2), newEncoder, allocate);
                }
            }
        }
        outputStream.write(LINE_SEPARATOR);
        for (java.lang.String str : strictJarManifest.getEntries().keySet()) {
            writeEntry(outputStream, ATTRIBUTE_NAME_NAME, str, newEncoder, allocate);
            java.util.jar.Attributes attributes = strictJarManifest.entries.get(str);
            java.util.Iterator<java.lang.Object> it2 = attributes.keySet().iterator();
            while (it2.hasNext()) {
                java.util.jar.Attributes.Name name3 = (java.util.jar.Attributes.Name) it2.next();
                writeEntry(outputStream, name3, attributes.getValue(name3), newEncoder, allocate);
            }
            outputStream.write(LINE_SEPARATOR);
        }
    }

    private static void writeEntry(java.io.OutputStream outputStream, java.util.jar.Attributes.Name name, java.lang.String str, java.nio.charset.CharsetEncoder charsetEncoder, java.nio.ByteBuffer byteBuffer) throws java.io.IOException {
        outputStream.write(name.toString().getBytes(java.nio.charset.StandardCharsets.US_ASCII));
        outputStream.write(VALUE_SEPARATOR);
        charsetEncoder.reset();
        byteBuffer.clear().limit((72 - r4.length()) - 2);
        java.nio.CharBuffer wrap = java.nio.CharBuffer.wrap(str);
        while (true) {
            java.nio.charset.CoderResult encode = charsetEncoder.encode(wrap, byteBuffer, true);
            if (java.nio.charset.CoderResult.UNDERFLOW == encode) {
                encode = charsetEncoder.flush(byteBuffer);
            }
            outputStream.write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.position());
            outputStream.write(LINE_SEPARATOR);
            if (java.nio.charset.CoderResult.UNDERFLOW != encode) {
                outputStream.write(32);
                byteBuffer.clear().limit(71);
            } else {
                return;
            }
        }
    }
}
