package vn.javis.tourde.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import vn.javis.tourde.model.Data;

public class SearchCourseUtils {

    public List<Data> createResearchCourseData() {
        List<Data> dataList = new ArrayList<>();
        String[] contentList1 = new String[]{"〜20km", "〜50km", "〜100km", "〜100km"};
        String[] contentList2 = new String[]{"激坂", "坂少"};
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
    public List<Data> createDataSelectArea() {
        List<Data> dataList = new ArrayList<>();
        String[] contentList1 = new String[]{"北海道"};
        String[] contentList2 = new String[]{"青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県"};
        String[] contentList3 = new String[]{"茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県"};
        String[] contentList4 = new String[]{"新潟県", "富山県", "石川県", "福井県", "長野県", "山梨県", "岐阜県", "静岡県", "愛知県"};
        String[] contentList5 = new String[]{"三重県", "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県"};
        String[] contentList6 = new String[]{"鳥取県", "島根県", "岡山県", "広島県", "山口県"};
        String[] contentList7 = new String[]{"徳島県", "香川県", "愛媛県", "高知県"};
        String[] contentList8 = new String[]{"福岡県", "佐賀県", "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県"};
        Data data1 = new vn.javis.tourde.model.Data();
        data1.setTitle("北海道地方");
        data1.setContent(Arrays.asList(contentList1));
        dataList.add(data1);
        Data data2 = new vn.javis.tourde.model.Data();
        data2.setTitle("東北地方");
        data2.setContent(Arrays.asList(contentList2));
        dataList.add(data2);
        Data data3 = new vn.javis.tourde.model.Data();
        data3.setTitle("関東地方");
        data3.setContent(Arrays.asList(contentList3));
        dataList.add(data3);
        Data data4 = new vn.javis.tourde.model.Data();
        data4.setTitle("中部地方");
        data4.setContent(Arrays.asList(contentList4));
        dataList.add(data4);
        Data data5 = new vn.javis.tourde.model.Data();
        data5.setTitle("近畿地方");
        data5.setContent(Arrays.asList(contentList5));
        dataList.add(data5);
        Data data6 = new vn.javis.tourde.model.Data();
        data6.setTitle("中国地方");
        data6.setContent(Arrays.asList(contentList6));
        dataList.add(data6);
        Data data7 = new vn.javis.tourde.model.Data();
        data7.setTitle("四国地方");
        data7.setContent(Arrays.asList(contentList7));
        dataList.add(data7);
        Data data8 = new Data();
        data8.setTitle("四国地方");
        data8.setContent(Arrays.asList(contentList8));
        dataList.add(data8);
        return dataList;
    }

    public List<Data> createAdditionalConditionData() {
        List<Data> dataList = new ArrayList<>();
        String[] contentList1 = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
        String[] contentList2 = new String[]{"グルメが充実", "絶景・景観が充実", "名所・観光地が充実", "フォトスポットが充実", "トレーニング要素が充実"};
        String[] contentList3 = new String[]{"都会・街中", "田舎・村里", "森林・林間"};
        String[] contentList4 = new String[]{"グループライド向き", "ソロ向き", "女性向き", "ファミリー向き", "カップル向き"};
        String[] contentList5 = new String[]{"初級者向き", "中級者向き", "上級者向き"};
        String[] contentList6 = new String[]{"初級者の脚で３時間", "初級者の脚で半日", "初級者の脚で１日", "２日間以上"};
        String[] contentList7 = new String[]{"信号が少ない", "交通量が少ない", "路面状態が良好", "オフロードあり"};
        String[] contentList8 = new String[]{"坂好き向き", "坂嫌い向き"};
        String[] contentList9 = new String[]{"サイクリング大会公式コース", "アプリ内イベント専用コース","季節限定コース"};
        Data data1 = new Data();
        data1.setTitle("おすすめの月");
        data1.setContent(Arrays.asList(contentList1));
        dataList.add(data1);
        Data data2 = new Data();
        data2.setTitle("コーステーマ");
        data2.setContent(Arrays.asList(contentList2));
        dataList.add(data2);
        Data data3 = new Data();
        data3.setTitle("コースエリア");
        data3.setContent(Arrays.asList(contentList3));
        dataList.add(data3);
        Data data4 = new Data();
        data4.setTitle("属性適正");
        data4.setContent(Arrays.asList(contentList4));
        dataList.add(data4);
        Data data5 = new Data();
        data5.setTitle("レベル適正");
        data5.setContent(Arrays.asList(contentList5));
        dataList.add(data5);
        Data data6 = new Data();
        data6.setTitle("所要時間");
        data6.setContent(Arrays.asList(contentList6));
        dataList.add(data6);
        Data data7 = new Data();
        data7.setTitle("道路環境");
        data7.setContent(Arrays.asList(contentList7));
        dataList.add(data7);
        Data data8 = new Data();
        data8.setTitle("登坂");
        data8.setContent(Arrays.asList(contentList8));
        dataList.add(data8);
        Data data9 = new Data();
        data9.setTitle("特殊コース");
        data9.setContent(Arrays.asList(contentList9));
        dataList.add(data9);
        return dataList;

    }
}
