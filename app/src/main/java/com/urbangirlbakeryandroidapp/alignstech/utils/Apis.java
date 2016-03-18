package com.urbangirlbakeryandroidapp.alignstech.utils;

/**
 * Created by Dell on 12/22/2015.
 */
public class Apis {

    public static final String BASE_URL = "http://cake.yogeshojha.com.np/";
    public static final String userDetialPostURl = BASE_URL +"api/" + "userRegister";
    public static final String userRegisterUpdate = BASE_URL +"api/userRegister/" + "update";

    public static final String some_categories_list = BASE_URL + "api/child-categories/5?n=3";
    public static final String some_gift_list = BASE_URL + "api/child-categories/3?n=3";
    public static final String headerImageSlider_urgent_cake = BASE_URL + "api/urgent-cake";
    public static final String  header_offers = BASE_URL + "api/offers";

    public static final String see_all_categories_cake = BASE_URL + "api/child-categories/5";
    public static final String see_all_gifts = BASE_URL + "api/child-categories/3";
    public static final String see_all_offers = BASE_URL + "api/child-categories/4";
    public static final String see_all_accessories = BASE_URL + "api/accessories";

    public static final String see_all_child_items = BASE_URL + "api/products/66";

    public static final String defaultImageUrl = "http://www.gettyimages.ca/gi-resources/images/Homepage/Hero/UK/CMS_Creative_164657191_Kingfisher.jpg";
    public static final String nav_collection = BASE_URL + "collection";

    public static final String product_order = BASE_URL + "api/order";

    public static final String cake_order_details = "http://cake.yogeshojha.com.np/api/receive_order_details";
    public static final String my_orders = BASE_URL + "api/order_product";

    public static final String gift_order_details = "http://cake.yogeshojha.com.np/api/receive_gift_order";

    public static final String post_complain = BASE_URL + "api/receive_complain";
    public static final String get_complain = BASE_URL + "api/my_complain";

    public static String post_gcm = BASE_URL + "api/notify";
    public static String get_gcm_notice = BASE_URL + "api/notice";
    public static String get_opening_closing = BASE_URL + "api/settings";

}
