package com.android.server.permission.jarjar.kotlin.text;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: StringsJVM.kt */
/* loaded from: classes2.dex */
public class StringsKt__StringsJVMKt extends com.android.server.permission.jarjar.kotlin.text.StringsKt__StringNumberConversionsKt {
    public static /* synthetic */ boolean startsWith$default(java.lang.String str, java.lang.String str2, boolean z, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return startsWith(str, str2, z);
    }

    public static final boolean startsWith(@org.jetbrains.annotations.NotNull java.lang.String $this$startsWith, @org.jetbrains.annotations.NotNull java.lang.String prefix, boolean ignoreCase) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter($this$startsWith, "<this>");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (!ignoreCase) {
            return $this$startsWith.startsWith(prefix);
        }
        return regionMatches($this$startsWith, 0, prefix, 0, prefix.length(), ignoreCase);
    }

    public static final boolean regionMatches(@org.jetbrains.annotations.NotNull java.lang.String $this$regionMatches, int thisOffset, @org.jetbrains.annotations.NotNull java.lang.String other, int otherOffset, int length, boolean ignoreCase) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter($this$regionMatches, "<this>");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(other, "other");
        if (!ignoreCase) {
            return $this$regionMatches.regionMatches(thisOffset, other, otherOffset, length);
        }
        return $this$regionMatches.regionMatches(ignoreCase, thisOffset, other, otherOffset, length);
    }
}
