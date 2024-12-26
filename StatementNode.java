public abstract class StatementNode{

    public abstract <T> T accept(StatementNodeVisitor<T> visitor);

    public interface StatementNodeVisitor<T> {
        T visitPrintNode(PrintNode node);
        T visitAssignmentNode(AssignmentNode node);
    }

    public static class PrintNode extends StatementNode{
        private final ExprNode expression;

        public PrintNode(ExprNode expression) {
            this.expression = expression;
        }

        public ExprNode getExpression() {
            return expression;
        }

        @Override
        public <T> T accept(StatementNodeVisitor<T> visitor) {
            return visitor.visitPrintNode(this);
        }
    }

    public static class AssignmentNode extends StatementNode{
        private final String variableName;
        private final ExprNode expression;

        public AssignmentNode(String variableName, ExprNode expression) {
            this.variableName = variableName;
            this.expression = expression;
        }

        public String getVariableName() {
            return variableName;
        }

        public ExprNode getExpression() {
            return expression;
        }

        @Override
        public <T> T accept(StatementNodeVisitor<T> visitor) {
            return visitor.visitAssignmentNode(this);
        }
    }

}