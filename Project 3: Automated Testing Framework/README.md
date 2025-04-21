# Project 03: Testing

In this project, I developed several core components of a custom testing framework in Java. First, I implemented my own version of **JUnit** from scratch—without using any existing JUnit libraries. Then, I extended the framework to support **QuickCheck-style property-based testing**, allowing for randomized test generation (with deterministic values for grading purposes).

To build this system, I worked extensively with **Java reflection** (`java.lang.reflect`) to dynamically discover and execute annotated test methods.
