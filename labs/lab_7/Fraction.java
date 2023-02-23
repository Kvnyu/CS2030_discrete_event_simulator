import java.util.Optional;

public class Fraction extends AbstractNum<Frac> {
    protected Fraction(Num numerator, Num denominator) {
        super(denominator.equals(Num.zero())
                || !numerator.isValid() || !denominator.isValid() ? Optional.empty()
                        : Optional.of(Frac.of(numerator, denominator)));
    }

    protected Fraction(Optional<Frac> maybeFrac) {
        super(maybeFrac.filter(x -> x.first().isValid() && x.second().isValid()));
    }

    static Fraction of(int numerator, int denominator) {
        return AbstractNum.valid.test(numerator) && AbstractNum.valid.test(denominator)
                ? new Fraction(Num.of(numerator), Num.of(denominator))
                : new Fraction(Num.invalid(), Num.invalid());
    }

    Fraction add(Fraction other) {
        Optional<Num> newNumerator = this.opt.<Num>flatMap((frac) -> other.opt.<Num>map(
                (otherFrac) -> (frac.first().mul(
                        otherFrac.second()).add(frac.second().mul(otherFrac.first())))));
        Optional<Num> newDenominator = this.opt.<Num>flatMap((frac) -> other.opt.map(
                (otherFrac) -> frac.second().mul(otherFrac.second())));
        Optional<Frac> newFrac = newNumerator.flatMap(num -> newDenominator.map(
                den -> Frac.of(num, den)));
        return new Fraction(newFrac);
    }

    Fraction sub(Fraction other) {
        Optional<Num> newNumerator = this.opt.<Num>flatMap((frac) -> other.opt.<Num>map(
                (otherFrac) -> ((frac.first().mul(
                        otherFrac.second()).sub(otherFrac.first().mul(frac.second()))))));
        Optional<Num> newDenominator = this.opt.<Num>flatMap((frac) -> other.opt.map(
                (otherFrac) -> frac.second().mul(otherFrac.second())));
        Optional<Frac> newFrac = newNumerator.flatMap(num -> newDenominator.map(
                den -> Frac.of(num, den)));
        return new Fraction(newFrac);
    }

    Fraction mul(Fraction other) {
        Optional<Num> newNumerator = this.opt.<Num>flatMap((frac) -> other.opt.<Num>map(
                (otherFrac) -> (frac.first().mul(otherFrac.first()))));
        Optional<Num> newDenominator = this.opt.<Num>flatMap((frac) -> other.opt.<Num>map(
                (otherFrac) -> (frac.second().mul(otherFrac.second()))));
        Optional<Frac> newFrac = newNumerator.flatMap(num -> newDenominator.map(
                den -> Frac.of(num, den)));
        return new Fraction(newFrac);
    }

}
