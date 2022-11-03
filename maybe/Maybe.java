import java.util.function.Function;

class Maybe<T> {
    private final T thing;

    private Maybe(T thing) {
        this.thing = thing;
    }

    static <U> Maybe<U> of(U thing) {
        if (thing == null) {
            throw new NullPointerException();
        }
        return new Maybe<U>(thing);
    }

    static <T> Maybe<T> empty() {
        return new Maybe<T>(null);
    }

    <R> Maybe<R> map(Function<? super T, ? extends R> mapper) {
        if (this.thing == null) {
            return Maybe.<R>empty();
        } else {
            return Maybe.<R>of(mapper.apply(this.thing));
        }
    }

    <R> Maybe<R> ofNullable(R thing) {
        if (thing == null) {
            return Maybe.empty();
        } else {
            return Maybe.<R>of(thing);
        }
    }

    T unwrap() {
        return this.thing;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Maybe<?> other = (Maybe<?>) obj;
        return other.unwrap() == this.unwrap();
    }

    @Override
    public String toString() {
        if (this.thing == null) {
            return "Maybe.empty";
        } else {
            return "Maybe[" + this.thing + "]";
        }
    }
}
