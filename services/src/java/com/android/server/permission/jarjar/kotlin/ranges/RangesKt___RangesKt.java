package com.android.server.permission.jarjar.kotlin.ranges;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: _Ranges.kt */
/* loaded from: classes2.dex */
public class RangesKt___RangesKt extends com.android.server.permission.jarjar.kotlin.ranges.RangesKt__RangesKt {
    public static int coerceAtMost(int $this$coerceAtMost, int maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static long coerceAtMost(long $this$coerceAtMost, long maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }
}
