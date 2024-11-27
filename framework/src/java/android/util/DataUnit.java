package android.util;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public class DataUnit {
    public static final android.util.DataUnit KILOBYTES = new android.util.DataUnit.AnonymousClass1("KILOBYTES", 0);
    public static final android.util.DataUnit MEGABYTES = new android.util.DataUnit.AnonymousClass2("MEGABYTES", 1);
    public static final android.util.DataUnit GIGABYTES = new android.util.DataUnit.AnonymousClass3("GIGABYTES", 2);
    public static final android.util.DataUnit TERABYTES = new android.util.DataUnit.AnonymousClass4("TERABYTES", 3);
    public static final android.util.DataUnit KIBIBYTES = new android.util.DataUnit.AnonymousClass5("KIBIBYTES", 4);
    public static final android.util.DataUnit MEBIBYTES = new android.util.DataUnit.AnonymousClass6("MEBIBYTES", 5);
    public static final android.util.DataUnit GIBIBYTES = new android.util.DataUnit.AnonymousClass7("GIBIBYTES", 6);
    public static final android.util.DataUnit TEBIBYTES = new android.util.DataUnit.AnonymousClass8("TEBIBYTES", 7);
    private static final /* synthetic */ android.util.DataUnit[] $VALUES = $values();

    private static /* synthetic */ android.util.DataUnit[] $values() {
        return new android.util.DataUnit[]{KILOBYTES, MEGABYTES, GIGABYTES, TERABYTES, KIBIBYTES, MEBIBYTES, GIBIBYTES, TEBIBYTES};
    }

    public static android.util.DataUnit valueOf(java.lang.String str) {
        return (android.util.DataUnit) java.lang.Enum.valueOf(android.util.DataUnit.class, str);
    }

    public static android.util.DataUnit[] values() {
        return (android.util.DataUnit[]) $VALUES.clone();
    }

    /* renamed from: android.util.DataUnit$1, reason: invalid class name */
    enum AnonymousClass1 extends android.util.DataUnit {
        private AnonymousClass1(java.lang.String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1000;
        }
    }

    private DataUnit(java.lang.String str, int i) {
    }

    /* renamed from: android.util.DataUnit$2, reason: invalid class name */
    enum AnonymousClass2 extends android.util.DataUnit {
        private AnonymousClass2(java.lang.String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1000000;
        }
    }

    /* renamed from: android.util.DataUnit$3, reason: invalid class name */
    enum AnonymousClass3 extends android.util.DataUnit {
        private AnonymousClass3(java.lang.String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1000000000;
        }
    }

    /* renamed from: android.util.DataUnit$4, reason: invalid class name */
    enum AnonymousClass4 extends android.util.DataUnit {
        private AnonymousClass4(java.lang.String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1000000000000L;
        }
    }

    /* renamed from: android.util.DataUnit$5, reason: invalid class name */
    enum AnonymousClass5 extends android.util.DataUnit {
        private AnonymousClass5(java.lang.String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1024;
        }
    }

    /* renamed from: android.util.DataUnit$6, reason: invalid class name */
    enum AnonymousClass6 extends android.util.DataUnit {
        private AnonymousClass6(java.lang.String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1048576;
        }
    }

    /* renamed from: android.util.DataUnit$7, reason: invalid class name */
    enum AnonymousClass7 extends android.util.DataUnit {
        private AnonymousClass7(java.lang.String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1073741824;
        }
    }

    /* renamed from: android.util.DataUnit$8, reason: invalid class name */
    enum AnonymousClass8 extends android.util.DataUnit {
        private AnonymousClass8(java.lang.String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1099511627776L;
        }
    }

    public long toBytes(long j) {
        throw new java.lang.AbstractMethodError();
    }
}
