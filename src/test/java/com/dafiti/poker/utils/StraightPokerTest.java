/**
 * 
 */
package com.dafiti.poker.utils;

import java.util.Arrays;
import java.util.List;

/**
 * The purpose of this class is test straigth poker validation
 * @author vin001
 *
 */
public class StraightPokerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> cards = Arrays.asList(14, 10, 9, 11, 13, 12, 2);
		StraightPoker straightPoker = new StraightPoker();
		System.out.println(cards.toString() + " is a valid straight poker line: " + straightPoker.isStraightPoker(cards));
	}

}
