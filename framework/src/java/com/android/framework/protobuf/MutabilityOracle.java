package com.android.framework.protobuf;

/* loaded from: classes4.dex */
interface MutabilityOracle {
    public static final com.android.framework.protobuf.MutabilityOracle IMMUTABLE = new com.android.framework.protobuf.MutabilityOracle() { // from class: com.android.framework.protobuf.MutabilityOracle.1
        @Override // com.android.framework.protobuf.MutabilityOracle
        public void ensureMutable() {
            throw new java.lang.UnsupportedOperationException();
        }
    };

    void ensureMutable();
}
