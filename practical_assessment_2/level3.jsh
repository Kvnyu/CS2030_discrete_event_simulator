Operator<Integer> add = Operator.<Integer>of((x, y) -> x + y, 4);
Operator<Integer> mul = Operator.<Integer>of((x, y) -> x * y, 3);
Expr.<Integer>of(2).op(mul, 3).op(add, 4);
Expr.<Integer>of(2).op(add, 3).op(mul, 4);
Expr.<Integer>of(2).op(add, 3).op(mul, 4).op(mul, 5);
Expr.<Integer>of(2).op(add, 3).op(mul, 4).op(add, 5);
Expr.<Integer>of(2).op(mul, 3).op(add, 4).op(mul, 5);
Operator<Integer> pow = Operator.<Integer>of((x, y) -> (int)Math.pow(x,y), 2);
Expr.of(2).op(add, 3).op(pow, 4).op(mul, 5)