import java.util.concurrent.ExecutionException;

import mathOperation.Calculate;

public class ThreadClass /*extends*/ implements Runnable/*Thread*/
{
	private int inputParam1;
	private int inputParam2;
	private String threadName;
	private int operationName;
	private static final int ADD = Calculate.ADD; 
	private static final int SUBTRACT = Calculate.SUBTRACT;
	private static final int DIVIDE = Calculate.DIVIDE;
	private static final int MULTIPLY = Calculate.MULTIPLY;
 
    public ThreadClass(int iArg1,int iArg2,int iOperation, String sThread)
    {
    	//System.out.println("CTOR ThreadClass");
    	
    	inputParam1 = iArg1;
    	inputParam2 = iArg2;
        threadName = sThread;
        operationName = iOperation;
    }
    /*public void recursiveOperation(int num1, int num2, int iOperation) throws InterruptedException, ExecutionException
    {
    	System.out.println("Entering Thread "+ threadName);
    	Calculate calcObj = new Calculate();
    	String strResult = "";
    	Integer result = calcObj.operation(inputParam1, inputParam2, operationName);
    	strResult = result.toString();
    	System.out.println("Exiting Thread " + threadName );
    	System.out.println(threadName + ": Result = " + result);
    	if(!strResult.isEmpty())
    	{
    		recursiveOperation(inputParam1, inputParam2, operationName);
    	}
    }*/
    
    @Override
    public void run()
    {
        try
        {
        	System.out.println("Entering Thread "+ threadName);
        	//recursiveOperation(inputParam1, inputParam2, operationName);
        	Calculate calcObj = new Calculate();
        	int result = calcObj.operation(inputParam1, inputParam2, operationName);
        	System.out.println("Exiting Thread " + threadName );
        	System.out.println(threadName + ": Result = " + result);
    	}
        catch(Exception e)
        {
        	System.out.println("Thread interrupted " + threadName);
        }
    }	
	
	public static void main(String [] arg)
	{
		try
		{
			ThreadClass T1 = new ThreadClass(1, 2, ADD, "ADD");
			Thread t1 = new Thread(T1);
			t1.start();
			
			ThreadClass T2 = new ThreadClass(4, 3, SUBTRACT, "SUBTRACT");
			Thread t2 = new Thread(T2);
			t2.start();
			
			ThreadClass T3 = new ThreadClass(5, 6, MULTIPLY, "MULTIPLY");
			Thread t3 = new Thread(T3);
			t3.start();
			
			ThreadClass T4 = new ThreadClass(56, 0, DIVIDE, "DIVIDE");
			Thread t4 = new Thread(T4);
			t4.start();
			/*ThreadClass T1 = new ThreadClass(1, 2, ADD, "Thread0");
			T1.start();
			ThreadClass T2 = new ThreadClass(4, 3, SUBTRACT, "Thread1");
		    T2.start();
			ThreadClass T3 = new ThreadClass(5, 6, MULTIPLY, "Thread2");
		    T3.start();
			ThreadClass T4 = new ThreadClass(56, 0, DIVIDE, "Thread3");
		    T4.start();*/
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
}