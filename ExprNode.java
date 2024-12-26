public abstract class ExprNode{

    public abstract <T> T accept(ExprNodeVisitor<T> visitor);

    public interface ExprNodeVisitor<T> {
        T visitLiteralNode(LiteralNode node);
        T visitBinaryNode(BinaryNode node);
        T visitUnaryNode(UnaryNode node);
        T visitGroupingNode(GroupingNode node);
    }

    public static class LiteralNode extends ExprNode{
        private final Object literal;

        public LiteralNode(Object literal) {
            this.literal = literal;
        }

        public Object getLiteral() {
            return literal;
        }

        @Override
        public <T> T accept(ExprNodeVisitor<T> visitor) {
            return visitor.visitLiteralNode(this);
        }
    }

    public static class BinaryNode extends ExprNode{
        private final Token operator;
        private final ExprNode left;
        private final ExprNode right;

        public BinaryNode(Token operator, ExprNode left, ExprNode right) {
            this.operator = operator;
            this.left = left;
            this.right = right;
        }

        public Token getOperator() {
            return operator;
        }

        public ExprNode getLeft() {
            return left;
        }

        public ExprNode getRight() {
            return right;
        }

        @Override
        public <T> T accept(ExprNodeVisitor<T> visitor) {
            return visitor.visitBinaryNode(this);
        }
    }

    public static class UnaryNode extends ExprNode{
        private final Token operator;
        private final ExprNode operand;

        public UnaryNode(Token operator, ExprNode operand) {
            this.operator = operator;
            this.operand = operand;
        }

        public Token getOperator() {
            return operator;
        }

        public ExprNode getOperand() {
            return operand;
        }

        @Override
        public <T> T accept(ExprNodeVisitor<T> visitor) {
            return visitor.visitUnaryNode(this);
        }
    }

    public static class GroupingNode extends ExprNode{
        private final ExprNode expression;

        public GroupingNode(ExprNode expression) {
            this.expression = expression;
        }

        public ExprNode getExpression() {
            return expression;
        }

        @Override
        public <T> T accept(ExprNodeVisitor<T> visitor) {
            return visitor.visitGroupingNode(this);
        }
    }

}