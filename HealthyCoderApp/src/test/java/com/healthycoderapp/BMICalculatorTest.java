package com.healthycoderapp;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class BMICalculatorTest {
	
	
	private String enviornment = "prod";
	
	@Nested
	class isDietPlanRecommented {
		
		@Test
		void should_ReturnTrue_when_DietRecommended() {
			//Given
			double weight = 89.0;
			double height = 1.72;
			
			//When
			boolean recommended = BMICalculator.isDietRecommended(weight, height);
			
			//Then
			assertTrue(recommended);

		}
		
		@ParameterizedTest
		@ValueSource(doubles = {89.0, 95.0, 110.0})
		void should_ReturnTrue_when_DietRecommendedParameterized(double coderWeight) {
			//Given
			double weight = coderWeight;
			double height = 1.72;
			
			//When
			boolean recommended = BMICalculator.isDietRecommended(weight, height);
			
			//Then
			assertTrue(recommended);

		}
		
		@ParameterizedTest(name = "weight= {0}, height= {1}")
		//@ValueSource(doubles = {89.0, 95.0, 110.0})
		@CsvSource(value = {
				"89.0,1.72", 
				"95.0,1.75" , 
				"110.0, 1.78"})
		void should_ReturnTrue_when_DietRecommendedParameterizedMultiple(double coderWeight, double coderHeight) {
			//Given
			double weight = coderWeight;
			double height = coderHeight;
			
			//When
			boolean recommended = BMICalculator.isDietRecommended(weight, height);
			
			//Then
			assertTrue(recommended);

		}
		
		@ParameterizedTest(name = "weight= {0}, height= {1}")
		//@ValueSource(doubles = {89.0, 95.0, 110.0})
//		@CsvSource(value = {
//				"89.0,1.72", 
//				"95.0,1.75" , 
//				"110.0, 1.78"})
		@CsvFileSource(resources="/diet-recommended-input-data.csv", numLinesToSkip = 1)
		void should_ReturnTrue_when_DietRecommendedParameterizedFiles(double coderWeight, double coderHeight) {
			//Given
			double weight = coderWeight;
			double height = coderHeight;
			
			//When
			boolean recommended = BMICalculator.isDietRecommended(weight, height);
			
			//Then
			assertTrue(recommended);

		}
		
		@Test
		void should_FalseTrue_when_DietNotRecommended() {
			//Given
			double weight = 50.0;
			double height = 1.92;
			
			//When
			boolean recommended = BMICalculator.isDietRecommended(weight, height);
			
			//Then
			assertFalse(recommended);

		}
		
		@Test
		void should_Throw_ArithematicExceptionWhenHeightZero() {
			//Given
			double weight = 50.0;
			double height = 0.0;
			
			//When
			Executable executable = ()-> BMICalculator.isDietRecommended(weight, height);
			
			//Then
			assertThrows(ArithmeticException.class, executable);

		}
		
	}

	@Nested
    class FindCoderWithWorstBMI {
		@Test
		void should_ReturnCodewithWorstBMI_CodeListisNotEmpty() {
			//given
			List<Coder> coders = new ArrayList<>();
			coders.add(new Coder(1.80, 60.0));
			coders.add(new Coder(1.82, 98.0));
			coders.add(new Coder(1.82, 64.7));
			
			
			//when
			Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
			
			//then
			assertAll (
				() -> assertEquals(1.82, coderWithWorstBMI.getHeight()),
				() -> assertEquals(98.0, coderWithWorstBMI.getWeight())
			);
		}
		
		@Test
		void should_ReturnCodewithWorstBMIIn1Ms_When_CoderlistHas10000Elements() {
			//given
			List<Coder> coders = new ArrayList<>();
			for(int i =0; i < 10000; i++) {
				coders.add(new Coder(1.0+i,10.0+i));
			}
			
			//when
			Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
			
			//then
			
			assertTimeout(Duration.ofMillis(500), executable);
		}
		
		@Test
		void should_ReturnNullwithWorstBMI_CodeListisEmpty() {
			//given
			List<Coder> coders = new ArrayList<>();
			
			
			
			//when
			Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
			
			//then
			assertNull(coderWithWorstBMI);
		}
		
    }
	
	@Nested
	class GetBMIScores{
		@Test
		void should_ReturnArrayGetBMIScoresWhenCodeListIsNotEmpty() {
			//given
			
			assumeTrue(BMICalculatorTest.this.enviornment.equals("prod"));
			List<Coder> coders = new ArrayList<>();
			coders.add(new Coder(1.80, 60.0));
			coders.add(new Coder(1.82, 98.0));
			coders.add(new Coder(1.82, 64.7));
			double[] expected = {18.52,29.59,19.53};
			
			
			//when
			double[] bmiScore = BMICalculator.getBMIScores(coders);
			
			
			
			//then
			assertArrayEquals(expected, bmiScore);
		}

	}
	
	
}
