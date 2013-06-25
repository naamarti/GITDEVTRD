package com.totalbanksolutions.framework.file;

import java.io.RandomAccessFile;

import org.apache.log4j.Logger;
 
public class ReverseFileReader 
{ 

	private static Logger log = Logger.getLogger(ReverseFileReader.class);

	private RandomAccessFile randomfile; 
	private long position;
    private long lastCharPosition;
    
	public ReverseFileReader ( String filename ) throws Exception 
	{           
		// Open up a random access file
		this.randomfile = new RandomAccessFile( filename, "r" );
		
		// Set our seek position to the end of the file
		this.position = this.randomfile.length();
		this.lastCharPosition = this.position;
		
		// Seek to the end of the file
		this.randomfile.seek(this.position);

		//Move our pointer to the first valid position at the end of the file.
		String thisLine = this.randomfile.readLine();
		
		while(thisLine == null || thisLine.length() == 0 || thisLine.substring(0,1).equals('\n') || thisLine.substring(0,1).startsWith(" "))
		{
	    	this.position--;
			this.randomfile.seek(this.position);
			thisLine = this.randomfile.readLine();
			this.randomfile.seek(this.position);
	    }	    
	 }        

	 public static void main(String[] args)
	 {
		 try
		 {
			 String fileName = "C:/temp/testfile.txt";
			 ReverseFileReader test = new ReverseFileReader( fileName );			 
			 
			 String lastLine = test.readLine();
			 log.debug("line read is : [" + lastLine + "]");			 
		 }
		 catch( Exception e )
		 {
			log.error("Error caught" + e.getMessage() );
		 }
		 
	 }

	 // Read one line from the current position towards the beginning
	 public String readLine() throws Exception 
	 {            
		 int thisCode;
	     char thisChar;
	     String finalLine = "";
	          
		// If our position is less than zero already, we are at the beginning
		// with nothing to return.
		if ( this.position < 0 ) {
			return null;
		}

		// if the file is terminated with a CRLF ( char code 10 followed by char 
		// code 13 ), then adjust file pointer position to capture last line 
		// appropriately
		if ( hasTerminatingCRLF() )
		{
			this.position -= 2;
		}

		while(true) 
		{
			// we've reached the beginning of the file
			if ( this.position < 0 ) {
				break;
			}

			// Seek to the current position
			this.randomfile.seek( this.position );
	           
			// Read the data at this position
			thisCode = this.randomfile.readByte();
			thisChar = (char)thisCode;

			
			// If this is a line break or carrige return, stop looking
			if ( thisCode == 13 || thisCode == 10 ) 
			{
				// See if the previous character is also a line break character.
				// this accounts for crlf combinations
				this.randomfile.seek( this.position-1 );
				int nextCode = this.randomfile.readByte();
				if ( (thisCode == 10 && nextCode == 13) || (thisCode == 13 && nextCode == 10) ) 
				{
					// If we found another linebreak character, ignore it
					this.position = this.position-1;
				}
	                           
				// Move the pointer for the next readline
				this.position--;
				break;
	                  
			} 
			else 
			{
				// This is a valid character append to the string  
				finalLine=thisChar + finalLine;
			}
	                  
			// Move to the next char
			this.position--;
	          
		}
	          
		// return the line
		return finalLine;
	 }        


	 public long getLastCharPosition()
	 {
		 return this.lastCharPosition;
	 }

	 public void closeFile() throws Exception
	 {
		 this.randomfile.close();
	 }
	 
	 private boolean hasTerminatingCRLF() throws Exception
	 {
		 boolean terminatesWithCRLF = false;
		
		 if(this.position >= 2)
		 {
			 
			 // Seek to the current position, and read the last two bytes of the file
			 // Read the data at this position
			 this.randomfile.seek( this.position );
			 int thisCode = this.randomfile.readByte();
			
//			 char thisChar = (char)thisCode;
			 this.randomfile.seek( this.position - 1 );
			 int nextCode = this.randomfile.readByte();
//			 char nextChar = (char)thisCode;
		
			 if ( thisCode == 10 && nextCode == 13 )
			 {
				 terminatesWithCRLF = true;
			 }
		 }
		 
		 return terminatesWithCRLF;
		
	 }
}
