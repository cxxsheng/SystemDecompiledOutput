package com.android.internal.widget;

/* loaded from: classes5.dex */
public class PeopleHelper {
    private static final float COLOR_SHIFT_AMOUNT = 60.0f;
    private static final java.util.regex.Pattern IGNORABLE_CHAR_PATTERN = java.util.regex.Pattern.compile("[\\p{C}\\p{Z}]");
    private static final java.util.regex.Pattern SPECIAL_CHAR_PATTERN = java.util.regex.Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
    private int mAvatarSize;
    private android.content.Context mContext;
    private android.graphics.Paint mPaint = new android.graphics.Paint(1);
    private android.graphics.Paint mTextPaint = new android.graphics.Paint();

    public void init(android.content.Context context) {
        this.mContext = context;
        this.mAvatarSize = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.messaging_avatar_size);
        this.mTextPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.mTextPaint.setAntiAlias(true);
    }

    public void animateViewForceHidden(final com.android.internal.widget.CachingIconView cachingIconView, final boolean z) {
        if (z == (cachingIconView.willBeForceHidden() || cachingIconView.isForceHidden())) {
            return;
        }
        cachingIconView.animate().cancel();
        cachingIconView.setWillBeForceHidden(z);
        cachingIconView.animate().scaleX(z ? 0.5f : 1.0f).scaleY(z ? 0.5f : 1.0f).alpha(z ? 0.0f : 1.0f).setInterpolator(z ? com.android.internal.widget.MessagingPropertyAnimator.ALPHA_OUT : com.android.internal.widget.MessagingPropertyAnimator.ALPHA_IN).setDuration(160L);
        if (cachingIconView.getVisibility() != 0) {
            cachingIconView.setForceHidden(z);
        } else {
            cachingIconView.animate().withEndAction(new java.lang.Runnable() { // from class: com.android.internal.widget.PeopleHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.CachingIconView.this.setForceHidden(z);
                }
            });
        }
        cachingIconView.animate().start();
    }

    public android.graphics.drawable.Icon createAvatarSymbol(java.lang.CharSequence charSequence, java.lang.String str, int i) {
        float f;
        float f2;
        if (str.isEmpty() || android.text.TextUtils.isDigitsOnly(str) || SPECIAL_CHAR_PATTERN.matcher(str).find()) {
            android.graphics.drawable.Icon createWithResource = android.graphics.drawable.Icon.createWithResource(this.mContext, com.android.internal.R.drawable.messaging_user);
            createWithResource.setTint(findColor(charSequence, i));
            return createWithResource;
        }
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(this.mAvatarSize, this.mAvatarSize, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        float f3 = this.mAvatarSize / 2.0f;
        int findColor = findColor(charSequence, i);
        this.mPaint.setColor(findColor);
        canvas.drawCircle(f3, f3, f3, this.mPaint);
        this.mTextPaint.setColor((com.android.internal.graphics.ColorUtils.calculateLuminance(findColor) > 0.5d ? 1 : (com.android.internal.graphics.ColorUtils.calculateLuminance(findColor) == 0.5d ? 0 : -1)) > 0 ? -16777216 : -1);
        android.graphics.Paint paint = this.mTextPaint;
        if (str.length() == 1) {
            f = this.mAvatarSize;
            f2 = 0.5f;
        } else {
            f = this.mAvatarSize;
            f2 = 0.3f;
        }
        paint.setTextSize(f * f2);
        canvas.drawText(str, f3, (int) (f3 - ((this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0f)), this.mTextPaint);
        return android.graphics.drawable.Icon.createWithBitmap(createBitmap);
    }

    private int findColor(java.lang.CharSequence charSequence, int i) {
        return com.android.internal.util.ContrastColorUtil.getShiftedColor(i, (int) (((float) (((float) ((((java.lang.Math.abs(charSequence.hashCode()) % 5) / 4.0f) - 0.5f) + java.lang.Math.max(0.30000001192092896d - r0, 0.0d))) - java.lang.Math.max(0.30000001192092896d - (1.0d - com.android.internal.util.ContrastColorUtil.calculateLuminance(i)), 0.0d))) * 60.0f));
    }

    private java.lang.String getPureName(java.lang.CharSequence charSequence) {
        return IGNORABLE_CHAR_PATTERN.matcher(charSequence).replaceAll("");
    }

    public java.lang.String findNamePrefix(java.lang.CharSequence charSequence, java.lang.String str) {
        java.lang.String pureName = getPureName(charSequence);
        if (pureName.isEmpty()) {
            return str;
        }
        try {
            return new java.lang.String(java.lang.Character.toChars(pureName.codePointAt(0)));
        } catch (java.lang.RuntimeException e) {
            return str;
        }
    }

    public java.lang.String findNameSplit(java.lang.CharSequence charSequence) {
        java.lang.String[] split = (charSequence instanceof java.lang.String ? (java.lang.String) charSequence : charSequence.toString()).trim().split("[ ]+");
        if (split.length > 1) {
            java.lang.String findNamePrefix = findNamePrefix(split[0], null);
            java.lang.String findNamePrefix2 = findNamePrefix(split[1], null);
            if (findNamePrefix != null && findNamePrefix2 != null) {
                return findNamePrefix + findNamePrefix2;
            }
        }
        return findNamePrefix(charSequence, "");
    }

    public java.util.Map<java.lang.CharSequence, java.lang.String> mapUniqueNamesToPrefix(java.util.List<com.android.internal.widget.MessagingGroup> list) {
        java.lang.String findNamePrefix;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        for (int i = 0; i < list.size(); i++) {
            com.android.internal.widget.MessagingGroup messagingGroup = list.get(i);
            java.lang.CharSequence senderName = messagingGroup.getSenderName();
            if (messagingGroup.needsGeneratedAvatar() && !android.text.TextUtils.isEmpty(senderName) && !arrayMap.containsKey(senderName) && (findNamePrefix = findNamePrefix(senderName, null)) != null) {
                if (arrayMap2.containsKey(findNamePrefix)) {
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) arrayMap2.get(findNamePrefix);
                    if (charSequence != null) {
                        arrayMap.put(charSequence, findNameSplit(charSequence));
                        arrayMap2.put(findNamePrefix, null);
                    }
                    arrayMap.put(senderName, findNameSplit(senderName));
                } else {
                    arrayMap.put(senderName, findNamePrefix);
                    arrayMap2.put(findNamePrefix, senderName);
                }
            }
        }
        return arrayMap;
    }

    public class NameToPrefixMap {
        java.util.Map<java.lang.String, java.lang.String> mMap;

        NameToPrefixMap(java.util.Map<java.lang.String, java.lang.String> map) {
            this.mMap = map;
        }

        public java.lang.String getPrefix(java.lang.CharSequence charSequence) {
            return this.mMap.get(charSequence.toString());
        }
    }

    public com.android.internal.widget.PeopleHelper.NameToPrefixMap mapUniqueNamesToPrefixWithGroupList(java.util.List<java.util.List<android.app.Notification.MessagingStyle.Message>> list) {
        android.app.Person senderPerson;
        java.lang.String findNamePrefix;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        for (int i = 0; i < list.size(); i++) {
            java.util.List<android.app.Notification.MessagingStyle.Message> list2 = list.get(i);
            if (!list2.isEmpty() && (senderPerson = list2.get(0).getSenderPerson()) != null) {
                java.lang.CharSequence name = senderPerson.getName();
                if (senderPerson.getIcon() == null && !android.text.TextUtils.isEmpty(name)) {
                    java.lang.String charSequence = name.toString();
                    if (!arrayMap.containsKey(charSequence) && (findNamePrefix = findNamePrefix(name, null)) != null) {
                        if (arrayMap2.containsKey(findNamePrefix)) {
                            java.lang.CharSequence charSequence2 = (java.lang.CharSequence) arrayMap2.get(findNamePrefix);
                            if (charSequence2 != null) {
                                arrayMap.put(charSequence2.toString(), findNameSplit(charSequence2));
                                arrayMap2.put(findNamePrefix, null);
                            }
                            arrayMap.put(charSequence, findNameSplit(name));
                        } else {
                            arrayMap.put(charSequence, findNamePrefix);
                            arrayMap2.put(findNamePrefix, name);
                        }
                    }
                }
            }
        }
        return new com.android.internal.widget.PeopleHelper.NameToPrefixMap(arrayMap);
    }

    public void maybeHideFirstSenderName(java.util.List<com.android.internal.widget.MessagingGroup> list, boolean z, java.lang.CharSequence charSequence) {
        for (int size = list.size() - 1; size >= 0; size--) {
            com.android.internal.widget.MessagingGroup messagingGroup = list.get(size);
            messagingGroup.setCanHideSenderIfFirst(z && android.text.TextUtils.equals(charSequence, messagingGroup.getSenderName()));
        }
    }
}
