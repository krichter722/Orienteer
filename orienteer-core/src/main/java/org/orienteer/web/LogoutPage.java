package org.orienteer.web;

import org.orienteer.OrienteerWebApplication;
import org.wicketstuff.annotation.mount.MountPath;

import ru.ydn.wicket.wicketorientdb.OrientDbWebSession;

@MountPath("/logout")
public class LogoutPage extends BasePage<Object> {
	private static final long serialVersionUID = 1L;

	public LogoutPage()
	{
		OrientDbWebSession.get().invalidate();
		setResponsePage(OrienteerWebApplication.get().getHomePage());
	}
}
