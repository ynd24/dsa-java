package edu.emory.cs.algebraic;

public interface Numeral<T extends Numeral<T>> { // T is generic type that must be subtype of Numeral
    void add(T n); // all methods in an interface are public
}
