/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.devtools.j2objc.ast;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Type node for an intersection type in a cast expression (added in JLS8).
 */
public class IntersectionType extends Type {
  private List<Type> types;

  public IntersectionType(org.eclipse.jdt.core.dom.IntersectionType jdtNode) {
    super(jdtNode);
    typeBinding = jdtNode.resolveBinding();
    types = Lists.newArrayListWithExpectedSize(jdtNode.types().size());
    for (Object x : jdtNode.types()) {
      types.add((Type) TreeConverter.convert(x));
    }
  }

  public IntersectionType(IntersectionType other) {
    super(other);
    typeBinding = other.getTypeBinding();
    types = other.types;
  }

  public List<Type> types() {
    return types;
  }

  public boolean isIntersectionType() {
    return true;
  }

  public IntersectionType copy() {
    return new IntersectionType(this);
  }

  @Override
  public void validateInner() {
    super.validateInner();
    Preconditions.checkNotNull(typeBinding);
  }

  @Override
  public Kind getKind() {
    return Kind.INTERSECTION_TYPE;
  }

  @Override
  protected void acceptInner(TreeVisitor visitor) {
    visitor.visit(this);
    visitor.endVisit(this);
  }
}