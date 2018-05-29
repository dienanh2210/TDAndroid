package vn.javis.tourde.utils;

import java.util.ArrayList;
import java.util.List;

public class CheckTwoList {
    public static List<Integer> getListIndex(String[] origin, List<String> listCompair)
    {
        List<Integer> listIndex = new ArrayList<>();
        for (int i=0;i<origin.length;i++) {
            if(listCompair.contains(origin[i]))
                listIndex.add(i);
        }
        return  listIndex;
    }
}
