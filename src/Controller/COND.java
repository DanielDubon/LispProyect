package Controller;


import java.util.ArrayList;
import java.util.List;

public class COND {

    private List<CondClause> clauses;

    public COND() {
        clauses = new ArrayList<>();
    }

    public void addClause(CondClause clause) {
        clauses.add(clause);
    }

    public int evaluate() {
        for (CondClause clause : clauses) {
            if (clause.getPredicate().evaluate()) {
                return clause.getExpression().evaluate();
            }
        }
        throw new IllegalArgumentException("No valid condition found in COND clause");
    }

    public static class CondClause {
        private Predicate predicate;
        private Expression expression;

        public CondClause(Predicate predicate, Expression expression) {
            this.predicate = predicate;
            this.expression = expression;
        }

        public Predicate getPredicate() {
            return predicate;
        }

        public Expression getExpression() {
            return expression;
        }
    }

    public interface Predicate {
        boolean evaluate();
    }

    public interface Expression {
        int evaluate();
    }
}
