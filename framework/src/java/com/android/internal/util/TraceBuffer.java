package com.android.internal.util;

/* loaded from: classes5.dex */
public class TraceBuffer<P, S extends P, T extends P> {
    private final java.util.Queue<T> mBuffer;
    private int mBufferCapacity;
    private int mBufferUsedSize;
    private final java.util.function.Consumer mProtoDequeuedCallback;
    private final com.android.internal.util.TraceBuffer.ProtoProvider<P, S, T> mProtoProvider;

    public interface ProtoProvider<P, S extends P, T extends P> {
        byte[] getBytes(P p);

        int getItemSize(P p);

        void write(S s, java.util.Queue<T> queue, java.io.OutputStream outputStream) throws java.io.IOException;
    }

    private static class ProtoOutputStreamProvider implements com.android.internal.util.TraceBuffer.ProtoProvider<android.util.proto.ProtoOutputStream, android.util.proto.ProtoOutputStream, android.util.proto.ProtoOutputStream> {
        private ProtoOutputStreamProvider() {
        }

        @Override // com.android.internal.util.TraceBuffer.ProtoProvider
        public int getItemSize(android.util.proto.ProtoOutputStream protoOutputStream) {
            return protoOutputStream.getRawSize();
        }

        @Override // com.android.internal.util.TraceBuffer.ProtoProvider
        public byte[] getBytes(android.util.proto.ProtoOutputStream protoOutputStream) {
            return protoOutputStream.getBytes();
        }

        @Override // com.android.internal.util.TraceBuffer.ProtoProvider
        public void write(android.util.proto.ProtoOutputStream protoOutputStream, java.util.Queue<android.util.proto.ProtoOutputStream> queue, java.io.OutputStream outputStream) throws java.io.IOException {
            outputStream.write(protoOutputStream.getBytes());
            java.util.Iterator<android.util.proto.ProtoOutputStream> it = queue.iterator();
            while (it.hasNext()) {
                outputStream.write(it.next().getBytes());
            }
        }
    }

    public TraceBuffer(int i) {
        this(i, new com.android.internal.util.TraceBuffer.ProtoOutputStreamProvider(), null);
    }

    public TraceBuffer(int i, java.util.function.Consumer<T> consumer) {
        this(i, new com.android.internal.util.TraceBuffer.ProtoOutputStreamProvider(), consumer);
    }

    public TraceBuffer(int i, com.android.internal.util.TraceBuffer.ProtoProvider protoProvider, java.util.function.Consumer<T> consumer) {
        this.mBuffer = new java.util.ArrayDeque();
        this.mBufferCapacity = i;
        this.mProtoProvider = protoProvider;
        this.mProtoDequeuedCallback = consumer;
        resetBuffer();
    }

    public synchronized int getAvailableSpace() {
        return this.mBufferCapacity - this.mBufferUsedSize;
    }

    public synchronized int size() {
        return this.mBuffer.size();
    }

    public synchronized void setCapacity(int i) {
        this.mBufferCapacity = i;
    }

    public synchronized void add(T t) {
        int itemSize = this.mProtoProvider.getItemSize(t);
        if (itemSize > this.mBufferCapacity) {
            throw new java.lang.IllegalStateException("Trace object too large for the buffer. Buffer size:" + this.mBufferCapacity + " Object size: " + itemSize);
        }
        discardOldest(itemSize);
        this.mBuffer.add(t);
        this.mBufferUsedSize += itemSize;
    }

    public synchronized boolean contains(final byte[] bArr) {
        return this.mBuffer.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.internal.util.TraceBuffer$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$contains$0;
                lambda$contains$0 = com.android.internal.util.TraceBuffer.this.lambda$contains$0(bArr, obj);
                return lambda$contains$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$contains$0(byte[] bArr, java.lang.Object obj) {
        return java.util.Arrays.equals(this.mProtoProvider.getBytes(obj), bArr);
    }

    public synchronized void writeTraceToFile(java.io.File file, S s) throws java.io.IOException {
        file.delete();
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
        try {
            file.setReadable(true, false);
            this.mProtoProvider.write(s, this.mBuffer, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } finally {
        }
    }

    private void discardOldest(int i) {
        long availableSpace = getAvailableSpace();
        while (availableSpace < i) {
            T poll = this.mBuffer.poll();
            if (poll == null) {
                throw new java.lang.IllegalStateException("No element to discard from buffer");
            }
            this.mBufferUsedSize -= this.mProtoProvider.getItemSize(poll);
            long availableSpace2 = getAvailableSpace();
            if (this.mProtoDequeuedCallback != null) {
                this.mProtoDequeuedCallback.accept(poll);
            }
            availableSpace = availableSpace2;
        }
    }

    public synchronized void resetBuffer() {
        if (this.mProtoDequeuedCallback != null) {
            java.util.Iterator<T> it = this.mBuffer.iterator();
            while (it.hasNext()) {
                this.mProtoDequeuedCallback.accept(it.next());
            }
        }
        this.mBuffer.clear();
        this.mBufferUsedSize = 0;
    }

    public synchronized int getBufferSize() {
        return this.mBufferUsedSize;
    }

    public synchronized java.lang.String getStatus() {
        return "Buffer size: " + this.mBufferCapacity + " bytes\nBuffer usage: " + this.mBufferUsedSize + " bytes\nElements in the buffer: " + this.mBuffer.size();
    }
}
