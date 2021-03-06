package org.orienteer.junit;

import java.io.IOException;
import java.net.URL;

import org.apache.wicket.util.tester.WicketTester;
import org.orienteer.services.OrienteerModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.util.Modules;

public class StaticInjectorProvider implements Provider<Injector>
{
	private static final Logger LOG = LoggerFactory.getLogger(StaticInjectorProvider.class);
	
	private static final Injector STATIC_INJECTOR;
	
	static
	{
		System.out.println("Using embedded mode");
		if(!System.getProperties().containsKey(OrienteerModule.PROPERTIES_FILE_NAME))
		{
			try
			{
				URL testProperties = OrienteerModule.lookupFile(OrienteerTestModule.TEST_PROPERTIES_FILE_NAME);
				if(testProperties!=null)
				{
					System.setProperty(OrienteerModule.PROPERTIES_FILE_NAME, testProperties.toString());
				}
			} catch (IOException e)
			{
				LOG.error("Can't get test properties", e);
			}
//			System.setProperty(OrienteerModule.PROPERTIES_FILE_NAME, OrienteerTestRunner.class.getResource("test-env.properties").toString());
		}
		STATIC_INJECTOR = Guice.createInjector(Modules.override(new OrienteerModule()).with(new OrienteerTestModule()));
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run() {
				WicketTester wicketTester = STATIC_INJECTOR.getInstance(WicketTester.class);
				wicketTester.destroy();
			}
		});
	}
	
	public static final StaticInjectorProvider INSTANCE = new StaticInjectorProvider();

	@Override
	public Injector get() {
		return STATIC_INJECTOR;
	}

}
