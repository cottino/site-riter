package com.putable.siteriter;

import java.awt.List;
import java.util.HashMap;

// RuleNode is probably a bad name 
/**
 * A class that tries to recursively expand sequences but it still has a few
 * bugs. there are still alot of comments for my sake in here i don't want to
 * get rid of sorry for the clutter.
 * 
 * @author cottino
 * 
 */
class RuleNode {
	String output = "";
	String finalOut = "";
	HashMap<String, List> hm;
	List keys;

	public RuleNode(List keys, HashMap<String, List> hm) {
		this.keys = keys;
		this.hm = hm;
	}

	// make a method for this that is called by a little make Parse method in
	// the impl
	// that is called in main with mass
	// index will be keys.getItemCount;
	public String parseSend(List keys, HashMap<String, List> hm, int index) {
		if (index == -1) {
			return finalOut;
		} else {
			finalOut = parseMain(index, keys, hm);
		}
		return parseSend(keys, hm, index - 1);
	}

	public String parseMain(int index, List keys, HashMap<String, List> hm) {

		if (index == -1) {
			return output;
		} else if (hm.containsKey(keys.getItem(index))) {
			return parseSend(hm.get(keys.getItem(index)), hm,
					hm.get(keys.getItem(index)).getItemCount() - 1);
		} else {
			output = keys.getItem(index) + " " + output;
		}
        //decrementing the index so that it sort of recursively iterates through 
		return parseMain(index - 1, keys, hm);
	}

}
