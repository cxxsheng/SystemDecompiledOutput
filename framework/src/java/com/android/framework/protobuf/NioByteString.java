package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class NioByteString extends com.android.framework.protobuf.ByteString.LeafByteString {
    private final java.nio.ByteBuffer buffer;

    NioByteString(java.nio.ByteBuffer byteBuffer) {
        com.android.framework.protobuf.Internal.checkNotNull(byteBuffer, "buffer");
        this.buffer = byteBuffer.slice().order(java.nio.ByteOrder.nativeOrder());
    }

    private java.lang.Object writeReplace() {
        return com.android.framework.protobuf.ByteString.copyFrom(this.buffer.slice());
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException {
        throw new java.io.InvalidObjectException("NioByteString instances are not to be serialized directly");
    }

    @Override // com.android.framework.protobuf.ByteString
    public byte byteAt(int i) {
        try {
            return this.buffer.get(i);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw e;
        } catch (java.lang.IndexOutOfBoundsException e2) {
            throw new java.lang.ArrayIndexOutOfBoundsException(e2.getMessage());
        }
    }

    @Override // com.android.framework.protobuf.ByteString
    public byte internalByteAt(int i) {
        return byteAt(i);
    }

    @Override // com.android.framework.protobuf.ByteString
    public int size() {
        return this.buffer.remaining();
    }

    @Override // com.android.framework.protobuf.ByteString
    public com.android.framework.protobuf.ByteString substring(int i, int i2) {
        try {
            return new com.android.framework.protobuf.NioByteString(slice(i, i2));
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw e;
        } catch (java.lang.IndexOutOfBoundsException e2) {
            throw new java.lang.ArrayIndexOutOfBoundsException(e2.getMessage());
        }
    }

    @Override // com.android.framework.protobuf.ByteString
    protected void copyToInternal(byte[] bArr, int i, int i2, int i3) {
        java.nio.ByteBuffer slice = this.buffer.slice();
        slice.position(i);
        slice.get(bArr, i2, i3);
    }

    @Override // com.android.framework.protobuf.ByteString
    public void copyTo(java.nio.ByteBuffer byteBuffer) {
        byteBuffer.put(this.buffer.slice());
    }

    @Override // com.android.framework.protobuf.ByteString
    public void writeTo(java.io.OutputStream outputStream) throws java.io.IOException {
        outputStream.write(toByteArray());
    }

    @Override // com.android.framework.protobuf.ByteString.LeafByteString
    boolean equalsRange(com.android.framework.protobuf.ByteString byteString, int i, int i2) {
        return substring(0, i2).equals(byteString.substring(i, i2 + i));
    }

    @Override // com.android.framework.protobuf.ByteString
    void writeToInternal(java.io.OutputStream outputStream, int i, int i2) throws java.io.IOException {
        if (this.buffer.hasArray()) {
            outputStream.write(this.buffer.array(), this.buffer.arrayOffset() + this.buffer.position() + i, i2);
        } else {
            com.android.framework.protobuf.ByteBufferWriter.write(slice(i, i2 + i), outputStream);
        }
    }

    @Override // com.android.framework.protobuf.ByteString
    void writeTo(com.android.framework.protobuf.ByteOutput byteOutput) throws java.io.IOException {
        byteOutput.writeLazy(this.buffer.slice());
    }

    @Override // com.android.framework.protobuf.ByteString
    public java.nio.ByteBuffer asReadOnlyByteBuffer() {
        return this.buffer.asReadOnlyBuffer();
    }

    @Override // com.android.framework.protobuf.ByteString
    public java.util.List<java.nio.ByteBuffer> asReadOnlyByteBufferList() {
        return java.util.Collections.singletonList(asReadOnlyByteBuffer());
    }

    @Override // com.android.framework.protobuf.ByteString
    protected java.lang.String toStringInternal(java.nio.charset.Charset charset) {
        byte[] byteArray;
        int length;
        int i;
        if (this.buffer.hasArray()) {
            byteArray = this.buffer.array();
            i = this.buffer.arrayOffset() + this.buffer.position();
            length = this.buffer.remaining();
        } else {
            byteArray = toByteArray();
            length = byteArray.length;
            i = 0;
        }
        return new java.lang.String(byteArray, i, length, charset);
    }

    @Override // com.android.framework.protobuf.ByteString
    public boolean isValidUtf8() {
        return com.android.framework.protobuf.Utf8.isValidUtf8(this.buffer);
    }

    @Override // com.android.framework.protobuf.ByteString
    protected int partialIsValidUtf8(int i, int i2, int i3) {
        return com.android.framework.protobuf.Utf8.partialIsValidUtf8(i, this.buffer, i2, i3 + i2);
    }

    @Override // com.android.framework.protobuf.ByteString
    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.framework.protobuf.ByteString)) {
            return false;
        }
        com.android.framework.protobuf.ByteString byteString = (com.android.framework.protobuf.ByteString) obj;
        if (size() != byteString.size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (obj instanceof com.android.framework.protobuf.NioByteString) {
            return this.buffer.equals(((com.android.framework.protobuf.NioByteString) obj).buffer);
        }
        if (obj instanceof com.android.framework.protobuf.RopeByteString) {
            return obj.equals(this);
        }
        return this.buffer.equals(byteString.asReadOnlyByteBuffer());
    }

    @Override // com.android.framework.protobuf.ByteString
    protected int partialHash(int i, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            i = (i * 31) + this.buffer.get(i4);
        }
        return i;
    }

    @Override // com.android.framework.protobuf.ByteString
    public java.io.InputStream newInput() {
        return new java.io.InputStream() { // from class: com.android.framework.protobuf.NioByteString.1
            private final java.nio.ByteBuffer buf;

            {
                this.buf = com.android.framework.protobuf.NioByteString.this.buffer.slice();
            }

            @Override // java.io.InputStream
            public void mark(int i) {
                this.buf.mark();
            }

            @Override // java.io.InputStream
            public boolean markSupported() {
                return true;
            }

            @Override // java.io.InputStream
            public void reset() throws java.io.IOException {
                try {
                    this.buf.reset();
                } catch (java.nio.InvalidMarkException e) {
                    throw new java.io.IOException(e);
                }
            }

            @Override // java.io.InputStream
            public int available() throws java.io.IOException {
                return this.buf.remaining();
            }

            @Override // java.io.InputStream
            public int read() throws java.io.IOException {
                if (!this.buf.hasRemaining()) {
                    return -1;
                }
                return this.buf.get() & 255;
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
                if (!this.buf.hasRemaining()) {
                    return -1;
                }
                int min = java.lang.Math.min(i2, this.buf.remaining());
                this.buf.get(bArr, i, min);
                return min;
            }
        };
    }

    @Override // com.android.framework.protobuf.ByteString
    public com.android.framework.protobuf.CodedInputStream newCodedInput() {
        return com.android.framework.protobuf.CodedInputStream.newInstance(this.buffer, true);
    }

    private java.nio.ByteBuffer slice(int i, int i2) {
        if (i < this.buffer.position() || i2 > this.buffer.limit() || i > i2) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Invalid indices [%d, %d]", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
        }
        java.nio.ByteBuffer slice = this.buffer.slice();
        slice.position(i - this.buffer.position());
        slice.limit(i2 - this.buffer.position());
        return slice;
    }
}
