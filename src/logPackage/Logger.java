// File: Logger.java
package logPackage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.text.MessageFormat;
import java.util.Stack;

/**
* <br><b> This Logger class is used to generate trace information</b>
* <br><br><b> Exposed methods : </b>
* 
* <br> 1. Logger logPackage.Logger.<b>createLoggerInstance()</b>
* <br> 2. void logPackage.Logger.<b> setTraceLevel(int iLevel)</b>
* <br> 3. void logPackage.Logger.<b>traceLog(int uTraceMethod, String sBufferArg1, Object... oVal)</b>
* <br> 4. Exposed static constants to be used along with traceLog to be passed for <b>uTraceMethod</b> parameter:
* <br><b> TRACEENTER
* <br> TRACELEAVE
* <br> TRACEINFO
* <br> TRACEERROR
* <br> TRACEDEBUG
* <br> TRACEWARNING</b>
* <br> 5. String logPackage.Logger.<b>getTraceBuffer()</b>
* 
* @author Gaurav Mazumdar
* @see <a href=https://blogs.sap.com/2018/03/21/asynchronous-singularity-for-mutual-exclusion/>SAP BLOG</a>
*/
public class Logger
{
	private StringBuffer sBuffer;
	private Stack<String> sStoreClassMethod = null;
	/**
	* <br><b> TRACEENTER:</b>
	* <br> Type: static final int
	* <br> To be used along with traceLog method for ENTER trace in methods. 
	*/
	public static final int TRACEENTER = 0;
	/**
	* <br><b> TRACELEAVE:</b> 
	* <br> Type: static final int
	* <br> To be used along with traceLog method for LEAVE trace in methods. 
	*/
	public static final int TRACELEAVE = 1;
	/**
	* <br><b> TRACEINFO:</b> 
	* <br> Type: static final int
	* <br> To be used along with traceLog method for INFO trace in methods. 
	*/
	public static final int TRACEINFO = 2;
	/**
	* <br><b> TRACEERROR:</b> 
	* <br> Type: static final int
	* <br> To be used along with traceLog method for ERROR trace in methods. 
	*/
	public static final int TRACEERROR = 3;
	/**
	* <br><b> TRACEDEBUG:</b>
	* <br> Type: static final int 
	* <br> To be used along with traceLog method for DEBUG trace in methods. 
	*/
	public static final int TRACEDEBUG = 4;
	/**
	* <br><b> TRACEWARNING:</b> 
	* <br> Type: static final int
	* <br> To be used along with traceLog method for WARNING trace in methods. 
	*/
	public static final int TRACEWARNING = 5;
	private enum traceLevel
	{
		LEVEL0,
		LEVEL1,
		LEVEL2,
		LEVEL3;
	}
	private traceLevel eLevel;
	
	/*
	* Logger(): No-argument constructor initializes instance variables. Set to private for singleton Logger class
	*/
	private Logger()
	{
		/*System.out.println("CTOR of Logger Class");*/
		sStoreClassMethod = new Stack<String>();
		StringBuffer sBufferedStream = new StringBuffer("<DEVLOG>") ;
		sBuffer = sBufferedStream;
	}
	
	/*
	* ThreadLocal<Logger>: Thread Local concept used for singleton Logger class so that Logger class returns one instance per thread
	*/
	private static final ThreadLocal<Logger> m_LogInstance = new ThreadLocal<Logger>()
    {
        @Override
        protected Logger initialValue()
        {
        	/*System.out.println("Thread specific singular instance: " + "HashCode= " + Thread.currentThread().hashCode()+ " " + Thread.currentThread().getName());*/
        	return new Logger();
        }
    };
	
	/**
	* <br><b> createLoggerInstance :</b> returns singleton instance for Logger class per execution per thread.
	* <br> Parameter : none 
	* <br> return type : Logger
	*/
	public static synchronized Logger createLoggerInstance()
	{
		return m_LogInstance.get();
	}
	
	/**
	* <br><b> setTraceLevel :</b> set Trace Level.
	* <br> Parameter : int
	* <br><b> 0 : </b> NO Trace.
	* <br><b> 1 : </b> ENTER, LEAVE and INFO trace.
	* <br><b> 2 : </b> ERROR Trace plus LEVEL1 traces.
	* <br><b> 3 : </b> WARNING, DEBUG plus LEVEL2 traces.
	* <br> return type : void
	*/
	public void setTraceLevel(int iLevel)
	{
		switch(iLevel) 
		{
			case 0:
				eLevel = traceLevel.LEVEL0;
				break;
			case 1:
				eLevel = traceLevel.LEVEL1;
				break;
			case 2:
				eLevel = traceLevel.LEVEL2;
				break;
			case 3:
				eLevel = traceLevel.LEVEL3;
				break;
			default:
				eLevel = traceLevel.LEVEL0;
				break;
		}
	}
	
	/*
	* getTraceLevel: returns trace level set by external source using setTraceLevel().
	*/
	private traceLevel getTraceLevel()
	{
		return eLevel;
	}

	/*
	* traceEnter: returns string buffer with class name and method ENTERED.
	*/
	private StringBuffer traceEnter(String sBufferArg1, Object sBufferArg2 )
	{
		StringBuffer sTempBuffer = new StringBuffer(MessageFormat.format("<ENTERING CLASSNAME=\"{0}\" METHODNAME=\"{1}\">", sBufferArg1, sBufferArg2));
		return sTempBuffer;
	}
	
	/*
	* traceLeave: returns string buffer with closing XML tag when EXITING a method.
	*/
	private StringBuffer traceLeave()
	{
		StringBuffer sTempBuffer = new StringBuffer("</ENTERING>");
		return sTempBuffer;
	}
	
	/*
	* traceInfo: returns string buffer with INFO details inside the methods.
	*/
	private <T> StringBuffer traceInfo(String sBufferArg1, Object sBufferArg2, T tValue )
	{
		T element = tValue;
		StringBuffer sTempBuffer = new StringBuffer(MessageFormat.format("<INFO METHODNAME=\"{0}\" INFORMATION=\"{1}\" VALUE=\"{2}\"></INFO>", sBufferArg1, sBufferArg2, element));
		return sTempBuffer;
	}
	
	/*
	* traceError: returns string buffer with ERROR details inside the methods.
	*/
	private <T> StringBuffer traceError(String sBufferArg1, Object sBufferArg2, T tValue )
	{
		T element = tValue;
		StringBuffer sTempBuffer = new StringBuffer(MessageFormat.format("<ERROR METHODNAME=\"{0}\" ERRORINFO=\"{1}\" VALUE=\"{2}\"></ERROR>", sBufferArg1, sBufferArg2, element));
		return sTempBuffer;
	}
	
	/*
	* traceDebug: returns string buffer with DEBUG details inside the methods.
	*/
	private <T> StringBuffer traceDebug(String sBufferArg1, Object sBufferArg2, T tValue )
	{
		T element = tValue;
		StringBuffer sTempBuffer = new StringBuffer(MessageFormat.format("<DEBUG METHODNAME=\"{0}\" DEBUGINFO=\"{1}\" VALUE=\"{2}\"></DEBUG>", sBufferArg1, sBufferArg2, element));
		return sTempBuffer;
	}
	
	/*
	* traceWarning: returns string buffer with WARNING details inside the methods.
	*/
	private <T> StringBuffer traceWarning(String sBufferArg1, Object sBufferArg2 , T tValue )
	{
		T element = tValue;
		StringBuffer sTempBuffer = new StringBuffer(MessageFormat.format("<WARNING METHODNAME=\"{0}\" WARNINFO=\"{1}\" VALUE=\"{2}\"></WARNING>", sBufferArg1, sBufferArg2, element));
		return sTempBuffer;
	}
	
	/**
	* <br><b> traceLog :</b> This method to be used to set the trace lines inside methods.
	* <br> Parameter :
	* <br> <b>uTraceMethod :</b> int type. Denotes the trace methods (ENTER, LEAVE, INFO, ERROR, DEBUG, WARNING) user wants to invoke.
	* <br> <b>sBufferArg1 :</b> String type. Denotes the Class name or Method name.
	* <br> <b>oVal :</b> Object type. Denotes the Method name or variable name or variable value. 
	* <br> return type : void
	* <br> <b>Example :</b>
	* <br><b> For ENTER and LEAVE (visible only with Trace level 1,2 and 3):</b>
	* <br> String ClassName = this.getClass().getSimpleName();
	* <br> String MethodName = new Object(){}.getClass().getEnclosingMethod().getName();
	* <br> logObject.traceLog(Logger.TRACEENTER, ClassName, MethodName);
	* <br> logObject.traceLog(Logger.TRACELEAVE, ClassName, MethodName);
	* <br><b> For INFO (visible only with Trace level 1,2 and 3):</b>
	* <br> logObject.traceLog(Logger.TRACEINFO, MethodName,"VARIABLE NAME1", VARIABLEDATA, "VARIABLE NAME2", VARIABLEDATA2,...so on);
	* <br><b> For ERROR (visible only with Trace level 2 and 3):</b>
	* <br> logObject.traceLog(Logger.TRACEERROR, MethodName,"ERROR CUSTOM TEXT 1 or ERROR DESCRIPTION 1", ERRORCODE1, "ERROR CUSTOM TEXT 2 or ERROR DESCRIPTION 2", ERRORCODE2,...so on);
	* <br><b> For DEBUG (visible only with Trace level  3):</b>
	* <br> logObject.traceLog(Logger.TRACEDEBUG, MethodName,"DEBUG TEXT 1 or DEBUG VARIABLE NAME 1", DEBUG_VARIABLE_VALUE1, "DEBUG TEXT 2 or DEBUG VARIABLE NAME 2", DEBUG_VARIABLE_VALUE2,...so on);
	* <br><b> For WARNING (visible only with Trace level  3):</b>
	* <br> logObject.traceLog(Logger.TRACEWARNING, MethodName,"WARNING TEXT 1 or WARNING VARIABLE NAME 1", WARNING_VARIABLE_VALUE2, "WARNING TEXT 2 or WARNING VARIABLE NAME 2", WARNING_VARIABLE_VALUE2,...so on);
	*/
	public void traceLog(int uTraceMethod, String sBufferArg1, Object... oVal)
	{
		Object[] element = oVal;
		int elementLength = element.length;
		String tempString = null;
		try
		{
			traceLevel iCurrentTraceLevel = getTraceLevel();
			StringBuffer sBufferedStream = new StringBuffer();
			switch (iCurrentTraceLevel)
			{
			case LEVEL3:
				if(uTraceMethod == TRACEWARNING)
				{
					if(elementLength == 0)
						sBufferedStream = sBufferedStream.append(traceWarning(sBufferArg1,"Missing WarningInfo", "Missing Data"));
					else if (elementLength % 2 == 0 )
					{
						for(int i = 0; i < elementLength; i = i+2)
						{
							sBufferedStream = sBufferedStream.append(traceWarning(sBufferArg1, element[i], element[i+1]));
						}
					}
					else
					{
						for(int i = 0; i < elementLength; i++)
						{
							sBufferedStream = sBufferedStream.append(traceWarning(sBufferArg1, element[i], "Missing Data"));
						}
					}
				}
				if(uTraceMethod == TRACEDEBUG)
				{
					if(elementLength == 0)
						sBufferedStream = sBufferedStream.append(traceDebug(sBufferArg1,"Missing DebugInfo", "Missing Data"));
					else if (elementLength % 2 == 0 )
					{
						for(int i = 0; i < elementLength; i = i+2)
						{
							sBufferedStream = sBufferedStream.append(traceDebug(sBufferArg1, element[i], element[i+1]));
						}
					}
					else
					{
						for(int i = 0; i < elementLength; i++)
						{
							sBufferedStream = sBufferedStream.append(traceDebug(sBufferArg1, element[i], "Missing Data"));
						}
					}
				}
			case LEVEL2:
				if(uTraceMethod == TRACEERROR)
				{
					if(elementLength == 0)
						sBufferedStream = sBufferedStream.append(traceError(sBufferArg1,"Missing ErrorInfo", "Missing Data"));
					else if (elementLength % 2 == 0 )
					{
						for(int i = 0; i < elementLength; i = i+2)
						{
							sBufferedStream = sBufferedStream.append(traceError(sBufferArg1, element[i], element[i+1]));
						}
					}
					else
					{
						for(int i = 0; i < elementLength; i++)
						{
							sBufferedStream = sBufferedStream.append(traceError(sBufferArg1, element[i], "Missing Data"));
						}
					}
				}
			case LEVEL1:
				if(uTraceMethod == TRACEENTER)
				{
					if(elementLength == 0)
					{
						sBufferedStream = traceEnter(sBufferArg1,"Missing MethodName");
						sStoreClassMethod.push(sBufferArg1 + " " + "Missing MethodName");
					}
					else if (elementLength != 0 )
					{
						sBufferedStream = traceEnter(sBufferArg1, element[0]);
						sStoreClassMethod.push(sBufferArg1 + " " + element[0]);
					}			
				}
				if(uTraceMethod == TRACELEAVE)
				{
					sBufferedStream = traceLeave();
					if(elementLength != 0)
						tempString = MessageFormat.format("{0} {1}",sBufferArg1, element[0]);
					else
						tempString = MessageFormat.format("{0} {1}",sBufferArg1, "Missing MethodName");
				}
				if(uTraceMethod == TRACEINFO)
				{
					if(elementLength == 0)
						sBufferedStream = sBufferedStream.append(traceInfo(sBufferArg1,"Missing Information", "Missing Data"));
					else if (elementLength % 2 == 0 )
					{
						for(int i = 0; i < elementLength; i = i+2)
						{
							sBufferedStream = sBufferedStream.append(traceInfo(sBufferArg1, element[i], element[i+1]));
						}
					}
					else
					{
						for(int i = 0; i < elementLength; i++)
						{
							sBufferedStream = sBufferedStream.append(traceInfo(sBufferArg1, element[i], "Missing Data"));
						}
					}
				}
				break;
			case LEVEL0:
				break;
			}
			if(sBufferedStream != null)
			{
				sBuffer.append(sBufferedStream);
				if(sStoreClassMethod.get(0).equals(tempString))
				{
					traceEnd();
					sStoreClassMethod.clear();
				}
			}
		}
		catch (Exception e) 
		{
			System.err.println(e);
		}

	}
	
	/**
	* <br><b> getTraceBuffer:</b> returns string buffer which contains the execution path per execution per thread
	* <br> Parameter : none 
	* <br> return type : String
	*/
	public String getTraceBuffer()
	{
		return sBuffer.toString();
	}
	
	/*
	* traceEnd: Closing the string buffer and do clean up activities at the end of the execution
	*/
	private void traceEnd()
	{
		try
		{
			String sBufferedStream = "</DEVLOG>" ;
			String tempBuffer = sBuffer.toString();
			tempBuffer = tempBuffer.replaceAll("</DEVLOG>", "");
			sBuffer = new StringBuffer(tempBuffer);
			sBuffer.append(sBufferedStream);
			String l_FilePath = System.getProperty("java.io.tmpdir")+ Thread.currentThread().getName() + new SimpleDateFormat("_yyyy_dd_MM_hhmmss").format(new Date()) +".xml";
			File file = new File(l_FilePath);
			BufferedWriter bw = null;
			FileWriter fw = null;
			fw = new FileWriter(file.getAbsolutePath(),true);
			bw = new BufferedWriter(fw);
			bw.write(sBuffer.toString());
			bw.close();
			fw.close();
			m_LogInstance.remove();
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
	}
}