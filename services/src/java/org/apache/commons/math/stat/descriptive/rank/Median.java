package org.apache.commons.math.stat.descriptive.rank;

/* loaded from: classes3.dex */
public class Median extends org.apache.commons.math.stat.descriptive.rank.Percentile implements java.io.Serializable {
    private static final long serialVersionUID = -3961477041290915687L;

    public Median() {
        super(50.0d);
    }

    public Median(org.apache.commons.math.stat.descriptive.rank.Median median) {
        super(median);
    }
}
