package com.android.internal.org.bouncycastle.math.raw;

/* loaded from: classes4.dex */
public abstract class Nat512 {
    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
        com.android.internal.org.bouncycastle.math.raw.Nat256.mul(iArr, iArr2, iArr3);
        com.android.internal.org.bouncycastle.math.raw.Nat256.mul(iArr, 8, iArr2, 8, iArr3, 16);
        int addToEachOther = com.android.internal.org.bouncycastle.math.raw.Nat256.addToEachOther(iArr3, 8, iArr3, 16);
        int addTo = addToEachOther + com.android.internal.org.bouncycastle.math.raw.Nat256.addTo(iArr3, 24, iArr3, 16, com.android.internal.org.bouncycastle.math.raw.Nat256.addTo(iArr3, 0, iArr3, 8, 0) + addToEachOther);
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        boolean z = com.android.internal.org.bouncycastle.math.raw.Nat256.diff(iArr, 8, iArr, 0, create, 0) != com.android.internal.org.bouncycastle.math.raw.Nat256.diff(iArr2, 8, iArr2, 0, create2, 0);
        int[] createExt = com.android.internal.org.bouncycastle.math.raw.Nat256.createExt();
        com.android.internal.org.bouncycastle.math.raw.Nat256.mul(create, create2, createExt);
        com.android.internal.org.bouncycastle.math.raw.Nat.addWordAt(32, addTo + (z ? com.android.internal.org.bouncycastle.math.raw.Nat.addTo(16, createExt, 0, iArr3, 8) : com.android.internal.org.bouncycastle.math.raw.Nat.subFrom(16, createExt, 0, iArr3, 8)), iArr3, 24);
    }

    public static void square(int[] iArr, int[] iArr2) {
        com.android.internal.org.bouncycastle.math.raw.Nat256.square(iArr, iArr2);
        com.android.internal.org.bouncycastle.math.raw.Nat256.square(iArr, 8, iArr2, 16);
        int addToEachOther = com.android.internal.org.bouncycastle.math.raw.Nat256.addToEachOther(iArr2, 8, iArr2, 16);
        int addTo = addToEachOther + com.android.internal.org.bouncycastle.math.raw.Nat256.addTo(iArr2, 24, iArr2, 16, com.android.internal.org.bouncycastle.math.raw.Nat256.addTo(iArr2, 0, iArr2, 8, 0) + addToEachOther);
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        com.android.internal.org.bouncycastle.math.raw.Nat256.diff(iArr, 8, iArr, 0, create, 0);
        int[] createExt = com.android.internal.org.bouncycastle.math.raw.Nat256.createExt();
        com.android.internal.org.bouncycastle.math.raw.Nat256.square(create, createExt);
        com.android.internal.org.bouncycastle.math.raw.Nat.addWordAt(32, addTo + com.android.internal.org.bouncycastle.math.raw.Nat.subFrom(16, createExt, 0, iArr2, 8), iArr2, 24);
    }
}
