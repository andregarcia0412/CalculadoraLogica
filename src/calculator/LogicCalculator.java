package calculator;

public abstract class LogicCalculator {
    public static Boolean conjunction(Boolean a, Boolean b) {
        return a && b;
    }

    public static Boolean disjunction(Boolean a, Boolean b) {
        return a || b;
    }

    public static Boolean negation(Boolean a) {
        return !a;
    }

    public static Boolean exclusiveDisjunction(Boolean a, Boolean b) {
        return a ^ b;
    }

    public static Boolean biconditional(Boolean a, Boolean b) {
        return negation(exclusiveDisjunction(a, b));
    }

    public static Boolean implication(Boolean a, Boolean b) {
        return disjunction(negation(a), b);
    }

}
