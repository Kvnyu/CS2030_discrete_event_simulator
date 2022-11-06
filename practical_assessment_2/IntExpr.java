class IntExpr extends AbstractIntExpr {
    private IntExpr(Expr<Integer> expr) {
        super(expr);
    }

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
        return new IntExpr(this.op(AbstractIntExpr.division, otherValue));
    }

    public IntExpr exp(int otherValue) {
        return new IntExpr(this.op(AbstractIntExpr.exponentiation, otherValue));
    }

    public IntExpr sub(int otherValue) {
        return new IntExpr(this.op(AbstractIntExpr.subtraction, otherValue));
    }
}
