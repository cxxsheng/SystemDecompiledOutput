package android.util.jar;

/* loaded from: classes3.dex */
class StrictJarManifestReader {
    private final byte[] buf;
    private final int endOfMainSection;
    private java.util.jar.Attributes.Name name;
    private int pos;
    private java.lang.String value;
    private final java.util.HashMap<java.lang.String, java.util.jar.Attributes.Name> attributeNameCache = new java.util.HashMap<>();
    private final java.io.ByteArrayOutputStream valueBuffer = new java.io.ByteArrayOutputStream(80);
    private int consecutiveLineBreaks = 0;

    public StrictJarManifestReader(byte[] bArr, java.util.jar.Attributes attributes) throws java.io.IOException {
        this.buf = bArr;
        while (readHeader()) {
            attributes.put(this.name, this.value);
        }
        this.endOfMainSection = this.pos;
    }

    public void readEntries(java.util.Map<java.lang.String, java.util.jar.Attributes> map, java.util.Map<java.lang.String, android.util.jar.StrictJarManifest.Chunk> map2) throws java.io.IOException {
        int i = this.pos;
        while (readHeader()) {
            if (!android.util.jar.StrictJarManifest.ATTRIBUTE_NAME_NAME.equals(this.name)) {
                throw new java.io.IOException("Entry is not named");
            }
            java.lang.String str = this.value;
            java.util.jar.Attributes attributes = map.get(str);
            if (attributes == null) {
                attributes = new java.util.jar.Attributes(12);
            }
            while (readHeader()) {
                attributes.put(this.name, this.value);
            }
            if (map2 != null) {
                if (map2.get(str) != null) {
                    throw new java.io.IOException("A jar verifier does not support more than one entry with the same name");
                }
                map2.put(str, new android.util.jar.StrictJarManifest.Chunk(i, this.pos));
                i = this.pos;
            }
            map.put(str, attributes);
        }
    }

    public int getEndOfMainSection() {
        return this.endOfMainSection;
    }

    private boolean readHeader() throws java.io.IOException {
        if (this.consecutiveLineBreaks > 1) {
            this.consecutiveLineBreaks = 0;
            return false;
        }
        readName();
        this.consecutiveLineBreaks = 0;
        readValue();
        return this.consecutiveLineBreaks > 0;
    }

    private void readName() throws java.io.IOException {
        int i = this.pos;
        while (this.pos < this.buf.length) {
            byte[] bArr = this.buf;
            int i2 = this.pos;
            this.pos = i2 + 1;
            if (bArr[i2] == 58) {
                java.lang.String str = new java.lang.String(this.buf, i, (this.pos - i) - 1, java.nio.charset.StandardCharsets.US_ASCII);
                byte[] bArr2 = this.buf;
                int i3 = this.pos;
                this.pos = i3 + 1;
                if (bArr2[i3] != 32) {
                    throw new java.io.IOException(java.lang.String.format("Invalid value for attribute '%s'", str));
                }
                try {
                    this.name = this.attributeNameCache.get(str);
                    if (this.name == null) {
                        this.name = new java.util.jar.Attributes.Name(str);
                        this.attributeNameCache.put(str, this.name);
                        return;
                    }
                    return;
                } catch (java.lang.IllegalArgumentException e) {
                    throw new java.io.IOException(e.getMessage());
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x005b, code lost:
    
        r7.valueBuffer.write(r7.buf, r0, r1 - r0);
        r7.value = r7.valueBuffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0071, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readValue() throws java.io.IOException {
        int i = this.pos;
        int i2 = this.pos;
        this.valueBuffer.reset();
        boolean z = false;
        while (true) {
            if (this.pos < this.buf.length) {
                byte[] bArr = this.buf;
                int i3 = this.pos;
                this.pos = i3 + 1;
                switch (bArr[i3]) {
                    case 0:
                        throw new java.io.IOException("NUL character in a manifest");
                    case 10:
                        if (!z) {
                            this.consecutiveLineBreaks++;
                            continue;
                        } else {
                            z = false;
                        }
                    case 13:
                        this.consecutiveLineBreaks++;
                        z = true;
                        continue;
                    case 32:
                        if (this.consecutiveLineBreaks != 1) {
                            break;
                        } else {
                            this.valueBuffer.write(this.buf, i, i2 - i);
                            i = this.pos;
                            this.consecutiveLineBreaks = 0;
                        }
                }
                if (this.consecutiveLineBreaks >= 1) {
                    this.pos--;
                } else {
                    i2 = this.pos;
                }
            }
        }
    }
}
