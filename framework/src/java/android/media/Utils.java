package android.media;

/* loaded from: classes2.dex */
public class Utils {
    private static final java.lang.String TAG = "Utils";

    public static <T extends java.lang.Comparable<? super T>> void sortDistinctRanges(android.util.Range<T>[] rangeArr) {
        java.util.Arrays.sort(rangeArr, new java.util.Comparator<android.util.Range<T>>() { // from class: android.media.Utils.1
            @Override // java.util.Comparator
            public int compare(android.util.Range<T> range, android.util.Range<T> range2) {
                if (range.getUpper().compareTo(range2.getLower()) < 0) {
                    return -1;
                }
                if (range.getLower().compareTo(range2.getUpper()) > 0) {
                    return 1;
                }
                throw new java.lang.IllegalArgumentException("sample rate ranges must be distinct (" + range + " and " + range2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        });
    }

    public static <T extends java.lang.Comparable<? super T>> android.util.Range<T>[] intersectSortedDistinctRanges(android.util.Range<T>[] rangeArr, android.util.Range<T>[] rangeArr2) {
        java.util.Vector vector = new java.util.Vector();
        int i = 0;
        for (android.util.Range<T> range : rangeArr2) {
            while (i < rangeArr.length && rangeArr[i].getUpper().compareTo(range.getLower()) < 0) {
                i++;
            }
            while (i < rangeArr.length && rangeArr[i].getUpper().compareTo(range.getUpper()) < 0) {
                vector.add(range.intersect(rangeArr[i]));
                i++;
            }
            if (i == rangeArr.length) {
                break;
            }
            if (rangeArr[i].getLower().compareTo(range.getUpper()) <= 0) {
                vector.add(range.intersect(rangeArr[i]));
            }
        }
        return (android.util.Range[]) vector.toArray(new android.util.Range[vector.size()]);
    }

    public static <T extends java.lang.Comparable<? super T>> int binarySearchDistinctRanges(android.util.Range<T>[] rangeArr, T t) {
        return java.util.Arrays.binarySearch(rangeArr, android.util.Range.create(t, t), new java.util.Comparator<android.util.Range<T>>() { // from class: android.media.Utils.2
            @Override // java.util.Comparator
            public int compare(android.util.Range<T> range, android.util.Range<T> range2) {
                if (range.getUpper().compareTo(range2.getLower()) < 0) {
                    return -1;
                }
                if (range.getLower().compareTo(range2.getUpper()) > 0) {
                    return 1;
                }
                return 0;
            }
        });
    }

    static int gcd(int i, int i2) {
        if (i == 0 && i2 == 0) {
            return 1;
        }
        if (i2 < 0) {
            i2 = -i2;
        }
        if (i < 0) {
            i = -i;
        }
        while (i != 0) {
            int i3 = i2 % i;
            i2 = i;
            i = i3;
        }
        return i2;
    }

    static android.util.Range<java.lang.Integer> factorRange(android.util.Range<java.lang.Integer> range, int i) {
        if (i == 1) {
            return range;
        }
        return android.util.Range.create(java.lang.Integer.valueOf(divUp(range.getLower().intValue(), i)), java.lang.Integer.valueOf(range.getUpper().intValue() / i));
    }

    static android.util.Range<java.lang.Long> factorRange(android.util.Range<java.lang.Long> range, long j) {
        if (j == 1) {
            return range;
        }
        return android.util.Range.create(java.lang.Long.valueOf(divUp(range.getLower().longValue(), j)), java.lang.Long.valueOf(range.getUpper().longValue() / j));
    }

    private static android.util.Rational scaleRatio(android.util.Rational rational, int i, int i2) {
        int gcd = gcd(i, i2);
        return new android.util.Rational((int) (rational.getNumerator() * (i / gcd)), (int) (rational.getDenominator() * (i2 / gcd)));
    }

    static android.util.Range<android.util.Rational> scaleRange(android.util.Range<android.util.Rational> range, int i, int i2) {
        if (i == i2) {
            return range;
        }
        return android.util.Range.create(scaleRatio(range.getLower(), i, i2), scaleRatio(range.getUpper(), i, i2));
    }

    static android.util.Range<java.lang.Integer> alignRange(android.util.Range<java.lang.Integer> range, int i) {
        return range.intersect(java.lang.Integer.valueOf(divUp(range.getLower().intValue(), i) * i), java.lang.Integer.valueOf((range.getUpper().intValue() / i) * i));
    }

    static int divUp(int i, int i2) {
        return ((i + i2) - 1) / i2;
    }

    static long divUp(long j, long j2) {
        return ((j + j2) - 1) / j2;
    }

    private static long lcm(int i, int i2) {
        if (i == 0 || i2 == 0) {
            throw new java.lang.IllegalArgumentException("lce is not defined for zero arguments");
        }
        return (i * i2) / gcd(i, i2);
    }

    static android.util.Range<java.lang.Integer> intRangeFor(double d) {
        return android.util.Range.create(java.lang.Integer.valueOf((int) d), java.lang.Integer.valueOf((int) java.lang.Math.ceil(d)));
    }

    static android.util.Range<java.lang.Long> longRangeFor(double d) {
        return android.util.Range.create(java.lang.Long.valueOf((long) d), java.lang.Long.valueOf((long) java.lang.Math.ceil(d)));
    }

    static android.util.Size parseSize(java.lang.Object obj, android.util.Size size) {
        if (obj == null) {
            return size;
        }
        try {
            return android.util.Size.parseSize((java.lang.String) obj);
        } catch (java.lang.ClassCastException | java.lang.NumberFormatException e) {
            android.util.Log.w(TAG, "could not parse size '" + obj + "'");
            return size;
        }
    }

    static int parseIntSafely(java.lang.Object obj, int i) {
        if (obj == null) {
            return i;
        }
        try {
            return java.lang.Integer.parseInt((java.lang.String) obj);
        } catch (java.lang.ClassCastException | java.lang.NumberFormatException e) {
            android.util.Log.w(TAG, "could not parse integer '" + obj + "'");
            return i;
        }
    }

    static android.util.Range<java.lang.Integer> parseIntRange(java.lang.Object obj, android.util.Range<java.lang.Integer> range) {
        if (obj == null) {
            return range;
        }
        try {
            java.lang.String str = (java.lang.String) obj;
            int indexOf = str.indexOf(45);
            if (indexOf >= 0) {
                return android.util.Range.create(java.lang.Integer.valueOf(java.lang.Integer.parseInt(str.substring(0, indexOf), 10)), java.lang.Integer.valueOf(java.lang.Integer.parseInt(str.substring(indexOf + 1), 10)));
            }
            int parseInt = java.lang.Integer.parseInt(str);
            return android.util.Range.create(java.lang.Integer.valueOf(parseInt), java.lang.Integer.valueOf(parseInt));
        } catch (java.lang.ClassCastException | java.lang.NumberFormatException | java.lang.IllegalArgumentException e) {
            android.util.Log.w(TAG, "could not parse integer range '" + obj + "'");
            return range;
        }
    }

    static android.util.Range<java.lang.Long> parseLongRange(java.lang.Object obj, android.util.Range<java.lang.Long> range) {
        if (obj == null) {
            return range;
        }
        try {
            java.lang.String str = (java.lang.String) obj;
            int indexOf = str.indexOf(45);
            if (indexOf >= 0) {
                return android.util.Range.create(java.lang.Long.valueOf(java.lang.Long.parseLong(str.substring(0, indexOf), 10)), java.lang.Long.valueOf(java.lang.Long.parseLong(str.substring(indexOf + 1), 10)));
            }
            long parseLong = java.lang.Long.parseLong(str);
            return android.util.Range.create(java.lang.Long.valueOf(parseLong), java.lang.Long.valueOf(parseLong));
        } catch (java.lang.ClassCastException | java.lang.NumberFormatException | java.lang.IllegalArgumentException e) {
            android.util.Log.w(TAG, "could not parse long range '" + obj + "'");
            return range;
        }
    }

    static android.util.Range<android.util.Rational> parseRationalRange(java.lang.Object obj, android.util.Range<android.util.Rational> range) {
        if (obj == null) {
            return range;
        }
        try {
            java.lang.String str = (java.lang.String) obj;
            int indexOf = str.indexOf(45);
            if (indexOf >= 0) {
                return android.util.Range.create(android.util.Rational.parseRational(str.substring(0, indexOf)), android.util.Rational.parseRational(str.substring(indexOf + 1)));
            }
            android.util.Rational parseRational = android.util.Rational.parseRational(str);
            return android.util.Range.create(parseRational, parseRational);
        } catch (java.lang.ClassCastException | java.lang.NumberFormatException | java.lang.IllegalArgumentException e) {
            android.util.Log.w(TAG, "could not parse rational range '" + obj + "'");
            return range;
        }
    }

    static android.util.Pair<android.util.Size, android.util.Size> parseSizeRange(java.lang.Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            java.lang.String str = (java.lang.String) obj;
            int indexOf = str.indexOf(45);
            if (indexOf >= 0) {
                return android.util.Pair.create(android.util.Size.parseSize(str.substring(0, indexOf)), android.util.Size.parseSize(str.substring(indexOf + 1)));
            }
            android.util.Size parseSize = android.util.Size.parseSize(str);
            return android.util.Pair.create(parseSize, parseSize);
        } catch (java.lang.ClassCastException | java.lang.NumberFormatException | java.lang.IllegalArgumentException e) {
            android.util.Log.w(TAG, "could not parse size range '" + obj + "'");
            return null;
        }
    }

    public static java.io.File getUniqueExternalFile(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        java.io.File externalStoragePublicDirectory = android.os.Environment.getExternalStoragePublicDirectory(str);
        externalStoragePublicDirectory.mkdirs();
        try {
            return android.os.FileUtils.buildUniqueFile(externalStoragePublicDirectory, str3, str2);
        } catch (java.io.FileNotFoundException e) {
            android.util.Log.e(TAG, "Unable to get a unique file name: " + e);
            return null;
        }
    }

    static java.lang.String getFileDisplayNameFromUri(android.content.Context context, android.net.Uri uri) {
        java.lang.String scheme = uri.getScheme();
        if ("file".equals(scheme)) {
            return uri.getLastPathSegment();
        }
        if ("content".equals(scheme)) {
            android.database.Cursor query = context.getContentResolver().query(uri, new java.lang.String[]{"_display_name"}, null, null, null);
            if (query != null) {
                try {
                    if (query.getCount() != 0) {
                        query.moveToFirst();
                        java.lang.String string = query.getString(query.getColumnIndex("_display_name"));
                        if (query != null) {
                            query.close();
                        }
                        return string;
                    }
                } catch (java.lang.Throwable th) {
                    if (query != null) {
                        try {
                            query.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
        }
        return uri.toString();
    }

    public static class ListenerList<V> {
        private final boolean mClearCallingIdentity;
        private final boolean mForceRemoveConsistency;
        private java.util.HashMap<java.lang.Object, android.media.Utils.ListenerList.ListenerWithCancellation<V>> mListeners;
        private final boolean mRestrictSingleCallerOnEvent;

        public interface Listener<V> {
            void onEvent(int i, V v);
        }

        private interface ListenerWithCancellation<V> extends android.media.Utils.ListenerList.Listener<V> {
            void cancel();
        }

        public ListenerList() {
            this(true, true, false);
        }

        public ListenerList(boolean z, boolean z2, boolean z3) {
            this.mListeners = new java.util.HashMap<>();
            this.mRestrictSingleCallerOnEvent = z;
            this.mClearCallingIdentity = z2;
            this.mForceRemoveConsistency = z3;
        }

        public void add(java.lang.Object obj, java.util.concurrent.Executor executor, android.media.Utils.ListenerList.Listener<V> listener) {
            java.util.Objects.requireNonNull(obj);
            java.util.Objects.requireNonNull(executor);
            java.util.Objects.requireNonNull(listener);
            android.media.Utils.ListenerList.AnonymousClass1 anonymousClass1 = new android.media.Utils.ListenerList.AnonymousClass1(executor, listener);
            synchronized (this.mListeners) {
                this.mListeners.put(obj, anonymousClass1);
            }
        }

        /* renamed from: android.media.Utils$ListenerList$1, reason: invalid class name */
        class AnonymousClass1 implements android.media.Utils.ListenerList.ListenerWithCancellation<V> {
            final /* synthetic */ java.util.concurrent.Executor val$executor;
            final /* synthetic */ android.media.Utils.ListenerList.Listener val$listener;
            private final java.lang.Object mLock = new java.lang.Object();
            private volatile boolean mCancelled = false;

            AnonymousClass1(java.util.concurrent.Executor executor, android.media.Utils.ListenerList.Listener listener) {
                this.val$executor = executor;
                this.val$listener = listener;
            }

            @Override // android.media.Utils.ListenerList.Listener
            public void onEvent(final int i, final V v) {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.media.Utils.ListenerList.Listener listener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.media.Utils$ListenerList$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.Utils.ListenerList.AnonymousClass1.this.lambda$onEvent$0(listener, i, v);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onEvent$0(android.media.Utils.ListenerList.Listener listener, int i, java.lang.Object obj) {
                if (android.media.Utils.ListenerList.this.mRestrictSingleCallerOnEvent || android.media.Utils.ListenerList.this.mForceRemoveConsistency) {
                    synchronized (this.mLock) {
                        if (this.mCancelled) {
                            return;
                        }
                        listener.onEvent(i, obj);
                        return;
                    }
                }
                if (this.mCancelled) {
                    return;
                }
                listener.onEvent(i, obj);
            }

            @Override // android.media.Utils.ListenerList.ListenerWithCancellation
            public void cancel() {
                if (android.media.Utils.ListenerList.this.mForceRemoveConsistency) {
                    synchronized (this.mLock) {
                        this.mCancelled = true;
                    }
                    return;
                }
                this.mCancelled = true;
            }
        }

        public void remove(java.lang.Object obj) {
            java.util.Objects.requireNonNull(obj);
            synchronized (this.mListeners) {
                android.media.Utils.ListenerList.ListenerWithCancellation<V> listenerWithCancellation = this.mListeners.get(obj);
                if (listenerWithCancellation == null) {
                    return;
                }
                this.mListeners.remove(obj);
                listenerWithCancellation.cancel();
            }
        }

        public void notify(int i, V v) {
            synchronized (this.mListeners) {
                if (this.mListeners.size() == 0) {
                    return;
                }
                java.lang.Object[] array = this.mListeners.values().toArray();
                java.lang.Long valueOf = this.mClearCallingIdentity ? java.lang.Long.valueOf(android.os.Binder.clearCallingIdentity()) : null;
                try {
                    for (java.lang.Object obj : array) {
                        ((android.media.Utils.ListenerList.ListenerWithCancellation) obj).onEvent(i, v);
                    }
                } finally {
                    if (valueOf != null) {
                        android.os.Binder.restoreCallingIdentity(valueOf.longValue());
                    }
                }
            }
        }
    }

    public static java.lang.String anonymizeBluetoothAddress(java.lang.String str) {
        if (str == null) {
            return null;
        }
        if (str.length() != "AA:BB:CC:DD:EE:FF".length()) {
            return str;
        }
        return "XX:XX:XX:XX" + str.substring("XX:XX:XX:XX".length());
    }

    public static java.lang.String anonymizeBluetoothAddress(int i, java.lang.String str) {
        if (!android.media.AudioSystem.isBluetoothDevice(i)) {
            return str;
        }
        return anonymizeBluetoothAddress(str);
    }
}
