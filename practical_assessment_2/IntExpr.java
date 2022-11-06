class IntExpr extends AbstractIntExpr {
    private IntExpr(Expr<Integer> expr) {
        super(expr);
    }

    public static IntExpr of(int value) {
        Expr<Integer> expr = Expr.<Integer>of(value);
        return new IntExpr(expr);
    }

    // public IntExpr mul(int otherValue){
    // return IntExpr.of(newValue);
    // }

    // public IntExpr add(int otherValue){
    // int newValue = AbstractIntExpr.addition.apply(this.getValue(), otherValue);
    // return IntExpr.of(newValue);
    // }
}
