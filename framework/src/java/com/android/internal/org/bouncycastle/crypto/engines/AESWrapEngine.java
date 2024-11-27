package com.android.internal.org.bouncycastle.crypto.engines;

/* loaded from: classes4.dex */
public class AESWrapEngine extends com.android.internal.org.bouncycastle.crypto.engines.RFC3394WrapEngine {
    public AESWrapEngine() {
        super(new com.android.internal.org.bouncycastle.crypto.engines.AESEngine());
    }

    public AESWrapEngine(boolean z) {
        super(new com.android.internal.org.bouncycastle.crypto.engines.AESEngine(), z);
    }
}
