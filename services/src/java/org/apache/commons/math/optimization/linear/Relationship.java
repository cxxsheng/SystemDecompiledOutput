package org.apache.commons.math.optimization.linear;

/* loaded from: classes3.dex */
public enum Relationship {
    EQ("="),
    LEQ("<="),
    GEQ(">=");

    private final java.lang.String stringValue;

    Relationship(java.lang.String str) {
        this.stringValue = str;
    }

    @Override // java.lang.Enum
    public java.lang.String toString() {
        return this.stringValue;
    }

    /* renamed from: org.apache.commons.math.optimization.linear.Relationship$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$math$optimization$linear$Relationship = new int[org.apache.commons.math.optimization.linear.Relationship.values().length];

        static {
            try {
                $SwitchMap$org$apache$commons$math$optimization$linear$Relationship[org.apache.commons.math.optimization.linear.Relationship.LEQ.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$apache$commons$math$optimization$linear$Relationship[org.apache.commons.math.optimization.linear.Relationship.GEQ.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
        }
    }

    public org.apache.commons.math.optimization.linear.Relationship oppositeRelationship() {
        switch (org.apache.commons.math.optimization.linear.Relationship.AnonymousClass1.$SwitchMap$org$apache$commons$math$optimization$linear$Relationship[ordinal()]) {
            case 1:
                return GEQ;
            case 2:
                return LEQ;
            default:
                return EQ;
        }
    }
}
