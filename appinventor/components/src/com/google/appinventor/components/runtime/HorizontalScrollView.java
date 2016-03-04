// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the MIT License https://raw.github.com/mit-cml/app-inventor/master/mitlicense.txt

package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentConstants;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Linear layout for placing components horizontally or vertically.
 *
 */
@SimpleObject
public final class HorizontalScrollView implements Layout {

  private final android.widget.HorizontalScrollView layoutManager;

  /**
   * Creates a new linear layout.
   *
   * @param context  view context
   * @param orientation one of
   *     {@link ComponentConstants#LAYOUT_ORIENTATION_HORIZONTAL}.
   *     {@link ComponentConstants#LAYOUT_ORIENTATION_VERTICAL}
   */
  HorizontalScrollView(Context context) {
    this(context, null, null);
  }

  /**
   * Creates a new linear layout with a preferred empty width/height.
   *
   * @param context  view context
   * @param orientation one of
   *     {@link ComponentConstants#LAYOUT_ORIENTATION_HORIZONTAL}.
   *     {@link ComponentConstants#LAYOUT_ORIENTATION_VERTICAL}
   * @param preferredEmptyWidth the preferred width of an empty layout
   * @param preferredEmptyHeight the preferred height of an empty layout
   */
  HorizontalScrollView(Context context, final Integer preferredEmptyWidth, final Integer preferredEmptyHeight) {
    if (preferredEmptyWidth == null && preferredEmptyHeight != null ||
        preferredEmptyWidth != null && preferredEmptyHeight == null) {
      throw new IllegalArgumentException("LinearLayout - preferredEmptyWidth and " +
          "preferredEmptyHeight must be either both null or both not null");
    }

    
	// Create an Android LinearLayout, but override onMeasure so that we can use our preferred
    // empty width/height.
    layoutManager = new android.widget.HorizontalScrollView(context) {
      @Override
      protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // If there was no preferred empty width/height specified (see constructors above), just
        // call super. (This is the case for the Form component.)
        if (preferredEmptyWidth == null || preferredEmptyHeight == null) {
          super.onMeasure(widthMeasureSpec, heightMeasureSpec);
          return;
        }

        // If the layout has any children, just call super.
        if (getChildCount() != 0) {
          super.onMeasure(widthMeasureSpec, heightMeasureSpec);
          return;
        }

        setMeasuredDimension(getSize(widthMeasureSpec, preferredEmptyWidth),
                             getSize(heightMeasureSpec, preferredEmptyHeight));
      }

      private int getSize(int measureSpec, int preferredSize) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
        // We were told how big to be
          result = specSize;
        } else {
        // Use the preferred size.
          result = preferredSize;
          if (specMode == MeasureSpec.AT_MOST) {
          // Respect AT_MOST value if that was what is called for by measureSpec
            result = Math.min(result, specSize);
          }
        }

        return result;
      }
    };

    /*layoutManager.setOrientation(
        orientation == ComponentConstants.LAYOUT_ORIENTATION_HORIZONTAL ?
        android.widget.LinearLayout.HORIZONTAL : android.widget.LinearLayout.VERTICAL);*/
		
  }

  // Layout implementation

  public ViewGroup getLayoutManager() {
    return layoutManager;
  }

  public void add(AndroidViewComponent component) {
    layoutManager.addView(component.getView(), new android.view.ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,  // width
        ViewGroup.LayoutParams.WRAP_CONTENT   // height
        ));
  }

//  public void setHorizontalGravity(int gravity) {
//    layoutManager.setHorizontalGravity(gravity);
//  }
//
//  public void setVerticalGravity(int gravity) {
//    layoutManager.setVerticalGravity(gravity);
//  }

}
