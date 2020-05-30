package org.contatosapp.model.definition;

/**
 *
 * @author zier
 */
@FunctionalInterface
public interface Action<T> {
    void execute(T t);
}
