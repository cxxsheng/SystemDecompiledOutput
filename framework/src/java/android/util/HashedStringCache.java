package android.util;

/* loaded from: classes3.dex */
public class HashedStringCache {
    private static final long DAYS_TO_MILLIS = 86400000;
    private static final boolean DEBUG = false;
    private static final int HASH_CACHE_SIZE = 100;
    private static final int HASH_LENGTH = 8;
    static final java.lang.String HASH_SALT = "_hash_salt";
    static final java.lang.String HASH_SALT_DATE = "_hash_salt_date";
    static final java.lang.String HASH_SALT_GEN = "_hash_salt_gen";
    private static final int MAX_SALT_DAYS = 100;
    private static final java.lang.String TAG = "HashedStringCache";
    private final java.security.MessageDigest mDigester;
    private byte[] mSalt;
    private int mSaltGen;
    private android.content.SharedPreferences mSharedPreferences;
    private static android.util.HashedStringCache sHashedStringCache = null;
    private static final java.nio.charset.Charset UTF_8 = java.nio.charset.Charset.forName(android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
    private final java.lang.Object mPreferenceLock = new java.lang.Object();
    private final android.util.LruCache<java.lang.String, java.lang.String> mHashes = new android.util.LruCache<>(100);
    private final java.security.SecureRandom mSecureRandom = new java.security.SecureRandom();

    private HashedStringCache() {
        try {
            this.mDigester = java.security.MessageDigest.getInstance(android.security.keystore.KeyProperties.DIGEST_MD5);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public static android.util.HashedStringCache getInstance() {
        if (sHashedStringCache == null) {
            sHashedStringCache = new android.util.HashedStringCache();
        }
        return sHashedStringCache;
    }

    public android.util.HashedStringCache.HashResult hashString(android.content.Context context, java.lang.String str, java.lang.String str2, int i) {
        if (i == -1 || context == null || android.text.TextUtils.isEmpty(str2) || android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        populateSaltValues(context, str, i);
        java.lang.String str3 = this.mHashes.get(str2);
        if (str3 != null) {
            return new android.util.HashedStringCache.HashResult(str3, this.mSaltGen);
        }
        this.mDigester.reset();
        this.mDigester.update(this.mSalt);
        this.mDigester.update(str2.getBytes(UTF_8));
        byte[] digest = this.mDigester.digest();
        java.lang.String encodeToString = android.util.Base64.encodeToString(digest, 0, java.lang.Math.min(8, digest.length), 3);
        this.mHashes.put(str2, encodeToString);
        return new android.util.HashedStringCache.HashResult(encodeToString, this.mSaltGen);
    }

    private boolean checkNeedsNewSalt(java.lang.String str, int i, long j) {
        if (j == 0 || i < -1) {
            return true;
        }
        if (i > 100) {
            i = 100;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis() - j;
        return currentTimeMillis >= ((long) i) * 86400000 || currentTimeMillis < 0;
    }

    private void populateSaltValues(android.content.Context context, java.lang.String str, int i) {
        synchronized (this.mPreferenceLock) {
            this.mSharedPreferences = getHashSharedPreferences(context);
            boolean checkNeedsNewSalt = checkNeedsNewSalt(str, i, this.mSharedPreferences.getLong(str + HASH_SALT_DATE, 0L));
            if (checkNeedsNewSalt) {
                this.mHashes.evictAll();
            }
            if (this.mSalt == null || checkNeedsNewSalt) {
                java.lang.String string = this.mSharedPreferences.getString(str + HASH_SALT, null);
                this.mSaltGen = this.mSharedPreferences.getInt(str + HASH_SALT_GEN, 0);
                if (string == null || checkNeedsNewSalt) {
                    this.mSaltGen++;
                    byte[] bArr = new byte[16];
                    this.mSecureRandom.nextBytes(bArr);
                    string = android.util.Base64.encodeToString(bArr, 3);
                    this.mSharedPreferences.edit().putString(str + HASH_SALT, string).putInt(str + HASH_SALT_GEN, this.mSaltGen).putLong(str + HASH_SALT_DATE, java.lang.System.currentTimeMillis()).apply();
                }
                this.mSalt = string.getBytes(UTF_8);
            }
        }
    }

    private android.content.SharedPreferences getHashSharedPreferences(android.content.Context context) {
        return context.getSharedPreferences(new java.io.File(new java.io.File(android.os.Environment.getDataUserCePackageDirectory(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, context.getUserId(), context.getPackageName()), "shared_prefs"), "hashed_cache.xml"), 0);
    }

    public class HashResult {
        public java.lang.String hashedString;
        public int saltGeneration;

        public HashResult(java.lang.String str, int i) {
            this.hashedString = str;
            this.saltGeneration = i;
        }
    }
}
