class IntExpr extends AbstractIntExpr {
    private IntExpr(Expr<Integer> expr) {
        super(expr);
    }

    private final Operator<Integer> subtraction = Operator.<Integer>of((x, y) -> {
        return x - y;
    }, 4);
    private final Operator<Integer> exponentiation = Operator.<Integer>of((x, y) -> {
        int result = 1;
        for (int i = 0; i < y; i++) {
            result *= x;
        }
        return result;
    }, 2);
    private final Operator<Integer> division = Operator.<Integer>of((x, y) -> {
        return x / y;
    }, 3);

    public static IntExpr of(int value) {
        Expr<Integer> expr = Expr.<Integer>of(value);
        return new IntExpr(expr);
    }

    public IntExpr add(int otherValue) {
        return new IntExpr(this.op(AbstractIntExpr.addition, otherValue));
    }

    public IntExpr mul(int otherValue) {
        return new IntExpr(this.op(AbstractIntExpr.multiplication, otherValue));
    }

    public IntExpr div(int otherValue) {
        return new IntExpr(this.op(division, otherValue));
    }

    public IntExpr exp(int otherValue) {
        return new IntExpr(this.op(exponentiation, otherValue));
    }

    public IntExpr sub(int otherValue) {
        return new IntExpr(this.op(subtraction, otherValue));
    }
}
