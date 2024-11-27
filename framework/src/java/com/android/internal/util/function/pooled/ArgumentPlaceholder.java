package com.android.internal.util.function.pooled;

/* loaded from: classes5.dex */
public final class ArgumentPlaceholder<R> {
    static final com.android.internal.util.function.pooled.ArgumentPlaceholder<?> INSTANCE = new com.android.internal.util.function.pooled.ArgumentPlaceholder<>();

    private ArgumentPlaceholder() {
    }

    public java.lang.String toString() {
        return android.telecom.Logging.Session.SESSION_SEPARATION_CHAR_CHILD;
    }
}
