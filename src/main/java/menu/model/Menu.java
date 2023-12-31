package menu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Menu {
	private static final Map<MenuCategory, List<String>> menus = new HashMap<>();

	static {
		menus.put(MenuCategory.JAPANESE,
			new ArrayList<>(List.of("규동", "우동", "미소시루", "스시", "가츠동", "오니기리", "하이라이스", "라멘", "오코노미야끼")));
		menus.put(MenuCategory.KOREAN,
			new ArrayList<>(List.of("김밥", "김치찌개", "쌈밥", "된장찌개", "비빔밥", "칼국수", "불고기", "떡볶이", "제육볶음")));
		menus.put(MenuCategory.CHINESE,
			new ArrayList<>(List.of("깐풍기", "볶음면", "동파육", "짜장면", "짬뽕", "마파두부", "탕수육", "토마토 달걀볶음", "고추잡채")));
		menus.put(MenuCategory.ASIAN,
			new ArrayList<>(List.of("팟타이", "카오 팟", "나시고렝", "파인애플 볶음밥", "쌀국수", "똠얌꿍", "반미", "월남쌈", "분짜")));
		menus.put(MenuCategory.WESTERN,
			new ArrayList<>(List.of("라자냐", "그라탱", "뇨끼", "끼슈", "프렌치 토스트", "바게트", "스파게티", "피자", "파니니")));
	}

	public static boolean isExistMenu(String menu) {
		return menus.values().stream()
			.anyMatch(category -> category.contains(menu));
	}

	public static List<String> getMenuBy(MenuCategory category) {
		return menus.get(category);
	}
}
