package com.android.internal.util;

/* loaded from: classes5.dex */
public class ArtBinaryXmlPullParser extends com.android.modules.utils.BinaryXmlPullParser {
    @Override // com.android.modules.utils.BinaryXmlPullParser
    protected com.android.modules.utils.FastDataInput obtainFastDataInput(java.io.InputStream inputStream) {
        return com.android.internal.util.ArtFastDataInput.obtain(inputStream);
    }
}
