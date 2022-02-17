package mathOperation;

import java.util.concurrent.Callable;
import logPackage.*;

public class Action implements Callable<Integer>
{
	private Logger logObject = Logger.createLoggerInstance();
	private int number1;
	private int number2;
	private int iActionType;
	
	public Action(int iArg1, int iArg2, int iAction)
	{
		number1 = iArg1;
		number2 = iArg2;
		iActionType = iAction;
	}
	
	@Override
    public Integer call()
    {
		switch(iActionType)
		{
			case 0:
				return add(number1, number2);
			case 1:
				return subtract(number1, number2);
			case 2:
				return divide(number1, number2);
			case 3:
				return multiply(number1, number2);
			default:
				return -1;
		}
    }
	
	private int add(int num1, int num2)
	{
		String ClassName = this.getClass().getSimpleName();
		String MethodName = new Object(){}.getClass().getEnclosingMethod().getName();
		logObject.traceLog(Logger.TRACEENTER, ClassName, MethodName);
		logObject.traceLog(Logger.TRACEINFO, MethodName, "Result", (num1 + num2));
		logObject.traceLog(Logger.TRACELEAVE, ClassName, MethodName);
		return (num1 + num2);
	}
	
	private int subtract(int num1, int num2)
	{
		String ClassName = this.getClass().getSimpleName();
		String MethodName = new Object(){}.getClass().getEnclosingMethod().getName();
		logObject.traceLog(Logger.TRACEENTER, ClassName, MethodName);
		logObject.traceLog(Logger.TRACEINFO, MethodName, "Result", (num1 - num2));
		logObject.traceLog(Logger.TRACELEAVE, ClassName, MethodName);
		return (num1 - num2);
	}
	
	private int divide(int num1, int num2)
	{
		String ClassName = this.getClass().getSimpleName();
		String MethodName = new Object(){}.getClass().getEnclosingMethod().getName();
		logObject.traceLog(Logger.TRACEENTER, ClassName, MethodName);
		if(num2 != 0)
		{
			logObject.traceLog(Logger.TRACEINFO, MethodName, "Result", num1 / num2 );
			logObject.traceLog(Logger.TRACELEAVE, ClassName, MethodName);
			return (num1 / num2);
		}
		else
		{
			logObject.traceLog(Logger.TRACEERROR, MethodName, "Divided by ZERO error, num2 (Denominator)", num2);
			logObject.traceLog(Logger.TRACELEAVE, ClassName, MethodName);
			return 0;
		}
	}
	
	private int multiply(int num1, int num2)
	{
		String ClassName = this.getClass().getSimpleName();
		String MethodName = new Object(){}.getClass().getEnclosingMethod().getName();
		logObject.traceLog(Logger.TRACEENTER, ClassName, MethodName);
		logObject.traceLog(Logger.TRACEINFO, MethodName, "Result", (num1 * num2));
		logObject.traceLog(Logger.TRACELEAVE, ClassName, MethodName);
		return (num1 * num2);
	}
}