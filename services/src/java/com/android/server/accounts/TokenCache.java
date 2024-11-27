package com.android.server.accounts;

/* loaded from: classes.dex */
class TokenCache {
    private static final int MAX_CACHE_CHARS = 64000;
    private com.android.server.accounts.TokenCache.TokenLruCache mCachedTokens = new com.android.server.accounts.TokenCache.TokenLruCache();

    TokenCache() {
    }

    static class Value {
        public final long expiryEpochMillis;
        public final java.lang.String token;

        public Value(java.lang.String str, long j) {
            this.token = str;
            this.expiryEpochMillis = j;
        }
    }

    private static class Key {
        public final android.accounts.Account account;
        public final java.lang.String packageName;
        public final byte[] sigDigest;
        public final java.lang.String tokenType;

        public Key(android.accounts.Account account, java.lang.String str, java.lang.String str2, byte[] bArr) {
            this.account = account;
            this.tokenType = str;
            this.packageName = str2;
            this.sigDigest = bArr;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null || !(obj instanceof com.android.server.accounts.TokenCache.Key)) {
                return false;
            }
            com.android.server.accounts.TokenCache.Key key = (com.android.server.accounts.TokenCache.Key) obj;
            return java.util.Objects.equals(this.account, key.account) && java.util.Objects.equals(this.packageName, key.packageName) && java.util.Objects.equals(this.tokenType, key.tokenType) && java.util.Arrays.equals(this.sigDigest, key.sigDigest);
        }

        public int hashCode() {
            return ((this.account.hashCode() ^ this.packageName.hashCode()) ^ this.tokenType.hashCode()) ^ java.util.Arrays.hashCode(this.sigDigest);
        }
    }

    private static class TokenLruCache extends android.util.LruCache<com.android.server.accounts.TokenCache.Key, com.android.server.accounts.TokenCache.Value> {
        private java.util.HashMap<android.accounts.Account, com.android.server.accounts.TokenCache.TokenLruCache.Evictor> mAccountEvictors;
        private java.util.HashMap<android.util.Pair<java.lang.String, java.lang.String>, com.android.server.accounts.TokenCache.TokenLruCache.Evictor> mTokenEvictors;

        private class Evictor {
            private final java.util.List<com.android.server.accounts.TokenCache.Key> mKeys = new java.util.ArrayList();

            public Evictor() {
            }

            public void add(com.android.server.accounts.TokenCache.Key key) {
                this.mKeys.add(key);
            }

            public void evict() {
                java.util.Iterator<com.android.server.accounts.TokenCache.Key> it = this.mKeys.iterator();
                while (it.hasNext()) {
                    com.android.server.accounts.TokenCache.TokenLruCache.this.remove(it.next());
                }
            }
        }

        public TokenLruCache() {
            super(com.android.server.accounts.TokenCache.MAX_CACHE_CHARS);
            this.mTokenEvictors = new java.util.HashMap<>();
            this.mAccountEvictors = new java.util.HashMap<>();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.util.LruCache
        public int sizeOf(com.android.server.accounts.TokenCache.Key key, com.android.server.accounts.TokenCache.Value value) {
            return value.token.length();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.util.LruCache
        public void entryRemoved(boolean z, com.android.server.accounts.TokenCache.Key key, com.android.server.accounts.TokenCache.Value value, com.android.server.accounts.TokenCache.Value value2) {
            com.android.server.accounts.TokenCache.TokenLruCache.Evictor remove;
            if (value != null && value2 == null && (remove = this.mTokenEvictors.remove(new android.util.Pair(key.account.type, value.token))) != null) {
                remove.evict();
            }
        }

        public void putToken(com.android.server.accounts.TokenCache.Key key, com.android.server.accounts.TokenCache.Value value) {
            android.util.Pair<java.lang.String, java.lang.String> pair = new android.util.Pair<>(key.account.type, value.token);
            com.android.server.accounts.TokenCache.TokenLruCache.Evictor evictor = this.mTokenEvictors.get(pair);
            if (evictor == null) {
                evictor = new com.android.server.accounts.TokenCache.TokenLruCache.Evictor();
            }
            evictor.add(key);
            this.mTokenEvictors.put(pair, evictor);
            com.android.server.accounts.TokenCache.TokenLruCache.Evictor evictor2 = this.mAccountEvictors.get(key.account);
            if (evictor2 == null) {
                evictor2 = new com.android.server.accounts.TokenCache.TokenLruCache.Evictor();
            }
            evictor2.add(key);
            this.mAccountEvictors.put(key.account, evictor2);
            put(key, value);
        }

        public void evict(java.lang.String str, java.lang.String str2) {
            com.android.server.accounts.TokenCache.TokenLruCache.Evictor evictor = this.mTokenEvictors.get(new android.util.Pair(str, str2));
            if (evictor != null) {
                evictor.evict();
            }
        }

        public void evict(android.accounts.Account account) {
            com.android.server.accounts.TokenCache.TokenLruCache.Evictor evictor = this.mAccountEvictors.get(account);
            if (evictor != null) {
                evictor.evict();
            }
        }
    }

    public void put(android.accounts.Account account, java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr, long j) {
        java.util.Objects.requireNonNull(account);
        if (str == null || java.lang.System.currentTimeMillis() > j) {
            return;
        }
        this.mCachedTokens.putToken(new com.android.server.accounts.TokenCache.Key(account, str2, str3, bArr), new com.android.server.accounts.TokenCache.Value(str, j));
    }

    public void remove(java.lang.String str, java.lang.String str2) {
        this.mCachedTokens.evict(str, str2);
    }

    public void remove(android.accounts.Account account) {
        this.mCachedTokens.evict(account);
    }

    public com.android.server.accounts.TokenCache.Value get(android.accounts.Account account, java.lang.String str, java.lang.String str2, byte[] bArr) {
        com.android.server.accounts.TokenCache.Value value = this.mCachedTokens.get(new com.android.server.accounts.TokenCache.Key(account, str, str2, bArr));
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (value != null && currentTimeMillis < value.expiryEpochMillis) {
            return value;
        }
        if (value != null) {
            remove(account.type, value.token);
            return null;
        }
        return null;
    }
}
