import java.util.Optional;

public class Num extends AbstractNum<Integer> {
    protected Num() {
        super(Optional.empty());
    }

    protected Num(int i) {
        super(AbstractNum.valid.test(i)
                ? Optional.<Integer>of(i)
                : Optional.empty());
    }

    protected Num(Optional<Integer> i) {
        super(i);
    }

    protected Num(AbstractNum<Integer> i) {
        super(i.opt);
    }

    static Num invalid() {
        return new Num();
    }

    static Num of(int i) {
        return new Num(i);
    }

    static Num zero() {
        return new Num(AbstractNum.zero());
    }

    static Num one() {
        return new Num(AbstractNum.zero().opt.map((x -> AbstractNum.s.apply(x))));
    }

    Num succ() {
        return new Num(new AbstractNum<Integer>(this.opt.map(x -> AbstractNum.s.apply(x))));
    }

    Num add(Num other) {
        if (!other.isValid()) {
            return Num.invalid();
        }
        Optional<Integer> maybeNum = this.opt
                .flatMap((first) -> other.opt.map(second -> {
                    int result = first.intValue();
                    Num i = Num.zero();
                    while (!i.equals(new Num(second))) {
                        result = AbstractNum.s.apply(result);
                        i = new Num(i.opt.map(x -> AbstractNum.s.apply(x)));
                    }
                    return result;
                })).filter(AbstractNum.valid);

        return new Num(maybeNum);
    }

    Num sub(Num other) {
        if (!other.isValid()) {
            return Num.invalid();
        }
        AbstractNum<Integer> negativeOther = new AbstractNum<Integer>(
                other.opt.map(x -> AbstractNum.n.apply(x)));
        Num result = new Num(negativeOther).add(this);
        return result;
    }

    Num mul(Num other) {
        if (!this.isValid() || !other.isValid()) {
            return Num.invalid();
        }
        Optional<Integer> maybeNum = other.opt.map((second) -> {
            Num result = Num.zero();
            Num i = Num.zero();
            while (!i.equals(new Num(second))) {
                i = new Num(i.opt.map(x -> AbstractNum.s.apply(x)));
                result = result.add(this);
            }
            return result;
        }).flatMap(value -> value.opt).filter(AbstractNum.valid);
        return new Num(maybeNum);
    }

}