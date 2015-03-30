/*
 * Copyright 2015 richter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.orienteer.components.properties.visualizers;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.ComponentDetachableModel;
import org.apache.wicket.model.IModel;
import org.orienteer.components.commands.SaveODocumentCommand;
import org.orienteer.components.properties.DisplayMode;
import org.orienteer.components.properties.visualizers.AbstractSimpleVisualizer;
import org.orienteer.components.properties.visualizers.IVisualizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ydn.wicket.wicketorientdb.model.OQueryModel;

/**
 * An {@link IVisualizer} to render binary fields. It displays a constant label
 * {@code <binary data>} because binary data might not be printable and a button
 * which allows download in form of a file in view mode and a text input field
 * and a file upload button to enter data in edit mode.
 *
 * @author richter
 */
public class BinaryVisualizer extends AbstractSimpleVisualizer implements IVisualizer {

    /**
     * The name used in the {@code name} property in
     * {@link AbstractSimpleVisualizer}
     */
    public final static String NAME = "binaryFileUpload";
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = LoggerFactory.getLogger(BinaryVisualizer.class);
    private final transient IModel<List<FileUpload>> fileUploadModel = new ComponentDetachableModel<List<FileUpload>>();

    public BinaryVisualizer() {
        super(NAME, false, OType.BINARY);
        LOGGER.debug(String.format("created new instance of %s", BinaryVisualizer.class.getName())); //extranous instances might be created in incorrectly configured lifecycles
    }

    @Override
    public <V> Component createComponent(String id, DisplayMode mode,
            IModel<ODocument> documentModel, IModel<OProperty> propertyModel,
            IModel<V> valueModel) {
        switch (mode) {
            case VIEW:
                LOGGER.debug("returning view component");
                return new Label(id, "<binary data>").setEscapeModelStrings(false);
            case EDIT:
                LOGGER.debug("returning edit component");
                MarkupContainer retValue = new WebMarkupContainer(id);
                retValue.add(new TextField<V>(id, valueModel));

                OProperty property = propertyModel.getObject();
//				OPropertyModel propertyModel

                OClass oClass = property.getLinkedClass();
                OQueryModel<ODocument> choicesModel = new OQueryModel<ODocument>("select from " + oClass.getName() + " LIMIT 100");

                FileUploadField fileUploadField = new FileUploadField(id, fileUploadModel);
                fileUploadField.add(new SaveODocumentCommand(null, null) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick(AjaxRequestTarget target) {
//				fileUploadModel.getObject().get(0).getInputStream();
//				propertyModel.setObject();
//				target.
//				getSchema().dropClass(object.getName());
                    }
                });
                retValue.add(fileUploadField);
                return retValue;
            default:
                LOGGER.warn("requested neither view nor edit component, returning null");
                return null;
        }
    }

}
