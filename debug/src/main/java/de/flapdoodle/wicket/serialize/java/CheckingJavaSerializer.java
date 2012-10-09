package de.flapdoodle.wicket.serialize.java;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.serialize.java.JavaSerializer;
import org.apache.wicket.util.io.SerializableChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckingJavaSerializer extends JavaSerializer {

	private static final Logger log = LoggerFactory.getLogger(JavaSerializer.class);
	private final ISerializableCheck serializableCheck;
	
	public CheckingJavaSerializer(String applicationKey,ISerializableCheck serializableCheck) {
		super(applicationKey);
		this.serializableCheck = serializableCheck;
	}

	@Override
	protected ObjectOutputStream newObjectOutputStream(OutputStream out)
			throws IOException {
		return new PreCheckingObjectOutputStream(out,serializableCheck);
	}
	
	private static class PreCheckingObjectOutputStream extends ObjectOutputStream
	{
		private final ObjectOutputStream oos;
		private final ISerializableCheck serializableCheck;

		public PreCheckingObjectOutputStream(OutputStream out, ISerializableCheck serializableCheck) throws IOException
		{
			this.serializableCheck = serializableCheck;
			oos = new ObjectOutputStream(out);
		}

		@Override
		protected final void writeObjectOverride(Object obj) throws IOException
		{
			try
			{
				if (PreSerializeChecker.isAvailable()) {
					new PreSerializeChecker(serializableCheck).writeObject(obj);
				}
				
				oos.writeObject(obj);
			}
			catch (NotSerializableException nsx)
			{
				if (SerializableChecker.isAvailable())
				{
					// trigger serialization again, but this time gather
					// some more info
					new SerializableChecker(nsx).writeObject(obj);
					// if we get here, we didn't fail, while we
					// should;
					throw nsx;
				}
				throw nsx;
			}
			catch (Exception e)
			{
				log.error("error writing object " + obj + ": " + e.getMessage(), e);
				throw new WicketRuntimeException(e);
			}
		}

		@Override
		public void flush() throws IOException
		{
			oos.flush();
		}

		@Override
		public void close() throws IOException
		{
			oos.close();
		}
	}
}
