package vn.javis.tourde.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import vn.javis.tourde.model.Data;
import vn.javis.tourde.model.MultiCheckGenre;

public class SearchCourseUtils {

    public List<Data> createResearchCourseData() {
        List<Data> dataList = new ArrayList<>();
        String[] contentList1 = new String[]{"〜20km", "〜50km", "〜100km", "100km〜"};
        String[] contentList2 = new String[]{"1000m以上（坂多）", "200m以下（坂少）"};
        String[] contentList3 = new String[]{"片道", "往復", "1周", ""};

        dataList = new ArrayList<>();
        Data data1 = new Data();
        data1.setTitle("距離");
        data1.setContent(Arrays.asList(contentList1));
        dataList.add(data1);
        Data data2 = new Data();
        data2.setTitle("獲得標高");
        data2.setContent(Arrays.asList(contentList2));
        dataList.add(data2);
        Data data3 = new Data();
        data3.setTitle("コース形態");
        data3.setContent(Arrays.asList(contentList3));
        dataList.add(data3);

        return dataList;
    }
    public int getIndexPrefecture(String s){
        ArrayList<String> dataList = new ArrayList<>();
        String[] contentList1 = new String[]{"北海道"};
        String[] contentList2 = new String[]{"青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県"};
        String[] contentList3 = new String[]{"茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県"};
        String[] contentList4 = new String[]{"新潟県", "富山県", "石川県", "福井県", "長野県", "山梨県", "岐阜県", "静岡県", "愛知県"};
        String[] contentList5 = new String[]{"三重県", "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県"};
        String[] contentList6 = new String[]{"鳥取県", "島根県", "岡山県", "広島県", "山口県"};
        String[] contentList7 = new String[]{"徳島県", "香川県", "愛媛県", "高知県"};
        String[] contentList8 = new String[]{"福岡県", "佐賀県", "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県"};
        dataList.addAll(Arrays.asList(contentList1));
        dataList.addAll(Arrays.asList(contentList2));
        dataList.addAll(Arrays.asList(contentList3));
        dataList.addAll(Arrays.asList(contentList4));
        dataList.addAll(Arrays.asList(contentList5));
        dataList.addAll(Arrays.asList(contentList6));
        dataList.addAll(Arrays.asList(contentList7));
        dataList.addAll(Arrays.asList(contentList8));
        return  dataList.indexOf(s)+1;
    }
    public String getIndexSeason(String s){

        ArrayList<String> dataList = new ArrayList<>();
        ArrayList<String> dataList2 = new ArrayList<>();
        String[] contentList1 = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
        String[] contentList2 = new String[]{"1","2","4","8","10","32","64","128","256","512","1024","2048"};
        dataList.addAll(Arrays.asList(contentList1));
        dataList2.addAll(Arrays.asList(contentList2));
        if(dataList.contains(s)) {
            return dataList2.get(dataList.indexOf(s));
        }
        return "-1";
    }
    public String getIndexTag(String s){

        ArrayList<String> dataList = new ArrayList<>();
        ArrayList<String> dataList2 = new ArrayList<>();
        String[] contentList1 = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
        String[] contentList2 = new String[]{"1","2","4","8","10","32","64","128","256","512","1024","2048"};
        if(dataList.contains(s)) {
            dataList.addAll(Arrays.asList(contentList1));
            dataList2.addAll(Arrays.asList(contentList2));
            return dataList2.get(dataList.indexOf(s));
        }
        return "-1";
    }
    public List<MultiCheckGenre> createDataSelectArea() {
        List<MultiCheckGenre> dataList = new ArrayList<>();
        String[] contentList1 = new String[]{"北海道"};
        String[] contentList2 = new String[]{"青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県"};
        String[] contentList3 = new String[]{"茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県"};
        String[] contentList4 = new String[]{"新潟県", "富山県", "石川県", "福井県", "長野県", "山梨県", "岐阜県", "静岡県", "愛知県"};
        String[] contentList5 = new String[]{"三重県", "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県"};
        String[] contentList6 = new String[]{"鳥取県", "島根県", "岡山県", "広島県", "山口県"};
        String[] contentList7 = new String[]{"徳島県", "香川県", "愛媛県", "高知県"};
        String[] contentList8 = new String[]{"福岡県", "佐賀県", "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県"};
        dataList.add(new MultiCheckGenre("北海道地方", Arrays.asList(contentList1)));
        dataList.add(new MultiCheckGenre("東北地方", Arrays.asList(contentList2)));
        dataList.add(new MultiCheckGenre("関東地方", Arrays.asList(contentList3)));
        dataList.add(new MultiCheckGenre("中部地方", Arrays.asList(contentList4)));
        dataList.add(new MultiCheckGenre("近畿地方", Arrays.asList(contentList5)));
        dataList.add(new MultiCheckGenre("中国地方", Arrays.asList(contentList6)));
        dataList.add(new MultiCheckGenre("四国地方", Arrays.asList(contentList7)));
        dataList.add(new MultiCheckGenre("九州地方", Arrays.asList(contentList8)));
        return dataList;
    }

    public List<MultiCheckGenre> createAdditionalConditionData() {
        List<MultiCheckGenre> dataList = new ArrayList<>();
        String[] contentList1 = new String[]{"1月"};
        String[] contentList2 = new String[]{"グルメが充実", "絶景・景観が充実", "名所・観光地が充実", "フォトスポットが充実", "トレーニング要素が充実","オールシーズン走れるコース", "推奨季節のあるコース"};
        String[] contentList3 = new String[]{"都会・街中", "田舎・村里", "森林・林間"};
        String[] contentList4 = new String[]{"グループライド向き", "ソロ向き", "女性向き", "ファミリー向き", "カップル向き"};
        String[] contentList5 = new String[]{"初級者向き", "中級者向き", "上級者向き"};
        String[] contentList6 = new String[]{"初級者の脚で３時間", "初級者の脚で半日", "初級者の脚で１日", "２日間以上"};
        String[] contentList7 = new String[]{"信号が少ない", "交通量が少ない", "路面状態が良好", "オフロードあり"};
        String[] contentList8 = new String[]{"坂好き向き", "坂嫌い向き"};
        String[] contentList9 = new String[]{"サイクリング大会公式コース", "アプリ内イベント専用コース","季節限定コース"};
        dataList.add(new MultiCheckGenre("コースの旬", Arrays.asList(contentList1), true));
        dataList.add(new MultiCheckGenre("コーステーマ", Arrays.asList(contentList2)));
        dataList.add(new MultiCheckGenre("コースロケーション", Arrays.asList(contentList3)));
        dataList.add(new MultiCheckGenre("コースの走り方タイプ", Arrays.asList(contentList4)));
        dataList.add(new MultiCheckGenre("コースレベル", Arrays.asList(contentList5)));
        dataList.add(new MultiCheckGenre("完走目安時間", Arrays.asList(contentList6)));
        dataList.add(new MultiCheckGenre("道路環境", Arrays.asList(contentList7)));
        dataList.add(new MultiCheckGenre("登り坂", Arrays.asList(contentList8)));
        dataList.add(new MultiCheckGenre("特殊コース", Arrays.asList(contentList9)));


        return dataList;

    }
}
