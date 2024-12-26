public class PrettyPrinter implements ExprNode.ExprNodeVisitor<String>{

    String print(ExprNode root) {
        return root.accept(this);
    }

    @Override
    public String visitLiteralNode(ExprNode.LiteralNode node) {
        return node.getLiteral().toString();
    }

    @Override
    public String visitBinaryNode(ExprNode.BinaryNode node) {
        return parenthesize(node.getOperator().VALUE.toString(), node.getLeft(), node.getRight());
    }

    @Override
    public String visitUnaryNode(ExprNode.UnaryNode node) {
        return parenthesize(node.getOperator().VALUE.toString(), node.getOperand());
    }

    @Override
    public String visitGroupingNode(ExprNode.GroupingNode node) {
        return node.getExpression().accept(this);
    }

    // THANK YOU CRAFTINGINTERPRETERS!
    private String parenthesize(String name, ExprNode... exprs) {
        StringBuilder builder = new StringBuilder();
    
        builder.append("(").append(name);
        for (ExprNode expr : exprs) {
          builder.append(" ");
          builder.append(expr.accept(this));
        }
        builder.append(")");
    
        return builder.toString();
      }
    
}
