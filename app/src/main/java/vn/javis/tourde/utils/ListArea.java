package vn.javis.tourde.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class ListArea {

    public static ArrayList<String> getAreas() {
        final String[] contentList1 = new String[]{"北海道"};
        final String[] contentList2 = new String[]{"青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県"};
        final String[] contentList3 = new String[]{"茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県"};
        final String[] contentList4 = new String[]{"新潟県", "富山県", "石川県", "福井県", "長野県", "山梨県", "岐阜県", "静岡県", "愛知県"};
        final String[] contentList5 = new String[]{"三重県", "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県"};
        final String[] contentList6 = new String[]{"鳥取県", "島根県", "岡山県", "広島県", "山口県"};
        final String[] contentList7 = new String[]{"徳島県", "香川県", "愛媛県",  "高知県"};
        final String[] contentList8 = new String[]{"福岡県", "佐賀県", "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県"};
        final ArrayList<String> listArea = new ArrayList<>();
        listArea.addAll(Arrays.asList(contentList1));
        listArea.addAll(Arrays.asList(contentList2));
        listArea.addAll(Arrays.asList(contentList3));
        listArea.addAll(Arrays.asList(contentList4));
        listArea.addAll(Arrays.asList(contentList5));
        listArea.addAll(Arrays.asList(contentList6));
        listArea.addAll(Arrays.asList(contentList7));
        listArea.addAll(Arrays.asList(contentList8));
        return listArea;
    }
    public static String getAreaName(int index){
        final String[] contentList1 = new String[]{"北海道"};
        final String[] contentList2 = new String[]{"青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県"};
        final String[] contentList3 = new String[]{"茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県"};
        final String[] contentList4 = new String[]{"新潟県", "富山県", "石川県", "福井県", "長野県", "山梨県", "岐阜県", "静岡県", "愛知県"};
        final String[] contentList5 = new String[]{"三重県", "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県"};
        final String[] contentList6 = new String[]{"鳥取県", "島根県", "岡山県", "広島県", "山口県"};
        final String[] contentList7 = new String[]{"徳島県", "香川県", "愛媛県",  "高知県"};
        final String[] contentList8 = new String[]{"福岡県", "佐賀県", "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県"};

;

        final ArrayList<String> listArea = new ArrayList<>();
        listArea.addAll(Arrays.asList(contentList1));
        listArea.addAll(Arrays.asList(contentList2));
        listArea.addAll(Arrays.asList(contentList3));
        listArea.addAll(Arrays.asList(contentList4));
        listArea.addAll(Arrays.asList(contentList5));
        listArea.addAll(Arrays.asList(contentList6));
        listArea.addAll(Arrays.asList(contentList7));
        listArea.addAll(Arrays.asList(contentList8));
        return  listArea.get(index);
    }

}
