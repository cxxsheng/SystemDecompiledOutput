package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public final class CompletableFutureUtil {
    private CompletableFutureUtil() {
    }

    private static <T> T getValueOrRethrowErrorInternal(java.util.concurrent.CompletableFuture<T> completableFuture) {
        boolean z = false;
        while (true) {
            try {
                try {
                    break;
                } catch (java.lang.InterruptedException e) {
                    z = true;
                } catch (java.util.concurrent.ExecutionException e2) {
                    java.lang.Throwable cause = e2.getCause();
                    throw new java.lang.RuntimeException(cause.getMessage(), cause.getCause());
                }
            } finally {
                if (z) {
                    java.lang.Thread.currentThread().interrupt();
                }
            }
        }
        return completableFuture.get();
    }

    private static <T> T getValueOrNullInternal(java.util.concurrent.CompletableFuture<T> completableFuture, java.lang.String str, java.lang.String str2, long j, com.android.internal.inputmethod.CancellationGroup cancellationGroup) {
        T t;
        boolean z = false;
        boolean z2 = cancellationGroup != null && cancellationGroup.tryRegisterFutureOrCancelImmediately(completableFuture);
        while (true) {
            try {
                try {
                    try {
                        try {
                            try {
                                t = completableFuture.get(j, java.util.concurrent.TimeUnit.MILLISECONDS);
                                break;
                            } catch (java.lang.InterruptedException e) {
                                z = true;
                            } catch (java.lang.Throwable th) {
                                logErrorInternal(str, str2, th.getMessage());
                                if (z2) {
                                    cancellationGroup.unregisterFuture(completableFuture);
                                }
                                if (z) {
                                    java.lang.Thread.currentThread().interrupt();
                                }
                                return null;
                            }
                        } catch (java.util.concurrent.TimeoutException e2) {
                            logTimeoutInternal(str, str2, j);
                            if (z2) {
                                cancellationGroup.unregisterFuture(completableFuture);
                            }
                            if (z) {
                                java.lang.Thread.currentThread().interrupt();
                            }
                            return null;
                        }
                    } catch (java.util.concurrent.CancellationException e3) {
                        logCancellationInternal(str, str2);
                        if (z2) {
                            cancellationGroup.unregisterFuture(completableFuture);
                        }
                        if (z) {
                            java.lang.Thread.currentThread().interrupt();
                        }
                        return null;
                    }
                } catch (java.util.concurrent.CompletionException e4) {
                    if (e4.getCause() instanceof java.util.concurrent.CancellationException) {
                        logCancellationInternal(str, str2);
                        if (z2) {
                            cancellationGroup.unregisterFuture(completableFuture);
                        }
                        if (z) {
                            java.lang.Thread.currentThread().interrupt();
                        }
                        return null;
                    }
                    logErrorInternal(str, str2, e4.getMessage());
                    if (z2) {
                        cancellationGroup.unregisterFuture(completableFuture);
                    }
                    if (z) {
                        java.lang.Thread.currentThread().interrupt();
                    }
                    return null;
                }
            } catch (java.lang.Throwable th2) {
                if (z2) {
                    cancellationGroup.unregisterFuture(completableFuture);
                }
                if (z) {
                    java.lang.Thread.currentThread().interrupt();
                }
                throw th2;
            }
        }
        if (z2) {
            cancellationGroup.unregisterFuture(completableFuture);
        }
        if (z) {
            java.lang.Thread.currentThread().interrupt();
        }
        return t;
    }

    private static void logTimeoutInternal(java.lang.String str, java.lang.String str2, long j) {
        if (str == null || str2 == null) {
            return;
        }
        android.util.Log.w(str, str2 + " didn't respond in " + j + " msec.");
    }

    private static void logErrorInternal(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (str == null || str2 == null) {
            return;
        }
        android.util.Log.w(str, str2 + " was failed with an exception=" + str3);
    }

    private static void logCancellationInternal(java.lang.String str, java.lang.String str2) {
        if (str == null || str2 == null) {
            return;
        }
        android.util.Log.w(str, str2 + " was cancelled.");
    }

    public static <T> T getResult(java.util.concurrent.CompletableFuture<T> completableFuture) {
        return (T) getValueOrRethrowErrorInternal(completableFuture);
    }

    public static boolean getBooleanResult(java.util.concurrent.CompletableFuture<java.lang.Boolean> completableFuture) {
        return ((java.lang.Boolean) getValueOrRethrowErrorInternal(completableFuture)).booleanValue();
    }

    public static int getIntegerResult(java.util.concurrent.CompletableFuture<java.lang.Integer> completableFuture) {
        return ((java.lang.Integer) getValueOrRethrowErrorInternal(completableFuture)).intValue();
    }

    public static boolean getResultOrFalse(java.util.concurrent.CompletableFuture<java.lang.Boolean> completableFuture, java.lang.String str, java.lang.String str2, com.android.internal.inputmethod.CancellationGroup cancellationGroup, long j) {
        java.lang.Boolean bool = (java.lang.Boolean) getValueOrNullInternal(completableFuture, str, str2, j, cancellationGroup);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static int getResultOrZero(java.util.concurrent.CompletableFuture<java.lang.Integer> completableFuture, java.lang.String str, java.lang.String str2, com.android.internal.inputmethod.CancellationGroup cancellationGroup, long j) {
        java.lang.Integer num = (java.lang.Integer) getValueOrNullInternal(completableFuture, str, str2, j, cancellationGroup);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static <T> T getResultOrNull(java.util.concurrent.CompletableFuture<T> completableFuture, java.lang.String str, java.lang.String str2, com.android.internal.inputmethod.CancellationGroup cancellationGroup, long j) {
        return (T) getValueOrNullInternal(completableFuture, str, str2, j, cancellationGroup);
    }
}
