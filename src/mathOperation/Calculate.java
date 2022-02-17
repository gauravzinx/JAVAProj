package mathOperation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import logPackage.Logger;
import mathOperation.Action;

public class Calculate 
{
	private Logger logObject = Logger.createLoggerInstance();
	public static final int ADD = 0; 
	public static final int SUBTRACT = 1;
	public static final int DIVIDE = 2;
	public static final int MULTIPLY = 3;
	
	public Calculate()
	{
		//System.out.println("CTOR Consumer Class");
	}
	
	private void startLog()
	{
		logObject.setTraceLevel(3);
	}
	
	public int operation(int num1, int num2, int iOperation) throws InterruptedException, ExecutionException
	{
		startLog();
		String ClassName = this.getClass().getSimpleName();
		String MethodName = new Object(){}.getClass().getEnclosingMethod().getName();
		logObject.traceLog(Logger.TRACEENTER, ClassName, MethodName); 
		logObject.traceLog(Logger.TRACEINFO, MethodName,"num1", num1, "num2", num2);
		ExecutorService es = Executors.newCachedThreadPool();
		Future<Integer> fAction = es.submit(new Action(num1, num2, iOperation));
		int iResult = 0;
		switch(iOperation)
		{
			case ADD:
				iResult = fAction.get();
				break;
			case SUBTRACT:
				iResult = fAction.get();
				break;
			case DIVIDE:
				iResult = fAction.get();
				break;
			case MULTIPLY:
				iResult = fAction.get();
				break;	
		}
		logObject.traceLog(Logger.TRACELEAVE, ClassName, MethodName);
		//System.out.println(logObject.getTraceBuffer()); //Print the stream Buffer to post back to DB
		return iResult;
	}
}