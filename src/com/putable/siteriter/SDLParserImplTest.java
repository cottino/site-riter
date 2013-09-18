package com.putable.siteriter;

import static org.junit.Assert.*;

import java.io.CharArrayReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SDLParserImplTest {
	private SDLParserImpl parser;

	@Before
	public void setup() throws Exception {

		parser = new SDLParserImpl();
	}

	@Test
	public void nullLoadCheck() throws IOException {
		try {
			parser.load(null);
		} catch (NullPointerException n) {
			return;
		}
		fail();
	}

	@Test
	public void NullParamMakePage() {
		try {
			parser.makePage(null, null);
		} catch (NullPointerException n) {
			return;
		}
		fail();
	}

	@Test
	//just a test for a constant normal page
	public void ExpectedGrammer() throws IOException {
		String spec = "Foo = \'Hello World\'; Bar = \'test\'";
		Reader reader = new StringReader(spec);
		String key = "abc123";
		Map<String, Integer> hm = new HashMap<String, Integer>();
		parser.load(reader);
		assertEquals(parser.makePage(key, hm), "Hello World");
	}

	@Test
	public void SelectorTest() throws IOException {
		String spec = "Page = \'Good Day World\' | \'My Name is Chris\'";
		Reader reader = new StringReader(spec);
		String key = "abc123";
		Map<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("Page", 0);
		parser.load(reader);
		assertEquals(parser.makePage(key, hm), "Good Day World");
		hm.clear();
		hm.put("Page", 1);
		assertEquals(parser.makePage(key, hm), "My Name is Chris");
	}

	@Test
	public void BigAndSmallNamesTest() throws IOException {
		String spec = "s = \'hi\' bbbbbbbbbbbbbbbbbbbbbbbbbbbb"
				+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb; bbbbbbbbbbbbbbbbbbbbbbbbbbbb"
				+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb = \'there\'";
		Reader reader = new StringReader(spec);
		String key = "abc123";
		Map<String, Integer> hm = new HashMap<String, Integer>();
		parser.load(reader);
		assertEquals(parser.makePage(key, hm), "hi there");
	}

	@Test
	public void WhiteSpaceTest() throws IOException {
		String spec = "first = \'there will be no space after me\' \'I am Chris\' next "
				+ "       next = \'hear me roar\' ";
		Reader reader = new StringReader(spec);
		String key = "abc123";
		Map<String, Integer> hm = new HashMap<String, Integer>();
		parser.load(reader);
		assertEquals(parser.makePage(key, hm),
				"there will be no space after meI am Chrishear me roar");
	}

	@Test
	public void UndefinedTest() throws IOException {
		String spec = "first = \'This is a defined statement followed by an undefined\' undefined "
				+ "Undefined = \'this statement is never used\'";
		Reader reader = new StringReader(spec);
		String key = "abc123";
		Map<String, Integer> hm = new HashMap<String, Integer>();
		parser.load(reader);
		assertEquals(parser.makePage(key, hm),
				"This is a defined statement followed by an undefined undefined?");
	}

	@Test
	public void DoubleSymbolTest() throws IOException {
		String spec = "first = \'This is the first definition not to be used\' "
				+ "first = \'since i am the last i will be used\'";
		Reader reader = new StringReader(spec);
		String key = "abc123";
		Map<String, Integer> hm = new HashMap<String, Integer>();
		parser.load(reader);
		assertEquals(parser.makePage(key, hm),
				"since i am the last i will be used");
	}
    
}
