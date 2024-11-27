package com.android.internal.util;

/* loaded from: classes5.dex */
public class RingBuffer<T> {
    private final T[] mBuffer;
    private long mCursor;
    private final java.util.function.Supplier<T> mNewItem;

    @java.lang.Deprecated
    public RingBuffer(final java.lang.Class<T> cls, int i) {
        this(new java.util.function.Supplier() { // from class: com.android.internal.util.RingBuffer$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Object createNewItem;
                createNewItem = com.android.internal.util.RingBuffer.createNewItem(cls);
                return createNewItem;
            }
        }, new java.util.function.IntFunction() { // from class: com.android.internal.util.RingBuffer$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i2) {
                return com.android.internal.util.RingBuffer.lambda$new$1(cls, i2);
            }
        }, i);
    }

    static /* synthetic */ java.lang.Object[] lambda$new$1(java.lang.Class cls, int i) {
        return (java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.Object createNewItem(java.lang.Class cls) {
        try {
            return cls.getDeclaredConstructor(new java.lang.Class[0]).newInstance(new java.lang.Object[0]);
        } catch (java.lang.IllegalAccessException | java.lang.InstantiationException | java.lang.NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
            return null;
        }
    }

    public RingBuffer(java.util.function.Supplier<T> supplier, java.util.function.IntFunction<T[]> intFunction, int i) {
        this.mCursor = 0L;
        com.android.internal.util.Preconditions.checkArgumentPositive(i, "A RingBuffer cannot have 0 capacity");
        this.mBuffer = intFunction.apply(i);
        this.mNewItem = supplier;
    }

    public int size() {
        return (int) java.lang.Math.min(this.mBuffer.length, this.mCursor);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        for (int i = 0; i < size(); i++) {
            this.mBuffer[i] = null;
        }
        this.mCursor = 0L;
    }

    public void append(T t) {
        T[] tArr = this.mBuffer;
        long j = this.mCursor;
        this.mCursor = 1 + j;
        tArr[indexOf(j)] = t;
    }

    public T getNextSlot() {
        long j = this.mCursor;
        this.mCursor = 1 + j;
        int indexOf = indexOf(j);
        if (this.mBuffer[indexOf] == null) {
            this.mBuffer[indexOf] = this.mNewItem.get();
        }
        return this.mBuffer[indexOf];
    }

    public T[] toArray() {
        T[] tArr = (T[]) java.util.Arrays.copyOf(this.mBuffer, size(), this.mBuffer.getClass());
        long j = this.mCursor - 1;
        int length = tArr.length - 1;
        while (length >= 0) {
            tArr[length] = this.mBuffer[indexOf(j)];
            length--;
            j--;
        }
        return tArr;
    }

    private int indexOf(long j) {
        return (int) java.lang.Math.abs(j % this.mBuffer.length);
    }
}
