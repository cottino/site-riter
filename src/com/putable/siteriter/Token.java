package com.putable.siteriter;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

//Foo = \'myname is chris \' Bar
//Bar = "chris"
public enum Token {
	DLITERAL {
		@Override
		public String handle(Reader reader) {
			String store = "";
			try {
				int data = 0;
				while (data != -1) {
					data = reader.read();

					if (data == '\"') {
						break;
					}
					if (data == '|') {
						break;
					}
					if (data == ';') {
						break;
					} else {
						store += (char) data;
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return store;
		}
	},
	SLITERAL {
		@Override
		public String handle(Reader reader){
			//System.out.println("here in handle");
			String store = "";
			try{
				int data = reader.read();
				//store = store + (char)data;
				
				while(data != '\'' && data !='\"'){
					store = store+(char)data;
					data = reader.read();
					//System.out.println(store+ "store in the loop");
				}
				store = store + (char)data;
//				int data = reader.read();
//				store = store+(char)data;
//				//System.out.println((char)data+"data in handle");
//				data = reader.read();
//				//store = store +(char)data;
				System.out.println(store + "store in handle ===");
//				while(data != '\''){
//				//	System.out.println((char)data+"lower in handle");
//					store = store+(char)data;
//				//	System.out.println(store+"store in handle!!!");
//					data = reader.read();
//				}
//				store = store+(char)data;
//			//System.out.println(store+"        sajfkdlsajfkdlsajfkds");
			}catch(IOException e){
				e.printStackTrace();
			}
			
			return store;
		}
//		public String handle(Reader reader) {
//			String store = "";
//			try {
//				int data = reader.read();
//				store += (char)data;
//				while (data != -1) {
//					data = reader.read();
//					
//					//EXPERIMENTAL TEST
//				    
//				    if (data == '\'') {
//                      store += (char)data;
//					break;
//					}
////					if (data == '|') {
////						break;
////					}
//				//	if (data == ';') {
////						System.out.print("this happened");
//					//	break;
//					//} 
//				    //{
//						store += (char) data;
//						// data = reader.read();
//				//	}
//					
//				}
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return store;
//		}
	},
	NAME {
		@Override
		public String handle(Reader reader){
			String store = "";
			try{
				int data = reader.read();
				while(data != '=' && data != ' '){
					char theChar =(char)data;
					store = store+theChar;
					data = reader.read();
				}
				while(data != '=')
				{
					data = reader.read();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			return store;
		}
//		public String handle(Reader reader) {
//			String store = "";
//			try {
//				int data = reader.read();
//				//*****was while data != -1_
//				while (data != ' ' && data != -1) {
//					//System.out.println("Executions in while on token");
//					char theChar = (char) data;
//					//in my run with just one rule this never gets used 
//					if (theChar == ' ' && reader.read() == '=') {
//						System.out.println("This happened");
//						//return store;
//						break;
//					}
//				//	if(theChar == ' ' && Character.isLetter(reader.read())){
//					//	break;
//					//}
//					if (theChar == ';') {
//						//probably a bad idea to do this 
//						store+= theChar;
//						return store;
//						//break;
//					} else {
//						store += theChar;
//						// System.out.println(theChar);
//						data = reader.read();
//					}
//					// reader.close();
//				}
//				// not sure if this line is a good idea;
//				// reader.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			System.out.println("STore is :::::"+store);
//
//			return store;
//		}
	},
	SOFTNAME {
		@Override
		public String handle(Reader reader){
			String store = "";
			
			try{
				int data = reader.read();
				while(data != ' ' && data !=';'){
					char theChar =(char)data;
					store = store+theChar;
					data = reader.read();
					System.out.println("SoftName");
				}
				//added 17th 
				if(data==';'){
					store = store+';';
				}
				
			}catch(IOException e){
				e.printStackTrace();
			}
			//added 17th 
			
			return store;
		}
	},
	BAR {
		@Override
		public String handle(Reader reader) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	public abstract String handle(Reader reader);

};
