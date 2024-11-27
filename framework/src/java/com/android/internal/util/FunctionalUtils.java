package com.android.internal.util;

/* loaded from: classes5.dex */
public class FunctionalUtils {

    @java.lang.FunctionalInterface
    public interface ThrowingChecked2Consumer<Input, ExceptionOne extends java.lang.Exception, ExceptionTwo extends java.lang.Exception> {
        void accept(Input input) throws java.lang.Exception, java.lang.Exception;
    }

    @java.lang.FunctionalInterface
    public interface ThrowingCheckedConsumer<Input, ExceptionType extends java.lang.Exception> {
        void accept(Input input) throws java.lang.Exception;
    }

    @java.lang.FunctionalInterface
    public interface ThrowingCheckedFunction<Input, Output, ExceptionType extends java.lang.Exception> {
        Output apply(Input input) throws java.lang.Exception;
    }

    @java.lang.FunctionalInterface
    public interface ThrowingCheckedSupplier<Output, ExceptionType extends java.lang.Exception> {
        Output get() throws java.lang.Exception;
    }

    private FunctionalUtils() {
    }

    public static <T> java.util.function.Consumer<T> uncheckExceptions(com.android.internal.util.FunctionalUtils.ThrowingConsumer<T> throwingConsumer) {
        return throwingConsumer;
    }

    public static <I, O> java.util.function.Function<I, O> uncheckExceptions(com.android.internal.util.FunctionalUtils.ThrowingFunction<I, O> throwingFunction) {
        return throwingFunction;
    }

    public static java.lang.Runnable uncheckExceptions(com.android.internal.util.FunctionalUtils.ThrowingRunnable throwingRunnable) {
        return throwingRunnable;
    }

    public static <A, B> java.util.function.BiConsumer<A, B> uncheckExceptions(com.android.internal.util.FunctionalUtils.ThrowingBiConsumer<A, B> throwingBiConsumer) {
        return throwingBiConsumer;
    }

    public static <T> java.util.function.Supplier<T> uncheckExceptions(com.android.internal.util.FunctionalUtils.ThrowingSupplier<T> throwingSupplier) {
        return throwingSupplier;
    }

    public static <T> java.util.function.Consumer<T> ignoreRemoteException(com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer<T> remoteExceptionIgnoringConsumer) {
        return remoteExceptionIgnoringConsumer;
    }

    public static java.lang.Runnable handleExceptions(final com.android.internal.util.FunctionalUtils.ThrowingRunnable throwingRunnable, final java.util.function.Consumer<java.lang.Throwable> consumer) {
        return new java.lang.Runnable() { // from class: com.android.internal.util.FunctionalUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.util.FunctionalUtils.lambda$handleExceptions$0(com.android.internal.util.FunctionalUtils.ThrowingRunnable.this, consumer);
            }
        };
    }

    static /* synthetic */ void lambda$handleExceptions$0(com.android.internal.util.FunctionalUtils.ThrowingRunnable throwingRunnable, java.util.function.Consumer consumer) {
        try {
            throwingRunnable.run();
        } catch (java.lang.Throwable th) {
            consumer.accept(th);
        }
    }

    @java.lang.FunctionalInterface
    public interface ThrowingRunnable extends java.lang.Runnable {
        void runOrThrow() throws java.lang.Exception;

        @Override // java.lang.Runnable
        default void run() {
            try {
                runOrThrow();
            } catch (java.lang.Exception e) {
                throw android.util.ExceptionUtils.propagate(e);
            }
        }
    }

    @java.lang.FunctionalInterface
    public interface ThrowingSupplier<T> extends java.util.function.Supplier<T> {
        T getOrThrow() throws java.lang.Exception;

        @Override // java.util.function.Supplier
        default T get() {
            try {
                return getOrThrow();
            } catch (java.lang.Exception e) {
                throw android.util.ExceptionUtils.propagate(e);
            }
        }
    }

    @java.lang.FunctionalInterface
    public interface ThrowingConsumer<T> extends java.util.function.Consumer<T> {
        void acceptOrThrow(T t) throws java.lang.Exception;

        @Override // java.util.function.Consumer
        default void accept(T t) {
            try {
                acceptOrThrow(t);
            } catch (java.lang.Exception e) {
                throw android.util.ExceptionUtils.propagate(e);
            }
        }
    }

    @java.lang.FunctionalInterface
    public interface RemoteExceptionIgnoringConsumer<T> extends java.util.function.Consumer<T> {
        void acceptOrThrow(T t) throws android.os.RemoteException;

        @Override // java.util.function.Consumer
        default void accept(T t) {
            try {
                acceptOrThrow(t);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @java.lang.FunctionalInterface
    public interface ThrowingFunction<T, R> extends java.util.function.Function<T, R> {
        R applyOrThrow(T t) throws java.lang.Exception;

        @Override // java.util.function.Function
        default R apply(T t) {
            try {
                return applyOrThrow(t);
            } catch (java.lang.Exception e) {
                throw android.util.ExceptionUtils.propagate(e);
            }
        }
    }

    @java.lang.FunctionalInterface
    public interface ThrowingBiFunction<T, U, R> extends java.util.function.BiFunction<T, U, R> {
        R applyOrThrow(T t, U u) throws java.lang.Exception;

        @Override // java.util.function.BiFunction
        default R apply(T t, U u) {
            try {
                return applyOrThrow(t, u);
            } catch (java.lang.Exception e) {
                throw android.util.ExceptionUtils.propagate(e);
            }
        }
    }

    @java.lang.FunctionalInterface
    public interface ThrowingBiConsumer<A, B> extends java.util.function.BiConsumer<A, B> {
        void acceptOrThrow(A a, B b) throws java.lang.Exception;

        @Override // java.util.function.BiConsumer
        default void accept(A a, B b) {
            try {
                acceptOrThrow(a, b);
            } catch (java.lang.Exception e) {
                throw android.util.ExceptionUtils.propagate(e);
            }
        }
    }

    public static java.lang.String getLambdaName(java.lang.Object obj) {
        int indexOf;
        java.lang.String obj2 = obj.toString();
        int indexOf2 = obj2.indexOf("-$$");
        if (indexOf2 == -1 || (indexOf = obj2.indexOf(36, indexOf2 + 3)) == -1) {
            return obj2;
        }
        int i = indexOf + 1;
        int indexOf3 = obj2.indexOf(36, i);
        return indexOf3 == -1 ? obj2.substring(0, indexOf2 - 1) + "$Lambda" : obj2.substring(0, indexOf2) + obj2.substring(i, indexOf3) + "$Lambda";
    }
}
