package com.putable.siteriter;

import java.util.HashMap;
import java.util.List;

public class ParseTree {
   RuleNode firstNode;
   HashMap<String, List> hm = new HashMap<String, List>();
   
   public ParseTree(RuleNode firstNode, HashMap<String, List> hm){
	   this.firstNode = firstNode; 
	   this.hm = hm; 
   }
   
   //public void setNext(RuleNode firstNode, RuleNode nextNode){
   //
   //}
}
