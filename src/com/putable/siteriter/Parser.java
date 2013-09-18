package com.putable.siteriter;

import java.awt.List;
import java.util.HashMap;

public class Parser {
	// public HashMap<String, List> getMap(HashMap<String, List> hm){
	// return hm;
	// }
	private String output = "";

	public String parseMain(List keySet, HashMap<String, List> hm) {
		// gona have to read through the keys up till = and stuff to determine
		// where stuff
		// needs to go then
		// makes calls depending on what the keys are
		// list.get(i) iterates through list
		String[] keys = keySet.getItems();
		// for (int i = 0; i < keySet.countItems(); i++) {
		// for(int i = 0; i<1;i++){
		// System.out.println("got to here");
		if (hm.containsKey(keys[0])) {
			System.out.println("and here");
			// change the token for strings to they store in hm as
			// '/dksl;af'/ so
			// that you can see if the information for that rule (i.e. where
			// we are in
			// the hashmap) is a name or a literal.
			for (int j = 0; j < hm.get(keys[0]).getItemCount(); j++) {
				System.out.println("and here");
				String storeLitEle = hm.get(keys[0]).getItem(j);
				// List storeAgain = hm.get(keys[0]);
				// 17th i changed the getItem in the following statement to be
				// |j| not 1
				System.out.println("STORELITELE IS :   "
						+ hm.get(keys[0]).getItem(j) + "J is:" + j);
				// List storeLit.add(hm.get(keys[0]).getItem(j)
				// make char firstChar = storeLit.get(0).charAt(0);
				char firstChar = storeLitEle.charAt(0);
				if (firstChar == '\'' || firstChar == '"') {

					output = output + storeLitEle;
					System.out.println(output + "jdksla;jfkd");
					System.out.println("!!!!!!!!!!!!!!!");
					// return parseMain()

				} else {
					System.out.println(output + ">?????");
					// return parseName(storeLit,hm);
					System.out.println("&&&&&&&&&&&&&&&");
					System.out.println("storeLit is :" + storeLitEle);

					if (storeLitEle.charAt(storeLitEle.length() - 1) == ';') {
						storeLitEle = storeLitEle.substring(0,
								storeLitEle.length() - 1);
					}
					// since the list is a string it doesn't have a match in the
					// map
					// because the key its NameOfRule2NameOfRule3
					parseName(storeLitEle, hm);
				}

			}

		}

		// }
		System.out.println("THE OUTPUT WILL BE:" + output);
		return output;

	}

	public String parseName(String name, HashMap<String, List> hm) {
	    System.out.println("gets into the method at least");
	    name = name+" ";
		if (hm.containsKey(name)) {
			System.out.println("beginning of parseName");
			List nameValue;
			nameValue = hm.get(name);
			// !!!!!!!!!!!!!i'm pretty sure this is where the error is
			// occuring!!!!!!!!!!!
			String[] hold = nameValue.getItems();
			for (int i = 0; i < hold.length; i++) {
				// Why is it picking up NameOfRule three in the same loop as
				// Rule2 gets expanded
				// output = output+hold[i];
				if (hm.containsKey(hold[i])) {
					parseName(hold[i], hm);
				} else {
					output += hold[i];
				}
				System.out.println(output + "int parseName's loop");
			}
		}
		System.out.println(output + "right before the return");
		// Check to make sure isCharacter and if so it gets sent here
		return output;
	}

	public String parseLiteral() {
		return null;
	}

}
