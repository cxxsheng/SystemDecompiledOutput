package com.android.internal.util.function.pooled;

/* loaded from: classes5.dex */
final class PooledLambdaImpl<R> extends com.android.internal.util.function.pooled.OmniFunction<java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, R> {
    private static final boolean DEBUG = false;
    private static final int FLAG_ACQUIRED_FROM_MESSAGE_CALLBACKS_POOL = 8192;
    private static final int FLAG_RECYCLED = 2048;
    private static final int FLAG_RECYCLE_ON_USE = 4096;
    private static final java.lang.String LOG_TAG = "PooledLambdaImpl";
    static final int MASK_EXPOSED_AS = 2080768;
    static final int MASK_FUNC_TYPE = 266338304;
    private static final int MAX_ARGS = 11;
    private static final int MAX_POOL_SIZE = 50;
    long mConstValue;
    java.lang.Object mFunc;
    static final com.android.internal.util.function.pooled.PooledLambdaImpl.Pool sPool = new com.android.internal.util.function.pooled.PooledLambdaImpl.Pool(new java.lang.Object());
    static final com.android.internal.util.function.pooled.PooledLambdaImpl.Pool sMessageCallbacksPool = new com.android.internal.util.function.pooled.PooledLambdaImpl.Pool(android.os.Message.sPoolSync);
    java.lang.Object[] mArgs = null;
    int mFlags = 0;

    static class Pool extends android.util.Pools.SynchronizedPool<com.android.internal.util.function.pooled.PooledLambdaImpl> {
        public Pool(java.lang.Object obj) {
            super(50, obj);
        }
    }

    private PooledLambdaImpl() {
    }

    @Override // com.android.internal.util.function.pooled.PooledLambda
    public void recycle() {
        if (!isRecycled()) {
            doRecycle();
        }
    }

    private void doRecycle() {
        com.android.internal.util.function.pooled.PooledLambdaImpl.Pool pool;
        if ((this.mFlags & 8192) != 0) {
            pool = sMessageCallbacksPool;
        } else {
            pool = sPool;
        }
        this.mFunc = null;
        if (this.mArgs != null) {
            java.util.Arrays.fill(this.mArgs, (java.lang.Object) null);
        }
        this.mFlags = 2048;
        this.mConstValue = 0L;
        pool.release(this);
    }

    @Override // com.android.internal.util.function.pooled.OmniFunction
    R invoke(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9, java.lang.Object obj10, java.lang.Object obj11) {
        checkNotRecycled();
        if (!fillInArg(obj) || !fillInArg(obj2) || !fillInArg(obj3) || !fillInArg(obj4) || !fillInArg(obj5) || !fillInArg(obj6) || !fillInArg(obj7) || !fillInArg(obj8) || !fillInArg(obj9) || !fillInArg(obj10) || fillInArg(obj11)) {
        }
        int decodeArgCount = com.android.internal.util.function.pooled.PooledLambdaImpl.LambdaType.decodeArgCount(getFlags(MASK_FUNC_TYPE));
        int i = 0;
        if (decodeArgCount != 15) {
            for (int i2 = 0; i2 < decodeArgCount; i2++) {
                if (this.mArgs[i2] == com.android.internal.util.function.pooled.ArgumentPlaceholder.INSTANCE) {
                    throw new java.lang.IllegalStateException("Missing argument #" + i2 + " among " + java.util.Arrays.toString(this.mArgs));
                }
            }
        }
        try {
            R doInvoke = doInvoke();
            if (isRecycleOnUse()) {
                doRecycle();
            } else if (!isRecycled()) {
                int size = com.android.internal.util.ArrayUtils.size(this.mArgs);
                while (i < size) {
                    popArg(i);
                    i++;
                }
            }
            return doInvoke;
        } catch (java.lang.Throwable th) {
            if (isRecycleOnUse()) {
                doRecycle();
            } else if (!isRecycled()) {
                int size2 = com.android.internal.util.ArrayUtils.size(this.mArgs);
                while (i < size2) {
                    popArg(i);
                    i++;
                }
            }
            throw th;
        }
    }

    private boolean fillInArg(java.lang.Object obj) {
        int size = com.android.internal.util.ArrayUtils.size(this.mArgs);
        for (int i = 0; i < size; i++) {
            if (this.mArgs[i] == com.android.internal.util.function.pooled.ArgumentPlaceholder.INSTANCE) {
                this.mArgs[i] = obj;
                this.mFlags = (int) (this.mFlags | com.android.internal.util.BitUtils.bitAt(i));
                return true;
            }
        }
        if (obj == null || obj == com.android.internal.util.function.pooled.ArgumentPlaceholder.INSTANCE) {
            return false;
        }
        throw new java.lang.IllegalStateException("No more arguments expected for provided arg " + obj + " among " + java.util.Arrays.toString(this.mArgs));
    }

    private void checkNotRecycled() {
        if (isRecycled()) {
            throw new java.lang.IllegalStateException("Instance is recycled: " + this);
        }
    }

    private R doInvoke() {
        int flags = getFlags(MASK_FUNC_TYPE);
        int decodeArgCount = com.android.internal.util.function.pooled.PooledLambdaImpl.LambdaType.decodeArgCount(flags);
        int decodeReturnType = com.android.internal.util.function.pooled.PooledLambdaImpl.LambdaType.decodeReturnType(flags);
        switch (decodeArgCount) {
            case 0:
                switch (decodeReturnType) {
                    case 1:
                        ((java.lang.Runnable) this.mFunc).run();
                        return null;
                    case 2:
                    case 3:
                        return (R) ((java.util.function.Supplier) this.mFunc).get();
                }
            case 1:
                switch (decodeReturnType) {
                    case 1:
                        ((java.util.function.Consumer) this.mFunc).accept(popArg(0));
                        return null;
                    case 2:
                        return (R) java.lang.Boolean.valueOf(((java.util.function.Predicate) this.mFunc).test(popArg(0)));
                    case 3:
                        return (R) ((java.util.function.Function) this.mFunc).apply(popArg(0));
                }
            case 2:
                switch (decodeReturnType) {
                    case 1:
                        ((java.util.function.BiConsumer) this.mFunc).accept(popArg(0), popArg(1));
                        return null;
                    case 2:
                        return (R) java.lang.Boolean.valueOf(((java.util.function.BiPredicate) this.mFunc).test(popArg(0), popArg(1)));
                    case 3:
                        return (R) ((java.util.function.BiFunction) this.mFunc).apply(popArg(0), popArg(1));
                }
            case 3:
                switch (decodeReturnType) {
                    case 1:
                        ((com.android.internal.util.function.TriConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2));
                        return null;
                    case 2:
                        return (R) java.lang.Boolean.valueOf(((com.android.internal.util.function.TriPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2)));
                    case 3:
                        return (R) ((com.android.internal.util.function.TriFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2));
                }
            case 4:
                switch (decodeReturnType) {
                    case 1:
                        ((com.android.internal.util.function.QuadConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3));
                        return null;
                    case 2:
                        return (R) java.lang.Boolean.valueOf(((com.android.internal.util.function.QuadPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3)));
                    case 3:
                        return (R) ((com.android.internal.util.function.QuadFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3));
                }
            case 5:
                switch (decodeReturnType) {
                    case 1:
                        ((com.android.internal.util.function.QuintConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4));
                        return null;
                    case 2:
                        return (R) java.lang.Boolean.valueOf(((com.android.internal.util.function.QuintPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4)));
                    case 3:
                        return (R) ((com.android.internal.util.function.QuintFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4));
                }
            case 6:
                switch (decodeReturnType) {
                    case 1:
                        ((com.android.internal.util.function.HexConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5));
                        return null;
                    case 2:
                        return (R) java.lang.Boolean.valueOf(((com.android.internal.util.function.HexPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5)));
                    case 3:
                        return (R) ((com.android.internal.util.function.HexFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5));
                }
            case 7:
                switch (decodeReturnType) {
                    case 1:
                        ((com.android.internal.util.function.HeptConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6));
                        return null;
                    case 2:
                        return (R) java.lang.Boolean.valueOf(((com.android.internal.util.function.HeptPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6)));
                    case 3:
                        return (R) ((com.android.internal.util.function.HeptFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6));
                }
            case 8:
                switch (decodeReturnType) {
                    case 1:
                        ((com.android.internal.util.function.OctConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7));
                        return null;
                    case 2:
                        return (R) java.lang.Boolean.valueOf(((com.android.internal.util.function.OctPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7)));
                    case 3:
                        return (R) ((com.android.internal.util.function.OctFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7));
                }
            case 9:
                switch (decodeReturnType) {
                    case 1:
                        ((com.android.internal.util.function.NonaConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8));
                        return null;
                    case 2:
                        return (R) java.lang.Boolean.valueOf(((com.android.internal.util.function.NonaPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8)));
                    case 3:
                        return (R) ((com.android.internal.util.function.NonaFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8));
                }
            case 10:
                switch (decodeReturnType) {
                    case 1:
                        ((com.android.internal.util.function.DecConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9));
                        return null;
                    case 2:
                        return (R) java.lang.Boolean.valueOf(((com.android.internal.util.function.DecPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9)));
                    case 3:
                        return (R) ((com.android.internal.util.function.DecFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9));
                }
            case 11:
                switch (decodeReturnType) {
                    case 1:
                        ((com.android.internal.util.function.UndecConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9), popArg(10));
                        return null;
                    case 2:
                        return (R) java.lang.Boolean.valueOf(((com.android.internal.util.function.UndecPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9), popArg(10)));
                    case 3:
                        return (R) ((com.android.internal.util.function.UndecFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9), popArg(10));
                }
            case 12:
                switch (decodeReturnType) {
                    case 1:
                        ((com.android.internal.util.function.DodecConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9), popArg(10), popArg(11));
                        return null;
                    case 2:
                        return (R) java.lang.Boolean.valueOf(((com.android.internal.util.function.DodecPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9), popArg(10), popArg(11)));
                    case 3:
                        return (R) ((com.android.internal.util.function.DodecFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9), popArg(10), popArg(11));
                }
            case 15:
                switch (decodeReturnType) {
                    case 4:
                        return (R) java.lang.Integer.valueOf(getAsInt());
                    case 5:
                        return (R) java.lang.Long.valueOf(getAsLong());
                    case 6:
                        return (R) java.lang.Double.valueOf(getAsDouble());
                    default:
                        return (R) this.mFunc;
                }
        }
        throw new java.lang.IllegalStateException("Unknown function type: " + com.android.internal.util.function.pooled.PooledLambdaImpl.LambdaType.toString(flags));
    }

    private boolean isConstSupplier() {
        return com.android.internal.util.function.pooled.PooledLambdaImpl.LambdaType.decodeArgCount(getFlags(MASK_FUNC_TYPE)) == 15;
    }

    private java.lang.Object popArg(int i) {
        java.lang.Object obj = this.mArgs[i];
        if (isInvocationArgAtIndex(i)) {
            this.mArgs[i] = com.android.internal.util.function.pooled.ArgumentPlaceholder.INSTANCE;
            this.mFlags = (int) (this.mFlags & (~com.android.internal.util.BitUtils.bitAt(i)));
        }
        return obj;
    }

    public java.lang.String toString() {
        if (isRecycled()) {
            return "<recycled PooledLambda@" + hashCodeHex(this) + ">";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (isConstSupplier()) {
            sb.append(getFuncTypeAsString()).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START).append(doInvoke()).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        } else {
            java.lang.Object obj = this.mFunc;
            if (obj instanceof com.android.internal.util.function.pooled.PooledLambdaImpl) {
                sb.append(obj);
            } else {
                sb.append(getFuncTypeAsString()).append("@").append(hashCodeHex(obj));
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
            sb.append(commaSeparateFirstN(this.mArgs, com.android.internal.util.function.pooled.PooledLambdaImpl.LambdaType.decodeArgCount(getFlags(MASK_FUNC_TYPE))));
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return sb.toString();
    }

    private java.lang.String commaSeparateFirstN(java.lang.Object[] objArr, int i) {
        return objArr == null ? "" : android.text.TextUtils.join(",", java.util.Arrays.copyOf(objArr, i));
    }

    private static java.lang.String hashCodeHex(java.lang.Object obj) {
        return java.lang.Integer.toHexString(java.util.Objects.hashCode(obj));
    }

    private java.lang.String getFuncTypeAsString() {
        if (isRecycled()) {
            return "<recycled>";
        }
        if (isConstSupplier()) {
            return "supplier";
        }
        java.lang.String lambdaType = com.android.internal.util.function.pooled.PooledLambdaImpl.LambdaType.toString(getFlags(MASK_EXPOSED_AS));
        return lambdaType.endsWith("Consumer") ? "consumer" : lambdaType.endsWith("Function") ? "function" : lambdaType.endsWith("Predicate") ? "predicate" : lambdaType.endsWith("Supplier") ? "supplier" : lambdaType.endsWith("Runnable") ? "runnable" : lambdaType;
    }

    static <E extends com.android.internal.util.function.pooled.PooledLambda> E acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.Pool pool, java.lang.Object obj, int i, int i2, int i3, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9, java.lang.Object obj10, java.lang.Object obj11, java.lang.Object obj12, java.lang.Object obj13) {
        com.android.internal.util.function.pooled.PooledLambdaImpl acquire = acquire(pool);
        acquire.mFunc = java.util.Objects.requireNonNull(obj);
        acquire.setFlags(MASK_FUNC_TYPE, com.android.internal.util.function.pooled.PooledLambdaImpl.LambdaType.encode(i, i3));
        acquire.setFlags(MASK_EXPOSED_AS, com.android.internal.util.function.pooled.PooledLambdaImpl.LambdaType.encode(i2, i3));
        if (com.android.internal.util.ArrayUtils.size(acquire.mArgs) < i) {
            acquire.mArgs = new java.lang.Object[i];
        }
        setIfInBounds(acquire.mArgs, 0, obj2);
        setIfInBounds(acquire.mArgs, 1, obj3);
        setIfInBounds(acquire.mArgs, 2, obj4);
        setIfInBounds(acquire.mArgs, 3, obj5);
        setIfInBounds(acquire.mArgs, 4, obj6);
        setIfInBounds(acquire.mArgs, 5, obj7);
        setIfInBounds(acquire.mArgs, 6, obj8);
        setIfInBounds(acquire.mArgs, 7, obj9);
        setIfInBounds(acquire.mArgs, 8, obj10);
        setIfInBounds(acquire.mArgs, 9, obj11);
        setIfInBounds(acquire.mArgs, 10, obj12);
        setIfInBounds(acquire.mArgs, 11, obj13);
        return acquire;
    }

    static com.android.internal.util.function.pooled.PooledLambdaImpl acquireConstSupplier(int i) {
        com.android.internal.util.function.pooled.PooledLambdaImpl acquire = acquire(sPool);
        int encode = com.android.internal.util.function.pooled.PooledLambdaImpl.LambdaType.encode(15, i);
        acquire.setFlags(MASK_FUNC_TYPE, encode);
        acquire.setFlags(MASK_EXPOSED_AS, encode);
        return acquire;
    }

    static com.android.internal.util.function.pooled.PooledLambdaImpl acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.Pool pool) {
        com.android.internal.util.function.pooled.PooledLambdaImpl acquire = pool.acquire();
        if (acquire == null) {
            acquire = new com.android.internal.util.function.pooled.PooledLambdaImpl();
        }
        acquire.mFlags &= -2049;
        acquire.setFlags(8192, pool == sMessageCallbacksPool ? 1 : 0);
        return acquire;
    }

    private static void setIfInBounds(java.lang.Object[] objArr, int i, java.lang.Object obj) {
        if (i < com.android.internal.util.ArrayUtils.size(objArr)) {
            objArr[i] = obj;
        }
    }

    @Override // com.android.internal.util.function.pooled.OmniFunction, java.util.function.Predicate, java.util.function.BiPredicate
    public com.android.internal.util.function.pooled.OmniFunction<java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, R> negate() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.util.function.pooled.OmniFunction, java.util.function.BiFunction
    public <V> com.android.internal.util.function.pooled.OmniFunction<java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, V> andThen(java.util.function.Function<? super R, ? extends V> function) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.util.function.DoubleSupplier
    public double getAsDouble() {
        return java.lang.Double.longBitsToDouble(this.mConstValue);
    }

    @Override // java.util.function.IntSupplier
    public int getAsInt() {
        return (int) this.mConstValue;
    }

    @Override // java.util.function.LongSupplier
    public long getAsLong() {
        return this.mConstValue;
    }

    @Override // com.android.internal.util.function.pooled.OmniFunction, com.android.internal.util.function.pooled.PooledPredicate, com.android.internal.util.function.pooled.PooledLambda, com.android.internal.util.function.pooled.PooledSupplier, com.android.internal.util.function.pooled.PooledRunnable, com.android.internal.util.function.pooled.PooledSupplier.OfInt, com.android.internal.util.function.pooled.PooledSupplier.OfLong, com.android.internal.util.function.pooled.PooledSupplier.OfDouble
    public com.android.internal.util.function.pooled.OmniFunction<java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, R> recycleOnUse() {
        this.mFlags |= 4096;
        return this;
    }

    @Override // android.os.TraceNameSupplier
    public java.lang.String getTraceName() {
        return com.android.internal.util.FunctionalUtils.getLambdaName(this.mFunc);
    }

    private boolean isRecycled() {
        return (this.mFlags & 2048) != 0;
    }

    private boolean isRecycleOnUse() {
        return (this.mFlags & 4096) != 0;
    }

    private boolean isInvocationArgAtIndex(int i) {
        return ((1 << i) & this.mFlags) != 0;
    }

    int getFlags(int i) {
        return unmask(i, this.mFlags);
    }

    void setFlags(int i, int i2) {
        this.mFlags &= ~i;
        this.mFlags = mask(i, i2) | this.mFlags;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int mask(int i, int i2) {
        return i & (i2 << java.lang.Integer.numberOfTrailingZeros(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int unmask(int i, int i2) {
        return (i2 & i) / (1 << java.lang.Integer.numberOfTrailingZeros(i));
    }

    static class LambdaType {
        public static final int MASK = 127;
        public static final int MASK_ARG_COUNT = 15;
        public static final int MASK_BIT_COUNT = 7;
        public static final int MASK_RETURN_TYPE = 112;

        LambdaType() {
        }

        static int encode(int i, int i2) {
            return com.android.internal.util.function.pooled.PooledLambdaImpl.mask(15, i) | com.android.internal.util.function.pooled.PooledLambdaImpl.mask(112, i2);
        }

        static int decodeArgCount(int i) {
            return i & 15;
        }

        static int decodeReturnType(int i) {
            return com.android.internal.util.function.pooled.PooledLambdaImpl.unmask(112, i);
        }

        static java.lang.String toString(int i) {
            int decodeArgCount = decodeArgCount(i);
            int decodeReturnType = decodeReturnType(i);
            if (decodeArgCount == 0) {
                if (decodeReturnType == 1) {
                    return "Runnable";
                }
                if (decodeReturnType == 3 || decodeReturnType == 2) {
                    return "Supplier";
                }
            }
            return argCountPrefix(decodeArgCount) + com.android.internal.util.function.pooled.PooledLambdaImpl.LambdaType.ReturnType.lambdaSuffix(decodeReturnType);
        }

        private static java.lang.String argCountPrefix(int i) {
            switch (i) {
                case 0:
                    return "";
                case 1:
                    return "";
                case 2:
                    return "Bi";
                case 3:
                    return "Tri";
                case 4:
                    return "Quad";
                case 5:
                    return "Quint";
                case 6:
                    return "Hex";
                case 7:
                    return "Hept";
                case 8:
                    return "Oct";
                case 9:
                    return "Nona";
                case 10:
                    return "Dec";
                case 11:
                    return "Undec";
                case 12:
                case 13:
                case 14:
                default:
                    return "" + i + "arg";
                case 15:
                    return "";
            }
        }

        static class ReturnType {
            public static final int BOOLEAN = 2;
            public static final int DOUBLE = 6;
            public static final int INT = 4;
            public static final int LONG = 5;
            public static final int OBJECT = 3;
            public static final int VOID = 1;

            ReturnType() {
            }

            static java.lang.String toString(int i) {
                switch (i) {
                    case 1:
                        return "VOID";
                    case 2:
                        return "BOOLEAN";
                    case 3:
                        return "OBJECT";
                    case 4:
                        return "INT";
                    case 5:
                        return "LONG";
                    case 6:
                        return "DOUBLE";
                    default:
                        return "" + i;
                }
            }

            static java.lang.String lambdaSuffix(int i) {
                return prefix(i) + suffix(i);
            }

            private static java.lang.String prefix(int i) {
                switch (i) {
                    case 4:
                        return "Int";
                    case 5:
                        return "Long";
                    case 6:
                        return "Double";
                    default:
                        return "";
                }
            }

            private static java.lang.String suffix(int i) {
                switch (i) {
                    case 1:
                        return "Consumer";
                    case 2:
                        return "Predicate";
                    case 3:
                        return "Function";
                    default:
                        return "Supplier";
                }
            }
        }
    }
}
