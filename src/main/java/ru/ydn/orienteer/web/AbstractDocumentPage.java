package ru.ydn.orienteer.web;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.SetModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.Strings;

import ru.ydn.wicket.wicketorientdb.OrientDbWebSession;
import ru.ydn.wicket.wicketorientdb.model.ODocumentModel;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;

public abstract class AbstractDocumentPage extends OrienteerBasePage<ODocument> 
{
	public AbstractDocumentPage() {
		super();
	}

	public AbstractDocumentPage(IModel<ODocument> model) {
		super(model);
	}

	public AbstractDocumentPage(PageParameters parameters) {
		super(parameters);
	}
	
	
	@Override
	protected IModel<ODocument> resolveByPageParameters(PageParameters parameters) {
		String rid = parameters.get("rid").toOptionalString();
		if(rid!=null)
		{
			return new ODocumentModel(new ORecordId(rid));
		}
		else
		{
			return new ODocumentModel((ODocument)null);
		}
	}

	
	@Override
	protected void onConfigure() {
		super.onConfigure();
		ODocument doc = getDocument();
		if(doc==null || Strings.isEmpty(doc.getClassName()))
        {
            throw new AbortWithHttpErrorCodeException(HttpServletResponse.SC_NOT_FOUND);
        }
	}
	
	@SuppressWarnings("unchecked")
	public IModel<ODocument> getDocumentModel()
	{
		return (IModel<ODocument>) getDefaultModel();
	}
	
	public ODocument getDocument()
	{
		return getDocumentModel().getObject();
	}
	
	
	
}