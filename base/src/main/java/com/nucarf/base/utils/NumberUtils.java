package com.nucarf.base.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Amor on 2018/5/25.
 */

public class NumberUtils {

    /**
     * 四舍五入保留两位
     *
     * @param money
     * @return
     */
    public static String totalMoney(String money) {
        Double mAmountF = Double.parseDouble(money) / 100;
        BigDecimal bigDec = new BigDecimal(mAmountF);
        double total = bigDec.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(total);
    }

    //获取截取后的金额字符串类型
    public static String getEndAmountString(String pAmount) {
        if (!TextUtils.isEmpty(pAmount)) {
            Double mAmountF = Double.parseDouble(pAmount);
            return amountToFolat(mAmountF);
        } else {
            return "0";
        }
    }

    //将金额转成 float 并且 只截取整数
    public static String amountToFolat(Double f) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String str = df.format(f).toString();
//        if(str.split("\\.")[1].equals("00")){
        str = str.split("\\.")[0];
//        }
        return str;
    }

    /**
     * 转换万
     *
     * @param number
     * @return
     */
    public static String formatWan(String number) {
        BigDecimal bigDecimal = new BigDecimal(number);
        if (bigDecimal.compareTo(BigDecimal.valueOf(10000)) == 1) {
            // 转换为万元（除以10000）
            BigDecimal decimal = bigDecimal.divide(new BigDecimal("10000"));
            // 保留两位小数
            DecimalFormat formater = new DecimalFormat("0.00");
            // 四舍五入
            formater.setRoundingMode(RoundingMode.HALF_UP);    // 5000008.89

            // 格式化完成之后得出结果
            String formatNum = formater.format(decimal) + "W";
            return formatNum;
        } else {
            return number;
        }

    }

    /**
     * 让 Map按key进行排序
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

//实现一个比较器类

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);  //从小到大排序
        }

    }


}
