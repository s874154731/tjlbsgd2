package com.sgd.tjlb.zhxf.utils;

import java.math.BigDecimal;

/**
 * @ProjectName: AssembleChat
 * @Package: com.duanfeng.assemblechat.ui.activity.mine.share.utils
 * @ClassName: DecimalUtils
 * @Description: DecimalUtils 工具类
 * @CreateDate: 2022/1/5/005 11:52
 * @UpdateUser: shi
 * @UpdateDate: 2022/1/5/005 11:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BigDecimalUtils {
    /**
     * 加法计算（result = x + y）
     *
     * @param x 被加数（可为null）
     * @param y 加数 （可为null）
     * @return 和 （可为null）
     * @author dengcs
     */
    public static BigDecimal add(BigDecimal x, BigDecimal y) {
        if (x == null) {
            return y;
        }
        if (y == null) {
            return x;
        }
        return x.add(y);
    }

    /**
     * 加法计算（result = a + b + c + d）
     *
     * @param a 被加数（可为null）
     * @param b 加数（可为null）
     * @param c 加数（可为null）
     * @param d 加数（可为null）
     * @return BigDecimal （可为null）
     * @author dengcs
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b, BigDecimal c, BigDecimal d) {
        BigDecimal ab = add(a, b);
        BigDecimal cd = add(c, d);
        return add(ab, cd);
    }

    /**
     * 累加计算(result=x + result)
     *
     * @param x      被加数（可为null）
     * @param result 和 （可为null,若被加数不为为null，result默认值为0）
     * @return result 和 （可为null）
     * @author dengcs
     */
    public static BigDecimal accumulate(BigDecimal x, BigDecimal result) {
        if (x == null) {
            return result;
        }
        if (result == null) {
            result = new BigDecimal("0");
        }
        return result.add(x);
    }

    /**
     * 减法计算(result = x - y)
     *
     * @param x 被减数（可为null）
     * @param y 减数（可为null）
     * @return BigDecimal 差 （可为null）
     * @author dengcs
     */
    public static BigDecimal subtract(BigDecimal x, BigDecimal y) {
        if (x == null || y == null) {
            return null;
        }
        return x.subtract(y);
    }

    /**
     * 乘法计算(result = x × y)
     *
     * @param x 乘数(可为null)
     * @param y 乘数(可为null)
     * @return BigDecimal 积
     * @author dengcs
     */
    public static BigDecimal multiply(BigDecimal x, BigDecimal y) {
        if (x == null || y == null) {
            return null;
        }
        return x.multiply(y);
    }

    /**
     * 除法计算(result = x ÷ y)
     *
     * @param x 被除数（可为null）
     * @param y 除数（可为null）
     * @return 商 （可为null,四舍五入，默认保留20位小数）
     * @author dengcs
     */
    public static BigDecimal divide(BigDecimal x, BigDecimal y) {
        if (x == null || y == null || y.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        // 结果为0.000..时，不用科学计数法展示
        return stripTrailingZeros(x.divide(y, 20, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 转为字符串(防止返回可续计数法表达式)
     *
     * @param x 要转字符串的小数
     * @return String
     * @author dengcs
     */
    public static String toPlainString(BigDecimal x) {
        if (x == null) {
            return null;
        }
        return x.toPlainString();
    }

    /**
     * 保留小数位数
     *
     * @param x 目标小数 2位小数
     * @return BigDecimal 结果四舍五入
     * @author dengcs
     */
    public static String scale(BigDecimal x) {
        if (x == null) {
            return "0";
        }
        //除以100
//        BigDecimal newX = divide(x, new BigDecimal(100));
        BigDecimal bdl = scale(x, 2);
        return bdl.toString();
    }

    /**
     * 保留小数位数
     *
     * @param x         目标小数 2位小数
     * @param divideNum 除以的位数
     * @return BigDecimal 结果四舍五入
     */
    public static String scaleDivide(BigDecimal x, int divideNum) {
        if (x == null) {
            return "0";
        }
        divideNum = divideNum <= 0 ? 1 : divideNum;
        //除以100
        BigDecimal newX = divide(x, new BigDecimal(divideNum));
        BigDecimal bdl = scale(newX, 2);
        return bdl.toString();
    }

    /**
     * 保留小数位数
     *
     * @param x     目标小数
     * @param scale 要保留小数位数
     * @return BigDecimal 结果四舍五入
     * @author dengcs
     */
    public static BigDecimal scale(BigDecimal x, int scale) {
        if (x == null) {
            return null;
        }
        return x.setScale(scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 保留小数位数
     *
     * @param x     目标小数
     * @param scale 要保留小数位数
     * @return BigDecimal 结果四舍五入
     * @author dengcs
     */
    public static BigDecimal scale2(BigDecimal x, int scale) {
        if (x == null) {
            return null;
        }
        return x.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 整型转为BigDecimal
     *
     * @param x(可为null)
     * @return BigDecimal (可为null)
     * @author dengcs
     */
    public static BigDecimal toBigDecimal(Integer x) {
        if (x == null) {
            return null;
        }
        return new BigDecimal(x.toString());
    }

    /**
     * 长整型转为BigDecimal
     *
     * @param x(可为null)
     * @return BigDecimal (可为null)
     * @author dengcs
     */
    public static BigDecimal toBigDecimal(Long x) {
        if (x == null) {
            return null;
        }
        return new BigDecimal(x.toString());
    }

    /**
     * 双精度型转为BigDecimal
     *
     * @param x(可为null)
     * @return BigDecimal (可为null)
     * @author dengcs
     */
    public static BigDecimal toBigDecimal(Double x) {
        if (x == null) {
            return null;
        }
        return new BigDecimal(x.toString());
    }

    /**
     * 单精度型转为BigDecimal
     *
     * @param x(可为null)
     * @return BigDecimal (可为null)
     * @author dengcs
     */
    public static BigDecimal toBigDecimal(Float x) {
        if (x == null) {
            return null;
        }
        return new BigDecimal(x.toString());
    }

    /**
     * 字符串型转为BigDecimal
     *
     * @param x(可为null)
     * @return BigDecimal (可为null)
     * @author dengcs
     */
    public static BigDecimal toBigDecimal(String x) {
        if (x == null) {
            return null;
        }
        return new BigDecimal(x);
    }

    /**
     * 对象类型转为BigDecimal
     *
     * @param x(可为null)
     * @return BigDecimal (可为null)
     * @author dengcs
     */
    public static BigDecimal toBigDecimal(Object x) {
        if (x == null) {
            return null;
        }
        BigDecimal result = null;
        try {
            result = new BigDecimal(x.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 倍数计算，用于单位换算
     *
     * @param x        目标数(可为null)
     * @param multiple 倍数 (可为null)
     * @return BigDecimal (可为null)
     * @author dengcs
     */
    public static BigDecimal multiple(BigDecimal x, Integer multiple) {
        if (x == null || multiple == null) {
            return null;
        }
        return BigDecimalUtils.multiply(x, toBigDecimal(multiple));
    }

    /**
     * 去除小数点后的0（如: 输入1.000返回1）
     *
     * @param x 目标数(可为null)
     * @return
     */
    public static BigDecimal stripTrailingZeros(BigDecimal x) {
        if (x == null) {
            return null;
        }
        return x.stripTrailingZeros();
    }

    /**
     * 乘以一百并且去掉小数点
     *
     * @param money
     * @return
     */
    public static String multiply100(BigDecimal money) {
        BigDecimal tradingMoney = BigDecimalUtils.multiply(money, new BigDecimal(100));
        return BigDecimalUtils.scale(tradingMoney, 0).toString();
    }

    /**
     * 乘以 多少
     *
     * @param money
     * @param count 倍数
     * @return
     */
    public static BigDecimal multiplyCount(BigDecimal money, int count) {
        return BigDecimalUtils.multiply(money, new BigDecimal(count));
    }

    /***
     *
     * setScale(1,BigDecimal.ROUND_DOWN);//直接删除多余的小数位，如2.35会变成2.3
     *
     * setScale(1,BigDecimal.ROUND_UP);//进位处理，2.35变成2.4
     *
     * setScale(1,BigDecimal.ROUND_HALF_UP);//四舍五入，2.35变成2.4
     *
     * setScaler(1,BigDecimal.ROUND_HALF_DOWN);//四舍五入，2.35变成2.3，如果是5则向下舍
     *
     *
     *
     * //前提为a、b均不能为null
     * if(a.compareTo(b) == -1){
     *     System.out.println("a小于b");
     * }
     *
     * if(a.compareTo(b) == 0){
     *     System.out.println("a等于b");
     * }
     *
     * if(a.compareTo(b) == 1){
     *     System.out.println("a大于b");
     * }
     *
     * if(a.compareTo(b) > -1){
     *     System.out.println("a大于等于b");
     * }
     *
     * if(a.compareTo(b) < 1){
     *     System.out.println("a小于等于b");
     * }
     */
    public static int compare(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2);
    }
}
