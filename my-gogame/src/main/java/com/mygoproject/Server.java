package com.mygoproject;


public class Server 
{
	public Server(int size) {
		Manager manager = new Manager(size);
		createSocket();
	}
	
	public void createSocket() {
		
	}
	
    public static void main( String[] args )
    {
    	int size  = 0;
    	try {
    		size = Integer.parseInt(args[0]);
    	} catch(NumberFormatException e) {
    		System.out.println("Wrong argument");
    	}
    	Server server = new Server(size);
    }
}
