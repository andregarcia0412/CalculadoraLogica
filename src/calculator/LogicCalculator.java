package calculator;

public abstract class LogicCalculator {
    public static Boolean calculateConjunction(Boolean a, Boolean b) {
        return a && b;
    }

    public static Boolean calculateDisjunction(Boolean a, Boolean b) {
        return a || b;
    }

    public static Boolean calculateNegation(Boolean a) {
        return !a;
    }

    public static Boolean calculateExclusiveDisjunction(Boolean a, Boolean b) {
        return a ^ b;
    }

    public static Boolean calculateBiconditional(Boolean a, Boolean b) {
        return calculateNegation(calculateExclusiveDisjunction(a, b));
    }

}
