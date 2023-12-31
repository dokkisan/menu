package menu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import menu.message.ErrorMessage;
import menu.message.OperationMessage;
import menu.model.Coach;
import menu.model.CoachRepository;
import menu.model.DayOfWeek;
import menu.model.Menu;
import menu.model.MenuCategory;
import menu.model.MenuManager;
import menu.view.InputView;
import menu.view.OutputView;

public class MenuController {
	private final InputView inputView;
	private final OutputView outputView;

	public MenuController() {
		this.inputView = new InputView();
		this.outputView = new OutputView();
	}

	public void run() {
		outputView.printMessage(OperationMessage.START_RECOMMEND.getMessage());
		createCoaches();
		getAvoidFoods();
		viewMenuRecommendationResult();
	}

	private void createCoaches() {
		CoachRepository coachRepository = new CoachRepository();
		outputView.printMessage(OperationMessage.INPUT_NAMES.getMessage());
		while (true) {
			try {
				for (String name : inputView.inputNames()) {
					coachRepository.save(new Coach(name));
				}
				return;
			} catch (IllegalArgumentException e) {
				outputView.printMessage(e.getMessage());
			}
		}
	}

	private void getAvoidFoods() {
		CoachRepository coachRepository = new CoachRepository();
		while (true) {
			try {
				List<Coach> coaches = coachRepository.findAll();
				for (Coach coach : coaches) {
					outputView.printMessage(coach.getName() + OperationMessage.INPUT_AVOID_FOODS.getMessage());
					List<String> avoidMenus = validateAvoidFoods(inputView.inputAvoidFoods());
					coach.setAvoidMenus(avoidMenus);
				}
				return;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private List<String> validateAvoidFoods(List<String> avoidMenus) {
		final int MAX_MENU_INPUT_COUNT = 2;

		for (String avoidMenu : avoidMenus) {
			if (!Menu.isExistMenu(avoidMenu)) {
				throw new IllegalArgumentException(ErrorMessage.INVALID_MENU.getMessage());
			}
		}
		if (avoidMenus.size() > MAX_MENU_INPUT_COUNT) {
			throw new IllegalArgumentException(ErrorMessage.INVALID_MENU_COUNT.getMessage());
		}

		return avoidMenus;
	}

	private void viewMenuRecommendationResult() {
		MenuManager menuManager = new MenuManager();
		outputView.printMessage(OperationMessage.RECOMMEND_RESULT.getMessage());
		outputView.printListWithJoining(createLunchDaysForView());
		outputView.printListWithJoining(createCategoriesForView(menuManager.getRecommendedCategories()));
		menuManager.recommendMenus();
		createRecommendedMenusForView();
		outputView.printMessage(OperationMessage.RECOMMEND_SUCCESS.getMessage());
	}

	private List<String> createCategoriesForView(Map<DayOfWeek, MenuCategory> recommendedCategories) {
		final String CATEGORY_PREFIX = "카테고리";

		List<String> categoryNames = new ArrayList<>();
		categoryNames.add(CATEGORY_PREFIX);
		for (DayOfWeek day : DayOfWeek.values()) {
			categoryNames.add(recommendedCategories.get(day).getName());
		}
		return categoryNames;
	}

	private List<String> createLunchDaysForView() {
		List<String> days = new ArrayList<>();
		days.add("구분");
		for (DayOfWeek day : DayOfWeek.values()) {
			days.add(day.getName());
		}
		return days;
	}

	private void createRecommendedMenusForView() {
		CoachRepository coachRepository = new CoachRepository();
		List<Coach> coaches = coachRepository.findAll();
		for (Coach coach : coaches) {
			List<String> recommendedMenus = new ArrayList<>();
			recommendedMenus.add(coach.getName());
			for (DayOfWeek day : DayOfWeek.values()) {
				recommendedMenus.add(coach.getRecommendedMenus().get(day));
			}
			outputView.printListWithJoining(recommendedMenus);
		}
	}
}
