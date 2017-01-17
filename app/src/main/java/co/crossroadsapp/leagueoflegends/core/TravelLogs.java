package co.crossroadsapp.leagueoflegends.core;//package com.example.sharmha.travellerdestiny.core;
//
//import android.app.Activity;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.io.IOException;
//
///**
// * Created by sharmha on 2/24/16.
// */
//public class TravelLogs {
//
//    private static LogToFile logToFile;
//    private static boolean enableLog = false;
//    private static boolean DEBUG = ( AppEngine.DEBUG_APP == 0 ? true : false );
//
//    static
//    {
//        if( enableLog && DEBUG )
//        {
//            try
//            {
//                logToFile = new LogToFile();
//            }
//            catch( RuntimeException e )
//            {
//                Log.e("Trimble-TrimbleLog", e.toString());
//            }
//            catch( IOException e )
//            {
//                Log.e( "Trimble-TrimbleLog", e.toString() );
//            }
//        }
//    }
//
//    private static final String LOG_TAG = "Trimble-";
//
//    private static String getClassName( Object obj )
//    {
//        String strName = "";
//        if( obj instanceof String )
//        {
//            strName = (String)obj;
//        }
//        else
//        {
//            strName = obj.getClass().getName();
//            int index = strName.lastIndexOf( '.' );
//            if( index != -1 )
//            {
//                strName = strName.substring( index + 1 );
//            }
//        }
//        return LOG_TAG + strName;
//    }
//
//    public static void setLogEnable( boolean enabled )
//    {
//        enableLog = enabled;
//        DEBUG = enabled;
//    }
//
//    public static void v( Object obj, String msg )
//    {
//        if( !DebugOn() )
//        {
//            return;
//        }
//        String strClassName = getClassName( obj );
//
//        Log.println( Log.VERBOSE, strClassName, msg );
//
//        if( logToFile != null && enableLog )
//        {
//            logToFile.writeLogToFile( Log.VERBOSE, strClassName, msg );
//        }
//    }
//
//    public static void throwToast( Activity act, String error )
//    {
//        if( !DebugOn() )
//        {
//            return;
//        }
//        Toast.makeText(act, error, Toast.LENGTH_LONG);
//    }
//
//    public static void d( Object obj, String msg )
//    {
//        if( !DebugOn() )
//        {
//            return;
//        }
//        String strClassName = getClassName( obj );
//
//        Log.println( Log.DEBUG, strClassName, msg );
//
//        if( logToFile != null && enableLog )
//        {
//            logToFile.writeLogToFile( Log.DEBUG, strClassName, msg );
//        }
//    }
//
//    public static void i( Object obj, String msg )
//    {
//        if( !DebugOn() )
//        {
//            return;
//        }
//        String strClassName = getClassName( obj );
//
//        Log.println( Log.INFO, strClassName, msg );
//
//        if( logToFile != null && enableLog )
//        {
//            logToFile.writeLogToFile( Log.INFO, strClassName, msg );
//        }
//    }
//
//    public static void w( Object obj, String msg )
//    {
//        if( !DebugOn() )
//        {
//            return;
//        }
//        String strClassName = getClassName( obj );
//
//        Log.println(Log.WARN, strClassName, msg);
//
//        if( logToFile != null && enableLog )
//        {
//            logToFile.writeLogToFile( Log.WARN, strClassName, msg );
//        }
//    }
//
//    public static void e( Object obj, String msg )
//    {
//        if( !DebugOn() )
//        {
//            return;
//        }
//        String strClassName = getClassName( obj );
//
//        Log.println( Log.ERROR, strClassName, msg );
//
//        if( logToFile != null && enableLog )
//        {
//            logToFile.writeLogToFile( Log.ERROR, strClassName, msg );
//        }
//    }
//
//    private static boolean DebugOn()
//    {
//        return DEBUG;
//    }
//}
