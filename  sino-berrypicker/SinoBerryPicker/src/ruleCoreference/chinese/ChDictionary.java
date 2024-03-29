package ruleCoreference.chinese;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import util.Common;

public class ChDictionary {

	public ChDictionary() {
		personPronouns.addAll(this.animatePronouns);
		allPronouns.addAll(firstPersonPronouns);
		allPronouns.addAll(secondPersonPronouns);
		allPronouns.addAll(thirdPersonPronouns);
		allPronouns.addAll(otherPronouns);
		// System.out.println(ChDictionary.class.getResource("../../dict/surname")
		// + "#");
		this.surnames = Common.readSurname(ChDictionary.class.getResourceAsStream(
				"/dict/surname"));
		this.locationSuffix = Common.readFile2Set(ChDictionary.class.getResourceAsStream("/dict/location_suffix"));
		// load gender number dictionary
		// loadDemonymLists("dict/demonyms.txt");
		// loadStateAbbreviation("dict/state-abbreviations.txt");
		// adjectiveNation.addAll(demonymSet);
		// adjectiveNation.removeAll(demonyms.keySet());

		animateHead = Common.readFile2Map(ChDictionary.class.getResourceAsStream(
				"/dict/chinese_animate"));
		inanimateHead = Common.readFile2Map(ChDictionary.class.getResourceAsStream(
				"/dict/chinese_inanimate"));
		maleHead = Common.readFile2Map(ChDictionary.class.getResourceAsStream(
				"/dict/chinese_male"));
		femaleHead = Common.readFile2Map(ChDictionary.class.getResourceAsStream(
				"/dict/chinese_female"));
		singleHead = Common.readFile2Map(ChDictionary.class.getResourceAsStream(
				"/dict/chinese_single"));
		pluraHead = Common.readFile2Map(ChDictionary.class.getResourceAsStream(
				"/dict/chinese_plura"));
		countries = Common.readFile2Set(ChDictionary.class.getResourceAsStream(
				"/dict/country2"));
	}

	public HashSet<String> countries;

	public HashMap<String, Integer> animateHead = new HashMap<String, Integer>();
	public HashMap<String, Integer> inanimateHead = new HashMap<String, Integer>();

	public HashMap<String, Integer> singleHead = new HashMap<String, Integer>();
	public HashMap<String, Integer> pluraHead = new HashMap<String, Integer>();

	public HashMap<String, Integer> maleHead = new HashMap<String, Integer>();
	public HashMap<String, Integer> femaleHead = new HashMap<String, Integer>();

	public HashSet<String> locationSuffix;

	// public final Map<String, Set<String>> demonyms = new HashMap<String,
	// Set<String>>();
	// public final Set<String> demonymSet = new HashSet<String>();
	// public final Set<String> adjectiveNation = new HashSet<String>();
	//
	// private void loadDemonymLists(String demonymFile) {
	// ArrayList<String> lines = Common.getLines(demonymFile);
	// for (String oneline : lines) {
	// String[] line = oneline.split("\t");
	// if (line[0].startsWith("#"))
	// continue;
	// Set<String> set = new HashSet<String>();
	// for (String s : line) {
	// set.add(s.toLowerCase());
	// demonymSet.add(s.toLowerCase());
	// }
	// demonyms.put(line[0].toLowerCase(), set);
	// adjectiveNation.addAll(demonymSet);
	// adjectiveNation.removeAll(demonyms.keySet());
	// }
	// }

	public void loadStateAbbreviation(String statesFile) {
		ArrayList<String> lines = Common.getLines(statesFile);
		for (String line : lines) {
			String[] tokens = line.split("\t");
			statesAbbreviation.put(tokens[1], tokens[0]);
			statesAbbreviation.put(tokens[2], tokens[0]);
		}
	}

	public final Map<String, String> statesAbbreviation = new HashMap<String, String>();

	public final Set<String> pluralModify = new HashSet<String>(Arrays.asList(
			"几", "多", "不少", "很多", "一些", "有些", "部分", "多数", "少数", "更多", "更少",
			"所有", "５", "大量"));

	public final Set<String> singleModify = new HashSet<String>(
			Arrays.asList("一"));

	public final Set<String> nonModify = new HashSet<String>(Arrays.asList(""));

	public final Set<String> temporals = new HashSet<String>(Arrays.asList("秒",
			"分钟", "小时", "天", "日", "周", "月", "年", "世纪", "毫秒", "周一", "周二", "周三",
			"周四", "周五", "周六", "周日", "今天", "现在", "昨天", "明年", "岁", "时间", "早晨",
			"早上", "晚上", "白天", "下午", "中午", "学期", "冬天", "春天", "一月", "二月", "三月",
			"四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月", "时候"));

	public final Set<String> titleWords = new HashSet<String>(Arrays.asList(
			"总统", "总理", "顾问", "部长", "市长", "省长", "先生", "外长", "教授", "副总理", "副总统",
			"大使", "同志", "王妃", "国王", "主席", "王后", "王子", "首相", "经理", "秘书", "女士",
			"总经理"));

	public final Set<String> notResolve = new HashSet<String>(Arrays.asList(
			"这", "那"));
	// 新 旧 前 原
	public final Set<String> newOld = new HashSet<String>(Arrays.asList("新",
			"旧", "前", "原"));

	public final Set<String> personPronouns = new HashSet<String>();
	public final Set<String> allPronouns = new HashSet<String>();
	public final Set<String> parts = new HashSet<String>(Arrays.asList("不少",
			"很多", "一些", "有些", "部分", "多数", "少数", "更多", "更少", "所有", "一个",
			"hundred", "thousand", "million", "billion", "tens", "dozens",
			"hundreds", "thousands", "millions", "billions", "group", "groups",
			"bunch", "number", "numbers", "pinch", "amount", "amount", "total",
			"all", "mile", "miles", "pounds"));
	public final Set<String> nonWords = new HashSet<String>(Arrays.asList("mm",
			"hmm", "ahem", "um", "ha", "er"));

	public final HashMap<ArrayList<String>, int[]> bigGenderNumber = new HashMap<ArrayList<String>, int[]>();

	public final HashSet<String> surnames;

	public final Set<String> femalePronouns = new HashSet<String>(
			Arrays.asList(new String[] { "她", "她们", "herself", "she" }));
	public final Set<String> malePronouns = new HashSet<String>(
			Arrays.asList(new String[] { "他", "他们" }));
	public final Set<String> neutralPronouns = new HashSet<String>(
			Arrays.asList(new String[] { "它" }));
	public final Set<String> possessivePronouns = new HashSet<String>(
			Arrays.asList(new String[] { "my", "your", "his", "her", "its",
					"our", "their", "whose" }));

	public final Set<String> pluralWords = new HashSet<String>(
			Arrays.asList(new String[] { "二", "三", "四", "五", "六", "七", "八",
					"九", "十", "百", "千", "万", "亿", "些", "多", "2", "3", "4", "5",
					"6", "7", "8", "9", "0", "所有" }));

	public final Set<String> numberWords = new HashSet<String>(
			Arrays.asList(new String[] { "二", "三", "四", "五", "六", "七", "八",
					"九", "十", "百", "千", "万", "亿", "2", "3", "4", "5", "6", "7",
					"8", "9", "0" }));

	public final Set<String> singleWords = new HashSet<String>(
			Arrays.asList(new String[] { "1", "一" }));

	public final Set<String> removeChars = new HashSet<String>(
			Arrays.asList(new String[] { "什么的", "哪", "什么", "谁", "啥", "哪儿",
					"哪里", "人们", "年", "原因", "啥时", "nothing", "one", "other",
					"plenty", "somebody", "someone", "something", "both",
					"few", "fewer", "many", "others", "several", "all", "any",
					"more", "most", "none", "some", "such" }));

	public final Set<String> removeWords = new HashSet<String>(
			Arrays.asList(new String[] { "_", "ｑｕｏｔ", "人", "时候", "问题", "情况",
					"未来", "战争", "可能" }));

	public final Set<String> singularPronouns = new HashSet<String>(
			Arrays.asList(new String[] { "他", "它", "哪个", "谁", "这", "那", "其",
					"其他", "其它", "那里", "那位", "你", "您", "我", "本身", "俺", "自己",
					"本人", "她", "此", "这个", "那个", "oneself", "one's" }));
	public final Set<String> pluralPronouns = new HashSet<String>(
			Arrays.asList(new String[] { "你们", "我们", "大家", "咱们", "您们", "那些",
					"这些", "它们", "她们", "他们", "双方", "themselves", "theirs",
					"their" }));
	public final Set<String> otherPronouns = new HashSet<String>(
			Arrays.asList(new String[] { "谁", "什么", "哪个", "双方", "when", "which" }));
	public final Set<String> thirdPersonPronouns = new HashSet<String>(
			Arrays.asList(new String[] { "他", "她", "它", "他们", "她们", "它们", "这",
					"那", "这些", "那些", "其", "其他", "其它", "那里", "那位", "they",
					"them", "themself", "themselves", "theirs", "their",
					"they", "them", "'em", "themselves" }));
	public final Set<String> secondPersonPronouns = new HashSet<String>(
			Arrays.asList(new String[] { "你", "你们", "您", "您们", "yourselves" }));

	public final Set<String> timePronoun = new HashSet<String>(
			Arrays.asList(new String[] { "当时", "那时", "此时" }));

	public final Set<String> firstPersonPronouns = new HashSet<String>(
			Arrays.asList(new String[] { "我", "我们", "俺", "大家", "本人", "咱们",
					"ourselves", "ours", "our" }));
//	public final Set<String> moneyPercentNumberPronouns = new HashSet<String>(
//			Arrays.asList(new String[] { "it", "its" }));
//	public final Set<String> dateTimePronouns = new HashSet<String>(
//			Arrays.asList(new String[] { "when" }));
//	public final Set<String> organizationPronouns = new HashSet<String>(
//			Arrays.asList(new String[] { "it", "its", "they", "their", "them",
//					"which" }));
//	public final Set<String> locationPronouns = new HashSet<String>(
//			Arrays.asList(new String[] { "it", "its", "where", "here", "there" }));
	public final Set<String> inanimatePronouns = new HashSet<String>(
			Arrays.asList(new String[] { "它", "它们", "这", "那", "这些", "那些", "什么",
					"哪个", "其它", "那里" }));
	public final Set<String> unknownAnimatePronouns = new HashSet<String>(
			Arrays.asList(new String[] { "一些", "许多", "其", "themselves",
					"theirs", "their", "they", "them", "themselves", }));
	public final Set<String> animatePronouns = new HashSet<String>(
			Arrays.asList(new String[] { "他", "你", "你们", "我", "她", "她们", "他们",
					"我们", "你们", "她", "您", "谁", "其他", "本身", "俺", "自己", "大家",
					"那位", "双方", "本人", "咱们", "her", "herself", "hers", "her",
					"one", "oneself", "one's", "they", "them", "themself",
					"themselves", "theirs", "their", "they", "them", "'em",
					"themselves", "who", "whom", "whose" }));

	public final Set<String> relativePronouns = new HashSet<String>(
			Arrays.asList(new String[] { "that", "who", "which", "whom",
					"where", "whose" }));

	public final Set<String> reflexivePronouns = new HashSet<String>(
			Arrays.asList(new String[] { "自己", "本身", "yourselves", "himself",
					"herself", "itself", "ourselves", "themselves", "oneself" }));

	public final Set<String> reportVerb = new HashSet<String>(Arrays.asList(
			"表示", "讲起", "说话", "说", "表示", "讲起", "说话", "说", "指出", "介绍", "认为",
			"密布", "觉得", "汇给", "低吟", "想", "介绍", "以为", "惊叫", "回忆", "宣告", "报道",
			"透露", "谈", "感慨", "反映", "宣布", "指"));

}
