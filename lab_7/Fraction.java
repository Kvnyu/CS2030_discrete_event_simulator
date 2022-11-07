import java.util.Optional;

public class Fraction {
    private final Optional<Frac> maybeFrac;

    protected Fraction(Num numerator, Num denominator) {
        if (denominator.equals(Num.zero()) || !numerator.isValid() || !denominator.isValid()) {
            this.maybeFrac = Optional.empty();
        } else {
            this.maybeFrac = Optional.of(Frac.of(numerator, denominator));
        }
    }

    protected Fraction(Optional<Frac> maybeFrac) {
        this.maybeFrac = maybeFrac;
    }

    static Fraction of(int numerator, int denominator) {
        return new Fraction(Num.of(numerator), Num.of(denominator));
    }

    Fraction add(Fraction other) {
        Optional<Num> newNumerator = this.maybeFrac.<Num>flatMap((frac) -> other.maybeFrac.<Num>map(
                (otherFrac) -> (frac.first().mul(otherFrac.second()).add(frac.second().mul(otherFrac.first())))));
        Optional<Num> newDenominator = this.maybeFrac.<Num>flatMap((frac) -> other.maybeFrac.map(
                (otherFrac) -> frac.second().mul(otherFrac.second())));
        Optional<Frac> newFrac = newNumerator.flatMap(num -> newDenominator.map(den -> Frac.of(num, den)));
        return new Fraction(newFrac);
    }

    Fraction sub(Fraction other) {
        Optional<Num> newNumerator = this.maybeFrac.<Num>flatMap((frac) -> other.maybeFrac.<Num>map(
                (otherFrac) -> ((frac.second().mul(otherFrac.first()).sub(frac.first().mul(otherFrac.second()))))));
        System.out.println(newNumerator);
        Optional<Num> newDenominator = this.maybeFrac.<Num>flatMap((frac) -> other.maybeFrac.map(
                (otherFrac) -> frac.second().mul(otherFrac.second())));
        Optional<Frac> newFrac = newNumerator.flatMap(num -> newDenominator.map(den -> Frac.of(num, den)));
        return new Fraction(newFrac);
    }

    Fraction mul(Fraction other) {
        Optional<Num> newNumerator = this.maybeFrac.<Num>flatMap((frac) -> other.maybeFrac.<Num>map(
                (otherFrac) -> (frac.first().mul(otherFrac.first()))));
        Optional<Num> newDenominator = this.maybeFrac.<Num>flatMap((frac) -> other.maybeFrac.<Num>map(
                (otherFrac) -> (frac.second().mul(otherFrac.second()))));
        Optional<Frac> newFrac = newNumerator.flatMap(num -> newDenominator.map(den -> Frac.of(num, den)));
        return new Fraction(newFrac);
    }

    @Override
    public String toString() {
        return this.maybeFrac.map(x -> x.toString()).orElse("NaN");
    }
}
