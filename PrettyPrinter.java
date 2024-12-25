public class PrettyPrinter implements ASTNode.ASTNodeVisitor<String>{

    String print(ASTNode root) {
        return root.accept(this);
    }

    @Override
    public String visitLiteralNode(ASTNode.LiteralNode node) {
        return node.getLiteral().toString();
    }

    @Override
    public String visitBinaryNode(ASTNode.BinaryNode node) {
        return parenthesize(node.getOperator().VALUE.toString(), node.getLeft(), node.getRight());
    }

    @Override
    public String visitUnaryNode(ASTNode.UnaryNode node) {
        return parenthesize(node.getOperator().LITERAL.toString(), node.getOperand());
    }

    @Override
    public String visitIdentifierNode(ASTNode.IdentifierNode node) {
        return node.getName();
    }

    @Override
    public String visitGroupingNode(ASTNode.GroupingNode node) {
        return node.getExpression().accept(this);
    }

    // THANK YOU CRAFTINGINTERPRETERS!
    private String parenthesize(String name, ASTNode... exprs) {
        StringBuilder builder = new StringBuilder();
    
        builder.append("(").append(name);
        for (ASTNode expr : exprs) {
          builder.append(" ");
          builder.append(expr.accept(this));
        }
        builder.append(")");
    
        return builder.toString();
      }
    
}
