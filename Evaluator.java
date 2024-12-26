public class Evaluator implements ExprNode.ExprNodeVisitor<Object>{

    public Object evaluate(ExprNode root) {
        return root.accept(this);
    }

    public void run(ExprNode root) {
        try{
            Object result = root.accept(this);
            if(result == null) {
                System.out.println("Something went horribly wrong here!");
            } else {
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void checkDouble(Object operand) {
        if (!(operand instanceof Double)) {
            throw new RuntimeException("Operand must be a number.");
        }
      }

    @Override
    public Object visitLiteralNode(ExprNode.LiteralNode node) {
        return node.getLiteral();
    }

    @Override
    public Object visitBinaryNode(ExprNode.BinaryNode node) {
        Object leftResult = evaluate(node.getLeft());
        Object rightResult = evaluate(node.getRight());

        checkDouble(leftResult);
        checkDouble(rightResult);

        switch(node.getOperator().TYPE) {
            case PLUS:
                return (double) leftResult + (double) rightResult;
            case MINUS:
                return (double) leftResult - (double) rightResult;
            case MULTIPLY:
                return (double) leftResult * (double) rightResult;
            case DIVIDE:
                return (double) leftResult / (double) rightResult;
            case EXPONENT:
                return Math.pow((double) leftResult,(double) rightResult);
            default:
                return null;

        }
    }

    @Override
    public Object visitUnaryNode(ExprNode.UnaryNode node) {
        Object result = evaluate(node.getOperand());
        checkDouble(result);

        switch(node.getOperator().TYPE) {
            case MINUS:
                return -1 * (double) result;
            default:
                return null;

        }  
   }

    @Override
    public Object visitGroupingNode(ExprNode.GroupingNode node) {
        return evaluate(node.getExpression());
    }

}
