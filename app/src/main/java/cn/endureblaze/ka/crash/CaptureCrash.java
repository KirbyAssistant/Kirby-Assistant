package cn.endureblaze.ka.crash;

import android.os.*;

public class CaptureCrash
{
	private CrashHandler mCrashHandler;

	private static CaptureCrash mInstance;

	private CaptureCrash()
	{

	}

	private static CaptureCrash getInstance()
	{
		if (mInstance == null)
		{
			synchronized (CaptureCrash.class)
			{
				if (mInstance == null)
				{
					mInstance = new CaptureCrash();
				}
			}
		}

		return mInstance;
	}

	public static void init(CrashHandler crashHandler)
	{
		getInstance().setCrashHandler(crashHandler);
	}

	private void setCrashHandler(CrashHandler crashHandler)
	{

		mCrashHandler = crashHandler;
		new Handler(Looper.getMainLooper()).post(() -> {
			for (;;)
			{
				try
				{
					Looper.loop();
				}
				catch (Throwable e)
				{
					if (mCrashHandler != null)
					{//捕获异常处理
						mCrashHandler.uncaughtException(Looper.getMainLooper().getThread(), e);
					}
				}
			}
		});

		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			if (mCrashHandler != null)
			{//捕获异常处理
				mCrashHandler.uncaughtException(t, e);
			}
		});

	}

	public interface CrashHandler
	{
		void uncaughtException(Thread t, Throwable e);
	}
}
