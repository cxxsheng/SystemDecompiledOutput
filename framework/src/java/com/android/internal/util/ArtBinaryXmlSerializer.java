package com.android.internal.util;

/* loaded from: classes5.dex */
public class ArtBinaryXmlSerializer extends com.android.modules.utils.BinaryXmlSerializer {
    @Override // com.android.modules.utils.BinaryXmlSerializer
    protected com.android.modules.utils.FastDataOutput obtainFastDataOutput(java.io.OutputStream outputStream) {
        return com.android.internal.util.ArtFastDataOutput.obtain(outputStream);
    }
}
