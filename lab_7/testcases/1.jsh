Num.one();
Num.zero().succ();
Num.one().equals(Num.zero().succ());
Num.of(-1).succ();
Num.one().succ().succ();
Num.of(-1).succ().succ();

Num one = Num.one();
Num zero = Num.zero();
Num invalid = Num.of(-1);
zero.add(zero);
zero.add(one);
zero.add(invalid);
one.add(zero);
one.add(one);
one.add(invalid);
invalid.add(zero);
Num result = zero.add(one).add(zero).add(one);
result.add(result);
zero.add(invalid).add(zero).add(one);


Num one = Num.one();
Num zero = Num.zero();
Num invalid = Num.of(-1);
zero.sub(zero);
zero.sub(invalid);
one.sub(zero);
one.sub(one);
one.sub(invalid);
invalid.sub(zero);
zero.mul(zero);
zero.mul(one);
zero.mul(invalid);
one.mul(zero);
one.mul(one);
one.mul(invalid);
invalid.mul(zero);
Num.of(2).mul(Num.of(3));


Fraction.of(1, 2);
Fraction.of(2, 1);
Fraction.of(2, 2);
Fraction.of(0, 2);
Fraction.of(2, 0);
Fraction.of(-1, 2);
Fraction.of(1, -2);

Fraction.of(1, 2).add(Fraction.of(1, 4));
Fraction.of(-1, 2).add(Fraction.of(1, 4));
Fraction.of(1, 4).sub(Fraction.of(1, 2));
Fraction.of(1, 2).mul(Fraction.of(2, 1));
Fraction.of(1, 2).mul(Fraction.of(2, 0));