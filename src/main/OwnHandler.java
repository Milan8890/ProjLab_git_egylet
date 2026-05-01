package main;

import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class OwnHandler extends Handler {

	@Override
	public void publish(LogRecord record) {
		
		
		String message = record.getMessage();
		Object[] args = record.getParameters();
		for (Object object : args) {
			// TODO máshogy
			message = message.replaceFirst("\\[Obj\\]", App.getObjectName(object));
		}
		System.out.println(message);
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() throws SecurityException {
	}

}
