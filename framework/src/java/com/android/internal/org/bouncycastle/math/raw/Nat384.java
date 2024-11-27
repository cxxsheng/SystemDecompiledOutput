package com.android.internal.org.bouncycastle.math.raw;

/* loaded from: classes4.dex */
public abstract class Nat384 {
    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
        com.android.internal.org.bouncycastle.math.raw.Nat192.mul(iArr, iArr2, iArr3);
        com.android.internal.org.bouncycastle.math.raw.Nat192.mul(iArr, 6, iArr2, 6, iArr3, 12);
        int addToEachOther = com.android.internal.org.bouncycastle.math.raw.Nat192.addToEachOther(iArr3, 6, iArr3, 12);
        int addTo = addToEachOther + com.android.internal.org.bouncycastle.math.raw.Nat192.addTo(iArr3, 18, iArr3, 12, com.android.internal.org.bouncycastle.math.raw.Nat192.addTo(iArr3, 0, iArr3, 6, 0) + addToEachOther);
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        boolean z = com.android.internal.org.bouncycastle.math.raw.Nat192.diff(iArr, 6, iArr, 0, create, 0) != com.android.internal.org.bouncycastle.math.raw.Nat192.diff(iArr2, 6, iArr2, 0, create2, 0);
        int[] createExt = com.android.internal.org.bouncycastle.math.raw.Nat192.createExt();
        com.android.internal.org.bouncycastle.math.raw.Nat192.mul(create, create2, createExt);
        com.android.internal.org.bouncycastle.math.raw.Nat.addWordAt(24, addTo + (z ? com.android.internal.org.bouncycastle.math.raw.Nat.addTo(12, createExt, 0, iArr3, 6) : com.android.internal.org.bouncycastle.math.raw.Nat.subFrom(12, createExt, 0, iArr3, 6)), iArr3, 18);
    }

    public static void square(int[] iArr, int[] iArr2) {
        com.android.internal.org.bouncycastle.math.raw.Nat192.square(iArr, iArr2);
        com.android.internal.org.bouncycastle.math.raw.Nat192.square(iArr, 6, iArr2, 12);
        int addToEachOther = com.android.internal.org.bouncycastle.math.raw.Nat192.addToEachOther(iArr2, 6, iArr2, 12);
        int addTo = addToEachOther + com.android.internal.org.bouncycastle.math.raw.Nat192.addTo(iArr2, 18, iArr2, 12, com.android.internal.org.bouncycastle.math.raw.Nat192.addTo(iArr2, 0, iArr2, 6, 0) + addToEachOther);
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.raw.Nat192.diff(iArr, 6, iArr, 0, create, 0);
        int[] createExt = com.android.internal.org.bouncycastle.math.raw.Nat192.createExt();
        com.android.internal.org.bouncycastle.math.raw.Nat192.square(create, createExt);
        com.android.internal.org.bouncycastle.math.raw.Nat.addWordAt(24, addTo + com.android.internal.org.bouncycastle.math.raw.Nat.subFrom(12, createExt, 0, iArr2, 6), iArr2, 18);
    }
}
