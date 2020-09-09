package com.datorama.services;

import java.util.List;
import java.util.Objects;

import com.datorama.str.models.TestParameters;

/** cause we have data driven tests, need to make them unique */
//TODO need to add another way to identify items
public class UniqueTestItem {
  private final String name;
  private final List<TestParameters> testParameters;

  public UniqueTestItem(String name, List<TestParameters> testParameters) {
    this.name = name;
    this.testParameters = testParameters;
  }

  public String getName() {
    return name;
  }

  public List<TestParameters> getTestParameters() {
    return testParameters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UniqueTestItem)) return false;
    UniqueTestItem that = (UniqueTestItem) o;
    return getName().equals(that.getName()) && getTestParameters().equals(that.getTestParameters());
  }

  public boolean equals(String name, List<TestParameters> parameters) {
    return getName().equals(name) && getTestParameters().equals(parameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getTestParameters());
  }

  @Override public String toString() {
    final StringBuilder sb = new StringBuilder("UniqueTestItem{");
    sb.append("name='").append(name).append('\'');
    sb.append(", testParameters=").append(testParameters);
    sb.append('}');
    return sb.toString();
  }
}
