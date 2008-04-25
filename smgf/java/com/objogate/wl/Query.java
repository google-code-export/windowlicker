package com.objogate.wl;

import org.hamcrest.SelfDescribing;

/**
 * Return a value obased on the contents of a component 
 * @param <ComponentType> The type of the Component
 * @param <ValueType> The type of the returned value
 */
public interface Query<ComponentType, ValueType> extends SelfDescribing {

  /**
   * Return a value based on what's in the component
   * @param component The component to investigate 
   * @return The result of the query
   */
  ValueType query(ComponentType component);
}
