import java.util.Optional;

class Expr<T> {
    private final Optional<T> value;
    private final Optional<Expr<T>> leftExpr;
    private final Optional<Operator<T>> operator;
    private final Optional<Expr<T>> rightExpr;
    private final boolean isPure;

    Expr(Expr<T> expr) {
        this.value = expr.value;
        this.leftExpr = expr.leftExpr;
        this.operator = expr.operator;
        this.rightExpr = expr.rightExpr;
        this.isPure = expr.isPure;
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
    }

    Expr(Optional<Expr<T>> leftExpr, Optional<Operator<T>> operator, Optional<Expr<T>> rightExpr) {
        this.value = Optional.empty();
        this.leftExpr = leftExpr;
        this.operator = operator;
        this.rightExpr = rightExpr;
        this.isPure = false;
    }

    public static <T> Expr<T> of(T value) {
        return new Expr<T>(value);
    }

    public Expr<T> op(Operator<T> operator, T otherValue) {
        return this.op(operator, Expr.<T>of(otherValue));
    }

    public Expr<T> op(Operator<T> operator, Expr<T> otherValue) {
        if (this.isPure) {
            // If this expr is a single value
            return new Expr<T>(Optional.of(this), Optional.of(operator),
                    Optional.of(otherValue));
        } else {
            if (this.operator.map(x -> x.compareTo(operator)).orElse(0) <= 0) {
                // If the precendence of the operator is lower
                return new Expr<T>(Optional.<Expr<T>>of(this), Optional.<Operator<T>>of(operator),
                        Optional.<Expr<T>>of(otherValue));
            } else {
                // If the precedence of the operator is higher
                return extendRightmostChild(operator, otherValue);
            }
        }
    }

    public Expr<T> extendRightmostChild(Operator<T> operator, Expr<T> otherValue) {
        if (!this.isPure) {
            // If are in an internal node
            Optional<Operator<T>> newOp = this.operator;
            Optional<Expr<T>> newRightExpr = this.rightExpr
                    .<Expr<T>>map(x -> x.extendRightmostChild(operator, otherValue));
            return new Expr<T>(this.leftExpr, newOp, newRightExpr);
        } else {
            // If we have reached the last node
            Optional<Operator<T>> newOp = Optional.of(operator);
            Optional<Expr<T>> newRightExpr = Optional.of(otherValue);
            return new Expr<T>(Optional.of(this), newOp, newRightExpr);
        }
    }

    Optional<T> evaluate() {
        if (this.isPure) {
            System.out.println(String.format("pure %s", this.value.toString()));
            return this.value;
        } else {
            // is unevaluated
            System.out.println(this.operator);
            Optional<T> leftValue = this.leftExpr.<T>flatMap(x -> x.evaluate());
            Optional<T> rightValue = this.rightExpr.<T>flatMap(x -> x.evaluate());
            System.out.println(String.format("left value %s", leftValue.toString()));
            System.out.println(String.format("right value %s", rightValue.toString()));
            // Fix this to use map later
            Optional<T> evaluatedValue = this.operator
                    .map(x -> x.apply(leftValue.orElseThrow(), rightValue.orElseThrow()));
            System.out.println(String.format("evaluated value %s", evaluatedValue));
            return evaluatedValue;
        }
    }

    @Override
    public String toString() {
        return String.format("(%s)", this.evaluate().<String>map(x -> x.toString()).orElse(""));
    }
}
