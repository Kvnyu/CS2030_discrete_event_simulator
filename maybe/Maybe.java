import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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

    static <R> Maybe<R> ofNullable(R thing) {
        if (thing == null) {
            return Maybe.empty();
        } else {
            return Maybe.<R>of(thing);
        }
    }

    Maybe<T> filter(Predicate<? super T> filterer) {
        if (this.thing == null) {
            return Maybe.<T>empty();
        }
        if (filterer.test(this.thing)) {
            return this;
        }
        return Maybe.<T>empty();
    }

    void ifPresent(Consumer<? super T> presentFunction) {
        if (this.thing != null) {
            presentFunction.accept(this.thing);
        }
    }

    void ifPresentOrElse(Consumer<? super T> presentFunction, Runnable elseFunction) {
        if (this.thing != null) {
            presentFunction.accept(this.thing);
        } else {
            elseFunction.run();
        }
    }

    T orElse(T otherValue) {
        if (this.thing != null) {
            return this.thing;
        } else {
            return otherValue;
        }
    }

    T orElseGet(Supplier<? extends T> supplier) {
        return this.thing == null ? supplier.get() : this.thing;
    }

    Maybe<? extends Object> or(Supplier<? extends Maybe<? extends Object>> supplier) {
        return this.thing != null ? this : supplier.get();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Maybe)) {
            return false;
        }
        final Maybe<?> other = (Maybe<?>) obj;
        if (this.thing == null && other.thing == null) {
            return true;
        }
        if (this.thing != null && other.thing != null) {
            return other.thing.equals(this.thing);
        }
        return false;
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