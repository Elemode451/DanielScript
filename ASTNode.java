public abstract class ASTNode {

    public abstract <T> T accept(ASTNodeVisitor<T> visitor);

    public interface ASTNodeVisitor<T> {
        T visitLiteralNode(LiteralNode node);
        T visitBinaryNode(BinaryNode node);
        T visitUnaryNode(UnaryNode node);
        T visitIdentifierNode(IdentifierNode node);
    }

    public static class LiteralNode extends ASTNode {
        private final Object literal;

        public LiteralNode(Object literal) {
            this.literal = literal;
        }

        public Object getLiteral() {
            return literal;
        }

        @Override
        public <T> T accept(ASTNodeVisitor<T> visitor) {
            return visitor.visitLiteralNode(this);
        }
    }

    public static class BinaryNode extends ASTNode {
        private final Token operator;
        private final ASTNode left;
        private final ASTNode right;

        public BinaryNode(Token operator, ASTNode left, ASTNode right) {
            this.operator = operator;
            this.left = left;
            this.right = right;
        }

        public Token getOperator() {
            return operator;
        }

        public ASTNode getLeft() {
            return left;
        }

        public ASTNode getRight() {
            return right;
        }

        @Override
        public <T> T accept(ASTNodeVisitor<T> visitor) {
            return visitor.visitBinaryNode(this);
        }
    }

    public static class UnaryNode extends ASTNode {
        private final Token operator;
        private final ASTNode operand;

        public UnaryNode(Token operator, ASTNode operand) {
            this.operator = operator;
            this.operand = operand;
        }

        public Token getOperator() {
            return operator;
        }

        public ASTNode getOperand() {
            return operand;
        }

        @Override
        public <T> T accept(ASTNodeVisitor<T> visitor) {
            return visitor.visitUnaryNode(this);
        }
    }

    public static class IdentifierNode extends ASTNode {
        private final String name;

        public IdentifierNode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public <T> T accept(ASTNodeVisitor<T> visitor) {
            return visitor.visitIdentifierNode(this);
        }
    }

}