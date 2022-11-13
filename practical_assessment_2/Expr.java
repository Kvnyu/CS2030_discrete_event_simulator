import java.util.Optional;

class Expr<T> {
    private final Optional<T> value;
    private final Optional<Expr<T>> leftExpr;
    private final Optional<Operator<T>> operator;
    private final Optional<Expr<T>> rightExpr;
    private final boolean isPure;
    private final boolean isInnerExpression;

    Expr(Expr<T> expr, boolean isInnerExpression) {
        this.value = expr.value;
        this.leftExpr = expr.leftExpr;
        this.operator = expr.operator;
        this.rightExpr = expr.rightExpr;
        this.isPure = expr.isPure;
        this.isInnerExpression = isInnerExpression;
    }

    Expr(Expr<T> expr) {
        this.value = expr.value;
        this.leftExpr = expr.leftExpr;
        this.operator = expr.operator;
        this.rightExpr = expr.rightExpr;
        this.isPure = expr.isPure;
        this.isInnerExpression = false;
    }

    Expr(T value) {
        this(Optional.of(value));
    }

    Expr(Optional<T> value) {
        this.value = value;
        this.leftExpr = Optional.empty();
        this.operator = Optional.empty();
        this.rightExpr = Optional.empty();
        this.isPure = true;
        this.isInnerExpression = false;
    }

    Expr(Optional<Expr<T>> leftExpr, Optional<Operator<T>> operator, Optional<Expr<T>> rightExpr) {
        this.value = Optional.empty();
        this.leftExpr = leftExpr;
        this.operator = operator;
        this.rightExpr = rightExpr;
        this.isPure = false;
        this.isInnerExpression = false;
    }

    public static <T> Expr<T> of(T value) {
        return new Expr<T>(value);
    }

    public Expr<T> op(Operator<T> operator, T otherValue) {
        return this.execOp(operator, Expr.<T>of(otherValue));
    }

    public Expr<T> op(Operator<T> operator, Expr<T> otherExpr) {
        return this.execOp(operator, new Expr<T>(otherExpr, true));
    }

    public Expr<T> execOp(Operator<T> operator, Expr<T> otherValue) {
        if (this.isPure) {
            // If this expr is a single value
            return new Expr<T>(Optional.of(this), Optional.of(operator),
                    Optional.of(otherValue));
        } else {
            return extendAboveRightmostNode(operator, otherValue);
        }
    }

    public Expr<T> extendAboveRightmostNode(Operator<T> operator, Expr<T> otherValue) {
        if (this.operator.map(x -> x.compareTo(operator)).orElse(0) > 0 && !this.isInnerExpression) {
            // If we have not yet reached the rightmost inner node or if we haven't reached
            // an inner expression yet
            Optional<Operator<T>> newOp = this.operator;
            Optional<Expr<T>> newRightExpr = this.rightExpr
                    .<Expr<T>>map(x -> x.extendAboveRightmostNode(operator, otherValue));
            return new Expr<T>(this.leftExpr, newOp, newRightExpr);
        } else {
            // If we have reached the rightmost node that has precendence lower than the new
            // op or if we have hit an inner expression
            Optional<Operator<T>> newOp = Optional.of(operator);
            Optional<Expr<T>> newRightExpr = Optional.of(otherValue);
            return new Expr<T>(Optional.of(this), newOp, newRightExpr);
        }

    }

    Optional<T> evaluate() {
        if (this.isPure) {
            // System.out.println(String.format("pure %s", this.value.toString()));
            return this.value;
        } else {
            // is unevaluated
            // System.out.println(this.operator);
            Optional<T> leftValue = this.leftExpr.<T>flatMap(x -> x.evaluate());
            Optional<T> rightValue = this.rightExpr.<T>flatMap(x -> x.evaluate());
            // System.out.println(String.format("left value %s", leftValue.toString()));
            // System.out.println(String.format("right value %s", rightValue.toString()));
            // Fix this to use map later
            Optional<T> evaluatedValue = this.operator
                    .flatMap(op -> leftValue.flatMap(left -> rightValue.map(right -> op.apply(left, right))));
            // System.out.println(String.format("evaluated value %s", evaluatedValue));
            return evaluatedValue;
        }
    }

    @Override
    public String toString() {
        return String.format("(%s)", this.evaluate().<String>map(x -> x.toString()).orElse(""));
    }
}
