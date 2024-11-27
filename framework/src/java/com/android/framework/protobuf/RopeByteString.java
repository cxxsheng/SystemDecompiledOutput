package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class RopeByteString extends com.android.framework.protobuf.ByteString {
    static final int[] minLengthByDepth = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, com.android.internal.logging.nano.MetricsProto.MetricsEvent.FUELGAUGE_ANOMALY_DETAIL, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, 102334155, 165580141, 267914296, 433494437, 701408733, 1134903170, 1836311903, Integer.MAX_VALUE};
    private static final long serialVersionUID = 1;
    private final com.android.framework.protobuf.ByteString left;
    private final int leftLength;
    private final com.android.framework.protobuf.ByteString right;
    private final int totalLength;
    private final int treeDepth;

    private RopeByteString(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ByteString byteString2) {
        this.left = byteString;
        this.right = byteString2;
        this.leftLength = byteString.size();
        this.totalLength = this.leftLength + byteString2.size();
        this.treeDepth = java.lang.Math.max(byteString.getTreeDepth(), byteString2.getTreeDepth()) + 1;
    }

    static com.android.framework.protobuf.ByteString concatenate(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ByteString byteString2) {
        if (byteString2.size() == 0) {
            return byteString;
        }
        if (byteString.size() == 0) {
            return byteString2;
        }
        int size = byteString.size() + byteString2.size();
        if (size < 128) {
            return concatenateBytes(byteString, byteString2);
        }
        if (byteString instanceof com.android.framework.protobuf.RopeByteString) {
            com.android.framework.protobuf.RopeByteString ropeByteString = (com.android.framework.protobuf.RopeByteString) byteString;
            if (ropeByteString.right.size() + byteString2.size() < 128) {
                return new com.android.framework.protobuf.RopeByteString(ropeByteString.left, concatenateBytes(ropeByteString.right, byteString2));
            }
            if (ropeByteString.left.getTreeDepth() > ropeByteString.right.getTreeDepth() && ropeByteString.getTreeDepth() > byteString2.getTreeDepth()) {
                return new com.android.framework.protobuf.RopeByteString(ropeByteString.left, new com.android.framework.protobuf.RopeByteString(ropeByteString.right, byteString2));
            }
        }
        if (size >= minLength(java.lang.Math.max(byteString.getTreeDepth(), byteString2.getTreeDepth()) + 1)) {
            return new com.android.framework.protobuf.RopeByteString(byteString, byteString2);
        }
        return new com.android.framework.protobuf.RopeByteString.Balancer().balance(byteString, byteString2);
    }

    private static com.android.framework.protobuf.ByteString concatenateBytes(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ByteString byteString2) {
        int size = byteString.size();
        int size2 = byteString2.size();
        byte[] bArr = new byte[size + size2];
        byteString.copyTo(bArr, 0, 0, size);
        byteString2.copyTo(bArr, 0, size, size2);
        return com.android.framework.protobuf.ByteString.wrap(bArr);
    }

    static com.android.framework.protobuf.RopeByteString newInstanceForTest(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ByteString byteString2) {
        return new com.android.framework.protobuf.RopeByteString(byteString, byteString2);
    }

    static int minLength(int i) {
        if (i >= minLengthByDepth.length) {
            return Integer.MAX_VALUE;
        }
        return minLengthByDepth[i];
    }

    @Override // com.android.framework.protobuf.ByteString
    public byte byteAt(int i) {
        checkIndex(i, this.totalLength);
        return internalByteAt(i);
    }

    @Override // com.android.framework.protobuf.ByteString
    byte internalByteAt(int i) {
        if (i < this.leftLength) {
            return this.left.internalByteAt(i);
        }
        return this.right.internalByteAt(i - this.leftLength);
    }

    @Override // com.android.framework.protobuf.ByteString
    public int size() {
        return this.totalLength;
    }

    @Override // com.android.framework.protobuf.ByteString, java.lang.Iterable
    /* renamed from: iterator */
    public java.util.Iterator<java.lang.Byte> iterator2() {
        return new com.android.framework.protobuf.ByteString.AbstractByteIterator() { // from class: com.android.framework.protobuf.RopeByteString.1
            com.android.framework.protobuf.ByteString.ByteIterator current = nextPiece();
            final com.android.framework.protobuf.RopeByteString.PieceIterator pieces;

            {
                this.pieces = new com.android.framework.protobuf.RopeByteString.PieceIterator(com.android.framework.protobuf.RopeByteString.this);
            }

            /* JADX WARN: Type inference failed for: r0v5, types: [com.android.framework.protobuf.ByteString$ByteIterator] */
            private com.android.framework.protobuf.ByteString.ByteIterator nextPiece() {
                if (this.pieces.hasNext()) {
                    return this.pieces.next().iterator2();
                }
                return null;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.current != null;
            }

            @Override // com.android.framework.protobuf.ByteString.ByteIterator
            public byte nextByte() {
                if (this.current == null) {
                    throw new java.util.NoSuchElementException();
                }
                byte nextByte = this.current.nextByte();
                if (!this.current.hasNext()) {
                    this.current = nextPiece();
                }
                return nextByte;
            }
        };
    }

    @Override // com.android.framework.protobuf.ByteString
    protected int getTreeDepth() {
        return this.treeDepth;
    }

    @Override // com.android.framework.protobuf.ByteString
    protected boolean isBalanced() {
        return this.totalLength >= minLength(this.treeDepth);
    }

    @Override // com.android.framework.protobuf.ByteString
    public com.android.framework.protobuf.ByteString substring(int i, int i2) {
        int checkRange = checkRange(i, i2, this.totalLength);
        if (checkRange == 0) {
            return com.android.framework.protobuf.ByteString.EMPTY;
        }
        if (checkRange == this.totalLength) {
            return this;
        }
        if (i2 <= this.leftLength) {
            return this.left.substring(i, i2);
        }
        if (i >= this.leftLength) {
            return this.right.substring(i - this.leftLength, i2 - this.leftLength);
        }
        return new com.android.framework.protobuf.RopeByteString(this.left.substring(i), this.right.substring(0, i2 - this.leftLength));
    }

    @Override // com.android.framework.protobuf.ByteString
    protected void copyToInternal(byte[] bArr, int i, int i2, int i3) {
        if (i + i3 <= this.leftLength) {
            this.left.copyToInternal(bArr, i, i2, i3);
        } else {
            if (i >= this.leftLength) {
                this.right.copyToInternal(bArr, i - this.leftLength, i2, i3);
                return;
            }
            int i4 = this.leftLength - i;
            this.left.copyToInternal(bArr, i, i2, i4);
            this.right.copyToInternal(bArr, 0, i2 + i4, i3 - i4);
        }
    }

    @Override // com.android.framework.protobuf.ByteString
    public void copyTo(java.nio.ByteBuffer byteBuffer) {
        this.left.copyTo(byteBuffer);
        this.right.copyTo(byteBuffer);
    }

    @Override // com.android.framework.protobuf.ByteString
    public java.nio.ByteBuffer asReadOnlyByteBuffer() {
        return java.nio.ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    }

    @Override // com.android.framework.protobuf.ByteString
    public java.util.List<java.nio.ByteBuffer> asReadOnlyByteBufferList() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.framework.protobuf.RopeByteString.PieceIterator pieceIterator = new com.android.framework.protobuf.RopeByteString.PieceIterator(this);
        while (pieceIterator.hasNext()) {
            arrayList.add(pieceIterator.next().asReadOnlyByteBuffer());
        }
        return arrayList;
    }

    @Override // com.android.framework.protobuf.ByteString
    public void writeTo(java.io.OutputStream outputStream) throws java.io.IOException {
        this.left.writeTo(outputStream);
        this.right.writeTo(outputStream);
    }

    @Override // com.android.framework.protobuf.ByteString
    void writeToInternal(java.io.OutputStream outputStream, int i, int i2) throws java.io.IOException {
        if (i + i2 <= this.leftLength) {
            this.left.writeToInternal(outputStream, i, i2);
        } else {
            if (i >= this.leftLength) {
                this.right.writeToInternal(outputStream, i - this.leftLength, i2);
                return;
            }
            int i3 = this.leftLength - i;
            this.left.writeToInternal(outputStream, i, i3);
            this.right.writeToInternal(outputStream, 0, i2 - i3);
        }
    }

    @Override // com.android.framework.protobuf.ByteString
    void writeTo(com.android.framework.protobuf.ByteOutput byteOutput) throws java.io.IOException {
        this.left.writeTo(byteOutput);
        this.right.writeTo(byteOutput);
    }

    @Override // com.android.framework.protobuf.ByteString
    void writeToReverse(com.android.framework.protobuf.ByteOutput byteOutput) throws java.io.IOException {
        this.right.writeToReverse(byteOutput);
        this.left.writeToReverse(byteOutput);
    }

    @Override // com.android.framework.protobuf.ByteString
    protected java.lang.String toStringInternal(java.nio.charset.Charset charset) {
        return new java.lang.String(toByteArray(), charset);
    }

    @Override // com.android.framework.protobuf.ByteString
    public boolean isValidUtf8() {
        return this.right.partialIsValidUtf8(this.left.partialIsValidUtf8(0, 0, this.leftLength), 0, this.right.size()) == 0;
    }

    @Override // com.android.framework.protobuf.ByteString
    protected int partialIsValidUtf8(int i, int i2, int i3) {
        if (i2 + i3 <= this.leftLength) {
            return this.left.partialIsValidUtf8(i, i2, i3);
        }
        if (i2 >= this.leftLength) {
            return this.right.partialIsValidUtf8(i, i2 - this.leftLength, i3);
        }
        int i4 = this.leftLength - i2;
        return this.right.partialIsValidUtf8(this.left.partialIsValidUtf8(i, i2, i4), 0, i3 - i4);
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
        if (this.totalLength != byteString.size()) {
            return false;
        }
        if (this.totalLength == 0) {
            return true;
        }
        int peekCachedHashCode = peekCachedHashCode();
        int peekCachedHashCode2 = byteString.peekCachedHashCode();
        if (peekCachedHashCode == 0 || peekCachedHashCode2 == 0 || peekCachedHashCode == peekCachedHashCode2) {
            return equalsFragments(byteString);
        }
        return false;
    }

    private boolean equalsFragments(com.android.framework.protobuf.ByteString byteString) {
        boolean equalsRange;
        com.android.framework.protobuf.RopeByteString.PieceIterator pieceIterator = new com.android.framework.protobuf.RopeByteString.PieceIterator(this);
        com.android.framework.protobuf.ByteString.LeafByteString next = pieceIterator.next();
        com.android.framework.protobuf.RopeByteString.PieceIterator pieceIterator2 = new com.android.framework.protobuf.RopeByteString.PieceIterator(byteString);
        com.android.framework.protobuf.ByteString.LeafByteString next2 = pieceIterator2.next();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int size = next.size() - i;
            int size2 = next2.size() - i2;
            int min = java.lang.Math.min(size, size2);
            if (i == 0) {
                equalsRange = next.equalsRange(next2, i2, min);
            } else {
                equalsRange = next2.equalsRange(next, i, min);
            }
            if (!equalsRange) {
                return false;
            }
            i3 += min;
            if (i3 >= this.totalLength) {
                if (i3 == this.totalLength) {
                    return true;
                }
                throw new java.lang.IllegalStateException();
            }
            if (min == size) {
                i = 0;
                next = pieceIterator.next();
            } else {
                i += min;
                next = next;
            }
            if (min == size2) {
                next2 = pieceIterator2.next();
                i2 = 0;
            } else {
                i2 += min;
            }
        }
    }

    @Override // com.android.framework.protobuf.ByteString
    protected int partialHash(int i, int i2, int i3) {
        if (i2 + i3 <= this.leftLength) {
            return this.left.partialHash(i, i2, i3);
        }
        if (i2 >= this.leftLength) {
            return this.right.partialHash(i, i2 - this.leftLength, i3);
        }
        int i4 = this.leftLength - i2;
        return this.right.partialHash(this.left.partialHash(i, i2, i4), 0, i3 - i4);
    }

    @Override // com.android.framework.protobuf.ByteString
    public com.android.framework.protobuf.CodedInputStream newCodedInput() {
        return com.android.framework.protobuf.CodedInputStream.newInstance((java.lang.Iterable<java.nio.ByteBuffer>) asReadOnlyByteBufferList(), true);
    }

    @Override // com.android.framework.protobuf.ByteString
    public java.io.InputStream newInput() {
        return new com.android.framework.protobuf.RopeByteString.RopeInputStream();
    }

    private static class Balancer {
        private final java.util.ArrayDeque<com.android.framework.protobuf.ByteString> prefixesStack;

        private Balancer() {
            this.prefixesStack = new java.util.ArrayDeque<>();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.framework.protobuf.ByteString balance(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ByteString byteString2) {
            doBalance(byteString);
            doBalance(byteString2);
            com.android.framework.protobuf.ByteString pop = this.prefixesStack.pop();
            while (!this.prefixesStack.isEmpty()) {
                pop = new com.android.framework.protobuf.RopeByteString(this.prefixesStack.pop(), pop);
            }
            return pop;
        }

        private void doBalance(com.android.framework.protobuf.ByteString byteString) {
            if (byteString.isBalanced()) {
                insert(byteString);
            } else {
                if (byteString instanceof com.android.framework.protobuf.RopeByteString) {
                    com.android.framework.protobuf.RopeByteString ropeByteString = (com.android.framework.protobuf.RopeByteString) byteString;
                    doBalance(ropeByteString.left);
                    doBalance(ropeByteString.right);
                    return;
                }
                throw new java.lang.IllegalArgumentException("Has a new type of ByteString been created? Found " + byteString.getClass());
            }
        }

        private void insert(com.android.framework.protobuf.ByteString byteString) {
            int depthBinForLength = getDepthBinForLength(byteString.size());
            int minLength = com.android.framework.protobuf.RopeByteString.minLength(depthBinForLength + 1);
            if (this.prefixesStack.isEmpty() || this.prefixesStack.peek().size() >= minLength) {
                this.prefixesStack.push(byteString);
                return;
            }
            int minLength2 = com.android.framework.protobuf.RopeByteString.minLength(depthBinForLength);
            com.android.framework.protobuf.ByteString pop = this.prefixesStack.pop();
            while (true) {
                if (this.prefixesStack.isEmpty() || this.prefixesStack.peek().size() >= minLength2) {
                    break;
                } else {
                    pop = new com.android.framework.protobuf.RopeByteString(this.prefixesStack.pop(), pop);
                }
            }
            com.android.framework.protobuf.RopeByteString ropeByteString = new com.android.framework.protobuf.RopeByteString(pop, byteString);
            while (!this.prefixesStack.isEmpty()) {
                if (this.prefixesStack.peek().size() >= com.android.framework.protobuf.RopeByteString.minLength(getDepthBinForLength(ropeByteString.size()) + 1)) {
                    break;
                } else {
                    ropeByteString = new com.android.framework.protobuf.RopeByteString(this.prefixesStack.pop(), ropeByteString);
                }
            }
            this.prefixesStack.push(ropeByteString);
        }

        private int getDepthBinForLength(int i) {
            int binarySearch = java.util.Arrays.binarySearch(com.android.framework.protobuf.RopeByteString.minLengthByDepth, i);
            if (binarySearch < 0) {
                return (-(binarySearch + 1)) - 1;
            }
            return binarySearch;
        }
    }

    private static final class PieceIterator implements java.util.Iterator<com.android.framework.protobuf.ByteString.LeafByteString> {
        private final java.util.ArrayDeque<com.android.framework.protobuf.RopeByteString> breadCrumbs;
        private com.android.framework.protobuf.ByteString.LeafByteString next;

        private PieceIterator(com.android.framework.protobuf.ByteString byteString) {
            if (byteString instanceof com.android.framework.protobuf.RopeByteString) {
                com.android.framework.protobuf.RopeByteString ropeByteString = (com.android.framework.protobuf.RopeByteString) byteString;
                this.breadCrumbs = new java.util.ArrayDeque<>(ropeByteString.getTreeDepth());
                this.breadCrumbs.push(ropeByteString);
                this.next = getLeafByLeft(ropeByteString.left);
                return;
            }
            this.breadCrumbs = null;
            this.next = (com.android.framework.protobuf.ByteString.LeafByteString) byteString;
        }

        private com.android.framework.protobuf.ByteString.LeafByteString getLeafByLeft(com.android.framework.protobuf.ByteString byteString) {
            while (byteString instanceof com.android.framework.protobuf.RopeByteString) {
                com.android.framework.protobuf.RopeByteString ropeByteString = (com.android.framework.protobuf.RopeByteString) byteString;
                this.breadCrumbs.push(ropeByteString);
                byteString = ropeByteString.left;
            }
            return (com.android.framework.protobuf.ByteString.LeafByteString) byteString;
        }

        private com.android.framework.protobuf.ByteString.LeafByteString getNextNonEmptyLeaf() {
            while (this.breadCrumbs != null && !this.breadCrumbs.isEmpty()) {
                com.android.framework.protobuf.ByteString.LeafByteString leafByLeft = getLeafByLeft(this.breadCrumbs.pop().right);
                if (!leafByLeft.isEmpty()) {
                    return leafByLeft;
                }
            }
            return null;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.next != null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public com.android.framework.protobuf.ByteString.LeafByteString next() {
            if (this.next == null) {
                throw new java.util.NoSuchElementException();
            }
            com.android.framework.protobuf.ByteString.LeafByteString leafByteString = this.next;
            this.next = getNextNonEmptyLeaf();
            return leafByteString;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    java.lang.Object writeReplace() {
        return com.android.framework.protobuf.ByteString.wrap(toByteArray());
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException {
        throw new java.io.InvalidObjectException("RopeByteStream instances are not to be serialized directly");
    }

    private class RopeInputStream extends java.io.InputStream {
        private com.android.framework.protobuf.ByteString.LeafByteString currentPiece;
        private int currentPieceIndex;
        private int currentPieceOffsetInRope;
        private int currentPieceSize;
        private int mark;
        private com.android.framework.protobuf.RopeByteString.PieceIterator pieceIterator;

        public RopeInputStream() {
            initialize();
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) {
            if (bArr == null) {
                throw new java.lang.NullPointerException();
            }
            if (i < 0 || i2 < 0 || i2 > bArr.length - i) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            int readSkipInternal = readSkipInternal(bArr, i, i2);
            if (readSkipInternal == 0 && (i2 > 0 || availableInternal() == 0)) {
                return -1;
            }
            return readSkipInternal;
        }

        @Override // java.io.InputStream
        public long skip(long j) {
            if (j < 0) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            if (j > 2147483647L) {
                j = 2147483647L;
            }
            return readSkipInternal(null, 0, (int) j);
        }

        private int readSkipInternal(byte[] bArr, int i, int i2) {
            int i3 = i2;
            while (i3 > 0) {
                advanceIfCurrentPieceFullyRead();
                if (this.currentPiece == null) {
                    break;
                }
                int min = java.lang.Math.min(this.currentPieceSize - this.currentPieceIndex, i3);
                if (bArr != null) {
                    this.currentPiece.copyTo(bArr, this.currentPieceIndex, i, min);
                    i += min;
                }
                this.currentPieceIndex += min;
                i3 -= min;
            }
            return i2 - i3;
        }

        @Override // java.io.InputStream
        public int read() throws java.io.IOException {
            advanceIfCurrentPieceFullyRead();
            if (this.currentPiece == null) {
                return -1;
            }
            com.android.framework.protobuf.ByteString.LeafByteString leafByteString = this.currentPiece;
            int i = this.currentPieceIndex;
            this.currentPieceIndex = i + 1;
            return leafByteString.byteAt(i) & 255;
        }

        @Override // java.io.InputStream
        public int available() throws java.io.IOException {
            return availableInternal();
        }

        @Override // java.io.InputStream
        public boolean markSupported() {
            return true;
        }

        @Override // java.io.InputStream
        public void mark(int i) {
            this.mark = this.currentPieceOffsetInRope + this.currentPieceIndex;
        }

        @Override // java.io.InputStream
        public synchronized void reset() {
            initialize();
            readSkipInternal(null, 0, this.mark);
        }

        private void initialize() {
            this.pieceIterator = new com.android.framework.protobuf.RopeByteString.PieceIterator(com.android.framework.protobuf.RopeByteString.this);
            this.currentPiece = this.pieceIterator.next();
            this.currentPieceSize = this.currentPiece.size();
            this.currentPieceIndex = 0;
            this.currentPieceOffsetInRope = 0;
        }

        private void advanceIfCurrentPieceFullyRead() {
            if (this.currentPiece != null && this.currentPieceIndex == this.currentPieceSize) {
                this.currentPieceOffsetInRope += this.currentPieceSize;
                this.currentPieceIndex = 0;
                if (this.pieceIterator.hasNext()) {
                    this.currentPiece = this.pieceIterator.next();
                    this.currentPieceSize = this.currentPiece.size();
                } else {
                    this.currentPiece = null;
                    this.currentPieceSize = 0;
                }
            }
        }

        private int availableInternal() {
            return com.android.framework.protobuf.RopeByteString.this.size() - (this.currentPieceOffsetInRope + this.currentPieceIndex);
        }
    }
}
