// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the MIT License https://raw.github.com/mit-cml/app-inventor/master/mitlicense.txt

package com.google.appinventor.client.editor.simple.components;



import static com.google.appinventor.client.Ode.MESSAGES;
import com.google.appinventor.client.editor.simple.SimpleEditor;
import com.google.appinventor.client.editor.simple.components.utils.PropertiesUtil;
import com.google.appinventor.client.editor.youngandroid.properties.YoungAndroidHorizontalAlignmentChoicePropertyEditor;
import com.google.appinventor.client.editor.youngandroid.properties.YoungAndroidLengthPropertyEditor;
import com.google.appinventor.client.editor.youngandroid.properties.YoungAndroidVerticalAlignmentChoicePropertyEditor;
import com.google.appinventor.client.output.OdeLog;
import com.google.appinventor.client.properties.BadPropertyEditorException;
import com.google.appinventor.client.properties.Property;
import com.google.appinventor.client.widgets.properties.EditableProperty;
import com.google.appinventor.client.widgets.properties.PropertyEditor;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AbsolutePanel;



/**
 * Mock HorizontalArrangement component.
 *
 * @author sharon@google.com (Sharon Perl)
 * @author pablomorpheo@gmail.com  (Pabloko)
 */
public final class MockVerticalScroll extends MockContainer {

  /**
   * Component type name.
   */
  public static final String TYPE = "VerticalScroll";
  
  
  // Form UI components
  protected final AbsolutePanel layoutWidget;

  private MockHVLayout myLayout;
  
  private static final String PROPERTY_NAME_HORIZONTAL_ALIGNMENT = "AlignHorizontal";
  private static final String PROPERTY_NAME_VERTICAL_ALIGNMENT = "AlignVertical";
  
  private YoungAndroidHorizontalAlignmentChoicePropertyEditor myHAlignmentPropertyEditor;
  private YoungAndroidVerticalAlignmentChoicePropertyEditor myVAlignmentPropertyEditor;
  
  private boolean initialized = false;
  
  
  public MockVerticalScroll(SimpleEditor editor) {
  
    super(editor, TYPE, images.verticalScroll(), MockHVArrangementHelper.makeLayout(0));

    myLayout = MockHVArrangementHelper.getLayout();

    rootPanel.setHeight("100%");

    layoutWidget = new AbsolutePanel();
    layoutWidget.setStylePrimaryName("ode-SimpleMockContainer");
    layoutWidget.add(rootPanel);

    initComponent(layoutWidget);
    try {
      myHAlignmentPropertyEditor = PropertiesUtil.getHAlignmentEditor(properties);
      myVAlignmentPropertyEditor = PropertiesUtil.getVAlignmentEditor(properties);
    } catch (BadPropertyEditorException e) {
      OdeLog.log(MESSAGES.badAlignmentPropertyEditorForArrangement());
      return;
    };

    initialized = true;
  }
  
	@Override
  public void onPropertyChange(String propertyName, String newValue) {
    super.onPropertyChange(propertyName, newValue);
    if  (propertyName.equals(PROPERTY_NAME_HORIZONTAL_ALIGNMENT)) {
      myLayout.setHAlignmentFlags(newValue);
      refreshForm();
    } else if (propertyName.equals(PROPERTY_NAME_VERTICAL_ALIGNMENT)) {
      myLayout.setVAlignmentFlags(newValue);
      refreshForm();
    } 
    
  }
    
}
