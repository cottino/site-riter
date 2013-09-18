package com.putable.siteriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.awt.List;
import java.util.Map;
import java.io.PushbackReader;
import java.lang.Object;
import java.io.FilterReader;

/**
 * A class that is supposed to take in a string and create tokens that are then
 * parsed correctly according to our EBNF grammer. Right now it takes the names
 * and stores them as keys in a hashmap with there associate rules then calls a
 * class im working on still to try and parse it NOTE: grammer has to be loaded
 * into a string in main for right now called RulesFile
 * 
 * @author cottino
 * 
 */
public class SDLParserImpl implements SDLParser {
	public static int count = 0;
	Token tokens;
	public static List keys;

	HashMap<String, List> massStorage = new HashMap<String, List>();

	/**
	 * a peek method for lookahead
	 * 
	 * @param in
	 *            a reader to read ahead
	 * @return the letter that will come next in the string when this is called
	 * @throws IOException
	 */
	static int peek(Reader in) throws IOException {
		in.mark(1);
		int ch = in.read();
		in.reset();
		return ch;
	}

	/**
	 * A method that creates a token for literals It takes in a reader and based
	 * on whether it is a SLITERAL or DLITERAL sends declares it as that enum
	 * and calls its handle method
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public String getLiteral(Reader reader) throws IOException {
		Token literal = Token.SLITERAL;
		String storeLit;
		storeLit = literal.handle(reader);
		// System.out.println(storeLit);

		return storeLit;
	}

	// public String getLiteral(Reader reader) throws IOException {
	// Token literal;
	// String storeLit;
	// int data = reader.read();
	// System.out.println((char)data+"in the getLiteral");
	// char theChar = (char) data;
	//
	// if (peek(reader) == '\'') {
	// literal = Token.SLITERAL;
	// } else if (peek(reader) == '\"') {
	// literal = Token.DLITERAL;
	// } else {
	// return getLiteral(reader);
	// }
	// storeLit = literal.handle(reader);
	//
	//
	// return storeLit;
	// }

	/**
	 * reads a name Token and sends it to its handle method
	 * 
	 * @param reader
	 * @return
	 */
	public String getName(Reader reader) {
		Token nameToken;
		nameToken = Token.NAME;
		String storeName = nameToken.handle(reader);
		return storeName;
	}

	public String getSoftName(Reader reader) {
		Token nameToken;
		nameToken = Token.SOFTNAME;
		String storeName = nameToken.handle(reader);
		return storeName;
	}

	/**
	 * helper method to keep track of the number of semicolons which directly
	 * corresponds with the number or rules
	 * 
	 * @param s
	 * @return
	 */
	public int semiCount(String s) {
		int j = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == ';') {
				j++;
			}
		}
		return j;
	}
	public boolean checkForSemi(String s){
		boolean hasSemi = false;
		for(int i = 0; i<s.length();i++){
			char c = s.charAt(i);
			if(c == ';'){
				hasSemi = true; 
			}
		}
		return hasSemi; 
	}

	/**
	 * Method to return a list with all the keys from the map inside of it
	 * 
	 * @param hm
	 *            the hashmap we are going to be manipulating
	 * @return
	 */
	public List getKeySet(HashMap<String, List> hm) {
		List keys = new List();
		List rightWay = new List();
		String[] temp;
		for (String key : hm.keySet()) {
			// System.out.println( key+"fjdkslajfdksal;f" );
			keys.add(key);

		}
		// a loop to get the key back in order since above loop starts at the
		// bottom.
		temp = keys.getItems();
		for (int i = keys.getItemCount() - 1; i >= 0; i--) {
			rightWay.add(temp[i]);
			// System.out.println(temp[i]);
		}
		return rightWay;
	}
	
	public int skipWhiteSpace(Reader reader) throws IOException
	{
		int data = reader.read();
		if(data == ' ')
		{
			return skipWhiteSpace(reader);
		}else
		{
			return data;
		}
	    
	}

	@Override
	public void load(Reader reader) throws IOException {
        char overflow = 0; 
		for (int i = 0; i < count; i++) {
			System.out.println("overflow is: "+overflow);
			boolean notEnd = true;
			String StoreName = "";
			List StoreList = new List();
			if(Character.isLetter(overflow)){
				StoreName = StoreName+overflow;
			}
			int data = skipWhiteSpace(reader);
			// Still have to do a check for the whitespace
	
		    StoreName = StoreName + (char) data;
			
			if (data != '\'' && data != '"') {
				StoreName = StoreName + getName(reader);
				System.out.println(StoreName + "StoreName in load");
			}
			
			data = skipWhiteSpace(reader);
			while (notEnd) {
				
			
				char theChar = 0;
				System.out.println("how mmany times in the while loop");
				if (data == '"' || data == '\'') {
					String StoreString = "";
					// System.out.println("gets to here");

					StoreString = StoreString + (char) data;
					System.out.println("above the if"+StoreString);
					StoreString = StoreString + getLiteral(reader);
					//at this point it is to late to do check for semi do it earlier in the looping 
					if(checkForSemi(StoreString)==true){
						System.out.println("this actually happened in the if" + StoreString);
						notEnd = false; 
						//load(reader);
					}
					StoreList.add(StoreString);
					System.out.println("StoreString in if is:" + StoreString);
					//StoreList.add(StoreString);
				} else {
					String StoreString = "";
					// System.out.println("gets to here too");
					StoreString = StoreString + (char) data;
					System.out.println("***StoreString is :" + StoreString);
					StoreString = StoreString + getSoftName(reader);
					if(checkForSemi(StoreString)==true){
						System.out.println("This actually happened in the else");
						notEnd = false;
						//load(reader);
					}
					// ABOVE:  make method that takes string and checks for a semi colon 
					//take in this string and all others of the like and if they return back true
					//for having a semicolon then gotcha break out of the loop; 
					// System.out.println(StoreString);
					System.out.println("StoreString in else is:" + StoreString);
					StoreList.add(StoreString);
				}
				data = skipWhiteSpace(reader);
				System.out.println((char) data + "data at the bottom of load");
				if(data == '='||data ==' '){
					data = reader.read();
				}
				if(data ==';'){
					notEnd = false;
				}
				if (data == -1) {
					notEnd = false;
				} 
				if(Character.isLetter((char)data)){
					overflow = (char)data;
				}else{
					overflow = ' ';
				}
				// System.out.println("this is DATA;"+(char)data);

			}
			massStorage.put(StoreName, StoreList);
		}

	}

	// public void load(Reader reader) throws IOException {
	// for (int i = 0; i < count; i++) {
	// // if(peek(reader)!=';'){System.out.println(i);}
	// String StoreName;
	// List StoreList = new List();
	// //Was while(peek(reader)==';' || peek(reader)==' ')
	// while (peek(reader) == ' ') {
	// reader.read();
	// }
	// StoreName = getName(reader);
	//
	// // do something if there is a start symbol later on (not coded yet)
	// StoreList.add(getLiteral(reader));
	// String[] StoreList2 = StoreList.getItems();
	// //System.out.println(StoreList2[0]+ "In the load");
	// // System.out.println(StoreName + "...." + i);
	//
	// while (peek(reader) == ' ') {
	// reader.read();
	// }
	//
	// if (peek(reader) == ';') {
	// massStorage.put(StoreName, StoreList);
	// } else {
	// //**Also experimental just added this while loop to the conditionals
	// //this would be the end of everything not just until the ; in the rule
	// while(peek(reader)!= -1){
	// if (peek(reader) == '\'' || peek(reader) == '"') {
	// String nextInSeq = getLiteral(reader);
	// StoreList.add(nextInSeq);
	// massStorage.put(StoreName, StoreList);
	// System.out.println("Executio in if");
	// } else {
	// String nextInSeq = getName(reader);
	// System.out.println("NEXTINSEQ IS: " + nextInSeq);
	// StoreList.add(nextInSeq);
	// massStorage.put(StoreName, StoreList);
	// System.out.println("Execution in else");
	// }
	// }
	// }
	//
	// }
	//
	// }

	/**
	 * Method that makes an instance of a class RuleNode that handles the
	 * sequence expansion
	 * 
	 * @param key
	 * @param hm
	 * @return
	 */
	// public String startParse(List key, HashMap<String, List> hm) {
	// String output = "";
	// RuleNode mine = new RuleNode(key, hm);
	// int index = key.getItemCount()-1;
	// //the item at the above index taken from the map
	// List thisKey = hm.get(key.getItem(index));
	// //number of items in the list at this key
	// int start = hm.get(key.getItem(index)).getItemCount()-1;
	// output = mine.parseSend(thisKey, hm,start);
	// // call mine.parseSend from right here with the right stuff
	// return output;
	// }

	@Override
	public String makePage(String key, Map<String, Integer> selectors) {

		return null;
	}

	public void sendtoParse(List keys, HashMap<String, List> hm) {
		Parser mine = new Parser();
		mine.parseMain(keys, hm);
	}

	/**
	 * @param args
	 * @throws IOException
	 * 
	 */
	public static void main(String[] args) throws IOException {

		String ruleFile = "NameOfRule =    \'this right here would be regular\' NameOfRule2 NameOfRule3 \'and this would be fantastic\';" +
				          "NameOfRule2 = \'Please,Please,Please work\' NameOfRule3;" +
				          "NameOfRule3 = \'just a test for the recursion\';";
				         // "NameOfRule2 = \'and this right here would be fantastic\';";
		// + "NameOfRule2 = \'second rule\';";
		// + "NameOfRule3 = \'third rule baby\';"
		// + "NameOfRule4 = \'Praise God\';";
		List keySet = new List();
		Reader myReader = new StringReader(ruleFile);
		SDLParserImpl test = new SDLParserImpl();
		count = test.semiCount(ruleFile);
		test.load(myReader);
		//keySet = test.getKeySet(test.massStorage);
		// test.sendtoParse(keySet, test.massStorage);
		// System.out.println(test.startParse(test.getKeySet(test.massStorage),
		// test.massStorage));
		/**
		 * all these comments and stuff below is for testing the hashmap to make
		 * sure it was behaving correctly
		 */
		// the space at end of key is causing a NullPointerExceptino
		
		//SUPER IMPORTANT need to compensate for space after Name of Rule
		 List getSequenceFromName = test.massStorage.get("NameOfRule");
		 List getSequenceFromName2 = test.massStorage.get("NameOfRule2");
	     List getSequenceFromName3 = test.massStorage.get("NameOfRule3");
	     
	// List getfour = test.massStorage.get("NameOfRule4");

		 String[] firstSequence = getSequenceFromName.getItems();
		 String[] secondSequence = getSequenceFromName2.getItems();
		 String[] thirdSequence = getSequenceFromName3.getItems();
		// String[] four = getfour.getItems();

		 System.out.print(firstSequence[0] + " Main  ");
		 System.out.print(firstSequence[1] + " Main  ");
		 System.out.print(firstSequence[2] + "Main ");
		 System.out.println(firstSequence[3] + "Main");
		 System.out.println(secondSequence[0] + " Main  ");
		 System.out.println(secondSequence[1]+ "Main");
		 System.out.println(thirdSequence[0] + " Main  ");
		// System.out.println(four[0] + " Main  ");

	}
}
