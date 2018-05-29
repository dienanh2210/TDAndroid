package vn.javis.tourde.utils;

import java.util.ArrayList;
import java.util.List;

public class BinaryConvert {
    public static String convertToMonths(int number){
      //  List<Integer> listMonths = new ArrayList<>();
        String listMonths="";
        List<Integer> list1 = new ArrayList<>();
        int divisor =number;
        int result=0;

        while (divisor>=2)
        {
            result = divisor%2;
            divisor = divisor/2;
            list1.add(result);
        }
        if(divisor>0)
            list1.add(divisor);

        for(int i=0;i<list1.size();i++)
        {
            if(list1.get(i)==1)
            {
                if(listMonths=="")
                    listMonths+=String.valueOf(i+1);
                else
                    listMonths+=","+String.valueOf(i+1);
            }

        }

        return  listMonths;
    }
}
