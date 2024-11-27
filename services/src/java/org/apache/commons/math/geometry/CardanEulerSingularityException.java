package org.apache.commons.math.geometry;

/* loaded from: classes3.dex */
public class CardanEulerSingularityException extends org.apache.commons.math.MathException {
    private static final long serialVersionUID = -1360952845582206770L;

    public CardanEulerSingularityException(boolean z) {
        super(z ? org.apache.commons.math.exception.util.LocalizedFormats.CARDAN_ANGLES_SINGULARITY : org.apache.commons.math.exception.util.LocalizedFormats.EULER_ANGLES_SINGULARITY, new java.lang.Object[0]);
    }
}
