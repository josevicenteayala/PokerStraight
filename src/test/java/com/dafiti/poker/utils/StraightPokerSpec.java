package com.dafiti.poker.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dafiti.poker.utils.StraightPoker;
import com.dafiti.poker.utils.StraightPokerException;

public class StraightPokerSpec {

	StraightPoker straightPoker;

	@Before
	public void init() {
		straightPoker = new StraightPoker();
	}

	@Test
	public void testVerifyCorrectCardValuesForCardsNull() {
		List<Integer> cards = null;
		try {
			StraightPoker.validateCardsValues(cards);
		} catch (StraightPokerException e) {
			assertTrue(e.getMessage().contains(StraightPoker.INVALID_CARD_LIST_ONLY_5_7_ALLOWED));
		}
	}

	@Test
	public void testVerifyCorrectCardValuesForCardsEmpty() {
		List<Integer> cards = new ArrayList<Integer>();
		try {
			StraightPoker.validateCardsValues(cards);
		} catch (StraightPokerException e) {
			assertTrue(e.getMessage().contains(StraightPoker.INVALID_CARD_LIST_ONLY_5_7_ALLOWED));
		}
	}

	@Test
	public void testVerifyCorrectCardValuesForAllPossibles() {
		List<Integer> cards = Arrays.asList(14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
		try {
			List<Integer> validCards = StraightPoker.validateCardsValues(cards);
			assertTrue(validCards.equals(cards));
		} catch (StraightPokerException e) {
			assertFalse(e.getMessage().contains(StraightPoker.INVALID_CARD_LIST_ONLY_5_7_ALLOWED));
		}
	}

	@Test
	public void testVerifyFilterIncorrectCardValues() {
		List<Integer> cardsExpected = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
		List<Integer> cardsWithIncorrectValues = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
				16);
		try {
			List<Integer> validCards = StraightPoker.validateCardsValues(cardsWithIncorrectValues);
			assertTrue(validCards.equals(cardsExpected));
		} catch (StraightPokerException e) {
			assertFalse(e.getMessage().contains(StraightPoker.INVALID_CARD_LIST_ONLY_5_7_ALLOWED));
		}
	}

	@Test
	public void testVerifyFilterIncorrectCardValuesInDifferentPositions() {
		List<Integer> cardsExpected = Arrays.asList(2, 4, 6, 10, 13);
		List<Integer> cardsWithIncorrectValues = Arrays.asList(0, 1, 2, 20, 4, 54, 6, 71, 88, 94, 10, 1156, 1267, 13,
				141, 15, 16);
		try {
			List<Integer> validCards = StraightPoker.validateCardsValues(cardsWithIncorrectValues);
			assertTrue(validCards.equals(cardsExpected));
		} catch (StraightPokerException e) {
			assertFalse(e.getMessage().contains(StraightPoker.INVALID_CARD_LIST_ONLY_5_7_ALLOWED));
		}
	}

	@Test
	public void testVerifyFilterNoneCardsValid() {
		List<Integer> cardsWithIncorrectValues = Arrays.asList(0, 1, 23, 20, 42, 54, 65, 71, 88, 94, 105, 1156, 1267,
				132, 141, 15, 16);
		try {
			List<Integer> validCards = StraightPoker.validateCardsValues(cardsWithIncorrectValues);
			assertTrue(validCards.isEmpty());
		} catch (StraightPokerException e) {
			assertFalse(e.getMessage().contains(StraightPoker.INVALID_CARD_LIST_ONLY_5_7_ALLOWED));
		}
	}

	@Test
	public void testVerifyMinCardsInList() {
		List<Integer> cards = Arrays.asList(14, 2, 3, 4);

		try {
			straightPoker.verifyStraightPoker(cards);
		} catch (StraightPokerException e) {
			assertTrue(e.getMessage().contains(StraightPoker.INVALID_CARD_LIST_ONLY_5_7_ALLOWED));
		}
	}

	@Test
	public void testVerifyMaxCardsInList() {
		List<Integer> cards = Arrays.asList(14, 2, 3, 4, 5, 6, 7, 8);
		try {
			straightPoker.verifyStraightPoker(cards);
		} catch (StraightPokerException e) {
			assertTrue(e.getMessage().contains(StraightPoker.INVALID_CARD_LIST_ONLY_5_7_ALLOWED));
		}
	}

	@Test
	public void testVerifyCorrectOrderWithNumber2Present() {
		List<Integer> cards = Arrays.asList(2, 4, 3, 6, 14, 5);
		List<Integer> cardsExpected = Arrays.asList(14, 2, 3, 4, 5, 6);
		StraightPoker.sortList(cards);
		assertTrue(cards.equals(cardsExpected));
	}

	@Test
	public void testVerifyCorrectOrderWithNumber13Present() {
		List<Integer> cards = Arrays.asList(13, 14, 5, 3, 6, 4);
		List<Integer> cardsExpected = Arrays.asList(3, 4, 5, 6, 13, 14);
		StraightPoker.sortList(cards);
		assertTrue(cards.equals(cardsExpected));
	}

	@Test
	public void testVerifyCorrectOrderWithoutNumbersTwoAndThirteen() {
		List<Integer> cards = Arrays.asList(14, 5, 3, 6, 4, 10, 7, 11);
		List<Integer> cardsExpected = Arrays.asList(3, 4, 5, 6, 7, 10, 11, 14);
		StraightPoker.sortList(cards);
		assertTrue(cards.equals(cardsExpected));
	}

	@Test
	public void testVerifyCorrectOrderWithNumbersTwoAndThirteen() {
		List<Integer> cards = Arrays.asList(14, 5, 2, 3, 6, 4, 10, 13, 7, 11);
		List<Integer> cardsExpected = Arrays.asList(14, 2, 3, 4, 5, 6, 7, 10, 11, 13);
		StraightPoker.sortList(cards);
		assertTrue(cards.equals(cardsExpected));
	}

	@Test
	public void testIsStraightCardsFrom2Until6() {
		List<Integer> cards = Arrays.asList(2, 3, 4, 5, 6);
		try {
			assertTrue(straightPoker.verifyStraightPoker(cards));
		} catch (StraightPokerException e) {
			assertFalse(e.getMessage().contains(StraightPoker.INVALID_CARD_LIST_ONLY_5_7_ALLOWED));
		}
	}

	@Test
	public void testIsStraightCardsFrom14Until5() {
		List<Integer> cards = Arrays.asList(14, 5, 4, 2, 3);
		try {
			assertTrue(straightPoker.verifyStraightPoker(cards));
		} catch (StraightPokerException e) {
			assertFalse(e.getMessage().contains(StraightPoker.INVALID_CARD_LIST_ONLY_5_7_ALLOWED));
		}
	}

	@Test
	public void testIsNotStraightCardsWithRepeatedNumbers() {
		List<Integer> cards = Arrays.asList(7, 7, 12, 11, 3, 4, 14);
		try {
			assertFalse(straightPoker.verifyStraightPoker(cards));
		} catch (StraightPokerException e) {
			assertFalse(e.getMessage().contains(StraightPoker.INVALID_CARD_LIST_ONLY_5_7_ALLOWED));
		}
	}
	
	@Test
	public void testIsNotStraightCardsWithOnlyThreeCards() {
		List<Integer> cards = Arrays.asList(7, 3, 2);
		assertFalse(straightPoker.isStraightPoker(cards));
	}	
	
	@Test
	public void testIsStraightCardsFrom2Until6CallingIsStraightPoker() {
		List<Integer> cards = Arrays.asList(2, 3, 4, 5, 6);
		assertTrue(straightPoker.isStraightPoker(cards));
	}

	@Test
	public void testIsStraightCardsFrom14Until5CallingIsStraightPoker() {
		List<Integer> cards = Arrays.asList(14, 5, 4, 2, 3);
		assertTrue(straightPoker.isStraightPoker(cards));
	}

	@Test
	public void testIsNotStraightCardsWithRepeatedNumbersCallingIsStraightPoker() {
		List<Integer> cards = Arrays.asList(7, 7, 12, 11, 3, 4, 14);
		assertFalse(straightPoker.isStraightPoker(cards));
	}	
}
