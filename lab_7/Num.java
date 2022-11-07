import java.util.Optional;

public class Num extends AbstractNum<Integer> {
    protected Num(int i) {
        super(i >= 0 ? Optional.<Integer>of(i) : Optional.empty());
    }

    protected Num(Optional<Integer> i) {
        super(i);
    }

    protected Num(AbstractNum<Integer> i) {
        super(i.opt);
    }

    static Num of(int i) {
        return new Num(i);
    }

    static Num zero() {
        return new Num(AbstractNum.zero());
    }

    static Num one() {
        return new Num(new AbstractNum<Integer>(1));
    }

    Num succ() {
        return new Num(new AbstractNum<Integer>(this.opt.map(x -> AbstractNum.s.apply(x))));
    }

    Num add(Num other) {
        if (!other.isValid()) {
            return Num.of(-1);
        }
        Optional<Integer> maybeNum = this.opt
                .flatMap((first) -> other.opt.map(second -> {
                    int result = first.intValue();
                    for (int i = second.intValue(); i > 0; i--) {
                        result = AbstractNum.s.apply(result);
                    }
                    return result;
                }));

        return new Num(maybeNum);
    }

    Num sub(Num other) {
        if (!other.isValid()) {
            return Num.of(-1);
        }
        AbstractNum<Integer> negativeOther = new AbstractNum<Integer>(other.opt.map(x -> AbstractNum.n.apply(x)));
        return new Num(negativeOther).add(this);
    }

    Num mul(Num other) {
        if (!this.isValid() || !other.isValid()) {
            return Num.of(-1);
        }
        Optional<Integer> maybeNum = other.opt.map((value) -> {
            Num result = Num.zero();
            for (int i = 0; i < value; i++) {
                result = result.add(this);
            }
            return result;
        }).flatMap(value -> value.opt);

        return new Num(maybeNum);

    }
}
