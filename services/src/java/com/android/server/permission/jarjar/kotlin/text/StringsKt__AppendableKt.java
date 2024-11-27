package com.android.server.permission.jarjar.kotlin.text;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Appendable.kt */
/* loaded from: classes2.dex */
public class StringsKt__AppendableKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static <T> void appendElement(@org.jetbrains.annotations.NotNull java.lang.Appendable $this$appendElement, T t, @org.jetbrains.annotations.Nullable com.android.server.permission.jarjar.kotlin.jvm.functions.Function1<? super T, ? extends java.lang.CharSequence> function1) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter($this$appendElement, "<this>");
        if (function1 == null) {
            if (!(t == 0 ? true : t instanceof java.lang.CharSequence)) {
                if (!(t instanceof java.lang.Character)) {
                    $this$appendElement.append(java.lang.String.valueOf(t));
                    return;
                } else {
                    $this$appendElement.append(((java.lang.Character) t).charValue());
                    return;
                }
            }
            $this$appendElement.append((java.lang.CharSequence) t);
            return;
        }
        $this$appendElement.append(function1.invoke(t));
    }
}
