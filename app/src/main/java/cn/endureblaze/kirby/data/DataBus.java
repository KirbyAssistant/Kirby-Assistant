package cn.endureblaze.kirby.data;

public class DataBus {
    private static boolean CHANGE_USER_AVATAR;

    private static boolean CHANGE_THEME;
    //MainResFragment的当前碎片
    private static int MAIN_RES_VIEWPAGER_ITEM;
    private static int MAIN_RES_TAB_LAYOUT_ITEM;
    //登录注册的数据
    private static boolean LOGIN_OR_REGISTER;
    private static String LOGIN_USERNAME;
    private static String LOGIN_PASSWORD;
    private static String REGISTER_USERNAME;
    private static String REGISTER_EMAIL;
    private static String REGISTER_PASSWORD;

    public static boolean isChangeUserAvatar() {
        return CHANGE_USER_AVATAR;
    }

    public static void setChangeUserAvatar(boolean changeUserAvatar) {
        CHANGE_USER_AVATAR = changeUserAvatar;
    }

    public static int getMainResTabLayoutItem() {
        return MAIN_RES_TAB_LAYOUT_ITEM;
    }

    public static void setMainResTabLayoutItem(int mainResTabLayoutItem) {
        MAIN_RES_TAB_LAYOUT_ITEM = mainResTabLayoutItem;
    }

    public static boolean isChangeTheme() {
        return CHANGE_THEME;
    }

    public static void setChangeTheme(boolean changeTheme) {
        CHANGE_THEME = changeTheme;
    }

    public static boolean isLoginOrRegister() {
        return LOGIN_OR_REGISTER;
    }

    public static void setLoginOrRegister(boolean loginOrRegister) {
        LOGIN_OR_REGISTER = loginOrRegister;
    }

    public static String getRegisterPasswordAgain() {
        return REGISTER_PASSWORD_AGAIN;
    }

    public static void setRegisterPasswordAgain(String registerPasswordAgain) {
        REGISTER_PASSWORD_AGAIN = registerPasswordAgain;
    }

    private static String REGISTER_PASSWORD_AGAIN;

    public static String getRegisterPassword() {
        return REGISTER_PASSWORD;
    }

    public static void setRegisterPassword(String registerPassword) {
        REGISTER_PASSWORD = registerPassword;
    }

    public static String getRegisterEmail() {
        return REGISTER_EMAIL;
    }

    public static void setRegisterEmail(String registerEmail) {
        REGISTER_EMAIL = registerEmail;
    }

    public static String getRegisterUsername() {
        return REGISTER_USERNAME;
    }

    public static void setRegisterUsername(String registerUsername) {
        REGISTER_USERNAME = registerUsername;
    }

    public static String getLoginPassword() {
        return LOGIN_PASSWORD;
    }

    public static void setLoginPassword(String loginPassword) {
        LOGIN_PASSWORD = loginPassword;
    }

    public static String getLoginUsername() {
        return LOGIN_USERNAME;
    }

    public static void setLoginUsername(String loginUsername) {
        LOGIN_USERNAME = loginUsername;
    }

    public static int getMainResViewpagerItem() {
        return MAIN_RES_VIEWPAGER_ITEM;
    }

    public static void setMainResViewpagerItem(int mainResViewpagerItem) {
        MAIN_RES_VIEWPAGER_ITEM = mainResViewpagerItem;
    }
}
