
abstract class AbstractIntExpr extends Expr<Integer> {
    protected static final Operator<Integer> addition = Operator.<Integer>of((x, y) -> {
        System.out.println("#");
        return x + y;
    }, 4);
    protected static final Operator<Integer> multiplication = Operator.<Integer>of((x, y) -> {
        System.out.println("#");
        return x * y;
    }, 3);
    protected static final Operator<Integer> subtraction = Operator.<Integer>of((x, y) -> {
        System.out.println("#");
        return x - y;
    }, 4);
    protected static final Operator<Integer> exponentiation = Operator.<Integer>of((x, y) -> {
        System.out.println("#");
        return (int) Math.pow(x, y);
    }, 2);
    protected static final Operator<Integer> division = Operator.<Integer>of((x, y) -> {
        System.out.println("#");
        return x / y;
    }, 3);

    protected AbstractIntExpr(Expr<Integer> expr) {
        super(expr);
    }
}
