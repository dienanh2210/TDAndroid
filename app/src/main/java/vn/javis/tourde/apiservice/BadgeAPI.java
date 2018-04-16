package vn.javis.tourde.apiservice;

import java.util.ArrayList;
import java.util.List;

import vn.javis.tourde.model.Badge;

public class BadgeAPI {
    private static BadgeAPI instance;
    private List<Badge> mListBagde = new ArrayList<>();

    public BadgeAPI() {
        instance =this;
        setBadgeForTest();
    }

    public List<Badge> getListBadge(){
        return mListBagde;
    }

    void setBadgeForTest(){
        Badge b1 = new Badge("琵琶湖１周","2018 06 04","icon_classic");
        mListBagde.add(b1);
        Badge b2 = new Badge("甘利山踏破","2018 06 04","icon_club");
        mListBagde.add(b2);
        Badge b3 = new Badge("キャノンボウル達成","2018 06 04","icon_tailorshop");
        mListBagde.add(b3);
        Badge b4 = new Badge("琵琶湖１周","2018 06 04","icon_fishing");
        mListBagde.add(b4);
    }

    public static BadgeAPI getInstance() {
        if(instance ==null)
            instance = new BadgeAPI();
        return instance;
    }
}
