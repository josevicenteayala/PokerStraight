package com.dafiti.poker.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class to handle straight poker validation
 * @author vin001
 *
 */
public class StraightPoker {

	public static final String INVALID_CARD_LIST_ONLY_5_7_ALLOWED = "Invalid card list, only size beteween 5-7 allowed";
	
	
	/**
	 * This is a wrapper method to check if a list of cards is a valid straight in poker game. 
	 * It handles StraightPokerException when validations fails. Is useful if caller does not handle Exceptions.
	 * @param cards to validate
	 * @return boolean, true if the list is valid or false if It does not.
	 */
	public boolean isStraightPoker(List<Integer> cards) {
		boolean isStraight = true;
		try {
			return verifyStraightPoker(cards);
		} catch (StraightPokerException e) {
			//TODO for version2: Log this exception
			isStraight = false;
		}
		return isStraight;
	}
	
	/**
	 * This method check if a list of cards is a valid straight in poker game. It throws StraightPokerException when validations fails.
	 * @param cards to validate
	 * @return boolean, true if the list is valid or false if It does not.
	 * @throws StraightPokerException
	 */
	public boolean verifyStraightPoker(List<Integer> cards) throws StraightPokerException {
		// Validation to control Integer values
		List<Integer> cardListCorrectValues = validateCardsValues(cards);

		// Validation to control card size
		if (cardListCorrectValues.isEmpty() || cardListCorrectValues.size() < PokerRules.NUMBER_OF_CARDS_MIN_ALLOWED
				|| cardListCorrectValues.size() > PokerRules.NUMBER_OF_CARDS_MAX_ALLOWED)
			throw new StraightPokerException(INVALID_CARD_LIST_ONLY_5_7_ALLOWED);

		// Sort the List
		sortList(cardListCorrectValues);
		
		Iterator<Integer> iterator = cardListCorrectValues.iterator();
		boolean isStraight = true;
		Integer previousCard = iterator.next();
		while(iterator.hasNext() && isStraight) {
			Integer nextCard = iterator.next();
			if(previousCard.equals(14)) {
				if(!nextCard.equals(2)) isStraight = false;
			} else if(nextCard.compareTo(previousCard) <= 0) isStraight = false;
			previousCard = nextCard;
		}
		
		return isStraight;
	}

	/**
	 * Sort a list with specific straight poker rules
	 * @param cardListCorrectValues
	 */
	public static void sortList(List<Integer> cardListCorrectValues) {
		if(cardListCorrectValues.contains(2)) {
			Collections.sort(cardListCorrectValues, pokerComparator());
		}else {
			Collections.sort(cardListCorrectValues);
		}
	}

	/** Apply size rules for card list. The valid size is between 5 and 7 cards.
	 * @param cards
	 * @return
	 * @throws StraightPokerException
	 */
	public static List<Integer> validateCardsValues(List<Integer> cards) throws StraightPokerException {
		if (Objects.isNull(cards) || cards.isEmpty())
			throw new StraightPokerException(INVALID_CARD_LIST_ONLY_5_7_ALLOWED);
		return cards.stream().filter(number -> number <= 14 && number >= 2).collect(Collectors.toList());
	}

	/**
	 * Compares its two integers for order. Returns a negative integer, zero, or a
	 * positive integer as numberToCompare is less than, equal to, or greater than
	 * numberRefernce, but there is a special rule when number 14 is present, in those cases
	 * Its behaviour is as number one does.
	 * <p>
	 * 
	 * @return comparator
	 */
	private static final Comparator<? super Integer> pokerComparator() {
		Comparator<? super Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer numberToCompare, Integer numberReference) {
				if (numberReference == 14) {
					return numberToCompare.compareTo(1);
				}
				
				if (numberToCompare == 14) {
					Integer one = 1;
					return one.compareTo(numberReference);
				}				
				return numberToCompare.compareTo(numberReference);
			}
		};
		return comparator;
	}

}
