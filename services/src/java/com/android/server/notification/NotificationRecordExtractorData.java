package com.android.server.notification;

/* loaded from: classes2.dex */
public final class NotificationRecordExtractorData {
    private final boolean mAllowBubble;
    private final android.app.NotificationChannel mChannel;
    private final java.lang.String mGroupKey;
    private final int mImportance;
    private final boolean mIsBubble;
    private final boolean mIsConversation;
    private final java.util.ArrayList<java.lang.String> mOverridePeople;
    private final int mPosition;
    private final int mProposedImportance;
    private final float mRankingScore;
    private final boolean mSensitiveContent;
    private final boolean mShowBadge;
    private final java.util.ArrayList<java.lang.CharSequence> mSmartReplies;
    private final java.util.ArrayList<android.service.notification.SnoozeCriterion> mSnoozeCriteria;
    private final java.lang.Integer mSuppressVisually;
    private final java.util.ArrayList<android.app.Notification.Action> mSystemSmartActions;
    private final java.lang.Integer mUserSentiment;
    private final int mVisibility;

    NotificationRecordExtractorData(int i, int i2, boolean z, boolean z2, boolean z3, android.app.NotificationChannel notificationChannel, java.lang.String str, java.util.ArrayList<java.lang.String> arrayList, java.util.ArrayList<android.service.notification.SnoozeCriterion> arrayList2, java.lang.Integer num, java.lang.Integer num2, java.util.ArrayList<android.app.Notification.Action> arrayList3, java.util.ArrayList<java.lang.CharSequence> arrayList4, int i3, float f, boolean z4, int i4, boolean z5) {
        this.mPosition = i;
        this.mVisibility = i2;
        this.mShowBadge = z;
        this.mAllowBubble = z2;
        this.mIsBubble = z3;
        this.mChannel = notificationChannel;
        this.mGroupKey = str;
        this.mOverridePeople = arrayList;
        this.mSnoozeCriteria = arrayList2;
        this.mUserSentiment = num;
        this.mSuppressVisually = num2;
        this.mSystemSmartActions = arrayList3;
        this.mSmartReplies = arrayList4;
        this.mImportance = i3;
        this.mRankingScore = f;
        this.mIsConversation = z4;
        this.mProposedImportance = i4;
        this.mSensitiveContent = z5;
    }

    boolean hasDiffForRankingLocked(com.android.server.notification.NotificationRecord notificationRecord, int i) {
        return (this.mPosition == i && this.mVisibility == notificationRecord.getPackageVisibilityOverride() && this.mShowBadge == notificationRecord.canShowBadge() && this.mAllowBubble == notificationRecord.canBubble() && this.mIsBubble == notificationRecord.getNotification().isBubbleNotification() && java.util.Objects.equals(this.mChannel, notificationRecord.getChannel()) && java.util.Objects.equals(this.mGroupKey, notificationRecord.getGroupKey()) && java.util.Objects.equals(this.mOverridePeople, notificationRecord.getPeopleOverride()) && java.util.Objects.equals(this.mSnoozeCriteria, notificationRecord.getSnoozeCriteria()) && java.util.Objects.equals(this.mUserSentiment, java.lang.Integer.valueOf(notificationRecord.getUserSentiment())) && java.util.Objects.equals(this.mSuppressVisually, java.lang.Integer.valueOf(notificationRecord.getSuppressedVisualEffects())) && java.util.Objects.equals(this.mSystemSmartActions, notificationRecord.getSystemGeneratedSmartActions()) && java.util.Objects.equals(this.mSmartReplies, notificationRecord.getSmartReplies()) && this.mImportance == notificationRecord.getImportance() && this.mProposedImportance == notificationRecord.getProposedImportance() && this.mSensitiveContent == notificationRecord.hasSensitiveContent()) ? false : true;
    }

    boolean hasDiffForLoggingLocked(com.android.server.notification.NotificationRecord notificationRecord, int i) {
        return (this.mPosition == i && java.util.Objects.equals(this.mChannel, notificationRecord.getChannel()) && java.util.Objects.equals(this.mGroupKey, notificationRecord.getGroupKey()) && java.util.Objects.equals(this.mOverridePeople, notificationRecord.getPeopleOverride()) && java.util.Objects.equals(this.mSnoozeCriteria, notificationRecord.getSnoozeCriteria()) && java.util.Objects.equals(this.mUserSentiment, java.lang.Integer.valueOf(notificationRecord.getUserSentiment())) && java.util.Objects.equals(this.mSystemSmartActions, notificationRecord.getSystemGeneratedSmartActions()) && java.util.Objects.equals(this.mSmartReplies, notificationRecord.getSmartReplies()) && this.mImportance == notificationRecord.getImportance() && notificationRecord.rankingScoreMatches(this.mRankingScore) && this.mIsConversation == notificationRecord.isConversation() && this.mProposedImportance == notificationRecord.getProposedImportance() && this.mSensitiveContent == notificationRecord.hasSensitiveContent()) ? false : true;
    }
}
