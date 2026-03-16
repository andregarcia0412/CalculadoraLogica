package calculator;

public enum TokenType {
    VAR(0),

    NOT(5){
        @Override
        public Boolean calculate(Boolean a, Boolean b) {
            return LogicCalculator.negation(a);
        }
    },

    AND(4){
        @Override
        public Boolean calculate(Boolean a, Boolean b) {
            return LogicCalculator.conjunction(a, b);
        }
    },

    OR(3){
        @Override
        public Boolean calculate(Boolean a, Boolean b) {
            return LogicCalculator.disjunction(a, b);
        }
    },

    XOR(3){
        @Override
        public Boolean calculate(Boolean a, Boolean b) {
            return LogicCalculator.exclusiveDisjunction(a, b);
        }
    },

    IMPLIES(2){
        @Override
        public Boolean calculate(Boolean a, Boolean b) {
            return LogicCalculator.implication(a, b);
        }
    },

    IFF(1){
        @Override
        public Boolean calculate(Boolean a, Boolean b) {
            return LogicCalculator.biconditional(a, b);
        }
    },

    LPARENTHESIS(-1),
    RPARENTHESIS(-1);

    public final int precedence;

    TokenType(int precedence){
        this.precedence = precedence;
    }

    public Boolean calculate(Boolean a, Boolean b){
        throw new UnsupportedOperationException("Operation not supported for " + this);
    }
}