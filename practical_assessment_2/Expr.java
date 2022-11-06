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
        if (this.isPure) {
            return new Expr<T>(this.leftExpr, Optional.of(operator),
                    Optional.of(new Expr<T>(otherValue)));
        } else {
            if (this.operator.map(x -> x.compareTo(operator)).orElse(0) <= 0) {
                return new Expr<T>(Optional.<Expr<T>>of(this), Optional.<Operator<T>>of(operator),
                        Optional.<Expr<T>>of(new Expr<T>(otherValue)));
            } else {
                return extendRightmostChild(operator, otherValue);
            }
        }
        // If this expr is a single value
        // If the precendence of the operator is lower
        // If the precedence of the operator is higher
    }

    public Expr<T> extendRightmostChild(Operator<T> operator, T otherValue) {
        if (!this.isPure) {
            Optional<Expr<T>> newLeftExpr = this.leftExpr;
            Optional<Operator<T>> newOp = this.operator;
            Optional<Expr<T>> newRightExpr = this.rightExpr
                    .<Expr<T>>map(x -> x.extendRightmostChild(operator, otherValue));
            return new Expr<T>(newLeftExpr, newOp, newRightExpr);
        } else {
            Optional<Expr<T>> newLeftExpr = this.value.map(x -> new Expr<T>(x));
            Optional<Operator<T>> newOp = Optional.of(operator);
            Optional<Expr<T>> newRightExpr = Optional.of(Expr.of(otherValue));
            return new Expr<T>(newLeftExpr, newOp, newRightExpr);
        }
    }

    @Override
    public String toString() {
        return String.format("(%s)", this.value.toString());
    }
}
