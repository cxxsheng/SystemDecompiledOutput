package com.android.server.locksettings.recoverablekeystore.storage;

/* loaded from: classes2.dex */
public class RecoverySessionStorage implements javax.security.auth.Destroyable {
    private final android.util.SparseArray<java.util.ArrayList<com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry>> mSessionsByUid = new android.util.SparseArray<>();

    @android.annotation.Nullable
    public com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry get(int i, java.lang.String str) {
        java.util.ArrayList<com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry> arrayList = this.mSessionsByUid.get(i);
        if (arrayList == null) {
            return null;
        }
        java.util.Iterator<com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry> it = arrayList.iterator();
        while (it.hasNext()) {
            com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry next = it.next();
            if (str.equals(next.mSessionId)) {
                return next;
            }
        }
        return null;
    }

    public void add(int i, com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry entry) {
        if (this.mSessionsByUid.get(i) == null) {
            this.mSessionsByUid.put(i, new java.util.ArrayList<>());
        }
        this.mSessionsByUid.get(i).add(entry);
    }

    public void remove(int i, final java.lang.String str) {
        if (this.mSessionsByUid.get(i) == null) {
            return;
        }
        this.mSessionsByUid.get(i).removeIf(new java.util.function.Predicate() { // from class: com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$remove$0;
                lambda$remove$0 = com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.lambda$remove$0(str, (com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry) obj);
                return lambda$remove$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$remove$0(java.lang.String str, com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry entry) {
        return entry.mSessionId.equals(str);
    }

    public void remove(int i) {
        java.util.ArrayList<com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry> arrayList = this.mSessionsByUid.get(i);
        if (arrayList == null) {
            return;
        }
        java.util.Iterator<com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().destroy();
        }
        this.mSessionsByUid.remove(i);
    }

    public int size() {
        int size = this.mSessionsByUid.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += this.mSessionsByUid.valueAt(i2).size();
        }
        return i;
    }

    @Override // javax.security.auth.Destroyable
    public void destroy() {
        int size = this.mSessionsByUid.size();
        for (int i = 0; i < size; i++) {
            java.util.Iterator<com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry> it = this.mSessionsByUid.valueAt(i).iterator();
            while (it.hasNext()) {
                it.next().destroy();
            }
        }
    }

    public static class Entry implements javax.security.auth.Destroyable {
        private final byte[] mKeyClaimant;
        private final byte[] mLskfHash;
        private final java.lang.String mSessionId;
        private final byte[] mVaultParams;

        public Entry(java.lang.String str, byte[] bArr, byte[] bArr2, byte[] bArr3) {
            this.mLskfHash = bArr;
            this.mSessionId = str;
            this.mKeyClaimant = bArr2;
            this.mVaultParams = bArr3;
        }

        public byte[] getLskfHash() {
            return this.mLskfHash;
        }

        public byte[] getKeyClaimant() {
            return this.mKeyClaimant;
        }

        public byte[] getVaultParams() {
            return this.mVaultParams;
        }

        @Override // javax.security.auth.Destroyable
        public void destroy() {
            java.util.Arrays.fill(this.mLskfHash, (byte) 0);
            java.util.Arrays.fill(this.mKeyClaimant, (byte) 0);
        }
    }
}
