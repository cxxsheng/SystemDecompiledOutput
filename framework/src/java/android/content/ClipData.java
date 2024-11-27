package android.content;

/* loaded from: classes.dex */
public class ClipData implements android.os.Parcelable {
    private static final java.lang.String TAG = "ClipData";
    final android.content.ClipDescription mClipDescription;
    final android.graphics.Bitmap mIcon;
    final java.util.ArrayList<android.content.ClipData.Item> mItems;
    private boolean mParcelItemActivityInfos;
    static final java.lang.String[] MIMETYPES_TEXT_PLAIN = {"text/plain"};
    static final java.lang.String[] MIMETYPES_TEXT_HTML = {"text/html"};
    static final java.lang.String[] MIMETYPES_TEXT_URILIST = {android.content.ClipDescription.MIMETYPE_TEXT_URILIST};
    static final java.lang.String[] MIMETYPES_TEXT_INTENT = {android.content.ClipDescription.MIMETYPE_TEXT_INTENT};
    public static final android.os.Parcelable.Creator<android.content.ClipData> CREATOR = new android.os.Parcelable.Creator<android.content.ClipData>() { // from class: android.content.ClipData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ClipData createFromParcel(android.os.Parcel parcel) {
            return new android.content.ClipData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ClipData[] newArray(int i) {
            return new android.content.ClipData[i];
        }
    };

    public static class Item {
        private android.content.pm.ActivityInfo mActivityInfo;
        final java.lang.String mHtmlText;
        final android.content.Intent mIntent;
        final android.content.IntentSender mIntentSender;
        final java.lang.CharSequence mText;
        private android.view.textclassifier.TextLinks mTextLinks;
        android.net.Uri mUri;

        public static final class Builder {
            private java.lang.String mHtmlText;
            private android.content.Intent mIntent;
            private android.content.IntentSender mIntentSender;
            private java.lang.CharSequence mText;
            private android.net.Uri mUri;

            public android.content.ClipData.Item.Builder setText(java.lang.CharSequence charSequence) {
                this.mText = charSequence;
                return this;
            }

            public android.content.ClipData.Item.Builder setHtmlText(java.lang.String str) {
                this.mHtmlText = str;
                return this;
            }

            public android.content.ClipData.Item.Builder setIntent(android.content.Intent intent) {
                this.mIntent = intent;
                return this;
            }

            public android.content.ClipData.Item.Builder setIntentSender(android.content.IntentSender intentSender) {
                if (intentSender != null && !intentSender.isImmutable()) {
                    throw new java.lang.IllegalArgumentException("Expected intent sender to be immutable");
                }
                this.mIntentSender = intentSender;
                return this;
            }

            public android.content.ClipData.Item.Builder setUri(android.net.Uri uri) {
                this.mUri = uri;
                return this;
            }

            public android.content.ClipData.Item build() {
                return new android.content.ClipData.Item(this.mText, this.mHtmlText, this.mIntent, this.mIntentSender, this.mUri);
            }
        }

        public Item(android.content.ClipData.Item item) {
            this.mText = item.mText;
            this.mHtmlText = item.mHtmlText;
            this.mIntent = item.mIntent;
            this.mIntentSender = item.mIntentSender;
            this.mUri = item.mUri;
            this.mActivityInfo = item.mActivityInfo;
            this.mTextLinks = item.mTextLinks;
        }

        public Item(java.lang.CharSequence charSequence) {
            this(charSequence, null, null, null, null);
        }

        public Item(java.lang.CharSequence charSequence, java.lang.String str) {
            this(charSequence, str, null, null, null);
        }

        public Item(android.content.Intent intent) {
            this(null, null, intent, null, null);
        }

        public Item(android.net.Uri uri) {
            this(null, null, null, null, uri);
        }

        public Item(java.lang.CharSequence charSequence, android.content.Intent intent, android.net.Uri uri) {
            this(charSequence, null, intent, null, uri);
        }

        public Item(java.lang.CharSequence charSequence, java.lang.String str, android.content.Intent intent, android.net.Uri uri) {
            this(charSequence, str, intent, null, uri);
        }

        private Item(java.lang.CharSequence charSequence, java.lang.String str, android.content.Intent intent, android.content.IntentSender intentSender, android.net.Uri uri) {
            if (str != null && charSequence == null) {
                throw new java.lang.IllegalArgumentException("Plain text must be supplied if HTML text is supplied");
            }
            this.mText = charSequence;
            this.mHtmlText = str;
            this.mIntent = intent;
            this.mIntentSender = intentSender;
            this.mUri = uri;
        }

        public java.lang.CharSequence getText() {
            return this.mText;
        }

        public java.lang.String getHtmlText() {
            return this.mHtmlText;
        }

        public android.content.Intent getIntent() {
            return this.mIntent;
        }

        public android.content.IntentSender getIntentSender() {
            return this.mIntentSender;
        }

        public android.net.Uri getUri() {
            return this.mUri;
        }

        public android.content.pm.ActivityInfo getActivityInfo() {
            return this.mActivityInfo;
        }

        public void setActivityInfo(android.content.pm.ActivityInfo activityInfo) {
            this.mActivityInfo = activityInfo;
        }

        public android.view.textclassifier.TextLinks getTextLinks() {
            return this.mTextLinks;
        }

        public void setTextLinks(android.view.textclassifier.TextLinks textLinks) {
            this.mTextLinks = textLinks;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:32:0x00a4  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x004c A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r2v10, types: [java.io.FileInputStream, java.io.InputStream, java.lang.AutoCloseable] */
        /* JADX WARN: Type inference failed for: r2v2, types: [android.net.Uri] */
        /* JADX WARN: Type inference failed for: r2v5 */
        /* JADX WARN: Type inference failed for: r2v6 */
        /* JADX WARN: Type inference failed for: r2v8 */
        /* JADX WARN: Type inference failed for: r2v9, types: [java.lang.AutoCloseable] */
        /* JADX WARN: Type inference failed for: r3v19 */
        /* JADX WARN: Type inference failed for: r3v2 */
        /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.AutoCloseable] */
        /* JADX WARN: Type inference failed for: r9v19 */
        /* JADX WARN: Type inference failed for: r9v4 */
        /* JADX WARN: Type inference failed for: r9v5, types: [android.content.ContentResolver] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public java.lang.CharSequence coerceToText(android.content.Context context) {
            java.lang.String str;
            ?? r9;
            java.lang.AutoCloseable autoCloseable;
            ?? r3;
            android.content.res.AssetFileDescriptor assetFileDescriptor;
            java.lang.String str2;
            java.io.InputStreamReader inputStreamReader;
            java.io.IOException e;
            java.lang.CharSequence text = getText();
            if (text != null) {
                return text;
            }
            android.content.res.AssetFileDescriptor assetFileDescriptor2 = null;
            try {
                r9 = context.getContentResolver();
            } catch (java.lang.Exception e2) {
                str = "Failed to obtain ContentResolver: ";
                android.util.Log.w(android.content.ClipData.TAG, "Failed to obtain ContentResolver: " + e2);
                r9 = 0;
            }
            ?? uri = getUri();
            if (uri != 0) {
                try {
                    if (r9 != 0) {
                        try {
                            str = "text/*";
                            assetFileDescriptor = r9.openTypedAssetFileDescriptor(uri, "text/*", null);
                            str2 = str;
                        } catch (java.io.FileNotFoundException | java.lang.RuntimeException e3) {
                            assetFileDescriptor = null;
                            str2 = str;
                            if (assetFileDescriptor != null) {
                            }
                        } catch (java.lang.SecurityException e4) {
                            str = "Failure opening stream";
                            android.util.Log.w(android.content.ClipData.TAG, "Failure opening stream", e4);
                            assetFileDescriptor = null;
                            str2 = str;
                            if (assetFileDescriptor != null) {
                            }
                        }
                        try {
                            if (assetFileDescriptor != null) {
                                try {
                                    uri = assetFileDescriptor.createInputStream();
                                    try {
                                        inputStreamReader = new java.io.InputStreamReader((java.io.InputStream) uri, android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
                                    } catch (java.io.IOException e5) {
                                        inputStreamReader = null;
                                        e = e5;
                                    } catch (java.lang.Throwable th) {
                                        th = th;
                                        str2 = null;
                                        assetFileDescriptor2 = assetFileDescriptor;
                                        th = th;
                                        autoCloseable = uri;
                                        r3 = str2;
                                        libcore.io.IoUtils.closeQuietly(assetFileDescriptor2);
                                        libcore.io.IoUtils.closeQuietly(autoCloseable);
                                        libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) r3);
                                        throw th;
                                    }
                                } catch (java.io.IOException e6) {
                                    inputStreamReader = null;
                                    e = e6;
                                    uri = 0;
                                } catch (java.lang.Throwable th2) {
                                    th = th2;
                                    uri = 0;
                                    str2 = null;
                                }
                                try {
                                    java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
                                    char[] cArr = new char[8192];
                                    while (true) {
                                        int read = inputStreamReader.read(cArr);
                                        if (read <= 0) {
                                            java.lang.String sb2 = sb.toString();
                                            libcore.io.IoUtils.closeQuietly(assetFileDescriptor);
                                            libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) uri);
                                            libcore.io.IoUtils.closeQuietly(inputStreamReader);
                                            return sb2;
                                        }
                                        sb.append(cArr, 0, read);
                                    }
                                } catch (java.io.IOException e7) {
                                    e = e7;
                                    android.util.Log.w(android.content.ClipData.TAG, "Failure loading text", e);
                                    java.lang.String obj = e.toString();
                                    libcore.io.IoUtils.closeQuietly(assetFileDescriptor);
                                    libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) uri);
                                    libcore.io.IoUtils.closeQuietly(inputStreamReader);
                                    return obj;
                                }
                            } else {
                                libcore.io.IoUtils.closeQuietly(assetFileDescriptor);
                                libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
                                libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
                            }
                        } catch (java.lang.Throwable th3) {
                            th = th3;
                        }
                    }
                } catch (java.lang.Throwable th4) {
                    th = th4;
                    autoCloseable = null;
                    r3 = 0;
                }
            }
            if (uri != 0) {
                java.lang.String scheme = uri.getScheme();
                return ("content".equals(scheme) || android.content.ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme) || "file".equals(scheme)) ? "" : uri.toString();
            }
            android.content.Intent intent = getIntent();
            return intent != null ? intent.toUri(1) : "";
        }

        public java.lang.CharSequence coerceToStyledText(android.content.Context context) {
            java.lang.CharSequence text = getText();
            if (text instanceof android.text.Spanned) {
                return text;
            }
            java.lang.String htmlText = getHtmlText();
            if (htmlText != null) {
                try {
                    android.text.Spanned fromHtml = android.text.Html.fromHtml(htmlText);
                    if (fromHtml != null) {
                        return fromHtml;
                    }
                } catch (java.lang.RuntimeException e) {
                }
            }
            if (text != null) {
                return text;
            }
            return coerceToHtmlOrStyledText(context, true);
        }

        public java.lang.String coerceToHtmlText(android.content.Context context) {
            java.lang.String htmlText = getHtmlText();
            if (htmlText != null) {
                return htmlText;
            }
            java.lang.CharSequence text = getText();
            if (text != null) {
                if (text instanceof android.text.Spanned) {
                    return android.text.Html.toHtml((android.text.Spanned) text);
                }
                return android.text.Html.escapeHtml(text);
            }
            java.lang.CharSequence coerceToHtmlOrStyledText = coerceToHtmlOrStyledText(context, false);
            if (coerceToHtmlOrStyledText != null) {
                return coerceToHtmlOrStyledText.toString();
            }
            return null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r15v8, types: [android.text.Spanned] */
        private java.lang.CharSequence coerceToHtmlOrStyledText(android.content.Context context, boolean z) {
            java.lang.String[] strArr;
            boolean z2;
            boolean z3;
            if (this.mUri == null) {
                return this.mIntent != null ? z ? uriToStyledText(this.mIntent.toUri(1)) : uriToHtml(this.mIntent.toUri(1)) : "";
            }
            java.io.FileInputStream fileInputStream = null;
            try {
                strArr = context.getContentResolver().getStreamTypes(this.mUri, "text/*");
            } catch (java.lang.SecurityException e) {
                strArr = null;
            }
            java.lang.String str = "text/html";
            if (strArr != null) {
                z2 = false;
                z3 = false;
                for (java.lang.String str2 : strArr) {
                    if ("text/html".equals(str2)) {
                        z2 = true;
                    } else if (str2.startsWith("text/")) {
                        z3 = true;
                    }
                }
            } else {
                z2 = false;
                z3 = false;
            }
            if (z2 || z3) {
                try {
                    try {
                        try {
                            try {
                                android.content.ContentResolver contentResolver = context.getContentResolver();
                                android.net.Uri uri = this.mUri;
                                if (!z2) {
                                    str = "text/plain";
                                }
                                java.io.FileInputStream createInputStream = contentResolver.openTypedAssetFileDescriptor(uri, str, null).createInputStream();
                                java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(createInputStream, android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
                                java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
                                char[] cArr = new char[8192];
                                while (true) {
                                    int read = inputStreamReader.read(cArr);
                                    if (read <= 0) {
                                        break;
                                    }
                                    sb.append(cArr, 0, read);
                                }
                                java.lang.String sb2 = sb.toString();
                                if (!z2) {
                                    if (z) {
                                        if (createInputStream != null) {
                                            try {
                                                createInputStream.close();
                                            } catch (java.io.IOException e2) {
                                            }
                                        }
                                        return sb2;
                                    }
                                    java.lang.String escapeHtml = android.text.Html.escapeHtml(sb2);
                                    if (createInputStream != null) {
                                        try {
                                            createInputStream.close();
                                        } catch (java.io.IOException e3) {
                                        }
                                    }
                                    return escapeHtml;
                                }
                                if (!z) {
                                    java.lang.String str3 = sb2.toString();
                                    if (createInputStream != null) {
                                        try {
                                            createInputStream.close();
                                        } catch (java.io.IOException e4) {
                                        }
                                    }
                                    return str3;
                                }
                                try {
                                    ?? fromHtml = android.text.Html.fromHtml(sb2);
                                    if (fromHtml != 0) {
                                        sb2 = fromHtml;
                                    }
                                    if (createInputStream != null) {
                                        try {
                                            createInputStream.close();
                                        } catch (java.io.IOException e5) {
                                        }
                                    }
                                    return sb2;
                                } catch (java.lang.RuntimeException e6) {
                                    if (createInputStream != null) {
                                        try {
                                            createInputStream.close();
                                        } catch (java.io.IOException e7) {
                                        }
                                    }
                                    return sb2;
                                }
                            } catch (java.io.IOException e8) {
                                android.util.Log.w(android.content.ClipData.TAG, "Failure loading text", e8);
                                return android.text.Html.escapeHtml(e8.toString());
                            } catch (java.lang.SecurityException e9) {
                                android.util.Log.w(android.content.ClipData.TAG, "Failure opening stream", e9);
                                if (0 != 0) {
                                    fileInputStream.close();
                                }
                            }
                        } finally {
                            if (0 != 0) {
                                try {
                                    fileInputStream.close();
                                } catch (java.io.IOException e10) {
                                }
                            }
                        }
                    } catch (java.io.FileNotFoundException e11) {
                        if (0 != 0) {
                            fileInputStream.close();
                        }
                    }
                } catch (java.io.IOException e12) {
                }
            }
            java.lang.String scheme = this.mUri.getScheme();
            return ("content".equals(scheme) || android.content.ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme) || "file".equals(scheme)) ? "" : z ? uriToStyledText(this.mUri.toString()) : uriToHtml(this.mUri.toString());
        }

        private java.lang.String uriToHtml(java.lang.String str) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(256);
            sb.append("<a href=\"");
            sb.append(android.text.Html.escapeHtml(str));
            sb.append("\">");
            sb.append(android.text.Html.escapeHtml(str));
            sb.append("</a>");
            return sb.toString();
        }

        private java.lang.CharSequence uriToStyledText(java.lang.String str) {
            android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder();
            spannableStringBuilder.append((java.lang.CharSequence) str);
            spannableStringBuilder.setSpan(new android.text.style.URLSpan(str), 0, spannableStringBuilder.length(), 33);
            return spannableStringBuilder;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("ClipData.Item { ");
            toShortString(sb, true);
            sb.append(" }");
            return sb.toString();
        }

        public void toShortString(java.lang.StringBuilder sb, boolean z) {
            boolean z2;
            boolean z3 = false;
            if (this.mHtmlText == null) {
                z2 = true;
            } else {
                if (z) {
                    sb.append("H(").append(this.mHtmlText.length()).append(')');
                } else {
                    sb.append("H:").append(this.mHtmlText);
                }
                z2 = false;
            }
            if (this.mText != null) {
                if (!z2) {
                    sb.append(' ');
                }
                if (z) {
                    sb.append("T(").append(this.mText.length()).append(')');
                } else {
                    sb.append("T:").append(this.mText);
                }
                z2 = false;
            }
            if (this.mUri == null) {
                z3 = z2;
            } else {
                if (!z2) {
                    sb.append(' ');
                }
                if (z) {
                    sb.append("U(").append(this.mUri.getScheme()).append(')');
                } else {
                    sb.append("U:").append(this.mUri);
                }
            }
            if (this.mIntent != null) {
                if (!z3) {
                    sb.append(' ');
                }
                sb.append("I:");
                this.mIntent.toShortString(sb, z, true, true, true);
            }
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            if (this.mHtmlText != null) {
                protoOutputStream.write(1138166333441L, this.mHtmlText);
            } else if (this.mText != null) {
                protoOutputStream.write(1138166333442L, this.mText.toString());
            } else if (this.mUri != null) {
                protoOutputStream.write(1138166333443L, this.mUri.toString());
            } else if (this.mIntent != null) {
                this.mIntent.dumpDebug(protoOutputStream, 1146756268036L, true, true, true, true);
            } else {
                protoOutputStream.write(1133871366149L, true);
            }
            protoOutputStream.end(start);
        }
    }

    public ClipData(java.lang.CharSequence charSequence, java.lang.String[] strArr, android.content.ClipData.Item item) {
        this.mClipDescription = new android.content.ClipDescription(charSequence, strArr);
        if (item == null) {
            throw new java.lang.NullPointerException("item is null");
        }
        this.mIcon = null;
        this.mItems = new java.util.ArrayList<>();
        this.mItems.add(item);
        this.mClipDescription.setIsStyledText(isStyledText());
    }

    public ClipData(android.content.ClipDescription clipDescription, android.content.ClipData.Item item) {
        this.mClipDescription = clipDescription;
        if (item == null) {
            throw new java.lang.NullPointerException("item is null");
        }
        this.mIcon = null;
        this.mItems = new java.util.ArrayList<>();
        this.mItems.add(item);
        this.mClipDescription.setIsStyledText(isStyledText());
    }

    public ClipData(android.content.ClipDescription clipDescription, java.util.ArrayList<android.content.ClipData.Item> arrayList) {
        this.mClipDescription = clipDescription;
        if (arrayList == null) {
            throw new java.lang.NullPointerException("item is null");
        }
        this.mIcon = null;
        this.mItems = arrayList;
    }

    public ClipData(android.content.ClipData clipData) {
        this.mClipDescription = clipData.mClipDescription;
        this.mIcon = clipData.mIcon;
        this.mItems = new java.util.ArrayList<>(clipData.mItems);
    }

    public android.content.ClipData copyForTransferWithActivityInfo() {
        android.content.ClipData clipData = new android.content.ClipData(this);
        clipData.mParcelItemActivityInfos = true;
        return clipData;
    }

    public boolean willParcelWithActivityInfo() {
        return this.mParcelItemActivityInfos;
    }

    public static android.content.ClipData newPlainText(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        return new android.content.ClipData(charSequence, MIMETYPES_TEXT_PLAIN, new android.content.ClipData.Item(charSequence2));
    }

    public static android.content.ClipData newHtmlText(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.String str) {
        return new android.content.ClipData(charSequence, MIMETYPES_TEXT_HTML, new android.content.ClipData.Item(charSequence2, str));
    }

    public static android.content.ClipData newIntent(java.lang.CharSequence charSequence, android.content.Intent intent) {
        return new android.content.ClipData(charSequence, MIMETYPES_TEXT_INTENT, new android.content.ClipData.Item(intent));
    }

    public static android.content.ClipData newUri(android.content.ContentResolver contentResolver, java.lang.CharSequence charSequence, android.net.Uri uri) {
        return new android.content.ClipData(charSequence, getMimeTypes(contentResolver, uri), new android.content.ClipData.Item(uri));
    }

    private static java.lang.String[] getMimeTypes(android.content.ContentResolver contentResolver, android.net.Uri uri) {
        java.lang.String[] strArr;
        if (!"content".equals(uri.getScheme())) {
            strArr = null;
        } else {
            java.lang.String type = contentResolver.getType(uri);
            strArr = contentResolver.getStreamTypes(uri, "*/*");
            if (type != null) {
                if (strArr == null) {
                    strArr = new java.lang.String[]{type};
                } else if (!com.android.internal.util.ArrayUtils.contains(strArr, type)) {
                    java.lang.String[] strArr2 = new java.lang.String[strArr.length + 1];
                    strArr2[0] = type;
                    java.lang.System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
                    strArr = strArr2;
                }
            }
        }
        if (strArr == null) {
            return MIMETYPES_TEXT_URILIST;
        }
        return strArr;
    }

    public static android.content.ClipData newRawUri(java.lang.CharSequence charSequence, android.net.Uri uri) {
        return new android.content.ClipData(charSequence, MIMETYPES_TEXT_URILIST, new android.content.ClipData.Item(uri));
    }

    public android.content.ClipDescription getDescription() {
        return this.mClipDescription;
    }

    public void addItem(android.content.ClipData.Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("item is null");
        }
        this.mItems.add(item);
        if (this.mItems.size() == 1) {
            this.mClipDescription.setIsStyledText(isStyledText());
        }
    }

    public void addItem(android.content.ContentResolver contentResolver, android.content.ClipData.Item item) {
        addItem(item);
        if (item.getHtmlText() != null) {
            this.mClipDescription.addMimeTypes(MIMETYPES_TEXT_HTML);
        } else if (item.getText() != null) {
            this.mClipDescription.addMimeTypes(MIMETYPES_TEXT_PLAIN);
        }
        if (item.getIntent() != null) {
            this.mClipDescription.addMimeTypes(MIMETYPES_TEXT_INTENT);
        }
        if (item.getUri() != null) {
            this.mClipDescription.addMimeTypes(getMimeTypes(contentResolver, item.getUri()));
        }
    }

    public android.graphics.Bitmap getIcon() {
        return this.mIcon;
    }

    public int getItemCount() {
        return this.mItems.size();
    }

    public android.content.ClipData.Item getItemAt(int i) {
        return this.mItems.get(i);
    }

    public void setItemAt(int i, android.content.ClipData.Item item) {
        this.mItems.set(i, item);
    }

    public void prepareToLeaveProcess(boolean z) {
        prepareToLeaveProcess(z, 1);
    }

    public void prepareToLeaveProcess(boolean z, int i) {
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.ClipData.Item item = this.mItems.get(i2);
            if (item.mIntent != null) {
                item.mIntent.prepareToLeaveProcess(z);
            }
            if (item.mUri != null && z) {
                if (android.os.StrictMode.vmFileUriExposureEnabled()) {
                    item.mUri.checkFileUriExposed("ClipData.Item.getUri()");
                }
                if (android.os.StrictMode.vmContentUriWithoutPermissionEnabled()) {
                    item.mUri.checkContentUriWithoutPermission("ClipData.Item.getUri()", i);
                }
            }
        }
    }

    public void prepareToLeaveProcess$ravenwood(boolean z, int i) {
    }

    public void prepareToEnterProcess(android.content.AttributionSource attributionSource) {
        int size = this.mItems.size();
        for (int i = 0; i < size; i++) {
            android.content.ClipData.Item item = this.mItems.get(i);
            if (item.mIntent != null) {
                item.mIntent.prepareToEnterProcess(false, attributionSource);
            }
        }
    }

    public void fixUris(int i) {
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.ClipData.Item item = this.mItems.get(i2);
            if (item.mIntent != null) {
                item.mIntent.fixUris(i);
            }
            if (item.mUri != null) {
                item.mUri = android.content.ContentProvider.maybeAddUserId(item.mUri, i);
            }
        }
    }

    public void fixUrisLight(int i) {
        android.net.Uri data;
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.ClipData.Item item = this.mItems.get(i2);
            if (item.mIntent != null && (data = item.mIntent.getData()) != null) {
                item.mIntent.setData(android.content.ContentProvider.maybeAddUserId(data, i));
            }
            if (item.mUri != null) {
                item.mUri = android.content.ContentProvider.maybeAddUserId(item.mUri, i);
            }
        }
    }

    private boolean isStyledText() {
        if (this.mItems.isEmpty()) {
            return false;
        }
        java.lang.CharSequence text = this.mItems.get(0).getText();
        return (text instanceof android.text.Spanned) && android.text.TextUtils.hasStyleSpan((android.text.Spanned) text);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("ClipData { ");
        toShortString(sb, true);
        sb.append(" }");
        return sb.toString();
    }

    public void toShortString(java.lang.StringBuilder sb, boolean z) {
        boolean z2;
        if (this.mClipDescription != null) {
            z2 = !this.mClipDescription.toShortString(sb, z);
        } else {
            z2 = true;
        }
        if (this.mIcon != null) {
            if (!z2) {
                sb.append(' ');
            }
            sb.append("I:");
            sb.append(this.mIcon.getWidth());
            sb.append(com.android.internal.transition.EpicenterTranslateClipReveal.StateProperty.TARGET_X);
            sb.append(this.mIcon.getHeight());
            z2 = false;
        }
        if (this.mItems.size() != 1) {
            if (!z2) {
                sb.append(' ');
            }
            sb.append(this.mItems.size()).append(" items:");
            z2 = false;
        }
        int i = 0;
        while (i < this.mItems.size()) {
            if (!z2) {
                sb.append(' ');
            }
            sb.append('{');
            this.mItems.get(i).toShortString(sb, z);
            sb.append('}');
            i++;
            z2 = false;
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (this.mClipDescription != null) {
            this.mClipDescription.dumpDebug(protoOutputStream, 1146756268033L);
        }
        if (this.mIcon != null) {
            long start2 = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1120986464257L, this.mIcon.getWidth());
            protoOutputStream.write(1120986464258L, this.mIcon.getHeight());
            protoOutputStream.end(start2);
        }
        for (int i = 0; i < this.mItems.size(); i++) {
            this.mItems.get(i).dumpDebug(protoOutputStream, 2246267895811L);
        }
        protoOutputStream.end(start);
    }

    public void collectUris(java.util.List<android.net.Uri> list) {
        for (int i = 0; i < this.mItems.size(); i++) {
            android.content.ClipData.Item itemAt = getItemAt(i);
            if (itemAt.getUri() != null) {
                list.add(itemAt.getUri());
            }
            android.content.Intent intent = itemAt.getIntent();
            if (intent != null) {
                if (intent.getData() != null) {
                    list.add(intent.getData());
                }
                if (intent.getClipData() != null) {
                    intent.getClipData().collectUris(list);
                }
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mClipDescription.writeToParcel(parcel, i);
        if (this.mIcon != null) {
            parcel.writeInt(1);
            this.mIcon.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        int size = this.mItems.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            android.content.ClipData.Item item = this.mItems.get(i2);
            android.text.TextUtils.writeToParcel(item.mText, parcel, i);
            parcel.writeString8(item.mHtmlText);
            parcel.writeTypedObject(item.mIntent, i);
            parcel.writeTypedObject(item.mIntentSender, i);
            parcel.writeTypedObject(item.mUri, i);
            parcel.writeTypedObject(this.mParcelItemActivityInfos ? item.mActivityInfo : null, i);
            parcel.writeTypedObject(item.mTextLinks, i);
        }
    }

    ClipData(android.os.Parcel parcel) {
        this.mClipDescription = new android.content.ClipDescription(parcel);
        if (parcel.readInt() != 0) {
            this.mIcon = android.graphics.Bitmap.CREATOR.createFromParcel(parcel);
        } else {
            this.mIcon = null;
        }
        this.mItems = new java.util.ArrayList<>();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            java.lang.CharSequence createFromParcel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            java.lang.String readString8 = parcel.readString8();
            android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
            android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
            android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
            android.content.pm.ActivityInfo activityInfo = (android.content.pm.ActivityInfo) parcel.readTypedObject(android.content.pm.ActivityInfo.CREATOR);
            android.view.textclassifier.TextLinks textLinks = (android.view.textclassifier.TextLinks) parcel.readTypedObject(android.view.textclassifier.TextLinks.CREATOR);
            android.content.ClipData.Item item = new android.content.ClipData.Item(createFromParcel, readString8, intent, intentSender, uri);
            item.setActivityInfo(activityInfo);
            item.setTextLinks(textLinks);
            this.mItems.add(item);
        }
    }
}
