package com.bmadmin.common;



import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BMUtil {
		/**
		 * Purpose:判断一个字符串是否为空
		 * 
		 * @author wojy
		 * @param value
		 * @return
		 */
		public static boolean isNotNull(String value) {
			boolean isNull = false;
			if (value != null && value.trim().length() > 0
					&& !"null".equalsIgnoreCase(value)) {
				isNull = true;
			}
			return isNull;
		}

		public static boolean isNullOrEmpty(Object paramObject) {
			return ((paramObject == null) || ("".equals(paramObject.toString())));
		}

		/**
		 * wjy 长度20位
		 * 
		 * @return
		 */
		public static String getCguid() {
			String rt = "";
			Date dt = new Date();
			SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMddhhmmss");

			rt = date_format.format(dt);
			rt += getRandmonVerifyCode(6);

			return rt;
		}

		/**
		 * 生成制定长度验证码
		 * 
		 * @param verifyCode_len
		 *            验证码长度
		 * @return String
		 */
		private synchronized static String getRandmonVerifyCode(int verifyCode_len) {
			char[] c = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
			int maxNum = 100;
			int count = 0;// 记录验证码长度

			StringBuffer verifyCodeStr = new StringBuffer();
			Random random = new Random(System.currentTimeMillis());
			while (count < verifyCode_len) {
				int i = random.nextInt(maxNum);
				if (i >= 0 && i < c.length) {
					verifyCodeStr.append(String.valueOf(i));
					count++;
				}
			}
			return verifyCodeStr.toString();
		}

		/**
		 * 列表内miniui框架时间转指定时间格式 wojy
		 * 
		 * @param time
		 * @return
		 */
		public static String getMiniuiCSTTime(String time) {
			if (isNotNull(time)) {
				Date annTime = new Date(time);
				SimpleDateFormat CeshiFmt = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				TimeZone gmtTime = TimeZone.getTimeZone("GMT-06:00");
				CeshiFmt.setTimeZone(gmtTime);
				time = CeshiFmt.format(annTime);
				return time;
			} else {
				return "";
			}
		}

		/**
		 *  列表内miniui框架时间转指定时间格式 wojy
		 * 
		 * @param time
		 * @return
		 */
		public static String getMiniuiCSTTimeYYYY_MM_DD(String time) {
			if (isNotNull(time)) {
				Date annTime = new Date(time);
				SimpleDateFormat CeshiFmt = new SimpleDateFormat("yyyy-MM-dd");
				TimeZone gmtTime = TimeZone.getTimeZone("GMT-06:00");
				CeshiFmt.setTimeZone(gmtTime);
				time = CeshiFmt.format(annTime);
				return time;
			} else {
				return "";
			}
		}

		/**
		 * 查询条件miniui框架时间转指定时间格式 wojy
		 * 
		 * @param time
		 * @return
		 */
		public static String getMiniuiGMT8Time(String time) {
			if (isNotNull(time)) {
				Date annTime = new Date(time);
				SimpleDateFormat CeshiFmt = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				TimeZone gmtTime = TimeZone.getTimeZone("GMT+08:00");
				CeshiFmt.setTimeZone(gmtTime);
				time = CeshiFmt.format(annTime);
				return time;
			} else {
				return "";
			}
		}
		
		/**
		 * 查询条件miniui框架时间转指定时间格式 wojy
		 * 
		 * @param time
		 * @return
		 */
		public static String getMiniuiGMT8TimeYYYY_MM_DD(String time) {
			if (isNotNull(time)) {
				Date annTime = new Date(time);
				SimpleDateFormat CeshiFmt = new SimpleDateFormat(
						"yyyy-MM-dd");
				TimeZone gmtTime = TimeZone.getTimeZone("GMT+08:00");
				CeshiFmt.setTimeZone(gmtTime);
				time = CeshiFmt.format(annTime);
				return time;
			} else {
				return "";
			}
		}

		/**
		 * 把传入的数转换为中文金额大写形式
		 * 
		 * @param flag
		 *            int 标志位，1 表示转换整数部分，0 表示转换小数部分
		 * @param s
		 *            String 要转换的字符串
		 * @return 转换好的带单位的中文金额大写形式
		 */
		private String numFormat(int flag, String s) {
			int sLength = s.length();
			// 货币大写形式
			String bigLetter[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
			// 货币单位
			String unit[] = { "元", "拾", "佰", "仟", "万",
					// 拾万位到仟万位
					"拾", "佰", "仟",
					// 亿位到万亿位
					"亿", "拾", "佰", "仟", "万" };
			String small[] = { "分", "角" };
			// 用来存放转换后的新字符串
			String newS = "";
			// 逐位替换为中文大写形式
			for (int i = 0; i < sLength; i++) {
				if (flag == 1) {
					// 转换整数部分为中文大写形式（带单位）
					newS = newS + bigLetter[s.charAt(i) - 48]
							+ unit[sLength - i - 1];
				} else if (flag == 2) {
					// 转换小数部分（带单位）
					newS = newS + bigLetter[s.charAt(i) - 48]
							+ small[sLength - i - 1];
				}
			}
			return newS;
		}
	    
	    public static String HtmlEncode(String theString){
		    theString = theString.replaceAll("\n", "\\\\n");
		    return theString;
	    }
	    
//	    public static String HtmlDiscode(String theString){
//		    theString = theString.replaceAll("&gt;", ">");
//		    theString = theString.replaceAll("&lt;", "<");
//		    theString = theString.replaceAll("&nbsp;", " ");
//		    theString = theString.replaceAll("&quot;", "\"");
//		    theString = theString.replaceAll("&#39;", "\'");
//		    theString = theString.replaceAll("<br/>", "\n");
//		    return theString;
//	    }
	    
	    /**
	     * 保留两位小数 乘法
	     */
	    public static String Plus(String val1,String val2){
	    	NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(2);
	    	BigDecimal bd1 = new BigDecimal(Double.parseDouble(val1));
	    	BigDecimal bd2 = new BigDecimal(Double.parseDouble(val2));
	    	BigDecimal num = bd1.multiply(bd2);
	    	String str = nf.format(num);
	    	return str;
	    }
	    
	    //默认除法运算精度
	    private static final int DEF_DIV_SCALE = 10;

	    /**
	    * 提供精确的加法运算。
	    * @param v1 被加数
	    * @param v2 加数
	    * @return 两个参数的和
	    */
	    public static double add(double v1,double v2)
	    {
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));
	        return b1.add(b2).doubleValue();
	    }
	    
	    /**
	    * 提供精确的减法运算。
	    * @param v1 被减数
	    * @param v2 减数
	    * @return 两个参数的差
	    */
	    public static double sub(double v1,double v2){
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));
	        return b1.subtract(b2).doubleValue();
	    }
	    /**
	    * 提供精确的乘法运算。
	    * @param v1 被乘数
	    * @param v2 乘数
	    * @return 两个参数的积
	    */
	    public static double mul(double v1,double v2)
	    {
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));
	        return b1.multiply(b2).doubleValue();
	    }
	    /**
	    * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	    * 小数点以后10位，以后的数字四舍五入。
	    * @param v1 被除数
	    * @param v2 除数
	    * @return 两个参数的商
	    */
	    public static double div(double v1,double v2)
	    {
	        return div(v1,v2,DEF_DIV_SCALE);
	    }
	    /**
	    * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	    * 定精度，以后的数字四舍五入。
	    * @param v1 被除数
	    * @param v2 除数
	    * @param scale 表示表示需要精确到小数点以后几位。
	    * @return 两个参数的商
	    */
	    public static double div(double v1,double v2,int scale)
	    {
	        if(scale<0)
	        {
	            throw new IllegalArgumentException("The scale must be a positive integer or zero");
	        }
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));
	        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	    }
	    /**
	    * 提供精确的小数位四舍五入处理。
	    * @param v 需要四舍五入的数字
	    * @param scale 小数点后保留几位
	    * @return 四舍五入后的结果
	    */
	    public static double round(double v,int scale)
	    {
	        if(scale<0)
	        {
	            throw new IllegalArgumentException("The scale must be a positive integer or zero");
	        }
	        BigDecimal b = new BigDecimal(Double.toString(v));
	        BigDecimal one = new BigDecimal("1");
	        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	    }
	    /**
		 * 把null转换成空字符串
		 */
		public static String NulltoString(Object o) {
			if (o == null)
				return "";
			return o.toString();
		}
		public static String NulltoZero(Object o) {
			if (o == null)
				return "0";
			if(o.equals(""))
				return "0";
			return o.toString();
		}
		public static String Object2String(Object t){
			if(t == null) return "";
			if("null".equals(String.valueOf(t))) return "";
			return String.valueOf(t);
		}
		
		public static String getRandomString(int length) { //length表示生成字符串的长度
		    String base = "abcdefghijklmnopqrstuvwxyz0123456789";  
		    Random random = new Random();  
		    StringBuffer sb = new StringBuffer();  
		    for (int i = 0; i < length; i++) {  
		        int number = random.nextInt(base.length());  
		        sb.append(base.charAt(number));  
		    }  
		    return sb.toString();  
		}
		
//		public static Map JsonToMap(String s) {
//			Map map = new HashMap();
//			JSONObject json = JSONObject.fromObject(s);
//			Iterator keys = json.keys();
//			while (keys.hasNext()) {
//				String key = (String) keys.next();
//				String value = json.get(key).toString();
//				if (value.startsWith("{") && value.endsWith("}")) {
//					map.put(key, JsonToMap(value));
//				} else {
//					map.put(key, value);
//				}
//			}
//			return map;
//		}
	//判断表示是否全为数字
	public static boolean strIsNum(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}

	//判断浮点数（double和float）
	public static boolean strIsDouble(String str){
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}


	//判断是否是日期格式YYYY-mm
	public static boolean strIsYearMonth(String str){
		SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM");
		try{
			dateFormat.parse(str);
			return true;
		}catch (Exception e){
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	//判断是否是日期格式YYYY
	public static boolean strIsYear(String str){
		SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy");
		try{
			dateFormat.parse(str);
			return true;
		}catch (Exception e){
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

//	public static void main(String[] args) {
//
//		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-HH-dd HH:mm:ss"));
//		System.out.println(now);
//
//	}
}
