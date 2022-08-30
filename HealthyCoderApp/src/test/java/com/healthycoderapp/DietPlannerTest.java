package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class DietPlannerTest {
	
	private DietPlanner dietPlanner;
	
	@BeforeEach
	void setup() {
		this.dietPlanner = new DietPlanner(20, 30, 50);
	}
	
	@AfterEach
	void afterEach() {
		
	}

	//@Test
	@RepeatedTest(value =10, name=RepeatedTest.SHORT_DISPLAY_NAME)
	void Should_RetuenCorrectDietPlan_When_CorrectCoder() {
		//given
		Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
		DietPlan expected = new DietPlan(2202, 110, 73, 275);
		
		//when
		DietPlan calculatedDietPlan = dietPlanner.calculateDiet(coder);
		
		//then
		assertAll (
				() -> assertEquals(expected.getCalories(),calculatedDietPlan.getCalories()),
				() -> assertEquals(expected.getProtein(),calculatedDietPlan.getProtein()),
				() -> assertEquals(expected.getFat(),calculatedDietPlan.getFat()),
				() -> assertEquals(expected.getCarbohydrate(),calculatedDietPlan.getCarbohydrate())
			);
	}

}
