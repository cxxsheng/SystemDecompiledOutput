package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class HdmiCecMessageCache {
    private static final android.util.FastImmutableArraySet<java.lang.Integer> CACHEABLE_OPCODES = new android.util.FastImmutableArraySet<>(new java.lang.Integer[]{71, 132, 135, 158});
    private final android.util.SparseArray<android.util.SparseArray<com.android.server.hdmi.HdmiCecMessage>> mCache = new android.util.SparseArray<>();

    HdmiCecMessageCache() {
    }

    public com.android.server.hdmi.HdmiCecMessage getMessage(int i, int i2) {
        android.util.SparseArray<com.android.server.hdmi.HdmiCecMessage> sparseArray = this.mCache.get(i);
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(i2);
    }

    public void flushMessagesFrom(int i) {
        this.mCache.remove(i);
    }

    public void flushAll() {
        this.mCache.clear();
    }

    public void cacheMessage(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        int opcode = hdmiCecMessage.getOpcode();
        if (!isCacheable(opcode)) {
            return;
        }
        int source = hdmiCecMessage.getSource();
        android.util.SparseArray<com.android.server.hdmi.HdmiCecMessage> sparseArray = this.mCache.get(source);
        if (sparseArray == null) {
            sparseArray = new android.util.SparseArray<>();
            this.mCache.put(source, sparseArray);
        }
        sparseArray.put(opcode, hdmiCecMessage);
    }

    private boolean isCacheable(int i) {
        return CACHEABLE_OPCODES.contains(java.lang.Integer.valueOf(i));
    }
}
