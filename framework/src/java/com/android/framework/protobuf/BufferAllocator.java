package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
abstract class BufferAllocator {
    private static final com.android.framework.protobuf.BufferAllocator UNPOOLED = new com.android.framework.protobuf.BufferAllocator() { // from class: com.android.framework.protobuf.BufferAllocator.1
        @Override // com.android.framework.protobuf.BufferAllocator
        public com.android.framework.protobuf.AllocatedBuffer allocateHeapBuffer(int i) {
            return com.android.framework.protobuf.AllocatedBuffer.wrap(new byte[i]);
        }

        @Override // com.android.framework.protobuf.BufferAllocator
        public com.android.framework.protobuf.AllocatedBuffer allocateDirectBuffer(int i) {
            return com.android.framework.protobuf.AllocatedBuffer.wrap(java.nio.ByteBuffer.allocateDirect(i));
        }
    };

    public abstract com.android.framework.protobuf.AllocatedBuffer allocateDirectBuffer(int i);

    public abstract com.android.framework.protobuf.AllocatedBuffer allocateHeapBuffer(int i);

    BufferAllocator() {
    }

    public static com.android.framework.protobuf.BufferAllocator unpooled() {
        return UNPOOLED;
    }
}
