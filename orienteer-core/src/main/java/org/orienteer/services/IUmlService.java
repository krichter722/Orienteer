package org.orienteer.services;

import org.orienteer.services.impl.PlantUmlService;

import com.google.inject.ImplementedBy;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OSchema;

@ImplementedBy(PlantUmlService.class)
public interface IUmlService
{
	public String describe(OSchema schema);
	public String describe(OClass oClass);
	public String describe(boolean goUp, boolean goDown, OClass... oClass);
	public String describe(OProperty oProperty);
	public String describeAsImage(OSchema schema);
	public String describeAsImage(OClass oClass);
	public String describeAsImage(boolean goUp, boolean goDown, OClass... oClass);
	public String describeAsImage(OProperty oProperty);
	public String asImage(String content);
	public boolean isUmlDebugEnabled();
}
