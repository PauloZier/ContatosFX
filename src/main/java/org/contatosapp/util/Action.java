package org.contatosapp.util;

/**
 *
 * @author zier
 */
@FunctionalInterface
public interface Action<T> {
    void execute(T t);
}
