package com.android.internal.logging;

/* loaded from: classes4.dex */
public class AndroidConfig {
    public AndroidConfig() {
        try {
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger("");
            logger.addHandler(new com.android.internal.logging.AndroidHandler());
            logger.setLevel(java.util.logging.Level.INFO);
            java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.WARNING);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
