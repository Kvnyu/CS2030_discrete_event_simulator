import java.util.function.BinaryOperator;

class Operator<T> implements Comparable<Operator<T>>{
    private final BinaryOperator<T> operator;
    private final int precedence;

    private Operator(BinaryOperator<T> operator, int precedence){
        this.operator = operator;
        this.precedence = precedence;
    }

    @Override
    public int compareTo(Operator<T> other){
        return this.precedence - other.precedence;
    }

    public static <T> Operator<T> of(BinaryOperator<T> operator, int precedence){
        return new Operator<>(operator, precedence);
    }

    public T apply(T o1, T o2){
        return this.operator.apply(o1, o2);
    }

    @Override
    public String toString(){
        return String.format("Operator of precedence %d", this.precedence);
    }

}
