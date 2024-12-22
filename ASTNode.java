public abstract class ASTNode {
    public class LiteralNode extends ASTNode {
        private final double value;

        public LiteralNode(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    public class BinaryNode extends ASTNode {
        private final String operator;
        private final ASTNode left;
        private final ASTNode right;

        public BinaryNode(String operator, ASTNode left, ASTNode right) {
            this.operator = operator;
            this.left = left;
            this.right = right;
        }

        public String getOperator() {
            return operator;
        }

        public ASTNode getLeft() {
            return left;
        }

        public ASTNode getRight() {
            return right;
        }
    }

    public class UnaryNode extends ASTNode {
        private final String operator;
        private final ASTNode operand;

        public UnaryNode(String operator, ASTNode operand) {
            this.operator = operator;
            this.operand = operand;
        }

        public String getOperator() {
            return operator;
        }

        public ASTNode getOperand() {
            return operand;
        }
    }

    public class IdentifierNode extends ASTNode {
        private final String name;

        public IdentifierNode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public class GroupingNode extends ASTNode {
        private final ASTNode expression;

        public GroupingNode(ASTNode expression) {
            this.expression = expression;
        }

        public ASTNode getExpression() {
            return expression;
        }
    }

    public class EqualityNode extends ASTNode {
        private final ASTNode left;
        private final ASTNode right;

        public EqualityNode(ASTNode left, ASTNode right) {
            this.left = left;
            this.right = right;
        }

        public ASTNode getLeft() {
            return left;
        }

        public ASTNode getRight() {
            return right;
        }
    }

}