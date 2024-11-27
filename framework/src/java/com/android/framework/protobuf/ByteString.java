package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
public abstract class ByteString implements java.lang.Iterable<java.lang.Byte>, java.io.Serializable {
    static final int CONCATENATE_BY_COPY_SIZE = 128;
    public static final com.android.framework.protobuf.ByteString EMPTY = new com.android.framework.protobuf.ByteString.LiteralByteString(com.android.framework.protobuf.Internal.EMPTY_BYTE_ARRAY);
    static final int MAX_READ_FROM_CHUNK_SIZE = 8192;
    static final int MIN_READ_FROM_CHUNK_SIZE = 256;
    private static final int UNSIGNED_BYTE_MASK = 255;
    private static final java.util.Comparator<com.android.framework.protobuf.ByteString> UNSIGNED_LEXICOGRAPHICAL_COMPARATOR;
    private static final com.android.framework.protobuf.ByteString.ByteArrayCopier byteArrayCopier;
    private int hash = 0;

    private interface ByteArrayCopier {
        byte[] copyFrom(byte[] bArr, int i, int i2);
    }

    public interface ByteIterator extends java.util.Iterator<java.lang.Byte> {
        byte nextByte();
    }

    public abstract java.nio.ByteBuffer asReadOnlyByteBuffer();

    public abstract java.util.List<java.nio.ByteBuffer> asReadOnlyByteBufferList();

    public abstract byte byteAt(int i);

    public abstract void copyTo(java.nio.ByteBuffer byteBuffer);

    protected abstract void copyToInternal(byte[] bArr, int i, int i2, int i3);

    public abstract boolean equals(java.lang.Object obj);

    protected abstract int getTreeDepth();

    abstract byte internalByteAt(int i);

    protected abstract boolean isBalanced();

    public abstract boolean isValidUtf8();

    public abstract com.android.framework.protobuf.CodedInputStream newCodedInput();

    public abstract java.io.InputStream newInput();

    protected abstract int partialHash(int i, int i2, int i3);

    protected abstract int partialIsValidUtf8(int i, int i2, int i3);

    public abstract int size();

    public abstract com.android.framework.protobuf.ByteString substring(int i, int i2);

    protected abstract java.lang.String toStringInternal(java.nio.charset.Charset charset);

    abstract void writeTo(com.android.framework.protobuf.ByteOutput byteOutput) throws java.io.IOException;

    public abstract void writeTo(java.io.OutputStream outputStream) throws java.io.IOException;

    abstract void writeToInternal(java.io.OutputStream outputStream, int i, int i2) throws java.io.IOException;

    abstract void writeToReverse(com.android.framework.protobuf.ByteOutput byteOutput) throws java.io.IOException;

    static {
        byteArrayCopier = com.android.framework.protobuf.Android.isOnAndroidDevice() ? new com.android.framework.protobuf.ByteString.SystemByteArrayCopier() : new com.android.framework.protobuf.ByteString.ArraysByteArrayCopier();
        UNSIGNED_LEXICOGRAPHICAL_COMPARATOR = new java.util.Comparator<com.android.framework.protobuf.ByteString>() { // from class: com.android.framework.protobuf.ByteString.2
            /* JADX WARN: Type inference failed for: r0v0, types: [com.android.framework.protobuf.ByteString$ByteIterator] */
            /* JADX WARN: Type inference failed for: r1v0, types: [com.android.framework.protobuf.ByteString$ByteIterator] */
            @Override // java.util.Comparator
            public int compare(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ByteString byteString2) {
                ?? iterator2 = byteString.iterator2();
                ?? iterator22 = byteString2.iterator2();
                while (iterator2.hasNext() && iterator22.hasNext()) {
                    int compareTo = java.lang.Integer.valueOf(com.android.framework.protobuf.ByteString.toInt(iterator2.nextByte())).compareTo(java.lang.Integer.valueOf(com.android.framework.protobuf.ByteString.toInt(iterator22.nextByte())));
                    if (compareTo != 0) {
                        return compareTo;
                    }
                }
                return java.lang.Integer.valueOf(byteString.size()).compareTo(java.lang.Integer.valueOf(byteString2.size()));
            }
        };
    }

    private static final class SystemByteArrayCopier implements com.android.framework.protobuf.ByteString.ByteArrayCopier {
        private SystemByteArrayCopier() {
        }

        @Override // com.android.framework.protobuf.ByteString.ByteArrayCopier
        public byte[] copyFrom(byte[] bArr, int i, int i2) {
            byte[] bArr2 = new byte[i2];
            java.lang.System.arraycopy(bArr, i, bArr2, 0, i2);
            return bArr2;
        }
    }

    private static final class ArraysByteArrayCopier implements com.android.framework.protobuf.ByteString.ByteArrayCopier {
        private ArraysByteArrayCopier() {
        }

        @Override // com.android.framework.protobuf.ByteString.ByteArrayCopier
        public byte[] copyFrom(byte[] bArr, int i, int i2) {
            return java.util.Arrays.copyOfRange(bArr, i, i2 + i);
        }
    }

    ByteString() {
    }

    @Override // java.lang.Iterable
    /* renamed from: iterator, reason: merged with bridge method [inline-methods] */
    public java.util.Iterator<java.lang.Byte> iterator2() {
        return new com.android.framework.protobuf.ByteString.AbstractByteIterator() { // from class: com.android.framework.protobuf.ByteString.1
            private final int limit;
            private int position = 0;

            {
                this.limit = com.android.framework.protobuf.ByteString.this.size();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.position < this.limit;
            }

            @Override // com.android.framework.protobuf.ByteString.ByteIterator
            public byte nextByte() {
                int i = this.position;
                if (i >= this.limit) {
                    throw new java.util.NoSuchElementException();
                }
                this.position = i + 1;
                return com.android.framework.protobuf.ByteString.this.internalByteAt(i);
            }
        };
    }

    static abstract class AbstractByteIterator implements com.android.framework.protobuf.ByteString.ByteIterator {
        AbstractByteIterator() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public final java.lang.Byte next() {
            return java.lang.Byte.valueOf(nextByte());
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public final boolean isEmpty() {
        return size() == 0;
    }

    public static final com.android.framework.protobuf.ByteString empty() {
        return EMPTY;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int toInt(byte b) {
        return b & 255;
    }

    private static int hexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return (c - android.text.format.DateFormat.CAPITAL_AM_PM) + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return (c - android.text.format.DateFormat.AM_PM) + 10;
        }
        return -1;
    }

    private static int extractHexDigit(java.lang.String str, int i) {
        int hexDigit = hexDigit(str.charAt(i));
        if (hexDigit == -1) {
            throw new java.lang.NumberFormatException("Invalid hexString " + str + " must only contain [0-9a-fA-F] but contained " + str.charAt(i) + " at index " + i);
        }
        return hexDigit;
    }

    public static java.util.Comparator<com.android.framework.protobuf.ByteString> unsignedLexicographicalComparator() {
        return UNSIGNED_LEXICOGRAPHICAL_COMPARATOR;
    }

    public final com.android.framework.protobuf.ByteString substring(int i) {
        return substring(i, size());
    }

    public final boolean startsWith(com.android.framework.protobuf.ByteString byteString) {
        return size() >= byteString.size() && substring(0, byteString.size()).equals(byteString);
    }

    public final boolean endsWith(com.android.framework.protobuf.ByteString byteString) {
        return size() >= byteString.size() && substring(size() - byteString.size()).equals(byteString);
    }

    public static com.android.framework.protobuf.ByteString fromHex(java.lang.String str) {
        if (str.length() % 2 != 0) {
            throw new java.lang.NumberFormatException("Invalid hexString " + str + " of length " + str.length() + " must be even.");
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (extractHexDigit(str, i2 + 1) | (extractHexDigit(str, i2) << 4));
        }
        return new com.android.framework.protobuf.ByteString.LiteralByteString(bArr);
    }

    public static com.android.framework.protobuf.ByteString copyFrom(byte[] bArr, int i, int i2) {
        checkRange(i, i + i2, bArr.length);
        return new com.android.framework.protobuf.ByteString.LiteralByteString(byteArrayCopier.copyFrom(bArr, i, i2));
    }

    public static com.android.framework.protobuf.ByteString copyFrom(byte[] bArr) {
        return copyFrom(bArr, 0, bArr.length);
    }

    static com.android.framework.protobuf.ByteString wrap(java.nio.ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return wrap(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
        }
        return new com.android.framework.protobuf.NioByteString(byteBuffer);
    }

    static com.android.framework.protobuf.ByteString wrap(byte[] bArr) {
        return new com.android.framework.protobuf.ByteString.LiteralByteString(bArr);
    }

    static com.android.framework.protobuf.ByteString wrap(byte[] bArr, int i, int i2) {
        return new com.android.framework.protobuf.ByteString.BoundedByteString(bArr, i, i2);
    }

    public static com.android.framework.protobuf.ByteString copyFrom(java.nio.ByteBuffer byteBuffer, int i) {
        checkRange(0, i, byteBuffer.remaining());
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return new com.android.framework.protobuf.ByteString.LiteralByteString(bArr);
    }

    public static com.android.framework.protobuf.ByteString copyFrom(java.nio.ByteBuffer byteBuffer) {
        return copyFrom(byteBuffer, byteBuffer.remaining());
    }

    public static com.android.framework.protobuf.ByteString copyFrom(java.lang.String str, java.lang.String str2) throws java.io.UnsupportedEncodingException {
        return new com.android.framework.protobuf.ByteString.LiteralByteString(str.getBytes(str2));
    }

    public static com.android.framework.protobuf.ByteString copyFrom(java.lang.String str, java.nio.charset.Charset charset) {
        return new com.android.framework.protobuf.ByteString.LiteralByteString(str.getBytes(charset));
    }

    public static com.android.framework.protobuf.ByteString copyFromUtf8(java.lang.String str) {
        return new com.android.framework.protobuf.ByteString.LiteralByteString(str.getBytes(com.android.framework.protobuf.Internal.UTF_8));
    }

    public static com.android.framework.protobuf.ByteString readFrom(java.io.InputStream inputStream) throws java.io.IOException {
        return readFrom(inputStream, 256, 8192);
    }

    public static com.android.framework.protobuf.ByteString readFrom(java.io.InputStream inputStream, int i) throws java.io.IOException {
        return readFrom(inputStream, i, i);
    }

    public static com.android.framework.protobuf.ByteString readFrom(java.io.InputStream inputStream, int i, int i2) throws java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (true) {
            com.android.framework.protobuf.ByteString readChunk = readChunk(inputStream, i);
            if (readChunk != null) {
                arrayList.add(readChunk);
                i = java.lang.Math.min(i * 2, i2);
            } else {
                return copyFrom(arrayList);
            }
        }
    }

    private static com.android.framework.protobuf.ByteString readChunk(java.io.InputStream inputStream, int i) throws java.io.IOException {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int read = inputStream.read(bArr, i2, i - i2);
            if (read == -1) {
                break;
            }
            i2 += read;
        }
        if (i2 == 0) {
            return null;
        }
        return copyFrom(bArr, 0, i2);
    }

    public final com.android.framework.protobuf.ByteString concat(com.android.framework.protobuf.ByteString byteString) {
        if (Integer.MAX_VALUE - size() < byteString.size()) {
            throw new java.lang.IllegalArgumentException("ByteString would be too long: " + size() + "+" + byteString.size());
        }
        return com.android.framework.protobuf.RopeByteString.concatenate(this, byteString);
    }

    public static com.android.framework.protobuf.ByteString copyFrom(java.lang.Iterable<com.android.framework.protobuf.ByteString> iterable) {
        int size;
        if (!(iterable instanceof java.util.Collection)) {
            java.util.Iterator<com.android.framework.protobuf.ByteString> it = iterable.iterator();
            size = 0;
            while (it.hasNext()) {
                it.next();
                size++;
            }
        } else {
            size = ((java.util.Collection) iterable).size();
        }
        if (size == 0) {
            return EMPTY;
        }
        return balancedConcat(iterable.iterator(), size);
    }

    private static com.android.framework.protobuf.ByteString balancedConcat(java.util.Iterator<com.android.framework.protobuf.ByteString> it, int i) {
        if (i < 1) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("length (%s) must be >= 1", java.lang.Integer.valueOf(i)));
        }
        if (i == 1) {
            return it.next();
        }
        int i2 = i >>> 1;
        return balancedConcat(it, i2).concat(balancedConcat(it, i - i2));
    }

    public void copyTo(byte[] bArr, int i) {
        copyTo(bArr, 0, i, size());
    }

    @java.lang.Deprecated
    public final void copyTo(byte[] bArr, int i, int i2, int i3) {
        checkRange(i, i + i3, size());
        checkRange(i2, i2 + i3, bArr.length);
        if (i3 > 0) {
            copyToInternal(bArr, i, i2, i3);
        }
    }

    public final byte[] toByteArray() {
        int size = size();
        if (size == 0) {
            return com.android.framework.protobuf.Internal.EMPTY_BYTE_ARRAY;
        }
        byte[] bArr = new byte[size];
        copyToInternal(bArr, 0, 0, size);
        return bArr;
    }

    final void writeTo(java.io.OutputStream outputStream, int i, int i2) throws java.io.IOException {
        checkRange(i, i + i2, size());
        if (i2 > 0) {
            writeToInternal(outputStream, i, i2);
        }
    }

    public final java.lang.String toString(java.lang.String str) throws java.io.UnsupportedEncodingException {
        try {
            return toString(java.nio.charset.Charset.forName(str));
        } catch (java.nio.charset.UnsupportedCharsetException e) {
            java.io.UnsupportedEncodingException unsupportedEncodingException = new java.io.UnsupportedEncodingException(str);
            unsupportedEncodingException.initCause(e);
            throw unsupportedEncodingException;
        }
    }

    public final java.lang.String toString(java.nio.charset.Charset charset) {
        return size() == 0 ? "" : toStringInternal(charset);
    }

    public final java.lang.String toStringUtf8() {
        return toString(com.android.framework.protobuf.Internal.UTF_8);
    }

    static abstract class LeafByteString extends com.android.framework.protobuf.ByteString {
        abstract boolean equalsRange(com.android.framework.protobuf.ByteString byteString, int i, int i2);

        LeafByteString() {
        }

        @Override // com.android.framework.protobuf.ByteString
        protected final int getTreeDepth() {
            return 0;
        }

        @Override // com.android.framework.protobuf.ByteString
        protected final boolean isBalanced() {
            return true;
        }

        @Override // com.android.framework.protobuf.ByteString
        void writeToReverse(com.android.framework.protobuf.ByteOutput byteOutput) throws java.io.IOException {
            writeTo(byteOutput);
        }
    }

    public final int hashCode() {
        int i = this.hash;
        if (i == 0) {
            int size = size();
            i = partialHash(size, 0, size);
            if (i == 0) {
                i = 1;
            }
            this.hash = i;
        }
        return i;
    }

    public static com.android.framework.protobuf.ByteString.Output newOutput(int i) {
        return new com.android.framework.protobuf.ByteString.Output(i);
    }

    public static com.android.framework.protobuf.ByteString.Output newOutput() {
        return new com.android.framework.protobuf.ByteString.Output(128);
    }

    public static final class Output extends java.io.OutputStream {
        private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
        private byte[] buffer;
        private int bufferPos;
        private final java.util.ArrayList<com.android.framework.protobuf.ByteString> flushedBuffers;
        private int flushedBuffersTotalBytes;
        private final int initialCapacity;

        Output(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("Buffer size < 0");
            }
            this.initialCapacity = i;
            this.flushedBuffers = new java.util.ArrayList<>();
            this.buffer = new byte[i];
        }

        @Override // java.io.OutputStream
        public synchronized void write(int i) {
            if (this.bufferPos == this.buffer.length) {
                flushFullBuffer(1);
            }
            byte[] bArr = this.buffer;
            int i2 = this.bufferPos;
            this.bufferPos = i2 + 1;
            bArr[i2] = (byte) i;
        }

        @Override // java.io.OutputStream
        public synchronized void write(byte[] bArr, int i, int i2) {
            if (i2 <= this.buffer.length - this.bufferPos) {
                java.lang.System.arraycopy(bArr, i, this.buffer, this.bufferPos, i2);
                this.bufferPos += i2;
            } else {
                int length = this.buffer.length - this.bufferPos;
                java.lang.System.arraycopy(bArr, i, this.buffer, this.bufferPos, length);
                int i3 = i2 - length;
                flushFullBuffer(i3);
                java.lang.System.arraycopy(bArr, i + length, this.buffer, 0, i3);
                this.bufferPos = i3;
            }
        }

        public synchronized com.android.framework.protobuf.ByteString toByteString() {
            flushLastBuffer();
            return com.android.framework.protobuf.ByteString.copyFrom(this.flushedBuffers);
        }

        private byte[] copyArray(byte[] bArr, int i) {
            byte[] bArr2 = new byte[i];
            java.lang.System.arraycopy(bArr, 0, bArr2, 0, java.lang.Math.min(bArr.length, i));
            return bArr2;
        }

        public void writeTo(java.io.OutputStream outputStream) throws java.io.IOException {
            com.android.framework.protobuf.ByteString[] byteStringArr;
            byte[] bArr;
            int i;
            synchronized (this) {
                byteStringArr = (com.android.framework.protobuf.ByteString[]) this.flushedBuffers.toArray(new com.android.framework.protobuf.ByteString[this.flushedBuffers.size()]);
                bArr = this.buffer;
                i = this.bufferPos;
            }
            for (com.android.framework.protobuf.ByteString byteString : byteStringArr) {
                byteString.writeTo(outputStream);
            }
            outputStream.write(copyArray(bArr, i));
        }

        public synchronized int size() {
            return this.flushedBuffersTotalBytes + this.bufferPos;
        }

        public synchronized void reset() {
            this.flushedBuffers.clear();
            this.flushedBuffersTotalBytes = 0;
            this.bufferPos = 0;
        }

        public java.lang.String toString() {
            return java.lang.String.format("<ByteString.Output@%s size=%d>", java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)), java.lang.Integer.valueOf(size()));
        }

        private void flushFullBuffer(int i) {
            this.flushedBuffers.add(new com.android.framework.protobuf.ByteString.LiteralByteString(this.buffer));
            this.flushedBuffersTotalBytes += this.buffer.length;
            this.buffer = new byte[java.lang.Math.max(this.initialCapacity, java.lang.Math.max(i, this.flushedBuffersTotalBytes >>> 1))];
            this.bufferPos = 0;
        }

        private void flushLastBuffer() {
            if (this.bufferPos < this.buffer.length) {
                if (this.bufferPos > 0) {
                    this.flushedBuffers.add(new com.android.framework.protobuf.ByteString.LiteralByteString(copyArray(this.buffer, this.bufferPos)));
                }
            } else {
                this.flushedBuffers.add(new com.android.framework.protobuf.ByteString.LiteralByteString(this.buffer));
                this.buffer = EMPTY_BYTE_ARRAY;
            }
            this.flushedBuffersTotalBytes += this.bufferPos;
            this.bufferPos = 0;
        }
    }

    static com.android.framework.protobuf.ByteString.CodedBuilder newCodedBuilder(int i) {
        return new com.android.framework.protobuf.ByteString.CodedBuilder(i);
    }

    static final class CodedBuilder {
        private final byte[] buffer;
        private final com.android.framework.protobuf.CodedOutputStream output;

        private CodedBuilder(int i) {
            this.buffer = new byte[i];
            this.output = com.android.framework.protobuf.CodedOutputStream.newInstance(this.buffer);
        }

        public com.android.framework.protobuf.ByteString build() {
            this.output.checkNoSpaceLeft();
            return new com.android.framework.protobuf.ByteString.LiteralByteString(this.buffer);
        }

        public com.android.framework.protobuf.CodedOutputStream getCodedOutput() {
            return this.output;
        }
    }

    protected final int peekCachedHashCode() {
        return this.hash;
    }

    static void checkIndex(int i, int i2) {
        if (((i2 - (i + 1)) | i) < 0) {
            if (i < 0) {
                throw new java.lang.ArrayIndexOutOfBoundsException("Index < 0: " + i);
            }
            throw new java.lang.ArrayIndexOutOfBoundsException("Index > length: " + i + ", " + i2);
        }
    }

    static int checkRange(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) < 0) {
            if (i < 0) {
                throw new java.lang.IndexOutOfBoundsException("Beginning index: " + i + " < 0");
            }
            if (i2 < i) {
                throw new java.lang.IndexOutOfBoundsException("Beginning index larger than ending index: " + i + ", " + i2);
            }
            throw new java.lang.IndexOutOfBoundsException("End index: " + i2 + " >= " + i3);
        }
        return i4;
    }

    public final java.lang.String toString() {
        return java.lang.String.format(java.util.Locale.ROOT, "<ByteString@%s size=%d contents=\"%s\">", java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)), java.lang.Integer.valueOf(size()), truncateAndEscapeForDisplay());
    }

    private java.lang.String truncateAndEscapeForDisplay() {
        return size() <= 50 ? com.android.framework.protobuf.TextFormatEscaper.escapeBytes(this) : com.android.framework.protobuf.TextFormatEscaper.escapeBytes(substring(0, 47)) + android.telecom.Logging.Session.TRUNCATE_STRING;
    }

    private static class LiteralByteString extends com.android.framework.protobuf.ByteString.LeafByteString {
        private static final long serialVersionUID = 1;
        protected final byte[] bytes;

        LiteralByteString(byte[] bArr) {
            if (bArr == null) {
                throw new java.lang.NullPointerException();
            }
            this.bytes = bArr;
        }

        @Override // com.android.framework.protobuf.ByteString
        public byte byteAt(int i) {
            return this.bytes[i];
        }

        @Override // com.android.framework.protobuf.ByteString
        byte internalByteAt(int i) {
            return this.bytes[i];
        }

        @Override // com.android.framework.protobuf.ByteString
        public int size() {
            return this.bytes.length;
        }

        @Override // com.android.framework.protobuf.ByteString
        public final com.android.framework.protobuf.ByteString substring(int i, int i2) {
            int checkRange = checkRange(i, i2, size());
            if (checkRange == 0) {
                return com.android.framework.protobuf.ByteString.EMPTY;
            }
            return new com.android.framework.protobuf.ByteString.BoundedByteString(this.bytes, getOffsetIntoBytes() + i, checkRange);
        }

        @Override // com.android.framework.protobuf.ByteString
        protected void copyToInternal(byte[] bArr, int i, int i2, int i3) {
            java.lang.System.arraycopy(this.bytes, i, bArr, i2, i3);
        }

        @Override // com.android.framework.protobuf.ByteString
        public final void copyTo(java.nio.ByteBuffer byteBuffer) {
            byteBuffer.put(this.bytes, getOffsetIntoBytes(), size());
        }

        @Override // com.android.framework.protobuf.ByteString
        public final java.nio.ByteBuffer asReadOnlyByteBuffer() {
            return java.nio.ByteBuffer.wrap(this.bytes, getOffsetIntoBytes(), size()).asReadOnlyBuffer();
        }

        @Override // com.android.framework.protobuf.ByteString
        public final java.util.List<java.nio.ByteBuffer> asReadOnlyByteBufferList() {
            return java.util.Collections.singletonList(asReadOnlyByteBuffer());
        }

        @Override // com.android.framework.protobuf.ByteString
        public final void writeTo(java.io.OutputStream outputStream) throws java.io.IOException {
            outputStream.write(toByteArray());
        }

        @Override // com.android.framework.protobuf.ByteString
        final void writeToInternal(java.io.OutputStream outputStream, int i, int i2) throws java.io.IOException {
            outputStream.write(this.bytes, getOffsetIntoBytes() + i, i2);
        }

        @Override // com.android.framework.protobuf.ByteString
        final void writeTo(com.android.framework.protobuf.ByteOutput byteOutput) throws java.io.IOException {
            byteOutput.writeLazy(this.bytes, getOffsetIntoBytes(), size());
        }

        @Override // com.android.framework.protobuf.ByteString
        protected final java.lang.String toStringInternal(java.nio.charset.Charset charset) {
            return new java.lang.String(this.bytes, getOffsetIntoBytes(), size(), charset);
        }

        @Override // com.android.framework.protobuf.ByteString
        public final boolean isValidUtf8() {
            int offsetIntoBytes = getOffsetIntoBytes();
            return com.android.framework.protobuf.Utf8.isValidUtf8(this.bytes, offsetIntoBytes, size() + offsetIntoBytes);
        }

        @Override // com.android.framework.protobuf.ByteString
        protected final int partialIsValidUtf8(int i, int i2, int i3) {
            int offsetIntoBytes = getOffsetIntoBytes() + i2;
            return com.android.framework.protobuf.Utf8.partialIsValidUtf8(i, this.bytes, offsetIntoBytes, i3 + offsetIntoBytes);
        }

        @Override // com.android.framework.protobuf.ByteString
        public final boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof com.android.framework.protobuf.ByteString) || size() != ((com.android.framework.protobuf.ByteString) obj).size()) {
                return false;
            }
            if (size() == 0) {
                return true;
            }
            if (obj instanceof com.android.framework.protobuf.ByteString.LiteralByteString) {
                com.android.framework.protobuf.ByteString.LiteralByteString literalByteString = (com.android.framework.protobuf.ByteString.LiteralByteString) obj;
                int peekCachedHashCode = peekCachedHashCode();
                int peekCachedHashCode2 = literalByteString.peekCachedHashCode();
                if (peekCachedHashCode == 0 || peekCachedHashCode2 == 0 || peekCachedHashCode == peekCachedHashCode2) {
                    return equalsRange(literalByteString, 0, size());
                }
                return false;
            }
            return obj.equals(this);
        }

        @Override // com.android.framework.protobuf.ByteString.LeafByteString
        final boolean equalsRange(com.android.framework.protobuf.ByteString byteString, int i, int i2) {
            if (i2 > byteString.size()) {
                throw new java.lang.IllegalArgumentException("Length too large: " + i2 + size());
            }
            int i3 = i + i2;
            if (i3 > byteString.size()) {
                throw new java.lang.IllegalArgumentException("Ran off end of other: " + i + ", " + i2 + ", " + byteString.size());
            }
            if (byteString instanceof com.android.framework.protobuf.ByteString.LiteralByteString) {
                com.android.framework.protobuf.ByteString.LiteralByteString literalByteString = (com.android.framework.protobuf.ByteString.LiteralByteString) byteString;
                byte[] bArr = this.bytes;
                byte[] bArr2 = literalByteString.bytes;
                int offsetIntoBytes = getOffsetIntoBytes() + i2;
                int offsetIntoBytes2 = getOffsetIntoBytes();
                int offsetIntoBytes3 = literalByteString.getOffsetIntoBytes() + i;
                while (offsetIntoBytes2 < offsetIntoBytes) {
                    if (bArr[offsetIntoBytes2] != bArr2[offsetIntoBytes3]) {
                        return false;
                    }
                    offsetIntoBytes2++;
                    offsetIntoBytes3++;
                }
                return true;
            }
            return byteString.substring(i, i3).equals(substring(0, i2));
        }

        @Override // com.android.framework.protobuf.ByteString
        protected final int partialHash(int i, int i2, int i3) {
            return com.android.framework.protobuf.Internal.partialHash(i, this.bytes, getOffsetIntoBytes() + i2, i3);
        }

        @Override // com.android.framework.protobuf.ByteString
        public final java.io.InputStream newInput() {
            return new java.io.ByteArrayInputStream(this.bytes, getOffsetIntoBytes(), size());
        }

        @Override // com.android.framework.protobuf.ByteString
        public final com.android.framework.protobuf.CodedInputStream newCodedInput() {
            return com.android.framework.protobuf.CodedInputStream.newInstance(this.bytes, getOffsetIntoBytes(), size(), true);
        }

        protected int getOffsetIntoBytes() {
            return 0;
        }
    }

    private static final class BoundedByteString extends com.android.framework.protobuf.ByteString.LiteralByteString {
        private static final long serialVersionUID = 1;
        private final int bytesLength;
        private final int bytesOffset;

        BoundedByteString(byte[] bArr, int i, int i2) {
            super(bArr);
            checkRange(i, i + i2, bArr.length);
            this.bytesOffset = i;
            this.bytesLength = i2;
        }

        @Override // com.android.framework.protobuf.ByteString.LiteralByteString, com.android.framework.protobuf.ByteString
        public byte byteAt(int i) {
            checkIndex(i, size());
            return this.bytes[this.bytesOffset + i];
        }

        @Override // com.android.framework.protobuf.ByteString.LiteralByteString, com.android.framework.protobuf.ByteString
        byte internalByteAt(int i) {
            return this.bytes[this.bytesOffset + i];
        }

        @Override // com.android.framework.protobuf.ByteString.LiteralByteString, com.android.framework.protobuf.ByteString
        public int size() {
            return this.bytesLength;
        }

        @Override // com.android.framework.protobuf.ByteString.LiteralByteString
        protected int getOffsetIntoBytes() {
            return this.bytesOffset;
        }

        @Override // com.android.framework.protobuf.ByteString.LiteralByteString, com.android.framework.protobuf.ByteString
        protected void copyToInternal(byte[] bArr, int i, int i2, int i3) {
            java.lang.System.arraycopy(this.bytes, getOffsetIntoBytes() + i, bArr, i2, i3);
        }

        java.lang.Object writeReplace() {
            return com.android.framework.protobuf.ByteString.wrap(toByteArray());
        }

        private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException {
            throw new java.io.InvalidObjectException("BoundedByteStream instances are not to be serialized directly");
        }
    }
}
