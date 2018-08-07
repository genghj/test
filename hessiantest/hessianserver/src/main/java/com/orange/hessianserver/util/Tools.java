package com.orange.hessianserver.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Tools {
	/**
	 * sets为空 默认全部
	 * @param sets
	 * @param str
	 * @return
	 */
	public static boolean inThisSets(String sets, String str) {
		boolean result = false;
		if(Tools.isEmpty(sets)) return true;
		sets=sets.replaceAll(":","").replaceAll("：","").replaceAll(" ", "").replaceAll(",", ";").replaceAll(";;", ";");
		str= Tools.notEmpty(str)?str.replaceAll(":","").replaceAll("：","").replaceAll(" ", "").replaceAll(",", ";").replaceAll(";;", ";"):"";
		String[] as = sets.split(";");
		// 非区间
		for (String ass : as) {
			if (ass.indexOf("-") != -1) {// 非区间-区间
				String[] qja = ass.split("-");
				if (qja.length == 2 && !"".equals(qja[0]) && !"".equals(qja[1])) {
					Integer min = Integer.valueOf(qja[0].trim());
					Integer max = Integer.valueOf(qja[1].trim());
					Integer assi = Integer.valueOf(str.trim());
					if (assi >= min && assi < max) {
						result = true;
						break;
					}
				}
			} else {// 非区间-非区间
				if (str.equals(ass)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public static boolean inChannel(String localChannel, String channel) {
		boolean result = false;
		String[] as = localChannel.split(";");
		// 非区间
		for (String ass : as) {
			if (ass.indexOf("-") != -1) {// 非区间-区间
				String[] qja = ass.split("-");
				if (qja.length == 2 && !"".equals(qja[0]) && !"".equals(qja[1])) {
					Integer min = Integer.valueOf(qja[0].trim());
					Integer max = Integer.valueOf(qja[1].trim());
					Integer assi = Integer.valueOf(channel.trim());
					if (assi >= min && assi < max) {
						result = true;
						break;
					}
				}
			} else {// 非区间-非区间
				if (channel.equals(ass)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 检查冲突算法,字符串使用";"分隔，可以使用"-"表示区间 1111-1222; 必须是数字才可以比较
	 * @param newStr
	 * @param oldStr
	 * @return
	 */
	public static Set<String> conflictStrings(String newStr, String oldStr) {
		Set<String> set = new HashSet<String>();
		try {
			if (newStr == null || "".equals(oldStr.trim())) return set;
			String[] as = newStr.split(";");
			String[] bs = oldStr.split(";");
			for (String bss : bs) {
				if (Tools.isEmpty(bss)) continue;
				if (bss.indexOf("-") != -1) { // 区间
					for (String ass : as) {
						if (Tools.isEmpty(ass)) continue;
						if (ass.indexOf("-") != -1) { // 区间-区间
							String[] assa = ass.split("-");
							String[] bssa = bss.split("-");
							if (assa.length == 2 && !"".equals(assa[0])
									&& !"".equals(assa[1]) && bssa.length == 2
									&& !"".equals(bssa[0]) && !"".equals(bssa[1])) {
								int amin = 0;
								;
								int amax = 0;
								int a1 = 0;
								if (Integer.valueOf(assa[0]) < Integer.valueOf(assa[1])) {
									amin = Integer.valueOf(assa[0]);
									amax = Integer.valueOf(assa[1]);
								} else if (Integer.valueOf(assa[0]) > Integer.valueOf(assa[1])) {
									amin = Integer.valueOf(assa[1]);
									amax = Integer.valueOf(assa[0]);
								} else {
									a1 = Integer.valueOf(assa[0]);
								}
								int bmin = 0;
								;
								int bmax = 0;
								int b1 = 0;
								if (Integer.valueOf(bssa[0]) < Integer.valueOf(bssa[1])) {
									bmin = Integer.valueOf(bssa[0]);
									bmax = Integer.valueOf(bssa[1]);
								} else if (Integer.valueOf(bssa[0]) > Integer.valueOf(bssa[1])) {
									bmin = Integer.valueOf(bssa[1]);
									bmax = Integer.valueOf(bssa[0]);
								} else {
									b1 = Integer.valueOf(bssa[0]);
								}

								if (a1 == 0) {//区间
									if (b1 == 0) {
										//区间对比
										if (bmin >= amax || amin >= bmax) {
											//无冲突
										} else if (bmin == amin || amax == bmax) {
											if (bmin == amin) {
												Integer min = amin;
												Integer max = amax > bmax ? bmax : amax;
												set.add(String.valueOf(min + "-" + max));
											} else if (amax == bmax) {
												Integer min = amin > bmin ? amin : bmin;
												Integer max = amax;
												set.add(String.valueOf(min + "-" + max));
											}
										} else {
											Integer[] sort = new Integer[4];
											sort[0] = bmin;
											sort[1] = bmax;
											sort[2] = amin;
											sort[3] = amax;
											Arrays.sort(sort);
											set.add(String.valueOf(sort[1] + "-" + sort[2]));
										}

									} else {//区间-非区间
										set = getSetByFqjToFqj(set, String.valueOf(b1), ass);
									}
								} else {
									if (b1 == 0) {//非区间 区间
										set = getSetByFqjToFqj(set, String.valueOf(a1), bss);
									} else {
										if (a1 == b1) set.add(String.valueOf(a1));
									}
								}

							}
						} else {// 区间-非区间
							set = getSetByFqjToFqj(set, ass, bss);

						}
					}
				} else {// 非区间
					for (String ass : as) {
						if (Tools.isEmpty(ass)) continue;
						if (ass.indexOf("-") != -1) {// 非区间-区间
							set = getSetByFqjToFqj(set, bss, ass);
						} else {// 非区间-非区间
							if (bss.equals(ass))
								set.add(bss);
						}
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return set;
	}
	public static Set<String> getSetByFqjToFqj(Set<String> set, String fqj, String qj) {
		if(Tools.notEmpty(fqj)&&Tools.notEmpty(qj)) {
			String[] qja = qj.split("-");
			if (qja.length == 2 && !"".equals(qja[0]) && !"".equals(qja[1])) {
				Integer min = Integer.valueOf(qja[0]);
				Integer max = Integer.valueOf(qja[1]);
				Integer assi = Integer.valueOf(fqj);
				if (assi >= min && assi < max) {
					set.add(fqj);
				}
			}
		}
		return set;
	}

	/**
	 * 随机生成六位数验证码
	 * 
	 * @return
	 */
	public static int getRandomNum() {
		Random r = new Random();
		return r.nextInt(900000) + 100000;// (Math.random()*(999999-100000)+100000)
	}

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @param splitRegex
	 *            分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date) {
		if (notEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		} else {
			return null;
		}
	}

	/**
	 * 按照参数format的格式，日期转字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 把时间根据时、分、秒转换为时间段
	 * 
	 * @param StrDate
	 */
	public static String getTimes(String StrDate) {
		String resultTimes = "";

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now;

		try {
			now = new Date();
			Date date = df.parse(StrDate);
			long times = now.getTime() - date.getTime();
			long day = times / (24 * 60 * 60 * 1000);
			long hour = (times / (60 * 60 * 1000) - day * 24);
			long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

			StringBuffer sb = new StringBuffer();
			// sb.append("发表于：");
			if (hour > 0) {
				sb.append(hour + "小时前");
			} else if (min > 0) {
				sb.append(min + "分钟前");
			} else {
				sb.append(sec + "秒前");
			}

			resultTimes = sb.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return resultTimes;
	}


	public static boolean isNum(String strin) {
		String s = strin.replaceAll("[0-9;]+", "");
		if (s.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static int getWordCount(String s) {
		s = s.replaceAll("[^\\x00-\\xff]", "**");
		int length = s.length();
		return length;
	}

	/**
	 * 按字节长度截取字符串
	 * 
	 * @param str
	 *            将要截取的字符串参数
	 * @param toCount
	 *            截取的字节长度
	 * @param more
	 *            字符串末尾补上的字符串
	 * @return 返回截取后的字符串
	 */
	@SuppressWarnings("static-access")
	public static String substring(String str, int toCount, String more) {
		int reInt = 0;
		String reStr = "";
		if (str == null)
			return "";
		char[] tempChar = str.toCharArray();
		for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
			String s1 = str.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes();
			reInt += b.length;
			reStr += tempChar[kk];
		}
		if (toCount == reInt || (toCount == reInt - 1))
			reStr += more;
		return reStr;
	}



	/**
	 * 判断两个字符串是否相等
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static boolean isEquals(String one, String two) {
		if (one != null && two != null) {
			return one.trim().equals(two.trim());
		} else if (one == null && two == null) {
			return true;
		} else if (one == null && two.trim().length() == 0) {
			return true;
		} else if (two == null && one.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将json串转化为对象，如果对象为空，进行初始化
	 * @param jsonStr
	 * @param clazz
	 * @param <T>
     * @return
     */
	public static <T> T transJsonStrToObject(String jsonStr, Class<T> clazz){
		T obj= JSONObject.parseObject(jsonStr, clazz);
		if(obj==null){
			try {
				obj=clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	public static int[] unique(int[] array){
		if (array.length == 0) {
			return array;
		}
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0, len = array.length; i < len; i++) {
			set.add(array[i]);
		}
		Iterator it = set.iterator();
		int[] result = new int[set.size()];
		int i=0;
		while(it.hasNext()){
			result[i++] = (int)it.next();
		}
		return result;


	}






}