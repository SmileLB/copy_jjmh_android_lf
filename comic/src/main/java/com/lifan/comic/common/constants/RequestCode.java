package com.lifan.comic.common.constants;

public class RequestCode {
    public static final int LOGIN_REQUEST_CODE = 2001;
    public static final int REGISTER_REQUEST_CODE = LOGIN_REQUEST_CODE + 1;
    public static final int READ_REQUEST_CODE = REGISTER_REQUEST_CODE + 1;
    public static final int SUBSCRIBE_REQUEST_CODE = READ_REQUEST_CODE + 1;
    public static final int PAY_REQUEST_CODE = SUBSCRIBE_REQUEST_CODE + 1;
    public static final int WEB_REQUEST_CODE = PAY_REQUEST_CODE + 1;
    public static final int OPEN_PIC_REQUEST_CODE = WEB_REQUEST_CODE + 1;
    public static final int CROP_IMG_REQUEST_ALBUM_CODE = OPEN_PIC_REQUEST_CODE + 1;
    public static final int VIP_REQUEST_CODE = CROP_IMG_REQUEST_ALBUM_CODE + 1;
    public static final int WELFARE_REQUEST_CODE = VIP_REQUEST_CODE + 1;
    public static final int REWARD_REQUEST_CODE = WELFARE_REQUEST_CODE + 1;
    public static final int SETTING_REQUEST_CODE = REWARD_REQUEST_CODE + 1;
    public static final int Coupon_REQUEST_CODE = SETTING_REQUEST_CODE + 1;
    public static final int RICH_REQUEST_CODE = Coupon_REQUEST_CODE + 1;
    public static final int COMMENT_REQUEST_CODE = RICH_REQUEST_CODE + 1;
    public static final int FEEDBACK_REQUEST_CODE = RICH_REQUEST_CODE + 1;
    public static final int MINE_REQUEST_CODE = RICH_REQUEST_CODE + 1;
    public static final int TL_ALI_REQUEST_CODE = MINE_REQUEST_CODE + 1;
    public static final int HC_ALI_REQUEST_CODE = TL_ALI_REQUEST_CODE + 1;
    public static final int KP_ALI_REQUEST_CODE = HC_ALI_REQUEST_CODE + 1;
    public static final int OPEN_CAMERA_REQUEST_CODE = KP_ALI_REQUEST_CODE + 1;
    public static final int CROP_IMG_REQUEST_CAMERA_CODE = OPEN_CAMERA_REQUEST_CODE + 1;
    public static final int EDIT_USER_INFO_REQUEST_CODE = CROP_IMG_REQUEST_CAMERA_CODE + 1;
}
