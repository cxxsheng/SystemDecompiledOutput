package android.provider;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class FontsContract {
    private static final long SYNC_FONT_FETCH_TIMEOUT_MS = 500;
    private static final java.lang.String TAG = "FontsContract";
    private static final int THREAD_RENEWAL_THRESHOLD_MS = 10000;
    private static volatile android.content.Context sContext;
    private static android.os.Handler sHandler;
    private static java.util.Set<java.lang.String> sInQueueSet;
    private static android.os.HandlerThread sThread;
    private static final java.lang.Object sLock = new java.lang.Object();
    private static final android.util.LruCache<java.lang.String, android.graphics.Typeface> sTypefaceCache = new android.util.LruCache<>(16);
    private static final java.lang.Runnable sReplaceDispatcherThreadRunnable = new java.lang.Runnable() { // from class: android.provider.FontsContract.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (android.provider.FontsContract.sLock) {
                if (android.provider.FontsContract.sThread != null) {
                    android.provider.FontsContract.sThread.quitSafely();
                    android.provider.FontsContract.sThread = null;
                    android.provider.FontsContract.sHandler = null;
                }
            }
        }
    };
    private static final java.util.Comparator<byte[]> sByteArrayComparator = new java.util.Comparator() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda12
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            return android.provider.FontsContract.lambda$static$13((byte[]) obj, (byte[]) obj2);
        }
    };

    @java.lang.Deprecated
    public static final class Columns implements android.provider.BaseColumns {
        public static final java.lang.String FILE_ID = "file_id";
        public static final java.lang.String ITALIC = "font_italic";
        public static final java.lang.String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final java.lang.String TTC_INDEX = "font_ttc_index";
        public static final java.lang.String VARIATION_SETTINGS = "font_variation_settings";
        public static final java.lang.String WEIGHT = "font_weight";

        private Columns() {
        }
    }

    private FontsContract() {
    }

    public static void setApplicationContextForResources(android.content.Context context) {
        sContext = context.getApplicationContext();
    }

    @java.lang.Deprecated
    public static class FontInfo {
        private final android.graphics.fonts.FontVariationAxis[] mAxes;
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final android.net.Uri mUri;
        private final int mWeight;

        public FontInfo(android.net.Uri uri, int i, android.graphics.fonts.FontVariationAxis[] fontVariationAxisArr, int i2, boolean z, int i3) {
            this.mUri = (android.net.Uri) com.android.internal.util.Preconditions.checkNotNull(uri);
            this.mTtcIndex = i;
            this.mAxes = fontVariationAxisArr;
            this.mWeight = i2;
            this.mItalic = z;
            this.mResultCode = i3;
        }

        public android.net.Uri getUri() {
            return this.mUri;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public android.graphics.fonts.FontVariationAxis[] getAxes() {
            return this.mAxes;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public int getResultCode() {
            return this.mResultCode;
        }
    }

    @java.lang.Deprecated
    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_REJECTED = 3;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        private final android.provider.FontsContract.FontInfo[] mFonts;
        private final int mStatusCode;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface FontResultStatus {
        }

        public FontFamilyResult(int i, android.provider.FontsContract.FontInfo[] fontInfoArr) {
            this.mStatusCode = i;
            this.mFonts = fontInfoArr;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public android.provider.FontsContract.FontInfo[] getFonts() {
            return this.mFonts;
        }
    }

    public static android.graphics.Typeface getFontSync(final android.provider.FontRequest fontRequest) {
        final java.lang.String identifier = fontRequest.getIdentifier();
        android.graphics.Typeface typeface = sTypefaceCache.get(identifier);
        if (typeface != null) {
            return typeface;
        }
        android.util.Log.w(TAG, "Platform version of downloadable fonts is deprecated. Please use androidx version instead.");
        synchronized (sLock) {
            android.graphics.Typeface typeface2 = sTypefaceCache.get(identifier);
            if (typeface2 != null) {
                return typeface2;
            }
            if (sHandler == null) {
                sThread = new android.os.HandlerThread("fonts", -2);
                sThread.start();
                sHandler = new android.os.Handler(sThread.getLooper());
            }
            final java.util.concurrent.locks.ReentrantLock reentrantLock = new java.util.concurrent.locks.ReentrantLock();
            final java.util.concurrent.locks.Condition newCondition = reentrantLock.newCondition();
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(true);
            final java.util.concurrent.atomic.AtomicBoolean atomicBoolean2 = new java.util.concurrent.atomic.AtomicBoolean(false);
            sHandler.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    android.provider.FontsContract.lambda$getFontSync$0(android.provider.FontRequest.this, identifier, atomicReference, reentrantLock, atomicBoolean2, atomicBoolean, newCondition);
                }
            });
            sHandler.removeCallbacks(sReplaceDispatcherThreadRunnable);
            sHandler.postDelayed(sReplaceDispatcherThreadRunnable, android.app.job.JobInfo.MIN_BACKOFF_MILLIS);
            long nanos = java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(SYNC_FONT_FETCH_TIMEOUT_MS);
            reentrantLock.lock();
            try {
                if (!atomicBoolean.get()) {
                    return (android.graphics.Typeface) atomicReference.get();
                }
                long j = nanos;
                do {
                    try {
                        j = newCondition.awaitNanos(j);
                    } catch (java.lang.InterruptedException e) {
                    }
                    if (!atomicBoolean.get()) {
                        return (android.graphics.Typeface) atomicReference.get();
                    }
                } while (j > 0);
                atomicBoolean2.set(true);
                android.util.Log.w(TAG, "Remote font fetch timed out: " + fontRequest.getProviderAuthority() + "/" + fontRequest.getQuery());
                return null;
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    static /* synthetic */ void lambda$getFontSync$0(android.provider.FontRequest fontRequest, java.lang.String str, java.util.concurrent.atomic.AtomicReference atomicReference, java.util.concurrent.locks.Lock lock, java.util.concurrent.atomic.AtomicBoolean atomicBoolean, java.util.concurrent.atomic.AtomicBoolean atomicBoolean2, java.util.concurrent.locks.Condition condition) {
        try {
            android.provider.FontsContract.FontFamilyResult fetchFonts = fetchFonts(sContext, null, fontRequest);
            if (fetchFonts.getStatusCode() == 0) {
                android.graphics.Typeface buildTypeface = buildTypeface(sContext, null, fetchFonts.getFonts());
                if (buildTypeface != null) {
                    sTypefaceCache.put(str, buildTypeface);
                }
                atomicReference.set(buildTypeface);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
        lock.lock();
        try {
            if (!atomicBoolean.get()) {
                atomicBoolean2.set(false);
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    @java.lang.Deprecated
    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface FontRequestFailReason {
        }

        public void onTypefaceRetrieved(android.graphics.Typeface typeface) {
        }

        public void onTypefaceRequestFailed(int i) {
        }
    }

    public static void requestFonts(final android.content.Context context, final android.provider.FontRequest fontRequest, android.os.Handler handler, final android.os.CancellationSignal cancellationSignal, final android.provider.FontsContract.FontRequestCallback fontRequestCallback) {
        final android.os.Handler handler2 = new android.os.Handler();
        final android.graphics.Typeface typeface = sTypefaceCache.get(fontRequest.getIdentifier());
        if (typeface != null) {
            handler2.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.provider.FontsContract.FontRequestCallback.this.onTypefaceRetrieved(typeface);
                }
            });
        } else {
            handler.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    android.provider.FontsContract.lambda$requestFonts$12(android.content.Context.this, cancellationSignal, fontRequest, handler2, fontRequestCallback);
                }
            });
        }
    }

    static /* synthetic */ void lambda$requestFonts$12(android.content.Context context, android.os.CancellationSignal cancellationSignal, android.provider.FontRequest fontRequest, android.os.Handler handler, final android.provider.FontsContract.FontRequestCallback fontRequestCallback) {
        try {
            android.provider.FontsContract.FontFamilyResult fetchFonts = fetchFonts(context, cancellationSignal, fontRequest);
            final android.graphics.Typeface typeface = sTypefaceCache.get(fontRequest.getIdentifier());
            if (typeface != null) {
                handler.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.provider.FontsContract.FontRequestCallback.this.onTypefaceRetrieved(typeface);
                    }
                });
                return;
            }
            if (fetchFonts.getStatusCode() != 0) {
                switch (fetchFonts.getStatusCode()) {
                    case 1:
                        handler.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.provider.FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-2);
                            }
                        });
                        break;
                    case 2:
                        handler.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.provider.FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-3);
                            }
                        });
                        break;
                    default:
                        handler.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.provider.FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-3);
                            }
                        });
                        break;
                }
                return;
            }
            android.provider.FontsContract.FontInfo[] fonts = fetchFonts.getFonts();
            if (fonts == null || fonts.length == 0) {
                handler.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.provider.FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(1);
                    }
                });
                return;
            }
            for (android.provider.FontsContract.FontInfo fontInfo : fonts) {
                if (fontInfo.getResultCode() != 0) {
                    final int resultCode = fontInfo.getResultCode();
                    if (resultCode < 0) {
                        handler.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.provider.FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-3);
                            }
                        });
                        return;
                    } else {
                        handler.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.provider.FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(resultCode);
                            }
                        });
                        return;
                    }
                }
            }
            final android.graphics.Typeface buildTypeface = buildTypeface(context, cancellationSignal, fonts);
            if (buildTypeface == null) {
                handler.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.provider.FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-3);
                    }
                });
            } else {
                sTypefaceCache.put(fontRequest.getIdentifier(), buildTypeface);
                handler.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.provider.FontsContract.FontRequestCallback.this.onTypefaceRetrieved(buildTypeface);
                    }
                });
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            handler.post(new java.lang.Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.provider.FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-1);
                }
            });
        }
    }

    public static android.provider.FontsContract.FontFamilyResult fetchFonts(android.content.Context context, android.os.CancellationSignal cancellationSignal, android.provider.FontRequest fontRequest) throws android.content.pm.PackageManager.NameNotFoundException {
        if (context.isRestricted()) {
            return new android.provider.FontsContract.FontFamilyResult(3, null);
        }
        android.content.pm.ProviderInfo provider = getProvider(context.getPackageManager(), fontRequest);
        if (provider == null) {
            return new android.provider.FontsContract.FontFamilyResult(1, null);
        }
        try {
            return new android.provider.FontsContract.FontFamilyResult(0, getFontFromProvider(context, fontRequest, provider.authority, cancellationSignal));
        } catch (java.lang.IllegalArgumentException e) {
            return new android.provider.FontsContract.FontFamilyResult(2, null);
        }
    }

    public static android.graphics.Typeface buildTypeface(android.content.Context context, android.os.CancellationSignal cancellationSignal, android.provider.FontsContract.FontInfo[] fontInfoArr) {
        int i;
        if (context.isRestricted()) {
            return null;
        }
        java.util.Map<android.net.Uri, java.nio.ByteBuffer> prepareFontData = prepareFontData(context, fontInfoArr, cancellationSignal);
        if (prepareFontData.isEmpty()) {
            return null;
        }
        int length = fontInfoArr.length;
        int i2 = 0;
        android.graphics.fonts.FontFamily.Builder builder = null;
        while (true) {
            i = 1;
            if (i2 >= length) {
                break;
            }
            android.provider.FontsContract.FontInfo fontInfo = fontInfoArr[i2];
            java.nio.ByteBuffer byteBuffer = prepareFontData.get(fontInfo.getUri());
            if (byteBuffer != null) {
                try {
                    android.graphics.fonts.Font.Builder weight = new android.graphics.fonts.Font.Builder(byteBuffer).setWeight(fontInfo.getWeight());
                    if (!fontInfo.isItalic()) {
                        i = 0;
                    }
                    android.graphics.fonts.Font build = weight.setSlant(i).setTtcIndex(fontInfo.getTtcIndex()).setFontVariationSettings(fontInfo.getAxes()).build();
                    if (builder == null) {
                        builder = new android.graphics.fonts.FontFamily.Builder(build);
                    } else {
                        builder.addFont(build);
                    }
                } catch (java.io.IOException e) {
                } catch (java.lang.IllegalArgumentException e2) {
                    return null;
                }
            }
            i2++;
        }
        if (builder == null) {
            return null;
        }
        android.graphics.fonts.FontFamily build2 = builder.build();
        android.graphics.fonts.FontStyle fontStyle = new android.graphics.fonts.FontStyle(400, 0);
        android.graphics.fonts.Font font = build2.getFont(0);
        int matchScore = fontStyle.getMatchScore(font.getStyle());
        while (i < build2.getSize()) {
            android.graphics.fonts.Font font2 = build2.getFont(i);
            int matchScore2 = fontStyle.getMatchScore(font2.getStyle());
            if (matchScore2 < matchScore) {
                font = font2;
                matchScore = matchScore2;
            }
            i++;
        }
        return new android.graphics.Typeface.CustomFallbackBuilder(build2).setStyle(font.getStyle()).build();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static java.util.Map<android.net.Uri, java.nio.ByteBuffer> prepareFontData(android.content.Context context, android.provider.FontsContract.FontInfo[] fontInfoArr, android.os.CancellationSignal cancellationSignal) {
        java.util.HashMap hashMap = new java.util.HashMap();
        android.content.ContentResolver contentResolver = context.getContentResolver();
        for (android.provider.FontsContract.FontInfo fontInfo : fontInfoArr) {
            if (fontInfo.getResultCode() == 0) {
                android.net.Uri uri = fontInfo.getUri();
                if (!hashMap.containsKey(uri)) {
                    java.nio.MappedByteBuffer mappedByteBuffer = null;
                    try {
                        android.os.ParcelFileDescriptor openFileDescriptor = contentResolver.openFileDescriptor(uri, "r", cancellationSignal);
                        if (openFileDescriptor != null) {
                            try {
                                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(openFileDescriptor.getFileDescriptor());
                                try {
                                    java.nio.channels.FileChannel channel = fileInputStream.getChannel();
                                    mappedByteBuffer = channel.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                                    fileInputStream.close();
                                } finally {
                                }
                            } catch (java.io.IOException e) {
                            } catch (java.lang.Throwable th) {
                                if (openFileDescriptor == null) {
                                    throw th;
                                }
                                try {
                                    openFileDescriptor.close();
                                    throw th;
                                } catch (java.lang.Throwable th2) {
                                    th.addSuppressed(th2);
                                    throw th;
                                }
                            }
                        }
                        if (openFileDescriptor != null) {
                            openFileDescriptor.close();
                        }
                    } catch (java.io.IOException e2) {
                    }
                    hashMap.put(uri, mappedByteBuffer);
                }
            }
        }
        return java.util.Collections.unmodifiableMap(hashMap);
    }

    public static android.content.pm.ProviderInfo getProvider(android.content.pm.PackageManager packageManager, android.provider.FontRequest fontRequest) throws android.content.pm.PackageManager.NameNotFoundException {
        java.lang.String providerAuthority = fontRequest.getProviderAuthority();
        android.content.pm.ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(providerAuthority, 0);
        if (resolveContentProvider == null) {
            throw new android.content.pm.PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        }
        if (!resolveContentProvider.packageName.equals(fontRequest.getProviderPackage())) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + fontRequest.getProviderPackage());
        }
        if (resolveContentProvider.applicationInfo.isSystemApp()) {
            return resolveContentProvider;
        }
        java.util.List<byte[]> convertToByteArrayList = convertToByteArrayList(packageManager.getPackageInfo(resolveContentProvider.packageName, 64).signatures);
        java.util.Collections.sort(convertToByteArrayList, sByteArrayComparator);
        java.util.List<java.util.List<byte[]>> certificates = fontRequest.getCertificates();
        for (int i = 0; i < certificates.size(); i++) {
            java.util.ArrayList arrayList = new java.util.ArrayList(certificates.get(i));
            java.util.Collections.sort(arrayList, sByteArrayComparator);
            if (equalsByteArrayList(convertToByteArrayList, arrayList)) {
                return resolveContentProvider;
            }
        }
        return null;
    }

    static /* synthetic */ int lambda$static$13(byte[] bArr, byte[] bArr2) {
        if (bArr.length != bArr2.length) {
            return bArr.length - bArr2.length;
        }
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return bArr[i] - bArr2[i];
            }
        }
        return 0;
    }

    private static boolean equalsByteArrayList(java.util.List<byte[]> list, java.util.List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!java.util.Arrays.equals(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static java.util.List<byte[]> convertToByteArrayList(android.content.pm.Signature[] signatureArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.content.pm.Signature signature : signatureArr) {
            arrayList.add(signature.toByteArray());
        }
        return arrayList;
    }

    public static android.provider.FontsContract.FontInfo[] getFontFromProvider(android.content.Context context, android.provider.FontRequest fontRequest, java.lang.String str, android.os.CancellationSignal cancellationSignal) {
        android.net.Uri withAppendedId;
        int i;
        boolean z;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.net.Uri build = new android.net.Uri.Builder().scheme("content").authority(str).build();
        android.net.Uri build2 = new android.net.Uri.Builder().scheme("content").authority(str).appendPath("file").build();
        android.database.Cursor query = context.getContentResolver().query(build, new java.lang.String[]{"_id", android.provider.FontsContract.Columns.FILE_ID, android.provider.FontsContract.Columns.TTC_INDEX, android.provider.FontsContract.Columns.VARIATION_SETTINGS, android.provider.FontsContract.Columns.WEIGHT, android.provider.FontsContract.Columns.ITALIC, android.provider.FontsContract.Columns.RESULT_CODE}, "query = ?", new java.lang.String[]{fontRequest.getQuery()}, null, cancellationSignal);
        if (query != null) {
            try {
                if (query.getCount() > 0) {
                    int columnIndex = query.getColumnIndex(android.provider.FontsContract.Columns.RESULT_CODE);
                    java.util.ArrayList arrayList2 = new java.util.ArrayList();
                    int columnIndexOrThrow = query.getColumnIndexOrThrow("_id");
                    int columnIndex2 = query.getColumnIndex(android.provider.FontsContract.Columns.FILE_ID);
                    int columnIndex3 = query.getColumnIndex(android.provider.FontsContract.Columns.TTC_INDEX);
                    int columnIndex4 = query.getColumnIndex(android.provider.FontsContract.Columns.VARIATION_SETTINGS);
                    int columnIndex5 = query.getColumnIndex(android.provider.FontsContract.Columns.WEIGHT);
                    int columnIndex6 = query.getColumnIndex(android.provider.FontsContract.Columns.ITALIC);
                    while (query.moveToNext()) {
                        int i2 = columnIndex != -1 ? query.getInt(columnIndex) : 0;
                        int i3 = columnIndex3 != -1 ? query.getInt(columnIndex3) : 0;
                        java.lang.String string = columnIndex4 != -1 ? query.getString(columnIndex4) : null;
                        if (columnIndex2 == -1) {
                            withAppendedId = android.content.ContentUris.withAppendedId(build, query.getLong(columnIndexOrThrow));
                        } else {
                            withAppendedId = android.content.ContentUris.withAppendedId(build2, query.getLong(columnIndex2));
                        }
                        if (columnIndex5 != -1 && columnIndex6 != -1) {
                            int i4 = query.getInt(columnIndex5);
                            boolean z2 = true;
                            if (query.getInt(columnIndex6) != 1) {
                                z2 = false;
                            }
                            z = z2;
                            i = i4;
                        } else {
                            i = 400;
                            z = false;
                        }
                        arrayList2.add(new android.provider.FontsContract.FontInfo(withAppendedId, i3, android.graphics.fonts.FontVariationAxis.fromFontVariationSettings(string), i, z, i2));
                    }
                    arrayList = arrayList2;
                }
            } finally {
            }
        }
        if (query != null) {
            query.close();
        }
        return (android.provider.FontsContract.FontInfo[]) arrayList.toArray(new android.provider.FontsContract.FontInfo[0]);
    }
}
